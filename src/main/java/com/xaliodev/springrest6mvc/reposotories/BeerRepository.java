package com.xaliodev.springrest6mvc.reposotories;

import com.xaliodev.springrest6mvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
