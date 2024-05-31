package com.example.FinanceTracker.service;

import java.util.List;

import com.example.FinanceTracker.dto.IncomeDTO;

public interface IncomeService {

	List<IncomeDTO> getAllIncome();

	IncomeDTO getIncomeById(Long id);

	IncomeDTO saveIncome(IncomeDTO incomeDTO);

	IncomeDTO updateIncome(Long id, IncomeDTO incomeDTO);

	void deleteIncome(Long id);

}
