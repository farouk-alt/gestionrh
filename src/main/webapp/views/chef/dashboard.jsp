<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Chef</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Animate.css -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <style>
        main {
            z-index: 1;
            position: relative;
        }
    </style>
</head>
<body>

<jsp:include page="../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
                <jsp:include page="../includes/chef-sidebar.jsp" />
        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
            <h2 class="mb-4">📊 Tableau de bord - Chef de Département</h2>

            <!-- Statistiques -->
            <div class="row g-4">
                <div class="col-md-3 animate__animated animate__fadeInUp">
                    <div class="card text-white bg-primary shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Total des demandes</h5>
                            <p class="card-text fs-4">${total}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 animate__animated animate__fadeInUp animate__delay-1s">
                    <div class="card text-white bg-success shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Demandes acceptées</h5>
                            <p class="card-text fs-4">${acceptees}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 animate__animated animate__fadeInUp animate__delay-2s">
                    <div class="card text-white bg-danger shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">Demandes refusées</h5>
                            <p class="card-text fs-4">${refusees}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3 animate__animated animate__fadeInUp animate__delay-3s">
                    <div class="card text-white bg-warning shadow-sm">
                        <div class="card-body">
                            <h5 class="card-title">En attente</h5>
                            <p class="card-text fs-4">${enAttente}</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Graphique ou Message -->
            <c:choose>
                <c:when test="${total == 0}">
                    <div class="alert alert-info mt-4">
                        Il n'y a actuellement aucune demande de congé à afficher dans le graphique.
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="row mt-4 dashboard-graph-section">
                        <div class="col-lg-8">
                            <div class="card shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title">État des demandes de congé</h5>
                                    <canvas id="etatChart" height="100"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                    <script>
                        const ctx = document.getElementById('etatChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: ['Acceptées', 'Refusées', 'En attente'],
                                datasets: [{
                                    label: 'Nombre de demandes',
                                    data: [${acceptees}, ${refusees}, ${enAttente}],
                                    backgroundColor: ['#198754', '#dc3545', '#ffc107'],
                                    borderColor: ['#198754', '#dc3545', '#ffc107'],
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        ticks: { stepSize: 1 }
                                    }
                                }
                            }
                        });
                    </script>
                </c:otherwise>
            </c:choose>
        </main>
    </div>
</div>

<jsp:include page="../includes/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
