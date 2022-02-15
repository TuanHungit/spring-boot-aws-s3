package com.hungnt.springbootawss3.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.hungnt.springbootawss3.service.IStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class StrorageService implements IStorageService {

    @Value("${application.bucket.name}")
    private String strBucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Override
    public String uploadFile(MultipartFile file) {
        String strFileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        try {
            File fileObj = convertMultipartFileToFile(file);
            s3Client.putObject(new PutObjectRequest(strBucketName, strFileName, fileObj));
            return "File uploaded: " + strFileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Can't upload file: " + strFileName;
        }
    }

    @Override
    public byte[] downloadFile(String strFileName) {
        S3Object s3Object = s3Client.getObject(strBucketName, strFileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public String deleteFile(String strFileName) {
        try {
            s3Client.deleteObject(strBucketName, strFileName);
            return "File deleted: " + strFileName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Can't delete file: " + strFileName;
        }
    }

    private File convertMultipartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Error converting multipart file to file", ex);
        }
        return convertedFile;
    }
}
