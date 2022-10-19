package com.careerclub.careerclub.Config;

public enum BucketName {
    CV("aws-tutorial-ogoma");
    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
