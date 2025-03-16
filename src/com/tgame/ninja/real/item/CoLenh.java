package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class CoLenh implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (!GameServer.openLangCo) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
            return;
        }
        Map mapNext = Map.findMap(player, 138);
        player.mapClear();
        player.map.goOutMap(player);
        player.x = mapNext.template.defaultX;
        player.y = mapNext.template.defaultY;
        mapNext.waitGoInMap(player);
        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
    }
}
