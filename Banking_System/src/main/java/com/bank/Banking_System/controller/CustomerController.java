package com.bank.Banking_System.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.Banking_System.dto.CustomerRequest;
import com.bank.Banking_System.entity.Customer;
import com.bank.Banking_System.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private CustomerService customerService;
	

	@PostMapping
	public Customer addCustomer(
	        @Valid @RequestBody CustomerRequest request) {

	    Customer customer = new Customer();

	    customer.setName(request.getName());
	    customer.setEmail(request.getEmail());
	    customer.setPhone(request.getPhone());
	    customer.setAddress(request.getAddress());

	    return customerService.addCustomer(customer);
	}

	    @GetMapping
	    public List<Customer> getAllCustomers() {
	        return customerService.getAllCustomers();
	    }
	    
	    @PutMapping("/{customerId}")
	    public Customer updateCustomer(
	            @PathVariable Long customerId,
	            @Valid @RequestBody CustomerRequest request) {

	        return customerService.updateCustomer(customerId, request);
	    }
	    @DeleteMapping("/{customerId}")
	    public String deleteCustomer(
	            @PathVariable Long customerId) {

	        customerService.deleteCustomer(customerId);

	        return "Customer deleted successfully";
	    }

}
