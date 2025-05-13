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

    #mainContent.dark-mode .btn-outline-success {
      border-color: #198754;
      color: #198754;
    }

    #mainContent {
      transition: background-color 0.3s ease, color 0.3s ease;
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

    #mainContent.dark-mode .btn-outline-success {
      border-color: #198754;
      color: #198754;
    }

    #mainContent {
      transition: background-color 0.3s ease, color 0.3s ease;
    }
  </style>

</head>

<body>

<%@ include file="../includes/header.jsp" %>

<div class="container-fluid">
  <div class="row">
    <%@ include file="../includes/employe-sidebar.jsp" %>


    <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <c:if test="${profilUpdated}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          ✅ Vos informations ont bien été mises à jour.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Fermer"></button>
        </div>
      </c:if>
      <c:if test="${not empty error}">
        <div class="toast-container position-fixed bottom-0 end-0 p-3">
          <div class="toast show align-items-center text-bg-danger border-0" role="alert">
            <div class="d-flex">
              <div class="toast-body">
                  ${error}
              </div>
              <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
          </div>
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
        <div class="mb-3">
          <label class="form-label">Solde de congé (jours)</label>
          <input type="number" class="form-control" value="${employe.soldeConge}" disabled>
        </div>

        <button type="submit" class="btn btn-primary">
          <i class="bi bi-save me-1"></i> Enregistrer les modifications
        </button>
      </form>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
