<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
</head>
<body>
<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../../includes/admin-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Gestion des employés</h1>
                <a href="${pageContext.request.contextPath}/admin/employes/ajouter" class="btn btn-primary">
                    <i class="bi bi-plus-circle me-1"></i> Nouvel employé
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
                            <h5 class="card-title mb-0">Liste des employés</h5>
                        </div>
                        <div class="col-auto">
                            <input type="text" id="tableFilter" class="form-control form-control-sm" placeholder="Rechercher...">
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-hover table-striped table-sm table-filterable table-sortable">
                            <thead>
                            <tr>
                                <th data-sort="id">ID</th>
                                <th data-sort="nom">Nom</th>
                                <th data-sort="email">Email</th>
                                <th data-sort="departement">Département</th>
                                <th data-sort="role">Rôle</th>
                                <th data-sort="dateEmbauche">Date d'embauche</th>
                                <th data-sort="quotaConge">solde congé</th>
                                <th class="text-end">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:choose>
                                <c:when test="${empty employes}">
                                    <tr>
                                        <td colspan="9" class="text-center">Aucun employé trouvé</td>
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
                                                        <span class="text-muted">Non assigné</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${employe.role == 'ADMIN'}">
                                                        <span class="badge bg-danger">Administrateur</span>
                                                    </c:when>
                                                    <c:when test="${employe.estChefActuel}">
                                                    <span class="badge bg-success">Chef</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-primary">Employé</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><fmt:formatDate value="${employe.dateCreation}" pattern="dd/MM/yyyy" /></td>
                                            <td>${employe.soldeConge} jours</td>
                                            <td class="text-end">
                                                <div class="btn-group btn-group-sm">
                                                    <a href="${pageContext.request.contextPath}/admin/employes/editer/${employe.id}" class="btn btn-outline-primary" title="Modifier">
                                                        <i class="bi bi-pencil"></i>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/employes/supprimer/${employe.id}" class="btn btn-outline-danger btn-delete" title="Supprimer"
                                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet employé ?');">
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

<jsp:include page="../../includes/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>
