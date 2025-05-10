package model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "employe")
public class Employe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nom_utilisateur", nullable = false, unique = true)
    private String nomUtilisateur;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "solde_conge", nullable = false)
    private int soldeConge = 18;

    @Column(name = "admin", nullable = false)
    private boolean admin = false;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_creation", nullable = false)
    private Date dateCreation = new Date();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Chef> historiqueChef = new ArrayList<>();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DemandeConge> demandes = new ArrayList<>();

    // Getters, setters, méthodes utilitaires
    public boolean isChefActuel() {
        return historiqueChef.stream().anyMatch(c -> c.getDateFin() == null);
    }

    public String getNomComplet() {
        return prenom + " " + nom;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getSoldeConge() {
        return soldeConge;
    }

    public void setSoldeConge(int soldeConge) {
        this.soldeConge = soldeConge;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public List<Chef> getHistoriqueChef() {
        return historiqueChef;
    }

    public void setHistoriqueChef(List<Chef> historiqueChef) {
        this.historiqueChef = historiqueChef;
    }

    public List<DemandeConge> getDemandes() {
        return demandes;
    }

    public void setDemandes(List<DemandeConge> demandes) {
        this.demandes = demandes;
    }
    @Transient
    public Chef getChefActuel() {
        try {
            if (historiqueChef != null) {
                for (Chef c : historiqueChef) {
                    if (c.getDateFin() == null) return c;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    @Transient
    private boolean estChefActuel;

    public boolean isEstChefActuel() {
        return estChefActuel;
    }

    public void setEstChefActuel(boolean estChefActuel) {
        this.estChefActuel = estChefActuel;
    }


    @Transient
    public String getRole() {
        if (admin) return "ADMIN";
        if (isEstChefActuel()) return "CHEF";
        return "EMPLOYE";
    }



}
