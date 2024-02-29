package com.example.springrest6mvb.service;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
	private Map<UUID, Beer> beerMap;

	public BeerServiceImpl() {
		this.beerMap = new HashMap<>();

		Beer beer1 = Beer.builder()
		                 .id(UUID.randomUUID())
		                 .version(1)
		                 .beerName("Schwarzer Spiegel")
		                 .beerStyle(BeerStyle.PILSNER)
		                 .upc("12341")
		                 .price(new BigDecimal("12.99"))
		                 .quantityOnHand(34)
		                 .createdDate(LocalDateTime.now())
		                 .updateDate(LocalDateTime.now())
		                 .build();

		Beer beer2 = Beer.builder()
		                 .id(UUID.randomUUID())
		                 .version(1)
		                 .beerName("Schöne Frau")
		                 .beerStyle(BeerStyle.ALE)
		                 .upc("112341")
		                 .price(new BigDecimal("19.99"))
		                 .quantityOnHand(21)
		                 .createdDate(LocalDateTime.now())
		                 .updateDate(LocalDateTime.now())
		                 .build();

		Beer beer3 = Beer.builder()
		                 .id(UUID.randomUUID())
		                 .version(1)
		                 .beerName("Starker Mann")
		                 .beerStyle(BeerStyle.STOUT)
		                 .upc("948541")
		                 .price(new BigDecimal("23.99"))
		                 .quantityOnHand(12)
		                 .createdDate(LocalDateTime.now())
		                 .updateDate(LocalDateTime.now())
		                 .build();

		beerMap.put(beer1.getId(), beer1);
		beerMap.put(beer2.getId(), beer2);
		beerMap.put(beer3.getId(), beer3);
	}

	@Override
	public List<Beer> listBeers() {
		return new ArrayList<>(beerMap.values());
	}

	@Override
	public Beer getBeerById(UUID id) {
		log.debug("Get Beer by Id in Service. Id: " + id.toString());
		return beerMap.get(id);
	}

	@Override
	public Beer saveNewBeer(Beer beer) {

		Beer savedBeer = Beer.builder()
		                     .id(UUID.randomUUID())
		                     .id(UUID.randomUUID())
		                     .version(1)
		                     .beerName(beer.getBeerName())
		                     .beerStyle(beer.getBeerStyle())
		                     .upc(beer.getUpc())
		                     .price(beer.getPrice())
		                     .quantityOnHand(beer.getQuantityOnHand())
		                     .createdDate(LocalDateTime.now())
		                     .updateDate(LocalDateTime.now())
		                     .build();
		beerMap.put(savedBeer.getId(), savedBeer);
		return savedBeer;
	}

	@Override
	public void updateByBeerId(UUID id, Beer beer) {
		Beer exiciting = beerMap.get(id);
		exiciting.setBeerName(beer.getBeerName());
		exiciting.setPrice(beer.getPrice());
		exiciting.setUpc(beer.getUpc());
		exiciting.setQuantityOnHand(beer.getQuantityOnHand());

		beerMap.put(exiciting.getId(), exiciting);
	}

	@Override
	public void deleteBeer(UUID id) {
		beerMap.remove(id);
	}
}
