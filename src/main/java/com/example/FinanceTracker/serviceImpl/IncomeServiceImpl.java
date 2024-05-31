package com.example.FinanceTracker.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FinanceTracker.dto.IncomeDTO;
import com.example.FinanceTracker.exception.ResourceNotFoundException;
import com.example.FinanceTracker.model.Income;
import com.example.FinanceTracker.repository.IncomeRepository;
import com.example.FinanceTracker.service.IncomeService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class IncomeServiceImpl implements IncomeService {

	@Autowired
	IncomeRepository incomeRepository;

	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public List<IncomeDTO> getAllIncome() {
		return incomeRepository.findAll().stream().map(income -> objectMapper.convertValue(income, IncomeDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public IncomeDTO getIncomeById(Long id) {
		return incomeRepository.findById(id).map(income -> objectMapper.convertValue(income, IncomeDTO.class))
				.orElseThrow(() -> new ResourceNotFoundException("Income not found with id " + id));
	}

	@Override
	public IncomeDTO saveIncome(IncomeDTO incomeDTO) {
		Income income = objectMapper.convertValue(incomeDTO, Income.class);
		return objectMapper.convertValue(incomeRepository.save(income), IncomeDTO.class);
	}

	@Override
	public IncomeDTO updateIncome(Long id, IncomeDTO incomeDTO) {
		Optional<Income> existingIncome = incomeRepository.findById(id);
		if (existingIncome.isPresent()) {
			Income updatedIncome = existingIncome.get();
			updatedIncome.setIncomeDescription(incomeDTO.getIncomeDescription());
			updatedIncome.setDate(incomeDTO.getDate());
			updatedIncome.setIncomeAmount(incomeDTO.getIncomeAmount());
			updatedIncome.setMode(incomeDTO.getMode());
			return objectMapper.convertValue(incomeRepository.save(updatedIncome), IncomeDTO.class);
		} else {
			throw new ResourceNotFoundException("Income not found with id " + id);
		}
	}

	@Override
	public void deleteIncome(Long id) {
		incomeRepository.deleteById(id);
	}
}
