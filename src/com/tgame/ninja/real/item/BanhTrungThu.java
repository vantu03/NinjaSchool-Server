package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class BanhTrungThu implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        switch (item.getTemplateId()) {
            case 298:
            case 299:
            case 300:
            case 301: {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                player.sendUpdateExp(3000000L, true);
                break;
            }
        }
    }
}
