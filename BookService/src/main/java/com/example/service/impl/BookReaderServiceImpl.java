package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.dao.BookInfoDao;
import com.example.dao.filter.FilterSortState;
import com.example.dao.filter.cond.FilterCond;
import com.example.dao.filter.sort.DefaultSortCond;
import com.example.entity.BookInfo;
import com.example.feign.SubscriptionServiceClient;
import com.example.repository.BookInfoRepository;
import com.example.service.BookReaderService;
import dao.BookInfoReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import util.HttpUtil;

@Service
public class BookReaderServiceImpl implements BookReaderService {
    @Autowired
    private BookInfoRepository bookInfoRepository;
    @Autowired
    private SubscriptionServiceClient subscriptionServiceClient;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final int DEFAULT_LIMIT = 20;

    @Override
    public boolean isAllBooksPublished(List<String> bookIds) {
        for (BookInfo bookInfo : bookInfoRepository.findAllById(bookIds)) {
            if (!bookInfo.isPublished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<BookInfoReaderDao> getBoughtBooks(String userId) {
        List<BookInfoReaderDao> res = new ArrayList<>();
        bookInfoRepository.findAllById(subscriptionServiceClient.getAvailableBooksByUser(userId))
                .forEach(b -> {
                    String downloadUrl = HttpUtil.buildDownloadLink(b.getFileUUID(), false);
                    res.add(
                            new BookInfoReaderDao(
                                    b.getId(),
                                    b.getShortDescription(),
                                    b.getAuthor(),
                                    b.getTitle(),
                                    downloadUrl
                            )
                    );
                });
        return res;
    }

    @Override
    public List<BookInfoDao> getBooks(FilterSortState state, Integer offset, Integer limit) {
        String filtersPart = state == null || state.filterConds == null ?
                "" :
                state.filterConds.stream()
                        .map(FilterCond::buildSqlCond)
                        .map(c -> "(" + c + ")")
                        .collect(Collectors.joining(" AND "));
        if (!filtersPart.isEmpty()) {
            filtersPart = "WHERE " + filtersPart + "\n";
        }
        String orderPart = state == null || state.sortCond == null ?
                new DefaultSortCond().buildSqlCond() + "\n" :
                state.sortCond.buildSqlCond() + "\n";

        String query = new StringBuilder("SELECT *").append("\n")
                .append("FROM ").append(BookInfo.TABLE_NAME).append('\n')
                .append(filtersPart)
                .append(orderPart)
                .append("LIMIT ").append(limit == null ? DEFAULT_LIMIT : limit).append("\n")
                .append("OFFSET ").append(offset == null ? 0 : offset).append("\n").toString();

        System.out.println(query);

        return jdbcTemplate.query(
                query,
                (rs, num) -> new BookInfoDao(
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("short_description")
                )
        );
    }
}
