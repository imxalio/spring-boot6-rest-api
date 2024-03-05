package com.xaliodev.springrest6mvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
	private UUID id;
	private String customerName;
	private Integer version;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
}
