<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord - Administration</title>

    <!-- Bootstrap & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>

    <!-- Ton fichier principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- CSS spécifique au dark mode de mainContent -->
    <style>
        #mainContent.dark-mode {
            background-color: #121212;
            color: #ffffff;
        }

        #mainContent.dark-mode h1,
        #mainContent.dark-mode h2,
        #mainContent.dark-mode h3,
        #mainContent.dark-mode p,
        #mainContent.dark-mode td,
        #mainContent.dark-mode th,
        #mainContent.dark-mode label,
        #mainContent.dark-mode .card-title,
        #mainContent.dark-mode .card-body,
        #mainContent.dark-mode .card-header,
        #mainContent.dark-mode .card-footer {
            color: inherit !important;
        }

        #mainContent.dark-mode a {
            color: #66b2ff;
        }

        #mainContent.dark-mode .btn-primary {
            background-color: #0d6efd;
            border-color: #0d6efd;
            color: #fff;
        }

        #mainContent.dark-mode .btn-outline-success {
            border-color: #198754;
            color: #198754;
        }

        #mainContent {
            transition: background-color 0.3s ease, color 0.3s ease;
        }
    </style>
</head>
<body>

<jsp:include page="../includes/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/admin-sidebar.jsp" />

        <!-- ✅ ID ajouté ici -->
        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2"><fmt:message key="admin.dashboard.title"/></h1>
            </div>

            <!-- ✅ Statistiques des employés et départements -->
            <div class="row">
                <div class="col-md-6 col-lg-3 mb-4">
                    <div class="card text-white bg-primary shadow-sm h-100 animate__animated animate__fadeInUp">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <h6 class="card-title text-white"><fmt:message key="admin.dashboard.employes"/></h6>
                                    <h2 class="display-4 mt-2 mb-0">${employes.size()}</h2>
                                </div>
                                <i class="bi bi-people-fill fs-1"></i>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="${pageContext.request.contextPath}/admin/employes" class="text-white">Voir détails <i class="bi bi-chevron-right"></i></a>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-3 mb-4">
                    <div class="card text-white bg-success shadow-sm h-100 animate__animated animate__fadeInUp animate__delay-1s">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <h6 class="card-title text-white"><fmt:message key="admin.dashboard.departements"/></h6>
                                    <h2 class="display-4 mt-2 mb-0">${departements.size()}</h2>
                                </div>
                                <i class="bi bi-building fs-1"></i>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="${pageContext.request.contextPath}/admin/departements" class="text-white"><fmt:message key="admin.dashboard.voir_details"/> <i class="bi bi-chevron-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ✅ Derniers employés -->
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card shadow-sm animate__animated animate__fadeInUp animate__delay-2s">
                        <div class="card-header bg-white">
                            <h5 class="card-title mb-0"><fmt:message key="admin.dashboard.latest_employes"/></h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th><fmt:message key="admin.dashboard.latest_employes.nom"/></th>
                                    <th><fmt:message key="admin.dashboard.latest_employes.email"/></th>
                                    <th><fmt:message key="admin.dashboard.latest_employes.departement"/></th>
                                    <th><fmt:message key="admin.dashboard.latest_employes.date"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="employe" items="${employes}" end="4">
                                    <tr>
                                        <td>${employe.nomComplet}</td>
                                        <td>${employe.email}</td>
                                        <td>${employe.departement.nom}</td>
                                        <td><fmt:formatDate value="${employe.dateCreation}" pattern="dd/MM/yyyy" /></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- ✅ Départements -->
                <div class="col-lg-6 mb-4">
                    <div class="card shadow-sm animate__animated animate__fadeInUp animate__delay-3s">
                        <div class="card-header bg-white">
                            <h5 class="card-title mb-0"><fmt:message key="admin.dashboard.departements_table"/></h5>
                        </div>
                        <div class="card-body">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th><fmt:message key="admin.dashboard.departements.nom"/></th>
                                    <th><fmt:message key="admin.dashboard.departements.chef"/></th>
                                    <th><fmt:message key="admin.dashboard.departements.nb_employes"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="departement" items="${departements}" end="4">
                                    <tr>
                                        <td>${departement.nom}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${departement.chef != null}">
                                                    ${departement.chef.employe.nomComplet}
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted"><fmt:message key="admin.dashboard.departements.chef_non_assigne"/></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${departement.employes.size()}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- ✅ Espace chef -->
            <c:if test="${employe.estChefActuel}">
                <div class="card mt-4 shadow-sm border border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success"><fmt:message key="admin.dashboard.espace_chef.title"/></h5>
                        <p><fmt:message key="admin.dashboard.espace_chef.text"/></p>
                        <a href="${pageContext.request.contextPath}/chef/dashboard" class="btn btn-outline-success me-2">
                            <i class="bi bi-diagram-3"></i> <fmt:message key="admin.dashboard.espace_chef.dashboard"/>
                        </a>
                        <a href="${pageContext.request.contextPath}/chef/profil" class="btn btn-outline-success">
                            <i class="bi bi-person-circle"></i> <fmt:message key="admin.dashboard.espace_chef.profil"/>
                        </a>
                    </div>
                </div>
            </c:if>
        </main>
    </div>
</div>


<!-- ✅ Script mode sombre appliqué uniquement au main -->
<script>
    const main = document.getElementById("mainContent");
    const themeIcon = document.getElementById("themeIcon");
    const toggleBtn = document.getElementById("toggleDarkMode");

    const applyTheme = (mode) => {
        if (!main) return;
        main.classList.toggle("dark-mode", mode === "dark");
        if (themeIcon) themeIcon.className = mode === "dark" ? "bi bi-sun-fill" : "bi bi-moon-fill";
        localStorage.setItem("theme", mode);
    };

    const currentMode = localStorage.getItem("theme");
    const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches;
    applyTheme(currentMode === "dark" || (prefersDark && !currentMode) ? "dark" : "light");

    if (toggleBtn) {
        toggleBtn.addEventListener("click", () => {
            const isDark = main.classList.contains("dark-mode");
            applyTheme(isDark ? "light" : "dark");
        });
    }
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
