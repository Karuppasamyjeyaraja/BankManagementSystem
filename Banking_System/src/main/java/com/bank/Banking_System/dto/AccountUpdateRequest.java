package com.bank.Banking_System.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountUpdateRequest {

    @NotBlank(message = "Account type is required")
    private String accountType;

    public AccountUpdateRequest() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}