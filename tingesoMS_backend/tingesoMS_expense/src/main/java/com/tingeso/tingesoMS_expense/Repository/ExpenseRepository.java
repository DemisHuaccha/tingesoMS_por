package com.tingeso.tingesoMS_expense.Repository;

import com.tingeso.tingesoMS_expense.Entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
