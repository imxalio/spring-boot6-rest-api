package com.xaliodev.springrest6mvc.bootstrap;

import com.xaliodev.springrest6mvc.entities.Beer;
import com.xaliodev.springrest6mvc.entities.Customer;
import com.xaliodev.springrest6mvc.model.BeerCSVRecord;
import com.xaliodev.springrest6mvc.model.BeerStyle;
import com.xaliodev.springrest6mvc.reposotories.BeerRepository;
import com.xaliodev.springrest6mvc.reposotories.CustomerRepository;
import com.xaliodev.springrest6mvc.service.BeerCsvService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

	private final BeerRepository beerRepository;
	private final CustomerRepository customerRepository;
	private final BeerCsvService beerCsvService;

	@Override
	public void run(String... args) throws Exception {
		loadBeerData();
		loadCustomerData();
		loadCsvData();
	}

	private void loadCsvData() throws FileNotFoundException {
		beerRepository.deleteAll();
		if (beerRepository.count() < 10) {
			File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

			List<BeerCSVRecord> recs = beerCsvService.convertCsv(file);

			recs.forEach(beerCSVRecord -> {
				BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
					case "American Pale Lager" -> BeerStyle.LAGER;
					case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
							BeerStyle.ALE;
					case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
					case "American Porter" -> BeerStyle.PORTER;
					case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
					case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
					case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
					case "English Pale Ale" -> BeerStyle.PALE_ALE;
					default -> BeerStyle.PILSNER;
				};

				beerRepository.save(Beer.builder()
				                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
				                        .beerStyle(beerStyle)
				                        .price(BigDecimal.TEN)
				                        .upc(String.valueOf(beerCSVRecord.getRow()))
				                        .quantityOnHand(beerCSVRecord.getCount())
				                        .build());
			});

		}
	}

	private void loadBeerData() {
		if (beerRepository.count() == 0) {
			Beer beer1 = Beer.builder()
			                 .beerName("Schwarzer Spiegel")
			                 .beerStyle(BeerStyle.PILSNER)
			                 .upc("12341")
			                 .price(new BigDecimal("12.99"))
			                 .quantityOnHand(34)
			                 .createdDate(LocalDateTime.now())
			                 .updateDate(LocalDateTime.now())
			                 .build();

			Beer beer2 = Beer.builder()
			                 .beerName("Schöne Frau")
			                 .beerStyle(BeerStyle.ALE)
			                 .upc("112341")
			                 .price(new BigDecimal("19.99"))
			                 .quantityOnHand(21)
			                 .createdDate(LocalDateTime.now())
			                 .updateDate(LocalDateTime.now())
			                 .build();

			Beer beer3 = Beer.builder()
			                 .beerName("Starker Mann")
			                 .beerStyle(BeerStyle.STOUT)
			                 .upc("948541")
			                 .price(new BigDecimal("23.99"))
			                 .quantityOnHand(12)
			                 .createdDate(LocalDateTime.now())
			                 .updateDate(LocalDateTime.now())
			                 .build();


			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);
		}
	}

	private void loadCustomerData() {
		if (customerRepository.count() == 0) {
			Customer customer1 = Customer.builder()
			                             .customerName("Mohamed El Alouani")
			                             .createdDate(LocalDateTime.now())
			                             .updatedDate(LocalDateTime.now())
			                             .build();

			Customer customer2 = Customer.builder()
			                             .customerName("Khalid Rojani")
			                             .createdDate(LocalDateTime.now())
			                             .updatedDate(LocalDateTime.now())
			                             .build();

			Customer customer3 = Customer.builder()

			                             .customerName("Lionel Messi")
			                             .createdDate(LocalDateTime.now())
			                             .updatedDate(LocalDateTime.now())
			                             .build();

			//			customerRepository.save(customer1);
			//			customerRepository.save(customer2);
			//			customerRepository.save(customer3);
			customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
		}
	}
}
