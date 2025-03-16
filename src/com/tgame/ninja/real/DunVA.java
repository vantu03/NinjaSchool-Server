package com.tgame.ninja.real;

import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.NJUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunVA extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunVA.class);

    public static Vector<DunVA> maps = new Vector<>();

    public int counttru;

    public int indexFinish;

    public boolean isAlert;

    public boolean[] isGos;

    public int levelDun;

    public int levelMax;

    public long timeEnd;

    public Vector<String> names;

    public DunVA(int mapTemplateId) {
        super(mapTemplateId);
        names = null;
        indexFinish = 0;
        levelDun = 1;
        levelMax = 0;
        counttru = 0;
        isDunVA = true;
        zoneId = 0;
        template = ServerController.mapTemplates.get(mapTemplateId);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        new Thread(() -> {
            Thread.currentThread().setName("DUNVA " + mapId);
            loadNpc();
            loadNpcPlayer();
            while (timeEnd - System.currentTimeMillis() > 0L) {
                try {
                    while (messages.size() > 0) {
                        if (ServerController.isExit) {
                            messages.clear();
                        } else {
                            if (indexFinish == 7) {
                                timeEnd = System.currentTimeMillis() - 60000L;
                                indexFinish = 8;
                            }
                            NMessage msg = messages.poll(100L, TimeUnit.MILLISECONDS);
                            if (msg != null && !NJUtil.inArrayInt(new int[]{
                                Cmd.PLAYER_ATTACK_N_P,
                                Cmd.PLAYER_ATTACK_NPC,
                                Cmd.PLAYER_ATTACK_P_N,
                                Cmd.PLAYER_ATTACK_PLAYER
                            }, msg.message.command) && indexFinish < 7) {
                                processMessage(msg.conn, msg.message);
                            }
                        }
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
                long l1 = System.currentTimeMillis();
                if (l1 - lastTime > 500L) {
                    updateNpc();
                    updateBuNhin();
                    updateItemMap();
                    updatePlayer();
                    lastTime = l1;
                    ++tick;
                    if (tick >= 2999) {
                        tick = 0;
                    }
                }
                synchronized (LOCK) {
                    try {
                        LOCK.wait(500L);
                    } catch (InterruptedException e) {
                        LOGGER.error("", e);
                    }
                }
            }
            Vector<Player> ps = new Vector<>(players);
            while (playerIns.size() > 0) {
                Player p = playerIns.remove(0);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                p.mapClear();
                goOutMap(p);
            }
            while (playerOuts.size() > 0) {
                Player p = playerOuts.remove(0);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                p.mapClear();
                goOutMap(p);
            }
            while (players.size() > 0) {
                Player p = players.remove(0);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                if (p.playerId >= 0) {
                    p.mapClear();
                    goOutMap(p);
                }
            }
            while (ps.size() > 0) {
                Player p = ps.get(0);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                if (p.playerId < 0) {
                    ps.remove(p);
                } else {
                    Map map = null;
                    if (template.mapTemplateId == 56) {
                        map = Map.find(p, 72);
                    } else if (template.mapTemplateId == 0) {
                        map = Map.find(p, 27);
                    } else if (template.mapTemplateId == 73) {
                        map = Map.find(p, 1);
                    } else if (template.mapTemplateId == 74) {
                        map = Map.find(p, 8);
                    } else if (template.mapTemplateId == 78) {
                        map = Map.find(p, 35);
                    }
                    if (map == null) {
                        map = Map.find(p, p.mapTemplateId_focus);
                    }
                    if (map == null) {
                        map = Map.find(p, 72);
                    }
                    if (map == null) {
                        map = Map.find(p, 1);
                    }
                    if (map == null) {
                        map = Map.find(p, 27);
                    }
                    if (map == null) {
                        map = Map.find(p, 22);
                    }
                    if (map == null) {
                        try {
                            synchronized (LOCK) {
                                LOCK.wait(500L);
                            }
                        } catch (Exception e) {
                            continue;
                        }
                    }
                    ps.remove(p);
                    if (map != null) {
                        p.x = map.template.defaultX;
                        p.y = map.template.defaultY;
                        map.waitGoInMap(p);
                    }
                }
            }
            ++DunVA.maxZones[getTemplateId()];
            maps.remove(this);
        }).start();
    }

    @Override
    public boolean isMapCapcha() {
        return false;
    }

    @Override
    public void loadNpc() {
        File file = new File("data/npc/" + getTemplateId() + ".npc");
        if (!file.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(file);
             DataInputStream dis = new DataInputStream(fis)
        ) {
            if (indexFinish <= 6) {
                npcs.clear();
                int npcSize = dis.readShort();
                if (getTemplateId() == 112) {
                    if (indexFinish == 6) {
                        Npc npc = new Npc(npcs.size(), 112, this);
                        npc.pointx = 562;
                        npc.pointy = 384;
                        npc.minX = npc.pointx - npc.template.rangeMove;
                        npc.maxX = npc.pointx + npc.template.rangeMove;
                        npc.minY = npc.pointy - npc.template.rangeMove;
                        npc.maxY = npc.pointy + npc.template.rangeMove;
                        npc.hp = npc.template.hp;
                        npc.level = levelDun;
                        npc.loadData(npc.template.npcTemplateId);
                        npc.reloadDunVA();
                        int n = npc.hp * 50;
                        npc.hp = n;
                        npc.maxhp = n;
                        npcs.add(npc);
                    } else {
                        for (int i = 0; i < npcSize; ++i) {
                            int npcTemplateId = dis.readShort();
                            int tileId = dis.readShort();
                            dis.readByte();
                            int indexW = tileId % template.tmw;
                            int indexH = tileId / template.tmw;
                            npcTemplateId += indexFinish;
                            Npc npc = new Npc(npcs.size(), npcTemplateId, this);
                            npc.pointx = (short) (indexW * template.size + template.size / 2);
                            npc.pointy = (short) (indexH * template.size + template.size);
                            if (npc.template.type == 4 || npc.template.type == 5) {
                                npc.pointy -= 24;
                            }
                            npc.minX = npc.pointx - npc.template.rangeMove;
                            npc.maxX = npc.pointx + npc.template.rangeMove;
                            npc.minY = npc.pointy - npc.template.rangeMove;
                            npc.maxY = npc.pointy + npc.template.rangeMove;
                            npc.hp = npc.template.hp;
                            npc.level = levelDun;
                            npc.loadData(npcTemplateId);
                            npc.reloadDunVA();
                            npcs.add(npc);
                        }
                    }
                } else if (getTemplateId() == 113) {
                    for (int i = 0; i < npcSize; ++i) {
                        int npcTemplateId = dis.readShort();
                        int tileId = dis.readShort();
                        dis.readByte();
                        int indexW = tileId % template.tmw;
                        int indexH = tileId / template.tmw;
                        npcTemplateId += indexFinish;
                        Npc npc = new Npc(npcs.size(), npcTemplateId, this);
                        npc.pointx = (short) (indexW * template.size + template.size / 2);
                        npc.pointy = (short) (indexH * template.size + template.size);
                        npc.minX = npc.pointx - npc.template.rangeMove;
                        npc.maxX = npc.pointx + npc.template.rangeMove;
                        npc.minY = npc.pointy - npc.template.rangeMove;
                        npc.maxY = npc.pointy + npc.template.rangeMove;
                        npc.level = levelDun;
                        int n2 = npc.template.hp * levelDun;
                        npc.maxhp = n2;
                        npc.hp = n2;
                        npc.status = Npc.STATUS_DIE;
                        npcs.add(npc);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void sendAlert(String[] strs) {
        try {
            for (String name : names) {
                try {
                    Player p = ServerController.hnPlayers.get(name);
                    if (p != null && p.getSession() != null && p.map.isDunVA) {
                        p.sendServerMessage(strs);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void updateNpc() {
        try {
            if (getTemplateId() == 113) {
                for (int i = npcs.size() - 1; i >= 0; --i) {
                    Npc npc = npcs.get(i);
                    if (npc.status == Npc.STATUS_DIE) {
                        --npc.timeWait;
                    }
                    npc.update(this, 0);
                }
            }
            if (getTemplateId() == 112) {
                int countDie = 0;
                for (int j = npcs.size() - 1; j >= 0; --j) {
                    Npc npc = npcs.get(j);
                    if (npc.status == Npc.STATUS_DIE) {
                        ++countDie;
                    } else {
                        npc.update(this, 0);
                    }
                }
                if (countDie == npcs.size() && npcs.size() > 0) {
                    if (indexFinish < 6) {
                        for (Player player : players) {
                            player.doBackDunVA();
                        }
                    } else if (indexFinish == 6) {
                        ++indexFinish;
                        timeEnd = System.currentTimeMillis() + 15000L;
                        for (String name : names) {
                            try {
                                Player p = ServerController.hnPlayers.get(name);
                                if (p != null && p.getSession() != null && p.map.isDunVA) {
                                    ((DunVA) p.map).timeEnd = timeEnd;
                                    if (p.typeNvbian == 7) {
                                        p.fibian();
                                    }
                                    NJUtil.sendServer(p.getSession(), AlertFunction.VUOT_AI_ALERT9(p.getSession()));
                                    p.addItemToBagNoDialog(new Item(288, true, "DunVA"));
                                    p.sendMapTime(15);
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void waitGoInMap(Player player) {
        player.sendMapTime((int) (timeEnd / 1000L - System.currentTimeMillis() / 1000L));
        player.map = this;
        if (getTemplateId() == 112) {
            int countDie = 0;
            for (int i = npcs.size() - 1; i >= 0; --i) {
                Npc npc = npcs.get(i);
                if (npc.status == Npc.STATUS_DIE) {
                    ++countDie;
                }
            }
            if (countDie == npcs.size() && npcs.size() > 0) {
                ++indexFinish;
                loadNpc();
            }
            if (indexFinish <= 5 && counttru < 5) {
                for (String name : names) {
                    try {
                        Player p = ServerController.hnPlayers.get(name);
                        if (p != null && p.getSession() != null && p.map.getTemplateId() == 113) {
                            p.map.loadNpc1();
                            if (p.map.npcs.firstElement().status == Npc.STATUS_DIE) {
                                ++counttru;
                                break;
                            }
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        playerIns.add(player);
    }

    public static void checkPlayerDunVA(DunVA mapNext) {
        if (mapNext.getTemplateId() == 112) {
            int count = 0;
            for (int i = 0; i < mapNext.isGos.length; ++i) {
                Player p = ServerController.hnPlayers.get(mapNext.names.get(i));
                if (mapNext.isGos[i] || p == null || !p.map.isDunVA) {
                    ++count;
                }
            }
            if (count >= mapNext.names.size()) {
                Arrays.fill(mapNext.isGos, false);
            }
        }
    }

    public static DunVA createDun(int mapTemplateId, Party party, int levelDun) {
        if (DunVA.maxZones[mapTemplateId] > 0) {
            DunVA dunVA = new DunVA(mapTemplateId);
            dunVA.names = new Vector<>();
            dunVA.levelDun = levelDun;
            dunVA.levelMax = levelDun;
            if (dunVA.levelDun > 68) {
                dunVA.levelDun = 68;
            }
            LocalTime timeNow = LocalTime.now();
            dunVA.timeEnd = System.currentTimeMillis() + NJUtil.getMilisByMinute(90) - (timeNow.getMinute() * 60 + timeNow.getSecond()) * 1000;
            for (int i = 0; i < party.players.size(); ++i) {
                String name = party.players.get(i).name;
                for (DunVA map : maps) {
                    if (map.names.contains(name) && map.getTemplateId() == mapTemplateId) {
                        Player p = party.players.firstElement();
                        p.sendOpenUISay(25, name + AlertFunction.VUOT_AI_ALERT6(p.getSession()));
                        return null;
                    }
                }
                dunVA.names.add(name);
            }
            dunVA.isGos = new boolean[dunVA.names.size()];
            --DunVA.maxZones[mapTemplateId];
            maps.add(dunVA);
            return dunVA;
        }
        Player p2 = party.players.firstElement();
        p2.sendOpenUISay(25, AlertFunction.MAP_FULL(p2.getSession()));
        return null;
    }

    public static DunVA findMap(String name, int mapTemplateId) {
        int i = 0;
        while (i < maps.size()) {
            if (maps.get(i).names.contains(name) && maps.get(i).getTemplateId() == mapTemplateId) {
                checkPlayerDunVA(maps.get(i));
                if (mapTemplateId == 112 && maps.get(i).indexFinish <= 5 && maps.get(i).isGos[maps.get(i).names.indexOf(name)]) {
                    return null;
                }
                return maps.get(i);
            } else {
                ++i;
            }
        }
        return null;
    }
}
