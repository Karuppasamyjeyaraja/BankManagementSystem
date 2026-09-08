package com.bank.Banking_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AccountRequest {

    @NotBlank(message = "Account number is required")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Account number must contain exactly 10 digits"
    )
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    private String accountType;

    public AccountRequest() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}