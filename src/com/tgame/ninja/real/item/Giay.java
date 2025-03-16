package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class Giay implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        switch (item.getTemplateId()) {
            case 549: {
                if (NJUtil.random.nextInt(100) < 90) {
                    int yen2 = 5000 + NJUtil.random.nextInt(15001);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                } else {
                    int yen2 = 100000 + NJUtil.random.nextInt(900001);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
            case 550: {
                if (NJUtil.random.nextInt(100) < 60) {
                    int yen2 = 10000 + NJUtil.random.nextInt(20001);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                } else {
                    int yen2 = 50000 + NJUtil.random.nextInt(450001);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
            case 551: {
                if (NJUtil.random.nextInt(100) < 70) {
                    int yen2 = 50000 + NJUtil.random.nextInt(95000);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                } else {
                    int yen2 = 1000 + NJUtil.random.nextInt(49001);
                    player.sendUpdateCoinLockYen(yen2);
                    NJUtil.sendServer(player.getSession(), "Chúc mừng bạn nhận được " + yen2 + " yên");
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
        }
    }
}
