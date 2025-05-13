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

        Long employeId = (Long) session.getAttribute("employeId");
        String role = (String) session.getAttribute("role");
        Employe employe = employeService.getEmployeById(employeId);

        // ✅ Si "Tout marquer comme lues"
        if ("true".equals(request.getParameter("markAll"))) {
            notificationService.marquerToutCommeLue(employe);
            response.sendRedirect(request.getContextPath() + "/notifications");
            return;
        }

        // ✅ Pagination
        int page = 1;
        int size = 5;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException ignored) {
            }
        }
        List<Notification> all = notificationService.getAllNotifications(employe);
        int totalPages = (int) Math.ceil((double) all.size() / size);
        int from = (page - 1) * size;
        int to = Math.min(from + size, all.size());
        List<Notification> paged = all.subList(from, to);

        request.setAttribute("notifications", paged);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("role", role);
        request.setAttribute("employe", employe);
        request.getRequestDispatcher("/views/notifications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("employeId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long employeId = (Long) session.getAttribute("employeId");
        Employe employe = employeService.getEmployeById(employeId);

        String idStr = request.getParameter("id");
        String markAll = request.getParameter("markAll");

        if ("true".equals(markAll)) {
            // ✅ Marquer toutes les notifications comme lues
            notificationService.marquerToutCommeLue(employe);
        } else if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                notificationService.marquerCommeLue(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect(request.getContextPath() + "/notifications");
    }
}
