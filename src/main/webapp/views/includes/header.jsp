<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

<header class="navbar navbar-expand-lg navbar-dark bg-dark shadow sticky-top">
    <div class="container-fluid">

        <!-- Logo -->
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
            <i class="bi bi-building fs-4 me-2"></i>
            <span class="fw-bold fs-5">Gestion RH</span>
        </a>

        <!-- Mobile toggle button -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarRH" aria-controls="navbarRH" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Right menu -->
        <div class="collapse navbar-collapse justify-content-end" id="navbarRH">
            <ul class="navbar-nav mb-2 mb-lg-0 align-items-center">

                <!-- Nom de l'utilisateur -->
                <li class="nav-item me-2 text-white d-none d-md-inline">
                    <i class="bi bi-person-circle me-1"></i>
                    <c:if test="${not empty sessionScope.employe}">
                        ${sessionScope.employe.nomComplet}
                    </c:if>
                </li>

                <!-- Bouton Dark Mode -->
                <li class="nav-item me-2">
                    <button id="toggleDarkMode" class="btn btn-outline-light btn-sm" title="Mode sombre / clair">
                        <i id="themeIcon" class="bi bi-moon-fill"></i>

                    </button>
                </li>

                <!-- Déconnexion -->
                <li class="nav-item">
                    <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right"></i> Déconnexion
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>
<!-- TOGGLE DARK MODE -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const toggleBtn = document.getElementById("toggleDarkMode");
        const themeIcon = document.getElementById("themeIcon");
        const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        const currentMode = localStorage.getItem("theme");

        function applyTheme(mode) {
            if (mode === "dark") {
                document.body.classList.add("dark-mode");
                themeIcon.className = "bi bi-sun-fill";
            } else {
                document.body.classList.remove("dark-mode");
                themeIcon.className = "bi bi-moon-fill";
            }
            localStorage.setItem("theme", mode);
        }

        if (currentMode === "dark" || (prefersDark && !currentMode)) {
            applyTheme("dark");
        } else {
            applyTheme("light");
        }

        toggleBtn.addEventListener("click", () => {
            const isDark = document.body.classList.contains("dark-mode");
            applyTheme(isDark ? "light" : "dark");
        });
    });
</script>
