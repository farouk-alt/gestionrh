package controller.chef;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employe;
import service.EmployeService;

import java.io.IOException;

public class ChefProfilServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Employe sessionChef = (Employe) request.getSession().getAttribute("employe");

        if (sessionChef == null || !sessionChef.isChefActuel()) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 🔁 Recharge à partir de la base avec historiqueChef initialisé (JOIN FETCH)
        Employe chef = employeService.getEmployeByIdWithHistoriqueChef(sessionChef.getId());
        request.setAttribute("profil", chef);

        request.getRequestDispatcher("/views/chef/profile.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Employe chef = (Employe) request.getSession().getAttribute("employe");

        String nom = request.getParameter("nom").trim();
        String prenom = request.getParameter("prenom").trim();
        String email = request.getParameter("email").trim();
        String motDePasse = request.getParameter("motDePasse").trim();

        chef.setNom(nom);
        chef.setPrenom(prenom);
        chef.setEmail(email);
        if (!motDePasse.isEmpty()) {
            chef.setMotDePasse(motDePasse); // hash si nécessaire
        }

        employeService.updateEmploye(chef);
        request.getSession().setAttribute("employe", chef);

        request.setAttribute("profil", chef);
        request.setAttribute("success", "Informations mises à jour avec succès.");
        request.getRequestDispatcher("/views/chef/profile.jsp").forward(request, response);
    }
}
