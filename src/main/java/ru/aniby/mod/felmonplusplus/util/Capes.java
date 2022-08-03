package ru.aniby.mod.felmonplusplus.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class Capes {
    public static String getURL(String capeName) {
        return "https://repos.felmon.xyz/capes/" + capeName.toLowerCase(Locale.ROOT) + ".png";
    }

    public static String getWithNickname(String nickname) {
        try {
            URL url = new URL("https://api.felmon.xyz/cape?nickname=" + nickname);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String cape = new JSONObject(result.toString()).getString("cape");
//            Felmon.LOGGER.info("Got felmon's cape for " + nickname + ": " + cape);
            return getURL(cape);
        } catch (Exception ignored) {
            return "";
        }
    }
}
