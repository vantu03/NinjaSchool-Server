package com.tgame.ninja.real;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.Cmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.NJUtil;
import java.util.Hashtable;
import java.util.Vector;

public class PlayerArenaInfo {

    private  static final Logger LOGGER = LoggerFactory.getLogger(PlayerArenaInfo.class);

    public static Hashtable<String, PlayerArenaInfo> ALL_PLAYER_TOP_THACH_DAU = new Hashtable<>();

    public static Vector<PlayerArenaInfo> ALL_PLAYER_ARENA_THIEN = new Vector<>();

    public static Vector<PlayerArenaInfo> ALL_PLAYER_ARENA_DIA = new Vector<>();

    public static String thachdau = "có thể thách đấu";

    public String name;

    public int userid;

    public int rank;

    public short level;

    public boolean isPlaying;

    public byte isGroup;

    public byte timesPlay;

    public byte coutwin;

    public long timeFight;

    public long timeWin;

    public String nameThachdau;

    public PlayerArenaInfo() {
        name = "";
        userid = 0;
        rank = 0;
        level = 1;
        isPlaying = false;
        isGroup = 0;
        timesPlay = 1;
        coutwin = 0;
        timeFight = System.currentTimeMillis();
        timeWin = -1L;
        nameThachdau = thachdau;
    }

    public static void addPlayer(PlayerArenaInfo p) {
        if (p.level < 80) {
            p.rank = ALL_PLAYER_ARENA_DIA.size();
            ALL_PLAYER_ARENA_DIA.add(p);
            p.isGroup = 0;
        } else {
            p.rank = ALL_PLAYER_ARENA_THIEN.size();
            ALL_PLAYER_ARENA_THIEN.add(p);
            p.isGroup = 1;
        }
        ALL_PLAYER_TOP_THACH_DAU.put(p.name, p);
        Database.createPlayerArena(p);
    }

    public static synchronized void createMsgList(Player p) {
        try {
            PlayerArenaInfo parena = ALL_PLAYER_TOP_THACH_DAU.get(p.name.toLowerCase());
            if (parena == null) {
                return;
            }
            Message message = new Message(Cmd.LIST_TOP_ARENA);
            int size = 10;
            Vector<PlayerArenaInfo> v = getListThidau(parena);
            if (size > v.size()) {
                size = v.size();
            }
            message.writeByte(size);
            for (int i = 0; i < size; ++i) {
                PlayerArenaInfo pp = v.get(i);
                message.writeUTF(pp.name);
                message.writeInt(pp.rank + 1);
                if (!p.name.equals(pp.name)) {
                    message.writeUTF(pp.nameThachdau);
                } else {
                    message.writeUTF("Không thể thách đấu");
                }
            }
            p.getSession().sendMessage(message);
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void doSendDiabang(Player p) {
        StringBuilder str = new StringBuilder();
        for (int size = Math.min(ALL_PLAYER_ARENA_DIA.size(), 10), i = 0; i < size; ++i) {
            str.append(i + 1).append(". ").append(ALL_PLAYER_ARENA_DIA.get(i).name).append("\n");
        }
        NJUtil.sendAlertDialogInfo(p.getSession(), "Địa bảng", String.valueOf(str));
    }

    public static void doSendThienbang(Player p) {
        StringBuilder str = new StringBuilder();
        for (int size = Math.min(ALL_PLAYER_ARENA_THIEN.size(), 10), i = 0; i < size; ++i) {
            str.append(i + 1).append(". ").append(ALL_PLAYER_ARENA_THIEN.get(i).name).append("\n");
        }
        NJUtil.sendAlertDialogInfo(p.getSession(), "Thiên bảng", String.valueOf(str));
    }

    public static Vector<PlayerArenaInfo> getListThidau(PlayerArenaInfo p) {
        Vector<PlayerArenaInfo> v = new Vector<>();
        int rank = p.rank;
        if (rank > 1000) {
            rank = 1000;
        }
        int rankstart = rank - 10;
        if (rankstart < 0) {
            rankstart = 0;
        }
        int rankend = rankstart + 10;
        if (p.isGroup == 0) {
            if (rankend > ALL_PLAYER_ARENA_DIA.size()) {
                rankend = ALL_PLAYER_ARENA_DIA.size();
            }
            for (int i = rankstart; i < rankend; ++i) {
                PlayerArenaInfo pp = ALL_PLAYER_ARENA_DIA.get(i);
                if (System.currentTimeMillis() - pp.timeFight >= 0L) {
                    pp.isPlaying = false;
                    pp.nameThachdau = thachdau;
                }
                v.add(pp);
            }
        } else {
            if (rankend > ALL_PLAYER_ARENA_THIEN.size()) {
                rankend = ALL_PLAYER_ARENA_THIEN.size();
            }
            for (int i = rankstart; i < rankend; ++i) {
                PlayerArenaInfo pp = ALL_PLAYER_ARENA_THIEN.get(i);
                if (System.currentTimeMillis() - pp.timeFight >= 0L) {
                    pp.isPlaying = false;
                    pp.nameThachdau = thachdau;
                }
                v.add(pp);
            }
        }
        return v;
    }
}
