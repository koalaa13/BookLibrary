package com.example.feign;

import java.util.List;

import dao.BookInfoPriceDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BankService", url = "http://bank-service")
public interface BankServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/transaction/create/{id}")
    boolean createTransaction(@PathVariable("id") String subscriptionId);

    @RequestMapping(method = RequestMethod.POST, value = "/book/prices")
    List<BookInfoPriceDao> getPrices(@RequestBody List<String> bookIds);
}
