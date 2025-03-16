package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public class ThanThuFood implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        player.doEat(item);
    }
}
