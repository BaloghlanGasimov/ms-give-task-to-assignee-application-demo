package com.example.msgivetaskstoassignee.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final MinioClient minioClient;
//    @Value("${minio.bucket-name}")
//    private String bucketName;

    @Transactional
    public String uploadAndGetPathImage(String bucketName, String objectName, InputStream fileStream) {

        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
            if(!found){
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(bucketName)
                                .build());
            }
            minioClient.putObject(
                    PutObjectArgs.
                            builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(fileStream,fileStream.available(), -1)
                            .build());
            String filePath = getFilePath(bucketName,objectName);

            return filePath;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected Error: "+e);
        }
    }

    public String getFilePath(String bucketName, String objectName){

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String bucketName, String objectName){
        try {
            minioClient.removeObject(
                    RemoveObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }catch (Exception e){
            System.out.println("Unexpected Error: "+e.getMessage());;
        }
    }

}
