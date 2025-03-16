package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class RuongTrangBi implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.classId <= 0) {
            NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
            return;
        }
        if (player.countFreeBag() < 1) {
            NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(player.getSession()), "1"));
            return;
        }
        int upgrade = 0;
        if (item.getTemplateId() == 383) {
            upgrade = 12;
        } else if (item.getTemplateId() == 384) {
            upgrade = 14;
        } else if (item.getTemplateId() == 385) {
            upgrade = 16;
        }
        if (playerMain.level < 50 && player.addItemToBagNoDialog(player.getItem4xRandom(upgrade, new int[]{ 0, 1, 1, 2, 2, 2 }, item.isLock))) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
            return;
        } else if (playerMain.level < 70 && player.addItemToBagNoDialog(player.getItem5xRandom(upgrade, new int[]{ 0, 1, 1, 2, 2, 2 }, item.isLock))) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
            return;
        } else {
            if (player.addItemToBagNoDialog(player.getItem7xRandom(upgrade, new int[]{ 0, 1, 1, 2, 2, 2 }, item.isLock))) {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                return;
            }
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.ERROR(player.getSession()));
    }
}
