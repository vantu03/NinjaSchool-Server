package com.tgame.ninja.model;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Player;

public class BuNhin {

    public long timeEnd;

    public short x;

    public short y;

    public int dx;

    public int dy;

    public int hp;

    public Player player;

    public BuNhin(short x, short y, int time, Player player, int dx, int dy) {
        timeEnd = System.currentTimeMillis() + (long) time;
        this.x = x;
        this.y = y;
        this.player = player;
        hp = player.getFullHp();
        this.dx = dx;
        this.dy = dy;
    }

    public void update(Map map) {
        if (System.currentTimeMillis() > timeEnd) {
            clearBuNhin(map);
        }
    }

    public void clearBuNhin(Map map) {
        try {
            int bunhinId = map.removeBuNhin(this);
            Message message = new Message(Cmd.CLEAR_BUNHIN);
            message.writeShort(bunhinId);
            map.sendToPlayer(message);
        } catch (Exception e) {
        }
    }
}
