package com.tgame.ninja.real;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.branch.tasks.NguyetNhanTask;
import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Vector;

public class PlayerCopy extends Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerCopy.class);

    public LiveObject target;

    public boolean isCreate;

    public long timeAttack;

    public long timeLive;

    public byte percentDam;

    protected byte indexSkill;

    public PlayerCopy() {
        target = null;
        isCreate = true;
        timeAttack = System.currentTimeMillis() + 2000L;
        timeLive = System.currentTimeMillis();
        percentDam = 30;
        indexSkill = 0;
        isPlayerCopy = true;
    }

    public PlayerCopy(Session conn, String name, int gender, int headId) {
        super(conn, name, gender, headId);
        target = null;
        isCreate = true;
        timeAttack = System.currentTimeMillis() + 2000L;
        timeLive = System.currentTimeMillis();
        percentDam = 30;
        indexSkill = 0;
        isMainchar = false;
        isPlayerCopy = true;
    }

    @Override
    public Point attackNpc(Vector<Byte> npcIndexs, Point point) {
        Message message;
        try {
            long timeNow = System.currentTimeMillis();
            if (myskill == null) {
                return point;
            }
            if (myskill.template.type != 1) {
                return point;
            }
            message = new Message(Cmd.PLAYER_ATTACK_NPC);
            message.writeInt(playerId);
            if (myskill == null) {
                try {
                    if (skills.size() <= 0) {
                        return point;
                    }
                    myskill = skills.get(0);
                } catch (Exception e) {
                }
            }
            if (myskill != null) {
                message.writeByte(myskill.template.skillTemplateId);
            } else {
                message.writeByte(0);
            }
            for (Byte npcIndex : npcIndexs) {
                message.writeByte(npcIndex);
            }
            sendLimitSpace(message, true);
            boolean isXuat = false;
            boolean isUpdame;
            boolean isChiMang = isUpdame = checkClearEffect11();
            checkClearEffect12();
            if (npcIndexs.size() == 0) {
                return point;
            }
            long t = System.currentTimeMillis();
            for (int j = 0; j < npcIndexs.size(); ++j) {
                byte npcId = npcIndexs.get(j);
                if (npcId >= 0) {
                    if (npcId < map.npcs.size() || owner.privateNpc != null) {
                        long t2 = System.currentTimeMillis() - t;
                        Npc npc = null;
                        if (npcId == map.npcs.size() && owner.privateNpc != null) {
                            npc = owner.privateNpc;
                        } else if (npcId < map.npcs.size()) {
                            npc = map.npcs.get(npcId);
                        }
                        if (npc != null) {
                            if (npc.status == Npc.STATUS_DIE) {
                                message = new Message(Cmd.NPC_DIE);
                                message.writeByte(npc.npcId);
                                NJUtil.sendMessage(getSession(), message);
                            } else if (!npc.isTenTrom()) {
                                if (!npc.isDauLinhDaoTac()) {
                                    long t3 = System.currentTimeMillis() - t;
                                    long t4 = 0L;
                                    if (point == null) {
                                        if (!isAttack(npc, j)) {
                                            if (npc.status != Npc.STATUS_DIE) {
                                                message = new Message(Cmd.NPC_MISS);
                                                message.writeByte(npc.npcId);
                                                message.writeInt(npc.hp);
                                                sendToPlayer(message, true);
                                            }
                                            continue;
                                        } else {
                                            point = new Point(npc.pointx, npc.pointy);
                                            t4 = System.currentTimeMillis() - t;
                                        }
                                    } else if (!isAttackLan(point.x, point.y, npc.pointx, npc.pointy, true)) {
                                        if (npc.status != Npc.STATUS_DIE) {
                                            message = new Message(Cmd.NPC_MISS);
                                            message.writeByte(npc.npcId);
                                            message.writeInt(npc.hp);
                                            sendToPlayer(message, true);
                                        }
                                        continue;
                                    }
                                    int dameHit = getDam(npc);
                                    long t6 = System.currentTimeMillis() - t;
                                    boolean resut = doAttacNpc(npc, dameHit, -1 * NJUtil.randomNumber(11), false, isChiMang, isUpdame);
                                    if (resut) {
                                        if (sumonHide != null) {
                                            if (sumonHide.attNpcs == null) {
                                                sumonHide.attNpcs = new Vector<>();
                                            }
                                            if (!sumonHide.attNpcs.contains(npc)) {
                                                sumonHide.attNpcs.add(npc);
                                            }
                                        }
                                        if (sumon != null) {
                                            sumon.npcAttId = npc.npcId;
                                            if (sumon.template.npcTemplateId == 70) {
                                                sumon.dame = dame_full / 3;
                                            } else if (sumon.template.npcTemplateId == 122) {
                                                sumon.dame = dame_full / 2;
                                            }
                                        }
                                    }
                                    long t7 = System.currentTimeMillis() - t;
                                    if ((t2 > 100L || t3 > 100L || t4 > 100L || t6 > 100L || t7 > 100L) && !isXuat) {
                                        isXuat = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            int timeNext = myskill.timeReplay;
            if (myskill.timeReplay <= 1000) {
                timeNext = (int) (myskill.timeReplay / 1.2);
            } else if (myskill.timeReplay <= 10000) {
                timeNext = (int) (myskill.timeReplay / 1.15);
            } else if (myskill.timeReplay <= 20000) {
                timeNext = (int) (myskill.timeReplay / 1.1);
            } else if (myskill.timeReplay <= 50000) {
                timeNext = (int) (myskill.timeReplay / 1.05);
            }
            myskill.timeAttackNext = timeNow + timeNext;
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return point;
    }

    @Override
    public Point attackPlayer(Vector<Integer> playerIndexs, Point point) {
        Vector<Attack> atts = new Vector<>();
        Message message;
        try {
            long timeNow = System.currentTimeMillis();
            Player p = this;
            if (map.isDunVD) {
                DunVD dun = (DunVD) map;
                if (!dun.phe1.contains(this) && !dun.phe2.contains(this)) {
                    return null;
                }
            }
            for (Effect effect : effects) {
                if (effect.template.type == 10) {
                    return null;
                }
            }
            boolean isUpdame;
            boolean isChiMang = isUpdame = checkClearEffect11();
            checkClearEffect12();
            if (p.getSkillValue().template.type == 0 ||
                p.getSkillValue().template.type == 3 ||
                p.getSkillValue().template.type == 2 ||
                p.getSkillValue().template.type == 4
            ) {
                return null;
            }
            if (playerIndexs.size() == 0) {
                return point;
            }
            for (Integer playerIndex : playerIndexs) {
                Player player = map.getPlayer(playerIndex);
                if (!player.isNhanban()) {
                    if (player.status != Player.ME_DIE && !player.isInvisible) {
                        if (!player.isNhanban()) {
                            boolean isNotAtt = false;
                            for (int k = 0; k < player.effects.size(); ++k) {
                                if (player.effects.get(k).template.type == 10) {
                                    isNotAtt = true;
                                    break;
                                }
                            }
                            if (!isNotAtt) {
                                if (point == null) {
                                    if (!isAttackPlayer(player)) {
                                        atts.add(new Attack(player, 0, -1));
                                        continue;
                                    }
                                    point = new Point(player.x, player.y);
                                } else if (!isAttackLan(point.x, point.y, player.x, player.y, false)) {
                                    atts.add(new Attack(player, 0, -1));
                                    continue;
                                }
                                int typeAtt = -1;
                                if (((owner.getKillPlayer() != null && owner.getKillPlayer().player.equals(player)) || (player.getKillPlayer() != null && player.getKillPlayer().player.equals(this) && !map.isLang())) && !map.isLang() && !player.map.isLang()) {
                                    typeAtt = playerId;
                                } else if ((owner.getTypePk() == Player.PK_DOSAT ||
                                    player.getTypePk() == Player.PK_DOSAT ||
                                    (owner.getTypePk() == Player.PK_NHOM && player.getTypePk() == Player.PK_NHOM)) &&
                                    !map.isLang() &&
                                    !player.map.isLang() &&
                                    !owner.isTeam(player)
                                ) {
                                    typeAtt = owner.playerId;
                                }
                                if ((map.template.isMapChienTruong() ||
                                    map.getTemplateId() == 110 ||
                                    map.getTemplateId() == 111 ||
                                    map.getTemplateId() == 130) &&
                                    player.getTypePk() != owner.getTypePk()
                                ) {
                                    typeAtt = playerId;
                                }
                                if (typeAtt != -1) {
                                    if (((owner.getKillPlayer() != null && owner.getKillPlayer().player.equals(player)) || (player.getKillPlayer() != null && player.getKillPlayer().player.equals(this) && !map.isLang())) && !map.isLang() && !player.map.isLang()) {
                                        typeAtt = owner.playerId;
                                    } else if ((owner.typePk == Player.PK_DOSAT ||
                                        player.typePk == Player.PK_DOSAT ||
                                        (owner.typePk == Player.PK_NHOM && player.typePk == Player.PK_NHOM)) &&
                                        !map.isLang() &&
                                        !player.map.isLang() &&
                                        !isTeam(player)
                                    ) {
                                        typeAtt = owner.playerId;
                                    }
                                    if ((map.template.isMapChienTruong() ||
                                        map.getTemplateId() == 110 ||
                                        map.getTemplateId() == 111 ||
                                        map.getTemplateId() == 130) &&
                                        player.typePk != typePk
                                    ) {
                                        typeAtt = owner.playerId;
                                    }
                                    if (typeAtt != -1) {
                                        int dameHit = doAttactPlayer(player, p.dame_full - player.dameDown, false, isChiMang, isUpdame);
                                        if (dameHit != 0) {
                                            atts.add(new Attack(player, dameHit, typeAtt));
                                            if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 24) {
                                                if (NJUtil.probability(p.getSkillValue().options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                    Effect eff = new Effect();
                                                    eff.template = ServerController.effTemplates.get(5);
                                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                    eff.timeLength = 2000;
                                                    player.addEffect(eff);
                                                }
                                            } else if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 34) {
                                                if (NJUtil.probability(p.getSkillValue().options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                    Effect eff = new Effect();
                                                    eff.template = ServerController.effTemplates.get(5);
                                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                    eff.timeLength = 4000;
                                                    player.addEffect(eff);
                                                }
                                            } else if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 25) {
                                                if (NJUtil.probability(myskill.options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                    Effect eff = new Effect();
                                                    eff.template = ServerController.effTemplates.get(6);
                                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                    eff.timeLength = 1500;
                                                    player.y = autoFall(player.x, atts.firstElement().playerHit.y);
                                                    player.addEffect(eff);
                                                }
                                            } else if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 35) {
                                                if (NJUtil.probability(myskill.options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                    Effect eff = new Effect();
                                                    eff.template = ServerController.effTemplates.get(6);
                                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                    eff.timeLength = 3000;
                                                    player.y = autoFall(player.x, player.y);
                                                    player.addEffect(eff);
                                                }
                                            } else if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 26) {
                                                if (NJUtil.probability(myskill.options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                    Effect eff = new Effect();
                                                    eff.template = ServerController.effTemplates.get(7);
                                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                    eff.timeLength = 1000;
                                                    player.y = autoFall(player.x, player.y);
                                                    player.addEffect(eff);
                                                }
                                            } else if (p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 36 && NJUtil.probability(myskill.options.firstElement().param, 100 - p.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(7);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 2000;
                                                player.y = autoFall(player.x, player.y);
                                                player.addEffect(eff);
                                            }
                                        }
                                        if (player.timeWait > 0) {
                                            player.endWait(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (atts.size() > 0 && p.getSkillValue().template.skillTemplateId == 42) {
                if (p.skillDao35 == 0) {
                    p.skillDao35 = 1;
                    Effect eff2 = new Effect();
                    eff2.template = ServerController.effTemplates.get(18);
                    eff2.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff2.timeLength = 1500 + p.timeKeepDao;
                    atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                    atts.firstElement().playerHit.addEffect(eff2);
                    x = atts.firstElement().playerHit.x;
                    y = atts.firstElement().playerHit.y;
                    moveFastPlayer(atts.firstElement().playerHit.playerId);
                    atts.firstElement().playerHit.checkDie(atts.firstElement().typeAtt);
                    return null;
                }
                if (p.skillDao35 == 1) {
                    p.skillDao35 = 0;
                    int dameHit2 = atts.firstElement().playerHit.doAttactPlayer(this, p.dame_full, false, false, false);
                    if (dameHit2 > 0) {
                        atts.firstElement().playerHit.sendHaveAtt(dameHit2);
                        atts.firstElement().playerHit.checkDie(atts.firstElement().typeAtt);
                    }
                    return null;
                }
            }
            if (atts.size() > 0 && p.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 55) {
                Effect eff2 = new Effect();
                eff2.template = ServerController.effTemplates.get(18);
                eff2.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff2.timeLength = myskill.options.firstElement().param * 1000;
                atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                atts.firstElement().playerHit.addEffect(eff2);
                return null;
            }
            sendShowAtt(atts);
            for (Attack att : atts) {
                att.playerHit.sendHaveAtt(att.dameHit);
                if (att.dameHit < 0) {
                    att.dameHit *= -1;
                }
                if (att.dameHit > 0) {
                    if (att.playerHit.reactDame > 0) {
                        int ddx = NJUtil.distance(x, att.playerHit.x);
                        int ddy = NJUtil.distance(y, att.playerHit.y);
                        if (ddx < 100 && ddy < 100) {
                            att.dameHit = att.dameHit * 5 * att.playerHit.reactDame / 1000;
                            if (att.dameHit > 0) {
                                att.dameHit = att.playerHit.doAttactPlayer(this, att.dameHit, true, isChiMang, isUpdame);
                                if (att.dameHit > 0) {
                                    sendHaveAtt(att.dameHit);
                                    checkDie(att.typeAtt);
                                }
                            }
                        }
                    }
                    att.playerHit.checkDie(att.typeAtt);
                    if (att.playerHit.hp <= 0 && !isControlCharNhanBan()) {
                        if (dailytask != null && dailytask.template.checkTask(this, 3)) {
                            checkTaskOrder(dailytask, 1);
                        }
                    }
                    if (taskMain != null && !isControlCharNhanBan() && ((taskMain.template.taskId == 13 && taskMain.index == 1 && att.playerHit.playerId == -11 && att.playerHit.status == Player.ME_DIE) || (taskMain.template.taskId == 13 && taskMain.index == 2 && att.playerHit.playerId == -12 && att.playerHit.status == Player.ME_DIE) || (taskMain.template.taskId == 13 && taskMain.index == 3 && att.playerHit.playerId == -10 && att.playerHit.status == Player.ME_DIE))) {
                        if (map != null) {
                            map.goOutMap(att.playerHit);
                            NpcPlayer npcPlayer = map.npcPlayers.get(0);
                            npcPlayer.status = NpcPlayer.A_STAND;
                            message = NJUtil.messageSubCommand(Cmd.NPC_PLAYER_UPDATE);
                            message.writeByte(npcPlayer.npcPlayerId);
                            message.writeByte(npcPlayer.status);
                            NJUtil.sendMessage(getSession(), message);
                            doTaskNext();
                        }
                    }
                }
            }
            int timeNext = myskill.timeReplay;
            if (myskill.timeReplay <= 1000) {
                timeNext = (int) (myskill.timeReplay / 1.2);
            } else if (myskill.timeReplay <= 10000) {
                timeNext = (int) (myskill.timeReplay / 1.15);
            } else if (myskill.timeReplay <= 20000) {
                timeNext = (int) (myskill.timeReplay / 1.1);
            } else if (myskill.timeReplay <= 50000) {
                timeNext = (int) (myskill.timeReplay / 1.05);
            }
            myskill.timeAttackNext = timeNow + timeNext;
        } catch (Exception e) {
        }
        return point;
    }

    @Override
    public void checkAddEffect() {
        Player p = this;
        if (p.itemBodys[10] == null) {
            p.sumon = null;
        } else if (p.itemBodys[10].template.itemTemplateId == 246) {
            p.sumon = new NpcSumon(-1, 70, (short) (p.dame_full / 3));
        } else if (p.itemBodys[10].template.itemTemplateId == 419) {
            p.sumon = new NpcSumon(-1, 122, (short) (p.dame_full / 2));
        } else if (p.itemBodys[10].template.itemTemplateId == 568) {
            p.sumon = new NpcSumon(-1, 205, (short) (p.dame_full / 2));
        } else if (p.itemBodys[10].template.itemTemplateId == 569) {
            p.sumon = new NpcSumon(-1, 206, (short) (p.dame_full / 2));
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(36);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = (int) (p.itemBodys[10].expires - System.currentTimeMillis());
            eff.param = 300;
            addEffect(eff);
        } else if (p.itemBodys[10].template.itemTemplateId == 570) {
            p.sumon = new NpcSumon(-1, 207, (short) (p.dame_full / 2));
        } else if (p.itemBodys[10].template.itemTemplateId == 571) {
            p.sumon = new NpcSumon(-1, 208, (short) (p.dame_full / 2));
        } else if (p.itemBodys[10].isPetNew()) {
            p.sumon = new NpcSumon(-1, p.itemBodys[10].template.getIDDataPaint(), (short) (p.dame_full / 2));
            if (p.itemBodys[10].isPetReBiHanh() || p.itemBodys[10].ispetReBiHanhKhangDs()) {
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(42);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = (int) (p.itemBodys[10].expires - System.currentTimeMillis());
                eff.param = 400;
                addEffect(eff);
            }
        }
    }

    @Override
    public boolean doAttacNpc(Npc npc, int dameHit, int perRan, boolean isReactDame, boolean isChiMang, boolean isUpdame) {
        Message message;
        try {
            Player player = this;
            int timeFire = 0;
            int timeIce = 0;
            int timeWind = 0;
            if (!isReactDame) {
                if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 48) {
                    if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                        return false;
                    }
                    if (npc.isBoss() || npc.template.npcTemplateId == 112 || npc.template.npcTemplateId == 113) {
                        NJUtil.sendServer(getSession(), AlertFunction.TOO_STRONG(getSession()));
                        return false;
                    }
                    npc.timeDisable = myskill.options.firstElement().param * 2;
                    message = new Message(Cmd.NPC_IS_DISABLE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(true);
                    sendToPlayer(message, true);
                    return false;
                } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 50) {
                    if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                        return false;
                    }
                    if (npc.isBoss() || map.isDunVA || map.isDunClan || map.isDunPB || map.template.isMapChienTruong()) {
                        NJUtil.sendServer(getSession(), AlertFunction.TOO_STRONG(getSession()));
                        return false;
                    }
                    npc.status = Npc.STATUS_DIE;
                    npc.hp = npc.template.hp;
                    npc.hits.clear();
                    int itx = npc.pointx;
                    int ity = autoFall(itx, npc.pointy);
                    ItemMap itemMap = new ItemMap(new Item(218), owner.playerId, itx, ity);
                    itemMap.item.quantity = myskill.options.firstElement().param;
                    map.addItemMap(itemMap);
                    message = new Message(Cmd.NPC_CHANGE);
                    message.writeByte(npc.npcId);
                    message.writeShort(itemMap.itemMapId);
                    message.writeShort(itemMap.item.template.itemTemplateId);
                    message.writeShort(itemMap.x);
                    message.writeShort(itemMap.y);
                    sendToPlayer(message, false);
                    return false;
                } else if (player.myskill.template.skillTemplateId == 42) {
                    if (player.skillDao35 == 0) {
                        player.skillDao35 = 1;
                        x = npc.pointx;
                        y = npc.pointy;
                        moveFastNpc(npc.npcId);
                        return false;
                    }
                    if (player.skillDao35 == 1) {
                        player.skillDao35 = 0;
                        return isHit(npc, player.dame_full, false, false, timeFire, timeIce, timeWind);
                    }
                } else {
                    if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 55) {
                        npc.timeDontMove = player.myskill.options.firstElement().param * 2;
                        message = new Message(Cmd.NPC_IS_MOVE);
                        message.writeByte(npc.npcId);
                        message.writeBoolean(true);
                        sendToPlayer(message, true);
                        return false;
                    }
                    if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 24) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeFire = 4;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 34) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeFire = 8;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 25) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeIce = 3;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 35) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeIce = 6;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 26) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeWind = 2;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 36 && NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                        timeWind = 4;
                    }
                }
            }
            if (npc.miss - player.exactly > 0) {
                int perMiss = npc.miss - player.exactly;
                if (NJUtil.probability(perMiss, 1000 - perMiss) == 0) {
                    if (!isReactDame) {
                        message = new Message(Cmd.NPC_MISS);
                        message.writeByte(npc.npcId);
                        message.writeInt(npc.hp);
                        sendToPlayer(message, true);
                    }
                    return true;
                }
            }
            int typeSys = NJUtil.typeSys(getSys(), npc.sys);
            int perRes = -10;
            if (npc.level < 10) {
                perRes = 0;
            }
            int perSys = 0;
            if (typeSys == 1) {
                perSys = (npc.template.sysDown + sysUp) * 100 / npc.template.hp;
            } else if (typeSys == 2) {
                if (npc.template.sysUp - sysDown > 0) {
                    perSys = (npc.template.sysUp - sysDown) * -100 / npc.template.hp;
                }
            }
            if (perSys > 60) {
                perSys = 60;
            } else if (perSys < -60) {
                perSys = -60;
            }
            int per = perRes + perRan + perSys;
            dameHit += dameHit * per / 100;
            if (npc.sys == 1) {
                dameHit += player.dameUpFire;
                dameHit += dameHit * player.perDameUpFire / 100;
            } else if (npc.sys == 2) {
                dameHit += player.dameUpIce;
                dameHit += dameHit * player.perDameUpIce / 100;
            } else if (npc.sys == 3) {
                dameHit += player.dameUpWind;
                dameHit += dameHit * player.perDameUpWind / 100;
            }
            if (isChiMang) {
                int dm = (int) (dameHit * (player.dameFatalUp / 100.0f + player.dameFatalHard));
                if (dm > dameHit) {
                    dameHit = dm;
                }
            } else if (NJUtil.probability(player.fatal, PlayerCopy.indexFatal - player.fatal) == 0) {
                isChiMang = true;
                int dm = (int) (dameHit * (player.dameFatalUp / 100.0f + player.dameFatalHard));
                if (dm > dameHit) {
                    dameHit = dm;
                }
            }
            return isHit(npc, dameHit, isChiMang, isReactDame, timeFire, timeIce, timeWind);
        } catch (Exception e) {
            LOGGER.error("", e);
            return false;
        }
    }

    @Override
    public int doAttactPlayer(Player player, int dame, boolean resDame, boolean isChiMang, boolean isUpdame) {
        boolean isMiss = false;
        dame += dam_player;
        int dameHit = dame / 5;
        for (int i = 0; i < player.effects.size(); ++i) {
            if (player.effects.get(i).template.type == 1) {
                dameHit *= PlayerCopy.dameFire;
                break;
            }
        }
        if (NJUtil.probability(player.missAll, 100 - player.missAll) == 0) {
            isMiss = true;
        } else if (player.miss - exactly > 0) {
            int perMiss = player.miss - exactly;
            if (perMiss <= 9000 && perMiss > 1200) {
                perMiss = 1200;
            }
            if (NJUtil.probability(perMiss, PlayerCopy.maxmiss - perMiss) == 0) {
                isMiss = true;
            }
        }
        if (isMiss) {
            return 0;
        }
        int typeSys = NJUtil.typeSys(getSys(), player.getSys());
        int perRes = 0;
        if (getSys() == 1) {
            perRes = player.resFire * 100 / -PlayerCopy.indexRes;
        } else if (getSys() == 2) {
            perRes = player.resIce * 100 / -PlayerCopy.indexRes;
        } else if (getSys() == 3) {
            perRes = player.resWind * 100 / -PlayerCopy.indexRes;
        }
        int perRan = NJUtil.randomNumber(7) * (NJUtil.randomBoolean() ? -1 : 1);
        int perSys = 0;
        if (typeSys == 1) {
            perSys = (player.sysUp + sysDown) * 100 / player.hp_full;
        } else if (typeSys == 2) {
            if (player.sysDown - sysUp > 0) {
                perSys = (player.sysDown - sysUp) * -100 / player.hp_full;
            }
        }
        if (perRes < -60) {
            perRes = -60;
        }
        if (perSys > 60) {
            perSys = 60;
        } else if (perSys < -60) {
            perSys = -60;
        }
        int per = perRes + perRan + perSys;
        dameHit += dameHit * per / 100;
        if (player.getSys() == 1) {
            dameHit -= player.dameDownFire;
            dameHit += dameUpFire;
            dameHit += dameHit * perDameUpFire / 100;
        } else if (player.getSys() == 2) {
            dameHit -= player.dameDownIce;
            dameHit += dameUpIce;
            dameHit += dameHit * perDameUpIce / 100;
        } else if (player.getSys() == 3) {
            dameHit -= player.dameDownWind;
            dameHit += dameUpWind;
            dameHit += dameHit * perDameUpWind / 100;
        }
        dameHit = dameHit * getPercentDamPhanThan() / 100;
        if (dameHit < 0) {
            dameHit = 0;
        }
        if (isChiMang) {
            int dm = (int) (dameHit * (dameFatalHard + (dameFatalUp - player.dameFatalDown) / 100.0f));
            if (dm > dameHit) {
                dameHit = dm;
            }
        } else if (!resDame && NJUtil.probability(fatal, PlayerCopy.indexFatal - fatal) == 0) {
            isChiMang = true;
            int dm = (int) (dameHit * (dameFatalHard + (dameFatalUp - player.dameFatalDown) / 100.0f));
            if (dm > dameHit) {
                dameHit = dm;
            }
        }
        if (isUpdame) {
            dameHit += dameHit * perDameInvi / 100;
        }
        player.hp -= dameHit;
        if (isChiMang) {
            return dameHit * -1;
        }
        return dameHit;
    }

    @Override
    public KillPlayer getKillPlayer() {
        return owner.getKillPlayer();
    }

    @Override
    public int getPercentDamPhanThan() {
        return percentDam;
    }

    @Override
    public void setPercentDamPhanThan(int pc) {
        percentDam = (byte) pc;
    }

    @Override
    public Player getPlayerMainControl() {
        return this;
    }

    @Override
    public Session getSession() {
        return null;
    }

    @Override
    public Skill getSkillValue() {
        return myskill;
    }

    @Override
    public boolean isFirstJoinGame() {
        return isCreate;
    }

    @Override
    public void setFirstJoinGame(boolean s) {
        isCreate = s;
    }

    @Override
    public boolean isHit(Npc npc, int dameHit, boolean isChiMang, boolean isReactDame, int timeFire, int timeIce, int timeWind) {
        Player playerMain = getPlayerMainControl();
        if (playerMain.isNhanban()) {
            dameHit = dameHit < 1 ? 1 : playerMain.getPercentDamPhanThan() * dameHit / 100;
        }
        dameHit += playerMain.dam_mob;
        int deltalv = playerMain.level - npc.level;
        if (npc.template.isBossId() && !npc.template.isBossEventId() && !npc.isPrivateNpc() && !map.isDunPB) {
            if (map.isVungDatMaQuy()) {
                if (deltalv > 20) {
                    dameHit = 1;
                }
            } else if (deltalv > 15) {
                dameHit = 1;
            }
        }
        if ((playerMain.getTypePk() == PK_PHE1 && (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 167)) ||
            (playerMain.getTypePk() == PK_PHE2 && (npc.template.npcTemplateId == 143 || npc.template.npcTemplateId == 166))
        ) {
            return false;
        }
        long t = System.currentTimeMillis();
        try {
            if ((npc.template.npcTemplateId == 144 && map.getTemplateId() == 130) ||
                ((npc.template.npcTemplateId == 97 || npc.template.npcTemplateId == 98 || (npc.template.npcTemplateId == 167 && map.template.isMapChienTruong())) && playerMain.getTypePk() == PK_PHE1) ||
                ((npc.template.npcTemplateId == 96 || npc.template.npcTemplateId == 99 || (npc.template.npcTemplateId == 166 && map.template.isMapChienTruong())) && playerMain.getTypePk() == PK_PHE2)
            ) {
                return false;
            }
            if (npc.timeFire > 0) {
                dameHit *= dameFire;
            }
            dameHit = (Math.min(npc.hp, dameHit));
            if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                dameHit = 1;
            }
            if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp <= npc.template.hp - 300) {
                dameHit = 0;
            }
            long t2 = System.currentTimeMillis() - t;
            if (!isReactDame) {
                Hit hit = npc.getHit(playerId);
                if (hit == null) {
                    hit = new Hit(this);
                    npc.hits.add(hit);
                }
                hit.dame += dameHit;
                int range = NJUtil.distance(x, y, npc.pointx, npc.pointy);
                if (hit.rangeAttack < range + 10) {
                    hit.rangeAttack = range + 10;
                }
                npc.hits.remove(hit);
                npc.hits.add(hit);
            }
            if (npc.template.npcTemplateId == 0) {
                npc.hp -= npc.template.hp / 5;
            } else {
                npc.hp -= dameHit;
            }
            npc.timeHit = 1000;
            if (npc.template.isBossId()) {
                if (playerMain.level > npc.template.level + 6) {
                    npc.reactDame = level - npc.template.level;
                } else {
                    npc.reactDame = 0;
                }
            }
            if (dameHit > 0 && npc.reactDame > 0 && !isReactDame) {
                int dameReact = dameHit * npc.reactDame / 1000;
                npc.attPlayerReact(this, dameReact);
            }
            if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp < 10) {
                npc.hp = npc.template.hp;
            }
            if (npc.hp > 0) {
                if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp % 10 == 0 && dameHit > 0) {
                    Item itEvent = new Item(458, false, "atk gio keo");
                    ItemMap itMap = new ItemMap(itEvent, -1, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                    itMap.y = autoFall(itMap.x, itMap.y);
                    map.addItemMap(itMap);
                    map.sendAddItemMap(itMap);
                }
                Message message = new Message(Cmd.NPC_HP);
                message.writeByte(npc.npcId);
                message.writeInt(npc.hp);
                message.writeInt(dameHit);
                message.writeBoolean(isChiMang);
                if (npc.isTinhAnh() || npc.isThuLinh() || (npc.template.npcTemplateId == 118 && npc.maxhp > 87420) || ((npc.template.npcTemplateId == 117 || npc.template.npcTemplateId == 119) && npc.maxhp == 5000)) {
                    message.writeByte(npc.levelBoss);
                    message.writeInt(npc.maxhp);
                    this.getSession().sendMessage(message);
                    try {
                        if (map != null) {
                            for (int k = 0; k < map.players.size(); ++k) {
                                try {
                                    if (map.players.get(k).playerId != playerId && npc.isHit(map.players.get(k))) {
                                        map.players.get(k).getSession().sendMessage(message);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    } catch (Exception e) {
                    } finally {
                        message.cleanup();
                    }
                } else {
                    sendToPlayer(message, true);
                }
                if (npc.timeFire < timeFire) {
                    npc.timeFire = timeFire;
                    message = new Message(Cmd.NPC_IS_FIRE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeFire > 0);
                    map.sendToPlayer(message);
                }
                if (npc.timeIce < timeIce) {
                    npc.timeIce = timeIce;
                    message = new Message(Cmd.NPC_IS_ICE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeIce > 0);
                    map.sendToPlayer(message);
                }
                if (npc.timeWind < timeWind) {
                    npc.timeWind = timeWind;
                    message = new Message(Cmd.NPC_IS_WIND);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeWind > 0);
                    map.sendToPlayer(message);
                }
                return true;
            }
            if (npc.status == Npc.STATUS_DIE) {
                return false;
            }
            if (npc.isPrivateNpc()) {
                try {
                    npc.status = Npc.STATUS_DIE;
                    npcDie(npc, dameHit, isChiMang);
                } catch (Exception e) {
                }
                /*if (npc.template.npcTemplateId == 230 && playerMain.privateNpc != null) {
                    // drop here
                }*/
                playerMain.privateNpc = null;
                return false;
            }
            countMonsterKill();
            if (npc.template.npcTemplateId == 113) {
                int[] hits = new int[npc.hits.size()];
                for (int i = 0; i < npc.hits.size(); ++i) {
                    hits[i] = npc.hits.get(i).dame;
                }
                Player player = npc.hits.get(NJUtil.probability(hits)).player;
                if (player.map.isDunVA) {
                    player.addItemToBagNoDialog(new Item(288, true, "atk moc nhan"));
                    String[] strs = {
                        player.name + " " + Alert_VN.VUOT_AI_ALERT14,
                        player.name + " " + Alert_EN.VUOT_AI_ALERT14
                    };
                    map.sendAlert(strs);
                }
            }
            //Player pKill = npc.getOwnerPlayer();
            int ownerId = npc.getOwner();
            npc.status = Npc.STATUS_DIE;
            npc.hp = npc.template.hp;
            npc.hits.clear();
            npc.timeDisable = 0;
            npc.timeDontMove = 0;
            npc.timeFire = 0;
            npc.timeIce = 0;
            npc.timeWind = 0;
            if (npc.isTinhAnh()) {
                if (Math.abs(deltalv) <= 10) {
                    checkStepNguyeNhanTask(NguyetNhanTask.TYPE_TIEU_DIET_TINH_ANH, 1);
                }
            } else if (npc.isThuLinh()) {
                if (Math.abs(deltalv) <= 10) {
                    checkStepNguyeNhanTask(NguyetNhanTask.TYPE_TIEU_DIET_THU_LINH, 1);
                }
            } else if (Math.abs(deltalv) <= 10) {
                checkStepNguyeNhanTask(NguyetNhanTask.TYPE_GIET_QUAI, 1);
            }
            if (map.template.isMapChienTruong()) {
                if (!MixedArena.isMapArena(map.template.mapTemplateId)) {
                    int pointAdd = 1;
                    if (npc.isTinhAnh()) {
                        pointAdd = 10;
                    } else if (npc.isThuLinh()) {
                        pointAdd = 50;
                    }
                    if (npc.template.npcTemplateId == 98 || npc.template.npcTemplateId == 99) {
                        pointAdd = 150;
                    }
                    if (map.giatocchien != null) {
                        map.giatocchien.addPoint(name, pointAdd);
                        sendChientruong(map.giatocchien.getPointCT(name));
                    } else {
                        playerMain.pointCT += pointAdd;
                        playerMain.checkStepNguyeNhanTask(NguyetNhanTask.TYPE_KIEM_DIEM_CHIEN_TRUONG, pointAdd);
                        sendChientruong(playerMain.pointCT);
                    }
                }
            }
            if (map.isDunPB) {
                int deltaPointPB = 0;
                if (map.template.typeMap == MapTemplate.MAP_PB) {
                    deltaPointPB = 1;
                    DunPB dun = (DunPB) map;
                    if (npc.template.npcTemplateId == 88) {
                        deltaPointPB = NJUtil.randomNumber(15) + 5;
                    } else if (npc.template.npcTemplateId == 89) {
                        deltaPointPB = NJUtil.randomNumber(30) + 10;
                    } else if (npc.template.npcTemplateId == 105) {
                        deltaPointPB = NJUtil.randomNumber(50) + 50;
                    } else if (npc.template.npcTemplateId == 138) {
                        deltaPointPB = NJUtil.randomNumber(50) + 50;
                    } else if (dun.loop == 4 || dun.loop == 6) {
                        deltaPointPB = 2;
                    } else if (dun.loop == 8 || dun.loop == 10) {
                        deltaPointPB = 3;
                    } else if (dun.loop == 12 || dun.loop == 14) {
                        deltaPointPB = 4;
                    } else if (dun.loop > 14) {
                        deltaPointPB = 5;
                    }
                }
                if (map.isDunPb9x() && npc.nDropItem <= 0) {
                    deltaPointPB = 0;
                }
                if (deltaPointPB > 0) {
                    DunPB dunPB = ((DunPB) map).findDunPB();
                    try {
                        for (int i = 0; i < dunPB.nameIns.size(); ++i) {
                            try {
                                Player p2 = ServerController.hnPlayers.get(dunPB.nameIns.get(i));
                                if (p2 != null && p2.map.isDunPB) {
                                    p2.pointPB += deltaPointPB;
                                    p2.sendPointPB();
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            Player playerAttacck = ServerController.hpPlayers.get(ownerId);
            if (playerAttacck != null && playerAttacck.map.equals(map) && !map.template.isMapChienTruong()) {
                if (playerAttacck.equals(this)) {
                    if (npc.isTinhAnh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                        int yen = npc.randomMoney() * 5;
                        if (map.isDunClan) {
                            yen *= 5;
                            Item itTiengiatoc = new Item(262, true, "isHit char 36");
                            itTiengiatoc.quantity = NJUtil.randomNumber(10) + 11;
                            clan.sendAddItem(itTiengiatoc);
                        }
                        playerAttacck.sendUpdateCoinLockYen(yen);
                        NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    } else if (npc.isThuLinh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                        int yen = npc.randomMoney() * 20;
                        playerAttacck.sendUpdateCoinLockYen(yen);
                        NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    }
                } else if (npc.isTinhAnh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                    int yen = (int) (npc.randomMoney() * 2.5);
                    if (map.isDunClan) {
                        yen *= 5;
                        Item itTiengiatoc = new Item(262, true, "isHit char 5");
                        itTiengiatoc.quantity = NJUtil.randomNumber(10) + 11;
                        clan.sendAddItem(itTiengiatoc);
                    }
                    playerAttacck.sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(getSession(), AlertFunction.YOU_GET(getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(getSession()));
                } else if (npc.isThuLinh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                    int yen = npc.randomMoney() * 10;
                    playerAttacck.sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(getSession(), AlertFunction.YOU_GET(getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(getSession()));
                }
            }
            if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                MixedArena.updatePointKillMob(this, npc);
            }
            if (playerAttacck != null && playerAttacck.dailytask != null && playerAttacck.dailytask.template.checkTask(playerAttacck, 4, npc.template.npcTemplateId)) {
                playerAttacck.checkTaskOrder(playerAttacck.dailytask, 1);
            }
            if (map.getTemplateId() == 130) { // kẹo chiến
                if (NJUtil.probability(10, 90) == 0) {
                    Item it = new Item(458, false, "isHit char 32");
                    ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                    itMap.y = autoFall(itMap.x, itMap.y);
                    map.addItemMap(itMap);
                    map.sendAddItemMap(itMap);
                }
            }
            ArrayList<DropRate> drops = new ArrayList<>();
            drops.add(new DropRate(1, 0)); // ko rơi
            drops.add(new DropRate(2, 1)); // yên
            drops.add(new DropRate(2, 2)); // hp mp
            drops.add(new DropRate(3, 3)); // đá
            drops.add(new DropRate(4, 4)); // trang bị
            int idGet = NJUtil.dropItem(drops);
            if (npc.isTinhAnh() || npc.isThuLinh()) {
                idGet = 3;
                if (npc.isTinhAnh()) {
                    ++map.maxBoss1;
                } else {
                    ++map.maxBoss2;
                }
            }
            if (map.isDunClan && npc.isTinhAnh()) {
                idGet = 99;
            }
            try {
                if (EventManager.getInstance().isMobEvent(npc) && Math.abs(playerMain.level - npc.level) <= 10 && playerAttacck != null) {
                    Vector<Item> rewards = EventManager.getInstance().killMobRewards(playerAttacck, npc);
                    if (rewards != null) {
                        for (Item it : rewards) {
                            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                            itMap.y = autoFall(itMap.x, itMap.y);
                            map.addItemMap(itMap);
                            map.sendAddItemMap(itMap);
                        }
                    }
                }
            } catch (Exception e) {
            }
            Item item = null;
            if (Math.abs(playerMain.level - npc.level) <= 7 || idGet == 99 || idGet == 0) {
                switch (idGet) {
                    case 0: // dơi lửa nv
                        if (npc.template.npcTemplateId == 41 && NJUtil.randomBoolean()) {
                            item = new Item(238, true, "xac doi lua nv");
                        }
                        break;
                    case 1: // yên
                        if ((map.isDunClan && npc.isTinhAnh()) || Math.abs(playerMain.level - npc.level) <= 7) {
                            item = new Item(12);
                            item.quantity = npc.randomMoney();
                            item.isLock = true;
                        }
                        break;
                    case 2: // random potion
                        int potion = NJUtil.randomBoolean() ? npc.throwItemHP() : npc.throwItemMP();
                        item = new Item(potion);
                        break;
                    case 3: // stone
                        if (npc.isThuLinh()) {
                            item = new Item(npc.randomThrowHT(true));
                        } else {
                            item = new Item(npc.randomThrowHT(false));
                        }
                        item.isLock = true;
                        break;
                    case 4: // random armor
                        item = new Item(npc.randomItemBody());
                        item.sys = NJUtil.randomNumber(3) + 1;
                        item = Item.getOptionThrow(item);
                        if (item.options == null) {
                            item = null;
                        }
                        break;
                    case 99: // chìa khoá gia tộc
                        item = new Item(260, false, "chia khoa gt");
                        break;
                }
            }
            if (item != null && item.getTemplateId() != 260 && item.getTemplateId() != 530 && map.isDunClan) {
                if (NJUtil.randomBoolean()) {
                    item = new Item(12, true, "isHit char 40");
                    item.quantity = npc.randomMoney() * 5;
                } else {
                    item = new Item(npc.randomThrowHT(false) + 1, true, "isHit char 41");
                }
            }
            if (npc.template.npcTemplateId == 0 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 2 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 1 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 2 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 3) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 3 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 4 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(209, true, "isHit char 42");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 4 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 4 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(210, true, "isHit char 43");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 54 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 5 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(211, true, "isHit char 44");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 81) {
                item = new Item(261, true, "isHit char 45");
            } else if (npc.template.npcTemplateId == 5 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 0) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 6 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 7 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 15 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 14 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(213, true, "isHit char 46");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 23 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 16 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 24 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 16 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 26 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 18 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(216, true, "isHit char 47");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 27 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 18 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(217, true, "isHit char 48");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId >= 5 && npc.template.npcTemplateId <= 14 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 12 && playerMain.taskMain.index >= 1 && playerMain.taskMain.index <= 3) {
                if (NJUtil.probability(15, 85) == 0) {
                    if (NJUtil.randomNumber(2) == 0) {
                        item = new Item((playerMain.gender == 1) ? 124 : 125, true, "isHit char 49");
                    } else {
                        item = new Item(174, true, "isHit char 50");
                    }
                    item.sys = NJUtil.randomNumber(3) + 1;
                    item = Item.getOptionThrow(item);
                    if (item.options == null) {
                        item = null;
                    }
                }
            } else if (npc.template.npcTemplateId == 69 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 20 && playerMain.taskMain.index == 1) {
                item = new Item(221, true, "isHit char 51");
                NJUtil.sendServer(getSession(), AlertFunction.LIMIT_TIME_20(getSession()));
                sendMapTime(20);
                ((Dun) map).timeEnd = System.currentTimeMillis() + 20000L;
            } else if (npc.template.npcTemplateId == 30 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 21 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 31 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 21 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 33 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 22 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(230, true, "isHit char 52");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 37 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 25 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 38 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 25 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 42 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 28 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 43 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 28 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 44 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 29 && playerMain.taskMain.index == 1 && npc.isTinhAnh()) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 47 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 31 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(264, true, "isHit char 53");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 48 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 31 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(265, true, "isHit char 54");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 51 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 33 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 52 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 33 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 57 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 36 && playerMain.taskMain.index == 1) {
                addItemToBagNoDialog(new Item(348, true, "isHit char 55"));
                doTaskUpdate((short) countItemBag(348));
                if (playerMain.taskMain.count >= playerMain.taskMain.template.counts[playerMain.taskMain.index]) {
                    doTaskNext();
                }
                Message message = NJUtil.messageNotMap(Cmd.OAN_HON);
                message.writeByte(npc.npcId);
                message.writeInt(playerId);
                sendToPlayer(message, true);
            } else if (npc.template.npcTemplateId == 58 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 37 && playerMain.taskMain.index == 1 && npc.isTinhAnh()) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 59 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 37 && playerMain.taskMain.index == 2 && npc.levelBoss == 2) {
                if (party != null) {
                    party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 60 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 38 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(349, true, "isHit char 56");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 61 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 38 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(350, true, "isHit char 57");
                } else {
                    item = null;
                }
            }
            boolean condition = npc.template.npcTemplateId == 54 && (playerMain.taskMain == null || playerMain.taskMain.template.taskId != 5 || playerMain.taskMain.index != 1);
            if (npc.template.npcTemplateId == 0 || condition || map.template.isMapChienTruong()) {
                item = null;
            }
            checkTaskDay(npc);
            if (playerAttacck != null) {
                playerAttacck.checkTaskBoos(npc);
                if (playerMain.party != null) {
                    playerMain.party.sendUpdateTaskDay(this, npc);
                }
                if (playerAttacck.party != null) {
                    playerAttacck.party.sendUpdateTaskBoss(playerAttacck, npc);
                }
            }
            long tt2 = System.currentTimeMillis() - t;
            if (t2 > 200L) {
                LOGGER.warn("{}. Mess danh cham 2: {}", getStringBaseInfo(), tt2);
            }
            if (map.isDunClan) {
                if (!npc.isBoss() && npc.template.npcTemplateId != 81) {
                    Item itTienGiaToc = new Item(262, true, "isHit char 58");
                    clan.sendAddItem(itTienGiaToc);
                } else if (npc.template.npcTemplateId == 82) {
                    Item itTienGiaToc = new Item(262, true, "isHit char 59");
                    itTienGiaToc.quantity = NJUtil.randomNumber(40) + 21;
                    ghiloghack(itTienGiaToc.getTemplateId());
                    clan.sendAddItem(itTienGiaToc);
                    int max = NJUtil.randomNumber(6) + 10;
                    for (int i = 0; i < max; ++i) {
                        int[] ids = { 12, 2, 3, 4, 5, 6 };
                        int[] pers = { 40, 40, 30, 20, 10, 5 };
                        Item itBoss = new Item(ids[NJUtil.probability(pers)], false, "isHit char 60");
                        if (itBoss.getTemplateId() == 12) {
                            itBoss.quantity = NJUtil.randomNumber(npc.randomMoney() * 12);
                        }
                        ItemMap itBoos = new ItemMap(itBoss, -1, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                        itBoos.y = autoFall(itBoos.x, itBoos.y);
                        map.addItemMap(itBoos);
                        map.sendAddItemMap(itBoos);
                    }
                    clan.exp += 500;
                    clan.closeDun();
                    Database.saveClan(clan);
                    String[] strs = {
                        Alert_VN.YOU_GET + " 500 " + Alert_VN.POINT_CLAN,
                        Alert_EN.YOU_GET + " 500 " + Alert_EN.POINT_CLAN
                    };
                    clan.sendAlert(strs, name);
                    sendServerMessage(strs);
                }
            } else if (map.isDunPB) {
                // phó bản 9x, boss 3 anh em
                if (npc.template.npcTemplateId == 198 || npc.template.npcTemplateId == 199 || npc.template.npcTemplateId == 200) {
                    throwItemBossPB(npc, -1);
                }
            } else if (map.isVungDatMaQuy()) {
                // 201_Kền Kền Vương | 203_Đại Lực Sĩ | 204_U Minh Khuyển
                if (npc.template.npcTemplateId == 201 || npc.template.npcTemplateId == 203 || npc.template.npcTemplateId == 204) {
                    throwItemBossVDMQ(npc, -1);
                } else if (NJUtil.random.nextInt(10000) < 750 && isUsingKhaiThienNhanPhu() != null && Math.abs(deltalv) <= 10) {
                    throwItemMobVDMQ(npc, ownerId);
                }
            } else if (map.isLangCo()) {
                // 161_Tử Lôi Diệu Thiên Long | 163_Phù thủy Bí Ngô | 162_Hỏa Kỳ Lân | 164_Băng Đế
                if (npc.template.npcTemplateId == 161 || npc.template.npcTemplateId == 162 || npc.template.npcTemplateId == 163 || npc.template.npcTemplateId == 164) {
                    throwItemBossLC(npc, -1);
                } else {
                    throwItemMobLC(npc, ownerId);
                }
            } else if (npc.template.npcTemplateId == 114) { // Thần thố
                throwItemBoss(npc, 1, -1);
            } else if (npc.template.npcTemplateId == 115) { // Xích phiến thiên long
                throwItemBoss(npc, 2, -1);
            } else if (npc.template.npcTemplateId == 116) { // Samurai chiến tướng
                throwItemBoss(npc, 3, -1);
            } else if (npc.template.npcTemplateId == 139) { // Hỏa ngưu vương
                throwItemBoss(npc, 4, -1);
            }
            if (npc.template.isBossId() && playerAttacck != null) {
                String alert = playerAttacck.name + " đã tiêu diệt " + npc.template.name + ", khiến mọi người đều ngưỡng mộ!!!";
                LOGGER.info(playerAttacck.name + " killed " + npc.template.name_en);
                NJUtil.sendServerAlert(alert);
            }
            if (item != null) {
                if (npc.isBoss() && item.template.type == 26) {
                    item.isLock = false;
                } else if (item.template.type == 25 || item.template.type == 23 || item.template.type == 24) {
                    item.isLock = true;
                }
                item.updateSale();
                item.typeUI = 3;
                int d = NJUtil.randomNumber(15);
                ItemMap itemMap = new ItemMap(item, ownerId, npc.pointx + ((map.tick % 2 == 0) ? d : (-d)), npc.pointy);
                if (item.getTemplateId() == 260) {
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByMinute(5);
                    itemMap.owner = -1;
                } else if (item.getTemplateId() == 261) {
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByMinute(30);
                    itemMap.owner = -1;
                } else {
                    item.expires = -1L;
                }
                itemMap.y = autoFall(itemMap.x, itemMap.y);
                map.addItemMap(itemMap);
                Message message = new Message(Cmd.NPC_DIE);
                message.writeByte(npc.npcId);
                message.writeInt(dameHit);
                message.writeBoolean(isChiMang);
                message.writeShort(itemMap.itemMapId);
                message.writeShort(itemMap.item.template.itemTemplateId);
                message.writeShort(itemMap.x);
                message.writeShort(itemMap.y);
                sendToPlayer(message, true);
                if (npc.template.npcTemplateId == 112) {
                    int countDie = 0;
                    for (int i = 0; i < map.npcs.size(); ++i) {
                        if (map.npcs.get(i).status == ME_NORMAL) {
                            ++countDie;
                        }
                    }
                    if (countDie > 0 && countDie == map.npcs.size()) {
                        addItemToBagNoDialog(new Item(288, true, "isHit char 110"));
                    }
                }
            } else {
                npcDie(npc, dameHit, isChiMang);
            }
        } catch (Exception e) {
            if (GameServer.isServerLocal()) {
                LOGGER.error("", e);
            }
        }
        return false;
    }

    @Override
    public boolean isNhanban() {
        return true;
    }

    @Override
    public void sendHaveAtt(int dameHit) {
        try {
            Message message = new Message(Cmd.HAVE_ATTACK_PLAYER);
            message.writeInt(playerId);
            message.writeInt(hp);
            message.writeInt(dameHit);
            sendLimitSpace(message, true);
            if (dameHit == 0) {
                Player p = ServerController.hpPlayers.get(playerId);
                if (p != null) {
                    message = new Message(Cmd.PLAYER_MOVE);
                    message.writeInt(p.playerId);
                    message.writeShort(p.x);
                    message.writeShort(p.y);
                    NJUtil.sendMessage(getSession(), message);
                }
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void sendShowAtt(Vector<Attack> atts) {
        try {
            if (atts.size() > 0) {
                Player p = this;
                Message message = new Message(Cmd.PLAYER_ATTACK_PLAYER);
                message.writeInt(playerId);
                message.writeByte(p.getSkillValue().template.skillTemplateId);
                for (Attack att : atts) {
                    message.writeInt(att.playerHit.playerId);
                }
                sendLimitSpace(message, true);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void update() {
        target = null;
        if (owner.status == Player.ME_DIE) {
            return;
        }
        try {
            if (myskill != null && (myskill.template.skillTemplateId == 47 || myskill.template.skillTemplateId == 51 || myskill.template.skillTemplateId == 52)) {
                Message message;
                long timeNow = System.currentTimeMillis();
                if (myskill.timeAttackNext > timeNow) {
                    return;
                }
                if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 49) {
                    short bx = owner.x;
                    short by = autoFall(bx, y);
                    BuNhin buNhin = new BuNhin(bx, by, myskill.options.firstElement().param * 1000, owner, myskill.dx, myskill.dy);
                    map.addBuNhin(buNhin);
                    message = new Message(Cmd.CREATE_BUNHIN);
                    message.writeUTF(name);
                    message.writeShort(buNhin.x);
                    message.writeShort(buNhin.y);
                    sendToPlayer(message, false);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 27) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(8);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = myskill.options.firstElement().param + myskill.options.firstElement().param * perUpBuff / 100;
                    owner.addEffect(eff);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 51) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(9);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 30000;
                    owner.addEffect(eff);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 30) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(10);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 90000;
                    owner.convertMp = (short) myskill.options.firstElement().param;
                    owner.addEffect(eff);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 52) {
                    isInvisible = true;
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(16);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = myskill.options.firstElement().param;
                    owner.addEffect(eff);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 53) {
                    owner.isInvisible = true;
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(15);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = myskill.options.firstElement().param * 1000;
                    owner.addEffect(eff);
                } else if (myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 56) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(17);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = myskill.options.firstElement().param;
                    owner.addEffect(eff);
                } else if (myskill.template.skillTemplateId == 51) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(19);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 90000;
                    eff.options = new Vector<>();
                    for (int i = 0; i < myskill.options.size(); ++i) {
                        SkillOption clone;
                        SkillOption op = clone = myskill.options.get(i).clone();
                        clone.param += op.param * perUpBuff / 100;
                        eff.options.add(op);
                    }
                    owner.addEffect(eff);
                    owner.updateData();
                    if (party != null) {
                        party.sendEffBuff(this, eff, eff.param);
                    }
                } else if (myskill.template.skillTemplateId == 52) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(20);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = myskill.options.firstElement().param * 1000;
                    eff.timeLength += eff.timeLength * perUpBuff / 100;
                    eff.options = new Vector<>();
                    for (int i = 0; i < myskill.options.size(); ++i) {
                        if (myskill.options.get(i).optionTemplate.skillOptionTemplateId == 40 || myskill.options.get(i).optionTemplate.skillOptionTemplateId == 41 || myskill.options.get(i).optionTemplate.skillOptionTemplateId == 42) {
                            eff.options.add(myskill.options.get(i));
                        }
                    }
                    owner.addEffect(eff);
                    owner.updateData();
                    if (party != null) {
                        party.sendEffBuff(this, eff, eff.param);
                    }
                } else if (myskill.template.skillTemplateId == 58) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(11);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = myskill.options.firstElement().param;
                    eff.param = 15000;
                    owner.addEffect(eff);
                    owner.updateData();
                }
                int timeNext = myskill.timeReplay;
                if (myskill.timeReplay <= 1000) {
                    timeNext = (int) (myskill.timeReplay / 1.2);
                } else if (myskill.timeReplay <= 10000) {
                    timeNext = (int) (myskill.timeReplay / 1.15);
                } else if (myskill.timeReplay <= 20000) {
                    timeNext = (int) (myskill.timeReplay / 1.1);
                } else if (myskill.timeReplay <= 50000) {
                    timeNext = (int) (myskill.timeReplay / 1.05);
                }
                myskill.timeAttackNext = timeNow + timeNext;
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void updateAll() {
    }

    @Override
    public void updateThucAn(Player player) {
        try {
            long timeNow = System.currentTimeMillis();
            if (player.effects.size() > 0) {
                for (int i = 0; i < player.effects.size(); ++i) {
                    Effect eff = player.effects.get(i);
                    if (player.status == Player.ME_NORMAL && (eff.template.type == 0 || eff.template.type == 12)) {
                        player.hp += eff.param;
                        player.mp += eff.param;
                        if (player.hp >= player.hp_full) {
                            player.hp = player.hp_full;
                        }
                        if (player.mp >= player.mp_full) {
                            player.mp = player.mp_full;
                        }
                    } else if (player.status == Player.ME_NORMAL && (eff.template.type == 4 || eff.template.type == 17 || eff.template.type == 1)) {
                        if (eff.template.type == 1) {
                            player.hp += player.autoUpHp;
                        } else {
                            player.hp += eff.param;
                        }
                        if (player.hp >= player.hp_full) {
                            player.hp = player.hp_full;
                        }
                    } else if (player.status == Player.ME_NORMAL && eff.template.type == 13) {
                        player.hp -= player.hp_full * 3 / 100;
                        player.checkDie(-1);
                        if (player.status == Player.ME_DIE) {
                            eff.timeLength = 0;
                        }
                    } else if (eff.template.type == 24) {
                        player.addMaxHp = eff.param;
                    }
                    long timeCheck = eff.timeStart * 1000L + eff.timeLength;
                    if (eff.timeStart != -1 && timeNow > timeCheck) {
                        player.removeEffect(eff, true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
