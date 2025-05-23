<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>--%>
<%--<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : 'fr'}" />--%>
<%--<fmt:setBundle basename="i18n.messages" />--%>

<%--<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">--%>

<%--<header class="navbar navbar-expand-lg navbar-dark bg-dark shadow sticky-top">--%>
<%--    <div class="container-fluid">--%>

<%--        <!-- Logo -->--%>
<%--        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">--%>
<%--            <i class="bi bi-building fs-4 me-2"></i>--%>
<%--            <span class="fw-bold fs-5"><fmt:message key="app.title"/></span>--%>

<%--        </a>--%>

<%--        <!-- Mobile toggle button -->--%>
<%--        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarRH" aria-controls="navbarRH" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--            <span class="navbar-toggler-icon"></span>--%>
<%--        </button>--%>

<%--        <!-- Right menu -->--%>
<%--        <div class="collapse navbar-collapse justify-content-end" id="navbarRH">--%>
<%--            <ul class="navbar-nav mb-2 mb-lg-0 align-items-center">--%>

<%--                <!-- Nom de l'utilisateur -->--%>
<%--                <li class="nav-item me-2 text-white d-none d-md-inline">--%>
<%--                    <i class="bi bi-person-circle me-1"></i>--%>
<%--                    <c:if test="${not empty sessionScope.employe}">--%>
<%--                        ${sessionScope.employe.nomComplet}--%>
<%--                    </c:if>--%>
<%--                </li>--%>

<%--                <!-- Bouton Dark Mode -->--%>
<%--                <li class="nav-item me-2">--%>
<%--                    <button id="toggleDarkMode" class="btn btn-outline-light btn-sm" title="Mode sombre / clair">--%>
<%--                        <i id="themeIcon" class="bi bi-moon-fill"></i>--%>

<%--                    </button>--%>
<%--                </li>--%>

<%--                <form method="get" action="${pageContext.request.contextPath}/change-lang" class="d-inline ms-3">--%>
<%--                    <select name="lang" onchange="this.form.submit()" class="form-select form-select-sm" style="width: auto; display: inline-block;">--%>
<%--                        <option value="fr" ${sessionScope.lang == 'fr' ? 'selected' : ''}>Français</option>--%>
<%--                        <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>--%>
<%--                        <option value="ar" ${sessionScope.lang == 'ar' ? 'selected' : ''}>العربية</option>--%>
<%--                    </select>--%>
<%--                </form>--%>


<%--                <!-- Déconnexion -->--%>
<%--                <li class="nav-item">--%>
<%--                    <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/logout">--%>
<%--                        <i class="bi bi-box-arrow-right"></i> Déconnexion--%>
<%--                    </a>--%>
<%--                </li>--%>
<%--            </ul>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</header>--%>
<%--<!-- TOGGLE DARK MODE -->--%>
<%--<script>--%>
<%--    document.addEventListener("DOMContentLoaded", () => {--%>
<%--        const toggleBtn = document.getElementById("toggleDarkMode");--%>
<%--        const themeIcon = document.getElementById("themeIcon");--%>
<%--        const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;--%>
<%--        const currentMode = localStorage.getItem("theme");--%>

<%--        function applyTheme(mode) {--%>
<%--            if (mode === "dark") {--%>
<%--                document.body.classList.add("dark-mode");--%>
<%--                themeIcon.className = "bi bi-sun-fill";--%>
<%--            } else {--%>
<%--                document.body.classList.remove("dark-mode");--%>
<%--                themeIcon.className = "bi bi-moon-fill";--%>
<%--            }--%>
<%--            localStorage.setItem("theme", mode);--%>
<%--        }--%>

<%--        if (currentMode === "dark" || (prefersDark && !currentMode)) {--%>
<%--            applyTheme("dark");--%>
<%--        } else {--%>
<%--            applyTheme("light");--%>
<%--        }--%>

<%--        toggleBtn.addEventListener("click", () => {--%>
<%--            const isDark = document.body.classList.contains("dark-mode");--%>
<%--            applyTheme(isDark ? "light" : "dark");--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />

<!-- ✅ Bootstrap + Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet" />
<style>
    /* Style pour le header */
    .navbar {
        background-color: #fff;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .navbar-brand {
        font-size: 1.5rem;
        color: #333;
    }

    .navbar-nav .nav-item {
        margin-left: 1rem;
    }

    .navbar-nav .nav-link {
        color: #333;
    }

    .navbar-nav .nav-link:hover {
        color: #007bff;
    }
    img{
        width: 40px;
        height: 40px;
    }
</style>

<!-- ✅ HEADER moderne -->
<header class="navbar navbar-expand-lg bg-white shadow-sm fixed-top border-bottom">
    <div class="container-fluid px-4">
        <a class="navbar-brand d-flex align-items-center fw-bold text-dark" href="${pageContext.request.contextPath}/">
            <i class="bi bi-building me-2 fs-4"></i>
<%--    <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="Logo" class="mb-2">--%>
    <fmt:message key="app.title" />
        </a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar"
                aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-end" id="mainNavbar">
            <ul class="navbar-nav align-items-center gap-2">

                <!-- Nom utilisateur -->
                <c:if test="${not empty sessionScope.employe}">
                    <li class="nav-item text-dark d-none d-md-inline">
                        <i class="bi bi-person-circle me-1"></i>
                            ${sessionScope.employe.nomComplet}
                    </li>
                </c:if>

                <!-- 🌙 Mode sombre -->
                <li class="nav-item">
                    <button id="toggleDarkMode" class="btn btn-outline-secondary btn-sm" title="Mode sombre / clair">
                        <i id="themeIcon" class="bi bi-moon-fill"></i>
                    </button>
                </li>

                <!-- 🌐 Langue -->
                <li class="nav-item">
                    <form method="get" action="${pageContext.request.contextPath}/change-lang" class="d-inline">
                        <select name="lang" onchange="this.form.submit()" class="form-select form-select-sm">
                            <option value="fr" ${sessionScope.lang == 'fr' ? 'selected' : ''}>Français</option>
                            <option value="en" ${sessionScope.lang == 'en' ? 'selected' : ''}>English</option>
                        </select>
                    </form>
                </li>

                <!-- 🔒 Déconnexion -->
                <li class="nav-item">
                    <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right me-1"></i>
                        <fmt:message key="header.logout" />
                    </a>
                </li>
            </ul>
        </div>
    </div>
</header>

<!-- ✅ Style local intégré -->
<style>
    body {
        padding-top: 70px; /* espace pour header fixed */
    }

    body.dark-mode {
        background-color: #1e1e1e;
        color: #f1f1f1;
    }

    body.dark-mode .navbar {
        background-color: #2c2c2c !important;
        color: #f1f1f1;
    }

    body.dark-mode .navbar a,
    body.dark-mode .navbar li,
    body.dark-mode .navbar button {
        color: #fff !important;
    }
    body.dark-mode select,
    body.dark-mode select option {
        background-color: #2c2c2c !important;
        color: #ffffff !important;
    }
    body.dark-mode select {
        border: 1px solid #888 !important;
    }


    .form-select-sm {
        min-width: 110px;
    }
</style>

<!-- ✅ Script toggle dark mode -->
<script>
    document.addEventListener("DOMContentLoaded", () => {
        const toggleBtn = document.getElementById("toggleDarkMode");
        const themeIcon = document.getElementById("themeIcon");
        const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
        const currentMode = localStorage.getItem("theme");

        function applyTheme(mode) {
            document.body.classList.toggle("dark-mode", mode === "dark");
            themeIcon.className = mode === "dark" ? "bi bi-sun-fill" : "bi bi-moon-fill";
            localStorage.setItem("theme", mode);
        }

        applyTheme(currentMode === "dark" || (prefersDark && !currentMode) ? "dark" : "light");

        toggleBtn.addEventListener("click", () => {
            const isDark = document.body.classList.contains("dark-mode");
            applyTheme(isDark ? "light" : "dark");
        });
    });
</script>
