package com.example.FinanceTracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.FinanceTracker.dto.BudgetDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Budget;
import com.example.FinanceTracker.repository.BudgetRepository;
import com.example.FinanceTracker.serviceImpl.BudgetServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {

	@Mock
	private BudgetRepository budgetRepository;

	@Mock
	private ObjectMapper objectMapper;

	@InjectMocks
	private BudgetServiceImpl budgetService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testSaveBudget() {
		BudgetDTO budgetDTO = new BudgetDTO();
		Budget budget = new Budget();
		when(objectMapper.convertValue(budgetDTO, Budget.class)).thenReturn(budget);
		when(budgetRepository.save(budget)).thenReturn(budget);
		when(objectMapper.convertValue(budget, BudgetDTO.class)).thenReturn(budgetDTO);
		BudgetDTO result = budgetService.saveBudget(budgetDTO);
		assertEquals(budgetDTO, result);
		verify(objectMapper, times(1)).convertValue(budgetDTO, Budget.class);
		verify(budgetRepository, times(1)).save(budget);
		verify(objectMapper, times(1)).convertValue(budget, BudgetDTO.class);
	}
	
	@Test
	void testUpdateBudget_NotFound() {
		Long id = 1L;
		BudgetDTO budgetDTO = new BudgetDTO();
		budgetDTO.setDescription("Updated Description");
		Optional<Budget> optionalExistingBudget = Optional.empty();
		when(budgetRepository.findById(id)).thenReturn(optionalExistingBudget);
		assertThrows(ResourceNotFoundException.class, () -> budgetService.updateBudget(id, budgetDTO));
		verify(budgetRepository, times(1)).findById(id);
		verify(budgetRepository, never()).save(any());
	}

	@Test
	void testDeleteBudget() {
		Long id = 1L;
		budgetService.deleteBudget(id);
		verify(budgetRepository, times(1)).deleteById(id);
	}

	@Test
	void testGetAllBudgets() {
		List<Budget> budgets = Arrays.asList(new Budget(), new Budget());
		when(budgetRepository.findAll()).thenReturn(budgets);
		when(objectMapper.convertValue(any(), eq(BudgetDTO.class))).thenReturn(new BudgetDTO());
		List<BudgetDTO> result = budgetService.getAllBudgets();
		assertEquals(2, result.size());
		verify(budgetRepository, times(1)).findAll();
		verify(objectMapper, times(2)).convertValue(any(), eq(BudgetDTO.class));
	}

	@Test
	void testGetBudgetById() {
		Long id = 1L;
		Budget budget = new Budget();
		budget.setId(id);
		BudgetDTO budgetDTO = new BudgetDTO();
		when(budgetRepository.findById(id)).thenReturn(Optional.of(budget));
		when(objectMapper.convertValue(budget, BudgetDTO.class)).thenReturn(budgetDTO);
		BudgetDTO result = budgetService.getBudgetById(id);
		assertEquals(budgetDTO, result);
		verify(budgetRepository, times(1)).findById(id);
		verify(objectMapper, times(1)).convertValue(budget, BudgetDTO.class);
	}

	@Test
	void testGetBudgetById_NotFound() {
		Long id = 1L;
		Optional<Budget> optionalBudget = Optional.empty();
		when(budgetRepository.findById(id)).thenReturn(optionalBudget);
		assertThrows(ResourceNotFoundException.class, () -> budgetService.getBudgetById(id));
		verify(budgetRepository, times(1)).findById(id);
		verify(objectMapper, never()).convertValue(any(), eq(BudgetDTO.class));
	}
}
