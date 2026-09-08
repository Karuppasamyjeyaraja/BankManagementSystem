package com.bank.Banking_System.exception;

public class CustomerNotFoundException extends RuntimeException {
	public CustomerNotFoundException(String message) {
        super(message);
    }
}
