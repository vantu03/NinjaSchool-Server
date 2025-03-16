package com.tgame.ninja.model;

public class Login {

    public int count;

    public long timeConnect;

    public long timeLock;

    public Login() {
        count = 1;
        timeConnect = System.currentTimeMillis();
        timeLock = 300000L;
    }

    public Login(int count) {
        this.count = count;
        timeConnect = System.currentTimeMillis();
        timeLock = 300000L;
    }
}
