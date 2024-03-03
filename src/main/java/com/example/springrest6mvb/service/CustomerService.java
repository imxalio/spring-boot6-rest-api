package com.example.springrest6mvb.service;

import com.example.springrest6mvb.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
	List<Customer> listCustomer();

	Customer getCustomerById(UUID customerId);

	Customer saveCustomer(Customer customer);

	void updateCustomer(UUID customerId, Customer customer);

	void deleteCustomer(UUID id);

	void patchCustomerById(UUID customerId, Customer customer);
}
