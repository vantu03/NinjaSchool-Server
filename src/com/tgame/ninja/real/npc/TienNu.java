package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TienNu implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        String[] getSay = playerTemplate.getSay(player.getSession());
        player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
    }
}
