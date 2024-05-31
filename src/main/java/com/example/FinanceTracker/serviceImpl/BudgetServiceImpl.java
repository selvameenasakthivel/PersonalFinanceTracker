package com.example.FinanceTracker.serviceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceTracker.dto.BudgetDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Budget;
import com.example.FinanceTracker.repository.BudgetRepository;
import com.example.FinanceTracker.service.BudgetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BudgetServiceImpl implements BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public BudgetDTO saveBudget(BudgetDTO budgetDTO) {
		Budget budget = objectMapper.convertValue(budgetDTO, Budget.class);
		return objectMapper.convertValue(budgetRepository.save(budget), BudgetDTO.class);
	}

	@Override
	public BudgetDTO updateBudget(Long id, BudgetDTO budgetDTO) {
		Optional<Budget> existingBudget = budgetRepository.findById(id);
		if (existingBudget.isPresent()) {
			Budget updatedBudget = existingBudget.get();
			updatedBudget.setDescription(budgetDTO.getDescription());
			updatedBudget.setAmount(budgetDTO.getAmount());
			updatedBudget.setMonth(budgetDTO.getMonth());
			updatedBudget.setYear(budgetDTO.getYear());
			return objectMapper.convertValue(budgetRepository.save(updatedBudget), BudgetDTO.class);
		} else {
			throw new ResourceNotFoundException("Budget not found with id " + id);
		}
	}

	@Override
	public void deleteBudget(Long id) {
		budgetRepository.deleteById(id);
	}

	@Override
	public List<BudgetDTO> getAllBudgets() {
		return budgetRepository.findAll().stream().map(budget -> objectMapper.convertValue(budget, BudgetDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public BudgetDTO getBudgetById(Long id) {
		return budgetRepository.findById(id).map(budget -> objectMapper.convertValue(budget, BudgetDTO.class))
				.orElseThrow(() -> new ResourceNotFoundException("Budget not found with id " + id));
	}

}
