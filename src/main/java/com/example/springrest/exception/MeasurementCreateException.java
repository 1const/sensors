package com.example.springrest.exception;

public class MeasurementCreateException extends RuntimeException{
    public MeasurementCreateException(String message) {
        super(message);
    }
}
