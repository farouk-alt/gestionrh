<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Recherche demandes de congé</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap & icônes -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <!-- CSS personnalisé -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../../includes/admin-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <h2 class="mb-4">🔍 Recherche dans l'historique des demandes de congé</h2>

            <!-- Formulaire de recherche -->
            <form method="post" action="${pageContext.request.contextPath}/admin/recherche-conge" class="mb-4">
                <div class="row g-3">
                    <div class="col-md-4">
                        <input type="text" name="nomEmploye" class="form-control" placeholder="Nom de l'employé" required />
                    </div>
                    <div class="col-md-4">
                        <input type="text" name="prenomEmploye" class="form-control" placeholder="Prénom de l'employé" required />
                    </div>
                    <div class="col-md-4">
                        <input type="text" name="nomDepartement" class="form-control" placeholder="Département" required />
                    </div>
                    <div class="col-md-4">
                        <input type="date" name="dateMiseAJour" class="form-control" required />
                    </div>
                    <div class="col-md-4">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-search"></i> Rechercher
                        </button>
                    </div>
                </div>
            </form>

            <!-- Affichage erreur -->
            <c:if test="${not empty erreur}">
                <div class="alert alert-danger">${erreur}</div>
            </c:if>

            <!-- Résultats -->
            <c:if test="${not empty resultats}">
                <h4>Résultats de la recherche :</h4>
                <c:if test="${fn:length(resultats) > 50}">
                    <div class="alert alert-warning">Résultats trop nombreux, veuillez affiner vos critères de recherche.</div>
                </c:if>
                <div class="mb-3 d-flex justify-content-end gap-2">
                    <a href="${pageContext.request.contextPath}/admin/conges/export-pdf" class="btn btn-outline-secondary">
                        <i class="bi bi-filetype-pdf"></i> Exporter en PDF
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/conges/export-excel" class="btn btn-outline-success">
                        <i class="bi bi-file-earmark-excel"></i> Exporter en Excel
                    </a>
                </div>
                <table class="table table-bordered table-hover mt-3">
                    <thead>
                    <tr>
                        <th>Employé</th>
                        <th>Département</th>
                        <th>Motif</th>
                        <th>Date début</th>
                        <th>Date fin</th>
                        <th>État</th>
                        <th>Chef validateur</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="demande" items="${resultats}">
                        <tr>
                            <td>${demande.employe.nomComplet}</td>
                            <td>${demande.employe.departement.nom}</td>
                            <td>${demande.motif}</td>
                            <td><fmt:formatDate value="${demande.dateDebut}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${demande.dateFin}" pattern="yyyy-MM-dd" /></td>
                            <td>${demande.etat}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty demande.updatedBy}">
                                        ${demande.updatedBy.nomComplet}
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-muted">Non spécifié</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${not empty resultats and fn:length(resultats) == 0}">
                <div class="alert alert-warning">Aucune demande ne correspond à ces critères.</div>
            </c:if>
        </main>
    </div>
</div>

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
