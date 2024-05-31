package com.example.FinanceTracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeDTO {

	@NotBlank(message = "Description is mandatory")
	private String incomeDescription;

	@NotNull(message = "Amount is mandatory")
	@Min(value = 0, message = "Amount must be positive")
	private BigDecimal incomeAmount;
	
	@NotNull(message = "Date is mandatory")
	@JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;
	
	private String mode;

}
