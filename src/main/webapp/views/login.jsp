<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // ✅ Forcer la langue par défaut en français si non définie
    if (session.getAttribute("lang") == null) {
        session.setAttribute("lang", "fr");
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion RH</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Animate.css pour l'effet fadeIn -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" />

    <style>
        body {
            background: url('${pageContext.request.contextPath}/assets/images/background-logo.jpg') no-repeat center center fixed;
            background-size: cover;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', sans-serif;
        }

        .login-card {
            background-color: rgba(255, 255, 255, 0.95);
            padding: 2rem;
            border-radius: 1rem;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 420px;
        }

        .login-header {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .login-header h3 {
            color: #0d6efd;
        }

        .form-floating label {
            color: #6c757d;
        }

        .btn-primary {
            background-color: #0d6efd;
            border-color: #0d6efd;
        }

        .btn-primary:hover {
            background-color: #0056d2;
        }

        .alert {
            margin-top: 1rem;
        }
    </style>
</head>
<body>
<div class="login-card animate__animated animate__fadeIn">
    <div class="login-header">
        <img src="${pageContext.request.contextPath}/assets/images/logo.jpg" alt="Logo" width="60" class="mb-2">
        <h3>Gestion RH</h3>
    </div>

    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
                ${error}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-floating mb-3">
            <input class="form-control" id="email" name="email" type="email" placeholder="email" required />
            <label for="email">Adresse email</label>
        </div>
        <div class="form-floating mb-3">
            <input class="form-control" id="password" name="password" type="password" placeholder="Mot de passe" required />
            <label for="password">Mot de passe</label>
        </div>
        <div class="d-grid">
            <button class="btn btn-primary btn-lg" type="submit">Se connecter</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
