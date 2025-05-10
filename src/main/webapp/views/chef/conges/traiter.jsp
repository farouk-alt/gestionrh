<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Traiter Demande - Chef</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../../includes/chef-sidebar.jsp"/>

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
            <h2 class="mb-4 text-primary">📝 Traiter la Demande de Congé</h2>

            <c:if test="${progressionAcceptation >= 50}">
                <div class="alert alert-danger animate__animated animate__fadeInDown" role="alert">
                    ⚠️ Vous avez atteint la limite de <strong>50%</strong> de demandes acceptées dans ce département.
                </div>
            </c:if>

            <c:if test="${not empty erreur}">
                <div class="alert alert-warning animate__animated animate__fadeInDown">
                        ${erreur}
                </div>
            </c:if>

            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title mb-3">Informations de la demande</h5>
                    <ul class="list-group mb-4">
                        <li class="list-group-item"><strong>Employé :</strong> ${demande.employe.nomComplet}</li>
                        <li class="list-group-item"><strong>Début :</strong> <fmt:formatDate value="${demande.dateDebut}" pattern="dd/MM/yyyy"/></li>
                        <li class="list-group-item"><strong>Fin :</strong> <fmt:formatDate value="${demande.dateFin}" pattern="dd/MM/yyyy"/></li>
                        <li class="list-group-item"><strong>Motif :</strong> ${demande.motif}</li>
                    </ul>

                    <c:if test="${progressionAcceptation != null}">
                        <div class="mb-4">
                            <label class="form-label">
                                Taux d’approbation actuel du département : <strong>${progressionAcceptation}%</strong>
                            </label>
                            <div class="progress">
                                <div class="progress-bar
                                    ${progressionAcceptation < 50 ? 'bg-success' : progressionAcceptation == 50 ? 'bg-warning' : 'bg-danger'}"
                                     role="progressbar"
                                     style="width: ${progressionAcceptation}%;"
                                     aria-valuenow="${progressionAcceptation}" aria-valuemin="0" aria-valuemax="100">
                                        ${progressionAcceptation}%
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <form method="post" action="${pageContext.request.contextPath}/chef/conges/traiter/${demande.id}">
                        <div class="mb-3">
                            <label for="commentaire" class="form-label">Commentaire (optionnel)</label>
                            <textarea name="commentaire" id="commentaire" class="form-control" rows="3"></textarea>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <button name="action" value="approuver" class="btn btn-success"
                                    <c:if test="${progressionAcceptation >= 50}">disabled</c:if>>
                                <i class="bi bi-check-circle me-1"></i> Approuver
                            </button>

                            <button name="action" value="refuser" class="btn btn-danger">
                                <i class="bi bi-x-circle me-1"></i> Refuser
                            </button>

                            <a href="${pageContext.request.contextPath}/chef/conges" class="btn btn-secondary">
                                <i class="bi bi-arrow-left me-1"></i> Retour
                            </a>
                        </div>
                    </form>

                    <c:if test="${progressionAcceptation >= 50}">
                        <div class="text-danger mt-3">
                            ❌ Le bouton <strong>“Approuver”</strong> est désactivé car plus de 50% des demandes
                            ont déjà été acceptées dans ce département.
                        </div>
                    </c:if>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
