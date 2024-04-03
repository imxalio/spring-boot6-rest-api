package com.xaliodev.springrest6mvc.reposotories;

import com.xaliodev.springrest6mvc.entities.Beer;
import com.xaliodev.springrest6mvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class BeerRepositoryTest {

	@Autowired
	BeerRepository beerRepository;

	@Test
	void testSaveBeer() {
		Beer savedBeer = beerRepository.save(Beer.builder()
		                                         .beerName("Mo")
		                                         .beerStyle(BeerStyle.STOUT)
		                                         .upc("28AMSDKA2")
		                                         .price(new BigDecimal("11.23"))
		                                         .build());
		beerRepository.flush();
		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();
	}

	@Test
	void testSaveBeerNameTooLong() {
		assertThrows(ConstraintViolationException.class, () -> {
			Beer savedBeer = beerRepository.save(Beer.builder()
			                                         .beerName("Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani Mohamed El ALouani ")
			                                         .beerStyle(BeerStyle.STOUT)
			                                         .upc("28AMSDKA2")
			                                         .price(new BigDecimal("11.23"))
			                                         .build());
			beerRepository.flush();
		});
	}

}