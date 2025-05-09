//package service;
//
//import dao.DepartementDAO;
//import java.util.List;
//import model.Departement;
//import org.hibernate.Hibernate;
//
//public class DepartementService {
//
//    private final DepartementDAO departementDAO;
//
//    public DepartementService() {
//        this.departementDAO = new DepartementDAO();
//    }
//
//    public void saveDepartement(Departement departement) {
//        departementDAO.saveDepartement(departement);
//    }
//
//    public void updateDepartement(Departement departement) {
//        departementDAO.updateDepartement(departement);
//    }
//
//    public void deleteDepartement(Long id) {
//        departementDAO.deleteDepartement(id);
//    }
//
//    public Departement getDepartementById(Long id) {
//        return departementDAO.getDepartementById(id);
//    }
//
//    public List<Departement> getAllDepartements() {
//        return departementDAO.getAllDepartements();
//    }
//
//    public Departement findByNom(String nom) {
//        return departementDAO.findByNom(nom);
//    }
//
//    public boolean nomExists(String nom) {
//        return departementDAO.findByNom(nom) != null;
//    }
//    public List<Departement> getAllDepartementsWithChef() {
//        return departementDAO.getAllDepartements();
//    }
//
//    public Departement getDepartementWithChefById(Long id) {
//        return departementDAO.getDepartementWithChefById(id);
//    }
//    public Departement getDepartementWithEmployesAndChefById(Long id) {
//        return departementDAO.getDepartementWithEmployesAndChefById(id);
//    }
//    public Departement getDepartementByIdWithChefs(Long id) {
//        Departement d = departementDAO.getDepartementById(id);
//        if (d != null) {
//            Hibernate.initialize(d.getAnciensChefs());
//            Hibernate.initialize(d.getEmployes()); // si besoin
//        }
//        return d;
//    }
//
//}
// ✅ DepartementService.java – corrigé selon les entités et la logique métier

package service;

import dao.DepartementDAO;
import model.Departement;
import org.hibernate.Hibernate;

import java.util.List;

public class DepartementService {

    private final DepartementDAO departementDAO = new DepartementDAO();

    public void saveDepartement(Departement departement) {
        departementDAO.saveDepartement(departement);
    }

    public void updateDepartement(Departement departement) {
        departementDAO.updateDepartement(departement);
    }

    public void deleteDepartement(Long id) {
        departementDAO.deleteDepartement(id);
    }

    public Departement getDepartementById(Long id) {
        return departementDAO.getDepartementById(id);
    }

    public List<Departement> getAllDepartements() {
        return departementDAO.getAllDepartements();
    }

    public boolean nomExists(String nom) {
        return departementDAO.findByNom(nom) != null;
    }

    public Departement findByNom(String nom) {
        return departementDAO.findByNom(nom);
    }

    public List<Departement> getAllDepartementsWithChef() {
        return departementDAO.getAllDepartements();
    }

    public Departement getDepartementWithEmployesAndChefById(Long id) {
        return departementDAO.getDepartementWithEmployesAndChefById(id);
    }

    public Departement getDepartementByIdWithChefs(Long id) {
        Departement d = departementDAO.getDepartementById(id);
        if (d != null) {
            Hibernate.initialize(d.getAnciensChefs());
            Hibernate.initialize(d.getEmployes());
        }
        return d;
    }

    public List<Departement> getDepartementsByChef(Long employeId) {
        return departementDAO.findDepartementsByChefActuel(employeId);
    }
}
