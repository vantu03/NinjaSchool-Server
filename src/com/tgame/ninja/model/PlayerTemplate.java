package com.tgame.ninja.model;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.server.GameServer;

public class PlayerTemplate {

    public int playerTemplateId;

    public String name;

    public String name_en;

    public int headId;

    public int bodyId;

    public int legId;

    public String[][] menu;

    public String[][] menu_en;

    public String[][] menu_event;

    public String[] say;

    public String[] say_en;

    public int optionId;

    public int menuId;

    public PlayerTemplate() {
        optionId = -1;
        menuId = -1;
    }

    public String[][] getMenu() {
        return EventManager.getInstance().handleNpcMenu(this);
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return name;
        }
        return name_en;
    }

    public String[] getSay(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return say;
        }
        return say_en;
    }
}
