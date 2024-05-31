package com.example.FinanceTracker.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceTracker.model.Budget;
import com.example.FinanceTracker.model.Expense;
import com.example.FinanceTracker.model.Income;
import com.example.FinanceTracker.model.Report;
import com.example.FinanceTracker.repository.BudgetRepository;
import com.example.FinanceTracker.repository.ExpenseRepository;
import com.example.FinanceTracker.repository.IncomeRepository;
import com.example.FinanceTracker.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private IncomeRepository incomeRepository;

	@Autowired
	private BudgetRepository budgetRepository;

	@Override
	public Report generateFinancialReport(String month, int year) {
		if (month == null || month.isEmpty()) {
			return generateAnnualFinancialReport(year);
		} else {
			return generateMonthlyFinancialReport(month, year);
		}
	}

	private Report generateMonthlyFinancialReport(String month, int year) {
		List<Expense> expenses = expenseRepository.findAll().stream()
				.filter(expense -> expense.getDate().getMonth().name().equalsIgnoreCase(month)
						&& expense.getDate().getYear() == year)
				.collect(Collectors.toList());
		
		List<Income> incomes = incomeRepository.findAll().stream()
				.filter(income -> income.getDate().getMonth().name().equalsIgnoreCase(month)
						&& income.getDate().getYear() == year)
				.collect(Collectors.toList());

		List<Budget> budgets = budgetRepository.findAll().stream()
				.filter(budget -> budget.getMonth().equalsIgnoreCase(month) && budget.getYear() == year)
				.collect(Collectors.toList());

		BigDecimal totalExpenses = expenses.stream().map(Expense::getExpenseAmount).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		BigDecimal totalIncome = incomes.stream().map(Income::getIncomeAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal estimatedBudget = budgets.stream().map(Budget::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal balanceDifference = totalIncome.subtract(totalExpenses);

		return new Report(month, year, totalIncome, totalExpenses, estimatedBudget, estimatedBudget.subtract(balanceDifference));
	}

	private Report generateAnnualFinancialReport(int year) {
		List<Expense> expenses = expenseRepository.findAll().stream()
				.filter(expense -> expense.getDate().getYear() == year).collect(Collectors.toList());

		List<Income> incomes = incomeRepository.findAll().stream().filter(income -> income.getDate().getYear() == year)
				.collect(Collectors.toList());

		List<Budget> budgets = budgetRepository.findAll().stream().filter(budget -> budget.getYear() == year)
				.collect(Collectors.toList());

		BigDecimal totalExpenses = expenses.stream().map(Expense::getExpenseAmount).reduce(BigDecimal.ZERO,
				BigDecimal::add);

		BigDecimal totalIncome = incomes.stream().map(Income::getIncomeAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal estimatedBudget = budgets.stream().map(Budget::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

		BigDecimal balanceDifference = totalIncome.subtract(totalExpenses);

		return new Report("Yearly", year, totalIncome, totalExpenses, estimatedBudget, estimatedBudget.subtract(balanceDifference));
	}

}
