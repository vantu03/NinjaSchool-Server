package com.tgame.ninja.real;

public class NpcSumon extends Npc {

    public int dame;

    public int npcAttId = -1;

    public int rangeX;

    public int rangeY;

    public NpcSumon(int npcId, int npcTemplateId, short dame) {
        super(npcId, npcTemplateId, null);
        rangeX = template.rangeAttack * 2;
        rangeY = template.rangeAttack;
        this.dame = dame;
    }

    public boolean isSumonAttack() {
        return template.npcTemplateId == 70 || template.npcTemplateId == 122 || template.npcTemplateId == 211 || template.npcTemplateId == 212 || template.npcTemplateId == 213 || template.npcTemplateId == 214 || template.npcTemplateId == 215 || template.npcTemplateId == 216 || template.npcTemplateId == 217;
    }

    public boolean isSuMonLongDen() {
        return template.npcTemplateId >= 205 && template.npcTemplateId <= 208;
    }

    public boolean isLongDenCaChep() {
        return template.npcTemplateId == 206;
    }

    public boolean isLongDentron() {
        return template.npcTemplateId == 205;
    }

    public boolean isLongDentrang() {
        return template.npcTemplateId == 208;
    }

    public boolean isLongDenSao() {
        return template.npcTemplateId == 207;
    }

    public boolean isThanThu() {
        return template.npcTemplateId >= 583 && template.npcTemplateId <= 589;
    }
}
