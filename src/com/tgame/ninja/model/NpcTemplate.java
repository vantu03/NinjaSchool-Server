package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class NpcTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(NpcTemplate.class);

    public int npcTemplateId;

    public String name;

    public String name_en;

    public int hp;

    public int level;

    public int dame;

    public int exp;

    public int rangeMove;

    public int rangeAttack;

    public int type;

    public int speed;

    public int waitAttack;

    public int sysUp;

    public int sysDown;

    public Frame[] frames;

    public byte[] arr;

    public byte[] arr1;

    public byte[][] arr2;

    public boolean isLoad;

    public NpcTemplate() {
        frames = new Frame[6];
        isLoad = true;
    }

    public void loadData() {
        int imgId = 1;
        try {
            for (int i = 0; i < frames.length; ++i) {
                frames[i] = new Frame();
                frames[i].datas = new Vector<>();
            }
            if (isBossId() || isPetNew()) {
                arr = NJUtil.readFileBytes("data/npcX1/" + npcTemplateId + "/1");
                switch (npcTemplateId) {
                    case 114:
                    case 116:
                        arr1 = new byte[] { 1, 2, 3, 2 };
                        arr2 = new byte[][] { { 4, 5, 6, 3 }, { 7, 8, 9, 3 } };
                        break;
                    case 115:
                        arr1 = new byte[] { 1, 2, 3, 2, 1, 2, 3 };
                        arr2 = new byte[][] { { 4, 5, 6, 3 }, { 7, 8, 9, 3 } };
                        break;
                    case 138:
                        arr1 = new byte[] { 1, 2, 3, 2 };
                        arr2 = new byte[][] { { 4, 5, 5, 5, 6, 3 }, { 7, 8, 9, 3 } };
                        break;
                    case 139:
                        arr1 = new byte[] { 1, 2, 3, 2 };
                        arr2 = new byte[][] { { 7, 8, 9, 10, 11, 3 }, { 4, 5, 6, 3 } };
                        break;
                    case 140:
                    case 161:
                        arr1 = new byte[] { 1, 2, 3 };
                        arr2 = new byte[][] { { 4, 5, 6, 3 }, { 7, 8, 9, 10 } };
                        break;
                    case 141:
                        arr1 = new byte[] { 3, 4, 5 };
                        arr2 = new byte[][] { { 6, 7, 8, 9 }, { 9, 10, 11, 12 } };
                        break;
                    case 144:
                        arr1 = new byte[] { 0, 1 };
                        arr2 = new byte[][] { { 2, 3, 4, 2 }, { 5, 6, 7, 8 } };
                        break;
                    case 160:
                        arr1 = new byte[] { 2, 3, 4, 5, 6 };
                        arr2 = new byte[][] { { 7, 8, 10, 11 }, { 7, 8, 9 } };
                        break;
                    case 162:
                        arr1 = new byte[] { 3, 4, 5 };
                        arr2 = new byte[][] { { 6, 7, 8, 9 }, { 9, 10, 11, 12 } };
                        break;
                    case 163:
                        arr1 = new byte[] { 0, 1 };
                        arr2 = new byte[][] { { 2, 3, 4, 2 }, { 5, 6, 7, 8 } };
                        break;
                    case 164:
                        arr1 = new byte[] { 2, 3, 2, 1, 2, 6, 2 };
                        arr2 = new byte[][] { { 4, 5, 6 }, { 7, 8, 9 } };
                        break;
                    case 165:
                        arr1 = new byte[] { 2, 3, 2, 1, 2, 6, 2 };
                        arr2 = new byte[][] { { 4, 5, 6 }, { 7, 8, 9 } };
                        break;
                    case 166:
                        arr1 = new byte[] { 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
                        arr2 = new byte[][] { { 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10, 11, 12 } };
                        break;
                    case 167:
                        arr1 = new byte[] { 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
                        arr2 = new byte[][] { { 7, 8, 9, 10, 11, 12 }, { 7, 8, 9, 10, 11, 12 } };
                        break;
                    case 198:
                        arr1 = new byte[] { 1, 1, 2, 2, 2, 3, 3, 2, 2 };
                        arr2 = new byte[][] { { 1, 4, 5, 6 }, { 2, 7, 8, 9 } };
                        break;
                    case 199:
                        arr1 = new byte[] { 1, 1, 2, 2, 2, 3, 3, 2, 2 };
                        arr2 = new byte[][] { { 1, 4, 5, 6 }, { 2, 7, 8, 9 } };
                        break;
                    case 200:
                        arr1 = new byte[] { 1, 1, 2, 2, 2, 3, 3, 2, 2 };
                        arr2 = new byte[][] { { 1, 4, 5, 6 }, { 2, 7, 8, 9 } };
                        break;
                    case 201:
                        arr1 = new byte[] { 1, 1, 2, 2, 3, 3 };
                        arr2 = new byte[][] { { 4, 5, 6, 7 }, { 4, 8, 9 } };
                        break;
                    case 203:
                        arr1 = new byte[] { 1, 1, 2, 2, 3, 3 };
                        arr2 = new byte[][] { { 4, 5, 6, 7 }, { 4, 8, 9 } };
                        break;
                    case 204:
                        arr1 = new byte[] { 1, 1, 2, 2, 3, 3 };
                        arr2 = new byte[][] { { 4, 5, 6, 7 }, { 4, 8, 9 } };
                        break;
                    case 209:
                        arr1 = new byte[] { 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
                        arr2 = new byte[][] { { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 }, { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 } };
                        break;
                    case 210:
                        arr1 = new byte[] { 1, 1, 1, 2, 2, 2, 3, 3, 2, 2 };
                        arr2 = new byte[][] { { 4, 5, 6, 7 }, { 7, 7, 8, 8, 9 } };
                        break;
                    case 211:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { { 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11 }, { 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11 } };
                        break;
                    case 212:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { new byte[1], new byte[1] };
                        break;
                    case 213:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { { 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 }, { 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 } };
                        break;
                    case 214:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { { 3, 3, 4, 4, 5, 5, 6, 6 }, { 3, 3, 4, 4, 5, 5, 6, 6 } };
                        break;
                    case 215:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { new byte[1], new byte[1] };
                        break;
                    case 216:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2, 1, 1 };
                        arr2 = new byte[][] { { 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 }, { 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 } };
                        break;
                    case 217:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2, 1, 1 };
                        arr2 = new byte[][] { { 3, 3, 4, 4, 5, 5, 6, 6 }, { 3, 3, 4, 4, 5, 5, 6, 6 } };
                        break;
                    case 218:
                        arr1 = new byte[] { 2, 2, 3, 3, 4, 4, 3, 3 };
                        arr2 = new byte[][] { { 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8 }, { 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12 } };
                        break;
                    case 220:
                        arr1 = new byte[] { 3, 4, 5, 4 };
                        arr2 = new byte[][] { { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 }, { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16 } };
                        break;
                    case 221:
                        arr1 = new byte[] { 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5 };
                        arr2 = new byte[][] { { 0, 0, 0, 6, 6, 6, 7, 7, 7 }, { 0, 0, 0, 6, 6, 6, 7, 7, 7 } };
                        break;
                    case 222:
                        arr1 = new byte[] { 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7 };
                        arr2 = new byte[][] { { 8, 8, 9, 9, 11, 11, 12, 12, 13, 13, 14, 14 }, { 8, 8, 9, 9, 11, 11, 12, 12, 13, 13, 14, 14 } };
                        break;
                    case 223:
                        arr1 = new byte[] { 0, 1, 2 };
                        arr2 = new byte[][] { { 0, 6, 7, 8, 7, 8, 7, 8, 9, 11, 12, 13 }, { 0, 6, 7, 8, 7, 8, 7, 8, 9, 11, 12, 13 } };
                        break;
                    case 224:
                        arr1 = new byte[3];
                        arr2 = new byte[][] { new byte[3], { 10, 10, 10 } };
                        break;
                    case 225:
                        arr1 = new byte[] { 0, 0, 0, 1, 1, 1, 2, 2, 2 };
                        arr2 = new byte[][] { { 0, 0, 0, 1, 1, 1, 2, 2, 2 }, { 10, 10, 10 } };
                        break;
                    case 226:
                        arr1 = new byte[] { 2, 2, 3, 3, 4, 4, 3, 3 };
                        arr2 = new byte[][] { { 5, 5, 8, 8, 11, 11, 12, 12, 13, 13 }, { 5, 5, 8, 8, 11, 11, 12, 12, 13, 13 } };
                        break;
                    case 227:
                        arr1 = new byte[] { 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6 };
                        arr2 = new byte[][] { { 1, 1, 8, 8, 9, 9, 11, 11, 12, 12, 12, 12 }, { 10, 10, 10 } };
                        break;
                    case 228:
                        arr1 = new byte[] { 2, 3, 2, 1, 2, 6, 2 };
                        arr2 = new byte[][] { { 4, 5, 6 }, { 7, 8, 9 } };
                        break;
                    case 229:
                        arr1 = new byte[] { 0, 0, 1, 1, 2, 2 };
                        arr2 = new byte[][] { { 0, 0, 1, 1, 2, 2 }, { 0, 0, 1, 1, 2, 2 } };
                        break;
                    case 230:
                        arr1 = new byte[] { 0, 1, 2 };
                        arr2 = new byte[][] { { 3, 4, 5, 6, 7 }, { 3, 4, 5, 6, 7 } };
                        break;
                    case 231:
                        arr1 = new byte[] { 1, 1, 2, 2, 3, 3 };
                        arr2 = new byte[][] { { 4, 4, 5, 5, 6, 6, 7, 7 }, { 4, 4, 5, 5, 6, 6, 7, 7 } };
                        break;
                    case 232:
                        arr1 = new byte[] { 0, 0, 1, 1 };
                        arr2 = new byte[][] { { 3, 5, 6, 7, 8, 9, 5 }, { 3, 5, 6, 7, 8, 9, 5 } };
                        break;
                    case 234:
                        arr1 = new byte[] { 0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1 };
                        arr2 = new byte[][] { { 0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1 }, { 0, 0, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1 } };
                        break;
                    case 235:
                        arr1 = new byte[] { 0, 0, 1, 1 };
                        arr2 = new byte[][] { { 0, 0, 1, 1 }, { 0, 0, 1, 1 } };
                        break;
                }
            } else {
                arr = new byte[0];
            }
            while (true) {
                byte[] dataHD1 = NJUtil.readFileBytes("data/npcX1/" + npcTemplateId + "/" + imgId + ".png");
                byte[] dataHD2 = NJUtil.readFileBytes("data/npcX2/" + npcTemplateId + "/" + imgId + ".png");
                byte[] dataHD3 = NJUtil.readFileBytes("data/npcX3/" + npcTemplateId + "/" + imgId + ".png");
                byte[] dataHD4 = NJUtil.readFileBytes("data/npcX4/" + npcTemplateId + "/" + imgId + ".png");
                byte[] dataHDPC = NJUtil.readFileBytes("data/npcXPC/" + npcTemplateId + "/" + imgId + ".png");
                if (dataHD1 == null || dataHD2 == null || dataHD3 == null || dataHD4 == null || dataHDPC == null) {
                    break;
                }
                frames[0].datas.add(dataHD1);
                frames[1].datas.add(dataHD2);
                frames[2].datas.add(dataHD3);
                frames[3].datas.add(dataHD4);
                frames[4].datas.add(dataHDPC);
                ++imgId;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public Vector<byte[]> loadData(int x) {
        int imgId = 1;
        if (!isLoad) {
            //noinspection SynchronizeOnNonFinalField
            synchronized (frames) {
                if (isLoad) {
                    if (x == 4) {
                        return frames[4].datas;
                    }
                    return frames[x].datas;
                } else {
                    try {
                        while (true) {
                            byte[] data;
                            if (x == 4) {
                                data = NJUtil.readFileBytes("data/npcXPC/" + npcTemplateId + "/" + imgId + ".png");
                                if (data == null) {
                                    break;
                                }
                                frames[4].datas.add(data);
                            } else {
                                data = NJUtil.readFileBytes("data/npcX" + (x + 1) + "/" + npcTemplateId + "/" + imgId + ".png");
                                if (data == null) {
                                    break;
                                }
                                frames[x].datas.add(data);
                            }
                            ++imgId;
                        }
                    } catch (Exception e) {
                        LOGGER.error("", e);
                    }
                    isLoad = true;
                }
            }
        }
        if (x == 4) {
            return frames[4].datas;
        }
        return frames[x].datas;
    }

    public String getName(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return name;
        }
        return name_en;
    }

    public boolean isBossId() {
        return NJUtil.inArrayInt(new int[]{
            114, 115, 116, 138, 139, 140, 141, 144, 160, 161,
            162, 163, 164, 165, 166, 167, 198, 199, 200, 201,
            203, 204, 209, 210, 218, 220, 221, 222, 223, 224,
            225, 226, 227, 228, 230, 231, 232
        }, npcTemplateId);
    }

    public boolean isBossEventId() {
        return false;
    }

    public boolean isModTool() {
        return isBossId() || isPetNew();
    }

    public boolean isPetNew() {
        return (npcTemplateId >= 211 && npcTemplateId <= 217) || npcTemplateId == 229 || npcTemplateId == 234 || npcTemplateId == 235;
    }
}
