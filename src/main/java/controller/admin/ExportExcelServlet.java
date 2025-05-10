package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DemandeConge;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/admin/conges/export-excel")
public class ExportExcelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<DemandeConge> resultats = (List<DemandeConge>) request.getSession().getAttribute("resultats");

        if (resultats == null || resultats.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/recherche-conge");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=\"resultats_conges.xlsx\"");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Demandes de congé");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // En-têtes
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Employé", "Département", "Motif",
                    "Date début", "Date fin", "État", "Chef validateur"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Données
            int rowNum = 1;
            for (DemandeConge d : resultats) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(d.getEmploye().getNomComplet());
                row.createCell(1).setCellValue(d.getEmploye().getDepartement().getNom());
                row.createCell(2).setCellValue(d.getMotif());
                row.createCell(3).setCellValue(sdf.format(d.getDateDebut()));
                row.createCell(4).setCellValue(sdf.format(d.getDateFin()));
                row.createCell(5).setCellValue(String.valueOf(d.getEtat()));
                row.createCell(6).setCellValue(
                        d.getUpdatedBy() != null ? d.getUpdatedBy().getNomComplet() : "Non spécifié");
            }

            // Ajuster largeur colonnes
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(response.getOutputStream());
        }
    }
}
