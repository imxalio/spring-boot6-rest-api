package com.example.springrest6mvb.service;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
	public void patchBeerById(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);

		if (StringUtils.hasText(beer.getBeerName())) {
			existing.setBeerName(beer.getBeerName());
		}

		if (beer.getBeerStyle() != null) {
			existing.setBeerStyle(beer.getBeerStyle());
		}

		if (beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
		}

		if (beer.getQuantityOnHand() != null) {
			existing.setQuantityOnHand(beer.getQuantityOnHand());
		}

		if (StringUtils.hasText(beer.getUpc())) {
			existing.setUpc(beer.getUpc());
		}
	}

	@Override
	public void deleteById(UUID beerId) {
		beerMap.remove(beerId);
	}

	@Override
	public void updateBeerById(UUID beerId, Beer beer) {
		Beer existing = beerMap.get(beerId);
		existing.setBeerName(beer.getBeerName());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());
	}

	@Override
	public List<Beer> listBeers() {
		return new ArrayList<>(beerMap.values());
	}

	@Override
	public Optional<Beer> getBeerById(UUID id) {

		log.debug("Get Beer by Id - in service. Id: " + id.toString());

		return Optional.of(beerMap.get(id));
	}

	@Override
	public Beer saveNewBeer(Beer beer) {

		Beer savedBeer = Beer.builder()
		                     .id(UUID.randomUUID())
		                     .version(1)
		                     .createdDate(LocalDateTime.now())
		                     .updateDate(LocalDateTime.now())
		                     .beerName(beer.getBeerName())
		                     .beerStyle(beer.getBeerStyle())
		                     .quantityOnHand(beer.getQuantityOnHand())
		                     .upc(beer.getUpc())
		                     .price(beer.getPrice())
		                     .build();

		beerMap.put(savedBeer.getId(), savedBeer);

		return savedBeer;
	}
}
