package com.tgame.ninja.real.item;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Effect;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;

public class DanDuoc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 275: {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(24);
                eff.param = 500;
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 600000;
                player.addEffect(eff);
                break;
            }
            case 276: {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                Effect eff = new Effect();
                eff.param = 500;
                eff.template = ServerController.effTemplates.get(25);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 600000;
                player.addEffect(eff);
                break;
            }
            case 277: {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                Effect eff = new Effect();
                eff.param = 100;
                eff.template = ServerController.effTemplates.get(26);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 600000;
                player.addEffect(eff);
                break;
            }
            case 278: {
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                Effect eff = new Effect();
                eff.param = 1000;
                eff.template = ServerController.effTemplates.get(29);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 600000;
                player.addEffect(eff);
                playerMain.addMaxHp = eff.param;
                playerMain.hp += playerMain.addMaxHp;
                playerMain.hp_full += playerMain.addMaxHp;
                try {
                    Message message = NJUtil.messageSubCommand(Cmd.REFRESH_HP);
                    message.writeInt(player.playerId);
                    message.writeInt(playerMain.hp);
                    message.writeInt(playerMain.hp_full);
                    player.sendToPlayer(message, true);
                } catch (Exception e) {
                }
                break;
            }
        }
    }
}
