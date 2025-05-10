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
</head>

<body>
<jsp:include page="../../includes/header.jsp" />
        <jsp:include page="../../includes/chef-sidebar.jsp" />

<div class="main-content animate__animated animate__fadeIn">
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
                <table class="table table-hover align-middle text-center" id="employeTable">
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
</body>
</html>
