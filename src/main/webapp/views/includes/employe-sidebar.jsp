<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="i18n.messages" />

<!-- Feuilles de style -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block sidebar collapse animate__animated animate__fadeInLeft">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/dashboard' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/dashboard">
                    <i class="bi bi-speedometer2 me-2"></i> <fmt:message key="sidebar.dashboard" />
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/mes-conges' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/mes-conges">
                    <i class="bi bi-calendar-check me-2"></i> <fmt:message key="sidebar.myRequests" />
                </a>
            </li>
            <li class="nav-item position-relative">
                <a class="nav-link d-flex align-items-center" href="${pageContext.request.contextPath}/notifications">
                    <i class="bi bi-bell-fill me-2"></i> <fmt:message key="sidebar.notifications" />
                    <c:if test="${notifCount > 0}">
            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger animate__animated animate__bounceIn">
                    ${notifCount}
            </span>
                    </c:if>
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/profil' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/profil">
                    <i class="bi bi-person-circle me-2"></i> <fmt:message key="sidebar.profile" />
                </a>
            </li>

        </ul>
    </div>
</nav>
