<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block sidebar collapse animate__animated animate__fadeInLeft">

    <div class="position-sticky pt-3">
        <ul class="nav flex-column">

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/dashboard' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/dashboard">
                    <i class="bi bi-speedometer2 me-1"></i><fmt:message key="sidebar.chef.dashboard" />
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/conges' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/conges">
                    <i class="bi bi-check-square me-1"></i> <fmt:message key="sidebar.chef.toValidate" />
                </a>
            </li>

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/conges/historique' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/conges/historique">
                    <i class="bi bi-archive me-1"></i> <fmt:message key="sidebar.chef.history" />
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/employes' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/employes">
                    <i class="bi bi-people me-1"></i> <fmt:message key="sidebar.chef.employees" />
                </a>
            </li>
            <li class="nav-item position-relative">
                <a class="nav-link d-flex align-items-center" href="${pageContext.request.contextPath}/notifications">
                    <i class="bi bi-bell-fill me-2"></i> <fmt:message key="sidebar.notifications" />
                    <c:if test="${notifCount > 0}">
            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger animate__animated animate__bounceIn">
                    ${notifCount}
            </span>
                    </c:if>
                </a>
            </li>



            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/chef/profil' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/chef/profil">
                    <i class="bi bi-person-circle me-1"></i> <fmt:message key="sidebar.profile" />
                </a>
            </li>

        </ul>
    </div>
</nav>
