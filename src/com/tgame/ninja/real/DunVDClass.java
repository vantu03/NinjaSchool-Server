package com.tgame.ninja.real;

import com.tgame.ninja.model.Alert_EN;
import com.tgame.ninja.model.Alert_VN;
import com.tgame.ninja.model.NMessage;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunVDClass extends Dun {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunVDClass.class);

    public static Vector<DunVDClass> maps = new Vector<>();

    public int money;

    public String name1;

    public String name2;

    public String str1;

    public String str2;

    public static Vector<Integer> zoneIds = new Vector<>();

    public DunVDClass dunHere;

    public int isStart;

    public boolean isGiaDau;

    public DunVDClass(int mapTemplateId) {
        super(mapTemplateId, true);
        money = 0;
        name1 = "";
        name2 = "";
        str1 = str2 = GameServer.isSvEnglish() ? Alert_EN.PHE : Alert_VN.PHE;
        isStart = 0;
        isGiaDau = false;
        DunVDClass.maps.add(this);
        isDunVD = true;
        zoneId = DunVDClass.zoneIds.get(0);
        DunVDClass.zoneIds.remove(0);
        template = ServerController.mapTemplates.get(mapTemplateId);
        timeEnd = System.currentTimeMillis() + NJUtil.getMilisByHour(2);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        dunHere = this;
        new Thread(() -> {
            Thread.currentThread().setName("DUN VD class " + mapId);
            try {
                if (ServerController.turnOnExit) {
                    return;
                }
                int countOutP1 = 0;
                int countOutP2 = 0;
                while (timeEnd - System.currentTimeMillis() > 0L) {
                    if (ServerController.isExit) {
                        if (money > 0) {
                            int m = money / 2;
                            sendCoinWin(getWin1(), m, true);
                            sendCoinWin(getWin2(), m, true);
                            money = 0;
                        }
                        break;
                    }
                    if (isStart >= 50) {
                        ++isStart;
                    }
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
                        countOutP1 = 0;
                        countOutP2 = 0;
                        try {
                            for (Player player1 : phe1) {
                                if (player1 == null || player1.getSession() == null || !player1.map.isDunVD || player1.status == Player.ME_DIE) {
                                    ++countOutP1;
                                }
                            }
                            for (Player player2 : phe2) {
                                if (player2 == null || player2.getSession() == null || !player2.map.isDunVD || player2.status == Player.ME_DIE) {
                                    ++countOutP2;
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (isStart >= 50) {
                            if (countOutP1 == phe1.size() && money > 0) {
                                sendCoinWin(getWin2(), money, false);
                                final int time = 10;
                                timeEnd = System.currentTimeMillis() + time * 1000;
                                for (Player player : players) {
                                    player.sendMapTime(time);
                                }
                                if (DunVDClass.arenas.size() >= 20) {
                                    DunVDClass.arenas.remove(0);
                                }
                                String str = str2 + " " + Alert_VN.WIN + " " + str1;
                                DunVDClass.arenas.add(str);
                                updateTaskPhe2();
                            } else if (countOutP2 == phe2.size() && money > 0) {
                                sendCoinWin(getWin1(), money, false);
                                final int time = 10;
                                timeEnd = System.currentTimeMillis() + time * 1000;
                                for (Player player : players) {
                                    player.sendMapTime(time);
                                }
                                if (DunVDClass.arenas.size() >= 20) {
                                    DunVDClass.arenas.remove(0);
                                }
                                String str = str1 + " " + Alert_VN.WIN + " " + str2;
                                DunVDClass.arenas.add(str);
                                updateTaskPhe1();
                            }
                        }
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
                if (money > 0 && countOutP1 > countOutP2) {
                    sendCoinWin(getWin2(), money, false);
                    updateTaskPhe2();
                } else if (money > 0 && countOutP2 > countOutP1) {
                    sendCoinWin(getWin1(), money, false);
                    updateTaskPhe1();
                } else if (money > 0) {
                    int m = money / 2;
                    sendCoinWin(getWin1(), m, true);
                    sendCoinWin(getWin2(), m, true);
                    String[] strs = {
                        String.valueOf(Alert_VN.END_TEST_DUN2) + (m - m / 100) + Alert_VN.END_TEST_DUN3,
                        String.valueOf(Alert_EN.END_TEST_DUN2) + (m - m / 100) + Alert_EN.END_TEST_DUN3,
                    };
                    sendAlert(strs);
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
                    } else if (isGiaDau && (p.name.equals(name1) || p.name.equals(name2))) {
                        ps.remove(p);
                        p.goGiaiDauClass();
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
                            if (p.mapTemplateId_focus != 138 && p.mapTemplateId_focus != 137) {
                                map = Map.find(p, p.mapTemplateId_focus);
                            } else {
                                map = Map.find(p, 22);
                            }
                        }
                        if (p.getSession().type_admin > 0) {
                            map = Map.find(p, 1);
                        }
                        if (map == null) {
                            NJUtil.sleep(500);
                        } else {
                            ps.remove(p);
                            p.x = map.template.defaultX;
                            p.y = map.template.defaultY;
                            if (p.getSession().type_admin > 0) {
                                p.x = 600;
                                p.y = 286;
                            }
                            map.waitGoInMap(p);
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            ++DunVDClass.maxZones[getTemplateId()];
            DunVDClass.maps.remove(dunHere);
            DunVDClass.zoneIds.add(zoneId);
        }).start();
    }

    @Override
    public boolean isMapCapcha() {
        return false;
    }

    @Override
    public boolean isGiaiDau() {
        return isDunVD;
    }

    @Override
    public boolean isGiaiDauNinjaDenhat() {
        return true;
    }

    public Player getWin1() {
        for (Player player : phe1) {
            if (player != null && player.getSession() != null) {
                return player;
            }
        }
        return null;
    }

    public Player getWin2() {
        for (Player player : phe2) {
            if (player != null && player.getSession() != null) {
                return player;
            }
        }
        return null;
    }

    public void sendCoinWin(Player player, int m, boolean isHoa) {
        if (isGiaDau) {
            if (isHoa) {
                ServerController.addPointLoiDaiClass(player, 1);
            } else {
                ServerController.addPointLoiDaiClass(player, 3);
            }
        }
        m -= m / 100;
        player.sendUpdateCoinBag(m);
        player.savezbLog("Thang cuoc", player.getXu() + "@" + m);
        money = 0;
        if (!isHoa) {
            String[] strs = {
                NJUtil.replace(Alert_VN.END_TEST_DUN1, player.name) + m + Alert_VN.END_TEST_DUN3,
                NJUtil.replace(Alert_EN.END_TEST_DUN1, player.name) + m + Alert_EN.END_TEST_DUN3
            };
            sendAlert(strs);
            if (player.dailytask != null && player.dailytask.template.checkTask(player, 2, 0)) {
                player.checkTaskOrder(player.dailytask, 1);
            }
        }
    }

    public void updateTaskPhe1() {
        for (Player pWin : phe1) {
            if (pWin.taskMain != null && pWin.taskMain.template.taskId == 42 && pWin.taskMain.index == 1) {
                pWin.checkTaskIndex();
            }
        }
    }

    public void updateTaskPhe2() {
        for (Player pWin : phe2) {
            if (pWin.taskMain != null && pWin.taskMain.template.taskId == 42 && pWin.taskMain.index == 1) {
                pWin.checkTaskIndex();
            }
        }
    }

    public static DunVDClass createDun(int mapTemplateId) {
        if (DunVDClass.zoneIds.size() == 0) {
            for (int i = 0; i < 100; ++i) {
                DunVDClass.zoneIds.add(i);
            }
        }
        if (DunVDClass.maxZones[mapTemplateId] > 0) {
            --DunVDClass.maxZones[mapTemplateId];
            return new DunVDClass(mapTemplateId);
        }
        return null;
    }
}
