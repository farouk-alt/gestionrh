package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe utilitaire pour la gestion des erreurs
 */
public class ErrorHandler {

    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

    /**
     * Enregistre une exception dans les logs
     *
     * @param e L'exception à enregistrer
     * @param source La classe source de l'exception
     */
    public static void logException(Exception e, Class<?> source) {
        LOGGER.log(Level.SEVERE, "Exception dans " + source.getName() + ": " + e.getMessage(), e);
    }

    /**
     * Enregistre une exception dans les logs avec un message personnalisé
     *
     * @param e L'exception à enregistrer
     * @param message Le message personnalisé
     */
    public static void logException(Exception e, String message) {
        LOGGER.log(Level.SEVERE, message + ": " + e.getMessage(), e);
    }

    /**
     * Convertit une exception en chaîne de caractères (stack trace)
     *
     * @param e L'exception à convertir
     * @return La stack trace sous forme de chaîne
     */
    public static String getStackTraceAsString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    /**
     * Récupère le message d'erreur utilisateur approprié
     *
     * @param e L'exception
     * @param defaultMessage Message par défaut si l'exception n'a pas de message
     * @return Le message d'erreur
     */
    public static String getUserFriendlyMessage(Exception e, String defaultMessage) {
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            return e.getMessage();
        }
        return defaultMessage;
    }
}
