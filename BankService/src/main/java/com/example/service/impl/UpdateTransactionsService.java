package com.example.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.entity.BookPrice;
import com.example.entity.SubscriptionTransaction;
import com.example.feign.SubscriptionServiceClient;
import com.example.repository.BookPriceRepository;
import com.example.repository.SubscriptionTransactionRepository;
import dao.SubscriptionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateTransactionsService {
    @Autowired
    private SubscriptionTransactionRepository transactionRepository;
    @Value("${transaction.pending.batch.size}")
    private int batchSize;
    @Autowired
    private SubscriptionServiceClient subscriptionServiceClient;
    @Autowired
    private BookPriceRepository bookPriceRepository;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void updateTransactions() {
        List<SubscriptionTransaction> pendingTransactions = transactionRepository.findPending(batchSize);
        if (pendingTransactions.isEmpty()) {
            return;
        }
        List<String> pendingSubsIds = pendingTransactions.stream().map(SubscriptionTransaction::getSubscriptionId).toList();
        List<SubscriptionDao> subscriptions =
                subscriptionServiceClient.getSubscriptionsForProcessingTransaction(pendingSubsIds);
        Set<String> nonCanceledSubIds = subscriptions.stream().map(s -> s.id).collect(Collectors.toSet());
        transactionRepository.processTransactions(nonCanceledSubIds);

        List<String> canceledSubIds = pendingTransactions.stream()
                .map(SubscriptionTransaction::getSubscriptionId)
                .filter(sId -> !nonCanceledSubIds.contains(sId))
                .toList();
        transactionRepository.cancelTransactions(canceledSubIds);

        sendMoney(subscriptions);
    }

    private void sendMoney(List<SubscriptionDao> subscriptions) {
        Set<String> bookIds = subscriptions.stream().flatMap(s -> s.booksIds.stream()).collect(Collectors.toSet());
        Map<String, BookPrice> bookPrices = new HashMap<>();
        bookPriceRepository.findAllById(bookIds).forEach(bp -> {
            bookPrices.put(bp.getBookId(), bp);
        });
        // TODO give/take money here
    }
}
