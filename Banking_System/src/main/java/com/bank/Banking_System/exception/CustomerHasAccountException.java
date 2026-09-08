package com.bank.Banking_System.exception;

public class CustomerHasAccountException extends RuntimeException {

    public CustomerHasAccountException(String message) {
        super(message);
    }
}