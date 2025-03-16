package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.MixedArena;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class MapTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapTemplate.class);

    public static final int T_TOP = 2;

    public static final int T_LEFT = 4;

    public static final int T_RIGHT = 8;

    public static final int T_TREE = 16;

    public static final int T_WATERFALL = 32;

    public static final int T_WATERFLOW = 64;

    public static final int T_TOPFALL = 128;

    public static final int T_OUTSIDE = 256;

    public static final int T_BRIDGE = 1024;

    public static final int T_UNDERWATER = 2048;

    public static final int T_SOLIDGROUND = 4096;

    public static final int T_BOTTOM = 8192;

    public static final int T_DIE = 16384;

    public static final int T_HEBI = 32768;

    public static final int T_BANG = 65536;

    public static final int T_JUM8 = 131072;

    public static final int T_NT0 = 262144;

    public static final int T_NT1 = 524288;

    public static final short ID_MAP_ARENA = 149;

    public static final byte MAP_NORMAL = 0;

    public static final byte MAP_DAUTRUONG = 1;

    public static final byte MAP_PB = 2;

    public static final byte MAP_CHIENTRUONG = 3;

    public int tileId;

    public int mapTemplateId;

    public int bgId;

    public String name;

    public String name_en;

    public int w;

    public int h;

    public int tmw;

    public int tmh;

    public short defaultX;

    public short defaultY;

    public short[] maps;

    public int[] types;

    public int size;

    public int typeMap;

    public Vector<NLink> links;

    public byte[] allIndexStand;

    public Vector<InfoItemMap> itemBehind;

    public Vector<InfoItemMap> itemMiddle;

    public Vector<InfoItemMap> itemTop;

    public Hashtable<Short, Short> listImageInMap;

    public Vector<Map> ALL_REGION_MAP;

    public short ID_HASH_MAP;

    public boolean isFullTest;

    public MapTemplate(int mapTemplateId) {
        size = 24;
        links = new Vector<>();
        itemBehind = new Vector<>();
        itemMiddle = new Vector<>();
        itemTop = new Vector<>();
        listImageInMap = new Hashtable<>();
        ALL_REGION_MAP = new Vector<>();
        isFullTest = false;
        this.mapTemplateId = mapTemplateId;
        if (mapTemplateId == 75 || mapTemplateId == 76 || mapTemplateId == 77 || mapTemplateId == 79) {
            typeMap = MapTemplate.MAP_DAUTRUONG;
        } else if ((mapTemplateId >= 91 && mapTemplateId <= 97) || (mapTemplateId >= 105 && mapTemplateId <= 109) || (mapTemplateId >= 114 && mapTemplateId <= 116) || (mapTemplateId >= 125 && mapTemplateId <= 128) || (mapTemplateId >= 157 && mapTemplateId <= 159)) {
            typeMap = MapTemplate.MAP_PB;
        } else if ((mapTemplateId >= 98 && mapTemplateId <= 104) || (mapTemplateId >= 120 && mapTemplateId <= 124) || MixedArena.isMapArena(mapTemplateId)) {
            typeMap = MapTemplate.MAP_CHIENTRUONG;
        } else {
            typeMap = MapTemplate.MAP_NORMAL;
        }
        try (FileInputStream fis = new FileInputStream("data/" + mapTemplateId + "_item");
             DataInputStream dis = new DataInputStream(fis)
        ) {
            byte size = dis.readByte();
            for (int i = 0; i < size; ++i) {
                InfoItemMap info = new InfoItemMap(dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readUnsignedByte());
                itemBehind.add(info);
                listImageInMap.put(info.idItem, info.idItem);
            }
            size = dis.readByte();
            for (int i = 0; i < size; ++i) {
                InfoItemMap info = new InfoItemMap(dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readUnsignedByte());
                itemMiddle.add(info);
                listImageInMap.put(info.idItem, info.idItem);
            }
            size = dis.readByte();
            for (int i = 0; i < size; ++i) {
                InfoItemMap info = new InfoItemMap(dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readUnsignedByte());
                itemTop.add(info);
                listImageInMap.put(info.idItem, info.idItem);
            }
            dis.read(allIndexStand = new byte[dis.available()], 0, allIndexStand.length);
        } catch (Exception e) {
        }
    }

    public synchronized boolean checkFullZone() {
        if (isMapNotCreateZone()) {
            return false;
        }
        if (isFullTest) {
            isFullTest = false;
            return true;
        }
        Vector<Map> allmaps = ServerController.ALL_MAP.get(mapTemplateId);
        for (Map m : allmaps) {
            if (m.players.size() < m.maxPlayer - m.maxPlayer / 3) {
                return false;
            }
        }
        return allmaps.size() < 120;
    }

    public synchronized void createNewZoneMap() {
        boolean isCreate = true;
        for (int i = 0; i < Map.mapNotCreate.length; ++i) {
            if (mapTemplateId == Map.mapNotCreate[i]) {
                isCreate = false;
                break;
            }
        }
        if (isCreate && mapTemplateId != ID_MAP_ARENA) {
            Vector<Map> allmaps = ServerController.ALL_MAP.get(mapTemplateId);
            if (allmaps.size() >= 120) {
                return;
            }
            Map map = new Map(ServerController.maps.size(), allmaps.size(), this);
            Arrays.fill(map.longDenZoneIds, -1);
            allmaps.addElement(map);
            ServerController.maps.add(map);
            ServerController.ALL_MAP.put(mapTemplateId, allmaps);
        }
    }

    public synchronized short getIDHashMap() {
        short id_HASH_MAP = ID_HASH_MAP;
        ID_HASH_MAP = (short) (id_HASH_MAP + 1);
        return id_HASH_MAP;
    }

    public Map getMap(int id_hash_map) {
        for (Map map : ALL_REGION_MAP) {
            if (map.mapId == id_hash_map) {
                return map;
            }
        }
        return null;
    }

    public Map getMapJoin() {
        //for (int i = 0; i < ALL_REGION_MAP.size(); ++i) {}
        int idMap = getIDHashMap();
        Map map = new Map(idMap, idMap, this);
        Arrays.fill(map.longDenZoneIds, -1);
        ALL_REGION_MAP.add(map);
        return map;
    }

    public String[] getName() {
        return new String[]{ name, name_en };
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return name;
        }
        return name_en;
    }

    public boolean isMapNotCreateZone() {
        return mapTemplateId == 134 || mapTemplateId == 135 || mapTemplateId == 136 || mapTemplateId == 137;
    }

    public void loadData() {
        byte[] data = NJUtil.readFileBytes("data/map/" + mapTemplateId);
        if (data != null) {
            tmw = ((data[0] < 0) ? (data[0] + 256) : data[0]);
            tmh = ((data[1] < 0) ? (data[1] + 256) : data[1]);
            w = tmw * size;
            h = tmh * size;
            maps = new short[data.length - 2];
            int index = 0;
            for (int i = 2; i < data.length; ++i) {
                if (data[i] < 0) {
                    maps[index] = (short) (data[i] + 256);
                } else {
                    maps[index] = data[i];
                }
                ++index;
            }
            setType();
        }
        if (GameServer.isServerLocal()) {
            Arrays.fill(Map.maxZones, 30);
        }
        boolean isCreate = true;
        for (int k = 0; k < Map.mapNotCreate.length; ++k) {
            if (mapTemplateId == Map.mapNotCreate[k]) {
                isCreate = false;
                break;
            }
        }
        if (isCreate && mapTemplateId != ID_MAP_ARENA) {
            if (Map.maxZones[mapTemplateId] > 20) {
                Map.maxZones[mapTemplateId] = 30;
            }
            Vector<Map> allmaps = new Vector<>();
            for (int i = 0; i < Map.maxZones[mapTemplateId]; ++i) {
                Map map = new Map(ServerController.maps.size(), i, this);
                Arrays.fill(map.longDenZoneIds, -1);
                allmaps.addElement(map);
                ServerController.maps.add(map);
            }
            ServerController.ALL_MAP.put(mapTemplateId, allmaps);
        }
    }

    public void setType() {
        int length = tmw * tmh;
        try {
            types = new int[maps.length];
            Hashtable<Integer, String> posstand = new Hashtable<>();
            try (ByteArrayInputStream b = new ByteArrayInputStream(allIndexStand);
                 DataInputStream msg = new DataInputStream(b)
            ) {
                for (int size = msg.readUnsignedByte(), i = 0; i < size; ++i) {
                    int xObject = msg.readUnsignedByte();
                    int yObject = msg.readUnsignedByte();
                    int ppp = yObject * tmw + xObject;
                    posstand.put(ppp, "1");
                }
            } catch (Exception e) {
            }
            for (int j = 0; j < length; ++j) {
                if (posstand.get(j) != null) {
                    types[j] |= 0x2;
                }
                if (tileId == 1) {
                    if (maps[j] == 1 || maps[j] == 2 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 7 || maps[j] == 36 || maps[j] == 37 || maps[j] == 54 || maps[j] == 91 || maps[j] == 92 || maps[j] == 93 || maps[j] == 94 || maps[j] == 73 || maps[j] == 74 || maps[j] == 97 || maps[j] == 98 || maps[j] == 116 || maps[j] == 117 || maps[j] == 118 || maps[j] == 120 || maps[j] == 61) {
                        types[j] |= T_TOP;
                    }
                    if (maps[j] == 2 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 20 || maps[j] == 21 || maps[j] == 22 || maps[j] == 23 || maps[j] == 36 || maps[j] == 37 || maps[j] == 38 || maps[j] == 39 || maps[j] == 61) {
                        types[j] |= T_SOLIDGROUND;
                    }
                    if (maps[j] == 8 || maps[j] == 9 || maps[j] == 10 || maps[j] == 12 || maps[j] == 13 || maps[j] == 14 || maps[j] == 30) {
                        types[j] |= T_TREE;
                    }
                    if (maps[j] == 17) {
                        types[j] |= T_WATERFALL;
                    }
                    if (maps[j] == 18) {
                        types[j] |= T_TOPFALL;
                    }
                    if (maps[j] == 37 || maps[j] == 38 || maps[j] == 61) {
                        types[j] |= T_LEFT;
                    }
                    if (maps[j] == 36 || maps[j] == 39 || maps[j] == 61) {
                        types[j] |= T_RIGHT;
                    }
                    if (maps[j] == 19) {
                        types[j] |= T_WATERFLOW;
                        if ((types[j - tmw] & T_SOLIDGROUND) == T_SOLIDGROUND) {
                            types[j] |= T_SOLIDGROUND;
                        }
                    }
                    if (maps[j] == 35) {
                        types[j] |= T_UNDERWATER;
                    }
                    if (maps[j] == 7) {
                        types[j] |= T_BRIDGE;
                    }
                    if (maps[j] == 32 || maps[j] == 33 || maps[j] == 34) {
                        types[j] |= T_OUTSIDE;
                    }
                }
                if (tileId == 2) {
                    if (maps[j] == 1 || maps[j] == 2 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 7 || maps[j] == 36 || maps[j] == 37 || maps[j] == 54 || maps[j] == 61 || maps[j] == 73 || maps[j] == 74 || maps[j] == 76 || maps[j] == 77 || maps[j] == 78 || maps[j] == 79 || maps[j] == 82 || maps[j] == 83 || maps[j] == 98 || maps[j] == 99 || maps[j] == 100 || maps[j] == 102 || maps[j] == 103 || maps[j] == 108 || maps[j] == 109 || maps[j] == 110 || maps[j] == 112 || maps[j] == 113 || maps[j] == 116 || maps[j] == 117 || maps[j] == 125 || maps[j] == 126 || maps[j] == 127 || maps[j] == 129 || maps[j] == 130) {
                        types[j] |= T_TOP;
                    }
                    if (maps[j] == 1 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 20 || maps[j] == 21 || maps[j] == 22 || maps[j] == 23 || maps[j] == 36 || maps[j] == 37 || maps[j] == 38 || maps[j] == 39 || maps[j] == 55 || maps[j] == 109 || maps[j] == 111 || maps[j] == 112 || maps[j] == 113 || maps[j] == 114 || maps[j] == 115 || maps[j] == 116 || maps[j] == 127 || maps[j] == 129 || maps[j] == 130) {
                        types[j] |= T_SOLIDGROUND;
                    }
                    if (maps[j] == 8 || maps[j] == 9 || maps[j] == 10 || maps[j] == 12 || maps[j] == 13 || maps[j] == 14 || maps[j] == 30 || maps[j] == 135) {
                        types[j] |= T_TREE;
                    }
                    if (maps[j] == 17) {
                        types[j] |= T_WATERFALL;
                    }
                    if (maps[j] == 18) {
                        types[j] |= T_TOPFALL;
                    }
                    if (maps[j] == 61 || maps[j] == 37 || maps[j] == 38 || maps[j] == 127 || maps[j] == 130 || maps[j] == 131) {
                        types[j] |= T_LEFT;
                    }
                    if (maps[j] == 61 || maps[j] == 36 || maps[j] == 39 || maps[j] == 127 || maps[j] == 129 || maps[j] == 132) {
                        types[j] |= T_RIGHT;
                    }
                    if (maps[j] == 19) {
                        types[j] |= T_WATERFLOW;
                        if ((types[j - tmw] & T_SOLIDGROUND) == T_SOLIDGROUND) {
                            types[j] |= T_SOLIDGROUND;
                        }
                    }
                    if (maps[j] == 134) {
                        types[j] |= T_WATERFLOW;
                        if ((types[j - tmw] & T_SOLIDGROUND) == T_SOLIDGROUND) {
                            types[j] |= T_SOLIDGROUND;
                        }
                    }
                    if (maps[j] == 35) {
                        types[j] |= T_UNDERWATER;
                    }
                    if (maps[j] == 7) {
                        types[j] |= T_BRIDGE;
                    }
                    if (maps[j] == 32 || maps[j] == 33 || maps[j] == 34) {
                        types[j] |= T_OUTSIDE;
                    }
                    if (maps[j] == 61 || maps[j] == 127) {
                        types[j] |= T_BOTTOM;
                    }
                }
                if (tileId == 3) {
                    if (maps[j] == 1 || maps[j] == 2 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 7 || maps[j] == 11 || maps[j] == 14 || maps[j] == 17 || maps[j] == 43 || maps[j] == 51 || maps[j] == 63 || maps[j] == 65 || maps[j] == 67 || maps[j] == 68 || maps[j] == 71 || maps[j] == 72 || maps[j] == 83 || maps[j] == 84 || maps[j] == 85 || maps[j] == 87 || maps[j] == 91 || maps[j] == 94 || maps[j] == 97 || maps[j] == 98 || maps[j] == 106 || maps[j] == 107 || maps[j] == 111 || maps[j] == 113 || maps[j] == 117 || maps[j] == 118 || maps[j] == 119 || maps[j] == 125 || maps[j] == 126 || maps[j] == 129 || maps[j] == 130 || maps[j] == 131 || maps[j] == 133 || maps[j] == 136 || maps[j] == 138 || maps[j] == 139 || maps[j] == 142) {
                        types[j] |= T_TOP;
                    }
                    if (maps[j] == 124 || maps[j] == 116 || maps[j] == 123 || maps[j] == 44 || maps[j] == 12 || maps[j] == 15 || maps[j] == 16 || maps[j] == 45 || maps[j] == 10 || maps[j] == 9) {
                        types[j] |= T_SOLIDGROUND;
                    }
                    if (maps[j] == 23) {
                        types[j] |= T_WATERFALL;
                    }
                    if (maps[j] == 24) {
                        types[j] |= T_TOPFALL;
                    }
                    if (maps[j] == 6 || maps[j] == 15 || maps[j] == 51 || maps[j] == 95 || maps[j] == 97 || maps[j] == 106 || maps[j] == 111 || maps[j] == 123 || maps[j] == 125 || maps[j] == 138 || maps[j] == 140) {
                        types[j] |= T_LEFT;
                    }
                    if (maps[j] == 7 || maps[j] == 16 || maps[j] == 51 || maps[j] == 96 || maps[j] == 98 || maps[j] == 107 || maps[j] == 111 || maps[j] == 124 || maps[j] == 126 || maps[j] == 139 || maps[j] == 141) {
                        types[j] |= T_RIGHT;
                    }
                    if (maps[j] == 25) {
                        types[j] |= T_WATERFLOW;
                        if ((types[j - tmw] & T_SOLIDGROUND) == T_SOLIDGROUND) {
                            types[j] |= T_SOLIDGROUND;
                        }
                    }
                    if (maps[j] == 34) {
                        types[j] |= T_UNDERWATER;
                    }
                    if (maps[j] == 17) {
                        types[j] |= T_BRIDGE;
                    }
                    if (maps[j] == 103 || maps[j] == 104 || maps[j] == 105 || maps[j] == 26 || maps[j] == 33) {
                        types[j] |= T_OUTSIDE;
                    }
                    if (maps[j] == 51 || maps[j] == 111 || maps[j] == 68) {
                        types[j] |= T_BOTTOM;
                    }
                    if (maps[j] == 82 || maps[j] == 110 || maps[j] == 143) {
                        types[j] |= T_DIE;
                    }
                    if (maps[j] == 113) {
                        types[j] |= T_BANG;
                    }
                    if (maps[j] == 142) {
                        types[j] |= T_HEBI;
                    }
                    if (maps[j] == 40 || maps[j] == 41) {
                        types[j] |= T_JUM8;
                    }
                    if (maps[j] == 110) {
                        types[j] |= T_NT0;
                    }
                    if (maps[j] == 143) {
                        types[j] |= T_NT1;
                    }
                }
                if (tileId == 4) {
                    if (maps[j] == 1 || maps[j] == 2 || maps[j] == 3 || maps[j] == 4 || maps[j] == 5 || maps[j] == 6 || maps[j] == 9 || maps[j] == 10 || maps[j] == 79 || maps[j] == 80 || maps[j] == 13 || maps[j] == 14 || maps[j] == 43 || maps[j] == 44 || maps[j] == 45 || maps[j] == 50) {
                        types[j] |= T_TOP;
                    }
                    if (maps[j] == 9 || maps[j] == 11) {
                        types[j] |= T_LEFT;
                    }
                    if (maps[j] == 10 || maps[j] == 12) {
                        types[j] |= T_RIGHT;
                    }
                    if (maps[j] == 13 || maps[j] == 14) {
                        types[j] |= T_BRIDGE;
                    }
                    if (maps[j] == 76 || maps[j] == 77) {
                        types[j] |= T_WATERFLOW;
                    }
                    if (maps[j] == 78) {
                        types[j] |= T_DIE;
                    }
                    if (maps[j] == 76 || maps[j] == 77) {
                        types[j] |= T_WATERFLOW;
                        if ((types[j - tmw] & T_SOLIDGROUND) == T_SOLIDGROUND) {
                            types[j] |= T_SOLIDGROUND;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public final boolean tileTypeAt(int px, int py, int t) {
        try {
            int tt = py / size * tmw + px / size;
            return (types[tt] & t) == t;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMapChienTruong() {
        return typeMap == MAP_CHIENTRUONG;
    }
}
