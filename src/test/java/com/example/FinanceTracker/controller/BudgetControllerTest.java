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

import com.example.FinanceTracker.dto.BudgetDTO;
import com.example.FinanceTracker.service.BudgetService;

@ExtendWith(MockitoExtension.class)
public class BudgetControllerTest {
	
	@Mock
    private BudgetService budgetService;

    @InjectMocks
    private BudgetController budgetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBudgets() {
        BudgetDTO budget1 = new BudgetDTO();
        BudgetDTO budget2 = new BudgetDTO();
        List<BudgetDTO> budgetList = Arrays.asList(budget1, budget2);
        when(budgetService.getAllBudgets()).thenReturn(budgetList);
        ResponseEntity<List<BudgetDTO>> responseEntity = budgetController.getAllBudgets();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(budgetList, responseEntity.getBody());
        verify(budgetService, times(1)).getAllBudgets();
    }

    @Test
    void testGetBudgetById() {
        long id = 1L;
        BudgetDTO budget = new BudgetDTO();
        when(budgetService.getBudgetById(id)).thenReturn(budget);
        ResponseEntity<BudgetDTO> responseEntity = budgetController.getBudgetById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(budget, responseEntity.getBody());
        verify(budgetService, times(1)).getBudgetById(id);
    }

    @Test
    void testCreateBudget() {
        BudgetDTO budgetDTO = new BudgetDTO();
        when(budgetService.saveBudget(budgetDTO)).thenReturn(budgetDTO);
        ResponseEntity<BudgetDTO> responseEntity = budgetController.createBudget(budgetDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(budgetDTO, responseEntity.getBody());
        verify(budgetService, times(1)).saveBudget(budgetDTO);
    }

    @Test
    void testUpdateBudget() {
        long id = 1L;
        BudgetDTO budgetDTO = new BudgetDTO();
        when(budgetService.updateBudget(id, budgetDTO)).thenReturn(budgetDTO);
        ResponseEntity<BudgetDTO> responseEntity = budgetController.updateBudget(id, budgetDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(budgetDTO, responseEntity.getBody());
        verify(budgetService, times(1)).updateBudget(id, budgetDTO);
    }

    @Test
    void testDeleteBudget() {
        long id = 1L;
        ResponseEntity<Void> responseEntity = budgetController.deleteBudget(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(budgetService, times(1)).deleteBudget(id);
    }

}
