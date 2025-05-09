package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chef;
import model.DemandeConge;
import service.DemandeCongeService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/recherche-conge")
public class RechercheCongeServlet extends HttpServlet {

    private final DemandeCongeService demandeCongeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Affichage du formulaire de recherche
        request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomEmploye = request.getParameter("nomEmploye");
        String nomDepartement = request.getParameter("nomDepartement");
        String dateStr = request.getParameter("dateMiseAJour");
        Date dateMiseAJour = null;

        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateMiseAJour = sdf.parse(dateStr);
            }
        } catch (Exception ignored) {
        }

        List<DemandeConge> resultats = demandeCongeService.rechercherParCriteres(nomEmploye, nomDepartement, dateStr, dateMiseAJour);
       request.setAttribute("resultats", resultats);
        request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
    }
}
