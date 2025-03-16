package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class ChuyenTinhThach implements IUseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChuyenTinhThach.class);

    @Override
    public void useItem(Player player, Item item) {
        if (doNangCapSao(player)) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
        }
    }

    public boolean doNangCapSao(Player player) {
        try {
            Player playerMain = player.getPlayerMainControl();
            if (playerMain.itemMons[4] == null) {
                NJUtil.sendServer(player.getSession(), "Vật phẩm này chỉ có thể sử dụng khi có thú cưỡi.");
                return false;
            }
            if (playerMain.itemMons[4].getTemplateId() == 523) {
                return false;
            }
            if (playerMain.itemMons[4].sys >= 4) {
                NJUtil.sendServer(player.getSession(), "Thú cưỡi đã đạt cấp sao tối đa.");
                return false;
            }
            if (playerMain.itemMons[4].upgrade < 99) {
                NJUtil.sendServer(player.getSession(), "Chỉ có thể sử dụng vật phẩm này khi thú cưỡi đã đạt cấp độ tối đa.");
                return false;
            }
            if (NJUtil.probability(90, 10) == 0) {
                NJUtil.sendServer(player.getSession(), "Nâng cấp sao thú cưỡi thất bại, bạn bị mất 1 chuyển tinh thạch.");
                return true;
            }
            NJUtil.sendServer(player.getSession(), "Thú cưỡi đã được tăng sao.");
            ++playerMain.itemMons[4].sys;
            playerMain.itemMons[4].upgrade = 0;
            int[] per = { 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22 };
            for (int i = 0; i < playerMain.itemMons[4].options.size(); ++i) {
                switch (playerMain.itemMons[4].options.get(i).optionTemplate.itemOptionTemplateId) {
                    case 6:
                    case 7:
                    case 74:
                        playerMain.itemMons[4].options.get(i).param = 50 * per[playerMain.itemMons[4].sys] + 50;
                        break;
                    case 10:
                    case 68:
                    case 69:
                        playerMain.itemMons[4].options.get(i).param = 10 * per[playerMain.itemMons[4].sys] + 10;
                        break;
                    case 70:
                    case 67:
                    case 72:
                    case 71:
                        playerMain.itemMons[4].options.get(i).param = 5 * per[playerMain.itemMons[4].sys] + 5;
                        break;
                    case 73:
                        playerMain.itemMons[4].options.get(i).param = 100 * per[playerMain.itemMons[4].sys] + 100;
                        break;
                }
            }
            player.loadThuCuoi(1);
            return true;
        } catch (Exception e) {
            LOGGER.error(player.getStringBaseInfo(), e);
        }
        return false;
    }
}
