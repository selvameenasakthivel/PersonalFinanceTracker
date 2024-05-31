package com.example.FinanceTracker.service;

import com.example.FinanceTracker.model.Report;

public interface ReportService {

	Report generateFinancialReport(String month, int year);

}
