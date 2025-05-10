package controller.employe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.DemandeConge;
import model.Employe;
import service.DemandeCongeService;

import java.io.IOException;
import java.util.List;

@WebServlet("/employe/mes-conges")
public class EmployeCongeServlet extends HttpServlet {

    private final DemandeCongeService demandeCongeService = new DemandeCongeService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Employe employe = (Employe) session.getAttribute("employe");

        if (employe == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        List<DemandeConge> mesConges = demandeCongeService.getDemandesParEmploye(employe.getId());

        request.setAttribute("conges", mesConges);
        request.getRequestDispatcher("/views/employe/conges/conges.jsp").forward(request, response);
    }
}
