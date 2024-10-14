package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.repository.BookInfoRepository;
import dao.BookInfoPriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPrivateController {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @PostMapping(value = "/prices")
    public List<BookInfoPriceDao> getBooksPrices(@RequestBody List<String> bookIds) {
        List<BookInfoPriceDao> res = new ArrayList<>();
        bookInfoRepository.findAllById(bookIds)
                .forEach(b -> res.add(new BookInfoPriceDao(b.getId(), b.getPrice())));
        return res;
    }
}
