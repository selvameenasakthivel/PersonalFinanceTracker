package com.example.FinanceTracker.service;

import java.util.List;

import com.example.FinanceTracker.dto.BudgetDTO;
import com.example.FinanceTracker.model.Budget;

import jakarta.validation.Valid;

public interface BudgetService {

	BudgetDTO saveBudget(@Valid BudgetDTO budgetDTO);

	List<BudgetDTO> getAllBudgets();

	BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO);

	void deleteBudget(Long id);

	BudgetDTO getBudgetById(Long id);

}
