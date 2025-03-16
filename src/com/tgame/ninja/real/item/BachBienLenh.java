package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;

public class BachBienLenh implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (item.getTemplateId() == 34) {
            if (player.getHp() <= 0) {
                return;
            }
            if (ServerController.huPlayers.get(player.getSession().userId).doChangeMap(player.mapTemplateId_focus, false, "doitemuse " + item.getTemplateId())) {
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
            }
        }
        if (item.getTemplateId() == 36) {
            if (player.getHp() <= 0) {
                return;
            }
            String al = player.getAlertTime(player.timeUseItemChangeMap);
            if (al != null) {
                NJUtil.sendDialog(player.getSession(), al);
                return;
            }
            player.timeUseItemChangeMap = System.currentTimeMillis() + Player.delayItemChangeMap;
            ServerController.huPlayers.get(player.getSession().userId).doChangeMap(player.mapTemplateId_focus, false, "doitemuse " + item.getTemplateId());
        }
    }
}
