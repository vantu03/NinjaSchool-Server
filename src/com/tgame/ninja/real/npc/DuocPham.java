package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class DuocPham implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        try {
            Player playerMain = player.getPlayerMainControl();
            if (player.taskMain == null && player.getTaskFinish() == 5) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0) {
                    player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                    return;
                }
                --playerTemplate.menuId;
            } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 5) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                    player.doTaskFinish(playerTemplate.playerTemplateId);
                    return;
                }
                --playerTemplate.menuId;
            }
            if (playerTemplate.menuId == 0) {
                if (playerTemplate.optionId == 0) {
                    player.sendOpenUI(Item.UI_STACK_LOCK);
                }
            } else if (playerTemplate.menuId == 1) {
                if (playerTemplate.optionId == 0) {
                    player.sendOpenUI(Item.UI_STACK);
                }
            } else if (playerTemplate.menuId == 2 && playerTemplate.optionId == 0) {
                if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 0) {
                    player.doTaskNext();
                    player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
        } catch (Exception e) {
        }
    }
}
