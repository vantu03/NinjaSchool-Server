package com.tgame.ninja.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.SessionManager;
import com.tgame.ninja.real.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketAPI implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketAPI.class);

    public static final String CMD_SHUT_DOWN = "shutdown";

    public static final String CMD_NAP_TIEN = "checknap";

    public static final String CMD_BAN_NAME = "ban";

    public static final String CMD_UNBAN_NAME = "unban";

    public static final String CMD_RELOAD_CONFIG = "reload";

    public static final String CMD_GET_SERVER_INFO = "serverinfo";

    private final Socket clientSocket;

    public SocketAPI(Socket clientSocket) {
        this.clientSocket = clientSocket;
        new Thread(this).start();
    }

    public void writeText(String text) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(clientSocket.getOutputStream())) {
            out.write((text + "\r\n").getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }

    @Override
    public void run() {
        try (
            BufferedInputStream buffer = new BufferedInputStream(clientSocket.getInputStream());
            DataInputStream input = new DataInputStream(buffer)
        ) {
            String str = input.readUTF();
            Pattern pattern = Pattern.compile("^(\\w+) (\\w+)? ?(\\w+)? ?(.*)");
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                JsonObject json = new JsonObject();
                json.addProperty("error", "May la ai?");
                writeText(new Gson().toJson(json));
                return;
            }
            if (!matcher.group(1).equals("ninjaadmin")) {
                return;
            }
            String command = "";
            String param = "";
            String param2 = "";
            try {
                command = matcher.group(2);
            } catch (Exception e) {

            }
            try {
                param = matcher.group(3);
            } catch (Exception e) {

            }
            try {
                param2 = matcher.group(4);
            } catch (Exception e) {

            }
            LOGGER.info("Receive message from web: {}", str);
            switch (command) {
                case CMD_BAN_NAME: {
                    JsonObject json = new JsonObject();
                    Player playerFind = ServerController.hnPlayers.get(param);
                    if (playerFind != null) {
                        playerFind.ban = true;
                        Database.saveLogAll(playerFind.name, "li do: " + param2, "banchar");
                        playerFind.getSession().disconnect("ban player");
                        LOGGER.info("Ban player {}. Reason: {}", playerFind.name, param2);
                        json.addProperty("message", "Đã ban " + param);
                    } else if (Database.banPlayer(param)) {
                        LOGGER.info("Ban player from DB {}. Reason: {}", param, param2);
                        Database.saveLogAll(param, "li do: " + param2, "banchar");
                        json.addProperty("message", "Đã ban " + param);
                    } else {
                        json.addProperty("error", "Không tìm thấy nhân vật này");
                    }
                    writeText(new Gson().toJson(json));
                    break;
                }
                case CMD_UNBAN_NAME: {
                    JsonObject json = new JsonObject();
                    if (Database.removeBanPlayer(param)) {
                        Database.saveLogAll(param, param + " remove ban. li do: " + param2, "banchar");
                        LOGGER.info("Web API: Remove ban player {}. Reason: {}", param, param2);
                        json.addProperty("message", "Đã gỡ lệnh ban " + param);
                    } else {
                        json.addProperty("message", "Không tìm thấy nhân vật này");
                    }
                    writeText(new Gson().toJson(json));
                    break;
                }
                case CMD_SHUT_DOWN: {
                    if (param.equals(GameServer.pwdCommand)) {
                        ServerController.turnOnExit = true;
                        JsonObject json = new JsonObject();
                        json.addProperty("message", "Chuẩn bị tắt server...");
                        writeText(new Gson().toJson(json));
                    }
                    break;
                }
                case CMD_NAP_TIEN: {
                    try {
                        Player playerFind = ServerController.hunPlayers.get(param);
                        if (playerFind != null && playerFind.getSession() != null) {
                            playerFind.isLoadPlayerNap = false;
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    break;
                }
                case CMD_RELOAD_CONFIG: {
                    if (GameServer.getInstance().loadGameConfig()) {
                        LOGGER.info("Web API: Game config reloaded");
                        JsonObject json = new JsonObject();
                        json.addProperty("message", "Đã reload lại config!");
                        writeText(new Gson().toJson(json));
                    }
                    break;
                }
                case CMD_GET_SERVER_INFO: {
                    LOGGER.info("Web API: Fetch info server");
                    JsonObject json = new JsonObject();
                    json.addProperty("free_mem", Runtime.getRuntime().freeMemory() / 1024 / 1024);
                    json.addProperty("total_mem", Runtime.getRuntime().totalMemory() / 1024 / 1024);
                    json.addProperty("sessions", SessionManager.instance.size());
                    json.addProperty("players", ServerController.huPlayers.size());
                    json.addProperty("gamepools_idle", Database.connPool.getIdleCount());
                    json.addProperty("gamepools_active", Database.connPool.getActiveCount());
                    json.addProperty("webpools_idle", Database.connPool.getIdleCount());
                    json.addProperty("webpools_active", Database.connPool.getActiveCount());
                    writeText(new Gson().toJson(json));
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
