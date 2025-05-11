//package dao;
//
//import java.util.List;
//import model.DemandeConge;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import util.HibernateUtil;
//
//public class DemandeCongeDAO {
//
//    public void saveDemandeConge(DemandeConge demandeConge) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.save(demandeConge);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void updateDemandeConge(DemandeConge demandeConge) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            session.update(demandeConge);
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteDemandeConge(Long id) {
//        Transaction transaction = null;
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            transaction = session.beginTransaction();
//            DemandeConge demandeConge = session.get(DemandeConge.class, id);
//            if (demandeConge != null) {
//                session.delete(demandeConge);
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
//    public DemandeConge getDemandeCongeById(Long id) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.get(DemandeConge.class, id);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<DemandeConge> getAllDemandesConge() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery("from DemandeConge", DemandeConge.class).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<DemandeConge> getDemandesCongeByEmploye(Long employeId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<DemandeConge> query = session.createQuery(
//                "from DemandeConge where employe.id = :employeId",
//                DemandeConge.class
//            );
//            query.setParameter("employeId", employeId);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<DemandeConge> getDemandesCongeByDepartement(Long departementId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<DemandeConge> query = session.createQuery(
//                "from DemandeConge dc where dc.employe.departement.id = :departementId",
//                DemandeConge.class
//            );
//            query.setParameter("departementId", departementId);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<DemandeConge> getDemandesCongeEnAttente() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            return session.createQuery(
//                "from DemandeConge where statut = 'EN_ATTENTE'",
//                DemandeConge.class
//            ).list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<DemandeConge> getDemandesCongeEnAttenteByDepartement(Long departementId) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<DemandeConge> query = session.createQuery(
//                "from DemandeConge dc where dc.employe.departement.id = :departementId and dc.statut = 'EN_ATTENTE'",
//                DemandeConge.class
//            );
//            query.setParameter("departementId", departementId);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
package dao;

import model.DemandeConge;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DemandeCongeDAO {

    public void save(DemandeConge demande) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.save(demande);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public void update(DemandeConge demande) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.update(demande);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

    public DemandeConge getById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(DemandeConge.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DemandeConge> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from DemandeConge", DemandeConge.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<DemandeConge> getByEmploye(Long employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from DemandeConge where employe.id = :eid", DemandeConge.class)
                    .setParameter("eid", employeId)
                    .list();
        }
    }

    public List<DemandeConge> getEnAttenteByDepartement(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from DemandeConge where employe.departement.id = :did and etat = 'EN_ATTENTE' order by dateMiseAjour asc",
                            DemandeConge.class)
                    .setParameter("did", departementId)
                    .list();
        }
    }

    public List<DemandeConge> getTraiteesByDepartement(Long departementId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from DemandeConge where employe.departement.id = :did and etat <> 'EN_ATTENTE'",
                            DemandeConge.class)
                    .setParameter("did", departementId)
                    .list();
        }
    }
    public List<DemandeConge> rechercherParCriteres(String employeNom, String employePrenom, String nomDepartement, Date dateMiseAjour) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("SELECT d FROM DemandeConge d WHERE 1=1");

            if (employeNom != null && !employeNom.isEmpty()) {
                hql.append(" AND lower(d.employe.nom) LIKE :nom");
            }
            if (employePrenom != null && !employePrenom.isEmpty()) {
                hql.append(" AND lower(d.employe.prenom) LIKE :prenom");
            }
            if (nomDepartement != null && !nomDepartement.isEmpty()) {
                hql.append(" AND lower(d.employe.departement.nom) LIKE :dep");
            }
            if (dateMiseAjour != null) {
                hql.append(" AND date(d.dateMiseAjour) = :dateMaj");
            }

            Query<DemandeConge> query = session.createQuery(hql.toString(), DemandeConge.class);

            if (employeNom != null && !employeNom.isEmpty()) {
                query.setParameter("nom", "%" + employeNom.toLowerCase() + "%");
            }
            if (employePrenom != null && !employePrenom.isEmpty()) {
                query.setParameter("prenom", "%" + employePrenom.toLowerCase() + "%");
            }
            if (nomDepartement != null && !nomDepartement.isEmpty()) {
                query.setParameter("dep", "%" + nomDepartement.toLowerCase() + "%");
            }
            if (dateMiseAjour != null) {
                query.setParameter("dateMaj", dateMiseAjour);
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public DemandeConge findByCritere(Date dateMaj, String nomEmp, String prenomEmp, String nomDepartement) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery(
                            "SELECT d FROM DemandeConge d " +
                                    "WHERE DATE(d.dateMiseAjour) = :dateMaj " +
                                    "AND LOWER(d.employe.nom) = :nomEmp " +
                                    "AND LOWER(d.employe.prenom) = :prenomEmp " +
                                    "AND LOWER(d.employe.departement.nom) = :nomDepartement", DemandeConge.class)
                    .setParameter("dateMaj", dateMaj)
                    .setParameter("nomEmp", nomEmp.toLowerCase())
                    .setParameter("prenomEmp", prenomEmp.toLowerCase())
                    .setParameter("nomDepartement", nomDepartement.toLowerCase())
                    .uniqueResult();
        } finally {
            session.close();
        }
    }
    public int countAllByDepartement(Long departementId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                            "SELECT COUNT(d) FROM DemandeConge d WHERE d.employe.departement.id = :depId", Long.class)
                    .setParameter("depId", departementId)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        } finally {
            session.close();
        }
    }

    public int countByEtatAndDepartement(String etat, Long departementId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Long count = session.createQuery(
                            "SELECT COUNT(d) FROM DemandeConge d WHERE d.etat = :etat AND d.employe.departement.id = :depId", Long.class)
                    .setParameter("etat", model.DemandeConge.EtatDemande.valueOf(etat))
                    .setParameter("depId", departementId)
                    .uniqueResult();
            return count != null ? count.intValue() : 0;
        } finally {
            session.close();
        }
    }
    public boolean existsPendingByEmploye(Long employeId) {
        String hql = "SELECT COUNT(d) FROM DemandeConge d WHERE d.etat = :etat AND d.employe.id = :employeId";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = session.createQuery(hql, Long.class)
                .setParameter("etat", DemandeConge.EtatDemande.EN_ATTENTE)
                .setParameter("employeId", employeId)
                .uniqueResult();
        return count != null && count > 0;
    }
    public int countAcceptedStillActiveByDepartement(Long departementId) {
        String hql = "SELECT COUNT(d) FROM DemandeConge d WHERE d.etat = :etat AND d.employe.departement.id = :depId AND d.dateFin >= :today";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Long count = session.createQuery(hql, Long.class)
                .setParameter("etat", DemandeConge.EtatDemande.ACCEPTE)
                .setParameter("depId", departementId)
                .setParameter("today", new Date())
                .uniqueResult();
        return count != null ? count.intValue() : 0;
    }
    public void delete(Long id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            DemandeConge demande = session.get(DemandeConge.class, id);
            if (demande != null) {
                session.delete(demande);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }





}
