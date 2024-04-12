package com.xaliodev.springrest6mvc.service;

import com.xaliodev.springrest6mvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {
	List<BeerCSVRecord> convertCsv(File csvFile);
}
