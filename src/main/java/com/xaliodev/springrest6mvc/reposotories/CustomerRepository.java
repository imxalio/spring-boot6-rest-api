package com.xaliodev.springrest6mvc.reposotories;

import com.xaliodev.springrest6mvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
