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
</head>
<body>

<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
                <jsp:include page="../../includes/chef-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
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
</body>
</html>
