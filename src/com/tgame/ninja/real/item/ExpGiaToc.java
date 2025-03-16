package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class ExpGiaToc implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        switch (item.getTemplateId()) {
            case 436: {
                if (player.isControlCharNhanBan()) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (player.clan == null) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ITEM_CLAN(player.getSession()));
                    return;
                }
                player.addExpClan(100 + NJUtil.randomNumber(201));
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
            case 437: {
                if (player.isControlCharNhanBan()) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (player.clan == null) {
                    return;
                }
                player.addExpClan(300 + NJUtil.randomNumber(501));
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
            case 438: {
                if (player.isControlCharNhanBan()) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (player.clan == null) {
                    return;
                }
                player.addExpClan(1000 + NJUtil.randomNumber(1001));
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                break;
            }
        }
    }
}
