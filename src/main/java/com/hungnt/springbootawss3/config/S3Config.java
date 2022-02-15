package com.hungnt.springbootawss3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Builder;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.s3.access.key}")
    private String strAccessKey;

    @Value("${cloud.aws.s3.secret.key}")
    private String strSecretKey;

    @Value("${cloud.aws.s3.region.static}")
    private String strRegion;

    @Bean
    public AmazonS3 initAmazonS3Client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(strAccessKey, strSecretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .withRegion(strRegion)
                .build();
    }

}
