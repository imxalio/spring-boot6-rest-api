package com.xaliodev.springrest6mvc.mappers;


import com.xaliodev.springrest6mvc.entities.Beer;
import com.xaliodev.springrest6mvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

	Beer beerDtoToBeer(BeerDTO dto);

	BeerDTO beerToBeerDTO(Beer beer);
}
