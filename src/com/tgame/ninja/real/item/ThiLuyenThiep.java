package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class ThiLuyenThiep implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (!GameServer.openVDMQ) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
            return;
        }
        if (playerMain.level < 60) {
            NJUtil.sendServer(player.getSession(), AlertFunction.NOT_LEVEL60(player.getSession()));
            return;
        }
        if (player.getSession().isVersion108() && player.useThiLuyenThiep(7200000)) {
            NJUtil.sendServer(player.getSession(), "Thời gian phiêu lưu ở Vùng Đất Ma Quỷ của bạn đã được tăng thêm 2 giờ.");
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
            return;
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.CAN_NOT_USE(player.getSession()));
    }
}
