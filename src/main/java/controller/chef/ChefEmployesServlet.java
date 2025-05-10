package controller.chef;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Chef;
import model.Employe;
import service.ChefService;
import service.EmployeService;

import java.io.IOException;
import java.util.List;

@WebServlet("/chef/employes")
public class ChefEmployesServlet extends HttpServlet {

    private final ChefService chefService = new ChefService();
    private final EmployeService employeService = new EmployeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Chef chef = chefService.findByEmployeId(employeId);

        if (chef == null || chef.getDepartement() == null) {
            response.sendRedirect(request.getContextPath() + "/chef/dashboard");
            return;
        }

        Long departementId = chef.getDepartement().getId();
        List<Employe> employes = employeService.getEmployesByDepartement(departementId);

        // ✅ Injecter l’information "est-ce un chef actif ?" dans chaque employé
        for (Employe emp : employes) {
            boolean estChef = chefService.isChef(emp.getId());
            emp.setEstChefActuel(estChef);  // à ajouter dans le modèle Employe
        }

        request.setAttribute("employes", employes);
        request.setAttribute("nomDepartement", chef.getDepartement().getNom());
        request.getRequestDispatcher("/views/chef/employes/liste.jsp").forward(request, response);
    }
}

