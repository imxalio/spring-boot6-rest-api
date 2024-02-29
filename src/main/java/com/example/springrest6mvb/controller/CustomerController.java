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
@RequestMapping("/api/v1/customer")
public class CustomerController {
	private final CustomerService customerService;

	@RequestMapping(method = RequestMethod.GET)
	public List<Customer> getAllCustomers() {
		return customerService.listCustomer();
	}

	@RequestMapping(value = "{customerId}", method = RequestMethod.GET)
	public Customer getCustomerById(@PathVariable(value = "customerId") UUID customerId) {
		return customerService.getCustomerById(customerId);

	}

	@PostMapping
	public ResponseEntity postCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.saveCustomer(customer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/customer/" + savedCustomer.getId()
		                                                               .toString());
		return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("{customerId}")
	public ResponseEntity updateCustomer(@PathVariable(value = "customerId") UUID customerId, @RequestBody Customer customer) {
		customerService.updateCustomer(customerId, customer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{customerId}")
	public ResponseEntity deleteCustomer(@PathVariable(value = "customerId") UUID id) {
		customerService.deleteCustomer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
