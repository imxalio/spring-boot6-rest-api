package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.service.BeerService;
import com.example.springrest6mvb.service.BeerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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


@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	BeerService beerService;

	@Autowired
	ObjectMapper objectMapper;

	BeerServiceImpl beerServiceImp = new BeerServiceImpl();

	@Test
	void createNewBeer() throws JsonProcessingException {
		Beer beer = beerServiceImp.listBeers()
		                          .get(0);
		System.out.println(objectMapper.writeValueAsString(beer));
	}

	@Test
	void getBeerList() throws Exception {
		given(beerService.listBeers()).willReturn(beerServiceImp.listBeers());
		mockMvc.perform(get("/api/v1/beer").accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.length()", is(3)));

	}

	@Test
	void getBeerById() throws Exception {
		Beer testBeer = beerServiceImp.listBeers()
		                              .get(0);

		//given(beerService.getBeerById(any(UUID.class))).willReturn(testBeer);
		given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

		//mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID())
		mockMvc.perform(get("/api/v1/beer/" + testBeer.getId()).
				                accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.id", is(testBeer.getId()
		                                              .toString())))
		       .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}
}