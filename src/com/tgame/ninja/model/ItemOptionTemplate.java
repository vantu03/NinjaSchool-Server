package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;

public class ItemOptionTemplate {

    public int itemOptionTemplateId;

    public String name;

    public String name_en;

    public int type;

    public int[] VALUE_GEM_UP;

    public int[] VALUE_GEM_GIAM;

    public ItemOptionTemplate() {
        VALUE_GEM_UP = null;
        VALUE_GEM_GIAM = null;
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == 0) {
            return name;
        }
        return name_en;
    }

    public void setInfoValueUp(String info) {
        if (info == null || info.equals("0")) {
            return;
        }
        String[] data = info.split(",");
        VALUE_GEM_UP = new int[data.length];
        for (int i = 0; i < data.length; ++i) {
            VALUE_GEM_UP[i] = Integer.parseInt(data[i]);
        }
    }

    public void setInfoValueGiam(String info) {
        if (info == null || info.equals("0")) {
            return;
        }
        String[] data = info.split(",");
        VALUE_GEM_GIAM = new int[data.length];
        for (int i = 0; i < data.length; ++i) {
            VALUE_GEM_GIAM[i] = Integer.parseInt(data[i]);
        }
    }
}
