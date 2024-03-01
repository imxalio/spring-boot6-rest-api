package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Customer;
import com.example.springrest6mvb.service.CustomerService;
import com.example.springrest6mvb.service.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	CustomerService customerService;

	CustomerServiceImpl customerServiceImp = new CustomerServiceImpl();

	@Test
	void getAllCustomers() throws Exception {
		given(customerService.listCustomer()).willReturn(customerServiceImp.listCustomer());
		mockMvc.perform(get("/api/v1/customer").accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void getCustomerById() throws Exception {
		Customer testCustomer = customerServiceImp.listCustomer()
		                                          .get(0);

		given(customerService.getCustomerById(testCustomer.getId())).willReturn(customerServiceImp.getCustomerById(testCustomer.getId()));
		mockMvc.perform(get("/api/v1/customer/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.id", is(testCustomer.getId()
		                                                  .toString())))
		       .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
	}
}