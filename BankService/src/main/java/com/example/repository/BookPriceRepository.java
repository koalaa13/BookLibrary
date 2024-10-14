package com.example.repository;

import com.example.entity.BookPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookPriceRepository extends CrudRepository<BookPrice, String> {
}
