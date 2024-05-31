package com.example.FinanceTracker.service;

import java.util.List;

import com.example.FinanceTracker.dto.ExpenseDTO;
import com.example.FinanceTracker.model.Expense;

public interface ExpenseService {

	ExpenseDTO saveExpense(ExpenseDTO expenseDTO);

	List<ExpenseDTO> getAllExpenses();

	void deleteExpense(Long id);

	ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO);

	ExpenseDTO getExpenseById(Long id);

}
