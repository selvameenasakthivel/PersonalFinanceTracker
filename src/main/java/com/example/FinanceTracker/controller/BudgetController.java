package com.example.FinanceTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FinanceTracker.dto.BudgetDTO;
import com.example.FinanceTracker.service.BudgetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/budgets")
public class BudgetController {

	@Autowired
	private BudgetService budgetService;

	@GetMapping
	public ResponseEntity<List<BudgetDTO>> getAllBudgets() {
		return ResponseEntity.ok(budgetService.getAllBudgets());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BudgetDTO> getBudgetById(@PathVariable Long id) {
		return ResponseEntity.ok(budgetService.getBudgetById(id));
	}

	@PostMapping
	public ResponseEntity<BudgetDTO> createBudget(@Valid @RequestBody BudgetDTO budgetDTO) {
		return ResponseEntity.ok(budgetService.saveBudget(budgetDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<BudgetDTO> updateBudget(@PathVariable Long id, @RequestBody BudgetDTO budgetDTO) {
		return ResponseEntity.ok(budgetService.updateBudget(id, budgetDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudget(@PathVariable Long id) {
		budgetService.deleteBudget(id);
		return ResponseEntity.noContent().build();
	}

}
