package service;

import dao.DemandeCongeDAO;
import dao.EmployeDAO;
import model.DemandeConge;
import model.Employe;
import org.hibernate.Session;
import util.HibernateUtil;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DemandeCongeService {

    private final DemandeCongeDAO demandeCongeDAO = new DemandeCongeDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();

    public boolean creerDemandeConge(Long employeId, Date dateDebut, Date dateFin, String motif) {
        Employe employe = employeDAO.getEmployeById(employeId);
        if (employe == null) return false;

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

        Employe employe = demande.getEmploye();

        // 🔁 Convertir les dates de la demande
        Date utilDateDebut = new Date(demande.getDateDebut().getTime());
        Date utilDateFin = new Date(demande.getDateFin().getTime());

        LocalDate dateDebut = utilDateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateFin = utilDateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        // 🔍 Exclure la demande en cours du calcul
        List<DemandeConge> congesExistants = getCongesChevauchants(employe.getId(), dateDebut, dateFin)
                .stream()
                .filter(c -> !c.getId().equals(demandeId)) // ✅ exclure
                .collect(Collectors.toList());

        // 🧮 Calcul intelligent
        int joursAAppliquer = calculerJoursAAppliquer(dateDebut, dateFin, congesExistants);

        // ❌ Si solde insuffisant, on refuse l'approbation
        if (joursAAppliquer > employe.getSoldeConge()) {
            return false;
        }

        // ✅ Mise à jour seulement si solde suffisant

        employe.setSoldeConge(employe.getSoldeConge() - joursAAppliquer);
        employeDAO.updateEmploye(employe, false); // ✅ motDePasseModifie = false

        demande.setEtat(DemandeConge.EtatDemande.ACCEPTE);
        demande.setDateMiseAjour(new Date());
        demande.setUpdatedBy(updatedBy);
        demandeCongeDAO.update(demande);

        // 🔔 Notifications
        NotificationService notifService = new NotificationService();
        Employe admin = employeDAO.findAdmin();
        notifService.creerNotification(admin, "Le chef " + updatedBy.getNomComplet() + " a approuvé une demande de congé.", "success");
        notifService.creerNotification(employe, "Votre demande de congé a été approuvée par " + updatedBy.getNomComplet(), "success");

        return true;
    }


    public boolean refuserDemandeConge(Long demandeId, Employe updatedBy) {
        DemandeConge demande = demandeCongeDAO.getById(demandeId);
        if (demande == null || demande.getEtat() != DemandeConge.EtatDemande.EN_ATTENTE) return false;

        demande.setEtat(DemandeConge.EtatDemande.REFUSE);
        demande.setDateMiseAjour(new Date());
        demande.setUpdatedBy(updatedBy);
        demandeCongeDAO.update(demande);

        // ✅ Récupérer les destinataires
        Employe employe = demande.getEmploye();
        Employe admin = employeDAO.findAdmin();

        // ✅ Envoyer les notifications
        NotificationService notifService = new NotificationService();
        notifService.creerNotification(admin, "Le chef " + updatedBy.getNomComplet() + " a refusé une demande de congé.", "danger");
        notifService.creerNotification(employe, "Votre demande de congé a été refusée par " + updatedBy.getNomComplet(), "danger");

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
    public List<DemandeConge> getCongesChevauchants(Long employeId, LocalDate dateDebut, LocalDate dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Date dateDebutConverted = Date.from(dateDebut.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dateFinConverted = Date.from(dateFin.atStartOfDay(ZoneId.systemDefault()).toInstant());

            String hql = "FROM DemandeConge dc WHERE dc.employe.id = :employeId " +
                    "AND dc.etat = 'ACCEPTE' " +
                    "AND dc.dateDebut <= :dateFin " +
                    "AND dc.dateFin >= :dateDebut";

            return session.createQuery(hql, DemandeConge.class)
                    .setParameter("employeId", employeId)
                    .setParameter("dateDebut", dateDebutConverted) // ✅ converted
                    .setParameter("dateFin", dateFinConverted)     // ✅ converted
                    .list();
        }
    }


    public int calculerJoursAAppliquer(LocalDate dateDebut, LocalDate dateFin, List<DemandeConge> congesExistants) {
        Set<LocalDate> joursDemande = new HashSet<>();
        for (LocalDate date = dateDebut; !date.isAfter(dateFin); date = date.plusDays(1)) {
            joursDemande.add(date);
        }
        for (DemandeConge conge : congesExistants) {
            LocalDate debut = ((java.sql.Date) conge.getDateDebut()).toLocalDate();
            LocalDate fin = ((java.sql.Date) conge.getDateFin()).toLocalDate();

            for (LocalDate date = debut; !date.isAfter(fin); date = date.plusDays(1)) {
                joursDemande.remove(date);
            }
        }



        return joursDemande.size(); // ✅ Nombre de jours à déduire réellement
    }
    public int calculerTauxAcceptationEmployes(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // ❌ Ne pas inclure le chef actuel dans le total
            String totalHql = "SELECT COUNT(e) FROM Employe e " +
                    "WHERE e.departement.id = :id " +
                    "AND e.id NOT IN (SELECT c.employe.id FROM Chef c WHERE c.dateFin IS NULL)";
            Long totalEmployes = session.createQuery(totalHql, Long.class)
                    .setParameter("id", departementId)
                    .uniqueResult();

            if (totalEmployes == null || totalEmployes == 0) return 0;

            // ✅ Nombre d'employés (hors chef) ayant un congé accepté encore actif
            String actifsHql = "SELECT COUNT(DISTINCT d.employe.id) FROM DemandeConge d " +
                    "WHERE d.etat = :etat AND d.employe.departement.id = :id " +
                    "AND d.dateFin >= CURRENT_DATE " +
                    "AND d.employe.id NOT IN (SELECT c.employe.id FROM Chef c WHERE c.dateFin IS NULL)";
            Long employesEnConge = session.createQuery(actifsHql, Long.class)
                    .setParameter("etat", DemandeConge.EtatDemande.ACCEPTE)
                    .setParameter("id", departementId)
                    .uniqueResult();

            return (int) ((double) employesEnConge / totalEmployes * 100);
        }
    }

    public int countEmployesDansDepartement(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(e) FROM Employe e " +
                    "WHERE e.departement.id = :id " +
                    "AND e.id NOT IN (SELECT c.employe.id FROM Chef c WHERE c.dateFin IS NULL)";
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("id", departementId)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        }
    }


    public int countEmployesEnConge(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(DISTINCT d.employe.id) FROM DemandeConge d " +
                    "WHERE d.etat = :etat AND d.employe.departement.id = :id AND d.dateFin >= CURRENT_DATE";
            return session.createQuery(hql, Long.class)
                    .setParameter("etat", DemandeConge.EtatDemande.ACCEPTE)
                    .setParameter("id", departementId)
                    .uniqueResult().intValue();
        }
    }

    public boolean existeDemandeIdentiqueAcceptee(Long employeId, Date dateDebut, Date dateFin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT COUNT(d) FROM DemandeConge d " +
                    "WHERE d.employe.id = :employeId " +
                    "AND d.etat = :etat " +
                    "AND d.dateDebut = :dateDebut " +
                    "AND d.dateFin = :dateFin";

            Long count = session.createQuery(hql, Long.class)
                    .setParameter("employeId", employeId)
                    .setParameter("etat", DemandeConge.EtatDemande.ACCEPTE)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .uniqueResult();

            return count != null && count > 0;
        }
    }


}
