package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.service.BeerService;
import com.example.springrest6mvb.service.BeerServiceImpl;
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

import static com.example.springrest6mvb.controller.BeerController.BEER_PATH;
import static com.example.springrest6mvb.controller.BeerController.BEER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(BeerController.class)
class BeerControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@MockBean
	BeerService beerService;

	@Autowired
	ObjectMapper objectMapper;

	BeerServiceImpl beerServiceImp;

	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;

	@Captor
	ArgumentCaptor<Beer> beerArgumentCaptor;

	@BeforeEach
	void setUp() {
		beerServiceImp = new BeerServiceImpl();
	}


	@Test
	void createNewBeer() throws Exception {
		Beer beer = beerServiceImp.listBeers()
		                          .get(0);

		beer.setVersion(null);
		beer.setId(null);
		given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImp.listBeers()
		                                                                         .get(1));

		mockMvc.perform(post(BEER_PATH).accept(MediaType.APPLICATION_JSON)
		                               .contentType(MediaType.APPLICATION_JSON)
		                               .content(objectMapper.writeValueAsBytes(beer)))
		       .andExpect(status().isCreated())
		       .andExpect(header().exists("Location"));
	}

	@Test
	void getBeerList() throws Exception {
		given(beerService.listBeers()).willReturn(beerServiceImp.listBeers());
		mockMvc.perform(get(BEER_PATH).accept(MediaType.APPLICATION_JSON))
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

		//mockMvc.perform(get(BEER_PATH+"/" + UUID.randomUUID())
		mockMvc.perform(get(BEER_PATH_ID, testBeer.getId()).
				                accept(MediaType.APPLICATION_JSON))
		       .andExpect(status().isOk())
		       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
		       .andExpect(jsonPath("$.id", is(testBeer.getId()
		                                              .toString())))
		       .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
	}

	@Test
	void testUpdateBeer() throws Exception {
		Beer beer = beerServiceImp.listBeers()
		                          .get(0);

		mockMvc.perform(put(BEER_PATH_ID, beer.getId())
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsBytes(beer)))
		       .andExpect(status().isNoContent());

		verify(beerService).updateByBeerId(any(UUID.class), any(Beer.class));

	}

	@Test
	void testDeleteBeer() throws Exception {
		Beer beer = beerServiceImp.listBeers()
		                          .get(0);

		mockMvc.perform(delete(BEER_PATH_ID, beer.getId()))
		       .andExpect(status().isNoContent());

		ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);
		verify(beerService).deleteBeer(argumentCaptor.capture());
		assertThat(beer.getId()).isEqualTo(argumentCaptor.getValue());
	}

	@Test
	void testPatchBeer() throws Exception {
		Beer beer = beerServiceImp.listBeers()
		                          .get(0);

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name");

		mockMvc.perform(patch(BEER_PATH_ID, beer.getId())
				                .contentType(MediaType.APPLICATION_JSON)
				                .accept(MediaType.APPLICATION_JSON)
				                .content(objectMapper.writeValueAsString(beerMap)))
		       .andExpect(status().isNoContent());

		verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());

		assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue()
		                                                                .getBeerName());
	}
}