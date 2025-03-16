package com.tgame.ninja.model;

import com.tgame.ninja.real.Npc;
import java.util.Vector;

public class SumonHide {

    public short dame;

    public short rangeX;

    public short rangeY;

    public Vector<Npc> attNpcs;

    public SumonHide(short dame, short rangeX, short rangeY) {
        attNpcs = null;
        this.dame = dame;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }
}
