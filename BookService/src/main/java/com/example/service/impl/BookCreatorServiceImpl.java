package com.example.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.BookInfoDao;
import com.example.dao.BookInfoForCreatorDao;
import com.example.dao.ModerationResultMessageResponse;
import com.example.dao.ModerationResultResponse;
import com.example.dao.ModerationResultWithErrorsResponse;
import com.example.entity.BookInfo;
import com.example.exception.ChangeInModerationStatusException;
import com.example.exception.ChangePublishedException;
import com.example.exception.PublicationException;
import com.example.feign.BankServiceClient;
import com.example.feign.ModerationServiceClient;
import com.example.repository.BookInfoRepository;
import com.example.service.BookCreatorService;
import dao.BookInfoPriceUploaderDao;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.StringUtil;

@Service
public class BookCreatorServiceImpl implements BookCreatorService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private ModerationServiceClient moderationServiceClient;
    @Autowired
    private BankServiceClient bankServiceClient;

    @Override
    public BookInfo createBookInfo(BookInfoDao bookInfoDao, String uploader) {
        return bookInfoRepository.save(
                new BookInfo(
                        bookInfoDao.shortDescription,
                        bookInfoDao.author,
                        bookInfoDao.title,
                        uploader,
                        Instant.now()
                )
        );
    }

    @Override
    public String getBookInfoUploader(String id) {
        return bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id))
                .getUploader();
    }

    @Override
    public void publishBook(String id) {
        BookInfo book = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        if (book.isPublished()) {
            throw new PublicationException("Already published");
        }
        if (book.isInModeration()) {
            throw new PublicationException("Moderation is not finished");
        }
        if (!book.isModerationSuccess()) {
            throw new PublicationException("Moderation is failed");
        }
        book.setPublished(true);
        bookInfoRepository.save(book);
    }

    @Override
    public void updateBookPrice(String id, BigDecimal price) {
        bookInfoRepository.findById(id).ifPresentOrElse(
                found -> {
                    if (found.isPublished()) {
                        throw new ChangePublishedException("Can't change price because book is already published");
                    }
                    bankServiceClient.updatePrice(new BookInfoPriceUploaderDao(id, price, found.getUploader()));
                },
                () -> {
                    throw new NoSuchEntityException(BookInfo.class, id);
                }
        );
    }

    @Override
    public void updateBookInfo(String id, BookInfoDao bookInfoDao) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        if (bookInfo.isModerationSuccess()) {
            throw new ChangeInModerationStatusException("Can't update book info because it's already moderated");
        }
        if (bookInfo.isInModeration()) {
            throw new ChangeInModerationStatusException("Can't update book info because it sent to moderation");
        }

        if (!StringUtil.isEmpty(bookInfoDao.title)) {
            bookInfo.setTitle(bookInfoDao.title);
        }
        if (!StringUtil.isEmpty(bookInfoDao.author)) {
            bookInfo.setAuthor(bookInfoDao.author);
        }
        if (!StringUtil.isEmpty(bookInfoDao.shortDescription)) {
            bookInfo.setShortDescription(bookInfoDao.shortDescription);
        }
        bookInfoRepository.save(bookInfo);
    }

    @Override
    public void updateBookFile(String id, String fileUUID) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        if (bookInfo.isModerationSuccess()) {
            throw new ChangeInModerationStatusException("Can't update book info because it's already moderated");
        }
        if (bookInfo.isInModeration()) {
            throw new ChangeInModerationStatusException("Can't update book info because it sent to moderation");
        }

        if (!StringUtil.isEmpty(fileUUID)) {
            bookInfo.setFileUUID(fileUUID);
        }
        bookInfoRepository.save(bookInfo);
    }

    @Transactional
    @Override
    public List<BookInfoForCreatorDao> getAllBooksInfoByUploader(String uploader) {
        List<BookInfo> books = bookInfoRepository.findAllByUploader(uploader);
        List<String> bookIds = books.stream().map(BookInfo::getId).toList();
        var prices = bankServiceClient.getPrices(bookIds)
                .stream()
                .collect(Collectors.toMap(bp -> bp.bookId, bp -> bp.price));
        return books.stream()
                .map(b -> new BookInfoForCreatorDao(
                                prices.get(b.getId()),
                                b.isInModeration(),
                                b.isModerationSuccess(),
                                b.getShortDescription(),
                                b.getAuthor(),
                                b.getTitle(),
                                b.isPublished()
                        )
                )
                .toList();
    }

    @Override
    public ModerationResultResponse buildModerationResultResponse(String id) {
        BookInfo bookInfo = bookInfoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(BookInfo.class, id));
        String moderationResultId = bookInfo.getModerationResultId();
        if (moderationResultId == null) {
            return new ModerationResultMessageResponse(
                    bookInfo.isInModeration() ?
                            "Can't get moderation result because books moderation is not finished yet" :
                            "Book was not send to moderation"
            );
        } else {
            return bookInfo.isModerationSuccess() ?
                    new ModerationResultMessageResponse("Moderation is successful") :
                    new ModerationResultWithErrorsResponse(
                            "Moderators found some errors",
                            moderationServiceClient.getModerationResultItems(moderationResultId)
                    );
        }
    }
}
