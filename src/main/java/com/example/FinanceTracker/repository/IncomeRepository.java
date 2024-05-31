package com.example.FinanceTracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FinanceTracker.model.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long>{

}
