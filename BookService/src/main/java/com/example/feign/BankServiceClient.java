package com.example.feign;

import java.util.List;

import dao.BookInfoPriceDao;
import dao.BookInfoPriceUploaderDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BankService", url = "http://bank-service")
public interface BankServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/book/price/createOrUpdate")
    void updatePrice(@RequestBody BookInfoPriceUploaderDao info);

    @RequestMapping(method = RequestMethod.POST, value = "/book/prices")
    List<BookInfoPriceDao> getPrices(@RequestBody List<String> bookIds);
}
