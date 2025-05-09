<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Historique des chefs</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../../includes/admin-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <h2 class="mb-4">🕓 Historique des chefs – ${departement.nom}</h2>

            <c:choose>
                <c:when test="${not empty departement.anciensChefs}">
                    <ul class="list-group">
                        <c:forEach var="chef" items="${departement.anciensChefs}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                👤 <strong>${chef.employe.nomComplet}</strong>
                                <span>
                                    nommé le <strong><fmt:formatDate value="${chef.dateDebut}" pattern="yyyy-MM-dd" /></strong>
                                    →
                                    <c:choose>
                                        <c:when test="${chef.dateFin != null}">
                                            jusqu’au <strong><fmt:formatDate value="${chef.dateFin}" pattern="yyyy-MM-dd" /></strong>
                                        </c:when>
                                        <c:otherwise>
                                            <strong class="text-success">(en fonction)</strong>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p class="text-muted">Aucun chef trouvé pour ce département.</p>
                </c:otherwise>
            </c:choose>

            <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-secondary mt-3">
                ⬅ Retour
            </a>
        </main>
    </div>
</div>

<jsp:include page="../../includes/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
