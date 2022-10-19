package com.careerclub.careerclub.Config;

public enum S3Bucket {
    TODO_IMAGE("aws-tutorial-ogoma");
    private final String bucketName;

    S3Bucket(String bucketName) {
        this.bucketName = bucketName;
    }
}
