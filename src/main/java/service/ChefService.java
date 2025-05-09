//package service;
//
//import dao.ChefDAO;
//import dao.DepartementDAO;
//import dao.EmployeDAO;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.List;
//import model.Chef;
//import model.Departement;
//import model.Employe;
//import org.hibernate.Hibernate;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import util.HibernateUtil;
//
//public class ChefService {
//
//    private final ChefDAO chefDAO;
//    private final EmployeDAO employeDAO;
//    private final DepartementDAO departementDAO;
//
//
//    public ChefService() {
//        this.chefDAO = new ChefDAO();
//        this.employeDAO = new EmployeDAO();
//        this.departementDAO = new DepartementDAO();
//    }
//
//    public void saveChef(Chef chef) {
//        chefDAO.saveOrUpdateChef(chef);
//    }
//
//    public void updateChef(Chef chef) {
//        chefDAO.updateChef(chef);
//    }
//
//    public void deleteChef(Long id) {
//        chefDAO.deleteChef(id);
//    }
//
//    public Chef getChefById(Long id) {
//        return chefDAO.getChefById(id);
//    }
//
//    public List<Chef> getAllChefs() {
//        return chefDAO.getAllChefs();
//    }
//
//    public Chef findByEmployeId(Long employeId) {
//        return chefDAO.findByEmployeId(employeId);
//    }
//
//    public Chef findByDepartementId(Long departementId) {
//        return chefDAO.findByDepartementId(departementId);
//    }
//
//    public boolean isChef(Long employeId) {
//        return findByEmployeId(employeId) != null;
//    }
//
//
//    public void nommerChef(Long employeId, Long departementId) {
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//
//        try {
//            tx = session.beginTransaction();
//
//            // Clôturer l’ancien chef s’il y en a un
//            Chef chefActuel = session.createQuery(
//                            "FROM Chef WHERE departement.id = :depId AND dateFin IS NULL", Chef.class)
//                    .setParameter("depId", departementId)
//                    .uniqueResult();
//
//            if (chefActuel != null) {
//                chefActuel.setDateFin(java.sql.Date.valueOf(LocalDate.now()));
//                session.update(chefActuel);
//            }
//
//            // Ajouter le nouveau chef
//            Employe employe = session.get(Employe.class, employeId);
//            Departement departement = session.get(Departement.class, departementId);
//
//            Chef nouveauChef = new Chef();
//            nouveauChef.setEmploye(employe);
//            nouveauChef.setDepartement(departement);
//            nouveauChef.setDateNomination(java.sql.Date.valueOf(LocalDate.now()));
//            nouveauChef.setDateFin(null);  // Chef en cours
//
//            session.persist(nouveauChef);
//
//            tx.commit();
//        } catch (Exception e) {
//            if (tx != null) tx.rollback();
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//    }
//
//
//
//
//
//
//
////    public void revoquerChef(Long chefId) {
////        Chef chef = chefDAO.getChefById(chefId);
////        if (chef != null) {
////            Employe employe = chef.getEmploye();
////            Departement departement = chef.getDepartement();
////
////            // Mettre à jour le département
////            if (departement != null) {
////                departement.setChef(null);
////                departementDAO.updateDepartement(departement);
////            }
////
////            // Mettre à jour le rôle de l'employé
////            if (employe != null) {
////                employe.setRole("EMPLOYE");
////                employeDAO.updateEmploye(employe);
////            }
////
////            // Supprimer le chef
////            chefDAO.deleteChef(chefId);
////        }
////    }
////    public void retirerChef(Long employeId) {
////        Session session = HibernateUtil.getSessionFactory().openSession();
////        Transaction tx = null;
////
////        try {
////            tx = session.beginTransaction();
////
////            // Fetch the chef by employe
////            Chef chef = session.createQuery(
////                    "FROM Chef WHERE employe.id = :employeId", Chef.class
////            ).setParameter("employeId", employeId).uniqueResult();
////
////            if (chef != null) {
////                Departement departement = chef.getDepartement();
////                if (departement != null) {
////                    departement.setChef(null); // remove link from department
////                    session.update(departement);
////                }
////
////                // ⚠️ Update the role of the employee to EMPLOYE
////                Employe employe = chef.getEmploye();
////                employe.setRole("EMPLOYE");
////                session.update(employe);
////
////                // Delete the chef record
////                session.delete(chef);
////            }
////
////            tx.commit();
////        } catch (Exception e) {
////            if (tx != null) tx.rollback();
////            e.printStackTrace();
////        } finally {
////            session.close();
////        }
////    }
////
//public void retirerChef(Long employeId) {
//    Session session = HibernateUtil.getSessionFactory().openSession();
//    Transaction tx = null;
//
//    try {
//        tx = session.beginTransaction();
//
//        Chef chef = session.createQuery(
//                        "FROM Chef WHERE employe.id = :id AND dateFin IS NULL", Chef.class)
//                .setParameter("id", employeId)
//                .uniqueResult();
//
//        if (chef != null) {
//            chef.setDateFin(java.sql.Date.valueOf(LocalDate.now()));
//            session.update(chef);
//        }
//
//        tx.commit();
//    } catch (Exception e) {
//        if (tx != null) tx.rollback();
//        e.printStackTrace();
//    } finally {
//        session.close();
//    }
//}
//
//    public Chef getChefActuel(Long departementId) {
//        return chefDAO.getChefActuelByDepartementId(departementId);
//    }
//
//
//
//
//}
// ✅ ChefService.java – corrigé selon les entités et la logique métier définies

package service;

import dao.ChefDAO;
import dao.DepartementDAO;
import dao.EmployeDAO;
import model.Chef;
import model.Departement;
import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ChefService {

    private final ChefDAO chefDAO = new ChefDAO();
    private final EmployeDAO employeDAO = new EmployeDAO();
    private final DepartementDAO departementDAO = new DepartementDAO();

    public void nommerChef(Long employeId, Long departementId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // 1. Clôturer le chef actuel s’il existe
            Chef chefActuel = session.createQuery(
                            "FROM Chef WHERE departement.id = :depId AND dateFin IS NULL", Chef.class)
                    .setParameter("depId", departementId)
                    .uniqueResult();

            if (chefActuel != null) {
                chefActuel.setDateFin(java.sql.Date.valueOf(LocalDate.now()));
                session.update(chefActuel);
            }

            // 2. Nommer un nouveau chef
            Employe employe = session.get(Employe.class, employeId);
            Departement departement = session.get(Departement.class, departementId);

            Chef nouveauChef = new Chef();
            nouveauChef.setEmploye(employe);
            nouveauChef.setDepartement(departement);
            nouveauChef.setDateDebut(java.sql.Date.valueOf(LocalDate.now()));
            nouveauChef.setDateFin(null);

            session.persist(nouveauChef);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void retirerChef(Long employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Chef chef = session.createQuery(
                            "FROM Chef WHERE employe.id = :id AND dateFin IS NULL", Chef.class)
                    .setParameter("id", employeId)
                    .uniqueResult();

            if (chef != null) {
                chef.setDateFin(java.sql.Date.valueOf(LocalDate.now()));
                session.update(chef);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Chef getChefActuel(Long departementId) {
        return chefDAO.getChefActuelByDepartementId(departementId);
    }

    public List<Chef> getAllChefs() {
        return chefDAO.getAllChefs();
    }

    public Chef getChefById(Long id) {
        return chefDAO.getChefById(id);
    }

    public void deleteChef(Long id) {
        chefDAO.deleteChef(id);
    }

    public void saveChef(Chef chef) {
        chefDAO.saveOrUpdateChef(chef);
    }

    public void updateChef(Chef chef) {
        chefDAO.saveOrUpdateChef(chef);
    }

    public Chef findByEmployeId(Long employeId) {
        return chefDAO.findAncienChefByEmployeId(employeId);
    }

    public boolean isChef(Long employeId) {
        Chef actif = chefDAO.getAllChefs().stream()
                .filter(c -> c.getEmploye().getId().equals(employeId) && c.getDateFin() == null)
                .findFirst().orElse(null);
        return actif != null;
    }

    public Chef findByDepartementId(Long departementId) {
        return getChefActuel(departementId);
    }
}
