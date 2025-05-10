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

public class ChefDashboardServlet extends HttpServlet {

    private final DemandeCongeService demandeCongeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Employe chef = (Employe) request.getSession().getAttribute("employe");

        Long chefDepartementId = chef.getDepartement().getId();

        int total = demandeCongeService.countAllByDepartement(chefDepartementId);
        int accepte = demandeCongeService.countByEtatAndDepartement("ACCEPTE", chefDepartementId);
        int refuse = demandeCongeService.countByEtatAndDepartement("REFUSE", chefDepartementId);
        int attente = demandeCongeService.countByEtatAndDepartement("EN_ATTENTE", chefDepartementId);

        request.setAttribute("total", total);
        request.setAttribute("acceptees", accepte);
        request.setAttribute("refusees", refuse);
        request.setAttribute("enAttente", attente);

        request.getRequestDispatcher("/views/chef/dashboard.jsp").forward(request, response);
    }
}
