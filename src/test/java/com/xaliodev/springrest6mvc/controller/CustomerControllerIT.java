package com.xaliodev.springrest6mvc.controller;

import com.xaliodev.springrest6mvc.entities.Customer;
import com.xaliodev.springrest6mvc.model.CustomerDTO;
import com.xaliodev.springrest6mvc.reposotories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class CustomerControllerIT {

	@Autowired
	CustomerController customerController;

	@Autowired
	CustomerRepository customerRepository;

	@Test
	void testGetAllCustomers() {
		List<CustomerDTO> customerDto = customerController.listAllCustomers();
		assertThat(customerDto.size()).isEqualTo(3);
	}

	@Test
	void testGetCustomerById() {
		Customer customer = customerRepository.findAll()
		                                      .get(0);
		assertThat(customerController.getCustomerById(customer.getId())).isNotNull();
	}

	@Test
	@Transactional
	@Rollback
	void testCustomerListIsEmpty() {
		customerRepository.deleteAll();
		assertThat(customerRepository.findAll()
		                             .size()).isEqualTo(0);
	}

	@Test
	void testCustomerNotFound() {
		assertThrows(NotFoundException.class, () -> assertThat(customerController.getCustomerById(UUID.randomUUID())));
	}
}