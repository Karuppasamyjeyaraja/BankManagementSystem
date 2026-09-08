package com.bank.Banking_System.exception;

public class DuplicateAccountException extends RuntimeException {

    public DuplicateAccountException(String message) {
        super(message);
    }
}
