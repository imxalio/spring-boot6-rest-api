package com.example.springrest6mvb.controller;


import com.example.springrest6mvb.model.Customer;
import com.example.springrest6mvb.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

	private final CustomerService customerService;
	public static final String CUSTOMER_PATH = "/api/v1/customer";
	public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

	@GetMapping(value = CUSTOMER_PATH)
	public List<Customer> getAllCustomers() {
		return customerService.listCustomer();
	}

	@GetMapping(value = CUSTOMER_PATH_ID)
	public Customer getCustomerById(@PathVariable(value = "customerId") UUID customerId) {
		return customerService.getCustomerById(customerId);

	}

	@PostMapping(value = CUSTOMER_PATH)
	public ResponseEntity postCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/customer/" + savedCustomer.getId()
		                                                               .toString());

		return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping(value = CUSTOMER_PATH_ID)
	public ResponseEntity updateCustomer(@PathVariable(value = "customerId") UUID customerId, @RequestBody Customer customer) {
		customerService.updateCustomer(customerId, customer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = CUSTOMER_PATH_ID)
	public ResponseEntity deleteCustomer(@PathVariable(value = "customerId") UUID id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(value = CUSTOMER_PATH_ID)
	public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId, @RequestBody Customer customer) {
		customerService.patchCustomerById(customerId, customer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

}
