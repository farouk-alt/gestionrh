package controller.admin;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.DemandeConge;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/admin/conges/export-pdf")
public class ExportPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<DemandeConge> resultats = (List<DemandeConge>) request.getSession().getAttribute("resultats");

        if (resultats == null || resultats.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/recherche-conge");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"resultats_conges.pdf\"");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            document.add(new Paragraph("📝 Résultats de la recherche des congés", titleFont));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2.5f, 2.5f, 3f, 2f, 2f, 1.5f, 3f});

            table.addCell(new PdfPCell(new Phrase("Employé", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Département", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Motif", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Date début", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Date fin", headerFont)));
            table.addCell(new PdfPCell(new Phrase("État", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Chef validateur", headerFont)));

            for (DemandeConge d : resultats) {
                table.addCell(new Phrase(d.getEmploye().getNomComplet(), bodyFont));
                table.addCell(new Phrase(d.getEmploye().getDepartement().getNom(), bodyFont));
                table.addCell(new Phrase(d.getMotif(), bodyFont));
                table.addCell(new Phrase(sdf.format(d.getDateDebut()), bodyFont));
                table.addCell(new Phrase(sdf.format(d.getDateFin()), bodyFont));
                table.addCell(new Phrase(String.valueOf(d.getEtat()), bodyFont));
                table.addCell(new Phrase(
                        d.getUpdatedBy() != null ? d.getUpdatedBy().getNomComplet() : "Non spécifié",
                        bodyFont));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
