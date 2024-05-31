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
import java.time.LocalDate;
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

import com.example.FinanceTracker.dto.ExpenseDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Expense;
import com.example.FinanceTracker.repository.ExpenseRepository;
import com.example.FinanceTracker.serviceImpl.ExpenseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

	@Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllExpenses() {
        List<Expense> expenses = Arrays.asList(new Expense(), new Expense());
        when(expenseRepository.findAll()).thenReturn(expenses);
        when(objectMapper.convertValue(any(), eq(ExpenseDTO.class))).thenReturn(new ExpenseDTO());
        List<ExpenseDTO> result = expenseService.getAllExpenses();
        assertEquals(2, result.size());
        verify(expenseRepository, times(1)).findAll();
        verify(objectMapper, times(2)).convertValue(any(), eq(ExpenseDTO.class));
    }

    @Test
    void testGetExpenseById() {
        Long id = 1L;
        Expense expense = new Expense();
        expense.setId(id);
        ExpenseDTO expenseDTO = new ExpenseDTO();
        when(expenseRepository.findById(id)).thenReturn(Optional.of(expense));
        when(objectMapper.convertValue(expense, ExpenseDTO.class)).thenReturn(expenseDTO);
        ExpenseDTO result = expenseService.getExpenseById(id);
        assertEquals(expenseDTO, result);
        verify(expenseRepository, times(1)).findById(id);
        verify(objectMapper, times(1)).convertValue(expense, ExpenseDTO.class);
    }

    @Test
    void testGetExpenseById_NotFound() {
        Long id = 1L;
        Optional<Expense> optionalExpense = Optional.empty();
        when(expenseRepository.findById(id)).thenReturn(optionalExpense);
        assertThrows(ResourceNotFoundException.class, () -> expenseService.getExpenseById(id));
        verify(expenseRepository, times(1)).findById(id);
        verify(objectMapper, never()).convertValue(any(), eq(ExpenseDTO.class));
    }

    @Test
    void testSaveExpense() {
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setExpenseDescription("Test Expense");
        Expense expense = new Expense();
        expense.setExpenseDescription("Test Expense");
        when(objectMapper.convertValue(expenseDTO, Expense.class)).thenReturn(expense);
        when(expenseRepository.save(expense)).thenReturn(expense);
        when(objectMapper.convertValue(expense, ExpenseDTO.class)).thenReturn(expenseDTO);
        ExpenseDTO result = expenseService.saveExpense(expenseDTO);
        assertEquals(expenseDTO, result);
        verify(expenseRepository, times(1)).save(expense);
        verify(objectMapper, times(1)).convertValue(expenseDTO, Expense.class);
        verify(objectMapper, times(1)).convertValue(expense, ExpenseDTO.class);
    }

    @Test
    void testUpdateExpense_NotFound() {
        Long id = 1L;
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setExpenseDescription("Updated Expense");
        Optional<Expense> optionalExistingExpense = Optional.empty();
        when(expenseRepository.findById(id)).thenReturn(optionalExistingExpense);
        assertThrows(ResourceNotFoundException.class, () -> expenseService.updateExpense(id, expenseDTO));
        verify(expenseRepository, times(1)).findById(id);
        verify(expenseRepository, never()).save(any());
    }

    @Test
    void testDeleteExpense() {
        Long id = 1L;
        Optional<Expense> expenseOptional = Optional.of(new Expense());
        when(expenseRepository.findById(id)).thenReturn(expenseOptional);
        expenseService.deleteExpense(id);
        verify(expenseRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteExpense_NotFound() {
        Long id = 1L;
        Optional<Expense> expenseOptional = Optional.empty();
        when(expenseRepository.findById(id)).thenReturn(expenseOptional);
        assertThrows(ResourceNotFoundException.class, () -> expenseService.deleteExpense(id));
        verify(expenseRepository, times(1)).findById(id);
        verify(expenseRepository, never()).deleteById(any());
    }
}
