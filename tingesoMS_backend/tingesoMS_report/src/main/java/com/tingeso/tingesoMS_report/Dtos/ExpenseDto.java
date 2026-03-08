package com.tingeso.tingesoMS_report.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    private Long id;
    private LocalDate date;
    private String documentType;
    private String documentNumber;
    private String reason;
    private Double expense;
}
