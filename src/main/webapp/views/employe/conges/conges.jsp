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
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/dataTables.bootstrap5.min.css"/>
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

        #mainContent.dark-mode td {
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
        #mainContent.dark-mode #empsTable_length label,
        #mainContent.dark-mode #empsTable_filter label {
            color: #000000 !important;
        }

    </style>



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


<main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
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

                <table  id="congesTable" class="table table-hover shadow-sm">
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/dataTables.bootstrap5.min.js"></script>
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
<script>
    $(document).ready(function () {
        $('#congesTable').DataTable({
            "language": {
                "search": "🔎 Recherche :",
                "lengthMenu": "Afficher _MENU_ entrées",
                "info": "Affichage de _START_ à _END_ sur _TOTAL_ entrées",
                "paginate": {
                    "first": "Premier",
                    "last": "Dernier",
                    "next": "Suivant",
                    "previous": "Précédent"
                }
            }
        });
    });
</script>

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
