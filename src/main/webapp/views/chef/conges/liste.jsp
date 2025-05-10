<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Demandes en attente - Chef</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <!-- Icônes Bootstrap -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Style perso -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
                <jsp:include page="../../includes/chef-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
            <h2 class="mb-4">📋 Demandes de congé en attente</h2>

            <c:if test="${empty demandes}">
                <div class="alert alert-info">
                    Il n'y a aucune demande en attente actuellement.
                </div>
            </c:if>

            <c:if test="${not empty demandes}">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-light">
                        <tr>
                            <th>Employé</th>
                            <th>Date début</th>
                            <th>Date fin</th>
                            <th>Motif</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="d" items="${demandes}">
                            <tr>
                                <td>${d.employe.prenom} ${d.employe.nom}</td>
                                <td><fmt:formatDate value="${d.dateDebut}" pattern="dd/MM/yyyy" /></td>
                                <td><fmt:formatDate value="${d.dateFin}" pattern="dd/MM/yyyy" /></td>
                                <td>${d.motif}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/chef/conges/traiter/${d.id}" class="btn btn-success btn-sm me-2">
                                        <i class="bi bi-check-circle"></i> Traiter
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/chef/conges/historique" class="btn btn-outline-secondary">
                    <i class="bi bi-clock-history"></i> Voir l'historique des demandes traitées
                </a>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
