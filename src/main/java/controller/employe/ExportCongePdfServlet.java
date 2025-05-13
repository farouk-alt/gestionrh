package controller.employe;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.DemandeConge;
import service.DemandeCongeService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

@WebServlet("/export/pdf")
public class ExportCongePdfServlet extends HttpServlet {

    private DemandeCongeService congeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Récupérer l'employé connecté
        HttpSession session = request.getSession(false);
        Long employeId = (Long) session.getAttribute("employeId");
        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. Récupérer les congés de cet employé
        List<DemandeConge> conges = congeService.getDemandesParEmploye(employeId);


        // 3. Configuration du PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=mes-demandes-conge.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Titre
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Mes demandes de congé", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Tableau
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);

            // En-têtes
            Stream.of("Date début", "Date fin", "Motif", "État", "Date mise à jour")
                    .forEach(header -> {
                        PdfPCell cell = new PdfPCell(new Phrase(header));
                        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        table.addCell(cell);
                    });
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (DemandeConge conge : conges) {
                table.addCell(conge.getDateDebut() != null ? sdf.format(conge.getDateDebut()) : "");
                table.addCell(conge.getDateFin() != null ? sdf.format(conge.getDateFin()) : "");
                table.addCell(conge.getMotif());

                String etatLisible;
                switch (conge.getEtat()) {
                    case EN_ATTENTE: etatLisible = "En attente"; break;
                    case ACCEPTE:    etatLisible = "Acceptée"; break;
                    case REFUSE:     etatLisible = "Refusée"; break;
                    default:         etatLisible = "Inconnu"; break;
                }
                table.addCell(etatLisible);

                table.addCell(conge.getDateMiseAjour() != null ? sdf.format(conge.getDateMiseAjour()) : "");
            }

            document.add(table);
            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ServletException("Erreur lors de la génération du PDF", e);
        }
    }
}
