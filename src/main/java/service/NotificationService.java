package service;

import dao.NotificationDAO;
import model.Employe;
import model.Notification;

import java.util.Date;
import java.util.List;

public class NotificationService {
    private NotificationDAO notificationDAO = new NotificationDAO();

    public void envoyerNotification(Employe destinataire, String contenu, String type) {
        Notification notif = new Notification();
        notif.setContenu(contenu);
        notif.setDestinataire(destinataire);
        notif.setType(type);
        notificationDAO.ajouterNotification(notif);
    }

    public List<Notification> getNonLues(Employe employe) {
        return notificationDAO.getNotificationsNonLues(employe);
    }
    public List<Notification> getAllNotifications(Employe employe) {
        return notificationDAO.getAllNotifications(employe);
    }

    public void marquerCommeLue(Long id) {
        notificationDAO.marquerCommeLue(id);
    }
    public void creerNotification(Employe destinataire, String contenu, String type) {
        Notification notif = new Notification();
        notif.setContenu(contenu);
        notif.setType(type);
        notif.setDestinataire(destinataire);
        notif.setDateNotification(new Date());

        NotificationDAO dao = new NotificationDAO();
        dao.ajouterNotification(notif);
    }
    public void marquerToutCommeLue(Employe employe) {
        notificationDAO.marquerToutCommeLues(employe);
    }
}
