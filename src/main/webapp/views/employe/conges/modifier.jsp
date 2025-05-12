<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier Demande de Congé</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap & animations -->
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
        #mainContent.dark-mode label,
        #mainContent.dark-mode .card-title,
        #mainContent.dark-mode .card-body,
        #mainContent.dark-mode .card-header,
        #mainContent.dark-mode .card-footer {
            color: inherit !important;
        }

        #mainContent.dark-mode label {
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

<jsp:include page="../../includes/header.jsp" />
<div class="container-fluid">
    <div class="row">
        <%@ include file="../../includes/employe-sidebar.jsp" %>

        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 animate__animated animate__fadeIn">
            <h2 class="mb-4 text-primary">✏️ Modifier la Demande de Congé</h2>
            <c:if test="${not empty error}">
                <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11;">
                    <div class="toast show text-white bg-danger border-0 animate__animated animate__fadeInUp" role="alert" aria-live="assertive" aria-atomic="true">
                        <div class="d-flex">
                            <div class="toast-body">
                                    ${error}
                            </div>
                            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="card shadow-sm animate__animated animate__fadeInUp">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/employe/conges/modifier/${demande.id}" method="post" class="row g-3">
                        <div class="col-md-6">
                            <label for="dateDebut" class="form-label">Date début</label>
                            <input type="date" class="form-control" id="dateDebut" name="dateDebut" value="<fmt:formatDate value='${demande.dateDebut}' pattern='yyyy-MM-dd'/>" required>
                        </div>

                        <div class="col-md-6">
                            <label for="dateFin" class="form-label">Date fin</label>
                            <input type="date" class="form-control" id="dateFin" name="dateFin" value="<fmt:formatDate value='${demande.dateFin}' pattern='yyyy-MM-dd'/>" required>
                        </div>

                        <div class="col-12">
                            <label for="motif" class="form-label">Motif</label>
                            <textarea class="form-control" id="motif" name="motif" rows="4" required>${demande.motif}</textarea>
                        </div>

                        <div class="col-12 d-flex justify-content-between">
                            <button type="submit" class="btn btn-primary animate__animated animate__pulse">
                                💾 Sauvegarder les modifications
                            </button>

                            <a href="${pageContext.request.contextPath}/employe/mes-conges" class="btn btn-secondary">
                            ⬅ Retour
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>
<!-- Toast Container -->
<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
    <div id="liveToast" class="toast align-items-center text-white bg-${toastType} border-0 animate__animated animate__fadeInUp" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="d-flex">
            <div class="toast-body">
                ${toastMessage}
            </div>
            <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    window.addEventListener('DOMContentLoaded', () => {
        const today = new Date().toISOString().split("T")[0];
        document.getElementById("dateDebut").setAttribute("min", today);
        document.getElementById("dateFin").setAttribute("min", today);

        document.querySelector("form").addEventListener("submit", function (e) {
            const debut = new Date(document.getElementById("dateDebut").value);
            const fin = new Date(document.getElementById("dateFin").value);
            if (debut > fin) {
                e.preventDefault();
                alert("❌ La date de début doit être avant la date de fin.");
            }
        });
    });
</script>
<script>
    window.addEventListener("DOMContentLoaded", () => {
        const toastEl = document.getElementById('liveToast');
        if (toastEl && toastEl.innerText.trim().length > 0) {
            const toast = new bootstrap.Toast(toastEl, { delay: 4000 });
            toast.show();
        }
    });
</script>
<script>
    window.addEventListener("DOMContentLoaded", () => {
        const toastEl = document.querySelector(".toast");
        if (toastEl) {
            const toast = new bootstrap.Toast(toastEl, { delay: 4000 });
            toast.show();
        }
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
