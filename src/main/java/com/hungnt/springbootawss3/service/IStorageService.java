package com.hungnt.springbootawss3.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    public String uploadFile(MultipartFile file);

    public byte[] downloadFile(String strFileName);

    public String deleteFile(String strFileName);
}
