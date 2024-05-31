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

import com.example.FinanceTracker.dto.IncomeDTO;
import com.example.FinanceTracker.service.IncomeService;

@ExtendWith(MockitoExtension.class)
public class IncomeControllerTest {

	@Mock
    private IncomeService incomeService;

    @InjectMocks
    private IncomeController incomeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllIncomes() {
        IncomeDTO income1 = new IncomeDTO();
        IncomeDTO income2 = new IncomeDTO();
        List<IncomeDTO> incomeList = Arrays.asList(income1, income2);
        when(incomeService.getAllIncome()).thenReturn(incomeList);
        ResponseEntity<List<IncomeDTO>> responseEntity = incomeController.getAllIncomes();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(incomeList, responseEntity.getBody());
        verify(incomeService, times(1)).getAllIncome();
    }

    @Test
    void testGetIncomeById() {
        long id = 1L;
        IncomeDTO income = new IncomeDTO();
        when(incomeService.getIncomeById(id)).thenReturn(income);
        ResponseEntity<IncomeDTO> responseEntity = incomeController.getIncomeById(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(income, responseEntity.getBody());
        verify(incomeService, times(1)).getIncomeById(id);
    }

    @Test
    void testCreateIncome() {
        IncomeDTO incomeDTO = new IncomeDTO();
        when(incomeService.saveIncome(incomeDTO)).thenReturn(incomeDTO);
        ResponseEntity<IncomeDTO> responseEntity = incomeController.createIncome(incomeDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(incomeDTO, responseEntity.getBody());
        verify(incomeService, times(1)).saveIncome(incomeDTO);
    }

    @Test
    void testUpdateIncome() {
        long id = 1L;
        IncomeDTO incomeDTO = new IncomeDTO();
        when(incomeService.updateIncome(id, incomeDTO)).thenReturn(incomeDTO);
        ResponseEntity<IncomeDTO> responseEntity = incomeController.updateIncome(id, incomeDTO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(incomeDTO, responseEntity.getBody());
        verify(incomeService, times(1)).updateIncome(id, incomeDTO);
    }

    @Test
    void testDeleteIncome() {
        long id = 1L;
        ResponseEntity<Void> responseEntity = incomeController.deleteIncome(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(incomeService, times(1)).deleteIncome(id);
    }
}
