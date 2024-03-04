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

		this.customerMap = new HashMap<>();
		customerMap.put(customer1.getId(), customer1);
		customerMap.put(customer2.getId(), customer2);
		customerMap.put(customer3.getId(), customer3);
	}

	@Override
	public void patchCustomerById(UUID customerId, Customer customer) {
		Customer existing = customerMap.get(customerId);

		if (StringUtils.hasText(customer.getCustomerName())) {
			existing.setCustomerName(customer.getCustomerName());
		}
	}

	@Override
	public void deleteCustomerById(UUID customerId) {
		customerMap.remove(customerId);
	}

	@Override
	public void updateCustomerById(UUID customerId, Customer customer) {
		Customer existing = customerMap.get(customerId);
		existing.setCustomerName(customer.getCustomerName());
	}

	@Override
	public Customer saveNewCustomer(Customer customer) {

		Customer savedCustomer = Customer.builder()
		                                 .id(UUID.randomUUID())
		                                 .version(1)
		                                 .updatedDate(LocalDateTime.now())
		                                 .createdDate(LocalDateTime.now())
		                                 .customerName(customer.getCustomerName())
		                                 .build();

		customerMap.put(savedCustomer.getId(), savedCustomer);

		return savedCustomer;
	}

	@Override
	public Optional<Customer> getCustomerById(UUID uuid) {
		return Optional.of(customerMap.get(uuid));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return new ArrayList<>(customerMap.values());
	}
}