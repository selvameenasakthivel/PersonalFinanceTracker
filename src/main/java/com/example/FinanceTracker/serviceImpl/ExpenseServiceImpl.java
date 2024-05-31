package com.example.FinanceTracker.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.FinanceTracker.dto.ExpenseDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Expense;
import com.example.FinanceTracker.repository.ExpenseRepository;
import com.example.FinanceTracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Validated
public class ExpenseServiceImpl implements ExpenseService {

	private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);

	@Autowired
	private ExpenseRepository expenseRepository;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public List<ExpenseDTO> getAllExpenses() {
		logger.info("Retrieving all expenses");
		return expenseRepository.findAll().stream().map(expense -> objectMapper.convertValue(expense, ExpenseDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ExpenseDTO getExpenseById(Long id) {
		logger.info("Retrieving expense with id: {}", id);
		return expenseRepository.findById(id).map(expense -> objectMapper.convertValue(expense, ExpenseDTO.class))
				.orElseThrow(() -> {
					logger.error("Expense not found with id: {}", id);
					return new ResourceNotFoundException("Expense not found with id " + id);
				});
	}

	@Override
	public ExpenseDTO saveExpense(ExpenseDTO expenseDTO) {
		logger.info("Creating new expense with description: {}", expenseDTO.getExpenseDescription());
		Expense expense = objectMapper.convertValue(expenseDTO, Expense.class);
		Expense savedExpense = expenseRepository.save(expense);
		logger.info("Expense created with id: {}", savedExpense.getId());
		return objectMapper.convertValue(savedExpense, ExpenseDTO.class);
	}

	@Override
	public ExpenseDTO updateExpense(Long id, ExpenseDTO expenseDTO) {
		logger.info("Updating expense with id: {}", id);
		Optional<Expense> existingExpense = expenseRepository.findById(id);
		if (existingExpense.isPresent()) {
			Expense updatedExpense = existingExpense.get();
			updatedExpense.setExpenseDescription(expenseDTO.getExpenseDescription());
			updatedExpense.setDate(expenseDTO.getDate());
			updatedExpense.setExpenseAmount(expenseDTO.getExpenseAmount());
			updatedExpense.setMode(expenseDTO.getMode());
			Expense savedExpense = expenseRepository.save(updatedExpense);
			logger.info("Expense updated with id: {}", savedExpense.getId());
			return objectMapper.convertValue(savedExpense, ExpenseDTO.class);
		} else {
			logger.error("Expense not found with id: {}", id);
			throw new ResourceNotFoundException("Expense not found with id " + id);
		}
	}

	@Override
	public void deleteExpense(Long id) {
		logger.info("Deleting expense with id: {}", id);
		Optional<Expense> expenseOptional = expenseRepository.findById(id);
		if (expenseOptional.isPresent()) {
			expenseRepository.deleteById(id);
		} else {
			throw new ResourceNotFoundException("Expense with id " + id + " not found");
		}
	}

}
