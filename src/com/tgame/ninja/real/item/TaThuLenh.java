package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TaThuLenh implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()), "1"));
            return;
        }
        if (player.countUseTTL > 0) {
            --player.countUseTTL;
            player.countLoopBoos += 2;
            player.removeItem(item, 3);
            player.sendClearItemBag(item.indexUI);
            NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.COUNT_TT(player.getSession()), String.valueOf(player.countLoopBoos)));
            return;
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.MAX_TTL(player.getSession()));
    }
}
