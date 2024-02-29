package com.example.springrest6mvb.service;

import com.example.springrest6mvb.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {
	List<Beer> listBeers();

	Beer getBeerById(UUID id);

	Beer saveNewBeer(Beer beer);

	void updateByBeerId(UUID id, Beer beer);

	void deleteBeer(UUID id);
}
