package com.tgame.ninja.real;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DunArena extends Map {

    private static final Logger LOGGER = LoggerFactory.getLogger(DunArena.class);

    public static short ID_ZONE = 0;

    public static Hashtable<Integer, DunArena> maps = new Hashtable<>();

    public int isStart;

    public byte isFinish;

    public String nameLose;

    public String nameShadow;

    public Vector<Player> phe1;

    public Vector<Player> phe2;

    public DunArena(int mapTemplateId) {
        super(mapTemplateId);
        nameShadow = "";
        nameLose = "";
        isFinish = 0;
        phe1 = new Vector<>();
        phe2 = new Vector<>();
        phe1 = new Vector<>();
        phe2 = new Vector<>();
        short id_ZONE = ID_ZONE;
        ID_ZONE = (short) (id_ZONE + 1);
        zoneId = id_ZONE;
        maps.put(zoneId, this);
        template = ServerController.mapTemplates.get(mapTemplateId);
        timeEnd = System.currentTimeMillis() + NJUtil.getMilisByHour(2);
        for (short i = 0; i < 1000; ++i) {
            itemMapIds.add(i);
        }
        messages = new LinkedBlockingQueue<>();
        new Thread(() -> {
            Thread.currentThread().setName("DUN ARENA " + mapId);
            try {
                while (timeEnd - System.currentTimeMillis() > 0L) {
                    if (isStart < 50) {
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
                    boolean isStop = false;
                    try {
                        while (playerIns.size() > 0) {
                            goInMap(playerIns.remove(0));
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    try {
                        while (playerOuts.size() > 0) {
                            Player p = playerOuts.remove(0);
                            goOutMap(p);
                            if (p.name.equals(phe1.get(0).name)) {
                                isStop = true;
                            }
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    if (isFinish == 2 || isStop) {
                        if (isStop) {
                            LOGGER.info("STOP");
                        }
                        break;
                    }
                    long l1 = System.currentTimeMillis();
                    if (l1 - lastTime > 100L) {
                        updateNpc();
                        updateBuNhin();
                        updateItemMap();
                        updatePlayer();
                        if (isStart >= 50) {
                            if (phe1.size() == 0 || phe1.get(0).getSession() == null) {
                                break;
                            }
                            if (isFinish == 1) {
                                isFinish = 2;
                                Player player1 = phe1.get(0);
                                Player player2 = phe2.get(0);
                                PlayerArenaInfo playerArena1 = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(player1.name);
                                PlayerArenaInfo playerArena2 = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(player2.name);
                                if (player1.name.equals(nameLose)) {
                                    NJUtil.sendServer(player1.getSession(), "Bạn đã thua");
                                    playerArena1.coutwin = 0;
                                    playerArena1.timeWin = -1L;
                                    playerArena1.timesPlay = 0;
                                } else {
                                    boolean isthanghang = false;
                                    if (playerArena1.rank > playerArena2.rank) {
                                        playerArena1.rank = playerArena2.rank;
                                        playerArena2.rank = playerArena1.rank;
                                        isthanghang = true;
                                        if (playerArena1.level < 80) {
                                            PlayerArenaInfo.ALL_PLAYER_ARENA_DIA.setElementAt(playerArena1, playerArena2.rank);
                                            PlayerArenaInfo.ALL_PLAYER_ARENA_DIA.setElementAt(playerArena2, playerArena1.rank);
                                        } else {
                                            PlayerArenaInfo.ALL_PLAYER_ARENA_THIEN.setElementAt(playerArena1, playerArena2.rank);
                                            PlayerArenaInfo.ALL_PLAYER_ARENA_THIEN.setElementAt(playerArena2, playerArena1.rank);
                                        }
                                        if (playerArena1.rank == 0) {
                                            String info = playerArena1.name + " đã chiến thắng " + playerArena2.name + ". " + playerArena1.name + " trở thành ông trùm của " + ((playerArena1.isGroup == 0) ? "Địa bảng" : "Thiên bảng");
                                            Player.sendAllCharServer(Player.createMSgCharServer("Admin", info));
                                        }
                                        if (playerArena1.isGroup == 1) {
                                            ++playerArena1.coutwin;
                                            long time = System.currentTimeMillis() - playerArena1.timeWin;
                                            if (playerArena1.coutwin == 10) {
                                                if (time < 1800000L) {
                                                    String info2 = playerArena1.name + " đã liên tiếp hạ gục 10 nhẫn giả. Ai cản " + playerArena1.name + " lại đi.";
                                                    Player.sendAllCharServer(Player.createMSgCharServer("Admin", info2));
                                                } else {
                                                    playerArena1.coutwin = 0;
                                                    playerArena1.timeWin = -1L;
                                                }
                                            } else if (playerArena1.coutwin == 20) {
                                                if (time < 3600000L) {
                                                    String info2 = playerArena1.name + " đã liên tiếp hạ gục 20 nhẫn giả. " + playerArena1.name + " bắt đầu hư cấu!";
                                                    Player.sendAllCharServer(Player.createMSgCharServer("Admin", info2));
                                                } else {
                                                    playerArena1.coutwin = 0;
                                                    playerArena1.timeWin = -1L;
                                                }
                                            } else if (playerArena1.coutwin == 50) {
                                                if (time < 9000000L) {
                                                    String info2 = playerArena1.name + " đã liên tiếp hạ gục 50 nhẫn giả. " + playerArena1.name + " đã không thể ngăn cản!";
                                                    Player.sendAllCharServer(Player.createMSgCharServer("Admin", info2));
                                                }
                                                playerArena1.coutwin = 0;
                                                playerArena1.timeWin = -1L;
                                            }
                                        }
                                    }
                                    NJUtil.sendServer(player1.getSession(), "Bạn đã thắng" + (isthanghang ? (" và được thăng lên hạng " + (playerArena2.rank + 1)) : ""));
                                    Database.updateRankPlayerArena(playerArena1);
                                    Database.updateRankPlayerArena(playerArena2);
                                    if (player1.dailytask != null && player1.dailytask.template.checkTask(player1, 2, 1)) {
                                        player1.checkTaskOrder(player1.dailytask, 1);
                                    }
                                }
                                playerArena1.isPlaying = false;
                                playerArena2.isPlaying = false;
                                playerArena1.nameThachdau = PlayerArenaInfo.thachdau;
                                playerArena2.nameThachdau = PlayerArenaInfo.thachdau;
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
                            LOCK.wait(200L);
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
                    if (p.playerId < 0 || p.isNhanban() || p.isPlayerArena()) {
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
                            if (p.mapTemplateId_focus != 138 && p.mapTemplateId_focus != 137) {
                                map = Map.find(p, p.mapTemplateId_focus);
                            } else {
                                map = Map.find(p, 22);
                            }
                        }
                        if (!p.isNhanban() && !p.isPlayerArena() && p.getSession().type_admin > 0) {
                            map = Map.find(p, 1);
                        }
                        if (map == null) {
                            try {
                                synchronized (LOCK) {
                                    LOCK.wait(100L);
                                }
                            } catch (Exception e) {
                                LOGGER.error("", e);
                                p.goReturn();
                            }
                        }
                        ps.remove(p);
                        if (map != null) {
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
            try {
                Player p1 = phe1.get(0);
                Player p2 = phe2.get(0);
                PlayerArenaInfo pp1 = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(p1.name.toLowerCase());
                PlayerArenaInfo pp2 = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(p2.name.toLowerCase());
                if (pp1 != null) {
                    pp1.isPlaying = false;
                    pp1.nameThachdau = PlayerArenaInfo.thachdau;
                }
                if (pp2 != null) {
                    pp2.isPlaying = false;
                    pp2.nameThachdau = PlayerArenaInfo.thachdau;
                }
            } catch (Exception e) {
            }
            phe1.clear();
            phe2.clear();
            maps.remove(zoneId);
        }).start();
    }

    public DunArena(int id, int id2, MapTemplate mapTemplate) {
        super(id, id2, mapTemplate);
        nameShadow = "";
        nameLose = "";
        isFinish = 0;
        phe1 = new Vector<>();
        phe2 = new Vector<>();
    }

    @Override
    public Vector<Player> getPhe1() {
        return phe1;
    }

    @Override
    public Vector<Player> getPhe2() {
        return phe2;
    }

    @Override
    public boolean isArena() {
        return true;
    }

    @Override
    public void setNamePlayerLose(String name) {
        if (nameLose.equals("")) {
            nameLose = name;
            isFinish = 1;
        }
    }

    @Override
    public void addPlayerPhe1(Player p) {
        phe1.add(p);
    }

    @Override
    public void addPlayerPhe2(Player p) {
        phe2.add(p);
    }

    @Override
    public boolean isMapCapcha() {
        return false;
    }

    @Override
    public void updatePlayer() {
        try {
            long timeNow = System.currentTimeMillis();
            for (int size = players.size(), i = 0; i < size; ++i) {
                try {
                    Player p = players.get(i);
                    if (p.playerId < 0 && !p.isNhanban()) {
                        if (p.headId == 164 && NJUtil.probability(1, 100) == 0) {
                            Message m = new Message(Cmd.CHAT_MAP);
                            m.writeInt(p.playerId);
                            m.writeUTF("Y ...a... y... a");
                            sendToPlayer(m);
                        }
                        if (p.timeUpdate == 0) {
                            p.timeUpdate = 4;
                            if (getTemplateId() == 33 && p.playerId < 0 && p.headId == 96) {
                                int dx = NJUtil.distance(p.x, p.owner.x);
                                int dy = NJUtil.distance(p.x, p.owner.x);
                                if (dx > 400 || dy > 400 || p.map.mapId != p.owner.map.mapId) {
                                    p.sendTaskFollowFail(p.owner, 1);
                                    playerOuts.add(p);
                                } else if (p.x < 48) {
                                    p.owner.doTaskNext();
                                    playerOuts.add(p);
                                } else {
                                    p.x -= (short) (20 + NJUtil.randomNumber(30));
                                    if (p.x <= 26) {
                                        p.x = 26;
                                    }
                                    p.sendMove();
                                }
                            }
                        } else {
                            --p.timeUpdate;
                        }
                    } else if (!p.map.equals(this)) {
                        playerOuts.add(p);
                    } else {
                        p.update();
                        int itt = p.countItemBag(458);
                        if (itt > 0 && tick % 2 == 0 && !p.isControlCharNhanBan()) {
                            if (itt > 7) {
                                itt = 7;
                            }
                            int[] ts = { 0, 100, 200, 400, 800, 1600, 3200, 6400 };
                            int dd = ts[itt];
                            if (dd >= p.getHp() - 1) {
                                dd = p.getPlayerMainControl().hp - 1;
                            }
                            p.updateFastHp(-dd);
                        }
                        p.showAlert();
                        if (p.itemMapPick != null) {
                            if (p.timeNhat > 1) {
                                --p.timeNhat;
                            } else if (p.timeNhat == 1) {
                                p.timeNhat = 0;
                                if (itemMaps.contains(p.itemMapPick)) {
                                    if (!p.isControlCharNhanBan() && p.taskMain != null && p.getTaskFinish() == 34 && p.taskMain.index == 1) {
                                        p.checkTaskIndex();
                                    }
                                    p.doPickItemMap(p.itemMapPick);
                                }
                                p.itemMapPick = null;
                                p.endWait(null);
                            }
                        }
                        if (p.getHp() <= 0 && p.status != Player.ME_DIE) {
                            p.checkDie(-1);
                        }
                        if (p.status == Player.ME_DIE && isHaveBoss) {
                            ++p.countDie;
                            if (p.countDie > 100) {
                                p.doBack("DUN ARENA");
                            }
                        } else {
                            p.countDie = 0;
                        }
                        if (p.getPlayerMainControl().timeMoveUp > 0) {
                            --p.getPlayerMainControl().timeMoveUp;
                            if (p.getPlayerMainControl().timeMoveUp == 1) {
                                p.getPlayerMainControl().timeMoveUp = 0;
                                p.resetPoint1(1000);
                                continue;
                            }
                        }
                        if (p.isCheckTile()) {
                            LocalDate dateNow = LocalDate.now();
                            LocalDate dLogin = NJUtil.getDateByMilis(p.login_date);
                            if (!p.isControlCharNhanBan() &&
                                !p.isNhanban() &&
                                DunArena.isTongKet &&
                                template.isMapChienTruong() &&
                                template.mapTemplateId != 98 &&
                                template.mapTemplateId != 104
                            ) {
                                p.backHomeChienTruong();
                            }
                            if (dateNow.getDayOfMonth() != dLogin.getDayOfMonth() || dateNow.getMonthValue() != dLogin.getMonthValue() || dateNow.getYear() != dLogin.getYear()) {
                                if (dateNow.getDayOfMonth() == 1) {
                                    ServerController.topTaiNang.clear();
                                    for (int mm = 0; mm < ServerController.topTaiNangClass.size(); ++mm) {
                                        ServerController.topTaiNangClass.get(mm).clear();
                                    }
                                }
                                p.login_date = timeNow;
                                p.pointUyDanh -= 1 + p.pointUyDanh / 50;
                                if (p.pointUyDanh <= 0) {
                                    p.pointUyDanh = 0;
                                }
                                p.countFinishDay = 0;
                                p.timeOnline = 0;
                                p.countLoopDay = Player.maxLoopDay;
                                p.pointOpenTui = Player.maxOpenTui;
                                p.countctkeo = Player.maxCTKeo;
                                p.countNvbian = Player.maxNVBA;
                                p.countChatAdmin = Player.maxChatAdm;
                                p.countPB = Player.maxPhoBan;
                                p.countUseTTL = Player.defaultCountTTL;
                                p.countUseHDL = Player.defaultCountHDL;
                                p.pointPB = 0;
                                p.timePB = 0;
                                p.friendPB = 0;
                                p.countLoopBoos += 2;
                                if (p.countLoopBoos > Player.maxLoopBoss) {
                                    p.countLoopBoos = Player.maxLoopBoss;
                                }
                                p.checkThuCuoi(1);
                                p.loadThuCuoi(1);
                            }
                            if (NJUtil.isNewWeek(dateNow, dLogin)) {
                                if (p.isChangeCoin) {
                                    p.isChangeCoin = false;
                                }
                                p.pointClanWeek = 0;
                            }
                            if (!template.isMapChienTruong() && p.isAutoSave) {
                                //p.isAutoSave = true;
                                p.savezaLog("Tu dong save");
                                if (p.isLockChat) {
                                    Database.saveLogAll(p.name, Player.getInfoFromVectorString(p.infoChat), "logchat");
                                    p.isLockChat = false;
                                }
                                Database.savePlayer(p, getTemplateId());
                            } else {
                                p.isAutoSave = false;
                            }
                            if (((p.getSession() == null && !p.isPlayerArena()) || ServerController.huPlayers.get(p.userId) == null) && !p.isNhanban() && !p.isPlayerArena()) {
                                playerOuts.add(p);
                            } else if (timeNow - p.timeActive >= 1000000L && !p.isControlCharNhanBan() && !p.isPlayerArena()) {
                                if (p.playerId >= 0) {
                                    p.getSession().disconnect("DunArena.updatePlayer");
                                }
                            } else {
                                if (p.timeReturnMap > 1) {
                                    --p.timeReturnMap;
                                }
                                if (getTemplateId() != 110 &&
                                    p.testSkillDun != null &&
                                    timeNow - p.testSkillDun.timeEnd > 0L &&
                                    !p.isControlCharNhanBan()
                                ) {
                                    p.testSkillDun = null;
                                }
                                if (getTemplateId() != 117 &&
                                    p.testGTDun != null &&
                                    timeNow - p.testGTDun.timeEnd > 0L &&
                                    !p.isControlCharNhanBan()
                                ) {
                                    p.testGTDun = null;
                                }
                                if (p.testSkill != null && timeNow - p.testSkill.timeEnd > 0L) {
                                    Player p1 = p.testSkill.player1;
                                    Player p2 = p.testSkill.player2;
                                    if (p1 != null && p2 != null) {
                                        p1.testSkill = null;
                                        p2.testSkill = null;
                                        p1.sendTestEndSkill(p1.playerId, p2.playerId);
                                    } else {
                                        p.testSkill = null;
                                    }
                                }
                                if (p.killPlayer != null && timeNow - p.killPlayer.timeEnd > 0L && !p.isControlCharNhanBan()) {
                                    Message message = new Message(Cmd.CLEAR_CUU_SAT);
                                    NJUtil.sendMessage(p.getSession(), message);
                                    message = new Message(Cmd.CLEAR_CUU_SAT);
                                    message.writeInt(p.playerId);
                                    NJUtil.sendMessage(p.killPlayer.player.getSession(), message);
                                    p.killPlayer = null;
                                }
                                if (p.inviteClan != null) {
                                    if (p.inviteClan.timeInvite > 0) {
                                        InviteClan inviteClan = p.inviteClan;
                                        --inviteClan.timeInvite;
                                    } else {
                                        p.inviteClan = null;
                                    }
                                }
                                if (p.pleaseClan != null) {
                                    if (p.pleaseClan.timeInvite > 0) {
                                        --p.pleaseClan.timeInvite;
                                    } else {
                                        p.pleaseClan = null;
                                    }
                                }
                                if ((p.tradeSet != null && timeNow - p.tradeSet.timeStart > 30000L) || p.isControlCharNhanBan()) {
                                    p.tradeSet = null;
                                }
                                if ((p.tradeGet != null && timeNow - p.tradeGet.timeStart > 30000L) || p.isControlCharNhanBan()) {
                                    p.tradeGet = null;
                                }
                                if (p.party != null && p.party.isTeamLeader(p)) {
                                    for (int j = 0; j < p.party.times.length; ++j) {
                                        if (p.party.times[j] > 0) {
                                            byte[] times = p.party.times;
                                            --times[j];
                                            if (p.party.times[j] == 0) {
                                                p.party.ids[j] = -1;
                                                p.party.times[j] = 0;
                                            }
                                        }
                                        if (p.party.timePleases[j] > 0) {
                                            byte[] timePleases = p.party.timePleases;
                                            --timePleases[j];
                                            if (p.party.timePleases[j] == 0) {
                                                p.party.idPleases[j] = -1;
                                                p.party.timePleases[j] = 0;
                                            }
                                        }
                                    }
                                }
                                if (p.sumonHide != null && p.sumonHide.attNpcs != null && tick % 2 == 0) {
                                    for (int j = p.sumonHide.attNpcs.size() - 1; j >= 0; --j) {
                                        Npc npc = p.sumonHide.attNpcs.get(j);
                                        int ndx = NJUtil.distance(p.x, npc.pointx);
                                        int ndy = NJUtil.distance(p.y, npc.pointy);
                                        if (npc.status != Npc.STATUS_DIE) {
                                            if (ndx < p.sumonHide.rangeX && ndy < p.sumonHide.rangeY) {
                                                Message message2 = NJUtil.messageSubCommand(Cmd.CALL_EFFECT_NPC);
                                                message2.writeByte(npc.npcId);
                                                p.sendToPlayer(message2, true);
                                                p.isHit(npc, p.sumonHide.dame - p.sumonHide.dame * NJUtil.randomNumber(11) / 100, false, true, 0, 0, 0);
                                            } else if (ndx >= p.sumonHide.rangeX * 3 && ndy >= p.sumonHide.rangeY * 3) {
                                                p.sumonHide.attNpcs.remove(j);
                                            }
                                        } else {
                                            p.sumonHide.attNpcs.remove(j);
                                        }
                                    }
                                }
                                if (p.status != Player.ME_DIE &&
                                    p.getPlayerMainControl().sumon != null &&
                                    p.getPlayerMainControl().sumon.npcAttId >= 0 &&
                                    p.getPlayerMainControl().sumon.npcAttId < npcs.size() &&
                                    p.getPlayerMainControl().sumon.isSumonAttack()
                                ) {
                                    if (p.getPlayerMainControl().sumon.timeAtt <= 0) {
                                        Npc npc = npcs.get(p.getPlayerMainControl().sumon.npcAttId);
                                        int ndx2 = NJUtil.distance(p.x, npc.pointx);
                                        int ndy2 = NJUtil.distance(p.y, npc.pointy);
                                        if (npc.status != Npc.STATUS_DIE && ndx2 < p.getPlayerMainControl().sumon.rangeX && ndy2 < p.getPlayerMainControl().sumon.rangeY) {
                                            p.getPlayerMainControl().sumon.timeAtt = p.getPlayerMainControl().sumon.template.waitAttack;
                                            Message message3 = new Message(Cmd.SUMON_ATTACK);
                                            message3.writeInt(p.playerId);
                                            message3.writeByte(npc.npcId);
                                            message3.writeShort(p.getPlayerMainControl().sumon.getIdSkillAttack());
                                            message3.writeByte(p.getPlayerMainControl().sumon.getIndexFrameAttack());
                                            message3.writeByte(p.getPlayerMainControl().sumon.getTypeAttack());
                                            message3.writeByte(0);
                                            message3.writeInt(-1);
                                            sendToPlayer(message3);
                                            p.isHit(npc, p.getPlayerMainControl().sumon.dame - p.getPlayerMainControl().sumon.dame * NJUtil.randomNumber(11) / 100, false, true, 0, 0, 0);
                                        } else if (npc.status == Npc.STATUS_DIE || ndx2 < p.getPlayerMainControl().sumon.rangeX * 2 || ndy2 < p.getPlayerMainControl().sumon.rangeY * 2) {
                                            p.getPlayerMainControl().sumon.npcAttId = -1;
                                        }
                                    } else {
                                        --p.getPlayerMainControl().sumon.timeAtt;
                                    }
                                }
                                if (tick % 10 == 0 && p.status == Player.ME_NORMAL) {
                                    p.getPlayerMainControl().hp += p.getPlayerMainControl().getEff5BuffHp();
                                    p.getPlayerMainControl().mp += p.getPlayerMainControl().getEff5BuffMp();
                                }
                                if (p.getSession() != null && p.getSession().clientType == GameServer.CLIENT_JAVA) {
                                    if (p.countHackXa > 5) {
                                        continue;
                                    }
                                    if (p.countHackMove > 5) {
                                        continue;
                                    }
                                }
                                if (tick % 5 == 0) {
                                    p.countHackMove = 0;
                                }
                                p.getPlayerMainControl().addMaxHp = 0;
                                if (p.getPlayerMainControl().effects.size() > 0) {
                                    for (int j = 0; j < p.getPlayerMainControl().effects.size(); ++j) {
                                        Effect eff = p.getPlayerMainControl().effects.get(j);
                                        if (p.status == Player.ME_NORMAL && (eff.template.type == 0 || eff.template.type == 12)) {
                                            p.getPlayerMainControl().hp += eff.param;
                                            p.getPlayerMainControl().mp += eff.param;
                                            if (p.getHp() >= p.getFullHp()) {
                                                p.getPlayerMainControl().hp = p.getFullHp();
                                            }
                                            if (p.getMp() >= p.getFullMp()) {
                                                p.getPlayerMainControl().mp = p.getFullMp();
                                            }
                                        } else if (p.getPlayerMainControl().status == Player.ME_NORMAL && (eff.template.type == 4 || eff.template.type == 17 || eff.template.type == 1)) {
                                            if (eff.template.type == 1) {
                                                p.getPlayerMainControl().hp += p.getPlayerMainControl().autoUpHp;
                                            } else {
                                                p.getPlayerMainControl().hp += eff.param;
                                            }
                                            if (p.getHp() >= p.getFullHp()) {
                                                p.getPlayerMainControl().hp = p.getFullHp();
                                            }
                                        } else if (p.status == Player.ME_NORMAL && eff.template.type == 13) {
                                            p.getPlayerMainControl().hp -= p.getFullHp() * 3 / 100;
                                            p.checkDie(-1);
                                            if (p.getPlayerMainControl().status == Player.ME_DIE) {
                                                eff.timeLength = 0;
                                            }
                                        } else if (eff.template.type == 24) {
                                            p.getPlayerMainControl().addMaxHp = eff.param;
                                        }
                                        long timeCheck = eff.timeStart * 1000L + eff.timeLength;
                                        if (eff.timeStart != -1 && timeNow > timeCheck) {
                                            p.removeEffect(eff, true);
                                        }
                                    }
                                }
                                if (p.timeWait == 1) {
                                    boolean isCloseWait = false;
                                    if (p.status != Player.ME_DIE) {
                                        if (!p.isControlCharNhanBan() &&
                                            p.taskMain != null &&
                                            p.itemWait != null &&
                                            p.itemWait.getTemplateId() == 219
                                        ) {
                                            if ((p.taskMain.template.taskId == 19 || p.taskMain.template.taskId == 35) && p.taskMain.index == 1) {
                                                p.sendUseItemUpToUp(p.itemWait.indexUI, 1);
                                                p.useItemUpToUp(p.itemWait);
                                                Item it = p.findItemBag(220);
                                                if (it != null) {
                                                    ++it.quantity;
                                                    int quantity = p.countItemBag(220);
                                                    p.taskMain.count = (short) quantity;
                                                    //Database.updateItem(it);
                                                    p.sendUpdateItemBag(it.indexUI, 1);
                                                    if (quantity == p.taskMain.template.counts[p.taskMain.index]) {
                                                        p.doTaskNext();
                                                    } else {
                                                        p.doTaskUpdate(p.taskMain.count);
                                                    }
                                                } else if (p.addItemToBagNoDialog(new Item(220, true, "DunArena"))) {
                                                    p.doTaskUpdate(p.taskMain.count = 1);
                                                } else {
                                                    NJUtil.sendServer(p.getSession(), AlertFunction.EMPTY_ONE_WATER(p.getSession()));
                                                }
                                            }
                                            isCloseWait = true;
                                            p.endWait(null);
                                        } else if (!p.isControlCharNhanBan() &&
                                            p.taskMain != null &&
                                            p.itemWait != null &&
                                            (p.itemWait.getTemplateId() == 233 || p.itemWait.getTemplateId() == 234 || p.itemWait.getTemplateId() == 235)
                                        ) {
                                            if (!p.isControlCharNhanBan() && p.taskMain.template.taskId == 24 && p.taskMain.index == 1) {
                                                if (NJUtil.randomBoolean()) {
                                                    p.updateUseDiaDo(p.itemWait);
                                                    isCloseWait = true;
                                                    p.endWait(null);
                                                } else {
                                                    isCloseWait = true;
                                                    p.endWait(AlertFunction.ITEM_HERE(p.getSession()));
                                                }
                                            }
                                        } else if (!p.isControlCharNhanBan() &&
                                            p.taskMain != null &&
                                            p.itemMapWait != null &&
                                            p.itemMapWait.item.getTemplateId() == 236
                                        ) {
                                            if (!p.isControlCharNhanBan() && p.taskMain.template.taskId == 26 && p.taskMain.index == 1) {
                                                p.doPickItemMap(p.itemMapWait);
                                                isCloseWait = true;
                                                p.endWait(null);
                                            }
                                        } else if (!p.isControlCharNhanBan() &&
                                            p.taskMain != null &&
                                            p.itemWait != null &&
                                            p.itemWait.getTemplateId() == 266 &&
                                            !p.isControlCharNhanBan() &&
                                            p.taskMain.template.taskId == 32
                                        ) {
                                            if (p.taskMain.index == 1) {
                                                if (NJUtil.probability(1, 100) == 0) {
                                                    p.removeItem(p.itemWait.indexUI);
                                                    p.sendClearItemBag(p.itemWait.indexUI);
                                                    p.addItemToBagNoDialog(new Item(267, true, "DunArena 1"));
                                                    p.endWait(null);
                                                    p.doTaskNext();
                                                } else {
                                                    p.removeItem(p.itemWait.indexUI);
                                                    p.sendClearItemBag(p.itemWait.indexUI);
                                                    p.endWait(AlertFunction.NOT_FOUND(p.getSession()));
                                                }
                                            } else {
                                                p.endWait(null);
                                            }
                                        }
                                    }
                                    if (!isCloseWait) {
                                        p.endWait(null);
                                    }
                                } else if (p.timeWait > 1) {
                                    --p.timeWait;
                                    if (p.status == Player.ME_DIE) {
                                        p.endWait(null);
                                    }
                                }
                                for (int j = 0; j < p.itemBags.length; ++j) {
                                    if (p.itemBags[j] != null && p.itemBags[j].expires != -1L && p.itemBags[j].expires < timeNow) {
                                        p.sendClearItemBag(p.itemBags[j].indexUI);
                                        p.removeItem(p.itemBags[j], 3);
                                    }
                                }
                                for (int j = 0; j < p.itemBoxs.length; ++j) {
                                    if (p.itemBoxs[j] != null && p.itemBoxs[j].expires != -1L && p.itemBoxs[j].expires < timeNow) {
                                        p.sendClearItemBox(p.itemBoxs[j].indexUI);
                                        p.removeItem(p.itemBoxs[j], 4);
                                    }
                                }
                                for (int j = 0; j < p.getPlayerMainControl().itemBodys.length; ++j) {
                                    if (p.getPlayerMainControl().itemBodys[j] != null && p.getPlayerMainControl().itemBodys[j].expires != -1L && p.getPlayerMainControl().itemBodys[j].expires < timeNow) {
                                        p.sendClearItemBody(p.getPlayerMainControl().itemBodys[j].indexUI);
                                        p.removeItem(p.getPlayerMainControl().itemBodys[j], 5);
                                        p.getPlayerMainControl().updateData();
                                        p.playerLoadInfo();
                                    }
                                }
                                if (p.getHp() > p.getFullHp()) {
                                    p.getPlayerMainControl().hp = p.getFullHp();
                                }
                                if (p.getMp() > p.getFullMp()) {
                                    p.getPlayerMainControl().mp = p.getFullMp();
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static DunArena createDunArena() {
        return new DunArena(149);
    }
}
