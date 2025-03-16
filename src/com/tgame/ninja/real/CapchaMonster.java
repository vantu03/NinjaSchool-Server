package com.tgame.ninja.real;

import com.tgame.ninja.io.*;
import java.util.*;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.*;

public class CapchaMonster extends Npc {

    public StringBuilder stConfirm;

    public long timeLive;

    public Capcha capcha;

    public int damAttack;

    public CapchaMonster(int npcId, int npcTemplateId, Map map) {
        super(npcId, npcTemplateId, map);
        stConfirm = new StringBuilder("999999");
        timeLive = System.currentTimeMillis();
        capcha = null;
        damAttack = 10;
    }

    public void initCapcha(int zoom) {
        capcha = new Capcha(zoom - 1);
    }

    @Override
    public boolean isHit(Player player) {
        attPlayer(player);
        return true;
    }

    @Override
    public boolean attack(Player player, int rangeAttx, int rangeAtty) {
        return (isPrivateNpc() || player.capcha != null) && (player.capcha == null || player.capcha.npcId == npcId) && (!isPrivateNpc() || player.privateNpc != null) && (player.privateNpc == null || player.privateNpc.npcId == npcId) && super.attack(player, rangeAttx, rangeAtty);
    }

    @Override
    public void attPlayer(Player player) {
        try {
            if (player.getHp() <= 0) {
                return;
            }
            timeAtt = template.waitAttack;
            int dameHit = player.hp_full * damAttack / 100;
            int dameMp = 0;
            if (isPrivateNpc()) {
                dameHit = player.hp_full * 5 / 100;
            }
            player.subHP(dameHit);
            if (player.playerId >= 0) {
                Message message = new Message(Cmd.NPC_ATTACK_ME);
                message.writeByte(npcId);
                message.writeInt(dameHit);
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
            player.checkDie(-1);
        } catch (Exception ex) {
        }
    }

    @Override
    public void update(Map map, int setLevelBoss) {
        try {
            if (System.currentTimeMillis() - timeLive >= 30000L) {
                timeLive = System.currentTimeMillis();
                damAttack += 10;
                if (damAttack > 50) {
                    damAttack = 50;
                }
            }
            if (timeHit > 0) {
                --timeHit;
            }
            if (timeHit <= 0 && hits.size() > 0) {
                hits.clear();
            }
            boolean isDontAtt = false;
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
            if (timeAtt > 0) {
                isDontAtt = true;
                --timeAtt;
            }
            if (template.rangeAttack <= 0 || isDontAtt) {
                return;
            }
            int rangeAttx;
            int rangeAtty;
            if (NJUtil.randomNumber(template.waitAttack) == 0) {
                int maxAtt = 1;
                Vector<Player> aa = new Vector<>();
                for (int i = hits.size() - 1; i >= 0; --i) {
                    Player player = hits.get(i).player;
                    if (player.isDisonnect) {
                        hits.remove(i);
                    } else {
                        rangeAttx = (int) (template.rangeAttack * 2.5);
                        rangeAtty = template.rangeAttack * 2;
                        if (player.map.equals(map) && dame > 0 && attack(player, rangeAttx, rangeAtty)) {
                            aa.add(player);
                        }
                        if (aa.size() >= maxAtt) {
                            return;
                        }
                    }
                }
                rangeAttx = template.rangeAttack;
                rangeAtty = template.rangeAttack;
                if (dame > 0) {
                    for (int i = 0; i < map.players.size(); ++i) {
                        Player player = map.players.get(i);
                        if (!player.isNhanban() && attack(player, rangeAttx, rangeAtty)) {
                            aa.add(player);
                        }
                        if (aa.size() >= maxAtt) {
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
    }

    public Vector<byte[]> loadImage(int x) {
        NpcTemplate npcTemplate = ServerController.npcTemplates.get(219);
        Vector<byte[]> datas = npcTemplate.loadData(x);
        Vector<byte[]> imgs = new Vector<>();
        int pc = NJUtil.random.nextInt(30) + 1;
        for (int i = 0; i < datas.size(); ++i) {
            imgs.add(capcha.createImage(i + 1, pc));
        }
        return imgs;
    }
}
