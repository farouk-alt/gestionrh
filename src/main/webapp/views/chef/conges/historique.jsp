<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Historique des demandes - Chef</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
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

        #mainContent.dark-mode td {
            color: #000000 !important;
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

<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
                <jsp:include page="../../includes/chef-sidebar.jsp" />

        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
            <h2 class="mb-4">📚 Historique des demandes traitées</h2>

            <c:if test="${empty demandesTraitees}">
                <div class="alert alert-info">
                    Aucune demande traitée n'est disponible pour le moment.
                </div>
            </c:if>

            <c:if test="${not empty demandesTraitees}">
                <div class="table-responsive animate__animated animate__fadeInUp animate__delay-1s">
                    <table class="table table-striped table-hover">
                        <thead class="table-light">
                        <tr>
                            <th>Employé</th>
                            <th>Date début</th>
                            <th>Date fin</th>
                            <th>Motif</th>
                            <th>État</th>
                            <th>Dernière mise à jour</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="d" items="${demandesTraitees}">
                            <tr>
                                <td>${d.employe.nomComplet}</td>
                                <td><fmt:formatDate value="${d.dateDebut}" pattern="dd/MM/yyyy"/></td>
                                <td><fmt:formatDate value="${d.dateFin}" pattern="dd/MM/yyyy"/></td>
                                <td>${d.motif}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${d.etat == 'ACCEPTE'}">
                                            <span class="badge bg-success">Acceptée</span>
                                        </c:when>
                                        <c:when test="${d.etat == 'REFUSE'}">
                                            <span class="badge bg-danger">Refusée</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Inconnue</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><fmt:formatDate value="${d.dateMiseAjour}" pattern="dd/MM/yyyy"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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

</body>
</html>
