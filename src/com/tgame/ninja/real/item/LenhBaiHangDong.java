package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class LenhBaiHangDong implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.countUseHDL > 0) {
            --player.countUseHDL;
            ++player.countPB;
            player.isAddPointUyDanh = true;
            player.removeItem(item, 3);
            player.sendClearItemBag(item.indexUI);
            NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.COUNT_HD(player.getSession()), String.valueOf(player.countPB)));
            player.savezbLog("su dung item 280", item.quantity + "@");
            return;
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.MAX_HDL(player.getSession()));
    }
}
