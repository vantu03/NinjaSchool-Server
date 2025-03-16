    package com.tgame.ninja.server;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.branch.tasks.NguyetNhanTask;
import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.io.SessionManager;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.*;
import com.tgame.ninja.real.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GameServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    public static int CLIENT_JAVA = 1;

    public static int CLIENT_ANDROID = 2;

    public static int CLIENT_IPHONE = 3;

    public static int CLIENT_PC = 4;

    public static int CLIENT_IPHONEAP = 5;

    public static int CLIENT_WINPHONE = 6;

    public static int CLIENT_ANDROID_STORE = 7;

    public static int SV_LOCAL = 0;

    public static int LANG_VI = 0;

    public static int LANG_EN = 1;

    public static int server = SV_LOCAL;

    public static int language = LANG_VI;

    public static int port = 14444;

    public static int portWeb = 19999;

    public static int limitCCU = 0;

    public static int maxPlayer = 3;

    public static int maxLevel = 0;

    public static boolean quickPlay = false;

    public static long timeStartServer = 0L;

    public static boolean isDailyMaintenance = false;

    public static String pwdCommand = null;

    public static LocalTime timeMaintenance;

    public static int expRate = 1;

    public static int yenRate = 1;

    public static int chestRate = 1;

    public static boolean isRestart = false;

    public static boolean openTrade = false;

    public static boolean openGiaToc = false;

    public static boolean openLanhDiaGiaToc = false;

    public static boolean openGiaTocChien = false;

    public static boolean openThanThuGiaToc = false;

    public static boolean openShinwa = false;

    public static boolean openVongXoay = false;

    public static boolean openLatHinh = false;

    public static boolean openLoiDai = false;

    public static boolean openHangDong3x = false;

    public static boolean openHangDong4x = false;

    public static boolean openHangDong5x = false;

    public static boolean openHangDong6x = false;

    public static boolean openHangDong7x = false;

    public static boolean openHangDong9x = false;

    public static boolean openChienTruong = false;

    public static boolean openChienTruongTest = false;

    public static boolean openChienTruongKeo = false;

    public static boolean openThienDia = false;

    public static boolean openMixedArena = false;

    public static boolean openDailyTask = false;

    public static boolean openNvBiAn = false;

    public static boolean openTaThu = false;

    public static boolean openThatThuAi = false;

    public static boolean openTinhTu = false;

    public static boolean openVDMQ = false;

    public static boolean openLangCo = false;

    public static boolean openTinhLuyen = false;

    public static boolean openKhamNgoc = false;

    public static boolean openNinjaTaiNang = false;

    public static boolean openNinjaDeNhat = false;

    public static boolean openWorldBoss = false;

    public static boolean openNguyetNhan = false;

    public static boolean openPhanThan = false;

    public static boolean openThienBienLenh = false;

    public static boolean openTangLuong = false;

    public static int minCCU = 0;

    public static int maxCCU = 0;

    public static int maxUser = 0;

    public static boolean setGiftThidau = false;

    public static boolean isTraoQua = false;

    public static Vector<LogData> logs = new Vector<>();

    public static int lockSize = 0;

    public static String outPath = "output";

    public static boolean isUpdateClan = false;

    static {
        File directory = new File(outPath);
        if (!directory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            directory.mkdir();
        }
    }

    public static GameServer getInstance() {
        return MyHolder.INSTANCE;
    }

    public static void main(String[] args) {
        if (args.length > 0 && args[0].trim().equals("restart")) {
            isRestart = true;
            LOGGER.info("Wait 5 minutes before server start");
            NJUtil.sleep(NJUtil.getMilisByMinute(5));
        }
        getInstance().runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> LOGGER.info("Good bye!")));
    }

    public void runServer() {
        timeStartServer = System.currentTimeMillis();
        if (!isRestart) {
            LOGGER.info("Server starting...");
        }
        Capcha.loadImage();
        loadConfig();
        NguyetNhanTask.loadInfoQuest();
        Player.loadInfo();
        loadData();
        updateClan();
        runLogCCUThread();
        runMonitorThread();
        runMonitorExit();
        runSeesionThread();
        runPlayerThread();
        runShinwaThread();
        runSaveLogThread();
        Player.setTimeCake();
        Database.loadServerSetting();
        runBackupThread();
        runRewardThread();
        runSocketGameServer();
        NJUtil.sleep(3000L);
        runSocketWebThread();
    }

    public void loadConfig() {
        try (FileInputStream fileInputStream = new FileInputStream("server.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);
            // Server config
            server = Integer.parseInt(prop.getProperty("server.id"));
            port = Integer.parseInt(prop.getProperty("server.port"));
            portWeb = Integer.parseInt(prop.getProperty("server.portWeb"));
            limitCCU = Integer.parseInt(prop.getProperty("server.limitCCU"));
            language = Integer.parseInt(prop.getProperty("server.language"));
            timeMaintenance = LocalTime.parse(prop.getProperty("server.maintenance"), DateTimeFormatter.ofPattern("H:m"));
            pwdCommand = prop.getProperty("server.password");
            maxPlayer = Integer.parseInt(prop.getProperty("server.maxPlayer"));
            maxLevel = Integer.parseInt(prop.getProperty("server.maxLevel"));
            quickPlay = Boolean.parseBoolean(prop.getProperty("server.quickPlay"));
            expRate = Integer.parseInt(prop.getProperty("server.xpRate"));
            yenRate = Integer.parseInt(prop.getProperty("server.yenRate"));
            chestRate = Integer.parseInt(prop.getProperty("server.chestRate"));
            // Database init
            Database.createPool(
                prop.getProperty("database.host"),
                prop.getProperty("database.name"),
                prop.getProperty("database.user"),
                prop.getProperty("database.password"),
                Integer.parseInt(prop.getProperty("database.maxPool"))
            );
            Database.createPoolWeb(
                prop.getProperty("database2.host"),
                prop.getProperty("database2.name"),
                prop.getProperty("database2.user"),
                prop.getProperty("database2.password"),
                Integer.parseInt(prop.getProperty("database2.maxPool"))
            );
            // Game config
            loadGameConfig();
            System.out.println("-----------------------------------------------------------");
            System.out.println("* LANGUAGE: " + (getLang() == LANG_VI ? "VN" : "EN"));
            System.out.println("* TIME MAINTENANCE: " + timeMaintenance.toString());
            System.out.println("* MAX LEVEL: " + maxLevel);
            System.out.println("-----------------------------------------------------------");
        } catch (Exception e) {
            LOGGER.error("", e);
            System.exit(0);
        }
    }

    public boolean loadGameConfig() {
        try (FileInputStream fileInputStream = new FileInputStream("server.properties")) {
            Properties prop = new Properties();
            prop.load(fileInputStream);
            openTrade = Boolean.parseBoolean(prop.getProperty("game.trade"));
            openGiaToc = Boolean.parseBoolean(prop.getProperty("game.clan"));
            openLanhDiaGiaToc = Boolean.parseBoolean(prop.getProperty("game.clanPB"));
            openGiaTocChien = Boolean.parseBoolean(prop.getProperty("game.clanBattle"));
            openThanThuGiaToc = Boolean.parseBoolean(prop.getProperty("game.clanPet"));
            openShinwa = Boolean.parseBoolean(prop.getProperty("game.shinwa"));
            openVongXoay = Boolean.parseBoolean(prop.getProperty("game.vongxoay"));
            openLatHinh = Boolean.parseBoolean(prop.getProperty("game.lathinh"));
            openLoiDai = Boolean.parseBoolean(prop.getProperty("game.loidai"));
            openHangDong3x = Boolean.parseBoolean(prop.getProperty("game.hd3x"));
            openHangDong4x = Boolean.parseBoolean(prop.getProperty("game.hd4x"));
            openHangDong5x = Boolean.parseBoolean(prop.getProperty("game.hd5x"));
            openHangDong6x = Boolean.parseBoolean(prop.getProperty("game.hd6x"));
            openHangDong7x = Boolean.parseBoolean(prop.getProperty("game.hd7x"));
            openHangDong9x = Boolean.parseBoolean(prop.getProperty("game.hd9x"));
            openChienTruong = Boolean.parseBoolean(prop.getProperty("game.chientruong"));
            openChienTruongKeo = Boolean.parseBoolean(prop.getProperty("game.chientruongKeo"));
            openThienDia = Boolean.parseBoolean(prop.getProperty("game.thiendia"));
            openMixedArena = Boolean.parseBoolean(prop.getProperty("game.mxArena"));
            openDailyTask = Boolean.parseBoolean(prop.getProperty("game.dailyTask"));
            openNvBiAn = Boolean.parseBoolean(prop.getProperty("game.nvBian"));
            openTaThu = Boolean.parseBoolean(prop.getProperty("game.tathu"));
            openThatThuAi = Boolean.parseBoolean(prop.getProperty("game.thatthu"));
            openTinhTu = Boolean.parseBoolean(prop.getProperty("game.tinhtu"));
            openVDMQ = Boolean.parseBoolean(prop.getProperty("game.vdmq"));
            openLangCo = Boolean.parseBoolean(prop.getProperty("game.langco"));
            openTinhLuyen = Boolean.parseBoolean(prop.getProperty("game.tinhluyen"));
            openKhamNgoc = Boolean.parseBoolean(prop.getProperty("game.khamngoc"));
            openNinjaTaiNang = Boolean.parseBoolean(prop.getProperty("game.njTainang"));
            openNinjaDeNhat = Boolean.parseBoolean(prop.getProperty("game.njDenhat"));
            openWorldBoss = Boolean.parseBoolean(prop.getProperty("game.worldBoss"));
            openNguyetNhan = Boolean.parseBoolean(prop.getProperty("game.nguyetnhan"));
            openPhanThan = Boolean.parseBoolean(prop.getProperty("game.phanthan"));
            openThienBienLenh = Boolean.parseBoolean(prop.getProperty("game.tbl"));
            openTangLuong = Boolean.parseBoolean(prop.getProperty("game.tangluong"));
            return true;
        } catch (Exception e) {
            LOGGER.error("Load config error", e);
        }
        return false;
    }

    public void loadData() {
        for (int i = 0; i < Player.coinUpClothes1.length; ++i) {
            int[] coinUpClothes1 = Player.coinUpClothes1;
            coinUpClothes1[i] -= Player.coinUpClothes1[i] * 3 / 10;
        }
        for (int i = 0; i < Player.coinUpAdorns1.length; ++i) {
            int[] coinUpAdorns1 = Player.coinUpAdorns1;
            coinUpAdorns1[i] -= Player.coinUpAdorns1[i] * 3 / 10;
        }
        for (int i = 0; i < Player.coinUpWeapons1.length; ++i) {
            int[] coinUpWeapons1 = Player.coinUpWeapons1;
            coinUpWeapons1[i] -= Player.coinUpWeapons1[i] * 3 / 10;
        }
        Arrays.fill(Map.bossTrumMapIds, -1);
        Arrays.fill(Map.bossTrumZoneIds, -1);
        int maxplayerId = Database.getMaxPlayerId();
        if (maxplayerId > ServerController.newplayeId) {
            ServerController.newplayeId = maxplayerId;
        }
        ServerController.dataArrow = NJUtil.readFileBytes("data/array/nj_arrow");
        ServerController.dataEffect = NJUtil.readFileBytes("data/array/nj_effect");
        ServerController.dataImage = NJUtil.readFileBytes("data/array/nj_image");
        ServerController.dataPart = NJUtil.readFileBytes("data/array/nj_part");
        ServerController.dataSkill = NJUtil.readFileBytes("data/array/nj_skill");
        Database.loadNames();
        Player.loadMaxExp();
        Database.loadAllPlayerArenaInfo();
        ServerController.sOptionTemplates = Database.getSkillOptionTemplate();
        ServerController.iOptionTemplates = Database.getItemOptionTemplate();
        ServerController.itemDefaults = Database.getItemDefault();
        Database.loadTop();
        EventManager.getInstance().loadData();
        ServerController.itemTemplates = Database.getItemTemplate();
        ServerController.npcTemplates = Database.getNpcTemplate();
        for (NpcTemplate npcTemplate : ServerController.npcTemplates) {
            npcTemplate.loadData();
        }
        ServerController.nClasss = Database.getNClass();
        for (NClass nClass : ServerController.nClasss) {
            for (SkillTemplate skillTemplate : nClass.skillTemplates) {
                for (Skill skill : skillTemplate.skills) {
                    ServerController.skills.put(skill.skillId, skill);
                }
            }
        }
        ServerController.mapTemplates = Database.getMapTemplate();
        ServerController.playerTemplates = Database.getPlayerTemplate();
        ServerController.effTemplates = Database.getEffectTemplate();
        Database.updateItemShopSell();
        Database.loadItemShop();
        ServerController.shopTrangsucs.addAll(ServerController.shopLiens);
        ServerController.shopTrangsucs.addAll(ServerController.shopNhans);
        ServerController.shopTrangsucs.addAll(ServerController.shopNgocBois);
        ServerController.shopTrangsucs.addAll(ServerController.shopPhus);
        ServerController.shopPhongcus.addAll(ServerController.shopNonNams);
        ServerController.shopPhongcus.addAll(ServerController.shopAoNams);
        ServerController.shopPhongcus.addAll(ServerController.shopGangTayNams);
        ServerController.shopPhongcus.addAll(ServerController.shopQuanNams);
        ServerController.shopPhongcus.addAll(ServerController.shopGiayNams);
        ServerController.shopPhongcus.addAll(ServerController.shopNonNus);
        ServerController.shopPhongcus.addAll(ServerController.shopAoNus);
        ServerController.shopPhongcus.addAll(ServerController.shopGangTayNus);
        ServerController.shopPhongcus.addAll(ServerController.shopQuanNus);
        ServerController.shopPhongcus.addAll(ServerController.shopGiayNus);
        ServerController.shopTrangsucs_not_sell.addAll(ServerController.shopLiens_not_sell);
        ServerController.shopTrangsucs_not_sell.addAll(ServerController.shopNhans_not_sell);
        ServerController.shopTrangsucs_not_sell.addAll(ServerController.shopNgocBois_not_sell);
        ServerController.shopTrangsucs_not_sell.addAll(ServerController.shopPhus_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopNonNams_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopAoNams_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopGangTayNams_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopQuanNams_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopGiayNams_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopNonNus_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopAoNus_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopGangTayNus_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopQuanNus_not_sell);
        ServerController.shopPhongcus_not_sell.addAll(ServerController.shopGiayNus_not_sell);
        ServerController.shops = Database.getItemAuction();
        ServerController.clans = Database.getClan();
        TaskTemplate.loadAllTask();
        Item.loadItemFunction();
        NpcPlayer.loadNpcFunction();
        try (FileInputStream fdis = new FileInputStream("data/link/Ninjaschool.dia");
             DataInputStream dis = new DataInputStream(fdis)
        ) {
            for (int size = dis.readShort(), j = 0; j < size; ++j) {
                NLink link = new NLink();
                link.mapTemplateId1 = dis.readShort();
                link.minX1 = dis.readShort();
                link.minY1 = dis.readShort();
                link.maxX1 = dis.readShort();
                link.maxY1 = dis.readShort();
                link.X1 = dis.readShort();
                link.Y1 = dis.readShort();
                link.mapTemplateId2 = dis.readShort();
                link.minX2 = dis.readShort();
                link.minY2 = dis.readShort();
                link.maxX2 = dis.readShort();
                link.maxY2 = dis.readShort();
                link.X2 = dis.readShort();
                link.Y2 = dis.readShort();
                for (int k = 0; k < ServerController.mapTemplates.size(); ++k) {
                    if (ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId1 || ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId2) {
                        ServerController.mapTemplates.get(k).links.add(link);
                    }
                }
                if (link.mapTemplateId1 == 98 || link.mapTemplateId2 == 98) {
                    link = link.clone();
                    if (link.mapTemplateId1 == 98) {
                        link.mapTemplateId1 = 131;
                        link.mapTemplateId2 = 130;
                        link.X2 = 48;
                        link.Y2 = 240;
                        link.minX2 = 0;
                        link.maxX2 = 24;
                        link.minY2 = 216;
                        link.maxY2 = 240;
                    } else {
                        link.mapTemplateId1 = 130;
                        link.mapTemplateId2 = 131;
                        link.X1 = 36;
                        link.Y1 = 240;
                        link.minX1 = 0;
                        link.maxX1 = 24;
                        link.minY1 = 216;
                        link.maxY1 = 240;
                    }
                    for (int k = 0; k < ServerController.mapTemplates.size(); ++k) {
                        if (ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId1 || ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId2) {
                            ServerController.mapTemplates.get(k).links.add(link);
                        }
                    }
                }
                if (link.mapTemplateId1 == 104 || link.mapTemplateId2 == 104) {
                    link = link.clone();
                    if (link.mapTemplateId1 == 104) {
                        link.mapTemplateId1 = 132;
                        link.mapTemplateId2 = 130;
                        link.X2 = 2136;
                        link.Y2 = 240;
                        link.minX2 = 2160;
                        link.maxX2 = 2184;
                        link.minY2 = 216;
                        link.maxY2 = 240;
                    } else {
                        link.mapTemplateId1 = 130;
                        link.mapTemplateId2 = 132;
                        link.X1 = 2160;
                        link.Y1 = 240;
                        link.minX1 = 2184;
                        link.maxX1 = 2208;
                        link.minY1 = 216;
                        link.maxY1 = 240;
                    }
                    for (int k = 0; k < ServerController.mapTemplates.size(); ++k) {
                        if (ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId1 || ServerController.mapTemplates.get(k).mapTemplateId == link.mapTemplateId2) {
                            ServerController.mapTemplates.get(k).links.add(link);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (MapTemplate mapTemplate : ServerController.mapTemplates) {
            mapTemplate.loadData();
        }
        byte[][] arr = {
            ServerController.readData(),
            ServerController.readMap(null),
            ServerController.readSkill(null),
            ServerController.readItem(null)
        };
        Data[] datas = Database.getDatas();
        if (datas == null) {
            ServerController.vData = ServerController.vMap = ServerController.vSkill = ServerController.vItem = 1;
            Database.updateData(0, arr[0], ServerController.vData);
            Database.updateData(1, arr[1], ServerController.vMap);
            Database.updateData(2, arr[2], ServerController.vSkill);
            Database.updateData(3, arr[3], ServerController.vItem);
        } else {
            boolean[] isDiff = new boolean[4];
            for (int k = 0; k < datas.length; ++k) {
                for (int l = 0; l < datas[k].data.length; ++l) {
                    if (datas[k].data[l] != arr[k][l]) {
                        isDiff[k] = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < isDiff.length; i++) {
                if (isDiff[i]) {
                    datas[i].data = arr[i];
                    ++datas[i].version;
                    if (datas[i].version >= 120) {
                        datas[i].version = 1;
                    }
                    Database.updateData(i, datas[i].data, datas[i].version);
                }
            }
            ServerController.vData = datas[0].version;
            ServerController.vMap = datas[1].version;
            ServerController.vSkill = datas[2].version;
            ServerController.vItem = datas[3].version;
        }
        Database.loadAllXoso();
        Player.startThreadXoso();
        Database.startThreadSavePlayerFaill();
    }

    private void runBackupThread() {
        new Thread(() -> {
            Thread.currentThread().setName("BACKUP");
            NJUtil.sleep(30000L);
            while (true) {
                MixedArena.updateTimer();
                LocalTime timeNow = LocalTime.now();
                int hour = timeNow.getHour();
                int minute = timeNow.getMinute();
                if (hour == timeMaintenance.getHour() && minute >= timeMaintenance.getMinute() && minute <= timeMaintenance.getMinute() + 1 && !isDailyMaintenance) {
                    isDailyMaintenance = true;
                    ServerController.isExit = true;
                    LOGGER.info("DAILY MAINTENANCE!!!");
                    NJUtil.sleep(30000L);
                    exitGame(false);
                    try {
                        Desktop.getDesktop().open(new File("restart.bat"));
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    System.exit(0);
                } else if (hour != timeMaintenance.getHour()) {
                    isDailyMaintenance = false;
                }
                NJUtil.sleep(30000L);
            }
        }).start();
    }

    private void runRewardThread() {
        new Thread(() -> {
            Thread.currentThread().setName("REWARD");
            while (true) {
                LocalDateTime dateTimeNow = LocalDateTime.now();
                int day = dateTimeNow.getDayOfMonth();
                int hour = dateTimeNow.getHour();
                int minute = dateTimeNow.getMinute();
                if ((hour == 23 && minute >= 45 && minute <= 46 && !isTraoQua) || setGiftThidau) {
                    isTraoQua = true;
                    setGiftThidau = false;
                    if (openNinjaTaiNang && day == 27) {
                        try {
                            Vector<TopTainang> vClear = new Vector<>(ServerController.topTaiNang);
                            vClear.sort(TopTainang.sort);
                            int[] xuTN = { 100000, 100000, 100000, 100000 };
                            int[] luongTN = { 100, 100, 100, 100 };
                            int[] yenTN = { 100000, 100000, 100000, 100000 };
                            for (int i = 0; i < vClear.size(); ++i) {
                                TopTainang top = vClear.get(i);
                                int xu = 0, luong = 0, yen = 0;
                                int rank = i + 1, index = -1; // hạng tài năng, index quà
                                if (rank == 1) {
                                    index = 0;
                                } else if (rank <= 4) {
                                    index = 1;
                                } else if (rank <= 10) {
                                    index = 2;
                                } else if (rank <= 30) {
                                    index = 3;
                                }
                                if (index >= 0) {
                                    xu = xuTN[index];
                                    luong = luongTN[index];
                                    yen = yenTN[index];
                                }
                                Player p = ServerController.hnPlayers.get(top.name);
                                if (p != null) {
                                    String info = AlertFunction.YOU_GET(p.getSession()) + " ";
                                    if (xu > 0) {
                                        p.sendUpdateCoinBag(xu);
                                        info = info + xu + " " + AlertFunction.COIN(p.getSession());
                                    }
                                    if (yen > 0) {
                                        p.sendUpdateCoinLockYen(yen);
                                        info = info + yen + " " + AlertFunction.COIN_LOCK(p.getSession());
                                    }
                                    if (luong > 0) {
                                        p.sendUpdateLuong(luong);
                                        info = info + ", " + luong + " " + AlertFunction.GOLD(p.getSession());
                                    }
                                    Message message = new Message(Cmd.CHAT_SERVER);
                                    message.writeUTF(top.name);
                                    message.writeUTF(info);
                                    p.getSession().sendMessage(message);
                                } else {
                                    Database.setMoneyRewardThiendia(top.name, xu, yen, luong);
                                }
                                Database.saveLogAll(top.name, "hang " + rank + " nhan duoc " + xu + " xu," + luong + " luong," + yen + " yen, online: " + (p != null ? "online" : "offline"), "quaninjatainang");
                            }
                        } catch (Exception e) {
                        }
                    }
                    if (openThienDia) {
                        Vector<PlayerArenaInfo> data_dia = new Vector<>(PlayerArenaInfo.ALL_PLAYER_ARENA_DIA);
                        Vector<PlayerArenaInfo> data_thien = new Vector<>(PlayerArenaInfo.ALL_PLAYER_ARENA_THIEN);
                        int[] xuTDB = { 100000, 100000, 100000, 100000 };
                        int[] luongTDB = { 100, 100, 100, 100 };
                        int[] yenTDB = { 100000, 100000, 100000, 100000 };
                        int size = Math.min(1000, data_dia.size());
                        for (int i = 0; i < size; ++i) {
                            try {
                                PlayerArenaInfo d = data_dia.get(i);
                                int xu = 0, luong = 0, yen = 0;
                                int rank = i + 1, index = -1; // hạng địa bảng, index quà
                                if (rank == 1) {
                                    index = 0;
                                } else if (rank <= 4) {
                                    index = 1;
                                } else if (rank <= 10) {
                                    index = 2;
                                } else if (rank <= 30) {
                                    index = 3;
                                }
                                if (index >= 0) {
                                    xu = xuTDB[index];
                                    luong = luongTDB[index];
                                    yen = yenTDB[index];
                                }
                                Player p = ServerController.hnPlayers.get(d.name);
                                if (p != null) {
                                    String info = AlertFunction.YOU_GET(p.getSession()) + " ";
                                    if (xu > 0) {
                                        p.sendUpdateCoinBag(xu);
                                        info = info + xu + " " + AlertFunction.COIN(p.getSession());
                                    }
                                    if (yen > 0) {
                                        p.sendUpdateCoinLockYen(yen);
                                        info = info + yen + " " + AlertFunction.YEN(p.getSession());
                                    }
                                    if (luong > 0) {
                                        p.sendUpdateLuong(luong);
                                        info = info + ", " + luong + " " + AlertFunction.GOLD(p.getSession());
                                    }
                                    Message message2 = new Message(Cmd.CHAT_SERVER);
                                    message2.writeUTF(d.name);
                                    message2.writeUTF(info);
                                    p.getSession().sendMessage(message2);
                                } else {
                                    Database.setMoneyRewardThiendia(d.name, xu, yen, luong);
                                }
                                Database.saveLogAll(d.name, "hang " + rank + " nhan duoc " + xu + " xu," + luong + " luong," + yen + " yen, online: " + (p != null ? "online" : "offline"), "quathiendiabang");
                            } catch (Exception e) {
                                LOGGER.error("", e);
                            }
                        }
                        size = Math.min(1000, data_thien.size());
                        for (int i = 0; i < size; ++i) {
                            try {
                                PlayerArenaInfo d = data_thien.get(i);
                                int xu = 0, luong = 0, yen = 0;
                                int rank = i + 1, index = -1; // hạng thiên bảng, index quà
                                if (rank == 1) {
                                    index = 0;
                                } else if (rank <= 4) {
                                    index = 1;
                                } else if (rank <= 10) {
                                    index = 2;
                                } else if (rank <= 30) {
                                    index = 3;
                                }
                                if (index >= 0) {
                                    xu = xuTDB[index];
                                    luong = luongTDB[index];
                                    yen = yenTDB[index];
                                }
                                Player p = ServerController.hnPlayers.get(d.name);
                                if (p != null) {
                                    String info = AlertFunction.YOU_GET(p.getSession()) + " ";
                                    if (xu > 0) {
                                        p.sendUpdateCoinBag(xu);
                                        info = info + xu + " " + AlertFunction.COIN(p.getSession());
                                    }
                                    if (yen > 0) {
                                        p.sendUpdateCoinLockYen(yen);
                                        info = info + yen + " " + AlertFunction.YEN(p.getSession());
                                    }
                                    if (luong > 0) {
                                        p.sendUpdateLuong(luong);
                                        info = info + ", " + luong + " " + AlertFunction.GOLD(p.getSession());
                                    }
                                    Message message2 = new Message(Cmd.CHAT_SERVER);
                                    message2.writeUTF(d.name);
                                    message2.writeUTF(info);
                                    p.getSession().sendMessage(message2);
                                } else {
                                    Database.setMoneyRewardThiendia(d.name, xu, yen, luong);
                                }
                                Database.saveLogAll(d.name, "hang " + rank + " nhan duoc " + xu + " xu," + luong + " luong," + yen + " yen, online: " + (p != null ? "online" : "offline"), "quathiendiabang");
                            } catch (Exception e) {
                                LOGGER.error("", e);
                            }
                        }
                    }
                }
                NJUtil.sleep(1000L);
            }
        }).start();
    }

    private void runLogCCUThread() {
        new Thread(() -> {
            NJUtil.sleep(30000L);
            Thread.currentThread().setName("CCU");
            while (true) {
                long timeNow = System.currentTimeMillis();
                maxCCU = Math.max(maxCCU, SessionManager.instance.size());
                minCCU = Math.min(minCCU, SessionManager.instance.size());
                maxUser = Math.max(maxUser, ServerController.huPlayers.size());
                int countJava = 0;
                int countAndroid = 0;
                int countIos = 0;
                int countPC = 0;
                try {
                    Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
                    for (int userId : huPlayers.keySet()) {
                        try {
                            Player p = ServerController.huPlayers.get(userId);
                            if (p == null || p.getSession() == null) {
                                continue;
                            }
                            if (p.getSession().clientType == CLIENT_JAVA) {
                                ++countJava;
                            } else if (p.getSession().clientType == CLIENT_ANDROID || p.getSession().clientType == CLIENT_ANDROID_STORE) {
                                ++countAndroid;
                            } else if (p.getSession().clientType == CLIENT_IPHONE || p.getSession().clientType == CLIENT_IPHONEAP) {
                                ++countIos;
                            } else if (p.getSession().clientType == CLIENT_PC) {
                                ++countPC;
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (timeNow - timeStartServer > NJUtil.getMilisByHour(1)) {
                    timeStartServer = timeNow;
                    if (SessionManager.instance.size() < maxCCU) {
                        minCCU = SessionManager.instance.size();
                    }
                    Database.logCCU(countJava, countAndroid, countIos, countPC);
                    maxCCU = SessionManager.instance.size();
                }
                NJUtil.sleep(3000L);
            }
        }).start();
    }

    private void runMonitorExit() {
        new Thread(() -> {
            Thread.currentThread().setName("MONITOR EXIT");
            while (true) {
                if ((ServerController.isExit || ServerController.turnOnExit) && !isDailyMaintenance) {
                    try {
                        ServerController.isExit = true;
                        Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
                        for (int userId : huPlayers.keySet()) {
                            try {
                                Player p = ServerController.huPlayers.get(userId);
                                if (p == null || p.getSession() == null) {
                                    continue;
                                }
                                NJUtil.sendDialog(p.getSession(), AlertFunction.SERVER_STOP(p.getSession()));
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    LOGGER.info("Wait 30 seconds to shutdown!");
                    NJUtil.sleep(30000L);
                    exitGame(true);
                    return;
                }
                NJUtil.sleep(30000L);
            }
        }).start();
    }

    private void runMonitorThread() {
        new Thread(() -> {
            Thread.currentThread().setName("MONITOR SESSION");
            NJUtil.sleep(3000L);
            while (true) {
                try {
                    if (!isServerLocal()) {
                        LOGGER.info("Mem: " + Runtime.getRuntime().freeMemory() / 1024L / 1024L +
                            "/" + Runtime.getRuntime().totalMemory() / 1024L / 1024L +
                            ", Session: " + SessionManager.instance.size() +
                            ", Player: " + ServerController.huPlayers.size() +
                            ", Lock: " + lockSize +
                            ", Game Pools: " + Database.connPool.getIdleCount() + "/" + Database.connPool.getActiveCount() +
                            ", Web Pools: " + Database.connPoolWeb.getIdleCount() + "/" + Database.connPoolWeb.getActiveCount()
                        );
                    }
                    Database.loadServerSetting();
                } catch (Exception e) {
                    LOGGER.error("", e);
                } finally {
                    NJUtil.sleep(30000L);
                }
            }
        }).start();
    }

    private void runPlayerThread() {
        new Thread(() -> {
            Thread.currentThread().setName("PLAYER");
            while (!ServerController.isExit) {
                LocalTime timeNow = LocalTime.now();
                int hour = timeNow.getHour();
                int minute = timeNow.getMinute();
                int second = timeNow.getSecond();
                if (GameServer.openChienTruong) {
                    if ((hour == 12 || hour == 15 || hour == 18) && minute >= 58) {
                        Map.idLefts.clear();
                        Map.idRights.clear();
                        Map.playerLefts.clear();
                        Map.playerRights.clear();
                        NJUtil.sendServerAlert(AlertFunction.OPEN_CT(null));
                        for (int i = 0; i < Map.npcTrus.size(); ++i) {
                            Map.npcTrus.get(i).status = 0;
                        }
                    }
                    if (hour == 14 || hour == 17 || hour == 20) {
                        if (minute <= 10) {
                            for (int i = 0; i < Map.npcTrus.size(); ++i) {
                                Map.npcTrus.get(i).status = 0;
                            }
                        } else if (minute == 11) {
                            for (int i = 0; i < Map.npcTrus.size(); ++i) {
                                if (Map.npcTrus.get(i).map.zoneId < 10) {
                                    Map.npcTrus.get(i).timeWait = Map.npcTrus.get(i).timeReturn - 100;
                                }
                            }
                            NJUtil.sendChienTruongAlert(AlertFunction.WAIT_TRU(null));
                        }
                    } else {
                        for (int i = 0; i < Map.npcTrus.size(); ++i) {
                            Map.npcTrus.get(i).status = 0;
                        }
                    }
                }
                try {
                    if (openWorldBoss) {
                        if (minute == 29) {
                            Map.isThaBossTrum = false;
                        }
                        if (!Map.isThaBossTrum && hour % 2 == 0 && minute == 30) {
                            Map.isThaBossTrum = true;
                            Map.thaBossTrum();
                        }
                    }
                    if (NJUtil.isOpenNinjaTaiNang()) {
                        if (hour == 17 && minute >= 50 && ServerController.loidaiduns.size() == 0) {
                            Player.openGiaiDau();
                            NJUtil.sendServerAlert(isSvEnglish() ? "Ninja tài năng đã bắt đầu mở cửa." : "Ninja tài năng đã bắt đầu mở cửa.");
                        } else if (hour == 18 && (minute == 0 || minute == 15 || minute == 30 || minute == 45)) {
                            Player.goLoiDai();
                        }
                    }
                    if (hour == 20 && ServerController.loidaiduns.size() != 0) {
                        ServerController.loidaiduns.clear();
                    }
                    if (NJUtil.isOpenNinjaDeNhat()) {
                        if (hour == 21 && minute >= 50 && ServerController.loidaidunsClass.size() == 0) {
                            Player.openGiaiDauClass();
                            NJUtil.sendServerAlert(isSvEnglish() ? "Ninja đệ nhất đã bắt đầu mở cửa." : "Ninja đệ nhất đã bắt đầu mở cửa.");
                        } else if (hour == 22 && (minute == 0 || minute == 15 || minute == 30 || minute == 45)) {
                            Player.goLoiDaiClass();
                        }
                    }
                    if (hour == 0 && ServerController.loidaidunsClass.size() != 0) {
                        ServerController.loidaidunsClass.clear();
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                if (hour == 0 && minute == 0 && second < 35) {
                    Clan.resetLog();
                }
                if ((hour == 14 || hour == 17 || hour == 20) && minute == 30) {
                    if (!Map.isTongKet) {
                        Map.isTongKet = true;
                        Map.tongketFinish();
                    }
                } else {
                    Map.isTongKet = false;
                }
                if (minute == 59) {
                    isUpdateClan = false;
                }
                if (!isUpdateClan && LocalDate.now().getDayOfWeek() == DayOfWeek.MONDAY && hour == 0 && minute == 0) {
                    isUpdateClan = true;
                    try {
                        for (int i = 0; i < ServerController.clans.size(); ++i) {
                            Clan clan = ServerController.clans.get(i);
                            clan.openDun = 3;
                            clan.use_card = 4;
                            clan.coin -= clan.getFee();
                            clan.writeLog("$", Clan.FREE_MONEY, clan.getFee());
                            try {
                                for (Member member : clan.members) {
                                    member.pointClanWeek = 0;
                                }
                            } catch (Exception e) {
                                LOGGER.error("", e);
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
                if (minute != 1 && minute != 31) {
                    continue;
                }
                updateClan();
            }
        }).start();
    }

    private void runShinwaThread() {
        if (GameServer.openShinwa) {
            new Thread(() -> {
                Thread.currentThread().setName("SHINWA");
                NJUtil.sleep(3000L);
                while (!ServerController.isExit) {
                    try {
                        for (int i = ServerController.shops.size() - 1; i >= 0; --i) {
                            Item item = ServerController.shops.get(i);
                            if (System.currentTimeMillis() - item.expires > NJUtil.getMilisByDay(1)) {
                                item.expires = -1L;
                                ServerController.removeItemShops(item);
                                Player p = ServerController.hnPlayers.get(item.playerName);
                                if (p != null && p.addItemToBagNoDialog(item)) {
                                    NJUtil.sendAlertDialogInfo(p.getSession(), AlertFunction.SYSTEM(p.getSession()), AlertFunction.YOU_GET(p.getSession()) + " " + item.template.getName(p.getSession()));
                                } else {
                                    Database.addBoardItem(item);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    NJUtil.sleep(30000L);
                }
            }).start();
        }
    }

    private void runSaveLogThread() {
        new Thread(() -> {
            Thread.currentThread().setName("SAVE LOG");
            while (!ServerController.isExit) {
                NJUtil.sleep(30000L);
                int count = 0;
                while (logs.size() > 0) {
                    try {
                        LogData data = logs.remove(0);
                        if (!Database.saveLogPlayer(data)) {
                            break;
                        }
                        if (++count < 500) {
                            continue;
                        }
                        NJUtil.sleep(10L);
                        count = 0;
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            }
        }).start();
    }

    private void runSeesionThread() {
        new Thread(() -> {
            Thread.currentThread().setName("SESSION");
            while (!ServerController.isExit) {
                NJUtil.sleep(30000L);
                long t = System.currentTimeMillis();
                lockSize = 0;
                try {
                    Hashtable<String, Login> hLogins = new Hashtable<>(ServerController.hLogins);
                    for (String key : hLogins.keySet()) {
                        try {
                            Login login = ServerController.hLogins.get(key);
                            if (login != null) {
                                if (login.count >= 10) {
                                    ++lockSize;
                                }
                                if (t - login.timeConnect <= login.timeLock) {
                                    continue;
                                }
                            }
                            ServerController.hLogins.remove(key);
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                }
                Vector<Session> conns = new Vector<>();
                try {
                    for (int i = 0; i < SessionManager.instance.size(); ++i) {
                        try {
                            Session s = SessionManager.instance.get(i);
                            if (s != null) {
                                if (s.userId == -1 && t - s.connectTime > 30000L) {
                                    conns.add(s);
                                } else if (t - s.lastActiveTime > 600000L) {
                                    conns.add(s);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    for (Session session : conns) {
                        try {
                            session.disconnect("session thread");
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        }).start();
    }

    private void runSocketGameServer() {
        new Thread(() -> {
            Thread.currentThread().setName("SOCKET GAME");
            try (ServerSocket server = new ServerSocket(port)) {
                System.out.println("Game server listen on port " + port);
                while (!ServerController.isExit) {
                    try {
                        Socket clientSocket = server.accept();
                        Session conn = new Session(clientSocket, new ServerController());
                        conn.start();
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
                exitGame(true);
            }
        }).start();
    }

    private void runSocketWebThread() {
        new Thread(() -> {
            Thread.currentThread().setName("SOCKET WEB SESSION");
            try (ServerSocket server = new ServerSocket(portWeb)) {
                while (!ServerController.isExit) {
                    try {
                        Socket clientSocket = server.accept();
                        new SocketAPI(clientSocket);
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
                System.exit(0);
            }
        }).start();
    }

    public void exitGame(boolean exit) {
        try {
            for (int i = 0; i < ServerController.clans.size(); ++i) {
                if (ServerController.clans.get(i).dunClan != null) {
                    ++ServerController.clans.get(i).openDun;
                }
            }
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < ServerController.topHangdong.size(); ++j) {
                str.append(ServerController.topHangdong.get(j).name).append(",")
                    .append(ServerController.topHangdong.get(j).ruong).append("\n");
            }
            NJUtil.saveFile(outPath + "/tophangdong.txt", str.toString());
            str = new StringBuilder();
            for (int j = 0; j < ServerController.topTaiNang.size(); ++j) {
                str.append(ServerController.topTaiNang.get(j).name).append(",")
                    .append(ServerController.topTaiNang.get(j).point).append("\n");
            }
            NJUtil.saveFile(outPath + "/toptainang.txt", str.toString());
            try {
                for (int j = 0; j < ServerController.topTaiNangClass.size(); ++j) {
                    str = new StringBuilder();
                    for (int k = 0; k < ServerController.topTaiNangClass.get(j).size(); ++k) {
                        str.append(ServerController.topTaiNangClass.get(j).get(k).name).append(",")
                            .append(ServerController.topTaiNangClass.get(j).get(k).point).append("\n");
                    }
                    NJUtil.saveFile(outPath + "/toptainang_" + j + ".txt", str.toString());
                }
            } catch (Exception e) {
            }
            StringBuilder strBoard = new StringBuilder();
            for (int l = 0; l < Database.boardwords.size(); ++l) {
                strBoard.append(Database.boardwords.get(l)).append("\n");
            }
            NJUtil.saveFile("data/boardword.txt", strBoard.toString());
            updateClan();
            Database.saveAllPlayer();
            Database.saveAllCharFail();
            LOGGER.info("Force disconnect " + ServerController.huPlayers.size() + " players");
            try {
                Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
                for (int userId : huPlayers.keySet()) {
                    try {
                        Player p = ServerController.huPlayers.get(userId);
                        if (p == null || p.getSession() == null) {
                            continue;
                        }
                        p.getSession().disconnect("kick all");
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            LOGGER.info("Wait 15 seconds...");
            NJUtil.sleep(15000L);
            if (exit) {
                System.exit(0);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    private void updateClan() {
        try {
            for (int i = 0; i < ServerController.clans.size(); ++i) {
                try {
                    Clan clan = ServerController.clans.get(i);
                    int count = 0;
                    for (int j = 0; j < clan.members.size(); ++j) {
                        Member mem = clan.members.get(j);
                        if (mem.typeClan == 1) {
                            mem.typeClan = 0;
                            if (++count >= 10) {
                                break;
                            }
                        }
                    }
                    clan.sort();
                    count = 0;
                    for (int j = 0; j < clan.members.size(); ++j) {
                        Member mem = clan.members.get(j);
                        if (mem.typeClan == 0 && mem.pointClan > 100 && !clan.isInvite(mem.name)) {
                            mem.typeClan = 1;
                            if (++count >= 10) {
                                break;
                            }
                        }
                    }
                    Database.saveClan(clan);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static int getServerId() {
        return server;
    }

    public static int getLang() {
        return language;
    }

    public static boolean isServerLocal() {
        return getServerId() == SV_LOCAL;
    }

    public static boolean isSvEnglish() {
        return getLang() == LANG_EN;
    }

    private static class MyHolder {

        public static GameServer INSTANCE = new GameServer();
    }
}
