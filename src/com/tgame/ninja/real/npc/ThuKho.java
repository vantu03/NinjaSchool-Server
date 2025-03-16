package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class ThuKho implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.taskMain != null && player.getTaskFinish() == 7 && player.taskMain.index > 5 && player.taskMain.index <= 10) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index + 1], player.taskMain.template.questions[player.getSession().typeLanguage][player.taskMain.index]);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerMain.isNhanban() && playerTemplate.menuId > 0) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (playerTemplate.menuId == 0) {
            player.sendOpenUI(Item.UI_BOX);
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                if (Player.canFocusReturnMap(player.map.template.mapTemplateId)) {
                    player.mapTemplateId_focus = (short) player.map.template.mapTemplateId;
                } else {
                    player.mapTemplateId_focus = 22;
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.SAVE_POINT(player.getSession()));
            }
        } else if (playerTemplate.menuId == 2) {
            if (!GameServer.openVDMQ) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (playerTemplate.optionId == 0) {
                if (!player.getSession().isVersion108()) {
                    return;
                }
                if (playerMain.level < 60) {
                    if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(player.getSession(), "Trình độ 60 mới có thể sử dụng chức năng này");
                    } else {
                        NJUtil.sendServer(player.getSession(), "Only level 60 can use");
                    }
                    return;
                }
                if (player.isUsingThiLuyen() != null) {
                    player.doChangeMap(139, true, "thu kho vdmq");
                    return;
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, "Sử dụng thí luyện thiếp rồi đến gặp ta.");
            } else {
                NJUtil.sendAlertDialogInfo(player.getSession(), GameServer.isSvEnglish() ? "Cursed Land" : "Vùng  Đất Ma Quỷ", AlertFunction.HELP_THI_LUYEN(player.getSession()));
            }
        } else if (playerTemplate.menuId == 3) {
            if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 4) {
                player.doTaskNext();
                player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
            } else {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            }
        }
    }
}
