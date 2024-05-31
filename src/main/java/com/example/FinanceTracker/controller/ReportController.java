package com.example.FinanceTracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceTracker.model.Report;
import com.example.FinanceTracker.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<Report> generateFinancialReport(@Valid @RequestParam(required = false) String month, @RequestParam int year) {
        logger.info("Generating financial report for month: {} and year: {}", month, year);
        Report report = reportService.generateFinancialReport(month, year);
        return ResponseEntity.ok(report);
    }

}
