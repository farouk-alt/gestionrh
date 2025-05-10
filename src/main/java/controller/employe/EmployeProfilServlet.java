package controller.employe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Employe;
import service.EmployeService;

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
        String password = request.getParameter("password");

        employe.setPrenom(prenom);
        employe.setNom(nom);
        employe.setEmail(email);

        if (password != null && !password.trim().isEmpty()) {
            employe.setMotDePasse(password); // Ajoutez un hachage ici si nécessaire
        }

        employeService.updateEmploye(employe);
        session.setAttribute("employe", employe); // Met à jour la session

        session.setAttribute("profilUpdated", true);  // après update
        response.sendRedirect(request.getContextPath() + "/employe/profil");

    }
}
