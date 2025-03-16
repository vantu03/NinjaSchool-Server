package com.tgame.ninja.model;

import com.tgame.ninja.real.Player;
import java.util.Comparator;

public class TopCaoThu {

    public String name;

    public long exp;

    public String str;

    public static final Comparator<TopCaoThu> sort = (o1, o2) -> (int) ((o2.exp - o1.exp) / 10000L);

    public short getLevel() {
        if (exp >= Player.maxExp1) {
            exp = Player.maxExp1;
        }
        long expRemain = exp;
        short i = 0;
        while (i < Player.exps1.length && expRemain >= Player.exps1[i]) {
            expRemain -= Player.exps1[i];
            ++i;
        }
        return i;
    }
}
