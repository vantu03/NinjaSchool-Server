package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class DiaDo implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        switch (item.getTemplateId()) {
            case 233: {
                if (player.map.getTemplateId() == 66) {
                    if (player.y == 360 && player.x >= 24 && player.x <= 72) {
                        player.createWait((byte) 10, item);
                        player.sendShowWait(AlertFunction.WAIT_GET_PEARL(player.getSession()));
                    } else {
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP1(player.getSession()));
                    }
                } else if (player.map.getTemplateId() == 35) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP2(player.getSession()));
                } else if (player.map.getTemplateId() == 67) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP3(player.getSession()));
                } else {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP4(player.getSession()));
                }
                break;
            }
            case 234: {
                if (player.map.getTemplateId() == 13) {
                    if (player.y == 456 && player.x >= 504 && player.x <= 552) {
                        player.createWait((byte) 10, item);
                        player.sendShowWait(AlertFunction.WAIT_GET_PEARL(player.getSession()));
                    } else {
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP1(player.getSession()));
                    }
                } else if (player.map.getTemplateId() == 57) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP6(player.getSession()));
                } else if (player.map.getTemplateId() == 14) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP7(player.getSession()));
                } else {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP8(player.getSession()));
                }
                break;
            }
            case 235: {
                if (player.map.getTemplateId() == 52) {
                    if (player.y == 432 && player.x >= 1224 && player.x <= 1296) {
                        player.createWait((byte) 10, item);
                        player.sendShowWait(AlertFunction.WAIT_GET_PEARL(player.getSession()));
                    } else {
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP1(player.getSession()));
                    }
                } else if (player.map.getTemplateId() == 51) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP9(player.getSession()));
                } else if (player.map.getTemplateId() == 64) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP10(player.getSession()));
                } else {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.ITEM_MAP(player.getSession()), AlertFunction.HELP_MAP11(player.getSession()));
                }
                break;
            }
        }
    }
}
