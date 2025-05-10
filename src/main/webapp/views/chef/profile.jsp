<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Mon Profil - Chef</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

  <!-- Bootstrap Icons -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

  <!-- Animate.css -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>

  <!-- Ton style perso -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../includes/header.jsp" />

<div class="container-fluid">
  <div class="row">
        <jsp:include page="../includes/chef-sidebar.jsp" />


    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <h2 class="mb-4">👤 Mon Profil</h2>

      <c:if test="${not empty success}">
        <div class="alert alert-success animate__animated animate__fadeInDown">
            ${success}
        </div>
      </c:if>

      <form method="post" action="${pageContext.request.contextPath}/chef/profil" class="shadow-sm p-4 rounded bg-light">
        <div class="mb-3">
          <label class="form-label">Nom</label>
          <input type="text" name="nom" class="form-control" value="${profil.nom}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Prénom</label>
          <input type="text" name="prenom" class="form-control" value="${profil.prenom}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" name="email" class="form-control" value="${profil.email}" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Mot de passe <small class="text-muted">(laisser vide pour ne pas changer)</small></label>
          <input type="password" name="motDePasse" class="form-control">
        </div>
        <button type="submit" class="btn btn-primary">
          <i class="bi bi-save me-1"></i> Mettre à jour
        </button>
      </form>
    </main>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
