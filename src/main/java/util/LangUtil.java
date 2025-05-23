package util;

import jakarta.servlet.http.HttpSession;

import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class LangUtil {

    public static String get(String key, HttpSession session) {
        String lang = (String) session.getAttribute("lang");
        if (lang == null) lang = "fr";

        Locale locale = switch (lang) {
            case "en" -> Locale.ENGLISH;
            default -> Locale.FRENCH;
        };

        try {
            ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", locale, new UTF8Control());
            return bundle.getString(key);
        } catch (Exception e) {
            return "???" + key + "???";
        }
    }

    // ✅ Cette classe permet de lire les fichiers properties avec UTF-8
    private static class UTF8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                        ClassLoader loader, boolean reload) throws java.io.IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream stream = loader.getResourceAsStream(resourceName)) {
                if (stream != null) {
                    return new PropertyResourceBundle(new InputStreamReader(stream, StandardCharsets.UTF_8));
                }
            }
            return null;
        }
    }
}
