package com.tgame.ninja.model;

import java.util.Vector;

public class Frame {

    public Vector<byte[]> datas;

    public int id;

    public long time;

    public Frame() {
        datas = new Vector<>();
    }
}
