package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class NhiemVuBiAn implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (!GameServer.openNvBiAn) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
            return;
        }
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (isNvbian(player)) {
            player.callEffectMe(21);
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
        }
    }

    private boolean isNvbian(Player player) {
        if (player.level > 90) {
            return false;
        }
        if (player.countNvbian > 0) {
            --player.countNvbian;
            player.typeNvbian = 1 + NJUtil.randomNumber(8);
            player.sendBian();
            return true;
        }
        NJUtil.sendDialog(player.getSession(), "Mỗi ngày chỉ sử dụng tối đa 3 lần vật phẩm này.");
        return false;
    }
}
