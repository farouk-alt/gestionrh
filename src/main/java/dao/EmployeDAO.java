package dao;

import model.Chef;
import model.Employe;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import service.NotificationService;
import util.HibernateUtil;
import util.PasswordUtil;

import java.util.List;

import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeDAO {
    private final ChefDAO chefDAO = new ChefDAO();
    private final NotificationService notifService = new NotificationService();



    public void saveEmploye(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(employe);
            tx.commit();

            // 🔔 Notification au chef du département
            if (employe.getDepartement() != null) {
                Long depId = employe.getDepartement().getId();
                Chef chef = chefDAO.getChefActuelByDepartementId(depId);
                if (chef != null && chef.getEmploye() != null) {
                    notifService.creerNotification(
                            chef.getEmploye(),
                            "👤 Nouvel employé dans votre département : " + employe.getNomComplet(),
                            "info"
                    );
                }
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateEmploye(Employe employe, boolean motDePasseModifie) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(employe);
            tx.commit(); // ✅ commit d'abord, puis envoie des notifs
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return;
        }

        // ✅ Appels en dehors de la session Hibernate
        notifService.creerNotification(
                employe,
                "✏️ Vos informations personnelles ont été mises à jour.",
                "info"
        );

        if (motDePasseModifie) {
            notifService.creerNotification(
                    employe,
                    "🔐 Votre mot de passe a été modifié par l’administrateur.",
                    "warning"
            );
        }
    }


    public void deleteEmploye(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employe employe = session.get(Employe.class, id);
            if (employe != null) {
                session.delete(employe);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Employe getEmployeById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employe> getAllEmployes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employe> employes = session.createQuery("from Employe", Employe.class).list();
        for (Employe e : employes) {
            Hibernate.initialize(e.getHistoriqueChef()); // 👈 important
        }
        session.close();
        return employes;
    }


    public Employe findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe where email = :email", Employe.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employe> getEmployesByDepartement(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe where departement.id = :deptId", Employe.class)
                    .setParameter("deptId", departementId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employe authenticate(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Employe employe = session.createQuery(
                            "from Employe where email = :email", Employe.class)
                    .setParameter("email", email)
                    .uniqueResult();

            // ✅ Exception temporaire pour le super admin en clair
            if (employe != null && "admin@gmail.com".equalsIgnoreCase(email)
                    && "1234".equals(password)) {
                return employe; // ⚠️ uniquement temporaire
            }

            // ✅ Vérification classique avec BCrypt
            if (employe != null && PasswordUtil.checkPassword(password, employe.getMotDePasse())) {
                return employe;
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<Employe> findByDepartementId(Long departementId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                            "FROM Employe WHERE departement.id = :depId", Employe.class)
                    .setParameter("depId", departementId)
                    .list();
        } finally {
            session.close();
        }
    }
    public Employe findAdmin() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Employe e WHERE e.admin = true", Employe.class)
                    .setMaxResults(1)
                    .uniqueResult();
        } finally {
            session.close();
        }
    }


}
