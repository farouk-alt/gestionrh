package controller.employe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Employe;
import service.EmployeService;
import util.PasswordUtil;

import java.io.IOException;

@WebServlet("/employe/profil")
public class EmployeProfilServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employe employe = (Employe) session.getAttribute("employe");

        if (employe == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Boolean updated = (Boolean) session.getAttribute("profilUpdated");
        if (updated != null && updated) {
            request.setAttribute("profilUpdated", true);
            session.removeAttribute("profilUpdated"); // pour affichage une seule fois
        }

        request.setAttribute("employe", employeService.getEmployeById(employe.getId()));

        request.getRequestDispatcher("/views/employe/profil.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Employe employe = (Employe) session.getAttribute("employe");

        if (employe == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String prenom = request.getParameter("prenom");
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String motDePasse = request.getParameter("password");

        employe.setPrenom(prenom);
        employe.setNom(nom);
        employe.setEmail(email);

        boolean motDePasseModifie = false;
        if (motDePasse != null && !motDePasse.trim().isEmpty()) {
            if (motDePasse.length() < 8) {
                request.setAttribute("error", "🔐 Le mot de passe doit contenir au moins 8 caractères.");
                request.setAttribute("employe", employe);
                request.getRequestDispatcher("/views/employe/profil.jsp").forward(request, response);
                return;
            }
            if (!motDePasse.equals(employe.getMotDePasse())) {
                employe.setMotDePasse(PasswordUtil.hashPassword(motDePasse));
                motDePasseModifie = true;
            }
        }

        employeService.updateEmploye(employe, motDePasseModifie);
        session.setAttribute("employe", employe);
        session.setAttribute("profilUpdated", true);
        response.sendRedirect(request.getContextPath() + "/employe/profil");
    }


}
