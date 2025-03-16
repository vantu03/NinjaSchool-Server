package com.tgame.ninja.real.item;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.io.IOException;

public class HoanLuongChiThao implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (player.map.isLangCo()) {
            return;
        }
        if (playerMain.pk <= 0) {
            NJUtil.sendServer(player.getSession(), AlertFunction.NOT_PK(player.getSession()));
            return;
        }
        player.useItemUpToUp(item);
        player.sendUseItemUpToUp(item.indexUI, 1);
        playerMain.pk -= 5;
        if (playerMain.pk < 0) {
            playerMain.pk = 0;
        }
        try {
            Message message = NJUtil.messageNotMap(Cmd.UPDATE_PK);
            message.writeByte(playerMain.pk);
            NJUtil.sendMessage(player.getSession(), message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
