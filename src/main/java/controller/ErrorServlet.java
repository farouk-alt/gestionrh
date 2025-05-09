package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.ErrorHandler;

/**
 * Servlet qui gère les erreurs non capturées par les pages d'erreur
 */
//@WebServlet("/error")
public class ErrorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Récupérer les informations d'erreur
        Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String servletName = (String) request.getAttribute("jakarta.servlet.error.servlet_name");
        String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");

        // Journaliser l'erreur
        if (throwable != null) {
            ErrorHandler.logException(new Exception(throwable),
                    "Erreur dans " + (servletName != null ? servletName : "inconnu") +
                            " pour l'URI " + (requestUri != null ? requestUri : "inconnue"));
        }

        // Ajouter les informations d'erreur à la requête
        request.setAttribute("errorCode", statusCode);
        request.setAttribute("errorMessage", throwable != null ? throwable.getMessage() : "Erreur inconnue");
        request.setAttribute("errorTrace", throwable != null ? ErrorHandler.getStackTraceAsString(new Exception(throwable)) : "");
        request.setAttribute("errorServlet", servletName);
        request.setAttribute("errorUri", requestUri);

        // Rediriger vers la page d'erreur appropriée
        if (statusCode != null) {
            switch (statusCode) {
                case 404:
                    request.getRequestDispatcher("/views/errors/404.jsp").forward(request, response);
                    break;
                case 500:
                    request.getRequestDispatcher("/views/errors/500.jsp").forward(request, response);
                    break;
                default:
                    request.getRequestDispatcher("/views/errors/error.jsp").forward(request, response);
                    break;
            }
        } else {
            request.getRequestDispatcher("/views/errors/error.jsp").forward(request, response);
        }
    }
}
