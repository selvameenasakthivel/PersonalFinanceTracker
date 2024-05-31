package com.example.FinanceTracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookDTO {
	private String bookName;
	private int bookCount;
	private Double amount;

}
