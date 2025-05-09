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
import model.Employe;
import service.ChefService;
import service.DemandeCongeService;
import service.EmployeService;

//@WebServlet("/chef/dashboard")
public class ChefDashboardServlet extends HttpServlet {
    
    private final ChefService chefService = new ChefService();
    private final EmployeService employeService = new EmployeService();
    private final DemandeCongeService demandeCongeService = new DemandeCongeService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer l'employé connecté
        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");
        
        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        // Vérifier si l'employé est bien un chef
        Chef chef = chefService.findByEmployeId(employeId);
        
        if (chef == null || chef.getDepartement() == null) {
            response.sendRedirect(request.getContextPath() + "/employe/dashboard");
            return;
        }
        
        // Récupérer les données pour le tableau de bord
        Long departementId = chef.getDepartement().getId();
        List<Employe> employes = employeService.getEmployesByDepartement(departementId);
        List<DemandeConge> demandesEnAttente = demandeCongeService.getDemandesEnAttenteParDepartement(departementId);
        
        // Ajouter les données à la requête
        request.setAttribute("chef", chef);
        request.setAttribute("departement", chef.getDepartement());
        request.setAttribute("employes", employes);
        request.setAttribute("demandesEnAttente", demandesEnAttente);
        
        // Afficher le tableau de bord
        request.getRequestDispatcher("/views/chef/dashboard.jsp").forward(request, response);
    }
}
