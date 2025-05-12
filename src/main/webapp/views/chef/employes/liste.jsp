<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>

    <!-- Bootstrap CSS & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>

    <style>
        .main-content {
            margin-left: 250px;
            padding: 30px;
            max-width: 100%;
        }

        .table-container {
            background-color: white;
            border-radius: 12px;
            overflow-x: auto;
        }

        .table th {
            background-color: #343a40;
            color: white;
        }

        .header-actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            flex-wrap: wrap;
            margin-bottom: 1.5rem;
        }

        .header-actions .form-control {
            max-width: 300px;
        }

        @media print {
            .no-print, .sidebar, .navbar {
                display: none !important;
            }

            .main-content {
                margin-left: 0;
                padding: 0;
                max-width: 100%;
            }
        }
    </style>
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
        #mainContent.dark-mode .btn-primary {
            background-color: #0d6efd;
            border-color: #0d6efd;
            color: #fff;
        }
        #mainContent.dark-mode td,
        #mainContent.dark-mode input {
            color: #000000 !important;
        }

        #mainContent.dark-mode .btn-outline-success {
            border-color: #198754;
            color: #198754;
        }

        #mainContent {
            transition: background-color 0.3s ease, color 0.3s ease;
        }
        #mainContent.dark-mode #empsTable_length label,
        #mainContent.dark-mode #empsTable_filter label {
            color: #000000 !important;
        }

    </style>

</head>

<body>
<jsp:include page="../../includes/header.jsp" />
        <jsp:include page="../../includes/chef-sidebar.jsp" />

<div id="mainContent" class="main-content animate__animated animate__fadeIn">
    <h2 class="mb-4">
        👥 Liste des employés – Département <strong class="text-primary">${nomDepartement}</strong>
    </h2>

    <div class="header-actions no-print">
        <input type="text" id="searchInput" class="form-control" placeholder="🔍 Rechercher un employé...">
        <button onclick="window.print()" class="btn btn-outline-danger">
            <i class="bi bi-file-earmark-pdf-fill me-1"></i> Exporter PDF
        </button>
    </div>

    <c:choose>
        <c:when test="${not empty employes}">
            <div class="table-container shadow-sm p-3">
                <table id="empsTable" class="table table-hover align-middle text-center" id="employeTable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Nom complet</th>
                        <th>Email</th>
                        <th>Rôle</th>
                        <th>Date d'embauche</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="emp" items="${employes}" varStatus="loop">
                        <tr class="animate__animated animate__fadeInUp">
                            <td>${loop.index + 1}</td>
                            <td><i class="bi bi-person-circle text-primary me-1"></i> ${emp.prenom} ${emp.nom}</td>
                            <td>${emp.email}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${emp.estChefActuel}">
                                        <span class="badge bg-success text-uppercase">Chef</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-info text-uppercase">Employé</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><fmt:formatDate value="${emp.dateCreation}" pattern="dd-MM-yyyy" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info shadow-sm">
                Aucun employé trouvé dans ce département.
            </div>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
<script>
    document.getElementById("searchInput").addEventListener("keyup", function () {
        const input = this.value.toLowerCase();
        const rows = document.querySelectorAll("#employeTable tbody tr");
        rows.forEach(row => {
            const text = row.innerText.toLowerCase();
            row.style.display = text.includes(input) ? "" : "none";
        });
    });
</script>
<script>
    $(document).ready(function () {
        $('#empsTable').DataTable({
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
