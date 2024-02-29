package com.example.springrest6mvb.controller;

import com.example.springrest6mvb.model.Beer;
import com.example.springrest6mvb.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

	private final BeerService beerService;

	//	@RequestMapping(method = RequestMethod.GET)
	@GetMapping
	public List<Beer> listBeers() {
		return beerService.listBeers();
	}


	//	@RequestMapping(value = "{beerId}", method = RequestMethod.GET)
	@GetMapping(value = "{beerId}")
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("Get Beer by Id - in controller");
		return beerService.getBeerById(beerId);
	}

	@PostMapping
	public ResponseEntity handlePost(@RequestBody Beer beer) {
		Beer savedBeer = beerService.saveNewBeer(beer);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", "/api/v1/beer/" + savedBeer.getId()
		                                                       .toString());
		return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
	}


	@PutMapping("{beerId}")
	public ResponseEntity updateByID(@PathVariable(value = "beerId") UUID id, @RequestBody Beer beer) {
		beerService.updateByBeerId(id, beer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("{beerId}")
	public ResponseEntity deleteById(@PathVariable(value = "beerId") UUID id) {
		beerService.deleteBeer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
}
