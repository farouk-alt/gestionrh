package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("employe") != null);
        boolean isLoginRequest = requestURI.equals(httpRequest.getContextPath() + "/login");
        boolean isResourceRequest = requestURI.contains("/assets/");
        boolean isInitRequest = requestURI.equals(httpRequest.getContextPath() + "/init"); // 👈

        if (isLoggedIn || isLoginRequest || isResourceRequest || isInitRequest) { // 👈 ajout isInitRequest
            if (isLoggedIn && !isResourceRequest) {
                String role = (String) session.getAttribute("role");

                if (requestURI.contains("/admin/") && !"ADMIN".equals(role)) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                    return;
                }

                if (requestURI.contains("/chef/") && !("CHEF".equals(role) || "ADMIN".equals(role))) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                    return;
                }
            }

            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }


    @Override
    public void destroy() {
    }
}
