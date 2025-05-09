//package dao;
//
//import java.util.List;
//
//import model.Departement;
//import model.Employe;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import util.HibernateUtil;
//
//public class EmployeDAO {
//
//    public void saveEmploye(Employe employe) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(employe);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void updateEmploye(Employe employe) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.update(employe);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteEmploye(Long id) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            Employe employe = session.get(Employe.class, id);
//            if (employe != null) {
//                session.delete(employe);
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public Employe getEmployeById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(Employe.class, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Employe> getAllEmployes() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Employe", Employe.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Employe findByEmail(String email) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Employe> query = session.createQuery("from Employe where email = :email", Employe.class);
//            query.setParameter("email", email);
//            return query.uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Employe> getEmployesByDepartement(Long departementId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Employe> query = session.createQuery("from Employe where departement.id = :deptId", Employe.class);
//            query.setParameter("deptId", departementId);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Employe authenticate(String email, String password) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Employe> query = session.createQuery(
//                "from Employe where email = :email and motDePasse = :password",
//                Employe.class
//            );
//            query.setParameter("email", email);
//            query.setParameter("password", password);
//            return query.uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Employe authenticateWithChef(String email, String password) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery(
//                            "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.email = :email AND e.motDePasse = :password",
//                            Employe.class
//                    )
//                    .setParameter("email", email)
//                    .setParameter("password", password)
//                    .uniqueResult();
//        }
//    }
//
//
//
//
//}
package dao;

import model.Employe;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

public class EmployeDAO {

    public void saveEmploye(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(employe);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void updateEmploye(Employe employe) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(employe);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void deleteEmploye(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Employe employe = session.get(Employe.class, id);
            if (employe != null) {
                session.delete(employe);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Employe getEmployeById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employe> getAllEmployes() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employe> employes = session.createQuery("from Employe", Employe.class).list();
        for (Employe e : employes) {
            Hibernate.initialize(e.getHistoriqueChef()); // 👈 important
        }
        session.close();
        return employes;
    }


    public Employe findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe where email = :email", Employe.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Employe> getEmployesByDepartement(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe where departement.id = :deptId", Employe.class)
                    .setParameter("deptId", departementId)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employe authenticate(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Employe where email = :email and motDePasse = :password", Employe.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
