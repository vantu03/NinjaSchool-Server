package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class BinhRong implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        boolean isOk = false;
        for (int l = 0; l < player.itemBags.length; ++l) {
            if (player.itemBags[l] == null || player.itemBags[l].getTemplateId() == 220) {
                isOk = true;
                break;
            }
        }
        if (player.timeWait > 0) {
            return;
        }
        if (!isOk) {
            NJUtil.sendServer(player.getSession(), AlertFunction.EMPTY_ONE_WATER(player.getSession()));
            return;
        }
        if ((player.map.getTemplateId() == 63 && player.x >= 1680 && player.x <= 1896 && player.y >= 322 && player.y <= 336) || (player.map.getTemplateId() == 24 && player.x >= 155 && player.x <= 589 && player.y >= 60 && player.y <= 384)) {
            player.createWait((byte) 5, item);
            player.sendShowWait(AlertFunction.WAIT_GET_WATER(player.getSession()));
            return;
        }
        player.endWait(AlertFunction.POINT_FAIL(player.getSession()));
    }
}
