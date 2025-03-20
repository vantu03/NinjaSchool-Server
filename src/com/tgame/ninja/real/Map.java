package com.tgame.ninja.real;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);

    public static Vector<String> arenas = new Vector<>();

    public static Vector<Npc> npcTrus = new Vector<>();

    public static Vector<Player> playerLefts = new Vector<>();

    public static Vector<Player> playerRights = new Vector<>();

    public static Vector<Integer> idLefts = new Vector<>();

    public static Vector<Integer> idRights = new Vector<>();

    public static String strTongket = "";

    public static boolean isTongKet = false;

    public static String[] winCT;

    public static boolean isTinhAnh;

    public static boolean isThulinh;

    public static int[] maxZones = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 40, 30, 30, 30, 30, 30, 30, 40, 30, 30, 30, 30, 60, 30, 30, 30, 30, 50, 30, 30, 30, 30, 40, 30, 30, 30, 30, 30, 40, 30, 30, 30, 30, 40, 30, 30, 30, 30, 40, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 0, 0, 0, 30, 0, 30, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 100, 100, 100, 100, 100, 100, 100, 15, 30, 30, 30, 30, 30, 15, 100, 100, 100, 100, 100, 20, 100, 100, 100, 100, 100, 100, 50, 15, 15, 1, 1, 1, 1, 1, 50, 50, 50, 50, 10, 1, 1, 1, 1, 5, 5, 5, 5, 10, 30, 30, 30, 30, 30, 30, 30, 30, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 100, 100, 100, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public static Vector<Map> mapkeos = new Vector<>();

    public static int maxParty = 5;

    public static int saveId = 0;

    public static int[] mapNotSave = {-1, 0, 56, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 98, 99, 100, 101, 102, 103, 104, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 149};

    public static int[] mapNotCreate = {0, 56, 73, 74, 78, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 105, 106, 107, 108, 109, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127, 128, 129, 130, 131, 132, 150, 151, 152, 153, 154, 155, 156};

    public static BossTaThu[] tathus = new BossTaThu[]{
        new BossTaThu(12, 30, 5),
        new BossTaThu(35, 33, 60),
        new BossTaThu(52, 35, 36),
        new BossTaThu(13, 37, 18),
        new BossTaThu(64, 40, 55),
        new BossTaThu(15, 43, 13),
        new BossTaThu(67, 45, 0),
        new BossTaThu(16, 47, 7),
        new BossTaThu(68, 49, 35),
        new BossTaThu(41, 51, 31),
        new BossTaThu(42, 53, 29),
        new BossTaThu(62, 57, 33),
        new BossTaThu(44, 59, 65),
        new BossTaThu(18, 61, 27),
        new BossTaThu(59, 63, 2),
        new BossTaThu(24, 65, 39),
        new BossTaThu(45, 67, 50),
        new BossTaThu(36, 132, 9),
        new BossTaThu(36, 135, 77),
        new BossTaThu(19, 129, 5),
        new BossTaThu(54, 130, 36),
        new BossTaThu(37, 137, 120),
        new BossTaThu(58, 133, 47)
    };

    public static byte[] npcMapIds = {22, 0, 0, 21, 0, 23, 26, 0, 71, 0, 3, 60, 39, 5, 5, 40, 30, 7, 30, 31, 9, 8, 9, 63, 47, 11, 50, 11, 49, 49, 12, 34, 51, 35, 66, 52, 66, 13, 52, 64, 64, 14, 14, 15, 15, 67, 67, 16, 68, 68, 16, 41, 41, 42, 42, 42, 62, 62, 44, 44, 18, 18, 24, 59, 59, 24, 45, 45, 53, 53, 53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 54, 58, 36, 58, 37, 36, 55, 37, 0, 0, 0};

    public static int[] bossTrumMapIds = new int[8];

    public static int[] bossTrumZoneIds = new int[8];

    public static boolean isThaBossTrum = false;

    public int[] longDenZoneIds;

    public Vector<Player> phe1;

    public Vector<Player> phe2;

    public MapTemplate template;

    public int mapId;

    public int zoneId;

    public LinkedBlockingQueue<NMessage> messages;

    public Vector<ItemMap> itemMaps;

    public Vector<BuNhin> buNhins;

    public Vector<Player> playerIns;

    public Vector<Player> playerOuts;

    public Vector<Player> players;

    public Vector<Npc> npcs;

    public Vector<NpcPlayer> npcPlayers;

    public Vector<Short> itemMapIds;

    public long timeEnd;

    public int maxPlayer;

    public int tick;

    public int maxBoss1;

    public int maxBoss2;

    public final Object LOCK;

    public long lastTime;

    public boolean isHaveBoss;

    public boolean isMapBoss;

    public GiaTocChien giatocchien;

    public boolean isDun;

    public boolean isDunClan;

    public boolean isDunPB;

    public boolean isDunVD;

    public boolean isDunVA;

    public boolean isStop;

    public Vector<ItemMap> its;

    public long timeXuathien;

    public Vector<String> playerAs;

    public Vector<String> playerBs;

    public int keoZoneID;

    public boolean isMapKeo;

    public int maxPlayerKeo;

    public int tt;

    public int txh;

    public short ID_HASH_MAP;

    public Vector<Npc> npcChild;

    public Map(int mapTemplateId) {
        longDenZoneIds = new int[5];
        phe1 = new Vector<>();
        phe2 = new Vector<>();
        itemMaps = new Vector<>();
        buNhins = new Vector<>();
        playerIns = new Vector<>();
        playerOuts = new Vector<>();
        players = new Vector<>();
        npcs = new Vector<>();
        npcPlayers = new Vector<>();
        itemMapIds = new Vector<>();
        timeEnd = 0L;
        maxPlayer = 20;
        tick = 0;
        maxBoss1 = 2;
        maxBoss2 = 1;
        LOCK = new Object();
        giatocchien = null;
        isDun = false;
        isDunClan = false;
        isDunPB = false;
        isDunVD = false;
        isDunVA = false;
        isStop = false;
        its = new Vector<>();
        timeXuathien = 0L;
        playerAs = null;
        playerBs = null;
        keoZoneID = 0;
        isMapKeo = false;
        maxPlayerKeo = 20;
        tt = 1800;
        txh = 600;
        ID_HASH_MAP = 0;
        npcChild = new Vector<>();
        mapId = -1;
        template = new MapTemplate(mapTemplateId);
    }

    public Map(int id, int zId, MapTemplate mapTemplate) {
        longDenZoneIds = new int[5];
        phe1 = new Vector<>();
        phe2 = new Vector<>();
        itemMaps = new Vector<>();
        buNhins = new Vector<>();
        playerIns = new Vector<>();
        playerOuts = new Vector<>();
        players = new Vector<>();
        npcs = new Vector<>();
        npcPlayers = new Vector<>();
        itemMapIds = new Vector<>();
        timeEnd = 0L;
        maxPlayer = 20;
        tick = 0;
        maxBoss1 = 2;
        maxBoss2 = 1;
        LOCK = new Object();
        giatocchien = null;
        isDun = false;
        isDunClan = false;
        isDunPB = false;
        isDunVD = false;
        isDunVA = false;
        isStop = false;
        its = new Vector<>();
        timeXuathien = 0L;
        playerAs = null;
        playerBs = null;
        keoZoneID = 0;
        isMapKeo = false;
        maxPlayerKeo = 20;
        tt = 1800;
        txh = 600;
        ID_HASH_MAP = 0;
        npcChild = new Vector<>();
        mapId = id;
        zoneId = zId;
        template = mapTemplate;
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        if (mapTemplate.mapTemplateId == 29) {
            addItemMap(new ItemMap(new Item(212, true, "map "), -1, 1140, 504, -1L));
            addItemMap(new ItemMap(new Item(212, true, "map 1"), -1, 1486, 168, -1L));
            addItemMap(new ItemMap(new Item(212, true, "map 2"), -1, 398, 360, -1L));
            addItemMap(new ItemMap(new Item(212, true, "map 3"), -1, 703, 480, -1L));
            addItemMap(new ItemMap(new Item(212, true, "map 4"), -1, 968, 720, -1L));
        } else if (mapTemplate.mapTemplateId == 64) {
            addItemMap(new ItemMap(new Item(236, true, "map 5"), -1, 560, 312, -1L));
            addItemMap(new ItemMap(new Item(236, true, "map 6"), -1, 140, 336, -1L));
            addItemMap(new ItemMap(new Item(236, true, "map 7"), -1, 520, 504, -1L));
            addItemMap(new ItemMap(new Item(236, true, "map 8"), -1, 370, 168, -1L));
        } else if (mapTemplate.mapTemplateId == 42) {
            ItemMap itMap;
            itMap = new ItemMap(new Item(347, true, "map 9"), -1, 147, 480, -1L);
            addItemMap(itMap);
            its.add(itMap);
            itMap = new ItemMap(new Item(347, true, "map 10"), -1, 745, 480, -1L);
            addItemMap(itMap);
            its.add(itMap);
            itMap = new ItemMap(new Item(347, true, "map 11"), -1, 503, 528, -1L);
            addItemMap(itMap);
            its.add(itMap);
            itMap = new ItemMap(new Item(347, true, "map 12"), -1, 534, 240, -1L);
            addItemMap(itMap);
            its.add(itMap);
        }
        messages = new LinkedBlockingQueue<>();
        loadNpcPlayer();
        loadNpc();
        EventManager.getInstance().init(this);
        new Thread(() -> {
            Thread.currentThread().setName("MAP " + template.mapTemplateId + "-" + mapId);
            while (true) {
                try {
                    while (true) {
                        if (ServerController.turnOnExit) {
                            return;
                        }
                        try {
                            if (ServerController.isExit) {
                                messages.clear();
                                return;
                            }
                            if (messages.size() > 5000) {
                                messages.clear();
                                for (int i = players.size() - 1; i >= 0; --i) {
                                    NJUtil.sendDialog(players.get(i).getSession(), AlertFunction.SERVER_FULL(players.get(i).getSession()));
                                    players.get(i).getSession().disconnect("Map ham tao 0");
                                }
                                LOGGER.warn("Qua tai: {},{}", getTemplateId(), zoneId);
                                return;
                            }
                            NMessage msg = messages.poll(100L, TimeUnit.MILLISECONDS);
                            if (msg != null) {
                                processMessage(msg.conn, msg.message);
                            }
                        } catch (Exception e) {
                            LOGGER.error("", e);
                        }
                        try {
                            while (playerIns.size() > 0) {
                                goInMap(playerIns.remove(0));
                            }
                        } catch (Exception e) {
                            LOGGER.error("", e);
                        }
                        try {
                            while (playerOuts.size() > 0) {
                                goOutMap(playerOuts.remove(0));
                            }
                        } catch (Exception e) {
                            LOGGER.error("", e);
                        }
                        long timeMillis = System.currentTimeMillis();
                        if (timeMillis - lastTime > 500L) {
                            if (giatocchien != null && zoneId == 0 && getTemplateId() == 98) {
                                giatocchien.playGTChien();
                            }
                            if (players.size() > 0) {
                                updateNpc();
                            }
                            updateNpcPlayer();
                            updateBuNhin();
                            updateItemMap();
                            updatePlayer();
                            lastTime = timeMillis;
                            ++tick;
                            if (tick >= 2999) {
                                tick = 0;
                            }
                            if (isStop) {
                                Map mapNext;
                                Player p;
                                for (Player playerIn : playerIns) {
                                    p = playerIn;
                                    p.typePk = Player.PK_NORMAL;
                                    p.updateTypePk();
                                    mapNext = findMap(p, p.mapTemplateIdGo);
                                    if (mapNext == null) {
                                        mapNext = findMap(p, 22);
                                    }
                                    p.mapClear();
                                    p.x = mapNext.template.defaultX;
                                    p.y = mapNext.template.defaultY;
                                    mapNext.waitGoInMap(p);
                                }
                                for (Player playerOut : playerOuts) {
                                    p = playerOut;
                                    p.typePk = Player.PK_NORMAL;
                                    p.updateTypePk();
                                    mapNext = findMap(p, p.mapTemplateIdGo);
                                    if (mapNext == null) {
                                        mapNext = findMap(p, 22);
                                    }
                                    p.mapClear();
                                    p.x = mapNext.template.defaultX;
                                    p.y = mapNext.template.defaultY;
                                    mapNext.waitGoInMap(p);
                                }
                                for (Player player : players) {
                                    p = player;
                                    p.typePk = Player.PK_NORMAL;
                                    p.updateTypePk();
                                    mapNext = findMap(p, p.mapTemplateIdGo);
                                    if (mapNext == null) {
                                        mapNext = findMap(p, 22);
                                    }
                                    p.mapClear();
                                    p.x = mapNext.template.defaultX;
                                    p.y = mapNext.template.defaultY;
                                    mapNext.waitGoInMap(p);
                                }
                                return;
                            }
                        }
                        if (getTemplateId() == 130 || getTemplateId() == 131 || getTemplateId() == 132) {
                            if (getTemplateId() == 130 && timeEnd - System.currentTimeMillis() <= txh * 1000L) {
                                for (Npc npc : npcs) {
                                    if (npc.template.npcTemplateId == 144 && npc.status == Npc.STATUS_DIE) {
                                        npc.timeWait = npc.timeReturn + 1;
                                    }
                                }
                            }
                            if (timeEnd - System.currentTimeMillis() <= 0L) {
                                if (getTemplateId() == 130) {
                                    int mauA = 0;
                                    int mauB = 0;
                                    for (Npc npc : npcs) {
                                        if (npc.template.npcTemplateId == 142) {
                                            mauA = npc.hp / 10;
                                        } else if (npc.template.npcTemplateId == 143) {
                                            mauB = npc.hp / 10;
                                        }
                                    }
                                    if (mauA > mauB) {
                                        for (int m = 0; m < playerAs.size(); ++m) {
                                            Player pp = ServerController.hnPlayers.get(playerAs.get(m));
                                            if (pp != null && pp.map.getTemplateId() >= 130 && pp.map.getTemplateId() <= 132) {
                                                Item iia = new Item(476, true, "map 13");
                                                iia.quantity = 3;
                                                pp.addItemToBagNoDialog(iia);
                                                NJUtil.sendServer(pp.getSession(), "Kẹo trắng đã giành chiến thắng.");
                                            }
                                            pp = ServerController.hnPlayers.get(playerBs.get(m));
                                            if (pp != null) {
                                                Item iia = new Item(476, true, "map 14");
                                                iia.quantity = 1;
                                                pp.addItemToBagNoDialog(iia);
                                                NJUtil.sendServer(pp.getSession(), "Kẹo trắng đã giành chiến thắng.");
                                            }
                                        }
                                    } else if (mauB > mauA) {
                                        for (int m = 0; m < playerAs.size(); ++m) {
                                            Player pp = ServerController.hnPlayers.get(playerAs.get(m));
                                            if (pp != null && pp.map.getTemplateId() >= 130 && pp.map.getTemplateId() <= 132) {
                                                Item iia = new Item(476, true, "map 15");
                                                iia.quantity = 1;
                                                pp.addItemToBagNoDialog(iia);
                                                NJUtil.sendServer(pp.getSession(), "Kẹo đen đã giành chiến thắng.");
                                            }
                                            pp = ServerController.hnPlayers.get(playerBs.get(m));
                                            if (pp != null) {
                                                Item iia = new Item(476, true, "map 16");
                                                iia.quantity = 3;
                                                pp.addItemToBagNoDialog(iia);
                                                NJUtil.sendServer(pp.getSession(), "Kẹo đen đã giành chiến thắng.");
                                            }
                                        }
                                    }
                                }
                                while (playerIns.size() + playerOuts.size() + players.size() > 0) {
                                    for (int k = playerIns.size() - 1; k >= 0; --k) {
                                        playerIns.get(k).doChangeMap(27, true, "run ct keo");
                                    }
                                    for (int k = playerOuts.size() - 1; k >= 0; --k) {
                                        playerOuts.get(k).doChangeMap(27, true, "run ct keo 1");
                                    }
                                    for (int k = players.size() - 1; k >= 0; --k) {
                                        players.get(k).doChangeMap(27, true, "run ct keo 2");
                                    }
                                    NJUtil.sleep(10);
                                }
                                for (int k = 0; k < mapkeos.size(); ++k) {
                                    if (mapkeos.get(k).zoneId == zoneId && mapkeos.get(k).template.mapTemplateId == template.mapTemplateId) {
                                        ServerController.maps.remove(mapkeos.get(k));
                                        mapkeos.remove(mapkeos.get(k));
                                        break;
                                    }
                                }
                                return;
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("lỗi vòng lặp map", e);
                }
            }
        }).start();
    }

    public int getTemplateId() {
        return template.mapTemplateId;
    }

    public void notifyMap() {
        try {
            LOCK.notify();
        } catch (Exception e) {
        }
    }

    public void loadNpc() {
        String allNpc = NJUtil.readFileString("data/npc/" + getTemplateId() + ".txt");
        if (allNpc != null && !allNpc.isEmpty()) {
            try {
                String[] lines = allNpc.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue; // Bỏ qua dòng trống
                    }
                    String[] npcInfo = line.split(",");
                    int npcTemplateId = Integer.parseInt(npcInfo[0]);
                    int tileId = Integer.parseInt(npcInfo[1]);
                    int indexW = tileId % template.tmw;
                    int indexH = tileId / template.tmw;
                    Npc npc = new Npc(npcs.size(), npcTemplateId, this);
                    npc.pointx = (short) (indexW * template.size + template.size / 2);
                    npc.pointy = (short) (indexH * template.size + template.size);
                    npc.minX = npc.pointx - npc.template.rangeMove;
                    npc.maxX = npc.pointx + npc.template.rangeMove;
                    npc.minY = npc.pointy - npc.template.rangeMove;
                    npc.maxY = npc.pointy + npc.template.rangeMove;
                    npc.hp = npc.template.hp;
                    npc.level = npc.template.level;
                    npc.isTathu(this);
                    if (npc.template.npcTemplateId == 98 || npc.template.npcTemplateId == 99) {
                        npcTrus.add(npc);
                    }
                    if (npc.template.npcTemplateId == 69 || npc.template.npcTemplateId == 71) {
                        npc.levelBoss = 2;
                    }
                    if (npc.template.isBossId() && (npc.template.npcTemplateId < 161 || npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) && !template.isMapChienTruong()) {
                        npc.exactly = 3000;
                        npc.status = Npc.STATUS_DIE;
                    }
                    if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId < 168) {
                        npc.timeReturn = 15000;
                    }
                    if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId <= 167) {
                        npc.exactly = 3000;
                    }
                    if (npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) {
                        npc.timeReturn = 600;
                    }
                    if (npc.template.npcTemplateId == 165) {
                        npc.status = Npc.STATUS_DIE;
                    }
                    if (npc.template.npcTemplateId >= 117 && npc.template.npcTemplateId <= 121) {
                        npc.exactly = 500;
                    }
                    if (npc.template.npcTemplateId == 202) {
                        npc.status = Npc.STATUS_DIE;
                        npcChild.add(npc);
                    }
                    if (npc.template.npcTemplateId == 201) {
                        npc.exactly = 3000;
                        npc.status = Npc.STATUS_DIE;
                    }
                    if (npc.template.npcTemplateId == 203) {
                        npc.exactly = 3000;
                        npc.status = Npc.STATUS_DIE;
                    }
                    if (npc.template.npcTemplateId == 204) {
                        npc.exactly = 3000;
                        npc.status = Npc.STATUS_DIE;
                    }
                    if (npc.template.npcTemplateId != 166 && npc.template.npcTemplateId != 167) {
                        npcs.add(npc);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Load NPC map " + getTemplateId(), e);
            }
        }
    }

    public void loadNpc1() {
        try {
            for (Npc npc : npcs) {
                if (npc.status == Npc.STATUS_DIE) {
                    npc.timeWait = npc.timeReturn + 5;
                } else {
                    npc.hp = npc.template.hp * npc.level;
                }
            }
        } catch (Exception e) {
        }
    }

    public void loadNpcPlayer() {
        String allNpc = NJUtil.readFileString("data/npcPlayer/" + getTemplateId() + ".txt");
        if (allNpc != null && !allNpc.isEmpty()) {
            try {
                String[] lines = allNpc.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (line.isEmpty()) {
                        continue; // Bỏ qua dòng trống
                    }
                    String[] npcInfo = line.split(",");
                    int playerTemplateId = Integer.parseInt(npcInfo[0]);
                    int tileId = Integer.parseInt(npcInfo[1]);
                    if ((getTemplateId() == 131 || getTemplateId() == 132) && playerTemplateId == 25) {
                        playerTemplateId = 32;
                    }
                    if (playerTemplateId < ServerController.playerTemplates.size()) {
                        int indexW = tileId % template.tmw;
                        int indexH = tileId / template.tmw;
                        short x = (short) (indexW * template.size + template.size / 2);
                        short y = (short) (indexH * template.size + template.size);
                        NpcPlayer npcPlayer = new NpcPlayer(npcPlayers.size(), playerTemplateId);
                        npcPlayer.x = x;
                        npcPlayer.pointx = x;
                        npcPlayer.y = y;
                        npcPlayer.pointy = y;
                        npcPlayer.status = NpcPlayer.A_STAND;
                        if (npcPlayer.template.playerTemplateId == 31 || npcPlayer.template.playerTemplateId == 34) {
                            npcPlayer.status = NpcPlayer.A_HIDE;
                        }
                        if (template.mapTemplateId == 0 || template.mapTemplateId == 56 || template.mapTemplateId == 73) {
                            npcPlayer.status = NpcPlayer.A_HIDE;
                            PlayerTemplate pTemplate = ServerController.playerTemplates.get(playerTemplateId);
                            Player p = new Player(pTemplate.getName(null), 1, pTemplate.headId);
                            p.map = this;
                            p.x = x;
                            p.y = y;
                            p.typePk = Player.PK_DOSAT;
                            p.hp = 1000;
                            p.level = 50;
                            p.resFire = 600;
                            p.resIce = 600;
                            p.resWind = 600;
                            p.hp_full = 1000;
                            p.dame_full = 350;
                            p.playerId = -1 * playerTemplateId - 1;
                            p.missAll = 10;
                            p.fatal = 150;
                            p.skills.clear();
                            if (template.mapTemplateId == 73) {
                                p.headId = 44;
                                p.bodyId = 45;
                                p.legId = 46;
                                p.weaponId = -1;
                                p.skills.add(ServerController.skills.get(11).cloneSkill());
                                p.skills.add(ServerController.skills.get(2).cloneSkill());
                                p.skills.add(ServerController.skills.get(19).cloneSkill());
                                p.skills.add(ServerController.skills.get(36).cloneSkill());
                                p.skills.add(ServerController.skills.get(81).cloneSkill());
                                p.skills.add(ServerController.skills.get(98).cloneSkill());
                                p.skills.add(ServerController.skills.get(115).cloneSkill());
                            } else if (template.mapTemplateId == 56) {
                                p.headId = 53;
                                p.bodyId = 54;
                                p.legId = 55;
                                p.weaponId = -1;
                                p.skills.add(ServerController.skills.get(11).cloneSkill());
                                p.skills.add(ServerController.skills.get(160).cloneSkill());
                                p.skills.add(ServerController.skills.get(177).cloneSkill());
                                p.skills.add(ServerController.skills.get(194).cloneSkill());
                                p.skills.add(ServerController.skills.get(239).cloneSkill());
                                p.skills.add(ServerController.skills.get(256).cloneSkill());
                                p.skills.add(ServerController.skills.get(273).cloneSkill());
                            } else if (template.mapTemplateId == 0) {
                                p.headId = 65;
                                p.bodyId = 66;
                                p.legId = 67;
                                p.weaponId = -1;
                                p.skills.add(ServerController.skills.get(11).cloneSkill());
                                p.skills.add(ServerController.skills.get(397).cloneSkill());
                                p.skills.add(ServerController.skills.get(426).cloneSkill());
                                p.skills.add(ServerController.skills.get(352).cloneSkill());
                                p.skills.add(ServerController.skills.get(318).cloneSkill());
                                p.skills.add(ServerController.skills.get(335).cloneSkill());
                                p.skills.add(ServerController.skills.get(352).cloneSkill());
                            }
                            players.add(p);
                        }
                        npcPlayers.add(npcPlayer);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("Load NPC map", e);
            }
        }
    }

    public void updateItemMap() {
        try {
            for (int i = itemMaps.size() - 1; i >= 0; --i) {
                itemMaps.get(i).update(this);
            }
            for (ItemMap it : its) {
                if (it.timeReturn > 1) {
                    --it.timeReturn;
                }
                if (!itemMaps.contains(it) && it.timeReturn == 1) {
                    it.timeReturn = 0;
                    itemMaps.add(it);
                    sendAddItemMap(it);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void updateNpc() {
        try {
            isMapBoss = false;
            for (int i = 0; i < bossTrumMapIds.length; ++i) {
                if (template.mapTemplateId == bossTrumMapIds[i] && zoneId == bossTrumZoneIds[i]) {
                    isMapBoss = true;
                    break;
                }
            }
            isHaveBoss = false;
            for (int i = npcs.size() - 1; i >= 0; --i) {
                if (template.mapTemplateId == 130) {
                    npcs.get(i).timeReturn = 600;
                }
                if (template.isMapChienTruong() && !npcs.get(i).template.isBossId()) {
                    npcs.get(i).timeReturn = 600;
                }
                if ((npcs.get(i).template.npcTemplateId == 98 || npcs.get(i).template.npcTemplateId == 99) && !npcs.get(i).template.isBossId()) {
                    npcs.get(i).timeReturn = 10000;
                }
                npcs.get(i).update(this, -1);
                if (npcs.get(i).template.isBossId() && npcs.get(i).status == Npc.STATUS_NORMAL) {
                    isHaveBoss = true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void updateNpcPlayer() {
        try {
            if (isLang()) {
                for (int i = 0; i < longDenZoneIds.length; ++i) {
                    if (zoneId == longDenZoneIds[i]) {
                        longDenZoneIds[i] = -1;
                        Message message;
                        for (int j = npcPlayers.size() - 1; j >= 0; --j) {
                            NpcPlayer npcPlayer = npcPlayers.get(j);
                            if (npcPlayer.status == NpcPlayer.A_HIDE) {
                                npcPlayer.status = NpcPlayer.A_STAND;
                                message = NJUtil.messageSubCommand(Cmd.NPC_PLAYER_UPDATE);
                                message.writeByte(npcPlayer.npcPlayerId);
                                message.writeByte(npcPlayer.status);
                                sendToPlayer(message);
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void updateBuNhin() {
        try {
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < buNhins.size(); i++) {
                buNhins.get(i).update(this);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void updateInfo(Player player) {
        try {
            if (player.playerId < 0) {
                return;
            }
            Message message = new Message(Cmd.MAP_INFO);
            message.writeByte(template.mapTemplateId);
            message.writeByte(template.tileId);
            message.writeByte(template.bgId);
            message.writeByte(template.typeMap);
            message.writeUTF(template.getName(player.getSession()));
            message.writeByte(zoneId);
            message.writeShort(player.x);
            message.writeShort(player.y);
            message.writeByte(template.links.size());
            for (int i = 0; i < template.links.size(); ++i) {
                NLink nLink = template.links.get(i);
                if (template.mapTemplateId == nLink.mapTemplateId1) {
                    message.writeShort(nLink.minX1);
                    message.writeShort(nLink.minY1);
                    message.writeShort(nLink.maxX1);
                    message.writeShort(nLink.maxY1);
                } else if (template.mapTemplateId == nLink.mapTemplateId2) {
                    message.writeShort(nLink.minX2);
                    message.writeShort(nLink.minY2);
                    message.writeShort(nLink.maxX2);
                    message.writeShort(nLink.maxY2);
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            int size = 0;
            for (int j = 0; j < npcs.size(); ++j) {
                Npc mob = npcs.elementAt(j);
                if (!mob.isMobEvent()) {
                    if (isMapTrain()) {
                        mob.checkHoiSinh();
                    }
                    dos.writeBoolean(mob.timeDisable > 0);
                    dos.writeBoolean(mob.timeDontMove > 0);
                    dos.writeBoolean(mob.timeFire > 0);
                    dos.writeBoolean(mob.timeIce > 0);
                    dos.writeBoolean(mob.timeWind > 0);
                    dos.writeByte(mob.template.npcTemplateId);
                    dos.writeByte(mob.sys);
                    if ((mob.isTinhAnh() || mob.isThuLinh()) && !mob.isHit(player)) {
                        dos.writeInt(mob.template.hp);
                    } else {
                        dos.writeInt(mob.hp);
                    }
                    dos.writeByte(mob.level);
                    if ((mob.isTinhAnh() || mob.levelBoss == 2) && !mob.isHit(player)) {
                        dos.writeInt(mob.template.hp);
                    } else {
                        dos.writeInt(mob.maxhp);
                    }
                    dos.writeShort(mob.pointx);
                    dos.writeShort(mob.pointy);
                    dos.writeByte(mob.status);
                    if ((mob.isTinhAnh() || mob.levelBoss == 2) && !mob.isHit(player)) {
                        dos.writeByte(0);
                    } else {
                        dos.writeByte(mob.levelBoss);
                    }
                    dos.writeBoolean(mob.template.isBossId());
                    ++size;
                }
            }
            message.writeByte(size);
            message.write(baos.toByteArray());
            try {
                dos.close();
                baos.close();
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            message.writeByte(buNhins.size());
            for (BuNhin buNhin : buNhins) {
                message.writeUTF(buNhin.player.name);
                message.writeShort(buNhin.x);
                message.writeShort(buNhin.y);
            }
            message.writeByte(npcPlayers.size());
            for (NpcPlayer npcPlayer : npcPlayers) {
                if (npcPlayer.template.playerTemplateId == 17) {
                    if (player.taskMain != null && player.taskMain.template.taskId == 17 && player.taskMain.index >= 0 && player.taskMain.index <= 1) {
                        message.writeByte(1);
                    } else {
                        message.writeByte(NpcPlayer.A_HIDE);
                    }
                } else if (npcPlayer.template.playerTemplateId == 34) {
                    if (EventManager.getInstance().checkVisibiliy(player, npcPlayer)) {
                        message.writeByte(npcPlayer.status);
                    } else {
                        message.writeByte(NpcPlayer.A_HIDE);
                    }
                } else {
                    message.writeByte(npcPlayer.status);
                }
                message.writeShort(npcPlayer.x);
                message.writeShort(npcPlayer.y);
                message.writeByte(npcPlayer.template.playerTemplateId);
            }
            message.writeByte(itemMaps.size());
            for (ItemMap itemMap : itemMaps) {
                message.writeShort(itemMap.itemMapId);
                message.writeShort(itemMap.item.getTemplateId());
                message.writeShort(itemMap.x);
                message.writeShort(itemMap.y);
            }
            if (giatocchien != null) {
                if (template.mapTemplateId == 120) {
                    message.writeUTF(AlertFunction.CLAN_NAME(player.getSession()) + giatocchien.nameGt1.name);
                } else if (template.mapTemplateId == 124) {
                    message.writeUTF(AlertFunction.CLAN_NAME(player.getSession()) + giatocchien.nameGt2.name);
                } else {
                    message.writeUTF(template.getName(player.getSession()));
                }
            } else {
                message.writeUTF(template.getName(player.getSession()));
            }
            if (template.allIndexStand != null && template.allIndexStand.length > 0) {
                message.writeByte(template.allIndexStand[0]);
                for (int j = 1; j < template.allIndexStand.length; ++j) {
                    message.writeByte(template.allIndexStand[j]);
                }
            } else {
                message.writeByte(0);
            }
            NJUtil.sendMessage(player.getSession(), message);
            for (int j = 0; j < npcs.size(); ++j) {
                Npc mob = npcs.elementAt(j);
                if (mob.isMobEvent() && EventManager.getInstance().checkVisibility(player, mob)) {
                    addDynamicMod(player, mob);
                }
            }
            EventManager.getInstance().handleMapPK(player, this);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        try {
            Message message = new Message(Cmd.TREE_MAP);
            message.writeByte(template.listImageInMap.size());
            for (short idItem : template.listImageInMap.values()) {
                message.writeShort(idItem);
                if (player.getSession().typeHD == 1) {
                    if (ServerController.imageTreex1[idItem] == null) {
                        ServerController.loadImageTree(idItem, player.getSession().typeHD);
                    }
                    message.writeBytes(ServerController.imageTreex1[idItem]);
                } else if (player.getSession().typeHD == 2) {
                    if (ServerController.imageTreex2[idItem] == null) {
                        ServerController.loadImageTree(idItem, player.getSession().typeHD);
                    }
                    message.writeBytes(ServerController.imageTreex2[idItem]);
                } else if (player.getSession().typeHD == 3) {
                    if (ServerController.imageTreex3[idItem] == null) {
                        ServerController.loadImageTree(idItem, player.getSession().typeHD);
                    }
                    message.writeBytes(ServerController.imageTreex3[idItem]);
                } else {
                    if (player.getSession().typeHD != 4) {
                        continue;
                    }
                    if (ServerController.imageTreex4[idItem] == null) {
                        ServerController.loadImageTree(idItem, player.getSession().typeHD);
                    }
                    message.writeBytes(ServerController.imageTreex4[idItem]);
                }
            }
            message.writeByte(template.itemBehind.size());
            for (int i = 0; i < template.itemBehind.size(); ++i) {
                InfoItemMap info = template.itemBehind.get(i);
                message.writeByte(info.idItem);
                message.writeByte(info.x);
                message.writeByte(info.y);
            }
            message.writeByte(template.itemMiddle.size());
            for (int i = 0; i < template.itemMiddle.size(); ++i) {
                InfoItemMap info = template.itemMiddle.get(i);
                message.writeByte(info.idItem);
                message.writeByte(info.x);
                message.writeByte(info.y);
            }
            message.writeByte(template.itemTop.size());
            for (int i = 0; i < template.itemTop.size(); ++i) {
                InfoItemMap info = template.itemTop.get(i);
                message.writeByte(info.idItem);
                message.writeByte(info.x);
                message.writeByte(info.y);
            }
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void updatePlayer() {
        try {
            long timeNow = System.currentTimeMillis();
            if (saveId == mapId) {
                ++saveId;
                if (saveId >= ServerController.maps.size()) {
                    saveId = 0;
                }
            }
            int size = players.size();
            for (int i = size - 1; i >= 0; --i) {
                Player player = players.get(i);
                if (countPlayerClone(player.playerId) >= 2) {
                    player.getSession().disconnect("Map.updatePlayer map 0");
                }
            }
            size = players.size();
            if (size == maxPlayerKeo && getTemplateId() == 133) {
                if (isMapKeo) {
                    isMapKeo = false;
                    Map map130 = new Map(ServerController.maps.size(), keoZoneID, ServerController.mapTemplates.get(130));
                    map130.timeEnd = System.currentTimeMillis() + tt * 1000L;
                    mapkeos.add(map130);
                    ServerController.maps.add(map130);
                    Map map131 = new Map(ServerController.maps.size(), keoZoneID, ServerController.mapTemplates.get(131));
                    map131.timeEnd = System.currentTimeMillis() + tt * 1000L;
                    mapkeos.add(map131);
                    ServerController.maps.add(map131);
                    Map map132 = new Map(ServerController.maps.size(), keoZoneID, ServerController.mapTemplates.get(132));
                    map132.timeEnd = System.currentTimeMillis() + tt * 1000L;
                    mapkeos.add(map132);
                    ServerController.maps.add(map132);
                    Vector<String> playerAs = new Vector<>();
                    map132.playerAs = playerAs;
                    map131.playerAs = playerAs;
                    map130.playerAs = playerAs;
                    Vector<String> playerBs = new Vector<>();
                    map132.playerBs = playerBs;
                    map131.playerBs = playerBs;
                    map130.playerBs = playerBs;
                    for (int i = size - 1; i >= 0; --i) {
                        --players.get(i).countctkeo;
                        map131.playerAs.add(players.get(i).name);
                        players.get(i).sendMapTime((int) ((map131.timeEnd - System.currentTimeMillis()) / 1000L));
                        players.get(i).doChangeMap1(131, keoZoneID, "map updateplayer 131");
                        --i;
                        --players.get(i).countctkeo;
                        map132.playerBs.add(players.get(i).name);
                        players.get(i).sendMapTime((int) ((map132.timeEnd - System.currentTimeMillis()) / 1000L));
                        players.get(i).doChangeMap1(132, keoZoneID, "map updateplayer 132");
                    }
                    Vector<String> playerAs2 = map130.playerAs;
                    map132.playerAs = playerAs2;
                    map131.playerAs = playerAs2;
                    Vector<String> playerBs2 = map130.playerBs;
                    map132.playerBs = playerBs2;
                    map131.playerBs = playerBs2;
                    ++keoZoneID;
                    return;
                }
                isMapKeo = true;
            } else {
                isMapKeo = false;
            }
            for (int i = 0; i < size; ++i) {
                try {
                    Player player = players.get(i);
                    if (player.playerId < 0 && !player.isNhanban()) {
                        if (player.headId == 164 && NJUtil.probability(1, 100) == 0) {
                            Message m = new Message(Cmd.CHAT_MAP);
                            m.writeInt(player.playerId);
                            m.writeUTF("Y ...a... y... a");
                            sendToPlayer(m);
                        }
                        if (player.timeUpdate == 0) {
                            player.timeUpdate = 4;
                            if (getTemplateId() == 33 && player.playerId < 0 && player.headId == 96) {
                                int dx = NJUtil.distance(player.x, player.owner.x);
                                int dy = NJUtil.distance(player.x, player.owner.x);
                                if (dx > 400 || dy > 400 || player.map.mapId != player.owner.map.mapId) {
                                    player.sendTaskFollowFail(player.owner, 1);
                                    playerOuts.add(player);
                                } else if (player.x < 48) {
                                    player.owner.doTaskNext();
                                    playerOuts.add(player);
                                } else {
                                    player.x -= (short) (20 + NJUtil.randomNumber(30));
                                    if (player.x <= 26) {
                                        player.x = 26;
                                    }
                                    player.sendMove();
                                }
                            }
                        } else {
                            --player.timeUpdate;
                        }
                    } else if (!player.map.equals(this)) {
                        playerOuts.add(player);
                    } else if (player.isNhanban() && player.checkTimeOutNhanban()) {
                        playerOuts.add(player);
                    } else {
                        player.update();
                        int itt = player.countItemBag(458);
                        if (itt > 0 && tick % 2 == 0 && !player.isControlCharNhanBan()) {
                            if (itt > 7) {
                                itt = 7;
                            }
                            int[] ts = {0, 100, 200, 400, 800, 1600, 3200, 6400};
                            int dd = ts[itt];
                            if (dd >= player.getHp() - 1) {
                                dd = player.getHp() - 1;
                            }
                            player.updateFastHp(-dd);
                        }
                        player.showAlert();
                        if (player.itemMapPick != null) {
                            if (player.timeNhat > 1) {
                                --player.timeNhat;
                            } else if (player.timeNhat == 1) {
                                player.timeNhat = 0;
                                if (itemMaps.contains(player.itemMapPick)) {
                                    if (!player.isControlCharNhanBan() && player.taskMain != null && player.getTaskFinish() == 34 && player.taskMain.index == 1) {
                                        player.checkTaskIndex();
                                    }
                                    player.doPickItemMap(player.itemMapPick);
                                }
                                player.itemMapPick = null;
                                player.endWait(null);
                            }
                        }
                        if (player.getHp() <= 0 && player.status != Player.ME_DIE) {
                            player.checkDie(-1);
                        }
                        if (player.status == Player.ME_DIE && isHaveBoss) {
                            ++player.countDie;
                            if (player.countDie > 100) {
                                player.doBack("map updateplayer");
                            }
                        } else {
                            player.countDie = 0;
                        }
                        player.checkNap();
                        try {
                            if (player.taskMain != null && player.taskMain.template != null) {
                                if (player.taskMain.template.taskId == 40 && player.taskMain.index == 2 && player.level >= 65) {
                                    player.doTaskNext();
                                } else if (player.taskMain.template.taskId == 41 && player.taskMain.index == 1 && player.level >= 67) {
                                    player.doTaskNext();
                                    player.doTaskNext();
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (player.getPlayerMainControl().timeMoveUp > 0) {
                            --player.getPlayerMainControl().timeMoveUp;
                            if (player.getPlayerMainControl().timeMoveUp == 1) {
                                player.getPlayerMainControl().timeMoveUp = 0;
                                player.resetPoint1(1000);
                                continue;
                            }
                        }
                        if (player.isCheckTile()) {
                            if (!player.isControlCharNhanBan() && (template.mapTemplateId == 130 || template.mapTemplateId == 131 || template.mapTemplateId == 132)) {
                                if (player.getTypePk() != Player.PK_PHE1 && player.getTypePk() != Player.PK_PHE2) {
                                    if (template.mapTemplateId == 131) {
                                        player.typePk = Player.PK_PHE1;
                                    }
                                    if (template.mapTemplateId == 132) {
                                        player.typePk = Player.PK_PHE2;
                                    }
                                    player.updateTypePk();
                                }
                            } else if (!player.isControlCharNhanBan() && !template.isMapChienTruong() && getTemplateId() != 110 && getTemplateId() != 111 && (player.getTypePk() == Player.PK_PHE1 || player.getTypePk() == Player.PK_PHE2)) {
                                player.typePk = Player.PK_NORMAL;
                                player.updateTypePk();
                            } else {
                                if (template.isMapChienTruong() && player.getTypePk() != Player.PK_PHE1 && player.getTypePk() != Player.PK_PHE2 && player.getTypePk() != 6) {
                                    player.getSession().disconnect("Map.updatePlayer map 1");
                                    continue;
                                }
                                if ((getTemplateId() == 110 || getTemplateId() == 111) && player.getTypePk() != Player.PK_NORMAL && player.getTypePk() != Player.PK_PHE1 && player.getTypePk() != Player.PK_PHE2) {
                                    player.typePk = Player.PK_NORMAL;
                                    player.updateTypePk();
                                }
                            }
                            if (getTemplateId() == 111) {
                                if (phe1 != null && phe1.contains(player) && player.getTypePk() != Player.PK_PHE1) {
                                    player.typePk = Player.PK_PHE1;
                                    player.updateTypePk();
                                }
                                if (phe2 != null && phe2.contains(player) && player.getTypePk() != Player.PK_PHE2) {
                                    player.typePk = Player.PK_PHE2;
                                    player.updateTypePk();
                                }
                            }
                            LocalDate dateNow = LocalDate.now();
                            LocalDate dLogin = NJUtil.getDateByMilis(player.login_date);
                            if (!player.isControlCharNhanBan() && !player.isNhanban() && isTongKet && template.isMapChienTruong() && template.mapTemplateId != 98 && template.mapTemplateId != 104) {
                                player.backHomeChienTruong();
                            }
                            if (dateNow.getDayOfMonth() != dLogin.getDayOfMonth() || dateNow.getMonthValue() != dLogin.getMonthValue() || dateNow.getYear() != dLogin.getYear()) {
                                if (dateNow.getDayOfMonth() == 1) {
                                    ServerController.topTaiNang.clear();
                                    for (int mm = 0; mm < ServerController.topTaiNangClass.size(); ++mm) {
                                        ServerController.topTaiNangClass.get(mm).clear();
                                    }
                                }
                                player.login_date = timeNow;
                                player.pointUyDanh -= 1 + player.pointUyDanh / 50;
                                if (player.pointUyDanh <= 0) {
                                    player.pointUyDanh = 0;
                                }
                                player.countFinishDay = 0;
                                player.timeOnline = 0;
                                player.countLoopDay = Player.maxLoopDay;
                                player.pointOpenTui = Player.maxOpenTui;
                                player.countctkeo = Player.maxCTKeo;
                                player.countNvbian = Player.maxNVBA;
                                player.countChatAdmin = Player.maxChatAdm;
                                player.countPB = Player.maxPhoBan;
                                player.countUseTTL = Player.defaultCountTTL;
                                player.countUseHDL = Player.defaultCountHDL;
                                player.pointPB = 0;
                                player.timePB = 0;
                                player.friendPB = 0;
                                player.countLoopBoos += 2;
                                if (player.countLoopBoos > Player.maxLoopBoss) {
                                    player.countLoopBoos = Player.maxLoopBoss;
                                }
                                player.checkThuCuoi(1);
                                player.loadThuCuoi(1);
                            }
                            if (NJUtil.isNewWeek(dateNow, dLogin)) {
                                if (player.isChangeCoin) {
                                    player.isChangeCoin = false;
                                }
                                player.pointClanWeek = 0;
                            }
                            if (template.isMapChienTruong() || !player.isAutoSave) {
                                player.isAutoSave = false;
                            }
                            if ((player.getSession() == null || ServerController.huPlayers.get(player.userId) == null) && !player.isNhanban()) {
                                playerOuts.add(player);
                            } else if (timeNow - player.timeActive >= 1000000L && !player.isControlCharNhanBan()) {
                                if (player.playerId >= 0) {
                                    player.getSession().disconnect("Map.updatePlayer map 2");
                                }
                            } else {
                                if (player.timeReturnMap > 1) {
                                    --player.timeReturnMap;
                                }
                                if (getTemplateId() != 110 && player.testSkillDun != null && timeNow - player.testSkillDun.timeEnd > 0L && !player.isControlCharNhanBan()) {
                                    player.testSkillDun = null;
                                }
                                if (getTemplateId() != 117 && player.testGTDun != null && timeNow - player.testGTDun.timeEnd > 0L && !player.isControlCharNhanBan()) {
                                    player.testGTDun = null;
                                }
                                if (player.testSkill != null && timeNow - player.testSkill.timeEnd > 0L) {
                                    Player p2 = player.testSkill.player1;
                                    Player p3 = player.testSkill.player2;
                                    if (p2 != null && p3 != null) {
                                        p2.testSkill = null;
                                        p3.testSkill = null;
                                        p2.sendTestEndSkill(p2.playerId, p3.playerId);
                                    } else {
                                        player.testSkill = null;
                                    }
                                }
                                if (player.killPlayer != null && timeNow - player.killPlayer.timeEnd > 0L && !player.isControlCharNhanBan()) {
                                    Message message = new Message(Cmd.CLEAR_CUU_SAT);
                                    NJUtil.sendMessage(player.getSession(), message);
                                    message = new Message(Cmd.CLEAR_CUU_SAT);
                                    message.writeInt(player.playerId);
                                    NJUtil.sendMessage(player.killPlayer.player.getSession(), message);
                                    player.killPlayer = null;
                                }
                                if (player.inviteClan != null) {
                                    if (player.inviteClan.timeInvite > 0) {
                                        --player.inviteClan.timeInvite;
                                    } else {
                                        player.inviteClan = null;
                                    }
                                }
                                if (player.pleaseClan != null) {
                                    if (player.pleaseClan.timeInvite > 0) {
                                        --player.pleaseClan.timeInvite;
                                    } else {
                                        player.pleaseClan = null;
                                    }
                                }
                                if ((player.tradeSet != null && timeNow - player.tradeSet.timeStart > 30000L) || player.isControlCharNhanBan()) {
                                    player.tradeSet = null;
                                }
                                if ((player.tradeGet != null && timeNow - player.tradeGet.timeStart > 30000L) || player.isControlCharNhanBan()) {
                                    player.tradeGet = null;
                                }
                                if (player.party != null && player.party.isTeamLeader(player)) {
                                    for (int l = 0; l < player.party.times.length; ++l) {
                                        if (player.party.times[l] > 0) {
                                            byte[] times = player.party.times;
                                            --times[l];
                                            if (player.party.times[l] == 0) {
                                                player.party.ids[l] = -1;
                                                player.party.times[l] = 0;
                                            }
                                        }
                                        if (player.party.timePleases[l] > 0) {
                                            byte[] timePleases = player.party.timePleases;
                                            --timePleases[l];
                                            if (player.party.timePleases[l] == 0) {
                                                player.party.idPleases[l] = -1;
                                                player.party.timePleases[l] = 0;
                                            }
                                        }
                                    }
                                }
                                if (player.sumonHide != null && player.sumonHide.attNpcs != null && tick % 2 == 0) {
                                    for (int l = player.sumonHide.attNpcs.size() - 1; l >= 0; --l) {
                                        Npc npc = player.sumonHide.attNpcs.get(l);
                                        int ndx = NJUtil.distance(player.x, npc.pointx);
                                        int ndy = NJUtil.distance(player.y, npc.pointy);
                                        if (npc.status != Npc.STATUS_DIE) {
                                            if (ndx < player.sumonHide.rangeX && ndy < player.sumonHide.rangeY) {
                                                Message message = NJUtil.messageSubCommand(Cmd.CALL_EFFECT_NPC);
                                                message.writeByte(npc.npcId);
                                                player.sendToPlayer(message, true);
                                                player.isHit(npc, player.sumonHide.dame - player.sumonHide.dame * NJUtil.randomNumber(11) / 100, false, true, 0, 0, 0);
                                            } else if (ndx >= player.sumonHide.rangeX * 3 && ndy >= player.sumonHide.rangeY * 3) {
                                                player.sumonHide.attNpcs.remove(l);
                                            }
                                        } else {
                                            player.sumonHide.attNpcs.remove(l);
                                        }
                                    }
                                }
                                if (player.status != Player.ME_DIE && player.getPlayerMainControl().sumon != null && player.getPlayerMainControl().sumon.npcAttId >= 0 && player.getPlayerMainControl().sumon.npcAttId < npcs.size() && player.getPlayerMainControl().sumon.isSumonAttack()) {
                                    if (player.getPlayerMainControl().sumon.timeAtt <= 0) {
                                        Npc npc = npcs.get(player.getPlayerMainControl().sumon.npcAttId);
                                        int ndx2 = NJUtil.distance(player.x, npc.pointx);
                                        int ndy2 = NJUtil.distance(player.y, npc.pointy);
                                        if (npc.status != Npc.STATUS_DIE && ndx2 < player.getPlayerMainControl().sumon.rangeX && ndy2 < player.getPlayerMainControl().sumon.rangeY) {
                                            player.getPlayerMainControl().sumon.timeAtt = player.getPlayerMainControl().sumon.template.waitAttack;
                                            Message message = new Message(Cmd.SUMON_ATTACK);
                                            message.writeInt(player.playerId);
                                            message.writeByte(npc.npcId);
                                            message.writeShort(player.getPlayerMainControl().sumon.getIdSkillAttack());
                                            message.writeByte(player.getPlayerMainControl().sumon.getIndexFrameAttack());
                                            message.writeByte(player.getPlayerMainControl().sumon.getTypeAttack());
                                            message.writeByte(0);
                                            message.writeInt(-1);
                                            sendToPlayer(message);
                                            player.isHit(npc, player.getPlayerMainControl().sumon.dame - player.getPlayerMainControl().sumon.dame * NJUtil.randomNumber(11) / 100, false, true, 0, 0, 0);
                                        } else if (npc.status == Npc.STATUS_DIE || ndx2 < player.getPlayerMainControl().sumon.rangeX * 2 || ndy2 < player.getPlayerMainControl().sumon.rangeY * 2) {
                                            player.getPlayerMainControl().sumon.npcAttId = -1;
                                        }
                                    } else {
                                        --player.getPlayerMainControl().sumon.timeAtt;
                                    }
                                }
                                if (tick % 10 == 0 && player.status == Player.ME_NORMAL) {
                                    player.getPlayerMainControl().hp += player.getPlayerMainControl().getEff5BuffHp();
                                    player.getPlayerMainControl().mp += player.getPlayerMainControl().getEff5BuffMp();
                                }
                                if (player.getSession().clientType == GameServer.CLIENT_JAVA) {
                                    if (player.countHackXa > 5) {
                                        continue;
                                    }
                                    if (player.countHackMove > 5) {
                                        continue;
                                    }
                                }
                                if (tick % 5 == 0) {
                                    player.countHackMove = 0;
                                }
                                player.getPlayerMainControl().addMaxHp = 0;
                                if (player.getPlayerMainControl().effects.size() > 0) {
                                    for (int l = 0; l < player.getPlayerMainControl().effects.size(); ++l) {
                                        Effect eff = player.getPlayerMainControl().effects.get(l);
                                        if (player.status == Player.ME_NORMAL && (eff.template.type == 0 || eff.template.type == 12)) {
                                            player.getPlayerMainControl().hp += eff.param;
                                            player.getPlayerMainControl().mp += eff.param;
                                            if (player.getHp() >= player.getFullHp()) {
                                                player.getPlayerMainControl().hp = player.getFullHp();
                                            }
                                            if (player.getMp() >= player.getFullMp()) {
                                                player.getPlayerMainControl().mp = player.getFullMp();
                                            }
                                        } else if (player.getPlayerMainControl().status == Player.ME_NORMAL && (eff.template.type == 4 || eff.template.type == 17 || eff.template.type == 1)) {
                                            if (eff.template.type == 1) {
                                                player.getPlayerMainControl().hp += player.getPlayerMainControl().autoUpHp;
                                            } else {
                                                player.getPlayerMainControl().hp += eff.param;
                                            }
                                            if (player.getHp() >= player.getFullHp()) {
                                                player.getPlayerMainControl().hp = player.getFullHp();
                                            }
                                        } else if (player.status == Player.ME_NORMAL && eff.template.type == 13) {
                                            player.getPlayerMainControl().hp -= player.getFullHp() * 3 / 100;
                                            player.checkDie(-1);
                                            if (player.getPlayerMainControl().status == Player.ME_DIE) {
                                                eff.timeLength = 0;
                                            }
                                        } else if (eff.template.type == 24) {
                                            player.getPlayerMainControl().addMaxHp = eff.param;
                                        }
                                        long timeCheck = eff.timeStart * 1000L + eff.timeLength;
                                        if (eff.timeStart != -1 && timeNow > timeCheck) {
                                            player.removeEffect(eff, true);
                                        }
                                    }
                                }
                                if (player.timeWait == 1) {
                                    boolean isCloseWait = false;
                                    if (player.status != Player.ME_DIE) {
                                        if (!player.isControlCharNhanBan() && player.taskMain != null && player.itemWait != null && player.itemWait.getTemplateId() == 219) {
                                            if ((player.taskMain.template.taskId == 19 || player.taskMain.template.taskId == 35) && player.taskMain.index == 1) {
                                                player.sendUseItemUpToUp(player.itemWait.indexUI, 1);
                                                player.useItemUpToUp(player.itemWait);
                                                Item it2 = player.findItemBag(220);
                                                if (it2 != null) {
                                                    ++it2.quantity;
                                                    int quantity = player.countItemBag(220);
                                                    player.taskMain.count = (short) quantity;
                                                    //Database.updateItem(it2);
                                                    player.sendUpdateItemBag(it2.indexUI, 1);
                                                    if (quantity == player.taskMain.template.counts[player.taskMain.index]) {
                                                        player.doTaskNext();
                                                    } else {
                                                        player.doTaskUpdate(player.taskMain.count);
                                                    }
                                                } else if (player.addItemToBagNoDialog(new Item(220, true, "map 17"))) {
                                                    player.doTaskUpdate(player.taskMain.count = 1);
                                                } else {
                                                    NJUtil.sendServer(player.getSession(), AlertFunction.EMPTY_ONE_WATER(player.getSession()));
                                                }
                                            }
                                            isCloseWait = true;
                                            player.endWait(null);
                                        } else if (!player.isControlCharNhanBan() && player.taskMain != null && player.itemWait != null && (player.itemWait.getTemplateId() == 233 || player.itemWait.getTemplateId() == 234 || player.itemWait.getTemplateId() == 235)) {
                                            if (!player.isControlCharNhanBan() && player.taskMain.template.taskId == 24 && player.taskMain.index == 1) {
                                                if (NJUtil.randomBoolean()) {
                                                    player.updateUseDiaDo(player.itemWait);
                                                    isCloseWait = true;
                                                    player.endWait(null);
                                                } else {
                                                    isCloseWait = true;
                                                    player.endWait(AlertFunction.ITEM_HERE(player.getSession()));
                                                }
                                            }
                                        } else if (!player.isControlCharNhanBan() && player.taskMain != null && player.itemMapWait != null && player.itemMapWait.item.getTemplateId() == 236) {
                                            if (!player.isControlCharNhanBan() && player.taskMain.template.taskId == 26 && player.taskMain.index == 1) {
                                                player.doPickItemMap(player.itemMapWait);
                                                isCloseWait = true;
                                                player.endWait(null);
                                            }
                                        } else if (!player.isControlCharNhanBan() && player.taskMain != null && player.itemWait != null && player.itemWait.getTemplateId() == 266 && !player.isControlCharNhanBan() && player.taskMain.template.taskId == 32) {
                                            if (player.taskMain.index == 1) {
                                                if (NJUtil.probability(1, 100) == 0) {
                                                    player.removeItem(player.itemWait.indexUI);
                                                    player.sendClearItemBag(player.itemWait.indexUI);
                                                    player.addItemToBagNoDialog(new Item(267, true, "map 18"));
                                                    player.endWait(null);
                                                    player.doTaskNext();
                                                } else {
                                                    player.removeItem(player.itemWait.indexUI);
                                                    player.sendClearItemBag(player.itemWait.indexUI);
                                                    player.endWait(AlertFunction.NOT_FOUND(player.getSession()));
                                                }
                                            } else {
                                                player.endWait(null);
                                            }
                                        }
                                    }
                                    if (!isCloseWait) {
                                        player.endWait(null);
                                    }
                                } else if (player.timeWait > 1) {
                                    --player.timeWait;
                                    if (player.status == Player.ME_DIE) {
                                        player.endWait(null);
                                    }
                                }
                                for (int l = 0; l < player.itemBags.length; ++l) {
                                    if (player.itemBags[l] != null && player.itemBags[l].expires != -1L && player.itemBags[l].expires < timeNow) {
                                        player.sendClearItemBag(player.itemBags[l].indexUI);
                                        player.removeItem(player.itemBags[l], 3);
                                    }
                                }
                                for (int l = 0; l < player.itemBoxs.length; ++l) {
                                    if (player.itemBoxs[l] != null && player.itemBoxs[l].expires != -1L && player.itemBoxs[l].expires < timeNow) {
                                        player.sendClearItemBox(player.itemBoxs[l].indexUI);
                                        player.removeItem(player.itemBoxs[l], 4);
                                    }
                                }
                                for (int l = 0; l < player.getPlayerMainControl().itemBodys.length; ++l) {
                                    if (player.getPlayerMainControl().itemBodys[l] != null && player.getPlayerMainControl().itemBodys[l].expires != -1L && player.getPlayerMainControl().itemBodys[l].expires < timeNow) {
                                        player.sendClearItemBody(player.getPlayerMainControl().itemBodys[l].indexUI);
                                        player.removeItem(player.getPlayerMainControl().itemBodys[l], 5);
                                        player.getPlayerMainControl().updateData();
                                        player.playerLoadInfo();
                                    }
                                }
                                if (player.getHp() > player.getFullHp()) {
                                    player.getPlayerMainControl().hp = player.getFullHp();
                                }
                                if (player.getMp() > player.getFullMp()) {
                                    player.getPlayerMainControl().mp = player.getFullMp();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void addDynamicEff(Player player, int idEff, int byMap_player, int x, int y) {
        try {
            int nloop = 2;
            int nsecond = -1;
            Message message = new Message(Cmd.SERVER_ADD_MOB);
            message.writeByte(1);
            message.writeByte(idEff);
            message.writeShort(x);
            message.writeShort(y);
            message.writeByte(nloop);
            message.writeShort(nsecond);
            message.writeShort(ServerController.dataDynamicEff[idEff].length);
            message.write(ServerController.dataDynamicEff[idEff]);
            sendToPlayer(message);
        } catch (Exception e) {
        }
    }

    public void addDynamicMod(Player player, Npc npcs) {
        try {
            Message message = new Message(Cmd.SERVER_ADD_MOB);
            message.writeByte(0);
            message.writeByte(1);
            message.writeByte(npcs.npcId);
            message.writeBoolean(npcs.timeDisable > 0);
            message.writeBoolean(npcs.timeDontMove > 0);
            message.writeBoolean(npcs.timeFire > 0);
            message.writeBoolean(npcs.timeIce > 0);
            message.writeBoolean(npcs.timeWind > 0);
            message.writeByte(npcs.template.npcTemplateId);
            message.writeByte(npcs.sys);
            if ((npcs.isTinhAnh() || npcs.levelBoss == 2) && !npcs.isHit(player)) {
                message.writeInt(npcs.template.hp);
            } else {
                message.writeInt(npcs.hp);
            }
            message.writeByte(npcs.level);
            if ((npcs.isTinhAnh() || npcs.levelBoss == 2) && !npcs.isHit(player)) {
                message.writeInt(npcs.template.hp);
            } else {
                message.writeInt(npcs.maxhp);
            }
            message.writeShort(npcs.pointx);
            message.writeShort(npcs.pointy);
            message.writeByte(npcs.status);
            if ((npcs.isTinhAnh() || npcs.levelBoss == 2) && !npcs.isHit(player)) {
                message.writeByte(0);
            } else {
                message.writeByte(npcs.levelBoss);
            }
            message.writeBoolean(npcs.template.isBossId());
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void addDynamicMod(Player player, Vector<Npc> npcs) {
        try {
            Message message = new Message(Cmd.SERVER_ADD_MOB);
            message.writeByte(0);
            message.writeByte(npcs.size());
            for (Npc npc : npcs) {
                message.writeByte(npc.npcId);
                message.writeBoolean(npc.timeDisable > 0);
                message.writeBoolean(npc.timeDontMove > 0);
                message.writeBoolean(npc.timeFire > 0);
                message.writeBoolean(npc.timeIce > 0);
                message.writeBoolean(npc.timeWind > 0);
                message.writeByte(npc.template.npcTemplateId);
                message.writeByte(npc.sys);
                if ((npc.isTinhAnh() || npc.levelBoss == 2) && !npc.isHit(player)) {
                    message.writeInt(npc.template.hp);
                } else {
                    message.writeInt(npc.hp);
                }
                message.writeByte(npc.level);
                if ((npc.isTinhAnh() || npc.levelBoss == 2) && !npc.isHit(player)) {
                    message.writeInt(npc.template.hp);
                } else {
                    message.writeInt(npc.maxhp);
                }
                message.writeShort(npc.pointx);
                message.writeShort(npc.pointy);
                message.writeByte(npc.status);
                if ((npc.isTinhAnh() || npc.levelBoss == 2) && !npc.isHit(player)) {
                    message.writeByte(0);
                } else {
                    message.writeByte(npc.levelBoss);
                }
                message.writeBoolean(npc.template.isBossId());
            }
            for (Player p : players) {
                if (p.getSession() != null) {
                    NJUtil.sendMessage(p.getSession(), message);
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean addItemMap(ItemMap itemMap) {
        if (itemMapIds.size() > 0) {
            itemMap.itemMapId = itemMapIds.remove(0);
            itemMaps.add(itemMap);
            if (itemMap.owner > -1) {
                Player p = ServerController.hpPlayers.get(itemMap.owner);
                if (p != null && p.getIdItemGetAuto(itemMap.itemMapId) > -2) {
                    p.listItemPickAuto.add(itemMap.itemMapId);
                }
            }
            return true;
        }
        return false;
    }

    public void removeItemMap(ItemMap itemMap) {
        if (itemMap.item.getTemplateId() == 212 || itemMap.item.getTemplateId() == 236) {
            itemMap.timeStart = -50L;
        } else {
            itemMapIds.add(itemMap.itemMapId);
            itemMaps.remove(itemMap);
        }
    }

    public void addBuNhin(BuNhin buNhin) {
        buNhins.add(buNhin);
    }

    public int removeBuNhin(BuNhin b) {
        int id = buNhins.indexOf(b);
        buNhins.remove(b);
        return id;
    }

    public void doRequestItemInfo(Session conn, Message message) {
        try {
            Player player = ServerController.huPlayers.get(conn.userId);
            int typeUI = message.readByte();
            int indexUI = message.readByte();
            Item item = null;
            switch (typeUI) {
                case 3:
                    item = player.itemBags[indexUI];
                    break;
                case 4:
                    item = player.itemBoxs[indexUI];
                    break;
                case 5:
                    if (player.isControlCharNhanBan()) {
                        item = player.playercopy.itemBodys[indexUI];
                        break;
                    }
                    item = player.itemBodys[indexUI];
                    break;
                case 41:
                    if (player.isControlCharNhanBan()) {
                        item = player.playercopy.itemMons[indexUI];
                        break;
                    }
                    item = player.itemMons[indexUI];
                    break;
                case 20:
                    item = ServerController.shopNonNams.get(indexUI);
                    break;
                case 21:
                    item = ServerController.shopNonNus.get(indexUI);
                    break;
                case 22:
                    item = ServerController.shopAoNams.get(indexUI);
                    break;
                case 23:
                    item = ServerController.shopAoNus.get(indexUI);
                    break;
                case 24:
                    item = ServerController.shopGangTayNams.get(indexUI);
                    break;
                case 25:
                    item = ServerController.shopGangTayNus.get(indexUI);
                    break;
                case 26:
                    item = ServerController.shopQuanNams.get(indexUI);
                    break;
                case 27:
                    item = ServerController.shopQuanNus.get(indexUI);
                    break;
                case 28:
                    item = ServerController.shopGiayNams.get(indexUI);
                    break;
                case 29:
                    item = ServerController.shopGiayNus.get(indexUI);
                    break;
                case 16:
                    item = ServerController.shopLiens.get(indexUI);
                    break;
                case 17:
                    item = ServerController.shopNhans.get(indexUI);
                    break;
                case 18:
                    item = ServerController.shopNgocBois.get(indexUI);
                    break;
                case 19:
                    item = ServerController.shopPhus.get(indexUI);
                    break;
                case 2:
                    item = ServerController.shopVuKhis.get(indexUI);
                    break;
                case 6:
                    item = ServerController.shopStacks.get(indexUI);
                    break;
                case 7:
                    item = ServerController.shopStackLocks.get(indexUI);
                    break;
                case 8:
                    item = ServerController.shopGrocerys.get(indexUI);
                    break;
                case 9:
                    item = ServerController.shopGroceryLocks.get(indexUI);
                    break;
                case 14:
                    if (player.playerIDBuyItem != -1) {
                        player.doRequestItemInfoCharSell(indexUI);
                        break;
                    }
                    item = ServerController.shopStores.get(indexUI);
                    break;
                case 15:
                    item = ServerController.shopBooks.get(indexUI);
                    break;
                case 32:
                    item = ServerController.shopFashions.get(indexUI);
                    break;
                case 34:
                    item = ServerController.shopGiatocs.get(indexUI);
                    break;
                case 30: {
                    if (player.isControlCharNhanBan()) {
                        player.doCancelTrade();
                        player.doCancelTrade1();
                        break;
                    }
                    if (player.idTrade <= -1) {
                        break;
                    }
                    Trade trade = Player.ALL_CHAR_TRADE.get(player.idTrade);
                    if (trade == null) {
                        break;
                    }
                    if (player.playerId == trade.player1.playerId) {
                        item = trade.itemTrades2[indexUI];
                        break;
                    }
                    item = trade.itemTrades1[indexUI];
                    break;
                }
                case 39:
                    item = player.clan.items.get(indexUI);
                    break;
            }
            if (item != null) {
                message = new Message(Cmd.REQUEST_ITEM_INFO);
                message.writeByte(typeUI);
                message.writeByte(indexUI);
                message.writeLong(item.expires);
                if (item.isTypeUIMe()) {
                    message.writeInt(item.saleCoinLock);
                } else if (item.isTypeUIShop()
                        || item.isTypeUIShopLock()
                        || item.isTypeUIStore()
                        || item.isTypeUIBook()
                        || item.isTypeUIFashion()
                        || item.isTypeUIClanShop()) {
                    message.writeInt(item.buyCoin);
                    message.writeInt(item.buyCoinLock);
                    message.writeInt(item.buyGold);
                }
                if (item.isTypeBody() || item.isTypeMon() || (item.isTypeGem() && conn.isVersion144())) {
                    message.writeByte(item.sys);
                    Vector<ItemOption> options = item.getOptions();
                    if (options != null) {
                        for (ItemOption option : options) {
                            message.writeByte(option.optionTemplate.itemOptionTemplateId);
                            if (conn.isVersion145()) {
                                message.writeInt(option.param);
                            } else {
                                message.writeShort(option.param);
                            }
                        }
                    }
                } else if (item.getTemplateId() == 233 || (item.getTemplateId() == 234 | item.getTemplateId() == 235)) {
                    message.writeBytes(item.template.imgs[conn.typeHD - 1].datas.get(0));
                }
                NJUtil.sendMessage(conn, message);
            }
        } catch (IOException e) {
            LOGGER.error("", e);
        } finally {
            message.cleanup();
        }
    }

    public boolean sendAddItemMap(ItemMap itemMap) {
        try {
            Message message = new Message(Cmd.ITEMMAP_ADD);
            message.writeShort(itemMap.itemMapId);
            message.writeShort(itemMap.item.getTemplateId());
            message.writeShort(itemMap.x);
            message.writeShort(itemMap.y);
            message.writeInt(0); // len
            /*int len = 0;
            message.writeInt(len);
            if (len > 0) {
                message.writeBytes(new byte[0]);
            }*/
            sendToPlayer(message);
            return true;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public void sendAlert(String[] strs) {
        try {
            for (Player player : players) {
                player.sendServerMessage(strs);
            }
        } catch (Exception e) {
        }
    }

    public void sendToPlayer(Message message) {
        try {
            //noinspection ForLoopReplaceableByForEach
            for (int i = 0; i < players.size(); i++) {
                try {
                    players.get(i)
                            .getSession()
                            .sendMessage(message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            message.cleanup();
        }
    }

    public void sendToPlayerExcept(Message message, int pid) {
        try {
            for (int i = 0; i < players.size(); ++i) {
                Player p = players.elementAt(i);
                if (p != null && p.playerId != pid) {
                    Session conn = p.getSession();
                    if (conn != null) {
                        conn.sendMessage(message);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void addPlayer(Player p) {
        if (players.contains(p)) {
            return;
        }
        for (Player player : players) {
            if (player.playerId == p.playerId) {
                if (!p.isNhanban()) {
                    p.getSession().disconnect("Map.addPlayer");
                }
                return;
            }
        }
        players.add(p);
        if (p.capcha != null && isMapCapcha()) {
            p.capcha.npcId = npcs.size();
            p.capcha.map = this;
            int indexW = p.x / 24;
            int indexH = p.y / 24 - 2;
            p.capcha.pointx = (short) (indexW * template.size + template.size / 2);
            p.capcha.pointy = (short) (indexH * template.size + template.size);
            p.capcha.minX = p.capcha.pointx - p.capcha.template.rangeMove;
            p.capcha.maxX = p.capcha.pointx + p.capcha.template.rangeMove;
            p.capcha.minY = p.capcha.pointy - p.capcha.template.rangeMove;
            p.capcha.maxY = p.capcha.pointy + p.capcha.template.rangeMove;
            p.map.addDynamicMod(p, p.capcha);
        }
    }

    public Player getPlayer(int playerId) {
        for (Player player : players) {
            if (player.playerId == playerId) {
                return player;
            }
        }
        return null;
    }

    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.name.equals(name)) {
                return player;
            }
        }
        return null;
    }

    public NpcPlayer getNpcPlayer(int npcPlayerId) {
        for (int i = 0; i < npcPlayers.size(); i++) {
            NpcPlayer npcPlayer = npcPlayers.get(i);
            if (npcPlayer.template != null && npcPlayer.template.playerTemplateId == npcPlayerId) {
                return npcPlayer;
            }
        }
        return null;
    }

    public Vector<Party> getPts() {
        Vector<Party> vParty = new Vector<>();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.party != null && !vParty.contains(player.party)) {
                vParty.add(player.party);
            }
        }
        return vParty;
    }

    public boolean isArena() {
        return false;
    }

    public boolean isDunPb9x() {
        return false;
    }

    public boolean isGiaiDau() {
        return false;
    }

    public boolean isGiaiDauNinjaDenhat() {
        return false;
    }

    public boolean isGiaiDauNinjaTaiNang() {
        return false;
    }

    public boolean isLang() {
        return getTemplateId() == 1 || getTemplateId() == 27 || getTemplateId() == 72 || getTemplateId() == 10 || getTemplateId() == 17 || getTemplateId() == 22 || getTemplateId() == 32 || getTemplateId() == 38 || getTemplateId() == 43 || getTemplateId() == 48 || getTemplateId() == 110 || getTemplateId() == 129;
    }

    public boolean isHangDong() {
        return template.mapTemplateId == 2 || template.mapTemplateId == 29 || template.mapTemplateId == 56 || template.mapTemplateId == 62 || template.mapTemplateId == 63 || template.mapTemplateId == 64 || template.mapTemplateId == 73 || template.mapTemplateId == 91 || template.mapTemplateId == 92 || template.mapTemplateId == 93 || template.mapTemplateId == 94 || template.mapTemplateId == 95 || template.mapTemplateId == 96 || template.mapTemplateId == 97 || template.mapTemplateId == 105 || template.mapTemplateId == 106 || template.mapTemplateId == 107 || template.mapTemplateId == 108 || template.mapTemplateId == 109;
    }

    public boolean isLangCo() {
        return getTemplateId() >= 134 && getTemplateId() <= 138;
    }

    public boolean isVungDatMaQuy() {
        return getTemplateId() >= 139 && getTemplateId() <= 148;
    }

    public boolean isMapCapcha() {
        return true;
    }

    public boolean isMapTrain() {
        return !isDun && !isDunClan && !isDunPB && !isDunVA && !isDunVD && !isDunPb9x() && !isLang();
    }

    public void npcDie(Npc npc, int dameHit, boolean isChiMang) {
        try {
            Message message = new Message(Cmd.NPC_DIE);
            message.writeByte(npc.npcId);
            message.writeInt(dameHit);
            message.writeBoolean(isChiMang);
            sendToPlayer(message);
        } catch (Exception e) {
        }
    }

    public Vector<Player> getPhe1() {
        return phe1;
    }

    public Vector<Player> getPhe2() {
        return phe2;
    }

    public boolean checkFinishHangDong95() {
        return false;
    }

    public int countPlayerClone(int playerId) {
        int count = 0;
        for (Player player : players) {
            if (player.playerId == playerId) {
                ++count;
            }
        }
        return count;
    }

    public Map findGTChien(Player player, int mapTemplateId) {
        try {
            if (player.party != null) {
                for (int i = 0; i < player.party.players.size(); ++i) {
                    if (!player.party.players.get(i).equals(player) && player.party.players.get(i).map.getTemplateId() == mapTemplateId && player.party.players.get(i).map.giatocchien != null) {
                        return player.party.players.get(i).map;
                    }
                }
            }
            for (int i = 0; i < giatocchien.maps.size(); ++i) {
                Map map = giatocchien.maps.get(i);
                if (map.getTemplateId() == mapTemplateId && map.players.size() < map.maxPlayer && (player.party == null || player.party.players.size() <= 1 || map.getPts().size() < maxParty - 1)) {
                    return map;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public Map findMapGTChien(Player player, int mapTemplateId) {
        Map map = findGTChien(player, mapTemplateId);
        if (map == null) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.MAP_FULL(player.getSession()));
        }
        return map;
    }

    public void goInMap(Player player) {
        try {
            player.timeMoveUp = 0;
            if (player.x <= 0 && player.y <= 0) {
                player.x = template.defaultX;
                player.y = template.defaultY;
            } else if (player.x <= 0 || player.y <= 0 || player.x >= template.w || player.y >= template.h) {
                player.x = template.defaultX;
                player.y = template.defaultY;
            }
            if (phe1 != null && phe1.contains(player)) {
                player.typePk = Player.PK_PHE1;
            }
            if (phe2 != null && phe2.contains(player)) {
                player.typePk = Player.PK_PHE2;
            }
            player.y = player.autoFall(player.x, player.y);
            player.updateInfo();
            updateInfo(player);
            addPlayer(player);
            player.sendAddPlayer();
            player.isChangeMap = false;
            if (player.isMainchar
                    && template.mapTemplateId == 2
                    && player.taskMain != null
                    && player.taskMain.template.taskId == 6
                    && player.taskMain.index == 1) {
                player.doTaskNext();
            } else if (player.isMainchar
                    && template.mapTemplateId == 71
                    && player.taskMain != null
                    && player.taskMain.template.taskId == 6
                    && player.taskMain.index == 2) {
                player.doTaskNext();
            } else if (player.isMainchar
                    && template.mapTemplateId == 26
                    && player.taskMain != null
                    && player.taskMain.template.taskId == 6
                    && player.taskMain.index == 3) {
                player.doTaskNext();
            } else if (template.mapTemplateId == 33 && player.playerId < 0 && player.headId == 96) {
                Player p = ServerController.hpPlayers.get(-player.playerId);
                if (p != null && p.headId == 96) {
                    Message message = new Message(Cmd.CHAT_MAP);
                    message.writeInt(player.playerId);
                    message.writeUTF(AlertFunction.FAR(p.getSession()));
                    NJUtil.sendMessage(p.getSession(), message);
                }
            }
            player.playerGoInMap(this);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void goOutMap(Player player) {
        try {
            if (player == null) {
                return;
            }
            if (getTemplateId() == 130) {
                for (int i = 0; i < player.itemBags.length; ++i) {
                    if (player.itemBags[i] != null && player.itemBags[i].getTemplateId() == 458) {
                        player.throwItem(player.itemBags[i]);
                    }
                }
            }
            if (player.playerId < 0) {
                player.removeAllPlayer();
                players.remove(player);
                player = null;
            } else if (players.remove(player) && player.getSession() != null) {
                if (player.idTrade > -1) {
                    player.doCancelTrade();
                }
                player.removeAllPlayer();
            }
            if (phe1 != null) {
                phe1.remove(player);
            }
            if (phe2 != null) {
                phe2.remove(player);
            }
            if (player != null) {
                player.playerGoOutMap();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void waitGoInMap(Player player) {
        if (player.hp <= 0) {
            player.status = Player.ME_NORMAL;
            player.hp = player.hp_full;
            player.mp = player.mp_full;
        }
        player.lastMoveX = 0;
        player.lastMoveY = 0;
        player.updateData();
        player.map = this;
        player.isChangeMap = true;
        playerIns.add(player);
    }

    public void setNamePlayerLose(String name) {
    }

    public void addPlayerPhe1(Player p) {
    }

    public void addPlayerPhe2(Player p) {
    }

    public void processMessage(Session conn, Message message) {
        try {
            // nếu player chết, cmd gửi lên không nằm trong phạm vi cho phép => return
            if (ServerController.huPlayers.get(conn.userId) == null || (ServerController.huPlayers.get(conn.userId).status == Player.ME_DIE && !NJUtil.inArrayInt(new int[]{
                Cmd.ME_LIVE,
                Cmd.ME_BACK,
                Cmd.CHAT_MAP,
                Cmd.CHAT_PRIVATE,
                Cmd.CHAT_SERVER,
                Cmd.CHAT_PARTY,
                Cmd.CHAT_CLAN,
                Cmd.PARTY_INVITE,
                Cmd.PARTY_ACCEPT,
                Cmd.PARTY_CANCEL,
                Cmd.PLAYER_IN_PARTY,
                Cmd.PARTY_OUT,
                Cmd.FRIEND_ADD,
                Cmd.FRIEND_INVITE,
                Cmd.FRIEND_REMOVE,
                Cmd.SUB_COMMAND,
                Cmd.SKILL_SELECT,
                Cmd.REQUEST_ITEM_PLAYER,
                Cmd.REQUEST_ITEM_INFO,
                Cmd.VIEW_INFO,
                Cmd.MAP_CHANGE
            }, message.command))) {
                return;
            }
            ServerController.huPlayers.get(conn.userId).cmdMe = message.command;
            switch (message.command) {
                case Cmd.SUB_COMMAND:
                    doSubCommand(conn, message);
                    break;
                case Cmd.CHAT_MAP:
                    ServerController.huPlayers.get(conn.userId).doChatPlayer(message);
                    break;
                case Cmd.CHAT_PRIVATE:
                    ServerController.huPlayers.get(conn.userId).doChatPrivate(message);
                    break;
                case Cmd.CHAT_SERVER:
                    ServerController.huPlayers.get(conn.userId).doChatServer(message);
                    break;
                case Cmd.CHAT_PARTY:
                    ServerController.huPlayers.get(conn.userId).doChatParty(message);
                    break;
                case Cmd.CHAT_CLAN:
                    ServerController.huPlayers.get(conn.userId).doChatClan(message);
                    break;
                case Cmd.MAP_CHANGE: {
                    try {
                        if (ServerController.huPlayers.get(conn.userId).map.isDunClan) {
                            ServerController.huPlayers.get(conn.userId).doChangeDunClan();
                        } else if (ServerController.huPlayers.get(conn.userId).map.isDunPB) {
                            ServerController.huPlayers.get(conn.userId).doChangeDunPB();
                        } else if (ServerController.huPlayers.get(conn.userId).map.isDunVA) {
                            ServerController.huPlayers.get(conn.userId).doChangeVA();
                        } else if (ServerController.huPlayers.get(conn.userId).map.giatocchien != null) {
                            ServerController.huPlayers.get(conn.userId).doChangeGiatocchien();
                        } else {
                            Player pp = ServerController.huPlayers.get(conn.userId);
                            Player p = pp.getPlayerMainControl();
                            int idMonsTemplate = p.checkExpireThuCuoiHetHan();
                            if (idMonsTemplate != -1 && pp.isSelectChar) {
                                NJUtil.sendDialog(p.getSession(), "Thú cưỡi đã hết hạn. Vui lòng cất thú cưỡi trước.");
                                p.map.goOutMap(p);
                                p.x = p.map.template.defaultX;
                                p.y = p.map.template.defaultY;
                                p.lastMoveX = 0;
                                p.lastMoveY = 0;
                                p.map.waitGoInMap(p);
                            } else {
                                p.isSelectChar = true;
                                ServerController.huPlayers.get(conn.userId).doChangeMap(-1, false, "map cmd_map_change");
                            }
                        }
                    } catch (Exception e) {
                    }
                    break;
                }
                case Cmd.ITEMMAP_MYPICK:
                    ServerController.huPlayers.get(conn.userId).doPick(message);
                    break;
                case Cmd.ME_THROW:
                    ServerController.huPlayers.get(conn.userId).doThrow(message);
                    break;
                case Cmd.ME_LIVE:
                    ServerController.huPlayers.get(conn.userId).doReturn(message);
                    break;
                case Cmd.ME_BACK:
                    ServerController.huPlayers.get(conn.userId).doBack("map cmd me_back");
                    break;
                case Cmd.PLAYER_MOVE:
                    ServerController.huPlayers.get(conn.userId).doMove(message);
                    break;
                case Cmd.PLAYER_ATTACK_N_P:
                    ServerController.huPlayers.get(conn.userId).doAttackBoth(message, 1);
                    break;
                case Cmd.ITEM_USE:
                    ServerController.huPlayers.get(conn.userId).doItemUse(message);
                    break;
                case Cmd.ITEM_USE_CHANGEMAP:
                    ServerController.huPlayers.get(conn.userId).doItemUseChangeMap(message);
                    break;
                case Cmd.ITEM_BUY:
                    ServerController.huPlayers.get(conn.userId).doItemBuy(message);
                    break;
                case Cmd.ITEM_SALE:
                    ServerController.huPlayers.get(conn.userId).doItemSale(message);
                    break;
                case Cmd.ITEM_BODY_TO_BAG:
                    ServerController.huPlayers.get(conn.userId).doItemBodyToBag(message);
                    break;
                case Cmd.ITEM_BOX_TO_BAG:
                    ServerController.huPlayers.get(conn.userId).doItemBoxToBag(message);
                    break;
                case Cmd.ITEM_BAG_TO_BOX:
                    ServerController.huPlayers.get(conn.userId).doItemBagToBox(message);
                    break;
                case Cmd.UPPEARL:
                    ServerController.huPlayers.get(conn.userId).doUppearl(message, true);
                    break;
                case Cmd.UPPEARL_LOCK:
                    ServerController.huPlayers.get(conn.userId).doUppearl(message, false);
                    break;
                case Cmd.UPGRADE:
                    ServerController.huPlayers.get(conn.userId).doUpgrade(message);
                    break;
                case Cmd.SPLIT:
                    ServerController.huPlayers.get(conn.userId).doSplit(message);
                    break;
                case Cmd.PLEASE_INPUT_PARTY:
                    ServerController.huPlayers.get(conn.userId).doPleaseParty(message);
                    break;
                case Cmd.ACCEPT_PLEASE_PARTY:
                    ServerController.huPlayers.get(conn.userId).doAcceptPleaseParty(message);
                    break;
                case Cmd.REQUEST_PLAYERS:
                    ServerController.huPlayers.get(conn.userId).doRequestPlayer(message);
                    break;
                case Cmd.ZONE_CHANGE:
                    ServerController.huPlayers.get(conn.userId).doChangeZone(message);
                    break;
                case Cmd.MENU: {
                    Player player = ServerController.huPlayers.get(conn.userId);
                    if (player.idActionNewMenu != -1) {
                        Player.doConfirmDyNamicMenu(player, message);
                        break;
                    }
                    player.doMenu(message);
                    break;
                }
                case Cmd.OPEN_UI_BOX:
                    ServerController.huPlayers.get(conn.userId).doOpenUIBox();
                    break;
                case Cmd.OPEN_UI_PT:
                    ServerController.huPlayers.get(conn.userId).doOpenUIPt();
                    break;
                case Cmd.OPEN_MENU_ID:
                    ServerController.huPlayers.get(conn.userId).doOpenMenuId(message.readShort());
                    break;
                case Cmd.OPEN_UI_ZONE:
                    ServerController.huPlayers.get(conn.userId).doOpenUIZone();
                    break;
                case Cmd.OPEN_UI_MENU:
                    ServerController.huPlayers.get(conn.userId).doMenuOpen(message);
                    break;
                case Cmd.SKILL_SELECT:
                    ServerController.huPlayers.get(conn.userId).doSkillSelect(message);
                    break;
                case Cmd.REQUEST_ITEM_INFO:
                    doRequestItemInfo(conn, message);
                    break;
                case Cmd.TRADE_INVITE:
                    ServerController.huPlayers.get(conn.userId).doInviteTrade(message);
                    break;
                case Cmd.TRADE_INVITE_ACCEPT:
                    ServerController.huPlayers.get(conn.userId).doAcceptInviteTrade(message);
                    break;
                case Cmd.TRADE_LOCK_ITEM:
                    ServerController.huPlayers.get(conn.userId).doItemTrade(message);
                    break;
                case Cmd.TRADE_ACCEPT:
                    ServerController.huPlayers.get(conn.userId).doAcceptTrade();
                    break;
                case Cmd.TASK_GET:
                    ServerController.huPlayers.get(conn.userId).doGetTask(message);
                    break;
                case Cmd.TRADE_INVITE_CANCEL:
                    ServerController.huPlayers.get(conn.userId).doCancelInviteTrade();
                    break;
                case Cmd.TRADE_CANCEL:
                    ServerController.huPlayers.get(conn.userId).doCancelTrade();
                    break;
                case Cmd.FRIEND_INVITE:
                    ServerController.huPlayers.get(conn.userId).doFriendInvite(message);
                    break;
                case Cmd.PLAYER_ATTACK_NPC:
                    ServerController.huPlayers.get(conn.userId).doAttackNpc(message);
                    break;
                case Cmd.PLAYER_ATTACK_PLAYER:
                    ServerController.huPlayers.get(conn.userId).doAttackPlayer(message);
                    break;
                case Cmd.TEST_INVITE:
                    ServerController.huPlayers.get(conn.userId).doTestInvite(message);
                    break;
                case Cmd.TEST_INVITE_ACCEPT:
                    ServerController.huPlayers.get(conn.userId).doTestInviteAccept(message);
                    break;
                case Cmd.ME_CUU_SAT:
                    ServerController.huPlayers.get(conn.userId).doAddCuuSat(message);
                    break;
                case Cmd.PLAYER_ATTACK_P_N:
                    ServerController.huPlayers.get(conn.userId).doAttackBoth(message, 2);
                    break;
                case Cmd.USE_SKILL_MY_BUFF:
                    ServerController.huPlayers.get(conn.userId).doUseSkillMyBuff(message);
                    break;
                case Cmd.PARTY_INVITE:
                    ServerController.huPlayers.get(conn.userId).doAddParty(message);
                    break;
                case Cmd.PARTY_ACCEPT:
                    ServerController.huPlayers.get(conn.userId).doAcceptParty(message);
                    break;
                case Cmd.PARTY_CANCEL:
                    ServerController.huPlayers.get(conn.userId).doCancelParty(message);
                    break;
                case Cmd.PARTY_OUT:
                    if (ServerController.huPlayers.get(conn.userId).isNotEditParty()) {
                        break;
                    }
                    ServerController.huPlayers.get(conn.userId).doOutParty();
                    break;
                case Cmd.OPEN_TEXT_BOX_ID:
                    ServerController.huPlayers.get(conn.userId).doProccessInputText(message);
                    break;
                case Cmd.VIEW_INFO:
                    ServerController.huPlayers.get(conn.userId).doPlayerInfo(message);
                    break;
                case Cmd.REQUEST_ITEM_PLAYER:
                    ServerController.huPlayers.get(conn.userId).doRequestItemPlayer(message);
                    break;
                case Cmd.TEST_DUN_INVITE:
                    ServerController.huPlayers.get(conn.userId).doTestDunAccept(message);
                    break;
                case Cmd.TEST_DUN_LIST:
                    ServerController.huPlayers.get(conn.userId).doGoViewTestDun(message);
                    break;
                case Cmd.SEND_ITEM_TO_AUCTION:
                    ServerController.huPlayers.get(conn.userId).doSendItemToSale(message);
                    break;
                case Cmd.VIEW_ITEM_AUCTION:
                    ServerController.huPlayers.get(conn.userId).doViewItemAuction(message);
                    break;
                case Cmd.BUY_ITEM_AUCTION:
                    ServerController.huPlayers.get(conn.userId).doBuyItemAuction(message);
                    break;
                case Cmd.TEST_GT_INVITE:
                    ServerController.huPlayers.get(conn.userId).doTestGTAccept(message);
                    break;
                case Cmd.OPEN_UI_CONFIRM_POPUP:
                    ServerController.huPlayers.get(conn.userId).doPopUp(message);
                    break;
                case Cmd.ITEM_MON_TO_BAG:
                    ServerController.huPlayers.get(conn.userId).doItemMonToBag(message);
                    break;
                case Cmd.LUYEN_THACH:
                    ServerController.huPlayers.get(conn.userId).doLuyenThach(message);
                    break;
                case Cmd.TINH_LUYEN:
                    ServerController.huPlayers.get(conn.userId).doTinhLuyen(message);
                    break;
                case Cmd.DOI_OPTION:
                    ServerController.huPlayers.get(conn.userId).doDoiOption(message);
                    break;
                case Cmd.CAT_KEO:
                    ServerController.huPlayers.get(conn.userId).doCatkeo(message);
                    break;
                case Cmd.LIST_TOP_ARENA:
                    ServerController.huPlayers.get(conn.userId).doThachDauArena(message);
                    break;
                case Cmd.KHAM_NGOC:
                    ServerController.huPlayers.get(conn.userId).doKhamnNgoc(message);
                    break;
            }
        } catch (Exception e) {
            message.cleanup();
        }
    }

    public void doSubCommand(Session conn, Message message) {
        try {
            message.subCmd = message.readByte();
            if (GameServer.isServerLocal()) {
                LOGGER.info(">> Receive message: {} > {}", Cmd.SUB_COMMAND, message.subCmd);
            }
            if (ServerController.huPlayers.get(conn.userId) == null) {
                NJUtil.sendDialog(conn, AlertFunction.ERROR(conn));
                return;
            }
            // nếu player chết, cmd gửi lên không nằm trong phạm vi cho phép => return
            if (ServerController.huPlayers.get(conn.userId).status == Player.ME_DIE && !NJUtil.inArrayInt(new int[]{
                Cmd.CHANGE_TYPE_PK,
                Cmd.CREATE_PARTY,
                Cmd.CHANGE_TEAMLEADER,
                Cmd.MOVE_MEMBER,
                Cmd.REQUEST_FRIEND,
                Cmd.REQUEST_ENEMIES,
                Cmd.FRIEND_REMOVE,
                Cmd.BAG_SORT,
                Cmd.SAVE_RMS,
                Cmd.LOAD_RMS
            }, message.subCmd)) {
                return;
            }
            switch (message.subCmd) {
                case Cmd.POTENTIAL_UP:
                    ServerController.huPlayers.get(conn.userId).doPotentialUp(message);
                    break;
                case Cmd.SKILL_UP:
                    ServerController.huPlayers.get(conn.userId).doSkillUp(message);
                    break;
                case Cmd.BAG_SORT:
                    ServerController.huPlayers.get(conn.userId).doBagSort();
                    break;
                case Cmd.BOX_SORT:
                    ServerController.huPlayers.get(conn.userId).doBoxSort();
                    break;
                case Cmd.BOX_COIN_IN:
                    ServerController.huPlayers.get(conn.userId).doCoinBoxIn(message);
                    break;
                case Cmd.BOX_COIN_OUT:
                    ServerController.huPlayers.get(conn.userId).doCoinBoxOut(message);
                    break;
                case Cmd.REQUEST_ITEM:
                    ServerController.huPlayers.get(conn.userId).doRequestItem(message);
                    break;
                case Cmd.CHANGE_TYPE_PK:
                    ServerController.huPlayers.get(conn.userId).doChangeTypePk(message);
                    break;
                case Cmd.CREATE_PARTY:
                    ServerController.huPlayers.get(conn.userId).doCreateParty();
                    break;
                case Cmd.CHANGE_TEAMLEADER:
                    ServerController.huPlayers.get(conn.userId).doChangeTeamLeader(message);
                    break;
                case Cmd.MOVE_MEMBER:
                    if (ServerController.huPlayers.get(conn.userId).isNotEditParty()) {
                        return;
                    }
                    ServerController.huPlayers.get(conn.userId).doMoveMember(message);
                    break;
                case Cmd.REQUEST_FRIEND:
                    ServerController.huPlayers.get(conn.userId).doRequestFriend();
                    break;
                case Cmd.REQUEST_ENEMIES:
                    ServerController.huPlayers.get(conn.userId).doRequestEnemies();
                    break;
                case Cmd.FRIEND_REMOVE:
                    ServerController.huPlayers.get(conn.userId).doRevemoFriend(message);
                    break;
                case Cmd.BUFF_LIVE:
                    ServerController.huPlayers.get(conn.userId).buffLive(message);
                    break;
                case Cmd.FIND_PARTY:
                    ServerController.huPlayers.get(conn.userId).doFindParty();
                    break;
                case Cmd.LOCK_PARTY:
                    ServerController.huPlayers.get(conn.userId).doLockTeam(message);
                    break;
                case Cmd.ADMIN_MOVE:
                    ServerController.huPlayers.get(conn.userId).doAdminMove(message);
                    break;
                case Cmd.SAVE_RMS:
                    ServerController.huPlayers.get(conn.userId).saveRms(message);
                    break;
                case Cmd.LOAD_RMS:
                    ServerController.huPlayers.get(conn.userId).loadRms(message);
                    break;
                case Cmd.CLAN_INVITE:
                    ServerController.huPlayers.get(conn.userId).clanInvite(message);
                    break;
                case Cmd.CLAN_ACCEPT_INVITE:
                    ServerController.huPlayers.get(conn.userId).clanAcceptInvite(message);
                    break;
                case Cmd.CLAN_PLEASE:
                    ServerController.huPlayers.get(conn.userId).clanPlease(message);
                    break;
                case Cmd.CLAN_ACCEPT_PLEASE:
                    ServerController.huPlayers.get(conn.userId).clanAcceptPlease(message);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public void addMessagePlayer(NMessage msg) {
        messages.add(msg);
    }

    public LinkedBlockingQueue<NMessage> getMessageMap() {
        return messages;
    }

    public static boolean isGo(Player player) {
        if (player.map.getTemplateId() == 112) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.VUOT_AI_ALERT13(player.getSession()));
            return false;
        }
        LocalTime timeNow = LocalTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        if (player.map.getTemplateId() == 98 || player.map.getTemplateId() == 104) {
            if (!GameServer.openChienTruongTest && (hour < 13 || hour >= 15) && (hour < 16 || hour >= 18) && (hour < 19 || hour >= 21)) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.OPEN_DOOR_CT2(player.getSession()));
                return false;
            }
            if (GameServer.openChienTruongTest
                    && (hour == 13 && minute < 30)
                    || (hour == 14 && minute >= 30)
                    || (hour == 16 && minute < 30)
                    || (hour == 17 && minute >= 30)
                    || (hour == 19 && minute < 30)
                    || (hour == 20 && minute >= 30)) {
                if (hour < 15 && player.level > 50 || hour >= 16 && hour < 18 && (player.level <= 50 || player.level > 70)) {
                    int[] ids = {1, 27, 72};
                    if (player.doChangeMap(ids[NJUtil.randomNumber(ids.length)], false, "map isGO")) {
                        playerLefts.remove(player);
                        playerRights.remove(player);
                    }
                    return false;
                }
                if (hour == 14 || hour == 17 || hour == 20) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.OPEN_DOOR_CT3(player.getSession()));
                } else {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.OPEN_DOOR_CT2(player.getSession()));
                }
                return false;
            }
        } else if (player.map.getTemplateId() == 113) {
            if ((hour < 9 || hour >= 11) && (hour < 21 || hour >= 23)) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.VUOT_AI_ALERT4(player.getSession()));
                return false;
            }
            if ((hour == 9 && minute < 30)
                    || (hour == 10 && minute >= 30)
                    || (hour == 21 && minute < 30)
                    || (hour == 22 && minute >= 30)) {
                if ((hour == 10 && minute == 30) || (hour == 22 && minute == 30)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.VUOT_AI_ALERT5(player.getSession()));
                } else {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.VUOT_AI_ALERT4(player.getSession()));
                }
                return false;
            }
        }
        return true;
    }

    public static boolean isGoGTChien(Player player) {
        if ((player.map.getTemplateId() == 98 || player.map.getTemplateId() == 104) && player.map.giatocchien.step < 3) {
            NJUtil.sendDialog(player.getSession(), "Chưa đến thời gian thi đấu");
            return false;
        }
        return true;
    }

    public static Map find(Player player, int mapTemplateId) {
        try {
            if (player.isChangeMap) {
                return null;
            }
            if (player.party != null) {
                for (int i = 0; i < player.party.players.size(); ++i) {
                    if (!player.party.players.get(i).equals(player) && player.party.players.get(i).map.getTemplateId() == mapTemplateId) {
                        return player.party.players.get(i).map;
                    }
                }
            }
            Vector<Map> maps = ServerController.ALL_MAP.get(mapTemplateId);
            if (maps != null) {
                for (int j = 0; j < maps.size(); ++j) {
                    Map map = maps.elementAt(j);
                    if ((map.players.size() < map.maxPlayer) && (player.party == null || player.party.players.size() <= 1 || map.getPts().size() < maxParty - 1)) {
                        return map;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static Map find1(Player player, int mapTemplateId, int zone) {
        try {
            if (player.isChangeMap) {
                return null;
            }
            Vector<Map> maps = ServerController.ALL_MAP.get(mapTemplateId);
            if (maps != null) {
                for (int i = 0; i < maps.size(); ++i) {
                    Map map = maps.elementAt(i);
                    if (map.zoneId == zone) {
                        return map;
                    }
                }
            }
            if (mapTemplateId == 131 || mapTemplateId == 130 || mapTemplateId == 132) {
                for (Map mapkeo : mapkeos) {
                    if (mapkeo.template.mapTemplateId == mapTemplateId && mapkeo.zoneId == zone) {
                        return mapkeo;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static Map findMap(Player player, int mapTemplateId) {
        if (mapTemplateId == 149) {
            return ServerController.ALL_MAP.get(mapTemplateId).elementAt(0);
        }
        Map map = find(player, mapTemplateId);
        if (map == null && (player.map.template.mapTemplateId < 134 || player.map.template.mapTemplateId > 138)) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.MAP_FULL(player.getSession()));
        }
        return map;
    }

    public static Map findMap1(Player player, int mapTemplateId, int zone) {
        Map map = find1(player, mapTemplateId, zone);
        if (map == null) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.MAP_FULL(player.getSession()));
        }
        return map;
    }

    public static void findZone(Player player, int zoneId, Vector<Map> list) {
        try {
            if (player.isChangeMap) {
                return;
            }
            for (Map map : list) {
                if (map.getTemplateId() == player.map.template.mapTemplateId && map.zoneId == zoneId) {
                    if (player.party != null) {
                        for (int j = 0; j < player.party.players.size(); ++j) {
                            Player p = player.party.players.get(j);
                            if (p.map.equals(map)) {
                                player.mapClear();
                                player.map.goOutMap(player);
                                map.waitGoInMap(player);
                                return;
                            }
                        }
                    }
                    if (player.getSession().type_admin < Player.ROLE_GM && !GameServer.isServerLocal()) {
                        if (map.players.size() >= map.maxPlayer) {
                            player.endWait(null);
                            NJUtil.sendServer(player.getSession(), AlertFunction.ZONE_FULL(player.getSession()));
                            return;
                        }
                        if (player.party != null && player.party.players.size() > 1 && map.getPts().size() >= maxParty - 1) {
                            player.endWait(null);
                            NJUtil.sendServer(player.getSession(), AlertFunction.MAX_PARTY(player.getSession()));
                            return;
                        }
                    }
                    player.mapClear();
                    player.map.goOutMap(player);
                    map.waitGoInMap(player);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void addLeft(Player player) {
        if (!playerLefts.contains(player)) {
            playerLefts.add(player);
        }
        if (!idLefts.contains(player.playerId) && !idRights.contains(player.playerId)) {
            player.pointCT = 0;
            player.pointPKCT = 0;
            player.resultCT = 0;
        }
        if (!idLefts.contains(player.playerId)) {
            idLefts.add(player.playerId);
        }
    }

    public static void addRight(Player player) {
        if (!playerRights.contains(player)) {
            playerRights.add(player);
        }
        if (!idLefts.contains(player.playerId) && !idRights.contains(player.playerId)) {
            player.pointCT = 0;
            player.pointPKCT = 0;
            player.resultCT = 0;
        }
        if (!idRights.contains(player.playerId)) {
            idRights.add(player.playerId);
        }
    }

    public static Npc createDynamicModNpc(int templateId, Map map, int idtile, int x, int y) {
        int npcSize = 1;
        Npc npc = null;
        for (int i = 0; i < npcSize; ++i) {
            if (templateId == 219) {
                npc = new CapchaMonster(map.npcs.size(), templateId, map);
            } else {
                npc = new Npc(map.npcs.size(), templateId, map);
            }
            npc.pointx = (short) (x * map.template.size + map.template.size / 2);
            npc.pointy = (short) (y * map.template.size + map.template.size);
            npc.minX = npc.pointx - npc.template.rangeMove;
            npc.maxX = npc.pointx + npc.template.rangeMove;
            npc.minY = npc.pointy - npc.template.rangeMove;
            npc.maxY = npc.pointy + npc.template.rangeMove;
            npc.hp = npc.template.hp;
            npc.level = npc.template.level;
            if (npc.template.npcTemplateId == 98 || npc.template.npcTemplateId == 99) {
                npcTrus.add(npc);
            }
            if (npc.template.npcTemplateId == 69 || npc.template.npcTemplateId == 71) {
                npc.levelBoss = 2;
            }
            if (npc.template.isBossId() && (npc.template.npcTemplateId < 161 || npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) && !map.template.isMapChienTruong()) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId < 168) {
                npc.timeReturn = 7200;
            }
            if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId <= 167) {
                npc.exactly = 3000;
            }
            if (npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) {
                npc.timeReturn = 600;
            }
            if (npc.template.npcTemplateId == 165) {
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId >= 117 && npc.template.npcTemplateId <= 121) {
                npc.exactly = 500;
            }
            if (npc.template.npcTemplateId == 202) {
                npc.status = Npc.STATUS_DIE;
                map.npcChild.add(npc);
            }
            if (npc.template.npcTemplateId == 201) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId == 203) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId == 204) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            map.npcs.add(npc);
        }
        return npc;
    }

    public static Npc createModCapcha(int templateId, Map map, int idtile, int x, int y) {
        int npcSize = 1;
        Npc npc = null;
        for (int i = 0; i < npcSize; ++i) {
            if (templateId == 219 || templateId == 220 || templateId == 230) {
                npc = new CapchaMonster(map.npcs.size(), templateId, map);
            } else if (templateId == 233) {
                npc = new CapchaMonster(map.npcs.size(), templateId, map);
            } else {
                npc = new Npc(map.npcs.size(), templateId, map);
            }
            npc.pointx = (short) (x * map.template.size + map.template.size / 2);
            npc.pointy = (short) (y * map.template.size + map.template.size);
            npc.minX = npc.pointx - npc.template.rangeMove;
            npc.maxX = npc.pointx + npc.template.rangeMove;
            npc.minY = npc.pointy - npc.template.rangeMove;
            npc.maxY = npc.pointy + npc.template.rangeMove;
            npc.hp = npc.template.hp;
            npc.level = npc.template.level;
            if (npc.template.npcTemplateId == 98 || npc.template.npcTemplateId == 99) {
                npcTrus.add(npc);
            }
            if (npc.template.npcTemplateId == 69 || npc.template.npcTemplateId == 71) {
                npc.levelBoss = 2;
            }
            if (npc.template.isBossId() && (npc.template.npcTemplateId < 161 || npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) && !map.template.isMapChienTruong()) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId < 168) {
                npc.timeReturn = 20000;
            }
            if (npc.template.npcTemplateId >= 161 && npc.template.npcTemplateId <= 167) {
                npc.exactly = 3000;
            }
            if (npc.template.npcTemplateId == 166 || npc.template.npcTemplateId == 167) {
                npc.timeReturn = 600;
            }
            if (npc.template.npcTemplateId == 165) {
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId >= 117 && npc.template.npcTemplateId <= 121) {
                npc.exactly = 500;
            }
            if (npc.template.npcTemplateId == 202) {
                npc.status = Npc.STATUS_DIE;
                map.npcChild.add(npc);
            }
            if (npc.template.npcTemplateId == 201) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId == 203) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId == 204) {
                npc.exactly = 3000;
                npc.status = Npc.STATUS_DIE;
            }
            if (npc.template.npcTemplateId == 228) {
                npc.exactly = 3000;
            }
            if (npc.template.npcTemplateId == 230) {
                npc.exactly = 3000;
            }
        }
        return npc;
    }

    public static String tongketCT() {
        StringBuilder str;
        int countBach = 0;
        int countHac = 0;
        Vector<Player> ps = new Vector<>();
        ps.addAll(playerLefts);
        ps.addAll(playerRights);
        for (Player playerLeft : playerLefts) {
            countBach += playerLeft.pointCT;
        }
        for (Player playerRight : playerRights) {
            countHac += playerRight.pointCT;
        }
        ps.sort(Player.sort);
        str = new StringBuilder(NJUtil.replace(Alert_VN.BACH_GIA, String.valueOf(countBach)));
        str.append("\n").append(NJUtil.replace(Alert_VN.HAC_GIA, String.valueOf(countHac)));
        for (int j = 0; j < Math.min(ps.size(), 10); ++j) {
            str.append("\n").append(j + 1).append(" . ")
                    .append(ps.get(j).name).append(": ")
                    .append(ps.get(j).pointCT).append(" ")
                    .append(Alert_VN.POINT).append(" (")
                    .append((ps.get(j).getTypePk() == Player.PK_PHE1) ? Alert_VN.BACH : Alert_VN.HAC).append(")")
                    .append("\n").append(Alert_VN.TITLE_NAME).append(": ")
                    .append(Alert_VN.TITLE_CT[ps.get(j).getCT()]);
        }
        return str.toString();
    }

    public static void tongketFinish() {
        int countBach = 0;
        int countHac = 0;
        Vector<Player> ps = new Vector<>();
        ps.addAll(playerLefts);
        ps.addAll(playerRights);
        for (Player left : playerLefts) {
            countBach += left.pointCT;
        }
        for (Player right : playerRights) {
            countHac += right.pointCT;
        }
        ps.sort(Player.sort);
        StringBuilder tongket;
        if (countBach > countHac) {
            tongket = new StringBuilder("c3" + Alert_VN.BACH_WIN);
        } else if (countBach < countHac) {
            tongket = new StringBuilder("c3" + Alert_VN.HAC_WIN);
        } else {
            tongket = new StringBuilder("c3" + Alert_VN.HAC_WIN);
        }
        tongket.append("\nc0").append(NJUtil.replace(Alert_VN.BACH_GIA, String.valueOf(countBach)));
        tongket.append("\n").append(NJUtil.replace(Alert_VN.HAC_GIA, String.valueOf(countHac)));
        int size = Math.min(ps.size(), 10);
        winCT = new String[3];
        for (int i = 0; i < size; ++i) {
            if (i < winCT.length) {
                winCT[i] = ps.get(i).name;
            }
            tongket.append("\n")
                    .append(i + 1).append(" . ")
                    .append(ps.get(i).name).append(": ")
                    .append(ps.get(i).pointCT).append(" ")
                    .append(Alert_VN.POINT).append(" (")
                    .append((ps.get(i).getTypePk() == Player.PK_PHE1) ? Alert_VN.BACH : Alert_VN.HAC).append(")");
            tongket.append("\n")
                    .append(Alert_VN.TITLE_NAME).append(": ")
                    .append(Alert_VN.TITLE_CT[ps.get(i).getCT()]);
        }
        strTongket = tongket.toString();
        if (countBach > countHac) {
            for (Player playerLeft : playerLefts) {
                playerLeft.resultCT = 1;
            }
            for (Player playerRight : playerRights) {
                playerRight.resultCT = 2;
            }
        } else if (countBach < countHac) {
            for (Player playerLeft : playerLefts) {
                playerLeft.resultCT = 2;
            }
            for (Player playerRight : playerRights) {
                playerRight.resultCT = 1;
            }
        }
        NJUtil.sendChienTruongAlert(Alert_VN.CLOSE_CT);
    }

    public static void thaBossTrum() {
        Vector<Integer> mapIds4x = new Vector<>();
        for (int id : new int[]{14, 15, 16, 34, 35, 52}) {
            mapIds4x.add(id);
        }
        Vector<Integer> mapIds5x = new Vector<>();
        for (int id : new int[]{44, 67, 68}) {
            mapIds5x.add(id);
        }
        Vector<Integer> mapIds6x = new Vector<>();
        for (int id : new int[]{24, 41, 45, 59}) {
            mapIds6x.add(id);
        }
        Vector<Integer> mapIds7x = new Vector<>();
        for (int id : new int[]{19, 36, 54}) {
            mapIds7x.add(id);
        }
        Vector<Integer> mapIds8x = new Vector<>();
        /*for (int id: new int[] {}) {
            mapIds8x.add(id);
        }*/
        Vector<Integer> mapIds9x = new Vector<>();
        for (int id : new int[]{141}) {
            mapIds9x.add(id);
        }
        Vector<Integer> mapIds9x2 = new Vector<>();
        for (int id : new int[]{142}) {
            mapIds9x2.add(id);
        }
        Vector<Integer> mapIds9x3 = new Vector<>();
        for (int id : new int[]{143}) {
            mapIds9x3.add(id);
        }
        Vector<Vector<Integer>> bs = new Vector<>();
        bs.add(mapIds4x);
        bs.add(mapIds5x);
        bs.add(mapIds6x);
        bs.add(mapIds7x);
        bs.add(mapIds8x);
        bs.add(mapIds9x);
        bs.add(mapIds9x2);
        bs.add(mapIds9x3);
        StringBuilder mapId = new StringBuilder();
        StringBuilder debugInfo = new StringBuilder();
        for (int i = 0; i < bossTrumMapIds.length; ++i) {
            Vector<Integer> mapIds = bs.get(i);
            if (mapIds.size() != 0) {
                int index = NJUtil.randomNumber(mapIds.size());
                if (GameServer.isSvEnglish()) {
                    mapId.append(ServerController.mapTemplates.get(mapIds.get(index)).name_en).append(", ");
                } else {
                    mapId.append(ServerController.mapTemplates.get(mapIds.get(index)).name).append(", ");
                }
                bossTrumMapIds[i] = mapIds.remove(index);
                if (GameServer.isServerLocal()) {
                    bossTrumZoneIds[i] = 0;
                } else {
                    bossTrumZoneIds[i] = 15 + NJUtil.randomNumber(15);
                }
                debugInfo.append("[").append(bossTrumMapIds[i]).append("] ")
                        .append(ServerController.mapTemplates.get(bossTrumMapIds[i]).name)
                        .append(" khu vực ").append(bossTrumZoneIds[i])
                        .append(", ");
            }
        }
        String[] a = {"Thần thú đã xuất hiện tại ", "Watch out!  Reports warn Legendary Monsters are roaming the "};
        Matcher matcher = Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(Normalizer.normalize(debugInfo, Normalizer.Form.NFD));
        LOGGER.info(a[GameServer.getLang()] + matcher.replaceAll("").replace("đ", "d"));
        NJUtil.sendServerAlert(a[GameServer.getLang()] + mapId);
    }

    public static void thaLongDen(int zoneId) {
        for (int i = 0; i < ServerController.maps.size(); ++i) {
            Map map = ServerController.maps.get(i);
            if (map.isLang()) {
                for (int j = 0; j < map.longDenZoneIds.length; ++j) {
                    if (zoneId != -1) {
                        map.longDenZoneIds[j] = zoneId;
                    } else {
                        map.longDenZoneIds[j] = 15 + NJUtil.randomNumber(15);
                    }
                }
            }
        }
        NJUtil.sendServerAlert("Trong làng đã bắt đầu xuất hiện lồng đèn.");
    }
}
