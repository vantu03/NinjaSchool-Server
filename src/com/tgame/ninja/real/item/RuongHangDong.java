package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.DropRate;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.util.ArrayList;

public class RuongHangDong implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.countFreeBag() < 1) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.BAG_FULL(player.getSession()));
            return;
        }
        Player playerMain = player.getPlayerMainControl();
        int rateCoinLock = 10000;
        int countUseRHD = Player.countUseRHD.getOrDefault(player.name, 0);
        ArrayList<DropRate> drops = new ArrayList<>();
        switch (item.getTemplateId()) {
            case 272: {
                rateCoinLock = 50000;
                // đá cường hoá
                drops.add(new DropRate(1, 3));
                drops.add(new DropRate(1, 4));
                drops.add(new DropRate(2, 5));
                drops.add(new DropRate(2, 6));
                // linh chi
                drops.add(new DropRate(4, 539));
                drops.add(new DropRate(5, 540));
                break;
            }
            case 282: {
                rateCoinLock = 75000;
                // đá cường hoá
                drops.add(new DropRate(1, 4));
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(2, 6));
                drops.add(new DropRate(3, 7));
                // linh chi
                if (countUseRHD < 100) {
                    drops.add(new DropRate(4, 539));
                    drops.add(new DropRate(5, 540));
                }
                break;
            }
            case 647: {
                rateCoinLock = 150000;
                // đá cường hoá
                drops.add(new DropRate(1, 4));
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(1, 6));
                drops.add(new DropRate(2, 7));
                drops.add(new DropRate(3, 8));
                // linh chi
                drops.add(new DropRate(4, 539));
                drops.add(new DropRate(5, 540));
                break;
            }
        }
        int typeLevel = Math.min(player.level / 10, 9);
        int coin = typeLevel * NJUtil.randomMinMax(rateCoinLock / 2, rateCoinLock);
        player.sendUpdateCoinLockYen(coin);
        NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + coin + " " + AlertFunction.YEN(player.getSession()));
        int itemId = NJUtil.dropItem(drops);
        if (itemId >= 0) {
            if (!player.addItemToBagNoDialog(new Item(itemId, false, "ruong hang dong " + item.getTemplateId()))) {
                NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(player.getSession()), "1"));
                return;
            }
        }
        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
        Player.countUseRHD.put(player.name, countUseRHD + 1);
        if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 40 && playerMain.taskMain.index == 1) {
            player.checkTaskIndex();
        }
    }
}
