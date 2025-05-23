<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : 'fr'}"/>
<fmt:setBundle basename="i18n.messages"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Erreur serveur - Gestion RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="bg-light">
<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow-lg border-0 rounded-lg mt-5 error-card">
                <div class="card-body text-center p-5">
                    <div class="error-icon mb-4">
                        <i class="bi bi-x-octagon-fill text-danger" style="font-size: 5rem;"></i>
                    </div>
                    <h1 class="display-1 fw-bold text-danger">500</h1>
                    <h2 class="mb-3"><fmt:message key="error.internalTitle" /></h2>
                    <p class="mb-4 text-muted"><fmt:message key="error.internalDescription" /></p>

                    <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
                        <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg px-4 gap-3">
                            <i class="bi bi-house-door-fill me-2"></i><fmt:message key="error.home" />
                        </a>
                        <button onclick="window.history.back()" class="btn btn-outline-secondary btn-lg px-4">
                            <i class="bi bi-arrow-left me-2"></i><fmt:message key="error.back" />
                        </button>
                    </div>

                    <div class="mt-4 pt-3 border-top">
                        <p class="small text-muted mb-0"><fmt:message key="error.contact" /></p>
                        <p class="small text-muted">
                            <i class="bi bi-envelope me-1"></i> support@gestionrh.com
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Animation de la carte d'erreur
    document.addEventListener('DOMContentLoaded', function() {
        const errorCard = document.querySelector('.error-card');
        errorCard.classList.add('animate-fade-in');

        // Animation de l'icône d'erreur
        const errorIcon = document.querySelector('.error-icon');
        errorIcon.style.animation = 'shake 1s';

        // Ajouter le style d'animation
        const style = document.createElement('style');
        style.textContent = `
                @keyframes shake {
                    0%, 100% { transform: translateX(0); }
                    10%, 30%, 50%, 70%, 90% { transform: translateX(-5px); }
                    20%, 40%, 60%, 80% { transform: translateX(5px); }
                }
            `;
        document.head.appendChild(style);
    });
</script>
</body>
</html>
