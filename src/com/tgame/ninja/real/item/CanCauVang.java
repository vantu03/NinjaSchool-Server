package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public class CanCauVang implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.timeCauca > 0) {
            return;
        }
        if (player.map.template.tileTypeAt(player.x, player.y, 64) || player.map.template.tileTypeAt(player.x, player.y - 1, 64) || player.map.template.tileTypeAt(player.x, player.y, 2048) || player.map.template.tileTypeAt(player.x, player.y - 1, 2048)) {
            player.createWaitCauCa((byte) 5, item);
            player.sendShowWait(AlertFunction.WAIT_GET_FIND(player.getSession()));
            return;
        }
        player.endWaitCauCa(AlertFunction.POINT_FAIL1(player.getSession()));
    }
}
