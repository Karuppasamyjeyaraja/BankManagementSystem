package com.bank.Banking_System.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.Banking_System.dto.CustomerRequest;
import com.bank.Banking_System.entity.Customer;
import com.bank.Banking_System.exception.CustomerHasAccountException;
import com.bank.Banking_System.exception.CustomerNotFoundException;
import com.bank.Banking_System.repository.AccountRepository;
import com.bank.Banking_System.repository.CustomerRepository;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public CustomerService(CustomerRepository customerRepository,
            AccountRepository accountRepository) {

    	this.customerRepository = customerRepository;
    	this.accountRepository = accountRepository;
    }

    // Add customer
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Update customer
    public Customer updateCustomer(
            Long customerId,
            CustomerRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        return customerRepository.save(customer);
    }
    public void deleteCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found"));

        if (accountRepository.existsByCustomerId(customerId)) {

            throw new CustomerHasAccountException(
                    "Cannot delete customer because customer has an account");
        }

        customerRepository.delete(customer);
    }
}