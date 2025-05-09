<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer un Département</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<jsp:include page="../../includes/header.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="../../includes/admin-sidebar.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">

            <h2 class="mb-4">Ajouter un nouveau département</h2>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/departements${empty departement.id ? '/ajouter' : '/editer/'.concat(departement.id)}" method="post">
                <div class="mb-3">
                    <label for="nom" class="form-label">Nom du département <span class="text-danger">*</span></label>
                    <input type="text" id="nom" name="nom" class="form-control" value="${departement.nom}" required>
                </div>

                <div class="text-end">
                    <a href="${pageContext.request.contextPath}/admin/departements" class="btn btn-secondary">Annuler</a>
                    <button type="submit" class="btn btn-primary">${empty departement.id ? 'Créer' : 'Enregistrer'}</button>
                </div>
            </form>


        </main>
    </div>
</div>

<jsp:include page="../../includes/footer.jsp" />
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
