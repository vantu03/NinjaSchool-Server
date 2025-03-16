package com.tgame.ninja.real.item;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class DanhVongPhu implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            return;
        }
        if (player.useDanhVongPhu <= 0) {
            if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                NJUtil.sendServer(player.getSession(), "Mỗi ngày chỉ có thể sử dụng vật phẩm này 6 lần");
            } else {
                NJUtil.sendServer(player.getSession(), "You have used all 6 extends today");
            }
            return;
        }
        player.totalNvNguyetNhan += 5;
        --player.useDanhVongPhu;
        if (player.getSession().typeLanguage == GameServer.LANG_VI) {
            NJUtil.sendServer(player.getSession(), String.format("Con có thể nhận được thêm %d nhiệm vụ nữa", player.totalNvNguyetNhan));
        } else {
            NJUtil.sendServer(player.getSession(), String.format("Now you can receive %d more tasks", player.totalNvNguyetNhan));
        }
        player.useItemUpToUp(item, 1);
        player.sendUseItemUpToUp(item.indexUI, 1);
    }
}
