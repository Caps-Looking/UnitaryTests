package com.example.demo.exception;

public class PermissionException extends RuntimeException {

    public PermissionException(String msg) {
        super(msg);
    }
}