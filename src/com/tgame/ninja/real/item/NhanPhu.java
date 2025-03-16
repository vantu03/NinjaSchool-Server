package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class NhanPhu implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.level < 60) {
            NJUtil.sendServer(player.getSession(), AlertFunction.NOT_LEVEL60(player.getSession()));
            return;
        }
        int[] timekt = { 7200000, 18000000 };
        int[] typekt = { 1, 0 };
        if (Player.useThienNhanPhu(player, timekt[item.getTemplateId() - 537], typekt[item.getTemplateId() - 537])) {
            player.useItemUpToUp(item);
            player.sendUseItemUpToUp(item.indexUI, 1);
            return;
        }
        NJUtil.sendServer(player.getSession(), AlertFunction.CAN_NOT_USE(player.getSession()));
    }
}
