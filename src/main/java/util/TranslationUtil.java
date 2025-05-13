// 📁 src/util/TranslationUtil.java
package util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

public class TranslationUtil {

    public static String translateViaAPI(String text, String targetLang) throws IOException {
        URL url = new URL("https://libretranslate.com/translate");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String data = "q=" + URLEncoder.encode(text, "UTF-8") +
                "&source=auto&target=" + targetLang + "&format=text";

        try (OutputStream os = conn.getOutputStream()) {
            os.write(data.getBytes());
        }

        String response = new BufferedReader(new InputStreamReader(conn.getInputStream()))
                .lines().collect(Collectors.joining());

        // ✅ Extraction simple (tu peux aussi utiliser un parser JSON si tu préfères)
        return response.substring(response.indexOf(":\"") + 2, response.lastIndexOf("\""));
    }
}
