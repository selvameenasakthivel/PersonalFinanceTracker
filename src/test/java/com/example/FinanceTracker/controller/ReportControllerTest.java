package com.example.FinanceTracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.FinanceTracker.model.Report;
import com.example.FinanceTracker.service.ReportService;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

	@Mock
	private ReportService reportService;

	@InjectMocks
	private ReportController reportController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGenerateFinancialReport() {
		String month = "January";
		int year = 2024;
		Report report = new Report();
		when(reportService.generateFinancialReport(month, year)).thenReturn(report);
		ResponseEntity<Report> responseEntity = reportController.generateFinancialReport(month, year);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(report, responseEntity.getBody());
		verify(reportService, times(1)).generateFinancialReport(month, year);
	}
}
