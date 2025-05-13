package dao;

import model.Notification;
import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class NotificationDAO {
    public void ajouterNotification(Notification notification) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(notification);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to get unread notifications for a specific employee
    public List<Notification> getNotificationsNonLues(Employe employe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Notification> notifications = null;

        try {
            // Query to fetch unread notifications
            notifications = session.createQuery(
                    "SELECT n FROM Notification n WHERE n.destinataire = :employe AND n.lue = false ORDER BY n.dateNotification DESC",
                    Notification.class
            ).setParameter("employe", employe).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Ensure the session is closed
            session.close();
        }

        return notifications;
    }

    // Method to mark a notification as read
    public void marquerCommeLue(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // Find the notification by ID
            Notification notif = session.get(Notification.class, id);

            if (notif != null) {
                notif.setLue(true); // Mark as read
                session.update(notif); // Update the entity
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an error
            }
            e.printStackTrace();
        } finally {
            // Ensure the session is closed
            session.close();
        }
    }
    public List<Notification> getAllNotifications(Employe employe) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Notification n WHERE n.destinataire = :employe ORDER BY n.dateNotification DESC",
                            Notification.class
                    ).setParameter("employe", employe)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void marquerToutCommeLues(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.createQuery("UPDATE Notification n SET n.lue = true WHERE n.destinataire = :emp")
                    .setParameter("emp", employe)
                    .executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}