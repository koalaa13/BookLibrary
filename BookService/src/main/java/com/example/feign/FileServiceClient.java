package com.example.feign;

import dao.FileResponse;
import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "FileService", url = "http://file-service")
public interface FileServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Headers("Content-Type: multipart/form-data")
    FileResponse uploadFile(@Param("file") MultipartFile file);
}
