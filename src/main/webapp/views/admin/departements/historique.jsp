<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../includes/header.jsp" %>
<%@ include file="../../includes/admin-sidebar.jsp" %>

<div class="main-content">
    <h2>🕓 Historique des chefs – ${departement.nom}</h2>

    <ul class="chef-history-list">
        <c:forEach var="chef" items="${departement.anciensChefs}">
            <li>
                👤 ${chef.employe.nomComplet} —
                nommé le <strong>${chef.dateNomination}</strong>
                →
                <c:choose>
                    <c:when test="${chef.dateFin != null}">jusqu’au <strong>${chef.dateFin}</strong></c:when>
                    <c:otherwise><strong class="actuel">(en fonction)</strong></c:otherwise>
                </c:choose>
            </li>
        </c:forEach>
    </ul>

    <a href="${pageContext.request.contextPath}/admin/departements" class="btn-retour">⬅ Retour</a>
</div>

<%@ include file="../../includes/footer.jsp" %>

<style>
    .main-content {
        padding: 20px;
        margin-left: 220px;
        font-family: Arial, sans-serif;
    }

    .chef-history-list {
        list-style: none;
        padding: 0;
    }

    .chef-history-list li {
        margin: 10px 0;
        background-color: #f9f9f9;
        border-left: 5px solid #007BFF;
        padding: 10px;
        border-radius: 4px;
    }

    .actuel {
        color: green;
    }

    .btn-retour {
        display: inline-block;
        margin-top: 20px;
        padding: 8px 15px;
        background-color: #007BFF;
        color: white;
        text-decoration: none;
        border-radius: 4px;
    }

    .btn-retour:hover {
        background-color: #0056b3;
    }
</style>
