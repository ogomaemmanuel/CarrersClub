package com.careerclub.careerclub.Advice;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
