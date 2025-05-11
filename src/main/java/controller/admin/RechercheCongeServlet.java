package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Chef;
import model.DemandeConge;
import model.Departement;
import service.DemandeCongeService;
import service.DepartementService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/recherche-conge")
public class RechercheCongeServlet extends HttpServlet {

    private final DemandeCongeService demandeCongeService = new DemandeCongeService();
    private final DepartementService departementService = new DepartementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ✅ Charger la liste des départements
        List<Departement> departements = departementService.getAllDepartements();
        request.setAttribute("departements", departements);

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

        // ✅ Trimmer les champs pour éviter les espaces vides et uniformiser la casse
        if (nom != null) nom = nom.trim().toLowerCase();
        if (prenom != null) prenom = prenom.trim().toLowerCase();
        if (departement != null) departement = departement.trim();

        Date dateMaj = null;
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                dateMaj = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            }
        } catch (Exception e) {
            request.setAttribute("erreur", "Format de date invalide.");
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
            return;
        }

        // ✅ VALIDATION DES CHAMPS
        if (nom == null || nom.isEmpty() ||
                prenom == null || prenom.isEmpty() ||
                departement == null || departement.isEmpty() ||
                dateMaj == null) {

            request.setAttribute("erreur", "Veuillez remplir tous les champs requis.");
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
            return;
        }

        // ✅ Appel au service si tout est OK
        List<DemandeConge> resultats = demandeCongeService.rechercherParCriteres(nom, prenom, departement, dateMaj);
        request.setAttribute("resultats", resultats);
        request.setAttribute("departements", departementService.getAllDepartements());
        request.getSession().setAttribute("resultats", resultats);
        request.getRequestDispatcher("/views/admin/conges/recherche.jsp").forward(request, response);
    }
}
