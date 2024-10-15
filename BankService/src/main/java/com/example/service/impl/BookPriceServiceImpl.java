package com.example.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.entity.BookPrice;
import com.example.repository.BookPriceRepository;
import com.example.service.BookPriceService;
import dao.BookInfoPriceDao;
import exception.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookPriceServiceImpl implements BookPriceService {
    @Autowired
    private BookPriceRepository bookPriceRepository;

    @Override
    public void create(String bookId, BigDecimal price, String uploader) {
        bookPriceRepository.save(new BookPrice(bookId, price, uploader));
    }

    @Transactional
    @Override
    public void update(String bookId, BigDecimal price) {
        var found = bookPriceRepository.findById(bookId)
                .orElseThrow(() -> new NoSuchEntityException(BookPrice.class, bookId));
        updatePrice(found, price);
    }

    private void updatePrice(BookPrice bookPrice, BigDecimal price) {
        bookPrice.setPrice(price);
        bookPriceRepository.save(bookPrice);
    }

    @Transactional
    @Override
    public void createOrUpdate(String bookId, BigDecimal price, String uploader) {
        bookPriceRepository.findById(bookId).ifPresentOrElse(
                found -> updatePrice(found, price),
                () -> create(bookId, price, uploader)
        );
    }

    @Override
    public List<BookInfoPriceDao> getPrices(List<String> bookIds) {
        List<BookInfoPriceDao> res = new ArrayList<>();
        bookPriceRepository.findAllById(bookIds)
                .forEach(bp -> res.add(new BookInfoPriceDao(bp.getBookId(), bp.getPrice())));
        return res;
    }
}
