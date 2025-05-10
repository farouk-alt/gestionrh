//package controller.admin;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import model.Departement;
//import model.Employe;
//import service.DepartementService;
//import service.EmployeService;
//
////@WebServlet("/admin/employes/*")
//public class GestionEmployeServlet extends HttpServlet {
//
//    private final EmployeService employeService = new EmployeService();
//    private final DepartementService departementService = new DepartementService();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/")) {
//            // Liste des employés
//            List<Employe> employes = employeService.getAllEmployes();
//            request.setAttribute("employes", employes);
//            request.getRequestDispatcher("/views/admin/employes/liste.jsp").forward(request, response);
//            // EmployeServlet.java
//
//        } else if (pathInfo.equals("/ajouter")) {
//            // Pas d'employé préexistant
//            request.setAttribute("employe", new Employe()); // 🟢 important !
//            request.setAttribute("departements", departementService.getAllDepartements());
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//        }
//
//        else if (pathInfo.startsWith("/editer/")) {
//            // Formulaire d'édition d'employé
//            try {
//                Long id = Long.parseLong(pathInfo.substring(8));
//                Employe employe = employeService.getEmployeById(id);
//
//                if (employe != null) {
//                    List<Departement> departements = departementService.getAllDepartements();
//                    request.setAttribute("employe", employe);
//                    request.setAttribute("departements", departements);
//                    request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//                } else {
//                    response.sendRedirect(request.getContextPath() + "/admin/employes");
//                }
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/employes");
//            }
//        } else if (pathInfo.startsWith("/supprimer/")) {
//        try {
//            Long id = Long.parseLong(pathInfo.substring(11));
//
//            boolean deleted = employeService.deleteEmploye(id);
//            if (!deleted) {
//                request.getSession().setAttribute("error", "Impossible de supprimer cet employé : il est chef d’un département.");
//            }
//
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//
//        } catch (NumberFormatException e) {
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//        }
//    }else if (pathInfo.startsWith("/departement/")) {
//            try {
//                Long depId = Long.parseLong(pathInfo.substring(12));
//                List<Employe> employes = employeService.getEmployesByDepartement(depId);
//                request.setAttribute("employes", employes);
//                request.getRequestDispatcher("/views/admin/employes/liste.jsp").forward(request, response);
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/employes");
//            }
//        } else {
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//        }
//    }
//
//    private void extracted(String pathInfo) {
//        Long id = Long.parseLong(pathInfo.substring(11));
//        employeService.deleteEmploye(id);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/ajouter")) {
//            // Ajout d'un nouvel employé
//            ajouterEmploye(request, response);
//        } else if (pathInfo.startsWith("/editer/")) {
//            // Mise à jour d'un employé existant
//            try {
//                Long id = Long.parseLong(pathInfo.substring(8));
//                mettreAJourEmploye(id, request, response);
//            } catch (NumberFormatException e) {
//                response.sendRedirect(request.getContextPath() + "/admin/employes");
//            }
//        } else {
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//        }
//    }
//
//    private void ajouterEmploye(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Récupérer les paramètres du formulaire
//        String nom = request.getParameter("nom");
//        String prenom = request.getParameter("prenom");
//        String email = request.getParameter("email");
//        String motDePasse = request.getParameter("motDePasse");
//        String dateEmbaucheStr = request.getParameter("dateEmbauche");
//        String salaireStr = request.getParameter("salaire");
//        String quotaCongeStr = request.getParameter("quotaConge");
//        String role = request.getParameter("role");
//        String departementIdStr = request.getParameter("departementId");
//
//        // Valider les données
//        if (nom == null || prenom == null || email == null || motDePasse == null ||
//            dateEmbaucheStr == null || salaireStr == null || quotaCongeStr == null ||
//            role == null || departementIdStr == null) {
//
//            request.setAttribute("error", "Tous les champs sont obligatoires");
//            List<Departement> departements = departementService.getAllDepartements();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier si l'email existe déjà
//        if (employeService.emailExists(email)) {
//            request.setAttribute("error", "Cet email est déjà utilisé");
//
//            // Remplir manuellement un employé avec les champs saisis
//            Employe employeForm = new Employe();
//            employeForm.setNom(nom);
//            employeForm.setPrenom(prenom);
//            employeForm.setEmail(email);
//            employeForm.setMotDePasse(""); // ne pas renvoyer le mot de passe
//            employeForm.setRole(role);
//
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                employeForm.setDateEmbauche(sdf.parse(dateEmbaucheStr));
//                employeForm.setSalaire(Double.parseDouble(salaireStr));
//                employeForm.setQuotaConge(Integer.parseInt(quotaCongeStr));
//
//                Long departementId = Long.parseLong(departementIdStr);
//                Departement d = departementService.getDepartementById(departementId);
//                employeForm.setDepartement(d);
//
//            } catch (Exception ignored) {}
//
//            request.setAttribute("employe", employeForm);
//            request.setAttribute("departements", departementService.getAllDepartements());
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//
//        try {
//            // Convertir les données
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dateEmbauche = sdf.parse(dateEmbaucheStr);
//            Double salaire = Double.parseDouble(salaireStr);
//            Integer quotaConge = Integer.parseInt(quotaCongeStr);
//            Long departementId = Long.parseLong(departementIdStr);
//
//            // Créer l'employé
//            Employe employe = new Employe(nom, prenom, email, motDePasse, dateEmbauche, salaire, quotaConge, role);
//
//            // Associer au département
//            Departement departement = departementService.getDepartementById(departementId);
//            if (departement != null) {
//                employe.setDepartement(departement);
//            }
//
//            // Sauvegarder l'employé
//            employeService.saveEmploye(employe);
//
//            // Rediriger vers la liste des employés
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//
//        } catch (ParseException | NumberFormatException e) {
//            request.setAttribute("error", "Format de données invalide");
//            List<Departement> departements = departementService.getAllDepartements();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//        }
//    }
//
//    private void mettreAJourEmploye(Long id, HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Récupérer l'employé existant
//        Employe employe = employeService.getEmployeById(id);
//
//        if (employe == null) {
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//            return;
//        }
//
//        // Récupérer les paramètres du formulaire
//        String nom = request.getParameter("nom");
//        String prenom = request.getParameter("prenom");
//        String email = request.getParameter("email");
//        String motDePasse = request.getParameter("motDePasse");
//        String dateEmbaucheStr = request.getParameter("dateEmbauche");
//        String salaireStr = request.getParameter("salaire");
//        String quotaCongeStr = request.getParameter("quotaConge");
//        String role = request.getParameter("role");
//        String departementIdStr = request.getParameter("departementId");
//
//        // Valider les données
//        if (nom == null || prenom == null || email == null ||
//            dateEmbaucheStr == null || salaireStr == null || quotaCongeStr == null ||
//            role == null || departementIdStr == null) {
//
//            request.setAttribute("error", "Tous les champs sont obligatoires");
//            request.setAttribute("employe", employe);
//            List<Departement> departements = departementService.getAllDepartements();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        // Vérifier si l'email existe déjà (sauf pour cet employé)
//        Employe existingEmploye = employeService.findByEmail(email);
//        if (existingEmploye != null && !existingEmploye.getId().equals(id)) {
//            request.setAttribute("error", "Cet email est déjà utilisé");
//            request.setAttribute("employe", employe);
//            List<Departement> departements = departementService.getAllDepartements();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // Mettre à jour les données de l'employé
//            employe.setNom(nom);
//            employe.setPrenom(prenom);
//            employe.setEmail(email);
//
//            // Mettre à jour le mot de passe seulement s'il est fourni
//            if (motDePasse != null && !motDePasse.isEmpty()) {
//                employe.setMotDePasse(motDePasse);
//            }
//
//            // Convertir les autres données
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date dateEmbauche = sdf.parse(dateEmbaucheStr);
//            Double salaire = Double.parseDouble(salaireStr);
//            Integer quotaConge = Integer.parseInt(quotaCongeStr);
//            Long departementId = Long.parseLong(departementIdStr);
//
//            employe.setDateEmbauche(dateEmbauche);
//            employe.setSalaire(salaire);
//            employe.setQuotaConge(quotaConge);
//            employe.setRole(role);
//
//            // Mettre à jour le département
//            Departement departement = departementService.getDepartementById(departementId);
//            if (departement != null) {
//                employe.setDepartement(departement);
//            }
//
//            // Sauvegarder les modifications
//            employeService.updateEmploye(employe);
//
//            // Rediriger vers la liste des employés
//            response.sendRedirect(request.getContextPath() + "/admin/employes");
//
//        } catch (ParseException | NumberFormatException e) {
//            request.setAttribute("error", "Format de données invalide");
//            request.setAttribute("employe", employe);
//            List<Departement> departements = departementService.getAllDepartements();
//            request.setAttribute("departements", departements);
//            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
//        }
//    }
//}
// ✅ GestionEmployeServlet.java — corrigée selon l'entité Employe actuelle (sans salaire, quotaConge, ni role)

package controller.admin;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Departement;
import model.Employe;
import service.DepartementService;
import service.EmployeService;

//@WebServlet("/admin/employes/*")
public class GestionEmployeServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();
    private final DepartementService departementService = new DepartementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Employe> employes = employeService.getAllEmployesAvecStatutChef();
            request.setAttribute("employes", employes);
            request.getRequestDispatcher("/views/admin/employes/liste.jsp").forward(request, response);
        } else if (pathInfo.equals("/ajouter")) {
            request.setAttribute("employe", new Employe());
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
        } else if (pathInfo.startsWith("/editer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(8));
                Employe employe = employeService.getEmployeById(id);
                if (employe != null) {
                    request.setAttribute("employe", employe);
                    request.setAttribute("departements", departementService.getAllDepartements());
                    request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/employes");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/employes");
            }
        } else if (pathInfo.startsWith("/supprimer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(11));
                boolean deleted = employeService.deleteEmploye(id);
                if (!deleted) {
                    request.getSession().setAttribute("error", "Impossible de supprimer cet employé : il est chef d’un département.");
                }
                response.sendRedirect(request.getContextPath() + "/admin/employes");
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/employes");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/employes");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/ajouter")) {
            ajouterEmploye(request, response);
        } else if (pathInfo.startsWith("/editer/")) {
            try {
                Long id = Long.parseLong(pathInfo.substring(8));
                mettreAJourEmploye(id, request, response);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/admin/employes");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/employes");
        }
    }

    private void ajouterEmploye(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String nomUtilisateur = request.getParameter("nomUtilisateur");
        String motDePasse = request.getParameter("motDePasse");
        String dateEmbaucheStr = request.getParameter("dateCreation");
        String soldeCongeStr = request.getParameter("soldeConge");
        String adminStr = request.getParameter("admin");
        String departementIdStr = request.getParameter("departementId");

        if (nom == null || prenom == null || email == null || nomUtilisateur == null || motDePasse == null ||
                dateEmbaucheStr == null || soldeCongeStr == null || departementIdStr == null) {
            request.setAttribute("error", "Tous les champs sont obligatoires");
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
            return;
        }

        if (employeService.emailExists(email)) {
            request.setAttribute("error", "Cet email est déjà utilisé");
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateEmbauche = sdf.parse(dateEmbaucheStr);
            int soldeConge = Integer.parseInt(soldeCongeStr);
            Long departementId = Long.parseLong(departementIdStr);
            boolean admin = "true".equalsIgnoreCase(adminStr);

            Employe employe = new Employe();
            employe.setNom(nom);
            employe.setPrenom(prenom);
            employe.setEmail(email);
            employe.setNomUtilisateur(nomUtilisateur);
            employe.setMotDePasse(motDePasse);
            employe.setDateCreation(dateEmbauche);
            employe.setSoldeConge(soldeConge);
            employe.setAdmin(admin);
            employe.setDateCreation(new Date());

            Departement departement = departementService.getDepartementById(departementId);
            employe.setDepartement(departement);

            employeService.saveEmploye(employe);
            response.sendRedirect(request.getContextPath() + "/admin/employes");
        } catch (ParseException | NumberFormatException e) {
            request.setAttribute("error", "Format de données invalide");
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
        }
    }

    private void mettreAJourEmploye(Long id, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employe employe = employeService.getEmployeById(id);
        if (employe == null) {
            response.sendRedirect(request.getContextPath() + "/admin/employes");
            return;
        }

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String nomUtilisateur = request.getParameter("nomUtilisateur");
        String motDePasse = request.getParameter("motDePasse");
        String dateEmbaucheStr = request.getParameter("dateCreation");
        String soldeCongeStr = request.getParameter("soldeConge");
        String adminStr = request.getParameter("admin");
        String departementIdStr = request.getParameter("departementId");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateEmbauche = sdf.parse(dateEmbaucheStr);
            int soldeConge = Integer.parseInt(soldeCongeStr);
            Long departementId = Long.parseLong(departementIdStr);
            boolean admin = "true".equalsIgnoreCase(adminStr);

            employe.setNom(nom);
            employe.setPrenom(prenom);
            employe.setEmail(email);
            employe.setNomUtilisateur(nomUtilisateur);
            if (motDePasse != null && !motDePasse.isEmpty()) {
                employe.setMotDePasse(motDePasse);
            }
            employe.setDateCreation(dateEmbauche);
            employe.setSoldeConge(soldeConge);
            employe.setAdmin(admin);

            Departement departement = departementService.getDepartementById(departementId);
            employe.setDepartement(departement);

            employeService.updateEmploye(employe);
            response.sendRedirect(request.getContextPath() + "/admin/employes");

        } catch (ParseException | NumberFormatException e) {
            request.setAttribute("error", "Format de données invalide");
            request.setAttribute("employe", employe);
            request.setAttribute("departements", departementService.getAllDepartements());
            request.getRequestDispatcher("/views/admin/employes/formulaire.jsp").forward(request, response);
        }
    }
}
