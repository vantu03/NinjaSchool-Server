package com.tgame.ninja.real;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.MapTemplate;
import com.tgame.ninja.model.NMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunMixedArena extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunMixedArena.class);

    public boolean bBossOut;

    public byte typePk;

    public DunMixedArena(int id, int zId, MapTemplate mapTemplate) {
        super(mapTemplate.mapTemplateId);
        bBossOut = false;
        mapId = id;
        zoneId = zId;
        template = mapTemplate;
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        loadNpcPlayer();
        loadNpc();
        new Thread(() -> {
            Thread.currentThread().setName("MixedArena MAP " + template.mapTemplateId + "-" + mapId);
            while (true) {
                try {
                    while (true) {
                        try {
                            short count = 0;
                            while (messages.size() > 0) {
                                if (ServerController.isExit) {
                                    messages.clear();
                                    break;
                                }
                                ++count;
                                if (messages.size() > 5000) {
                                    messages.clear();
                                    for (int i = players.size() - 1; i >= 0; --i) {
                                        NJUtil.sendDialog(players.get(i).getSession(), AlertFunction.SERVER_FULL(players.get(i).getSession()));
                                        players.get(i).getSession().disconnect("Map ham tao 0");
                                    }
                                    LOGGER.warn("Qua tai: {},{}", getTemplateId(), zoneId);
                                    break;
                                }
                                NMessage msg = messages.poll(100L, TimeUnit.MILLISECONDS);
                                if (msg != null) {
                                    processMessage(msg.conn, msg.message);
                                }
                                if (count < 500) {
                                    continue;
                                }
                                count = 0;
                                NJUtil.sleep(5L);
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
                        long l3 = System.currentTimeMillis();
                        if (l3 - lastTime > 500L) {
                            updateNpc();
                            updateNpcPlayer();
                            updateItemMap();
                            updatePlayer();
                            lastTime = l3;
                            ++tick;
                            if (tick >= 2999) {
                                tick = 0;
                            }
                            if (isStop) {
                                break;
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
                    Map mapNext;
                    Player p;
                    for (Player playerIn : playerIns) {
                        p = playerIn;
                        p.typePk = Player.PK_NORMAL;
                        p.updateTypePk();
                        mapNext = Map.findMap(p, p.mapTemplateIdGo);
                        if (mapNext == null) {
                            mapNext = Map.findMap(p, 22);
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
                        mapNext = Map.findMap(p, p.mapTemplateIdGo);
                        if (mapNext == null) {
                            mapNext = Map.findMap(p, 22);
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
                        mapNext = Map.findMap(p, p.mapTemplateIdGo);
                        if (mapNext == null) {
                            mapNext = Map.findMap(p, 22);
                        }
                        p.mapClear();
                        p.x = mapNext.template.defaultX;
                        p.y = mapNext.template.defaultY;
                        mapNext.waitGoInMap(p);
                    }
                } catch (Exception e) {
                    LOGGER.error("lỗi vòng lặp map", e);
                    continue;
                }
                break;
            }
        }).start();
    }

    @Override
    public void loadNpc() {
        super.loadNpc();
        for (int i = 0; i < npcs.size(); ++i) {
            Npc npc = npcs.elementAt(i);
            if (npc != null && checkIdBoss(npc)) {
                npc.status = Npc.STATUS_DIE;
                npc.timeWait = 0;
                npc.timeReturn = Integer.MAX_VALUE;
            }
        }
        bBossOut = false;
    }

    @Override
    public void updateNpc() {
        super.updateNpc();
        updateBossAppear();
    }

    void addMob(Npc npc) {
        try {
            Message message = new Message(Cmd.SERVER_ADD_MOB);
            message.writeByte(0);
            message.writeByte(1);
            message.writeByte(npc.npcId);
            message.writeBoolean(npc.timeDisable > 0);
            message.writeBoolean(npc.timeDontMove > 0);
            message.writeBoolean(npc.timeFire > 0);
            message.writeBoolean(npc.timeIce > 0);
            message.writeBoolean(npc.timeWind > 0);
            message.writeByte(npc.template.npcTemplateId);
            message.writeByte(npc.sys);
            message.writeInt(npc.hp);
            message.writeByte(npc.level);
            message.writeInt(npc.maxhp);
            message.writeShort(npc.pointx);
            message.writeShort(npc.pointy);
            message.writeByte(npc.status);
            message.writeByte(npc.levelBoss);
            message.writeBoolean(npc.template.isBossId());
            for (Player p : players) {
                if (p.getSession() != null) {
                    NJUtil.sendMessage(p.getSession(), message);
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean checkBossAvailable(int npcTemplateId) {
        for (int i = 0; i < npcs.size(); ++i) {
            Npc npc = npcs.elementAt(i);
            if (npc.template.npcTemplateId == npcTemplateId && npc.status != Npc.STATUS_DIE && ((npcTemplateId == 200 && MixedArena.bBossOut1) || (npcTemplateId == 199 && MixedArena.bBossOut2) || (npcTemplateId == 198 && MixedArena.bBossOut3))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIdBoss(Npc npc) {
        return (template.mapTemplateId == 150 && npc.template.npcTemplateId == 200) || (template.mapTemplateId == 151 && npc.template.npcTemplateId == 199) || (template.mapTemplateId == 152 && npc.template.npcTemplateId == 198);
    }

    public boolean checkInvincible(Player p) {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return p.typePk == typePk && p.calInvincible != null && dateTimeNow.isBefore(p.calInvincible);
    }

    public void setInvincible(Player p) {
        if (p.typePk == typePk) {
            p.calInvincible = LocalDateTime.now().plusSeconds(5);
        }
    }

    public void updateBossAppear() {
        if (zoneId == 0 && !bBossOut) {
            if (template.mapTemplateId == 150 && MixedArena.bBossOut1) {
                for (int i = 0; i < npcs.size(); ++i) {
                    Npc boss = npcs.elementAt(i);
                    if (boss.template.npcTemplateId == 200) {
                        boss.hp = boss.template.hp;
                        int n = 0;
                        boss.timeReturn = n;
                        boss.timeWait = n;
                        boss.status = 5;
                        addMob(boss);
                        break;
                    }
                }
                bBossOut = true;
            } else if (template.mapTemplateId == 151 && MixedArena.bBossOut2) {
                for (int i = 0; i < npcs.size(); ++i) {
                    Npc boss = npcs.elementAt(i);
                    if (boss.template.npcTemplateId == 199) {
                        boss.hp = boss.template.hp;
                        int n2 = 0;
                        boss.timeReturn = n2;
                        boss.timeWait = n2;
                        boss.status = 5;
                        addMob(boss);
                        break;
                    }
                }
                bBossOut = true;
            } else if (template.mapTemplateId == 152 && MixedArena.bBossOut3) {
                for (int i = 0; i < npcs.size(); ++i) {
                    Npc boss = npcs.elementAt(i);
                    if (boss.template.npcTemplateId == 198) {
                        boss.hp = boss.template.hp;
                        int n3 = 0;
                        boss.timeReturn = n3;
                        boss.timeWait = n3;
                        boss.status = 5;
                        addMob(boss);
                        break;
                    }
                }
                bBossOut = true;
            }
        }
    }
}
