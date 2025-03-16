package com.tgame.ninja.real;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemOption;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Vector;

public class AdminChat {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminChat.class);

    public static final String CMD_SHUT_DOWN = "shutdown";

    public static final String CMD_ADD_COIN = "addcoin";

    public static final String CMD_ADD_GOLD = "addgold";

    public static final String CMD_ADD_EXP_GT = "addexpclan";

    public static final String CMD_ADD_ITEM = "additem";

    public static final String CMD_ADMIN_CHEAT = "cheat";

    public static final String CMD_AUTO_DIE = "tusat";

    public static final String CMD_BAN_NAME = "ban";

    public static final String CMD_UNBAN_NAME = "unban";

    public static final String CMD_BOSS_WORLD = "bosstrum";

    public static final String CMD_CLEAR_BAG = "xbag";

    public static final String CMD_CLEAR_MOB = "xmob";

    public static final String CMD_DISCONNECT = "dis";

    public static final String CMD_MAP_SWITCH = "map";

    public static final String CMD_POINT_TT = "tinhtu";

    public static final String CMD_SET_LEVEL = "level";

    public static final String CMD_RELOAD_CONFIG = "reload";

    public static final String CMD_TEST_CT = "testct";

    public static boolean handleChat(Player player, String str) {
        if (player.isAdmin()) {
            String command = "";
            String param1 = "";
            String param2 = "";
            String[] words = str.split(" ");
            try {
                command = words[0];
            } catch (Exception e) {
            }
            try {
                param1 = words[1];
            } catch (Exception e) {
            }
            try {
                param2 = words[2];
            } catch (Exception e) {
            }
            try {
                if (player.getSession().type_admin >= Player.ROLE_GM) {
                    switch (command) {
                        case CMD_BAN_NAME: {
                            Player playerFind = ServerController.hnPlayers.get(param1);
                            if (playerFind != null) {
                                playerFind.ban = true;
                                Database.saveLogAll(playerFind.name, player.name + " ban", "banchar");
                                playerFind.getSession().disconnect("ban player");
                                LOGGER.info("Admin-{}: Ban player {}", player.name, playerFind.name);
                                NJUtil.sendServer(player.getSession(), "Đã ban " + playerFind.name);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_UNBAN_NAME: {
                            if (Database.removeBanPlayer(param1)) {
                                Database.saveLogAll(param1, player.name + " remove ban", "banchar");
                                LOGGER.info("Admin-{}: Remove ban player {}", player.name, param1);
                                NJUtil.sendServer(player.getSession(), "Đã gỡ lệnh ban " + param1);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_MAP_SWITCH: {
                            try {
                                int mapTemplateId = Integer.parseInt(param1);
                                player.doChangeMap(mapTemplateId, true, "admin chat map");
                                LOGGER.info("Admin-{}: Dich chuyen map", player.name);
                            } catch (Exception e) {
                                NJUtil.sendServer(player.getSession(), "Id map không hợp lệ");
                            }
                            return true;
                        }
                        case CMD_AUTO_DIE: {
                            Player playerFind = player;
                            if (!param1.isEmpty()) {
                                playerFind = ServerController.hnPlayers.get(param1);
                            }
                            if (playerFind != null) {
                                playerFind.autoDie("chat tu chet");
                                LOGGER.info("Admin-{}: Force die player {}", player.name, playerFind.name);
                                NJUtil.sendServer(player.getSession(), "Đã tự sát " + playerFind.name);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_DISCONNECT: {
                            Player playerFind = player;
                            if (!param1.isEmpty()) {
                                playerFind = ServerController.hnPlayers.get(param1);
                            }
                            if (playerFind != null) {
                                playerFind.getSession().disconnect("kick player");
                                LOGGER.info("Admin-{}: Kick player {}", player.name, playerFind.name);
                                NJUtil.sendServer(player.getSession(), "Đã kick " + playerFind.name);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                    }
                }
                if (player.getSession().type_admin >= Player.ROLE_ADMIN) {
                    switch (command) {
                        case CMD_SHUT_DOWN: {
                            if (param1.equals(GameServer.pwdCommand)) {
                                ServerController.turnOnExit = true;
                                NJUtil.sendServer(player.getSession(), "Chuẩn bị tắt server...");
                                return true;
                            }
                            return false;
                        }
                        case CMD_ADD_COIN: {
                            int coinAdd = 0;
                            try {
                                coinAdd = Integer.parseInt(param1);
                            } catch (Exception e) {
                            }
                            Player playerFind = player;
                            if (!param2.isEmpty()) {
                                playerFind = ServerController.hnPlayers.get(param2);
                            }
                            if (playerFind != null) {
                                playerFind.sendUpdateCoinBag(coinAdd);
                                LOGGER.info("Admin-{}: Add {} coin to player {}", player.name, coinAdd, playerFind.name);
                                NJUtil.sendServer(player.getSession(), "Đã cộng " + coinAdd + " xu cho nhân vật " + playerFind.name);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_ADD_GOLD: {
                            int goldAdd = 0;
                            try {
                                goldAdd = Integer.parseInt(param1);
                            } catch (Exception e) {
                            }
                            Player playerFind = player;
                            if (!param2.isEmpty()) {
                                playerFind = ServerController.hnPlayers.get(param2);
                            }
                            if (playerFind != null) {
                                playerFind.sendUpdateLuong(goldAdd);
                                LOGGER.info("Admin-{}: Add {} gold to player {}", player.name, goldAdd, playerFind.name);
                                NJUtil.sendServer(player.getSession(), "Đã cộng " + goldAdd + " lượng cho nhân vật " + playerFind.name);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_ADD_EXP_GT: {
                            if (player.clan != null) {
                                int expAdd = 0;
                                try {
                                    expAdd = Integer.parseInt(param1);
                                } catch (Exception e) {
                                }
                                if (expAdd > 0) {
                                    player.addExpClan(expAdd);
                                    LOGGER.info("Admin-{}: Add {} exp clan {}", player.name, player.clan.name, expAdd);
                                    NJUtil.sendServer(player.getSession(), "Đã tăng " + expAdd + "exp cho gia tộc " + player.clan.name);
                                }
                            }
                            return true;
                        }
                        case CMD_ADMIN_CHEAT: {
                            player.isAdminUseCheat = !player.isAdminUseCheat;
                            LOGGER.info("Admin-{}: Su dung cheat: " + player.isAdminUseCheat, player.name);
                            NJUtil.sendServer(player.getSession(), "Đã " + (player.isAdminUseCheat ? "bật" : "tắt") + " cheat");
                            return true;
                        }
                        case CMD_BOSS_WORLD: {
                            Map.thaBossTrum();
                            LOGGER.info("Admin-{}: Tha boss trum", player.name);
                            return true;
                        }
                        case CMD_ADD_ITEM: {
                            int itemId = -1;
                            int quantity = 1;
                            try {
                                itemId = Integer.parseInt(param1);
                            } catch (Exception e) {
                            }
                            try {
                                quantity = Integer.parseInt(param2);
                            } catch (Exception e) {
                            }
                            if (itemId >= 0) {
                                Item item = new Item(itemId, false, "doChatMap 1");
                                item.isLock = false;
                                if (item.getTemplateId() >= 397 && item.getTemplateId() <= 402) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.param = 2;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(59);
                                    item.options.add(option);
                                }
                                if (item.getTemplateId() == 443 ||
                                    item.getTemplateId() == 485 ||
                                    item.getTemplateId() == 523 ||
                                    item.getTemplateId() == 524 ||
                                    item.getTemplateId() == 776 ||
                                    item.getTemplateId() == 777
                                ) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.param = 0;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(65);
                                    item.options.add(option);
                                    option = new ItemOption();
                                    option.param = 1000;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(66);
                                    item.options.add(option);
                                }
                                if (item.getTemplateId() == 594) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.param = 3000;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(6);
                                    item.options.add(option);
                                }
                                if (item.isTrangBiThu()) {
                                    item.randomGetOptionTrangBiThu(false);
                                }
                                if (item.getTemplateId() == 568) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.param = 30;
                                    option.active = 1;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(100);
                                    item.options.add(option);
                                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                                }
                                if (item.getTemplateId() == 569) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.param = 300;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(99);
                                    item.options.add(option);
                                    option.active = 1;
                                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                                }
                                if (item.getTemplateId() == 570) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.active = 1;
                                    option.param = 50;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(98);
                                    item.options.add(option);
                                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                                }
                                if (item.getTemplateId() == 571) {
                                    item.options = new Vector<>();
                                    ItemOption option = new ItemOption();
                                    option.active = 1;
                                    option.param = 20;
                                    option.optionTemplate = ServerController.iOptionTemplates.get(101);
                                    item.options.add(option);
                                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                                }
                                if (item.isPetTuanLoc() || item.isPetBoru()) {
                                    item.createOptionPetTuanLoc();
                                }
                                if (item.isTypeBiKip()) {
                                    item.createOptionBiKip();
                                }
                                item.createOptionItemGem();
                                item.quantity = quantity;
                                if ((item.template.isUpToUp && player.countFreeBag() < 1) || (!item.template.isUpToUp && player.countFreeBag() < quantity)) {
                                    NJUtil.sendServer(player.getSession(), "Số lượng item vượt quá mức hành trang");
                                    return true;
                                }
                                Database.saveLogAll(player.name, "add item " + itemId + " > " + quantity, "additem");
                                LOGGER.info("Admin-{}: Add {} item {}", player.name, quantity, itemId);
                                player.addItemToBagNoDialog(item);
                            } else {
                                NJUtil.sendServer(player.getSession(), "Không tìm thấy nhân vật này");
                            }
                            return true;
                        }
                        case CMD_SET_LEVEL: {
                            int level = 0;
                            try {
                                level = Integer.parseInt(param1);
                            } catch (Exception e) {
                            }
                            if (level > 0) {
                                LOGGER.info("Admin-{}: Set level {}", player.name, level);
                                player.setLevel(level);
                                return true;
                            }
                            return false;
                        }
                        case CMD_CLEAR_MOB: {
                            LOGGER.info("Admin-{}: Clear all mob in map {}", player.name, player.map.mapId);
                            int point = 0;
                            if (player.map.isDunPB) {
                                DunPB dun = (DunPB) player.map;
                                if (dun.loop == 0 || dun.loop == 2) {
                                    point = 1;
                                } else if (dun.loop == 4 || dun.loop == 6) {
                                    point = 2;
                                } else if (dun.loop == 8 || dun.loop == 10) {
                                    point = 3;
                                } else if (dun.loop == 12 || dun.loop == 14) {
                                    point = 4;
                                } else if (dun.loop > 14) {
                                    point = 5;
                                }
                            }
                            if (player.map.getTemplateId() == 128) {
                                player.pointPB += 100 + NJUtil.randomNumber(100);
                            }
                            for (int j = 0; j < player.map.npcs.size(); ++j) {
                                if (player.map.npcs.get(j).status != Npc.STATUS_DIE) {
                                    player.pointPB += point;
                                }
                                player.sendPointPB();
                                player.map.npcs.get(j).status = Npc.STATUS_DIE;
                                if (player.map.isDunPb9x()) {
                                    --player.map.npcs.get(j).nDropItem;
                                    if (player.map.npcs.get(j).nDropItem < 0) {
                                        player.map.npcs.get(j).nDropItem = 0;
                                    }
                                }
                                player.npcDie(player.map.npcs.get(j), 0, false);
                            }
                            return true;
                        }
                        case CMD_CLEAR_BAG: {
                            LOGGER.info("Admin-{}: Clear bag", player.name);
                            for (Item item : player.itemBags) {
                                if (item != null) {
                                    player.throwItem(item);
                                }
                            }
                            player.sendUpdateInfoMe();
                            return true;
                        }
                        case CMD_POINT_TT: {
                            int pointTT = 0;
                            try {
                                pointTT = Integer.parseInt(param1);
                            } catch (Exception e) {
                                NJUtil.sendServer(player.getSession(), "Điểm không hợp lệ");
                            }
                            if (pointTT > 0) {
                                player.pointTT += pointTT;
                                LOGGER.info("Admin-{}: Add {} point TT", player.name, pointTT);
                                NJUtil.sendServer(player.getSession(), "Đã tăng " + pointTT + " điểm tinh tú");
                            }
                            return true;
                        }
                        case CMD_RELOAD_CONFIG: {
                            if (GameServer.getInstance().loadGameConfig()) {
                                NJUtil.sendServer(player.getSession(), "Đã reload lại config!");
                                LOGGER.info("Admin-{}: Game config reloaded", player.name);
                                return true;
                            }
                            return false;
                        }
                        case CMD_TEST_CT: {
                            GameServer.openChienTruongTest = !GameServer.openChienTruongTest;
                            LOGGER.info("Admin-{}: Test CT: " + GameServer.openChienTruongTest, player.name);
                            NJUtil.sendServer(player.getSession(), "Đã " + (GameServer.openChienTruongTest ? "mở" : "đóng") + " test chiến trường");
                            return true;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
        return false;
    }
}
