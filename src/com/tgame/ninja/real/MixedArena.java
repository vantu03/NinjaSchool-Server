package com.tgame.ninja.real;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

public class MixedArena {

    private static final Logger LOGGER = LoggerFactory.getLogger(MixedArena.class);

    public static int winningSchool = 0;

    public static final int MAP_WAIT_HIROSAKI = 154;

    public static final int MAP_WAIT_OOKAZA = 156;

    public static final int MAP_WAIT_HARUNA = 155;

    public static final int MAP_FIGHT_HIROSAKI = 150;

    public static final int MAP_FIGHT_HARUNA = 152;

    public static final int MAP_FIGHT_OOKAZA = 151;

    public static final short BOSS_ID_1 = 200;

    public static final short BOSS_ID_2 = 199;

    public static final short BOSS_ID_3 = 198;

    public static boolean isTest = false;

    public static boolean isTimeTesting = false;

    public static boolean isRunning = false;

    public static MixedArena instance;

    public static int point1 = 0;

    public static int point2 = 0;

    public static int point3 = 0;

    public static int lastPoint1 = 0;

    public static int lastPoint2 = 0;

    public static int lastPoint3 = 0;

    public static boolean bBossOut1 = false;

    public static boolean bBossOut2 = false;

    public static boolean bBossOut3 = false;

    public static HashMap<Integer, Vector<Map>> ALL_MAPS = new HashMap<>();

    public static boolean isWaitingTime = false;

    public static boolean isFightingTime = false;

    public static Player[] lastTopPlayers = null;

    public static HashMap<Integer, Player> players1 = new HashMap<>();

    public static HashMap<Integer, Player> players2 = new HashMap<>();

    public static HashMap<Integer, Player> players3 = new HashMap<>();

    static {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            loadWinningSchool();
        } else {
            winningSchool = 0;
        }
    }

    public static void extendArenaMaps() {
        int maxPlayer = players1.size();
        if (players2.size() > maxPlayer) {
            maxPlayer = players2.size();
        }
        if (players3.size() > maxPlayer) {
            maxPlayer = players3.size();
        }
        for (int addCount = maxPlayer / 10 + 1, i = ALL_MAPS.get(MAP_FIGHT_HIROSAKI).size(); i < addCount; ++i) {
            DunMixedArena dMap = new DunMixedArena(ServerController.maps.size(), i, ServerController.mapTemplates.get(MAP_FIGHT_HIROSAKI));
            dMap.typePk = Player.PK_PHE1;
            ServerController.maps.addElement(dMap);
            ALL_MAPS.get(MAP_FIGHT_HIROSAKI).addElement(dMap);
            dMap = new DunMixedArena(ServerController.maps.size(), i, ServerController.mapTemplates.get(MAP_FIGHT_HARUNA));
            dMap.typePk = Player.PK_PHE2;
            ServerController.maps.addElement(dMap);
            ALL_MAPS.get(MAP_FIGHT_HARUNA).addElement(dMap);
            dMap = new DunMixedArena(ServerController.maps.size(), i, ServerController.mapTemplates.get(MAP_FIGHT_OOKAZA));
            dMap.typePk = Player.PK_PHE3;
            ServerController.maps.addElement(dMap);
            ALL_MAPS.get(MAP_FIGHT_OOKAZA).addElement(dMap);
            Map map = new Map(ServerController.maps.size(), i, ServerController.mapTemplates.get(153));
            ServerController.maps.addElement(map);
            ALL_MAPS.get(153).addElement(map);
        }
    }

    public static void initWaitMaps() {
        if (!isRunning) {
            ALL_MAPS.put(MAP_WAIT_HIROSAKI, new Vector<>());
            ALL_MAPS.put(MAP_WAIT_OOKAZA, new Vector<>());
            ALL_MAPS.put(MAP_WAIT_HARUNA, new Vector<>());
            ALL_MAPS.put(MAP_FIGHT_HIROSAKI, new Vector<>());
            ALL_MAPS.put(MAP_FIGHT_HARUNA, new Vector<>());
            ALL_MAPS.put(MAP_FIGHT_OOKAZA, new Vector<>());
            ALL_MAPS.put(153, new Vector<>());
            point1 = 0;
            point2 = 0;
            point3 = 0;
            bBossOut1 = false;
            bBossOut2 = false;
            bBossOut3 = false;
            isRunning = true;
            Database.initMixedArena(NJUtil.getWeekString());
        }
    }

    public static void extendWaitMaps() {
        int maxPlayer = players1.size();
        if (players2.size() > maxPlayer) {
            maxPlayer = players2.size();
        }
        if (players3.size() > maxPlayer) {
            maxPlayer = players3.size();
        }
        int addCount = ++maxPlayer / 20 + 1;
        Map map;
        for (int i = ALL_MAPS.get(MAP_WAIT_HIROSAKI).size(); i < addCount; ++i) {
            map = new Map(ServerController.maps.size(), ALL_MAPS.get(MAP_WAIT_HIROSAKI).size(), ServerController.mapTemplates.get(MAP_WAIT_HIROSAKI));
            ALL_MAPS.get(MAP_WAIT_HIROSAKI).addElement(map);
            ServerController.maps.addElement(map);
        }
        for (int i = ALL_MAPS.get(MAP_WAIT_OOKAZA).size(); i < addCount; ++i) {
            map = new Map(ServerController.maps.size(), ALL_MAPS.get(MAP_WAIT_OOKAZA).size(), ServerController.mapTemplates.get(MAP_WAIT_OOKAZA));
            ALL_MAPS.get(MAP_WAIT_OOKAZA).addElement(map);
            ServerController.maps.addElement(map);
        }
        for (int i = ALL_MAPS.get(MAP_WAIT_HARUNA).size(); i < addCount; ++i) {
            map = new Map(ServerController.maps.size(), ALL_MAPS.get(MAP_WAIT_HARUNA).size(), ServerController.mapTemplates.get(MAP_WAIT_HARUNA));
            ALL_MAPS.get(MAP_WAIT_HARUNA).addElement(map);
            ServerController.maps.addElement(map);
        }
    }

    public static void closingArena() {
        if (isRunning) {
            for (java.util.Map.Entry<Integer, Vector<Map>> entry : ALL_MAPS.entrySet()) {
                for (Map map : entry.getValue()) {
                    map.isStop = true;
                    ServerController.maps.removeElement(map);
                }
            }
            LocalDateTime dateTimeNow = LocalDateTime.now();
            int hour = dateTimeNow.getHour();
            DayOfWeek dayOfWeek = dateTimeNow.getDayOfWeek();
            int day = dateTimeNow.getDayOfMonth();
            int mon = dateTimeNow.getMonthValue();
            int year = dateTimeNow.getYear();
            Vector<Player> finalList = new Vector<>();
            Player[] topPlayers = new Player[10];
            /*for (int i = 0; i < topPlayers.length; ++i) {
                topPlayers[i] = null;
            }*/
            for (java.util.Map.Entry<Integer, Player> entry2 : players1.entrySet()) {
                Player p = entry2.getValue();
                finalList.addElement(p);
            }
            for (java.util.Map.Entry<Integer, Player> entry2 : players2.entrySet()) {
                Player p = entry2.getValue();
                finalList.addElement(p);
            }
            for (java.util.Map.Entry<Integer, Player> entry2 : players3.entrySet()) {
                Player p = entry2.getValue();
                finalList.addElement(p);
            }
            for (Player p2 : finalList) {
                for (int j = 0; j < topPlayers.length; ++j) {
                    if (topPlayers[j] == null) {
                        topPlayers[j] = p2;
                        break;
                    }
                    if (p2.pointCT >= topPlayers[j].pointCT) {
                        shiftRightArray(topPlayers, j);
                        topPlayers[j] = p2;
                        break;
                    }
                }
            }
            for (int i = 0; i < topPlayers.length && topPlayers[i] != null; ++i) {
                if (i == 0) {
                    Player player;
                    Player top1 = player = topPlayers[i];
                    player.point3Arena += 10;
                    top1.savezaLog("3Arena", String.format("top 1 3 arena, time: %d-%d-%d %d H", day, mon, year, hour));
                } else if (i <= 3) {
                    Player player2;
                    Player top2_4 = player2 = topPlayers[i];
                    player2.point3Arena += 5;
                    top2_4.savezaLog("3Arena", String.format("top 2-4 3 arena, time: %d-%d-%d %d H", day, mon, year, hour));
                } else {
                    Player player3;
                    Player top5_10 = player3 = topPlayers[i];
                    player3.point3Arena += 2;
                    top5_10.savezaLog("3Arena", String.format("top 5-10 3 arena, time: %d-%d-%d %d H", day, mon, year, hour));
                }
            }
            lastTopPlayers = topPlayers;
            /*int schoolWin = 1;
            int max = MixedArena.point1;
            if (MixedArena.point2 > max) {
                max = MixedArena.point2;
                schoolWin = 2;
            }
            if (MixedArena.point3 > max) {
                max = MixedArena.point3;
                schoolWin = 3;
            }
            switch (schoolWin) {
                case 1:
            }*/
            finalList.clear();
            lastPoint1 = point1;
            lastPoint2 = point2;
            lastPoint3 = point3;
            players1.clear();
            players2.clear();
            players3.clear();
            ALL_MAPS.clear();
            updatePointLog();
            if (dayOfWeek == DayOfWeek.FRIDAY && hour > 20) {
                updateWinningSchool();
            } else if (dayOfWeek == DayOfWeek.SATURDAY && hour < 11) {
                winningSchool = 0;
            }
            isRunning = false;
        }
    }

    public static boolean isMapArena(int mapTemplateId) {
        return mapTemplateId >= MAP_FIGHT_HIROSAKI && mapTemplateId <= MAP_WAIT_OOKAZA;
    }

    public static void addPoint(Player player, int add) {
        player.sendChientruong(player.pointCT += add);
        if (player.typePk == Player.PK_PHE1) {
            point1 += add;
        } else if (player.typePk == Player.PK_PHE2) {
            point2 += add;
        } else {
            point3 += add;
        }
    }

    public static boolean backToBase(Player p) {
        p.status = Player.ME_NORMAL;
        p.hp = p.hp_full;
        p.mp = p.mp_full;
        Map mapNext = null;
        if (p.isTieu() || p.isKiem()) {
            for (Map map : ALL_MAPS.get(MAP_WAIT_HIROSAKI)) {
                if (map.players.size() < 20) {
                    mapNext = map;
                    break;
                }
            }
        } else if (p.isKunai() || p.isCung()) {
            for (Map map : ALL_MAPS.get(MAP_WAIT_OOKAZA)) {
                if (map.players.size() < 20) {
                    mapNext = map;
                    break;
                }
            }
        } else {
            for (Map map : ALL_MAPS.get(MAP_WAIT_HARUNA)) {
                if (map.players.size() < 20) {
                    mapNext = map;
                    break;
                }
            }
        }
        if (mapNext == null) {
            leave(p);
            return false;
        }
        p.mapClear();
        p.map.goOutMap(p);
        p.x = mapNext.template.defaultX;
        p.y = mapNext.template.defaultY;
        mapNext.waitGoInMap(p);
        return true;
    }

    public static int calcDamAttackMob(Player player, int dam) {
        if (player.map.template.mapTemplateId == MAP_FIGHT_HIROSAKI && player.typePk == Player.PK_PHE1) {
            return 0;
        }
        if (player.map.template.mapTemplateId == MAP_FIGHT_HARUNA && player.typePk == Player.PK_PHE2) {
            return 0;
        }
        if (player.map.template.mapTemplateId == MAP_FIGHT_OOKAZA && player.typePk == Player.PK_PHE3) {
            return 0;
        }
        return dam;
    }

    public static boolean checkChangeMap(Player player, int mapTemplateId) {
        Session conn = player.getSession();
        if (!isWaitingTime && !isFightingTime) {
            if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                NJUtil.sendServer(conn, "Chưa tới thời gian đăng ký chiến trường");
            } else {
                NJUtil.sendServer(conn, "The arena didn't register yet");
            }
            return false;
        }
        if (isWaitingTime && !isFightingTime) {
            if ((player.isTieu() || player.isKiem()) && mapTemplateId == MAP_WAIT_HIROSAKI) {
                return true;
            }
            if ((player.isKunai() || player.isCung()) && mapTemplateId == MAP_WAIT_OOKAZA) {
                return true;
            }
            if ((player.isDao() || player.isQuat()) && mapTemplateId == MAP_WAIT_HARUNA) {
                return true;
            }
            if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                NJUtil.sendServer(conn, "Chưa tới thời gian diễn ra chiến trường");
            } else {
                NJUtil.sendServer(conn, "The arena didn't open yet");
            }
            return false;
        } else {
            if (((mapTemplateId == MAP_WAIT_OOKAZA || mapTemplateId == MAP_WAIT_HARUNA) && player.getTypePk() == Player.PK_PHE1) ||
                ((mapTemplateId == MAP_WAIT_HIROSAKI || mapTemplateId == MAP_WAIT_HARUNA) && player.getTypePk() == Player.PK_PHE2) ||
                ((mapTemplateId == MAP_WAIT_HIROSAKI || mapTemplateId == MAP_WAIT_OOKAZA) && player.getTypePk() == Player.PK_PHE3)
            ) {
                if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(conn, "Lối đi này đã bị chặn");
                } else {
                    NJUtil.sendServer(conn, "The passage is blocked for now");
                }
                return false;
            }
            return true;
        }
    }

    public static Map doChangeMap(Player player, int mapToId, int mapFromId) {
        Map mapNext = null;
        if (mapToId == MAP_WAIT_HIROSAKI || mapToId == MAP_WAIT_HARUNA || mapToId == MAP_WAIT_OOKAZA) {
            if (player.isTieu() || player.isKiem()) {
                for (Map map : ALL_MAPS.get(MAP_WAIT_HIROSAKI)) {
                    if (map.players.size() < 20) {
                        mapNext = map;
                        break;
                    }
                }
            } else if (player.isKunai() || player.isCung()) {
                for (Map map : ALL_MAPS.get(MAP_WAIT_OOKAZA)) {
                    if (map.players.size() < 20) {
                        mapNext = map;
                        break;
                    }
                }
            } else {
                for (Map map : ALL_MAPS.get(MAP_WAIT_HARUNA)) {
                    if (map.players.size() < 20) {
                        mapNext = map;
                        break;
                    }
                }
            }
        } else {
            extendArenaMaps();
            Vector<Map> maps = ALL_MAPS.get(mapToId);
            for (Map map : maps) {
                if (player.isTieu() || player.isKiem()) {
                    int count = 0;
                    for (Player pInMap : map.players) {
                        if (pInMap.isTieu() || pInMap.isKiem()) {
                            ++count;
                        }
                    }
                    if (count < 10) {
                        mapNext = map;
                        break;
                    }
                } else if (player.isKunai() || player.isCung()) {
                    int count = 0;
                    for (Player pInMap : map.players) {
                        if (pInMap.isKunai() || pInMap.isCung()) {
                            ++count;
                        }
                    }
                    if (count < 10) {
                        mapNext = map;
                        break;
                    }
                } else {
                    int count = 0;
                    for (Player pInMap : map.players) {
                        if (pInMap.isDao() || pInMap.isQuat()) {
                            ++count;
                        }
                    }
                    if (count < 10) {
                        mapNext = map;
                        break;
                    }
                }
            }
            if (mapNext == null) {
                return null;
            }
            if ((mapFromId == MAP_WAIT_HIROSAKI || mapFromId == MAP_WAIT_OOKAZA || mapFromId == MAP_WAIT_HARUNA) &&
                (mapToId == MAP_FIGHT_HIROSAKI || mapToId == MAP_FIGHT_HARUNA || mapToId == MAP_FIGHT_OOKAZA)
            ) {
                ((DunMixedArena) mapNext).setInvincible(player);
            }
        }
        return mapNext;
    }

    public static void getGuide(Player p) {
        Session conn = p.getSession();
        if (conn.typeLanguage == GameServer.LANG_VI) {
            NJUtil.sendAlertDialogInfo(conn, "Hướng dẫn", "Thời gian:\n+ 11h45 - 13h. Dành cho cấp độ dưới 90.\n+ 14h45 - 16h. Không giới hạn cấp độ.\n+ 20h45 - 22h. Dành cho cấp độ lớn hơn 90\n\nPhần thưởng:\n- Top 1 điểm tích lũy sẽ nhận được 10 Điểm thi đua\n- Top 2 đến 4 nhận được 5 Điểm thi đua\n- Top 5 đến 10 nhận được 2 Điểm thi đua");
        } else {
            NJUtil.sendAlertDialogInfo(conn, "Guide", "Schedule:\n+ 11h45 - 13h. Level below 90 permitted.\n+ 14h45 - 16h. All level permitted.\n+ 20h45 - 22h. Level above 90 permitted\n\nRewards:\n- Top 1 will receive 10 Hero point\n- Top 2 - 4 receive 5 Hero point\n- Top 5 - 10 receive 2 Hero point");
        }
    }

    public static void getSummary(Player p) {
        Session conn = p.getSession();
        StringBuilder summary = new StringBuilder();
        int[] schools = new int[3];
        Arrays.fill(schools, 1);
        int max = lastPoint1;
        int max2;
        if (lastPoint2 > max) {
            schools[0] = 2;
            max = lastPoint2;
            max2 = lastPoint1;
        } else {
            schools[1] = 2;
            max2 = lastPoint2;
        }
        if (lastPoint3 > max) {
            schools[2] = schools[1];
            schools[1] = schools[0];
            schools[0] = 3;
        } else if (lastPoint3 > max2) {
            schools[2] = schools[1];
            schools[1] = 3;
        } else {
            schools[2] = 3;
        }
        if (conn.typeLanguage == GameServer.LANG_VI) {
            summary.append("Thành tích trường:\n");
            for (int school : schools) {
                switch (school) {
                    case 1:
                        summary.append("- Hirosaki : ").append(lastPoint1).append(" điểm\n");
                        break;
                    case 2:
                        summary.append("- Ookaza : ").append(lastPoint2).append(" điểm\n");
                        break;
                    case 3:
                        summary.append("- Haruna : ").append(lastPoint3).append(" điểm\n");
                        break;
                }
            }
            summary.append("Thành tích top 10:\n");
            if (lastTopPlayers != null) {
                for (Player lastTopPlayer : lastTopPlayers) {
                    if (lastTopPlayer == null) {
                        break;
                    }
                    summary.append("- ").append(lastTopPlayer.name).append("\n");
                }
            } else {
                summary.append("Chưa có dữ liệu\n");
            }
            NJUtil.sendAlertDialogInfo(conn, "Thành tích", summary.toString());
        } else {
            summary.append("Billboard:\n");
            for (int school : schools) {
                switch (school) {
                    case 1:
                        summary.append("- Hirosaki : ").append(lastPoint1).append(" points\n");
                        break;
                    case 2:
                        summary.append("- Ookaza : ").append(lastPoint2).append(" points\n");
                        break;
                    case 3:
                        summary.append("- Haruna : ").append(lastPoint3).append(" points\n");
                        break;
                }
            }
            summary.append("Top 10 heroes:\n");
            if (lastTopPlayers != null) {
                for (Player lastTopPlayer : lastTopPlayers) {
                    if (lastTopPlayer == null) {
                        break;
                    }
                    summary.append("- ").append(lastTopPlayer.name).append("\n");
                }
            } else {
                summary.append("No data\n");
            }
            NJUtil.sendAlertDialogInfo(conn, "Summary", summary.toString());
        }
    }

    public static void getSummaryPlayer(Player p) {
        Session conn = p.getSession();
        StringBuilder summary = new StringBuilder();
        int[] schools = new int[3];
        Arrays.fill(schools, 1);
        int max = lastPoint1;
        int max2;
        if (lastPoint2 > max) {
            schools[0] = 2;
            max = lastPoint2;
            max2 = lastPoint1;
        } else {
            schools[1] = 2;
            max2 = lastPoint2;
        }
        if (lastPoint3 > max) {
            schools[2] = schools[1];
            schools[1] = schools[0];
            schools[0] = 3;
        } else if (lastPoint3 > max2) {
            schools[2] = schools[1];
            schools[1] = 3;
        } else {
            schools[2] = 3;
        }
        if (conn.typeLanguage == GameServer.LANG_VI) {
            summary.append("Thành tích trường:\n");
            for (int school : schools) {
                switch (school) {
                    case 1:
                        summary.append("- Hirosaki : ").append(lastPoint1).append(" điểm\n");
                        break;
                    case 2:
                        summary.append("- Ookaza : ").append(lastPoint2).append(" điểm\n");
                        break;
                    case 3:
                        summary.append("- Haruna : ").append(lastPoint3).append(" điểm\n");
                        break;
                }
            }
            summary.append("\nĐiểm thi đua: ").append(p.point3Arena).append(" điểm\n");
            NJUtil.sendAlertDialogInfo(conn, "Thành tích", summary.toString());
        } else {
            summary.append("Billboard:\n");
            for (int school : schools) {
                switch (school) {
                    case 1:
                        summary.append("- Hirosaki : ").append(lastPoint1).append(" points\n");
                        break;
                    case 2:
                        summary.append("- Ookaza : ").append(lastPoint2).append(" points\n");
                        break;
                    case 3:
                        summary.append("- Haruna : ").append(lastPoint3).append(" points\n");
                        break;
                }
            }
            summary.append("\nHero point: ").append(p.point3Arena).append(" points\n");
            NJUtil.sendAlertDialogInfo(conn, "Summary", summary.toString());
        }
    }

    public static void handleMenu(Player player, int menuId) {
        if (menuId == 1) {
            if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                NJUtil.sendAlertDialogInfo(player.getSession(), "Tổng kết", String.format("Điểm tích lũy: %d\nĐiểm tổng kết\n- Hirosaki : %d\n- Ookaza : %d\n- Haruna : %d", player.pointCT, point1, point2, point3));
            } else {
                NJUtil.sendAlertDialogInfo(player.getSession(), "Summary", String.format("Your Emulation point: %d\nSchool Hero point:\n- Hirosaki : %d\n- Ookaza : %d\n- Haruna : %d", player.pointCT, point1, point2, point3));
            }
        } else {
            leave(player);
        }
    }

    public static boolean isWaitingHaruna(Player player, Map map) {
        return (player.isKunai() || player.isCung()) && map.template.mapTemplateId == MAP_WAIT_OOKAZA;
    }

    public static boolean isWaitingHirosaki(Player player, Map map) {
        return (player.isTieu() || player.isKiem()) && map.template.mapTemplateId == MAP_WAIT_HIROSAKI;
    }

    public static boolean isWaitingOokaza(Player player, Map map) {
        return (player.isDao() || player.isQuat()) && map.template.mapTemplateId == MAP_WAIT_HARUNA;
    }

    public static boolean isWaitingRoom(Map map) {
        return map != null && (map.template.mapTemplateId == MAP_WAIT_HIROSAKI || map.template.mapTemplateId == MAP_WAIT_OOKAZA || map.template.mapTemplateId == MAP_WAIT_HARUNA);
    }

    public static void join(Player p) {
        updateTimer();
        if (isWaitingTime) {
            if (p.classId == 0) {
                if (p.getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(p.getSession(), "Con hãy nhập học một trường rồi hãy gặp ta để báo danh nhé.");
                } else {
                    NJUtil.sendServer(p.getSession(), "You must join in 1 class to participate in this competition");
                }
                return;
            }
            initWaitMaps();
            if (players1.containsKey(p.playerId) || players2.containsKey(p.playerId) || players3.containsKey(p.playerId)) {
                if (p.getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(p.getSession(), "Con đã đăng ký đấu trường rồi");
                } else {
                    NJUtil.sendServer(p.getSession(), "You have already registered, now be patient");
                }
                return;
            }
            if (Math.abs(players1.size() - players2.size()) > 10 ||
                Math.abs(players2.size() - players3.size()) > 10 ||
                Math.abs(players3.size() - players1.size()) > 10
            ) {
                if (p.getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(p.getSession(), "Đấu trường quá đông. Con không thể đăng ký thêm");
                } else {
                    NJUtil.sendServer(p.getSession(), "The competition is too crowded. You cannot join at the moment");
                }
                return;
            }
            if (p.party != null) {
                p.party.out(p);
            }
            extendWaitMaps();
            if (p.isKiem() || p.isTieu()) {
                //noinspection SynchronizeOnNonFinalField
                synchronized (players1) {
                    Map mapNext = null;
                    for (Map map : ALL_MAPS.get(MAP_WAIT_HIROSAKI)) {
                        if (map.players.size() < 20) {
                            mapNext = map;
                            break;
                        }
                    }
                    if (mapNext != null) {
                        p.typePk = Player.PK_PHE1;
                        p.updateTypePk();
                        p.sendChientruong(p.pointCT = 0);
                        p.mapTemplateIdGo = p.map.getTemplateId();
                        p.mapClear();
                        p.map.goOutMap(p);
                        p.x = mapNext.template.defaultX;
                        p.y = mapNext.template.defaultY;
                        mapNext.waitGoInMap(p);
                        players1.put(p.playerId, p);
                    } else {
                        NJUtil.sendServer(p.getSession(), AlertFunction.ERROR(p.getSession()));
                    }
                    return;
                }
            }
            if (p.isKunai() || p.isCung()) {
                //noinspection SynchronizeOnNonFinalField
                synchronized (players2) {
                    Map mapNext = null;
                    for (Map map : ALL_MAPS.get(MAP_WAIT_OOKAZA)) {
                        if (map.players.size() < 20) {
                            mapNext = map;
                            break;
                        }
                    }
                    if (mapNext != null) {
                        p.typePk = Player.PK_PHE2;
                        p.updateTypePk();
                        p.sendChientruong(p.pointCT = 0);
                        p.mapTemplateIdGo = p.map.getTemplateId();
                        p.mapClear();
                        p.map.goOutMap(p);
                        p.x = mapNext.template.defaultX;
                        p.y = mapNext.template.defaultY;
                        mapNext.waitGoInMap(p);
                        players2.put(p.playerId, p);
                    } else {
                        NJUtil.sendServer(p.getSession(), AlertFunction.ERROR(p.getSession()));
                    }
                    return;
                }
            }
            if (!p.isDao() && !p.isQuat()) {
                return;
            }
            //noinspection SynchronizeOnNonFinalField
            synchronized (players3) {
                Map mapNext = null;
                for (Map map : ALL_MAPS.get(MAP_WAIT_HARUNA)) {
                    if (map.players.size() < 20) {
                        mapNext = map;
                        break;
                    }
                }
                if (mapNext != null) {
                    p.typePk = Player.PK_PHE3;
                    p.updateTypePk();
                    p.sendChientruong(p.pointCT = 0);
                    p.mapTemplateIdGo = p.map.getTemplateId();
                    p.mapClear();
                    p.map.goOutMap(p);
                    p.x = mapNext.template.defaultX;
                    p.y = mapNext.template.defaultY;
                    mapNext.waitGoInMap(p);
                    players3.put(p.playerId, p);
                } else {
                    NJUtil.sendServer(p.getSession(), AlertFunction.ERROR(p.getSession()));
                }
                return;
            }
        }
        if (isFightingTime) {
            NJUtil.sendServer(p.getSession(), "Đấu trường đang diễn ra, Con hãy quay lại sau nhé!");
        } else {
            NJUtil.sendServer(p.getSession(), "Chưa tới giờ báo danh, Con hãy quay lại sau nhé!");
        }
    }

    public static int leave(Player p) {
        if (players1.containsKey(p.playerId)) {
            players1.remove(p.playerId);
            p.clearRecentPK();
            p.status = Player.ME_NORMAL;
            int maxBack = 1;
            Map mapNext = Map.findMap(p, maxBack);
            if (mapNext == null) {
                return 22;
            }
            p.mapClear();
            p.map.goOutMap(p);
            p.x = mapNext.template.defaultX;
            p.y = mapNext.template.defaultY;
            mapNext.waitGoInMap(p);
            return maxBack;
        } else if (players2.containsKey(p.playerId)) {
            players2.remove(p.playerId);
            p.clearRecentPK();
            p.status = Player.ME_NORMAL;
            int maxBack = 72;
            Map mapNext = Map.findMap(p, maxBack);
            if (mapNext == null) {
                return 22;
            }
            p.mapClear();
            p.map.goOutMap(p);
            p.x = mapNext.template.defaultX;
            p.y = mapNext.template.defaultY;
            mapNext.waitGoInMap(p);
            return maxBack;
        } else {
            if (!players3.containsKey(p.playerId)) {
                return 22;
            }
            players3.remove(p.playerId);
            p.clearRecentPK();
            p.status = Player.ME_NORMAL;
            int maxBack = 27;
            Map mapNext = Map.findMap(p, maxBack);
            if (mapNext == null) {
                return 22;
            }
            p.mapClear();
            p.map.goOutMap(p);
            p.x = mapNext.template.defaultX;
            p.y = mapNext.template.defaultY;
            mapNext.waitGoInMap(p);
            return maxBack;
        }
    }

    public static void loadWinningSchool() {
        int pointSchool1 = 0;
        int pointSchool2 = 0;
        int pointSchool3 = 0;
        try {
            int[] point = Database.getMixedArena(NJUtil.getWeekString());
            pointSchool1 = point[0];
            pointSchool2 = point[1];
            pointSchool3 = point[2];
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        if (pointSchool1 > pointSchool2 && pointSchool1 > pointSchool3) {
            winningSchool = 1;
        } else if (pointSchool2 > pointSchool1 && pointSchool2 > pointSchool3) {
            winningSchool = 2;
        } else if (pointSchool3 > pointSchool1 && pointSchool3 > pointSchool2) {
            winningSchool = 3;
        } else {
            winningSchool = 0;
        }
    }

    public static void shiftRightArray(Player[] ps, int index) {
        if (index < 0) {
            return;
        }
        if (ps.length - 1 - index >= 0) {
            System.arraycopy(ps, index, ps, index + 1, ps.length - 1 - index);
        }
    }

    public static void updatePointKillMob(Player player, Npc npc) {
        if (npc.template.npcTemplateId == BOSS_ID_1 || npc.template.npcTemplateId == BOSS_ID_2 || npc.template.npcTemplateId == BOSS_ID_3) {
            if (!((DunMixedArena) player.map).checkBossAvailable(npc.template.npcTemplateId)) {
                return;
            }
            npc.status = Npc.STATUS_DIE;
            npc.timeWait = 0;
            npc.timeReturn = Integer.MAX_VALUE;
            if ((player.typePk == Player.PK_PHE1 && (npc.template.npcTemplateId == BOSS_ID_2 || npc.template.npcTemplateId == BOSS_ID_3)) ||
                (player.typePk == Player.PK_PHE2 && (npc.template.npcTemplateId == BOSS_ID_1 || npc.template.npcTemplateId == BOSS_ID_3)) ||
                (player.typePk == Player.PK_PHE3 && (npc.template.npcTemplateId == BOSS_ID_1 || npc.template.npcTemplateId == BOSS_ID_2))
            ) {
                addPoint(player, 500);
            }
        }
    }

    public static void updatePointKillPlayer(Player player1, Player player2) {
        if (player1.typePk == player2.typePk) {
            return;
        }
        if (player2.status == Player.ME_DIE) {
            if (!player1.checkRecentPK(player2)) {
                player1.addRecentPK(player2);
                addPoint(player1, 5);
            } else {
                NJUtil.sendServer(player1.getSession(), "Bạn không thể pk 1 người quá nhiều lần");
            }
        }
    }

    public static void updatePointLog() {
        int pointSchool1 = 0;
        int pointSchool2 = 0;
        int pointSchool3 = 0;
        String weekString = NJUtil.getWeekString();
        try {
            int[] point = Database.getMixedArena(weekString);
            pointSchool1 = point[0];
            pointSchool2 = point[1];
            pointSchool3 = point[2];
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        pointSchool1 += point1;
        pointSchool2 += point2;
        pointSchool3 += point3;
        Database.updateMixedArena(weekString, pointSchool1, pointSchool2, pointSchool3);
    }

    public static void updateTimer() {
        LocalDateTime dateTimeNow = LocalDateTime.now();
        int hour = dateTimeNow.getHour();
        int min = dateTimeNow.getMinute();
        int sec = dateTimeNow.getSecond();
        if (isTimeTesting) {
            isWaitingTime = false;
            isFightingTime = false;
            LocalDateTime dateTimeNew = LocalDateTime.now().plusMinutes(15);
            LocalDateTime dateTimeFight = LocalDateTime.now().plusMinutes(60);
            if (dateTimeNow.isBefore(dateTimeNew)) {
                isWaitingTime = true;
                isFightingTime = false;
                LOGGER.info("Waiting arena !!! " + dateTimeNew.getHour() + ":" + dateTimeNew.getMinute());
                int calmin = (int) ((dateTimeNew.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - dateTimeNow.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) / 60000L);
                if (GameServer.openMixedArena && calmin % 5 == 0) {
                    if (GameServer.isSvEnglish()) {
                        NJUtil.sendServerAlert("School competition will begin in " + calmin + " minutes! Meet the principal to register your name.");
                    } else {
                        NJUtil.sendServerAlert("Cuộc tranh tài giữa các trường sẽ bắt đầu trong thời gian " + calmin + " phút nữa! Hãy gặp Hiệu trưởng để báo danh.");
                    }
                }
                initWaitMaps();
            } else if (dateTimeNow.isBefore(dateTimeFight) && dateTimeNow.isAfter(dateTimeNew)) {
                isWaitingTime = true;
                isFightingTime = true;
                LOGGER.info("Waiting arena !!! " + dateTimeFight.getHour() + ":" + dateTimeFight.getMinute());
                LocalDateTime dateTimeMark1 = LocalDateTime.now().plusMinutes(20);
                LocalDateTime dateTimeMark2 = LocalDateTime.now().plusMinutes(40);
                if (dateTimeNow.isAfter(dateTimeMark1) && dateTimeNow.isBefore(dateTimeMark2)) {
                    int max = point1;
                    int maxSchool = 1;
                    if (point2 > max) {
                        max = point2;
                        maxSchool = 2;
                    }
                    if (point3 > max) {
                        //max = MixedArena.point3;
                        maxSchool = 3;
                    }
                    switch (maxSchool) {
                        case 1:
                            bBossOut1 = true;
                            break;
                        case 2:
                            bBossOut2 = true;
                            break;
                        case 3:
                            bBossOut3 = true;
                            break;
                    }
                } else if (dateTimeNow.isAfter(dateTimeMark2)) {
                    if (bBossOut1) {
                        int max = point2;
                        int maxSchool = 2;
                        if (point3 > max) {
                            //max = MixedArena.point3;
                            maxSchool = 3;
                        }
                        if (maxSchool == 2) {
                            bBossOut2 = true;
                        } else {
                            bBossOut3 = true;
                        }
                    } else if (bBossOut2) {
                        int max = point1;
                        int maxSchool = 1;
                        if (point3 > max) {
                            //max = MixedArena.point3;
                            maxSchool = 3;
                        }
                        if (maxSchool == 1) {
                            bBossOut1 = true;
                        } else {
                            bBossOut3 = true;
                        }
                    } else {
                        int max = point2;
                        int maxSchool = 2;
                        if (point1 > max) {
                            //max = MixedArena.point1;
                            maxSchool = 1;
                        }
                        if (maxSchool == 1) {
                            bBossOut1 = true;
                        } else {
                            bBossOut2 = true;
                        }
                    }
                }
            }
            if (!isWaitingTime && !isFightingTime) {
                closingArena();
            }
        } else {
            isWaitingTime = false;
            isFightingTime = false;
            if ((hour == 11 && min >= 45) || (hour == 14 && min >= 45) || (hour == 20 && min >= 45)) {
                isWaitingTime = true;
                isFightingTime = false;
                if (GameServer.openMixedArena && min % 5 == 0 && sec >= 30) {
                    if (GameServer.isSvEnglish()) {
                        NJUtil.sendServerAlert("School competition will begin in " + (60 - min) + " minutes! Meet the principal to register your name.");
                    } else {
                        NJUtil.sendServerAlert("Cuộc tranh tài giữa các trường sẽ bắt đầu trong thời gian " + (60 - min) + " phút nữa! Hãy gặp Hiệu trưởng để báo danh.");
                    }
                }
                initWaitMaps();
            } else if (hour == 12 || hour == 15 || hour == 21) {
                isWaitingTime = true;
                isFightingTime = true;
                min %= 60;
                if (min >= 20 && min < 40) {
                    int max2 = point1;
                    int maxSchool2 = 1;
                    if (point2 > max2) {
                        max2 = point2;
                        maxSchool2 = 2;
                    }
                    if (point3 > max2) {
                        //max2 = MixedArena.point3;
                        maxSchool2 = 3;
                    }
                    switch (maxSchool2) {
                        case 1:
                            bBossOut1 = true;
                            break;
                        case 2:
                            bBossOut2 = true;
                            break;
                        case 3:
                            bBossOut3 = true;
                            break;
                    }
                } else if (min >= 40) {
                    if (bBossOut1) {
                        int max2 = point2;
                        int maxSchool2 = 2;
                        if (point3 > max2) {
                            //max2 = MixedArena.point3;
                            maxSchool2 = 3;
                        }
                        if (maxSchool2 == 2) {
                            bBossOut2 = true;
                        } else {
                            bBossOut3 = true;
                        }
                    } else if (bBossOut2) {
                        int max2 = point1;
                        int maxSchool2 = 1;
                        if (point3 > max2) {
                            //max2 = MixedArena.point3;
                            maxSchool2 = 3;
                        }
                        if (maxSchool2 == 1) {
                            bBossOut1 = true;
                        } else {
                            bBossOut3 = true;
                        }
                    } else {
                        int max2 = point2;
                        int maxSchool2 = 2;
                        if (point1 > max2) {
                            //max2 = MixedArena.point1;
                            maxSchool2 = 1;
                        }
                        if (maxSchool2 == 1) {
                            bBossOut1 = true;
                        } else {
                            bBossOut2 = true;
                        }
                    }
                }
            }
            if (!isWaitingTime && !isFightingTime) {
                closingArena();
            }
        }
    }

    public static void updateWinningSchool() {
        int pointSchool1 = 0;
        int pointSchool2 = 0;
        int pointSchool3 = 0;
        String weekString = NJUtil.getWeekString();
        try {
            int[] point = Database.getMixedArena(weekString);
            pointSchool1 = point[0];
            pointSchool2 = point[1];
            pointSchool3 = point[2];
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        pointSchool1 += point1;
        pointSchool2 += point2;
        pointSchool3 += point3;
        if (pointSchool1 > pointSchool2 && pointSchool1 > pointSchool3) {
            winningSchool = 1;
        } else if (pointSchool2 > pointSchool1 && pointSchool2 > pointSchool3) {
            winningSchool = 2;
        } else if (pointSchool3 > pointSchool1 && pointSchool3 > pointSchool2) {
            winningSchool = 3;
        } else {
            winningSchool = 0;
        }
        StringBuilder sb = new StringBuilder();
        LocalDate dateNow = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        sb.append(dateNow.toString());
        sb.append(",").append(dateNow.plusDays(1));
        String sb2;
        if (winningSchool == 1) {
            sb2 = "1,2";
        } else if (winningSchool == 2) {
            sb2 = "3,4";
        } else if (winningSchool == 3) {
            sb2 = "5,6";
        } else {
            sb2 = "-1,-1";
        }
        Database.updateWinningSchool(String.format("%s;%s", sb, sb2));
        Database.updateMixedArena(weekString, pointSchool1, pointSchool2, pointSchool3);
    }
}
