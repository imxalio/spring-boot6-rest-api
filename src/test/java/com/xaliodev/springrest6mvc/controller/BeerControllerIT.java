package com.xaliodev.springrest6mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xaliodev.springrest6mvc.entities.Beer;
import com.xaliodev.springrest6mvc.mappers.BeerMapper;
import com.xaliodev.springrest6mvc.model.BeerDTO;
import com.xaliodev.springrest6mvc.reposotories.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
class BeerControllerIT {

	@Autowired
	BeerController beerController;

	@Autowired
	BeerRepository beerRepository;

	@Autowired
	BeerMapper beerMapper;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	WebApplicationContext webApplicationContext;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
		                         .build();
	}

	@Test
	void listBeerTest() {
		List<BeerDTO> listBeers = beerController.listBeers();
		assertThat(listBeers.size()).isEqualTo(2410);
	}

	@Test
	@Transactional
	@Rollback
	void testEmptyList() {
		beerRepository.deleteAll();
		List<BeerDTO> listBeers = beerController.listBeers();
		assertThat(listBeers.size()).isEqualTo(0);
	}

	@Test
	void testGetById() {

		Beer beer = beerRepository.findAll()
		                          .get(0);
		BeerDTO dto = beerController.getBeerById(beer.getId());

		assertThat(dto).isNotNull();
	}

	@Test
	void testBeerIsNotFound() {
		assertThrows(NotFoundException.class, () -> assertThat(beerController.getBeerById(UUID.randomUUID())));
	}

	@Test
	@Transactional
	@Rollback
	void testPostBeer() {
		BeerDTO beerDTO = BeerDTO.builder()
		                         .beerName("Mohamed")
		                         .build();
		ResponseEntity responseEntity = beerController.handlePost(beerDTO);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(responseEntity.getHeaders()
		                         .getLocation()).isNotNull();

		String[] locationUUID = responseEntity.getHeaders()
		                                      .getLocation()
		                                      .toString()
		                                      .split("/");
		UUID savedUUid = UUID.fromString(locationUUID[4]);

		Beer beer = beerRepository.findById(savedUUid)
		                          .get();
		assertThat(beer).isNotNull();
	}

	@Transactional
	@Rollback
	@Test
	void updateExistingBeer() {
		Beer beer = beerRepository.findAll()
		                          .get(0);
		BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
		beerDTO.setId(null);
		beerDTO.setVersion(null);
		final String beerName = "UPDATED";
		beerDTO.setBeerName(beerName);

		ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		Beer updatedBeer = beerRepository.findById(beer.getId())
		                                 .get();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
	}

	@Test
	void testUpdateNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerController.updateById(UUID.randomUUID(), BeerDTO.builder()
			                                                    .build());
		});
	}

	@Transactional
	@Rollback
	@Test
	void testDeleteByIdFound() {
		Beer beer = beerRepository.findAll()
		                          .get(0);
		ResponseEntity responseEntity = beerController.deleteById(beer.getId());
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
		assertThat(beerRepository.findById(beer.getId())
		                         .isEmpty());
	}

	@Test
	void testDeleteByIdNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerController.deleteById(UUID.randomUUID());
		});
	}

	@Test
	void testPatchBeerWrongName() throws Exception {
		Beer beer = beerRepository.findAll()
		                          .get(0);

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name 012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");

		MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
				                                   .contentType(MediaType.APPLICATION_JSON)
				                                   .accept(MediaType.APPLICATION_JSON)
				                                   .content(objectMapper.writeValueAsString(beerMap)))
		                          .andExpect(status().isBadRequest())
		                          .andExpect(jsonPath("$.length()", is(1)))
		                          .andReturn();

		System.out.println(result.getResponse()
		                         .getContentAsString());
	}
}