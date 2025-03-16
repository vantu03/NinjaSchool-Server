package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TheBaiTT implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 289: {
                ++playerMain.pointTT;
                playerMain.useItemUpToUp(item);
                playerMain.sendUseItemUpToUp(item.indexUI, 1);
                NJUtil.sendServer(playerMain.getSession(), NJUtil.replace(AlertFunction.VUOT_AI_ALERT12(playerMain.getSession()), "1"));
                break;
            }
            case 290: {
                playerMain.pointTT += 3;
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.VUOT_AI_ALERT12(player.getSession()), "3"));
                break;
            }
            case 291: {
                playerMain.pointTT += 9;
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.VUOT_AI_ALERT12(player.getSession()), "9"));
                break;
            }
        }
    }
}
