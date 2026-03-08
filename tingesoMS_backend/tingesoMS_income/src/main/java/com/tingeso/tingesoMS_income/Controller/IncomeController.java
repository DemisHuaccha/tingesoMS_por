package com.tingeso.tingesoMS_income.Controller;

import com.tingeso.tingesoMS_income.Entities.Income;
import com.tingeso.tingesoMS_income.Service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return ResponseEntity.ok(incomes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getIncomeById(id);
        if (income != null) {
            return ResponseEntity.ok(income);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Income> saveIncome(@RequestBody Income income) {
        Income savedIncome = incomeService.saveIncome(income);
        return ResponseEntity.ok(savedIncome);
    }
}
