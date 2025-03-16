package com.tgame.ninja.real;

import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class PlayerArena extends Player {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerArena.class);

    public byte attack35AfterBuff;

    public byte maxHp;

    public byte way;

    public long timeResistDongbang;

    public PlayerArena() {
        attack35AfterBuff = 0;
        maxHp = 10;
        way = 2;
        timeResistDongbang = 0L;
        timeSendMove = System.currentTimeMillis();
    }

    public PlayerArena(Session conn, String name, int gender, int headId) {
        super(conn, name, gender, headId);
        attack35AfterBuff = 0;
        maxHp = 10;
        way = 2;
        timeResistDongbang = 0L;
        isMainchar = false;
        timeSendMove = System.currentTimeMillis();
    }

    public PlayerArena(String name, int gender, int headId) {
        super(name, gender, headId);
        attack35AfterBuff = 0;
        maxHp = 10;
        way = 2;
        timeResistDongbang = 0L;
        isMainchar = false;
        timeSendMove = System.currentTimeMillis();
    }

    @Override
    public boolean isAttack(boolean isBuff) {
        if (myskill == null) {
            return false;
        }
        if (System.currentTimeMillis() - myskill.timeAttackNext < 0L) {
            return false;
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
        myskill.timeAttackNext = System.currentTimeMillis() + timeNext;
        return true;
    }

    @Override
    public boolean isAttackPlayer(Player player) {
        return true;
    }

    @Override
    public void update() {
        try {
            mp = mp_full;
            if (status == 1 || hp <= 0) {
                return;
            }
            int dir = way;
            Player p = null;
            for (int i = 0; i < map.players.size(); ++i) {
                if (!map.players.get(i).isNhanban() && !map.players.get(i).isPlayerArena() && !map.players.get(i).isInvisible) {
                    p = map.players.get(i);
                    break;
                }
            }
            long pc = 60L;
            if (hp < hp_full * pc / 100L) {
                if (maxHp > 0) {
                    updateHp(1500);
                    --maxHp;
                }
                if (isTieu()) {
                    Skill sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[0][classId]);
                    if (sk != null) {
                        skillSelect(sk, true);
                        doUseSkillMyBuff();
                    }
                } else if (isCung()) {
                    Skill sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[3][classId]);
                    if (sk != null) {
                        skillSelect(sk, true);
                        doUseSkillMyBuff();
                        sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[0][classId]);
                        skillSelect(sk, true);
                        doUseSkillMyBuff();
                    }
                }
            }
            pc = 80L;
            if (hp < hp_full * pc / 100L && isQuat()) {
                Skill sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[2][classId]);
                if (sk != null) {
                    skillSelect(sk, true);
                    doUseSkillMyBuff();
                }
            }
            if (isQuat()) {
                Skill sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[0][classId]);
                if (sk != null) {
                    skillSelect(sk, true);
                    doUseSkillMyBuff();
                }
                sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[1][classId]);
                if (sk != null) {
                    skillSelect(sk, true);
                    doUseSkillMyBuff();
                }
            }
            if (isKiem()) {
                Skill sk = getSkill(PlayerArena.ID_SKILL_35_40_15_60[0][classId]);
                if (sk != null) {
                    skillSelect(sk, true);
                    doUseSkillMyBuff();
                }
            }
            int distance = 0;
            if (p != null) {
                distance = NJUtil.distance(x, p.x);
                if (x < p.x - 50) {
                    dir = 1;
                } else if (x > p.x + 50) {
                    dir = 2;
                } else {
                    int d = NJUtil.distance(x, p.x);
                    if (d < 70) {
                        dir = 0;
                        if (!canNotAttack()) {
                            lastSkill = System.currentTimeMillis();
                            Vector<Integer> playerindex = new Vector<>();
                            playerindex.add(p.playerId);
                            Skill skill35 = getSkill(PlayerArena.ID_SKILL_35_40_15_60[0][classId]);
                            if (isKunai() || isDao()) {
                                skillSelect(skill35, true);
                                if (isAttack(false)) {
                                    attackPlayer(playerindex, null);
                                }
                            }
                            if (!isQuat()) {
                                Skill sk2 = getSkill(PlayerArena.ID_SKILL_35_40_15_60[1][classId]);
                                if (sk2 != null) {
                                    skillSelect(sk2, true);
                                    if (isAttack(false)) {
                                        attackPlayer(playerindex, null);
                                    }
                                }
                            }
                            if (playercopy == null) {
                                Skill skillpt = getSkillPhanThan();
                                if (skillpt != null) {
                                    skillSelect(skillpt, false);
                                    doUseSkillMyBuff();
                                }
                            }
                            Skill sk3 = getSkillAttackHighest();
                            skillSelect(sk3, false);
                            if (isAttack(false)) {
                                attackPlayer(playerindex, null);
                            }
                        }
                    }
                }
            }
            if (System.currentTimeMillis() - timeSendMove >= 0L) {
                timeSendMove = System.currentTimeMillis() + 100L;
                if (!canNotMove()) {
                    int d = 70;
                    if (distance < 70) {
                        d = distance + 70;
                    }
                    if (dir == 1) {
                        if (x + d > 661) {
                            x = 651;
                            way = 2;
                        } else {
                            x += (short) d;
                        }
                        sendMove();
                    } else if (dir == 2) {
                        if (x - d < 107) {
                            x = 117;
                            way = 1;
                        } else {
                            x -= (short) d;
                        }
                        sendMove();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public void sendMove() {
        super.sendMove();
        if (playercopy != null) {
            int dd = 0;
            playercopy.x = (short) (x + dd);
            playercopy.y = y;
            if (map.template.tileTypeAt(x, y, 2)) {
                if (map.template.tileTypeAt(x + 24, y, 2)) {
                    playercopy.x = (short) (x + 24);
                } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                    playercopy.x = (short) (x - 24);
                }
            }
            if (playercopy.x < 0) {
                playercopy.x = 12;
            }
            if (playercopy.x >= map.template.w) {
                playercopy.x = (short) (map.template.w - 12);
            }
            playercopy.sendMove();
        }
    }

    @Override
    public Point attackPlayer(Vector<Integer> playerIndexs, Point point) {
        Vector<Attack> atts = new Vector<>();
        try {
            Player p = this;
            for (int j = 0; j < p.effects.size(); ++j) {
                if (p.effects.get(j).template.type == 10) {
                    return null;
                }
            }
            boolean isUpdame;
            boolean isChiMang = isUpdame = p.checkClearEffect11();
            p.checkClearEffect12();
            if (p.getSkillValue().template.type == 0 || p.getSkillValue().template.type == 3 || p.getSkillValue().template.type == 2 || p.getSkillValue().template.type == 4) {
                return null;
            }
            if (playerIndexs.size() == 0) {
                return point;
            }
            for (Integer playerIndex : playerIndexs) {
                Player player = map.getPlayer(playerIndex);
                if (player != null && player.getPlayerMainControl().status != Player.ME_DIE && !player.getPlayerMainControl().isInvisible) {
                    if (!player.isNhanban()) {
                        boolean isNotAtt = false;
                        for (int k = 0; k < player.getPlayerMainControl().effects.size(); ++k) {
                            if (player.getPlayerMainControl().effects.get(k).template.type == 10) {
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
                            int typeAtt = playerId;
                            int dameHit = doAttactPlayer(player, p.dame_full - player.dameDown, false, isChiMang, isUpdame);
                            if (player.haveLongDenNgoiSao()) {
                                dameHit /= 2;
                            }
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
            if (atts.size() > 0 && p.getSkillValue().template.skillTemplateId == 42) {
                if (p.skillDao35 == 0) {
                    p.skillDao35 = 1;
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(18);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 1500 + p.timeKeepDao;
                    atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                    atts.firstElement().playerHit.addEffect(eff);
                    x = atts.firstElement().playerHit.x;
                    y = atts.firstElement().playerHit.y;
                    moveFastPlayer(atts.firstElement().playerHit.playerId);
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
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(18);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = myskill.options.firstElement().param * 1000;
                atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                atts.firstElement().playerHit.addEffect(eff);
                return null;
            }
            sendShowAtt(atts);
            for (Attack att : atts) {
                att.playerHit.sendHaveAtt(att.dameHit);
                if (att.dameHit < 0) {
                    att.dameHit *= -1;
                }
                if (att.dameHit > 0) {
                    if (att.playerHit.hp <= 0) {
                        map.setNamePlayerLose(att.playerHit.name);
                    }
                    if (att.playerHit.reactDame > 0) {
                        int ddx = NJUtil.distance(x, att.playerHit.x);
                        int ddy = NJUtil.distance(y, att.playerHit.y);
                        if (ddx < 100 && ddy < 100) {
                            att.dameHit = att.dameHit * 5 * att.playerHit.reactDame / 1000;
                            if (att.dameHit > 0) {
                                att.dameHit = att.playerHit.doAttactPlayer(this, att.dameHit, true, isChiMang, isUpdame);
                                if (att.dameHit > 0) {
                                    sendHaveAtt(att.dameHit);
                                    if (hp <= 0) {
                                        map.setNamePlayerLose(name);
                                    }
                                    checkDie(att.typeAtt);
                                }
                            }
                        }
                    }
                    att.playerHit.checkDie(att.typeAtt);
                }
            }
            if (playercopy != null && !isControlCharNhanBan()) {
                Skill sk = playercopy.getSkillAttackHighest();
                if (sk != null) {
                    playercopy.skillSelect(sk, false);
                }
                playercopy.attackPlayer(playerIndexs, point);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return point;
    }

    @Override
    public Player clonePlayer(int minute) {
        if (playercopy != null) {
            return playercopy;
        }
        int[][] hid = {
            { 11, 26, 27, 28 },
            { 2, 23, 24, 25 }
        };
        playercopy = ServerController.doCreatePlayerCopy(name, gender, hid[gender][NJUtil.random.nextInt(hid[gender].length)], this);
        if (playercopy != null) {
            playercopy.x = x;
            playercopy.y = y;
            if (map.template.tileTypeAt(x, y, 2)) {
                if (map.template.tileTypeAt(x + 24, y, 2)) {
                    playercopy.x = (short) (x + 24);
                } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                    playercopy.x = (short) (x - 24);
                }
            }
            playercopy.owner = this;
            playercopy.timeUpdate = 4;
            playercopy.isMainchar = false;
            playercopy.sysNpc = NJUtil.randomNumber(3) + 1;
            playercopy.timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
            timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
            playercopy.login_date = System.currentTimeMillis();
            if (playercopy.skills.size() > 0) {
                playercopy.myskill = playercopy.skills.get(0);
            }
            playercopy.level = playercopy.getLevel();
            if (dataRms[1][PlayerArena.getindexRms.get("CSkill")] != null && dataRms[1][PlayerArena.getindexRms.get("CSkill")].length > 0) {
                playercopy.skillSelect(playercopy.getSkill(dataRms[1][PlayerArena.getindexRms.get("CSkill")][0]), false);
            }
            if (playercopy.level < 10) {
                if (playercopy.itemBodys[1] == null) {
                    Item it = new Item(194, true, "PlayerArena");
                    playercopy.addItem(it, 5, it.template.type);
                }
                int ss = 0;
                for (int i = 0; i <= 9; ++i) {
                    ss += (int) PlayerArena.exps1[i];
                }
                long exp1 = ss - playercopy.exp;
                playercopy.sendUpdateExp(exp1, true);
            }
            playercopy.resetData();
            playercopy.changeNpcMe();
            try {
                playercopy.setFirstJoinGame(false);
                map.waitGoInMap(playercopy);
                playercopy.taskFinish = 10000;
                playercopy.taskMain = new Task(10000);
            } catch (Exception e) {
                LOGGER.error("", e);
            }
            if (((playercopy.effId_food >= 0 && playercopy.effId_food <= 4) || playercopy.effId_food == 28 || playercopy.effId_food == 30 || playercopy.effId_food == 31) && playercopy.timeEff_food > 0) {
                playercopy.useEff(playercopy.effId_food, playercopy.timeEff_food);
            }
            if (playercopy.effId_exp_bonus == 22 && playercopy.timeEff_exp_bonus > 0) {
                playercopy.useExpx2(playercopy.timeEff_exp_bonus);
            }
            if (playercopy.timeEff_ThiLuyen > 0) {
                playercopy.useThiLuyenThiep(playercopy.timeEff_ThiLuyen);
            }
            playercopy.loadThuCuoi(1);
            playercopy.typePk = PK_DOSAT;
            updateTypePk();
        }
        return null;
    }

    @Override
    public Skill getSkillValue() {
        return myskill;
    }

    @Override
    public boolean isPlayerArena() {
        return true;
    }

    @Override
    public boolean canNotAttack() {
        if (System.currentTimeMillis() - lastSkill <= 500L) {
            return true;
        }
        for (int i = effects.size() - 1; i >= 0; --i) {
            if (effects.get(i).template.type == 2 || effects.get(i).template.type == 3 || effects.get(i).template.type == 10) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long getTimeResistDongBang() {
        return timeResistDongbang;
    }

    @Override
    public void setTimeResistDongBang(int timeResistDongbang) {
        this.timeResistDongbang = System.currentTimeMillis() + timeResistDongbang;
    }

    public void doUseSkillMyBuff() {
        try {
            Player playerMain = getPlayerMainControl();
            if (map.isDunVD) {
                DunVD dun = (DunVD) map;
                if (!dun.phe1.contains(this) && !dun.phe2.contains(this)) {
                    return;
                }
            }
            if (isLockUseItem()) {
                return;
            }
            checkClearEffect11();
            checkClearEffect12();
            if (!isAttack(true)) {
                return;
            }
            if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId != 49) {
                if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 27) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(8);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = playerMain.myskill.options.firstElement().param + playerMain.myskill.options.firstElement().param * playerMain.perUpBuff / 100;
                    addEffect(eff);
                    if (playerMain.party != null) {
                        playerMain.party.sendEffBuff(this, eff, playerMain.myskill.options.lastElement().param + playerMain.myskill.options.lastElement().param * playerMain.perUpBuff / 100);
                    }
                } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 51) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(9);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 30000;
                    addEffect(eff);
                    playerMain.sumonHide = new SumonHide((short) (playerMain.dame_full * playerMain.myskill.options.firstElement().param / 100), (short) playerMain.myskill.dx, (short) playerMain.myskill.dy);
                } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 30) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(10);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 90000;
                    convertMp = (short) myskill.options.firstElement().param;
                    addEffect(eff);
                } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 52) {
                    isInvisible = true;
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(16);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = playerMain.myskill.options.firstElement().param;
                    addEffect(eff);
                } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 53) {
                    isInvisible = true;
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(15);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = playerMain.myskill.options.firstElement().param * 1000;
                    addEffect(eff);
                } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 56) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(17);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 5000;
                    eff.param = playerMain.myskill.options.firstElement().param;
                    addEffect(eff);
                } else if (playerMain.myskill.template.skillTemplateId == 51) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(19);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = 90000;
                    eff.options = new Vector<>();
                    for (int i = 0; i < playerMain.myskill.options.size(); ++i) {
                        SkillOption clone;
                        SkillOption op = clone = playerMain.myskill.options.get(i).clone();
                        clone.param += op.param * perUpBuff / 100;
                        eff.options.add(op);
                    }
                    addEffect(eff);
                    updateData();
                    if (party != null) {
                        party.sendEffBuff(this, eff, eff.param);
                    }
                } else if (playerMain.myskill.template.skillTemplateId == 52) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(20);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = myskill.options.firstElement().param * 1000;
                    eff.timeLength += eff.timeLength * perUpBuff / 100;
                    eff.options = new Vector<>();
                    for (int i = 0; i < myskill.options.size(); ++i) {
                        if (playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 40 || playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 41 || playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 42) {
                            eff.options.add(playerMain.myskill.options.get(i));
                        }
                    }
                    addEffect(eff);
                    updateData();
                    if (party != null) {
                        party.sendEffBuff(this, eff, eff.param);
                    }
                } else if (myskill.template.skillTemplateId == 58) {
                    updateMp(0);
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(11);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = myskill.options.firstElement().param;
                    eff.param = 15000;
                    addEffect(eff);
                    playerMain.updateData();
                } else if (GameServer.openPhanThan && playerMain.myskill.isSkillPhanThan() && isMainchar) {
                    clonePlayer(myskill.options.get(0).param);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }
}
