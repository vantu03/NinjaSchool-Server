package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;

public class Shinwa implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (playerTemplate.menuId == 0) {
            player.sendLoadItemSale(playerTemplate.optionId);
        } else {
            if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_AUCTION_SALE);
            }
        }
    }
}
