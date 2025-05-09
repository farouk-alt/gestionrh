//package model;
//
//import java.io.Serializable;
//import java.util.HashSet;
//import java.util.Set;
//import javax.persistence.*;
//
//@Entity
//@Table(name = "departement")
//public class Departement implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "nom", nullable = false, unique = true)
//    private String nom;
//
//    @Column(name = "description")
//    private String description;
//
//    // 🔄 Historique des chefs (actuels et anciens)
//    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Chef> anciensChefs = new HashSet<>();
//
//    // 👨‍💼 Employés du département
//    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Employe> employes = new HashSet<>();
//
//    // Constructeurs
//    public Departement() {}
//
//    public Departement(String nom, String description) {
//        this.nom = nom;
//        this.description = description;
//    }
//
//    // Getters & Setters
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getNom() { return nom; }
//    public void setNom(String nom) { this.nom = nom; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public Set<Chef> getAnciensChefs() { return anciensChefs; }
//    public void setAnciensChefs(Set<Chef> anciensChefs) { this.anciensChefs = anciensChefs; }
//
//    public Set<Employe> getEmployes() { return employes; }
//    public void setEmployes(Set<Employe> employes) { this.employes = employes; }
//
//    public void ajouterEmploye(Employe employe) {
//        employes.add(employe);
//        employe.setDepartement(this);
//    }
//}
package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "departement")
public class Departement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employe> employes = new ArrayList<>();

    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chef> anciensChefs = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }

    public List<Chef> getAnciensChefs() {
        return anciensChefs;
    }

    public void setAnciensChefs(List<Chef> anciensChefs) {
        this.anciensChefs = anciensChefs;
    }
    @Transient
    public Chef getChef() {
        for (Chef c : anciensChefs) {
            if (c.getDateFin() == null) {
                return c;
            }
        }
        return null;
    }

}
