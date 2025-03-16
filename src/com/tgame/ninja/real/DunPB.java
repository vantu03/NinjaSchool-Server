package com.tgame.ninja.real;

import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.NJUtil;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunPB extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunPB.class);

    public long timeEnd;

    public Vector<String> nameIns;

    public Vector<String> nameWaits;

    public static Hashtable<Integer, DunPB[]> dunPBs = new Hashtable<>();

    public boolean isOpen;

    public boolean isFinish;

    public int playerId;

    public int typeDun;

    public int loop;

    public boolean isHangOk;

    public Vector<Short> listHangDong95;

    public DunPB(int mapTemplateId, int playerId) {
        super(mapTemplateId);
        nameIns = null;
        nameWaits = null;
        this.playerId = -1;
        listHangDong95 = new Vector<>();
        isDunPB = true;
        this.playerId = playerId;
        zoneId = 0;
        template = ServerController.mapTemplates.get(mapTemplateId);
        timeEnd = System.currentTimeMillis() + NJUtil.getMilisByHour(1);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        new Thread(() -> {
            Thread.currentThread().setName("DUN PB " + mapId);
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
                    NJUtil.sendServer(p.getSession(), AlertFunction.CLOSE_DUN_PB(p.getSession()));
                    Map map = Map.findMap(p, p.mapTemplateIdGo);
                    if (map == null) {
                        map = Map.findMap(p, 22);
                    }
                    if (map == null) {
                        NJUtil.sleep(10);
                    } else {
                        ps.remove(p);
                        p.x = map.template.defaultX;
                        p.y = map.template.defaultY;
                        map.waitGoInMap(p);
                    }
                }
            }
            ++DunPB.maxZones[getTemplateId()];
            DunPB.dunPBs.remove(playerId);
        }).start();
    }

    @Override
    public boolean isDunPb9x() {
        return typeDun == 95;
    }

    @Override
    public void loadNpc() {
        super.loadNpc();
        if (getTemplateId() == 93) {
            for (Npc npc : npcs) {
                npc.status = Npc.STATUS_DIE;
                npc.timeWait = npc.timeReturn;
                npc.update(this, 2);
            }
        } else if (getTemplateId() == 97) {
            for (Npc npc : npcs) {
                if (npc.template.npcTemplateId == 89) {
                    npc.status = Npc.STATUS_DIE;
                    npc.timeWait = npc.timeReturn;
                    npc.update(this, 2);
                }
            }
        } else if (getTemplateId() == 109) {
            for (Npc npc : npcs) {
                if (npc.template.npcTemplateId == 105) {
                    npc.status = Npc.STATUS_DIE;
                    npc.timeWait = npc.timeReturn;
                    npc.update(this, 2);
                }
            }
        }
    }

    @Override
    public void updateNpc() {
        if (getTemplateId() == 157 || getTemplateId() == 158 || getTemplateId() == 159) {
            try {
                for (int i = npcs.size() - 1; i >= 0; --i) {
                    Npc npc = npcs.get(i);
                    if (npc.status == Npc.STATUS_DIE && npc.template.isBossId() && checkFinishHangDong95()) {
                        npcs.clear();
                        try {
                            DunPB[] arr = DunPB.dunPBs.get(playerId);
                            for (DunPB dunPB : arr) {
                                try {
                                    for (int j = 0; j < dunPB.nameIns.size(); ++j) {
                                        try {
                                            Player player = ServerController.hnPlayers.get(dunPB.nameIns.get(j));
                                            if (player != null && player.map.isDunPB) {
                                                player.pointPB += 30;
                                                player.sendPointPB();
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        } catch (Exception e) {
                        }
                        break;
                    }
                    npc.update(this, 4);
                }
                if (isFinish) {
                    DunPB[] arr = DunPB.dunPBs.get(playerId);
                    final int t = 60;
                    int timePB = (3600000 - (int) (arr[0].timeEnd - System.currentTimeMillis())) / 1000;
                    for (DunPB pb : arr) {
                        pb.timeEnd = System.currentTimeMillis() + t * 1000;
                    }
                    try {
                        DunPB dunPB = DunPB.dunPBs.get(playerId)[0];
                        for (int l = 0; l < dunPB.nameIns.size(); ++l) {
                            try {
                                Player player = ServerController.hnPlayers.get(dunPB.nameIns.get(l));
                                if (player != null && player.map.isDunPB) {
                                    if (player.typeNvbian == 2) {
                                        player.fibian();
                                    }
                                    /*if (player.isAddPointUyDanh) {
                                        player.addPointUydanh(12);
                                    }
                                    player.isAddPointUyDanh = false;*/
                                    player.addExpClan(10);
                                    player.timePB = timePB;
                                    player.friendPB = dunPB.nameIns.size();
                                    if (player.taskMain != null && player.taskMain.template.taskId == 39 && player.taskMain.index == 2) {
                                        player.checkTaskIndex();
                                    }
                                    player.sendMapTime(t);
                                    NJUtil.sendServer(player.getSession(), AlertFunction.DUN_PB_ALERT6(player.getSession()));
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                    }
                    isFinish = false;
                    isHangOk = false;
                }
            } catch (Exception e) {
            }
            return;
        }
        try {
            int countDie = 0;
            int countDie2 = 0;
            int countDie3 = 0;
            boolean is117 = false;
            boolean is118 = false;
            for (int i = npcs.size() - 1; i >= 0; --i) {
                Npc npc = npcs.get(i);
                if (npc.template.npcTemplateId == 117 && npc.maxhp > 100) {
                    is117 = true;
                }
                if (npc.template.npcTemplateId == 119 && npc.maxhp > 100) {
                    is118 = true;
                }
                if (npc.status == Npc.STATUS_DIE) {
                    if (npc.template.npcTemplateId == 120) {
                        ++countDie2;
                    }
                    if (npc.template.npcTemplateId == 121) {
                        ++countDie3;
                    }
                    ++countDie;
                } else {
                    npc.update(this, 0);
                }
            }
            if (countDie2 >= 27 && is117) {
                for (int i = npcs.size() - 1; i >= 0; --i) {
                    Npc npc = npcs.get(i);
                    if (npc.status != Npc.STATUS_DIE && npc.template.npcTemplateId == 117) {
                        int n = 100;
                        npc.hp = n;
                        npc.maxhp = n;
                        npc.dame = 100;
                    }
                }
            }
            if (countDie3 >= 25 && is118) {
                for (int i = npcs.size() - 1; i >= 0; --i) {
                    Npc npc = npcs.get(i);
                    if (npc.status != Npc.STATUS_DIE && npc.template.npcTemplateId == 119) {
                        int n = 100;
                        npc.hp = n;
                        npc.maxhp = n;
                        npc.dame = 100;
                    }
                }
            }
            if (isHangOk || (countDie == npcs.size() && npcs.size() > 0)) {
                DunPB[] arr = DunPB.dunPBs.get(playerId);
                if (isHangOk || (arr != null && arr[arr.length - 1].equals(this) && isOpen && !isFinish)) {
                    if (template.mapTemplateId == 128 && loop == 0) {
                        ++loop;
                        for (int i = npcs.size() - 1; i >= 0; --i) {
                            if (npcs.get(i).template.npcTemplateId == 138) {
                                npcs.get(i).timeWait = npcs.get(i).timeReturn;
                                npcs.get(i).update(this, 0);
                            }
                        }
                    } else if (template.mapTemplateId == 116) {
                        if (loop == 0 && players.size() > 0) {
                            if (players.firstElement().typeNvbian == 2) {
                                players.firstElement().fibian();
                            }
                            /*int point = 2;
                            if (typeDun == 45) {
                                point = 4;
                            } else if (typeDun == 55) {
                                point = 6;
                            } else if (typeDun == 65) {
                                point = 6;
                            } else if (typeDun == 75) {
                                point = 8;
                            } else if (typeDun == 95) {
                                point = 12;
                            }
                            if (players.firstElement().isAddPointUyDanh) {
                                players.firstElement().addPointUydanh(point);
                            }
                            players.firstElement().isAddPointUyDanh = false;*/
                            players.firstElement().addExpClan(10);
                            if (players.firstElement().taskMain != null && players.firstElement().taskMain.template.taskId == 39 && players.firstElement().taskMain.index == 2) {
                                players.firstElement().checkTaskIndex();
                            }
                        }
                        loop += 2;
                        for (int i = npcs.size() - 1; i >= 0; --i) {
                            Npc npc = npcs.get(i);
                            if (npc.status == Npc.STATUS_DIE) {
                                npc.timeWait = npc.timeReturn;
                                npc.update(this, 0);
                                int n = npc.hp + npc.hp * loop * 10 / 100;
                                npc.maxhp = n;
                                npc.hp = n;
                                npc.dame += npc.dame * loop * 10 / 100;
                            }
                        }
                    } else {
                        isFinish = true;
                        int t = 60;
                        int timePB = (3600000 - (int) (arr[0].timeEnd - System.currentTimeMillis())) / 1000;
                        for (DunPB pb : arr) {
                            pb.timeEnd = System.currentTimeMillis() + t * 1000;
                        }
                        try {
                            DunPB dunPB = DunPB.dunPBs.get(playerId)[0];
                            for (int l = 0; l < dunPB.nameIns.size(); ++l) {
                                try {
                                    Player p = ServerController.hnPlayers.get(dunPB.nameIns.get(l));
                                    if (p != null && p.map.isDunPB) {
                                        if (p.typeNvbian == 2) {
                                            p.fibian();
                                        }
                                        /*int point2 = 2;
                                        if (typeDun == 45) {
                                            point2 = 4;
                                        } else if (typeDun == 55) {
                                            point2 = 6;
                                        } else if (typeDun == 65) {
                                            point2 = 6;
                                        } else if (typeDun == 75) {
                                            point2 = 8;
                                        } else if (typeDun == 95) {
                                            point2 = 12;
                                        }
                                        if (p.isAddPointUyDanh) {
                                            p.addPointUydanh(point2);
                                        }
                                        p.isAddPointUyDanh = false;*/
                                        p.addExpClan(10);
                                        p.timePB = timePB;
                                        p.friendPB = dunPB.nameIns.size();
                                        if (p.taskMain != null && p.taskMain.template.taskId == 39 && p.taskMain.index == 2) {
                                            p.checkTaskIndex();
                                        }
                                        p.sendMapTime(t);
                                        NJUtil.sendServer(p.getSession(), AlertFunction.DUN_PB_ALERT6(p.getSession()));
                                    }
                                } catch (Exception e) {
                                }
                            }
                        } catch (Exception e) {
                        }
                        isHangOk = false;
                    }
                } else if (typeDun == 55) {
                    if (arr == null) {
                        return;
                    }
                    if (getTemplateId() == 105 && !arr[1].isOpen && !arr[2].isOpen && !arr[3].isOpen) {
                        arr[1].isOpen = true;
                        arr[2].isOpen = true;
                        arr[3].isOpen = true;
                        String[] strs = {
                            arr[1].template.getName()[0] + ", " + arr[2].template.getName()[0] + ", " + arr[3].template.getName()[0] + Alert_VN.PB_OPENED,
                            arr[1].template.getName()[1] + ", " + arr[2].template.getName()[1] + ", " + arr[3].template.getName()[1] + Alert_EN.PB_OPENED,
                        };
                        sendAlert(strs);
                    }
                    if ((getTemplateId() == 106 || getTemplateId() == 107 || getTemplateId() == 108) && !arr[4].isOpen && arr[1].isDie() && arr[2].isDie() && arr[3].isDie()) {
                        arr[4].isOpen = true;
                        String[] strs = {
                            arr[4].template.getName()[0] + Alert_VN.PB_OPENED,
                            arr[4].template.getName()[1] + Alert_EN.PB_OPENED,
                        };
                        sendAlert(strs);
                    }
                } else {
                    if (arr == null) {
                        return;
                    }
                    for (int i = 0; i < arr.length - 1; ++i) {
                        if (arr[i].equals(this) && !arr[i + 1].isOpen) {
                            if ((template.mapTemplateId == 115 || template.mapTemplateId == 116) && players.size() > 0) {
                                Player player = players.firstElement();
                                player.pointPB += 10 + NJUtil.randomNumber(20);
                            }
                            arr[i + 1].isOpen = true;
                            String[] strs2 = {
                                arr[i + 1].template.getName()[0] + Alert_VN.PB_OPENED,
                                arr[i + 1].template.getName()[1] + Alert_EN.PB_OPENED,
                            };
                            sendAlert(strs2);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public boolean checkFinishHangDong95() {
        //noinspection SynchronizeOnNonFinalField
        synchronized (listHangDong95) {
            if (getTemplateId() == 157 || getTemplateId() == 158 || getTemplateId() == 159) {
                if (listHangDong95.size() == 0) {
                    return (isFinish = true);
                }
                if (listHangDong95.get(0) == getTemplateId()) {
                    listHangDong95.remove(0);
                    for (Npc npc : npcs) {
                        if (npc.status != Npc.STATUS_DIE) {
                            npc.status = Npc.STATUS_DIE;
                            npcDie(npc, 0, false);
                        }
                    }
                    npcs.clear();
                    if (listHangDong95.size() == 0) {
                        return (isFinish = true);
                    }
                }
            }
        }
        return isFinish;
    }

    @Override
    public void sendAlert(String[] strs) {
        try {
            DunPB dunPB = DunPB.dunPBs.get(playerId)[0];
            for (int i = 0; i < dunPB.nameIns.size(); ++i) {
                try {
                    Player p = ServerController.hnPlayers.get(dunPB.nameIns.get(i));
                    if (p != null && p.map.isDunPB) {
                        p.sendServerMessage(strs);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public boolean isMapCapcha() {
        return false;
    }

    public DunPB findDunPB(int mapTemplateId) {
        DunPB[] arr = DunPB.dunPBs.get(playerId);
        for (DunPB dunPB : arr) {
            if (dunPB != null && dunPB.getTemplateId() == mapTemplateId) {
                return dunPB;
            }
        }
        return null;
    }

    public DunPB findDunPB() {
        return DunPB.dunPBs.get(playerId)[0];
    }

    public boolean isDie() {
        int countDie = 0;
        for (int i = npcs.size() - 1; i >= 0; --i) {
            if (npcs.get(i).status == Npc.STATUS_DIE) {
                ++countDie;
            }
        }
        return countDie == npcs.size();
    }

    public void sendAddExp(int expAdd, String meName) {
        try {
            DunPB dunPB = DunPB.dunPBs.get(playerId)[0];
            for (int i = 0; i < dunPB.nameIns.size(); ++i) {
                try {
                    Player p = ServerController.hnPlayers.get(dunPB.nameIns.get(i));
                    if (p != null && p.map.isDunPB && !p.name.equals(meName)) {
                        p.sendUpdateExp(expAdd, true);
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static DunPB createDun(int mapTemplateId, int playerId) {
        --DunPB.maxZones[mapTemplateId];
        return new DunPB(mapTemplateId, playerId);
    }

    public static int openDun(int typeDun, Player player) {
        Hashtable<Integer, DunPB[]> pbs = new Hashtable<>(DunPB.dunPBs);
        for (int pId : pbs.keySet()) {
            try {
                DunPB[] arrPB = DunPB.dunPBs.get(pId);
                int time = (int) (arrPB[0].timeEnd / 1000L - System.currentTimeMillis() / 1000L);
                if (arrPB[0].nameIns.contains(player.name)) {
                    player.mapClear();
                    player.map.goOutMap(player);
                    player.x = 0;
                    player.y = 0;
                    player.sendMapTime(time);
                    player.sendPointPB();
                    arrPB[0].waitGoInMap(player);
                    return 3;
                }
                if (!arrPB[0].nameWaits.contains(player.name)) {
                    continue;
                }
                if (player.countPB <= 0 && player.getSession().type_admin < Player.ROLE_GM) {
                    return 1;
                }
                --player.countPB;
                player.pointPB = 0;
                player.timePB = 0;
                player.friendPB = 1;
                player.mapClear();
                player.map.goOutMap(player);
                player.x = 0;
                player.y = 0;
                player.sendMapTime(time);
                player.sendPointPB();
                arrPB[0].waitGoInMap(player);
                arrPB[0].nameIns.add(player.name);
                return 3;
            } catch (Exception e) {
            }
        }
        if (player.countPB <= 0 && player.getSession().type_admin < Player.ROLE_GM) {
            return 1;
        }
        if (player.party != null && !player.party.isTeamLeader(player)) {
            return 2;
        }
        DunPB[] arr = null;
        switch (typeDun) {
            case 35: {
                if (DunPB.maxZones[91] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 30 || player.party.players.get(i).level >= 40) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                arr = new DunPB[]{ createDun(91, player.playerId), createDun(92, player.playerId), createDun(93, player.playerId) };
                break;
            }
            case 45: {
                if (DunPB.maxZones[94] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 40 || player.party.players.get(i).level >= 50) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                arr = new DunPB[]{ createDun(94, player.playerId), createDun(95, player.playerId), createDun(96, player.playerId), createDun(97, player.playerId) };
                break;
            }
            case 55: {
                if (DunPB.maxZones[105] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 50 || player.party.players.get(i).level >= 60) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                arr = new DunPB[]{ createDun(105, player.playerId), createDun(106, player.playerId), createDun(107, player.playerId), createDun(108, player.playerId), createDun(109, player.playerId) };
                break;
            }
            case 65: {
                if (DunPB.maxZones[114] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 60 || player.party.players.get(i).level >= 70) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                arr = new DunPB[]{ createDun(114, player.playerId), createDun(115, player.playerId), createDun(116, player.playerId) };
                break;
            }
            case 75: {
                if (DunPB.maxZones[125] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 70) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                arr = new DunPB[]{ createDun(125, player.playerId), createDun(126, player.playerId), createDun(127, player.playerId), createDun(128, player.playerId) };
                break;
            }
            case 95: {
                if (DunPB.maxZones[157] <= 0) {
                    return 0;
                }
                if (player.party != null) {
                    for (int i = 0; i < player.party.players.size(); ++i) {
                        if (player.party.players.get(i).countPB < 0 || player.party.players.get(i).level < 90) {
                            player.sendOpenUISay(0, player.party.players.get(i).name + AlertFunction.DUN_PB_ALERT7(player.getSession()));
                            return 3;
                        }
                    }
                }
                Vector<Short> abc = new Vector<>();
                abc.add((short) 157);
                abc.add((short) 158);
                abc.add((short) 159);
                Vector<Short> listHangDong95 = new Vector<>();
                for (int i = 0; i < 3; ++i) {
                    listHangDong95.add(abc.remove(NJUtil.random.nextInt(abc.size())));
                }
                LOGGER.info("thu tu ket thuc pb: " + listHangDong95.get(0) + " >> " + listHangDong95.get(1) + " >> " + listHangDong95.get(2));
                arr = new DunPB[]{ createDun(157, player.playerId), createDun(158, player.playerId), createDun(159, player.playerId) };
                for (int i = 0; i < 3; ++i) {
                    arr[i].listHangDong95 = listHangDong95;
                }
                break;
            }
        }
        if (arr != null) {
            for (DunPB dunPB : arr) {
                dunPB.typeDun = typeDun;
            }
            int time = 3600;
            long timeEnd = System.currentTimeMillis() + time * 1000;
            for (int k = 0; k < arr.length; ++k) {
                arr[k].timeEnd = timeEnd;
                if (typeDun == 95) {
                    arr[k].isOpen = true;
                } else if (k == 0) {
                    arr[k].isOpen = true;
                }
            }
            player.mapClear();
            player.map.goOutMap(player);
            player.x = 0;
            player.y = 0;
            player.sendMapTime(time);
            arr[0].waitGoInMap(player);
            arr[0].nameIns = new Vector<>();
            arr[0].nameWaits = new Vector<>();
            arr[0].nameIns.add(player.name);
            if (player.party == null) {
                arr[0].nameWaits.add(player.name);
            } else {
                for (int k = 0; k < player.party.players.size(); ++k) {
                    Player p = player.party.players.get(k);
                    arr[0].nameWaits.add(p.name);
                    if (!player.name.equals(p.name)) {
                        NJUtil.sendServer(p.getSession(), player.name + AlertFunction.DUN_PB_ALERT4(p.getSession()) + " " + AlertFunction.DUN_LEVEL(p.getSession()) + " " + typeDun + AlertFunction.DUN_PB_ALERT5(p.getSession()));
                    }
                }
            }
            --player.countPB;
            player.pointPB = 0;
            player.timePB = 0;
            player.friendPB = 1;
            player.sendPointPB();
            DunPB.dunPBs.put(player.playerId, arr);
        }
        return 4;
    }
}
