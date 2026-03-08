package com.tingeso.tingesoMS_report.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovementDto {
    private LocalDate date;
    private String documentType;
    private String documentNumber;
    private String reason;
    private Double income;
    private Double expense;
}
