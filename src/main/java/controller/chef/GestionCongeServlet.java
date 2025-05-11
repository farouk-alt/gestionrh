// ✅ GestionCongeServlet.java – corrigée selon la nouvelle entité DemandeConge et service mis à jour

package controller.chef;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Chef;
import model.DemandeConge;
import service.ChefService;
import service.DemandeCongeService;

//@WebServlet("/chef/conges/*")
public class GestionCongeServlet extends HttpServlet {

    private final ChefService chefService = new ChefService();
    private final DemandeCongeService demandeCongeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Chef chef = chefService.findByEmployeId(employeId);

        if (chef == null || chef.getDepartement() == null) {
            response.sendRedirect(request.getContextPath() + "/employe/dashboard");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            Long departementId = chef.getDepartement().getId();
            List<DemandeConge> demandes = demandeCongeService.getDemandesEnAttenteParDepartement(departementId);
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/views/chef/conges/liste.jsp").forward(request, response);

        }else if (pathInfo.startsWith("/traiter/")) {
            try {
                Long demandeId = Long.parseLong(pathInfo.substring(9));
                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

                if (demande != null && demande.getEmploye().getDepartement().getId().equals(chef.getDepartement().getId())) {
                    request.setAttribute("demande", demande);

                    // ✅ Calculer le taux d’acceptation (pour la progress bar)
                    Long depId = chef.getDepartement().getId();
                    int totalDemandes = demandeCongeService.countAllByDepartement(depId);
                    int dejaAcceptees = demandeCongeService.countAcceptedStillActiveByDepartement(depId);
                    double taux = (totalDemandes > 0) ? (dejaAcceptees * 100.0 / totalDemandes) : 0.0;
                    int progressionAcceptation = demandeCongeService.calculerPourcentageAcceptationActuel(chef.getDepartement().getId());
                    request.setAttribute("progressionAcceptation", progressionAcceptation);


                    request.setAttribute("progressionAcceptation", taux);

                    // ✅ Envoyer à la page JSP
                    request.getRequestDispatcher("/views/chef/conges/traiter.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/chef/conges");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/chef/conges");
            }
        }
        else if (pathInfo.startsWith("/historique")) {
            Long departementId = chef.getDepartement().getId();
            List<DemandeConge> historiques = demandeCongeService.getDemandesTraiteesParDepartement(departementId);
            request.setAttribute("demandesTraitees", historiques);

            request.getRequestDispatcher("/views/chef/conges/historique.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/chef/conges");
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Chef chef = chefService.findByEmployeId(employeId);

        if (chef == null || chef.getDepartement() == null) {
            response.sendRedirect(request.getContextPath() + "/employe/dashboard");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.startsWith("/traiter/")) {
            try {
                Long demandeId = Long.parseLong(pathInfo.substring(9));
                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

                if (demande != null && demande.getEmploye().getDepartement().getId().equals(chef.getDepartement().getId())) {
                    String action = request.getParameter("action");

                    if ("approuver".equals(action)) {
                        Long depId = chef.getDepartement().getId();
                        int totalDemandes = demandeCongeService.countAllByDepartement(depId);
                        int dejaAcceptees = demandeCongeService.countByEtatAndDepartement("ACCEPTE", depId);

                        // Vérification du seuil 50%
                        if (totalDemandes > 0 && ((double) dejaAcceptees / totalDemandes) >= 0.5) {
                            request.setAttribute("erreur", "⚠️ Vous avez atteint la limite de 50% de demandes acceptées pour ce département.");
                            request.setAttribute("demande", demande);
                            request.getRequestDispatcher("/views/chef/conges/traiter.jsp").forward(request, response);
                            return;
                        }

                        demandeCongeService.approuverDemandeConge(demandeId, chef.getEmploye());

                    }
                    else if ("refuser".equals(action)) {
                        demandeCongeService.refuserDemandeConge(demandeId, chef.getEmploye());

                    }
                }

                response.sendRedirect(request.getContextPath() + "/chef/conges");

            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/chef/conges");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/chef/conges");
        }
    }
}
