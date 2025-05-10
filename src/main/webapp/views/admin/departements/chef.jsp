<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion du chef de département - Gestion RH</title>
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
                <h1 class="h2">Gestion du chef de département</h1>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="row">
                <div class="col-md-8 col-lg-6">
                    <div class="card shadow-sm mb-4">
                        <div class="card-header bg-white">
                            <h5 class="card-title mb-0">Département: ${departement.nom}</h5>
                        </div>
                        <div class="card-body">
                            <div class="mb-3">
                                <label class="form-label">Chef actuel</label>
                                <div>
                                    <c:choose>
                                        <c:when test="${departement.chef != null}">
                                            <div class="d-flex align-items-center">
                                                <div class="bg-light rounded-circle p-2 me-2">
                                                    <i class="bi bi-person-fill fs-4"></i>
                                                </div>
                                                <div>
                                                    <h6 class="mb-0">${departement.chef.employe.nomComplet}</h6>
                                                    <small class="text-muted">${departement.chef.employe.email}</small>
                                                    <br>
                                                    <small class="text-muted">Nommé le: <fmt:formatDate value="${departement.chef.dateNomination}" pattern="dd/MM/yyyy" /></small>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="text-muted mb-0">Aucun chef n'est actuellement assigné à ce département.</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <hr>

                            <form action="${pageContext.request.contextPath}/admin/departements/chef/${departement.id}" method="post" class="needs-validation" novalidate>
                                <div class="mb-3">
                                    <label for="employeId" class="form-label">Nommer un nouveau chef</label>
                                    <select class="form-select" id="employeId" name="employeId" required>
                                        <option value="">Sélectionner un employé</option>

                                        <c:forEach var="employe" items="${employes}">
                                            <c:if test="${employe.role ne 'ADMIN'}">
                                                <option value="${employe.id}" <c:if test="${employe.estChefActuel}">disabled</c:if>>
                                                        ${employe.nom} <c:if test="${employe.estChefActuel}">(déjà chef)</c:if>
                                                </option>
                                            </c:if>
                                        </c:forEach>
                                    </select>

                                    <div class="invalid-feedback">
                                        Veuillez sélectionner un employé.
                                    </div>
                                    <div class="form-text">
                                        <i class="bi bi-info-circle me-1"></i> La nomination d'un nouveau chef remplacera le chef actuel.
                                    </div>
                                </div>

                                <div class="d-grid gap-2 d-md-flex justify-content-md-end">

                                    <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-outline-secondary">
                                        <i class="bi bi-x-circle me-1"></i> Annuler
                                    </a>

                                    <button type="submit" class="btn btn-primary">
                                        <i class="bi bi-person-check me-1"></i> Nommer
                                    </button>
                                </div>
                            </form> <!-- ✅ fermeture du formulaire de nomination -->
                        </div>
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
