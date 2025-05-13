package controller.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Employe;
import service.EmployeService;
import util.PasswordUtil;

import java.io.IOException;

public class AdminProfilServlet extends HttpServlet {
    private final EmployeService employeService = new EmployeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employe admin = (Employe) request.getSession().getAttribute("employe"); // récupère depuis la session
        request.setAttribute("admin", admin);
        request.getRequestDispatcher("/views/admin/profil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Employe admin = (Employe) request.getSession().getAttribute("employe");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        admin.setNom(nom);
        admin.setPrenom(prenom);
        admin.setEmail(email);

        boolean motDePasseModifie = false;
        if (motDePasse != null && !motDePasse.trim().isEmpty()) {
            if (motDePasse.length() < 8) {
                request.setAttribute("error", "🔐 Le mot de passe doit contenir au moins 8 caractères.");
                request.setAttribute("admin", admin);
                request.getRequestDispatcher("/views/admin/profil.jsp").forward(request, response);
                return;
            }
            if (!motDePasse.equals(admin.getMotDePasse())) {
                admin.setMotDePasse(PasswordUtil.hashPassword(motDePasse));
                motDePasseModifie = true;
            }
        }

        employeService.updateEmploye(admin, motDePasseModifie);
        request.getSession().setAttribute("employe", admin);
        request.setAttribute("success", "✅ Profil mis à jour avec succès");
        request.getRequestDispatcher("/views/admin/profil.jsp").forward(request, response);
    }


}

