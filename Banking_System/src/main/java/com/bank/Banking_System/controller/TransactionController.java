package com.bank.Banking_System.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.Banking_System.entity.Transaction;
import com.bank.Banking_System.service.TransactionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/transactions")
@AllArgsConstructor
public class TransactionController {
	 private TransactionService transactionService;
	 
	 @GetMapping("/account/{accountId}")
	 public List<Transaction> getTransactions(
	         @PathVariable Long accountId) {

	     return transactionService.getTransactionsByAccount(accountId);
	 }

}
