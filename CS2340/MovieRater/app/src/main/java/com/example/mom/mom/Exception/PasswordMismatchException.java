package com.example.mom.mom.Exception;

/**
 * Created by jesse on 2/29/16.
 */
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String szMessage) {
        super(szMessage);
    }
}
