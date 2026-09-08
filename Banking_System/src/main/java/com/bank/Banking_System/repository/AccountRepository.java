package com.bank.Banking_System.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.Banking_System.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByCustomerId(Long customerId);

    List<Account> findByCustomerId(Long customerId);
}