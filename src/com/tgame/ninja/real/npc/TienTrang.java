package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.model.TaskTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;

public class TienTrang implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.map.getTemplateId() == 22) {
            if (playerTemplate.optionId == 0) {
                if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 3) {
                    player.doTaskNext();
                    player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
            return;
        }
        if (playerMain.isAcountAo()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.USER_XACTHUC(player.getSession()));
            return;
        }
        switch (playerTemplate.menuId) {
            case 0:
                int taskRequire = 32;
                if (player.taskFinish <= taskRequire) {
                    TaskTemplate task = ServerController.taskTemplates.get(taskRequire);
                    NJUtil.sendDialog(player.getSession(), "Phải hoàn thành " + task.name[player.getSession().typeLanguage] + " mới có thể đổi lượng sang xu.");
                    return;
                }
                if (player.getLuong() >= 50) {
                    if (player.isLock) {
                        player.doCancelTrade();
                        player.sendNotUnlock();
                        return;
                    }
                    player.doSendTextBoxId("Số lượng đổi (1 lượng = 10000 xu)", 2400);
                } else {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_GOLD(player.getSession()));
                }
                break;
            case 1:
                if (playerTemplate.optionId == 0) {
                    if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 3) {
                        player.doTaskNext();
                        player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
                    } else {
                        String[] getSay = playerTemplate.getSay(player.getSession());
                        player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                    }
                }
                break;
        }
    }
}
