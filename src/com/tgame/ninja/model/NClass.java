package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import java.util.Vector;

public class NClass {

    public int classId;

    public String name;

    public String name_en;

    public Vector<SkillTemplate> skillTemplates;

    public NClass() {
        skillTemplates = new Vector<>();
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == 0) {
            return name;
        }
        return name_en;
    }
}
