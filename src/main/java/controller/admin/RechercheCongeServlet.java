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

        String nom = request.getParameter("nomEmploye");
        String prenom = request.getParameter("prenomEmploye");
        String departement = request.getParameter("nomDepartement");
        String dateStr = request.getParameter("dateMiseAJour");

// ✅ Trimmer les champs pour éviter les espaces vides
        if (nom != null) nom = nom.trim();
        if (prenom != null) prenom = prenom.trim();
        if (departement != null) departement = departement.trim();


        Date dateMaj = null;
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                dateMaj = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            }
        } catch (Exception e) {
            request.setAttribute("erreur", "Format de date invalide.");
            request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
            return;
        }

        // ✅ VALIDATION DES CHAMPS
        if (nom == null || nom.isEmpty() ||
                prenom == null || prenom.isEmpty() ||
                departement == null || departement.isEmpty() ||
                dateMaj == null) {

            request.setAttribute("erreur", "Veuillez remplir tous les champs requis.");
            request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
            return;
        }

        // ✅ Appel au service si tout est OK
        List<DemandeConge> resultats = demandeCongeService.rechercherParCriteres(nom, prenom, departement, dateMaj);
        request.setAttribute("resultats", resultats);
        request.getSession().setAttribute("resultats", resultats); // 📌 ICI
        request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
    }


}
