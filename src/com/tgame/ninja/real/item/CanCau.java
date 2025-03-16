package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class CanCau implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.timeWait > 0) {
            return;
        }
        if (player.map.getTemplateId() == 38 && player.x >= 72 && player.x <= 288 && player.y >= 336 && player.y <= 360) {
            player.createWait((byte) 5, item);
            player.sendShowWait(AlertFunction.WAIT_GET_FIND(player.getSession()));
            return;
        }
        player.endWait(AlertFunction.POINT_FAIL1(player.getSession()));
    }
}
