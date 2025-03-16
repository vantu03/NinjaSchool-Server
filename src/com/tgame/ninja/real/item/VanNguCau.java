package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class VanNguCau implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.timeWait > 0) {
            return;
        }
        if (player.map.getTemplateId() == 32 && player.x >= 100 && player.x <= 2705 && player.y >= 450 && player.y <= 456) {
            player.createWaitCauCa((byte) 3, item);
            player.sendShowWait(AlertFunction.WAIT_GET_FIND(player.getSession()));
            return;
        }
        player.endWait(AlertFunction.POINT_FAIL1(player.getSession()));
    }
}
