package com.tgame.ninja.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Net {

    public static Logger LOGGER = LoggerFactory.getLogger(Net.class);

    public static String get(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                return reader.readLine();
            }
        } catch (Exception e) {
            LOGGER.error("Request: " + urlStr, e);
        }
        return "";
    }
}
