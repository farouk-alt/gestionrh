// ✅ DemandeCongeServlet.java – corrigée selon l'entité et le service DemandeConge

package controller.employe;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dao.ChefDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Chef;
import model.DemandeConge;
import model.Employe;
import service.DemandeCongeService;
import service.EmployeService;
import service.NotificationService;

//@WebServlet("/employe/conges/*")
public class DemandeCongeServlet extends HttpServlet {

    private final EmployeService employeService = new EmployeService();
    private final DemandeCongeService demandeCongeService = new DemandeCongeService();
    private final NotificationService notifService = new NotificationService();
    private final ChefDAO chefDAO = new ChefDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Long employeId = (Long) session.getAttribute("employeId");

        if (employeId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")) {
            List<DemandeConge> demandes = demandeCongeService.getDemandesParEmploye(employeId);
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/views/employe/conges/conges.jsp").forward(request, response);

        } else if (pathInfo.equals("/demander")) {
            Employe employe = employeService.getEmployeById(employeId);
            request.setAttribute("employe", employe);
            request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);

        } else if (pathInfo.startsWith("/voir/")) {
            try {
                Long demandeId = Long.parseLong(pathInfo.substring(6));
                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

                if (demande != null && demande.getEmploye().getId().equals(employeId)) {
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/details.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
            }
        } else if (pathInfo.startsWith("/modifier/")) {
            try {
                Long demandeId = Long.parseLong(pathInfo.substring(10));
                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

                if (demande != null && demande.getEmploye().getId().equals(employeId) && demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
        }
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession();
//        Long employeId = (Long) session.getAttribute("employeId");
//
//        if (employeId == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
//            return;
//        }
//
//        String pathInfo = request.getPathInfo();
//
//        if (pathInfo != null && pathInfo.equals("/demander")) {
//            String dateDebutStr = request.getParameter("dateDebut");
//            String dateFinStr = request.getParameter("dateFin");
//            String motif = request.getParameter("motif");
//
//            if (dateDebutStr == null || dateFinStr == null || motif == null) {
//                request.setAttribute("error", "Tous les champs sont obligatoires");
//                request.setAttribute("employe", employeService.getEmployeById(employeId));
//                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//                return;
//            }
//
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                Date dateDebut = sdf.parse(dateDebutStr);
//                Date dateFin = sdf.parse(dateFinStr);
//
//                if (dateDebut.after(dateFin)) {
//                    request.setAttribute("error", "La date de début doit être antérieure à la date de fin");
//                    request.setAttribute("employe", employeService.getEmployeById(employeId));
//                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//                    return;
//                }
//                if (demandeCongeService.hasPendingRequest(employeId)) {
//                    request.setAttribute("error", "❌ Vous avez déjà une demande en attente.");
//                    request.setAttribute("employe", employeService.getEmployeById(employeId));
//                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//                    return;
//                }
//
//                // 🔁 Conversion en LocalDate
//                LocalDate localDateDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                LocalDate localDateFin = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//// 🔍 Vérification des congés chevauchants
//                List<DemandeConge> congesChevauchants = demandeCongeService.getCongesChevauchants(employeId, localDateDebut, localDateFin);
//                int joursAAppliquer = demandeCongeService.calculerJoursAAppliquer(localDateDebut, localDateFin, congesChevauchants);
//
//// 👨‍💼 Vérification du solde
//                Employe employe = employeService.getEmployeById(employeId);
//                if (joursAAppliquer > employe.getSoldeConge()) {
//                    request.setAttribute("error", "❌ Solde insuffisant. Jours à déduire : " + joursAAppliquer);
//                    request.setAttribute("employe", employe);
//                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//                    return;
//                }
//
//// ✅ Création de la demande
//                boolean success = demandeCongeService.creerDemandeConge(employeId, dateDebut, dateFin, motif);
//                if (success) {
//                    employe.setSoldeConge(employe.getSoldeConge() - joursAAppliquer);
//                    employeService.updateEmploye(employe);
//
//                    Chef chef = chefDAO.getChefActuelByDepartementId(employe.getDepartement().getId());
//                    if (chef != null && chef.getEmploye() != null) {
//                        notifService.creerNotification(
//                                chef.getEmploye(),
//                                "📝 Nouvelle demande de congé de " + employe.getNomComplet(),
//                                "warning"
//                        );
//                    }
//
//                    session.setAttribute("toastMessage", "✅ Demande envoyée avec succès !");
//                    response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
//                    return;
//                } else {
//                    request.setAttribute("error", "❌ Une erreur est survenue.");
//                    request.setAttribute("employe", employe);
//                    request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//                }
//
//
//            } catch (ParseException e) {
//                request.setAttribute("error", "Format de date invalide");
//                request.setAttribute("employe", employeService.getEmployeById(employeId));
//                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
//            }
//
//        } else if (pathInfo.startsWith("/modifier/")) {
//            try {
//                Long demandeId = Long.parseLong(pathInfo.substring(10));
//                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);
//
//                if (demande != null && demande.getEmploye().getId().equals(employeId)
//                        && demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {
//
//                    String dateDebutStr = request.getParameter("dateDebut");
//                    String dateFinStr = request.getParameter("dateFin");
//                    String motif = request.getParameter("motif");
//
//                    if (dateDebutStr == null || dateFinStr == null || motif == null || motif.trim().isEmpty()) {
//                        request.setAttribute("error", "❌ Tous les champs sont obligatoires.");
//                        request.setAttribute("demande", demande);
//                        request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
//                        return;
//                    }
//
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    Date dateDebut = sdf.parse(dateDebutStr);
//                    Date dateFin = sdf.parse(dateFinStr);
//
//                    if (dateDebut.after(dateFin)) {
//                        request.setAttribute("error", "❌ La date de début doit être avant la date de fin.");
//                        request.setAttribute("demande", demande);
//                        request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
//                        return;
//                    }
//// 🔁 Conversion
//                    LocalDate newDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                    LocalDate newFin = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//
//// 🔍 Récupère les autres congés approuvés SAUF celui qu'on modifie
//                    List<DemandeConge> autresConges = demandeCongeService.getCongesChevauchants(employeId, newDebut, newFin)
//                            .stream().filter(c -> !c.getId().equals(demande.getId())).collect(Collectors.toList());
//
//// 🧮 Calcule les jours à déduire (si on élargit la période)
//                    int joursAAppliquer = demandeCongeService.calculerJoursAAppliquer(newDebut, newFin, autresConges);
//
//// ✅ Calcule la différence entre ancien et nouveau
//                    long anciensJours = (demande.getDateFin().getTime() - demande.getDateDebut().getTime()) / (1000 * 60 * 60 * 24) + 1;
//                    long nouveauxJours = joursAAppliquer;
//
//                    long difference = nouveauxJours - anciensJours;
//
//// Vérifie solde uniquement si on élargit
//                    Employe employe = employeService.getEmployeById(employeId);
//                    if (difference > 0 && difference > employe.getSoldeConge()) {
//                        request.setAttribute("error", "❌ Solde insuffisant pour la modification. Jours restants : " + employe.getSoldeConge());
//                        request.setAttribute("demande", demande);
//                        request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
//                        return;
//                    }
//
//// ✅ Mise à jour
//                    demande.setDateDebut(dateDebut);
//                    demande.setDateFin(dateFin);
//                    demande.setMotif(motif.trim());
//                    demande.setDateMiseAjour(new Date());
//                    demandeCongeService.update(demande);
//
//// Mise à jour du solde si nécessaire
//                    if (difference > 0) {
//                        employe.setSoldeConge(employe.getSoldeConge() - (int) difference);
//                        employeService.updateEmploye(employe);
//                    }
//
//                    session.setAttribute("toastMessage", "✅ Demande modifiée avec succès !");
//                    response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
//                    return;
//
//                    long joursDemandes = (dateFin.getTime() - dateDebut.getTime()) / (1000 * 60 * 60 * 24) + 1;
//                    Employe employe = employeService.getEmployeById(employeId);
//
//                    long anciensJours = (demande.getDateFin().getTime() - demande.getDateDebut().getTime()) / (1000 * 60 * 60 * 24) + 1;
//                    long difference = joursDemandes - anciensJours;
//
//                    if (difference > 0 && difference > employe.getSoldeConge()) {
//                        request.setAttribute("error", "❌ Solde de congé insuffisant pour cette modification.");
//                        request.setAttribute("demande", demande);
//                        request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
//                        return;
//                    }
//
//                    demande.setDateDebut(dateDebut);
//                    demande.setDateFin(dateFin);
//                    demande.setMotif(motif.trim());
//                    demande.setDateMiseAjour(new Date());
//
//                    demandeCongeService.update(demande);
//                    session.setAttribute("toastMessage", "✅ Demande modifiée avec succès !");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
//
//        } else if (pathInfo.startsWith("/supprimer/")) {
//            try {
//                Long demandeId = Long.parseLong(pathInfo.substring(11));
//                DemandeConge demande = demandeCongeService.getDemandeById(demandeId);
//
//                if (demande != null && demande.getEmploye().getId().equals(employeId) && demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {
//                    demandeCongeService.supprimerDemandeSiEnAttente(demandeId, employeId);
//                    session.setAttribute("toastMessage", "❌ Demande supprimée avec succès.");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
//        }
//    }
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    HttpSession session = request.getSession();
    Long employeId = (Long) session.getAttribute("employeId");

    if (employeId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }

    String pathInfo = request.getPathInfo();

    if (pathInfo != null && pathInfo.equals("/demander")) {
        String dateDebutStr = request.getParameter("dateDebut");
        String dateFinStr = request.getParameter("dateFin");
        String motif = request.getParameter("motif");

        if (dateDebutStr == null || dateFinStr == null || motif == null) {
            request.setAttribute("error", "Tous les champs sont obligatoires");
            request.setAttribute("employe", employeService.getEmployeById(employeId));
            request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = sdf.parse(dateDebutStr);
            Date dateFin = sdf.parse(dateFinStr);

            if (dateDebut.after(dateFin)) {
                request.setAttribute("error", "La date de début doit être antérieure à la date de fin");
                request.setAttribute("employe", employeService.getEmployeById(employeId));
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                return;
            }

            if (demandeCongeService.hasPendingRequest(employeId)) {
                request.setAttribute("error", "❌ Vous avez déjà une demande en attente.");
                request.setAttribute("employe", employeService.getEmployeById(employeId));
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                return;
            }

            LocalDate localDateDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateFin = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            List<DemandeConge> congesChevauchants = demandeCongeService.getCongesChevauchants(employeId, localDateDebut, localDateFin);
            int joursAAppliquer = demandeCongeService.calculerJoursAAppliquer(localDateDebut, localDateFin, congesChevauchants);

            Employe employe = employeService.getEmployeById(employeId);
            if (joursAAppliquer > employe.getSoldeConge()) {
                request.setAttribute("error", "❌ Solde insuffisant. Jours à déduire : " + joursAAppliquer);
                request.setAttribute("employe", employe);
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
                return;
            }

            boolean success = demandeCongeService.creerDemandeConge(employeId, dateDebut, dateFin, motif);
            if (success) {

                Chef chef = chefDAO.getChefActuelByDepartementId(employe.getDepartement().getId());
                if (chef != null && chef.getEmploye() != null) {
                    notifService.creerNotification(
                            chef.getEmploye(),
                            "📝 Nouvelle demande de congé de " + employe.getNomComplet(),
                            "warning"
                    );
                }

                session.setAttribute("toastMessage", "✅ Demande envoyée avec succès !");
                response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
            } else {
                request.setAttribute("error", "❌ Une erreur est survenue.");
                request.setAttribute("employe", employe);
                request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
            }

        } catch (ParseException e) {
            request.setAttribute("error", "Format de date invalide");
            request.setAttribute("employe", employeService.getEmployeById(employeId));
            request.getRequestDispatcher("/views/employe/conges/formulaire.jsp").forward(request, response);
        }

    }  else if (pathInfo.startsWith("/modifier/")) {
        try {
            Long demandeId = Long.parseLong(pathInfo.substring(10));
            DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

            if (demande != null && demande.getEmploye().getId().equals(employeId)
                    && demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {

                String dateDebutStr = request.getParameter("dateDebut");
                String dateFinStr = request.getParameter("dateFin");
                String motif = request.getParameter("motif");

                if (dateDebutStr == null || dateFinStr == null || motif == null || motif.trim().isEmpty()) {
                    request.setAttribute("error", "❌ Tous les champs sont obligatoires.");
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
                    return;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dateDebut = sdf.parse(dateDebutStr);
                Date dateFin = sdf.parse(dateFinStr);

                if (dateDebut.after(dateFin)) {
                    request.setAttribute("error", "❌ La date de début doit être avant la date de fin.");
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
                    return;
                }

                LocalDate newDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate newFin = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                List<DemandeConge> autresConges = demandeCongeService.getCongesChevauchants(employeId, newDebut, newFin)
                        .stream().filter(c -> !c.getId().equals(demande.getId())).collect(Collectors.toList());
                int joursAAppliquer = demandeCongeService.calculerJoursAAppliquer(newDebut, newFin, autresConges);

                int nouveauxJours = joursAAppliquer;
                int anciensJours = (int) ((demande.getDateFin().getTime() - demande.getDateDebut().getTime()) / (1000 * 60 * 60 * 24)) + 1;
                int difference = nouveauxJours - anciensJours;

                Employe employe = employeService.getEmployeById(employeId);

                // ✅ Vérification du solde AVANT déduction
                if (difference > 0 && difference > employe.getSoldeConge()) {
                    request.setAttribute("error", "❌ Solde insuffisant pour la modification. Jours restants : " + employe.getSoldeConge());
                    request.setAttribute("demande", demande);
                    request.getRequestDispatcher("/views/employe/conges/modifier.jsp").forward(request, response);
                    return;
                }

                // ✅ Appliquer la mise à jour de la demande
                demande.setDateDebut(dateDebut);
                demande.setDateFin(dateFin);
                demande.setMotif(motif.trim());
                demande.setDateMiseAjour(new Date());
                demandeCongeService.update(demande);

                session.setAttribute("toastMessage", "✅ Demande modifiée avec succès !");
                response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
        }
    }
 else if (pathInfo.startsWith("/supprimer/")) {
        try {
            Long demandeId = Long.parseLong(pathInfo.substring(11));
            DemandeConge demande = demandeCongeService.getDemandeById(demandeId);

            if (demande != null && demande.getEmploye().getId().equals(employeId)
                    && demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {
                demandeCongeService.supprimerDemandeSiEnAttente(demandeId, employeId);
                session.setAttribute("toastMessage", "❌ Demande supprimée avec succès.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/employe/mes-conges");
    }
}

}