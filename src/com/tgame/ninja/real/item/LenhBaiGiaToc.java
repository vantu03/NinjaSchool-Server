package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class LenhBaiGiaToc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        if (player.isControlCharNhanBan()) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.clan == null) {
            NJUtil.sendServer(player.getSession(), AlertFunction.NOT_CLAN(player.getSession()));
            return;
        }
        if (player.clan.use_card <= 0) {
            NJUtil.sendServer(player.getSession(), AlertFunction.MAX_CARD_CLAN(player.getSession()));
            return;
        }
        --player.clan.use_card;
        ++player.clan.openDun;
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        NJUtil.sendServer(player.getSession(), NJUtil.replace(AlertFunction.COUNT_OPENGT(player.getSession()), String.valueOf(player.clan.openDun)));
        player.savezbLog("su dung item 281", item.quantity + "@");
    }
}
