package com.tgame.ninja.real;

import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.npc.*;
import com.tgame.ninja.server.ServerController;

public class NpcPlayer {

    public static final int BINH_KHI = 0;

    public static final int PHONG_CU = 1;

    public static final int TRANG_SUC = 2;

    public static final int DUOC_PHAM = 3;

    public static final int TAP_HOA = 4;

    public static final int THU_KHO = 5;

    public static final int THO_REN = 6;

    public static final int XA_PHU_LANG = 7;

    public static final int XA_PHU_TRUONG = 8;

    public static final int CHU_NHIEM_HOA = 9;

    public static final int CHU_NHIEM_THUY = 10;

    public static final int CHU_NHIEM_PHONG = 11;

    public static final int TRUONG_LANG_TONE = 12;

    public static final int KHU_VUC = 13;

    public static final int GIAO_THU_1 = 14;

    public static final int GIAO_THU_2 = 15;

    public static final int GIAO_THU_3 = 16;

    public static final int CHAU_BA_REI = 17;

    public static final int TRUONG_LANG_CHAI = 18;

    public static final int TRUONG_LANG_KOJIN = 19;

    public static final int TRUONG_LANG_CHAKUMI = 20;

    public static final int TRUONG_LANG_ECHIGO = 21;

    public static final int TRUONG_LANG_SANZU = 22;

    public static final int TRUONG_LANG_OSHIN = 23;

    public static final int TIEN_TRANG = 24;

    public static final int RIKUDOU = 25;

    public static final int GOOSHO = 26;

    public static final int TRU_CO_QUAN = 27;

    public static final int SHINWA = 28;

    public static final int CHI_HANG = 29;

    public static final int RAKKII = 30;

    public static final int LONG_DEN = 31;

    public static final int KAGAI = 32;

    public static final int TIEN_NU = 33;

    public static final int THANG_BE = 34;

    public static final int ONG_NOEL = 35;

    public static final int VUA_HUNG = 36;

    public PlayerTemplate template;

    public int npcPlayerId;

    public int pointx;

    public int pointy;

    public int x;

    public int y;

    public int status;

    public static final byte A_STAND = 1;

    public static final byte A_HIDE = 15;

    public long timeHide;

    public NpcPlayer(int npcPlayerId, int playerTemplateId) {
        this.npcPlayerId = npcPlayerId;
        template = ServerController.playerTemplates.get(playerTemplateId);
    }

    public void update() {
    }

    public static void loadNpcFunction() {
        ServerController.hNpcMenus.put(BINH_KHI, new BinhKhi());
        ServerController.hNpcMenus.put(PHONG_CU, new PhongCu());
        ServerController.hNpcMenus.put(TRANG_SUC, new TrangSuc());
        ServerController.hNpcMenus.put(DUOC_PHAM, new DuocPham());
        ServerController.hNpcMenus.put(TAP_HOA, new TapHoa());
        ServerController.hNpcMenus.put(THU_KHO, new ThuKho());
        ServerController.hNpcMenus.put(THO_REN, new ThoRen());
        ServerController.hNpcMenus.put(XA_PHU_LANG, new XaPhuLang());
        ServerController.hNpcMenus.put(XA_PHU_TRUONG, new XaPhuTruong());
        ServerController.hNpcMenus.put(CHU_NHIEM_HOA, new ChuNhiemHoa());
        ServerController.hNpcMenus.put(CHU_NHIEM_THUY, new ChuNhiemThuy());
        ServerController.hNpcMenus.put(CHU_NHIEM_PHONG, new ChuNhiemPhong());
        ServerController.hNpcMenus.put(TRUONG_LANG_TONE, new TruongLangTone());
        GiaoThu npcGiaoThu = new GiaoThu();
        ServerController.hNpcMenus.put(GIAO_THU_1, npcGiaoThu);
        ServerController.hNpcMenus.put(GIAO_THU_2, npcGiaoThu);
        ServerController.hNpcMenus.put(GIAO_THU_3, npcGiaoThu);
        ServerController.hNpcMenus.put(CHAU_BA_REI, new ChauBaRei());
        ServerController.hNpcMenus.put(TRUONG_LANG_CHAI, new TruongLangChai());
        ServerController.hNpcMenus.put(TRUONG_LANG_KOJIN, new TruongLangKojin());
        ServerController.hNpcMenus.put(TRUONG_LANG_CHAKUMI, new TruongLangChakumi());
        ServerController.hNpcMenus.put(TRUONG_LANG_ECHIGO, new TruongLangEchigo());
        ServerController.hNpcMenus.put(TRUONG_LANG_SANZU, new TruongLangSanzu());
        ServerController.hNpcMenus.put(TRUONG_LANG_OSHIN, new TruongLangOshin());
        ServerController.hNpcMenus.put(TIEN_TRANG, new TienTrang());
        ServerController.hNpcMenus.put(RIKUDOU, new Rikudou());
        ServerController.hNpcMenus.put(GOOSHO, new Goosho());
        ServerController.hNpcMenus.put(TRU_CO_QUAN, new TruCoQuan());
        ServerController.hNpcMenus.put(SHINWA, new Shinwa());
        ServerController.hNpcMenus.put(RAKKII, new Rakkii());
        ServerController.hNpcMenus.put(LONG_DEN, new LongDen());
        ServerController.hNpcMenus.put(KAGAI, new Kagai());
        ServerController.hNpcMenus.put(TIEN_NU, new TienNu());
        ServerController.hNpcMenus.put(VUA_HUNG, new VuaHung());
    }
}
