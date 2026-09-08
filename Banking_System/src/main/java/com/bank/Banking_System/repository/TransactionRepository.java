package com.bank.Banking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.Banking_System.entity.Transaction;

public interface TransactionRepository  extends  JpaRepository<Transaction,Long>{

    List<Transaction> findByFromAccountIdOrToAccountId(
            Long fromAccountId,
            Long toAccountId);
	
}
