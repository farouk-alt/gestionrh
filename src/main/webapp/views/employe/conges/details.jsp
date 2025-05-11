<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Détails de la Demande</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
</head>
<body>

<jsp:include page="../../includes/header.jsp"/>

<div class="container-fluid">
  <div class="row">
    <jsp:include page="../../includes/employe-sidebar.jsp"/>

    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <div class="card shadow-lg border-0">
        <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
          <h5 class="mb-0">📄 Détails de la demande</h5>
          <i class="bi bi-file-earmark-text"></i>
        </div>
        <div class="card-body">
          <ul class="list-group list-group-flush">
            <li class="list-group-item"><strong>👤 Employé :</strong> ${demande.employe.nomComplet}</li>
            <li class="list-group-item"><strong>📅 Début :</strong> <fmt:formatDate value="${demande.dateDebut}" pattern="dd/MM/yyyy"/></li>
            <li class="list-group-item"><strong>📅 Fin :</strong> <fmt:formatDate value="${demande.dateFin}" pattern="dd/MM/yyyy"/></li>
            <li class="list-group-item"><strong>📝 Motif :</strong> ${demande.motif}</li>
            <li class="list-group-item"><strong>📌 État :</strong>
              <c:choose>
                <c:when test="${demande.etat.name() == 'ACCEPTE'}">
                  <span class="badge bg-success">Acceptée</span>
                </c:when>
                <c:when test="${demande.etat.name() == 'REFUSE'}">
                  <span class="badge bg-danger">Refusée</span>
                </c:when>
                <c:otherwise>
                  <span class="badge bg-warning text-dark">En attente</span>
                </c:otherwise>
              </c:choose>
            </li>
          </ul>

          <a href="${pageContext.request.contextPath}/employe/mes-conges" class="btn btn-outline-secondary mt-4">
            <i class="bi bi-arrow-left-circle"></i> Retour à la liste
          </a>
        </div>
      </div>
    </main>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
