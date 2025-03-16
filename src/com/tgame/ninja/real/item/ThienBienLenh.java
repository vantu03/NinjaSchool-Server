package com.tgame.ninja.real.item;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class ThienBienLenh implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (!GameServer.openThienBienLenh) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
            return;
        }
        player.idActionNewMenu = 0;
        try {
            Message message = new Message(Cmd.OPEN_UI_NEWMENU);
            message.writeUTF("Phạm vi 240");
            message.writeUTF("Phạm vi 360");
            message.writeUTF("Phạm vi toàn map");
            message.writeUTF("Nhặt tất cả");
            message.writeUTF("Nhặt v.phẩm hữu dụng");
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
        }
    }
}
