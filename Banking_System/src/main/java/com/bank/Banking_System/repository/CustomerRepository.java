package com.bank.Banking_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.Banking_System.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

}
