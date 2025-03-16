package com.tgame.ninja.model;

import com.tgame.ninja.real.Player;

public class KillPlayer {

    public long timeEnd;

    public Player player;

    public KillPlayer(Player player) {
        this.player = player;
        timeEnd = System.currentTimeMillis() + 100000L;
    }
}
