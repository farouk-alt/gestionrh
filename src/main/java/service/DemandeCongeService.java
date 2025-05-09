//package service;
//
//import dao.DemandeCongeDAO;
//import dao.EmployeDAO;
//import java.util.Date;
//import java.util.List;
//import model.DemandeConge;
//import model.Employe;
//
//public class DemandeCongeService {
//
//    private final DemandeCongeDAO demandeCongeDAO;
//    private final EmployeDAO employeDAO;
//
//    public DemandeCongeService() {
//        this.demandeCongeDAO = new DemandeCongeDAO();
//        this.employeDAO = new EmployeDAO();
//    }
//
//    public void saveDemandeConge(DemandeConge demandeConge) {
//        demandeCongeDAO.saveDemandeConge(demandeConge);
//    }
//
//    public void updateDemandeConge(DemandeConge demandeConge) {
//        demandeCongeDAO.updateDemandeConge(demandeConge);
//    }
//
//    public void deleteDemandeConge(Long id) {
//        demandeCongeDAO.deleteDemandeConge(id);
//    }
//
//    public DemandeConge getDemandeCongeById(Long id) {
//        return demandeCongeDAO.getDemandeCongeById(id);
//    }
//
//    public List<DemandeConge> getAllDemandesConge() {
//        return demandeCongeDAO.getAllDemandesConge();
//    }
//
//    public List<DemandeConge> getDemandesCongeByEmploye(Long employeId) {
//        return demandeCongeDAO.getDemandesCongeByEmploye(employeId);
//    }
//
//    public List<DemandeConge> getDemandesCongeByDepartement(Long departementId) {
//        return demandeCongeDAO.getDemandesCongeByDepartement(departementId);
//    }
//
//    public List<DemandeConge> getDemandesCongeEnAttente() {
//        return demandeCongeDAO.getDemandesCongeEnAttente();
//    }
//
//    public List<DemandeConge> getDemandesCongeEnAttenteByDepartement(Long departementId) {
//        return demandeCongeDAO.getDemandesCongeEnAttenteByDepartement(departementId);
//    }
//
//    public boolean creerDemandeConge(Long employeId, Date dateDebut, Date dateFin, String motif) {
//        Employe employe = employeDAO.getEmployeById(employeId);
//
//        if (employe == null) {
//            return false;
//        }
//
//        // Calculer le nombre de jours
//        long diff = dateFin.getTime() - dateDebut.getTime();
//        int nombreJours = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
//
//        // Vérifier si l'employé a assez de jours de congé
//        if (employe.getQuotaConge() < nombreJours) {
//            return false;
//        }
//
//        // Créer la demande de congé
//        DemandeConge demandeConge = new DemandeConge(employe, dateDebut, dateFin, motif);
//        demandeCongeDAO.saveDemandeConge(demandeConge);
//
//        return true;
//    }
//
//    public boolean approuverDemandeConge(Long demandeId, String commentaire) {
//        DemandeConge demande = demandeCongeDAO.getDemandeCongeById(demandeId);
//
//        if (demande == null || !"EN_ATTENTE".equals(demande.getStatut())) {
//            return false;
//        }
//
//        // Approuver la demande
//        demande.approuver(commentaire);
//        demandeCongeDAO.updateDemandeConge(demande);
//
//        // Mettre à jour le quota de congé de l'employé
//        Employe employe = demande.getEmploye();
//        int nouveauQuota = employe.getQuotaConge() - demande.getNombreJours();
//        employe.setQuotaConge(nouveauQuota);
//        employeDAO.updateEmploye(employe);
//
//        return true;
//    }
//
//    public boolean refuserDemandeConge(Long demandeId, String commentaire) {
//        DemandeConge demande = demandeCongeDAO.getDemandeCongeById(demandeId);
//
//        if (demande == null || !"EN_ATTENTE".equals(demande.getStatut())) {
//            return false;
//        }
//
//        // Refuser la demande
//        demande.refuser(commentaire);
//        demandeCongeDAO.updateDemandeConge(demande);
//
//        return true;
//    }
//}
// ✅ ChefService.java – corrigé selon les entités et la logique métier définies

package service;

import dao.DemandeCongeDAO;
import dao.EmployeDAO;
import model.DemandeConge;
import model.Employe;

import java.util.Date;
import java.util.List;

public class DemandeCongeService {

    private final DemandeCongeDAO demandeCongeDAO = new DemandeCongeDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    public boolean creerDemandeConge(Long employeId, Date dateDebut, Date dateFin, String motif) {
        Employe employe = employeDAO.getEmployeById(employeId);
        if (employe == null) return false;

        long diff = dateFin.getTime() - dateDebut.getTime();
        int nombreJours = (int) (diff / (1000 * 60 * 60 * 24)) + 1;

        if (employe.getSoldeConge() < nombreJours) return false;

        DemandeConge demande = new DemandeConge();
        demande.setEmploye(employe);
        demande.setDateDebut(dateDebut);
        demande.setDateFin(dateFin);
        demande.setMotif(motif);
        demande.setEtat(DemandeConge.EtatDemande.EN_ATTENTE);

        demandeCongeDAO.save(demande);
        return true;
    }

    public boolean approuverDemandeConge(Long demandeId) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande == null || demande.getEtat() != DemandeConge.EtatDemande.EN_ATTENTE) return false;

        demande.setEtat(DemandeConge.EtatDemande.ACCEPTE);
        demande.setDateMiseAjour(new Date());
        demandeCongeDAO.update(demande);

        Employe employe = demande.getEmploye();
        long diff = demande.getDateFin().getTime() - demande.getDateDebut().getTime();
        int nbJours = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
        employe.setSoldeConge(employe.getSoldeConge() - nbJours);
        employeDAO.updateEmploye(employe);

        return true;
    }

    public boolean refuserDemandeConge(Long demandeId) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande == null || demande.getEtat() != DemandeConge.EtatDemande.EN_ATTENTE) return false;

        demande.setEtat(DemandeConge.EtatDemande.REFUSE);
        demande.setDateMiseAjour(new Date());
        demandeCongeDAO.update(demande);

        return true;
    }

    public List<DemandeConge> getDemandesParEmploye(Long employeId) {
        return demandeCongeDAO.getByEmploye(employeId);
    }

    public List<DemandeConge> getDemandesEnAttenteParDepartement(Long departementId) {
        return demandeCongeDAO.getEnAttenteByDepartement(departementId);
    }

    public List<DemandeConge> getDemandesTraiteesParDepartement(Long departementId) {
        return demandeCongeDAO.getTraiteesByDepartement(departementId);
    }

    public List<DemandeConge> getAllDemandes() {
        return demandeCongeDAO.getAll();
    }

    public DemandeConge getDemandeById(Long id) {
        return demandeCongeDAO.getById(id);
    }

    public void deleteDemande(Long id) {
        // Pas utilisé directement, mais fourni
    }
    public List<DemandeConge> rechercherParCriteres(String nom, String prenom, String departement, Date dateMaj) {
        return demandeCongeDAO.rechercherParCriteres(nom, prenom, departement, dateMaj);
    }

}
