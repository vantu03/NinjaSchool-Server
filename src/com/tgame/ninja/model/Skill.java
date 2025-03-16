package com.tgame.ninja.model;

import java.util.Vector;

public class Skill {

    public SkillTemplate template;

    public int skillId;

    public int point;

    public int level;

    public int timeReplay;

    public int dx;

    public int dy;

    public int maxFight;

    public int manaUse;

    public Vector<SkillOption> options;

    public long timeAttackNext;

    public int accept;

    public Skill() {
        options = new Vector<>();
    }

    public Skill cloneSkill() {
        Skill newSkill = new Skill();
        newSkill.template = template;
        newSkill.skillId = skillId;
        newSkill.point = point;
        newSkill.level = level;
        newSkill.timeReplay = timeReplay;
        newSkill.dx = dx;
        newSkill.dy = dy;
        newSkill.maxFight = maxFight;
        newSkill.manaUse = manaUse;
        newSkill.options = options;
        newSkill.timeAttackNext = 0L;
        return newSkill;
    }

    public boolean isSkill6x() {
        return template.skillTemplateId >= 55 && template.skillTemplateId <= 60;
    }

    public boolean isSkillPhanThan() {
        return template.skillTemplateId >= 67 && template.skillTemplateId <= 72;
    }

    public boolean isSkillAttack() {
        return template.isSkillAttack();
    }

    public boolean isBuffMe() {
        return false;
    }

    public boolean isBuffBoth() {
        return template.type == 2;
    }

    public boolean isBuffSkill() {
        return isBuffMe() || isBuffBoth();
    }

    public boolean isBussHoiSinh() {
        return template.skillTemplateId == 49;
    }
}
