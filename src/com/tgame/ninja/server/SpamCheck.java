package com.tgame.ninja.server;

public class SpamCheck {

    public long timeCheck = 0L;

    public long timeLock = 0L;

    public int countSpam = 0;

    public long getTimeLock() {
        return Math.max((timeLock - System.currentTimeMillis()) / 1000L, 0L);
    }
}
