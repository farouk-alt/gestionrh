<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des départements - Gestion RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>
    <style>
        #mainContent.dark-mode {
            background-color: #121212;
            color: #ffffff;
        }
        #mainContent.dark-mode h1, #mainContent.dark-mode h2,
        #mainContent.dark-mode th, #mainContent.dark-mode td,
        #mainContent.dark-mode .card-title, #mainContent.dark-mode .card-body,
        #mainContent.dark-mode label {
            color: inherit !important;
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
        <jsp:include page="../../includes/admin-sidebar.jsp" />

        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Gestion des départements</h1>
                <a href="${pageContext.request.contextPath}/admin/departements/ajouter" class="btn btn-primary">
                    <i class="bi bi-plus-circle me-1"></i> Nouveau département
                </a>
            </div>

            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sessionScope.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="success" scope="session" />
            </c:if>

            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${sessionScope.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="error" scope="session" />
            </c:if>

            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h5 class="card-title mb-0">Liste des départements</h5>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="depsTable" class="table table-hover table-striped table-sm">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nom</th>
                                <th>Chef</th>
                                <th class="text-end">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${empty departements}">
                                    <tr><td colspan="5" class="text-center">Aucun département trouvé</td></tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="dep" items="${departements}">
                                        <tr>
                                            <td>${dep.id}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/admin/departements/details/${dep.id}" class="text-decoration-none">
                                                        ${dep.nom}
                                                </a>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${dep.chef != null}">
                                                        ${dep.chef.employe.nomComplet}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">Non assigné</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-end">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/admin/departements/editer/${dep.id}" class="btn btn-outline-primary">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/departements/chef/${dep.id}" class="btn btn-outline-success" title="Gérer le chef">
                                                        <i class="bi bi-person"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/departements/supprimer/${dep.id}" class="btn btn-outline-danger" onclick="return confirm('Supprimer ce département ?')">
                                                        <i class="bi bi-trash"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/departements/historique/${dep.id}" class="btn btn-info">
                                                        🕓 Voir historique des chefs
                                                    </a>

                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
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
<script>
    $(document).ready(function () {
        $('#depsTable').DataTable({
            "language": {
                "search": "🔎 Recherche :",
                "lengthMenu": "Afficher _MENU_ entrées",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ entrées",
                "paginate": {
                    "first": "Premier",
                    "last": "Dernier",
                    "next": "Suivant",
                    "previous": "Précédent"
                }
            }
        });
    });
</script>

</body>
</html>
