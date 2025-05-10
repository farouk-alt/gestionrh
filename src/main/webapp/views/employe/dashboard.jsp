<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Employé</title>

    <!-- Bootstrap, Icons, Animations -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet"/>

    <!-- Ton CSS global employé -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<jsp:include page="../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
        <%-- Sidebar Employé --%>
        <jsp:include page="../includes/employe-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4 main-content animate__animated animate__fadeIn">
            <h2 class="mb-4"><i class="bi bi-person-fill text-primary me-2"></i>Bienvenue, <strong>${employe.prenom} ${employe.nom}</strong></h2>

            <div class="row g-4">
                <!-- Profil -->
                <div class="col-md-6 col-xl-3">
                    <div class="card border-start border-primary border-4 shadow-sm animate__animated animate__fadeInUp">
                        <div class="card-body d-flex align-items-center">
                            <i class="bi bi-person-circle text-primary fs-1 me-3"></i>
                            <div>
                                <h6 class="text-muted mb-0">Mon Profil</h6>
                                <p class="mb-0">${employe.email}</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Total demandes -->
                <div class="col-md-6 col-xl-3">
                    <div class="card border-start border-success border-4 shadow-sm animate__animated animate__fadeInUp">
                        <div class="card-body d-flex align-items-center">
                            <i class="bi bi-file-earmark-text text-success fs-1 me-3"></i>
                            <div>
                                <h6 class="text-muted mb-0">Total demandes</h6>
                                <h5 class="mb-0 fw-bold">${totalDemandes}</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- En attente -->
                <div class="col-md-6 col-xl-3">
                    <div class="card border-start border-warning border-4 shadow-sm animate__animated animate__fadeInUp">
                        <div class="card-body d-flex align-items-center">
                            <i class="bi bi-hourglass-split text-warning fs-1 me-3"></i>
                            <div>
                                <h6 class="text-muted mb-0">En attente</h6>
                                <h5 class="mb-0 fw-bold">${demandesEnAttente}</h5>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Refusées -->
                <div class="col-md-6 col-xl-3">
                    <div class="card border-start border-danger border-4 shadow-sm animate__animated animate__fadeInUp">
                        <div class="card-body d-flex align-items-center">
                            <i class="bi bi-x-circle text-danger fs-1 me-3"></i>
                            <div>
                                <h6 class="text-muted mb-0">Refusées</h6>
                                <h5 class="mb-0 fw-bold">${demandesRefusees}</h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Section future graphique ou historique -->
            <div class="dashboard-graph-section mt-5">
                <!-- tu peux insérer un graphique Chart.js ici plus tard -->
            </div>
        </main>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
