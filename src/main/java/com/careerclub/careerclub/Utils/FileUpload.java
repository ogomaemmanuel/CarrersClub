package com.careerclub.careerclub.Utils;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.careerclub.careerclub.Config.BucketName;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public class FileUpload {
    private final AmazonS3 amazonS3;

    public FileUpload(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public ArrayList<String> upload(MultipartFile file){

        if(file.isEmpty()){
            throw new IllegalStateException("Cannot upload empty file");
        }
        Map<String,String> metadata = new HashMap<>();
        metadata.put("Content-Type",file.getContentType());
        metadata.put("Content-Length",String.valueOf(file.getSize()));

        String path = String.format("%s/%s", BucketName.CV.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s",file.getOriginalFilename());

        try{
            ObjectMetadata objectMetadata = new ObjectMetadata();
            Optional.of(metadata).ifPresent(map -> {
                if(!map.isEmpty()){
                    map.forEach(objectMetadata::addUserMetadata);
                }
            });
            try {
                var result = new ArrayList<String>();
                amazonS3.putObject(path,fileName, file.getInputStream(), objectMetadata);
                result.add(path);
                result.add(fileName);
                return result;
            }catch (AmazonServiceException e){
                throw new IllegalStateException("Failed to upload the file", e);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to upload file",e);
        }

    }
}
