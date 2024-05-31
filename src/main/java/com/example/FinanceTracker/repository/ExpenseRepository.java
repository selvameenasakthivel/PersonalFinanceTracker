package com.example.FinanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinanceTracker.model.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
