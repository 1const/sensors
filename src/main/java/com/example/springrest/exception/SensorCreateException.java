package com.example.springrest.exception;

public class SensorCreateException extends RuntimeException{
    public SensorCreateException(String message) {
        super(message);
    }
}
