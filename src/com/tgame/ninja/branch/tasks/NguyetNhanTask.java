package com.tgame.ninja.branch.tasks;

import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.ItemTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.ServerController;
import com.tgame.ninja.server.NJUtil;
import java.util.ArrayList;
import java.util.Vector;

public class NguyetNhanTask {

    public static byte TYPE_NANG_CAP = 0;

    public static byte TYPE_GIET_QUAI = 1;

    public static byte TYPE_KIEM_DIEM_CHIEN_TRUONG = 2;

    public static byte TYPE_CHIEN_THANG_LOI_DAI = 3;

    public static byte TYPE_TIEU_DIET_TINH_ANH = 4;

    public static byte TYPE_TIEU_DIET_THU_LINH = 5;

    public static final int MAX_RECIEVE = 20;

    public static final int MAX_EXTEND = 6;

    public static final int EXTEND_RECEIVE = 5;

    public byte type;

    public byte id_language;

    public int step;

    public int step_min;

    public int step_max;

    public int finish_step;

    public int id;

    public int idItem;

    public String name;

    public String content;

    public static ArrayList<NguyetNhanTask> alltask;

    public static int[] MONEY_UPDGRADE_DA_DANH_VONG = { 0, 50000, 75000, 125000, 200000, 400000, 700000, 1200000, 1800000, 2500000 };

    public static int[] MONEY_UPDGRADE_NGUYET_NHAN = { 0, 500000, 750000, 1250000, 2000000, 4000000, 7000000, 12000000, 18000000, 25000000 };

    public static int[] MONEY_UPDGRADE_NGUYET_NHAN_LUONG = { 0, 50, 60, 85, 100, 120, 150, 200, 300, 500 };

    public static int[] POINT_DANH_VONG_UPDGRADE = { 0, 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000 };

    public static int[] PC_UPDGRADE_NGUYET_NHAN = { 0, 50, 35, 25, 20, 15, 10, 7, 3, 1 };

    public static int[] ID_DA_DANH_VONG = { 0, 695, 696, 697, 698, 699, 700, 701, 702, 703, 704 };

    public static int[][] ID_WEAPONE_TEMPLATE = {
        new int[0],
        { 94, 95, 96, 97, 98, 331, 369, 506, 632 },
        { 114, 115, 116, 117, 118, 332, 370, 507, 633 },
        { 99, 100, 101, 102, 103, 333, 371, 508, 634 },
        { 109, 110, 111, 112, 113, 334, 372, 509, 635 },
        { 104, 105, 106, 107, 108, 335, 373, 510, 636 },
        { 119, 120, 121, 122, 123, 336, 374, 511, 637 }
    };

    public int[][] ID_ARMOR_TEMPLATE;

    public static void loadInfoQuest() {
        NguyetNhanTask.alltask = new ArrayList<>();
        if (GameServer.isSvEnglish()) {
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Upgrade equipment", "- Upgrade # to level @", 0, 0, 1, 8));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Defeat monster", "- Use #.\n- Defeat @ monsters within 10 levels.", 1, 1, 100, 500));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Collect battlefield point", "- Use #.\n- Earn @ battlefield points.", 2, 2, 100, 1000));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Win battle", "- Use #.\n- Win @ battle matches", 3, 3, 1, 10));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Defeat Elies monster", "- Use item #.\n- Defeat @ elites monsters within 10 levels.", 4, 4, 1, 5));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Defeat Chief monster", "- Use #.\n- Defeat @ chief monsters within 10 levels.", 5, 5, 1, 3));
        } else {
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Nâng cấp trang bị", "- Nâng cấp trang bị # lên cấp @", 0, 0, 1, 8));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Tiêu diệt quái", "- Sử dụng trang bị #.\n- Tiêu diệt @ quái lệch 10 cấp độ.", 1, 1, 100, 500));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Kiếm điểm chiến trường", "- Sử dụng trang bị #.\n- Đạt @ điểm chiến trường", 2, 2, 100, 1000));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Chiến thắng lôi đài", "- Sử dụng trang bị #.\n- Chiến thắng @ trận lôi đài", 3, 3, 1, 10));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Tiêu diệt tinh anh", "- Sử dụng trang bị #.\n- Tiêu diệt @ quái tinh anh lệch 10 cấp độ.", 4, 4, 1, 5));
            NguyetNhanTask.alltask.add(new NguyetNhanTask("Tiêu diệt thủ lĩnh", "- Sử dụng trang bị #.\n- Tiêu diệt @ quái thủ lĩnh lệch 10 cấp độ.", 5, 5, 1, 3));
        }
    }

    public void checkStepQuest(Player player, int type, int point, Item it) {
        if (this.type == type) {
            ItemTemplate template = ServerController.itemTemplates.get(idItem);
            Item itcheck = player.itemBodys[template.type];
            if (type == NguyetNhanTask.TYPE_NANG_CAP) {
                itcheck = it;
            }
            if (itcheck != null && itcheck.template.itemTemplateId == idItem) {
                if (step < finish_step) {
                    if (type == NguyetNhanTask.TYPE_NANG_CAP) {
                        step = point;
                    } else {
                        step += point;
                    }
                }
                if (step >= finish_step) {
                    if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(player.getSession(), "Hoàn thành nhiệm vụ, hãy gặp # để trả nhiệm vụ".replace("#", "Ameji"));
                    } else {
                        NJUtil.sendServer(player.getSession(), "Task completed, meet # to end the task".replace("#", "Ameji"));
                    }
                } else {
                    NguyetNhanTask temp = NguyetNhanTask.alltask.get(id);
                    NJUtil.sendServer(player.getSession(), temp.content.replace("#", template.getName(player.getSession())).replace("@", step + "/" + finish_step));
                }
            }
        }
    }

    public static int getIDTemplateQuest(Player p) {
        Vector<Integer> allid = Database.getIDItemNhiemVuNguyetNhan(p);
        if (allid != null) {
            for (int i = 0; i < NguyetNhanTask.ID_WEAPONE_TEMPLATE[p.classId].length; ++i) {
                allid.add(NguyetNhanTask.ID_WEAPONE_TEMPLATE[p.classId][i]);
            }
            int idtemplate = allid.get(NJUtil.random.nextInt(allid.size()));
            NguyetNhanTask temp = NguyetNhanTask.alltask.get(NJUtil.random.nextInt(NguyetNhanTask.alltask.size()));
            p.nguyetNhanTask = new NguyetNhanTask(temp, idtemplate, p.getSession());
            return idtemplate;
        }
        return -1;
    }

    public boolean isFinish() {
        return step >= finish_step;
    }

    public boolean doAddGiftQuest(Player p) {
        return false;
    }

    public NguyetNhanTask(NguyetNhanTask template, int idtemplate, Session s) {
        id_language = 0;
        step = 0;
        name = "";
        content = "";
        ID_ARMOR_TEMPLATE = new int[][]{ new int[0], new int[0], new int[0], new int[0], new int[0], new int[0] };
        id = template.id;
        type = template.type;
        finish_step = template.step_min + NJUtil.random.nextInt(template.step_max + 1);
        idItem = idtemplate;
        ItemTemplate itTemplate = ServerController.itemTemplates.get(idtemplate);
        name = template.name;
        step = 0;
        id_language = s.typeLanguage;
        content = template.content.replace("#", itTemplate.getName(s)).replace("@", step + "/" + finish_step);
    }

    public NguyetNhanTask(int idLanguage, int idNguyenNhanTask, int idtemplate, int finish_step, int step) {
        id_language = 0;
        this.step = 0;
        name = "";
        content = "";
        ID_ARMOR_TEMPLATE = new int[][]{ new int[0], new int[0], new int[0], new int[0], new int[0], new int[0] };
        NguyetNhanTask template = NguyetNhanTask.alltask.get(idNguyenNhanTask);
        id = template.id;
        type = template.type;
        idItem = idtemplate;
        ItemTemplate itTemplate = ServerController.itemTemplates.get(idtemplate);
        name = template.name;
        this.finish_step = finish_step;
        this.step = step;
        id_language = (byte) idLanguage;
        content = template.content.replace("#", itTemplate.getName()[idLanguage]).replace("@", step + "/" + finish_step);
    }

    public String getInfoSave() {
        return id + "#" + idItem + "#" + finish_step + "#" + step + "#" + id_language;
    }

    public String[] getInfoSend2Client(Session s) {
        String[] info = { s.typeLanguage == GameServer.LANG_VI ? "Nhiệm vụ" : "Quest", name };
        NguyetNhanTask template = NguyetNhanTask.alltask.get(id);
        ItemTemplate itTemplate = ServerController.itemTemplates.get(idItem);
        info[1] = info[1] + "\n" + template.content.replace("#", itTemplate.getName(s)).replace("@", step + "/" + finish_step);
        if (step >= finish_step) {
            info[1] = info[1] + "\n- " + "Hoàn thành nhiệm vụ, hãy gặp # để trả nhiệm vụ".replace("#", "Ameji");
        }
        return info;
    }

    public NguyetNhanTask(String name, String content, int id, int type, int step_min, int step_max) {
        id_language = 0;
        step = 0;
        this.name = "";
        this.content = "";
        ID_ARMOR_TEMPLATE = new int[][]{ new int[0], new int[0], new int[0], new int[0], new int[0], new int[0] };
        this.name = name;
        this.content = content;
        this.id = (short) id;
        this.type = (byte) type;
        this.step_min = step_min;
        this.step_max = step_max;
    }
}
