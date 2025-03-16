package com.tgame.ninja.real.npc;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Dun;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class GiaoThu implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.taskMain != null && player.taskMain.template.taskId == 15 && (player.taskMain.index == 1 || player.taskMain.index == 2 || player.taskMain.index == 3)) {
            if (playerTemplate.menuId == 0) {
                int j = 0;
                while (j < player.itemBags.length) {
                    if (player.itemBags[j] != null && player.itemBags[j].getTemplateId() == 214) {
                        if (player.itemBags[j].quantity >= 2) {
                            --player.itemBags[j].quantity;
                            //Database.updateItem(player.itemBags[j]);
                            try {
                                Message message = new Message(Cmd.ITEM_BAG_REFRESH);
                                message.writeByte(j);
                                message.writeShort(player.itemBags[j].quantity);
                                NJUtil.sendMessage(player.getSession(), message);
                            } catch (Exception e) {
                            }
                            break;
                        }
                        player.removeItem(player.itemBags[j], 3);
                        player.sendClearItemBag(j);
                        break;
                    } else {
                        ++j;
                    }
                }
                player.doTaskNext();
                player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index]);
            }
        } else if (playerTemplate.playerTemplateId == 16) {
            if (player.taskMain == null && player.getTaskFinish() == 16) {
                if (playerTemplate.menuId == 0) {
                    player.confirmTask(playerTemplate.playerTemplateId, new String[]{
                        AlertFunction.GET(player.getSession()),
                        AlertFunction.NO(player.getSession())
                    });
                }
            } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 16 && playerTemplate.menuId == 0) {
                if (playerTemplate.optionId == 0) {
                    player.doTaskFinish(playerTemplate.playerTemplateId);
                }
            }
        } else if (playerTemplate.playerTemplateId == 15 && player.taskMain != null && player.taskMain.template.taskId == 20 && player.taskMain.index == 1) {
            Dun dun = Dun.createDun(74);
            if (dun == null) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_74_CLOSE(player.getSession()));
                return;
            }
            int time4 = 180;
            dun.playerOpen = player;
            dun.timeEnd = System.currentTimeMillis() + time4 * 1000;
            player.mapClear();
            player.map.goOutMap(player);
            player.x = 0;
            player.y = 0;
            player.sendMapTime(time4);
            dun.waitGoInMap(player);
        }
    }
}
