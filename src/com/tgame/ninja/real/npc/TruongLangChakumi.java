package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class TruongLangChakumi implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.taskMain == null && (player.getTaskFinish() == 28 || player.getTaskFinish() == 29 || player.getTaskFinish() == 30 || player.getTaskFinish() == 31 || player.getTaskFinish() == 32)) {
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && (player.getTaskFinish() == 28 || player.getTaskFinish() == 29 || player.getTaskFinish() == 30 || player.getTaskFinish() == 31 || player.getTaskFinish() == 32)) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && (player.taskMain.index == 0 || player.taskMain.index == 1) && player.getTaskFinish() == 32) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                int index = -1;
                for (int i = 0; i < player.itemBags.length; ++i) {
                    if (index == -1 && player.itemBags[i] == null) {
                        index = i;
                    }
                    if (player.itemBags[i] != null && player.itemBags[i].getTemplateId() == 266) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_ROD(player.getSession()));
                        return;
                    }
                }
                if (index == -1) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                    return;
                }
                player.addItemToBagNoDialog(new Item(266, index, true, "char 2"));
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
            String[] getSay = playerTemplate.getSay(player.getSession());
            player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
        }
    }
}
