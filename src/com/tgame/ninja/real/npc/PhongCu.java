package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class PhongCu implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.taskMain == null && player.getTaskFinish() == 15) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 15) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            if (player.isPlayerNam()) {
                if (playerTemplate.optionId == 0) {
                    player.sendOpenUI(Item.UI_NONNAM);
                } else if (playerTemplate.optionId == 1) {
                    player.sendOpenUI(Item.UI_AONAM);
                } else if (playerTemplate.optionId == 2) {
                    player.sendOpenUI(Item.UI_GANGTAYNAM);
                } else if (playerTemplate.optionId == 3) {
                    player.sendOpenUI(Item.UI_QUANNAM);
                } else if (playerTemplate.optionId == 4) {
                    player.sendOpenUI(Item.UI_GIAYNAM);
                }
            } else if (player.isPlayerNu()) {
                if (playerTemplate.optionId == 0) {
                    player.sendOpenUI(Item.UI_NONNU);
                } else if (playerTemplate.optionId == 1) {
                    player.sendOpenUI(Item.UI_AONU);
                } else if (playerTemplate.optionId == 2) {
                    player.sendOpenUI(Item.UI_GANGTAYNU);
                } else if (playerTemplate.optionId == 3) {
                    player.sendOpenUI(Item.UI_QUANNU);
                } else if (playerTemplate.optionId == 4) {
                    player.sendOpenUI(Item.UI_GIAYNU);
                }
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerTemplate.optionId == 0) {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            }
        } else if (playerTemplate.menuId == 2) {
            if (player.level < 20) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_LEVEL20(player.getSession()));
                return;
            }
            if (player.countFreeBag() <= 0) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                return;
            }
            if (playerTemplate.optionId == 0) {
                Item it2 = player.findItemBag(411);
                if (it2 != null) {
                    player.useItemUpToUp(it2);
                    player.sendUseItemUpToUp(it2.indexUI, 1);
                    it2 = new Item(417, false, "doMenu char 31");
                    it2.quantity = 1;
                    player.addItemToBagNoDialog(it2);
                    player.sendUpdateExp(3000000L, true);
                    return;
                }
            } else if (playerTemplate.optionId == 1) {
                Item it2 = player.findItemBag(412);
                if (it2 != null) {
                    player.useItemUpToUp(it2);
                    player.sendUseItemUpToUp(it2.indexUI, 1);
                    it2 = new Item(417, false, "doMenu char 32");
                    it2.quantity = 3;
                    player.addItemToBagNoDialog(it2);
                    player.sendUpdateExp(5000000L, true);
                    return;
                }
            } else if (playerTemplate.optionId == 2) {
                Item it2 = player.findItemBag(413);
                if (it2 != null) {
                    player.useItemUpToUp(it2);
                    player.sendUseItemUpToUp(it2.indexUI, 1);
                    it2 = new Item(417, false, "doMenu char 33");
                    it2.quantity = 5;
                    player.addItemToBagNoDialog(it2);
                    player.sendUpdateExp(7000000L, true);
                    return;
                }
            }
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_ITEM(player.getSession()));
        }
    }
}
