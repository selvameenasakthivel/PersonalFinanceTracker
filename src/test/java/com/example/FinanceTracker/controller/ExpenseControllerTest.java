package com.example.FinanceTracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.FinanceTracker.dto.ExpenseDTO;
import com.example.FinanceTracker.service.ExpenseService;

@ExtendWith(MockitoExtension.class)
public class ExpenseControllerTest {

	@Mock
	private ExpenseService expenseService;

	@InjectMocks
	private ExpenseController expenseController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetAllExpenses() {
		ExpenseDTO expense1 = new ExpenseDTO();
		ExpenseDTO expense2 = new ExpenseDTO();
		List<ExpenseDTO> expenseList = Arrays.asList(expense1, expense2);
		when(expenseService.getAllExpenses()).thenReturn(expenseList);
		ResponseEntity<List<ExpenseDTO>> responseEntity = expenseController.getAllExpenses();
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expenseList, responseEntity.getBody());
		verify(expenseService, times(1)).getAllExpenses();
	}

	@Test
	void testGetExpenseById() {
		long id = 1L;
		ExpenseDTO expense = new ExpenseDTO();
		when(expenseService.getExpenseById(id)).thenReturn(expense);
		ResponseEntity<ExpenseDTO> responseEntity = expenseController.getExpenseById(id);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expense, responseEntity.getBody());
		verify(expenseService, times(1)).getExpenseById(id);
	}

	@Test
	void testCreateExpense() {
		ExpenseDTO expenseDTO = new ExpenseDTO();
		when(expenseService.saveExpense(expenseDTO)).thenReturn(expenseDTO);
		ResponseEntity<ExpenseDTO> responseEntity = expenseController.createExpense(expenseDTO);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expenseDTO, responseEntity.getBody());
		verify(expenseService, times(1)).saveExpense(expenseDTO);
	}

	@Test
	void testUpdateExpense() {
		long id = 1L;
		ExpenseDTO expenseDTO = new ExpenseDTO();
		when(expenseService.updateExpense(id, expenseDTO)).thenReturn(expenseDTO);
		ResponseEntity<ExpenseDTO> responseEntity = expenseController.updateExpense(id, expenseDTO);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(expenseDTO, responseEntity.getBody());
		verify(expenseService, times(1)).updateExpense(id, expenseDTO);
	}

	@Test
	void testDeleteExpense() {
		long id = 1L;
		ResponseEntity<Void> responseEntity = expenseController.deleteExpense(id);
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
		verify(expenseService, times(1)).deleteExpense(id);
	}
}
