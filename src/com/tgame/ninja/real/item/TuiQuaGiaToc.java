package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TuiQuaGiaToc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        int[] idIts = { 4, 5, 6, 7, 269 };
        int[] pers = { 2000, 1500, 150, 10, 1 };
        Item itTuiGT = new Item(idIts[NJUtil.probability(pers)], true, "doItemUse char 8");
        if (itTuiGT.getTemplateId() == 269) {
            itTuiGT.isLock = false;
        }
        if (player.addItemToBagNoDialog(itTuiGT)) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
            return;
        }
        NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(player.getSession()), "1"));
    }
}
