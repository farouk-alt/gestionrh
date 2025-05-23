package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer la session et la détruire
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Rediriger vers la page de connexion
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
