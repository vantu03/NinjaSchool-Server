package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public interface IUseHandler {

    void useItem(Player player, Item item);
}
