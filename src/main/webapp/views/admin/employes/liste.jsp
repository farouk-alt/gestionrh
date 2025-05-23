<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des employés - Gestion RH</title>
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
                <h1 class="h2"><fmt:message key="admin.employes.title" /></h1>
                <a href="${pageContext.request.contextPath}/admin/employes/ajouter" class="btn btn-primary">
                    <i class="bi bi-plus-circle me-1"></i> <fmt:message key="admin.employes.btn.add" />
                </a>
            </div>

            <c:if test="${not empty sessionScope.success}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${sessionScope.success}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="success" scope="session" />
            </c:if>

            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${sessionScope.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="error" scope="session" />
            </c:if>

            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <div class="row align-items-center">
                        <div class="col">
                            <h5 class="card-title mb-0"><fmt:message key="admin.employes.table.title" /></h5>
                        </div>
                        <div class="col-auto">
                            <input type="text" id="tableFilter" class="form-control form-control-sm" placeholder="<fmt:message key="admin.employes.table.search" />">
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table id="employeTable" class="table table-hover table-striped table-sm table-filterable table-sortable">
                            <thead>
                            <tr>
                                <th data-sort="id"><fmt:message key="admin.employes.col.id" /></th>
                                <th data-sort="nom"><fmt:message key="admin.employes.col.nom" /></th>
                                <th data-sort="email"><fmt:message key="admin.employes.col.email" /></th>
                                <th data-sort="departement"><fmt:message key="admin.employes.col.departement" /></th>
                                <th data-sort="role"><fmt:message key="admin.employes.col.role" /></th>
                                <th data-sort="dateEmbauche"><fmt:message key="admin.employes.col.dateEmbauche" /></th>
                                <th data-sort="quotaConge"><fmt:message key="admin.employes.col.quotaConge" /></th>
                                <th class="text-end"><fmt:message key="admin.employes.col.actions" /></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${empty employes}">
                                    <tr>
                                        <td colspan="9" class="text-center"><fmt:message key="admin.employes.empty" />
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach var="employe" items="${employes}">
                                        <tr>
                                            <td>${employe.id}</td>
                                            <td>${employe.nomComplet}</td>
                                            <td>${employe.email}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${employe.departement != null}">
                                                        ${employe.departement.nom}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted"><fmt:message key="admin.employes.role.non_assigne" /></span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:if test="${employe.admin}">
                                                    <span class="badge bg-danger me-1"><fmt:message key="admin.employes.role.admin" /></span>
                                                </c:if>
                                                <c:if test="${employe.estChefActuel}">
                                                    <span class="badge bg-success"><fmt:message key="admin.employes.role.chef" /></span>
                                                </c:if>
                                                <c:if test="${not employe.admin and not employe.estChefActuel}">
                                                    <span class="badge bg-primary"><fmt:message key="admin.employes.role.employe" /></span>
                                                </c:if>
                                            </td>
                                            <td><fmt:formatDate value="${employe.dateCreation}" pattern="dd/MM/yyyy" /></td>
                                            <td>${employe.soldeConge} <fmt:message key="admin.employes.role.employe.solde" /></td>
                                            <td class="text-end">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/admin/employes/editer/${employe.id}" class="btn btn-outline-primary" title="<fmt:message key="admin.employes.btn.edit" />">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/employes/supprimer/${employe.id}" class="btn btn-outline-danger btn-delete" title="<fmt:message key="admin.employes.btn.delete" />"
                                                       onclick="return confirm('<fmt:message key="admin.employes.delete.confirm" />');">
                                                        <i class="bi bi-trash"></i>
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
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
