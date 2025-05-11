<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes demandes de congé</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp"/>
<jsp:include page="../../includes/employe-sidebar.jsp"/>
<c:if test="${not empty sessionScope.toastMessage}">
    <div class="toast-container position-fixed bottom-0 end-0 p-3">
        <div class="toast align-items-center text-white bg-success border-0 show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                        ${sessionScope.toastMessage}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    </div>
    <c:remove var="toastMessage" scope="session" />
</c:if>


<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
    <h2 class="mb-4">📅 Mes demandes de congé</h2>

    <a href="${pageContext.request.contextPath}/employe/conges/demander" class="btn btn-success mb-3">
        <i class="bi bi-plus-circle"></i> Nouvelle demande
    </a>

    <c:choose>
        <c:when test="${not empty conges}">
            <div class="table-responsive">
                <a href="${pageContext.request.contextPath}/export/pdf" class="btn btn-outline-danger float-end mb-3">
                    <i class="bi bi-file-earmark-pdf"></i> Exporter PDF
                </a>

                <!-- 2. Champ de filtre JS -->
                <input type="text" id="filtreTable" class="form-control mb-3" placeholder="🔎 Filtrer les demandes...">

                <table class="table table-hover shadow-sm">
                    <thead class="table-light">
                    <tr>
                        <th>#</th>
                        <th>Date début</th>
                        <th>Date fin</th>
                        <th>Motif</th>
                        <th>État</th>
                        <th>Date maj</th>
                        <th>Traité par</th>
                        <th>Actions</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="conge" items="${conges}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><fmt:formatDate value="${conge.dateDebut}" pattern="yyyy-MM-dd" /></td>

                            <td><fmt:formatDate value="${conge.dateFin}" pattern="yyyy-MM-dd" /></td>

                            <td>${conge.motif}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${conge.etat == 'EN_ATTENTE'}">
                                        <span class="badge text-bg-warning">En attente</span>
                                    </c:when>
                                    <c:when test="${conge.etat == 'ACCEPTE'}">
                                        <span class="badge text-bg-success">Acceptée</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge text-bg-danger">Refusée</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${conge.dateMiseAjour != null}">
                                    ${conge.dateMiseAjour}
                                </c:if>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty conge.updatedBy}">
                                        ${conge.updatedBy.prenom} ${conge.updatedBy.nom}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:if test="${conge.etat == 'EN_ATTENTE'}">
                                    <!-- Icône Modifier -->
                                    <a href="${pageContext.request.contextPath}/employe/conges/modifier/${conge.id}"
                                       class="btn btn-sm btn-outline-warning me-1"
                                       title="Modifier">
                                        <i class="bi bi-pencil-square"></i>
                                    </a>

                                    <!-- Icône Supprimer -->
                                    <form action="${pageContext.request.contextPath}/employe/conges/supprimer/${conge.id}"
                                          method="post" style="display:inline;"
                                          onsubmit="return confirm('Confirmer la suppression ?');">
                                        <button type="submit"
                                                class="btn btn-sm btn-outline-danger"
                                                title="Supprimer">
                                            <i class="bi bi-trash3"></i>
                                        </button>
                                    </form>
                                </c:if>

                                <!-- Icône Voir -->
                                <a href="${pageContext.request.contextPath}/employe/conges/voir/${conge.id}"
                                   class="btn btn-sm btn-outline-primary"
                                   title="Voir les détails">
                                    <i class="bi bi-eye"></i>
                                </a>
                            </td>

                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info animate__animated animate__fadeInDown">
                Vous n’avez encore effectué aucune demande de congé.
            </div>
        </c:otherwise>
    </c:choose>
</main>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById("filtreTable").addEventListener("keyup", function () {
        const filtre = this.value.toLowerCase();
        const lignes = document.querySelectorAll("table tbody tr");
        lignes.forEach(ligne => {
            const texte = ligne.innerText.toLowerCase();
            ligne.style.display = texte.includes(filtre) ? "" : "none";
        });
    });
</script>

</body>
</html>
