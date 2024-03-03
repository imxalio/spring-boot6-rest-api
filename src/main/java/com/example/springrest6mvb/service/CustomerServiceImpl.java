package com.example.springrest6mvb.service;

import com.example.springrest6mvb.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

	private Map<UUID, Customer> customerMap;

	public CustomerServiceImpl() {
		this.customerMap = new HashMap<>();

		Customer customer1 = Customer.builder()
		                             .id(UUID.randomUUID())
		                             .customerName("Mohamed El Alouani")
		                             .version(1)
		                             .createdDate(LocalDateTime.now())
		                             .updatedDate(LocalDateTime.now())
		                             .build();

		Customer customer2 = Customer.builder()
		                             .id(UUID.randomUUID())
		                             .customerName("Khalid Rojani")
		                             .version(1)
		                             .createdDate(LocalDateTime.now())
		                             .updatedDate(LocalDateTime.now())
		                             .build();

		Customer customer3 = Customer.builder()
		                             .id(UUID.randomUUID())
		                             .customerName("Lionel Messi")
		                             .version(1)
		                             .createdDate(LocalDateTime.now())
		                             .updatedDate(LocalDateTime.now())
		                             .build();
		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);


	}

	@Override
	public List<Customer> listCustomer() {
		return new ArrayList<>(customerMap.values());
	}

	@Override
	public Customer getCustomerById(UUID customerId) {
		return customerMap.get(customerId);
	}

	@Override
	public Customer saveCustomer(Customer customer) {
		Customer savedCustomer = Customer.builder()
		                                 .id(UUID.randomUUID())
		                                 .version(1)
		                                 .customerName(customer.getCustomerName())
		                                 .createdDate(LocalDateTime.now())
		                                 .updatedDate(LocalDateTime.now())
		                                 .build();
		customerMap.put(savedCustomer.getId(), savedCustomer);

		return savedCustomer;
	}

	@Override
	public void updateCustomer(UUID customerId, Customer customer) {
		Customer existingCustomer = customerMap.get(customerId);
		existingCustomer.setCustomerName(customer.getCustomerName());

		customerMap.put(existingCustomer.getId(), existingCustomer);
	}

	@Override
	public void deleteCustomer(UUID id) {
		customerMap.remove(id);
	}

	@Override
	public void patchCustomerById(UUID customerId, Customer customer) {
		Customer existing = customerMap.get(customerId);

		if (StringUtils.hasText(customer.getCustomerName())) {
			existing.setCustomerName(customer.getCustomerName());
		}
	}
}
