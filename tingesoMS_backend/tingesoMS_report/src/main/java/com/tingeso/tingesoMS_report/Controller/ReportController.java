package com.tingeso.tingesoMS_report.Controller;

import com.tingeso.tingesoMS_report.Dtos.ReportResponseDto;
import com.tingeso.tingesoMS_report.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/summary")
    public ResponseEntity<?> getSummaryReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        // Heurística 5: Prevención de errores (Validación de fechas)
        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest()
                    .body("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        try {
            ReportResponseDto report = reportService.generateReport(startDate, endDate);

            // CORRECCIÓN AQUÍ: Ahora validamos la lista única de movimientos
            // Heurística 9: Ayudar a reconocer que no hay datos en ese rango
            if (report.getMovements() == null || report.getMovements().isEmpty()) {
                return ResponseEntity.ok()
                        .body("No se encontraron movimientos (entradas o salidas) en el rango seleccionado.");
            }

            return ResponseEntity.ok(report);

        } catch (Exception e) {
            // Log para debug interno
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Ocurrió un error al generar el reporte: " + e.getMessage());
        }
    }
}
