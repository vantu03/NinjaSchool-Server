package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public class NhanSamNganNam implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        int exp = (int) (Player.exps1[playerMain.level] / 10L);
        player.sendUseItemUpToUp(item.indexUI, 1);
        player.useItemUpToUp(item);
        player.sendUpdateExp(exp, true);
    }
}
