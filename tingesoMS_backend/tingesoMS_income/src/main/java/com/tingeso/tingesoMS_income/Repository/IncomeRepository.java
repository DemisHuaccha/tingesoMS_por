package com.tingeso.tingesoMS_income.Repository;

import com.tingeso.tingesoMS_income.Entities.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
}
