package com.xaliodev.springrest6mvc.service;

import com.xaliodev.springrest6mvc.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCsvServiceImpTest {

	BeerCsvService beerCsvService = new BeerCsvServiceImp();

	@Test
	void convertCsv() throws FileNotFoundException {
		File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
		List<BeerCSVRecord> recs = beerCsvService.convertCsv(file);
		System.out.println(recs.size());
		assertThat(recs.size()).isGreaterThan(0);
	}
}