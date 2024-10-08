package com.example.service;

import dao.BookInfoModerationDao;

public interface BookModerationService {
    BookInfoModerationDao getBookInfoForModeration(String bookInfoId);
}
