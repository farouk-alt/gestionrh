<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Détails de la Demande</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
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

<jsp:include page="../../includes/header.jsp"/>

<div class="container-fluid">
  <div class="row">
    <jsp:include page="../../includes/employe-sidebar.jsp"/>

    <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
      <div class="card shadow-lg border-0">
        <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
          <h5 class="mb-0"><fmt:message key="demande.titre"/></h5>
          <i class="bi bi-file-earmark-text"></i>
        </div>
        <div class="card-body">
          <ul class="list-group list-group-flush">
            <li class="list-group-item"><strong><fmt:message key="demande.employe"/></strong> ${demande.employe.nomComplet}</li>
            <li class="list-group-item"><strong><fmt:message key="demande.debut"/></strong> <fmt:formatDate value="${demande.dateDebut}" pattern="dd/MM/yyyy"/></li>
            <li class="list-group-item"><strong><fmt:message key="demande.fin"/></strong> <fmt:formatDate value="${demande.dateFin}" pattern="dd/MM/yyyy"/></li>
            <li class="list-group-item"><strong><fmt:message key="demande.details.motif"/></strong> ${demande.motif}</li>
            <li class="list-group-item"><strong><fmt:message key="demande.etat"/></strong>
              <c:choose>
                <c:when test="${demande.etat.name() == 'ACCEPTE'}">
                  <span class="badge bg-success"><fmt:message key="demande.etat.accepte"/></span>
                </c:when>
                <c:when test="${demande.etat.name() == 'REFUSE'}">
                  <span class="badge bg-danger"><fmt:message key="demande.etat.refuse"/></span>
                </c:when>
                <c:otherwise>
                  <span class="badge bg-warning text-dark"><fmt:message key="demande.etat.en_attente"/></span>
                </c:otherwise>
              </c:choose>
            </li>
          </ul>

          <a href="${pageContext.request.contextPath}/employe/mes-conges" class="btn btn-outline-secondary mt-4">
            <i class="bi bi-arrow-left-circle"></i> <fmt:message key="demande.retour"/>
          </a>
        </div>
      </div>
    </main>
  </div>
</div>

<!-- Bootstrap JS -->
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
