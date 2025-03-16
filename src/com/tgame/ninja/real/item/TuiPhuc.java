package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TuiPhuc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
        NJUtil.sendServer(player.getSession(), "Chúc bạn may mắn lần sau :))");
    }
}
