package com.tgame.ninja.model;

import com.tgame.ninja.real.Player;

public class Attack {

    public int dameHit;

    public int typeAtt;

    public Player playerHit;

    public Attack(Player playerHit, int dameHit, int typeAtt) {
        this.playerHit = playerHit;
        this.dameHit = dameHit;
        this.typeAtt = typeAtt;
    }
}
