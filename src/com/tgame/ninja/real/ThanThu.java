package com.tgame.ninja.real;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemOption;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.util.Vector;

public class ThanThu extends Item {

    public static int[] expUpLevel = { 1000, 1000, 1000, 5000, 5000, 5000, 5000, 10000, 10000, 10000, 10000, 10000 };

    public static short[][] idIconItem = {
        { 2493, 2487, 2488, 2489 },
        { 2494, 2484, 2485, 2486 },
        { 2490, 2490, 2490, 2490 }
    };

    public static short[][] idBigIconItem = {
        { 2493, 2506, 2507, 2508 },
        { 2494, 2502, 2503, 2504 },
        { 2505, 2505, 2505, 2505 }
    };

    public static short[][] idItemThanThu = {
        { -1, 587, 588, 589 },
        { -1, 584, 585, 586 },
        { -1, 583, 583, 583 }
    };

    public long exp;

    public int currentExp;

    public short level;

    public byte pos;

    public byte lvThanthu;

    public int timeAp;

    public long time;

    public Item getItem() {
        if (lvThanthu == 0 || timeAp > -1) {
            return null;
        }
        Item it = new Item(ThanThu.idItemThanThu[template.itemTemplateId != 596 ? 1 : 0][lvThanthu]);
        it.isLock = true;
        it.options = new Vector<>();
        int[] dam = getDam();
        it.options.add(new ItemOption(dam[0], 102));
        it.options.add(new ItemOption(dam[1], 103));
        it.expires = System.currentTimeMillis() + 259200000L;
        return it;
    }

    public ThanThu() {
        exp = 0L;
        currentExp = 0;
        level = 1;
        pos = 0;
        lvThanthu = 0;
        timeAp = -1;
        time = System.currentTimeMillis();
    }

    public ThanThu(int i) {
        super(i);
        exp = 0L;
        currentExp = 0;
        level = 1;
        pos = 0;
        lvThanthu = 0;
        timeAp = -1;
        time = System.currentTimeMillis();
    }

    public void addExp(int exp) {
        this.exp += exp;
    }

    public int getLevel() {
        return level;
    }

    public int getIdIcon() {
        return template.iconId;
    }

    public String getInfoSave() {
        countTimeAp();
        return level + "," + lvThanthu + "," + exp + "," + currentExp + "," + template.itemTemplateId + "," + timeAp + "," + System.currentTimeMillis();
    }

    public void initInfo(String info) {
        try {
            String[] data = info.split(",");
            template = ServerController.itemTemplates.get(Integer.parseInt(data[4]));
            quantity = 1;
            expires = -1L;
            level = Short.parseShort(data[0]);
            lvThanthu = Byte.parseByte(data[1]);
            exp = Long.parseLong(data[2]);
            currentExp = Integer.parseInt(data[3]);
            timeAp = Integer.parseInt(data[5]);
            time = Long.parseLong(data[6]);
        } catch (Exception ex) {
        }
    }

    synchronized void countTimeAp() {
        if (timeAp > -1) {
            long t = System.currentTimeMillis() - time;
            timeAp -= (int) t;
            if (timeAp <= 0) {
                time = -1L;
                ++lvThanthu;
            }
            time = System.currentTimeMillis();
        }
    }

    public int doEat(Item item, Session s) {
        int id = item.template.itemTemplateId;
        if (id == 605) {
            if (lvThanthu >= 3) {
                NJUtil.sendServer(s, "Thần thú đã đạt cấp độ tối đa");
                return 0;
            }
            if ((lvThanthu == 1 && level < 30) || (lvThanthu == 2 && level < 70)) {
                NJUtil.sendServer(s, "Không đủ điều kiện để sử dụng");
                return 0;
            }
            int pc = 6;
            if (lvThanthu == 2 && level == 70) {
                pc = 3;
            }
            if (NJUtil.randomNumber(100) <= pc) {
                ++lvThanthu;
                if (lvThanthu > 3) {
                    lvThanthu = 3;
                }
                NJUtil.sendServer(s, "Chúc mừng thần thú " + getName(s) + " đã được tiến hoá lên " + lvThanthu + " sao");
                return 1;
            }
            NJUtil.sendServer(s, "Thăng cấp thất bại");
            return 2;
        } else {
            if (id != 598 && id != 599 && id != 600) {
                NJUtil.sendServer(s, "Không đủ điều kiện để sử dụng");
                return 0;
            }
            if (timeAp > -1) {
                long t = System.currentTimeMillis() - time;
                time = System.currentTimeMillis();
                timeAp -= (int) t;
                if (timeAp <= 0) {
                    time = -1L;
                    ++lvThanthu;
                }
                NJUtil.sendServer(s, "Không đủ điều kiện để sử dụng");
                return 0;
            }
            if (level >= 120) {
                NJUtil.sendServer(s, "Thần thú đã đạt cấp độ tối đa");
                return 0;
            }
            int[] exp = { 50, 150, 1000 };
            currentExp += exp[id - 598];
            if (currentExp >= ThanThu.expUpLevel[level / 10]) {
                currentExp -= ThanThu.expUpLevel[level / 10];
                ++level;
            }
            this.exp += exp[id - 598];
            return 1;
        }
    }

    public String getName(Session s) {
        String name = template.getName(s);
        if (lvThanthu > 0) {
            if (template.itemTemplateId == 596) {
                name = "Dị long cấp " + level;
            } else if (template.itemTemplateId == 601) {
                name = "Hải mã cấp " + level;
            }
        }
        return name;
    }

    public int[] getDam() {
        return new int[]{ lvThanthu * 30 * level * 5, lvThanthu * 30 * level };
    }

    public String getStar() {
        return new String(new char[Math.max(0, level)]).replace("\0", "*");
    }

    public void writeInfo2Client(Message msg, Session s) {
        try {
            countTimeAp();
            msg.writeUTF(getName(s));
            msg.writeShort(ThanThu.idIconItem[template.itemTemplateId != 596 ? 1 : 0][lvThanthu]);
            msg.writeShort(ThanThu.idBigIconItem[template.itemTemplateId != 596 ? 1 : 0][lvThanthu]);
            msg.writeInt(timeAp);
            if (timeAp > -1) {
                msg.writeByte(1);
                msg.writeUTF("Thời gian nở:");
            } else {
                int[] dam = getDam();
                msg.writeByte(2);
                msg.writeUTF("Sát thương quái: " + dam[0]);
                msg.writeUTF("Sát thương người: " + dam[1]);
                msg.writeInt(currentExp);
                msg.writeInt(ThanThu.expUpLevel[level / 10]);
            }
            msg.writeByte(lvThanthu);
        } catch (Exception ex) {
        }
    }
}
