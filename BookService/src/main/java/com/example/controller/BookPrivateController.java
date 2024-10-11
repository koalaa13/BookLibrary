package com.example.controller;

import java.util.List;

import com.example.repository.BookInfoRepository;
import dao.BookInfoPriceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookPrivateController {
    @Autowired
    private BookInfoRepository bookInfoRepository;

    @GetMapping(value = "/prices")
    public List<BookInfoPriceDao> getBooksPrices(List<String> bookIds) {
        return bookInfoRepository.getPrices(bookIds);
    }
}
