package com.tingeso.tingesoMS_expense.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String documentType; // Boleta/Factura
    private String documentNumber;
    private String reason; // Artículos de oficina, Productos de limpieza, Reparaciones, Combustible, Taxis, Alimentación, Varios
    private Double expense;
}
