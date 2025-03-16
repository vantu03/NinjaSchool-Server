package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;

public class LiveObject {

    public boolean isPlayer() {
        return false;
    }

    public boolean isNpc() {
        return false;
    }

    public boolean isNpcPlayer() {
        return false;
    }

    public void objDie(Session s) {
    }

    public void objLive(Session s) {
    }

    public long getTimeResistDongBang() {
        return 0L;
    }

    public void setTimeResistDongBang(int time) {
    }
}
