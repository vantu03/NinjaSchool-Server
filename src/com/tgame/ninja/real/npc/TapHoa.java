package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.real.PlayerArenaInfo;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class TapHoa implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.getTaskFinish() == 1 && player.taskMain != null && player.taskMain.index >= 0 && player.taskMain.index < 5) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index + 1], player.taskMain.template.questions[player.getSession().typeLanguage][player.taskMain.index]);
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.getTaskFinish() == 7 && player.taskMain.index > 0 && player.taskMain.index <= 5) {
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
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_GROCERY_LOCK);
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_GROCERY);
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                if (player.taskMain != null && player.taskMain.template.taskId == 0 && player.taskMain.index == 1) {
                    player.doTaskNext();
                    player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
        } else if (playerTemplate.menuId == 3) {
            if (!GameServer.openThienDia) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.optionId == 0) {
                if (playerMain.level < 50) {
                    player.sendOpenUISay(4, AlertFunction.NOT_LEVEL50(player.getSession()));
                    return;
                }
                PlayerArenaInfo parena = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(player.name.toLowerCase());
                if (parena == null) {
                    parena = new PlayerArenaInfo();
                    parena.name = player.name.toLowerCase();
                    parena.userid = player.getSession().userId;
                    parena.level = player.level;
                    PlayerArenaInfo.addPlayer(parena);
                    player.sendOpenUISay(playerTemplate.playerTemplateId, "Đăng ký thành công");
                } else {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, "Bạn đã đăng ký thành công rồi");
                }
            } else if (playerTemplate.optionId == 1) {
                PlayerArenaInfo parena = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(player.name.toLowerCase());
                if (parena == null) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, "Bạn phải đăng ký mới có thể sử dụng chức năng này");
                } else {
                    PlayerArenaInfo.createMsgList(player);
                }
            } else if (playerTemplate.optionId == 2) {
                PlayerArenaInfo.doSendThienbang(player);
            } else if (playerTemplate.optionId == 3) {
                PlayerArenaInfo.doSendDiabang(player);
            }
        }
    }
}
