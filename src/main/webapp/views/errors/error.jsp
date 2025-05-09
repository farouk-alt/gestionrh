<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Erreur - Gestion RH</title>
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
            <i class="bi bi-exclamation-circle-fill text-danger" style="font-size: 5rem;"></i>
          </div>
          <h1 class="mb-3">Une erreur est survenue</h1>
          <p class="mb-4 text-muted">Nous sommes désolés, mais une erreur s'est produite lors du traitement de votre demande.</p>

          <c:if test="${pageContext.errorData.statusCode != null}">
            <div class="alert alert-danger">
              <p><strong>Code d'erreur:</strong> ${pageContext.errorData.statusCode}</p>
              <c:if test="${pageContext.exception != null}">
                <p><strong>Type d'erreur:</strong> ${pageContext.exception.getClass().getName()}</p>
                <p><strong>Message:</strong> ${pageContext.exception.message}</p>
              </c:if>
            </div>
          </c:if>

          <div class="d-grid gap-2 d-sm-flex justify-content-sm-center">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-lg px-4 gap-3">
              <i class="bi bi-house-door-fill me-2"></i>Retour à l'accueil
            </a>
            <button onclick="window.history.back()" class="btn btn-outline-secondary btn-lg px-4">
              <i class="bi bi-arrow-left me-2"></i>Page précédente
            </button>
          </div>

          <div class="mt-4 pt-3 border-top">
            <p class="small text-muted mb-0">Si le problème persiste, veuillez contacter l'administrateur système.</p>
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
    errorIcon.style.animation = 'pulse 2s infinite';

    // Ajouter le style d'animation
    const style = document.createElement('style');
    style.textContent = `
                @keyframes pulse {
                    0% { transform: scale(1); }
                    50% { transform: scale(1.1); }
                    100% { transform: scale(1); }
                }
            `;
    document.head.appendChild(style);
  });
</script>
</body>
</html>
