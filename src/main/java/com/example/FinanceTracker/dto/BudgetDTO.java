package com.example.FinanceTracker.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BudgetDTO {
	
	@NotBlank(message = "Description is mandatory")
	private String description;

	@NotNull(message = "Amount is mandatory")
	@Min(value = 0, message = "Amount must be positive")
	private BigDecimal amount;
	
	@NotNull(message = "Month is mandatory")
	private String month;
	
	@NotNull(message = "Year is mandatory")
    private Integer year;

}
