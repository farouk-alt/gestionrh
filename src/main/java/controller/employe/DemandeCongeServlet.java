// ✅ DemandeCongeServlet.java – corrigée selon l'entité et le service DemandeConge

package controller.employe;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DemandeConge;
import model.Employe;
import service.DemandeCongeService;
import service.EmployeService;

//@WebServlet("/employe/conges/*")
public class DemandeCongeServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();
    private final DemandeCongeService demandeCongeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<DemandeConge> demandes = demandeCongeService.getDemandesParEmploye(employeId);
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/views/employe/conges/liste.jsp").forward(request, response);

        } else if (pathInfo.equals("/demander")) {
            Employe employe = employeService.getEmployeById(employeId);
            request.setAttribute("employe", employe);
            request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);

        } else if (pathInfo.startsWith("/voir/")) {
            try {
                Long demandeId = Long.parseLong(pathInfo.substring(6));
                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

                if (demande != null && demande.getEmploye().getId().equals(employeId)) {
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/details.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/employe/conges");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/employe/conges");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/employe/conges");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/demander")) {
            String dateDebutStr = request.getParameter("dateDebut");
            String dateFinStr = request.getParameter("dateFin");
            String motif = request.getParameter("motif");

            if (dateDebutStr == null || dateFinStr == null || motif == null) {
                request.setAttribute("error", "Tous les champs sont obligatoires");
                request.setAttribute("employe", employeService.getEmployeById(employeId));
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                return;
            }

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateDebut = sdf.parse(dateDebutStr);
                Date dateFin = sdf.parse(dateFinStr);

                if (dateDebut.after(dateFin)) {
                    request.setAttribute("error", "La date de début doit être antérieure à la date de fin");
                    request.setAttribute("employe", employeService.getEmployeById(employeId));
                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                    return;
                }

                boolean success = demandeCongeService.creerDemandeConge(employeId, dateDebut, dateFin, motif);

                if (success) {
                    response.sendRedirect(request.getContextPath() + "/employe/conges");
                } else {
                    request.setAttribute("error", "Solde de congé insuffisant");
                    request.setAttribute("employe", employeService.getEmployeById(employeId));
                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                }

            } catch (ParseException e) {
                request.setAttribute("error", "Format de date invalide");
                request.setAttribute("employe", employeService.getEmployeById(employeId));
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/employe/conges");
        }
    }
}
