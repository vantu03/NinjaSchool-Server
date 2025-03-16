package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;

public class Goosho implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban() && playerTemplate.menuId > 1) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
            player.sendOpenUI(Item.UI_STORE);
        } else if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0) {
            player.sendOpenUI(Item.UI_BOOK);
        } else if (playerTemplate.menuId == 2 && playerTemplate.optionId == 0) {
            player.sendOpenUI(Item.UI_FASHION);
        } else if (playerTemplate.menuId == 3 && playerTemplate.optionId == 0) {
            player.sendOpenUI(Item.UI_CLANSHOP);
        }
    }
}
