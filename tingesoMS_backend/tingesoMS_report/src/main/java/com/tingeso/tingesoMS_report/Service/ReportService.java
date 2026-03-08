package com.tingeso.tingesoMS_report.Service;

import com.tingeso.tingesoMS_report.Dtos.ExpenseDto;
import com.tingeso.tingesoMS_report.Dtos.IncomeDto;
import com.tingeso.tingesoMS_report.Dtos.MovementDto;
import com.tingeso.tingesoMS_report.Dtos.ReportResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private RestTemplate restTemplate;

    // Ajustar puertos según tus microservicios (usando puertos estáticos como pide el proyecto)
    private static final String INCOME_SERVICE_URL = "http://Localhost:6002/api/incomes";
    private static final String EXPENSE_SERVICE_URL = "http://Localhost:6003/api/expenses";

    /*
    public ReportResponseDto generateReport(LocalDate startDate, LocalDate endDate) {
        // Obtenemos las listas completas de los otros microservicios
        List<IncomeDto> incomes = getIncomesFromMs();
        List<ExpenseDto> expenses = getExpensesFromMs();

        // Filtramos por fecha (Heurística 3: Libertad del usuario para elegir el rango)
        List<IncomeDto> filteredIncomes = incomes.stream()
                .filter(i -> !i.getDate().isBefore(startDate) && !i.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        List<ExpenseDto> filteredExpenses = expenses.stream()
                .filter(e -> !e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        // Calculamos totales usando los nombres de campos específicos de tus entidades
        Double totalIncome = filteredIncomes.stream()
                .map(IncomeDto::getIncome)
                .filter(Objects::nonNull) // Evita sumar nulos
                .mapToDouble(Double::doubleValue)
                .sum();

        Double totalExpense = filteredExpenses.stream()
                .map(ExpenseDto::getExpense)
                .filter(Objects::nonNull) // Evita sumar nulos
                .mapToDouble(Double::doubleValue)
                .sum();

        Double balance = totalIncome - totalExpense;

        // El DTO de respuesta llevará las listas filtradas y los totales calculados
        return new ReportResponseDto(
                filteredIncomes,
                filteredExpenses,
                totalIncome,
                totalExpense,
                balance,
                startDate,
                endDate
        );
    }
    */

    public ReportResponseDto generateReport(LocalDate startDate, LocalDate endDate) {
        List<IncomeDto> incomes = getIncomesFromMs();
        List<ExpenseDto> expenses = getExpensesFromMs();

        List<MovementDto> allMovements = new ArrayList<>();

        // Mapear Entradas: income tiene valor, expense es null
        incomes.stream()
                .filter(i -> !i.getDate().isBefore(startDate) && !i.getDate().isAfter(endDate))
                .forEach(i -> allMovements.add(new MovementDto(
                        i.getDate(), i.getDocumentType(), i.getDocumentNumber(),
                        i.getReason(), i.getIncome(), null
                )));

        // Mapear Salidas: income es null, expense tiene valor
        expenses.stream()
                .filter(e -> !e.getDate().isBefore(startDate) && !e.getDate().isAfter(endDate))
                .forEach(e -> allMovements.add(new MovementDto(
                        e.getDate(), e.getDocumentType(), e.getDocumentNumber(),
                        e.getReason(), null, e.getExpense()
                )));

        // Ordenar cronológicamente para que la tabla tenga sentido
        List<MovementDto> sortedMovements = allMovements.stream()
                .sorted(Comparator.comparing(MovementDto::getDate))
                .collect(Collectors.toList());

        // Cálculos de totales para el resumen superior
        Double totalIncome = incomes.stream()
                .map(IncomeDto::getIncome)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue).sum();

        Double totalExpense = expenses.stream()
                .map(ExpenseDto::getExpense)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue).sum();

        Double balance = totalIncome - totalExpense;

        return new ReportResponseDto(
                sortedMovements,
                totalIncome,
                totalExpense,
                balance,
                startDate,
                endDate
        );
    }



    private List<IncomeDto> getIncomesFromMs() {
        try {
            ResponseEntity<List<IncomeDto>> response = restTemplate.exchange(
                    INCOME_SERVICE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<IncomeDto>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en MS Income: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<ExpenseDto> getExpensesFromMs() {
        try {
            ResponseEntity<List<ExpenseDto>> response = restTemplate.exchange(
                    EXPENSE_SERVICE_URL,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ExpenseDto>>() {}
            );
            return response.getBody() != null ? response.getBody() : Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Error en MS Expense: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
