//package model;
//
//import java.io.Serializable;
//import java.util.Date;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "demande_conge")
//public class DemandeConge implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "employe_id", nullable = false)
//    private Employe employe;
//
//    @Column(name = "date_debut", nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date dateDebut;
//
//    @Column(name = "date_fin", nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date dateFin;
//
//    @Column(name = "motif")
//    private String motif;
//
//    @Column(name = "statut")
//    private String statut; // EN_ATTENTE, APPROUVE, REFUSE
//
//    @Column(name = "commentaire")
//    private String commentaire;
//
//    @Column(name = "date_demande")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dateDemande;
//
//    @Column(name = "date_reponse")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dateReponse;
//
//    @Column(name = "nombre_jours")
//    private Integer nombreJours;
//
//    // Constructeurs
//    public DemandeConge() {
//        this.dateDemande = new Date();
//        this.statut = "EN_ATTENTE";
//    }
//
//    public DemandeConge(Employe employe, Date dateDebut, Date dateFin, String motif) {
//        this.employe = employe;
//        this.dateDebut = dateDebut;
//        this.dateFin = dateFin;
//        this.motif = motif;
//        this.dateDemande = new Date();
//        this.statut = "EN_ATTENTE";
//        this.nombreJours = calculerNombreJours();
//    }
//
//    // Getters et Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Employe getEmploye() {
//        return employe;
//    }
//
//    public void setEmploye(Employe employe) {
//        this.employe = employe;
//    }
//
//    public Date getDateDebut() {
//        return dateDebut;
//    }
//
//    public void setDateDebut(Date dateDebut) {
//        this.dateDebut = dateDebut;
//        this.nombreJours = calculerNombreJours();
//    }
//
//    public Date getDateFin() {
//        return dateFin;
//    }
//
//    public void setDateFin(Date dateFin) {
//        this.dateFin = dateFin;
//        this.nombreJours = calculerNombreJours();
//    }
//
//    public String getMotif() {
//        return motif;
//    }
//
//    public void setMotif(String motif) {
//        this.motif = motif;
//    }
//
//    public String getStatut() {
//        return statut;
//    }
//
//    public void setStatut(String statut) {
//        this.statut = statut;
//    }
//
//    public String getCommentaire() {
//        return commentaire;
//    }
//
//    public void setCommentaire(String commentaire) {
//        this.commentaire = commentaire;
//    }
//
//    public Date getDateDemande() {
//        return dateDemande;
//    }
//
//    public void setDateDemande(Date dateDemande) {
//        this.dateDemande = dateDemande;
//    }
//
//    public Date getDateReponse() {
//        return dateReponse;
//    }
//
//    public void setDateReponse(Date dateReponse) {
//        this.dateReponse = dateReponse;
//    }
//
//    public Integer getNombreJours() {
//        return nombreJours;
//    }
//
//    public void setNombreJours(Integer nombreJours) {
//        this.nombreJours = nombreJours;
//    }
//
//    // Méthode pour calculer le nombre de jours de congé
//    private Integer calculerNombreJours() {
//        if (dateDebut == null || dateFin == null) {
//            return 0;
//        }
//
//        long diff = dateFin.getTime() - dateDebut.getTime();
//        return (int) (diff / (1000 * 60 * 60 * 24)) + 1;
//    }
//
//    // Méthode pour approuver la demande
//    public void approuver(String commentaire) {
//        this.statut = "APPROUVE";
//        this.commentaire = commentaire;
//        this.dateReponse = new Date();
//    }
//
//    // Méthode pour refuser la demande
//    public void refuser(String commentaire) {
//        this.statut = "REFUSE";
//        this.commentaire = commentaire;
//        this.dateReponse = new Date();
//    }
//}
package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "demande_conge")
public class DemandeConge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut", nullable = false)
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin", nullable = false)
    private Date dateFin;

    @Column(name = "motif", nullable = false)
    private String motif;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatDemande etat = EtatDemande.EN_ATTENTE;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_mise_a_jour")
    private Date dateMiseAjour;

    public enum EtatDemande {
        EN_ATTENTE,
        ACCEPTE,
        REFUSE
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public EtatDemande getEtat() {
        return etat;
    }

    public void setEtat(EtatDemande etat) {
        this.etat = etat;
    }

    public Date getDateMiseAjour() {
        return dateMiseAjour;
    }

    public void setDateMiseAjour(Date dateMiseAjour) {
        this.dateMiseAjour = dateMiseAjour;
    }
}
