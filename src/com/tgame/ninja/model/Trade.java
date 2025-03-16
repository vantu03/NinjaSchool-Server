package com.tgame.ninja.model;

import com.tgame.ninja.real.Player;
import java.util.Vector;

public class Trade {

    public int timeTrade;

    public Player player1;

    public int step1;

    public int coin1;

    public Item[] itemTrades1;

    public boolean isFinishTrade;

    public Player player2;

    public int step2;

    public int coin2;

    public int idTrade;

    public Item[] itemTrades2;

    public long timeFinishTrade;

    public Trade(Player player1, Player player2) {
        timeTrade = 10;
        itemTrades1 = new Item[12];
        isFinishTrade = false;
        idTrade = -1;
        itemTrades2 = new Item[12];
        timeFinishTrade = 0L;
        this.player1 = player1;
        this.player2 = player2;
    }

    public boolean isTimeOver() {
        return timeFinishTrade > 0L && System.currentTimeMillis() - timeFinishTrade >= 0L;
    }

    public Vector<Item> getItemTrade(int pos) {
        Item[] it = itemTrades1;
        if (pos == 1) {
            it = itemTrades2;
        }
        Vector<Item> item = new Vector<>();
        for (Item value : it) {
            if (value != null) {
                item.add(value);
            }
        }
        return item;
    }

    public boolean isContainItem(Item item, int pos) {
        Item[] it = itemTrades1;
        if (pos == 1) {
            it = itemTrades2;
        }
        for (Item value : it) {
            if (value != null && item != null && value.indexUI == item.indexUI) {
                return true;
            }
        }
        return false;
    }

    public boolean isAddItemTrade(Item item, int pos) {
        if (pos == 0) {
            for (int i = 0; i < itemTrades1.length; ++i) {
                if (itemTrades1[i] == null) {
                    itemTrades1[i] = item;
                    return true;
                }
            }
        } else if (pos == 1) {
            for (int i = 0; i < itemTrades2.length; ++i) {
                if (itemTrades2[i] == null) {
                    itemTrades2[i] = item;
                    return true;
                }
            }
        }
        return false;
    }
}
