package com.xaliodev.springrest6mvc.mappers;


import com.xaliodev.springrest6mvc.entities.Customer;
import com.xaliodev.springrest6mvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

	Customer customerDtoToCustomer(CustomerDTO dto);

	CustomerDTO customerToCustomerDto(Customer customer);
}
