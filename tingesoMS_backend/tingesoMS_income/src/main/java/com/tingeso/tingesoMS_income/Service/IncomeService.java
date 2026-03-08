package com.tingeso.tingesoMS_income.Service;

import com.tingeso.tingesoMS_income.Entities.Income;
import com.tingeso.tingesoMS_income.Repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }
    
    public Income getIncomeById(Long id) {
        return incomeRepository.findById(id).orElse(null);
    }
}
