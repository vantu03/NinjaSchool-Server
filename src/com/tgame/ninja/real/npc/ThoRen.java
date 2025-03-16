package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class ThoRen implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.taskMain != null && player.getTaskFinish() == 7 && player.taskMain.index > 10 && player.taskMain.index <= 15) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index + 1], player.taskMain.template.questions[player.getSession().typeLanguage][player.taskMain.index]);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_UPGRADE);
            } else if (playerTemplate.optionId == 1) {
                player.sendOpenUI(Item.UI_UPGRADE_GOLD);
            } else if (playerTemplate.optionId == 2) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HELP_UPGRADE(player.getSession()));
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_UPPEARL_LOCK);
            } else if (playerTemplate.optionId == 1) {
                player.sendOpenUI(Item.UI_UPPEARL);
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_SPLIT);
            }
        } else if (playerTemplate.menuId == 3) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_CONVERT);
            }
        } else if (playerTemplate.menuId == 4) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_LUYEN_NGOC);
            }
        } else if (playerTemplate.menuId == 5) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_KHAM_NGOC);
            }
        } else if (playerTemplate.menuId == 6) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_GOT_NGOC);
            }
        } else if (playerTemplate.menuId == 7) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_THAO_NGOC);
            }
        } else if (playerTemplate.menuId == 8 && playerTemplate.optionId == 0) {
            if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 2) {
                player.doTaskNext();
                player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
            } else {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            }
        }
    }
}
