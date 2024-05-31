package com.example.FinanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinanceTracker.model.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>{

}
