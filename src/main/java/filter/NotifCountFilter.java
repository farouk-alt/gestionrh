package filter;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Employe;
import model.Notification;
import service.EmployeService;
import service.NotificationService;

import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class NotifCountFilter implements Filter {

    private final EmployeService employeService = new EmployeService();
    private final NotificationService notificationService = new NotificationService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        try {
            if (session != null && session.getAttribute("employeId") != null) {
                Long employeId = (Long) session.getAttribute("employeId");
                Employe employe = employeService.getEmployeById(employeId);
                if (employe != null) {
                    List<Notification> notifications = notificationService.getNonLues(employe);
                    request.setAttribute("notifCount", notifications.size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Éviter de bloquer l'accès même en cas d'erreur
        }

        chain.doFilter(request, response);
    }
}

