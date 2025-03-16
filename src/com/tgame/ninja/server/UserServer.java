package com.tgame.ninja.server;

import com.tgame.ninja.model.Net;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServer.class);

    public static final String BASE_URL;

    static {
        if (GameServer.isServerLocal()) {
            BASE_URL = "http://127.0.0.1:14444";
        } else {
            BASE_URL = "http://127.0.0.1";
        }
    }

    public static String createTemp() {
        try {
            return Net.get(BASE_URL + "/usersv/create-temp").trim();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return "";
    }

    public static String registerTemp(String tmpname, String username, String password, int type, String bindInfo) {
        try {
            String dataBind = (type == 0 ? "email=" : "phone=") + bindInfo;
            String url = BASE_URL + "/usersv/bind-account?tmpname=" + tmpname + "&username=" + username + "&password=" + password + "&" + dataBind;
            return Net.get(url).trim();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return "";
    }
}
