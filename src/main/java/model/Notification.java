package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.Date;

@Entity
@Table(name = "notification")
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;

    private boolean lue;

    private String type; // Exemple : "info", "success", "danger"

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateNotification;

    @ManyToOne
    @JoinColumn(name = "employe_id")
    private Employe destinataire;

    public Notification() {
        this.lue = false;
        this.dateNotification = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public boolean isLue() {
        return lue;
    }

    public void setLue(boolean lue) {
        this.lue = lue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public Employe getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Employe destinataire) {
        this.destinataire = destinataire;
    }
}
