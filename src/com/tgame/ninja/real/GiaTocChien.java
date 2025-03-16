package com.tgame.ninja.real;

import com.tgame.ninja.model.Clan;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.util.Hashtable;
import java.util.Vector;

public class GiaTocChien {

    public static Vector<GiaTocChien> gtcs;

    public int money;

    public int timeStart;

    public int timeLength;

    public boolean isItem;

    public boolean isStop;

    public Vector<Map> maps;

    public Vector<String> nameLefts;

    public Vector<String> nameRights;

    public Map map1;

    public Map map2;

    public int step;

    public Vector<String> namePlayLefts;

    public Vector<String> namePlayRights;

    public Clan nameGt1;

    public Clan nameGt2;

    public Hashtable<String, Integer> points;

    static {
        GiaTocChien.gtcs = new Vector<>();
    }

    public GiaTocChien(Player p1, Player p2, int money1, int money2) {
        maps = new Vector<>();
        nameLefts = new Vector<>();
        nameRights = new Vector<>();
        map1 = null;
        map2 = null;
        step = 0;
        namePlayLefts = new Vector<>();
        namePlayRights = new Vector<>();
        points = new Hashtable<>();
        money = money1 + money2;
        Clan clan = p1.clan;
        clan.coin -= money1;
        Clan clan2 = p2.clan;
        clan2.coin -= money2;
        timeStart = (int) (System.currentTimeMillis() / 1000L);
        timeLength = 300;
        nameGt1 = p1.clan;
        nameGt2 = p2.clan;
        nameLefts.add(p1.name);
        nameRights.add(p2.name);
        Map map = null;
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 7; ++j) {
                int mapTemplateId = j + 98;
                if (j > 0 && j < 6) {
                    mapTemplateId = j + 119;
                }
                map = new Map(ServerController.maps.size(), i, ServerController.mapTemplates.get(mapTemplateId));
                map.zoneId = i;
                map.giatocchien = this;
                if (map.template.mapTemplateId == 98 || map.template.mapTemplateId == 104) {
                    for (int k = 0; k < map.npcPlayers.size(); ++k) {
                        if (map.npcPlayers.get(k).template.playerTemplateId == 25) {
                            map.npcPlayers.get(k).template = ServerController.playerTemplates.get(32);
                        }
                    }
                }
                maps.add(map);
                if (map1 == null && mapTemplateId == 98) {
                    map1 = map;
                }
                if (map2 == null && mapTemplateId == 104) {
                    map2 = map;
                }
            }
        }
        goTestGTC(p1);
        goTestGTC(p2);
        GiaTocChien.gtcs.add(this);
        NJUtil.sendServer(p1.getSession(), "Mỗi bên có 5 phút để triệu hồi thành viên.");
        NJUtil.sendServer(p2.getSession(), "Mỗi bên có 5 phút để triệu hồi thành viên.");
    }

    public int getTime() {
        return timeLength - (int) (System.currentTimeMillis() / 1000L - timeStart);
    }

    public void playGTChien() {
        int timeNow = (int) (System.currentTimeMillis() / 1000L);
        if (step < 3 && timeNow > timeLength + timeStart) {
            timeStart = timeNow;
            timeLength = 3600;
            step = 3;
            for (Map map : maps) {
                for (int j = 0; j < map.players.size(); ++j) {
                    map.players.get(j).sendMapTime(getTime());
                }
            }
            sendAlert("Trận đấu giữa 2 gia tộc bắt đầu");
        } else if (step >= 3 && timeNow > timeLength + timeStart) {
            for (Map map : maps) {
                map.isStop = true;
            }
            GiaTocChien.gtcs.remove(this);
            int pointLeft = getPoint1();
            int pointRight = getPoint2();
            if (pointLeft >= pointRight) {
                Clan nameGt1 = this.nameGt1;
                nameGt1.coin += money - money / 20;
                if (money >= 100000) {
                    NJUtil.sendServerAlert("Gia tộc " + this.nameGt1.name + " đã chiến thắng gia tộc " + nameGt2.name + " trong gia tộc chiến.");
                }
                sendAlert("Trận đấu kết thúc, gia tộc " + this.nameGt1.name + " chiến thắng nhận được " + money + " xu.");
            } else {
                Clan nameGt2 = this.nameGt2;
                nameGt2.coin += money - money / 20;
                if (money >= 100000) {
                    NJUtil.sendServerAlert("Gia tộc " + this.nameGt2.name + " đã chiến thắng gia tộc " + nameGt1.name + " trong gia tộc chiến.");
                }
                sendAlert("Trận đấu kết thúc, gia tộc " + this.nameGt2.name + " chiến thắng nhận được " + money + " xu.");
            }
            money = 0;
        }
    }

    public void sendAlert(String str) {
        for (Map map : maps) {
            for (int j = 0; j < map.players.size(); ++j) {
                NJUtil.sendServer(map.players.get(j).getSession(), str);
            }
        }
    }

    public boolean goTestGTC(Player p) {
        if (nameLefts.contains(p.name)) {
            if (namePlayLefts.size() < 30 || namePlayLefts.contains(p.name)) {
                p.mapTemplateIdGo = 72;
                p.typePk = 4;
                p.updateTypePk();
                p.mapClear();
                p.map.goOutMap(p);
                p.x = map1.template.defaultX;
                p.y = map1.template.defaultY;
                p.sendMapTime(getTime());
                map1.waitGoInMap(p);
                p.sendChientruong(map1.giatocchien.getPointCT(p.name));
                if (!namePlayLefts.contains(p.name)) {
                    namePlayLefts.add(p.name);
                }
                return true;
            }
            NJUtil.sendDialog(p.getSession(), "Đã đủ số lượng 30 thành viên");
        } else if (nameRights.contains(p.name)) {
            if (namePlayRights.size() < 30 || namePlayRights.contains(p.name)) {
                p.mapTemplateIdGo = 72;
                p.typePk = 5;
                p.updateTypePk();
                p.mapClear();
                p.map.goOutMap(p);
                p.x = map2.template.defaultX;
                p.y = map2.template.defaultY;
                p.sendMapTime(getTime());
                map2.waitGoInMap(p);
                p.sendChientruong(map2.giatocchien.getPointCT(p.name));
                if (!namePlayRights.contains(p.name)) {
                    namePlayRights.add(p.name);
                }
            } else {
                NJUtil.sendDialog(p.getSession(), "Đã đủ số lượng 30 thành viên");
            }
        }
        return false;
    }

    public int getPointCT(String name) {
        try {
            points.putIfAbsent(name, 0);
            return points.get(name);
        } catch (Exception ex) {
            return 0;
        }
    }

    public void addPoint(String name, int point) {
        points.merge(name, point, Integer::sum);
    }

    public int getPoint1() {
        int point = 0;
        try {
            for (String name : points.keySet()) {
                try {
                    if (!nameLefts.contains(name)) {
                        continue;
                    }
                    point += points.get(name);
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex2) {
        }
        return point;
    }

    public int getPoint2() {
        int point = 0;
        try {
            for (String name : points.keySet()) {
                try {
                    if (!nameRights.contains(name)) {
                        continue;
                    }
                    point += points.get(name);
                } catch (Exception ex) {
                }
            }
        } catch (Exception ex2) {
        }
        return point;
    }

    public boolean addInvite(String name, Clan clan) {
        if (nameGt1.equals(clan) && !nameLefts.contains(name)) {
            nameLefts.add(name);
            return true;
        }
        if (nameGt2.equals(clan) && !nameRights.contains(name)) {
            nameRights.add(name);
            return true;
        }
        return false;
    }
}
