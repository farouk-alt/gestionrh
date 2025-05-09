package controller.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Departement;
import model.Employe;
import service.DepartementService;
import service.EmployeService;

//@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    
    private final EmployeService employeService = new EmployeService();
    private final DepartementService departementService = new DepartementService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer les données pour le tableau de bord
        List<Employe> employes = employeService.getAllEmployes();
        List<Departement> departements = departementService.getAllDepartements();
        
        // Ajouter les données à la requête
        request.setAttribute("employes", employes);
        request.setAttribute("departements", departements);
        
        // Afficher le tableau de bord
        request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
    }
}
