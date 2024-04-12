package com.xaliodev.springrest6mvc.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.xaliodev.springrest6mvc.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCsvServiceImp implements BeerCsvService {


	@Override
	public List<BeerCSVRecord> convertCsv(File csvFile) {

		try {
			List<BeerCSVRecord> beerCSVRecords =
					new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile)).withType(BeerCSVRecord.class)
					                                                            .build()
					                                                            .parse();
			return beerCSVRecords;

		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
