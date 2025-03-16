package com.tgame.ninja.real;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Clan;
import com.tgame.ninja.model.NMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunClan extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunClan.class);

    public Clan clanOpen;

    public int countFinish;

    public boolean isUnLock;

    public int levelDun;

    public int levelMax;

    public Vector<String> names;

    public long timeEnd;

    public int waitLoadMob;

    public DunClan(int mapTemplateId, int levelDun) {
        super(mapTemplateId);
        names = null;
        waitLoadMob = 0;
        countFinish = 0;
        this.levelDun = 0;
        levelMax = 0;
        this.levelDun = levelDun;
        levelMax = levelDun;
        if (this.levelDun > 68) {
            this.levelDun = 68;
        }
        isDunClan = true;
        zoneId = 0;
        template = ServerController.mapTemplates.get(mapTemplateId);
        timeEnd = System.currentTimeMillis() + NJUtil.getMilisByMinute(10);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        new Thread(() -> {
            Thread.currentThread().setName("DUN CLAN " + mapId);
            loadNpc();
            loadNpcPlayer();
            while (timeEnd - System.currentTimeMillis() > 0L) {
                try {
                    while (messages.size() > 0) {
                        if (ServerController.isExit) {
                            messages.clear();
                        } else {
                            NMessage msg = messages.poll(100L, TimeUnit.MILLISECONDS);
                            if (msg != null) {
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
                p.clearEffect(true);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                p.mapClear();
                goOutMap(p);
            }
            while (playerOuts.size() > 0) {
                Player p = playerOuts.remove(0);
                p.clearEffect(true);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                p.mapClear();
                goOutMap(p);
            }
            while (players.size() > 0) {
                Player p = players.remove(0);
                p.clearEffect(true);
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                if (p.playerId >= 0) {
                    p.mapClear();
                    goOutMap(p);
                }
            }
            while (ps.size() > 0) {
                Player p = ps.get(0);
                p.clan.dunClan = null;
                p.hp = p.hp_full;
                p.status = Player.ME_NORMAL;
                if (p.playerId < 0) {
                    ps.remove(p);
                } else {
                    NJUtil.sendServer(p.getSession(), AlertFunction.CLOSE_DUN_CLAN(p.getSession()));
                    Map map = Map.findMap(p, p.mapTemplateIdGo);
                    if (map == null) {
                        NJUtil.sleep(500L);
                    } else {
                        ps.remove(p);
                        p.x = map.template.defaultX;
                        p.y = map.template.defaultY;
                        map.waitGoInMap(p);
                    }
                }
            }
            ++DunClan.maxZones[getTemplateId()];
            if (clanOpen != null) {
                clanOpen.clearDun();
            }
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
            for (int npcSize = dis.readShort(), i = 0; i < npcSize; ++i) {
                int npcTemplateId = dis.readShort();
                int tileId = dis.readShort();
                dis.readByte();
                int indexW = tileId % template.tmw;
                int indexH = tileId / template.tmw;
                Npc npc = new Npc(npcs.size(), npcTemplateId, this);
                npc.pointx = (short) (indexW * template.size + template.size / 2);
                npc.pointy = (short) (indexH * template.size + template.size);
                npc.minX = npc.pointx - npc.template.rangeMove;
                npc.maxX = npc.pointx + npc.template.rangeMove;
                npc.minY = npc.pointy - npc.template.rangeMove;
                npc.maxY = npc.pointy + npc.template.rangeMove;
                npc.level = npc.template.level;
                npc.isTathu(this);
                npc.level = levelDun;
                if (npc.level < 30) {
                    npc.level = 30;
                }
                npc.loadData(npcTemplateId);
                npc.reloadDun();
                if (npc.template.npcTemplateId == 82) {
                    npc.levelBoss = 2;
                }
                npcs.add(npc);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void updateNpc() {
        try {
            if (getTemplateId() == 90) {
                for (int i = npcs.size() - 1; i >= 0; --i) {
                    Npc npc = npcs.get(i);
                    if (npc.status != Npc.STATUS_DIE) {
                        npc.update(this, 0);
                    }
                }
                return;
            }
            int loop = 1;
            if (getTemplateId() == 81 || getTemplateId() == 82 || getTemplateId() == 83) {
                if (clanOpen != null && clanOpen.dunClan != null && clanOpen.dunClan.names != null && clanOpen.dunClan.names.size() >= 20) {
                    loop = 2;
                }
            } else if (getTemplateId() == 84 || getTemplateId() == 85 || getTemplateId() == 86) {
                if (clanOpen != null && clanOpen.dunClan != null && clanOpen.dunClan.names != null && clanOpen.dunClan.names.size() >= 15) {
                    loop = 2;
                }
            } else if (getTemplateId() == 87 || getTemplateId() == 88 || getTemplateId() == 89) {
                if (clanOpen != null && clanOpen.dunClan != null && clanOpen.dunClan.names != null && clanOpen.dunClan.names.size() >= 10) {
                    loop = 2;
                }
            }
            if (waitLoadMob > 0) {
                --waitLoadMob;
                if (waitLoadMob <= 0) {
                    if (countFinish < loop) {
                        for (int j = npcs.size() - 1; j >= 0; --j) {
                            Npc npc = npcs.get(j);
                            if (npc.template.npcTemplateId != 81) {
                                npc.timeWait = npc.timeReturn;
                                npc.update(this, 0);
                            }
                        }
                    } else if (countFinish == loop) {
                        Npc npc = npcs.get(NJUtil.randomNumber(npcs.size()));
                        while (npc.template.npcTemplateId == 81) {
                            npc = npcs.get(NJUtil.randomNumber(npcs.size()));
                        }
                        npc.timeWait = npc.timeReturn;
                        npc.update(this, 1);
                    } else if (countFinish > loop && (getTemplateId() == 87 || getTemplateId() == 88 || getTemplateId() == 89)) {
                        for (int j = npcs.size() - 1; j >= 0; --j) {
                            Npc npc = npcs.get(j);
                            if (npc.template.npcTemplateId == 81) {
                                npc.timeWait = npc.timeReturn;
                                npc.update(this, 0);
                            }
                        }
                    }
                }
            } else {
                int countDie = 0;
                for (int k = npcs.size() - 1; k >= 0; --k) {
                    Npc npc = npcs.get(k);
                    if (npc.template.npcTemplateId == 81 && countFinish < loop) {
                        npc.status = Npc.STATUS_DIE;
                        ++countDie;
                    } else if (npc.status == Npc.STATUS_DIE) {
                        ++countDie;
                    } else {
                        npc.update(this, 0);
                    }
                }
                if (countDie == npcs.size() && npcs.size() > 0) {
                    if (countFinish <= loop) {
                        ++countFinish;
                    }
                    if (countFinish < loop) {
                        waitLoadMob = 8;
                    } else if (countFinish == loop) {
                        waitLoadMob = 5;
                    } else if (countFinish > loop && (getTemplateId() == 87 || getTemplateId() == 88 || getTemplateId() == 89)) {
                        waitLoadMob = 5;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static DunClan createDun(int mapTemplateId, int levelDun) {
        if (DunClan.maxZones[mapTemplateId] > 0) {
            --DunClan.maxZones[mapTemplateId];
            return new DunClan(mapTemplateId, levelDun);
        }
        return null;
    }

    public static DunClan createDun(int mapTemplateId, int levelDun, Clan clanOpen) {
        if (DunClan.maxZones[mapTemplateId] > 0) {
            --DunClan.maxZones[mapTemplateId];
            DunClan dunClan = new DunClan(mapTemplateId, levelDun);
            dunClan.clanOpen = clanOpen;
            return dunClan;
        }
        return null;
    }

    public static DunClan findDunClan(Player player, int mapTemplateId) {
        try {
            int maxsize = 8;
            int size1 = player.clan.dunClan1.players.size() + player.clan.dunClan4.players.size() + player.clan.dunClan7.players.size();
            int size2 = player.clan.dunClan2.players.size() + player.clan.dunClan5.players.size() + player.clan.dunClan8.players.size();
            int size3 = player.clan.dunClan3.players.size() + player.clan.dunClan6.players.size() + player.clan.dunClan9.players.size();
            if (player.clan.dunClan.template.mapTemplateId == mapTemplateId) {
                return player.clan.dunClan;
            }
            if (player.clan.dunClan1.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 1) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                if (size1 >= maxsize && player.map.getTemplateId() == 80) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN5(player.getSession()));
                    return null;
                }
                return player.clan.dunClan1;
            } else if (player.clan.dunClan2.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 1) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                if (size2 >= maxsize && player.map.getTemplateId() == 80) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN5(player.getSession()));
                    return null;
                }
                return player.clan.dunClan2;
            } else if (player.clan.dunClan3.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 1) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                if (size3 >= maxsize && player.map.getTemplateId() == 80) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN5(player.getSession()));
                    return null;
                }
                return player.clan.dunClan3;
            } else if (player.clan.dunClan4.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 4) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan4;
            } else if (player.clan.dunClan5.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 4) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan5;
            } else if (player.clan.dunClan6.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 4) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan6;
            } else if (player.clan.dunClan7.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 7) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan7;
            } else if (player.clan.dunClan8.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 7) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan8;
            } else if (player.clan.dunClan9.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 7) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan9;
            } else if (player.clan.dunClan10.template.mapTemplateId == mapTemplateId) {
                if (player.clan.stepDoor < 10) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.ERROR_DUN_CLAN4(player.getSession()));
                    return null;
                }
                return player.clan.dunClan10;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
