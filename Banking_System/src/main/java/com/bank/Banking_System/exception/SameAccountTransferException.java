package com.bank.Banking_System.exception;

public class SameAccountTransferException extends RuntimeException  {
	
	 public SameAccountTransferException(String message) {
	        super(message);
	    }

}
