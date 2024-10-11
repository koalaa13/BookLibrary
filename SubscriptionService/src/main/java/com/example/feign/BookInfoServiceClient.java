package com.example.feign;

import java.util.List;

import dao.BookInfoPriceDao;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "BookInfoService", url = "http://localhost:8090")
public interface BookInfoServiceClient {
    @RequestMapping(method = RequestMethod.GET, value = "/prices")
    List<BookInfoPriceDao> getPrices(List<String> bookIds);
}
