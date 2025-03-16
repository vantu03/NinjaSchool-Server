package com.tgame.ninja.model;

public class BoardPlayer {

    public int id;

    public String playerName;

    public long coin_bag_xu;

    public long coin_lock_yen;

    public long gold;

    public long exp;

    public String sender;

    public int ticket;

    public BoardPlayer(int id, String playerName, long coin_bag, long coin_lock, long exp, String sender, long gold, int ticket) {
        this.id = id;
        this.playerName = playerName;
        coin_bag_xu = coin_bag;
        coin_lock_yen = coin_lock;
        this.exp = exp;
        this.sender = sender;
        this.gold = gold;
        this.ticket = ticket;
    }
}
