<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Recherche Avancée - Historique Congés</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<jsp:include page="../../includes/header.jsp" />
<div class="container-fluid">
  <div class="row">
    <jsp:include page="../../includes/admin-sidebar.jsp" />

    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
      <h2>🔍 Recherche avancée - Historique des demandes de congé</h2>

      <form action="${pageContext.request.contextPath}/admin/conges/recherche" method="get" class="row g-3">
        <div class="col-md-4">
          <label for="nomEmploye" class="form-label">Nom employé</label>
          <input type="text" id="nomEmploye" name="nomEmploye" class="form-control">
        </div>

        <div class="col-md-4">
          <label for="nomDepartement" class="form-label">Nom département</label>
          <input type="text" id="nomDepartement" name="nomDepartement" class="form-control">
        </div>

        <div class="col-md-4">
          <label for="dateMiseAJour" class="form-label">Date de mise à jour</label>
          <input type="date" id="dateMiseAJour" name="dateMiseAJour" class="form-control">
        </div>

        <div class="col-12 text-end">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-search"></i> Rechercher
          </button>
        </div>
      </form>

      <c:if test="${not empty resultats}">
        <h3 class="mt-4">Résultats</h3>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th>Nom employé</th>
            <th>Début</th>
            <th>Fin</th>
            <th>Motif</th>
            <th>État</th>
            <th>Chef de département</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="demande" items="${resultats}">
            <tr>
              <td>${demande.employe.nomComplet}</td>
              <td><fmt:formatDate value="${demande.dateDebut}" pattern="dd/MM/yyyy" /></td>
              <td><fmt:formatDate value="${demande.dateFin}" pattern="dd/MM/yyyy" /></td>
              <td>${demande.motif}</td>
              <td>${demande.etat}</td>
              <td>
                <c:forEach var="chef" items="${demande.employe.departement.anciensChefs}">
                  <c:if test="${chef.dateFin == null || chef.dateFin.time >= demande.dateMiseAjour.time}">
                    ${chef.employe.nomComplet}
                  </c:if>
                </c:forEach>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </c:if>

      <c:if test="${empty resultats and not empty param.nomEmploye}">
        <div class="alert alert-warning mt-4">Aucun résultat trouvé pour les critères spécifiés.</div>
      </c:if>
    </main>
  </div>
</div>

<jsp:include page="../../includes/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
