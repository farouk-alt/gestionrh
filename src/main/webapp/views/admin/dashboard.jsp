<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord - Administration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../includes/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/admin-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 class="h2">Tableau de bord</h1>
            </div>

            <!-- Statistiques -->
            <div class="row">
                <div class="col-md-6 col-lg-3 mb-4">
                    <div class="card text-white bg-primary shadow-sm h-100 animate__animated animate__fadeInUp">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <h6 class="card-title text-white">Employés</h6>
                                    <h2 class="display-4 mt-2 mb-0">${employes.size()}</h2>
                                </div>
                                <i class="bi bi-people-fill fs-1"></i>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="${pageContext.request.contextPath}/admin/employes" class="text-white">Voir détails <i class="bi bi-chevron-right"></i></a>
                        </div>
                    </div>
                </div>

                <div class="col-md-6 col-lg-3 mb-4">
                    <div class="card text-white bg-success shadow-sm h-100 animate__animated animate__fadeInUp animate__delay-1s">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <h6 class="card-title text-white">Départements</h6>
                                    <h2 class="display-4 mt-2 mb-0">${departements.size()}</h2>
                                </div>
                                <i class="bi bi-building fs-1"></i>
                            </div>
                        </div>
                        <div class="card-footer">
                            <a href="${pageContext.request.contextPath}/admin/departements" class="text-white">Voir détails <i class="bi bi-chevron-right"></i></a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Derniers ajouts -->
            <div class="row">
                <div class="col-lg-6 mb-4">
                    <div class="card shadow-sm animate__animated animate__fadeInUp animate__delay-2s">
                        <div class="card-header bg-white">
                            <h5 class="card-title mb-0">Derniers employés ajoutés</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>Nom</th>
                                        <th>Email</th>
                                        <th>Département</th>
                                        <th>Date d'embauche</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="employe" items="${employes}" end="4">
                                        <tr>
                                            <td>${employe.nomComplet}</td>
                                            <td>${employe.email}</td>
                                            <td>${employe.departement.nom}</td>
                                            <td><fmt:formatDate value="${employe.dateCreation}" pattern="dd/MM/yyyy" /></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer bg-white">
                            <a href="${pageContext.request.contextPath}/admin/employes" class="btn btn-sm btn-primary">Voir tous les employés</a>
                        </div>
                    </div>
                </div>

                <div class="col-lg-6 mb-4">
                    <div class="card shadow-sm animate__animated animate__fadeInUp animate__delay-3s">
                        <div class="card-header bg-white">
                            <h5 class="card-title mb-0">Départements</h5>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th>Nom</th>
                                        <th>Chef</th>
                                        <th>Nb employés</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="departement" items="${departements}" end="4">
                                        <tr>
                                            <td>${departement.nom}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${departement.chef != null}">
                                                        ${departement.chef.employe.nomComplet}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">Non assigné</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${departement.employes.size()}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer bg-white">
                            <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-sm btn-primary">Voir tous les départements</a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Section chef si admin est aussi chef -->
            <c:if test="${employe.estChefActuel}">
                <div class="card mt-4 shadow-sm border border-success">
                    <div class="card-body">
                        <h5 class="card-title text-success">🧑‍💼 Espace Chef de Département</h5>
                        <p>Vous êtes également chef de département. Accédez à vos outils :</p>
                        <a href="${pageContext.request.contextPath}/chef/dashboard" class="btn btn-outline-success me-2">
                            <i class="bi bi-diagram-3"></i> Dashboard Chef
                        </a>
                        <a href="${pageContext.request.contextPath}/chef/profil" class="btn btn-outline-success">
                            <i class="bi bi-person-circle"></i> Mon Profil Chef
                        </a>
                    </div>
                </div>
            </c:if>

        </main>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
