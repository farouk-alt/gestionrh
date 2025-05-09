package util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // No-op (optional: initialize resources here)
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Shutdown Hibernate and other resources
        HibernateUtil.shutdown();
    }
}