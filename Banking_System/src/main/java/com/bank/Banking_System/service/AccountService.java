package com.bank.Banking_System.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.Banking_System.dto.AccountUpdateRequest;
import com.bank.Banking_System.entity.Account;
import com.bank.Banking_System.entity.Customer;
import com.bank.Banking_System.entity.Transaction;

import com.bank.Banking_System.exception.AccountBlockedException;
import com.bank.Banking_System.exception.AccountNotFoundException;
import com.bank.Banking_System.exception.CustomerNotFoundException;
import com.bank.Banking_System.exception.DuplicateAccountException;
import com.bank.Banking_System.exception.InsufficientBalanceException;
import com.bank.Banking_System.exception.InvalidAmountException;
import com.bank.Banking_System.exception.SameAccountTransferException;

import com.bank.Banking_System.repository.AccountRepository;
import com.bank.Banking_System.repository.CustomerRepository;
import com.bank.Banking_System.repository.TransactionRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    public AccountService(
            AccountRepository accountRepository,
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository) {

        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }


    // ================= CREATE ACCOUNT =================

    public Account createAccount(Long customerId, Account account) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        if (accountRepository.existsByAccountNumber(
                account.getAccountNumber())) {

            throw new DuplicateAccountException(
                    "Account number already exists");
        }

        account.setBalance(0);

        account.setStatus("ACTIVE");

        account.setCustomer(customer);

        return accountRepository.save(account);
    }


    // ================= GET ALL ACCOUNTS =================

    public List<Account> getAllAccounts() {

        return accountRepository.findAll();
    }


    // ================= GET ACCOUNT BY ID =================

    public Account getAccountById(Long accountId) {

        return accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));
    }


    // ================= GET ACCOUNT BY NUMBER =================

    public Account getAccountByAccountNumber(
            String accountNumber) {

        return accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));
    }


    // ================= BALANCE =================

    public double getBalance(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        return account.getBalance();
    }


    // ================= BLOCK ACCOUNT =================

    public Account blockAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        if ("CLOSED".equals(account.getStatus())) {

            throw new AccountBlockedException(
                    "Closed account cannot be blocked");
        }

        account.setStatus("BLOCKED");

        return accountRepository.save(account);
    }


    // ================= ACTIVATE ACCOUNT =================

    public Account activateAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        if ("CLOSED".equals(account.getStatus())) {

            throw new AccountBlockedException(
                    "Closed account cannot be activated");
        }

        account.setStatus("ACTIVE");

        return accountRepository.save(account);
    }


    // ================= CLOSE ACCOUNT =================

    public Account closeAccount(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        if (account.getBalance() > 0) {

            throw new AccountBlockedException(
                    "Cannot close account with remaining balance");
        }

        account.setStatus("CLOSED");

        return accountRepository.save(account);
    }


    // ================= DEPOSIT =================

    @Transactional
    public Account depositMoney(
            Long accountId,
            double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        checkAccountActive(account);

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Deposit amount must be greater than zero");
        }

        account.setBalance(
                account.getBalance() + amount);

        Account savedAccount =
                accountRepository.save(account);


        Transaction transaction = new Transaction();

        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setTransactionDate(
                LocalDateTime.now());

        transaction.setFromAccountId(accountId);

        transactionRepository.save(transaction);

        return savedAccount;
    }


    // ================= WITHDRAW =================

    @Transactional
    public Account withdrawMoney(
            Long accountId,
            double amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        checkAccountActive(account);

        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Withdrawal amount must be greater than zero");
        }

        if (amount > account.getBalance()) {

            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }

        account.setBalance(
                account.getBalance() - amount);

        Account savedAccount =
                accountRepository.save(account);


        Transaction transaction = new Transaction();

        transaction.setTransactionType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setTransactionDate(
                LocalDateTime.now());

        transaction.setToAccountId(accountId);

        transactionRepository.save(transaction);

        return savedAccount;
    }


    // ================= TRANSFER =================

    @Transactional
    public void transferMoney(
            Long fromAccountId,
            Long toAccountId,
            double amount) {

        Account fromAccount =
                accountRepository.findById(fromAccountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Sender account not found"));


        Account toAccount =
                accountRepository.findById(toAccountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Receiver account not found"));


        checkAccountActive(fromAccount);

        checkAccountActive(toAccount);


        if (amount <= 0) {

            throw new InvalidAmountException(
                    "Transfer amount must be greater than zero");
        }


        if (fromAccountId.equals(toAccountId)) {

            throw new SameAccountTransferException(
                    "Cannot transfer to the same account");
        }


        if (fromAccount.getBalance() < amount) {

            throw new InsufficientBalanceException(
                    "Insufficient balance");
        }


        fromAccount.setBalance(
                fromAccount.getBalance() - amount);

        toAccount.setBalance(
                toAccount.getBalance() + amount);


        accountRepository.save(fromAccount);

        accountRepository.save(toAccount);


        Transaction transaction =
                new Transaction();

        transaction.setTransactionType("TRANSFER");
        transaction.setAmount(amount);
        transaction.setTransactionDate(
                LocalDateTime.now());

        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);

        transactionRepository.save(transaction);
    }


    // ================= CHECK ACTIVE =================

    private void checkAccountActive(Account account) {

        if (!"ACTIVE".equals(account.getStatus())) {

            throw new AccountBlockedException(
                    "Account is not active");
        }
    }
    public List<Account> getAccountsByCustomer(Long customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(
                    "Customer not found");
        }

        return accountRepository.findByCustomerId(customerId);
    }
    public Account updateAccount(
            Long accountId,
            AccountUpdateRequest request) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Account not found"));

        if ("CLOSED".equals(account.getStatus())) {
            throw new AccountBlockedException(
                    "Cannot update a closed account");
        }

        account.setAccountType(request.getAccountType());

        return accountRepository.save(account);
    }
}