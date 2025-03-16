package com.tgame.ninja.model;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.real.Npc;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class Party {

    public Vector<Player> players;

    public Vector<Effect> effBuffs;

    public int[] ids;

    public byte[] times;

    public int[] idPleases;

    public byte[] timePleases;

    public int space;

    public boolean isLock;

    public Party(Player p) {
        players = new Vector<>();
        effBuffs = new Vector<>();
        ids = new int[10];
        times = new byte[10];
        idPleases = new int[10];
        timePleases = new byte[10];
        space = 600;
        players.add(p);
        for (int i = 0; i < ids.length; ++i) {
            ids[i] = -1;
            idPleases[i] = -1;
        }
    }

    public void changeTeamLeader(int index) {
        Message message = null;
        try {
            message = NJUtil.messageSubCommand(Cmd.CHANGE_TEAMLEADER);
            message.writeByte(index);
            for (Player player : players) {
                player.clearTestDun();
                player.getSession().sendMessage(message);
            }
        } catch (Exception e) {
        } finally {
            if (message != null) {
                message.cleanup();
            }
        }
    }

    public void clearInvite(int playerId) {
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i] == playerId) {
                ids[i] = -1;
                times[i] = 0;
                break;
            }
        }
    }

    public boolean isFull() {
        return players.size() >= 6;
    }

    public boolean isHaveInvite(Player p) {
        for (int i = 0; i < ids.length; ++i) {
            if (ids[i] == p.playerId) {
                ids[i] = -1;
                times[i] = 0;
                players.add(p);
                p.clearTestDun();
                return true;
            }
        }
        return false;
    }

    public boolean isHavePlease(Player p) {
        for (int i = 0; i < idPleases.length; ++i) {
            if (idPleases[i] == p.playerId) {
                idPleases[i] = -1;
                timePleases[i] = 0;
                players.add(p);
                return true;
            }
        }
        return false;
    }

    public boolean isTeamLeader(Player p) {
        return players.firstElement().equals(p);
    }

    public void moveOut(Player p) {
        try {
            players.remove(p);
            sendParty();
            p.party = null;
            Message message = NJUtil.messageSubCommand(Cmd.MOVE_MEMBER);
            NJUtil.sendMessage(p.getSession(), message);
        } catch (Exception e) {
        }
    }

    public void out(Player p) {
        try {
            if (players.size() > 1) {
                if (players.get(0).equals(p)) {
                    for (int i = 1; i < players.size(); ++i) {
                        NJUtil.sendServer(players.get(i).getSession(), players.get(1).name + " " + AlertFunction.TEAMLEADER(players.get(i).getSession()));
                    }
                }
                players.remove(p);
                sendParty();
            }
            p.party = null;
            p.clearTestDun();
            NJUtil.sendMessage(p.getSession(), new Message(Cmd.PARTY_OUT));
        } catch (Exception e) {
        }
    }

    public void sendAddExp(Player player, int expAdd) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < space && dy < space && NJUtil.distance(player.level, pInParty.level) <= 12) {
                int exp = (pInParty.buffExp > 0) ? (expAdd + expAdd * pInParty.buffExp / 100) : expAdd;
                pInParty.sendUpdateExp(exp, true);
            }
        }
    }

    public void sendEffBuff(Player player, Effect eff, int param) {
        int maxBuff = player.myskill.maxFight - 1;
        int countBuff = 0;
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (countBuff > maxBuff) {
                break;
            }
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < player.myskill.dx && dy < player.myskill.dy) {
                ++countBuff;
                Effect effFriend = eff.clone();
                effFriend.param = param;
                if (player.myskill.template.skillTemplateId == 52) {
                    effFriend.options = new Vector<>();
                    for (int j = 0; j < player.myskill.options.size(); ++j) {
                        if (player.myskill.options.get(j).optionTemplate.skillOptionTemplateId == 47 || player.myskill.options.get(j).optionTemplate.skillOptionTemplateId == 54 || player.myskill.options.get(j).optionTemplate.skillOptionTemplateId == 57) {
                            effFriend.options.add(player.myskill.options.get(j));
                        }
                    }
                }
                pInParty.addEffect(effFriend);
                pInParty.updateData();
            }
        }
    }

    public void sendGetTaskBoss(Player player, TaskOrder taskLoopBoss) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < space && dy < space && pInParty.countLoopBoos > 0) {
                --pInParty.countLoopBoos;
                pInParty.getTaskOrder(pInParty.taskLoopBoss = taskLoopBoss.clone());
            }
        }
    }

    public void sendParty() {
        Message message = null;
        try {
            message = new Message(Cmd.PLAYER_IN_PARTY);
            message.writeBoolean(isLock);
            for (Player p : players) {
                message.writeInt(p.playerId);
                message.writeByte(p.getPlayerMainControl().classId);
                message.writeUTF(p.getPlayerMainControl().name);
            }
            for (Player player : players) {
                player.getSession().sendMessage(message);
            }
        } catch (Exception e) {
        } finally {
            if (message != null) {
                message.cleanup();
            }
        }
    }

    public void sendTaskAtt(Player p) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(p.x, pInParty.x);
            int dy = NJUtil.distance(p.y, pInParty.y);
            if (!pInParty.equals(p) && pInParty.map.equals(p.map) && dx < space && dy < space && pInParty.taskMain != null && pInParty.taskMain.template.taskId == p.taskMain.template.taskId && pInParty.taskMain.index == p.taskMain.index) {
                pInParty.checkTaskIndex();
            }
        }
    }

    public void sendTaskPick(Player player, Item item) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < space && dy < space && pInParty.taskMain != null && pInParty.taskMain.template.taskId == player.taskMain.template.taskId && pInParty.taskMain.index == player.taskMain.index) {
                pInParty.checkPickTask(item, false);
            }
        }
    }

    public void sendTeam(Message message) {
        try {
            for (Player player : players) {
                player.getSession().sendMessage(message);
            }
        } catch (Exception e) {
            return;
        } finally {
            if (message != null) {
                message.cleanup();
            }
        }
        if (message != null) {
            message.cleanup();
        }
    }

    public void sendUpdateTaskBoss(Player player, Npc npc) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < space && dy < space && pInParty.taskLoopBoss != null) {
                pInParty.checkTaskBoos(npc);
            }
        }
    }

    public void sendUpdateTaskDay(Player player, Npc npc) {
        for (Player pInParty : players) {
            int dx = NJUtil.distance(player.x, pInParty.x);
            int dy = NJUtil.distance(player.y, pInParty.y);
            if (!pInParty.equals(player) && pInParty.map.equals(player.map) && dx < space && dy < space && pInParty.taskLoopDay != null) {
                pInParty.checkTaskDay(npc);
            }
        }
    }

    public boolean setInvite(int playerId) {
        int index = -1;
        for (int i = 0; i < ids.length; ++i) {
            if (index == -1 && ids[i] == -1) {
                index = i;
            }
            if (ids[i] == playerId) {
                return true;
            }
        }
        ids[index] = playerId;
        times[index] = 50;
        return false;
    }

    public int setPleaseParty(int playerId) {
        int index = -1;
        for (int i = 0; i < idPleases.length; ++i) {
            if (index == -1 && idPleases[i] == -1) {
                index = i;
            }
            if (idPleases[i] == playerId) {
                return 1;
            }
        }
        if (index == -1) {
            return -1;
        }
        idPleases[index] = playerId;
        timePleases[index] = 50;
        return 0;
    }
}
