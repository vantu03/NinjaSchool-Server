package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class Potion implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.pk >= 14) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PK_MAX_1(player.getSession()));
            return;
        }
        if (item.isTypeHP()) {
            if (playerMain.hp >= playerMain.hp_full) {
                NJUtil.sendServer(player.getSession(), AlertFunction.HP_FULL(player.getSession()));
                return;
            }
            if (player.map.isArena() && player.countHpUseArena >= 10) {
                return;
            }
            if (player.map.isArena() && player.countHpUseArena < 10) {
                ++player.countHpUseArena;
            }
        }
        if (item.isTypeMP()) {
            if (playerMain.mp >= playerMain.mp_full) {
                NJUtil.sendServer(player.getSession(), AlertFunction.MP_FULL(player.getSession()));
                return;
            }
        }
        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
        switch (item.getTemplateId()) {
            case 13:
                player.updateHp(25);
                break;
            case 14:
                player.updateHp(90);
                break;
            case 15:
                player.updateHp(230);
                break;
            case 16:
                player.updateHp(400);
                break;
            case 17:
                player.updateHp(650);
                break;
            case 565:
                player.updateHp(1500);
                break;
            case 18:
                player.updateMp(150);
                break;
            case 19:
                player.updateMp(500);
                break;
            case 20:
                player.updateMp(1000);
                break;
            case 21:
                player.updateMp(2000);
                break;
            case 22:
                player.updateMp(3500);
                break;
            case 566:
                player.updateMp(5000);
                break;
        }
    }
}
