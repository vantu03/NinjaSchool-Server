package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TayTiemNang implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        ++playerMain.resetPotential;
        NJUtil.sendServer(player.getSession(), AlertFunction.YOU_HAVE_POTENTIAL(player.getSession()) + " " + playerMain.resetPotential);
    }
}
