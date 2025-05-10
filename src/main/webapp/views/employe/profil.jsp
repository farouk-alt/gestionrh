<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Mon Profil</title>

  <!-- CSS & Animations -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>

  <!-- Ton CSS principal -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
  <div class="row">
    <%@ include file="../includes/employe-sidebar.jsp" %>


    <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <c:if test="${profilUpdated}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          ✅ Vos informations ont bien été mises à jour.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
        </div>
      </c:if>

      <h2 class="mb-4"><i class="bi bi-person-circle me-2"></i> Mon Profil</h2>

      <form method="post" action="${pageContext.request.contextPath}/employe/profil">
        <div class="row mb-3">
          <div class="col-md-6">
            <label class="form-label">Prénom</label>
            <input type="text" class="form-control" name="prenom" value="${employe.prenom}" required>
          </div>
          <div class="col-md-6">
            <label class="form-label">Nom</label>
            <input type="text" class="form-control" name="nom" value="${employe.nom}" required>
          </div>
        </div>

        <div class="mb-3">
          <label class="form-label">Email</label>
          <input type="email" class="form-control" name="email" value="${employe.email}" required>
        </div>

        <div class="mb-3">
          <label class="form-label">Mot de passe</label>
          <input type="password" class="form-control" name="password" placeholder="Laisser vide pour ne pas changer">
        </div>

        <button type="submit" class="btn btn-primary">
          <i class="bi bi-save me-1"></i> Enregistrer les modifications
        </button>
      </form>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
