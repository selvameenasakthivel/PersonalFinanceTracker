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

import com.example.FinanceTracker.dto.ExpenseDTO;
import com.example.FinanceTracker.model.Expense;
import com.example.FinanceTracker.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@GetMapping
	public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
		return ResponseEntity.ok(expenseService.getAllExpenses());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseDTO> getExpenseById(@PathVariable Long id) {
		return ResponseEntity.ok(expenseService.getExpenseById(id));
	}

	@PostMapping
	public ResponseEntity<ExpenseDTO> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
		return ResponseEntity.ok(expenseService.saveExpense(expenseDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExpenseDTO> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO expenseDTO) {
		return ResponseEntity.ok(expenseService.updateExpense(id, expenseDTO));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
		expenseService.deleteExpense(id);
		return ResponseEntity.noContent().build();
	}
}
