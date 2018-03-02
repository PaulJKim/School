package com.example.mom.mom.Exception;

/**
 * Created by Jesse on 3/17/2016.
 */
public class LockedAccountException extends RuntimeException {
    public LockedAccountException(String szMessage) {
        super(szMessage);
    }
}
