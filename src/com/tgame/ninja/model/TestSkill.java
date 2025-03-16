package com.tgame.ninja.model;

import com.tgame.ninja.real.Player;

public class TestSkill {

    public long timeEnd;

    public Player player1;

    public Player player2;

    public int money1;

    public int money2;

    public TestSkill(int time) {
        timeEnd = System.currentTimeMillis() + time;
    }

    public int isTesting(Player p1, Player p2) {
        if ((p1.equals(player1) && p2.equals(player2)) || (p1.equals(player2) && p2.equals(player1))) {
            return -2;
        }
        return -1;
    }

    public Player getPlayer(Player p) {
        if (player1.equals(p)) {
            return player2;
        }
        return player1;
    }
}
