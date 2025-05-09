<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-md-3 col-lg-2 me-0 px-3" href="${pageContext.request.contextPath}/">
        <i class="bi bi-building"></i> Gestion RH
    </a>
    <button class="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="w-100"></div>

    <div class="navbar-nav">
        <div class="nav-item text-nowrap d-flex align-items-center">
            <span class="nav-link px-3 text-white">
                <i class="bi bi-person-circle me-1"></i>
                <c:if test="${not empty sessionScope.employe}">
                    ${sessionScope.employe.nomComplet}
                </c:if>
            </span>
            <a class="nav-link px-3" href="${pageContext.request.contextPath}/logout">
                <i class="bi bi-box-arrow-right"></i> Déconnexion
            </a>
        </div>
    </div>
</header>

