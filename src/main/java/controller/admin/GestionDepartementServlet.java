//package controller.admin;
//
//import java.io.IOException;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import model.Departement;
//import model.Employe;
//import service.ChefService;
//import service.DepartementService;
//import service.EmployeService;
//
////@WebServlet("/admin/departements/*")
//public class GestionDepartementServlet extends HttpServlet {
//
//    private final DepartementService departementService = new DepartementService();
//    private final EmployeService employeService = new EmployeService();
//    private final ChefService chefService = new ChefService();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            // Liste des départements
//            List<Departement> departements = departementService.getAllDepartementsWithChef();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/departements/liste.jsp").forward(request, response);
//        } else if (pathInfo.equals("/ajouter")) {
//            // Formulaire d'ajout de département
//            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//        } else if (pathInfo.startsWith("/editer/")) {
//            // Formulaire d'édition de département
//            try {
//                Long id = Long.parseLong(pathInfo.substring(8));
//                Departement departement = departementService.getDepartementWithChefById(id);
//
//                if (departement != null) {
//                    request.setAttribute("departement", departement);
//                    request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/admin/departements");
//                }
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else if (pathInfo.startsWith("/supprimer/")) {
//            // Suppression de département
//            try {
//                Long id = Long.parseLong(pathInfo.substring(11));
//                departementService.deleteDepartement(id);
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else if (pathInfo.startsWith("/details/")) {
//            try {
//                Long id = Long.parseLong(pathInfo.substring(9));
//                Departement departement = departementService.getDepartementWithEmployesAndChefById(id);
//
//                if (departement != null) {
//                    request.setAttribute("departement", departement);
//                    request.getRequestDispatcher("/views/admin/departements/details.jsp").forward(request, response);
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/admin/departements");
//                }
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else if (pathInfo.startsWith("/retirer-chef/")) {
//            try {
//                Long employeId = Long.parseLong(pathInfo.substring(15));
//                chefService.retirerChef(employeId);
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            } catch (Exception e) {
//                e.printStackTrace();
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        }
//        else if (pathInfo.startsWith("/chef/")) {
//            // Gestion du chef de département
//            try {
//                Long id = Long.parseLong(pathInfo.substring(6));
//                Departement departement = departementService.getDepartementById(id);
//
//                if (departement != null) {
//                    // Récupérer tous les employés pour le choix du chef
//                    List<Employe> employes = employeService.getAllEmployes();
//                    request.setAttribute("departement", departement);
//                    request.setAttribute("employes", employes);
//                    request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/admin/departements");
//                }
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else if (pathInfo.startsWith("/historique/")) {
//        try {
//            Long id = Long.parseLong(pathInfo.substring(12));
//            Departement departement = departementService.getDepartementByIdWithChefs(id); // ✅ méthode corrigée
//
//            if (departement != null) {
//                request.setAttribute("departement", departement);
//                request.getRequestDispatcher("/views/admin/departements/historique.jsp").forward(request, response);
//            } else {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } catch (NumberFormatException e) {
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//        }
//    }
//
//        else {
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/ajouter")) {
//            // Ajout d'un nouveau département
//            ajouterDepartement(request, response);
//        } else if (pathInfo.startsWith("/editer/")) {
//            // Mise à jour d'un département existant
//            try {
//                Long id = Long.parseLong(pathInfo.substring(8));
//                mettreAJourDepartement(id, request, response);
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else if (pathInfo.startsWith("/chef/")) {
//            // Nomination d'un chef de département
//            try {
//                Long departementId = Long.parseLong(pathInfo.substring(6));
//                nommerChef(departementId, request, response);
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/departements");
//            }
//        } else {
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//        }
//    }
//
//    private void ajouterDepartement(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Récupérer les paramètres du formulaire
//        String nom = request.getParameter("nom");
//        String description = request.getParameter("description");
//
//        // Valider les données
//        if (nom == null || nom.trim().isEmpty()) {
//            request.setAttribute("error", "Le nom du département est obligatoire");
//            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier si le nom existe déjà
//        if (departementService.nomExists(nom)) {
//            request.setAttribute("error", "Ce nom de département existe déjà");
//            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Créer le département
//        Departement departement = new Departement(nom, description);
//
//        // Sauvegarder le département
//        departementService.saveDepartement(departement);
//
//        // Rediriger vers la liste des départements
//        response.sendRedirect(request.getContextPath() + "/admin/departements");
//    }
//
//    private void mettreAJourDepartement(Long id, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Récupérer le département existant
//        Departement departement = departementService.getDepartementById(id);
//
//        if (departement == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//            return;
//        }
//
//        // Récupérer les paramètres du formulaire
//        String nom = request.getParameter("nom");
//        String description = request.getParameter("description");
//
//        // Valider les données
//        if (nom == null || nom.trim().isEmpty()) {
//            request.setAttribute("error", "Le nom du département est obligatoire");
//            request.setAttribute("departement", departement);
//            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier si le nom existe déjà (sauf pour ce département)
//        Departement existingDepartement = departementService.findByNom(nom);
//        if (existingDepartement != null && !existingDepartement.getId().equals(id)) {
//            request.setAttribute("error", "Ce nom de département existe déjà");
//            request.setAttribute("departement", departement);
//            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Mettre à jour les données du département
//        departement.setNom(nom);
//        departement.setDescription(description);
//
//        // Sauvegarder les modifications
//        departementService.updateDepartement(departement);
//
//        // Rediriger vers la liste des départements
//        response.sendRedirect(request.getContextPath() + "/admin/departements");
//    }
//
//    private void nommerChef(Long departementId, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Récupérer le département
//        Departement departement = departementService.getDepartementById(departementId);
//
//        if (departement == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//            return;
//        }
//
//        // Récupérer l'employé sélectionné
//        String employeIdStr = request.getParameter("employeId");
//
//        if (employeIdStr == null || employeIdStr.trim().isEmpty()) {
//            request.setAttribute("error", "Veuillez sélectionner un employé");
//            request.setAttribute("departement", departement);
//            List<Employe> employes = employeService.getAllEmployes();
//            request.setAttribute("employes", employes);
//            request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            Long employeId = Long.parseLong(employeIdStr);
//
//            // Nommer le chef
//            chefService.nommerChef(employeId, departementId);
//
//            // Rediriger vers la liste des départements
//            response.sendRedirect(request.getContextPath() + "/admin/departements");
//
//        } catch (NumberFormatException e) {
//            request.setAttribute("error", "ID d'employé invalide");
//            request.setAttribute("departement", departement);
//            List<Employe> employes = employeService.getAllEmployes();
//            request.setAttribute("employes", employes);
//            request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
//        }
//    }
//}
// ✅ GestionDepartementServlet.java – corrigée selon les entités, services et logique actuelle

// ✅ GestionDepartementServlet.java – corrigée selon les entités, services et logique actuelle

package controller.admin;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Departement;
import model.Employe;
import service.ChefService;
import service.DepartementService;
import service.EmployeService;

//@WebServlet("/admin/departements/*")
public class GestionDepartementServlet extends HttpServlet {

    private final DepartementService departementService = new DepartementService();
    private final EmployeService employeService = new EmployeService();
    private final ChefService chefService = new ChefService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Departement> departements = departementService.getAllDepartementsWithChef();
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("/views/admin/departements/liste.jsp").forward(request, response);
        } else if (pathInfo.equals("/ajouter")) {
            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/editer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(8));
                Departement departement = departementService.getDepartementById(id);
                if (departement != null) {
                    request.setAttribute("departement", departement);
                    request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/departements");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/supprimer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(11));
                departementService.deleteDepartement(id);
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/details/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(9));
                Departement departement = departementService.getDepartementWithEmployesAndChefById(id);
                if (departement != null) {
                    request.setAttribute("departement", departement);
                    request.getRequestDispatcher("/views/admin/departements/details.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/departements");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/retirer-chef/")) {
            try {
                Long employeId = Long.parseLong(pathInfo.substring(15));
                chefService.retirerChef(employeId);
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/chef/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(6));
                Departement departement = departementService.getDepartementById(id);
                if (departement != null) {
                    List<Employe> employes = employeService.getAllEmployes();
                    request.setAttribute("departement", departement);
                    request.setAttribute("employes", employes);
                    request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/departements");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/historique/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(12));
                Departement departement = departementService.getDepartementByIdWithChefs(id);
                if (departement != null) {
                    request.setAttribute("departement", departement);
                    request.getRequestDispatcher("/views/admin/departements/historique.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/departements");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/departements");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/ajouter")) {
            ajouterDepartement(request, response);
        } else if (pathInfo.startsWith("/editer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(8));
                mettreAJourDepartement(id, request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else if (pathInfo.startsWith("/chef/")) {
            try {
                Long departementId = Long.parseLong(pathInfo.substring(6));
                nommerChef(departementId, request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/departements");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/departements");
        }
    }

    private void ajouterDepartement(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        if (nom == null || nom.trim().isEmpty()) {
            request.setAttribute("error", "Le nom du département est obligatoire");
            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
            return;
        }

        if (departementService.nomExists(nom)) {
            request.setAttribute("error", "Ce nom de département existe déjà");
            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
            return;
        }

        Departement departement = new Departement(); // plus de constructeur
        departement.setNom(nom); // description supprimée
        departementService.saveDepartement(departement);
        response.sendRedirect(request.getContextPath() + "/admin/departements");
    }

    private void mettreAJourDepartement(Long id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Departement departement = departementService.getDepartementById(id);
        if (departement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/departements");
            return;
        }

        String nom = request.getParameter("nom");
        String description = request.getParameter("description");

        if (nom == null || nom.trim().isEmpty()) {
            request.setAttribute("error", "Le nom du département est obligatoire");
            request.setAttribute("departement", departement);
            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
            return;
        }

        Departement existing = departementService.findByNom(nom);
        if (existing != null && !existing.getId().equals(id)) {
            request.setAttribute("error", "Ce nom de département existe déjà");
            request.setAttribute("departement", departement);
            request.getRequestDispatcher("/views/admin/departements/formulaire.jsp").forward(request, response);
            return;
        }

        departement.setNom(nom);
        departementService.updateDepartement(departement);
        response.sendRedirect(request.getContextPath() + "/admin/departements");
    }

    private void nommerChef(Long departementId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Departement departement = departementService.getDepartementById(departementId);
        if (departement == null) {
            response.sendRedirect(request.getContextPath() + "/admin/departements");
            return;
        }

        String employeIdStr = request.getParameter("employeId");
        if (employeIdStr == null || employeIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez sélectionner un employé");
            request.setAttribute("departement", departement);
            request.setAttribute("employes", employeService.getAllEmployes());
            request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
            return;
        }

        try {
            Long employeId = Long.parseLong(employeIdStr);
            chefService.nommerChef(employeId, departementId);
            response.sendRedirect(request.getContextPath() + "/admin/departements");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID d'employé invalide");
            request.setAttribute("departement", departement);
            request.setAttribute("employes", employeService.getAllEmployes());
            request.getRequestDispatcher("/views/admin/departements/chef.jsp").forward(request, response);
        }
    }
}
