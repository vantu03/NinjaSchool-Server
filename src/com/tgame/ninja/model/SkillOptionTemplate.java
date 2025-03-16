package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;

public class SkillOptionTemplate {

    public int skillOptionTemplateId;

    public String name;

    public String name_en;

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == 0) {
            return name;
        }
        return name_en;
    }
}
