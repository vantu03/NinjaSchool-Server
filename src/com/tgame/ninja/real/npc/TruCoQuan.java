package com.tgame.ninja.real.npc;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.DunClan;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class TruCoQuan implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
            if (player.map.getTemplateId() == 80 && player.map.players.size() < 6) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_DUN_CLAN9(player.getSession()));
                return;
            }
            if (player.map.getTemplateId() < 80 || player.map.getTemplateId() > 90) {
                return;
            }
            DunClan dunClan = (DunClan) player.map;
            if (dunClan.isUnLock) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_DUN_CLAN8(player.getSession()));
                return;
            }
            Item itKey2 = player.findItemBag(260);
            if (itKey2 == null) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_DUN_CLAN7(player.getSession()));
                return;
            }
            player.removeItem(itKey2, 3);
            player.sendClearItemBag(itKey2.indexUI);
            ++player.clan.stepDoor;
            dunClan.isUnLock = true;
            int levelDun = 0;
            for (int m = 0; m < player.map.players.size(); ++m) {
                levelDun += player.map.players.get(m).level;
            }
            levelDun = levelDun / player.map.players.size() + 5;
            String[] strs = {
                player.name + player.name + Alert_VN.ALERT_DUN_CLANS[player.map.getTemplateId() - 80],
                player.name + player.name + Alert_EN.ALERT_DUN_CLANS[player.map.getTemplateId() - 80]
            };
            player.map.sendAlert(strs);
            if (player.map.getTemplateId() == 87 || player.map.getTemplateId() == 88 || player.map.getTemplateId() == 89) {
                NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.NOTE(player.getSession()), AlertFunction.ALERT_DUN_CLAN1(player.getSession()));
                return;
            }
            if (player.map.getTemplateId() == 80) {
                int time3 = 5400000;
                player.clan.createDun(levelDun, time3);
                try {
                    Message message = NJUtil.messageSubCommand(Cmd.CLAN_CHANGE_ALERT);
                    message.writeInt(time3 / 1000);
                    player.clan.dunClan.sendToPlayer(message);
                } catch (Exception e) {
                }
                dunClan.names = new Vector<>();
                for (int i2 = 0; i2 < dunClan.players.size(); ++i2) {
                    dunClan.names.add(dunClan.players.get(i2).name);
                }
            }
        }
    }
}
