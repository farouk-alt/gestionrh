//package dao;
//
//import java.util.Date;
//import java.util.List;
//import model.Chef;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import util.HibernateUtil;
//
//public class ChefDAO {
//
//    public void saveOrUpdateChef(Chef chef) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            // On utilise merge pour éviter les duplications
//            session.merge(chef);
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//    }
//
//
//    public void updateChef(Chef chef) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.update(chef);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteChef(Long id) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            Chef chef = session.get(Chef.class, id);
//            if (chef != null) {
//                session.delete(chef);
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
//    public Chef getChefById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(Chef.class, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Chef> getAllChefs() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Chef", Chef.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Chef findByEmployeId(Long employeId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Chef where employe.id = :employeId", Chef.class)
//                    .setParameter("employeId", employeId)
//                    .uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Chef findByDepartementId(Long departementId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from Chef where departement.id = :departementId", Chef.class)
//                    .setParameter("departementId", departementId)
//                    .uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    public Chef findAncienChefByEmployeId(Long employeId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery(
//                            "from Chef where employe.id = :employeId order by dateNomination desc",
//                            Chef.class
//                    )
//                    .setParameter("employeId", employeId)
//                    .setMaxResults(1)
//                    .uniqueResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public void reactiverAncienChef(Long chefId) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            Chef chef = session.get(Chef.class, chefId);
//            if (chef != null) {
//                chef.setDateNomination(new java.util.Date());
//                chef.setDateFin(null);
//                session.update(chef);
//            }
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//    }
//    public void cloturerChefActuel(Long departementId) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//
//            Chef chefActuel = session.createQuery(
//                            "FROM Chef WHERE departement.id = :depId AND dateFin IS NULL", Chef.class)
//                    .setParameter("depId", departementId)
//                    .uniqueResult();
//
//            if (chefActuel != null) {
//                chefActuel.setDateFin(new Date());
//                session.update(chefActuel);
//            }
//
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//        }
//    }
//    public Chef getChefActuelByDepartementId(Long departementId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery(
//                            "FROM Chef WHERE departement.id = :depId AND dateFin IS NULL", Chef.class)
//                    .setParameter("depId", departementId)
//                    .uniqueResult();
//        }
//    }
//
//
//
//
//}
// ✅ DAO finalisé selon les entités générées et les exigences fonctionnelles du cahier des charges

// ───────────────────────────────────────────────────────
// 🔹 ChefDAO.java
package dao;

import model.Chef;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class ChefDAO {

    public void saveOrUpdateChef(Chef chef) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(chef);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Chef getChefActuelByDepartementId(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Chef WHERE departement.id = :depId AND dateFin IS NULL", Chef.class)
                    .setParameter("depId", departementId)
                    .uniqueResult();
        }
    }

    public void cloturerChefActuel(Long departementId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Chef chef = getChefActuelByDepartementId(departementId);
            if (chef != null) {
                chef.setDateFin(new Date());
                session.update(chef);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void reactiverAncienChef(Long chefId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Chef chef = session.get(Chef.class, chefId);
            if (chef != null) {
                chef.setDateFin(null);
                chef.setDateDebut(new Date());
                session.update(chef);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public List<Chef> getAllChefs() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Chef", Chef.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Chef getChefById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Chef.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteChef(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Chef chef = session.get(Chef.class, id);
            if (chef != null) {
                session.delete(chef);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public Chef findAncienChefByEmployeId(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Chef where employe.id = :employeId order by dateDebut desc",
                            Chef.class)
                    .setParameter("employeId", employeId)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Chef> findChefsByEmployeId(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Chef where employe.id = :employeId",
                            Chef.class)
                    .setParameter("employeId", employeId)
                    .list();
        }
    }
    public boolean isChefActuel(Long employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                            "SELECT COUNT(c) FROM Chef c WHERE c.employe.id = :empId AND c.dateFin IS NULL", Long.class)
                    .setParameter("empId", employeId)
                    .uniqueResult();

            return count != null && count > 0;
        } finally {
            session.close();
        }
    }

}

