<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mon Profil</title>

    <!-- Liens CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">

    <!-- Dark mode ciblé sur #mainContent -->
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

        #mainContent.dark-mode a {
            color: #66b2ff;
        }

        #mainContent {
            transition: background-color 0.3s ease, color 0.3s ease;
        }
    </style>
</head>
<body>

<jsp:include page="../includes/header.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/admin-sidebar.jsp" />

        <!-- ✅ Mode sombre appliqué ici uniquement -->
        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <h2>Mon Profil</h2>

            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/admin/profil">
                <div class="mb-3">
                    <label class="form-label">Nom</label>
                    <input type="text" name="nom" class="form-control" value="${admin.nom}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Prénom</label>
                    <input type="text" name="prenom" class="form-control" value="${admin.prenom}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" class="form-control" value="${admin.email}" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mot de passe (laisser vide pour ne pas modifier)</label>
                    <input type="password" name="motDePasse" class="form-control">
                </div>
                <div class="text-end">
                    <button type="submit" class="btn btn-primary">Mettre à jour</button>
                </div>
            </form>
        </main>
    </div>
</div>



<!-- ✅ Script dark mode pour #mainContent -->
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
