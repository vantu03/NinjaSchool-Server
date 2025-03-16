package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class SachTiemNang implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.limitTiemnangso >= 8) {
            NJUtil.sendServer(player.getSession(), AlertFunction.LIMIT_TIEMNANG_SO(player.getSession()));
            return;
        }
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + AlertFunction.TEN_POINT_POTENTIAL(player.getSession()));
        playerMain.potential_point += 10;
        ++playerMain.limitTiemnangso;
        player.updatePotential();
    }
}
