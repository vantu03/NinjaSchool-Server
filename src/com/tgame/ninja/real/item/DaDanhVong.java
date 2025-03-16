package com.tgame.ninja.real.item;

import com.tgame.ninja.branch.tasks.NguyetNhanTask;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class DaDanhVong implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case Item.DANH_VONG_CAP_1:
            case Item.DANH_VONG_CAP_2:
            case Item.DANH_VONG_CAP_3:
            case Item.DANH_VONG_CAP_4:
            case Item.DANH_VONG_CAP_5:
            case Item.DANH_VONG_CAP_6:
            case Item.DANH_VONG_CAP_7:
            case Item.DANH_VONG_CAP_8:
            case Item.DANH_VONG_CAP_9: {
                if (playerMain.isNhanban()) {
                    return;
                }
                ItemTemplate temp = item.template;
                if (temp.idTemplateUp <= -1 || item.quantity < 10) {
                    break;
                }
                int soluong = item.quantity / 10;
                if (soluong > 100) {
                    soluong = 100;
                }
                if (player.countFreeBag() < 1) {
                    if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(player.getSession(), "Con phải có 1 ô trống trong hành trang");
                    } else {
                        NJUtil.sendServer(player.getSession(), "You must have 1 free slot in your bag");
                    }
                    return;
                }
                int yen = player.getYen();
                int xu = player.getXu();
                int money = soluong * NguyetNhanTask.MONEY_UPDGRADE_DA_DANH_VONG[item.template.idTemplateUp - 695];
                if (yen < money && xu < money) {
                    if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(player.getSession(), "Không đủ yên hoặc xu.");
                    } else {
                        NJUtil.sendServer(player.getSession(), "Not enought money.");
                    }
                    return;
                }
                if (yen >= money) {
                    player.subYen(money);
                } else {
                    player.subXu(money);
                }
                player.updateInfo();
                Item it8 = new Item(temp.idTemplateUp, true, "douseitem");
                it8.quantity = soluong;
                player.addItemToBagNoDialog(it8);
                player.useItemUpToUp(item, soluong * 10);
                player.sendUseItemUpToUp(item.indexUI, soluong * 10);
                player.savezaLog("nang cap da dv", "nang cap da danh vong " + temp.name + " nhan dc " + soluong + " " + it8.template.name);
                break;
            }
        }
    }
}
