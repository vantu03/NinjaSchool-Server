package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class TruongLangSanzu implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (!player.isNpcOk(playerTemplate.playerTemplateId)) {
            return;
        }
        if (player.taskMain == null && player.getTaskFinish() >= 33 && player.getTaskFinish() <= 42) {
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() >= 33 && player.getTaskFinish() <= 42) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
            try {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            } catch (Exception e) {
            }
        } else if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0) {
            if (!GameServer.openNinjaDeNhat) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (player.countLoiDaiClass >= 50) {
                NJUtil.sendServer(player.getSession(), AlertFunction.MAXLOIDAI(player.getSession()));
                return;
            }
            player.goGiaiDauClass();
        } else if (playerTemplate.menuId == 2 && playerTemplate.optionId == 0) {
            if (!GameServer.openNinjaDeNhat) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            player.topTaiNangClass(playerTemplate.optionId);
        }
    }
}
