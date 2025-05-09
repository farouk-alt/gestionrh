<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/views/chef/dashboard.jsp' ? 'active' : ''}" href="${pageContext.request.contextPath}/chef/dashboard">
                    <i class="bi bi-speedometer2 me-1"></i>
                    Tableau de bord
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/views/chef/conges') ? 'active' : ''}" href="${pageContext.request.contextPath}/chef/conges">
                    <i class="bi bi-calendar-check me-1"></i>
                    Gestion des congés
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/views/employe/conges') ? 'active' : ''}" href="${pageContext.request.contextPath}/employe/conges">
                    <i class="bi bi-calendar-plus me-1"></i>
                    Mes congés
                </a>
            </li>
        </ul>
    </div>
</nav>
