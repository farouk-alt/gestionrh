<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="page.leave.search.title" /></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
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
        #mainContent.dark-mode .card-title,
        #mainContent.dark-mode .card-body,
        #mainContent.dark-mode .card-header,
        #mainContent.dark-mode .card-footer{
            color: inherit !important;
        }
        #mainContent.dark-mode .form-label,
        #mainContent.dark-mode td{
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
        <jsp:include page="../../includes/admin-sidebar.jsp"/>

        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
            <h2 class="mb-4 text-primary">🔍 <fmt:message key="page.leave.search.title" /></h2>

            <c:if test="${not empty erreur}">
                <div class="alert alert-danger animate__animated animate__fadeInDown">${erreur}</div>
            </c:if>

            <form method="post" class="row g-3 shadow p-4 mb-5 bg-light rounded">
                <div class="col-md-4">
                    <label class="form-label"><fmt:message key="label.employee.lastName" /></label>
                    <input type="text" class="form-control" name="nomEmploye" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label"><fmt:message key="label.employee.firstName" /></label>
                    <input type="text" class="form-control" name="prenomEmploye" required>
                </div>
                <div class="col-md-4">
                    <label class="form-label"><fmt:message key="label.department.name" /></label>
                    <select class="form-select" name="nomDepartement" required>
                        <c:forEach var="dep" items="${departements}">
                            <option value="${dep.nom}">${dep.nom}</option>
                        </c:forEach>
                    </select>

                </div>
                <div class="col-md-4">
                    <label class="form-label"><fmt:message key="label.updated.date" /></label>
                    <input type="date" class="form-control" name="dateMiseAJour" required>
                </div>
                <div class="col-12">
                    <button class="btn btn-primary">🔍 <fmt:message key="button.search" /></button>
                </div>
            </form>

            <c:if test="${not empty resultats}">
                <div class="d-flex justify-content-end gap-2 mb-3 animate__animated animate__fadeInDown">
                    <a href="${pageContext.request.contextPath}/admin/conges/export-pdf" class="btn btn-outline-danger">
                        <i class="bi bi-file-earmark-pdf-fill"></i> <fmt:message key="button.export.pdf" />
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/conges/export-excel" class="btn btn-outline-success">
                        <i class="bi bi-file-earmark-excel-fill"></i> <fmt:message key="button.export.excel" />
                    </a>
                </div>

                <table class="table table-bordered animate__animated animate__fadeInUp">
                    <thead class="table-light">
                    <tr>
                        <th><fmt:message key="column.reason" /></th>
                        <th><fmt:message key="column.startDate" /></th>
                        <th><fmt:message key="column.endDate" /></th>
                        <th><fmt:message key="column.status" /></th>
                        <th><fmt:message key="column.updatedBy" /></th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="d" items="${resultats}">
                        <tr>
                            <td>${d.motif}</td>
                            <td><fmt:formatDate value="${d.dateDebut}" pattern="dd/MM/yyyy"/></td>
                            <td><fmt:formatDate value="${d.dateFin}" pattern="dd/MM/yyyy"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${d.etat == 'ACCEPTE'}">
                                        <span class="badge bg-success"><fmt:message key="status.accepted" /></span>
                                    </c:when>
                                    <c:when test="${d.etat == 'REFUSE'}">
                                        <span class="badge bg-danger"><fmt:message key="status.rejected" /></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-warning text-dark"><fmt:message key="status.pending" /></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${not empty d.updatedBy}">
                                    ${d.updatedBy.prenom} ${d.updatedBy.nom}
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty resultats && param.nomEmploye != null}">
                <div class="alert alert-warning mt-4 animate__animated animate__fadeInDown">
                    ❌ <fmt:message key="label.no.results" />
                </div>
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
