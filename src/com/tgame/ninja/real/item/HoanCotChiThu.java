package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class HoanCotChiThu implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 254: {
                if (playerMain.level < 1 || playerMain.level >= 30) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.LEVEL_ERROR_3(player.getSession()));
                    return;
                }
                if (playerMain.exp_down <= 0L) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.NOT_EXP_DOWN(player.getSession()));
                    return;
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                player.sendUpdateExp(playerMain.exp_down, false);
                break;
            }
            case 255: {
                if (playerMain.level < 30 || playerMain.level >= 60) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.LEVEL_ERROR_3(player.getSession()));
                    return;
                }
                if (playerMain.exp_down <= 0L) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.NOT_EXP_DOWN(player.getSession()));
                    return;
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                player.sendUpdateExp(playerMain.exp_down, false);
                break;
            }
            case 256: {
                if (playerMain.level < 60) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.LEVEL_ERROR_3(player.getSession()));
                    return;
                }
                if (playerMain.exp_down <= 0L) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.NOT_EXP_DOWN(player.getSession()));
                    return;
                }
                player.useItemUpToUp(item);
                player.sendUseItemUpToUp(item.indexUI, 1);
                player.sendUpdateExp(playerMain.exp_down, false);
                break;
            }
        }
    }
}
