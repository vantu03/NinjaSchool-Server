package com.tgame.ninja.model;

public class InviteTrade
{
    public int playerId;
    public long timeStart;
    
    public InviteTrade(int playerId) {
        this.playerId = playerId;
        timeStart = System.currentTimeMillis();
    }
}
