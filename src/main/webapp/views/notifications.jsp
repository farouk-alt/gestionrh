<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />


<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="notifications.pageTitle"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        .notif-card {
            transition: all 0.3s ease;
        }
        .notif-card:hover {
            transform: scale(1.02);
        }
        .unread {
            background-color: #f8f9fa;
            border-left: 4px solid #0d6efd;
        }
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

<jsp:include page="includes/header.jsp"/>
<div class="container-fluid">
    <div class="row">
        <c:choose>
            <c:when test="${sessionScope.role == 'ADMIN'}">
                <jsp:include page='./includes/admin-sidebar.jsp'/>
            </c:when>
            <c:when test="${sessionScope.role == 'CHEF'}">
                <jsp:include page='./includes/chef-sidebar.jsp'/>
            </c:when>
            <c:otherwise>
                <jsp:include page='./includes/employe-sidebar.jsp'/>
            </c:otherwise>
        </c:choose>

        <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <fmt:message key="notifications.dateFormat" var="datePattern" />
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="bi bi-bell-fill me-2 text-warning"></i>
                    <fmt:message key="notifications.title" />
                </h2>

                <form method="post" action="${pageContext.request.contextPath}/notifications" class="mb-3">
                    <input type="hidden" name="markAll" value="true" />
                    <button type="submit" class="btn btn-outline-primary">
                        <i class="bi bi-check2-all"></i>
                        <fmt:message key="notifications.markAll" />
                    </button>

                </form>

            </div>

            <c:choose>
                <c:when test="${empty notifications}">
                    <div class="alert alert-info">
                        <fmt:message key="notifications.none" />
                    </div>

                </c:when>
                <c:otherwise>
                    <c:forEach var="n" items="${notifications}">
                        <div class="card mb-3 shadow-sm notif-card ${!n.lue ? 'unread' : ''}">
                            <div class="card-body d-flex justify-content-between align-items-center">
                                <div>
                                    <p class="mb-1"><strong>${n.contenu}</strong></p>
                                    <fmt:formatDate value="${n.dateNotification}" pattern="${datePattern}" />


                                </div>
                                <c:if test="${!n.lue}">
                                    <form method="post">
                                        <input type="hidden" name="id" value="${n.id}">
                                        <button type="submit" class="btn btn-outline-success btn-sm">
                                            <fmt:message key="notifications.markOne" />
                                        </button>
                                    </form>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Pagination JSP simple -->
                    <nav>
                        <ul class="pagination justify-content-center">
                            <c:forEach var="i" begin="1" end="${totalPages}" varStatus="loop">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/notifications?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
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
