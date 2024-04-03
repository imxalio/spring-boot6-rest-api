package com.xaliodev.springrest6mvc.service;

import com.xaliodev.springrest6mvc.model.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

	List<BeerDTO> listBeers();

	Optional<BeerDTO> getBeerById(UUID id);

	BeerDTO saveNewBeer(BeerDTO beer);

	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

	boolean deleteById(UUID beerId);

	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
