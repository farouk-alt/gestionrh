package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/change-lang")
public class ChangeLangServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();
        if (lang != null && (lang.equals("fr") || lang.equals("en"))) {
            session.setAttribute("lang", lang);
        }
        String referer = request.getHeader("referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath());
    }
}

