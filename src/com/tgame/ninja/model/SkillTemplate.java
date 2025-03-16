package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import java.util.Vector;

public class SkillTemplate {

    public int skillTemplateId;

    public int classId;

    public String name;

    public String name_en;

    public int maxPoint;

    public int type;

    public int iconId;

    public String description;

    public String description_en;

    public Vector<Skill> skills;

    public SkillTemplate() {
        skills = new Vector<>();
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == 0) {
            return name;
        }
        return name_en;
    }

    public String getDescription(Session conn) {
        if (conn == null || conn.typeLanguage == 0) {
            return description;
        }
        return description_en;
    }

    public boolean isSkillPassive() {
        return type == 0;
    }

    public boolean isSkillAttack() {
        return type == 1;
    }

    public boolean isSkillBuffMe() {
        return type == 2;
    }

    public boolean isBuffQuai() {
        return type == 3;
    }
}
