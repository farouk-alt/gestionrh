//package dao;
//
//import java.util.Date;
//import java.util.List;
//
//import model.Chef;
//import model.Departement;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import util.HibernateUtil;
//import org.hibernate.Hibernate; // ⬅️ à importer
//
//public class DepartementDAO {
//
//
//    public void saveDepartement(Departement departement) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(departement);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void updateDepartement(Departement departement) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.update(departement);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteDepartement(Long id) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            // 1. Clôturer le chef actuel si existe
//            Chef chef = session.createQuery(
//                            "FROM Chef WHERE departement.id = :id AND dateFin IS NULL", Chef.class)
//                    .setParameter("id", id)
//                    .uniqueResult();
//
//            if (chef != null) {
//                chef.setDateFin(new Date());
//                session.update(chef);
//            }
//
//            // 2. Supprimer le département
//            Departement departement = session.get(Departement.class, id);
//            if (departement != null) {
//                session.delete(departement);
//            }
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//
//    public Departement getDepartementById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Departement d = session.get(Departement.class, id);
//            if (d != null) {
//                Hibernate.initialize(d.getEmployes());
//                Hibernate.initialize(d.getAnciensChefs());
//            }
//            return d;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//
//    public List<Departement> getAllDepartements() {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            List<Departement> departements = session.createQuery("from Departement", Departement.class).list();
//
//            // Initialisation des listes employes pour éviter LazyInitializationException
//            for (Departement d : departements) {
//                Hibernate.initialize(d.getEmployes());  // 👈 important
//                Hibernate.initialize(d.getAnciensChefs());      // (optionnel) si tu accèdes aussi à chef
//            }
//
//            transaction.commit();
//            return departements;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public Departement getDepartementWithChefById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Departement d = session.get(Departement.class, id);
//            if (d != null) {
//                Hibernate.initialize(d.getAnciensChefs()); // pour récupérer tous les chefs
//            }
//            return d;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Departement> findDepartementsByChefActuel(Long employeId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery(
//                            "SELECT c.departement FROM Chef c WHERE c.employe.id = :empId AND c.dateFin IS NULL",
//                            Departement.class
//                    )
//                    .setParameter("empId", employeId)
//                    .list();
//        }
//    }
//
//
//    public Departement getDepartementWithEmployesAndChefById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Departement d = session.get(Departement.class, id);
//            if (d != null) {
//                Hibernate.initialize(d.getEmployes());
//                Hibernate.initialize(d.getAnciensChefs());
//            }
//            return d;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//
//
//    public Departement findByNom(String nom) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Departement where nom = :nom", Departement.class)
//                    .setParameter("nom", nom)
//                    .uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
package dao;

import model.Chef;
import model.Departement;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class DepartementDAO {

    public void saveDepartement(Departement departement) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(departement);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateDepartement(Departement departement) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(departement);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteDepartement(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Chef chef = session.createQuery(
                            "FROM Chef WHERE departement.id = :id AND dateFin IS NULL", Chef.class)
                    .setParameter("id", id)
                    .uniqueResult();
            if (chef != null) {
                chef.setDateFin(new Date());
                session.update(chef);
            }
            Departement d = session.get(Departement.class, id);
            if (d != null) session.delete(d);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Departement getDepartementById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Departement d = session.get(Departement.class, id);
            if (d != null) {
                Hibernate.initialize(d.getEmployes());
                Hibernate.initialize(d.getAnciensChefs());
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Departement> getAllDepartements() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Departement> departements = session.createQuery("from Departement", Departement.class).list();
            for (Departement d : departements) {
                Hibernate.initialize(d.getEmployes());
                Hibernate.initialize(d.getAnciensChefs());
            }
            return departements;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Departement findByNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Departement where nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Departement> findDepartementsByChefActuel(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT c.departement FROM Chef c WHERE c.employe.id = :empId AND c.dateFin IS NULL",
                            Departement.class)
                    .setParameter("empId", employeId)
                    .list();
        }
    }
    public Departement getDepartementWithEmployesAndChefById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Departement d = session.get(Departement.class, id);
            if (d != null) {
                Hibernate.initialize(d.getEmployes());
                Hibernate.initialize(d.getAnciensChefs());
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}