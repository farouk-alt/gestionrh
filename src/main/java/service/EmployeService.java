//package service;
//
//import dao.DepartementDAO;
//import dao.EmployeDAO;
//import java.util.List;
//
//import model.Departement;
//import model.Employe;
//
//public class EmployeService {
//
//    private final EmployeDAO employeDAO;
//    private final DepartementDAO departementDAO;
//
//    public EmployeService() {
//        this.employeDAO = new EmployeDAO();
//        this.departementDAO = new DepartementDAO();
//    }
//
//    public void saveEmploye(Employe employe) {
//        employeDAO.saveEmploye(employe);
//    }
//
//    public void updateEmploye(Employe employe) {
//        employeDAO.updateEmploye(employe);
//    }
//
//    public boolean deleteEmploye(Long id) {
//        Employe employe = employeDAO.getEmployeById(id);
//        if (employe == null) return false;
//
//        // Vérifier s'il est chef actuellement
//        List<Departement> depChefs = departementDAO.findDepartementsByChefActuel(employe.getId());
//        if (depChefs != null && !depChefs.isEmpty()) {
//            return false; // ⚠️ Il est chef → refuser suppression
//        }
//
//        // Si pas chef, on peut le supprimer
//        employeDAO.deleteEmploye(id);
//        return true;
//    }
//
//
//
//    public Employe getEmployeById(Long id) {
//        return employeDAO.getEmployeById(id);
//    }
//
//    public Employe authenticate(String email, String password) {
//        return employeDAO.authenticateWithChef(email, password);
//    }
//
//
//
//    public List<Employe> getAllEmployes() {
//        return employeDAO.getAllEmployes();
//    }
//
//    public Employe findByEmail(String email) {
//        return employeDAO.findByEmail(email);
//    }
//
//    public List<Employe> getEmployesByDepartement(Long departementId) {
//        return employeDAO.getEmployesByDepartement(departementId);
//    }
//
//
//    public boolean emailExists(String email) {
//        return employeDAO.findByEmail(email) != null;
//    }
//
//    // Méthode pour mettre à jour le quota de congés
//    public void updateQuotaConge(Long employeId, int nouveauQuota) {
//        Employe employe = getEmployeById(employeId);
//        if (employe != null) {
//            employe.setQuotaConge(nouveauQuota);
//            updateEmploye(employe);
//        }
//    }
////    public List<Employe> getEmployesByDepartement(Long depId) {
////        return employeDAO.findByDepartement(depId);
////    }
//
//}
// ✅ EmployeService.java – corrigé selon les entités et les exigences métiers

package service;

import dao.ChefDAO;
import dao.DepartementDAO;
import dao.EmployeDAO;
import model.Departement;
import model.Employe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.HibernateUtil;

import java.util.List;

public class EmployeService {

    private final EmployeDAO employeDAO = new EmployeDAO();
    private final DepartementDAO departementDAO = new DepartementDAO();
    private final ChefDAO chefDAO = new ChefDAO();

    public void saveEmploye(Employe employe) {
        employeDAO.saveEmploye(employe);
    }

    public void updateEmploye(Employe employe) {
        employeDAO.updateEmploye(employe);
    }

    public boolean deleteEmploye(Long id) {
        Employe employe = employeDAO.getEmployeById(id);
        if (employe == null) return false;

        // ⚠️ Ne pas supprimer si l'employé est chef actuellement
        List<Departement> deps = departementDAO.findDepartementsByChefActuel(employe.getId());
        if (deps != null && !deps.isEmpty()) return false;

        employeDAO.deleteEmploye(id);
        return true;
    }

    public Employe getEmployeById(Long id) {
        return employeDAO.getEmployeById(id);
    }

    public List<Employe> getAllEmployes() {
        return employeDAO.getAllEmployes();
    }

    public List<Employe> getEmployesByDepartement(Long departementId) {
        return employeDAO.getEmployesByDepartement(departementId);
    }

    public Employe findByEmail(String email) {
        return employeDAO.findByEmail(email);
    }

    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    public void updateQuotaConge(Long employeId, int nouveauQuota) {
        Employe employe = getEmployeById(employeId);
        if (employe != null) {
            employe.setSoldeConge(nouveauQuota);
            updateEmploye(employe);
        }
    }

    public Employe authenticate(String email, String password) {
        return employeDAO.authenticate(email, password);
    }

    public List<Employe> getEmployesSansChefActuel() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        // Employés qui ne sont pas actuellement chefs dans un autre département
        String hql = "FROM Employe e WHERE NOT EXISTS (" +
                "SELECT 1 FROM Chef c WHERE c.employe.id = e.id AND c.dateFin IS NULL" +
                ")";

        List<Employe> result = session.createQuery(hql, Employe.class).getResultList();
        session.close();
        return result;
    }
    public List<Employe> getAllEmployesAvecHistoriqueChef() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                    "SELECT DISTINCT e FROM Employe e LEFT JOIN FETCH e.historiqueChef", Employe.class
            ).getResultList();
        } finally {
            session.close();
        }
    }
    public List<Employe> getEmployesByDepartementAvecStatutChef(Long departementId) {
        List<Employe> employes = employeDAO.findByDepartementId(departementId);

        for (Employe e : employes) {
            boolean estChefActuel = chefDAO.isChefActuel(e.getId());
            e.setEstChefActuel(estChefActuel);
        }

        return employes;
    }




}