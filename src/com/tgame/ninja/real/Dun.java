package com.tgame.ninja.real;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Dun extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dun.class);

    public long timeEnd;

    public Player playerOpen;

    public boolean isItem;

    public boolean isStop;

    public boolean isMap;

    public Dun(int mapTemplateId, boolean isMap) {
        super(mapTemplateId);
        this.isMap = isMap;
    }

    public Dun(int mapTemplateId) {
        super(mapTemplateId);
        isDun = true;
        zoneId = 0;
        template = ServerController.mapTemplates.get(mapTemplateId);
        timeEnd = System.currentTimeMillis() + NJUtil.getMilisByHour(2);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        new Thread(() -> {
            Thread.currentThread().setName("MAP " + mapId);
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
                    if (getTemplateId() == 110 && tick == 5) {
                        for (Player player : players) {
                            player.sendOpenUISay(0, AlertFunction.HEPL_TEST_DUN1(player.getSession()));
                        }
                    } else if (getTemplateId() == 110 && tick == 15) {
                        for (Player player : players) {
                            player.sendOpenUISay(0, AlertFunction.HEPL_TEST_DUN2(player.getSession()));
                        }
                    }
                    updateNpc();
                    updateBuNhin();
                    updateItemMap();
                    updatePlayer();
                    lastTime = l1;
                    ++tick;
                    if (tick >= 2999) {
                        tick = 0;
                    }
                    if (template.mapTemplateId == 56 || template.mapTemplateId == 0 || template.mapTemplateId == 73) {
                        int minX = 792;
                        if (template.mapTemplateId == 56) {
                            minX = 696;
                        } else if (template.mapTemplateId == 73) {
                            minX = 744;
                        }
                        for (Player value : players) {
                            try {
                                if (value.playerId < 0 && playerOpen.status != Player.ME_DIE) {
                                    Skill skillMove = value.skills.get(0);
                                    int dx = NJUtil.distance(value.x, playerOpen.x);
                                    if (playerOpen.x < minX && value.x - minX < 220) {
                                        value.x += (short) (NJUtil.randomNumber(30) + (value.x - minX));
                                        if (NJUtil.randomNumber(3) == 0) {
                                            value.sendMove();
                                        } else {
                                            value.moveFast();
                                        }
                                    } else if (playerOpen.x >= minX && dx <= 100) {
                                        Skill skill;
                                        if (playerOpen.y == value.y) {
                                            skill = value.skills.get(NJUtil.randomNumber(6) + 1);
                                        } else {
                                            skill = value.skills.get(NJUtil.randomNumber(3) + 3);
                                        }
                                        if (l1 > skill.timeAttackNext && playerOpen.y == value.y) {
                                            skill.timeAttackNext = l1 + skill.timeReplay * 5L;
                                            if (!value.isLockAtt()) {
                                                int dameHit = value.doAttactPlayer(playerOpen, NJUtil.randomNumber(50) + value.dame_full, false, false, false);
                                                Message message = new Message(Cmd.PLAYER_ATTACK_PLAYER);
                                                message.writeInt(value.playerId);
                                                message.writeByte(skill.template.skillTemplateId);
                                                message.writeInt(playerOpen.playerId);
                                                value.sendToPlayer(message, false);
                                                message = new Message(Cmd.HAVE_ATTACK_PLAYER);
                                                message.writeInt(playerOpen.playerId);
                                                message.writeInt(playerOpen.getHp());
                                                message.writeInt(dameHit);
                                                value.sendToPlayer(message, false);
                                                playerOpen.checkDie(-1);
                                            }
                                        } else if (NJUtil.randomNumber(5) == 2) {
                                            value.x += (short) (NJUtil.randomNumber(30) * (NJUtil.randomBoolean() ? -1 : 1));
                                            value.sendMove();
                                        }
                                    } else if (playerOpen.x >= minX && dx < 200) {
                                        value.x = (short) (playerOpen.x + ((value.x > playerOpen.x) ? 30 : -30));
                                        value.sendMove();
                                    } else if (playerOpen.x >= minX && l1 > skillMove.timeAttackNext) {
                                        skillMove.timeAttackNext = l1 + skillMove.timeReplay;
                                        value.x = (short) (playerOpen.x + ((value.x > playerOpen.x) ? 30 : -30));
                                        Message message = new Message(Cmd.MOVE_FAST);
                                        message.writeInt(value.playerId);
                                        message.writeShort(value.x);
                                        message.writeShort(value.y);
                                        value.sendToPlayer(message, false);
                                    } else if (NJUtil.randomNumber(5) == 2) {
                                        value.x += (short) (NJUtil.randomNumber(30) * (NJUtil.randomBoolean() ? -1 : 1));
                                        value.sendMove();
                                    }
                                }
                            } catch (Exception e) {
                                LOGGER.error("", e);
                            }
                        }
                    }
                }
                synchronized (LOCK) {
                    try {
                        LOCK.wait(500L);
                    } catch (InterruptedException e) {
                        LOGGER.error("", e);
                    }
                }
                String[] strs = { Alert_VN.HEPL_TEST_DUN3, Alert_EN.HEPL_TEST_DUN3 };
                if (getTemplateId() != 129 && (playerOpen == null || playerOpen.getSession() == null || ServerController.huPlayers.get(playerOpen.userId) == null || !players.contains(playerOpen))) {
                    if (getTemplateId() == 110) {
                        sendAlert(strs);
                    }
                    if (getTemplateId() == 117) {
                        sendAlert(strs);
                        break;
                    }
                    break;
                } else {
                    if (getTemplateId() == 110 && (playerOpen.testSkillDun == null || playerOpen.testSkillDun.player1 == null || playerOpen.testSkillDun.player2 == null || playerOpen.testSkillDun.player2.testSkillDun == null)) {
                        sendAlert(strs);
                        break;
                    }
                    if (getTemplateId() == 117 && (playerOpen.testGTDun == null || playerOpen.testGTDun.player1 == null || playerOpen.testGTDun.player2 == null || playerOpen.testGTDun.player2.testGTDun == null)) {
                        playerOpen.clearTestGT();
                        sendAlert(strs);
                        break;
                    }
                    if (isStop) {
                        break;
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
            if (playerOpen != null && playerOpen.taskMain != null) {
                if ((playerOpen.taskMain.template.taskId == 13 && playerOpen.taskMain.index == 1 && template.mapTemplateId == 56) || (playerOpen.taskMain.template.taskId == 13 && playerOpen.taskMain.index == 2 && template.mapTemplateId == 0) || (playerOpen.taskMain.template.taskId == 13 && playerOpen.taskMain.index == 3 && template.mapTemplateId == 73)) {
                    NJUtil.sendServer(playerOpen.getSession(), AlertFunction.TASK_FAIL(playerOpen.getSession()));
                } else if (playerOpen.taskMain.template.taskId == 20 && playerOpen.taskMain.index == 1 && template.mapTemplateId == 74) {
                    NJUtil.sendServer(playerOpen.getSession(), AlertFunction.OUT_DUN(playerOpen.getSession()));
                } else if (playerOpen.taskMain.template.taskId == 23 && playerOpen.taskMain.index == 1 && template.mapTemplateId == 78) {
                    NJUtil.sendServer(playerOpen.getSession(), AlertFunction.TASK_FAIL1(playerOpen.getSession()));
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
                        NJUtil.sleep(500);
                    } else {
                        ps.remove(p);
                        p.x = map.template.defaultX;
                        p.y = map.template.defaultY;
                        map.waitGoInMap(p);
                    }
                }
            }
            ++Dun.maxZones[getTemplateId()];
            if (playerOpen != null) {
                playerOpen.clearTestDun();
            }
        }).start();
    }

    @Override
    public void updateNpc() {
        try {
            int countDie = 0;
            for (int i = npcs.size() - 1; i >= 0; --i) {
                Npc npc = npcs.get(i);
                if (npc.status == Npc.STATUS_DIE) {
                    ++countDie;
                } else {
                    npc.update(this, 0);
                }
            }
            if (getTemplateId() == 78 && countDie == npcs.size() && !isItem) {
                isItem = true;
                int[] x = { 208, 538, 130, 84 };
                int[] y = { 432, 264, 264, 96 };
                int index = NJUtil.randomNumber(x.length);
                ItemMap itemMap = new ItemMap(new Item(232, true, "Dun"), -1, x[index], y[index], System.currentTimeMillis() + 20000L);
                addItemMap(itemMap);
                sendAddItemMap(itemMap);
                NJUtil.sendServer(playerOpen.getSession(), AlertFunction.FIND_ITEM(playerOpen.getSession()));
            }
        } catch (Exception e) {
        }
    }

    public static Dun createDun(int mapTemplateId) {
        if (Dun.maxZones[mapTemplateId] > 0) {
            --Dun.maxZones[mapTemplateId];
            return new Dun(mapTemplateId);
        }
        return null;
    }
}
