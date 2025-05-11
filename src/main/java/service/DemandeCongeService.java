package service;

import dao.DemandeCongeDAO;
import dao.EmployeDAO;
import model.DemandeConge;
import model.Employe;
import org.hibernate.Session;
import util.HibernateUtil;

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

    public boolean approuverDemandeConge(Long demandeId, Employe updatedBy) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande == null || demande.getEtat() != DemandeConge.EtatDemande.EN_ATTENTE) return false;

        demande.setEtat(DemandeConge.EtatDemande.ACCEPTE);
        demande.setDateMiseAjour(new Date());
        demande.setUpdatedBy(updatedBy); // ✅ ici
        demandeCongeDAO.update(demande);

        Employe employe = demande.getEmploye();
        long diff = demande.getDateFin().getTime() - demande.getDateDebut().getTime();
        int nbJours = (int) (diff / (1000 * 60 * 60 * 24)) + 1;
        employe.setSoldeConge(employe.getSoldeConge() - nbJours);
        employeDAO.updateEmploye(employe);

        return true;
    }


    public boolean refuserDemandeConge(Long demandeId, Employe updatedBy) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande == null || demande.getEtat() != DemandeConge.EtatDemande.EN_ATTENTE) return false;

        demande.setEtat(DemandeConge.EtatDemande.REFUSE);
        demande.setDateMiseAjour(new Date());
        demande.setUpdatedBy(updatedBy); // ✅ ici aussi
        demandeCongeDAO.update(demande);

        return true;
    }
    public int calculerPourcentageAcceptationActuel(Long departementId) {
        String totalQuery = "SELECT COUNT(d) FROM DemandeConge d WHERE d.employe.departement.id = :id";
        String actifsQuery = "SELECT COUNT(d) FROM DemandeConge d WHERE d.etat = :etat AND d.employe.departement.id = :id AND d.dateFin >= CURRENT_DATE";
        Session session = HibernateUtil.getSessionFactory().openSession();
        long total = session.createQuery(totalQuery, Long.class)
                .setParameter("id", departementId).getSingleResult();

        long actifs = session.createQuery(actifsQuery, Long.class)
                .setParameter("etat", DemandeConge.EtatDemande.ACCEPTE)
                .setParameter("id", departementId).getSingleResult();

        return total == 0 ? 0 : (int) ((double) actifs / total * 100);
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
    public int countAllByDepartement(Long departementId) {
        return demandeCongeDAO.countAllByDepartement(departementId);
    }

    public int countByEtatAndDepartement(String etat, Long departementId) {
        return demandeCongeDAO.countByEtatAndDepartement(etat, departementId);
    }
    public long countAllDemandes(Long employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT COUNT(dc) FROM DemandeConge dc WHERE dc.employe.id = :id";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", employeId)
                    .uniqueResult();
            return count != null ? count : 0;
        } finally {
            session.close();
        }
    }

    public long countByEtat(Long employeId, String etat) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT COUNT(dc) FROM DemandeConge dc WHERE dc.employe.id = :id AND dc.etat = :etat";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", employeId)
                    .setParameter("etat", model.DemandeConge.EtatDemande.valueOf(etat))
                    .uniqueResult();
            return count != null ? count : 0;
        } finally {
            session.close();
        }
    }
    public boolean hasPendingRequest(Long employeId) {
        return demandeCongeDAO.existsPendingByEmploye(employeId);
    }
    public int countAcceptedStillActiveByDepartement(Long departementId) {
        return demandeCongeDAO.countAcceptedStillActiveByDepartement(departementId);
    }
    public void update(DemandeConge demande) {
        demandeCongeDAO.update(demande);
    }
    public boolean supprimerDemandeSiEnAttente(Long demandeId, Long employeId) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande != null &&
                demande.getEmploye().getId().equals(employeId) &&
                demande.getEtat() == DemandeConge.EtatDemande.EN_ATTENTE) {
            demandeCongeDAO.delete(demandeId);
            return true;
        }
        return false;
    }




}
