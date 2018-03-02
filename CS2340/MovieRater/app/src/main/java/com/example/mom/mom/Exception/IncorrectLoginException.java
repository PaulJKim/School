package com.example.mom.mom.Exception;

/**
 * Created by Jesse on 3/17/2016.
 */
public class IncorrectLoginException extends RuntimeException {
    public IncorrectLoginException(String szMessage) {
        super(szMessage);
    }
}
