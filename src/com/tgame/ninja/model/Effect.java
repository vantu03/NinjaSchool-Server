package com.tgame.ninja.model;

import java.util.Vector;

public class Effect {

    public static final byte EFF_ME = 0;

    public static final byte EFF_FRIEND = 1;

    public EffectTemplate template;

    public int timeStart;

    public int timeLength;

    public int param;

    public Vector<SkillOption> options;

    public Effect clone() {
        Effect eff = new Effect();
        eff.template = template;
        eff.timeStart = timeStart;
        eff.timeLength = timeLength;
        eff.param = param;
        eff.options = options;
        return eff;
    }
}
