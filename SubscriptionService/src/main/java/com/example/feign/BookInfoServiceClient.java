package com.example.feign;

import java.util.List;

import dao.BookInfoPriceDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BookInfoService", url = "http://localhost:8090")
public interface BookInfoServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/prices")
    List<BookInfoPriceDao> getPrices(@RequestBody List<String> bookIds);
}
