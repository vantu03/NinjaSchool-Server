package com.tgame.ninja.real.item;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.DropRate;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.util.ArrayList;

public class SummerKite implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (EventManager.getInstance().isSummerEvent()) {
            if (player.countFreeBag() <= 0) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.BAG_FULL(player.getSession()));
                return;
            }
            ArrayList<DropRate> drops = new ArrayList<>();
            if (item.getTemplateId() == 435) {
                // kinh nghiệm
                drops.add(new DropRate(4, -6));
                drops.add(new DropRate(2, -5));
                drops.add(new DropRate(1, -4));
                // đá cường hoá
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(1, 6));
                drops.add(new DropRate(2, 7));
                drops.add(new DropRate(3, 8));
                drops.add(new DropRate(4, 9));
                // bí kíp
                drops.add(new DropRate(3, 397));
                drops.add(new DropRate(3, 398));
                drops.add(new DropRate(3, 399));
                drops.add(new DropRate(3, 400));
                drops.add(new DropRate(3, 401));
                drops.add(new DropRate(3, 402));
                // đan dược
                drops.add(new DropRate(1, 275));
                drops.add(new DropRate(1, 276));
                drops.add(new DropRate(1, 277));
                drops.add(new DropRate(1, 278));
                // rương trang bị
                drops.add(new DropRate(4, 383));
                drops.add(new DropRate(5, 384));
                if (NJUtil.randomNumber(1000) > 950) {
                    // +16
                    drops.add(new DropRate(5, 385));
                    // yoroi
                    drops.add(new DropRate(5, 420));
                    drops.add(new DropRate(5, 421));
                    drops.add(new DropRate(5, 422));
                }
                // pet boru
                drops.add(new DropRate(3, 781));
            } else {
                // kinh nghiệm
                drops.add(new DropRate(4, -3));
                drops.add(new DropRate(2, -2));
                drops.add(new DropRate(1, -1));
                // đá cường hoá
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(2, 6));
                drops.add(new DropRate(3, 7));
                // bí kíp
                drops.add(new DropRate(3, 397));
                drops.add(new DropRate(3, 398));
                drops.add(new DropRate(3, 399));
                drops.add(new DropRate(3, 400));
                drops.add(new DropRate(3, 401));
                drops.add(new DropRate(3, 402));
                // đan dược
                drops.add(new DropRate(1, 275));
                drops.add(new DropRate(1, 276));
                drops.add(new DropRate(1, 277));
                drops.add(new DropRate(1, 278));
                // rương trang bị
                drops.add(new DropRate(4, 383));
                drops.add(new DropRate(5, 384));
                // pet boru
                drops.add(new DropRate(4, 781));
            }
            Item it;
            int itemId = NJUtil.dropItem(drops);
            int expAdd = 0;
            if (itemId == -6) {
                expAdd = 30000000;
            } else if (itemId == -5) {
                expAdd = 15000000;
            } else if (itemId == -4) {
                expAdd = 10000000;
            } else if (itemId == -3) {
                expAdd = 5000000;
            } else if (itemId == -2) {
                expAdd = 2000000;
            } else if (itemId == -1) {
                expAdd = 1000000;
            } else if (itemId >= 0) {
                it = new Item(itemId, false, "Summer kite " + item.getTemplateId());
                if (it.isTypeBiKip()) {
                    if (item.getTemplateId() == 434 || NJUtil.randomNumber(1000) > 10) {
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(Item.randomItem(new int[]{ 3, 3, 3, 7, 7, 7, 30 }));
                    }
                    it.createOptionBiKip();
                } else if (it.isPetBoru()) {
                    if (item.getTemplateId() == 434 || NJUtil.randomNumber(1000) > 10) {
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(Item.randomItem(new int[]{ 3, 3, 3, 7, 7, 7, 30 }));
                    }
                    it.createOptionBoru();
                } else if (it.isTypeYoroi()) {
                    it.createOptionYoroi();
                }
                if (!player.addItemToBagNoDialog(it)) {
                    NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(player.getSession()), "1"));
                    return;
                }
                if (it.getTemplateId() == 385 || it.isTypeYoroi()) {
                    NJUtil.sendServerAlert(player.name + " sử dụng " + item.template.name + " nhận được " + it.template.name + ".");
                }
            }
            if (expAdd > 0) {
                player.sendUpdateExp(expAdd, true);
            }
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
        } else {
            NJUtil.sendServer(player.getSession(), "Sự kiện đã kết thúc, không thể sử dụng vật phẩm này.");
        }
    }
}
