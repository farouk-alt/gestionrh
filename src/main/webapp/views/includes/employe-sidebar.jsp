<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Feuilles de style -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>

<style>
    /* Uniformiser la hauteur et l'arrière-plan */
    #sidebarMenu {
        height: 100vh;
        border-right: 1px solid #ddd;
        background-color: #f8f9fa;
    }

    /* Style des liens */
    #sidebarMenu .nav-link {
        font-size: 1rem;
        color: #333;
        padding: 0.75rem 1rem;
        margin: 0.2rem 0.5rem;
        border-radius: 0.375rem;
        transition: all 0.2s ease-in-out;
    }

    #sidebarMenu .nav-link i {
        font-size: 1.1rem;
        vertical-align: middle;
    }

    /* Hover */
    #sidebarMenu .nav-link:hover {
        background-color: rgba(13, 110, 253, 0.1);
        color: #0d6efd;
        transform: translateX(4px);
    }

    /* Active */
    #sidebarMenu .nav-link.active {
        font-weight: bold;
        background-color: #0d6efd;
        color: white !important;
    }

    #sidebarMenu .nav-link.active i {
        color: white;
    }
</style>

<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block sidebar collapse animate__animated animate__fadeInLeft">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">

            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/dashboard' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/dashboard">
                    <i class="bi bi-speedometer2 me-2"></i> Tableau de bord
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/mes-conges' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/mes-conges">
                    <i class="bi bi-calendar-check me-2"></i> Mes Demandes
                </a>
            </li>


            <li class="nav-item">
                <a class="nav-link ${pageContext.request.servletPath == '/employe/profil' ? 'active' : ''}"
                   href="${pageContext.request.contextPath}/employe/profil">
                    <i class="bi bi-person-circle me-2"></i> Mon Profil
                </a>
            </li>

        </ul>
    </div>
</nav>
