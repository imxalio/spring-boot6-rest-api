package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

	private final BeerService beerService;

	@GetMapping(value = BEER_PATH)
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}


	@GetMapping(value = BEER_PATH_ID)
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("Get Beer by Id - in controller");
		return beerService.getBeerById(beerId);
	}

	@PostMapping(value = BEER_PATH)
	public ResponseEntity handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/beer/" + savedBeer.getId()
		                                                       .toString());
		return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
	}


	@PutMapping(value = BEER_PATH_ID)
	public ResponseEntity updateByID(@PathVariable(value = "beerId") UUID id, @RequestBody Beer beer) {
		beerService.updateByBeerId(id, beer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = BEER_PATH_ID)
	public ResponseEntity deleteById(@PathVariable(value = "beerId") UUID id) {
		beerService.deleteBeer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@PatchMapping(value = BEER_PATH_ID)
	public ResponseEntity patchBeerById(@PathVariable(value = "beerId") UUID beerId, @RequestBody Beer beer) {
		beerService.patchBeerById(beerId, beer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
