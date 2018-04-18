package com.example.demo.Exceptions;

public class CustomException extends RuntimeException {

    public CustomException(String txt) {
        super(txt);
    }
}