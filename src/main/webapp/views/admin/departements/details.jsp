<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Détails du département</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
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

</head>
<body>
<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
  <div class="row">
    <jsp:include page="../../includes/admin-sidebar.jsp" />

      <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
      <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
        <h2 class="h4">Détails du département : <strong>${departement.nom}</strong></h2>
      </div>

      <div class="card mb-4">
        <div class="card-body">

          <h5 class="mt-4">Chef de département</h5>
          <c:choose>
            <c:when test="${not empty departement.anciensChefs}">
<%--              <p>--%>
<%--                  ${departement.chef.employe.nomComplet}--%>
<%--                (<a href="mailto:${departement.chef.employe.email}">${departement.chef.employe.email}</a>)--%>
<%--              </p>--%>
              <c:forEach var="chef" items="${departement.anciensChefs}">
                <c:if test="${chef.dateFin == null}">
                  ${chef.employe.nomComplet}
                </c:if>
              </c:forEach>

            </c:when>
            <c:otherwise>
              <p class="text-muted">Aucun chef assigné.</p>
            </c:otherwise>
          </c:choose>

          <h5 class="mt-4">Liste des employés</h5>
          <c:choose>
            <c:when test="${not empty departement.employes}">
              <ul class="list-group list-group-flush">
                <c:forEach var="emp" items="${departement.employes}">
                  <li class="list-group-item d-flex justify-content-between align-items-center">
                      ${emp.nomComplet} <span class="text-muted small">${emp.email}</span>
                  </li>
                </c:forEach>
              </ul>
            </c:when>
            <c:otherwise>
              <p class="text-muted">Aucun employé dans ce département.</p>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-secondary">
        <i class="bi bi-arrow-left"></i> Retour à la liste
      </a>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
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
