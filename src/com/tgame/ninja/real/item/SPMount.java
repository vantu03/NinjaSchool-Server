package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class SPMount implements IUseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SPMount.class);

    @Override
    public void useItem(Player player, Item item) {
        if (doThucAnThuCuoi(player, item.getTemplateId())) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
        }
    }

    private boolean doThucAnThuCuoi(Player player, int ituseid) {
        try {
            Player playerMain = player.getPlayerMainControl();
            if (playerMain.itemMons[4] == null) {
                NJUtil.sendServer(player.getSession(), "Vật phẩm này chỉ có thể sử dụng khi có thú cưỡi.");
                return false;
            }
            if (playerMain.itemMons[4].getTemplateId() == 523) {
                return false;
            }
            if (playerMain.itemMons[4].getTemplateId() == 776 && ituseid != 779) {
                NJUtil.sendServer(player.getSession(), "Không thể sử dụng vật phẩm này cho Hắc ngưu hoặc Kim Ngưu.");
                return false;
            }
            if (playerMain.itemMons[4].getTemplateId() != 776 && ituseid == 779) {
                NJUtil.sendServer(player.getSession(), "Vật phẩm này chỉ sử dụng cho Hắc ngưu hoặc Kim Ngưu.");
                return false;
            }
            if (
                (playerMain.itemMons[4].getTemplateId() != 443 || ituseid != 444) &&
                    ((playerMain.itemMons[4].getTemplateId() != 485 && playerMain.itemMons[4].getTemplateId() != 524) || ituseid != 469) &&
                    (playerMain.itemMons[4].getTemplateId() != 776 || ituseid != 779)
            ) {
                NJUtil.sendServer(player.getSession(), "Vật phẩm này chỉ có thể sử dụng khi có thú cưỡi.");
                return false;
            }
            if (playerMain.itemMons[4].options.get(1).param >= 1000) {
                NJUtil.sendServer(player.getSession(), "HP thú cưỡi đã đầy.");
                return false;
            }
            ItemOption itemOption = playerMain.itemMons[4].options.get(1);
            itemOption.param += (ituseid == 779) ? 100 : 200;
            if (playerMain.itemMons[4].options.get(1).param > 1000) {
                playerMain.itemMons[4].options.get(1).param = 1000;
            }
            player.loadThuCuoi(1);
            return true;
        } catch (Exception e) {
            LOGGER.error(player.getStringBaseInfo(), e);
            return false;
        }
    }
}
