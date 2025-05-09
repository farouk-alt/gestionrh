package controller;

import model.Employe;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;

@WebServlet("/init")
public class InitHibernateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        try {
            // Vérifier s’il existe déjà un admin
            Employe existing = session.createQuery("FROM Employe WHERE email = :email", Employe.class)
                    .setParameter("email", "karti@gmail.com")
                    .uniqueResult();

            if (existing != null) {
                resp.getWriter().println("ℹ️ Admin déjà existant : " + existing.getEmail());
            } else {
                Employe admin = new Employe();
                admin.setNom("Admin");
                admin.setPrenom("Super");
                admin.setEmail("karti@gmail.com");
                admin.setNomUtilisateur("admin");
                admin.setMotDePasse("1234"); // 🔐 À chiffrer dans une vraie appli
                admin.setDateCreation(new Date());
                admin.setSoldeConge(60);
                admin.setAdmin(true); // ✅ Ceci remplace le champ "role = ADMIN"

                session.save(admin);
                tx.commit();
                resp.getWriter().println("✅ Admin inséré avec succès !");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            resp.getWriter().println("❌ Erreur : " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
