package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;

public class ThatThuBao implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();

        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
        if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 40 && playerMain.taskMain.index == 2) {
            player.checkTaskIndex();
        }
    }
}
