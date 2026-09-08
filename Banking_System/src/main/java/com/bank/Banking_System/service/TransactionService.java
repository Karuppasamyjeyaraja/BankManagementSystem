package com.bank.Banking_System.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.Banking_System.entity.Transaction;
import com.bank.Banking_System.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {

	private TransactionRepository transactionRepository;
	
	 public Transaction saveTransaction(Transaction transaction) {
	        return transactionRepository.save(transaction);
	    }

	 public List<Transaction> getTransactionsByAccount(Long accountId) {

		    return transactionRepository
		            .findByFromAccountIdOrToAccountId(
		                    accountId,
		                    accountId);
		}
}
