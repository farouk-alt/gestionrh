<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <!DOCTYPE html>
        <html lang="fr">
        <head>
            <meta charset="UTF-8">
            <title>${empty employe.id ? 'Ajouter' : 'Modifier'} un employé</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

            <!-- ✅ Dark mode ciblé sur mainContent -->
            <style>
                #mainContent.dark-mode {
                    background-color: #121212;
                    color: #ffffff;
                }

                #mainContent.dark-mode h1,
                #mainContent.dark-mode h2,
                #mainContent.dark-mode h3,
                #mainContent.dark-mode label,
                #mainContent.dark-mode p,
                #mainContent.dark-mode td,
                #mainContent.dark-mode th,
                #mainContent.dark-mode .card-title,
                #mainContent.dark-mode .card-body,
                #mainContent.dark-mode .card-header,
                #mainContent.dark-mode .card-footer {
                    color: inherit !important;
                }
                #mainContent {
                    transition: background-color 0.3s ease, color 0.3s ease;
                }
            </style>
        </head>
        <body>

        <jsp:include page="../../includes/header.jsp" />

        <div class="container-fluid">
            <div class="row">
                <jsp:include page="../../includes/admin-sidebar.jsp" />

                <!-- ✅ Ajout de l'ID pour le dark mode -->
                <main id="mainContent" class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                    <h1 class="h2 mb-4">${empty employe.id ? 'Ajouter' : 'Modifier'} un employé</h1>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/admin/employes${empty employe.id ? '/ajouter' : '/editer/'.concat(employe.id)}" method="post" class="needs-validation" novalidate>
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Nom *</label>
                                <input type="text" class="form-control" name="nom" value="${employe.nom}" required>
                                <div class="invalid-feedback">Champ requis</div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Prénom *</label>
                                <input type="text" class="form-control" name="prenom" value="${employe.prenom}" required>
                                <div class="invalid-feedback">Champ requis</div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email *</label>
                            <input type="email" class="form-control" name="email" value="${employe.email}" required>
                            <div class="invalid-feedback">Email invalide</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Nom d'utilisateur *</label>
                            <input type="text" class="form-control" name="nomUtilisateur" value="${employe.nomUtilisateur}" required>
                            <div class="invalid-feedback">Champ requis</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Mot de passe <c:if test="${empty employe.id}"><span class="text-danger">*</span></c:if></label>
                            <input type="password" class="form-control" name="motDePasse" <c:if test="${empty employe.id}">required</c:if>>
                            <div class="invalid-feedback">Champ requis si création</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Date d'embauche *</label>
                            <input type="date" class="form-control" name="dateCreation"
                                   value="<fmt:formatDate value='${employe.dateCreation}' pattern='yyyy-MM-dd'/>"
                                   required>
                            <div class="invalid-feedback">Date invalide</div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Solde de congés *</label>
                                <input type="number" class="form-control" name="soldeConge" value="${employe.soldeConge}" min="0" required>
                                <div class="invalid-feedback">Champ requis</div>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Rôle *</label>
                                <select class="form-select" name="admin" required>
                                    <option value="">Sélectionner un rôle</option>
                                    <option value="true" ${employe.admin ? 'selected' : ''}>Administrateur</option>
                                    <option value="false" ${!employe.admin ? 'selected' : ''}>Employé</option>
                                </select>
                                <div class="invalid-feedback">Champ requis</div>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Département *</label>
                            <select class="form-select" name="departementId" required>
                                <option value="">Sélectionner un département</option>
                                <c:forEach var="d" items="${departements}">
                                    <option value="${d.id}" ${employe.departement != null && employe.departement.id == d.id ? 'selected' : ''}>
                                            ${d.nom}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Champ requis</div>
                        </div>

                        <div class="text-end">
                            <a href="${pageContext.request.contextPath}/admin/employes" class="btn btn-secondary">Annuler</a>
                            <button type="submit" class="btn btn-primary">${empty employe.id ? 'Ajouter' : 'Enregistrer'}</button>
                        </div>
                    </form>
                </main>
            </div>
        </div>



        <!-- ✅ Script dark mode (main uniquement) -->
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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>
        </html>

    </div>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
