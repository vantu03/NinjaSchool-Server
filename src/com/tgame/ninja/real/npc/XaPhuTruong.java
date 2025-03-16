package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class XaPhuTruong implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(1, false, "doMenu xa_phu_truong");
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(27, false, "doMenu xa_phu_truong 1");
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(72, false, "doMenu xa_phu_truong 2");
            }
        } else if (playerTemplate.menuId == 3 && playerTemplate.optionId == 0) {
            String[] getSay = playerTemplate.getSay(player.getSession());
            player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
        }
    }
}
