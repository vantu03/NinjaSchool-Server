package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Dun;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class ChiaKhoaCoQuan implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.map.getTemplateId() != 35 || player.x < 1776 || player.x > 1872 || player.y != 432) {
            NJUtil.sendServer(player.getSession(), AlertFunction.POINT_FAIL1(player.getSession()));
            return;
        }
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        Dun dun = Dun.createDun(78);
        if (dun == null) {
            NJUtil.sendServer(player.getSession(), AlertFunction.DUN_78_CLOSE(player.getSession()));
            return;
        }
        int time = 900;
        dun.playerOpen = player;
        dun.timeEnd = System.currentTimeMillis() + time * 1000;
        player.mapClear();
        player.map.goOutMap(player);
        player.x = 0;
        player.y = 0;
        player.sendMapTime(time);
        dun.waitGoInMap(player);
    }
}
