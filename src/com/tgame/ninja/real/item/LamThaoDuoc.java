package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Effect;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;

public class LamThaoDuoc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (!player.map.isDunClan) {
            NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_DUN_CLAN12(player.getSession()));
            return;
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.ALERT_DUN_CLAN2(player.getSession()));
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        Effect eff = new Effect();
        eff.template = ServerController.effTemplates.get(23);
        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
        eff.timeLength = 300000;
        player.addEffect(eff);
    }
}
