package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public class TuanThuLenh implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        player.initPrivateMonster(230);
        player.useItemUpToUp(item, 1);
        player.sendUseItemUpToUp(item.indexUI, 1);
    }
}
