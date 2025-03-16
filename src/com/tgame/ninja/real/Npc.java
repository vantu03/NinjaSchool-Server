package com.tgame.ninja.real;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class Npc extends LiveObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(Npc.class);

    public static final byte TYPE_DUNG = 0;

    public static final byte TYPE_DI = 1;

    public static final byte TYPE_NHAY = 2;

    public static final byte TYPE_LET = 3;

    public static final byte TYPE_BAY = 4;

    public static final byte TYPE_DAU = 5;

    public static final byte STATUS_DIE = 0;

    public static final byte STATUS_NORMAL = 5;

    public Map map;

    public NpcTemplate template;

    public int npcId;

    public short pointx;

    public short pointy;

    public int hp;

    public int minX;

    public int maxX;

    public int minY;

    public int maxY;

    public int timeWait;

    public int timeAtt;

    public int status;

    public int timeHit;

    public Vector<Hit> hits;

    public int timeReturn;

    public int timeDisable;

    public int timeDontMove;

    public int timeFire;

    public int timeIce;

    public int timeWind;

    public int sys;

    public int miss;

    public int exactly;

    public int reactDame;

    public int levelBoss;

    public int dame;

    public int maxhp;

    public int maxexp;

    public int level;

    public long timeHide;

    public int timedoikeo;

    public byte nDropItem;

    public int idskilltest;

    public Npc(int npcId, int npcTemplateId, Map map) {
        timeWait = 0;
        timeAtt = 0;
        hits = new Vector<>();
        timedoikeo = 0;
        nDropItem = 1;
        idskilltest = 0;
        template = ServerController.npcTemplates.get(npcTemplateId);
        hp = template.hp;
        dame = template.dame;
        maxhp = template.hp;
        maxexp = template.exp;
        this.npcId = npcId;
        this.map = map;
        loadData(npcTemplateId);
        if (template.npcTemplateId == 69 || template.npcTemplateId == 71 || template.npcTemplateId == 82) {
            levelBoss = 1;
        }
    }

    @Override
    public boolean isNpc() {
        return true;
    }

    @Override
    public void objDie(Session s) {
        try {
            Message message = new Message(Cmd.NPC_DIE);
            message.writeByte(npcId);
            NJUtil.sendMessage(s, message);
        } catch (Exception e) {
        }
    }

    @Override
    public void objLive(Session s) {
        try {
            Message message = new Message(Cmd.NPC_LIVE);
            message.writeByte(npcId);
            message.writeByte(sys);
            if (levelBoss == 3) {
                message.writeByte(3);
            } else {
                message.writeByte(0);
            }
            message.writeInt(maxhp);
            NJUtil.sendMessage(s, message);
        } catch (Exception e) {
        }
    }

    public void loadData(int npcTemplateId) {
        sys = NJUtil.randomNumber(3) + 1;
        status = 5;
        timeReturn = 30;
        miss = level / 10 * 3;
        if (level < 10) {
            miss = 20;
        } else if (level < 20) {
            miss = 30;
        } else if (level < 30) {
            miss = 60;
        } else if (level < 40) {
            miss = 70;
        } else if (level < 50) {
            miss = 100;
        } else if (level < 60) {
            miss = 110;
        } else if (level < 70) {
            miss = 140;
        } else {
            miss = 150;
        }
        if (npcTemplateId == 0 || npcTemplateId == 54) {
            miss = 0;
        }
        exactly = 5 * level;
        if (npcTemplateId >= 168) {
            exactly = 10 * level;
        }
    }

    public void update(Map map, int setLevelBoss) {
        try {
            if (status == Npc.STATUS_DIE) {
                if (template.npcTemplateId != 144 && template.npcTemplateId != 231) {
                    if (map.getTemplateId() != 130 && map.getTemplateId() != 157 && map.getTemplateId() != 158 && map.getTemplateId() != 159 &&
                        template.isBossId() && template.npcTemplateId != 138 && !map.isMapBoss && !map.template.isMapChienTruong()
                    ) {
                        return;
                    }
                    if (template.npcTemplateId == 141) {
                        return;
                    }
                    timeHit = 0;
                    ++timeWait;
                }
                if (isTimeReborn()) {
                    if (map.isMapBoss && template.isBossId()) {
                        for (int i = 0; i < Map.bossTrumMapIds.length; ++i) {
                            if (map.template.mapTemplateId == Map.bossTrumMapIds[i] && map.zoneId == Map.bossTrumZoneIds[i]) {
                                Map.bossTrumMapIds[i] = -1;
                                Map.bossTrumZoneIds[i] = -1;
                                break;
                            }
                        }
                    }
                    timeWait = 0;
                    sys = NJUtil.randomNumber(3) + 1;
                    status = 5;
                    timeAtt = template.waitAttack;
                    if (map.isDunClan) {
                        reloadDun();
                        levelBoss = setLevelBoss;
                        if (levelBoss == 1) {
                            dame *= (int) 2.5;
                            hp *= 10;
                            maxexp *= 10;
                        }
                        maxhp = hp;
                    } else if (map.isDunPB) {
                        if (map.getTemplateId() == 116 && (npcId == 82 || npcId == 85)) {
                            setLevelBoss = 1;
                        }
                        levelBoss = setLevelBoss;
                        if (levelBoss == 1) {
                            int n = template.hp * 10;
                            hp = n;
                            maxhp = n;
                            dame = (int) (template.dame * 2.5);
                            maxexp = template.exp * 20;
                            --map.maxBoss1;
                        } else if (levelBoss == 2) {
                            int n = template.hp * 100;
                            hp = n;
                            maxhp = n;
                            dame = (int) (template.dame * 4.2);
                            maxexp = template.exp * 400;
                            --map.maxBoss2;
                        }
                    } else if (setLevelBoss == -1) {
                        if (map.template.isMapChienTruong()) {
                            levelBoss = NJUtil.probability(2000, 150, 100, 0);
                        } else {
                            levelBoss = NJUtil.probability(2000, 20, 5, 0);
                        }
                        if (template.npcTemplateId == 98 || template.npcTemplateId == 99 || template.isBossId()) {
                            levelBoss = 0;
                        }
                        if (!isTathu(map)) {
                            if (levelBoss == 1 && map.maxBoss1 == 0) {
                                levelBoss = 0;
                            }
                            if (levelBoss == 2 && map.maxBoss2 == 0) {
                                levelBoss = 0;
                            }
                            if (Map.isTinhAnh) {
                                levelBoss = 1;
                                Map.isTinhAnh = false;
                            }
                            if (Map.isThulinh) {
                                levelBoss = 2;
                                Map.isThulinh = false;
                            }
                            if (levelBoss == 0 || template.npcTemplateId == 0 || template.npcTemplateId == 54 || level < 10) {
                                hp = template.hp;
                                maxhp = hp;
                                dame = template.dame;
                                maxexp = template.exp;
                                levelBoss = 0;
                            } else if (levelBoss == 1) {
                                int n = template.hp * 10;
                                hp = n;
                                maxhp = n;
                                if (map.template.isMapChienTruong()) {
                                    n = template.hp * 5;
                                    hp = n;
                                    maxhp = n;
                                }
                                dame = (int) (template.dame * 2.5);
                                maxexp = template.exp * 20;
                                --map.maxBoss1;
                            } else if (levelBoss == 2) {
                                int n = template.hp * 100;
                                hp = n;
                                maxhp = n;
                                if (map.template.isMapChienTruong()) {
                                    n = template.hp * 40;
                                    hp = n;
                                    maxhp = n;
                                }
                                dame = (int) (template.dame * 4.2);
                                maxexp = template.exp * 400;
                                --map.maxBoss2;
                            }
                        }
                    } else {
                        levelBoss = setLevelBoss;
                    }
                    if (template.npcTemplateId == 113) {
                        int n = template.hp * level;
                        maxhp = n;
                        hp = n;
                    }
                    Message message = new Message(Cmd.NPC_LIVE);
                    message.writeByte(npcId);
                    message.writeByte(sys);
                    if (levelBoss == 3) {
                        message.writeByte(3);
                    } else {
                        message.writeByte(0);
                    }
                    message.writeInt(maxhp);
                    map.sendToPlayer(message);
                    message.cleanup();
                }
            } else {
                if (timeHit > 0) {
                    --timeHit;
                }
                if (timeHit <= 0 && hits.size() > 0) {
                    hits.clear();
                }
                boolean isDontAtt = false;
                if (timeDisable > 0) {
                    isDontAtt = true;
                    --timeDisable;
                    if (timeDisable == 0) {
                        Message message = new Message(Cmd.NPC_IS_DISABLE);
                        message.writeByte(npcId);
                        message.writeBoolean(false);
                        map.sendToPlayer(message);
                        message.cleanup();
                    }
                }
                if (timeDontMove > 0) {
                    --timeDontMove;
                    if (timeDontMove == 0) {
                        Message message = new Message(Cmd.NPC_IS_MOVE);
                        message.writeByte(npcId);
                        message.writeBoolean(false);
                        map.sendToPlayer(message);
                        message.cleanup();
                    }
                }
                if (timeFire > 0) {
                    --timeFire;
                    if (timeFire == 0) {
                        Message message = new Message(Cmd.NPC_IS_FIRE);
                        message.writeByte(npcId);
                        message.writeBoolean(false);
                        map.sendToPlayer(message);
                        message.cleanup();
                    }
                }
                if (timeIce > 0) {
                    isDontAtt = true;
                    --timeIce;
                    if (timeIce == 0) {
                        Message message = new Message(Cmd.NPC_IS_ICE);
                        message.writeByte(npcId);
                        message.writeBoolean(false);
                        map.sendToPlayer(message);
                        message.cleanup();
                    }
                }
                if (timeWind > 0) {
                    isDontAtt = true;
                    --timeWind;
                    if (timeWind == 0) {
                        Message message = new Message(Cmd.NPC_IS_WIND);
                        message.writeByte(npcId);
                        message.writeBoolean(false);
                        map.sendToPlayer(message);
                        message.cleanup();
                    }
                }
                if (template.npcTemplateId == 144 && map.getTemplateId() == 130 && map.players.size() > 0 && status != 0) {
                    template.rangeAttack = 100000;
                    if (timedoikeo == 0) {
                        if (NJUtil.probability(10, 80) == 0) {
                            isDontAtt = true;
                        }
                        if (NJUtil.randomNumber(2) == 0) {
                            doikeoxanh();
                        } else {
                            doikeodo();
                        }
                    } else if (timedoikeo < 0) {
                        ++timedoikeo;
                        if (timedoikeo == 0) {
                            doikeodo();
                        }
                    } else {
                        --timedoikeo;
                        if (timedoikeo == 0) {
                            doikeoxanh();
                        }
                    }
                } else if (timeAtt > 0) {
                    isDontAtt = true;
                    --timeAtt;
                }
                if (template.rangeAttack <= 0 || isDontAtt) {
                    return;
                }
                int rangeAttx, rangeAtty;
                /*if (template.npcTemplateId == 69 || template.npcTemplateId == 71) {
                    rangeAttx = template.rangeAttack + 1;
                    rangeAtty = 80;
                }*/
                if (NJUtil.randomNumber(template.waitAttack) == 0) {
                    int maxAtt = 1;
                    if (template.isBossId() && template.npcTemplateId != 138) {
                        maxAtt += NJUtil.randomNumber(5);
                    } else if (template.level > 80 && levelBoss == 1) {
                        maxAtt += NJUtil.randomNumber(3);
                    } else if (template.level > 80 && levelBoss == 2) {
                        maxAtt += NJUtil.randomNumber(5);
                    }
                    if (template.npcTemplateId == 144 && map.getTemplateId() == 130 && map.players.size() > 0 && status != 0) {
                        maxAtt = 1;
                    }
                    Vector<Player> aa = new Vector<>();
                    for (int j = hits.size() - 1; j >= 0; --j) {
                        Player player = hits.get(j).player;
                        if (player.isDisonnect) {
                            hits.remove(j);
                        } else if ((template.npcTemplateId != 97 && (template.npcTemplateId != 167 || !map.template.isMapChienTruong())) || player.getTypePk() != 4) {
                            if ((template.npcTemplateId != 96 && (template.npcTemplateId != 166 || !map.template.isMapChienTruong())) || player.getTypePk() != 5) {
                                if (player.getPlayerMainControl().classId == 2 || player.getPlayerMainControl().classId == 4) {
                                    if (levelBoss > 0) {
                                        rangeAttx = template.rangeAttack * 4;
                                        rangeAtty = (int) (template.rangeAttack * 3.5);
                                    } else {
                                        rangeAttx = template.rangeAttack * 3;
                                        rangeAtty = (int) (template.rangeAttack * 2.5);
                                    }
                                } else {
                                    rangeAttx = (int) (template.rangeAttack * 2.5);
                                    rangeAtty = template.rangeAttack * 2;
                                }
                                if (template.npcTemplateId == 69 || template.npcTemplateId == 71) {
                                    rangeAttx = (int) (template.rangeAttack * 1.5);
                                    rangeAtty = 100;
                                }
                                if (player.map.equals(map) && dame > 0 && attack(player, rangeAttx, rangeAtty) && !aa.contains(player)) {
                                    aa.add(player);
                                }
                                if (aa.size() >= maxAtt) {
                                    return;
                                }
                            }
                        }
                    }
                    rangeAttx = template.rangeAttack;
                    rangeAtty = template.rangeAttack;
                    if (template.npcTemplateId == 69 || template.npcTemplateId == 71) {
                        rangeAttx = template.rangeAttack + 1;
                        rangeAtty = 80;
                    }
                    if (dame > 0) {
                        for (int j = 0; j < map.players.size(); ++j) {
                            Player player = map.players.get(j);
                            if (template.npcTemplateId != 144 || map.getTemplateId() != 130 || timedoikeo >= 0 || player.getTypePk() != 5) {
                                if (template.npcTemplateId != 144 || map.getTemplateId() != 130 || timedoikeo <= 0 || player.getTypePk() != 4) {
                                    if (template.npcTemplateId == 144 && map.getTemplateId() == 130) {
                                        if (timedoikeo == 0 || timedoikeo < -20) {
                                            continue;
                                        }
                                        if (timedoikeo > 20) {
                                            continue;
                                        }
                                    }
                                    if ((template.npcTemplateId != 97 && (template.npcTemplateId != 167 || !map.template.isMapChienTruong())) || player.getTypePk() != 4) {
                                        if ((template.npcTemplateId != 96 && (template.npcTemplateId != 166 || !map.template.isMapChienTruong())) || player.getTypePk() != 5) {
                                            if (!player.isNhanban() && attack(player, rangeAttx, rangeAtty) && !aa.contains(player)) {
                                                aa.add(player);
                                            }
                                            if (aa.size() >= maxAtt) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public boolean isHit(Player player) {
        for (Hit hit : hits) {
            if (hit.player.equals(player)) {
                return true;
            }
        }
        return false;
    }

    public Hit getHit(int playerId) {
        for (Hit hit : hits) {
            if (hit.player.playerId == playerId) {
                return hit;
            }
        }
        return null;
    }

    public void checkHoiSinh() {
        try {
            if (status == 0) {
                if (template.npcTemplateId != 144 && template.npcTemplateId != 231) {
                    if (map.getTemplateId() != 130 && map.getTemplateId() != 157 && map.getTemplateId() != 158 && map.getTemplateId() != 159 && template.isBossId() && template.npcTemplateId != 138 && !map.isMapBoss && !map.template.isMapChienTruong()) {
                        return;
                    }
                    if (template.npcTemplateId == 141) {
                        return;
                    }
                    timeHit = 0;
                    ++timeWait;
                }
                if (isTimeReborn()) {
                    if (map.isMapBoss && template.isBossId()) {
                        for (int i = 0; i < Map.bossTrumMapIds.length; ++i) {
                            if (map.template.mapTemplateId == Map.bossTrumMapIds[i] && map.zoneId == Map.bossTrumZoneIds[i]) {
                                Map.bossTrumMapIds[i] = -1;
                                Map.bossTrumZoneIds[i] = -1;
                                break;
                            }
                        }
                    }
                    timeWait = 0;
                    sys = NJUtil.randomNumber(3) + 1;
                    status = 5;
                    timeAtt = template.waitAttack;
                    if (map.isDunClan) {
                        reloadDun();
                        levelBoss = -1;
                        maxhp = hp;
                    } else if (map.isDunPB) {
                        if (map.getTemplateId() == 116 && (npcId == 82 || npcId == 85)) {
                            levelBoss = 1;
                        }
                        if (levelBoss == 1) {
                            int n = template.hp * 10;
                            hp = n;
                            maxhp = n;
                            dame = (int) (template.dame * 2.5);
                            maxexp = template.exp * 20;
                            --map.maxBoss1;
                        } else if (levelBoss == 2) {
                            int n = template.hp * 100;
                            hp = n;
                            maxhp = n;
                            dame = (int) (template.dame * 4.2);
                            maxexp = template.exp * 400;
                            --map.maxBoss2;
                        }
                    } else if (levelBoss == -1) {
                        if (map.template.isMapChienTruong()) {
                            levelBoss = NJUtil.probability(2000, 150, 100, 0);
                        } else {
                            levelBoss = NJUtil.probability(2000, 15, 2, 0);
                        }
                        if (template.npcTemplateId == 98 || template.npcTemplateId == 99 || template.isBossId()) {
                            levelBoss = 0;
                        }
                        if (!isTathu(map)) {
                            if (levelBoss == 1 && map.maxBoss1 == 0) {
                                levelBoss = 0;
                            }
                            if (levelBoss == 2 && map.maxBoss2 == 0) {
                                levelBoss = 0;
                            }
                            if (Map.isTinhAnh) {
                                levelBoss = 1;
                                Map.isTinhAnh = false;
                            }
                            if (Map.isThulinh) {
                                levelBoss = 2;
                                Map.isThulinh = false;
                            }
                            if (levelBoss == 0 || template.npcTemplateId == 0 || template.npcTemplateId == 54 || level < 10) {
                                hp = template.hp;
                                maxhp = hp;
                                dame = template.dame;
                                maxexp = template.exp;
                                levelBoss = 0;
                            } else if (levelBoss == 1) {
                                int n = template.hp * 10;
                                hp = n;
                                maxhp = n;
                                if (map.template.isMapChienTruong()) {
                                    n = template.hp * 5;
                                    hp = n;
                                    maxhp = n;
                                }
                                dame = (int) (template.dame * 2.5);
                                maxexp = template.exp * 20;
                                --map.maxBoss1;
                            } else if (levelBoss == 2) {
                                int n = template.hp * 100;
                                hp = n;
                                maxhp = n;
                                if (map.template.isMapChienTruong()) {
                                    n = template.hp * 40;
                                    hp = n;
                                    maxhp = n;
                                }
                                dame = (int) (template.dame * 4.2);
                                maxexp = template.exp * 400;
                                --map.maxBoss2;
                            }
                        }
                    }
                    if (template.npcTemplateId == 113) {
                        int n = template.hp * level;
                        maxhp = n;
                        hp = n;
                    }
                    Message message = new Message(Cmd.NPC_LIVE);
                    message.writeByte(npcId);
                    message.writeByte(sys);
                    if (levelBoss == 3) {
                        message.writeByte(3);
                    } else {
                        message.writeByte(0);
                    }
                    message.writeInt(maxhp);
                    map.sendToPlayer(message);
                    message.cleanup();
                }
            }
        } catch (Exception e) {
        }
    }

    public int getExp(int dameHit, int plevel) {
        int expAdd = Math.round(maxexp / (float) maxhp * dameHit);
        expAdd -= expAdd * NJUtil.randomNumber(20) / 100;
        if (map.getTemplateId() < 134 || map.getTemplateId() > 137) {
            if (level - plevel > 10) {
                return 0;
            }
            if (plevel > level) {
                expAdd -= expAdd * 10 * (plevel - level) / 100;
            }
            if (expAdd <= 0) {
                return 0;
            }
        }
        return expAdd;
    }

    public int getExpClan(int dameHit) {
        int expAdd = Math.round(maxexp / (float) maxhp * dameHit);
        expAdd -= expAdd * NJUtil.randomNumber(20) / 100;
        return expAdd;
    }

    public void attPlayer(Player player) {
        try {
            if (player.getHp() <= 0) {
                return;
            }
            for (int i = 0; i < player.map.buNhins.size(); ++i) {
                BuNhin buNhin = player.map.buNhins.get(i);
                if (buNhin.player.equals(player)) {
                    int dx = NJUtil.distance(pointx, buNhin.x);
                    int dy = NJUtil.distance(pointy, buNhin.y);
                    if (dx < buNhin.dx && dy < buNhin.dy) {
                        Message message = new Message(Cmd.NPC_ATTACK_BUNHIN);
                        message.writeByte(npcId);
                        message.writeShort(i);
                        message.writeShort(getIdSkillAttack());
                        message.writeByte(getIndexFrameAttack());
                        message.writeByte(getTypeAttack());
                        NJUtil.sendMessage(player.getSession(), message);
                        buNhin.hp -= dame;
                        if (buNhin.hp < 0) {
                            buNhin.clearBuNhin(player.map);
                        }
                        return;
                    }
                }
            }
            timeAtt = template.waitAttack;
            boolean isMiss = false;
            int dameHit = 0;
            int dameMp = 0;
            int dameHp = 0;
            int exac = exactly;
            if (levelBoss == 1) {
                exac += 50;
            } else if (levelBoss == 2) {
                exac += 100;
            } else if (template.isBossId()) {
                exac += 200;
            }
            if (NJUtil.probability(player.getPlayerMainControl().missAll, 100 - player.getPlayerMainControl().missAll) == 0) {
                isMiss = true;
            } else if (player.getPlayerMainControl().miss - exac > 0) {
                int perMiss = player.getPlayerMainControl().miss - exac;
                if (perMiss <= 9000 && perMiss > 1200) {
                    perMiss = 1200;
                }
                if (NJUtil.probability(perMiss, Player.maxmiss - perMiss) == 0) {
                    isMiss = true;
                }
            }
            if (!isMiss) {
                int typeSys = NJUtil.typeSys(sys, player.getSys());
                if (template.npcTemplateId == 145) {
                    dameHit = 401 + NJUtil.randomNumber(1600);
                } else {
                    dameHit = dame - player.getPlayerMainControl().dameDown;
                }
                int perRes = 0;
                if (sys == 1) {
                    perRes = player.getPlayerMainControl().resFire * 100 / -Player.indexRes;
                } else if (sys == 2) {
                    perRes = player.getPlayerMainControl().resIce * 100 / -Player.indexRes;
                } else if (sys == 3) {
                    perRes = player.getPlayerMainControl().resWind * 100 / -Player.indexRes;
                }
                int perRan = NJUtil.randomNumber(7) * (NJUtil.randomBoolean() ? -1 : 1);
                int perSys = 0;
                if (typeSys == 1) {
                    perSys = (player.getPlayerMainControl().sysUp + template.sysDown) * 100 / player.getFullHp();
                } else if (typeSys == 2) {
                    if (player.getPlayerMainControl().sysDown - template.sysUp > 0) {
                        perSys = (player.getPlayerMainControl().sysDown - template.sysUp) * -100 / player.getFullHp();
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
                if (sys == 1) {
                    dameHit -= player.getPlayerMainControl().dameDownFire / 2;
                } else if (sys == 2) {
                    dameHit -= player.getPlayerMainControl().dameDownIce / 2;
                } else if (sys == 3) {
                    dameHit -= player.getPlayerMainControl().dameDownWind / 2;
                }
                if (dameHit < 0) {
                    dameHit = 1;
                }
                if (player.haveLongDenNgoiSao()) {
                    dameHit /= 2;
                }
                if (player.getPlayerMainControl().convertMp > 0 && player.iDamage == 0) {
                    dameMp = dameHit * player.getPlayerMainControl().convertMp / 100;
                    int mp = player.getMp() - dameMp;
                    if (mp < 0) {
                        dameMp = player.getMp();
                        dameHp = dameHit - dameMp - mp;
                    } else {
                        dameHp = dameHit - dameMp;
                    }
                    player.subHP(dameHp);
                    player.subMP(dameMp);
                } else {
                    if (player.iDamage > 0) {
                        dameHit = player.iDamage;
                    }
                    player.subHP(dameHit);
                }
                if (template.npcTemplateId == 72 && NJUtil.probability(30, 70) == 1) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(5);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 2500;
                    player.addEffect(eff);
                } else if (template.npcTemplateId == 79 && NJUtil.probability(30, 70) == 1) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(6);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 1500;
                    player.y = player.autoFall(player.x, player.y);
                    player.addEffect(eff);
                } else if (template.npcTemplateId == 74 && NJUtil.probability(30, 70) == 1) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(7);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 1000;
                    player.y = player.autoFall(player.x, player.y);
                    player.addEffect(eff);
                } else if (template.npcTemplateId == 82 && NJUtil.randomBoolean()) {
                    int ran = NJUtil.randomNumber(3);
                    Effect eff2 = new Effect();
                    eff2.template = ServerController.effTemplates.get(ran + 5);
                    eff2.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    if (ran == 0) {
                        eff2.timeLength = 2500;
                    } else if (ran == 1) {
                        eff2.timeLength = 2000;
                        player.y = player.autoFall(player.x, player.y);
                    } else if (ran == 2) {
                        eff2.timeLength = 1500;
                        player.y = player.autoFall(player.x, player.y);
                    }
                    player.addEffect(eff2);
                } else if (template.npcTemplateId == 139 && NJUtil.randomBoolean()) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(5);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 2000;
                    player.addEffect(eff);
                } else if (template.npcTemplateId == 141 && NJUtil.probability(80, 20) == 1) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(5);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 1500;
                    player.addEffect(eff);
                } else if (template.npcTemplateId == 141 && NJUtil.probability(99, 1) == 1) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(5);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    player.addEffect(eff);
                }
            }
            if (player.playerId >= 0) {
                Message message = new Message(Cmd.NPC_ATTACK_ME);
                message.writeByte(npcId);
                if (player.getPlayerMainControl().convertMp > 0) {
                    message.writeInt(dameHp);
                } else {
                    message.writeInt(dameHit);
                }
                message.writeInt(dameMp);
                message.writeShort(getIdSkillAttack());
                message.writeByte(getIndexFrameAttack());
                message.writeByte(getTypeAttack());
                NJUtil.sendMessage(player.getSession(), message);
                if (player.timeWait > 0) {
                    player.endWait(null);
                }
                if (player.timePick > 0L) {
                    player.timePick = 0L;
                    player.itemMapPick = null;
                    player.endWait(null);
                }
            }
            Message message = new Message(Cmd.NPC_ATTACK_PLAYER);
            message.writeByte(npcId);
            message.writeInt(player.playerId);
            message.writeInt(player.getHp());
            message.writeInt(player.getMp());
            message.writeShort(getIdSkillAttack());
            message.writeByte(getIndexFrameAttack());
            message.writeByte(getTypeAttack());
            player.sendToPlayer(message, false);
            if (dameHit > 0 && player.getPlayerMainControl().reactDame > 0) {
                int ddx = NJUtil.distance(pointx, player.x);
                int ddy = NJUtil.distance(pointy, player.y);
                if (ddx < 120 && ddy < 120) {
                    int perReactDame = player.getPlayerMainControl().reactDame / 6;
                    dameHit = dameHit * perReactDame / 100;
                    if (dameHit > 0) {
                        player.doAttacNpc(this, dameHit, 0, true, false, false);
                    }
                }
            }
            player.checkDie(-1);
        } catch (Exception e) {
        }
    }

    public void attPlayerReact(Player player, int dameReact) {
        try {
            for (int i = 0; i < player.map.buNhins.size(); ++i) {
                BuNhin buNhin = player.map.buNhins.get(i);
                if (buNhin.player.equals(player)) {
                    int dx = NJUtil.distance(pointx, buNhin.x);
                    int dy = NJUtil.distance(pointy, buNhin.y);
                    if (dx < buNhin.dx && dy < buNhin.dy) {
                        Message message = new Message(Cmd.NPC_ATTACK_BUNHIN);
                        message.writeByte(npcId);
                        message.writeShort(i);
                        NJUtil.sendMessage(player.getSession(), message);
                        buNhin.hp -= dame;
                        if (buNhin.hp < 0) {
                            buNhin.clearBuNhin(player.map);
                        }
                        return;
                    }
                }
            }
            boolean isMiss = false;
            if (NJUtil.probability(player.getPlayerMainControl().missAll, 100 - player.getPlayerMainControl().missAll) == 0) {
                isMiss = true;
            } else if (player.getPlayerMainControl().miss - exactly > 0) {
                int perMiss = player.getPlayerMainControl().miss - exactly;
                if (perMiss <= 9000 && perMiss > 1200) {
                    perMiss = 1200;
                }
                if (NJUtil.probability(perMiss, Player.maxmiss - perMiss) == 0) {
                    isMiss = true;
                }
            }
            int dameMp = 0, dameHp = 0;
            if (!isMiss) {
                int typeSys = NJUtil.typeSys(sys, player.getSys());
                int perRes = 0;
                if (sys == 1) {
                    perRes = player.getPlayerMainControl().resFire * 100 / -Player.indexRes;
                } else if (sys == 2) {
                    perRes = player.getPlayerMainControl().resIce * 100 / -Player.indexRes;
                } else if (sys == 3) {
                    perRes = player.getPlayerMainControl().resWind * 100 / -Player.indexRes;
                }
                int perRan = NJUtil.randomNumber(7) * (NJUtil.randomBoolean() ? -1 : 1);
                int perSys = 0;
                if (typeSys == 1) {
                    perSys = (player.getPlayerMainControl().sysUp + template.sysDown) * 100 / player.getFullHp();
                } else if (typeSys == 2) {
                    if (player.getPlayerMainControl().sysDown - template.sysUp > 0) {
                        perSys = (player.getPlayerMainControl().sysDown - template.sysUp) * -100 / player.getFullHp();
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
                int dameHit = dameReact * per / 100;
                if (dameHit < 0) {
                    dameHit = 0;
                }
                if (player.getPlayerMainControl().convertMp > 0 && player.iDamage == 0) {
                    dameMp = dameHit * player.getPlayerMainControl().convertMp / 100;
                    int mp = player.getMp() - dameMp;
                    if (mp < 0) {
                        dameMp = player.getMp();
                        dameHp = dameHit - dameMp - mp;
                    } else {
                        dameHp = dameHit - dameMp;
                    }
                    player.subMP(dameMp);
                    player.subHP(dameHp);
                } else {
                    if (player.iDamage > 0) {
                        dameHit = player.iDamage;
                    }
                    dameHp = dameHit;
                    player.subHP(dameHit);
                }
            }
            player.sendHaveAttReact(dameHp, dameMp);
            if (GameServer.isServerLocal()) {
                LOGGER.info("attPlayerReact. Player: {}, Dame HP: {}, Dame MP: {}", player.name, dameHp, dameMp);
            }
            player.checkDie(-1);
        } catch (Exception e) {
        }
    }

    public boolean attack(Player player, int rangeAttx, int rangeAtty) {
        try {
            int dx = NJUtil.distance(pointx, player.x);
            int dy = NJUtil.distance(pointy, player.y);
            if (player.getPlayerMainControl().status != Player.ME_DIE && !player.getPlayerMainControl().isInvisible) {
                boolean isAtt = false;
                Hit hit = getHit(player.playerId);
                if (hit != null) {
                    if (rangeAttx < hit.rangeAttack) {
                        rangeAttx = hit.rangeAttack;
                    }
                    if (rangeAtty < hit.rangeAttack) {
                        rangeAtty = hit.rangeAttack;
                    }
                }
                if (rangeAttx > template.rangeAttack && dx <= rangeAttx && dy <= rangeAtty && template.rangeAttack > 0) {
                    isAtt = true;
                } else if (hit == null) {
                    if (((template.type == TYPE_DUNG || template.type == TYPE_DI || template.type == TYPE_NHAY || template.type == TYPE_LET) && dx <= rangeAttx && pointy == player.y) || ((template.type == TYPE_BAY || template.type == TYPE_DAU) && dx <= rangeAttx && dy <= rangeAtty)) {
                        isAtt = true;
                    }
                } else if (dx <= rangeAttx && dy <= rangeAtty && template.rangeAttack > 0) {
                    isAtt = true;
                }
                if (isAtt) {
                    if (player.getPlayerMainControl().checkDongBang(this)) {
                        return false;
                    }
                    try {
                        if (template.npcTemplateId == 201 && status != 0) {
                            boolean canattack = true;
                            for (int i = 0; i < map.npcChild.size(); ++i) {
                                if (map.npcChild.get(i).status != STATUS_DIE) {
                                    canattack = false;
                                    break;
                                }
                            }
                            if (canattack) {
                                Message message = new Message(Cmd.NPC_ATTACK_PLAYER);
                                message.writeByte(npcId);
                                message.writeInt(-1);
                                message.writeShort(getIdSkillAttack());
                                message.writeByte(getIndexFrameAttack());
                                message.writeByte(getTypeAttack());
                                player.sendToPlayer(message, true);
                                for (int j = 0; j < map.npcChild.size(); ++j) {
                                    Npc npc = map.npcChild.get(j);
                                    npc.timeWait = 0;
                                    npc.sys = NJUtil.randomNumber(3) + 1;
                                    npc.status = STATUS_NORMAL;
                                    npc.timeAtt = npc.template.waitAttack;
                                    message = new Message(Cmd.NPC_LIVE);
                                    message.writeByte(npc.npcId);
                                    message.writeByte(npc.sys);
                                    npc.levelBoss = -1;
                                    message.writeByte(0);
                                    message.writeInt(npc.maxhp);
                                    map.sendToPlayer(message);
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    attPlayer(player);
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public int getOwner() {
        Hit hit = null;
        for (Hit value : hits) {
            if (hit == null) {
                hit = value;
            } else if (value.dame > hit.dame) {
                hit = value;
            }
        }
        if (hit == null) {
            return -1;
        }
        if (hit.player.isNhanban()) {
            return hit.player.owner.playerId;
        }
        return hit.player.playerId;
    }

    public Player getOwnerPlayer() {
        try {
            Hit hit = null;
            for (Hit value : hits) {
                try {
                    if (hit == null && value.player.map.equals(map)) {
                        hit = value;
                    } else if (hit != null && value.dame > hit.dame && value.player.map.equals(map)) {
                        hit = value;
                    }
                } catch (Exception e) {
                }
            }
            if (hit != null) {
                return hit.player;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int getHitAttack() {
        return 0;
    }

    public int getIdSkillAttack() {
        if (template.isPetNew()) {
            return 1;
        }
        if (template.npcTemplateId == 218 || template.npcTemplateId == 220) {
            return 1;
        }
        if (template.npcTemplateId == 230) {
            return 3;
        }
        if (template.npcTemplateId == 231) {
            return 1;
        }
        if (template.npcTemplateId == 232) {
            return 1;
        }
        if (template.npcTemplateId == 223) {
            return 5;
        }
        if (template.npcTemplateId == 226) {
            return 5;
        }
        if (template.npcTemplateId == 227) {
            return 5;
        }
        return -1;
    }

    public int getIndexFrameAttack() {
        return 1;
    }

    public int getTypeAttack() {
        return 1;
    }

    public int getTemplateId() {
        return template.npcTemplateId;
    }

    public boolean isBoss() {
        return template.isBossId() || levelBoss > 0;
    }

    public boolean isMobEvent() {
        return false;
    }

    public boolean isPrivateNpc() {
        return template.npcTemplateId == 220 || template.npcTemplateId == 230 || template.npcTemplateId == 233;
    }

    public boolean isThanThu() {
        return false;
    }

    public boolean isTinhAnh() {
        return levelBoss == 1;
    }

    public boolean isThuLinh() {
        return levelBoss == 2;
    }

    public boolean isTathu() {
        return levelBoss == 3;
    }

    public boolean isTathu(Map map) {
        if (map.zoneId % 5 == 0) {
            for (int i = 0; i < Map.tathus.length; ++i) {
                if (map.template.mapTemplateId == Map.tathus[i].mapTemplateId && npcId == Map.tathus[i].npcId) {
                    levelBoss = 3;
                    timeReturn = 200;
                    int n = template.hp * 200;
                    hp = n;
                    maxhp = n;
                    dame = template.dame * 3;
                    maxexp = template.exp * 100;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isThanTho() {
        return template.npcTemplateId == 114;
    }

    public boolean isXichPhienThienLong() {
        return template.npcTemplateId == 115;
    }

    public boolean isSamuraiChienTuong() {
        return template.npcTemplateId == 116;
    }

    public boolean isSonTinh() {
        return template.npcTemplateId == 166;
    }

    public boolean isThuyThinh() {
        return template.npcTemplateId == 167;
    }

    public boolean isTuongGiac() {
        return template.npcTemplateId == 218;
    }

    public boolean isTenTrom() {
        return template.npcTemplateId == 221;
    }

    public boolean isDauLinhDaoTac() {
        return template.npcTemplateId == 222;
    }

    public void move() {
    }

    public Message messageMove() {
        return null;
    }

    public void onDie() {
    }

    public boolean isTimeReborn() {
        return (timeWait >= timeReturn && template.npcTemplateId != 202) || (map.isMapBoss && template.isBossId());
    }

    public int randomItemBody() {
        if (level >= 10 && level <= 20) {
            return Item.randomItem(new int[]{ 94, 99, 104, 109, 114, 119, 124, 125, 134, 135, 144, 145, 154, 155, 164, 165, 174, 179, 184, 189 });
        }
        if (level > 20 && level <= 30) {
            return Item.randomItem(new int[]{ 95, 100, 105, 110, 115, 120, 126, 127, 136, 137, 146, 147, 156, 157, 166, 167, 175, 180, 185, 190 });
        }
        if (level > 30 && level <= 40) {
            return Item.randomItem(new int[]{ 96, 101, 106, 111, 116, 121, 128, 129, 138, 139, 148, 149, 158, 159, 168, 169, 176, 181, 186, 191 });
        }
        if (level > 40 && level <= 50) {
            return Item.randomItem(new int[]{ 97, 102, 107, 112, 117, 122, 130, 131, 140, 141, 150, 151, 160, 161, 170, 171, 177, 188, 187, 192 });
        }
        if (level > 50 && level <= 60) {
            return Item.randomItem(new int[]{ 98, 103, 108, 113, 118, 123, 132, 133, 142, 143, 152, 153, 162, 163, 172, 173, 178, 189, 188, 193 });
        }
        if (level > 60 && level <= 70) {
            return Item.randomItem(new int[]{ 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336 });
        }
        if (level > 70) {
            return Item.randomItem(new int[]{ 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 365, 366, 367, 368, 369, 370, 371, 372, 373, 374 });
        }
        return Item.randomItem(new int[]{ 194, 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208 });
    }

    public int randomMoney() {
        int moneyDrop = NJUtil.randomNumber(20) + 150;
        if (level >= 10 && level <= 20) {
            moneyDrop = NJUtil.randomNumber(40) + 300;
        }
        if (level > 20 && level <= 30) {
            moneyDrop = NJUtil.randomNumber(60) + 450;
        }
        if (level > 30 && level <= 40) {
            moneyDrop = NJUtil.randomNumber(80) + 600;
        }
        if (level > 40 && level <= 50) {
            moneyDrop = NJUtil.randomNumber(100) + 750;
        }
        if (level > 50 && level <= 60) {
            moneyDrop = NJUtil.randomNumber(150) + 900;
        }
        if (level > 60 && level <= 70) {
            moneyDrop = NJUtil.randomNumber(200) + 1050;
        }
        if (level > 70 && level <= 80) {
            moneyDrop = NJUtil.randomNumber(300) + 1200;
        }
        if (level > 80 && level <= 90) {
            moneyDrop = NJUtil.randomNumber(400) + 1500;
        }
        if (level > 90) {
            moneyDrop = NJUtil.randomNumber(700) + 1800;
        }
        return moneyDrop * GameServer.yenRate;
    }

    public int randomThrowHT(boolean isMax) {
        int[] ids = {};
        int typeLevel = Math.min(level / 10, 7);
        switch (typeLevel) {
            case 0:
                ids = new int[]{ 0 };
                break;
            case 1:
                ids = new int[]{ 0, 0, 1 };
                break;
            case 2:
                ids = new int[]{ 0, 0, 0, 1, 1, 2 };
                break;
            case 3:
                ids = new int[]{ 1, 1, 1, 1, 2, 2, 3 };
                break;
            case 4:
                ids = new int[]{ 2, 2, 2, 2, 3, 3, 4 };
                break;
            case 5:
                ids = new int[]{ 3, 3, 3, 3, 4, 4, 5 };
                break;
            case 6:
                ids = new int[]{ 4, 4, 4, 4, 5, 5, 6 };
                break;
            case 7:
                ids = new int[]{ 5, 5, 5, 5, 6, 6, 7 };
                break;
        }
        if (isMax) {
            return ids[ids.length - 1];
        }
        return Item.randomItem(ids);
    }

    public int throwItemHP() {
        if (level >= 10 && level <= 30) {
            return 14;
        }
        if (level > 30 && level <= 50) {
            return 15;
        }
        if (level > 50 && level <= 70) {
            return 16;
        }
        if (level > 70) {
            return 17;
        }
        return 13;
    }

    public int throwItemMP() {
        if (level >= 10 && level <= 30) {
            return 19;
        }
        if (level > 30 && level <= 50) {
            return 20;
        }
        if (level > 50 && level <= 70) {
            return 21;
        }
        if (level > 70) {
            return 22;
        }
        return 18;
    }

    public void reloadDun() {
        int idTemp = level;
        if (idTemp == 54) {
            idTemp = 55;
        }
        NpcTemplate temp = ServerController.npcTemplates.get(idTemp);
        int lv = 31;
        int hp30 = 9900;
        int hpp = 0;
        while (lv != temp.level) {
            hpp = (hp30 = hp30 + 1500 + NJUtil.randomNumber(500));
            ++lv;
        }
        dame = (int) (temp.dame * 1.2);
        hp = hpp * 3;
        if (template.npcTemplateId == 76) {
            hp += hp / 20;
            reactDame = 80;
        } else if (template.npcTemplateId == 77) {
            miss = 800;
        } else if (template.npcTemplateId == 80) {
            dame -= dame / 20;
        } else if (template.npcTemplateId == 73 || template.npcTemplateId == 75 || template.npcTemplateId == 78) {
            hp *= (int) 1.5;
            dame *= (int) 1.5;
        } else if (template.npcTemplateId == 82) {
            dame *= (int) 2.5;
            miss = 2000;
            hp *= 200;
        }
        maxhp = hp;
        maxexp = hp / 10 + hp / 100 * level;
        maxexp *= (int) 1.2;
        if (template.npcTemplateId == 81) {
            dame = 0;
            maxexp = 0;
            hp *= 3;
            maxhp *= 3;
        }
    }

    public void reloadDunVA() {
        int idTemp = level;
        if (idTemp == 54) {
            idTemp = 55;
        }
        NpcTemplate temp = ServerController.npcTemplates.get(idTemp);
        int lv = 31;
        int hp30 = 9900;
        int hpp = 0;
        while (lv != temp.level) {
            hpp = (hp30 = hp30 + 1500 + NJUtil.randomNumber(500));
            ++lv;
        }
        dame = temp.dame;
        hp = (int) (hpp * 1.5);
        if (template.npcTemplateId == 76) {
            hp += hp / 20;
            reactDame = 80;
        } else if (template.npcTemplateId == 77) {
            miss = 800;
        } else if (template.npcTemplateId == 80) {
            dame -= dame / 20;
        } else if (template.npcTemplateId == 73 || template.npcTemplateId == 75 || template.npcTemplateId == 78) {
            hp *= (int) 1.5;
            dame *= (int) 1.5;
        } else if (template.npcTemplateId == 82) {
            dame *= (int) 2.5;
            miss = 2000;
            hp *= 200;
        }
        maxhp = hp;
        maxexp = hp / 10 + hp / 100 * level;
        maxexp *= (int) 1.2;
        if (template.npcTemplateId == 81) {
            dame = 0;
            maxexp = 0;
            hp *= 3;
            maxhp *= 3;
        }
    }

    public void doikeodo() {
        try {
            timedoikeo = 70;
            Message message = new Message(Cmd.SERVER_ALERT);
            if (GameServer.isSvEnglish()) {
                message.writeUTF("Black candy, give me a candy.");
            } else {
                message.writeUTF("Kẹo đen, hãy đưa kẹo đây.");
            }
            for (int i = 0; i < Map.mapkeos.size(); ++i) {
                if (Map.mapkeos.get(i).zoneId == map.zoneId) {
                    Map.mapkeos.get(i).sendToPlayer(message);
                }
            }
        } catch (Exception e) {
        }
    }

    public void doikeoxanh() {
        try {
            timedoikeo = -70;
            Message message = new Message(Cmd.SERVER_ALERT);
            if (GameServer.isSvEnglish()) {
                message.writeUTF("White candy, give me a candy.");
            } else {
                message.writeUTF("Kẹo trắng, hãy đưa kẹo đây.");
            }
            for (int i = 0; i < Map.mapkeos.size(); ++i) {
                if (Map.mapkeos.get(i).zoneId == map.zoneId) {
                    Map.mapkeos.get(i).sendToPlayer(message);
                }
            }
        } catch (Exception e) {
        }
    }
}
