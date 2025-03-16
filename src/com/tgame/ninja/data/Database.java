package com.tgame.ninja.data;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.*;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;

public class Database {

    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

    public static ConnectionPool connPool;

    public static ConnectionPool connPoolWeb;

    public static Vector<PlayerSaveFail> allPlayerSaveFail = new Vector<>();

    public static Hashtable<String, PlayerSaveFail> hAllPlayerSaveFail = new Hashtable<>();

    public static Vector<String> boardwords = new Vector<>();

    public static String[] names;

    public static void createPool(String host, String dbname, String user, String pwd, int maxConn) {
        connPool = new ConnectionPool(host, user, pwd, dbname, maxConn, false, true);
    }

    public static void createPoolWeb(String host, String dbname, String user, String pwd, int maxConn) {
        connPoolWeb = new ConnectionPool(host, user, pwd, dbname, maxConn, true, true);
    }

    public static Connection getConnectPool() throws SQLException {
        return connPool.getDataSource().getConnection();
    }

    public static Connection getConnectPoolWeb() throws SQLException {
        return connPoolWeb.getDataSource().getConnection();
    }

    public static LoginData loginWeb(String username, String password) {
        String sql = "SELECT id,username,password,ban,is_active,type_admin FROM users WHERE username = ? AND password = ? LIMIT 1";
        LoginData data = new LoginData();
        try (Connection conn = getConnectPoolWeb();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Compare password directly
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    data = new LoginData();
                    data.id = rs.getInt("id");
                    data.username = rs.getString("username"); 
                    data.password = rs.getString("password");
                    data.ban = rs.getBoolean("ban");
                    data.isActive = rs.getBoolean("is_active");
                    data.typeAdmin = rs.getByte("type_admin");
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return data;
    }

    public static boolean createPlayer(Player player) {
        String sql = "INSERT INTO nin_player(id,name,userId,headId,coin_bag,coin_box,coin_lock,gold,gender,reg_date,login_date,ban,exp,exp_down,pk,bag,box,mapTemplateId,mapTemplateId_focus,x,y,classId,potential_point,skill_info,skill_point,p_strength,p_agile,p_mp,p_hp,taskFinish,taskIndex,taskCount,effId,timeEff,changeCoin,friends,info,info_event,item_info,clanName,idserver,codeSecure) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, player.playerId);
            pstmt.setString(2, player.name);
            pstmt.setInt(3, player.userId);
            pstmt.setInt(4, player.headId);
            pstmt.setInt(5, player.getXu());
            pstmt.setInt(6, player.getXuBox());
            pstmt.setInt(7, player.getYen());
            pstmt.setInt(8, player.getLuong());
            pstmt.setInt(9, player.gender);
            pstmt.setLong(10, player.reg_date);
            pstmt.setLong(11, player.login_date);
            pstmt.setInt(12, player.ban ? 1 : 0);
            pstmt.setLong(13, player.exp);
            pstmt.setLong(14, player.exp_down);
            pstmt.setInt(15, player.pk);
            pstmt.setInt(16, player.bag);
            pstmt.setInt(17, player.box);
            pstmt.setInt(18, player.map.template.mapTemplateId);
            pstmt.setInt(19, player.mapTemplateId_focus);
            pstmt.setInt(20, player.x);
            pstmt.setInt(21, player.y);
            pstmt.setInt(22, player.classId);
            pstmt.setInt(23, player.potential_point);
            pstmt.setString(24, player.getSkillInfo());
            pstmt.setInt(25, player.skill_point);
            pstmt.setInt(26, player.p_strength);
            pstmt.setInt(27, player.p_agile);
            pstmt.setInt(28, player.p_mp);
            pstmt.setInt(29, player.p_hp);
            pstmt.setInt(30, player.taskFinish);
            pstmt.setInt(31, (player.taskMain != null) ? player.taskMain.index : -1);
            pstmt.setInt(32, (player.taskMain != null) ? player.taskMain.count : 0);
            pstmt.setInt(33, -1); // effId
            pstmt.setInt(34, 0);  // timeEff
            pstmt.setInt(35, player.isChangeCoin ? 1 : 0);
            pstmt.setString(36, player.getFriends());
            pstmt.setString(37, player.getInfo(false));
            pstmt.setString(38, player.getInfoEvent());
            pstmt.setString(39, ""); // item_info
            pstmt.setString(40, ""); // clanName
            pstmt.setString(41, "0"); // idserver
            pstmt.setString(42, "" ); // codeSecure
    
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static boolean isCreate(String name) {
        String sql = "SELECT id FROM nin_player WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static int countMaxPlayer() {
        String sql = "select count(id) as a from nin_player";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static int countTotalPlayer() {
        String sql = "SELECT count(*) FROM nin_player";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static int getMaxPlayerId() {
        String sql = "SELECT MAX(id) FROM nin_player";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return -1;
    }

    public static Vector<Player> getListPlayerByServer(int userId, byte idserver) {
        String sql = "SELECT * FROM nin_player WHERE userId = ? and idserver=" + idserver;
        if (GameServer.getServerId() != 2) {
            sql = "SELECT * FROM nin_player WHERE userId = ?";
        }
        Vector<Player> players = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Player player = mapPlayer(rs);
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setItemInfo(rs.getString("item_info"), true);
                    } catch (Exception e) {
                        player.isLoadDataError = true;
                        LOGGER.error("", e);
                    }
                    if (players.size() < 3) {
                        if (players.size() == 0) {
                            players.add(player);
                        } else if (player.login_date > players.get(0).login_date) {
                            players.add(0, player);
                        } else {
                            players.add(player);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return players;
    }

    public static Player getPlayer(String charname) {
        String sql = "SELECT * FROM nin_player WHERE name='" + charname + "'";
        Player player = null;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    player = mapPlayerArena(rs);
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                        player.setSkill(rs.getString("skill_info"));
                    } catch (Exception e2) {
                        player.isLoadDataError = true;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return player;
    }

    public static Player getPlayer(int userId, int playerId) {
        String sql = "SELECT * FROM nin_player WHERE userId = ? AND id = ?";
        Player player = null;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    player = mapPlayer(rs);
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setSkill(rs.getString("skill_info"));
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                    } catch (Exception e2) {
                        player.isLoadDataError = true;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return player;
    }

    public static Vector<Player> getPlayerByServer(int userId, byte idserver) {
        String sql = "SELECT * FROM nin_player WHERE userId = ? and idserver=" + idserver;
        if (GameServer.getServerId() != 2) {
            sql = "SELECT * FROM nin_player WHERE userId = ?";
        }
        Vector<Player> players = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Player player = mapPlayer(rs);
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                        player.setSkill(rs.getString("skill_info"));
                    } catch (Exception e) {
                        player.isLoadDataError = true;
                        LOGGER.error("", e);
                    }
                    if (players.size() < 3) {
                        if (players.size() == 0) {
                            players.add(player);
                        } else if (player.login_date > players.get(0).login_date) {
                            players.add(0, player);
                        } else {
                            players.add(player);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (int i = players.size() - 1; i >= 0; --i) {
            if (players.get(i).isLoadDataError) {
                players.remove(i);
            } else {
                players.get(i).reward = getReward(players.get(i).playerId);
            }
        }
        return players;
    }

    public static Vector<Player> getPlayerByServer(int userId, byte idserver, String name) {
        String sql = "SELECT * FROM nin_player WHERE userId = ? and idserver=" + idserver + " and name='" + name + "'";
        if (GameServer.getServerId() != 2) {
            sql = "SELECT * FROM nin_player WHERE userId = ?";
        }
        Vector<Player> players = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Player player = mapPlayer(rs);
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                        player.setSkill(rs.getString("skill_info"));
                    } catch (Exception e) {
                        player.isLoadDataError = true;
                        LOGGER.error("", e);
                    }
                    if (players.size() < 3) {
                        if (players.size() == 0) {
                            players.add(player);
                        } else if (player.login_date > players.get(0).login_date) {
                            players.add(0, player);
                        } else {
                            players.add(player);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (int i = players.size() - 1; i >= 0; --i) {
            if (players.get(i).isLoadDataError) {
                players.remove(i);
            } else {
                players.get(i).reward = getReward(players.get(i).playerId);
            }
        }
        return players;
    }

    public static String getTimeOnline(String playername) {
        String sql = "SELECT info FROM nin_player WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, playername);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("info");
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static void savePlayer(Player player, int mapSave) {
        if (player.playercopy != null) {
            savePlayerCoppy(player.playercopy, player.playercopy.map.getTemplateId());
            if (player.playercopy.myskill != null && player.playercopy.myskill.template != null) {
                player.saveRms("CSkill", new byte[]{ (byte) player.playercopy.myskill.template.skillTemplateId }, false);
            }
        }
        updateLevelPlayerArena(player.name, player.level);
        int x = player.x;
        int y = player.y;
        boolean isSave = false;
        int mapTemplateId = mapSave;
        for (int i = 0; i < Map.mapNotSave.length; ++i) {
            if (Map.mapNotSave[i] == mapTemplateId) {
                int mapDefault = player.mapTemplateId_focus;
                if (player.mapTemplateIdGo != -1) {
                    mapDefault = player.mapTemplateIdGo;
                }
                for (int j = 0; j < Map.mapNotSave.length; ++j) {
                    if (mapDefault == Map.mapNotSave[j]) {
                        mapDefault = 22;
                        break;
                    }
                }
                if (mapDefault < 0) {
                    mapDefault = 1;
                }
                MapTemplate template = ServerController.mapTemplates.get(mapDefault);
                x = template.defaultX;
                y = template.defaultY;
                mapTemplateId = template.mapTemplateId;
                break;
            }
        }
        String sql = "UPDATE nin_player SET name = ?, userId = ?, headId = ?, coin_bag = ?, coin_box = ?, coin_lock = ?, gold = ?, gender = ?, reg_date = ?, login_date = ?, ban = ?, exp = ?, exp_down = ?, pk = ?, bag = ?, box = ?, mapTemplateId = ?, mapTemplateId_focus = ?, x = ?, y = ?, classId = ?, potential_point = ?, skill_info = ?, skill_point = ?, p_strength = ?, p_agile = ?, p_mp = ?, p_hp = ?, taskFinish = ?, taskIndex = ?, taskCount = ?, changeCoin = ?, friends = ?, info = ?, info_event = ?, item_info = ?, clanName = ?, codeSecure = ? WHERE id = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, player.name);
            pstmt.setInt(2, player.userId);
            pstmt.setInt(3, player.headId);
            pstmt.setInt(4, player.getXu());
            pstmt.setInt(5, player.getXuBox());
            pstmt.setInt(6, player.getYen());
            pstmt.setInt(7, player.getLuong());
            pstmt.setInt(8, player.gender);
            pstmt.setLong(9, player.reg_date);
            pstmt.setLong(10, player.login_date);
            pstmt.setInt(11, player.ban ? 1 : 0);
            pstmt.setLong(12, player.exp);
            pstmt.setLong(13, player.exp_down);
            pstmt.setInt(14, player.pk);
            pstmt.setInt(15, player.bag);
            pstmt.setInt(16, player.box);
            pstmt.setInt(17, mapTemplateId);
            pstmt.setInt(18, player.mapTemplateId_focus);
            pstmt.setInt(19, x);
            pstmt.setInt(20, y);
            pstmt.setInt(21, player.classId);
            pstmt.setInt(22, player.potential_point);
            pstmt.setString(23, player.getSkillInfo());
            pstmt.setInt(24, player.skill_point);
            pstmt.setInt(25, player.p_strength);
            pstmt.setInt(26, player.p_agile);
            pstmt.setInt(27, player.p_mp);
            pstmt.setInt(28, player.p_hp);
            pstmt.setInt(29, player.taskFinish);
            pstmt.setInt(30, (player.taskMain != null) ? player.taskMain.index : -1);
            pstmt.setInt(31, (player.taskMain != null) ? player.taskMain.count : 0);
            pstmt.setInt(32, player.isChangeCoin ? 1 : 0);
            pstmt.setString(33, player.getFriends());
            pstmt.setString(34, player.getInfo(false));
            pstmt.setString(35, player.getInfoEvent());
            pstmt.setString(36, player.getItemInfo());
            pstmt.setString(37, (player.clan == null) ? "" : player.clan.name);
            pstmt.setString(38, player.codeSecure);
            pstmt.setInt(39, player.playerId);
            int result = pstmt.executeUpdate();
            if (result != 0) {
                isSave = true;
            }
        } catch (Exception e) {
            if (player.name != null && !player.name.isEmpty() && hAllPlayerSaveFail.get(player.name) == null) {
                PlayerSaveFail p = new PlayerSaveFail(player);
                allPlayerSaveFail.add(p);
                hAllPlayerSaveFail.put(player.name, p);
            }
            LOGGER.error("Error save character: {} ({},{},{},{},{},{},{}, {}, {})", player.name, player.exp, player.exp_down, player.getXu(), player.getXuBox(), player.getLuong(), player.getYen(), player.getInfo(false), player.getInfoEvent(), player.getItemInfo(), e);
            try {
                saveLogAll(player.getSession().username, e.toString(), "loi_savePlayerDB");
            } catch (Exception e2) {
            }
        }
        if (isSave) {
            try {
                if (player.name != null) {
                    hAllPlayerSaveFail.remove(player.name);
                }
            } catch (Exception e) {
            }
            if (player.reward != null) {
                updateReward(player.reward);
            }
        }
    }

    public static void saveAllPlayer() {
        try {
            Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
            for (int userId : huPlayers.keySet()) {
                try {
                    Player p = ServerController.huPlayers.get(userId);
                    if (p == null || p.getSession() == null) {
                        continue;
                    }
                    try {
                        if (p.map != null && p.map.isDunPB) {
                            ++p.countPB;
                        }
                        if (p.isLockChat) {
                            saveLogAll(p.name, Player.getInfoFromVectorString(p.infoChat), "logchat");
                            p.isLockChat = false;
                        }
                    } catch (Exception e) {
                    }
                    int idmap = 22;
                    if (p.map != null) {
                        idmap = p.map.getTemplateId();
                    }
                    savePlayer(p, idmap);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
            LOGGER.info("Du lieu nguoi choi da duoc luu!");
        } catch (Exception e) {
        }
    }

    public static void saveAllCharFail() {
        try {
            Vector<PlayerSaveFail> remove = new Vector<>();
            for (PlayerSaveFail saveFail : allPlayerSaveFail) {
                Player pl = saveFail.p;
                savePlayer(pl, pl.map.getTemplateId());
                if (hAllPlayerSaveFail.get(pl.name) != null) {
                    ++saveFail.count;
                    if (saveFail.count >= 50) {
                        remove.add(saveFail);
                    }
                } else {
                    remove.add(saveFail);
                }
            }
            for (PlayerSaveFail playerSaveFail : remove) {
                try {
                    allPlayerSaveFail.remove(playerSaveFail);
                    hAllPlayerSaveFail.remove(playerSaveFail.p.name);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static boolean isCharSaveFail(String name) {
        return hAllPlayerSaveFail.get(name) != null;
    }

    public static boolean banPlayer(String name) {
        String sql = "UPDATE nin_player SET ban = 1 WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static boolean removeBanPlayer(String name) {
        String sql = "UPDATE nin_player SET ban = 0 WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static void loadTop() {
        try {
            ServerController.topHangdong = getTophangdong();
            ServerController.topCaothu = getTopcaothu();
            ServerController.topDaiGia = getTopdaigia();
            ServerController.topTaiNang = getToptainang();
            ServerController.topTaiNangClass = getToptainangClass();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static Vector<TopCaoThu> getDataTopcaothu() {
        String sql = "SELECT name,exp,info FROM nin_player order by exp desc limit 10";
        Vector<TopCaoThu> list = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TopCaoThu topcaothu = new TopCaoThu();
                    Player p = new Player();
                    p.exp = rs.getLong("exp");
                    p.setInfo(rs.getString("info").trim());
                    topcaothu.name = rs.getString("name").trim().toLowerCase();
                    topcaothu.exp = p.exp;
                    list.add(topcaothu);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            return list;
        }
        return list;
    }

    public static Vector<TopDaiGia> getDataTopdaigia() {
        String sql = "SELECT name,gold FROM nin_player WHERE ban = 0 AND name NOT IN('phuocne', 'top1ks', 'legends', 'izshutdow', 'gamemobi1', 'shinobi') order by gold desc limit 10";
        Vector<TopDaiGia> list = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TopDaiGia topCoinLock = new TopDaiGia();
                    String name = rs.getString("name");
                    if (name != null) {
                        topCoinLock.name = name.trim().toLowerCase();
                        topCoinLock.money = rs.getInt("gold");
                        list.add(topCoinLock);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            return list;
        }
        return list;
    }

    public static Vector<String> getTopcaothu() {
        Vector<String> list = new Vector<>();
        Vector<String> names = new Vector<>();
        try {
            String str = NJUtil.readFileString(GameServer.outPath + "/toplevel.txt", false);
            if (str != null) {
                String[] strs = str.split("\n");
                for (int i = 0; i < Math.min(strs.length, 10); ++i) {
                    String data = strs[i].trim();
                    if (!data.isEmpty()) {
                        String[] datas = data.split(",");
                        list.add(datas[0].trim() + " đạt cấp " + GameServer.maxLevel + " vào " + datas[1].trim());
                        names.add(datas[0].trim().toLowerCase());
                    }
                }
            }
            if (list.size() < 10) {
                Vector<TopCaoThu> tops = new Vector<>(getDataTopcaothu());
                tops.sort(TopCaoThu.sort);
                for (TopCaoThu top : tops) {
                    if (list.size() >= 10) {
                        break;
                    }
                    if (!names.contains(top.name)) {
                        list.add(top.name + " đạt cấp " + top.getLevel());
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return list;
    }

    public static Vector<String> getTopdaigia() {
        Vector<String> tops = new Vector<>();
        try {
            Vector<TopDaiGia> list = new Vector<>(getDataTopdaigia());
            list.sort(TopDaiGia.sort);
            for (int i = 0; i < Math.min(list.size(), 10); ++i) {
                tops.add(list.get(i).name + ": " + NJUtil.numberToString(String.valueOf(list.get(i).money)) + " lượng");
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return tops;
    }

    public static Vector<TopHangDong> getTophangdong() {
        Vector<TopHangDong> list = new Vector<>();
        try {
            String str = NJUtil.readFileString(GameServer.outPath + "/tophangdong.txt", false);
            if (str != null) {
                String[] strs = str.split("\n");
                for (int i = 0; i < Math.min(strs.length, 10); ++i) {
                    String data = strs[i].trim();
                    if (!data.isEmpty()) {
                        String[] datas = data.split(",");
                        list.add(new TopHangDong(datas[0].trim(), Integer.parseInt(datas[1].trim())));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return list;
    }

    public static Vector<TopTainang> getToptainang() {
        Vector<TopTainang> list = new Vector<>();
        LocalDate dateNow = LocalDate.now();
        if (dateNow.getDayOfMonth() == 1) {
            return list;
        }
        try {
            String str = NJUtil.readFileString(GameServer.outPath + "/toptainang.txt", false);
            if (str != null) {
                String[] strs = str.split("\n");
                for (int i = 0; i < Math.min(strs.length, 10); ++i) {
                    String data = strs[i].trim();
                    if (!data.isEmpty()) {
                        String[] datas = data.split(",");
                        list.add(new TopTainang(datas[0].trim(), Integer.parseInt(datas[1].trim())));
                    }
                    if (list.size() > 10) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Vector<Vector<TopTainang>> getToptainangClass() {
        Vector<Vector<TopTainang>> list = new Vector<>();
        LocalDate dateNow = LocalDate.now();
        if (dateNow.getDayOfMonth() == 1) {
            return list;
        }
        try {
            for (int j = 0; j < 6; ++j) {
                Vector<TopTainang> top = new Vector<>();
                list.add(top);
                String str = NJUtil.readFileString(GameServer.outPath + "/toptainang_" + j + ".txt", false);
                if (str != null) {
                    String[] strs = str.split("\n");
                    for (int i = 0; i < Math.min(strs.length, 10); ++i) {
                        String data = strs[i].trim();
                        if (!data.isEmpty()) {
                            String[] datas = data.split(",");
                            top.add(new TopTainang(datas[0].trim(), Integer.parseInt(datas[1].trim())));
                        }
                        if (top.size() > 10) {
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static boolean createClan(Clan clan) {
        String sql = "INSERT INTO nin_clan(name,level,icon,coin,main_name,reg_date,openDun,item_info,itemlevel) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, clan.name);
            pstmt.setInt(2, clan.level);
            pstmt.setInt(3, clan.icon);
            pstmt.setInt(4, clan.coin);
            pstmt.setString(5, clan.main_name);
            pstmt.setLong(6, clan.reg_date);
            pstmt.setInt(7, clan.openDun);
            pstmt.setString(8, "");
            pstmt.setInt(9, 0);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static Vector<Clan> getClan() {
        String sql = "SELECT * FROM nin_clan ORDER BY reg_date";
        Vector<Clan> clans = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Clan clan = new Clan();
                    clan.name = rs.getString("name");
                    clan.level = Math.min(rs.getInt("level"), Clan.exps.length - 1);
                    clan.exp = rs.getInt("exp");
                    clan.icon = rs.getInt("icon");
                    clan.coin = rs.getInt("coin");
                    clan.use_card = rs.getInt("use_card");
                    clan.main_name = rs.getString("main_name");
                    clan.assist_name = rs.getString("assist_name");
                    clan.elder1_name = rs.getString("elder1_name");
                    clan.elder2_name = rs.getString("elder2_name");
                    clan.elder3_name = rs.getString("elder3_name");
                    clan.elder4_name = rs.getString("elder4_name");
                    clan.elder5_name = rs.getString("elder5_name");
                    clan.reg_date = rs.getLong("reg_date");
                    clan.log = rs.getString("log");
                    clan.alert = rs.getString("alert");
                    clan.openDun = rs.getInt("openDun");
                    clan.itemlevel = rs.getInt("itemlevel");
                    clan.setItemInfo(rs.getString("item_info"));
                    clan.setThanThuInfo(rs.getString("thanthu"));
                    int max = -(clan.getFee() * 3 + 1);
                    if (clan.coin > 0 && clan.coin > max) {
                        clans.add(clan);
                    } else {
                        saveLogAll(clan.main_name, "thieu ngan sach duy tri: " + clan.coin + " < " + max, "clan");
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (Clan clan2 : clans) {
            clan2.addMembers(getMember(clan2));
        }
        return clans;
    }

    public static Vector<Member> getMember(Clan clan) {
        String sql = "SELECT classId,name,exp,info,login_date FROM nin_player WHERE clanName = ?";
        Vector<Member> members = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, clan.name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Member member = new Member(0);
                    member.classId = rs.getInt("classId");
                    member.name = rs.getString("name");
                    byte switchNewExp = 0;
                    try {
                        String[] infos = rs.getString("info").split(",");
                        member.pointClan = Integer.parseInt(infos[25]);
                        member.pointClanWeek = Integer.parseInt(infos[28]);
                        LocalDate dateNow = LocalDate.now();
                        LocalDate dateLogin = NJUtil.getDateByMilis(rs.getLong("login_date"));
                        if ((dateNow.getDayOfMonth() != dateLogin.getDayOfMonth() || dateNow.getMonthValue() != dateLogin.getMonthValue() || dateNow.getYear() != dateLogin.getYear()) && NJUtil.isNewWeek(dateNow, dateLogin)) {
                            member.pointClanWeek = 0;
                        }
                        switchNewExp = Byte.parseByte(infos[68]);
                    } catch (Exception e) {
                    }
                    if (switchNewExp == 0) {
                        member.level = Player.getLevel(rs.getLong("exp"));
                    } else {
                        member.level = Player.getLevel1(rs.getLong("exp"));
                    }
                    if (member.name.equals(clan.main_name)) {
                        member.typeClan = 4;
                    } else if (member.name.equals(clan.assist_name)) {
                        member.typeClan = 3;
                    } else if (member.name.equals(clan.elder1_name) || member.name.equals(clan.elder2_name) || member.name.equals(clan.elder3_name) || member.name.equals(clan.elder4_name) || member.name.equals(clan.elder5_name)) {
                        member.typeClan = 2;
                    }
                    members.add(member);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return members;
    }

    public static boolean isHaveClan(String name) {
        String sql = "SELECT name FROM nin_clan WHERE name=?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static void saveClan(Clan clan) {
        String sql = "UPDATE nin_clan SET level = ?, exp = ?, icon = ?, coin = ?, main_name = ?, assist_name = ?, elder1_name = ?, elder2_name = ?, elder3_name = ?, elder4_name = ?, elder5_name = ?, log = ?, alert = ?, openDun = ?, use_card = ?, item_info = ?, itemlevel = ?,thanthu=? WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            clan.reset();
            pstmt.setInt(1, clan.level);
            pstmt.setInt(2, clan.exp);
            pstmt.setInt(3, clan.icon);
            pstmt.setInt(4, clan.coin);
            pstmt.setString(5, clan.main_name);
            pstmt.setString(6, clan.assist_name);
            pstmt.setString(7, clan.elder1_name);
            pstmt.setString(8, clan.elder2_name);
            pstmt.setString(9, clan.elder3_name);
            pstmt.setString(10, clan.elder4_name);
            pstmt.setString(11, clan.elder5_name);
            pstmt.setString(12, clan.log);
            try {
                if (clan.alert.length() >= 255) {
                    clan.alert = clan.alert.substring(0, 254);
                }
            } catch (Exception e) {
            }
            String infott = clan.getInfoSaveThanThu();
            pstmt.setString(13, clan.alert);
            pstmt.setInt(14, clan.openDun);
            pstmt.setInt(15, clan.use_card);
            pstmt.setString(16, clan.getItemInfo());
            pstmt.setInt(17, clan.itemlevel);
            pstmt.setString(18, infott);
            pstmt.setString(19, clan.name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void clearClan(String name) {
        String sql = "UPDATE nin_player SET clanName = '' WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateThanThuClan(Clan clan) {
        String sql = "UPDATE nin_clan SET thanthu = ? WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, clan.getInfoSaveThanThu());
            pstmt.setString(2, clan.name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void createPlayerArena(PlayerArenaInfo player) {
        String sql = "INSERT INTO nin_thach_dau(charname,userid,rank,lv,timeReg,lastPlay,timeLog) VALUES(?,?,?,?,now(),now(),?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, player.name);
            pstmt.setInt(2, player.userid);
            pstmt.setInt(3, player.rank);
            pstmt.setInt(4, player.level);
            pstmt.setLong(5, System.currentTimeMillis());
            pstmt.executeUpdate();
        } catch (Exception e) {
            updateRankPlayerArena(player);
        }
    }

    public static void loadAllPlayerArenaInfo() {
        String sql = "select * from nin_thach_dau order by rank";
        deletePlayerArenaTimeout();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlayerArenaInfo p = new PlayerArenaInfo();
                    p.name = rs.getString("charname");
                    p.userid = rs.getInt("userid");
                    p.rank = rs.getInt("rank");
                    p.level = rs.getShort("lv");
                    PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.put(p.name, p);
                    PlayerArenaInfo.addPlayer(p);
                }
            }
        } catch (Exception e) {
        }
    }

    public static void deletePlayerArenaTimeout() {
        long time = System.currentTimeMillis() - NJUtil.getMilisByDay(7);
        String sql = "delete from nin_thach_dau where timeLog < " + time;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void updateLevelPlayerArena(String name, int level) {
        String sql = "update nin_thach_dau set lv=? where charname=?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, level);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void updateRankPlayerArena(PlayerArenaInfo player) {
        String sql = "update nin_thach_dau set rank=?,lastPlay=now(),timeLog=? where charname=?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, player.rank);
            pstmt.setLong(2, System.currentTimeMillis());
            pstmt.setString(3, player.name);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static long addItemAuction(Item item) {
        String sql = "INSERT INTO nin_item_auction(playerId,playername,itemTemplateId,sys,typeUI,indexUI,expires,isLock,upgrade,optionInfo,quantity,saleCoin,saleShop) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setInt(1, item.playerId);
            pstmt.setString(2, item.playerName);
            pstmt.setInt(3, item.getTemplateId());
            pstmt.setInt(4, item.sys);
            pstmt.setInt(5, item.typeUI);
            pstmt.setInt(6, item.indexUI);
            pstmt.setLong(7, item.expires);
            pstmt.setBoolean(8, item.isLock);
            pstmt.setInt(9, item.upgrade);
            pstmt.setString(10, item.getOptionInfo());
            pstmt.setInt(11, item.quantity);
            pstmt.setInt(12, item.saleCoinLock);
            pstmt.setInt(13, item.saleShop);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return -1;
    }

    public static Vector<Item> getItemAuction() {
        String sql = "SELECT * FROM nin_item_auction WHERE sold = 0 ORDER BY id";
        Vector<Item> items = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getInt("itemTemplateId"));
                    item.itemId = rs.getLong("id");
                    item.playerId = rs.getInt("playerId");
                    item.playerName = rs.getString("playername");
                    item.sys = rs.getInt("sys");
                    if (item.sys > 3 && !item.isTypeMon()) {
                        item.sys -= 3;
                    }
                    item.typeUI = rs.getInt("typeUI");
                    item.indexUI = rs.getInt("indexUI");
                    item.upgrade = rs.getInt("upgrade");
                    item.expires = rs.getLong("expires");
                    item.isLock = rs.getBoolean("isLock");
                    item.quantity = rs.getInt("quantity");
                    item.saleCoinLock = rs.getInt("saleCoin");
                    item.saleShop = rs.getInt("saleShop");
                    String optionInfo = rs.getString("optionInfo");
                    if (optionInfo != null && !optionInfo.trim().isEmpty()) {
                        item.options = new Vector<>();
                        String[] optionInfos = optionInfo.split(";");
                        for (String info : optionInfos) {
                            String[] datas = info.split(",");
                            int optionTemplateId = Integer.parseInt(datas[0]);
                            ItemOption option = new ItemOption();
                            option.param = Integer.parseInt(datas[1]);
                            option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                            item.options.add(option);
                        }
                    }
                    items.add(item);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return items;
    }

    public static void removeItemAuction(long id) {
            String sql = "UPDATE nin_item_auction SET sold = 1 WHERE id = ?";
            System.out.println(sql);
            try (Connection conn = getConnectPool();
                 PreparedStatement pstmt = conn.prepareStatement(sql)
            ) {
                pstmt.setLong(1, id);
                pstmt.executeUpdate();
            } catch (Exception e) {
                LOGGER.error("", e);
            }
    }

    public static void addBoardItem(Item item) {
        String sql = "INSERT INTO board_item(playerName,itemTemplateId,sys,expires,isLock,upgrade,optionInfo,quantity,saleCoin) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, item.playerName);
            pstmt.setInt(2, item.getTemplateId());
            pstmt.setInt(3, item.sys);
            pstmt.setLong(4, item.expires);
            pstmt.setBoolean(5, item.isLock);
            pstmt.setInt(6, item.upgrade);
            pstmt.setString(7, item.getOptionInfo());
            pstmt.setInt(8, item.quantity);
            pstmt.setInt(9, item.saleCoinLock);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static Vector<Item> getBoardItem(String playerName) {
        String sql = "SELECT * FROM board_item WHERE received = 0 AND playerName = ?";
        Vector<Item> items = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, playerName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getInt("itemTemplateId"));
                    item.itemId = rs.getLong("id");
                    item.playerName = rs.getString("playerName");
                    item.sys = rs.getInt("sys");
                    if (item.sys > 3 && !item.isTypeMon()) {
                        item.sys -= 3;
                    }
                    item.upgrade = rs.getInt("upgrade");
                    item.expires = rs.getLong("expires");
                    item.isLock = rs.getBoolean("isLock");
                    item.quantity = rs.getInt("quantity");
                    item.saleCoinLock = rs.getInt("saleCoin");
                    String optionInfo = rs.getString("optionInfo");
                    if (optionInfo != null && !optionInfo.trim().isEmpty()) {
                        item.options = new Vector<>();
                        String[] optionInfos = optionInfo.split(";");
                        for (String info : optionInfos) {
                            String[] datas = info.split(",");
                            int optionTemplateId = Integer.parseInt(datas[0]);
                            ItemOption option = new ItemOption();
                            option.param = Integer.parseInt(datas[1]);
                            option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                            item.options.add(option);
                        }
                    }
                    items.add(item);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return items;
    }

    public static void clearBoardItem(long itemId) {
        String sql = "UPDATE board_item SET received = 1 WHERE id = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setLong(1, itemId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static Vector<BoardPlayer> getBoardPlayer(String playerName) {
        String sql = "SELECT * FROM board_player WHERE received = 0 AND playerName = ?";
        Vector<BoardPlayer> items = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, playerName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new BoardPlayer(
                        rs.getInt("id"),
                        rs.getString("playerName"),
                        rs.getInt("coin_bag"),
                        rs.getInt("coin_lock"),
                        rs.getLong("exp"),
                        rs.getString("sender"),
                        rs.getInt("gold"),
                        rs.getInt("ticket")
                    ));
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return items;
    }

    public static void insertBoardPlayer(String playerName, int coin_bag, String sender) {
        String sql = "INSERT INTO board_player(playerName, coin_bag, sender) VALUES(?, ?, ?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, playerName);
            pstmt.setInt(2, coin_bag);
            pstmt.setString(3, sender);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void clearBoardPlayer(int id) {
        String sql = "UPDATE board_player SET received = 1 WHERE id = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void insertPlayerNapThiendia(String name, int coin_bag_xu, int coin_lock_yen, int gold_luong) {
        String sql = "INSERT INTO board_thidau(name,coin_bag,coin_lock,gold) VALUES(?, ?, ?,?) ON DUPLICATE KEY UPDATE gold=gold+?,coin_bag=coin_bag+?,coin_lock=coin_lock+?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            pstmt.setInt(2, coin_bag_xu);
            pstmt.setInt(3, coin_lock_yen);
            pstmt.setInt(4, gold_luong);
            pstmt.setInt(5, gold_luong);
            pstmt.setInt(6, coin_bag_xu);
            pstmt.setInt(7, coin_lock_yen);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void setMoneyRewardThiendia(String charname, int coin_bag, int coin_lock, int gold) {
        insertPlayerNapThiendia(charname, coin_bag, coin_lock, gold);
    }

    public static void clearPlayerNapThidau(String name) {
        String sql = "DELETE FROM board_thidau WHERE name = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static synchronized boolean createPlayerCoppy(Player player, int ownerid) {
        String sql = "INSERT INTO nin_playercoppy(id,name,userId,headId,gender,reg_date,login_date,ban,exp,exp_down,pk,bag,box,mapTemplateId,mapTemplateId_focus,x,y,classId,potential_point,skill_info,skill_point,p_strength,p_agile,p_mp,p_hp,taskFinish,taskIndex,taskCount,effId,timeEff,changeCoin,friends,info,info_event,item_info,ownerid) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setInt(1, player.playerId);
            pstmt.setString(2, player.name);
            pstmt.setInt(3, player.userId);
            pstmt.setInt(4, player.headId);
            pstmt.setInt(5, player.gender);
            pstmt.setLong(6, player.reg_date);
            pstmt.setLong(7, player.login_date);
            pstmt.setInt(8, player.ban ? 1 : 0);
            pstmt.setLong(9, player.exp);
            pstmt.setLong(10, player.exp_down);
            pstmt.setInt(11, player.pk);
            pstmt.setInt(12, player.bag);
            pstmt.setInt(13, player.box);
            pstmt.setInt(14, player.map.template.mapTemplateId);
            pstmt.setInt(15, player.mapTemplateId_focus);
            pstmt.setInt(16, player.x);
            pstmt.setInt(17, player.y);
            pstmt.setInt(18, player.classId);
            pstmt.setInt(19, player.potential_point);
            pstmt.setString(20, player.getSkillInfo());
            pstmt.setInt(21, player.skill_point);
            pstmt.setInt(22, player.p_strength);
            pstmt.setInt(23, player.p_agile);
            pstmt.setInt(24, player.p_mp);
            pstmt.setInt(25, player.p_hp);
            pstmt.setInt(26, 0);
            pstmt.setInt(27, -1);
            pstmt.setInt(28, 0);
            pstmt.setInt(29, -1);
            pstmt.setInt(30, 0);
            pstmt.setInt(31, 0);
            pstmt.setString(32, player.getFriends());
            pstmt.setString(33, player.getInfo(false));
            pstmt.setString(34, player.getInfoEvent());
            pstmt.setString(35, " & & ");
            pstmt.setInt(36, ownerid);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    player.playerId = rs.getInt(1);
                    return true;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return false;
    }

    public static Vector<PlayerCopy> getPlayerCopy(int ownerId) {
        String sql = "SELECT * FROM nin_playercoppy WHERE ownerid = ? order by exp desc limit 1";
        Vector<PlayerCopy> players = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, ownerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlayerCopy player = mapPlayerCoppy(rs);
                    player.isPlayerCopy = true;
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setSkill(rs.getString("skill_info"));
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    if (players.size() < 3) {
                        if (players.size() == 0) {
                            players.add(player);
                        } else if (player.login_date > players.get(0).login_date) {
                            players.add(0, player);
                        } else {
                            players.add(player);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return players;
    }

    public static Vector<PlayerCopy> getPlayerCopy(String name) {
        String sql = "SELECT * FROM nin_playercoppy WHERE name = ?";
        Vector<PlayerCopy> players = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlayerCopy player = mapPlayerCoppy(rs);
                    player.isPlayerCopy = true;
                    try {
                        player.setInfo(rs.getString("info").trim());
                        player.setSkill(rs.getString("skill_info"));
                        player.setFriends(rs.getString("friends").trim());
                        player.setItemInfo(rs.getString("item_info"), false);
                    } catch (Exception e) {
                    }
                    if (players.size() < 3) {
                        if (players.size() == 0) {
                            players.add(player);
                        } else if (player.login_date > players.get(0).login_date) {
                            players.add(0, player);
                        } else {
                            players.add(player);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return players;
    }

    public static void savePlayerCoppy(Player player, int mapSave) {
        String sql = "UPDATE nin_playercoppy SET name = ?, userId = ?, headId = ?, gender = ?, reg_date = ?, login_date = ?, ban = ?, exp = ?, exp_down = ?, pk = ?, bag = ?, box = ?, mapTemplateId = ?, mapTemplateId_focus = ?, x = ?, y = ?, classId = ?, potential_point = ?, skill_info = ?, skill_point = ?, p_strength = ?, p_agile = ?, p_mp = ?, p_hp = ?, taskFinish = ?, taskIndex = ?, taskCount = ?, changeCoin = ?, friends = ?, info = ?, info_event = ?, item_info = ?, clanName = ? WHERE id = ?";
        int x = player.x;
        int y = player.y;
        int mapTemplateId = mapSave;
        for (int i = 0; i < Map.mapNotSave.length; ++i) {
            if (Map.mapNotSave[i] == mapTemplateId) {
                int mapDefault = player.mapTemplateId_focus;
                if (player.mapTemplateIdGo != -1) {
                    mapDefault = player.mapTemplateIdGo;
                }
                for (int j = 0; j < Map.mapNotSave.length; ++j) {
                    if (mapDefault == Map.mapNotSave[j]) {
                        mapDefault = 22;
                        break;
                    }
                }
                if (mapDefault < 0) {
                    mapDefault = 1;
                }
                MapTemplate template = ServerController.mapTemplates.get(mapDefault);
                x = template.defaultX;
                y = template.defaultY;
                mapTemplateId = template.mapTemplateId;
                break;
            }
        }
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, player.name);
            pstmt.setInt(2, player.userId);
            pstmt.setInt(3, player.headId);
            pstmt.setInt(4, player.gender);
            pstmt.setLong(5, player.reg_date);
            pstmt.setLong(6, player.login_date);
            pstmt.setInt(7, player.ban ? 1 : 0);
            pstmt.setLong(8, player.exp);
            pstmt.setLong(9, player.exp_down);
            pstmt.setInt(10, player.pk);
            pstmt.setInt(11, player.bag);
            pstmt.setInt(12, player.box);
            pstmt.setInt(13, mapTemplateId);
            pstmt.setInt(14, player.mapTemplateId_focus);
            pstmt.setInt(15, x);
            pstmt.setInt(16, y);
            pstmt.setInt(17, player.classId);
            pstmt.setInt(18, player.potential_point);
            pstmt.setString(19, player.getSkillInfo());
            pstmt.setInt(20, player.skill_point);
            pstmt.setInt(21, player.p_strength);
            pstmt.setInt(22, player.p_agile);
            pstmt.setInt(23, player.p_mp);
            pstmt.setInt(24, player.p_hp);
            pstmt.setInt(25, player.taskFinish);
            pstmt.setInt(26, (player.taskMain != null) ? player.taskMain.index : -1);
            pstmt.setInt(27, (player.taskMain != null) ? player.taskMain.count : 0);
            pstmt.setInt(28, player.isChangeCoin ? 1 : 0);
            pstmt.setString(29, player.getFriends());
            pstmt.setString(30, player.getInfo(false));
            pstmt.setString(31, player.getInfoEvent());
            pstmt.setString(32, player.getItemInfo());
            pstmt.setString(33, (player.clan == null) ? "" : player.clan.name);
            pstmt.setInt(34, player.playerId * -1 - 1000000000);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void createReward(int playerId) {
        String sql = "INSERT INTO nin_reward(playerId) VALUES(?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, playerId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static Reward getReward(int playerId) {
        String sql = "SELECT * FROM nin_reward WHERE playerId = ?";
        Reward reward = new Reward(playerId);
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    reward.isOpen = rs.getBoolean("isOpen");
                    reward.isUp10 = rs.getBoolean("isUp10");
                    reward.isUp20 = rs.getBoolean("isUp20");
                    reward.isUp30 = rs.getBoolean("isUp30");
                    reward.isUp40 = rs.getBoolean("isUp40");
                    reward.isUp50 = rs.getBoolean("isUp50");
                    reward.isUp60 = rs.getBoolean("isUp60");
                    reward.isUp70 = rs.getBoolean("isUp70");
                    reward.isUp80 = rs.getBoolean("isUp80");
                    reward.isUp90 = rs.getBoolean("isUp90");
                    reward.isDenbu = rs.getBoolean("isDenbu");
                    reward.isNewbie = rs.getBoolean("isNewbie");
                    return reward;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        createReward(playerId);
        return reward;
    }

    public static String getSqlReward(int playerId) {
        String sql = "SELECT * FROM nin_reward WHERE playerId = ?";
        String sqlReward = "INSERT INTO nin_reward(";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, playerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                sqlReward = sqlReward + getNameCols(rs) + ") VALUES(";
                if (rs.next()) {
                    sqlReward = sqlReward + getValueCols(rs) + ");";
                    return sqlReward;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static void updateReward(Reward reward) {
        String sql = "UPDATE nin_reward SET isOpen = ?, isUp10 = ?, isUp20 = ?, isUp30 = ?, isUp40 = ?, isUp50 = ?, isUp60 = ?, isUp70 = ?, isUp80 = ?, isUp90 = ?, isDenbu = ?, isNewbie = ? WHERE playerId = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, reward.isOpen ? 1 : 0);
            pstmt.setInt(2, reward.isUp10 ? 1 : 0);
            pstmt.setInt(3, reward.isUp20 ? 1 : 0);
            pstmt.setInt(4, reward.isUp30 ? 1 : 0);
            pstmt.setInt(5, reward.isUp40 ? 1 : 0);
            pstmt.setInt(6, reward.isUp50 ? 1 : 0);
            pstmt.setInt(7, reward.isUp60 ? 1 : 0);
            pstmt.setInt(8, reward.isUp70 ? 1 : 0);
            pstmt.setInt(9, reward.isUp80 ? 1 : 0);
            pstmt.setInt(10, reward.isUp90 ? 1 : 0);
            pstmt.setInt(11, reward.isDenbu ? 1 : 0);
            pstmt.setInt(12, reward.isNewbie ? 1 : 0);
            pstmt.setInt(13, reward.playerId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static boolean saveLogPlayer(LogData logData) {
        String sql = "INSERT INTO nin_log_player(username,playername,date_log,text,info,log_type) VALUES(?,?,now(),?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, logData.username);
            pstmt.setString(2, logData.playername);
            pstmt.setString(3, logData.text);
            pstmt.setString(4, logData.info);
            pstmt.setByte(5, logData.log_type);
            if (pstmt.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static void saveLogAll(String charname, String info, String aclog) {
        String sql = "INSERT INTO nin_log_all(charname,infolog,tlog,aclog) values (?,?,now(),?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, charname);
            pstmt.setString(2, info);
            pstmt.setString(3, aclog);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void insertCuahang(String date_log, int itemTemplateId, int quantity) {
        String sql = "INSERT INTO nin_log_buy(date_log,itemTemplateId,quantity) VALUES(?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, date_log);
            pstmt.setInt(2, itemTemplateId);
            pstmt.setInt(3, quantity);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void saveLogPlayerWithGems(Player player, int count, String content) {
        String sql = "insert into `nin_log_gem` set `pid`= ?, `pname` = ?, `count` = ?, `content` = ?, `userid` = ? on duplicate key update `count` = ?, `content` = ?;";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, player.playerId);
            pstmt.setString(2, player.name);
            pstmt.setInt(3, count);
            pstmt.setString(4, content);
            pstmt.setInt(5, player.userId);
            pstmt.setInt(6, count);
            pstmt.setString(7, content);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateLogNapLevel(int level, int gold) {
        String sql = "INSERT INTO nin_log_nap_level(level,gold) VALUES(?,?) ON DUPLICATE KEY UPDATE gold = gold + ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, level);
            pstmt.setInt(2, gold);
            pstmt.setInt(3, gold);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void doAddUpdatePlayerXoso(String charname, int money) {
        String sql = "insert into nin_xoso(charname,money) values ('" + charname + "'," + money + ") ON DUPLICATE KEY UPDATE money=" + money;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void loadAllXoso() {
        String sql = "select * from nin_xoso WHERE money > 0 OR win = 1";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("charname");
                    int money = rs.getInt("money");
                    int win = rs.getInt("win");
                    int moneyWin = rs.getInt("moneywin");
                    int moneyBid = rs.getInt("moneyBid");
                    if (money > 0) {
                        PlayerXoso pxs = new PlayerXoso();
                        pxs.name = name;
                        pxs.money = money;
                        Player.ALL_PLAYER_XOSO.put(name, pxs);
                        Player.MONEY_XOSO += money;
                    }
                    if (win == 1) {
                        Player.moneyCharXosoBid = moneyBid;
                        Player.moneyCharXosowin = moneyWin;
                        Player.charNameXosoWin = name;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void resetXoso(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        String sql = "update nin_xoso set money=0,win=0";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        updateWinXoso(charnameWin, moneyBid, moneyWin, moneyTaxWin);
    }

    public static void updateTaxWinXoso(int moneyTaxWin) {
        String sql = "update nin_xoso set moneyTax=moneyTax+" + moneyTaxWin;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateWinXoso(String charnameWin, int moneyBid, int moneyWin, int moneyTaxWin) {
        String sql = "update nin_xoso set win=1,moneyBid=" + moneyBid + ",moneywin=" + moneyWin + " where charname='" + charnameWin + "'";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        saveLogAll(charnameWin, "thang " + moneyWin + " xu, dat cuoc " + moneyBid, "xoso");
        updateTaxWinXoso(moneyTaxWin);
    }

    public static Data[] getDatas() {
        String sql = "SELECT * FROM nin_data ORDER BY id";
        Data[] datas = new Data[4];
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                int i = 0;
                while (rs.next()) {
                    byte[] data = rs.getBytes("data");
                    if (data == null) {
                        return null;
                    }
                    datas[i] = new Data(data, rs.getInt("version"));
                    ++i;
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
            return datas;
        }
        return datas;
    }

    public static void updateData(int id, byte[] data, int version) {
        String sql = "UPDATE nin_data SET data = ?, version = ? WHERE id = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setBytes(1, data);
            pstmt.setInt(2, version);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void loadItemShop() {
        String sql = "SELECT * FROM nin_item_shop where sell=1 ORDER BY id";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idTemplate = rs.getInt("itemTemplateId");
                    Item item = new Item(idTemplate);
                    item.itemId = rs.getLong("id");
                    item.typeUI = rs.getInt("typeUI");
                    item.sys = rs.getInt("sys");
                    item.expires = rs.getLong("expires");
                    item.buyCoin = rs.getInt("buyCoin");
                    item.buyCoinLock = rs.getInt("buyCoinLock");
                    item.buyGold = rs.getInt("buyGold");
                    item.buyGoldLock = rs.getInt("buyGoldLock");
                    String optionInfo = rs.getString("optionInfo");
                    if (optionInfo != null && !optionInfo.trim().isEmpty()) {
                        item.options = new Vector<>();
                        String[] optionInfos = optionInfo.split(";");
                        for (String info : optionInfos) {
                            String[] data = info.split(",");
                            int optionTemplateId = Integer.parseInt(data[0]);
                            ItemOption option = new ItemOption();
                            option.param = Integer.parseInt(data[1]);
                            option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                            item.changeOption(option);
                            item.options.add(option);
                        }
                    }
                    if (item.buyGold > 0) {
                        Player.ALL_ITEM_SHOP_LUONG.put(idTemplate, "");
                    }
                    item.options = Item.getOptionTemplate(item);
                    if (item.isPetTuanLoc()) {
                        item.createOptionPetTuanLoc();
                    }
                    updateItemShop(item);
                    if (item.sys >= 4 && !item.isTypeMon()) {
                        item.sys -= 3;
                    }
                    switch (item.typeUI) {
                        case 20:
                            item.indexUI = ServerController.shopNonNams.size();
                            if (item.canSell()) {
                                ServerController.shopNonNams.add(item);
                                continue;
                            }
                            ServerController.shopNonNams_not_sell.add(item);
                            continue;
                        case 21:
                            item.indexUI = ServerController.shopNonNus.size();
                            if (item.canSell()) {
                                ServerController.shopNonNus.add(item);
                                continue;
                            }
                            ServerController.shopNonNus_not_sell.add(item);
                            continue;
                        case 22:
                            item.indexUI = ServerController.shopAoNams.size();
                            if (item.canSell()) {
                                ServerController.shopAoNams.add(item);
                                continue;
                            }
                            ServerController.shopAoNams_not_sell.add(item);
                            continue;
                        case 23:
                            item.indexUI = ServerController.shopAoNus.size();
                            if (item.canSell()) {
                                ServerController.shopAoNus.add(item);
                                continue;
                            }
                            ServerController.shopAoNus_not_sell.add(item);
                            continue;
                        case 24:
                            item.indexUI = ServerController.shopGangTayNams.size();
                            if (item.canSell()) {
                                ServerController.shopGangTayNams.add(item);
                                continue;
                            }
                            ServerController.shopGangTayNams_not_sell.add(item);
                            continue;
                        case 25:
                            item.indexUI = ServerController.shopGangTayNus.size();
                            if (item.canSell()) {
                                ServerController.shopGangTayNus.add(item);
                                continue;
                            }
                            ServerController.shopGangTayNus_not_sell.add(item);
                            continue;
                        case 26:
                            item.indexUI = ServerController.shopQuanNams.size();
                            if (item.canSell()) {
                                ServerController.shopQuanNams.add(item);
                                continue;
                            }
                            ServerController.shopQuanNams_not_sell.add(item);
                            continue;
                        case 27:
                            item.indexUI = ServerController.shopQuanNus.size();
                            if (item.canSell()) {
                                ServerController.shopQuanNus.add(item);
                                continue;
                            }
                            ServerController.shopQuanNus_not_sell.add(item);
                            continue;
                        case 28:
                            item.indexUI = ServerController.shopGiayNams.size();
                            if (item.canSell()) {
                                ServerController.shopGiayNams.add(item);
                                continue;
                            }
                            ServerController.shopGiayNams_not_sell.add(item);
                            continue;
                        case 29:
                            item.indexUI = ServerController.shopGiayNus.size();
                            if (item.canSell()) {
                                ServerController.shopGiayNus.add(item);
                                continue;
                            }
                            ServerController.shopGiayNus_not_sell.add(item);
                            continue;
                        case 16:
                            item.indexUI = ServerController.shopLiens.size();
                            if (item.canSell()) {
                                ServerController.shopLiens.add(item);
                                continue;
                            }
                            ServerController.shopLiens_not_sell.add(item);
                            continue;
                        case 17:
                            item.indexUI = ServerController.shopNhans.size();
                            if (item.canSell()) {
                                ServerController.shopNhans.add(item);
                                continue;
                            }
                            ServerController.shopNhans_not_sell.add(item);
                            continue;
                        case 18:
                            item.indexUI = ServerController.shopNgocBois.size();
                            if (item.canSell()) {
                                ServerController.shopNgocBois.add(item);
                                continue;
                            }
                            ServerController.shopNgocBois_not_sell.add(item);
                            continue;
                        case 19:
                            item.indexUI = ServerController.shopPhus.size();
                            if (item.canSell()) {
                                ServerController.shopPhus.add(item);
                                continue;
                            }
                            ServerController.shopPhus_not_sell.add(item);
                            continue;
                        case 2:
                            item.indexUI = ServerController.shopVuKhis.size();
                            if (item.canSell()) {
                                ServerController.shopVuKhis.add(item);
                                continue;
                            }
                            ServerController.shopVuKhis_not_sell.add(item);
                            continue;
                        case 6:
                            item.indexUI = ServerController.shopStacks.size();
                            ServerController.shopStacks.add(item);
                            continue;
                        case 7:
                            item.isLock = true;
                            item.indexUI = ServerController.shopStackLocks.size();
                            ServerController.shopStackLocks.add(item);
                            continue;
                        case 8:
                            item.indexUI = ServerController.shopGrocerys.size();
                            ServerController.shopGrocerys.add(item);
                            continue;
                        case 9:
                            item.isLock = true;
                            item.indexUI = ServerController.shopGroceryLocks.size();
                            ServerController.shopGroceryLocks.add(item);
                            continue;
                        case 14:
                            item.indexUI = ServerController.shopStores.size();
                            ServerController.shopStores.add(item);
                            continue;
                        case 15:
                            item.indexUI = ServerController.shopBooks.size();
                            ServerController.shopBooks.add(item);
                            continue;
                        case 32:
                            item.indexUI = ServerController.shopFashions.size();
                            ServerController.shopFashions.add(item);
                            continue;
                        case 34:
                            item.indexUI = ServerController.shopGiatocs.size();
                            ServerController.shopGiatocs.add(item);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateItemShop(Item item) {
        String sql = "UPDATE nin_item_shop SET optionInfo = ? WHERE id = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, item.getOptionInfo());
            pstmt.setLong(2, item.itemId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateItemShopSell(int iditem, boolean sell) {
        int typeSell = sell ? 1 : 0;
        String sql = "UPDATE nin_item_shop SET sell=" + typeSell + " where itemTemplateId=" + iditem;
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateItemShopSell() {
        updateItemShopSell(268, GameServer.openTaThu);
        updateItemShopSell(490, GameServer.openLangCo);
        updateItemShopSell(537, GameServer.openVDMQ);
        updateItemShopSell(538, GameServer.openVDMQ);
        updateItemShopSell(564, GameServer.openVDMQ);
        updateItemShopSell(545, GameServer.openPhanThan);
        updateItemShopSell(432, EventManager.getInstance().isSummerEvent());
        updateItemShopSell(433, EventManager.getInstance().isSummerEvent());
    }

    public static Vector<EffectTemplate> getEffectTemplate() {
        String sql = "SELECT * FROM nin_effect_template ORDER BY id";
        Vector<EffectTemplate> effTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    EffectTemplate effTempalte = new EffectTemplate();
                    effTempalte.id = rs.getInt("id");
                    effTempalte.name = rs.getString("name");
                    effTempalte.type = rs.getByte("type");
                    effTempalte.iconId = rs.getInt("iconId");
                    effTemplates.add(effTempalte);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return effTemplates;
    }

    public static Vector<ItemTemplate> getItemTemplate() {
        String sql = "SELECT * FROM nin_item_template ORDER BY id";
        Vector<ItemTemplate> itemTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ItemTemplate itemTemplate = new ItemTemplate(rs.getInt("id"));
                    itemTemplate.idTemplateUp = rs.getInt("idTemplateUp");
                    itemTemplate.name = rs.getString("name");
                    itemTemplate.name_en = rs.getString("name_en");
                    itemTemplate.description = rs.getString("description");
                    itemTemplate.description_en = rs.getString("description_en");
                    itemTemplate.type = rs.getInt("type");
                    itemTemplate.gender = rs.getInt("gender");
                    itemTemplate.level = rs.getInt("level");
                    if (itemTemplate.level < 1) {
                        itemTemplate.level = 1;
                    }
                    itemTemplate.iconId = rs.getInt("iconId");
                    itemTemplate.part = rs.getInt("part");
                    itemTemplate.isUpToUp = rs.getBoolean("up_to_up");
                    itemTemplates.add(itemTemplate);
                    if (itemTemplate.type != 1 && itemTemplate.type != 15 && itemTemplate.type != 13 && itemTemplate.type != 12 && itemTemplate.type != 11 && itemTemplate.type != 10 && itemTemplate.type != 14 && itemTemplate.type < 16) {
                        if (itemTemplate.gender == 0) {
                            ItemTemplate.ALL_ITEM_NU.add(itemTemplate);
                        } else if (itemTemplate.gender == 1) {
                            ItemTemplate.ALL_ITEM_NAM.add(itemTemplate);
                        } else {
                            if (itemTemplate.gender != 2) {
                                continue;
                            }
                            ItemTemplate.ALL_ITEM_NAM_NU.add(itemTemplate);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return itemTemplates;
    }

    public static Vector<Integer> getIDItemNhiemVuNguyetNhan(Player p) {
        String sql = "SELECT * FROM nin_item_template where type<10 and type!=1 and level>=10 and level<=" + p.level + " and (gender=" + p.gender + " or gender=2)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                Vector<Integer> allid = new Vector<>();
                while (rs.next()) {
                    allid.add(rs.getInt("id"));
                }
                return allid;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static Vector<Item> getItemDefault() {
        String sql = "SELECT * FROM nin_item_default ORDER BY id";
        Vector<Item> items = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.template = new ItemTemplate();
                    item.template.type = rs.getInt("type");
                    item.sys = rs.getInt("sys");
                    String optionInfo = rs.getString("optionInfo");
                    if (optionInfo != null && !optionInfo.trim().isEmpty()) {
                        item.options = new Vector<>();
                        String[] optionInfos = optionInfo.split(";");
                        for (String info : optionInfos) {
                            String[] datas = info.split(",");
                            int optionTemplateId = Integer.parseInt(datas[0]);
                            ItemOption option = new ItemOption();
                            option.param = Integer.parseInt(datas[1]);
                            option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                            item.options.add(option);
                        }
                    }
                    items.add(item);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return items;
    }

    public static Vector<ItemOptionTemplate> getItemOptionTemplate() {
        String sql = "SELECT * FROM nin_item_option ORDER BY id";
        Vector<ItemOptionTemplate> optionTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ItemOptionTemplate optionTemplate = new ItemOptionTemplate();
                    optionTemplate.itemOptionTemplateId = rs.getInt("id");
                    optionTemplate.name = rs.getString("name");
                    optionTemplate.name_en = rs.getString("name_en");
                    optionTemplate.type = rs.getInt("type");
                    optionTemplate.setInfoValueGiam(rs.getString("vlgiam"));
                    optionTemplate.setInfoValueUp(rs.getString("vlup"));
                    optionTemplates.add(optionTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return optionTemplates;
    }

    public static Vector<MapTemplate> getMapTemplate() {
        String sql = "SELECT * FROM nin_map_template ORDER BY id";
        Vector<MapTemplate> mapTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MapTemplate mapTemplate = new MapTemplate(rs.getInt("id"));
                    mapTemplate.name = rs.getString("name");
                    mapTemplate.name_en = rs.getString("name_en");
                    mapTemplate.tileId = rs.getInt("tileId");
                    mapTemplate.defaultX = rs.getShort("defaultX");
                    mapTemplate.defaultY = rs.getShort("defaultY");
                    mapTemplate.bgId = rs.getInt("bgId");
                    mapTemplates.add(mapTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return mapTemplates;
    }

    public static Vector<NClass> getNClass() {
        String sql = "SELECT * FROM nin_class ORDER BY id";
        Vector<NClass> nClasss = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NClass nClass = new NClass();
                    nClass.classId = rs.getInt("id");
                    nClass.name = rs.getString("name");
                    nClass.name_en = rs.getString("name_en");
                    nClasss.add(nClass);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (NClass classs : nClasss) {
            classs.skillTemplates = getSkillTemplate(classs.classId);
        }
        return nClasss;
    }

    public static Vector<NpcTemplate> getNpcTemplate() {
        String sql = "SELECT * FROM nin_npc_template ORDER BY id";
        Vector<NpcTemplate> npcTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NpcTemplate npcTemplate = new NpcTemplate();
                    npcTemplate.npcTemplateId = rs.getInt("id");
                    npcTemplate.name = rs.getString("name");
                    npcTemplate.name_en = rs.getString("name_en");
                    npcTemplate.hp = rs.getInt("hp");
                    npcTemplate.level = rs.getInt("level");
                    npcTemplate.dame = rs.getInt("dame");
                    npcTemplate.exp = rs.getInt("exp");
                    npcTemplate.rangeMove = rs.getInt("rangeMove");
                    npcTemplate.rangeAttack = rs.getInt("rangeAttack");
                    npcTemplate.type = rs.getInt("type");
                    npcTemplate.speed = rs.getInt("speed");
                    npcTemplate.type = rs.getInt("type");
                    npcTemplate.waitAttack = rs.getInt("waitAttack");
                    npcTemplate.sysUp = rs.getInt("sysUp");
                    npcTemplate.sysDown = rs.getInt("sysDown");
                    npcTemplates.add(npcTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return npcTemplates;
    }

    public static Vector<PlayerTemplate> getPlayerTemplate() {
        String sql = "SELECT * FROM nin_player_template ORDER BY id";
        Vector<PlayerTemplate> playerTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PlayerTemplate playerTemplate = new PlayerTemplate();
                    playerTemplate.playerTemplateId = rs.getInt("id");
                    playerTemplate.name = rs.getString("name");
                    playerTemplate.name_en = rs.getString("name_en");
                    playerTemplate.headId = rs.getInt("headId");
                    playerTemplate.bodyId = rs.getInt("bodyId");
                    playerTemplate.legId = rs.getInt("legId");
                    playerTemplate.say = rs.getString("say").split(";");
                    playerTemplate.say_en = rs.getString("say_en").split(";");
                    String menu = rs.getString("menu");
                    if (menu != null && !menu.isEmpty()) {
                        String[] menus = menu.split(";");
                        playerTemplate.menu = new String[menus.length][];
                        for (int i = 0; i < menus.length; ++i) {
                            playerTemplate.menu[i] = menus[i].split(",");
                        }
                    }
                    menu = rs.getString("menu_en");
                    if (menu != null && !menu.isEmpty()) {
                        String[] menus = menu.split(";");
                        playerTemplate.menu_en = new String[menus.length][];
                        for (int i = 0; i < menus.length; ++i) {
                            playerTemplate.menu_en[i] = menus[i].split(",");
                        }
                    }
                    playerTemplates.add(playerTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return playerTemplates;
    }

    public static byte[] getRms(int playerId, String key, boolean isme) {
        String sql = "SELECT " + (isme ? "rmsValue" : "rsmcopy") + " FROM nin_rms WHERE playerId = ? AND rmsKey = ?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.setString(2, key);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    byte[] data = rs.getBytes(1);
                    return data != null ? data : new byte[0];
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    public static void saveRms(int playerId, String key, byte[] value, boolean isme) {
        String sql = "INSERT INTO nin_rms(playerId, rmsKey, rmsValue) VALUES(?, ?, ?)";
        if (!isme) {
            sql = "INSERT INTO nin_rms(playerId, rmsKey, rsmcopy) VALUES(?, ?, ?)";
        }
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, playerId);
            pstmt.setString(2, key);
            pstmt.setBytes(3, value);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateRms(int playerId, String key, byte[] value, boolean isme) {
        String sql = "UPDATE nin_rms SET rmsValue = ? WHERE playerId = ? AND rmsKey = ?";
        if (!isme) {
            sql = "UPDATE nin_rms SET rsmcopy = ? WHERE playerId = ? AND rmsKey = ?";
        }
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setBytes(1, value);
            pstmt.setInt(2, playerId);
            pstmt.setString(3, key);
            pstmt.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static Vector<Skill> getSkill(SkillTemplate template) {
        String sql = "SELECT * FROM nin_skill WHERE skillTemplateId = ? ORDER BY id";
        Vector<Skill> skills = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, template.skillTemplateId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Skill skill = new Skill();
                    skill.template = template;
                    skill.skillId = rs.getInt("id");
                    skill.point = rs.getInt("point");
                    skill.level = rs.getInt("level");
                    skill.manaUse = rs.getInt("manaUse");
                    skill.timeReplay = rs.getInt("timeReplay");
                    if (GameServer.isServerLocal() && skill.isSkillPhanThan()) {
                        skill.timeReplay = 5000;
                        skill.manaUse = 1;
                    }
                    skill.dx = rs.getInt("dx");
                    skill.dy = rs.getInt("dy");
                    skill.maxFight = rs.getInt("maxFight");
                    String optionInfo = rs.getString("optionInfo");
                    if (optionInfo != null && !optionInfo.trim().isEmpty()) {
                        skill.options = new Vector<>();
                        String[] optionInfos = optionInfo.split(";");
                        for (String info : optionInfos) {
                            String[] datas = info.split(",");
                            int optionTemplateId = Integer.parseInt(datas[0]);
                            SkillOption option = new SkillOption();
                            option.param = Integer.parseInt(datas[1]);
                            option.optionTemplate = ServerController.sOptionTemplates.get(optionTemplateId);
                            skill.options.add(option);
                        }
                    }
                    skills.add(skill);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return skills;
    }

    public static Vector<SkillOptionTemplate> getSkillOptionTemplate() {
        String sql = "SELECT * FROM nin_skill_option ORDER BY id";
        Vector<SkillOptionTemplate> optionTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SkillOptionTemplate optionTemplate = new SkillOptionTemplate();
                    optionTemplate.skillOptionTemplateId = rs.getInt("id");
                    optionTemplate.name = rs.getString("name");
                    optionTemplate.name_en = rs.getString("name_en");
                    optionTemplates.add(optionTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return optionTemplates;
    }

    public static Vector<SkillTemplate> getSkillTemplate(int classId) {
        String sql = "SELECT * FROM nin_skill_template WHERE classId = ? ORDER BY id";
        Vector<SkillTemplate> skillTemplates = new Vector<>();
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, classId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    SkillTemplate skillTemplate = new SkillTemplate();
                    skillTemplate.skillTemplateId = rs.getInt("id");
                    skillTemplate.name = rs.getString("name");
                    skillTemplate.name_en = rs.getString("name_en");
                    skillTemplate.classId = rs.getInt("classId");
                    skillTemplate.maxPoint = rs.getInt("maxPoint");
                    skillTemplate.type = rs.getInt("type");
                    skillTemplate.iconId = rs.getInt("iconId");
                    skillTemplate.description = rs.getString("description");
                    skillTemplate.description_en = rs.getString("description_en");
                    skillTemplates.add(skillTemplate);
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        for (SkillTemplate skillTemplate : skillTemplates) {
            skillTemplate.skills = getSkill(skillTemplate);
        }
        return skillTemplates;
    }

    public static void logCCU(int java, int android, int ios, int pc) {
        String sql = "INSERT INTO nin_cculog(logtime,maxccu,minccu,java,android,ios,pc) VALUES (NOW(),?,?,?,?,?,?)";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, GameServer.maxCCU);
            pstmt.setInt(2, GameServer.minCCU);
            pstmt.setInt(3, java);
            pstmt.setInt(4, android);
            pstmt.setInt(5, ios);
            pstmt.setInt(6, pc);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void loadNames() {
        try {
            String str = NJUtil.readFileString("data/nameword.txt", false);
            if (str != null) {
                names = str.replace("\r\n", "\n").split("\n");
            }
            str = NJUtil.readFileString("data/boardword.txt", false);
            if (str != null) {
                Collections.addAll(boardwords, str.replace("\r\n", "\n").split("\n"));
            }
        } catch (Exception e) {
        }
    }

    public static Player mapPlayer(ResultSet rs) throws SQLException {
        try {
            Player player = new Player();
            player.playerId = rs.getInt("id");
            player.name = rs.getString("name").toLowerCase();
            player.userId = rs.getInt("userId");
            player.headId = rs.getInt("headId");
            player.setXu(rs.getInt("coin_bag"));
            player.setXuBox(rs.getInt("coin_box"));
            player.setYen(rs.getInt("coin_lock"));
            player.setLuong(rs.getInt("gold"));
            player.gender = rs.getByte("gender");
            player.reg_date = rs.getLong("reg_date");
            player.login_date = rs.getLong("login_date");
            player.ban = rs.getBoolean("ban");
            player.exp = rs.getLong("exp");
            player.exp_down = rs.getLong("exp_down");
            player.pk = rs.getByte("pk");
            player.bag = rs.getByte("bag");
            player.box = rs.getByte("box");
            int mapTemplateId = rs.getInt("mapTemplateId");
            player.x = rs.getShort("x");
            player.y = rs.getShort("y");
            if (player.x <= 0 || player.y <= 0 || mapTemplateId < 0 || mapTemplateId > ServerController.mapTemplates.size()) {
                mapTemplateId = 22;
                player.x = 100;
                player.y = 100;
            }
            if (player.bag % 6 != 0) {
                player.isLoadDataError = true;
                player.bag = (byte) (player.bag / 6 * 6);
            }
            if (player.box % 6 != 0) {
                player.isLoadDataError = true;
                player.box = (byte) (player.box / 6 * 6);
            }
            if (player.pk > 15) {
                player.isLoadDataError = true;
                player.pk = 15;
            }
            if (player.exp < 0L || player.exp_down < 0L) {
                player.isLoadDataError = true;
            }
            player.itemBags = new Item[player.bag];
            player.itemBoxs = new Item[player.box];
            player.map = new Map(mapTemplateId);
            player.mapTemplateId_focus = rs.getShort("mapTemplateId_focus");
            if (!Player.canFocusReturnMap(player.mapTemplateId_focus)) {
                player.mapTemplateId_focus = 22;
                player.x = 100;
                player.y = 100;
            }
            if (player.mapTemplateId_focus < 0) {
                player.mapTemplateId_focus += 255;
            }
            player.classId = rs.getByte("classId");
            player.potential_point = rs.getShort("potential_point");
            player.skill_point = rs.getShort("skill_point");
            player.p_strength = rs.getShort("p_strength");
            player.p_agile = rs.getShort("p_agile");
            player.p_mp = rs.getInt("p_mp");
            player.p_hp = rs.getInt("p_hp");
            player.taskFinish = rs.getShort("taskFinish");
            player.taskIndex = rs.getByte("taskIndex");
            player.taskCount = rs.getShort("taskCount");
            player.effId = rs.getInt("effId");
            player.timeEff = rs.getInt("timeEff");
            player.isChangeCoin = rs.getBoolean("changeCoin");
            player.codeSecure = rs.getString("codeSecure");
            if (player.taskFinish < 28 && player.mapTemplateId_focus == 38) {
                player.mapTemplateId_focus = 22;
            }
            player.clanName = rs.getString("clanName");
            if (player.clanName == null || player.clanName.trim().isEmpty()) {
                try {
                    player.clan = ServerController.getClanPc(player);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
            if (!player.clanName.isEmpty()) {
                player.clan = ServerController.getClan(player.clanName);
                if (player.clan == null) {
                    try {
                        player.clan = ServerController.getClanPc(player);
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                }
            } else {
                try {
                    player.clan = ServerController.getClanPc(player);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            }
            return player;
        } catch (Exception e) {
            LOGGER.error(rs.getString("name"), e);
            throw new SQLException();
        }
    }

    public static PlayerArena mapPlayerArena(ResultSet rs) throws SQLException {
        try {
            PlayerArena player = new PlayerArena();
            player.playerId = rs.getInt("id");
            player.name = rs.getString("name").toLowerCase();
            player.userId = rs.getInt("userId");
            player.headId = rs.getInt("headId");
            player.setXu(rs.getInt("coin_bag"));
            player.setXuBox(rs.getInt("coin_box"));
            player.setYen(rs.getInt("coin_lock"));
            player.setLuong(rs.getInt("gold"));
            player.gender = rs.getByte("gender");
            player.reg_date = rs.getLong("reg_date");
            player.login_date = rs.getLong("login_date");
            player.ban = rs.getBoolean("ban");
            player.exp = rs.getLong("exp");
            player.exp_down = rs.getLong("exp_down");
            player.pk = rs.getByte("pk");
            player.bag = rs.getByte("bag");
            player.box = rs.getByte("box");
            int mapTemplateId = rs.getInt("mapTemplateId");
            player.x = rs.getShort("x");
            player.y = rs.getShort("y");
            if (player.x <= 0 || player.y <= 0 || mapTemplateId < 0 || mapTemplateId > ServerController.mapTemplates.size()) {
                mapTemplateId = 22;
                player.x = 100;
                player.y = 100;
            }
            if (player.bag % 6 != 0) {
                player.isLoadDataError = true;
                player.bag = (byte) (player.bag / 6 * 6);
            }
            if (player.box % 6 != 0) {
                player.isLoadDataError = true;
                player.box = (byte) (player.box / 6 * 6);
            }
            if (player.pk > 15) {
                player.isLoadDataError = true;
                player.pk = 15;
            }
            if (player.exp < 0L || player.exp_down < 0L) {
                player.isLoadDataError = true;
            }
            player.itemBags = new Item[player.bag];
            player.itemBoxs = new Item[player.box];
            player.map = new Map(mapTemplateId);
            player.mapTemplateId_focus = rs.getShort("mapTemplateId_focus");
            if (!Player.canFocusReturnMap(player.mapTemplateId_focus)) {
                player.mapTemplateId_focus = 22;
                player.x = 100;
                player.y = 100;
            }
            if (player.mapTemplateId_focus < 0) {
                player.mapTemplateId_focus += 255;
            }
            player.classId = rs.getByte("classId");
            player.potential_point = rs.getShort("potential_point");
            player.skill_point = rs.getShort("skill_point");
            player.p_strength = rs.getShort("p_strength");
            player.p_agile = rs.getShort("p_agile");
            player.p_mp = rs.getInt("p_mp");
            player.p_hp = rs.getInt("p_hp");
            player.taskFinish = rs.getShort("taskFinish");
            player.taskIndex = rs.getByte("taskIndex");
            player.taskCount = rs.getShort("taskCount");
            player.effId = rs.getInt("effId");
            player.timeEff = rs.getInt("timeEff");
            player.isChangeCoin = rs.getBoolean("changeCoin");
            if (player.taskFinish < 28 && player.mapTemplateId_focus == 38) {
                player.mapTemplateId_focus = 22;
            }
            player.clanName = rs.getString("clanName");
            if (player.clanName == null || player.clanName.trim().isEmpty()) {
                try {
                    player.clan = ServerController.getClanPc(player);
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
            } else {
                player.clan = ServerController.getClan(player.clanName);
            }
            return player;
        } catch (Exception e) {
            LOGGER.error(rs.getString("name"), e);
            throw new SQLException();
        }
    }

    public static PlayerCopy mapPlayerCoppy(ResultSet rs) throws SQLException {
        try {
            PlayerCopy player = new PlayerCopy();
            player.playerId = rs.getInt("id");
            player.name = rs.getString("name").toLowerCase();
            player.userId = rs.getInt("userId");
            player.headId = rs.getInt("headId");
            player.gender = rs.getByte("gender");
            player.reg_date = rs.getLong("reg_date");
            player.login_date = rs.getLong("login_date");
            player.ban = rs.getBoolean("ban");
            player.exp = rs.getLong("exp");
            player.exp_down = rs.getLong("exp_down");
            player.pk = rs.getByte("pk");
            player.bag = rs.getByte("bag");
            player.box = rs.getByte("box");
            //int mapTemplateId = rs.getInt("mapTemplateId");
            player.x = rs.getShort("x");
            player.y = rs.getShort("y");
            player.itemBags = new Item[player.bag];
            player.itemBoxs = new Item[player.box];
            player.classId = rs.getByte("classId");
            player.potential_point = rs.getShort("potential_point");
            player.skill_point = rs.getShort("skill_point");
            player.p_strength = rs.getShort("p_strength");
            player.p_agile = rs.getShort("p_agile");
            player.p_mp = rs.getInt("p_mp");
            player.p_hp = rs.getInt("p_hp");
            player.effId = rs.getInt("effId");
            player.timeEff = rs.getInt("timeEff");
            return player;
        } catch (Exception e) {
            LOGGER.error(rs.getString("name"), e);
            throw new SQLException();
        }
    }

    public static void loadServerSetting() {
        String sql = "select x2_exp,x2_exp_class,bonus_gold,badword from nin_setting";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            try (ResultSet rs = pstmt.executeQuery()) {
                String defaultDate = LocalDate.of(1970, 1, 1).toString();
                while (rs.next()) {
                    String timeX2 = rs.getString("x2_exp");
                    if (timeX2 != null && !timeX2.isEmpty()) {
                        try {
                            String[] splitTime = timeX2.split(",");
                            Player.timeX2Exp[0] = splitTime[0];
                            Player.timeX2Exp[1] = splitTime[1];
                        } catch (Exception e) {
                            Player.timeX2Exp[0] = defaultDate;
                            Player.timeX2Exp[1] = defaultDate;
                            LOGGER.error("Loi parse timeX2Exp", e);
                        }
                    }
                    String classX2 = rs.getString("x2_exp_class");
                    if (classX2 != null && !classX2.isEmpty()) {
                        try {
                            String[] splitData = classX2.split(";");
                            String[] splitTime = splitData[0].split(",");
                            String[] splitClass = splitData[1].split(",");
                            Player.timeClassX2Exp[0] = splitTime[0];
                            Player.timeClassX2Exp[1] = splitTime[1];
                            Player.classX2Exp[0] = Integer.parseInt(splitClass[0]);
                            Player.classX2Exp[1] = Integer.parseInt(splitClass[1]);
                        } catch (Exception e) {
                            Player.timeClassX2Exp[0] = defaultDate;
                            Player.timeClassX2Exp[1] = defaultDate;
                            Player.classX2Exp[0] = -1;
                            Player.classX2Exp[1] = -1;
                            LOGGER.error("Loi parse classX2Exp", e);
                        }
                    }
                    String[] badword = rs.getString("badword").split(",");
                    if (badword.length > 0) {
                        BadWord.list = badword;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void initMixedArena(String weekString) {
        String sql = "insert into nin_mixed_arena(w_m_y) values(?) on duplicate key update p1=0,p2=0,p3=0";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, weekString);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static int[] getMixedArena(String weekString) {
        String sql = "select p1,p2,p3 from nin_mixed_arena where w_m_y=?";
        int[] point = { 0, 0, 0 };
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, weekString);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    point[0] = rs.getInt("p1");
                    point[1] = rs.getInt("p2");
                    point[2] = rs.getInt("p3");
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return point;
    }

    public static void updateMixedArena(String weekString, int p1, int p2, int p3) {
        String sql = "update nin_mixed_arena set p1=?,p2=?,p3=? where w_m_y=?";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, p1);
            pstmt.setInt(2, p2);
            pstmt.setInt(3, p3);
            pstmt.setString(4, weekString);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static void updateWinningSchool(String s) {
        String sql = "update `nin_setting` set `x2_exp_class`= ?;";
        try (Connection conn = getConnectPool();
             PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, s);
            pstmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static String getNameCols(ResultSet rs) {
        StringBuilder sql = new StringBuilder();
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int columnCount = rsmd.getColumnCount(), i = 1; i < columnCount + 1; ++i) {
                sql.append(rsmd.getColumnName(i));
                if (i < columnCount) {
                    sql.append(",");
                }
            }
        } catch (Exception e) {
        }
        return sql.toString();
    }

    public static String getValueCols(ResultSet rs) {
        StringBuilder sql = new StringBuilder();
        try {
            int i = 1;
            //noinspection InfiniteLoopStatement
            while (true) {
                sql.append("'").append(rs.getString(i)).append("', ");
                ++i;
            }
        } catch (Exception e) {
            return sql.substring(0, sql.length() - 2);
        }
    }

    public static void startThreadSavePlayerFaill() {
        try {
            new Thread(() -> {
                while (!ServerController.isExit) {
                    saveAllCharFail();
                    NJUtil.sleep(500L);
                }
            }).start();
        } catch (Exception e) {
        }
    }
}
