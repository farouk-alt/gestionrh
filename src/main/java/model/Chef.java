//package model;
//
//import java.io.Serializable;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "chef")
//public class Chef implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne // 🔁 Peut être nommé plusieurs fois !
//    @JoinColumn(name = "employe_id", nullable = false) // ❌ PAS de unique = true !
//    private Employe employe;
//
//    @ManyToOne // 🔁 un employé peut être chef dans plusieurs départements dans le temps
//    @JoinColumn(name = "departement_id", nullable = false)
//    private Departement departement;
//
//    @Column(name = "date_fin")
//    @Temporal(TemporalType.DATE)
//    private java.util.Date dateFin;
//
//    @Column(name = "date_nomination")
//    @Temporal(TemporalType.DATE)
//    private java.util.Date dateNomination;
//
//    public Chef() {
//    }
//
//    public Chef(Employe employe, java.util.Date dateNomination) {
//        this.employe = employe;
//        this.dateNomination = dateNomination;
//    }
//
//    // Getters & Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public Employe getEmploye() { return employe; }
//    public void setEmploye(Employe employe) { this.employe = employe; }
//
//    public Departement getDepartement() { return departement; }
//    public void setDepartement(Departement departement) { this.departement = departement; }
//
//    public java.util.Date getDateNomination() { return dateNomination; }
//    public void setDateNomination(java.util.Date dateNomination) { this.dateNomination = dateNomination; }
//
//    public java.util.Date getDateFin() { return dateFin; }
//    public void setDateFin(java.util.Date dateFin) { this.dateFin = dateFin; }
//
//    @Transient
//    public boolean isActif() {
//        return this.dateFin == null;
//    }
//}
package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "chef")
public class Chef implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    @ManyToOne
    @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_nomination", nullable = false)
    private Date dateNomination ;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Transient
    public boolean isActif() {
        return this.dateFin == null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Date getDateDebut() {
        return dateNomination;
    }

    public void setDateDebut(Date dateNomination) {
        this.dateNomination = dateNomination;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Date getDateNomination() {
        return dateNomination;
    }

    public void setDateNomination(Date dateNomination) {
        this.dateNomination = dateNomination;
    }
}
