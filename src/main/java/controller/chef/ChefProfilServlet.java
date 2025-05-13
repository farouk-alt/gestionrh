package controller.chef;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employe;
import service.EmployeService;
import util.PasswordUtil;

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

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        chef.setNom(nom);
        chef.setPrenom(prenom);
        chef.setEmail(email);

        boolean motDePasseModifie = false;
        if (motDePasse != null && !motDePasse.trim().isEmpty()) {
            if (motDePasse.length() < 8) {
                request.setAttribute("error", "🔐 Le mot de passe doit contenir au moins 8 caractères.");
                request.setAttribute("profil", chef);
                request.getRequestDispatcher("/views/chef/profile.jsp").forward(request, response);
                return;
            }
            if (!motDePasse.equals(chef.getMotDePasse())) {
                chef.setMotDePasse(PasswordUtil.hashPassword(motDePasse));
                motDePasseModifie = true;
            }
        }

        employeService.updateEmploye(chef, motDePasseModifie);
        request.getSession().setAttribute("employe", chef);
        request.setAttribute("profil", chef);
        request.setAttribute("success", "✅ Informations mises à jour avec succès.");
        request.getRequestDispatcher("/views/chef/profile.jsp").forward(request, response);
    }


}
