package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class ExpMount implements IUseHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpMount.class);

    @Override
    public void useItem(Player player, Item item) {
        switch (item.getTemplateId()) {
            case 449:
            case 470: {
                if (doTangKinhNghiem(player, 5, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 450: {
                if (doTangKinhNghiem(player, 7, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 451:
            case 471: {
                if (doTangKinhNghiem(player, 14, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 452: {
                if (doTangKinhNghiem(player, 20, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 453:
            case 472: {
                if (doTangKinhNghiem(player, 25, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 573:
            case 574:
            case 575: {
                int[] expsoitang = { 200, 400, 600 };
                if (doTangKinhNghiem(player, expsoitang[item.getTemplateId() - 573], item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 576: {
                if (doTangKinhNghiem(player, 100, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 577: {
                if (doTangKinhNghiem(player, 250, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 578: {
                if (doTangKinhNghiem(player, 500, item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
            case 778: {
                if (doTangKinhNghiem(player, GameServer.isServerLocal() ? 1000 : (NJUtil.random.nextInt(10) + 1), item)) {
                    player.useItemUpToUp(item);
                    player.sendUseItemUpToUp(item.indexUI, 1);
                }
                break;
            }
        }
    }

    public boolean doTangKinhNghiem(Player player, int expAdd, Item itemUse) {
        try {
            Player playerMain = player.getPlayerMainControl();
            if (playerMain.itemMons[4] == null) {
                NJUtil.sendServer(player.getSession(), "Vật phẩm này chỉ có thể sử dụng khi có thú cưỡi.");
                return false;
            }
            if (playerMain.itemMons[4].upgrade >= 99) {
                playerMain.itemMons[4].options.get(0).param = 0;
                playerMain.itemMons[4].upgrade = 99;
                NJUtil.sendServer(player.getSession(), "Thú cưỡi đã đạt cấp tối đa.");
                return false;
            }
            if (playerMain.itemMons[4].isHuyetSacHungLang()) {
                return false;
            }
            if (playerMain.itemMons[4].isXichNhanNganLang() && !itemUse.isThucAnSoi()) {
                NJUtil.sendServer(player.getSession(), "Không thể sử dụng vật phẩm này cho sói.");
                return false;
            }
            if (playerMain.itemMons[4].isXe() && !itemUse.isNhienLieuXe()) {
                NJUtil.sendServer(player.getSession(), "Không thể sử dụng vật phẩm này cho xe máy.");
                return false;
            }
            if (playerMain.itemMons[4].isTrau() && !itemUse.isThucAnTrau()) {
                NJUtil.sendServer(player.getSession(), "Không thể sử dụng vật phẩm này cho trâu.");
                return false;
            }
            playerMain.itemMons[4].options.get(0).param += expAdd;
            if (playerMain.itemMons[4].options.get(0).param >= 1000) {
                player.doUpgradeMon(playerMain.itemMons[4]);
            }
            player.loadThuCuoi(1);
            return true;
        } catch (Exception e) {
            LOGGER.error(player.getStringBaseInfo(), e);
            return false;
        }
    }
}
