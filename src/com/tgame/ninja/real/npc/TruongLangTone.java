package com.tgame.ninja.real.npc;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class TruongLangTone implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.taskMain == null && (player.getTaskFinish() == 0 || player.getTaskFinish() == 1 || player.getTaskFinish() == 2 || player.getTaskFinish() == 3 || player.getTaskFinish() == 4 || player.getTaskFinish() == 7 || player.getTaskFinish() == 8)) {
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (!playerMain.isControlCharNhanBan() && !playerMain.isNhanban() && player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && (player.getTaskFinish() == 0 || player.getTaskFinish() == 1 || player.getTaskFinish() == 2 || player.getTaskFinish() == 3 || player.getTaskFinish() == 4 || player.getTaskFinish() == 7 || player.getTaskFinish() == 8)) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            try {
                if (player.getSession().clientType == GameServer.CLIENT_PC) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.TRUONG_LANG(player.getSession()), AlertFunction.HELP_3(player.getSession())[playerTemplate.optionId]);
                } else if (player.getSession().isQwerty) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.TRUONG_LANG(player.getSession()), AlertFunction.HELP_1(player.getSession())[playerTemplate.optionId]);
                } else if (player.getSession().isTouch || player.getSession().clientType == GameServer.CLIENT_IPHONE || player.getSession().clientType == GameServer.CLIENT_WINPHONE) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.TRUONG_LANG(player.getSession()), AlertFunction.HELP_2(player.getSession())[playerTemplate.optionId]);
                } else {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.TRUONG_LANG(player.getSession()), AlertFunction.HELP_0(player.getSession())[playerTemplate.optionId]);
                }
            } catch (Exception e) {
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                if (player.taskMain != null) {
                    String tname = player.taskMain.template.name[player.getSession().typeLanguage].replace(AlertFunction.TASK1(player.getSession()), "");
                    tname = tname.replace(AlertFunction.TASK(player.getSession()), "");
                    player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.HELP_TASK(player.getSession()), tname));
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerMain.isNhanban()) {
                return;
            }
            if (player.level >= 10) {
                player.clearTask();
                try {
                    Message message = NJUtil.messageNotMap(Cmd.CLEAR_TASK);
                    NJUtil.sendMessage(player.getSession(), message);
                } catch (Exception e) {
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.CLEAR_TASK(player.getSession()));
            } else {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LIMIT_LV_CLEAR(player.getSession()));
            }
        } else if (playerTemplate.menuId == 3 || playerTemplate.menuId == 4) {
            if (!GameServer.openPhanThan) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (player.map.getTemplateId() == 22) {
                player.switchMainCharAndCopyNPCTajma(playerTemplate.menuId);
            }
        }
    }
}
