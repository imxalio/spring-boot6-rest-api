package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Customer;
import com.example.springrest6mvb.service.CustomerService;
import com.example.springrest6mvb.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.springrest6mvb.controller.CustomerController.CUSTOMER_PATH;
import static com.example.springrest6mvb.controller.CustomerController.CUSTOMER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	CustomerService customerService;

	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;

	@Captor
	ArgumentCaptor<Customer> customerArgumentCaptor;

	CustomerServiceImpl customerServiceImp;

	@BeforeEach
	void setUp() {
		customerServiceImp = new CustomerServiceImpl();
	}

	@Test
	void createNewCustomer() throws Exception {
		Customer customer = customerServiceImp.listCustomer()
		                                      .get(0);
		customer.setId(null);
		customer.setVersion(null);

		given(customerService.saveCustomer(any(Customer.class))).willReturn(customerServiceImp.listCustomer()
		                                                                                      .get(1));
		mockMvc.perform(post(CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON)
		                                   .contentType(MediaType.APPLICATION_JSON)
		                                   .content(objectMapper.writeValueAsBytes(customer)))
		       .andExpect(status().isCreated())
		       .andExpect(header().exists("Location"));

	}

	@Test
	void getAllCustomers() throws Exception {
		given(customerService.listCustomer()).willReturn(customerServiceImp.listCustomer());
		mockMvc.perform(get(CUSTOMER_PATH).accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void getCustomerById() throws Exception {
		Customer testCustomer = customerServiceImp.listCustomer()
		                                          .get(0);

		given(customerService.getCustomerById(testCustomer.getId())).willReturn(customerServiceImp.getCustomerById(testCustomer.getId()));
		mockMvc.perform(get(CUSTOMER_PATH_ID, testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.id", is(testCustomer.getId()
		                                                  .toString())))
		       .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
	}

	@Test
	void testUpdateCustomer() throws Exception {
		Customer customer = customerServiceImp.listCustomer()
		                                      .get(0);

		mockMvc.perform(put(CUSTOMER_PATH_ID, customer.getId())
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsBytes(customer)))
		       .andExpect(status().isNoContent());

		verify(customerService).updateCustomer(any(UUID.class), any(Customer.class));
	}

	@Test
	void testDeleteCustomer() throws Exception {
		Customer customer = customerServiceImp.listCustomer()
		                                      .get(0);

		mockMvc.perform(delete(CUSTOMER_PATH_ID, customer.getId()))
		       .andExpect(status().isNoContent());

		ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(customerService).deleteCustomer(argumentCaptor.capture());
		assertThat(customer.getId()).isEqualTo(argumentCaptor.getValue());
	}

	@Test
	void testPatchCustomer() throws Exception {
		Customer customer = customerServiceImp.listCustomer()
		                                      .get(0);

		Map<String, Object> customerMap = new HashMap<>();
		customerMap.put("customerName", "New Name");

		mockMvc.perform(patch(CUSTOMER_PATH_ID, customer.getId())
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(customerMap)))
		       .andExpect(status().isNoContent());

		verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),
		                                          customerArgumentCaptor.capture());

		assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
		assertThat(customerArgumentCaptor.getValue()
		                                 .getCustomerName())
				.isEqualTo(customerMap.get("customerName"));
	}
}