package com.tingeso.tingesoMS_report.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {
    private List<MovementDto> movements;
    private Double totalIncomes;
    private Double totalExpenses;
    private Double balance;
    private LocalDate startDate;
    private LocalDate endDate;
}
