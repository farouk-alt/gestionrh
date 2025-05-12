package controller.Notification;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Employe;
import model.Notification;
import service.EmployeService;
import service.NotificationService;

import java.io.IOException;
import java.util.List;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();
    private final NotificationService notificationService = new NotificationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("employeId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // ✅ Récupération des infos de session
        Long employeId = (Long) session.getAttribute("employeId");
        String role = (String) session.getAttribute("role");

        // ✅ Employé connecté
        Employe employe = employeService.getEmployeById(employeId);

        // ✅ Notifications pour cet employé
        List<Notification> notifications = notificationService.getAllNotifications(employe);

        // ✅ Transmission aux JSP
        request.setAttribute("employe", employe); // pour l'affichage
        request.setAttribute("notifications", notifications);
        request.setAttribute("role", role);       // pour inclure la bonne sidebar

        // ✅ Redirection vers la page JSP
        request.getRequestDispatcher("/views/notifications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");

        if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                notificationService.marquerCommeLue(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // ✅ Recharge la page après l'action
        response.sendRedirect(request.getContextPath() + "/notifications");
    }
}
