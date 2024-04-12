package com.xaliodev.springrest6mvc.bootstrap;

import com.xaliodev.springrest6mvc.reposotories.BeerRepository;
import com.xaliodev.springrest6mvc.reposotories.CustomerRepository;
import com.xaliodev.springrest6mvc.service.BeerCsvService;
import com.xaliodev.springrest6mvc.service.BeerCsvServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCsvServiceImp.class)
class BootstrapDataTest {

	@Autowired
	BeerRepository beerRepository;
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BeerCsvService beerCsvService;

	BootstrapData bootstrapData;

	@BeforeEach
	void setUp() {
		bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvService);
	}

	@Test
	void testRun() throws Exception {
		bootstrapData.run(null);

		assertThat(beerRepository.count()).isEqualTo(2410);
		assertThat(customerRepository.count()).isEqualTo(3);

	}
}