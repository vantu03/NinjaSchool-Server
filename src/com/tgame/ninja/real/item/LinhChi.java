package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class LinhChi implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 248: {
                if (playerMain.isUsingX3() != null || playerMain.isUsingX4() != null) {
                    NJUtil.sendServer(player.getSession(), "Không thể sử dụng lúc này");
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useExpx2(18000000);
                break;
            }
            case 539: {
                if (playerMain.isUsingX4() != null) {
                    NJUtil.sendServer(player.getSession(), "Không thể sử dụng lúc này");
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useExpx3(3600000);
                break;
            }
            case 540: {
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useExpx4(3600000);
                break;
            }
        }
    }
}
