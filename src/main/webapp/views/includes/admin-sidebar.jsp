<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="employe" value="${sessionScope.employe}" />

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/admin/dashboard' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/dashboard">
                    <i class="bi bi-speedometer2 me-1"></i> Tableau de bord
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/admin/employes') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/employes">
                    <i class="bi bi-people me-1"></i> Gestion des employés
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/admin/departements') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/departements">
                    <i class="bi bi-building me-1"></i> Gestion des départements
                </a>
            </li>
            <!-- ✅ Accès espace Chef uniquement si l’admin est chef actif -->
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/conges/historique' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/conges/historique">
                    <i class="bi bi-archive me-1"></i> Historique traitées
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/admin/profil' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/profil">
                    <i class="bi bi-person-circle me-1"></i> Mon Profil
                </a>
            </li>

        </ul>
    </div>
</nav>
