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

import com.example.FinanceTracker.dto.IncomeDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Income;
import com.example.FinanceTracker.repository.IncomeRepository;
import com.example.FinanceTracker.serviceImpl.IncomeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {

	@Mock
    private IncomeRepository incomeRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private IncomeServiceImpl incomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllIncome() {
        List<Income> incomeList = Arrays.asList(new Income(), new Income());
        when(incomeRepository.findAll()).thenReturn(incomeList);
        when(objectMapper.convertValue(any(), eq(IncomeDTO.class))).thenReturn(new IncomeDTO());
        List<IncomeDTO> result = incomeService.getAllIncome();
        assertEquals(2, result.size());
        verify(incomeRepository, times(1)).findAll();
        verify(objectMapper, times(2)).convertValue(any(), eq(IncomeDTO.class));
    }

    @Test
    void testGetIncomeById() {
        Long id = 1L;
        Income income = new Income();
        income.setId(id);
        IncomeDTO incomeDTO = new IncomeDTO();
        when(incomeRepository.findById(id)).thenReturn(Optional.of(income));
        when(objectMapper.convertValue(income, IncomeDTO.class)).thenReturn(incomeDTO);
        IncomeDTO result = incomeService.getIncomeById(id);
        assertEquals(incomeDTO, result);
        verify(incomeRepository, times(1)).findById(id);
        verify(objectMapper, times(1)).convertValue(income, IncomeDTO.class);
    }

    @Test
    void testGetIncomeById_NotFound() {
        Long id = 1L;
        Optional<Income> optionalIncome = Optional.empty();
        when(incomeRepository.findById(id)).thenReturn(optionalIncome);
        assertThrows(ResourceNotFoundException.class, () -> incomeService.getIncomeById(id));
        verify(incomeRepository, times(1)).findById(id);
        verify(objectMapper, never()).convertValue(any(), eq(IncomeDTO.class));
    }

    @Test
    void testSaveIncome() {
        IncomeDTO incomeDTO = new IncomeDTO();
        incomeDTO.setIncomeDescription("Test Income");
        Income income = new Income();
        income.setIncomeDescription("Test Income");
        when(objectMapper.convertValue(incomeDTO, Income.class)).thenReturn(income);
        when(incomeRepository.save(income)).thenReturn(income);
        when(objectMapper.convertValue(income, IncomeDTO.class)).thenReturn(incomeDTO);
        IncomeDTO result = incomeService.saveIncome(incomeDTO);
        assertEquals(incomeDTO, result);
        verify(incomeRepository, times(1)).save(income);
        verify(objectMapper, times(1)).convertValue(incomeDTO, Income.class);
        verify(objectMapper, times(1)).convertValue(income, IncomeDTO.class);
    }
}
