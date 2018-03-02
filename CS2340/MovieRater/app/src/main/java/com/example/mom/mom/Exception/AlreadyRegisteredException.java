package com.example.mom.mom.Exception;

/**
 * Created by jesse on 2/29/16.
 */
public class AlreadyRegisteredException extends RuntimeException {
    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
