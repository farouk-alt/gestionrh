<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="page.advanced.leave.search.title" /></title>
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
      <h2>🔍 <fmt:message key="page.advanced.leave.search.title" /></h2>

      <form action="${pageContext.request.contextPath}/admin/conges/recherche" method="get" class="row g-3">
        <div class="col-md-4">
          <label for="nomEmploye" class="form-label"><fmt:message key="label.employee.name" /></label>
          <input type="text" id="nomEmploye" name="nomEmploye" class="form-control">
        </div>

        <div class="col-md-4">
          <label for="nomDepartement" class="form-label"><fmt:message key="label.department.name" /></label>
          <input type="text" id="nomDepartement" name="nomDepartement" class="form-control">
        </div>

        <div class="col-md-4">
          <label for="dateMiseAJour" class="form-label"><fmt:message key="label.updated.date" /></label>
          <input type="date" id="dateMiseAJour" name="dateMiseAJour" class="form-control">
        </div>

        <div class="col-12 text-end">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-search"></i> <fmt:message key="button.search" />
          </button>
        </div>
      </form>

      <c:if test="${not empty resultats}">
        <h3 class="mt-4"><fmt:message key="label.results" /></h3>
        <table class="table table-bordered">
          <thead>
          <tr>
            <th><fmt:message key="column.employee.name" /></th>
            <th><fmt:message key="column.start.date" /></th>
            <th><fmt:message key="column.end.date" /></th>
            <th><fmt:message key="column.reason" /></th>
            <th><fmt:message key="column.status" /></th>
            <th><fmt:message key="column.department.head" /></th>
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
        <div class="alert alert-warning mt-4"><fmt:message key="label.no.results" /></div>
      </c:if>
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
