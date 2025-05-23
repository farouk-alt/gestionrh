<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<c:set var="employe" value="${sessionScope.employe}" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block sidebar collapse animate__animated animate__fadeInLeft">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/admin/dashboard' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/dashboard">
                    <i class="bi bi-speedometer2 me-2"></i> <fmt:message key="sidebar.chef.dashboard" />
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/admin/employes') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/employes">
                    <i class="bi bi-people me-1"></i> <fmt:message key="sidebar.admin.employees" />
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath.startsWith('/admin/departements') ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/departements">
                    <i class="bi bi-building me-1"></i> <fmt:message key="sidebar.admin.departments" />
                </a>
            </li>
            <!-- ✅ Accès espace Chef uniquement si l’admin est chef actif -->
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/recherche-conge">
                    <i class="bi bi-search"></i> <fmt:message key="sidebar.admin.searchLeave" />
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link d-flex align-items-center position-relative" href="${pageContext.request.contextPath}/notifications">
                    <i class="bi bi-bell-fill me-2"></i> <fmt:message key="sidebar.notifications" />
                    <c:if test="${notifCount > 0}">
            <span class="badge bg-danger rounded-pill position-absolute top-0 start-100 translate-middle"
                  style="font-size: 0.75rem; transform: translate(-50%, 10%) scale(1.1); animation: pulse 1.2s infinite;">
                    ${notifCount}
            </span>
                    </c:if>
                </a>
            </li>




            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/admin/profil' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/admin/profil">
                    <i class="bi bi-person-circle me-1"></i> <fmt:message key="sidebar.profile" />
                </a>
            </li>

        </ul>
    </div>
</nav>
