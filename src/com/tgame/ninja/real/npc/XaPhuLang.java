package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class XaPhuLang implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.taskMain == null && player.getTaskFinish() == 6) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 6) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 5) {
                    player.doTaskNext();
                    player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(10, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(17, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 3) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(22, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 4) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(32, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 5) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(38, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 6) {
            if (playerTemplate.optionId == 0) {
                player.doChangeMap(43, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
            }
        } else if (playerTemplate.menuId == 7 && playerTemplate.optionId == 0) {
            player.doChangeMap(48, false, "doMenu xa_phu_lang " + playerTemplate.menuId);
        }
    }
}
