package com.tingeso.tingesoMS_expense.Service;

import com.tingeso.tingesoMS_expense.Entities.Expense;
import com.tingeso.tingesoMS_expense.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }
    
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }
}
