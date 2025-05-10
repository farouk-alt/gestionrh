package controller;

import java.io.IOException;

import dao.ChefDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employe;
import service.EmployeService;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private final EmployeService employeService = new EmployeService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifier si l'utilisateur est déjà connecté
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("employe") != null) {
            // Rediriger vers la page d'accueil appropriée selon le rôle
            Employe employe = (Employe) session.getAttribute("employe");
            redirectBasedOnRole(employe,request, response);
        } else {
            // Afficher la page de connexion
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Authentifier l'utilisateur
        Employe employe = employeService.authenticate(email, password);

        if (employe != null) {

            // Vérifier si l'utilisateur se fait passer pour un chef alors qu'il ne l'est plus
            if ("CHEF".equals(employe.getRole()) && !employe.isChefActuel()) {
                request.setAttribute("error", "Vous n’êtes plus chef de département.");
                request.getRequestDispatcher("/views/login.jsp").forward(request, response);
                return;
            }

            // Créer une session
            HttpSession session = request.getSession();
            boolean estChef = new ChefDAO().isChefActuel(employe.getId());
            employe.setEstChefActuel(estChef);
            session.setAttribute("employe", employe);

            session.setAttribute("employeId", employe.getId());
            session.setAttribute("role", employe.getRole());

            redirectBasedOnRole(employe, request, response);
        }
        else {
            // Authentification échouée
            request.setAttribute("error", "Email ou mot de passe incorrect");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }
    
    private void redirectBasedOnRole(Employe employe,HttpServletRequest request, HttpServletResponse response) throws IOException {
        String role = employe.getRole();
        switch (role) {
            case "ADMIN":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            case "CHEF":
                response.sendRedirect(request.getContextPath() + "/chef/dashboard");
                break;
            case "EMPLOYE":
                response.sendRedirect(request.getContextPath() + "/employe/dashboard");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login");
                break;
        }
    }
}
