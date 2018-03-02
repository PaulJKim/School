package com.example.mom.mom.Exception;

/**
 * Created by Jesse on 3/17/2016.
 */
public class BannedAccountException extends RuntimeException {
    public BannedAccountException(String szMessage) {
        super(szMessage);
    }
}
