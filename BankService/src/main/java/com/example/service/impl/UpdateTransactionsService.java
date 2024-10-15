package com.example.service.impl;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.entity.BookPrice;
import com.example.entity.MoneyAccount;
import com.example.entity.SubscriptionTransaction;
import com.example.feign.SubscriptionServiceClient;
import com.example.repository.BookPriceRepository;
import com.example.repository.MoneyAccountRepository;
import com.example.repository.SubscriptionTransactionRepository;
import dao.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTransactionsService {
    private static final BigDecimal LEFT_PRICE_PERCENT = new BigDecimal("0.7");
    @Autowired
    private SubscriptionTransactionRepository transactionRepository;
    @Value("${transaction.pending.batch.size}")
    private int batchSize;
    @Autowired
    private SubscriptionServiceClient subscriptionServiceClient;
    @Autowired
    private BookPriceRepository bookPriceRepository;
    @Autowired
    private MoneyAccountRepository moneyAccountRepository;
    private Map<String, BookPrice> cachedBookPrices;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void updateTransactions() {
        List<SubscriptionTransaction> pendingTransactions = transactionRepository.findPending(batchSize);
        if (pendingTransactions.isEmpty()) {
            return;
        }
        List<String> pendingSubsIds =
                pendingTransactions.stream().map(SubscriptionTransaction::getSubscriptionId).toList();
        List<SubscriptionDao> subscriptions =
                subscriptionServiceClient.getSubscriptionsForProcessingTransaction(pendingSubsIds);
        Set<String> nonCanceledSubIds = subscriptions.stream().map(s -> s.id).collect(Collectors.toSet());
        transactionRepository.processTransactions(nonCanceledSubIds);

        List<String> canceledSubIds = pendingTransactions.stream()
                .map(SubscriptionTransaction::getSubscriptionId)
                .filter(sId -> !nonCanceledSubIds.contains(sId))
                .toList();
        transactionRepository.cancelTransactions(canceledSubIds);

        Map<String, BigDecimal> moneyCalculations = calculateAmounts(subscriptions);
        sendMoney(moneyCalculations);
    }

    private void sendMoney(Map<String, BigDecimal> usersMoney) {
        usersMoney.forEach((userId, amount) -> {
            moneyAccountRepository.findByUserId(userId).ifPresent(acc -> {
                acc.setBalance(acc.getBalance().add(amount));
                moneyAccountRepository.save(acc);
            });
        });
    }

    // userId -> amount to add
    private Map<String, BigDecimal> calculateAmounts(List<SubscriptionDao> subscriptions) {
        Set<String> bookIds = subscriptions.stream().flatMap(s -> s.booksIds.stream()).collect(Collectors.toSet());
        cachedBookPrices = new HashMap<>();
        bookPriceRepository.findAllById(bookIds).forEach(bp -> {
            cachedBookPrices.put(bp.getBookId(), bp);
        });

        Map<String, BigDecimal> res = new HashMap<>();
        for (SubscriptionDao sub : subscriptions) {
            // for buyer should withdraw money
            String userId = sub.userId;
            res.put(userId, res.getOrDefault(userId, BigDecimal.ZERO).subtract(sub.price));

            // for creators/sellers should add this money
            calculateAmountsForCreators(sub.booksIds, sub.price)
                    .forEach(
                            (creator, amount) ->
                                    res.put(creator, res.getOrDefault(creator, BigDecimal.ZERO).add(amount))
                    );
        }
        return res;
    }

    private Map<String, BigDecimal> calculateAmountsForCreators(List<String> books, BigDecimal subscriptionPrice) {
        BigDecimal allBooksPrice = books.stream()
                .map(bId -> cachedBookPrices.get(bId).getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return books.stream().map(bId -> cachedBookPrices.get(bId))
                .collect(
                        Collectors.groupingBy(
                                BookPrice::getUploader,
                                Collectors.reducing(BigDecimal.ZERO, BookPrice::getPrice, BigDecimal::add)
                        )
                )
                .entrySet()
                .stream()
                .map(e -> {
                    String creator = e.getKey();
                    BigDecimal amountBeforeFee = e.getValue();
                    BigDecimal part = amountBeforeFee.divide(allBooksPrice);
                    BigDecimal moneyPart = part.multiply(subscriptionPrice);
                    BigDecimal amountAfterFee = moneyPart.multiply(LEFT_PRICE_PERCENT);
                    return new AbstractMap.SimpleEntry<>(creator, amountAfterFee);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    }
}
