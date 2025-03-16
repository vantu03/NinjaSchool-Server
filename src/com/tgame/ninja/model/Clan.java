package com.tgame.ninja.model;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.real.DunClan;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.real.ThanThu;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.util.Comparator;
import java.util.Vector;

public class Clan {

    public static final int CREATE_CLAN = 0;

    public static final int MOVE_OUT_MEM = 1;

    public static final int MOVE_IN_MONEY = 2;

    public static final int MOVE_OUT_MONEY = 3;

    public static final int FREE_MONEY = 4;

    public static final int UP_LEVEL = 5;

    public static final int TYPE_NORMAL = 0;

    public static final int TYPE_UUTU = 1;

    public static final int TYPE_TRUONGLAO = 2;

    public static final int TYPE_TOCPHO = 3;

    public static final int TYPE_TOCTRUONG = 4;

    public static int[] exps = {
        0, 2000, 3720, 6510, 9920, 13950, 18600, 23870, 29760, 36270,
        43400, 54560, 66960, 80600, 95480, 111600, 128960, 147560, 167400, 188480,
        210800, 240870, 272800, 306590, 342240, 379750, 419120, 460350, 503440, 548390,
        595200, 650000, 650000, 650000, 650000, 650000, 700000, 700000, 700000, 700000,
        700000, 800000, 800000, 800000, 800000, 800000, 900000, 900000, 900000, 900000,
        900000, 1500000, 2000000, 2500000, 3000000, 3500000, 4000000, 4500000, 5000000, 5500000
    };

    public static int[] coins = {
        0, 500000, 600000, 700000, 800000, 900000, 1000000, 1100000, 1200000, 1300000,
        1400000, 1600000, 1800000, 2000000, 2200000, 2400000, 2600000, 2800000, 3000000, 3200000,
        3400000, 3700000, 4000000, 4300000, 4600000, 4900000, 5200000, 5500000, 5800000, 6100000,
        6400000, 7000000, 7000000, 7000000, 7000000, 7000000, 8000000, 8200000, 8400000, 8600000,
        8800000, 9000000, 9200000, 9400000, 9600000, 9800000, 10000000, 11000000, 12000000, 13000000,
        14000000, 20000000, 25000000, 30000000, 35000000, 40000000, 45000000, 50000000, 55000000, 60000000
    };

    public static int[] freeClan = {
        0, 35000, 38500, 42000, 45500, 49000, 52500, 56000, 59500, 63000,
        66500, 70000, 73500, 77000, 80500, 84000, 87500, 91000, 94500, 98000,
        101500, 105000, 108500, 112000, 115500, 119000, 122500, 126000, 129500, 133000,
        136500, 140000, 143500, 147000, 150500, 154000, 158000, 163000, 169000, 176000,
        186000, 197000, 209000, 222000, 236000, 251000, 267000, 284000, 302000, 321000,
        341000, 350000, 400000, 450000, 500000, 550000, 600000, 650000, 700000, 750000
    };

    public Vector<Item> items;

    public String name;

    public int exp;

    public int icon;

    public int coin;

    public int use_card;

    public int level;

    public int itemlevel;

    public long reg_date;

    public String main_name;

    public String assist_name;

    public String elder1_name;

    public String elder2_name;

    public String elder3_name;

    public String elder4_name;

    public String elder5_name;

    public String log;

    public String alert;

    public int countMoveOut;

    public Vector<Member> members;

    public Vector<String> inviteNames;

    public Vector<ThanThu> allThanThu;

    public int openDun;

    public byte stepDoor;

    public DunClan dunClan;

    public DunClan dunClan1;

    public DunClan dunClan2;

    public DunClan dunClan3;

    public DunClan dunClan4;

    public DunClan dunClan5;

    public DunClan dunClan6;

    public DunClan dunClan7;

    public DunClan dunClan8;

    public DunClan dunClan9;

    public DunClan dunClan10;

    public static final Comparator<Clan> sort = (o1, o2) -> {
        if (o2.level == o1.level) {
            return o2.exp - o1.exp;
        }
        return o2.level - o1.level;
    };

    public Clan() {
        items = new Vector<>();
        level = 1;
        itemlevel = 0;
        main_name = "";
        assist_name = "";
        elder1_name = "";
        elder2_name = "";
        elder3_name = "";
        elder4_name = "";
        elder5_name = "";
        log = "";
        alert = "";
        countMoveOut = 5;
        members = new Vector<>();
        inviteNames = new Vector<>();
        allThanThu = new Vector<>();
    }

    public void addMember(Member mem) {
        try {
            for (Member member : members) {
                if (member.name.equals(mem.name)) {
                    return;
                }
            }
            members.add(mem);
        } catch (Exception ex) {
        }
    }

    public void addMembers(Vector<Member> mems) {
        for (Member mem : mems) {
            addMember(mem);
        }
    }

    public void clearDun() {
        dunClan = null;
        dunClan1 = null;
        dunClan2 = null;
        dunClan3 = null;
        dunClan4 = null;
        dunClan5 = null;
        dunClan6 = null;
        dunClan7 = null;
        dunClan8 = null;
        dunClan9 = null;
        dunClan10 = null;
    }

    public void closeDun() {
        final int time = 60;
        dunClan.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan1.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan2.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan3.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan4.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan5.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan6.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan7.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan8.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan9.timeEnd = System.currentTimeMillis() + time * 1000;
        dunClan10.timeEnd = System.currentTimeMillis() + time * 1000;
        for (int i = 0; i < dunClan.players.size(); ++i) {
            Player pInDunClan = dunClan.players.get(i);
            pInDunClan.sendMapTime(time);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan1.players.size(); ++i) {
            Player pInDunClan = dunClan1.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan2.players.size(); ++i) {
            Player pInDunClan = dunClan2.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan3.players.size(); ++i) {
            Player pInDunClan = dunClan3.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan4.players.size(); ++i) {
            Player pInDunClan = dunClan4.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan5.players.size(); ++i) {
            Player pInDunClan = dunClan5.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan6.players.size(); ++i) {
            Player pInDunClan = dunClan6.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan7.players.size(); ++i) {
            Player pInDunClan = dunClan7.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan8.players.size(); ++i) {
            Player pInDunClan = dunClan8.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan9.players.size(); ++i) {
            Player pInDunClan = dunClan9.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan10.players.size(); ++i) {
            Player pInDunClan = dunClan10.players.get(i);
            pInDunClan.pointUyDanh += 5;
            NJUtil.sendServer(pInDunClan.getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(pInDunClan.getSession()), "5"));
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.ALERT_DUN_CLAN3(pInDunClan.getSession()));
        }
    }

    public void createDun(int levelDun, int time) {
        dunClan1 = DunClan.createDun(81, levelDun);
        dunClan2 = DunClan.createDun(82, levelDun);
        dunClan3 = DunClan.createDun(83, levelDun);
        dunClan4 = DunClan.createDun(84, levelDun);
        dunClan5 = DunClan.createDun(85, levelDun);
        dunClan6 = DunClan.createDun(86, levelDun);
        dunClan7 = DunClan.createDun(87, levelDun);
        dunClan8 = DunClan.createDun(88, levelDun);
        dunClan9 = DunClan.createDun(89, levelDun);
        dunClan10 = DunClan.createDun(90, levelDun);
        dunClan.timeEnd = System.currentTimeMillis() + time;
        dunClan1.timeEnd = System.currentTimeMillis() + time;
        dunClan2.timeEnd = System.currentTimeMillis() + time;
        dunClan3.timeEnd = System.currentTimeMillis() + time;
        dunClan4.timeEnd = System.currentTimeMillis() + time;
        dunClan5.timeEnd = System.currentTimeMillis() + time;
        dunClan6.timeEnd = System.currentTimeMillis() + time;
        dunClan7.timeEnd = System.currentTimeMillis() + time;
        dunClan8.timeEnd = System.currentTimeMillis() + time;
        dunClan9.timeEnd = System.currentTimeMillis() + time;
        dunClan10.timeEnd = System.currentTimeMillis() + time;
    }

    public int getType(String name) {
        if (main_name.equals(name)) {
            return TYPE_TOCTRUONG;
        }
        if (assist_name.equals(name)) {
            return TYPE_TOCPHO;
        }
        if (elder1_name.equals(name) || elder2_name.equals(name) || elder3_name.equals(name) || elder4_name.equals(name) || elder5_name.equals(name)) {
            return TYPE_TRUONGLAO;
        }
        return TYPE_NORMAL;
    }

    public boolean isGoDun(String name) {
        if (isInvite(name)) {
            return true;
        }
        for (Member mem : members) {
            if (mem.name.equals(name) && mem.typeClan == 1) {
                return true;
            }
        }
        return inviteNames.contains(name);
    }

    public boolean isMain(String name) {
        return main_name.equals(name);
    }

    public boolean isAssist(String name) {
        return assist_name.equals(name);
    }

    public boolean isInvite(String name) {
        return main_name.equals(name) || assist_name.equals(name) || (elder1_name.equals(name) || elder2_name.equals(name) || elder3_name.equals(name) || elder4_name.equals(name) || elder5_name.equals(name));
    }

    public boolean isFull() {
        return members.size() >= getMax();
    }

    public int getMax() {
        return level * 5 + 45;
    }

    public void sort() {
        members.sort(Member.sort);
    }

    public Member getMember(String memName) {
        try {
            for (Member mem : members) {
                if (mem.name.equals(memName)) {
                    return mem;
                }
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public void sendAlert(String[] strs, String me) {
        try {
            for (Member member : members) {
                Player playerMap = ServerController.hnPlayers.get(member.name);
                if (playerMap != null && playerMap.getSession() != null && (me == null || !me.equals(playerMap.name))) {
                    playerMap.sendServerMessage(strs);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void reset() {
        String newlog = "";
        String[] logs = log.split("\n");
        for (int max = Math.min(logs.length, 80), i = 0; i < max; ++i) {
            StringBuilder sb = new StringBuilder(newlog);
            String string = logs[i] + "\n";
            logs[i] = string;
            newlog = sb.append(string).toString();
        }
        log = newlog;
    }

    public void writeLog(String name, int type, int money) {
        log = name + "," + type + "," + money + "," + NJUtil.getDateTime() + "\n" + log;
    }

    public void sendAddExp(int expAdd) {
        for (int i = 0; i < dunClan.players.size(); ++i) {
            Player pInDunClan = dunClan.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan1.players.size(); ++i) {
            Player pInDunClan = dunClan1.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan2.players.size(); ++i) {
            Player pInDunClan = dunClan2.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan3.players.size(); ++i) {
            Player pInDunClan = dunClan3.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan4.players.size(); ++i) {
            Player pInDunClan = dunClan4.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan5.players.size(); ++i) {
            Player pInDunClan = dunClan5.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan6.players.size(); ++i) {
            Player pInDunClan = dunClan6.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan7.players.size(); ++i) {
            Player pInDunClan = dunClan7.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan8.players.size(); ++i) {
            Player pInDunClan = dunClan8.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan9.players.size(); ++i) {
            Player pInDunClan = dunClan9.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
        for (int i = 0; i < dunClan10.players.size(); ++i) {
            Player pInDunClan = dunClan10.players.get(i);
            if (!pInDunClan.name.equals(name)) {
                int exp = (pInDunClan.buffExp > 0) ? (expAdd + expAdd * pInDunClan.buffExp / 100) : expAdd;
                pInDunClan.sendUpdateExp(exp, true);
            }
        }
    }

    public void sendAddItem(Item item) {
        for (int i = 0; i < dunClan.players.size(); ++i) {
            Player pInDunClan = dunClan.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan1.players.size(); ++i) {
            Player pInDunClan = dunClan1.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan2.players.size(); ++i) {
            Player pInDunClan = dunClan2.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan3.players.size(); ++i) {
            Player pInDunClan = dunClan3.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan4.players.size(); ++i) {
            Player pInDunClan = dunClan4.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan5.players.size(); ++i) {
            Player pInDunClan = dunClan5.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan6.players.size(); ++i) {
            Player pInDunClan = dunClan6.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan7.players.size(); ++i) {
            Player pInDunClan = dunClan7.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan8.players.size(); ++i) {
            Player pInDunClan = dunClan8.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan9.players.size(); ++i) {
            Player pInDunClan = dunClan9.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
        for (int i = 0; i < dunClan10.players.size(); ++i) {
            Player pInDunClan = dunClan10.players.get(i);
            pInDunClan.addItemToBagNoDialog(item);
            NJUtil.sendServer(pInDunClan.getSession(), AlertFunction.YOU_GET(pInDunClan.getSession()) + " " + item.quantity + " " + item.template.getName(pInDunClan.getSession()));
        }
    }

    public void sendAlertDun(String[] strs, String name) {
        try {
            for (int i = 0; i < dunClan.names.size(); ++i) {
                Player pInDunClan = ServerController.hnPlayers.get(dunClan.names.get(i));
                if (pInDunClan != null && pInDunClan.map.isDunClan && !pInDunClan.name.equals(name)) {
                    pInDunClan.sendServerMessage(strs);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void sendChat(Message message) {
        for (Member member : members) {
            try {
                Player player = ServerController.hnPlayers.get(member.name);
                if (player != null && player.getSession() != null) {
                    player.getSession().sendMessage(message);
                }
            } catch (Exception ex) {
            }
        }
        if (message != null) {
            message.cleanup();
        }
    }

    public void setItemInfo(String itemInfo) {
        if (itemInfo == null || itemInfo.trim().equals("")) {
            return;
        }
        itemInfo = itemInfo.trim();
        items.clear();
        String[] infos = (itemInfo + " ").split("@");
        for (int i = 0; i < infos.length; ++i) {
            if (!infos[i].trim().equals("")) {
                Item it = Item.parseItem(infos[i], -1);
                if (it != null) {
                    it.indexUI = i;
                    items.add(it);
                }
            }
        }
    }

    public void setThanThuInfo(String info) {
        if (info == null || info.equals("")) {
            return;
        }
        String[] data = info.split("\\|");
        allThanThu.clear();
        for (String datum : data) {
            try {
                if (!datum.trim().equals("")) {
                    ThanThu tt = new ThanThu();
                    tt.initInfo(datum.trim());
                    allThanThu.add(tt);
                }
            } catch (Exception ex) {
            }
        }
    }

    public String getItemInfo() {
        StringBuilder str = new StringBuilder(" ");
        for (Item item : items) {
            str.append(item.getAllInfo()).append("@");
        }
        return str.toString();
    }

    public boolean addItem(Item item) {
        if (item.template.itemTemplateId == 596 || item.template.itemTemplateId == 601) {
            for (ThanThu thanThu : allThanThu) {
                if (thanThu.template.itemTemplateId == item.template.itemTemplateId) {
                    return false;
                }
            }
            ThanThu newItem = new ThanThu(item.template.itemTemplateId);
            newItem.timeAp = (int) NJUtil.getMilisByDay(3);
            newItem.playerId = item.playerId;
            newItem.typeUI = item.typeUI;
            newItem.indexUI = item.indexUI;
            newItem.quantity = item.quantity;
            newItem.expires = item.expires;
            newItem.isLock = item.isLock;
            newItem.sys = item.sys;
            newItem.upgrade = item.upgrade;
            newItem.buyCoin = item.buyCoin;
            newItem.buyCoinLock = item.buyCoinLock;
            newItem.buyGold = item.buyGold;
            newItem.buyGoldLock = item.buyGoldLock;
            newItem.saleCoinLock = item.saleCoinLock;
            if (item.options != null) {
                newItem.options = new Vector<>();
                for (int j = 0; j < item.options.size(); ++j) {
                    ItemOption option = new ItemOption();
                    option.optionTemplate = item.options.get(j).optionTemplate;
                    option.param = item.options.get(j).param;
                    newItem.options.add(option);
                }
            }
            allThanThu.add(newItem);
            Database.updateThanThuClan(this);
            return true;
        }
        item.typeUI = 39;
        item.isLock = true;
        for (Item value : items) {
            if (value.template.itemTemplateId == item.template.itemTemplateId) {
                value.quantity += item.quantity;
                Database.saveClan(this);
                return true;
            }
        }
        items.add(item);
        Database.saveClan(this);
        return true;
    }

    public boolean removeItem(int indexUI) {
        if (items.get(indexUI).quantity > 1) {
            Item item = items.get(indexUI);
            --item.quantity;
        } else {
            items.remove(indexUI);
        }
        Database.saveClan(this);
        return true;
    }

    public String getInfoSaveThanThu() {
        StringBuilder st = new StringBuilder();
        for (ThanThu thanThu : allThanThu) {
            st.append(thanThu.getInfoSave()).append("|");
        }
        return st.toString();
    }

    public int doEat(Item item, int index, Session s) {
        return allThanThu.get(index).doEat(item, s);
    }

    public int doEat(Item item, Session s) {
        return allThanThu.get(0).doEat(item, s);
    }

    public int getUpgradeExp() {
        if (level >= exps.length) {
            return exps[exps.length - 1] * level / 10;
        }
        return exps[level];
    }

    public int getUpgradeCoin() {
        if (level >= coins.length) {
            return coins[coins.length - 1] * level / 10;
        }
        return coins[level];
    }

    public int getFee() {
        if (level >= freeClan.length) {
            return freeClan[coins.length - 1] * level / 10;
        }
        return freeClan[level];
    }

    public static void resetLog() {
        for (int i = 0; i < ServerController.clans.size(); ++i) {
            try {
                ServerController.clans.get(i).countMoveOut = 5;
                ServerController.clans.get(i).reset();
            } catch (Exception ex) {
            }
        }
    }
}
