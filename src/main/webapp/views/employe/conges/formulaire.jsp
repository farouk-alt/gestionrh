<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Nouvelle Demande de Congé</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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

    #mainContent.dark-mode label {
      color: #000000 !important;
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

<jsp:include page="../../includes/header.jsp"/>

<div class="container-fluid">
  <div class="row">
    <%@ include file="../../includes/employe-sidebar.jsp" %>

    <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <h3 class="mb-4">📝 Nouvelle Demande de Congé</h3>
      <c:if test="${not empty error}">
        <div class="error-box position-relative p-3 mb-4 rounded" id="errorBox">
          <span class="close-btn position-absolute top-0 end-0 p-2" onclick="document.getElementById('errorBox').style.display='none'">&times;</span>
            ${error}
        </div>
      </c:if>



      <form action="${pageContext.request.contextPath}/employe/conges/demander" method="post" class="row g-3">
        <div class="col-md-6">
          <label for="dateDebut" class="form-label">Date début</label>
          <input type="date" class="form-control" name="dateDebut" id="dateDebut" required>
        </div>
        <div class="col-md-6">
          <label for="dateFin" class="form-label">Date fin</label>
          <input type="date" class="form-control" name="dateFin" id="dateFin" required>
        </div>
        <div class="col-12">
          <label for="motif" class="form-label">Motif</label>
          <textarea class="form-control" name="motif" rows="3" id="motif" required></textarea>
        </div>
        <div class="col-12">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-check-circle me-1"></i> Envoyer la demande
          </button>
        </div>
      </form>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
  window.addEventListener('DOMContentLoaded', () => {
    const today = new Date().toISOString().split("T")[0];
    document.getElementById("dateDebut").setAttribute("min", today);
    document.getElementById("dateFin").setAttribute("min", today);

    document.querySelector("form").addEventListener("submit", function (e) {
      const dateDebut = new Date(document.getElementById("dateDebut").value);
      const dateFin = new Date(document.getElementById("dateFin").value);
      const now = new Date();
      now.setHours(0,0,0,0); // normalisation

      if (dateDebut < now || dateFin < now) {
        e.preventDefault();
        alert("❌ Vous ne pouvez pas sélectionner une date passée.");
      } else if (dateDebut > dateFin) {
        e.preventDefault();
        alert("❌ La date de début doit être avant la date de fin.");
      }
    });
  });
</script>
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
