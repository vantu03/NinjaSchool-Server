package com.tgame.ninja.real.item;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.io.IOException;

public class TuiVai implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 215: {
                if (playerMain.bag == 30) {
                    playerMain.savezbLog("Su dung item", String.valueOf(item.getTemplateId()));
                    playerMain.bag = 36;
                    Item[] items = new Item[playerMain.bag];
                    System.arraycopy(playerMain.itemBags, 0, items, 0, playerMain.itemBags.length);
                    playerMain.itemBags = items;
                    playerMain.removeItem(item, 3);
                    try {
                        Message message = NJUtil.messageSubCommand(Cmd.UPDATE_BAG_COUNT);
                        message.writeByte(playerMain.bag);
                        message.writeByte(item.indexUI);
                        NJUtil.sendMessage(playerMain.getSession(), message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    NJUtil.sendServer(playerMain.getSession(), AlertFunction.BAG_EXTENDED(playerMain.getSession()));
                }
                break;
            }
            case 229: {
                if (playerMain.bag < 36) {
                    NJUtil.sendServer(playerMain.getSession(), NJUtil.replace(AlertFunction.ERROR_EXTENDED(playerMain.getSession()), "1"));
                    return;
                }
                if (playerMain.bag == 36) {
                    playerMain.savezbLog("Su dung item", String.valueOf(item.getTemplateId()));
                    playerMain.bag = 42;
                    Item[] items = new Item[playerMain.bag];
                    System.arraycopy(playerMain.itemBags, 0, items, 0, playerMain.itemBags.length);
                    playerMain.itemBags = items;
                    playerMain.removeItem(item, 3);
                    try {
                        Message message = NJUtil.messageSubCommand(Cmd.UPDATE_BAG_COUNT);
                        message.writeByte(playerMain.bag);
                        message.writeByte(item.indexUI);
                        NJUtil.sendMessage(playerMain.getSession(), message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    NJUtil.sendServer(playerMain.getSession(), AlertFunction.BAG_EXTENDED(playerMain.getSession()));
                }
                break;
            }
            case 283: {
                if (playerMain.bag < 42) {
                    NJUtil.sendServer(playerMain.getSession(), NJUtil.replace(AlertFunction.ERROR_EXTENDED(playerMain.getSession()), "1"));
                    return;
                }
                if (playerMain.bag == 42) {
                    playerMain.savezbLog("Su dung item", String.valueOf(item.getTemplateId()));
                    playerMain.bag = 54;
                    Item[] items = new Item[playerMain.bag];
                    System.arraycopy(playerMain.itemBags, 0, items, 0, playerMain.itemBags.length);
                    playerMain.itemBags = items;
                    playerMain.removeItem(item, 3);
                    try {
                        Message message = NJUtil.messageSubCommand(Cmd.UPDATE_BAG_COUNT);
                        message.writeByte(playerMain.bag);
                        message.writeByte(item.indexUI);
                        NJUtil.sendMessage(playerMain.getSession(), message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    NJUtil.sendServer(playerMain.getSession(), AlertFunction.BAG_EXTENDED(playerMain.getSession()));
                }
                break;
            }
        }
    }
}
