package controller.employe;

import java.io.IOException;
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

//@WebServlet("/employe/dashboard")
public class EmployeDashboardServlet extends HttpServlet {

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

        Employe employeConnecte = employeService.getEmployeById(employeId);

        request.setAttribute("employe", employeConnecte);
        request.setAttribute("totalDemandes", demandeCongeService.countAllDemandes(employeId));
        request.setAttribute("demandesEnAttente", demandeCongeService.countByEtat(employeId, "EN_ATTENTE"));
        request.setAttribute("demandesAcceptees", demandeCongeService.countByEtat(employeId, "ACCEPTE"));
        request.setAttribute("demandesRefusees", demandeCongeService.countByEtat(employeId, "REFUSE"));

        request.getRequestDispatcher("/views/employe/dashboard.jsp").forward(request, response);
    }
}
