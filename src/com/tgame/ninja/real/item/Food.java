package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class Food implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.pk >= 14) {
            NJUtil.sendServer(player.getSession(), AlertFunction.PK_MAX_1(player.getSession()));
            return;
        }
        switch (item.getTemplateId()) {
            case 23: {
                if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 1) {
                    player.doTaskNext();
                    player.sendOpenUISay(4, playerMain.taskMain.template.des[player.getSession().typeLanguage][playerMain.taskMain.index]);
                }
                if (playerMain.isUseFood(0)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(0, 1800000);
                break;
            }
            case 24: {
                if (playerMain.isUseFood(1)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(1, 1800000);
                break;
            }
            case 25: {
                if (playerMain.isUseFood(2)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(2, 1800000);
                break;
            }
            case 26: {
                if (playerMain.isUseFood(3)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(3, 1800000);
                break;
            }
            case 27: {
                if (playerMain.isUseFood(4)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(4, 1800000);
                break;
            }
            case 29: {
                if (playerMain.isUseFood(5)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(5, 1800000);
                break;
            }
            case 30: {
                if (playerMain.isUseFood(5)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(5, 259200000);
                break;
            }
            case 249: {
                if (playerMain.isUseFood(3)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(3, 259200000);
                break;
            }
            case 250: {
                if (playerMain.isUseFood(4)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(4, 259200000);
                break;
            }
            case 409: {
                if (playerMain.isUseFood(30)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(6, 86400000);
                break;
            }
            case 410: {
                if (player.isUseFood(31)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(7, 86400000);
                break;
            }
            case 567: {
                if (player.isUseFood(35)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_EAT(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                player.useFood(8, 86400000);
                break;
            }
        }
    }
}
