<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="page.chef.history.title" /></title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Styles -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- Dark Mode ciblé sur #mainContent -->
    <style>
        #mainContent.dark-mode {
            background-color: #121212;
            color: #ffffff;
        }

        #mainContent.dark-mode h1,
        #mainContent.dark-mode h2,
        #mainContent.dark-mode p,
        #mainContent.dark-mode label,
        #mainContent.dark-mode .list-group-item {
            color: inherit !important;
        }

        #mainContent.dark-mode .list-group-item {
            background-color: #1f1f1f;
            border-color: #333;
        }

        #mainContent.dark-mode .btn-secondary {
            background-color: #444;
            border-color: #444;
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

        <!-- ✅ Mode sombre ciblé ici -->
        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <h2 class="mb-4"><h2 class="mb-4">🕓 <fmt:message key="page.chef.history.title" /> – ${departement.nom}</h2>

            <c:choose>
                <c:when test="${not empty departement.anciensChefs}">
                    <ul class="list-group">
                        <c:forEach var="chef" items="${departement.anciensChefs}">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                👤 <strong>${chef.employe.nomComplet}</strong>
                                <span>
                                    <fmt:message key="label.appointedOn" /> <strong><fmt:formatDate value="${chef.dateDebut}" pattern="yyyy-MM-dd" /></strong>
                                    →
                                    <c:choose>
                                        <c:when test="${chef.dateFin != null}">
                                            <fmt:message key="label.until" /> <strong><fmt:formatDate value="${chef.dateFin}" pattern="yyyy-MM-dd" /></strong>
                                        </c:when>
                                        <c:otherwise>
                                            <strong class="text-success">(<fmt:message key="label.inFunction" />)</strong>
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                <p class="text-muted"><fmt:message key="label.no.chefs.found" /></p>

            </c:otherwise>
            </c:choose>

            <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-secondary mt-3">
                ⬅ <fmt:message key="button.back" />
            </a>
        </main>
    </div>
</div>


<!-- Script Bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- ✅ Script dark mode ciblé sur #mainContent -->
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
