package com.tgame.ninja.model;

public class InviteClan {

    public int timeInvite;

    public int playerId;

    public InviteClan(int playerId) {
        timeInvite = 50;
        this.playerId = playerId;
    }
}
