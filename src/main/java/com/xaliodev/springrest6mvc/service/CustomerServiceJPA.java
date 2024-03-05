package com.xaliodev.springrest6mvc.service;

import com.xaliodev.springrest6mvc.mappers.CustomerMapper;
import com.xaliodev.springrest6mvc.model.CustomerDTO;
import com.xaliodev.springrest6mvc.reposotories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
	private final CustomerRepository customerRepository;
	private final CustomerMapper customerMapper;

	@Override
	public Optional<CustomerDTO> getCustomerById(UUID uuid) {
		return Optional.empty();
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return null;
	}

	@Override
	public CustomerDTO saveNewCustomer(CustomerDTO customer) {
		return null;
	}

	@Override
	public void updateCustomerById(UUID customerId, CustomerDTO customer) {

	}

	@Override
	public void deleteCustomerById(UUID customerId) {

	}

	@Override
	public void patchCustomerById(UUID customerId, CustomerDTO customer) {

	}
}
