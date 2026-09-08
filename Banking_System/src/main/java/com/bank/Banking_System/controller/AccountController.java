package com.bank.Banking_System.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.Banking_System.dto.AccountRequest;
import com.bank.Banking_System.dto.AccountUpdateRequest;
import com.bank.Banking_System.dto.TransferRequest;
import com.bank.Banking_System.entity.Account;
import com.bank.Banking_System.service.AccountService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/accounts")
public class AccountController {
	 private AccountService accountService;
	 public AccountController(AccountService accountService) {
	        this.accountService = accountService;
	    }

	 @PostMapping("/customer/{customerId}")
	 public Account createAccount(
	         @PathVariable Long customerId,
	         @Valid @RequestBody AccountRequest request) {

	     Account account = new Account();

	     account.setAccountNumber(request.getAccountNumber());
	     account.setAccountType(request.getAccountType());
	     account.setBalance(0);
	     account.setStatus("ACTIVE");

	     return accountService.createAccount(customerId, account);
	 }
	    

	    @GetMapping
	    public List<Account> getAllAccounts() {
	        return accountService.getAllAccounts();
	    }
	    
	    @PostMapping("/{accountId}/deposit")
	    public Account depositMoney(
	            @PathVariable Long accountId,
	            @RequestParam double amount) {

	        return accountService.depositMoney(accountId, amount);
	    }
	    @PostMapping("/{accountId}/withdraw")
	    public Account withdrawMoney(
	            @PathVariable Long accountId,
	            @RequestParam double amount) {

	        return accountService.withdrawMoney(accountId, amount);
	    }
	    
	    @PostMapping("/transfer")
	    public String transferMoney(
	            @RequestBody TransferRequest request) {

	        accountService.transferMoney(
	                request.getFromAccountId(),
	                request.getToAccountId(),
	                request.getAmount());

	        return "Money transferred successfully";
	    }
	    @GetMapping("/{accountId}/balance")
	    public double getBalance(
	            @PathVariable Long accountId) {

	        return accountService.getBalance(accountId);
	    }
	    @GetMapping("/{accountId}")
	    public Account getAccountById(
	            @PathVariable Long accountId) {

	        return accountService.getAccountById(accountId);
	    }
	    @GetMapping("/number/{accountNumber}")
	    public Account getAccountByAccountNumber(
	            @PathVariable String accountNumber) {

	        return accountService.getAccountByAccountNumber(accountNumber);
	    }
	 // ================= BLOCK =================

	    @PutMapping("/{accountId}/block")
	    public Account blockAccount(
	            @PathVariable Long accountId) {

	        return accountService.blockAccount(accountId);
	    }


	    // ================= ACTIVATE =================

	    @PutMapping("/{accountId}/activate")
	    public Account activateAccount(
	            @PathVariable Long accountId) {

	        return accountService.activateAccount(accountId);
	    }


	    // ================= CLOSE =================

	    @PutMapping("/{accountId}/close")
	    public Account closeAccount(
	            @PathVariable Long accountId) {

	        return accountService.closeAccount(accountId);
	    }
	    @GetMapping("/customer/{customerId}")
	    public List<Account> getAccountsByCustomer(
	            @PathVariable Long customerId) {

	        return accountService.getAccountsByCustomer(customerId);
	    }
	    @PutMapping("/{accountId}")
	    public Account updateAccount(
	            @PathVariable Long accountId,
	            @Valid @RequestBody AccountUpdateRequest request) {

	        return accountService.updateAccount(
	                accountId,
	                request);
	    }
	    
}
