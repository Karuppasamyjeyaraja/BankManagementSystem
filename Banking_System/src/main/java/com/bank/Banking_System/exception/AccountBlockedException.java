package com.bank.Banking_System.exception;

public class AccountBlockedException extends RuntimeException {

    public AccountBlockedException(String message) {
        super(message);
    }
}