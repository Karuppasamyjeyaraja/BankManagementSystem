package com.bank.Banking_System.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorResponse {
	 
	  public ErrorResponse(String message, int status) {
	        this.message = message;
	        this.status = status;
	        this.timestamp = LocalDateTime.now();
	    }
	 private String message;
	    private int status;
	    private LocalDateTime timestamp;

}
