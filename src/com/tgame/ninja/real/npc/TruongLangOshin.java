package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TruongLangOshin implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.taskMain == null && (player.getTaskFinish() == 21 || player.getTaskFinish() == 22 || player.getTaskFinish() == 23 || player.getTaskFinish() == 24)) {
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && (player.getTaskFinish() == 21 || player.getTaskFinish() == 22 || player.getTaskFinish() == 23 || player.getTaskFinish() == 24)) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && (player.taskMain.index == 0 || player.taskMain.index == 1) && player.getTaskFinish() == 23) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                int index = -1;
                for (int i4 = 0; i4 < player.itemBags.length; ++i4) {
                    if (index == -1 && player.itemBags[i4] == null) {
                        index = i4;
                    }
                    if (player.itemBags[i4] != null && player.itemBags[i4].getTemplateId() == 231) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_KEY(player.getSession()));
                        return;
                    }
                }
                if (index == -1) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                    return;
                }
                player.addItemToBagNoDialog(new Item(231, index, true, "char 3"));
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            }
        }
    }
}
