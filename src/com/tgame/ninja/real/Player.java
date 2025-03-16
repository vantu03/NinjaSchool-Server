package com.tgame.ninja.real;

import com.tgame.ninja.branch.event.EventManager;
import com.tgame.ninja.branch.tasks.DailyTaskData;
import com.tgame.ninja.branch.tasks.NguyetNhanTask;
import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;
import com.tgame.ninja.io.ThreadPool;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.item.IUseHandler;
import com.tgame.ninja.real.npc.INpcHandler;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player extends LiveObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    public static final byte ROLE_GM = 2;

    public static final byte ROLE_ADMIN = 99;

    public static final byte ME_NORMAL = 0;

    public static final byte ME_DIE = 1;

    public static final byte PK_NORMAL = 0;

    public static final byte PK_NHOM = 1;

    public static final byte PK_BANG = 2;

    public static final byte PK_DOSAT = 3;

    public static final byte PK_PHE1 = 4;

    public static final byte PK_PHE2 = 5;

    public static final byte PK_PHE3 = 6;

    public static final byte GENDER_FEMALE = 0;

    public static final byte GENDER_MALE = 1;

    public static final byte HEAD_MALE_DEO_BANG = 2;

    public static final byte HEAD_FEMALE_NGAN = 11;

    public static final byte HEAD_MALE_BO_XU = 23;

    public static final byte HEAD_MALE_SAMURAI = 24;

    public static final byte HEAD_MALE_NHIM = 25;

    public static final byte HEAD_FEMALE_CAI_TRAM = 26;

    public static final byte HEAD_FEMALE_BUI = 27;

    public static final byte HEAD_FEMALE_XU = 28;

    public static int maxmiss = 1500;

    public static int[] id4xts = { 177, 182, 187, 192 };

    public static int[] id4xnam = { 130, 140, 150, 160, 170 };

    public static int[] id4xnu = { 131, 141, 151, 161, 171 };

    public static int[] id4xvk = { 97, 117, 102, 112, 107, 122 };

    public static int[] id5xts = { 178, 183, 188, 193 };

    public static int[] id5xnam = { 132, 142, 152, 162, 172 };

    public static int[] id5xnu = { 133, 143, 153, 163, 173 };

    public static int[] id5xvk = { 98, 118, 103, 113, 108, 123 };

    public static int[] id6xts = { 318, 320, 322, 324 };

    public static int[] id6xnam = { 317, 319, 321, 323, 325 };

    public static int[] id6xnu = { 326, 327, 328, 329, 330 };

    public static int[] id6xvk = { 331, 332, 333, 334, 335, 336 };

    public static int[] id7xts = { 356, 358, 360, 362 };

    public static int[] id7xnam = { 355, 357, 359, 361, 363 };

    public static int[] id7xnu = { 364, 365, 366, 367, 368 };

    public static int[] id7xvk = { 369, 370, 371, 372, 373, 374 };

    public static int[] id9xts = { 628, 629, 630, 631 };

    public static int[] id9xnam = { 618, 620, 622, 624, 626 };

    public static int[] id9xnu = { 619, 621, 623, 625, 627 };

    public static int[] id9xvk = { 632, 633, 634, 635, 636, 637 };

    public static int[] crystals = { 1, 4, 16, 64, 256, 1024, 4096, 16384, 65536, 262144, 1048576, 4194304 };

    public static int[] upAdorn = { 6, 14, 50, 256, 320, 512, 1024, 5120, 6016, 9088, 19904, 86016, 108544, 166912, 360448, 1589248 };

    public static int[] upClothe = { 4, 9, 33, 132, 177, 256, 656, 2880, 3968, 6016, 13440, 54144, 71680, 108544, 225280, 1032192 };

    public static int[] upWeapon = { 18, 42, 132, 627, 864, 1360, 2816, 13824, 17792, 26880, 54016, 267264, 315392, 489472, 1032192, 4587520 };

    public static int[] coinUpAdorns = { 180, 420, 1500, 7680, 9600, 15360, 30720, 153600, 180480, 272640, 597120, 2580480, 3256320, 5007360, 10813440, 16220160 };

    public static int[] coinUpAdorns1 = { 180, 420, 1500, 7680, 9600, 15360, 30720, 153600, 180480, 272640, 597120, 2580480, 3256320, 5007360, 10813440, 16220160 };

    public static int[] coinUpClothes = { 120, 270, 990, 3960, 5310, 7680, 19680, 86400, 119040, 180480, 403200, 1624320, 2150400, 3256320, 6758400, 10137600 };

    public static int[] coinUpClothes1 = { 120, 270, 990, 3960, 5310, 7680, 19680, 86400, 119040, 180480, 403200, 1624320, 2150400, 3256320, 6758400, 10137600 };

    public static int[] coinUpCrystals = { 10, 40, 160, 640, 2560, 10240, 40960, 163840, 655360, 1310720, 3932160, 11796480 };

    public static int[] coinUpWeapons = { 540, 1260, 3960, 18810, 25920, 40800, 84480, 414720, 533760, 806400, 1620480, 8017920, 9461760, 14684160, 22026240, 33039360 };

    public static int[] coinUpWeapons1 = { 540, 1260, 3960, 18810, 25920, 40800, 84480, 414720, 533760, 806400, 1620480, 8017920, 9461760, 14684160, 22026240, 33039360 };

    public static int[] khamGem = { 18, 42, 132, 627, 864, 1360, 2816, 13824, 17792, 26880, 54016, 267264, 315392, 489472, 1032192, 4587520 };

    public static int[] price_kham_gem = { 0, 800000, 1600000, 2400000, 3200000, 4800000, 7200000, 10800000, 15600000, 20100000, 28100000 };

    public static int[] goldUps = { 1, 2, 3, 4, 5, 10, 15, 20, 50, 100, 150, 200, 300, 400, 500, 600 };

    public static int[] maxPercents = { 80, 75, 70, 65, 60, 55, 50, 45, 40, 35, 30, 25, 20, 15, 10, 5 };

    public static int[] levelByMobId = { 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 55, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 68, 70, 129, 129, 129, 132, 132, 132, 135, 135, 135, 130, 130, 130, 130, 134, 134, 134, 134, 137, 137, 137, 137, 136, 136, 136, 136, 131, 131, 131, 133, 133, 175, 175, 175, 175, 175, 175, 175, 180, 180, 203, 203, 203, 203, 182, 182, 182, 187, 187, 187, 210, 210, 210, 172, 172, 172, 189, 189, 189, 189, 189, 190, 190, 190, 190, 190, 190, 190, 190, 190, 190 };

    public static final byte maxLoopBoss = 2;

    public static final byte maxLoopDay = 20;

    public static final int maxOpenTui = 3;

    public static final byte maxCTKeo = 2;

    public static final byte maxNVBA = 3;

    public static final byte maxChatAdm = 1;

    public static final byte maxPhoBan = 1;

    public static final byte defaultCountTTL = 2;

    public static final byte defaultCountHDL = 1;

    public static int freePotential = 0;

    public static int freeSkill = 0;

    public static int dameFire = 2;

    public static int indexFatal = 1200;

    public static int indexRes = 1500;

    public static int[][] opsTinhLuyen = null;

    public static int[][] opsTinhLuyen_max = null;

    public static int[][] opsTinhLuyen_min = null;

    public static short[] ID_ITEM_GET_AUTO = {
        7, 8, 9, 10, 11, 12, 16, 17, 21, 22,
        30, 36, 37, 38, 383, 384, 385, 240, 241, 251,
        250, 268, 270, 271, 279, 280, 308, 309, 340, 409,
        410, 419, 436, 437, 438, 443, 439, 440, 441, 442,
        449, 450, 451, 452, 453, 454, 455, 456, 457, 470,
        471, 472, 476, 485, 486, 487, 488, 489, 490, 491,
        523, 524, 535, 536, 539, 540, 545, 547, 572, 571,
        570, 569, 568, 567, 565, 566, 564, 552, 553, 554,
        555, 556, 557, 558, 559, 560, 561, 562, 563, 475
    };

    public static long timeDropItem = System.currentTimeMillis() + NJUtil.getMilisByMinute(5);

    public static String[] timeX2Exp = new String[2];

    public static String[] timeClassX2Exp = new String[2];

    public static int[] classX2Exp = new int[2];

    public static Hashtable<Integer, Trade> ALL_CHAR_TRADE = new Hashtable<>();

    public static Hashtable<String, PlayerXoso> ALL_PLAYER_XOSO = new Hashtable<>();

    public static long MONEY_XOSO = 0L;

    public static String charNameXosoWin = "";

    public static int moneyCharXosoBid = 0;

    public static int moneyCharXosowin = 0;

    public static long timeXoso = System.currentTimeMillis();

    public static Hashtable<Integer, String> ALL_ITEM_SHOP_LUONG = new Hashtable<>();

    public static byte[][] ID_SKILL_35_40_15_60 = {
        { 0, 6, 15, 24, 33, 42, 51 },
        { 0, 7, 16, 25, 34, 43, 52 },
        { 0, -1, -1, -1, -1, -1, 47 },
        { 0, -1, -1, -1, 58, -1, -1 }
    };

    public static int ID_TRADE = 0;

    public static byte count = 0;

    public static int countTotalMatch = 0;

    public static long[] exps;

    public static long[] exps1;

    public static long[] expSkill;

    public static long maxExp;

    public static long maxExp1;

    public static String[][] NAME_DANH_VONG = {
        {
            "Danh vọng vũ khí",
            "Danh vọng dây chuyền",
            "Danh vọng nhẫn",
            "Danh vọng ngọc bội",
            "Danh vọng bùa",
            "Danh vọng nón",
            "Danh vọng áo",
            "Danh vọng găng tay",
            "Danh vọng quần",
            "Danh vọng giày"
        },
        {
            "Weapon Fame",
            "Necklace Fame",
            "Ring Fame",
            "Gems Fame",
            "Charm Fame",
            "Hat Fame",
            "Cloth Fame",
            "Gloves Fame",
            "Pants Fame",
            "Shoes Fame"
        }
    };

    public static short[] RANGE_THIEN_BIEN_LENH = { 240, 360, 30000 };

    public static long delayItemChangeMap = 1000L;

    public static long delayHoiSinh = 5000L;

    public static byte dxlink = 100;

    public static byte dylink = 80;

    public static Hashtable<String, Integer> getindexRms = new Hashtable<>();

    public static Comparator<Player> sort = (o1, o2) -> o2.pointCT - o1.pointCT;

    public static final int maxCoin = 2000000000;

    public static Hashtable<String, Integer> countUseRHD = new Hashtable<>();

    public boolean isSelectChar;

    public byte rwct;

    public int timeReturnMap;

    public Item itemWait;

    public Item itemCauca;

    public ItemMap itemMapWait;

    public long timeUseItemChangeMap;

    public long timeHoiSinh;

    public Clan clan;

    public int pointCT;

    public int pointPKCT;

    public int resultCT;

    public int countChatAdmin;

    public int pointTT;

    public boolean isLock;

    public boolean isLoadPlayerNap;

    public int effId;

    public int timeEff;

    public long lastSkill;

    public String strChat;

    public int perDameInvi;

    public int autoUpHp;

    public int timeKeepDao;

    public int perUpBuff;

    public int countDie;

    public int countAttUp;

    public int playerIDBuyItem;

    public boolean isSellingItem;

    public int addMaxHp;

    public ItemMap itemMapPick;

    public int timeNhat;

    public int showbb;

    public int countLatHinh;

    public int cmdMe;

    public int subMe;

    public int countLoiDai;

    public int countLoiDaiClass;

    public int countctkeo;

    public int pointLoiDai;

    public int pointLoiDaiClass;

    public int timeNhanPB;

    public boolean isChangeMap;

    public int timeUp;

    public Player playercopy;

    public int timeSpleep;

    public int timeMoveUp;

    public int typeNvbian;

    public int countNvbian;

    public Vector<Short> listItemPickAuto;

    public String clanName;

    public short taskFinish;

    public byte taskIndex;

    public short taskCount;

    public byte effId_food;

    public byte effId_exp_bonus;

    public byte effId_KhaiThienNhanPhu;

    public int timeEff_food;

    public int timeEff_exp_bonus;

    public int timeEff_ThiLuyen;

    public int timeKhai_Thien_Nhan_Phu;

    public int userId;

    public int playerId;

    public int status;

    public String name;

    public int headId;

    public int weaponId;

    public int bodyId;

    public int legId;

    public int coin_bag;

    public int coin_box;

    public int coin_lock;

    public int gold;

    public byte gender;

    public long reg_date;

    public long login_date;

    public boolean ban;

    public Map map;

    public short mapTemplateId_focus;

    public short x;

    public short y;

    public short level;

    public long exp;

    public long exp_down;

    public byte pk;

    public byte typePk;

    public int hp_full;

    public int hp;

    public int mp_full;

    public int mp;

    public int dame_full;

    public int dam_player;

    public int dam_mob;

    public short resFire;

    public short resIce;

    public short resWind;

    public short timeUpFire;

    public short timeUpIce;

    public short timeUpWind;

    public short timeDownFire;

    public short timeDownIce;

    public short timeDownWind;

    public short sysUp;

    public short sysDown;

    public short effFireBig;

    public short effIceBig;

    public short effWindBig;

    public short effFire;

    public short effIce;

    public short effWind;

    public short miss;

    public short missAll;

    public short convertMp;

    public short exactly;

    public short fatal;

    public short reactDame;

    public short resReactDame;

    public short liveBack;

    public short buffExp;

    public byte bag;

    public byte box;

    public byte speed;

    public byte speedHard;

    public short dameDown;

    public short dameDownFire;

    public short dameDownIce;

    public short dameDownWind;

    public short dameUpFire;

    public short dameUpIce;

    public short dameUpWind;

    public short perDameUpFire;

    public short perDameUpIce;

    public short perDameUpWind;

    public float dameFatalHard;

    public float dameFatalUp;

    public float dameFatalDown;

    public int eff5BuffHp;

    public int eff5BuffMp;

    public int eff5BuffHpGem;

    public int eff5BuffMpGem;

    public byte classId;

    public short potential_point;

    public short skill_point;

    public short p_strength;

    public short p_agile;

    public int p_hp;

    public int p_mp;

    public short view;

    public short timeUpdate;

    public Player owner;

    private Session conn;

    public Skill myskill;

    public Vector<Skill> skills;

    public Item[] itemBags;

    public Item[] itemBoxs;

    public Item[] itemBodys;

    public Item[] itemMons;

    public Vector<Player> ptPlayers;

    public InviteTrade tradeSet;

    public InviteTrade tradeGet;

    public long timeLockSort;

    public TestSkill testSkill;

    public TestSkill testSkillDun;

    public TestSkill testGTDun;

    public KillPlayer killPlayer;

    public Vector<Effect> effects;

    public Task taskMain;

    public TaskOrder taskLoopDay;

    public TaskOrder taskLoopBoss;

    public long timeActive;

    public Vector<Friend> friends;

    public int sysNpc;

    public boolean isHaveClan;

    public byte timeWait;

    public byte timeCauca;

    public Party party;

    public boolean isLoadDataError;

    public boolean isInvisible;

    public int skillDao35;

    public NpcSumon sumon;

    public SumonHide sumonHide;

    public int mapTemplateIdGo;

    public boolean isChangeCoin;

    public String codeSecure;

    public int pointOpenTui;

    public int resetPotential;

    public int resetSkill;

    public int pointUyDanh;

    public int pointNon;

    public int pointVukhi;

    public int pointAo;

    public int pointLien;

    public int pointGangtay;

    public int pointNhan;

    public int pointQuan;

    public int pointNgocboi;

    public int pointGiay;

    public int pointPhu;

    public long timeChat;

    public long timePick;

    public boolean isDisonnect;

    public boolean isAutoSave;

    public int countHackXa;

    public int countHackDanh;

    public int countHackMove;

    public long timeSendMove;

    public String itemInfo;

    public byte countFinishDay;

    public byte countLoopDay;

    public byte countLoopBoos;

    public byte countUseTTL;

    public byte countUseHDL;

    public byte countPB;

    public boolean isAddPointUyDanh;

    public byte limitKynangso;

    public byte limitTiemnangso;

    public byte limitBanhBangHoa;

    public byte limitBanhPhongloi;

    public int pointClan;

    public int timeOutClan;

    public int countPassErr;

    public int pointClanWeek;

    public int pointPB;

    public int timePB;

    public int friendPB;

    public Reward reward;

    public InviteClan inviteClan;

    public InviteClan pleaseClan;

    public String signature1;

    public String signature2;

    public String productPrice;

    public String productDate;

    public String transID;

    public String productID;

    public long timeAtt;

    public boolean isMove;

    public byte xmove;

    public byte ymove;

    public short lastMoveX;

    public short lastMoveY;

    public byte countCheckLock;

    public boolean isLockAcount;

    public byte timeKillMonsterLangCo;

    public byte countHpUseArena;

    public long timeAutoSave;

    public int id_private_eff;

    public boolean isMainchar;

    public CapchaMonster capcha;

    public short rangeTrain;

    public short xStartAuto;

    public short yStartAuto;

    public byte typeGetItemAuto;

    public byte typeSelectBox;

    public byte idBoss;

    public byte idActionNewMenu;

    public int idSendHuyEff;

    public byte fullSet6;

    public byte fullSet8;

    public byte fullSet10;

    public int damChiMang;

    public short indexBagMenu;

    public boolean haveNapTien;

    public byte[][][] dataRms;

    public String[][] keyRms;

    public Npc privateNpc;

    public byte countConfirmCapcha;

    public byte countFail;

    public boolean isLockChat;

    public Vector<String> infoChat;

    public long timeChatMap;

    public long timeStartPlay;

    public int idTrade;

    public long expSkillPhanthan;

    public byte nHit2PrivateMonster;

    public boolean resetCa;

    public long timeLiveCoppy;

    public boolean isPlayerCopy;

    public byte switchNewExp;

    public String dayLogin;

    public long timeLogOut;

    public int timeOnline;

    public boolean isSetTimeOnline;

    public boolean haveCapcha;

    public short countMonsKill;

    public short maxKillMonster;

    public boolean isSendAlert;

    public Item itFood;

    public String memName;

    public byte indexUITT;

    public byte countHackNgoc;

    public int point3Arena;

    public DailyTaskData dailytask;

    public NguyetNhanTask nguyetNhanTask;

    public short totalNvNguyetNhan;

    public byte useDanhVongPhu;

    public int maxUseExtend;

    public boolean bIsLockMatsu;

    public int iDamage;

    public long timeAction;

    public byte oldTypePK;

    public LocalDateTime calInvincible;

    public Vector<Player> lsRecentPK;

    public boolean isAdminUseCheat;

    public boolean isHideChat;

    static {
        getindexRms.put("OSkill", 0);
        getindexRms.put("KSkill", 1);
        getindexRms.put("CSkill", 2);
    }

    public Player() {
        isSelectChar = false;
        rwct = 0;
        clan = null;
        pointCT = 0;
        pointPKCT = 0;
        resultCT = 0;
        countChatAdmin = 0;
        pointTT = 0;
        isLock = false;
        isLoadPlayerNap = false;
        strChat = "";
        perDameInvi = 0;
        autoUpHp = 0;
        timeKeepDao = 0;
        perUpBuff = 0;
        countDie = 0;
        countAttUp = 0;
        playerIDBuyItem = -1;
        isSellingItem = false;
        addMaxHp = 0;
        timeNhat = 0;
        showbb = 0;
        countLatHinh = 0;
        countLoiDai = 0;
        countLoiDaiClass = 0;
        countctkeo = 0;
        pointLoiDai = 0;
        pointLoiDaiClass = 0;
        timeNhanPB = 0;
        isChangeMap = false;
        timeUp = 0;
        playercopy = null;
        timeSpleep = 0;
        timeMoveUp = 0;
        typeNvbian = 0;
        countNvbian = 2;
        listItemPickAuto = new Vector<>();
        clanName = "";
        name = "";
        speedHard = 3;
        dameFatalHard = 1.8f;
        view = 320;
        skills = new Vector<>();
        itemBodys = new Item[16];
        itemMons = new Item[5];
        ptPlayers = new Vector<>();
        tradeSet = null;
        tradeGet = null;
        timeLockSort = 0L;
        effects = new Vector<>();
        friends = new Vector<>();
        timeWait = 0;
        timeCauca = 0;
        skillDao35 = 0;
        mapTemplateIdGo = -1;
        isAutoSave = false;
        countHackXa = 0;
        countHackDanh = 0;
        countHackMove = 0;
        timeSendMove = 0L;
        itemInfo = "";
        countFinishDay = 0;
        countLoopDay = 0;
        countLoopBoos = 0;
        countUseTTL = 0;
        countUseHDL = 0;
        countPB = 0;
        isAddPointUyDanh = false;
        limitKynangso = 0;
        limitTiemnangso = 0;
        limitBanhBangHoa = 0;
        limitBanhPhongloi = 0;
        pointClan = 0;
        timeOutClan = 0;
        countPassErr = 0;
        pointClanWeek = 0;
        pointPB = 0;
        timePB = 0;
        friendPB = 0;
        reward = null;
        inviteClan = null;
        pleaseClan = null;
        signature1 = "";
        signature2 = "";
        productPrice = "";
        productDate = "";
        transID = "";
        productID = "";
        timeAtt = 0L;
        isMove = false;
        xmove = 0;
        ymove = 0;
        lastMoveX = 0;
        lastMoveY = 0;
        countCheckLock = 0;
        isLockAcount = false;
        timeKillMonsterLangCo = 0;
        countHpUseArena = 0;
        timeAutoSave = System.currentTimeMillis();
        id_private_eff = -1;
        isMainchar = true;
        capcha = null;
        rangeTrain = 30000;
        typeGetItemAuto = 0;
        typeSelectBox = 0;
        idBoss = -1;
        idActionNewMenu = -1;
        idSendHuyEff = -1;
        fullSet6 = 0;
        fullSet8 = 0;
        fullSet10 = 0;
        damChiMang = 0;
        indexBagMenu = -1;
        haveNapTien = false;
        dataRms = new byte[][][]{ new byte[3][], new byte[3][] };
        keyRms = new String[][]{
            { "OSkill", "KSkill", "CSkill" },
            { "OSkill", "KSkill", "CSkill" }
        };
        privateNpc = null;
        countConfirmCapcha = 0;
        countFail = 0;
        isLockChat = false;
        infoChat = new Vector<>();
        timeChatMap = 0L;
        timeStartPlay = System.currentTimeMillis();
        idTrade = -1;
        expSkillPhanthan = 0L;
        nHit2PrivateMonster = 10;
        resetCa = false;
        timeLiveCoppy = -1L;
        isPlayerCopy = false;
        timeLogOut = System.currentTimeMillis();
        switchNewExp = 0;
        dayLogin = "";
        haveCapcha = false;
        countMonsKill = 0;
        maxKillMonster = 50;
        isSendAlert = false;
        itFood = null;
        memName = "";
        indexUITT = -1;
        countHackNgoc = 0;
        point3Arena = 0;
        dailytask = null;
        nguyetNhanTask = null;
        totalNvNguyetNhan = 20;
        useDanhVongPhu = 6;
        maxUseExtend = 3;
        bIsLockMatsu = true;
        iDamage = 0;
        timeAction = 0L;
        oldTypePK = -127;
        calInvincible = null;
        lsRecentPK = new Vector<>();
        playerId = -1;
    }

    public Player(Session conn, String name, int gender, int headId) {
        isSelectChar = false;
        rwct = 0;
        clan = null;
        pointCT = 0;
        pointPKCT = 0;
        resultCT = 0;
        countChatAdmin = 0;
        pointTT = 0;
        isLock = false;
        isLoadPlayerNap = false;
        strChat = "";
        perDameInvi = 0;
        autoUpHp = 0;
        timeKeepDao = 0;
        perUpBuff = 0;
        countDie = 0;
        countAttUp = 0;
        playerIDBuyItem = -1;
        isSellingItem = false;
        addMaxHp = 0;
        timeNhat = 0;
        showbb = 0;
        countLatHinh = 0;
        countLoiDai = 0;
        countLoiDaiClass = 0;
        countctkeo = 0;
        pointLoiDai = 0;
        pointLoiDaiClass = 0;
        timeNhanPB = 0;
        isChangeMap = false;
        timeUp = 0;
        playercopy = null;
        timeSpleep = 0;
        timeMoveUp = 0;
        typeNvbian = 0;
        countNvbian = 2;
        listItemPickAuto = new Vector<>();
        clanName = "";
        this.name = "";
        speedHard = 3;
        dameFatalHard = 1.8f;
        view = 320;
        skills = new Vector<>();
        itemBodys = new Item[16];
        itemMons = new Item[5];
        ptPlayers = new Vector<>();
        tradeSet = null;
        tradeGet = null;
        timeLockSort = 0L;
        effects = new Vector<>();
        friends = new Vector<>();
        timeWait = 0;
        timeCauca = 0;
        skillDao35 = 0;
        mapTemplateIdGo = -1;
        isAutoSave = false;
        countHackXa = 0;
        countHackDanh = 0;
        countHackMove = 0;
        timeSendMove = 0L;
        itemInfo = "";
        countFinishDay = 0;
        countLoopDay = 0;
        countLoopBoos = 0;
        countUseTTL = 0;
        countUseHDL = 0;
        countPB = 0;
        isAddPointUyDanh = false;
        limitKynangso = 0;
        limitTiemnangso = 0;
        limitBanhBangHoa = 0;
        limitBanhPhongloi = 0;
        pointClan = 0;
        timeOutClan = 0;
        countPassErr = 0;
        pointClanWeek = 0;
        pointPB = 0;
        timePB = 0;
        friendPB = 0;
        reward = null;
        inviteClan = null;
        pleaseClan = null;
        signature1 = "";
        signature2 = "";
        productPrice = "";
        productDate = "";
        transID = "";
        productID = "";
        timeAtt = 0L;
        isMove = false;
        xmove = 0;
        ymove = 0;
        lastMoveX = 0;
        lastMoveY = 0;
        countCheckLock = 0;
        isLockAcount = false;
        timeKillMonsterLangCo = 0;
        countHpUseArena = 0;
        timeAutoSave = System.currentTimeMillis();
        id_private_eff = -1;
        isMainchar = true;
        capcha = null;
        rangeTrain = 30000;
        typeGetItemAuto = 0;
        typeSelectBox = 0;
        idBoss = -1;
        idActionNewMenu = -1;
        idSendHuyEff = -1;
        fullSet6 = 0;
        fullSet8 = 0;
        fullSet10 = 0;
        damChiMang = 0;
        indexBagMenu = -1;
        haveNapTien = false;
        dataRms = new byte[][][]{ new byte[3][], new byte[3][] };
        keyRms = new String[][]{
            { "OSkill", "KSkill", "CSkill" },
            { "OSkill", "KSkill", "CSkill" }
        };
        privateNpc = null;
        countConfirmCapcha = 0;
        countFail = 0;
        isLockChat = false;
        infoChat = new Vector<>();
        timeChatMap = 0L;
        timeStartPlay = System.currentTimeMillis();
        idTrade = -1;
        expSkillPhanthan = 0L;
        nHit2PrivateMonster = 10;
        resetCa = false;
        timeLiveCoppy = -1L;
        isPlayerCopy = false;
        timeLogOut = System.currentTimeMillis();
        switchNewExp = 0;
        dayLogin = "";
        haveCapcha = false;
        countMonsKill = 0;
        maxKillMonster = 50;
        isSendAlert = false;
        itFood = null;
        memName = "";
        indexUITT = -1;
        countHackNgoc = 0;
        point3Arena = 0;
        dailytask = null;
        nguyetNhanTask = null;
        totalNvNguyetNhan = 20;
        useDanhVongPhu = 6;
        maxUseExtend = 3;
        bIsLockMatsu = true;
        iDamage = 0;
        timeAction = 0L;
        oldTypePK = -127;
        calInvincible = null;
        lsRecentPK = new Vector<>();
        this.conn = conn;
        if (conn != null) {
            userId = conn.userId;
        }
        this.name = name;
        this.gender = (byte) gender;
        this.headId = headId;
        classId = 0;
        long currentTimeMillis = System.currentTimeMillis();
        login_date = currentTimeMillis;
        reg_date = currentTimeMillis;
        bag = 30;
        box = 30;
        map = new Map(22);
        mapTemplateId_focus = 22;
        x = 1674;
        y = 264;
        p_strength = 15;
        p_agile = 5;
        p_hp = 5;
        p_mp = 5;
        skills.add(ServerController.nClasss.get(0).skillTemplates.get(0).skills.get(0));
        itemBags = new Item[bag];
        itemBoxs = new Item[box];
        taskIndex = -1;
        pointUyDanh = 3;
        pointOpenTui = maxOpenTui;
        countLoopDay = maxLoopDay;
        countLoopBoos = maxLoopBoss;
        countUseTTL = defaultCountTTL;
        countUseHDL = defaultCountHDL;
    }

    public Player(String name, int gender, int headId) {
        isSelectChar = false;
        rwct = 0;
        clan = null;
        pointCT = 0;
        pointPKCT = 0;
        resultCT = 0;
        countChatAdmin = 0;
        pointTT = 0;
        isLock = false;
        isLoadPlayerNap = false;
        strChat = "";
        perDameInvi = 0;
        autoUpHp = 0;
        timeKeepDao = 0;
        perUpBuff = 0;
        countDie = 0;
        countAttUp = 0;
        playerIDBuyItem = -1;
        isSellingItem = false;
        addMaxHp = 0;
        timeNhat = 0;
        showbb = 0;
        countLatHinh = 0;
        countLoiDai = 0;
        countLoiDaiClass = 0;
        countctkeo = 0;
        pointLoiDai = 0;
        pointLoiDaiClass = 0;
        timeNhanPB = 0;
        isChangeMap = false;
        timeUp = 0;
        playercopy = null;
        timeSpleep = 0;
        timeMoveUp = 0;
        typeNvbian = 0;
        countNvbian = 2;
        listItemPickAuto = new Vector<>();
        clanName = "";
        this.name = "";
        speedHard = 3;
        dameFatalHard = 1.8f;
        view = 320;
        skills = new Vector<>();
        itemBodys = new Item[16];
        itemMons = new Item[5];
        ptPlayers = new Vector<>();
        tradeSet = null;
        tradeGet = null;
        timeLockSort = 0L;
        effects = new Vector<>();
        friends = new Vector<>();
        timeWait = 0;
        timeCauca = 0;
        skillDao35 = 0;
        mapTemplateIdGo = -1;
        isAutoSave = false;
        countHackXa = 0;
        countHackDanh = 0;
        countHackMove = 0;
        timeSendMove = 0L;
        itemInfo = "";
        countFinishDay = 0;
        countLoopDay = 0;
        countLoopBoos = 0;
        countUseTTL = 0;
        countUseHDL = 0;
        countPB = 0;
        isAddPointUyDanh = false;
        limitKynangso = 0;
        limitTiemnangso = 0;
        limitBanhBangHoa = 0;
        limitBanhPhongloi = 0;
        pointClan = 0;
        timeOutClan = 0;
        countPassErr = 0;
        pointClanWeek = 0;
        pointPB = 0;
        timePB = 0;
        friendPB = 0;
        reward = null;
        inviteClan = null;
        pleaseClan = null;
        signature1 = "";
        signature2 = "";
        productPrice = "";
        productDate = "";
        transID = "";
        productID = "";
        timeAtt = 0L;
        isMove = false;
        xmove = 0;
        ymove = 0;
        lastMoveX = 0;
        lastMoveY = 0;
        countCheckLock = 0;
        isLockAcount = false;
        timeKillMonsterLangCo = 0;
        countHpUseArena = 0;
        timeAutoSave = System.currentTimeMillis();
        id_private_eff = -1;
        isMainchar = true;
        capcha = null;
        rangeTrain = 30000;
        typeGetItemAuto = 0;
        typeSelectBox = 0;
        idBoss = -1;
        idActionNewMenu = -1;
        idSendHuyEff = -1;
        fullSet6 = 0;
        fullSet8 = 0;
        fullSet10 = 0;
        damChiMang = 0;
        indexBagMenu = -1;
        haveNapTien = false;
        dataRms = new byte[][][]{ new byte[3][], new byte[3][] };
        keyRms = new String[][]{
            { "OSkill", "KSkill", "CSkill" },
            { "OSkill", "KSkill", "CSkill" }
        };
        privateNpc = null;
        countConfirmCapcha = 0;
        countFail = 0;
        isLockChat = false;
        infoChat = new Vector<>();
        timeChatMap = 0L;
        timeStartPlay = System.currentTimeMillis();
        idTrade = -1;
        expSkillPhanthan = 0L;
        nHit2PrivateMonster = 10;
        resetCa = false;
        timeLiveCoppy = -1L;
        isPlayerCopy = false;
        timeLogOut = System.currentTimeMillis();
        switchNewExp = 0;
        dayLogin = "";
        haveCapcha = false;
        countMonsKill = 0;
        maxKillMonster = 50;
        isSendAlert = false;
        itFood = null;
        memName = "";
        indexUITT = -1;
        countHackNgoc = 0;
        point3Arena = 0;
        dailytask = null;
        nguyetNhanTask = null;
        totalNvNguyetNhan = 20;
        useDanhVongPhu = 6;
        maxUseExtend = 3;
        bIsLockMatsu = true;
        iDamage = 0;
        timeAction = 0L;
        oldTypePK = -127;
        calInvincible = null;
        lsRecentPK = new Vector<>();
        userId = -1;
        this.name = name;
        this.gender = (byte) gender;
        this.headId = headId;
        classId = 0;
        long currentTimeMillis = System.currentTimeMillis();
        login_date = currentTimeMillis;
        reg_date = currentTimeMillis;
        bag = 30;
        box = 30;
        map = new Map(22);
        mapTemplateId_focus = 22;
        x = 1674;
        y = 264;
        p_strength = 15;
        p_agile = 5;
        p_hp = 5;
        p_mp = 5;
        skills.add(ServerController.nClasss.get(0).skillTemplates.get(0).skills.get(0));
        itemBags = new Item[bag];
        itemBoxs = new Item[box];
        taskIndex = -1;
    }

    public String getStringBaseInfo() {
        if (getSession() != null) {
            return getSession().username + "," + name + "," + getSession().getClientAddress() + "," + getSession().clientType;
        }
        return "";
    }

    public Session getSession() {
        return conn;
    }

    public void setSession(Session s) {
        conn = s;
    }

    public int getSys() {
        if (playerId < 0) {
            return sysNpc;
        }
        Player playerMain = getPlayerMainControl();
        if (playerMain.classId == 1 || playerMain.classId == 2) {
            return 1;
        }
        if (playerMain.classId == 3 || playerMain.classId == 4) {
            return 2;
        }
        if (playerMain.classId == 5 || playerMain.classId == 6) {
            return 3;
        }
        return 0;
    }

    public int getHp() {
        return getPlayerMainControl().hp;
    }

    public int getMp() {
        return getPlayerMainControl().mp;
    }

    public int getFullHp() {
        return getPlayerMainControl().hp_full;
    }

    public int getFullMp() {
        return getPlayerMainControl().mp_full;
    }

    public void subHP(int value) {
        Player playerMain = getPlayerMainControl();
        playerMain.hp -= value;
        if (playerMain.hp <= 0) {
            playerMain.hp = 0;
        }
    }

    public void subMP(int value) {
        Player playerMain = getPlayerMainControl();
        playerMain.mp -= value;
        if (playerMain.mp <= 0) {
            playerMain.mp = 0;
        }
    }

    public int getXu() {
        if (coin_bag < 0) {
            coin_bag = 0;
        }
        return coin_bag;
    }

    public void addXu(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        long sum = (long) coin_bag + (long) xu;
        if (sum <= 0L) {
            return;
        }
        if (sum > maxCoin) {
            coin_bag = maxCoin;
        } else {
            coin_bag = (int) sum;
        }
    }

    public void subXu(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        if (dailytask != null && dailytask.template.checkTask(this, 0, 0)) {
            checkTaskOrder(dailytask, xu);
        }
        coin_bag -= xu;
        if (coin_bag < 0) {
            coin_bag = 0;
        }
    }

    public void setXu(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        if (xu > maxCoin) {
            xu = maxCoin;
        }
        coin_bag = xu;
    }

    public int getXuBox() {
        if (coin_box < 0) {
            coin_box = 0;
        }
        return coin_box;
    }

    public void addXuBox(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        long sum = (long) coin_box + (long) xu;
        if (sum <= 0L) {
            return;
        }
        if (sum > maxCoin) {
            coin_box = maxCoin;
        } else {
            coin_box = (int) sum;
        }
    }

    public void subXuBox(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        coin_box -= xu;
        if (coin_box < 0) {
            coin_box = 0;
        }
    }

    public void setXuBox(int xu) {
        if (xu < 0) {
            xu = 0;
        }
        if (xu > maxCoin) {
            xu = maxCoin;
        }
        coin_box = xu;
    }

    public int getYen() {
        if (coin_lock < 0) {
            coin_lock = 0;
        }
        return coin_lock;
    }

    public void addYen(int yen) {
        if (yen < 0) {
            yen = 0;
        }
        long sum = (long) coin_lock + (long) yen;
        if (sum <= 0L) {
            return;
        }
        if (sum > maxCoin) {
            coin_lock = maxCoin;
        } else {
            coin_lock = (int) sum;
        }
    }

    public void subYen(int yen) {
        if (yen < 0) {
            yen = 0;
        }
        if (dailytask != null && dailytask.template.checkTask(this, 0, 1)) {
            checkTaskOrder(dailytask, yen);
        }
        coin_lock -= yen;
        if (coin_lock < 0) {
            coin_lock = 0;
        }
    }

    public void setYen(int yen) {
        if (yen < 0) {
            yen = 0;
        }
        if (yen > maxCoin) {
            yen = maxCoin;
        }
        coin_lock = yen;
    }

    public int getLuong() {
        if (gold < 0) {
            gold = 0;
        }
        return gold;
    }

    public void addLuong(int luong) {
        if (luong < 0) {
            luong = 0;
        }
        if (gold + luong <= 0) {
            return;
        }
        gold += luong;
        if (gold > maxCoin) {
            gold = maxCoin;
        }
    }

    public void subLuong(int luong) {
        if (luong < 0) {
            luong = 0;
        }
        if (dailytask != null && dailytask.template.checkTask(this, 0, 2)) {
            checkTaskOrder(dailytask, luong);
        }
        gold -= luong;
        if (gold < 0) {
            gold = 0;
        }
    }

    public void setLuong(int luong) {
        if (luong < 0) {
            luong = 0;
        }
        if (luong > maxCoin) {
            luong = maxCoin;
        }
        gold = luong;
    }

    public void resetData() {
        updateData();
        hp = hp_full;
        mp = mp_full;
    }

    public void setLevel(int level) {
        long ss = 0L;
        if (level > GameServer.maxLevel) {
            return;
        }
        for (int k = 0; k < level; ++k) {
            ss += (switchNewExp == 0 ? Player.exps[k] : Player.exps1[k]);
        }
        long exp = ss - getPlayerMainControl().exp;
        sendUpdateExp(exp, true);
    }

    public void kickOption(Item item, int maxKick) {
        int kick = 0;
        if (item != null && item.options != null) {
            for (int i = 0; i < item.options.size(); ++i) {
                item.options.get(i).active = 0;
                if (item.options.get(i).optionTemplate.type == 2) {
                    if (kick < maxKick) {
                        item.options.get(i).active = 1;
                        ++kick;
                    }
                } else if (item.options.get(i).optionTemplate.type == 3 && item.upgrade >= 4) {
                    item.options.get(i).active = 1;
                } else if (item.options.get(i).optionTemplate.type == 4 && item.upgrade >= 8) {
                    item.options.get(i).active = 1;
                } else if (item.options.get(i).optionTemplate.type == 5 && item.upgrade >= 12) {
                    item.options.get(i).active = 1;
                } else if (item.options.get(i).optionTemplate.type == 6 && item.upgrade >= 14) {
                    item.options.get(i).active = 1;
                } else if (item.options.get(i).optionTemplate.type == 7 && item.upgrade >= 16) {
                    item.options.get(i).active = 1;
                }
            }
        }
    }

    public int getEff5BuffHp() {
        int result = eff5BuffHp + eff5BuffHpGem;
        if (result < 0) {
            result = 0;
        }
        return result;
    }

    public int getEff5BuffMp() {
        int result = eff5BuffMp + eff5BuffMpGem;
        if (result < 0) {
            result = 0;
        }
        return result;
    }

    public void updateData() {
        speed = (byte) (speedHard + 1);
        if (level >= 10 && level < 30) {
            speed = 5;
        } else if (level >= 30) {
            speed = 6;
        }
        short n = 0;
        sysDown = n;
        sysUp = n;
        timeUpWind = n;
        timeUpIce = n;
        timeUpFire = n;
        timeDownWind = n;
        timeDownIce = n;
        timeDownFire = n;
        effWind = n;
        effIce = n;
        effFire = n;
        resReactDame = n;
        reactDame = n;
        fatal = n;
        exactly = n;
        missAll = n;
        miss = n;
        resWind = n;
        resIce = n;
        resFire = n;
        dameDown = n;
        dame_full = n;
        mp_full = n;
        hp_full = n;
        eff5BuffMp = n;
        eff5BuffHp = n;
        perUpBuff = n;
        timeKeepDao = n;
        autoUpHp = n;
        perDameInvi = n;
        dameFatalDown = n;
        dameFatalUp = n;
        short dameDownFire = 0;
        perDameUpWind = dameDownFire;
        perDameUpIce = dameDownFire;
        perDameUpFire = dameDownFire;
        dameUpWind = dameDownFire;
        dameUpIce = dameDownFire;
        dameUpFire = dameDownFire;
        dameDownWind = dameDownFire;
        dameDownIce = dameDownFire;
        this.dameDownFire = dameDownFire;
        eff5BuffHpGem = 0;
        eff5BuffMpGem = 0;
        dam_player = 0;
        dam_mob = 0;
        damChiMang = 0;
        int pp_strength = p_strength;
        int pp_agile = p_agile;
        int pp_hp = p_hp;
        int pp_mp = p_mp;
        if (itemBodys[11] != null && itemBodys[11].options != null && itemBodys[11].options.firstElement().optionTemplate.itemOptionTemplateId == 57) {
            pp_strength += itemBodys[11].options.firstElement().param;
            pp_agile += itemBodys[11].options.firstElement().param;
            pp_hp += itemBodys[11].options.firstElement().param;
            pp_mp += itemBodys[11].options.firstElement().param;
        }
        ItemOption aa = null;
        for (Item body : itemBodys) {
            if (body != null && body.options != null && body.options.size() > 0) {
                for (int j = 0; j < body.options.size(); ++j) {
                    if (body.options.get(j).optionTemplate.itemOptionTemplateId == 58 && body.options.get(j).param > 0) {
                        aa = body.options.get(j);
                    }
                }
                if (aa != null) {
                    break;
                }
            }
        }
        if (aa != null) {
            pp_strength += pp_strength * aa.param / 100;
            pp_agile += pp_agile * aa.param / 100;
            pp_hp += pp_hp * aa.param / 100;
            pp_mp += pp_mp * aa.param / 100;
        }
        if (isSysOut()) {
            dame_full = pp_strength;
            miss = (short) (pp_agile * 1.5);
            exactly = (short) pp_agile;
            hp_full = pp_hp * 10;
            mp_full = pp_mp * 8;
        } else {
            miss = (short) (pp_agile * 1.5);
            exactly = (short) pp_agile;
            hp_full = pp_hp * 10;
            mp_full = pp_mp * 4;
            dame_full = pp_mp;
        }
        int dame_begin = dame_full;
        int hp_begin = hp_full;
        int mp_begin = mp_full;
        int kich0 = 2;
        int kich2 = 2;
        int kich3 = 2;
        int kichVukhi = 0;
        if (itemBodys[0] == null) {
            --kich0;
        }
        if (itemBodys[6] == null) {
            --kich0;
        }
        if (itemBodys[5] == null) {
            --kich0;
        }
        kickOption(itemBodys[0], kich0);
        kickOption(itemBodys[6], kich0);
        kickOption(itemBodys[5], kich0);
        if (itemBodys[2] == null) {
            --kich2;
        }
        if (itemBodys[8] == null) {
            --kich2;
        }
        if (itemBodys[7] == null) {
            --kich2;
        }
        kickOption(itemBodys[2], kich2);
        kickOption(itemBodys[8], kich2);
        kickOption(itemBodys[7], kich2);
        if (itemBodys[4] == null) {
            --kich3;
        }
        if (itemBodys[3] == null) {
            --kich3;
        }
        if (itemBodys[9] == null) {
            --kich3;
        }
        if (itemBodys[1] != null && itemBodys[1].options != null) {
            if (itemBodys[1].sys == getSys()) {
                kichVukhi = 2;
            }
            kickOption(itemBodys[1], kichVukhi);
        }
        kickOption(itemBodys[4], kich3);
        kickOption(itemBodys[3], kich3);
        kickOption(itemBodys[9], kich3);
        int old_fullSet6 = fullSet6;
        int old_fullSet7 = fullSet8;
        int old_fullSet8 = fullSet10;
        fullSet6 = 0;
        fullSet8 = 0;
        fullSet10 = 0;
        for (Item itemBody : itemBodys) {
            try {
                updateIndexOption(itemBody, hp_begin, mp_begin, dame_begin);
            } catch (Exception e) {
            }
        }
        if (fullSet10 >= 10) {
            if (old_fullSet7 >= 10) {
                idSendHuyEff = 1;
            } else if (old_fullSet6 >= 10) {
                idSendHuyEff = 0;
            }
        } else if (fullSet8 >= 10) {
            if (old_fullSet8 >= 10) {
                idSendHuyEff = 2;
            } else if (old_fullSet6 >= 10) {
                idSendHuyEff = 0;
            }
        } else if (fullSet6 >= 10) {
            if (old_fullSet7 >= 10) {
                idSendHuyEff = 1;
            } else if (old_fullSet8 >= 10) {
                idSendHuyEff = 2;
            }
        } else if (old_fullSet7 >= 10) {
            idSendHuyEff = 1;
        } else if (old_fullSet8 >= 10) {
            idSendHuyEff = 2;
        } else if (old_fullSet6 >= 10) {
            idSendHuyEff = 0;
        }
        idSendHuyEff = -1;
        if (itemMons[4] != null) {
            for (Item itemMon : itemMons) {
                updateIndexOption(itemMon, hp_begin, mp_begin, dame_begin);
            }
        }
        for (Skill skill : skills) {
            if (skill != null && skill.options != null && skill.template.type == 0) {
                for (int l = 0; l < skill.options.size(); ++l) {
                    updateSkillOption(skill.options.get(l), dame_begin, hp_begin, mp_begin);
                }
            }
        }
        if (myskill != null && myskill.options != null && myskill.template.type == 1) {
            for (int k = 0; k < myskill.options.size(); ++k) {
                updateSkillOption(myskill.options.get(k), dame_begin, hp_begin, mp_begin);
            }
        }
        if (myskill != null) {
            if (myskill.template.skillTemplateId == 5 ||
                myskill.template.skillTemplateId == 14 ||
                myskill.template.skillTemplateId == 23 ||
                myskill.template.skillTemplateId == 32 ||
                myskill.template.skillTemplateId == 41 ||
                myskill.template.skillTemplateId == 50
            ) {
                int dameSkill = getDameSkill();
                for (Skill skill2 : skills) {
                    if (skill2 != null && skill2.options != null) {
                        for (int j2 = 0; j2 < skill2.options.size(); ++j2) {
                            SkillOption option = skill2.options.get(j2);
                            if (option.optionTemplate.skillOptionTemplateId == 58) {
                                dame_full += dameSkill * option.param / 100;
                            }
                        }
                    }
                }
            } else if (myskill.template.skillTemplateId == 7 ||
                myskill.template.skillTemplateId == 16 ||
                myskill.template.skillTemplateId == 25 ||
                myskill.template.skillTemplateId == 34 ||
                myskill.template.skillTemplateId == 43 ||
                myskill.template.skillTemplateId == 52
            ) {
                int dameSkill = getDameSkill();
                for (Skill skill2 : skills) {
                    if (skill2 != null && skill2.options != null) {
                        for (int j2 = 0; j2 < skill2.options.size(); ++j2) {
                            SkillOption option = skill2.options.get(j2);
                            if (option.optionTemplate.skillOptionTemplateId == 59) {
                                dame_full += dameSkill * option.param / 100;
                            }
                        }
                    }
                }
            } else if (myskill.template.skillTemplateId == 9 ||
                myskill.template.skillTemplateId == 18 ||
                myskill.template.skillTemplateId == 27 ||
                myskill.template.skillTemplateId == 36 ||
                myskill.template.skillTemplateId == 45 ||
                myskill.template.skillTemplateId == 54
            ) {
                int dameSkill = getDameSkill();
                for (Skill skill2 : skills) {
                    if (skill2 != null && skill2.options != null) {
                        for (int j2 = 0; j2 < skill2.options.size(); ++j2) {
                            SkillOption option = skill2.options.get(j2);
                            if (option.optionTemplate.skillOptionTemplateId == 60) {
                                dame_full += dameSkill * option.param / 100;
                            }
                        }
                    }
                }
            }
        }
        for (Effect effect : effects) {
            if (effect.template.type == 7) {
                miss += (short) effect.param;
            } else if (effect.template.type == 13) {
                dame_full += dame_begin * effect.param / 100;
            } else if (effect.template.type == 15) {
                resFire += (short) effect.options.get(0).param;
                resWind += (short) effect.options.get(0).param;
                resIce += (short) effect.options.get(0).param;
                dame_full += dame_begin * effect.options.get(1).param / 100;
            } else if (effect.template.type == 16) {
                for (int j3 = 0; j3 < effect.options.size(); ++j3) {
                    SkillOption option2 = effect.options.get(j3);
                    if (option2.optionTemplate.skillOptionTemplateId == 40) {
                        timeDownFire += 3000;
                    } else if (option2.optionTemplate.skillOptionTemplateId == 41) {
                        timeDownIce += 2000;
                    } else if (option2.optionTemplate.skillOptionTemplateId == 42) {
                        timeDownWind += 1000;
                    } else if (option2.optionTemplate.skillOptionTemplateId == 47) {
                        timeDownFire += 2000;
                    } else if (option2.optionTemplate.skillOptionTemplateId == 54) {
                        timeDownIce += 1000;
                    } else if (option2.optionTemplate.skillOptionTemplateId == 57) {
                        timeDownWind += 500;
                    }
                }
            } else if (effect.template.type == 19) {
                exactly += 2000;
            } else if (effect.template.type == 20) {
                exactly += (short) effect.param;
            } else if (effect.template.type == 21) {
                dame_full += dame_begin * effect.param / 100;
            } else if (effect.template.type == 22) {
                resFire += (short) effect.param;
                resIce += (short) effect.param;
                resWind += (short) effect.param;
            } else if (effect.template.type == 23) {
                hp += effect.param;
                hp_full += effect.param;
            }
        }
        hp_full += addMaxHp;
        if (hp > hp_full) {
            hp = hp_full;
        }
        if (mp > mp_full) {
            mp = mp_full;
        }
        if (classId == 1 || classId == 2) {
            resIce -= level;
            resWind += level;
        } else if (classId == 3 || classId == 4) {
            resWind -= level;
            resFire += level;
        } else if (classId == 5 || classId == 6) {
            resFire -= level;
            resIce += level;
        }
    }

    public void refreshPotential() {
        potential_point = 0;
        for (int i = 1; i <= level; ++i) {
            if (i >= 70 && i < 80) {
                potential_point += 20;
            } else if (i >= 80 && i < 90) {
                potential_point += 30;
            } else if (i >= 90) {
                potential_point += 50;
            } else {
                potential_point += 10;
            }
        }
        potential_point += (short) (limitTiemnangso * 10);
        potential_point += (short) (limitBanhBangHoa * 10);
    }

    public void refreshSkill() {
        skill_point = (short) (level - 9);
        skill_point += limitKynangso;
        skill_point += limitBanhPhongloi;
    }

    public void doMove(short xNext, short yNext) {
        Message message;
        int speed = this.speed;
        if (itemBodys[4] != null) {
            if (itemBodys[4].template.itemTemplateId == 485) {
                speed += 3;
            } else {
                speed += 2;
            }
        }
        if (status != ME_DIE && getHp() != 0) {
            if (capcha == null) {
                lastMoveX = 0;
                lastMoveY = 0;
                if (speed > 12) {
                    speed = 12;
                }
                try {
                    isMove = true;
                    if (xNext < 0 || yNext < 0 || xNext > map.template.w || yNext > map.template.h) {
                        return;
                    }
                    if ((map.getTemplateId() == 114 && ((x >= 98 && x <= 912 && y >= 550 && yNext < 614) || (x >= 912 && x <= 1032 && y >= 618 && yNext < 614))) || (map.getTemplateId() == 115 && x >= 135 && x <= 2515 && y >= 312 && yNext < 276)) {
                        resetPoint();
                        return;
                    }
                    int[] rangemove = { 55, 58, 66, 75, 83, 93, 97 };
                    int index = speed - 6;
                    if (index < 0) {
                        index = 0;
                    }
                    if (Math.abs(x - xNext) > rangemove[index]) {
                        message = new Message(Cmd.RESET_POINT);
                        message.writeShort(x);
                        message.writeShort(y);
                        getSession().sendMessage(message);
                        return;
                    }
                    timeMoveUp = 0;
                    if (map.getTemplateId() == 111 || map.isArena()) {
                        if (xNext < 98) {
                            xNext = (x = 98);
                        }
                        if (xNext > 670) {
                            xNext = (x = 670);
                        }
                        if (map.getPhe1().contains(this) || map.getPhe2().contains(this)) {
                            yNext = (y = 264);
                        } else {
                            yNext = (y = 336);
                        }
                    } else if (yNext > map.template.h - 48 || xNext <= 24 || xNext >= map.template.w - 24) {
                        boolean isOk = map.template.tileTypeAt(xNext, yNext - 1, 64) || map.template.tileTypeAt(xNext - 1, yNext, 32) || map.template.tileTypeAt(xNext - 1, yNext, 2048);
                        if (!isOk) {
                            for (int i = 0; i < map.template.links.size(); ++i) {
                                NLink link = map.template.links.get(i);
                                if ((link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 <= xNext + dxlink && link.maxX1 >= xNext - dxlink && link.minY1 <= yNext + dylink && link.maxY1 >= yNext - dylink) ||
                                    (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 <= xNext + dxlink && link.maxX2 >= xNext - dxlink && link.minY2 <= yNext + dylink && link.maxY2 >= yNext - dylink)
                                ) {
                                    isOk = true;
                                    break;
                                }
                            }
                        }
                        if (!isOk) {
                            defaultPoint();
                            autoDie("doMove player");
                            return;
                        }
                    }
                    if (map == null) {
                        return;
                    }
                    if (x != xNext || y != yNext) {
                        Player playerMain = getPlayerMainControl();
                        for (int i = playerMain.effects.size() - 1; i >= 0; --i) {
                            if (playerMain.effects.get(i).template.type == 12) {
                                removeEffect(effects.get(i), true);
                            } else if (playerMain.effects.get(i).template.type == 2 || playerMain.effects.get(i).template.type == 3 || playerMain.effects.get(i).template.type == 14) {
                                resetPoint1(1000);
                                return;
                            }
                        }
                        x = xNext;
                        y = yNext;
                        if (map.template.tileTypeAt(x, y, 2)) {
                            countAttUp = 0;
                        }
                        if (!isInvisible) {
                            sendMove();
                        }
                        if (playercopy != null) {
                            int dd;
                            if (xNext > x) {
                                dd = -25;
                            } else if (xNext < x) {
                                dd = 25;
                            } else {
                                dd = 0;
                            }
                            playercopy.x = (short) (x + dd);
                            playercopy.y = y;
                            if (map.template.tileTypeAt(x, y, 2)) {
                                if (map.template.tileTypeAt(x + 24, y, 2)) {
                                    playercopy.x = (short) (x + 24);
                                } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                                    playercopy.x = (short) (x - 24);
                                } else if (!map.template.tileTypeAt(playercopy.x, playercopy.y, 2)) {
                                    playercopy.x = x;
                                }
                                playercopy.y = y;
                            }
                            if (playercopy.x < 0) {
                                playercopy.x = 12;
                            }
                            if (playercopy.x >= map.template.w) {
                                playercopy.x = (short) (map.template.w - 12);
                            }
                            if (isMainchar) {
                                message = new Message(Cmd.PLAYER_MOVE);
                                message.writeInt(playercopy.playerId);
                                message.writeShort(playercopy.x);
                                message.writeShort(playercopy.y);
                                playercopy.sendLimitSpace(message, false);
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(getStringBaseInfo(), e);
                }
                return;
            }
        }
        try {
            message = new Message(Cmd.RESET_POINT);
            message.writeShort(x);
            message.writeShort(y);
            getSession().sendMessage(message);
        } catch (Exception e) {
        }
    }

    public void doMove(Message message) {
        if (isChangeMap) {
            lastMoveX = 0;
            lastMoveY = 0;
            return;
        }
        int speed = this.speed;
        if (itemBodys[4] != null) {
            if (itemBodys[4].template.itemTemplateId == 485) {
                speed += 3;
            } else {
                speed += 2;
            }
        }
        if (status != ME_DIE && getHp() > 0) {
            if (capcha == null) {
                if (speed > 12) {
                    speed = 12;
                }
                try {
                    long time = System.currentTimeMillis();
                    if (time - getSession().timeSend < 100L) {
                        NJUtil.sleep(time - getSession().timeSend);
                    }
                    getSession().timeSend = System.currentTimeMillis();
                    isMove = true;
                    short xNext;
                    try {
                        xNext = message.readShort();
                    } catch (Exception e) {
                        return;
                    }
                    short yNext = y;
                    try {
                        yNext = message.readShort();
                    } catch (Exception e) {
                    }
                    int dxx = x + xmove - xNext;
                    int dyy = y + ymove - yNext;
                    lastMoveX = xNext;
                    lastMoveY = yNext;
                    if (xNext == x && yNext == y) {
                        return;
                    }
                    if (Math.abs(dxx) < 20 && Math.abs(dyy) < 20) {
                        return;
                    }
                    xmove = 0;
                    ymove = 0;
                    lastMoveX = 0;
                    lastMoveY = 0;
                    if (xNext < 0 || yNext < 0 || xNext > map.template.w || yNext > map.template.h) {
                        return;
                    }
                    if ((map.getTemplateId() == 114 && ((x >= 98 && x <= 912 && y >= 550 && yNext < 614) || (x >= 912 && x <= 1032 && y >= 618 && yNext < 614))) || (map.getTemplateId() == 115 && x >= 135 && x <= 2515 && y >= 312 && yNext < 276)) {
                        resetPoint();
                        LOGGER.info("KEO NGUOC LAI NE");
                        return;
                    }
                    int[] rangemove = { 55, 58, 66, 75, 83, 93, 97 };
                    int index = speed - 6;
                    if (index < 0) {
                        index = 0;
                    }
                    if (Math.abs(x - xNext) > rangemove[index]) {
                        message = new Message(Cmd.RESET_POINT);
                        message.writeShort(x);
                        message.writeShort(y);
                        getSession().sendMessage(message);
                        return;
                    }
                    timeMoveUp = 0;
                    if (map.getTemplateId() == 111 || map.isArena()) {
                        if (xNext < 98) {
                            xNext = (x = 98);
                        }
                        if (xNext > 670) {
                            xNext = (x = 670);
                        }
                        if (map.getPhe1().contains(this) || map.getPhe2().contains(this)) {
                            yNext = (y = 264);
                        } else {
                            yNext = (y = 336);
                        }
                    } else if (yNext > map.template.h - 48 || xNext <= 24 || xNext >= map.template.w - 24) {
                        boolean isOk = map.template.tileTypeAt(xNext, yNext - 1, 64) || map.template.tileTypeAt(xNext - 1, yNext, 32) || map.template.tileTypeAt(xNext - 1, yNext, 2048);
                        if (!isOk) {
                            for (int i = 0; i < map.template.links.size(); ++i) {
                                NLink link = map.template.links.get(i);
                                if ((link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 <= xNext + dxlink && link.maxX1 >= xNext - dxlink && link.minY1 <= yNext + dylink && link.maxY1 >= yNext - dylink) ||
                                    (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 <= xNext + dxlink && link.maxX2 >= xNext - dxlink && link.minY2 <= yNext + dylink && link.maxY2 >= yNext - dylink)
                                ) {
                                    isOk = true;
                                    break;
                                }
                            }
                        }
                        if (!isOk) {
                            defaultPoint();
                            autoDie("doMove player");
                            return;
                        }
                    }
                    if (map == null) {
                        return;
                    }
                    if (x != xNext || y != yNext) {
                        Player playerMain = getPlayerMainControl();
                        for (int i = playerMain.effects.size() - 1; i >= 0; --i) {
                            if (playerMain.effects.get(i).template.type == 12) {
                                removeEffect(effects.get(i), true);
                            } else if (playerMain.effects.get(i).template.type == 2 || playerMain.effects.get(i).template.type == 3 || playerMain.effects.get(i).template.type == 14) {
                                resetPoint1(1000);
                                return;
                            }
                        }
                        x = xNext;
                        y = yNext;
                        if (map.template.tileTypeAt(x, y, 2)) {
                            countAttUp = 0;
                        }
                        if (!isInvisible) {
                            sendMove();
                        }
                        if (playercopy != null) {
                            int dd;
                            if (xNext > x) {
                                dd = -25;
                            } else if (xNext < x) {
                                dd = 25;
                            } else {
                                dd = 0;
                            }
                            playercopy.x = (short) (x + dd);
                            playercopy.y = y;
                            if (map.template.tileTypeAt(x, y, 2)) {
                                if (map.template.tileTypeAt(x + 24, y, 2)) {
                                    playercopy.x = (short) (x + 24);
                                } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                                    playercopy.x = (short) (x - 24);
                                } else if (!map.template.tileTypeAt(playercopy.x, playercopy.y, 2)) {
                                    playercopy.x = x;
                                }
                                playercopy.y = y;
                            }
                            if (playercopy.x < 0) {
                                playercopy.x = 12;
                            }
                            if (playercopy.x >= map.template.w) {
                                playercopy.x = (short) (map.template.w - 12);
                            }
                            if (isMainchar) {
                                message = new Message(Cmd.PLAYER_MOVE);
                                message.writeInt(playercopy.playerId);
                                message.writeShort(playercopy.x);
                                message.writeShort(playercopy.y);
                                playercopy.sendLimitSpace(message, false);
                            }
                        }
                    }
                } catch (Exception e) {
                }
                return;
            }
        }
        try {
            message = new Message(Cmd.RESET_POINT);
            message.writeShort(x);
            message.writeShort(y);
            getSession().sendMessage(message);
            if (status == ME_DIE || getHp() <= 0) {
                message = new Message(Cmd.PLAYER_DIE);
                message.writeInt(playerId);
                message.writeByte(getPlayerMainControl().pk);
                message.writeShort(getPlayerMainControl().x);
                message.writeShort(getPlayerMainControl().y);
                sendToPlayer(message, true);
            }
        } catch (Exception e) {
        }
    }

    public void sendTeleport(int type, int xNext, int yNext, boolean sendMe, boolean sendOther, String place) {
        if (GameServer.isServerLocal()) {
            LOGGER.info("Send teleport > {} > {}", sendMe, place);
        }
        try {
            Message msg = new Message(Cmd.TELEPORT_TRAIN);
            msg.writeByte(type);
            x = (short) xNext;
            y = (short) yNext;
            if (type >= 0) {
                msg.writeInt(playerId);
                msg.writeShort(xNext);
                msg.writeShort(yNext);
            } else {
                msg.writeInt(rangeTrain);
            }
            getSession().sendMessage(msg);
            if (sendOther) {
                try {
                    if (map == null) {
                        return;
                    }
                    for (int i = 0; i < map.players.size(); ++i) {
                        try {
                            Session s = map.players.get(i).getSession();
                            if (map.players.get(i).playerId != playerId && s != null) {
                                if (!s.isVersion116()) {
                                    Message message = new Message(Cmd.PLAYER_MOVE);
                                    message.writeInt(playerId);
                                    message.writeShort(x);
                                    message.writeShort(y);
                                    s.sendMessage(message);
                                } else {
                                    s.sendMessage(msg);
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e) {
                }
            }
            if (playercopy != null && type >= 0) {
                int dd;
                if (xNext > x) {
                    dd = -25;
                } else if (xNext < x) {
                    dd = 25;
                } else {
                    dd = 0;
                }
                playercopy.x = (short) (x + dd);
                playercopy.y = y;
                if (map.template.tileTypeAt(x, y, 2)) {
                    if (map.template.tileTypeAt(x + 24, y, 2)) {
                        playercopy.x = (short) (x + 24);
                    } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                        playercopy.x = (short) (x - 24);
                    } else if (!map.template.tileTypeAt(playercopy.x, playercopy.y, 2)) {
                        playercopy.x = x;
                    }
                    playercopy.y = y;
                }
                if (playercopy.x < 0) {
                    playercopy.x = 12;
                }
                if (playercopy.x >= map.template.w) {
                    playercopy.x = (short) (map.template.w - 12);
                }
                if (isMainchar) {
                    Message message = new Message(Cmd.PLAYER_MOVE);
                    message.writeInt(playercopy.playerId);
                    message.writeShort(playercopy.x);
                    message.writeShort(playercopy.y);
                    playercopy.sendToPlayer(message, false);
                }
            }
        } catch (Exception e) {
        }
    }

    public void doTelePort(Message message) {
        try {
            if (status != ME_DIE) {
                if (getHp() != 0) {
                    short idmons;
                    if (conn.clientType == GameServer.CLIENT_JAVA) {
                        if (conn.isVersion131()) {
                            idmons = (short) message.dis.readUnsignedByte();
                        } else {
                            idmons = message.dis.readShort();
                        }
                    } else {
                        idmons = (short) message.dis.readUnsignedByte();
                    }
                    Npc npc = map.npcs.get(idmons);
                    Item item = findItemBag(572);
                    if (npc == null || item == null) {
                        if (item == null) {
                            sendTeleport(-2, x, y, false, false, "doTelePort");
                        }
                        sendTeleport(0, x, y, false, false, "doTelePort1");
                        return;
                    }
                    short xNext = npc.pointx;
                    short yNext = npc.pointy;
                    if (rangeTrain != 30000) {
                        int dsx = NJUtil.distance(xStartAuto, xNext);
                        int dsy = NJUtil.distance(yStartAuto, yNext);
                        if (dsx > rangeTrain || dsy > rangeTrain) {
                            sendTeleport(0, xStartAuto, yStartAuto, false, true, "doTelePort3");
                            return;
                        }
                    }
                    x = xNext;
                    y = yNext;
                    lastMoveX = x;
                    lastMoveY = y;
                    sendTeleport(0, x, y, false, true, "doTelePort4");
                    return;
                }
            }
            try {
                message = new Message(Cmd.RESET_POINT);
                message.writeShort(x);
                message.writeShort(y);
                getSession().sendMessage(message);
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public void autoDie(String pos) {
        if (GameServer.isServerLocal()) {
            LOGGER.info("Auto die > " + pos);
        }
        getPlayerMainControl().hp = 0;
        checkDie(-1);
    }

    public void sendLimitSpace(Message message, boolean isSendMe) {
        if (map == null) {
            return;
        }
        for (int i = 0; i < map.players.size(); ++i) {
            try {
                Player p = map.players.get(i);
                Session s = p.getSession();
                if (isSendMe || p.playerId != playerId) {
                    if (s != null) {
                        /*if (p.getSession().isGPRS) {
                            int dx = NJUtil.distance(p.x, x);
                            int dy = NJUtil.distance(p.y, y);
                        }*/
                        map.players.get(i).getSession().sendMessage(message);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    public boolean isPlayerNam() {
        return gender == 1;
    }

    public boolean isPlayerNu() {
        return gender == 0;
    }

    public void sendUpdateInfoMe() {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.UPDATE_INFO_ME);
            message.writeInt(playerId);
            if (playerMain.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMain.clan.name);
                message.writeByte(playerMain.clan.getType(playerMain.name));
            }
            message.writeByte(playerMain.getTaskFinish());
            message.writeByte(playerMain.gender);
            if (playerMain.itemBodys != null && playerMain.itemBodys[11] != null) {
                message.writeShort(playerMain.itemBodys[11].template.part);
            } else {
                message.writeShort(playerMain.headId);
            }
            message.writeByte(playerMain.speed);
            message.writeUTF(playerMain.name);
            message.writeByte(playerMain.pk);
            message.writeByte(playerMain.getTypePk());
            message.writeInt(playerMain.hp_full);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.mp_full);
            message.writeInt(playerMain.mp);
            message.writeLong(playerMain.exp);
            message.writeLong(playerMain.exp_down);
            message.writeShort(playerMain.eff5BuffHp);
            message.writeShort(playerMain.eff5BuffMp);
            message.writeByte(playerMain.classId);
            message.writeShort(playerMain.potential_point);
            message.writeShort(playerMain.p_strength);
            message.writeShort(playerMain.p_agile);
            message.writeInt(playerMain.p_hp);
            message.writeInt(playerMain.p_mp);
            message.writeShort(playerMain.skill_point);
            message.writeByte(playerMain.skills.size());
            for (int i = 0; i < playerMain.skills.size(); ++i) {
                message.writeShort(playerMain.skills.get(i).skillId);
            }
            message.writeInt(getXu());
            message.writeInt(getYen());
            message.writeInt(getLuong());
            message.writeByte(bag);
            int countBall = 0;
            for (Item itemBag : itemBags) {
                if (itemBag != null) {
                    if (itemBag.template.type == 22) {
                        ++countBall;
                    }
                    message.writeShort(itemBag.template.itemTemplateId);
                    message.writeBoolean(itemBag.isLock);
                    if (itemBag.isTypeBody() || itemBag.isTypeMon() || (itemBag.isTypeGem() && conn.isVersion144())) {
                        message.writeByte(itemBag.upgrade);
                    }
                    message.writeBoolean(itemBag.expires != -1L);
                    message.writeShort(itemBag.quantity);
                } else {
                    message.writeShort(-1);
                }
            }
            if (getSession().version >= 105) {
                for (int j = 0; j < playerMain.itemBodys.length; ++j) {
                    if (playerMain.itemBodys[j] != null) {
                        message.writeShort(playerMain.itemBodys[j].template.itemTemplateId);
                        message.writeByte(playerMain.itemBodys[j].upgrade);
                        message.writeByte(playerMain.itemBodys[j].sys);
                    } else {
                        message.writeShort(-1);
                    }
                }
            } else {
                for (int j = 0; j < playerMain.itemBodys.length; ++j) {
                    if (playerMain.itemBodys[j] != null) {
                        message.writeShort(playerMain.itemBodys[j].template.itemTemplateId);
                        message.writeByte(playerMain.itemBodys[j].upgrade);
                        message.writeByte(playerMain.itemBodys[j].sys);
                    }
                }
            }
            message.writeBoolean(isMainchar);
            message.writeBoolean(isNhanban());
            short[] idPartTHoiTrang = getIdPartThoiTrang();
            for (short i : idPartTHoiTrang) {
                message.writeShort(i);
            }
            NJUtil.sendMessage(getSession(), message);
            if (countBall >= 7) {
                ServerController.doRequestIcon(getSession(), 1670);
                ServerController.doRequestIcon(getSession(), 1671);
                ServerController.doRequestIcon(getSession(), 1672);
                ServerController.doRequestIcon(getSession(), 1673);
                ServerController.doRequestIcon(getSession(), 1674);
                ServerController.doRequestIcon(getSession(), 1675);
                ServerController.doRequestIcon(getSession(), 1676);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateInfoMe2Char() {
        try {
            Message message = new Message(Cmd.UPDATE_INFO_CHAR);
            Player playerMain = getPlayerMainControl();
            message.writeInt(playerId);
            if (playerMain.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMain.clan.name);
                message.writeByte(playerMain.clan.getType(playerMain.name));
            }
            message.writeBoolean(playerMain.isInvisible);
            message.writeByte(playerMain.getTypePk());
            message.writeByte(playerMain.classId);
            message.writeByte(playerMain.gender);
            if (playerMain.itemBodys != null && playerMain.itemBodys[11] != null) {
                message.writeShort(playerMain.itemBodys[11].template.part);
            } else {
                message.writeShort(playerMain.headId);
            }
            message.writeUTF(playerMain.name);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeByte(playerMain.level);
            if (playerMain.playerId < 0 && !playerMain.isNhanban()) {
                message.writeShort(playerMain.weaponId);
                message.writeShort(playerMain.bodyId);
                message.writeShort(playerMain.legId);
                message.writeByte(-1);
            } else {
                message.writeShort(playerMain.getPartBody(1));
                message.writeShort(playerMain.getPartBody(2));
                message.writeShort(playerMain.getPartBody(6));
                int npcId = -1;
                if (playerMain.itemBodys[10] != null) {
                    if (playerMain.itemBodys[10].template.itemTemplateId == 246) {
                        npcId = 70;
                    } else if (playerMain.itemBodys[10].template.itemTemplateId == 419) {
                        npcId = 122;
                    }
                }
                message.writeByte(npcId);
            }
            message.writeShort(playerMain.x);
            message.writeShort(playerMain.y);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeByte(playerMain.effects.size());
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                int timeRemain = (int) (System.currentTimeMillis() / 1000L - playerMain.effects.get(i).timeStart);
                message.writeByte(playerMain.effects.get(i).template.id);
                message.writeInt(timeRemain);
                message.writeInt(playerMain.effects.get(i).timeLength);
                message.writeShort(playerMain.effects.get(i).param);
            }
            message.writeBoolean(playerMain.isMainchar);
            message.writeBoolean(isNhanban());
            short[] idPartTHoiTrang = getIdPartThoiTrang();
            for (short i : idPartTHoiTrang) {
                message.writeShort(i);
            }
            sendToPlayer(message, false);
            loadAoChoang();
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void addEffect(Effect eff) {
        if (eff.template.type == 1) {
            eff.timeLength -= timeDownFire;
        } else if (eff.template.type == 2) {
            eff.timeLength -= timeDownIce;
        } else if (eff.template.type == 3) {
            eff.timeLength -= timeDownWind;
        }
        if (eff.timeLength < 0) {
            return;
        }
        try {
            Player playerMain = getPlayerMainControl();
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.type == eff.template.type) {
                    if (eff.template.id >= playerMain.effects.get(i).template.id) {
                        if (eff.template.id == playerMain.effects.get(i).template.id) {
                            if (playerMain.effects.get(i).timeStart == -1) {
                                if (eff.param <= playerMain.effects.get(i).param) {
                                    return;
                                }
                            } else {
                                int timeRemain = (int) (playerMain.effects.get(i).timeLength - (System.currentTimeMillis() / 1000L - playerMain.effects.get(i).timeStart));
                                if (eff.timeLength < timeRemain) {
                                    return;
                                }
                            }
                        }
                        playerMain.effects.set(i, eff);
                        int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
                        Message message = NJUtil.messageSubCommand(Cmd.ME_EDIT_EFFECT);
                        message.writeByte(eff.template.id);
                        message.writeInt(timeRemain);
                        message.writeInt(eff.timeLength);
                        message.writeShort(eff.param);
                        NJUtil.sendMessage(getSession(), message);
                        message = NJUtil.messageSubCommand(Cmd.PLAYER_EDIT_EFFECT);
                        message.writeInt(playerId);
                        message.writeByte(eff.template.id);
                        message.writeInt(timeRemain);
                        message.writeInt(eff.timeLength);
                        message.writeShort(eff.param);
                        sendToPlayer(message, false);
                    }
                    playerMain.updateData();
                    return;
                }
            }
            playerMain.effects.add(eff);
            int timeRemain2 = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain2);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            if (eff.template.type == 1 || eff.template.type == 2 || eff.template.type == 3 || eff.template.type == 14) {
                message.writeShort(playerMain.x);
                message.writeShort(playerMain.y);
            }
            NJUtil.sendMessage(getSession(), message);
            message = NJUtil.messageSubCommand(Cmd.PLAYER_ADD_EFFECT);
            message.writeInt(playerMain.playerId);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain2);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            if (eff.template.type == 1 || eff.template.type == 2 || eff.template.type == 3 || eff.template.type == 14) {
                message.writeShort(playerMain.x);
                message.writeShort(playerMain.y);
            }
            sendToPlayer(message, false);
            playerMain.updateData();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void addEffectPlayer(int idEff, int time, int timeWait, int top_bottom) {
        try {
            Message message = new Message(Cmd.EFF_DYNAMIC_TOOL);
            message.dos.writeByte(0);
            message.dos.writeByte(0);
            message.dos.writeInt(playerId);
            message.dos.writeShort(idEff);
            message.dos.writeInt(time);
            message.dos.writeByte(timeWait);
            message.dos.writeByte(top_bottom);
            conn.sendMessage(message);
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void addExpClan(int value) {
        if (clan != null) {
            clan.exp += value;
            pointClan += value;
            pointClanWeek += value;
            Member mem = clan.getMember(name);
            if (mem != null) {
                mem.pointClan = pointClan;
                mem.pointClanWeek = pointClanWeek;
                NJUtil.sendServer(getSession(), AlertFunction.YOU_GET(getSession()) + " " + value + " " + AlertFunction.POINT_CLAN(getSession()));
            }
            Database.saveClan(clan);
        }
    }

    public void addItem(Item item, int typeUI, int indexUI) {
        Player playerMain = getPlayerMainControl();
        item.typeUI = typeUI;
        item.indexUI = indexUI;
        if (typeUI == 5) {
            playerMain.itemBodys[item.indexUI] = item;
        } else if (typeUI == 3) {
            itemBags[item.indexUI] = item;
        } else if (typeUI == 4) {
            itemBoxs[item.indexUI] = item;
        } else if (typeUI == 41) {
            playerMain.itemMons[item.indexUI] = item;
        }
    }

    public boolean addItemToBag(Item item, boolean isShowDialog) {
        try {
            if (item.template.isUpToUp && item.expires == -1L) {
                int indexUI = -1;
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] != null && itemBags[i].template.equals(item.template) && itemBags[i].isLock == item.isLock) {
                        itemBags[i].quantity += item.quantity;
                        //Database.updateItem(itemBags[i]);
                        sendUpdateItemBag(i, item.quantity);
                        return true;
                    }
                    if (indexUI == -1 && itemBags[i] == null) {
                        indexUI = i;
                    }
                }
                if (indexUI == -1) {
                    if (isShowDialog) {
                        NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    }
                    return false;
                }
                Item it = item.cloneItem();
                it.playerId = playerId;
                it.typeUI = 3;
                it.indexUI = indexUI;
                it.quantity = item.quantity;
                addItem(it, it.typeUI, it.indexUI);
                sendAddItemBag(itemBags[indexUI]);
            } else {
                Item[] items = new Item[item.quantity];
                for (int i = 0; i < items.length; ++i) {
                    items[i] = item.cloneItem();
                    items[i].playerId = playerId;
                    items[i].typeUI = 3;
                    items[i].quantity = 1;
                }
                int countFree = 0;
                for (int j = 0; j < itemBags.length; ++j) {
                    if (itemBags[j] == null) {
                        items[countFree].indexUI = j;
                        if (++countFree >= items.length) {
                            break;
                        }
                    }
                }
                if (items.length > countFree) {
                    if (isShowDialog) {
                        NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    }
                    return false;
                }
                for (Item value : items) {
                    addItem(value, value.typeUI, value.indexUI);
                    sendAddItemBag(value);
                }
            }
        } catch (Exception e) {
        } finally {
            ghiloghack(item.getTemplateId());
        }
        if (item.template.type == 22) {
            int countBall = 0;
            for (Item itemBox : itemBoxs) {
                if (itemBox != null && itemBox.template.type == 22) {
                    ++countBall;
                }
            }
            for (Item itemBag : itemBags) {
                if (itemBag != null && itemBag.template.type == 22) {
                    ++countBall;
                }
            }
            if (countBall >= 7) {
                ServerController.doRequestIcon(getSession(), 1670);
                ServerController.doRequestIcon(getSession(), 1671);
                ServerController.doRequestIcon(getSession(), 1672);
                ServerController.doRequestIcon(getSession(), 1673);
                ServerController.doRequestIcon(getSession(), 1674);
                ServerController.doRequestIcon(getSession(), 1675);
                ServerController.doRequestIcon(getSession(), 1676);
            }
        }
        return true;
    }

    public boolean addItemToBag(Item item) {
        try {
            if (item.template.isUpToUp && item.expires == -1L) {
                int indexUI = -1;
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] != null && itemBags[i].template.itemTemplateId == item.template.itemTemplateId && itemBags[i].isLock == item.isLock) {
                        itemBags[i].quantity += item.quantity;
                        sendUpdateItemBag(i, item.quantity);
                        return true;
                    }
                    if (indexUI == -1 && itemBags[i] == null) {
                        indexUI = i;
                    }
                }
                if (indexUI == -1) {
                    NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    return false;
                }
                item.playerId = playerId;
                item.typeUI = 3;
                item.indexUI = indexUI;
                addItem(item, item.typeUI, item.indexUI);
                sendAddItemBag(itemBags[indexUI]);
            } else {
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] == null) {
                        item.playerId = playerId;
                        item.typeUI = 3;
                        item.indexUI = i;
                        addItem(item, item.typeUI, item.indexUI);
                        sendAddItemBag(itemBags[i]);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            return true;
        } finally {
            ghiloghack(item.getTemplateId());
        }
        ghiloghack(item.getTemplateId());
        return true;
    }

    public boolean addItemToBagNoDialog(Item item) {
        if (item == null || item.template == null) {
            return false;
        }
        try {
            if (item.template.isUpToUp && item.expires == -1L) {
                int indexUI = -1;
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] != null && itemBags[i].template.equals(item.template) && itemBags[i].isLock == item.isLock) {
                        itemBags[i].quantity += item.quantity;
                        sendUpdateItemBag(i, item.quantity);
                        return true;
                    }
                    if (indexUI == -1 && itemBags[i] == null) {
                        indexUI = i;
                    }
                }
                if (indexUI == -1) {
                    return false;
                }
                Item it = item.cloneItem();
                it.playerId = playerId;
                it.typeUI = 3;
                it.indexUI = indexUI;
                it.quantity = item.quantity;
                addItem(it, it.typeUI, it.indexUI);
                sendAddItemBag(itemBags[indexUI]);
            } else {
                Item[] items = new Item[item.quantity];
                for (int i = 0; i < items.length; ++i) {
                    items[i] = item.cloneItem();
                    items[i].playerId = playerId;
                    items[i].typeUI = 3;
                    items[i].quantity = 1;
                }
                int countFree = 0;
                for (int j = 0; j < itemBags.length; ++j) {
                    if (itemBags[j] == null) {
                        items[countFree].indexUI = j;
                        if (++countFree >= items.length) {
                            break;
                        }
                    }
                }
                if (items.length > countFree) {
                    return false;
                }
                for (Item value : items) {
                    addItem(value, value.typeUI, value.indexUI);
                    sendAddItemBag(value);
                }
            }
        } catch (Exception e) {
        } finally {
            ghiloghack(item.getTemplateId());
        }
        if (item.template.type == 22) {
            int countBall = 0;
            for (Item itemBox : itemBoxs) {
                if (itemBox != null && itemBox.template.type == 22) {
                    ++countBall;
                }
            }
            for (Item itemBag : itemBags) {
                if (itemBag != null && itemBag.template.type == 22) {
                    ++countBall;
                }
            }
            if (countBall >= 7) {
                ServerController.doRequestIcon(getSession(), 1670);
                ServerController.doRequestIcon(getSession(), 1671);
                ServerController.doRequestIcon(getSession(), 1672);
                ServerController.doRequestIcon(getSession(), 1673);
                ServerController.doRequestIcon(getSession(), 1674);
                ServerController.doRequestIcon(getSession(), 1675);
                ServerController.doRequestIcon(getSession(), 1676);
            }
        }
        return true;
    }

    public void addPlayer(Player playerMap) {
        try {
            if (isNhanban()) {
                return;
            }
            Player pp = playerMap.getPlayerMainControl();
            Message message = new Message(Cmd.PLAYER_ADD);
            playerLoadAll(message, playerMap);
            NJUtil.sendMessage(getSession(), message);
            if (pp.itemBodys != null && pp.itemBodys[12] != null) {
                message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_AO_CHOANG);
                message.writeInt(playerMap.playerId);
                message.writeInt(pp.hp);
                message.writeInt(pp.hp_full);
                message.writeShort(pp.itemBodys[12].template.itemTemplateId);
                NJUtil.sendMessage(getSession(), message);
            }
            if (pp.itemBodys != null && pp.itemBodys[13] != null) {
                message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_GIA_TOC);
                message.writeInt(playerMap.playerId);
                message.writeInt(pp.hp);
                message.writeInt(pp.hp_full);
                message.writeShort(pp.itemBodys[13].template.itemTemplateId);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void addPointClan(int pointAdd) {
        clan.exp += pointAdd;
        String[] strs = {
            Alert_VN.YOU_GET + " " + pointAdd + " " + Alert_VN.POINT_CLAN,
            Alert_EN.YOU_GET + " " + pointAdd + " " + Alert_EN.POINT_CLAN
        };
        clan.sendAlert(strs, name);
        Database.saveClan(clan);
    }

    public void addPointUydanh(int point) {
        pointUyDanh += point;
        NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.POINT_UYDANH(getSession()), String.valueOf(point)));
    }

    public void addPriveEffectPlayer(int idEff, int time, int timeWait, int top_bottom) {
        try {
            Message message = new Message(Cmd.EFF_DYNAMIC_TOOL);
            message.dos.writeByte(0);
            message.dos.writeByte(0);
            message.dos.writeInt(playerId);
            message.dos.writeShort(idEff);
            message.dos.writeInt(time);
            message.dos.writeByte(timeWait);
            message.dos.writeByte(top_bottom);
            conn.sendMessage(message);
        } catch (Exception e) {
        }
    }

    public synchronized void addRecentPK(Player p) {
        if (lsRecentPK.size() >= 40) {
            lsRecentPK.removeElementAt(0);
        }
        lsRecentPK.addElement(p);
    }

    public Point attackNpc(Vector<Byte> npcIndexs, Point point) {
        Message message;
        try {
            Player playerMain = getPlayerMainControl();
            boolean isXuat = false;
            boolean isUpdame;
            boolean isChiMang = isUpdame = playerMain.checkClearEffect11();
            playerMain.checkClearEffect12();
            if (playerMain.getSkillValue().template.type == 0 || playerMain.getSkillValue().template.type == 2 || playerMain.getSkillValue().template.type == 4) {
                return null;
            }
            if (npcIndexs.size() == 0) {
                return point;
            }
            long t = System.currentTimeMillis();
            for (int i = 0; i < npcIndexs.size(); ++i) {
                byte npcId = npcIndexs.get(i);
                if (npcId < 0 || (npcId >= map.npcs.size() && privateNpc == null)) {
                    continue;
                }
                long t2 = System.currentTimeMillis() - t;
                Npc npc = null;
                if (npcId == map.npcs.size() && privateNpc != null) {
                    npc = privateNpc;
                } else if (npcId < map.npcs.size()) {
                    npc = map.npcs.get(npcId);
                }
                if (npc != null) {
                    if (npc.status == Npc.STATUS_DIE) {
                        message = new Message(Cmd.NPC_DIE);
                        message.writeByte(npc.npcId);
                        NJUtil.sendMessage(getSession(), message);
                    } else if (!npc.isTuongGiac() || playerMain.itemBodys[11] != null) {
                        if ((!npc.isTenTrom() && !npc.isDauLinhDaoTac()) || !playerMain.isNhanban()) {
                            long t3 = System.currentTimeMillis() - t;
                            long t4 = 0L;
                            if (point == null) {
                                if (!isAttack(npc, i)) {
                                    if (npc.status != Npc.STATUS_DIE) {
                                        message = new Message(Cmd.NPC_MISS);
                                        message.writeByte(npc.npcId);
                                        message.writeInt(npc.hp);
                                        sendToPlayer(message, true);
                                    }
                                    continue;
                                } else {
                                    point = new Point(npc.pointx, npc.pointy);
                                    t4 = System.currentTimeMillis() - t;
                                }
                            } else if (!isAttackLan(point.x, point.y, npc.pointx, npc.pointy, true)) {
                                if (npc.status != Npc.STATUS_DIE) {
                                    message = new Message(Cmd.NPC_MISS);
                                    message.writeByte(npc.npcId);
                                    message.writeInt(npc.hp);
                                    sendToPlayer(message, true);
                                }
                                continue;
                            }
                            int dameHit = playerMain.getDam(npc);
                            long t6 = System.currentTimeMillis() - t;
                            if (dameHit > 0 && doAttacNpc(npc, dameHit, -1 * NJUtil.randomNumber(11), false, isChiMang, isUpdame)) {
                                if (playerMain.sumonHide != null) {
                                    if (playerMain.sumonHide.attNpcs == null) {
                                        playerMain.sumonHide.attNpcs = new Vector<>();
                                    }
                                    if (!playerMain.sumonHide.attNpcs.contains(npc)) {
                                        playerMain.sumonHide.attNpcs.add(npc);
                                    }
                                }
                                if (playerMain.sumon != null && playerMain.sumon.isSumonAttack()) {
                                    playerMain.sumon.npcAttId = npc.npcId;
                                    if (!playerMain.sumon.template.isPetNew()) {
                                        if (playerMain.sumon.template.npcTemplateId == 70) {
                                            playerMain.sumon.dame = playerMain.dame_full / 3;
                                        } else if (sumon.template.npcTemplateId == 122) {
                                            playerMain.sumon.dame = playerMain.dame_full / 2;
                                        }
                                    } else {
                                        ItemOption iOpt = playerMain.itemBodys[10].options.get(0);
                                        if (iOpt != null) {
                                            playerMain.sumon.dame = iOpt.param;
                                        }
                                    }
                                }
                            }
                            long t7 = System.currentTimeMillis() - t;
                            if ((t2 > 100L || t3 > 100L || t4 > 100L || t6 > 100L || t7 > 100L) && !isXuat) {
                                isXuat = true;
                            }
                        }
                    }
                }
            }
            if (playercopy != null && isMainchar && playercopy.isAttack(false)) {
                playercopy.attackNpc(npcIndexs, point);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return point;
    }

    public Point attackPlayer(Vector<Integer> playerIndexs, Point point) {
        Vector<Attack> atts = new Vector<>();
        Message message;
        try {
            Player playerMain = getPlayerMainControl();
            if (map.isDunVD) {
                if (map.isGiaiDauNinjaTaiNang()) {
                    DunVD dun = (DunVD) map;
                    if (!dun.phe1.contains(this) && !dun.phe2.contains(this)) {
                        return null;
                    }
                } else if (map.isGiaiDauNinjaDenhat()) {
                    DunVDClass dun2 = (DunVDClass) map;
                    if (!dun2.phe1.contains(this) && !dun2.phe2.contains(this)) {
                        return null;
                    }
                }
            }
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.type == 10) {
                    return null;
                }
            }
            boolean isUpdame;
            boolean isChiMang;
            isChiMang = isUpdame = playerMain.checkClearEffect11();
            playerMain.checkClearEffect12();
            if (playerMain.getSkillValue().template.type == 0 || playerMain.getSkillValue().template.type == 3 || playerMain.getSkillValue().template.type == 2 || playerMain.getSkillValue().template.type == 4) {
                return null;
            }
            if (playerIndexs.size() == 0) {
                return point;
            }
            for (Integer playerIndex : playerIndexs) {
                Player player = map.getPlayer(playerIndex);
                if (player != null && player.getPlayerMainControl().status != ME_DIE && !player.getPlayerMainControl().isInvisible && !player.isNhanban()) {
                    if (!map.isMapTrain() || player.getTypePk() == PK_DOSAT || playerMain.getTypePk() != PK_DOSAT || !player.isKhangDoSat()) {
                        if (playerMain.getKillPlayer() == null || !playerMain.getKillPlayer().player.equals(player) || !player.isKhangDoSat()) {
                            boolean isNotAtt = false;
                            for (int i = 0; i < player.getPlayerMainControl().effects.size(); ++i) {
                                if (player.getPlayerMainControl().effects.get(i).template.type == 10) {
                                    isNotAtt = true;
                                    break;
                                }
                            }
                            if (!isNotAtt) {
                                if (point == null) {
                                    if (!isAttackPlayer(player)) {
                                        atts.add(new Attack(player, 0, -1));
                                        continue;
                                    }
                                    point = new Point(player.x, player.y);
                                } else if (!isAttackLan(point.x, point.y, player.x, player.y, false)) {
                                    atts.add(new Attack(player, 0, -1));
                                    continue;
                                }
                                int typeAtt = -1;
                                if (((playerMain.getKillPlayer() != null && playerMain.getKillPlayer().player.equals(player)) || (player.getKillPlayer() != null && player.getKillPlayer().player.equals(this) && !map.isLang())) && !map.isLang() && !player.map.isLang()) {
                                    typeAtt = playerMain.playerId;
                                } else if ((playerMain.getTypePk() == PK_DOSAT || player.getTypePk() == PK_DOSAT || (playerMain.getTypePk() == PK_NHOM && player.getTypePk() == PK_NHOM)) && !map.isLang() && !player.map.isLang() && !isTeam(player)) {
                                    typeAtt = playerMain.playerId;
                                } else if (testSkill != null && testSkill.player1 != null && testSkill.player2 != null) {
                                    typeAtt = testSkill.isTesting(this, player);
                                }
                                if ((map.template.isMapChienTruong() || map.getTemplateId() == 110 || map.getTemplateId() == 111 || map.getTemplateId() == 130) && player.getTypePk() != getTypePk()) {
                                    typeAtt = playerMain.playerId;
                                }
                                if (typeAtt != -1) {
                                    int dameHit;
                                    if (playerMain.iDamage > 0) {
                                        dameHit = playerMain.iDamage;
                                        player.subHP(dameHit);
                                    } else {
                                        dameHit = doAttactPlayer(player, playerMain.dame_full - player.dameDown, false, isChiMang, isUpdame);
                                    }
                                    if (player.haveLongDenNgoiSao()) {
                                        dameHit /= 2;
                                    }
                                    if (dameHit != 0) {
                                        atts.add(new Attack(player, dameHit, typeAtt));
                                        if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 24) {
                                            if (NJUtil.probability(playerMain.getSkillValue().options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(5);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 2000;
                                                player.addEffect(eff);
                                            }
                                        } else if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 34) {
                                            if (NJUtil.probability(playerMain.getSkillValue().options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(5);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 4000;
                                                player.addEffect(eff);
                                            }
                                        } else if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 25) {
                                            if (NJUtil.probability(myskill.options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(6);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 1500;
                                                player.y = autoFall(player.x, atts.firstElement().playerHit.y);
                                                player.addEffect(eff);
                                            }
                                        } else if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 35) {
                                            if (NJUtil.probability(myskill.options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(6);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 3000;
                                                player.y = autoFall(player.x, player.y);
                                                player.addEffect(eff);
                                            }
                                        } else if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 26) {
                                            if (NJUtil.probability(myskill.options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                                Effect eff = new Effect();
                                                eff.template = ServerController.effTemplates.get(7);
                                                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                                eff.timeLength = 1000;
                                                player.y = autoFall(player.x, player.y);
                                                player.addEffect(eff);
                                            }
                                        } else if (playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 36 && NJUtil.probability(myskill.options.firstElement().param, 100 - playerMain.getSkillValue().options.firstElement().param) == 0) {
                                            Effect eff = new Effect();
                                            eff.template = ServerController.effTemplates.get(7);
                                            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                            eff.timeLength = 2000;
                                            player.y = autoFall(player.x, player.y);
                                            player.addEffect(eff);
                                        }
                                    }
                                    if (player.timeWait > 0) {
                                        player.endWait(null);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (atts.size() > 0 && playerMain.getSkillValue().template.skillTemplateId == 42) {
                if (playerMain.skillDao35 == 0) {
                    playerMain.skillDao35 = 1;
                    Effect eff2 = new Effect();
                    eff2.template = ServerController.effTemplates.get(18);
                    eff2.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff2.timeLength = 1500 + playerMain.timeKeepDao;
                    atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                    atts.firstElement().playerHit.addEffect(eff2);
                    playerMain.x = atts.firstElement().playerHit.x;
                    playerMain.y = atts.firstElement().playerHit.y;
                    playerMain.moveFastPlayer(atts.firstElement().playerHit.playerId);
                    atts.firstElement().playerHit.checkDie(atts.firstElement().typeAtt);
                    return null;
                }
                if (playerMain.skillDao35 == 1) {
                    playerMain.skillDao35 = 0;
                    int dameHit2 = atts.firstElement().playerHit.doAttactPlayer(playerMain, playerMain.dame_full, false, false, false);
                    if (dameHit2 > 0) {
                        atts.firstElement().playerHit.sendHaveAtt(dameHit2);
                        atts.firstElement().playerHit.checkDie(atts.firstElement().typeAtt);
                    }
                    return null;
                }
            }
            if (atts.size() > 0 && playerMain.getSkillValue().options.firstElement().optionTemplate.skillOptionTemplateId == 55) {
                Effect eff2 = new Effect();
                eff2.template = ServerController.effTemplates.get(18);
                eff2.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff2.timeLength = myskill.options.firstElement().param * 1000;
                atts.firstElement().playerHit.y = autoFall(atts.firstElement().playerHit.x, atts.firstElement().playerHit.y);
                atts.firstElement().playerHit.addEffect(eff2);
                return null;
            }
            sendShowAtt(atts);
            for (Attack att : atts) {
                att.playerHit.sendHaveAtt(att.dameHit);
                if (att.dameHit < 0) {
                    att.dameHit *= -1;
                }
                if (att.dameHit > 0) {
                    if (att.playerHit.hp <= 0 && att.playerHit.isPlayerArena()) {
                        map.setNamePlayerLose(att.playerHit.name);
                    }
                    if (att.playerHit.reactDame > 0) {
                        int ddx = NJUtil.distance(x, att.playerHit.x);
                        int ddy = NJUtil.distance(y, att.playerHit.y);
                        if (ddx < 100 && ddy < 100) {
                            att.dameHit = att.dameHit * 5 * att.playerHit.reactDame / 1000;
                            if (att.dameHit > 0) {
                                att.dameHit = att.playerHit.doAttactPlayer(this, att.dameHit, true, isChiMang, isUpdame);
                                if (att.dameHit > 0) {
                                    if (hp <= 0) {
                                        map.setNamePlayerLose(name);
                                    }
                                    sendHaveAtt(att.dameHit);
                                    checkDie(att.typeAtt);
                                }
                            }
                        }
                    }
                    att.playerHit.checkDie(att.typeAtt);
                    if (att.playerHit.hp <= 0 && !isControlCharNhanBan()) {
                        if (dailytask != null && dailytask.template.checkTask(this, 3)) {
                            checkTaskOrder(dailytask, 1);
                        }
                        if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                            MixedArena.updatePointKillPlayer(playerMain, att.playerHit);
                        }
                    }
                    if (taskMain != null && !isControlCharNhanBan() && ((taskMain.template.taskId == 13 && taskMain.index == 1 && att.playerHit.playerId == -11 && att.playerHit.status == ME_DIE) || (taskMain.template.taskId == 13 && taskMain.index == 2 && att.playerHit.playerId == -12 && att.playerHit.status == ME_DIE) || (taskMain.template.taskId == 13 && taskMain.index == 3 && att.playerHit.playerId == -10 && att.playerHit.status == ME_DIE))) {
                        if (map != null) {
                            map.goOutMap(att.playerHit);
                            NpcPlayer npcPlayer = map.npcPlayers.get(0);
                            npcPlayer.status = NpcPlayer.A_STAND;
                            message = NJUtil.messageSubCommand(Cmd.NPC_PLAYER_UPDATE);
                            message.writeByte(npcPlayer.npcPlayerId);
                            message.writeByte(npcPlayer.status);
                            NJUtil.sendMessage(getSession(), message);
                            doTaskNext();
                        }
                    }
                }
            }
            if (playercopy != null && !isControlCharNhanBan() && !map.isLang() && playercopy.isAttack(false)) {
                playercopy.attackPlayer(playerIndexs, point);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return point;
    }

    public short autoFall(int x, int y) {
        if (map == null || map.template == null) {
            return (short) y;
        }
        if (y % 24 != 0 || !map.template.tileTypeAt(x, y + 1, 2)) {
            for (y = (y / 24 + 1) * 24; y < map.template.h + 24; y += 24) {
                if (y % 24 == 0 && map.template.tileTypeAt(x, y + 1, 2)) {
                    break;
                }
            }
        }
        return (short) y;
    }

    public void backHomeChienTruong() {
        status = ME_NORMAL;
        hp = hp_full;
        mp = mp_full;
        int maxBack = 98;
        if (getTypePk() == PK_PHE2) {
            maxBack = 104;
        }
        if (MixedArena.isMapArena(map.template.mapTemplateId)) {
            if (!MixedArena.backToBase(this)) {
                goReturnHack();
            }
            return;
        }
        Map mapNext = Map.findMap(this, maxBack);
        if (mapNext == null) {
            return;
        }
        mapClear();
        map.goOutMap(this);
        x = mapNext.template.defaultX;
        y = mapNext.template.defaultY;
        mapNext.waitGoInMap(this);
    }

    public void backHomeGTChien() {
        status = ME_NORMAL;
        hp = hp_full;
        mp = mp_full;
        int maxBack = 98;
        if (getTypePk() == PK_PHE2) {
            maxBack = 104;
        }
        Map mapNext = map.findMapGTChien(this, maxBack);
        if (mapNext == null) {
            return;
        }
        mapClear();
        map.goOutMap(this);
        x = mapNext.template.defaultX;
        y = mapNext.template.defaultY;
        mapNext.waitGoInMap(this);
    }

    public void buffLive(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            if (map.isDunVD) {
                if (map.isGiaiDauNinjaTaiNang()) {
                    DunVD dun = (DunVD) map;
                    if (!dun.phe1.contains(this) && !dun.phe2.contains(this)) {
                        return;
                    }
                } else if (map.isGiaiDauNinjaDenhat()) {
                    DunVDClass dun2 = (DunVDClass) map;
                    if (!dun2.phe1.contains(this) && !dun2.phe2.contains(this)) {
                        return;
                    }
                }
            }
            if ((map.getTemplateId() == 110 || map.getTemplateId() == 111) && !map.phe1.contains(this) && !map.phe2.contains(this)) {
                NJUtil.sendServer(getSession(), AlertFunction.HEPL_TEST_DUN5(getSession()));
                return;
            }
            if (map.getTemplateId() >= 134 && map.getTemplateId() <= 137) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_RETURN(getSession()));
                return;
            }
            Player playerMap = null;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
            }
            if (playerMap == null || playerMap.status != ME_DIE || playerMap.capcha != null || capcha != null) {
                return;
            }
            boolean isOk = false;
            for (Skill skill : skills) {
                if (skill.template.type == 4) {
                    skillSelect(skill, true);
                    isOk = true;
                    break;
                }
            }
            if (!isOk) {
                return;
            }
            int dx = NJUtil.distance(playerMap.x, x);
            int dy = NJUtil.distance(playerMap.y, y);
            if (isAttack(false) && dx <= myskill.dx && dy <= myskill.dy) {
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(11);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 5000;
                eff.param = myskill.options.firstElement().param;
                playerMap.goReturn();
                playerMap.addEffect(eff);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void calculatorOptionCharOffline(Player player) {
        player.login_date = System.currentTimeMillis();
        if (player.skills.size() > 0) {
            player.myskill = player.skills.get(0);
        }
        player.level = player.getLevel();
        player.resetData();
        player.timeActive = System.currentTimeMillis();
        player.mapClear();
        player.updateAll();
        player.changeNpcMe();
        player.loadThuCuoi(0);
        player.sendBian();
        if (((player.effId_food >= 0 && player.effId_food <= 4) || player.effId_food == 28 || player.effId_food == 30 || player.effId_food == 31) && player.timeEff_food > 0) {
            player.useEff(player.effId_food, player.timeEff_food);
        }
        if (player.timeEff_ThiLuyen > 0) {
            player.useThiLuyenThiep(player.timeEff_ThiLuyen);
        }
        if (player.timeKhai_Thien_Nhan_Phu > 0) {
            useThienNhanPhu(player, player.timeKhai_Thien_Nhan_Phu, (player.effId_KhaiThienNhanPhu != 40) ? 1 : 0);
        }
        if (player.effId_exp_bonus == 22 && player.timeEff_exp_bonus > 0) {
            player.useExpx2(player.timeEff_exp_bonus);
        }
        if (player.effId_exp_bonus == 32 && player.timeEff_exp_bonus > 0) {
            player.useExpx3(player.timeEff_exp_bonus);
        }
        if (player.effId_exp_bonus == 33 && player.timeEff_exp_bonus > 0) {
            player.useExpx4(player.timeEff_exp_bonus);
        }
        Item item = player.findItemBag(572);
        player.xStartAuto = player.x;
        player.yStartAuto = player.y;
        if (item != null) {
            player.sendTeleport(-1, player.x, player.y, false, false, "doselect");
        } else {
            player.sendTeleport(-2, player.x, player.y, false, false, "doselect");
        }
        player.doAddGiftNewDay();
    }

    public void callEffectBall() {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.CALL_EFFECT_BALL);
            NJUtil.sendMessage(getSession(), message);
            message = NJUtil.messageSubCommand(Cmd.CALL_EFFECT_BALL_1);
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void callEffectMe(int effId) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.CALL_EFFECT_ME);
            message.writeShort(effId);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public int canMove() {
        return (capcha != null && capcha.status != CapchaMonster.STATUS_DIE) ? 1 : 0;
    }

    public boolean canNotMove() {
        for (int i = effects.size() - 1; i >= 0; --i) {
            if (effects.get(i).template.type == 2 || effects.get(i).template.type == 3 || effects.get(i).template.type == 14) {
                return true;
            }
        }
        return false;
    }

    public boolean canNotAttack() {
        if (System.currentTimeMillis() - lastSkill <= 200L) {
            return true;
        }
        for (int i = effects.size() - 1; i >= 0; --i) {
            if (effects.get(i).template.type == 2 || effects.get(i).template.type == 3 || effects.get(i).template.type == 10) {
                return true;
            }
        }
        return false;
    }

    public void changeItemOption(Item item, int countUp) {
        if (item.options != null) {
            for (int i = 0; i < item.options.size(); ++i) {
                ItemOption option = item.options.elementAt(i);
                if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7) {
                    option.param += 15 * countUp;
                } else if (option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                    option.param += 10 * countUp;
                } else if (option.optionTemplate.itemOptionTemplateId == 10 ||
                    option.optionTemplate.itemOptionTemplateId == 11 ||
                    option.optionTemplate.itemOptionTemplateId == 12 ||
                    option.optionTemplate.itemOptionTemplateId == 13 ||
                    option.optionTemplate.itemOptionTemplateId == 14 ||
                    option.optionTemplate.itemOptionTemplateId == 15 ||
                    option.optionTemplate.itemOptionTemplateId == 17 ||
                    option.optionTemplate.itemOptionTemplateId == 18 ||
                    option.optionTemplate.itemOptionTemplateId == 20
                ) {
                    option.param += 5 * countUp;
                } else if (option.optionTemplate.itemOptionTemplateId == 21 ||
                    option.optionTemplate.itemOptionTemplateId == 22 ||
                    option.optionTemplate.itemOptionTemplateId == 23 ||
                    option.optionTemplate.itemOptionTemplateId == 24 ||
                    option.optionTemplate.itemOptionTemplateId == 25 ||
                    option.optionTemplate.itemOptionTemplateId == 26
                ) {
                    option.param += 150 * countUp;
                } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                    option.param += 3 * countUp;
                }
                if (option.param < 0) {
                    option.param = 0;
                }
            }
        }
        //Database.updateItem(item);
    }

    public void changeNpcMe() {
        try {
            if (isNhanban()) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            playerMain.checkClearEffect36();
            playerMain.checkClearEffect42();
            if (playerMain.itemBodys[10] == null) {
                playerMain.sumon = null;
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(0);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 246) {
                playerMain.sumon = new NpcSumon(-1, 70, (short) (playerMain.dame_full / 3));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(70);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 419) {
                playerMain.sumon = new NpcSumon(-1, 122, (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(122);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 568) {
                playerMain.sumon = new NpcSumon(-1, 205, (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(205);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 569) {
                playerMain.sumon = new NpcSumon(-1, 206, (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(206);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(36);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = (int) (playerMain.itemBodys[10].expires - System.currentTimeMillis());
                eff.param = 300;
                addEffect(eff);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 570) {
                playerMain.sumon = new NpcSumon(-1, 207, (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(207);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].template.itemTemplateId == 571) {
                playerMain.sumon = new NpcSumon(-1, 208, (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(208);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
            } else if (playerMain.itemBodys[10].isPetNew()) {
                playerMain.sumon = new NpcSumon(-1, playerMain.itemBodys[10].template.getIDDataPaint(), (short) (playerMain.dame_full / 2));
                Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_THU_NUOI);
                message.writeByte(playerMain.itemBodys[10].template.getIDDataPaint());
                message.writeByte(1);
                NJUtil.sendMessage(getSession(), message);
                if (playerMain.itemBodys[10].isPetReBiHanh() || playerMain.itemBodys[10].ispetReBiHanhKhangDs()) {
                    Effect eff = new Effect();
                    eff.template = ServerController.effTemplates.get(42);
                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                    eff.timeLength = (int) (playerMain.itemBodys[10].expires - System.currentTimeMillis());
                    eff.param = 400;
                    addEffect(eff);
                }
            }
        } catch (Exception e) {
        }
    }

    public void changeSkill(Skill skill, int sPoint) {
        try {
            Player playerMain = getPlayerMainControl();
            int i = 0;
            while (i < skill.template.skills.size()) {
                if (skill.point + sPoint == skill.template.skills.get(i).point) {
                    Skill newSkill = skill.template.skills.get(i);
                    if (newSkill.level > playerMain.level) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_LEVEL(getSession()));
                        return;
                    }
                    playerMain.skills.set(playerMain.skills.indexOf(skill), newSkill);
                    if (playerMain.myskill == null || playerMain.myskill.template.equals(newSkill.template)) {
                        playerMain.skillSelect(newSkill, false);
                        break;
                    }
                    break;
                } else {
                    ++i;
                }
            }
            playerMain.skill_point -= (short) sPoint;
            playerMain.updateData();
            updateSkill();
            if (!playerMain.isNhanban() && taskMain != null && taskMain.template.taskId == 9 && taskMain.index == 1) {
                doTaskNext();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void changeTypePk(byte updateTypePk) {
        try {
            if (updateTypePk == 2) {
                return;
            }
            if (updateTypePk >= 0 && updateTypePk <= 3) {
                typePk = updateTypePk;
                if (getTypePk() == PK_NHOM && isHaveClan) {
                    typePk = PK_BANG;
                } else if (getTypePk() == PK_BANG && !isHaveClan) {
                    typePk = PK_NHOM;
                }
                if (getTypePk() != PK_NORMAL && testSkill != null) {
                    if (testSkill.player1 != null && testSkill.player2 != null) {
                        Player p1 = testSkill.player1;
                        Player p2 = testSkill.player2;
                        p1.testSkill = null;
                        p2.testSkill = null;
                        p1.sendTestEndSkill(p1.playerId, p2.playerId);
                    } else {
                        testSkill = null;
                    }
                }
                updateTypePk();
            }
        } catch (Exception e) {
        }
    }

    public void checkAddEffect() {
    }

    public boolean checkClearEffect11() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.type == 11) {
                removeEffect(effects.get(i), true);
                return true;
            }
        }
        return false;
    }

    public void checkClearEffect12() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.type == 12) {
                removeEffect(effects.get(i), true);
                break;
            }
        }
    }

    public boolean checkClearEffect36() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 36) {
                removeEffect(effects.get(i), true);
                return true;
            }
        }
        return false;
    }

    public boolean checkClearEffect42() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 42) {
                removeEffect(effects.get(i), true);
                return true;
            }
        }
        return false;
    }

    public void checkDie(int killId) {
        try {
            Player playerMain = getPlayerMainControl();
            if (playerMain.hp <= 0) {
                isChangeMap = false;
                try {
                    if (map.isArena()) {
                        map.setNamePlayerLose(name);
                    }
                } catch (Exception e) {
                }
                for (Item itemBag : itemBags) {
                    if (itemBag != null && itemBag.getTemplateId() == 458) {
                        throwItem(itemBag);
                    }
                }
                playerMain.hp = 0;
                int typeDownExp = 0;
                if (playerId >= 0) {
                    if (killId == -2 && testSkill != null) {
                        playerMain.hp = 50;
                        sendTestEnd();
                        return;
                    }
                    if (killId >= 0) {
                        if (map.isVungDatMaQuy()) {
                            countPlayerDieVungDatMaQuy();
                        }
                        Player playerMap = map.getPlayer(killId);
                        if (map.template.isMapChienTruong() && map.template.mapTemplateId != 98 && map.template.mapTemplateId != 104) {
                            if (playerMap != null && !playerMap.equals(this) && !MixedArena.isMapArena(map.template.mapTemplateId)) {
                                if (map.giatocchien != null && playerMap.map.giatocchien != null) {
                                    int pointGTC = map.giatocchien.getPointCT(name);
                                    int pointAdd = 5;
                                    if (pointGTC >= 4000) {
                                        pointAdd = 50;
                                    } else if (pointGTC >= 1500) {
                                        pointAdd = 30;
                                    } else if (pointGTC >= 600) {
                                        pointAdd = 20;
                                    } else if (pointGTC >= 200) {
                                        pointAdd = 10;
                                    }
                                    playerMap.map.giatocchien.addPoint(playerMap.name, pointAdd);
                                    playerMap.sendChientruong(playerMap.map.giatocchien.getPointCT(playerMap.name));
                                } else {
                                    int diem = getPointCT();
                                    playerMap.pointCT += diem;
                                    playerMap.checkStepNguyeNhanTask(NguyetNhanTask.TYPE_KIEM_DIEM_CHIEN_TRUONG, diem);
                                    ++playerMap.pointPKCT;
                                    playerMap.sendChientruong(playerMap.pointCT);
                                }
                                NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.KILL1(getSession()), playerMap.name));
                                NJUtil.sendServer(playerMap.getSession(), NJUtil.replace(AlertFunction.KILL2(playerMap.getSession()), name));
                            }
                        } else {
                            if (!isControlCharNhanBan()) {
                                --pk;
                                if (pk <= 1) {
                                    if (pk < 0) {
                                        pk = 0;
                                    }
                                } else {
                                    int perExpDown = pk + 1;
                                    long expDown = exps1[level] * perExpDown / 100L;
                                    long expLv = getMaxExp1(level - 1);
                                    if (exp == expLv) {
                                        exp_down += expDown;
                                        typeDownExp = 2;
                                    } else if (exp - expDown >= expLv) {
                                        exp -= expDown;
                                        typeDownExp = 1;
                                    } else if (exp - expDown < expLv) {
                                        exp_down += expDown - (exp - expLv);
                                        exp = expLv;
                                        typeDownExp = 2;
                                    }
                                }
                                if (exp_down >= exps1[level] / 2L) {
                                    exp_down = exps1[level] / 2L;
                                }
                            }
                            if (playerMap != null) {
                                if (!playerMap.isControlCharNhanBan() && (playerMain.getKillPlayer() == null || !playerMain.getKillPlayer().player.equals(playerMap)) && (playerMain.getTypePk() != PK_NHOM || playerMap.getTypePk() != PK_NHOM)) {
                                    if (playerMap.getTypePk() == PK_DOSAT) {
                                        if (!playerMain.isNhanban()) {
                                            Friend f = findFriend(playerMap.name);
                                            if (f == null) {
                                                friends.add(new Friend(playerMap.name, (byte) 2));
                                            }
                                            if (playerMap.typeNvbian == 1) {
                                                playerMap.fibian();
                                            }
                                            ++playerMap.pk;
                                            if (playerMap.pk >= 15) {
                                                playerMap.pk = 15;
                                            }
                                            if (playerMap.isNhanban()) {
                                                Player owner = playerMap.owner;
                                                ++owner.pk;
                                                if (playerMap.owner.pk > 15) {
                                                    playerMap.owner.pk = 15;
                                                }
                                            }
                                        }
                                    } else if (playerMap.getKillPlayer() != null && playerMap.getKillPlayer().player.equals(this) && !playerMain.isNhanban()) {
                                        Friend f = findFriend(playerMap.name);
                                        if (f == null) {
                                            friends.add(new Friend(playerMap.name, (byte) 2));
                                        } else if (f.type == 0 || f.type == 1) {
                                            f.type = 2;
                                            Message message = NJUtil.messageSubCommand(Cmd.FRIEND_REMOVE);
                                            message.writeUTF(playerMap.name);
                                            NJUtil.sendMessage(getSession(), message);
                                        }
                                        playerMap.pk += 2;
                                        if (playerMap.pk >= 15) {
                                            playerMap.pk = 15;
                                        }
                                        if (playerMap.isNhanban()) {
                                            Player owner2 = playerMap.owner;
                                            owner2.pk += 2;
                                            if (playerMap.owner.pk > 15) {
                                                playerMap.owner.pk = 15;
                                            }
                                        }
                                    }
                                }
                                Message message = NJUtil.messageSubCommand(Cmd.ME_UPDATE_PK);
                                message.writeByte(playerMap.getPlayerMainControl().pk);
                                NJUtil.sendMessage(playerMap.getSession(), message);
                                if (playerMap.isNhanban()) {
                                    message = NJUtil.messageSubCommand(Cmd.ME_UPDATE_PK);
                                    message.writeByte(playerMap.owner.pk);
                                    NJUtil.sendMessage(playerMap.owner.getSession(), message);
                                }
                            } else {
                                NJUtil.sendServer(getSession(), AlertFunction.YOU_DIE(getSession()));
                            }
                        }
                    }
                } else if (owner != null && playerId == -owner.playerId) {
                    sendTaskFollowFail(owner, 2);
                    map.playerOuts.add(this);
                }
                playerMain.hp = 0;
                status = ME_DIE;
                y = autoFall(x, y);
                sendTestEnd();
                if (!playerMain.isNhanban()) {
                    clearCuuSatMe();
                    if (playerId >= 0) {
                        if (typeDownExp == 0 || typeDownExp == 1) {
                            Message message = new Message(Cmd.ME_DIE);
                            message.writeByte(playerMain.pk);
                            message.writeShort(x);
                            message.writeShort(y);
                            if (typeDownExp == 1) {
                                message.writeLong(exp);
                            }
                            NJUtil.sendMessage(getSession(), message);
                        }
                        if (typeDownExp == 2) {
                            Message message = new Message(Cmd.ME_DIE_EXP_DOWN);
                            message.writeByte(playerMain.pk);
                            message.writeShort(x);
                            message.writeShort(y);
                            message.writeLong(exp_down);
                            NJUtil.sendMessage(getSession(), message);
                        }
                    }
                    Message message = new Message(Cmd.PLAYER_DIE);
                    message.writeInt(playerId);
                    message.writeByte(playerMain.pk);
                    message.writeShort(x);
                    message.writeShort(y);
                    sendToPlayer(message, false);
                    Item it = findItemBag(348);
                    if (taskMain != null && taskMain.template.taskId == 36 && it != null && it.quantity >= 2) {
                        sendUseItemUpToUp(it.indexUI, it.quantity / 2);
                        Task taskMain = this.taskMain;
                        taskMain.count -= (short) (it.quantity / 2 - 1);
                        it.quantity -= it.quantity / 2;
                        ghiloghack(it.getTemplateId());
                        doTaskUpdate((short) it.quantity);
                        message = NJUtil.messageNotMap(Cmd.OAN_HON1);
                        message.writeInt(playerId);
                        sendToPlayer(message, true);
                    }
                    if (map.isLangCo() || (map.isVungDatMaQuy() && isUsingThiLuyen() == null)) {
                        Map map = Map.find(this, mapTemplateId_focus);
                        if (map != null) {
                            Player pkick = playerMain;
                            if (playerMain.isNhanban()) {
                                pkick = playerMain.owner;
                            }
                            playerMain.map.goOutMap(pkick);
                            x = 0;
                            y = 0;
                            lastMoveX = x;
                            lastMoveY = y;
                            map.waitGoInMap(this);
                            goReturn();
                        }
                    }
                } else {
                    switchMainCharAndCopyNPCTajma(4);
                    if (playercopy != null && playercopy.hp <= 0) {
                        playercopy.hp = 1;
                    }
                    Database.savePlayer(this, map.getTemplateId());
                    map.playerOuts.add(playercopy);
                    playercopy = null;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public int checkExpireThuCuoiHetHan() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.itemMons.length; ++i) {
            if (playerMain.itemMons[i] != null && playerMain.itemMons[i].isTypeMon() && playerMain.itemMons[i].expires != -1L && System.currentTimeMillis() - playerMain.itemMons[i].expires >= 0L) {
                return playerMain.itemMons[i].getTemplateId();
            }
        }
        return -1;
    }

    public boolean checkPickTask(Item item, boolean isMe) {
        if (addItemToBag(item, isMe)) {
            if (taskMain != null && taskMain.template.taskId == 4 && taskMain.index == 1 && item.template.itemTemplateId == 209) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 209);
            } else if (taskMain != null && taskMain.template.taskId == 4 && taskMain.index == 2 && item.template.itemTemplateId == 210) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 210);
            } else if (taskMain != null && taskMain.template.taskId == 5 && taskMain.index == 1 && item.template.itemTemplateId == 211) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 211);
            } else if (taskMain != null && taskMain.template.taskId == 14 && taskMain.index == 1 && item.template.itemTemplateId == 212) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 212);
            } else if (taskMain != null && taskMain.template.taskId == 14 && taskMain.index == 2 && item.template.itemTemplateId == 213) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 213);
            } else if (taskMain != null && taskMain.template.taskId == 18 && taskMain.index == 1 && item.template.itemTemplateId == 216) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 216);
            } else if (taskMain != null && taskMain.template.taskId == 18 && taskMain.index == 2 && item.template.itemTemplateId == 217) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 217);
            } else if (taskMain != null && taskMain.template.taskId == 22 && taskMain.index == 1 && item.template.itemTemplateId == 230) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 230);
            } else if (taskMain != null && taskMain.template.taskId == 26 && taskMain.index == 1 && item.template.itemTemplateId == 236) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 236);
            } else if (taskMain != null && taskMain.template.taskId == 27 && taskMain.index == 1 && item.template.itemTemplateId == 239) {
                checkTaskIndex(item, 239);
            } else if (taskMain != null && taskMain.template.taskId == 31 && taskMain.index == 1 && item.template.itemTemplateId == 264) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 264);
            } else if (taskMain != null && taskMain.template.taskId == 31 && taskMain.index == 2 && item.template.itemTemplateId == 265) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 265);
            } else if (taskMain != null && taskMain.template.taskId == 38 && taskMain.index == 1 && item.template.itemTemplateId == 349) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 349);
            } else if (taskMain != null && taskMain.template.taskId == 38 && taskMain.index == 2 && item.template.itemTemplateId == 350) {
                if (party != null && isMe) {
                    party.sendTaskPick(this, item);
                }
                checkTaskIndex(item, 350);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean checkRecentPK(Player player) {
        for (int i = lsRecentPK.size() - 1; i >= 0; --i) {
            Player p = lsRecentPK.elementAt(i);
            if (p.playerId == player.playerId) {
                return true;
            }
        }
        return false;
    }

    public void checkStepNguyeNhanTask(int type, int point) {
        try {
            if (nguyetNhanTask != null) {
                nguyetNhanTask.checkStepQuest(this, type, point, null);
            }
        } catch (Exception e) {
        }
    }

    public void checkStepNguyeNhanTaskNangCap(int type, int point, Item item) {
        try {
            if (nguyetNhanTask != null) {
                nguyetNhanTask.checkStepQuest(this, type, point, item);
            }
        } catch (Exception e) {
        }
    }

    public void checkTaskBoos(Npc npc) {
        if (taskLoopBoss != null && npc.isTathu() && taskLoopBoss.killId == npc.template.npcTemplateId) {
            checkTaskOrder(taskLoopBoss);
        }
    }

    public void checkTaskDay(Npc npc) {
        if (!isMainchar) {
            return;
        }
        if (taskLoopDay != null && taskLoopDay.killId == npc.template.npcTemplateId) {
            checkTaskOrder(taskLoopDay);
        }
    }

    public void checkTaskIndex() {
        Player playerMain = getPlayerMainControl();
        if (!isMainchar || playerMain.taskMain == null) {
            return;
        }
        short count = (short) (playerMain.taskMain.count + 1);
        if (count >= playerMain.taskMain.template.counts[playerMain.taskMain.index]) {
            doTaskNext();
        } else {
            doTaskUpdate(count);
        }
    }

    public void checkTaskIndex(Item item, int itemTaskId) {
        Player playerMain = getPlayerMainControl();
        ++playerMain.taskMain.count;
        int count = countItemBag(itemTaskId);
        if (count != playerMain.taskMain.count) {
            item.quantity = playerMain.taskMain.count - count;
            addItemToBag(item, true);
        }
        if (playerMain.taskMain.count >= playerMain.taskMain.template.counts[playerMain.taskMain.index]) {
            doTaskNext();
        } else {
            doTaskUpdate(playerMain.taskMain.count);
        }
    }

    public void checkTaskOrder(TaskOrder taskOrder, int ncount) {
        if (isNhanban() || isControlCharNhanBan()) {
            return;
        }
        try {
            if (ncount < 0) {
                ncount = 0;
            }
            int count = taskOrder.count + ncount;
            boolean sendUpdate = false;
            if (count > taskOrder.maxCount) {
                if (taskOrder.count < taskOrder.maxCount) {
                    sendUpdate = true;
                }
                taskOrder.count = taskOrder.maxCount;
            } else {
                sendUpdate = true;
                taskOrder.count = count;
            }
            if (sendUpdate) {
                Message message = new Message(Cmd.GET_TASK_UPDATE);
                message.writeByte(taskOrder.taskId);
                if (getSession().isVersionTet2015()) {
                    message.writeInt(taskOrder.count);
                } else {
                    message.writeShort(taskOrder.count);
                }
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void checkTaskOrder(TaskOrder taskOrder) {
        try {
            Player playerMain = getPlayerMainControl();
            if (playerMain.isNhanban()) {
                clearTaskOrder(taskOrder);
                return;
            }
            int count = taskOrder.count + 1;
            if (count > taskOrder.maxCount) {
                taskOrder.count = taskOrder.maxCount;
            } else {
                taskOrder.count = count;
                Message message = new Message(Cmd.GET_TASK_UPDATE);
                message.writeByte(taskOrder.taskId);
                if (getSession().isVersionTet2015()) {
                    message.writeInt(taskOrder.count);
                } else {
                    message.writeShort(taskOrder.count);
                }
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void checkThuCuoi(int numDay) {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.itemMons.length; ++i) {
            checkThuCuoi(playerMain.itemMons[i], numDay);
        }
        for (Item itemBag : itemBags) {
            checkThuCuoi(itemBag, numDay);
        }
        for (Item itemBox : itemBoxs) {
            checkThuCuoi(itemBox, numDay);
        }
    }

    public void checkThuCuoi(Item item, int numDay) {
        if (item != null && item.isLock && item.template.type == 33 && item.options != null && item.options.size() > 2) {
            if (item.options.get(1).param >= 700) {
                ItemOption itemOption = item.options.get(0);
                itemOption.param += 5 * numDay;
            }
            item.options.get(1).param -= (20 + item.upgrade) * numDay;
            if (item.upgrade >= 99) {
                item.upgrade = 99;
                item.options.get(0).param = 0;
            }
            if (item.options.get(1).param < 1) {
                item.options.get(1).param = 1;
            } else if (item.options.get(0).param >= 1000) {
                doUpgradeMon(item);
            }
        }
    }

    public void checkTiemNang() {
        int total = potential_point + p_strength + p_agile + p_hp + p_mp;
        int potential_point = 30;
        for (int i = 1; i <= level; ++i) {
            if (i >= 70 && i < 80) {
                potential_point += 20;
            } else if (i >= 80 && i < 90) {
                potential_point += 30;
            } else if (i >= 90) {
                potential_point += 50;
            } else {
                potential_point += 10;
            }
        }
        potential_point += limitTiemnangso * 10;
        potential_point += limitBanhBangHoa * 10;
        if (total != potential_point) {
            LOGGER.warn("{}. Reset tiem nang: {},{},{},{},{},{}", getStringBaseInfo(), potential_point, p_strength, p_agile, p_hp, p_mp, level);
            tayTiemnang();
        }
    }

    public boolean checkTimeOutNhanban() {
        if (playercopy != null && playercopy.timeLiveCoppy > 0L && System.currentTimeMillis() - playercopy.timeLiveCoppy > 0L) {
            playercopy.timeLiveCoppy = -1L;
            return true;
        }
        return false;
    }

    public void clanAcceptInvite(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.isControlCharNhanBan() || NJUtil.distance(playerMap.x, x) > 60 || NJUtil.distance(playerMap.y, y) > 60 || playerMap.inviteClan == null || playerMap.inviteClan.playerId != playerId) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN4(getSession()));
                return;
            }
            playerMap.inviteClan = null;
            if (clan == null && playerMap.clan != null && playerMap.clan.isInvite(playerMap.name) && !playerMap.clan.isFull()) {
                clan = playerMap.clan;
                Member mem = new Member(0);
                mem.name = name;
                mem.level = level;
                mem.classId = classId;
                mem.isOnline = true;
                playerMap.clan.addMember(mem);
                sendUpdateClan(this, mem.typeClan);
                NJUtil.sendServer(getSession(), name + " " + AlertFunction.IN_CLAN(getSession()) + " " + clan.name + ".");
                NJUtil.sendServer(playerMap.getSession(), name + " " + AlertFunction.IN_CLAN(getSession()) + " " + clan.name + ".");
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void clanAcceptPlease(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.isControlCharNhanBan() || NJUtil.distance(playerMap.x, x) > 60 || NJUtil.distance(playerMap.y, y) > 60 || playerMap.pleaseClan == null || playerMap.pleaseClan.playerId != playerId) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN4(getSession()));
                return;
            }
            playerMap.pleaseClan = null;
            if (playerMap.clan == null && clan != null && clan.isInvite(name) && !clan.isFull()) {
                playerMap.clan = clan;
                Member mem = new Member(0);
                mem.name = playerMap.name;
                mem.level = playerMap.level;
                mem.classId = playerMap.classId;
                mem.isOnline = true;
                clan.addMember(mem);
                sendUpdateClan(playerMap, mem.typeClan);
                NJUtil.sendServer(getSession(), playerMap.name + " + Alert_VN.IN_CLAN(getSession()) +  " + clan.name + ".");
                NJUtil.sendServer(playerMap.getSession(), playerMap.name + " + Alert_VN.IN_CLAN(getSession()) +  " + clan.name + ".");
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void clanInvite(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.isControlCharNhanBan() || NJUtil.distance(playerMap.x, x) > 60 || NJUtil.distance(playerMap.y, y) > 60) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN1(getSession()));
                return;
            }
            if (playerMap.clan != null || clan == null || !clan.isInvite(name)) {
                return;
            }
            if (playerMap.timeOutClan > System.currentTimeMillis() / 1000L) {
                NJUtil.sendServer(getSession(), playerMap.name + AlertFunction.CLAN_OUT_ONE_DAY(getSession()));
                return;
            }
            if (inviteClan != null) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN2(getSession()));
                return;
            }
            if (clan.isFull()) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN3(getSession()));
                return;
            }
            inviteClan = new InviteClan(playerMap.playerId);
            message = NJUtil.messageSubCommand(Cmd.CLAN_INVITE);
            message.writeInt(playerId);
            message.writeUTF(clan.name);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void clanPlease(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.isControlCharNhanBan() || NJUtil.distance(playerMap.x, x) > 60 || NJUtil.distance(playerMap.y, y) > 60) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN1(getSession()));
                return;
            }
            if (playerMap.clan == null || !playerMap.clan.isInvite(playerMap.name) || clan != null) {
                return;
            }
            if (pleaseClan != null) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN5(getSession()));
                return;
            }
            if (playerMap.clan.isFull()) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_INVITE_CLAN3(getSession()));
                return;
            }
            if (timeOutClan > System.currentTimeMillis() / 1000L) {
                NJUtil.sendServer(getSession(), name + AlertFunction.CLAN_OUT_ONE_DAY(getSession()));
                return;
            }
            pleaseClan = new InviteClan(playerMap.playerId);
            message = NJUtil.messageSubCommand(Cmd.CLAN_PLEASE);
            message.writeInt(playerId);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void clanBet(String str) {
        try {
            if (isGTChien()) {
                sendOpenUISay(32, AlertFunction.GTC_1(getSession()));
                return;
            }
            String strNum = str.trim().replaceAll("[^0-9]", "");
            if (strNum.isEmpty()) {
                return;
            }
            int money = Integer.parseInt(strNum);
            if (money <= 0 || testGTDun == null || map.getTemplateId() != 117) {
                return;
            }
            if (money < 1000) {
                sendOpenUISay(32, AlertFunction.HEPL_TEST_DUN8(getSession()));
                return;
            }
            if (money > 100000000) {
                sendOpenUISay(32, "Chỉ được cược tối đa 100.0000.000 xu");
                return;
            }
            if (money > clan.coin) {
                sendOpenUISay(32, AlertFunction.HEPL_TEST_DUN9(getSession()));
                return;
            }
            if (testGTDun.player1.equals(this)) {
                testGTDun.money1 = money;
            } else {
                testGTDun.money2 = money;
            }
            if (testGTDun.money1 >= 1000 && testGTDun.money2 >= 1000 && testGTDun.money1 == testGTDun.money2 && testGTDun.player1.clan.coin >= testGTDun.money1 && testGTDun.player2.clan.coin >= testGTDun.money2) {
                new GiaTocChien(testGTDun.player1, testGTDun.player2, testGTDun.money1, testGTDun.money2);
                clearTestGT();
            } else {
                String[] strs = {
                    name + " " + NJUtil.replace(Alert_VN.HEPL_TEST_DUN10, String.valueOf(money)),
                    name + " " + NJUtil.replace(Alert_EN.HEPL_TEST_DUN10, String.valueOf(money))
                };
                map.sendAlert(strs);
            }
        } catch (Exception e) {
        }
    }

    public void clearAllEff(boolean remove) {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            removeEffect(playerMain.effects.get(i), remove);
        }
    }

    public void clearCuuSatMe() {
        try {
            if (playerId < 0) {
                return;
            }
            killPlayer = null;
            for (int i = 0; i < map.players.size(); ++i) {
                Player p = map.players.get(i);
                if (p.killPlayer != null && p.killPlayer.player != null && p.killPlayer.player.equals(this)) {
                    p.killPlayer = null;
                    Message message = new Message(Cmd.CLEAR_CUU_SAT);
                    NJUtil.sendMessage(p.getSession(), message);
                }
            }
        } catch (Exception e) {
        }
    }

    public void clearData(boolean isClearTestDun) {
        try {
            ALL_CHAR_TRADE.remove(idTrade);
        } catch (Exception e) {
        }
        if (testSkill != null) {
            Player p1 = testSkill.player1;
            Player p2 = testSkill.player2;
            if (p1 != null && p2 != null) {
                p1.testSkill = null;
                p2.testSkill = null;
                p1.sendTestEndSkill(p1.playerId, p2.playerId);
            } else {
                testSkill = null;
            }
        }
        if (isClearTestDun) {
            clearTestGT();
            clearTestDun();
        }
        killPlayer = null;
        if (sumon != null) {
            sumon.npcAttId = -1;
        }
        if (sumonHide != null) {
            sumonHide.attNpcs = null;
        }
        if (party != null && party.players.size() <= 1) {
            party = null;
        }
        clearCuuSatMe();
    }

    public void clearEffect(boolean remove) {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.type == 19) {
                removeEffect(playerMain.effects.get(i), remove);
                break;
            }
        }
    }

    public void clearRecentPK() {
        if (lsRecentPK == null) {
            lsRecentPK = new Vector<>();
        } else {
            lsRecentPK.clear();
        }
    }

    public void clearTask() {
        taskMain = null;
        taskIndex = -1;
        taskCount = 0;
        for (int i = 0; i < itemBags.length; ++i) {
            if (itemBags[i] != null && (itemBags[i].template.type == 25 || itemBags[i].template.type == 23 || itemBags[i].template.type == 24)) {
                /*if (itemBags[i].template.type == 23 || itemBags[i].template.type == 24) {
                    Database.removeItem(itemBags[i].itemId);
                }*/
                itemBags[i] = null;
            }
        }
    }

    public void clearTaskOrder(TaskOrder taskorder) {
        try {
            Message message = new Message(Cmd.CLEAR_TASK_ORDER);
            message.writeByte(taskorder.taskId);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void clearTestDun() {
        if (testSkillDun != null) {
            Player p1 = testSkillDun.player1;
            Player p2 = testSkillDun.player2;
            if (p1 != null) {
                p1.testSkillDun = null;
            }
            if (p2 != null) {
                p2.testSkillDun = null;
            }
        }
    }

    public void clearTestGT() {
        if (testGTDun != null) {
            Player p1 = testGTDun.player1;
            Player p2 = testGTDun.player2;
            if (p1 != null) {
                p1.testGTDun = null;
            }
            if (p2 != null) {
                p2.testGTDun = null;
            }
        }
    }

    public Player clonePlayer(int minute) {
        if (playercopy != null) {
            return playercopy;
        }
        Item item = findItemBag(545);
        int[][] hid = {
            { 11, 26, 27, 28 },
            { 2, 23, 24, 25 }
        };
        int idHead = hid[gender][NJUtil.random.nextInt(hid[gender].length)];
        playercopy = ServerController.doCreatePlayerCopy(name, gender, idHead, this);
        if (playercopy != null) {
            playercopy.x = x;
            playercopy.y = y;
            if (map.template.tileTypeAt(x, y, 2)) {
                if (map.template.tileTypeAt(x + 24, y, 2)) {
                    playercopy.x = (short) (x + 24);
                } else if (map.template.tileTypeAt(x - 24, y, 2)) {
                    playercopy.x = (short) (x - 24);
                }
            }
            playercopy.owner = this;
            playercopy.timeUpdate = 4;
            playercopy.isMainchar = false;
            playercopy.sysNpc = NJUtil.randomNumber(3) + 1;
            if (playercopy != null) {
                long time = System.currentTimeMillis() - playercopy.timeLiveCoppy;
                if (time >= 0L && playercopy.timeLiveCoppy >= 1L) {
                    if (item == null) {
                        NJUtil.sendServer(getSession(), "Cần có " + ServerController.itemTemplates.get(545).getName(getSession()) + " để thực hiện.");
                        return playercopy = null;
                    }
                    playercopy.timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
                    timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
                }
                if (playercopy.timeLiveCoppy < 1L) {
                    if (item == null) {
                        NJUtil.sendServer(getSession(), "Cần có " + ServerController.itemTemplates.get(545).getName(getSession()) + " để thực hiện.");
                        return playercopy = null;
                    }
                    playercopy.timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
                    timeLiveCoppy = System.currentTimeMillis() + minute * 60000L;
                }
                playercopy.login_date = System.currentTimeMillis();
                if (playercopy.skills.size() > 0) {
                    playercopy.myskill = playercopy.skills.get(0);
                }
                playercopy.level = playercopy.getLevel();
                if (dataRms[1][getindexRms.get("CSkill")] != null && dataRms[1][getindexRms.get("CSkill")].length > 0) {
                    playercopy.skillSelect(playercopy.getSkill(dataRms[1][getindexRms.get("CSkill")][0]), false);
                }
                if (playercopy.level < 10) {
                    if (playercopy.itemBodys[1] == null) {
                        Item it = new Item(194, true, "clonePlayer char");
                        playercopy.addItem(it, 5, it.template.type);
                    }
                    int ss = 0;
                    for (int i = 0; i <= 9; ++i) {
                        ss += (int) exps1[i];
                    }
                    long exp1 = ss - playercopy.exp;
                    playercopy.sendUpdateExp(exp1, true);
                }
                playercopy.resetData();
                playercopy.changeNpcMe();
                playercopy.checkAddEffect();
                try {
                    playercopy.setFirstJoinGame(false);
                    map.waitGoInMap(playercopy);
                    playercopy.taskFinish = 10000;
                    playercopy.taskMain = new Task(10000);
                } catch (Exception e) {
                    LOGGER.error(getStringBaseInfo(), e);
                }
                if (((playercopy.effId_food >= 0 && playercopy.effId_food <= 4) || playercopy.effId_food == 28 || playercopy.effId_food == 30 || playercopy.effId_food == 31 || playercopy.effId_food == 35) && playercopy.timeEff_food > 0) {
                    playercopy.useEff(playercopy.effId_food, playercopy.timeEff_food);
                }
                if (playercopy.effId_exp_bonus == 22 && playercopy.timeEff_exp_bonus > 0) {
                    playercopy.useExpx2(playercopy.timeEff_exp_bonus);
                }
                if (playercopy.timeEff_ThiLuyen > 0) {
                    playercopy.useThiLuyenThiep(playercopy.timeEff_ThiLuyen);
                }
                if (playercopy.timeKhai_Thien_Nhan_Phu > 0) {
                    useThienNhanPhu(playercopy, playercopy.timeKhai_Thien_Nhan_Phu, (playercopy.effId_KhaiThienNhanPhu != 40) ? 1 : 0);
                }
                playercopy.loadThuCuoi(1);
            }
            removeItem(item, 3);
            sendClearItemBag(item.indexUI);
            playercopy.setPercentDamPhanThan(getSkillPhanThan().options.get(1).param);
            return playercopy;
        }
        return null;
    }

    public void confirmTask(int playerTemplateId, String[] menu) {
        try {
            TaskTemplate taskTemplate = ServerController.taskTemplates.get(getTaskFinish());
            Message message = new Message(Cmd.OPEN_UI_CONFIRM);
            message.writeShort(playerTemplateId);
            message.writeUTF(taskTemplate.des[getSession().typeLanguage][0]);
            message.writeByte(menu.length);
            for (String s : menu) {
                message.writeUTF(s);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void confirmTask(int playerTemplateId, String say, String[] menu) {
        try {
            Message message = new Message(Cmd.OPEN_UI_CONFIRM);
            message.writeShort(playerTemplateId);
            message.writeUTF(say);
            message.writeByte(menu.length);
            for (String s : menu) {
                message.writeUTF(s);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public int countFreeBag() {
        int countFree = 0;
        for (Item itemBag : itemBags) {
            if (itemBag == null) {
                ++countFree;
            }
        }
        return countFree;
    }

    public int countItemBag(int itemTemplateId) {
        int count = 0;
        for (Item itemBag : itemBags) {
            if (itemBag != null && itemBag.getTemplateId() == itemTemplateId) {
                count += itemBag.quantity;
            }
        }
        return count;
    }

    public void countMonsterKill() {
        if (map.isLangCo()) {
            timeKillMonsterLangCo = 0;
        }
        if (GameServer.getServerId() >= 0 && level >= 10 && map.isMapCapcha() && capcha == null) {
            ++countMonsKill;
            if (countMonsKill == maxKillMonster) {
                initCapCha();
            }
        }
    }

    public synchronized void countPlayerDieVungDatMaQuy() {
    }

    public void createWait(byte timeWait, Item itemWait) {
        this.timeWait = timeWait;
        this.itemWait = itemWait;
    }

    public void createWaitCauCa(byte timeWait, Item itemWait) {
        timeCauca = timeWait;
        itemCauca = itemWait.cloneItem();
    }

    public void createWaitMap(byte timeWait, ItemMap itemMapWait) {
        this.timeWait = timeWait;
        this.itemMapWait = itemMapWait;
    }

    public void defaultPoint() {
        x = map.template.defaultX;
        y = map.template.defaultY;
    }

    public void doAcceptParty(Message message) {
        try {
            Player playerMap = ServerController.hpPlayers.get(message.readInt());
            if (playerMap == null || party != null || playerMap.party == null || !playerMap.party.isTeamLeader(playerMap) || playerMap.party.isFull()) {
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            if (playerMap.party.isHaveInvite(this)) {
                (party = playerMap.party).sendParty();
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAcceptPleaseParty(Message message) {
        try {
            Player playerMap;
            try {
                playerMap = ServerController.hnPlayers.get(message.readUTF());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.party != null || party == null || !party.isTeamLeader(this) || party.isFull() || isNotEditParty()) {
                return;
            }
            if (party.isHavePlease(playerMap)) {
                playerMap.party = party;
                party.sendParty();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doInviteTrade(Message message) {
        if (!GameServer.openTrade) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (idTrade > -1 || isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap = map.getPlayer(message.readInt());
            int taskRequire = 8;
            if (taskFinish <= taskRequire) {
                TaskTemplate task = ServerController.taskTemplates.get(taskRequire);
                NJUtil.sendDialog(getSession(), "Phải hoàn thành " + task.name[getSession().typeLanguage] + " mới có thể giao dịch");
                return;
            }
            if (isAcountAo()) {
                NJUtil.sendDialog(getSession(), "Tài khoản của bạn chưa xác thực. Không thể giao dịch");
                return;
            }
            if (playerMap.isAcountAo()) {
                NJUtil.sendDialog(getSession(), "Không thể trao đổi với người có tài khoản chưa được xác thực.");
                return;
            }
            if (playerMap.idTrade > -1) {
                NJUtil.sendDialog(getSession(), "Người chơi đang chờ hoàn thành một giao dịch khác.");
                return;
            }
            if (NJUtil.distance(playerMap.x, x) > 60 || NJUtil.distance(playerMap.y, y) > 60) {
                NJUtil.sendDialog(getSession(), AlertFunction.ERROR_INVITE_TRADE1(getSession()));
                return;
            }
            if (playerMap.status == ME_DIE) {
                return;
            }
            if (tradeSet != null || idTrade > -1) {
                NJUtil.sendDialog(getSession(), AlertFunction.ERROR_INVITE_TRADE2(getSession()));
                return;
            }
            if (playerMap.tradeGet != null) {
                NJUtil.sendDialog(getSession(), AlertFunction.ERROR_INVITE_TRADE3(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            tradeSet = new InviteTrade(playerMap.playerId);
            playerMap.tradeGet = new InviteTrade(playerId);
            message = new Message(Cmd.TRADE_INVITE);
            message.writeInt(playerId);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAcceptInviteTrade(Message message) {
        if (!GameServer.openTrade) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        int taskRequire = 8;
        if (taskFinish <= taskRequire) {
            TaskTemplate task = ServerController.taskTemplates.get(taskRequire);
            NJUtil.sendDialog(getSession(), "Phải hoàn thành " + task.name[getSession().typeLanguage] + " mới có thể giao dịch");
            return;
        }
        if (idTrade > -1 || isControlCharNhanBan()) {
            return;
        }
        try {
            if (tradeGet != null) {
                Player playerMap = map.getPlayer(tradeGet.playerId);
                if (playerMap != null && playerMap.tradeSet != null && playerMap.tradeSet.playerId == playerId && NJUtil.distance(playerMap.x, x) <= 60 && NJUtil.distance(playerMap.y, y) <= 60) {
                    tradeGet = null;
                    tradeSet = null;
                    playerMap.tradeGet = null;
                    playerMap.tradeSet = null;
                    Trade t = new Trade(this, playerMap);
                    int idTrade = getIdTrade();
                    t.idTrade = idTrade;
                    t.timeFinishTrade = System.currentTimeMillis() + NJUtil.getMilisByMinute(2);
                    ALL_CHAR_TRADE.put(idTrade, t);
                    this.idTrade = idTrade;
                    playerMap.idTrade = idTrade;
                    checkClearEffect11();
                    checkClearEffect12();
                    playerMap.checkClearEffect11();
                    playerMap.checkClearEffect12();
                    message = new Message(Cmd.OPEN_UI_TRADE);
                    message.writeUTF(playerMap.name);
                    NJUtil.sendMessage(getSession(), message);
                    message = new Message(Cmd.OPEN_UI_TRADE);
                    message.writeUTF(name);
                    NJUtil.sendMessage(playerMap.getSession(), message);
                }
                return;
            }
            tradeGet = null;
            NJUtil.sendDialog(getSession(), AlertFunction.ERROR_ACCEPT_TRADE(getSession()));
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doCancelInviteTrade() {
        try {
            tradeGet = null;
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doAcceptTrade() {
        if (!GameServer.openTrade) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (idTrade == -1 || isControlCharNhanBan()) {
            return;
        }
        if (isLock) {
            doCancelTrade();
            sendNotUnlock();
            return;
        }
        boolean isTrade = false;
        Message message;
        try {
            //noinspection SynchronizeOnNonFinalField
            synchronized (ALL_CHAR_TRADE) {
                Trade trade = ALL_CHAR_TRADE.get(idTrade);
                if (trade != null && trade.isFinishTrade) {
                    NJUtil.sendDialog(getSession(), "Không thể giao dịch quá nhanh");
                    return;
                }
                if (trade == null || NJUtil.distance(trade.player1.x, trade.player2.x) > 60 || NJUtil.distance(trade.player1.y, trade.player2.y) > 60 || trade.player1.map.mapId != trade.player2.map.mapId || trade.step1 < 1 || trade.step2 < 1 || trade.isTimeOver()) {
                    if (trade != null) {
                        doCancelTrade();
                    }
                    return;
                }
                isTrade = true;
                Player player1 = trade.player1;
                Player player2 = trade.player2;
                long xup1 = player1.getXu();
                long xup2 = player2.getXu();
                long sumBegin = xup1 + xup2;
                boolean isCancel = false;
                Vector<Item> itemTrades1 = trade.getItemTrade(0);
                for (Item item1 : itemTrades1) {
                    if (item1 == null || player1.itemBags[item1.indexUI] == null || player1.itemBags[item1.indexUI].isLock || !player1.itemBags[item1.indexUI].equals(item1)) {
                        isCancel = true;
                        break;
                    }
                }
                Vector<Item> itemTrades2 = trade.getItemTrade(1);
                for (Item element : itemTrades2) {
                    if (element == null || player2.itemBags[element.indexUI] == null || player2.itemBags[element.indexUI].isLock || !player2.itemBags[element.indexUI].equals(element)) {
                        isCancel = true;
                        break;
                    }
                }
                if (trade.coin1 < 0 || trade.coin2 < 0 || player1.getXu() < trade.coin1 || player2.getXu() < trade.coin2 || isCancel) {
                    doCancelTrade();
                    ServerController.khoaLogin(getSession().username + getSession().getClientAddress());
                    return;
                }
                if (player1.playerId == playerId && trade.step1 == 1) {
                    ++trade.step1;
                    message = new Message(Cmd.TRADE_ACCEPT);
                    NJUtil.sendMessage(player2.getSession(), message);
                } else if (player2.playerId == playerId && trade.step2 == 1) {
                    ++trade.step2;
                    message = new Message(Cmd.TRADE_ACCEPT);
                    NJUtil.sendMessage(player1.getSession(), message);
                }
                if (trade.step1 >= 2 && trade.step2 >= 2) {
                    boolean isTrade2 = player1.isItemToBag(itemTrades2, itemTrades1);
                    boolean isTrade3 = player2.isItemToBag(itemTrades1, itemTrades2);
                    if (!isTrade2 && !isTrade3) {
                        NJUtil.sendDialog(player1.getSession(), AlertFunction.FULL_BAG_TRADE(player1.getSession()));
                        doCancelTrade();
                    } else if (!isTrade2) {
                        NJUtil.sendDialog(player1.getSession(), AlertFunction.FULL_BAG_TRADE(player1.getSession()));
                        NJUtil.sendDialog(player2.getSession(), AlertFunction.FULL_BAG_TRADE1(player2.getSession()));
                        doCancelTrade();
                    } else if (!isTrade3) {
                        NJUtil.sendDialog(player2.getSession(), AlertFunction.FULL_BAG_TRADE(player2.getSession()));
                        NJUtil.sendDialog(player1.getSession(), AlertFunction.FULL_BAG_TRADE1(player1.getSession()));
                        doCancelTrade();
                    } else {
                        //int[] index1 = new int[itemTrades1.size()];
                        //int[] index2 = new int[itemTrades2.size()];
                        if (trade.itemTrades1 == null || trade.itemTrades2 == null) {
                            doCancelTrade();
                            return;
                        }
                        for (Item element : itemTrades1) {
                            try {
                                //index1[k] = itemTrades1.get(k).indexUI;
                                player1.removeItem(element.indexUI);
                            } catch (Exception e) {
                                doCancelTrade();
                                return;
                            }
                        }
                        for (Item element : itemTrades2) {
                            try {
                                //index2[k] = itemTrades2.get(k).indexUI;
                                player2.removeItem(element.indexUI);
                            } catch (Exception e) {
                                doCancelTrade();
                                return;
                            }
                        }
                        StringBuilder itemBag1 = new StringBuilder();
                        for (Item value : itemTrades1) {
                            try {
                                itemBag1.append(value.itemId).append("$")
                                    .append(value.getTemplateId()).append("$")
                                    .append(value.typeUI).append("$")
                                    .append(value.indexUI).append("$")
                                    .append(value.sys).append("$")
                                    .append(value.expires).append("$")
                                    .append(value.isLock).append("$")
                                    .append(value.upgrade).append("$")
                                    .append(value.getOptionInfo()).append("$")
                                    .append(value.quantity).append("$")
                                    .append(value.saleCoinLock).append("#");
                            } catch (Exception e) {
                            }
                            player2.addItemToBag(value);
                        }
                        StringBuilder itemBag2 = new StringBuilder();
                        for (Item item : itemTrades2) {
                            try {
                                itemBag2.append(item.itemId).append("$")
                                    .append(item.getTemplateId()).append("$")
                                    .append(item.typeUI).append("$")
                                    .append(item.indexUI).append("$")
                                    .append(item.sys).append("$")
                                    .append(item.expires).append("$")
                                    .append(item.isLock).append("$")
                                    .append(item.upgrade).append("$")
                                    .append(item.getOptionInfo()).append("$")
                                    .append(item.quantity).append("$")
                                    .append(item.saleCoinLock).append("#");
                            } catch (Exception e) {
                            }
                            player1.addItemToBag(item);
                        }
                        player1.subXu(trade.coin1);
                        player1.addXu(trade.coin2);
                        player2.subXu(trade.coin2);
                        player2.addXu(trade.coin1);
                        player1.savezaLog("Trao doi voi " + player2.name, trade.coin1 + "@" + trade.coin2 + "@" + itemBag1 + "@" + itemBag2 + "@" + player1.getXu() + "@" + player2.getXu() + "@" + xup1 + "@" + xup2);
                        player2.savezaLog("Trao doi voi " + player1.name, trade.coin2 + "@" + trade.coin1 + "@" + itemBag2 + "@" + itemBag1 + "@" + player2.getXu() + "@" + player1.getXu() + "@" + xup2 + "@" + xup1);
                        try {
                            Database.savePlayer(player1, player1.map.getTemplateId());
                            Database.savePlayer(player2, player2.map.getTemplateId());
                        } catch (Exception e) {
                        }
                        long sumEnd = (long) player1.getXu() + (long) player2.getXu();
                        if (sumEnd > sumBegin) {
                            LOGGER.warn("{},{}. Hack coin", player1.getStringBaseInfo(), player2.getStringBaseInfo());
                            player1.khoaNhanVat();
                            player2.khoaNhanVat();
                            Database.saveLogAll(player1.name, "bi khoa hack trade tai doaccepttrade char", "hack");
                            Database.saveLogAll(player2.name, "bi khoa hack trade tai doaccepttrade char", "hack");
                            return;
                        }
                        int n = 0;
                        trade.coin2 = n;
                        trade.coin1 = n;
                        trade.itemTrades1 = null;
                        trade.itemTrades2 = null;
                        trade.isFinishTrade = true;
                        message = new Message(Cmd.TRADE_OK);
                        message.writeInt(player1.getXu());
                        player1.getSession().sendMessage(message);
                        message = new Message(Cmd.TRADE_OK);
                        message.writeInt(player2.getXu());
                        player2.getSession().sendMessage(message);
                        message.cleanup();
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
            if (isTrade) {
                message = new Message(Cmd.TRADE_CANCEL);
                NJUtil.sendMessage(getSession(), message);
            }
        }
    }

    public void doCancelTrade() {
        try {
            Trade trade = ALL_CHAR_TRADE.remove(idTrade);
            if (trade != null) {
                Player p1 = trade.player1;
                Player p2 = trade.player2;
                p1.idTrade = -1;
                p2.idTrade = -1;
                Message message = new Message(Cmd.TRADE_CANCEL);
                NJUtil.sendMessage(p1.getSession(), message);
                NJUtil.sendMessage(p2.getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doItemTrade(Message message) {
        if (!GameServer.openTrade) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (idTrade == -1 || isControlCharNhanBan()) {
            return;
        }
        try {
            int maxLength = 12;
            Trade trade = ALL_CHAR_TRADE.get(idTrade);
            if (trade != null) {
                Player playerMap = null;
                Vector<Item> itemTrades = new Vector<>();
                Hashtable<Integer, String> checkindex = new Hashtable<>();
                int coin = 0;
                if (trade.player1.playerId == playerId) {
                    ++trade.step1;
                    playerMap = trade.player2;
                    coin = message.readInt();
                    int size = message.readByte();
                    long sum = (long) playerMap.getXu() + (long) coin;
                    if (sum > maxCoin) {
                        doCancelTrade();
                        return;
                    }
                    if (playerMap.countFreeBag() < size) {
                        NJUtil.sendDialog(getSession(), AlertFunction.FULL_BAG_TRADE1(getSession()));
                        doCancelTrade();
                        return;
                    }
                    if (size > maxLength) {
                        NJUtil.sendDialog(getSession(), "Không thể giao dịch quá nhiều vật phẩm.");
                        doCancelTrade();
                        return;
                    }
                    if (coin < 0 || coin > trade.player1.getXu()) {
                        trade.coin1 = 0;
                        coin = 0;
                    } else {
                        trade.coin1 = coin;
                    }
                    for (int i = 0; i < size; ++i) {
                        int index = message.readByte();
                        if (checkindex.get(index) != null || itemBags[index].isLock || itemBags[index].quantity >= 30000 || trade.isContainItem(itemBags[index], 0) || itemTrades.contains(itemBags[index])) {
                            NJUtil.sendDialog(getSession(), "Vật phẩm giao dịch không hợp lệ.");
                            NJUtil.sleep(5000L);
                            doCancelTrade();
                            if (checkindex.get(index) != null || trade.isContainItem(itemBags[index], 0) || itemTrades.contains(itemBags[index])) {
                                Database.saveLogAll(getSession().username, name + " hack giao dich ", "hack");
                                khoaNhanVat();
                            }
                            return;
                        }
                        checkindex.put(index, String.valueOf(index));
                        itemTrades.add(itemBags[index]);
                        trade.isAddItemTrade(itemBags[index], 0);
                    }
                } else if (trade.player2.playerId == playerId) {
                    ++trade.step2;
                    playerMap = trade.player1;
                    int coin2 = message.readInt();
                    trade.coin2 = coin2;
                    coin = coin2;
                    int size = message.readByte();
                    long sum = (long) playerMap.getXu() + (long) coin;
                    if (sum > maxCoin) {
                        doCancelTrade();
                        return;
                    }
                    if (playerMap.countFreeBag() < size) {
                        NJUtil.sendDialog(getSession(), AlertFunction.FULL_BAG_TRADE1(getSession()));
                        doCancelTrade();
                        return;
                    }
                    if (size > maxLength) {
                        NJUtil.sendDialog(getSession(), "Không thể giao dịch quá nhiều vật phẩm.");
                        doCancelTrade();
                        return;
                    }
                    if (coin < 0 || coin > trade.player2.getXu()) {
                        trade.coin2 = 0;
                        coin = 0;
                    } else {
                        trade.coin2 = coin;
                    }
                    for (int i = 0; i < size; ++i) {
                        int index = message.readByte();
                        if (checkindex.get(index) != null || itemBags[index].isLock || itemBags[index].quantity >= 30000 || trade.isContainItem(itemBags[index], 1) || itemTrades.contains(itemBags[index])) {
                            NJUtil.sendDialog(getSession(), "Vật phẩm giao dịch không hợp lệ.");
                            NJUtil.sleep(5000L);
                            doCancelTrade();
                            if (checkindex.get(index) != null || trade.isContainItem(itemBags[index], 1) || itemTrades.contains(itemBags[index])) {
                                Database.saveLogAll(getSession().username, name + " hack giao dich ", "hack");
                                khoaNhanVat();
                            }
                            return;
                        }
                        checkindex.put(index, String.valueOf(index));
                        itemTrades.add(itemBags[index]);
                        trade.isAddItemTrade(itemBags[index], 1);
                    }
                }
                message = new Message(Cmd.TRADE_LOCK_ITEM);
                message.writeInt(coin);
                message.writeByte(itemTrades.size());
                for (Item itemTrade : itemTrades) {
                    message.writeShort(itemTrade.template.itemTemplateId);
                    if (itemTrade.isTypeBody() || (itemTrade.isTypeGem() && conn.isVersion144())) {
                        message.writeByte(itemTrade.upgrade);
                    }
                    message.writeBoolean(itemTrade.expires != -1L);
                    message.writeShort(itemTrade.quantity);
                }
                if (playerMap != null) {
                    NJUtil.sendMessage(playerMap.getSession(), message);
                }
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doCancelTrade1() {
        try {
            Message message = new Message(Cmd.TRADE_CANCEL);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doAddCuuSat(Message message) {
        try {
            if (isControlCharNhanBan() || isNhanban()) {
                return;
            }
            if (isLock) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            if (map.template.isMapChienTruong()) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_CUUSAT(getSession()));
                return;
            }
            Player playerMap;
            try {
                playerMap = map.getPlayer(message.readInt());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null || playerMap.status == ME_DIE || (killPlayer != null && killPlayer.player.equals(playerMap))) {
                return;
            }
            if (map.isLang()) {
                NJUtil.sendServer(getSession(), AlertFunction.TEST_ERROR_MAP(getSession()));
                return;
            }
            if (killPlayer != null) {
                NJUtil.sendServer(getSession(), AlertFunction.HAVE_CUU_SAT(getSession()));
                return;
            }
            if (pk >= 14) {
                NJUtil.sendServer(getSession(), AlertFunction.PK_MAX_2(getSession()));
                return;
            }
            if (playerMap.isKhangDoSat()) {
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            if (testSkill != null) {
                if (testSkill.player2.equals(playerMap) && testSkill.player1 != null) {
                    NJUtil.sendServer(getSession(), AlertFunction.TIME_IN_TEST(getSession()));
                    return;
                }
                if (testSkill.player2.equals(playerMap) && testSkill.player1 == null) {
                    testSkill = null;
                }
            }
            checkClearEffect11();
            checkClearEffect12();
            killPlayer = new KillPlayer(playerMap);
            message = new Message(Cmd.ADD_CUU_SAT);
            message.writeInt(playerId);
            NJUtil.sendMessage(playerMap.getSession(), message);
            message = new Message(Cmd.ME_CUU_SAT);
            message.writeInt(playerMap.playerId);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAddGiftNewDay() {
        if (!dayLogin.equals(NJUtil.getDayOpen())) {
            dayLogin = NJUtil.getDayOpen();
            totalNvNguyetNhan = 20;
            useDanhVongPhu = 6;
            // new day gift here
        }
    }

    public void doAddParty(Message message) {
        try {
            Player playerMap;
            try {
                playerMap = ServerController.hnPlayers.get(message.readUTF());
            } catch (Exception e) {
                return;
            }
            if (playerMap == null) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                return;
            }
            if (isNhanban()) {
                return;
            }
            if ((isControlCharNhanBan() && !playerMap.isControlCharNhanBan()) || (!isControlCharNhanBan() && playerMap.isControlCharNhanBan())) {
                NJUtil.sendServer(getSession(), AlertFunction.CAN_NOT_INVITE_PARTY(getSession()));
                return;
            }
            if (party != null && party.isFull()) {
                return;
            }
            if (party != null && !party.isTeamLeader(this)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_TEAMLEADER(getSession()));
                return;
            }
            if (party != null && playerMap.party != null && party.equals(playerMap.party)) {
                NJUtil.sendServer(getSession(), playerMap.name + AlertFunction.FRIEND_TEAM(getSession()));
                return;
            }
            if (playerMap.party != null) {
                NJUtil.sendServer(getSession(), AlertFunction.TEAMED(getSession()));
                return;
            }
            if ((playerMap.getTypePk() == PK_PHE1 && getTypePk() == PK_PHE2) || (playerMap.getTypePk() == PK_PHE1 && getTypePk() == PK_PHE2)) {
                NJUtil.sendServer(getSession(), AlertFunction.PHE_DIFFERENT1(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            if (playerMap.isNotEditParty2()) {
                return;
            }
            if (party == null) {
                if (map.getPts().size() >= Map.maxParty) {
                    NJUtil.sendServer(getSession(), AlertFunction.MAP_FULL_PT(getSession()));
                    return;
                }
                (party = new Party(this)).sendParty();
            }
            if (!party.setInvite(playerMap.playerId)) {
                message = new Message(Cmd.PARTY_INVITE);
                message.writeInt(playerId);
                message.writeUTF(name);
                NJUtil.sendMessage(playerMap.getSession(), message);
            } else {
                NJUtil.sendServer(getSession(), AlertFunction.SEND_INVITE(getSession()));
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAdminMove(Message message) {
        try {
            if (getSession().type_admin == 0) {
                return;
            }
            int nextTemplateId = message.readByte();
            doChangeMap(nextTemplateId, false, "admin move");
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public boolean doAttacNpc(Npc npc, int dameHit, int perRan, boolean isReactDame, boolean isChiMang, boolean isUpdame) {
        Message message;
        try {
            Player player = getPlayerMainControl();
            int timeFire = 0;
            int timeIce = 0;
            int timeWind = 0;
            if (!isReactDame) {
                if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 48) {
                    if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                        return false;
                    }
                    if (npc.isBoss() || npc.template.npcTemplateId == 112 || npc.template.npcTemplateId == 113) {
                        NJUtil.sendServer(getSession(), AlertFunction.TOO_STRONG(getSession()));
                        return false;
                    }
                    npc.timeDisable = myskill.options.firstElement().param * 2;
                    message = new Message(Cmd.NPC_IS_DISABLE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(true);
                    sendToPlayer(message, true);
                    return false;
                } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 50) {
                    if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                        return false;
                    }
                    if (npc.isBoss() || map.isDunVA || map.isDunClan || map.isDunPB || map.template.isMapChienTruong()) {
                        NJUtil.sendServer(getSession(), AlertFunction.TOO_STRONG(getSession()));
                        return false;
                    }
                    npc.status = Npc.STATUS_DIE;
                    npc.hp = npc.template.hp;
                    npc.hits.clear();
                    int itx = npc.pointx;
                    int ity = autoFall(itx, npc.pointy);
                    ItemMap itemMap = new ItemMap(new Item(218), playerId, itx, ity);
                    itemMap.item.quantity = myskill.options.firstElement().param;
                    map.addItemMap(itemMap);
                    message = new Message(Cmd.NPC_CHANGE);
                    message.writeByte(npc.npcId);
                    message.writeShort(itemMap.itemMapId);
                    message.writeShort(itemMap.item.template.itemTemplateId);
                    message.writeShort(itemMap.x);
                    message.writeShort(itemMap.y);
                    sendToPlayer(message, true);
                    return false;
                } else if (player.myskill.template.skillTemplateId == 42) {
                    if (player.skillDao35 == 0) {
                        player.skillDao35 = 1;
                        x = npc.pointx;
                        y = npc.pointy;
                        moveFastNpc(npc.npcId);
                        return false;
                    }
                    if (player.skillDao35 == 1) {
                        player.skillDao35 = 0;
                        return isHit(npc, player.dame_full, false, false, timeFire, timeIce, timeWind);
                    }
                } else {
                    if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 55) {
                        npc.timeDontMove = player.myskill.options.firstElement().param * 2;
                        message = new Message(Cmd.NPC_IS_MOVE);
                        message.writeByte(npc.npcId);
                        message.writeBoolean(true);
                        sendToPlayer(message, true);
                        return false;
                    }
                    if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 24) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeFire = 4;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 34) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeFire = 8;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 25) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeIce = 3;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 35) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeIce = 6;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 26) {
                        if (NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                            timeWind = 2;
                        }
                    } else if (player.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 36 && NJUtil.probability(player.myskill.options.firstElement().param, 100 - player.myskill.options.firstElement().param) == 0) {
                        timeWind = 4;
                    }
                }
            }
            if (npc.miss - player.exactly > 0) {
                int perMiss = npc.miss - player.exactly;
                if (NJUtil.probability(perMiss, 1000 - perMiss) == 0) {
                    if (!isReactDame) {
                        message = new Message(Cmd.NPC_MISS);
                        message.writeByte(npc.npcId);
                        message.writeInt(npc.hp);
                        sendToPlayer(message, true);
                    }
                    return true;
                }
            }
            int typeSys = NJUtil.typeSys(getSys(), npc.sys);
            int perRes = -10;
            if (npc.level < 10) {
                perRes = 0;
            }
            int perSys = 0;
            if (typeSys == 1) {
                perSys = (npc.template.sysDown + sysUp) * 100 / npc.template.hp;
            } else if (typeSys == 2) {
                if (npc.template.sysUp - sysDown > 0) {
                    perSys = (npc.template.sysUp - sysDown) * -100 / npc.template.hp;
                }
            }
            if (perSys > 60) {
                perSys = 60;
            } else if (perSys < -60) {
                perSys = -60;
            }
            int per = perRes + perRan + perSys;
            dameHit += dameHit * per / 100;
            if (npc.sys == 1) {
                dameHit += player.dameUpFire;
                dameHit += dameHit * player.perDameUpFire / 100;
            } else if (npc.sys == 2) {
                dameHit += player.dameUpIce;
                dameHit += dameHit * player.perDameUpIce / 100;
            } else if (npc.sys == 3) {
                dameHit += player.dameUpWind;
                dameHit += dameHit * player.perDameUpWind / 100;
            }
            if (isChiMang) {
                int dm = (int) (dameHit * (player.dameFatalUp / 100.0f + player.dameFatalHard));
                if (dm > dameHit) {
                    dameHit = dm;
                }
            } else if (NJUtil.probability(player.fatal, indexFatal - player.fatal) == 0) {
                isChiMang = true;
                int dm = (int) (dameHit * (player.dameFatalUp / 100.0f + player.dameFatalHard));
                dm += damChiMang;
                if (dm > dameHit) {
                    dameHit = dm;
                }
            }
            return isHit(npc, dameHit, isChiMang, isReactDame, timeFire, timeIce, timeWind);
        } catch (Exception e) {
            LOGGER.error("", e);
            return false;
        }
    }

    public void doAttackBoth(Message message, int type) {
        try {
            if (isNhanban() || capcha != null) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            if (!isAttack(false)) {
                return;
            }
            if (System.currentTimeMillis() - playerMain.lastSkill <= 200L) {
                return;
            }
            playerMain.lastSkill = System.currentTimeMillis();
            Vector<Byte> npcIndexs = new Vector<>();
            Vector<Integer> playerIndexs = new Vector<>();
            int size = message.readByte();
            try {
                for (int i = 0; i < size; ++i) {
                    byte idd = message.readByte();
                    if (idd == map.npcs.size() && privateNpc != null) {
                        if (privateNpc.status != Npc.STATUS_DIE && !npcIndexs.contains(idd)) {
                            npcIndexs.add(idd);
                        }
                    } else {
                        try {
                            if (map.npcs.get(idd).status != Npc.STATUS_DIE && !npcIndexs.contains(idd)) {
                                npcIndexs.add(idd);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e) {
            }
            try {
                for (int i = 0; i < size; ++i) {
                    int idd2 = message.readInt();
                    if (!playerIndexs.contains(idd2)) {
                        playerIndexs.add(idd2);
                    }
                }
            } catch (Exception e) {
            }
            if (npcIndexs.size() + playerIndexs.size() == 0 || npcIndexs.size() + playerIndexs.size() > playerMain.getSkillValue().maxFight) {
                return;
            }
            message = new Message(Cmd.PLAYER_ATTACK_N_P);
            message.writeInt(playerId);
            message.writeByte(playerMain.getSkillValue().template.skillTemplateId);
            message.writeByte(npcIndexs.size());
            for (Byte npcIndex : npcIndexs) {
                message.writeByte(npcIndex);
            }
            for (Integer playerIndex : playerIndexs) {
                message.writeInt(playerIndex);
            }
            sendLimitSpace(message, true);
            if (type == 1) {
                Point point = attackNpc(npcIndexs, null);
                attackPlayer(playerIndexs, point);
            } else if (type == 2) {
                Point point = attackPlayer(playerIndexs, null);
                attackNpc(npcIndexs, point);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAttackNpc(Message message) {
        try {
            if (isNhanban()) {
                return;
            }
            if (capcha != null) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            if (!isAttack(false)) {
                return;
            }
            if (System.currentTimeMillis() - playerMain.lastSkill <= 200L) {
                return;
            }
            playerMain.lastSkill = System.currentTimeMillis();
            Vector<Byte> npcIndexs = new Vector<>();
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    byte idd = message.readByte();
                    if (idd == map.npcs.size() && privateNpc != null) {
                        if (privateNpc.status == Npc.STATUS_DIE || npcIndexs.contains(idd)) {
                            continue;
                        }
                        npcIndexs.add(idd);
                    } else {
                        try {
                            if (map.npcs.get(idd).status == Npc.STATUS_DIE || npcIndexs.contains(idd)) {
                                continue;
                            }
                            npcIndexs.add(idd);
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception ex2) {
                if (npcIndexs.size() == 0 || npcIndexs.size() > playerMain.myskill.maxFight) {
                    return;
                }
                message = new Message(Cmd.PLAYER_ATTACK_NPC);
                message.writeInt(playerId);
                message.writeByte(playerMain.getSkillValue().template.skillTemplateId);
                for (Byte npcIndex : npcIndexs) {
                    message.writeByte(npcIndex);
                }
                sendLimitSpace(message, true);
                attackNpc(npcIndexs, null);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doAttackPlayer(Message message) {
        try {
            if (isNhanban() || capcha != null) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            if (!isAttack(false) || System.currentTimeMillis() - playerMain.lastSkill <= 200L) {
                return;
            }
            playerMain.lastSkill = System.currentTimeMillis();
            Vector<Integer> playerIndexs = new Vector<>();
            try {
                //noinspection InfiniteLoopStatement
                while (true) {
                    int idd = message.readInt();
                    if (!playerIndexs.contains(idd)) {
                        playerIndexs.add(idd);
                    }
                }
            } catch (Exception e) {
                if (playerIndexs.size() == 0 || playerIndexs.size() > playerMain.myskill.maxFight) {
                    return;
                }
                attackPlayer(playerIndexs, null);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public int doAttactPlayer(Player p2, int dame, boolean resDame, boolean isChiMang, boolean isUpdame) {
        if (MixedArena.isMapArena(map.template.mapTemplateId) && (map.template.mapTemplateId == 150 || map.template.mapTemplateId == 151 || map.template.mapTemplateId == 152) && ((DunMixedArena) map).checkInvincible(p2)) {
            return 0;
        }
        Player playerMain = getPlayerMainControl();
        Player player2 = p2.getPlayerMainControl();
        boolean isMiss = false;
        dame += playerMain.dam_player;
        int dameHit = dame / 5;
        for (int i = 0; i < player2.effects.size(); ++i) {
            if (player2.effects.get(i).template.type == 1) {
                dameHit *= dameFire;
                break;
            }
        }
        if (player2.checkDongBang(this)) {
            return 0;
        }
        if (NJUtil.probability(player2.missAll, 100 - player2.missAll) == 0) {
            isMiss = true;
        } else if (player2.miss - playerMain.exactly > 0) {
            int perMiss = player2.miss - playerMain.exactly;
            if (perMiss <= 9000 && perMiss > 1200) {
                perMiss = 1200;
            }
            if (NJUtil.probability(perMiss, maxmiss - perMiss) == 0) {
                isMiss = true;
            }
        }
        if (isMiss) {
            return 0;
        }
        int typeSys = NJUtil.typeSys(getSys(), player2.getSys());
        int perRes = 0;
        if (getSys() == 1) {
            perRes = player2.resFire * 100 / -indexRes;
        } else if (getSys() == 2) {
            perRes = player2.resIce * 100 / -indexRes;
        } else if (getSys() == 3) {
            perRes = player2.resWind * 100 / -indexRes;
        }
        int perRan = NJUtil.randomNumber(7) * (NJUtil.randomBoolean() ? -1 : 1);
        int perSys = 0;
        if (typeSys == 1) {
            perSys = (player2.sysUp + playerMain.sysDown) * 100 / player2.hp_full;
        } else if (typeSys == 2) {
            if (player2.sysDown - playerMain.sysUp > 0) {
                perSys = (player2.sysDown - playerMain.sysUp) * -100 / player2.hp_full;
            }
        }
        if (perRes < -60) {
            perRes = -60;
        }
        if (perSys > 60) {
            perSys = 60;
        } else if (perSys < -60) {
            perSys = -60;
        }
        if (player2.haveLongDenOngTrang()) {
            perRes = 0;
        }
        int per = perRes + perRan + perSys;
        dameHit += dameHit * per / 100;
        if (playerMain.getSys() == 1) {
            dameHit -= player2.dameDownFire;
            dameHit += playerMain.dameUpFire;
            dameHit += dameHit * perDameUpFire / 100;
        } else if (playerMain.getSys() == 2) {
            dameHit -= player2.dameDownIce;
            dameHit += dameUpIce;
            dameHit += dameHit * playerMain.perDameUpIce / 100;
        } else if (playerMain.getSys() == 3) {
            dameHit -= player2.dameDownWind;
            dameHit += playerMain.dameUpWind;
            dameHit += dameHit * playerMain.perDameUpWind / 100;
        }
        dameHit = dameHit * playerMain.getPercentDamPhanThan() / 100;
        if (isChiMang) {
            int dm = (int) (dameHit * (playerMain.dameFatalHard + (playerMain.dameFatalUp - player2.dameFatalDown) / 100.0f));
            if (dm > dameHit) {
                dameHit = dm;
            }
        } else if (!resDame && NJUtil.probability(playerMain.fatal, indexFatal - playerMain.fatal) == 0) {
            isChiMang = true;
            int dm = (int) (dameHit * (playerMain.dameFatalHard + (playerMain.dameFatalUp - player2.dameFatalDown) / 100.0f));
            dm += damChiMang;
            if (dm > dameHit) {
                dameHit = dm;
            }
        }
        if (isUpdame) {
            dameHit += dameHit * playerMain.perDameInvi / 100;
        }
        if (dameHit < 0) {
            dameHit = 1;
        }
        if (isAdminUseCheat && player2.isAdmin()) {
            dameHit = 1;
        }
        player2.hp -= dameHit;
        if (player2.hp <= 0 && map.isDunVD) {
            playerMain.checkStepNguyeNhanTask(NguyetNhanTask.TYPE_CHIEN_THANG_LOI_DAI, 1);
        }
        if (isChiMang) {
            return dameHit * -1;
        }
        return dameHit;
    }

    public void doBack(String posback) {
        if (status != ME_DIE) {
            goReturnHack();
            return;
        }
        if (map.template.mapTemplateId == 130 || map.template.mapTemplateId == 131 || map.template.mapTemplateId == 132) {
            if (getTypePk() == PK_PHE1) {
                doChangeMap1(131, map.zoneId, "player doBack");
            } else if (getTypePk() == PK_PHE2) {
                doChangeMap1(132, map.zoneId, "player doBack 1");
            } else {
                doChangeMap(27, true, "doback " + posback);
            }
            return;
        }
        if (map.isDunClan) {
            doBackDunClan();
            return;
        }
        if (map.isDunPB) {
            doBackDunPB();
            return;
        }
        if (map.isDunVA) {
            doBackDunVA();
            return;
        }
        if (map.isDunVD && map.isGiaiDau() && map.isGiaiDauNinjaTaiNang()) {
            goGiaiDau();
            return;
        }
        if (map.isDunVD && map.isGiaiDau() && map.isGiaiDauNinjaDenhat()) {
            goGiaiDauClass();
            return;
        }
        if (map.template.isMapChienTruong()) {
            if (map.giatocchien != null) {
                doBackGTChien();
            } else {
                doBackChienTruong();
            }
            return;
        }
        try {
            Player playerMain = getPlayerMainControl();
            if (status == ME_DIE) {
                status = ME_NORMAL;
                playerMain.hp = playerMain.hp_full;
                playerMain.mp = playerMain.mp_full;
                if (!doChangeMap((mapTemplateId_focus != 138) ? mapTemplateId_focus : 22, false, "doback 1 " + posback)) {
                    doChangeMap(72, false, "doback 2 " + posback);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBackChienTruong() {
        try {
            if (status == ME_DIE) {
                backHomeChienTruong();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBackDunClan() {
        try {
            if (status == ME_DIE) {
                status = ME_NORMAL;
                hp = hp_full;
                mp = mp_full;
                Map mapNext = DunClan.findDunClan(this, 80);
                if (mapNext == null) {
                    return;
                }
                mapClear();
                map.goOutMap(this);
                x = mapNext.template.defaultX;
                y = mapNext.template.defaultY;
                sendMapTime((int) (clan.dunClan.timeEnd / 1000L - System.currentTimeMillis() / 1000L));
                mapNext.waitGoInMap(this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBackDunPB() {
        try {
            if (status == ME_DIE) {
                if (map.template.mapTemplateId == 114 || map.template.mapTemplateId == 115 || map.template.mapTemplateId == 116) {
                    for (int i = 0; i < map.npcs.size(); ++i) {
                        map.npcs.get(i).hits.clear();
                    }
                }
                status = ME_NORMAL;
                hp = hp_full;
                mp = mp_full;
                DunPB mapNext = ((DunPB) map).findDunPB();
                mapClear();
                map.goOutMap(this);
                x = mapNext.template.defaultX;
                y = mapNext.template.defaultY;
                sendMapTime((int) (mapNext.timeEnd / 1000L - System.currentTimeMillis() / 1000L));
                mapNext.waitGoInMap(this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBackDunVA() {
        try {
            if (map.getTemplateId() == 112) {
                DunVA mapNext = DunVA.findMap(name, 113);
                DunVA mapHere = (DunVA) map;
                if (mapHere == null || mapNext == null) {
                    return;
                }
                status = ME_NORMAL;
                hp = hp_full;
                mp = mp_full;
                if (mapHere.indexFinish < 5) {
                    String[] strs = {
                        name + Alert_VN.VUOT_AI_ALERT11,
                        name + Alert_EN.VUOT_AI_ALERT11
                    };
                    mapNext.sendAlert(strs);
                } else if (mapHere.indexFinish == 5 && !mapHere.isAlert) {
                    mapHere.isAlert = true;
                    mapNext.sendAlert(new String[]{ Alert_VN.VUOT_AI_ALERT10, Alert_EN.VUOT_AI_ALERT10 });
                }
                mapClear();
                map.goOutMap(this);
                x = mapNext.template.defaultX;
                y = mapNext.template.defaultY;
                mapNext.waitGoInMap(this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBackGTChien() {
        try {
            if (status == ME_DIE) {
                backHomeGTChien();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBagSort() {
        try {
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            Vector<Item> items = new Vector<>();
            if (itemBags == null) {
                return;
            }
            idActionNewMenu = -1;
            for (Item item : itemBags) {
                if (item != null && item.template.isUpToUp && item.expires == -1L) {
                    items.add(item);
                }
            }
            for (int i = 0; i < items.size(); ++i) {
                Item itemi = items.get(i);
                if (itemi != null) {
                    for (int j = i + 1; j < items.size(); ++j) {
                        Item itemj = items.get(j);
                        if (itemj != null && itemi.template.equals(itemj.template) && itemi.isLock == itemj.isLock) {
                            itemi.quantity += itemj.quantity;
                            ghiloghack(itemi.getTemplateId());
                            removeItem(itemj, itemj.typeUI);
                            items.set(j, null);
                        }
                    }
                }
            }
            for (int i = 0; i < itemBags.length; ++i) {
                if (itemBags[i] != null) {
                    for (int k = 0; k <= i; ++k) {
                        if (itemBags[k] == null) {
                            itemBags[k] = itemBags[i];
                            itemBags[k].indexUI = k;
                            itemBags[i] = null;
                            break;
                        }
                    }
                }
            }
            /*for (int i = 0; i < itemBags.length && itemBags[i] != null; ++i) {
                Database.updateItem(itemBags[i]);
            }*/
            NJUtil.sendMessage(getSession(), NJUtil.messageSubCommand(Cmd.BAG_SORT));
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBoxSort() {
        try {
            Vector<Item> items = new Vector<>();
            for (Item item : itemBoxs) {
                if (item != null && item.template.isUpToUp && item.expires == -1L) {
                    items.add(item);
                }
            }
            for (int i = 0; i < items.size(); ++i) {
                Item itemi = items.get(i);
                if (itemi != null) {
                    for (int j = i + 1; j < items.size(); ++j) {
                        Item itemj = items.get(j);
                        if (itemj != null && itemi.template.equals(itemj.template) && itemi.isLock == itemj.isLock) {
                            itemi.quantity += itemj.quantity;
                            ghiloghack(itemi.getTemplateId());
                            removeItem(itemj, itemj.typeUI);
                            items.set(j, null);
                        }
                    }
                }
            }
            for (int i = 0; i < itemBoxs.length; ++i) {
                if (itemBoxs[i] != null) {
                    for (int k = 0; k <= i; ++k) {
                        if (itemBoxs[k] == null) {
                            itemBoxs[k] = itemBoxs[i];
                            itemBoxs[k].indexUI = k;
                            itemBoxs[i] = null;
                            break;
                        }
                    }
                }
            }
            /*for (int i = 0; i < itemBoxs.length && itemBoxs[i] != null; ++i) {
                Database.updateItem(itemBoxs[i]);
            }*/
            NJUtil.sendMessage(getSession(), NJUtil.messageSubCommand(Cmd.BOX_SORT));
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doBuyItemAuction(Message message) {
        try {
            if (!GameServer.openShinwa) {
                NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            int itemId = message.readInt();
            Item item = null;
            for (int i = 0; i < ServerController.shops.size(); ++i) {
                if (ServerController.shops.get(i).itemId == itemId) {
                    item = ServerController.shops.get(i);
                    break;
                }
            }
            if (item == null) {
                NJUtil.sendServer(getSession(), AlertFunction.ITEM_ERROR(getSession()));
                return;
            }
            if (item.saleShop > getXu()) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_ENOUGH_COIN1(getSession()));
                return;
            }
            long tt = item.saleShop;
            long ex = tt * 99L;
            int coinBanDuoc = (int) (ex / 100L);
            if (coinBanDuoc == 0) {
                coinBanDuoc = 1;
            }
            if (item.saleShop <= 0 || coinBanDuoc <= 0) {
                LOGGER.warn("{}. Hack gian hang {},{}", getStringBaseInfo(), item.saleShop, coinBanDuoc);
                return;
            }
            long expires = item.expires;
            item.expires = -1L;
            if (!addItemToBagNoDialog(item)) {
                item.expires = expires;
                NJUtil.sendServer(getSession(), AlertFunction.BAG_FULL(getSession()));
                return;
            }
            savezbLog("Mua ghang cua " + item.playerName, item.buyGold + "@" + item.saleShop + "@" + item.buyCoinLock + "@" + item.template.itemTemplateId + "@" + item.quantity);
            sendUpdateCoinBag(-item.saleShop);
            updateBoardNap(item.playerName, coinBanDuoc);
            ServerController.removeItemShops(item);
            resetButtonClient();
            Database.savePlayer(this, map.getTemplateId());
        } catch (Exception e) {
        }
    }

    public void doCancelParty(Message message) {
        try {
            Player playerMap = ServerController.hpPlayers.get(message.readInt());
            if (playerMap == null || playerMap.party == null) {
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            playerMap.party.clearInvite(playerId);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doCatkeo(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            int npcId = message.readShort();
            if (npcId != 142 && npcId != 143 && npcId != 144) {
                return;
            }
            Item it = findItemBag(458);
            if (it != null && map.getTemplateId() == 130) {
                if (npcId == 144) {
                    int i = 0;
                    while (i < map.npcs.size()) {
                        if (map.npcs.get(i).template.npcTemplateId == npcId) {
                            if (map.npcs.get(i).timedoikeo < 0 && getTypePk() == PK_PHE1) {
                                sendUseItemUpToUp(it.indexUI, 1);
                                useItemUpToUp(it, 1);
                                map.npcs.get(i).doikeodo();
                                callEffectMe(21);
                                break;
                            }
                            if (map.npcs.get(i).timedoikeo > 0 && getTypePk() == PK_PHE2) {
                                sendUseItemUpToUp(it.indexUI, 1);
                                useItemUpToUp(it, 1);
                                map.npcs.get(i).doikeoxanh();
                                callEffectMe(21);
                                break;
                            }
                            break;
                        } else {
                            ++i;
                        }
                    }
                } else {
                    for (int i = 0; i < map.npcs.size(); ++i) {
                        if (map.npcs.get(i).template.npcTemplateId == npcId) {
                            Npc npc = map.npcs.get(i);
                            npc.hp += it.quantity * 10;
                            sendUseItemUpToUp(it.indexUI, it.quantity);
                            useItemUpToUp(it, it.quantity);
                            callEffectMe(21);
                            break;
                        }
                    }
                }
            } else {
                NJUtil.sendServer(getSession(), "Hành trang không có kẹo.");
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public boolean doChangeDunClan() {
        if (isControlCharNhanBan()) {
            return false;
        }
        try {
            if (clan == null || clan.dunClan == null) {
                Map mapNext = Map.findMap(this, mapTemplateIdGo);
                if (mapNext == null) {
                    getSession().disconnect("Player.doChangeDunClan");
                    return false;
                }
                mapClear();
                map.goOutMap(this);
                x = mapNext.template.defaultX;
                y = mapNext.template.defaultY;
                mapNext.waitGoInMap(this);
                return false;
            } else {
                int time = (int) (clan.dunClan.timeEnd / 1000L - System.currentTimeMillis() / 1000L);
                int i = 0;
                while (i < map.template.links.size()) {
                    NLink link = map.template.links.get(i);
                    if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 <= x + 48 && link.maxX1 >= x - 48 && link.minY1 <= y + 48 && link.maxY1 >= y - 48) {
                        if (map.getTemplateId() == 80 && clan.stepDoor == 0) {
                            NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN4(getSession()));
                            lastMoveX = 0;
                            lastMoveY = 0;
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            resetPoint();
                            return false;
                        }
                        Map mapNext2 = DunClan.findDunClan(this, link.mapTemplateId2);
                        if (mapNext2 == null) {
                            lastMoveX = 0;
                            lastMoveY = 0;
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            resetPoint();
                            return false;
                        }
                        mapClear();
                        map.goOutMap(this);
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        sendMapTime(time);
                        mapNext2.waitGoInMap(this);
                        return true;
                    } else if (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 <= x + 48 && link.maxX2 >= x - 48 && link.minY2 <= y + 48 && link.maxY2 >= y - 48) {
                        if (map.getTemplateId() == 80 && clan.stepDoor == 0) {
                            NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN4(getSession()));
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            resetPoint();
                            return false;
                        }
                        Map mapNext2 = DunClan.findDunClan(this, link.mapTemplateId1);
                        if (mapNext2 == null) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            resetPoint();
                            return false;
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        sendMapTime(time);
                        mapNext2.waitGoInMap(this);
                        return true;
                    } else {
                        ++i;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public boolean doChangeDunPB() {
        if (isControlCharNhanBan()) {
            return false;
        }
        try {
            int time = (int) (((DunPB) map).timeEnd / 1000L - System.currentTimeMillis() / 1000L);
            int i = 0;
            while (i < map.template.links.size()) {
                NLink link = map.template.links.get(i);
                if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 - 48 <= x && link.maxX1 + 48 >= x && link.minY1 - 48 <= y && link.maxY1 + 48 >= y) {
                    DunPB mapNext = ((DunPB) map).findDunPB(link.mapTemplateId2);
                    if (mapNext == null || !mapNext.isOpen) {
                        NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN4(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.template.mapTemplateId >= 106 && mapNext.template.mapTemplateId <= 108 && mapNext.players.size() >= 2) {
                        NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN14(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X2;
                    y = (short) link.Y2;
                    sendMapTime(time);
                    mapNext.waitGoInMap(this);
                    return true;
                } else if (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 - 48 <= x && link.maxX2 + 48 >= x && link.minY2 - 48 <= y && link.maxY2 + 48 >= y) {
                    DunPB mapNext = ((DunPB) map).findDunPB(link.mapTemplateId1);
                    if (mapNext == null || !mapNext.isOpen) {
                        NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN4(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.template.mapTemplateId >= 106 && mapNext.template.mapTemplateId <= 108 && mapNext.players.size() >= 2) {
                        NJUtil.sendDialog(getSession(), AlertFunction.ERROR_DUN_CLAN14(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X1;
                    y = (short) link.Y1;
                    sendMapTime(time);
                    mapNext.waitGoInMap(this);
                    return true;
                } else {
                    ++i;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public boolean doChangeGiatocchien() {
        if (isControlCharNhanBan()) {
            return false;
        }
        try {
            int i = 0;
            while (i < map.template.links.size()) {
                NLink link = map.template.links.get(i);
                if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 - 48 <= x && link.maxX1 + 48 >= x && link.minY1 - 48 <= y && link.maxY1 + 48 >= y && link.mapTemplateId2 != 99 && link.mapTemplateId2 != 103) {
                    if (!Map.isGoGTChien(this)) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    Map mapNext = null;
                    if ((link.mapTemplateId2 == 104 && getTypePk() == PK_PHE1) || (link.mapTemplateId2 == 98 && getTypePk() == PK_PHE2)) {
                        NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                    } else {
                        mapNext = map.findMapGTChien(this, link.mapTemplateId2);
                    }
                    if (mapNext == null) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.template.isMapChienTruong()) {
                        Effect eff = new Effect();
                        eff.template = ServerController.effTemplates.get(14);
                        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                        eff.timeLength = 1500;
                        addEffect(eff);
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X2;
                    y = (short) link.Y2;
                    sendMapTime(map.giatocchien.getTime());
                    mapNext.waitGoInMap(this);
                    return true;
                } else if (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 - 48 <= x && link.maxX2 + 48 >= x && link.minY2 - 48 <= y && link.maxY2 + 48 >= y && link.mapTemplateId1 != 99 && link.mapTemplateId1 != 103) {
                    if (!Map.isGoGTChien(this)) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        resetPoint();
                        return false;
                    }
                    Map mapNext = null;
                    if ((link.mapTemplateId1 == 104 && getTypePk() == PK_PHE1) || (link.mapTemplateId1 == 98 && getTypePk() == PK_PHE2)) {
                        NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                    } else {
                        mapNext = map.findMapGTChien(this, link.mapTemplateId1);
                    }
                    if (mapNext == null) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.template.isMapChienTruong()) {
                        Effect eff = new Effect();
                        eff.template = ServerController.effTemplates.get(14);
                        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                        eff.timeLength = 1500;
                        addEffect(eff);
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X1;
                    y = (short) link.Y1;
                    sendMapTime(map.giatocchien.getTime());
                    mapNext.waitGoInMap(this);
                    return true;
                } else {
                    ++i;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public boolean doChangeMap(int nextTemplateId, boolean isFast, String pos) {
        if (GameServer.isServerLocal()) {
            LOGGER.info("Change map > " + pos);
        }
        try {
            if (isChangeMap || getHp() <= 0) {
                return false;
            }
            if (map != null && (map.template.mapTemplateId == 130 || map.template.mapTemplateId == 131 || map.template.mapTemplateId == 132)) {
                return doChangeMap1(nextTemplateId, map.zoneId, "player dochangemap");
            }
            if (nextTemplateId == -1 && map != null) {
                int i = 0;
                while (i < map.template.links.size()) {
                    NLink link = map.template.links.get(i);
                    if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 <= x + dxlink && link.maxX1 >= x - dxlink && link.minY1 <= y + dylink && link.maxY1 >= y - dylink) {
                        if (getTaskFinish() <= 2 || (getTaskFinish() <= 32 && map.template.mapTemplateId == 40 && link.mapTemplateId2 != 39 && link.mapTemplateId2 != 65) ||
                            (getTaskFinish() <= 4 && map.template.mapTemplateId == 21 && link.mapTemplateId2 != 22) ||
                            (getTaskFinish() <= 5 && map.template.mapTemplateId == 23 && link.mapTemplateId2 != 22) ||
                            (getTaskFinish() <= 5 && map.template.mapTemplateId == 6 && link.mapTemplateId2 != 20 && link.mapTemplateId2 != 21) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 71 && link.mapTemplateId2 != 70) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 2 && link.mapTemplateId2 != 6) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 26 && link.mapTemplateId2 != 25) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 1 && link.mapTemplateId2 != 2) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 27 && link.mapTemplateId2 != 26) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 72 && link.mapTemplateId2 != 71) ||
                            (getTaskFinish() <= 12 && map.template.mapTemplateId == 4 && link.mapTemplateId2 != 3) ||
                            (getTaskFinish() <= 12 && map.template.mapTemplateId == 60 && link.mapTemplateId2 != 28) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 6 && link.mapTemplateId2 == 7) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 39 && link.mapTemplateId2 != 72 && link.mapTemplateId2 != 46) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 5 && link.mapTemplateId2 != 4) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 29 && link.mapTemplateId2 != 60) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 7 && link.mapTemplateId2 != 6 && link.mapTemplateId2 != 5) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 40 && link.mapTemplateId2 != 39) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 30 && link.mapTemplateId2 != 31) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 8 && link.mapTemplateId2 != 7) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 46 && link.mapTemplateId2 != 39) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 31 && link.mapTemplateId2 != 30) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 47 && link.mapTemplateId2 != 46) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 9 && link.mapTemplateId2 != 8) ||
                            (getTaskFinish() <= 17 && map.template.mapTemplateId == 48 && link.mapTemplateId2 != 47) ||
                            (getTaskFinish() <= 17 && map.template.mapTemplateId == 10 && link.mapTemplateId2 != 9) ||
                            (getTaskFinish() <= 18 && map.template.mapTemplateId == 50 && link.mapTemplateId2 != 48) ||
                            (getTaskFinish() <= 18 && map.template.mapTemplateId == 11 && link.mapTemplateId2 != 10) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 33 && link.mapTemplateId2 != 61) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 50 && link.mapTemplateId2 != 48) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 12 && link.mapTemplateId2 != 11) ||
                            (getTaskFinish() <= 21 && map.template.mapTemplateId == 34 && link.mapTemplateId2 != 33) ||
                            (getTaskFinish() <= 22 && map.template.mapTemplateId == 51 && link.mapTemplateId2 != 49) ||
                            (getTaskFinish() <= 22 && map.template.mapTemplateId == 57 && link.mapTemplateId2 != 12) ||
                            (getTaskFinish() <= 23 && map.template.mapTemplateId == 35 && link.mapTemplateId2 != 34) ||
                            (getTaskFinish() <= 25 && map.template.mapTemplateId == 52 && link.mapTemplateId2 != 51) ||
                            (getTaskFinish() <= 25 && map.template.mapTemplateId == 13 && link.mapTemplateId2 != 57) ||
                            (getTaskFinish() <= 27 && map.template.mapTemplateId == 66 && link.mapTemplateId2 != 35) ||
                            (getTaskFinish() <= 27 && map.template.mapTemplateId == 14 && link.mapTemplateId2 != 13) ||
                            (getTaskFinish() <= 33 && map.template.mapTemplateId == 24 && link.mapTemplateId2 != 59) ||
                            (getTaskFinish() <= 33 && map.template.mapTemplateId == 18 && link.mapTemplateId2 != 17)
                        ) {
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            if (getTaskFinish() > ServerController.taskTemplates.lastElement().taskId) {
                                NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_TEST(getSession()));
                            } else {
                                NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_MAP(getSession()));
                            }
                            return false;
                        }
                        if (!Map.isGo(this)) {
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            return false;
                        }
                        Map mapNext = null;
                        if (map.getTemplateId() == 138) {
                            int[] aa = { 134, 135, 136, 137 };
                            mapNext = Map.findMap(this, aa[NJUtil.randomNumber(4)]);
                            mapClear();
                            map.goOutMap(this);
                            x = mapNext.template.defaultX;
                            y = mapNext.template.defaultY;
                            lastMoveX = x;
                            lastMoveY = y;
                            mapNext.waitGoInMap(this);
                            return true;
                        }
                        if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                            if (!MixedArena.checkChangeMap(this, link.mapTemplateId2)) {
                                x = (short) link.X1;
                                y = (short) link.Y1;
                                lastMoveX = x;
                                lastMoveY = y;
                                resetPoint();
                                return false;
                            }
                            mapNext = MixedArena.doChangeMap(this, link.mapTemplateId2, link.mapTemplateId1);
                        } else if ((link.mapTemplateId2 == 104 && getTypePk() == PK_PHE1) || (link.mapTemplateId2 == 98 && getTypePk() == PK_PHE2)) {
                            NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                        } else {
                            MapTemplate mt = ServerController.mapTemplates.get(link.mapTemplateId2);
                            if (mt.checkFullZone()) {
                                mt.createNewZoneMap();
                            }
                            mapNext = Map.findMap(this, link.mapTemplateId2);
                        }
                        if (mapNext == null) {
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            return false;
                        }
                        if (mapNext.template.isMapChienTruong()) {
                            Effect eff = new Effect();
                            eff.template = ServerController.effTemplates.get(14);
                            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                            eff.timeLength = 1500;
                            addEffect(eff);
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        lastMoveX = x;
                        lastMoveY = y;
                        mapNext.waitGoInMap(this);
                        return true;
                    } else if (link.mapTemplateId2 == map.template.mapTemplateId &&
                        link.minX2 <= x + dxlink &&
                        link.maxX2 >= x - dxlink &&
                        link.minY2 <= y + dylink &&
                        link.maxY2 >= y - dylink
                    ) {
                        if (getTaskFinish() <= 2 || (getTaskFinish() <= 32 && map.template.mapTemplateId == 40 && link.mapTemplateId1 != 39 && link.mapTemplateId1 != 65) ||
                            (getTaskFinish() <= 4 && map.template.mapTemplateId == 21 && link.mapTemplateId1 != 22) ||
                            (getTaskFinish() <= 5 && map.template.mapTemplateId == 23 && link.mapTemplateId1 != 22) ||
                            (getTaskFinish() <= 5 && map.template.mapTemplateId == 6 && link.mapTemplateId1 != 20 && link.mapTemplateId1 != 21) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 71 && link.mapTemplateId1 != 70) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 2 && link.mapTemplateId1 != 6) ||
                            (getTaskFinish() <= 6 && map.template.mapTemplateId == 26 && link.mapTemplateId1 != 25) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 1 && link.mapTemplateId1 != 2) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 27 && link.mapTemplateId1 != 26) ||
                            (getTaskFinish() <= 7 && map.template.mapTemplateId == 72 && link.mapTemplateId1 != 71) ||
                            (getTaskFinish() <= 12 && map.template.mapTemplateId == 4 && link.mapTemplateId1 != 3) ||
                            (getTaskFinish() <= 12 && map.template.mapTemplateId == 60 && link.mapTemplateId1 != 28) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 6 && link.mapTemplateId1 == 7) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 39 && link.mapTemplateId1 != 72 && link.mapTemplateId1 != 46) ||
                            (getTaskFinish() <= 13 && map.template.mapTemplateId == 5 && link.mapTemplateId1 != 4) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 29 && link.mapTemplateId1 != 60) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 7 && link.mapTemplateId1 != 6 && link.mapTemplateId1 != 5) ||
                            (getTaskFinish() <= 14 && map.template.mapTemplateId == 40 && link.mapTemplateId1 != 39) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 30 && link.mapTemplateId1 != 31) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 8 && link.mapTemplateId1 != 7) ||
                            (getTaskFinish() <= 15 && map.template.mapTemplateId == 46 && link.mapTemplateId1 != 39) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 31 && link.mapTemplateId1 != 30) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 47 && link.mapTemplateId1 != 46) ||
                            (getTaskFinish() <= 16 && map.template.mapTemplateId == 9 && link.mapTemplateId1 != 8) ||
                            (getTaskFinish() <= 17 && map.template.mapTemplateId == 48 && link.mapTemplateId1 != 47) ||
                            (getTaskFinish() <= 17 && map.template.mapTemplateId == 10 && link.mapTemplateId1 != 9) ||
                            (getTaskFinish() <= 18 && map.template.mapTemplateId == 50 && link.mapTemplateId1 != 48) ||
                            (getTaskFinish() <= 18 && map.template.mapTemplateId == 11 && link.mapTemplateId1 != 10) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 33 && link.mapTemplateId1 != 61) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 50 && link.mapTemplateId1 != 48) ||
                            (getTaskFinish() <= 19 && map.template.mapTemplateId == 12 && link.mapTemplateId1 != 11) ||
                            (getTaskFinish() <= 21 && map.template.mapTemplateId == 34 && link.mapTemplateId1 != 33) ||
                            (getTaskFinish() <= 22 && map.template.mapTemplateId == 51 && link.mapTemplateId1 != 49) ||
                            (getTaskFinish() <= 22 && map.template.mapTemplateId == 57 && link.mapTemplateId1 != 12) ||
                            (getTaskFinish() <= 23 && map.template.mapTemplateId == 35 && link.mapTemplateId1 != 34) ||
                            (getTaskFinish() <= 25 && map.template.mapTemplateId == 52 && link.mapTemplateId1 != 51) ||
                            (getTaskFinish() <= 25 && map.template.mapTemplateId == 13 && link.mapTemplateId1 != 57) ||
                            (getTaskFinish() <= 27 && map.template.mapTemplateId == 66 && link.mapTemplateId1 != 35) ||
                            (getTaskFinish() <= 27 && map.template.mapTemplateId == 14 && link.mapTemplateId1 != 13) ||
                            (getTaskFinish() <= 33 && map.template.mapTemplateId == 24 && link.mapTemplateId1 != 59) ||
                            (getTaskFinish() <= 33 && map.template.mapTemplateId == 18 && link.mapTemplateId1 != 17)
                        ) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            if (getTaskFinish() > ServerController.taskTemplates.lastElement().taskId) {
                                NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_TEST(getSession()));
                            } else {
                                NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_MAP(getSession()));
                            }
                            return false;
                        }
                        if (!Map.isGo(this)) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            return false;
                        }
                        Map mapNext = null;
                        if (map.getTemplateId() == 138) {
                            int[] aa = { 134, 135, 136, 137 };
                            mapNext = Map.findMap(this, aa[NJUtil.randomNumber(4)]);
                            mapClear();
                            map.goOutMap(this);
                            x = mapNext.template.defaultX;
                            y = mapNext.template.defaultY;
                            lastMoveX = x;
                            lastMoveY = y;
                            mapNext.waitGoInMap(this);
                            return true;
                        }
                        if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                            if (!MixedArena.checkChangeMap(this, link.mapTemplateId1)) {
                                x = (short) link.X2;
                                y = (short) link.Y2;
                                lastMoveX = x;
                                lastMoveY = y;
                                resetPoint();
                                return false;
                            }
                            mapNext = MixedArena.doChangeMap(this, link.mapTemplateId1, link.mapTemplateId2);
                        } else if ((link.mapTemplateId1 == 104 && getTypePk() == PK_PHE1) || (link.mapTemplateId1 == 98 && getTypePk() == PK_PHE2)) {
                            NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                        } else {
                            MapTemplate mt = ServerController.mapTemplates.get(link.mapTemplateId1);
                            if (mt.checkFullZone()) {
                                mt.createNewZoneMap();
                            }
                            mapNext = Map.findMap(this, link.mapTemplateId1);
                        }
                        if (mapNext == null) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            lastMoveX = x;
                            lastMoveY = y;
                            resetPoint();
                            return false;
                        }
                        if (mapNext.template.isMapChienTruong()) {
                            Effect eff = new Effect();
                            eff.template = ServerController.effTemplates.get(14);
                            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                            eff.timeLength = 1500;
                            addEffect(eff);
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        lastMoveX = x;
                        lastMoveY = y;
                        mapNext.waitGoInMap(this);
                        return true;
                    } else {
                        ++i;
                    }
                }
            } else {
                if (getTaskFinish() <= 8 && nextTemplateId != 22) {
                    if (getTaskFinish() > ServerController.taskTemplates.lastElement().taskId) {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_TEST(getSession()));
                    } else {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_MAP(getSession()));
                    }
                    return false;
                }
                if (getTaskFinish() <= 16 && nextTemplateId != 22 && nextTemplateId != 1 && nextTemplateId != 27 && nextTemplateId != 72) {
                    if (getTaskFinish() > ServerController.taskTemplates.lastElement().taskId) {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_TEST(getSession()));
                    } else {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_MAP(getSession()));
                    }
                    return false;
                }
                if (!isFast &&
                    getSession().type_admin == 0 &&
                    getTaskFinish() <= 33 &&
                    nextTemplateId != 22 &&
                    nextTemplateId != 1 &&
                    nextTemplateId != 27 &&
                    nextTemplateId != 72 &&
                    nextTemplateId != 10 &&
                    nextTemplateId != 48 &&
                    nextTemplateId != 32 &&
                    getTaskFinish() < 33 &&
                    (getTaskFinish() < 28 || nextTemplateId != 38)
                ) {
                    if (getTaskFinish() > ServerController.taskTemplates.lastElement().taskId) {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_TEST(getSession()));
                    } else {
                        NJUtil.sendDialog(getSession(), AlertFunction.LIMIT_MAP(getSession()));
                    }
                    return false;
                }
                Map mapNext = Map.findMap(this, nextTemplateId);
                if (mapNext == null) {
                    return false;
                }
                mapClear();
                map.goOutMap(this);
                x = 0;
                y = 0;
                lastMoveX = x;
                lastMoveY = y;
                mapNext.waitGoInMap(this);
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public boolean doChangeMap1(int nextTemplateId, int zonee, String pos) {
        if (GameServer.isServerLocal()) {
            LOGGER.info("Change map 1 > " + pos);
        }
        try {
            if (isChangeMap) {
                return false;
            }
            if (nextTemplateId == -1) {
                int i = 0;
                while (i < map.template.links.size()) {
                    NLink link = map.template.links.get(i);
                    if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 <= x + dxlink && link.maxX1 >= x - dxlink && link.minY1 <= y + dylink && link.maxY1 >= y - dylink) {
                        if (!Map.isGo(this)) {
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            resetPoint();
                            return false;
                        }
                        Map mapNext = null;
                        if ((link.mapTemplateId2 == 132 && getTypePk() == PK_PHE1) || (link.mapTemplateId2 == 131 && getTypePk() == PK_PHE2)) {
                            NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                        } else {
                            mapNext = Map.findMap1(this, link.mapTemplateId2, zonee);
                        }
                        if (mapNext == null) {
                            x = (short) link.X1;
                            y = (short) link.Y1;
                            resetPoint();
                            return false;
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        mapNext.waitGoInMap(this);
                        sendMapTime((int) ((mapNext.timeEnd - System.currentTimeMillis()) / 1000L));
                        return true;
                    } else if (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 <= x + dxlink && link.maxX2 >= x - dxlink && link.minY2 <= y + dylink && link.maxY2 >= y - dylink) {
                        if (!Map.isGo(this)) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            resetPoint();
                            return false;
                        }
                        Map mapNext = null;
                        if ((link.mapTemplateId1 == 132 && getTypePk() == PK_PHE1) || (link.mapTemplateId1 == 131 && getTypePk() == PK_PHE2)) {
                            NJUtil.sendDialog(getSession(), AlertFunction.DONT_MOVE(getSession()));
                        } else {
                            mapNext = Map.findMap1(this, link.mapTemplateId1, zonee);
                        }
                        if (mapNext == null) {
                            x = (short) link.X2;
                            y = (short) link.Y2;
                            resetPoint();
                            return false;
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        mapNext.waitGoInMap(this);
                        sendMapTime((int) ((mapNext.timeEnd - System.currentTimeMillis()) / 1000L));
                        return true;
                    } else {
                        ++i;
                    }
                }
            } else {
                Map mapNext2 = Map.findMap1(this, nextTemplateId, zonee);
                if (mapNext2 == null) {
                    return false;
                }
                mapClear();
                map.goOutMap(this);
                x = 0;
                y = 0;
                lastMoveX = x;
                lastMoveY = y;
                mapNext2.waitGoInMap(this);
                sendMapTime((int) ((mapNext2.timeEnd - System.currentTimeMillis()) / 1000L));
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public void doChangeOptionLuyenNgoc(Item item, int level, int oldLv) {
        if (!item.isTypeGem()) {
            return;
        }
        if (item.options != null) {
            for (int i = 0; i < item.options.size(); ++i) {
                ItemOption option = item.options.elementAt(i);
                if (option.optionTemplate.itemOptionTemplateId != Item.OPT_EXP_UPGRADE) {
                    if (option.param < 0) {
                        int[] valueSub = option.optionTemplate.VALUE_GEM_GIAM;
                        if (valueSub != null) {
                            for (int j = oldLv; j < level; ++j) {
                                option.param -= valueSub[j];
                            }
                        }
                    } else {
                        int[] valueUp = option.optionTemplate.VALUE_GEM_UP;
                        if (valueUp != null) {
                            for (int j = oldLv; j < level; ++j) {
                                option.param += valueUp[j];
                            }
                        }
                    }
                }
            }
        }
    }

    public void doChangeTeamLeader(Message message) {
        try {
            byte index = message.readByte();
            if (party == null || !party.players.get(0).equals(this) || index < 0 || index >= party.players.size() || party.players.size() <= 1) {
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            Player pTeam = party.players.get(index);
            party.players.set(index, party.players.get(0));
            party.players.set(0, pTeam);
            party.changeTeamLeader(index);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChangeTypePk(Message message) {
        try {
            if (isControlCharNhanBan() || isNhanban()) {
                return;
            }
            if (isLock) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            if (EventManager.getInstance().isMapEvent(map)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_CHANGE_PK(getSession()));
                return;
            }
            if (map.template.isMapChienTruong() || map.template.mapTemplateId == 130 || map.template.mapTemplateId == 131 || map.template.mapTemplateId == 132) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_CHANGE_PK(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            byte updateTypePk = message.readByte();
            for (Effect effect : effects) {
                if (effect.template.type == 10) {
                    NJUtil.sendServer(getSession(), AlertFunction.NOT_ATT(getSession()));
                    return;
                }
            }
            if (updateTypePk != 0 && pk >= 14) {
                NJUtil.sendServer(getSession(), AlertFunction.PK_MAX_3(getSession()));
                return;
            }
            if (exp_down >= exps1[level] / 3L) {
                NJUtil.sendServer(getSession(), AlertFunction.EXP_DOWN_MAX(getSession()));
                return;
            }
            changeTypePk(updateTypePk);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public boolean doChangeVA() {
        if (isControlCharNhanBan()) {
            return false;
        }
        try {
            int i = 0;
            while (i < map.template.links.size()) {
                NLink link = map.template.links.get(i);
                if (link.mapTemplateId1 == map.template.mapTemplateId && link.minX1 - 48 <= x && link.maxX1 + 48 >= x && link.minY1 - 48 <= y && link.maxY1 + 48 >= y) {
                    if (!Map.isGo(this)) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    DunVA mapNext = DunVA.findMap(name, link.mapTemplateId2);
                    if (mapNext == null || (mapNext.getTemplateId() == 112 && mapNext.indexFinish <= 5 && mapNext.players.size() > 0)) {
                        NJUtil.sendDialog(getSession(), AlertFunction.VUOT_AI_ALERT8(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X1;
                        y = (short) link.Y1;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.getTemplateId() == 112) {
                        int index = mapNext.names.indexOf(name);
                        mapNext.isGos[index] = true;
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X2;
                    y = (short) link.Y2;
                    mapNext.waitGoInMap(this);
                    return true;
                } else if (link.mapTemplateId2 == map.template.mapTemplateId && link.minX2 - 48 <= x && link.maxX2 + 48 >= x && link.minY2 - 48 <= y && link.maxY2 + 48 >= y) {
                    if (!Map.isGo(this)) {
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        resetPoint();
                        return false;
                    }
                    DunVA mapNext = DunVA.findMap(name, link.mapTemplateId1);
                    if (mapNext == null || (mapNext.getTemplateId() == 112 && mapNext.indexFinish <= 5 && mapNext.players.size() > 0)) {
                        NJUtil.sendDialog(getSession(), AlertFunction.VUOT_AI_ALERT8(getSession()));
                        lastMoveX = 0;
                        lastMoveY = 0;
                        x = (short) link.X2;
                        y = (short) link.Y2;
                        resetPoint();
                        return false;
                    }
                    if (mapNext.getTemplateId() == 112) {
                        int index = mapNext.names.indexOf(name);
                        mapNext.isGos[index] = true;
                    }
                    mapClear();
                    map.goOutMap(this);
                    lastMoveX = 0;
                    lastMoveY = 0;
                    x = (short) link.X1;
                    y = (short) link.Y1;
                    mapNext.waitGoInMap(this);
                    return true;
                } else {
                    ++i;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        return false;
    }

    public void doChangeZone(Message message) {
        try {
            if (getHp() <= 0 || isChangeMap || map.isDun || map.isDunClan || map.isDunPB || map.isDunVD || map.getTemplateId() == 110 || map.isArena()) {
                return;
            }
            if (!isOkZone()) {
                autoDie("doChangeZone player");
                return;
            }
            int zoneId;
            int indexUI;
            try {
                zoneId = message.readByte();
                indexUI = message.readByte();
            } catch (Exception e) {
                return;
            }
            Vector<Map> list = ServerController.ALL_MAP.get(map.template.mapTemplateId);
            if (map.giatocchien != null) {
                list = map.giatocchien.maps;
            }
            if (indexUI == -1) {
                if (!isOk(13)) {
                    return;
                }
                Map.findZone(this, zoneId, list);
            } else {
                Item item = null;
                try {
                    item = itemBags[indexUI];
                } catch (Exception e) {
                }
                if (item != null) {
                    if (item.template.itemTemplateId == 35) {
                        removeItem(item, 3);
                        sendClearItemBag(item.indexUI);
                        Map.findZone(this, zoneId, list);
                    } else if (item.template.itemTemplateId == 37) {
                        Map.findZone(this, zoneId, list);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChatClan(Message message) {
        try {
            String str;
            try {
                str = message.readUTF().trim();
                if (str.length() > 100) {
                    return;
                }
                if (BadWord.checkBadWord(str)) {
                    message = new Message(Cmd.CHAT_MAP);
                    message.writeInt(playerId);
                    message.writeUTF(AlertFunction.CHAT_FILTER(getSession()));
                    getSession().sendMessage(message);
                    return;
                }
            } catch (Exception e) {
                return;
            }
            if (clan != null) {
                message = new Message(Cmd.CHAT_CLAN);
                message.writeUTF(name);
                message.writeUTF(str);
                clan.sendChat(message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChatParty(Message message) {
        try {
            String str;
            try {
                str = message.readUTF();
                if (BadWord.checkBadWord(str)) {
                    message = new Message(Cmd.CHAT_MAP);
                    message.writeInt(playerId);
                    message.writeUTF(AlertFunction.CHAT_FILTER(getSession()));
                    getSession().sendMessage(message);
                    return;
                }
            } catch (Exception e) {
                return;
            }
            message = new Message(Cmd.CHAT_PARTY);
            message.writeUTF(name);
            message.writeUTF(str);
            if (party != null) {
                party.sendTeam(message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChatPlayer(Message message) {
        try {
            String str;
            try {
                str = message.readUTF().trim();
                if (str.length() > 50) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
           if (str.equalsIgnoreCase("captcha")) {
            initCapCha(); // Gọi phương thức khởi tạo CAPTCHA
            return;
        }
            if (AdminChat.handleChat(this, str)) {
               
                return;
            }
            if (str.isEmpty()) {
                return;
            }
            if (BadWord.checkBadWord(str)) {
                message = new Message(Cmd.CHAT_MAP);
                message.writeInt(playerId);
                message.writeUTF(AlertFunction.CHAT_FILTER(getSession()));
                getSession().sendMessage(message);
                return;
            }
            str = str.replaceAll("\\s+", " ").trim();
            str = BadWord.replaceStringWithSpace(str);
            str = BadWord.replaceExtendDomain(str);
            message = new Message(Cmd.CHAT_MAP);
            message.writeInt(playerId);
            message.writeUTF(str);
            sendToPlayer(message, true);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChatPrivate(Message message) {
        try {
            if (isControlCharNhanBan()) {
                return;
            }
            String playerName = "";
            String str = "";
            Player playerMap = null;
            try {
                playerName = message.readUTF();
                str = message.readUTF().trim();
                if (BadWord.checkBadWord(str)) {
                    message = new Message(Cmd.CHAT_MAP);
                    message.writeInt(playerId);
                    message.writeUTF(AlertFunction.CHAT_FILTER(getSession()));
                    getSession().sendMessage(message);
                    return;
                }
                playerMap = ServerController.hnPlayers.get(playerName);
            } catch (Exception e) {
            }
            if (playerMap == null) {
                message = new Message(Cmd.CHAT_PRIVATE);
                message.writeUTF(playerName);
                message.writeUTF(AlertFunction.NOT_ONLINE_2(getSession()));
                NJUtil.sendMessage(getSession(), message);
                return;
            }
            message = new Message(Cmd.CHAT_PRIVATE);
            message.writeUTF(name);
            message.writeUTF(str);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doChatServer(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            if (isLock) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            String str;
            try {
                str = message.readUTF().toLowerCase();
                if (BadWord.checkBadWord(str)) {
                    message = new Message(Cmd.CHAT_MAP);
                    message.writeInt(playerId);
                    message.writeUTF(AlertFunction.CHAT_FILTER(getSession()));
                    getSession().sendMessage(message);
                    return;
                }
            } catch (Exception e) {
                return;
            }
            byte subMoney = -1;
            try {
                subMoney = message.readByte();
            } catch (Exception e) {
            }
            int goldDown = 5;
            if (getLuong() >= goldDown || subMoney > -1) {
                long timeNow = System.currentTimeMillis();
                if (timeNow > timeChat) {
                    if (subMoney == -1) {
                        timeChat = timeNow + 5000L;
                        subLuong(goldDown);
                        updateGold();
                    } else {
                        str = "Chúc " + str;
                        Item it = findItemBag((subMoney == 0) ? 514 : 515);
                        if (it == null) {
                            return;
                        }
                        useItemUpToUp(it, 1);
                        sendUseItemUpToUp(it.indexUI, 1);
                        /*if (subMoney == 1) {
                            // qua chuc tet
                        }*/
                    }
                    savezbLog("Chat the gioi", getLuong() + "@" + goldDown + "@" + subMoney);
                    Matcher matcher = Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(Normalizer.normalize(str, Normalizer.Form.NFD));
                    LOGGER.info("{}. Chat server: {}", getStringBaseInfo(), matcher.replaceAll("").replace("đ", "d"));
                    message = new Message(Cmd.CHAT_SERVER);
                    message.writeUTF(name);
                    message.writeUTF(str);
                    HashMap<Integer, Player> huPlayers = new HashMap<>(ServerController.huPlayers);
                    for (int userId : huPlayers.keySet()) {
                        try {
                            Player player = ServerController.huPlayers.get(userId);
                            if (player == null || player.getSession() == null) {
                                continue;
                            }
                            player.getSession().sendMessage(message);
                        } catch (Exception e) {
                        }
                    }
                } else {
                    NJUtil.sendServer(getSession(), AlertFunction.HAVE_CHAT(getSession()));
                }
            } else {
                NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_GOLD_CHAT(getSession()));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doCheckKey1(Message message) {
        try {
            signature1 = message.readUTF();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doCheckKey2(Message message) {
        try {
            signature2 = message.readUTF();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doCheckKey3(Message message) {
        try {
            productPrice = message.readUTF();
            productDate = message.readUTF();
            transID = message.readUTF();
            productID = message.readUTF();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doCheckKey4() {
        try {
            NJUtil.sendDialog(getSession(), "Chức năng đang tạm đóng");
        } catch (Exception e) {
        }
    }

    public void doMenuOpen(Message message) {
        try {
            int inputTemplateId = message.readShort();
            PlayerTemplate pNpc = ServerController.playerTemplates.get(inputTemplateId);
            if (pNpc == null) {
                return;
            }
            try {
                if ((pNpc.playerTemplateId == 9 && map.template.mapTemplateId == 73) || (pNpc.playerTemplateId == 10 && map.template.mapTemplateId == 56) || (pNpc.playerTemplateId == 11 && map.template.mapTemplateId == 0)) {
                    if (pNpc.playerTemplateId == 10) {
                        sendOpenUISay(pNpc.playerTemplateId, AlertFunction.TASK_OK_1(getSession()));
                    } else if (pNpc.playerTemplateId == 11) {
                        sendOpenUISay(pNpc.playerTemplateId, AlertFunction.TASK_OK_2(getSession()));
                    } else {
                        sendOpenUISay(pNpc.playerTemplateId, AlertFunction.TASK_OK_3(getSession()));
                    }
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if ((pNpc.playerTemplateId == 9 || pNpc.playerTemplateId == 10 || pNpc.playerTemplateId == 11) && MixedArena.isMapArena(map.template.mapTemplateId)) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.SUMMARY(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if ((pNpc.playerTemplateId == 9 && map.template.mapTemplateId == 154) || (pNpc.playerTemplateId == 10 && map.template.mapTemplateId == 155) || (pNpc.playerTemplateId == 11 && map.template.mapTemplateId == 156)) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.SUMMARY(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if (map.template.isMapChienTruong() && pNpc.playerTemplateId == 25) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.SUMMARY(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if (map.getTemplateId() == 110 && pNpc.playerTemplateId == 0) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.INPUT_MONEY(getSession()));
                    message.writeUTF(AlertFunction.SAY(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if (map.getTemplateId() == 129 && pNpc.playerTemplateId == 0) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.THANH_TICH(getSession()));
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.HELP(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if (map.getTemplateId() == 117 && pNpc.playerTemplateId == 32) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.INPUT_MONEY(getSession()));
                    message.writeUTF(AlertFunction.HELP(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if ((map.getTemplateId() == 131 || map.getTemplateId() == 132) && pNpc.playerTemplateId == 32) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.RESULT(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if ((map.getTemplateId() == 98 || map.getTemplateId() == 104) && pNpc.playerTemplateId == 32) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.MOVE_BACK(getSession()));
                    message.writeUTF(AlertFunction.VIEW_RESULT(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                if (map.getTemplateId() == 22 && pNpc.playerTemplateId == 24) {
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF(AlertFunction.SAY(getSession()));
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
                message = new Message(Cmd.OPEN_UI_MENU);
                if (pNpc.playerTemplateId == 12 && taskMain == null && (getTaskFinish() == 0 || getTaskFinish() == 1 || getTaskFinish() == 2 || getTaskFinish() == 3 || getTaskFinish() == 4 || getTaskFinish() == 7 || getTaskFinish() == 8)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 12 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 0 || getTaskFinish() == 1 || getTaskFinish() == 2 || getTaskFinish() == 3 || getTaskFinish() == 4 || getTaskFinish() == 7 || getTaskFinish() == 8)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 3 && taskMain == null && getTaskFinish() == 5) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 3 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 5) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 7 && taskMain == null && getTaskFinish() == 6) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 7 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 6) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 4 && taskMain != null && taskMain.template.taskId == 1 && taskMain.index <= 4) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 4 && taskMain != null && taskMain.template.taskId == 7 && taskMain.index > 0 && taskMain.index <= 5) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 5 && taskMain != null && taskMain.template.taskId == 7 && taskMain.index > 5 && taskMain.index <= 10) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 6 && taskMain != null && taskMain.template.taskId == 7 && taskMain.index > 10 && taskMain.index <= 15) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 0 && taskMain == null && getTaskFinish() == 13) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 0 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 13) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 2 && taskMain == null && getTaskFinish() == 14) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 2 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 14) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 1 && taskMain == null && getTaskFinish() == 15) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 1 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 15) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 16 && taskMain == null && getTaskFinish() == 16) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 16 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() == 16) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 18 && taskMain == null && (getTaskFinish() == 17 || getTaskFinish() == 18 || getTaskFinish() == 19 || getTaskFinish() == 20)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 18 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 17 || getTaskFinish() == 18 || getTaskFinish() == 19 || getTaskFinish() == 20)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 23 && taskMain == null && (getTaskFinish() == 21 || getTaskFinish() == 22 || getTaskFinish() == 23 || getTaskFinish() == 24)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 23 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 21 || getTaskFinish() == 22 || getTaskFinish() == 23 || getTaskFinish() == 24)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 23 && taskMain != null && (taskMain.index == 0 || taskMain.index == 1) && getTaskFinish() == 23) {
                    message.writeUTF(AlertFunction.GET_KEY(getSession()));
                } else if (pNpc.playerTemplateId == 19 && taskMain == null && (getTaskFinish() == 25 || getTaskFinish() == 26 || getTaskFinish() == 27)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 19 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 25 || getTaskFinish() == 26 || getTaskFinish() == 27)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 22 && taskMain == null && getTaskFinish() >= 33 && getTaskFinish() <= 42) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 22 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && getTaskFinish() >= 33 && getTaskFinish() <= 42) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 17 && taskMain != null && taskMain.template.taskId == 17 && taskMain.index == 1) {
                    NpcPlayer npcPlayer = map.getNpcPlayer(pNpc.playerTemplateId);
                    if (npcPlayer == null) {
                        return;
                    }
                    sendOpenUISay(pNpc.playerTemplateId, AlertFunction.GO_BACK(getSession()));
                    if (npcPlayer.status != NpcPlayer.A_HIDE) {
                        message.writeUTF(AlertFunction.LET_GO(getSession()));
                    }
                } else if (pNpc.playerTemplateId == 20 && taskMain == null && (getTaskFinish() == 28 || getTaskFinish() == 29 || getTaskFinish() == 30 || getTaskFinish() == 31 || getTaskFinish() == 32)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 20 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 28 || getTaskFinish() == 29 || getTaskFinish() == 30 || getTaskFinish() == 31 || getTaskFinish() == 32)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 20 && taskMain != null && taskMain.index == 1 && getTaskFinish() == 32) {
                    message.writeUTF(AlertFunction.GET_ROD(getSession()));
                } else if ((pNpc.playerTemplateId == 14 && taskMain != null && taskMain.template.taskId == 15 && taskMain.index == 1) || (pNpc.playerTemplateId == 15 && taskMain != null && taskMain.template.taskId == 15 && taskMain.index == 2) || (pNpc.playerTemplateId == 16 && taskMain != null && taskMain.template.taskId == 15 && taskMain.index == 3)) {
                    String[] strs = { Alert_VN.SEND_MAIL, Alert_EN.SEND_MAIL };
                    message.writeUTF(strs[getSession().typeLanguage]);
                } else if (pNpc.playerTemplateId == 15 && taskMain != null && taskMain.template.taskId == 20 && taskMain.index == 1) {
                    sendOpenUISay(pNpc.playerTemplateId, AlertFunction.YOU_REMEMBER(getSession()));
                    message.writeUTF(AlertFunction.GO_IN_2(getSession()));
                } else if ((classId == 1 || classId == 2) && pNpc.playerTemplateId == 9 && taskMain == null && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if ((classId == 1 || classId == 2) && pNpc.playerTemplateId == 9 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 9 && taskMain != null && getTaskFinish() == 13 && taskMain.index == 3) {
                    sendOpenUISay(pNpc.playerTemplateId, AlertFunction.CHALLENGE(getSession()));
                    message.writeUTF(AlertFunction.GO_IN_1(getSession()));
                } else if ((classId == 3 || classId == 4) && pNpc.playerTemplateId == 10 && taskMain == null && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if ((classId == 3 || classId == 4) && pNpc.playerTemplateId == 10 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 10 && taskMain != null && getTaskFinish() == 13 && taskMain.index == 1) {
                    sendOpenUISay(pNpc.playerTemplateId, AlertFunction.CHALLENGE(getSession()));
                    message.writeUTF(AlertFunction.GO_IN_1(getSession()));
                } else if ((classId == 5 || classId == 6) && pNpc.playerTemplateId == 11 && taskMain == null && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(ServerController.taskTemplates.get(getTaskFinish()).name[getSession().typeLanguage]);
                } else if ((classId == 5 || classId == 6) && pNpc.playerTemplateId == 11 && taskMain != null && taskMain.index >= taskMain.template.subNames[getSession().typeLanguage].length - 1 && (getTaskFinish() == 9 || getTaskFinish() == 10 || getTaskFinish() == 11 || getTaskFinish() == 12)) {
                    message.writeUTF(AlertFunction.FINISH_TASK(getSession()));
                } else if (pNpc.playerTemplateId == 11 && taskMain != null && getTaskFinish() == 13 && taskMain.index == 2) {
                    sendOpenUISay(pNpc.playerTemplateId, AlertFunction.CHALLENGE(getSession()));
                    message.writeUTF(AlertFunction.GO_IN_1(getSession()));
                }
                NJUtil.sendMessage(getSession(), message);
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
        } catch (Exception e) {
        }
    }

    public void doChientruong(PlayerTemplate playerTemplate, int optionId) {
        if (capcha != null) {
            return;
        }
        LocalTime timeNow = LocalTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        if (optionId == 3) {
            NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.HELP(getSession()), AlertFunction.OPEN_DOOR_CT7(getSession()));
            return;
        }
        if (optionId == 2) {
            topChienTruong();
            return;
        }
        if (getSession().type_admin < ROLE_GM && hour != 13 && (hour != 14 || minute >= 30) && hour != 16 && (hour != 17 || minute >= 30) && hour != 19 && (hour != 20 || minute >= 30)) {
            sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.OPEN_DOOR_CT1(getSession()));
            return;
        }
        if (hour >= 13 && hour < 15 && level > 50) {
            sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.OPEN_DOOR_CT5(getSession()));
            return;
        }
        if (hour >= 16 && hour < 18 && (level <= 50 || level > 70)) {
            sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.OPEN_DOOR_CT5(getSession()));
            return;
        }
        if (optionId == 0) {
            if (level < 30) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LIMIT_LEVEL_CHIENTRUONG(getSession()));
            } else if (Map.idRights.contains(playerId)) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHE_FAIL1(getSession()));
            } else if (Map.playerLefts.size() > Map.playerRights.size() + 10) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LIMIT_LEFT(getSession()));
            } else {
                if (party != null) {
                    party.out(this);
                }
                Map.addLeft(this);
                Map mapNext = Map.findMap(this, 98);
                if (mapNext != null) {
                    typePk = PK_PHE1;
                    updateTypePk();
                    sendChientruong(pointCT);
                    mapTemplateIdGo = map.getTemplateId();
                    mapClear();
                    map.goOutMap(this);
                    x = mapNext.template.defaultX;
                    y = mapNext.template.defaultY;
                    mapNext.waitGoInMap(this);
                }
            }
        } else if (optionId == 1) {
            if (level < 30) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LIMIT_LEVEL_CHIENTRUONG(getSession()));
            } else if (Map.idLefts.contains(playerId)) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHE_FAIL2(getSession()));
            } else if (Map.playerRights.size() > Map.playerLefts.size() + 10) {
                sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LIMIT_RIGHT(getSession()));
            } else {
                Map.addRight(this);
                Map mapNext = Map.findMap(this, 104);
                if (mapNext != null) {
                    typePk = PK_PHE2;
                    updateTypePk();
                    sendChientruong(pointCT);
                    mapTemplateIdGo = map.getTemplateId();
                    mapClear();
                    map.goOutMap(this);
                    x = mapNext.template.defaultX;
                    y = mapNext.template.defaultY;
                    mapNext.waitGoInMap(this);
                }
            }
        }
    }

    public void doClanSendItem(Message message) {
        try {
            if (clan == null || (!clan.isMain(name) && !clan.isAssist(name))) {
                return;
            }
            int indexUI = message.readByte();
            Item item = clan.items.get(indexUI).cloneItem();
            if (item.getTemplateId() == 281) {
                return;
            }
            item.expires += System.currentTimeMillis();
            item.quantity = 1;
            String memName = message.readUTF();
            Member mem = clan.getMember(memName);
            if (mem == null) {
                NJUtil.sendDialog(getSession(), AlertFunction.CLAN_1(getSession()));
                return;
            }
            Player player = ServerController.hnPlayers.get(memName);
            if (player == null) {
                NJUtil.sendDialog(getSession(), AlertFunction.CLAN_2(getSession()));
                return;
            }
            if (item.getTemplateId() == 604) {
                doGiveThanThu(memName, indexUI);
                return;
            }
            if (!player.addItemToBagNoDialog(item)) {
                NJUtil.sendDialog(getSession(), AlertFunction.CLAN_3(getSession()));
                return;
            }
            clan.removeItem(indexUI);
            doRequestClanItem();
        } catch (Exception e) {
        }
    }

    public void doClanUseItem(Message message) {
        try {
            if (clan == null || (!clan.isMain(name) && !clan.isAssist(name))) {
                return;
            }
            int indexUI = message.readByte();
            Item item = clan.items.get(indexUI);
            if (item.getTemplateId() != 281) {
                return;
            }
            if (clan.use_card <= 0) {
                NJUtil.sendServer(getSession(), AlertFunction.MAX_CARD_CLAN(getSession()));
                return;
            }
            --clan.use_card;
            ++clan.openDun;
            clan.removeItem(indexUI);
            doRequestClanItem();
            NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.COUNT_OPENGT(getSession()), String.valueOf(clan.openDun)));
        } catch (Exception e) {
        }
    }

    public void doClearTask() {
        try {
            if (taskMain != null) {
                clearTask();
                Message message = NJUtil.messageNotMap(Cmd.CLEAR_TASK);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doCoinBoxIn(Message message) {
        try {
            int coinIn = message.readInt();
            if (coinIn <= 0 || coinIn > getXu()) {
                return;
            }
            long sum = (long) coinIn + (long) getXuBox();
            if (sum > maxCoin) {
                NJUtil.sendDialog(getSession(), AlertFunction.MAX_COIN(getSession()));
                return;
            }
            int xu = getXu();
            int xubox = getXuBox();
            subXu(coinIn);
            addXuBox(coinIn);
            savezbLog("Cat xu", coinIn + "@" + getXuBox() + "@" + xubox + "@" + getXu() + "@" + xu);
            message = NJUtil.messageSubCommand(Cmd.BOX_COIN_IN);
            message.writeInt(coinIn);
            NJUtil.sendMessage(getSession(), message);
            doOpenUIBox();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doCoinBoxOut(Message message) {
        try {
            int coinOut = message.readInt();
            if (coinOut <= 0 || coinOut > getXuBox()) {
                return;
            }
            long sum = (long) coinOut + (long) getXu();
            if (sum > maxCoin) {
                NJUtil.sendDialog(getSession(), AlertFunction.MAX_COIN(getSession()));
                return;
            }
            int xu = getXu();
            int xubox = getXuBox();
            subXuBox(coinOut);
            addXu(coinOut);
            savezbLog("Rut xu", coinOut + "@" + getXuBox() + "@" + xubox + "@" + getXu() + "@" + xu);
            message = NJUtil.messageSubCommand(Cmd.BOX_COIN_OUT);
            message.writeInt(coinOut);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doConfirmCapcha(int index) {
        if (capcha != null) {
            try {
                ++countConfirmCapcha;
                capcha.stConfirm.delete(0, 1);
                capcha.stConfirm.append(Capcha.st.charAt(index));
                if (capcha.capcha.getText().equals(capcha.stConfirm.toString())) {
                    capcha.stConfirm = new StringBuilder("999999");
                    npcDie(capcha, 1000000, false);
                    int expAdd = 10;
                    for (NpcTemplate npcTemplate : ServerController.npcTemplates) {
                        if (!npcTemplate.isBossId()) {
                            if (npcTemplate.level > level) {
                                break;
                            }
                            expAdd = npcTemplate.exp;
                        }
                    }
                    try {
                        if (!getPlayerMainControl().isNhanban()) {
                            sendUpdateExp(expAdd * getPlayerMainControl().level * 2L + NJUtil.random.nextInt(100), false);
                        }
                    } catch (Exception e) {
                    }
                    Database.saveLogAll("", name + " giet ma " + expAdd * level * 2, "gietma");
                    capcha = null;
                    isChangeMap = false;
                    updateInfo();
                    haveCapcha = false;
                    if (level < 20) {
                        maxKillMonster = 50;
                    } else {
                        maxKillMonster = (short) (NJUtil.random.nextInt(201) + 600 + level * 5);
                    }
                    countMonsKill = 0;
                    countConfirmCapcha = 0;
                    countFail = 0;
                } else if (countConfirmCapcha >= 6) {
                    countConfirmCapcha = 0;
                    ++countFail;
                    if (countFail >= 3) {
                        getSession().disconnect("Player.doConfirmCapcha");
                    }
                }
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
        }
    }

    public void doCreateParty() {
        try {
            if (isNotEditParty()) {
                return;
            }
            if (party == null) {
                clearTestDun();
                (party = new Party(this)).sendParty();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doDoiOption(Message message) {
        if (!GameServer.openTinhLuyen) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Item it1 = itemBags[message.readByte()];
            Item[] it2s = new Item[20];
            try {
                int[] a = new int[20];
                for (int i = 0; i < 20; ++i) {
                    a[i] = message.readByte();
                }
                for (int i = 0; i < 19; ++i) {
                    for (int j = i + 1; j < 20; ++j) {
                        if (a[i] == a[j]) {
                            ++countHackNgoc;
                            if (countHackNgoc >= 3) {
                                doCancelTrade1();
                                Database.saveLogAll(name, "hack dich chuyen trang bi", "hack");
                                getSession().disconnect("hack dich chuyen trang bi");
                            }
                            return;
                        }
                    }
                }
                for (int i = 0; i < it2s.length; ++i) {
                    it2s[i] = itemBags[a[i]];
                    if (it2s[i].template.itemTemplateId != Item.CHUYEN_TINH_THACH) {
                        doCancelTrade1();
                        return;
                    }
                }
            } catch (Exception e) {
                doCancelTrade1();
                return;
            }
            if (it1 == null || it1.upgrade < 12) {
                NJUtil.sendDialog(getSession(), "Không thể dịch chuyển trang bị này.");
                return;
            }
            try {
                for (int k = 0; k < it1.options.size(); ++k) {
                    if (it1.options.get(k).optionTemplate.itemOptionTemplateId == 85) {
                        NJUtil.sendDialog(getSession(), "Trang bị này đã được dịch chuyển.");
                        return;
                    }
                }
            } catch (Exception e) {
            }
            ItemOption option = new ItemOption();
            option.param = 0;
            option.optionTemplate = ServerController.iOptionTemplates.get(85);
            it1.options.add(option);
            if (it1.upgrade >= 12) {
                option = new ItemOption();
                switch (it1.template.type) {
                    case 0:
                    case 7:
                    case 5:
                        if (it1.sys == 2) {
                            option.param = opsTinhLuyen[19][0];
                            option.optionTemplate = ServerController.iOptionTemplates.get(95);
                            break;
                        }
                        if (it1.sys == 1) {
                            option.param = opsTinhLuyen[20][0];
                            option.optionTemplate = ServerController.iOptionTemplates.get(96);
                            break;
                        }
                        option.param = opsTinhLuyen[21][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(97);
                        break;
                    case 2:
                        option.param = opsTinhLuyen[5][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(80);
                        break;
                    case 6:
                    case 8:
                        option.param = opsTinhLuyen[8][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(83);
                        break;
                    case 4:
                        option.param = opsTinhLuyen[10][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(86);
                        break;
                    case 1:
                        option.param = opsTinhLuyen[11][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(87);
                        break;
                    case 3:
                        option.param = opsTinhLuyen[6][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(81);
                        break;
                    case 9:
                        option.param = opsTinhLuyen[9][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(84);
                        break;
                }
                ItemOption itemOption = option;
                itemOption.param += random25Percent(option.param);
                it1.options.add(option);
                option = new ItemOption();
                switch (it1.template.type) {
                    case 0:
                    case 3:
                        option.param = opsTinhLuyen[4][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(79);
                        break;
                    case 2:
                        option.param = opsTinhLuyen[15][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(91);
                        break;
                    case 6:
                    case 9:
                        option.param = opsTinhLuyen[7][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(82);
                        break;
                    case 4:
                        option.param = opsTinhLuyen[18][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(94);
                        break;
                    case 8:
                        option.param = opsTinhLuyen[9][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(84);
                        break;
                    case 1:
                    case 7:
                        if (it1.sys == 1) {
                            option.param = opsTinhLuyen[12][0];
                            option.optionTemplate = ServerController.iOptionTemplates.get(88);
                            break;
                        }
                        if (it1.sys == 2) {
                            option.param = opsTinhLuyen[13][0];
                            option.optionTemplate = ServerController.iOptionTemplates.get(89);
                            break;
                        }
                        option.param = opsTinhLuyen[14][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(90);
                        break;
                    case 5:
                        option.param = opsTinhLuyen[16][0];
                        option.optionTemplate = ServerController.iOptionTemplates.get(92);
                        break;
                }
                ItemOption itemOption2 = option;
                itemOption2.param += random25Percent(option.param);
                it1.options.add(option);
            }
            changeItemOption(it1, 0);
            doCancelTrade1();
            for (Item it2 : it2s) {
                removeItem(it2, 3);
                sendClearItemBag(it2.indexUI);
            }
            message = new Message(Cmd.DOI_OPTION);
            message.writeByte(it1.indexUI);
            message.writeByte(it1.upgrade);
            NJUtil.sendMessage(getSession(), message);
            NJUtil.sendServer(getSession(), "Trang bị đã được dịch chuyển.");
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doEat(Item item) {
        try {
            if (clan == null) {
                return;
            }
            if (item.template.itemTemplateId == 605 && !clan.isMain(name)) {
                NJUtil.sendServer(conn, "Tộc trưởng mới có thể tiến hoá thần thú");
                return;
            }
            int size = clan.allThanThu.size();
            if (size == 0) {
                NJUtil.sendServer(conn, "Gia tộc chưa sở hữu thần thú");
                return;
            }
            if (size == 1) {
                int a = clan.doEat(item, getSession());
                if (a == 1 || a == 2) {
                    useItemUpToUp(item);
                    sendUseItemUpToUp(item.indexUI, 1);
                    doRequestClanItem();
                    Database.saveClan(clan);
                    Database.saveLogAll(name, name + " cho " + clan.allThanThu.get(0).getName(conn) + " ăn " + item.template.getName(getSession()), "choan");
                }
            } else {
                itFood = item;
                idActionNewMenu = 11;
                Message message = new Message(Cmd.OPEN_UI_NEWMENU);
                for (int i = 0; i < size; ++i) {
                    message.writeUTF(clan.allThanThu.get(i).getName(getSession()));
                }
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doFindParty() {
        try {
            Vector<Party> vParty = map.getPts();
            Message message = NJUtil.messageSubCommand(Cmd.FIND_PARTY);
            for (Party value : vParty) {
                Player p = value.players.firstElement();
                message.writeByte(p.classId);
                message.writeByte(p.level);
                message.writeUTF(p.name);
                message.writeByte(p.party.players.size());
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doFinishTaskNguyetNhan() {
        String info = "";
        if (nguyetNhanTask != null) {
            if (countFreeBag() < 1) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Con phải có 1 ô trống trong hành trang");
                } else {
                    NJUtil.sendServer(getSession(), "You must have 1 free slot in your bag");
                }
                return;
            }
            NguyetNhanTask temp = NguyetNhanTask.alltask.get(nguyetNhanTask.id);
            ItemTemplate template = ServerController.itemTemplates.get(nguyetNhanTask.idItem);
            int sl = 2;
            int pc_da = 30;
            if (nguyetNhanTask.finish_step == temp.step_max) {
                sl = 4;
                pc_da = 1;
            }
            String namePoint = "";
            switch (template.type) {
                case 0:
                    pointNon += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " hat";
                        break;
                    }
                    namePoint = " nón";
                    break;
                case 1:
                    pointVukhi += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " weapon";
                        break;
                    }
                    namePoint = " vũ khí";
                    break;
                case 2:
                    pointAo += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " armor";
                        break;
                    }
                    namePoint = " áo";
                    break;
                case 3:
                    pointLien += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " necklace";
                        break;
                    }
                    namePoint = " liên";
                    break;
                case 4:
                    pointGangtay += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " gloves";
                        break;
                    }
                    namePoint = " găng";
                    break;
                case 5:
                    pointNhan += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " ring";
                        break;
                    }
                    namePoint = " nhẫn";
                    break;
                case 6:
                    pointQuan += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " pants";
                        break;
                    }
                    namePoint = " quần";
                    break;
                case 7:
                    pointNgocboi += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " gems";
                        break;
                    }
                    namePoint = " ngọc bội";
                    break;
                case 8:
                    pointGiay += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " shoes";
                        break;
                    }
                    namePoint = " giày";
                    break;
                case 9:
                    pointPhu += sl;
                    if (GameServer.isSvEnglish()) {
                        namePoint = " charm";
                        break;
                    }
                    namePoint = " phù";
                    break;
            }
            if (GameServer.isSvEnglish()) {
                info = "- " + sl + " honor points " + namePoint;
            } else {
                info = "- " + sl + " điểm danh vọng" + namePoint;
            }
            Item it = null;
            if (NJUtil.random.nextInt(100) < pc_da) {
                try {
                    it = new Item(Item.DANH_VONG_CAP_1, true, "doDieuGiay");
                    it.quantity = 1;
                    addItemToBagNoDialog(it);
                    info = info + "\n- " + it.template.getName(getSession());
                } catch (Exception e) {
                    Database.saveLogAll(name, "loi tra nv nguyet nhan", "nguyetnhanfail");
                }
            }
            savezaLog("nguyetnhan", "point " + sl + " " + namePoint + "," + ((it != null) ? it.template.name : ""));
        }
        NJUtil.sendAlertDialogInfo(getSession(), GameServer.isSvEnglish() ? "Receive" : "Nhận được", info);
        Database.savePlayer(this, map.getTemplateId());
        Database.saveLogAll(name, info, "nguyetnhan");
        nguyetNhanTask = null;
    }

    public void doFriendInvite(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            int maxFriend = 0;
            for (Friend friend : friends) {
                if (friend.type == 0 || friend.type == 1) {
                    ++maxFriend;
                }
            }
            if (maxFriend >= 70) {
                NJUtil.sendServer(getSession(), AlertFunction.MAX_FRIEND(getSession()));
                return;
            }
            Player playerMap;
            try {
                String fName = message.readUTF().trim().toLowerCase();
                if (fName.equals(name)) {
                    NJUtil.sendServer(getSession(), AlertFunction.NOT_ADD_ME(getSession()));
                    return;
                }
                playerMap = ServerController.hnPlayers.get(fName);
            } catch (Exception e) {
                return;
            }
            if (playerMap == null) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                return;
            }
            Friend fMe = findFriend(playerMap.name);
            if (fMe != null) {
                NJUtil.sendServer(getSession(), playerMap.name + AlertFunction.FRIEND_ADDED(getSession()));
                return;
            }
            Friend fFriend = playerMap.findFriend(name);
            if (fFriend == null) {
                friends.add(new Friend(playerMap.name, (byte) 0));
                sendAddFriend(playerMap.name, 0);
                message = new Message(Cmd.FRIEND_INVITE);
                message.writeUTF(name);
                NJUtil.sendMessage(playerMap.getSession(), message);
            } else if (fFriend.type == 0) {
                friends.add(new Friend(playerMap.name, (byte) 1));
                fFriend.type = 1;
                sendAddFriend(playerMap.name, 1);
                playerMap.sendAddFriend(name, 1);
            } else {
                if (fFriend.type == 1) {
                    friends.add(new Friend(playerMap.name, (byte) 1));
                    sendAddFriend(playerMap.name, 1);
                    NJUtil.sendServer(getSession(), AlertFunction.YOU_AND(getSession()) + " " + playerMap.name + " " + AlertFunction.FRIENDED(getSession()));
                    return;
                }
                if (fFriend.type == 2) {
                    friends.add(new Friend(playerMap.name, (byte) 0));
                    sendAddFriend(playerMap.name, 0);
                    NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.CAREFUL(getSession()), playerMap.name));
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doGetTask(Message message) {
        Player playerMain = getPlayerMainControl();
        try {
            int playerTemplateId;
            int menuId;
            try {
                playerTemplateId = message.readByte();
                menuId = message.readByte();
            } catch (Exception e) {
                return;
            }
            int optionId = -1;
            try {
                optionId = message.readByte();
            } catch (Exception e) {
            }
            PlayerTemplate playerTemplate = ServerController.playerTemplates.get(playerTemplateId);
            if (playerMain.taskMain == null && menuId == 0) {
                if (getTaskFinish() == 15 || getTaskFinish() == 19 || getTaskFinish() == 23 || getTaskFinish() == 24 || getTaskFinish() == 27 || getTaskFinish() == 32 || getTaskFinish() == 35) {
                    int countFree = countFreeBag();
                    if (countFree < 1) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    if (getTaskFinish() == 15) {
                        Item item = new Item(214, true, "doGetTask char 1");
                        item.quantity = 3;
                        addItemToBagNoDialog(item);
                    } else if (getTaskFinish() == 19) {
                        Item item = new Item(219, true, "doGetTask char 2");
                        item.quantity = 50;
                        addItemToBagNoDialog(item);
                    } else if (getTaskFinish() == 35) {
                        Item item = new Item(219, true, "doGetTask char 3");
                        item.quantity = 150;
                        addItemToBagNoDialog(item);
                    } else if (getTaskFinish() == 23) {
                        addItemToBagNoDialog(new Item(231, true, "doGetTask char 4"));
                    } else if (getTaskFinish() == 24) {
                        int[] ids = { 233, 234, 235 };
                        addItemToBagNoDialog(new Item(ids[NJUtil.randomNumber(ids.length)], true, "doGetTask char 5"));
                    } else if (getTaskFinish() == 26) {
                        int[] ids = { 233, 234, 235 };
                        addItemToBagNoDialog(new Item(ids[NJUtil.randomNumber(ids.length)], true, "doGetTask char 6"));
                    } else if (getTaskFinish() == 27) {
                        Item item = new Item(237, true, "doGetTask char 5");
                        item.quantity = 100;
                        addItemToBagNoDialog(item);
                    } else if (getTaskFinish() == 32) {
                        Item item = new Item(266, true, "doGetTask char 6");
                        addItemToBagNoDialog(item);
                    }
                }
                playerMain.taskMain = new Task(getTaskFinish());
                sendTask();
                sendOpenUISay(playerTemplate.playerTemplateId, playerMain.taskMain.template.sayGet);
                if (playerMain.taskMain.template.taskId == 2 && playerMain.taskMain.index == 0 && playerMain.itemBodys[1] != null) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 0) {
                    playerMain.taskMain.count = (short) countItemBag(23);
                    if (playerMain.taskMain.count == 1) {
                        doTaskUpdate(playerMain.taskMain.count);
                    } else if (playerMain.taskMain.count >= 2) {
                        doTaskNext();
                    }
                } else if (playerMain.taskMain.template.taskId == 4 && playerMain.taskMain.index == 0 && playerMain.level >= 5) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 5 && playerMain.taskMain.index == 0 && playerMain.level >= 6) {
                    doTaskNext();
                } else if (taskMain.template.taskId == 6 && playerMain.taskMain.index == 0 && playerMain.level >= 7) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 7 && taskMain.index == 0) {
                    if (playerMain.level >= 8) {
                        doTaskNext();
                    }
                } else if (taskMain.template.taskId == 8 && playerMain.taskMain.index == 0 && playerMain.level >= 9) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 9 && playerMain.taskMain.index == 0 && playerMain.skills.size() > 0) {
                    doTaskNext();
                    int i = 0;
                    while (i < playerMain.skills.size()) {
                        if (playerMain.skills.get(i).point > 0) {
                            doTaskNext();
                            if (isSysOut()) {
                                if (playerMain.p_strength > 15 || playerMain.p_agile > 5 || playerMain.p_mp > 5 || playerMain.p_hp > 5) {
                                    doTaskNext();
                                    break;
                                }
                            } else {
                                if (playerMain.p_strength > 5 || playerMain.p_agile > 5 || playerMain.p_mp > 10 || playerMain.p_hp > 10) {
                                    doTaskNext();
                                    break;
                                }
                            }
                            break;
                        } else {
                            ++i;
                        }
                    }
                } else if (playerMain.taskMain.template.taskId == 11 && playerMain.taskMain.index == 0 && playerMain.level >= 11) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 12 && playerMain.taskMain.index == 0 && playerMain.level >= 12) {
                    doTaskNext();
                    if (playerMain.itemBodys[1] != null && playerMain.itemBodys[1].upgrade >= 1) {
                        doTaskNext();
                        for (int i = 0; i < playerMain.itemBodys.length; ++i) {
                            if (playerMain.itemBodys[i] != null && playerMain.itemBodys[i].upgrade >= 1 && playerMain.itemBodys[i].isTypeAdorn()) {
                                doTaskNext();
                                for (int j = 0; j < itemBodys.length; ++j) {
                                    if (playerMain.itemBodys[j] != null && playerMain.itemBodys[j].upgrade >= 1 && playerMain.itemBodys[j].isTypeClothe()) {
                                        doTaskNext();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else if (playerMain.taskMain.template.taskId == 13 && playerMain.taskMain.index == 0 && playerMain.level >= 14) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 14 && playerMain.taskMain.index == 0 && playerMain.level >= 16) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 15 && playerMain.taskMain.index == 0 && playerMain.level >= 19) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 16 && playerMain.taskMain.index == 0 && playerMain.level >= 22) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 17 && playerMain.taskMain.index == 0 && playerMain.level >= 25) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 18 && playerMain.taskMain.index == 0 && playerMain.level >= 26) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 19 && playerMain.taskMain.index == 0 && playerMain.level >= 27) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 20 && playerMain.taskMain.index == 0 && playerMain.level >= 29) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 21 && playerMain.taskMain.index == 0 && playerMain.level >= 30) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 22 && playerMain.taskMain.index == 0 && playerMain.level >= 32) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 23 && playerMain.taskMain.index == 0 && playerMain.level >= 34) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 24 && playerMain.taskMain.index == 0 && playerMain.level >= 35) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 25 && playerMain.taskMain.index == 0 && playerMain.level >= 37) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 26 && playerMain.taskMain.index == 0 && playerMain.level >= 38) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 27 && playerMain.taskMain.index == 0 && playerMain.level >= 39) {
                    doTaskNext();
                    playerMain.taskMain.count = (short) countItemBag(239);
                    if (playerMain.taskMain.count >= playerMain.taskMain.template.counts[playerMain.taskMain.index]) {
                        doTaskNext();
                    } else {
                        doTaskUpdate(playerMain.taskMain.count);
                    }
                } else if (playerMain.taskMain.template.taskId == 28 && playerMain.taskMain.index == 0 && playerMain.level >= 41) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 29 && playerMain.taskMain.index == 0 && playerMain.level >= 43) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 30 && playerMain.taskMain.index == 0 && playerMain.level >= 45) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 31 && playerMain.taskMain.index == 0 && playerMain.level >= 47) {
                    doTaskNext();
                } else if (taskMain.template.taskId == 32 && playerMain.taskMain.index == 0 && playerMain.level >= 49) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 33 && playerMain.taskMain.index == 0 && playerMain.level >= 51) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 34 && playerMain.taskMain.index == 0 && playerMain.level >= 53) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 35 && playerMain.taskMain.index == 0 && playerMain.level >= 55) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 36 && playerMain.taskMain.index == 0 && playerMain.level >= 57) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 37 && playerMain.taskMain.index == 0 && playerMain.level >= 59) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 38 && playerMain.taskMain.index == 0 && playerMain.level >= 61) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 39 && playerMain.taskMain.index == 0 && playerMain.level >= 63) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 40 && playerMain.taskMain.index == 0 && playerMain.level >= 65) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 41 && playerMain.taskMain.index == 0 && playerMain.level >= 67) {
                    doTaskNext();
                } else if (playerMain.taskMain.template.taskId == 42 && playerMain.taskMain.index == 0 && playerMain.level >= 69) {
                    doTaskNext();
                }
            } else if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 1 && playerMain.taskMain.index >= 0 && playerMain.taskMain.index <= 4) {
                if (optionId == -1) {
                    if (playerMain.taskMain.template.results[playerMain.taskMain.index] == menuId) {
                        doTaskNext();
                        if (playerMain.taskMain.index < playerMain.taskMain.template.questions[getSession().typeLanguage].length) {
                            confirmTask(playerTemplate.playerTemplateId, playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                        } else if (playerMain.taskMain.index == playerMain.taskMain.template.questions[getSession().typeLanguage].length) {
                            sendOpenUISay(playerTemplate.playerTemplateId, playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1]);
                        }
                    } else {
                        confirmTask(playerTemplate.playerTemplateId, AlertFunction.ASK_FAIL(getSession()) + playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                    }
                }
            } else if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 7 && ((playerMain.taskMain.index > 0 && playerMain.taskMain.index < 5) || (playerMain.taskMain.index > 5 && playerMain.taskMain.index < 10) || (playerMain.taskMain.index > 10 && playerMain.taskMain.index < 15))) {
                if (optionId == -1) {
                    if (playerMain.taskMain.template.results[playerMain.taskMain.index] == menuId) {
                        doTaskNext();
                        if (playerMain.taskMain.index < playerMain.taskMain.template.questions[getSession().typeLanguage].length) {
                            confirmTask(playerTemplate.playerTemplateId, playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                        }
                    } else {
                        confirmTask(playerTemplate.playerTemplateId, AlertFunction.ASK_FAIL(getSession()) + playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                    }
                }
            } else if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 7 && playerMain.taskMain.index == 5) {
                if (optionId == -1) {
                    if (playerMain.taskMain.template.results[playerMain.taskMain.index] == menuId) {
                        doTaskNext();
                        sendOpenUISay(playerTemplate.playerTemplateId, playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.template.des[getSession().typeLanguage].length - 4]);
                    } else {
                        confirmTask(playerTemplate.playerTemplateId, AlertFunction.ASK_FAIL(getSession()) + playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                    }
                }
            } else if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 7 && playerMain.taskMain.index == 10) {
                if (optionId == -1) {
                    if (playerMain.taskMain.template.results[playerMain.taskMain.index] == menuId) {
                        doTaskNext();
                        sendOpenUISay(playerTemplate.playerTemplateId, taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.template.des[getSession().typeLanguage].length - 3]);
                    } else {
                        confirmTask(playerTemplate.playerTemplateId, AlertFunction.ASK_FAIL(getSession()) + playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                    }
                }
            } else if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 7 && playerMain.taskMain.index == 15 && optionId == -1) {
                if (playerMain.taskMain.template.results[playerMain.taskMain.index] == menuId) {
                    doTaskNext();
                    sendOpenUISay(playerTemplate.playerTemplateId, playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.template.des[getSession().typeLanguage].length - 2]);
                } else {
                    confirmTask(playerTemplate.playerTemplateId, AlertFunction.ASK_FAIL(getSession()) + playerMain.taskMain.template.des[getSession().typeLanguage][playerMain.taskMain.index + 1], playerMain.taskMain.template.questions[getSession().typeLanguage][playerMain.taskMain.index]);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doGiveThanThu(String memName, int indexUI) {
        try {
            Player p = ServerController.hnPlayers.get(memName);
            int alltt = clan.allThanThu.size();
            if (alltt == 0) {
                return;
            }
            if (alltt != 1) {
                this.memName = memName;
                indexUITT = (byte) indexUI;
                idActionNewMenu = 10;
                Message message = new Message(Cmd.OPEN_UI_NEWMENU);
                for (int i = 0; i < alltt; ++i) {
                    message.writeUTF(clan.allThanThu.get(i).getName(conn));
                }
                NJUtil.sendMessage(getSession(), message);
                return;
            }
            Item item = clan.allThanThu.get(0).getItem();
            if (item == null) {
                return;
            }
            if (!p.addItemToBagNoDialog(item)) {
                NJUtil.sendDialog(getSession(), AlertFunction.CLAN_3(getSession()));
                return;
            }
            clan.removeItem(indexUI);
            doRequestClanItem();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doGoGTChien() {
        try {
            isGTChien();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doGoViewTestDun(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        if (capcha != null) {
            return;
        }
        try {
            int zoneId = message.readByte();
            for (int i = 0; i < DunVD.maps.size(); ++i) {
                DunVD dunVD = DunVD.maps.get(i);
                if (dunVD.zoneId == zoneId) {
                    typePk = PK_NORMAL;
                    updateTypePk();
                    mapClear();
                    map.goOutMap(this);
                    x = dunVD.template.defaultX;
                    y = dunVD.template.defaultY;
                    dunVD.waitGoInMap(this);
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doItemBagToBox(Message message) {
        try {
            if (!isOk(5)) {
                return;
            }
            int bagIndex;
            Item item;
            try {
                bagIndex = message.readByte();
                item = itemBags[bagIndex];
            } catch (Exception e) {
                return;
            }
            int boxIndex = -1;
            if (item != null) {
                if (item.template.type == 25 || item.template.type == 23 || item.template.type == 24) {
                    NJUtil.sendDialog(getSession(), AlertFunction.MOVE_ERROR(getSession()));
                    return;
                }
                if (item.template.isUpToUp && item.expires == -1L) {
                    for (int i = 0; i < itemBoxs.length; ++i) {
                        if (itemBoxs[i] != null && itemBoxs[i].template.equals(item.template) && itemBoxs[i].isLock == item.isLock) {
                            removeItem(itemBags[bagIndex], 3);
                            itemBoxs[i].quantity += item.quantity;
                            ghiloghack(itemBoxs[i].getTemplateId());
                            //Database.updateItem(itemBoxs[i]);
                            itemBagToBox(bagIndex, i);
                            return;
                        }
                        if (boxIndex == -1 && itemBoxs[i] == null) {
                            boxIndex = i;
                        }
                    }
                } else {
                    boxIndex = findIndexEmpty(4);
                }
                if (boxIndex == -1) {
                    NJUtil.sendDialog(getSession(), AlertFunction.BOX_FULL(getSession()));
                    return;
                }
                removeItem(itemBags[bagIndex], 3);
                item.typeUI = 4;
                item.indexUI = boxIndex;
                addItem(item, item.typeUI, item.indexUI);
                itemBagToBox(bagIndex, boxIndex);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemBodyToBag(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            Item item = null;
            try {
                item = playerMain.itemBodys[message.readByte()];
            } catch (Exception e) {
            }
            if (item == null) {
                return;
            }
            int indexUI = findIndexEmpty(3);
            if (indexUI != -1) {
                itemBodyToBag(item, indexUI);
                addItem(item, 3, item.indexUI = indexUI);
                doPlayerInfo(this);
            } else {
                NJUtil.sendServer(getSession(), AlertFunction.BAG_FULL(getSession()));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemBoxToBag(Message message) {
        try {
            if (!isOk(5)) {
                return;
            }
            if (getPlayerMainControl().isNhanban() && typeSelectBox != 0) {
                return;
            }
            int boxIndex;
            Item item;
            try {
                boxIndex = message.readByte();
                item = itemBoxs[boxIndex];
            } catch (Exception e) {
                return;
            }
            int bagIndex = -1;
            if (item != null) {
                if (item.template.isUpToUp && item.expires == -1L) {
                    for (int i = 0; i < itemBags.length; ++i) {
                        if (itemBags[i] != null && itemBags[i].template.equals(item.template) && itemBags[i].isLock == item.isLock) {
                            removeItem(itemBoxs[boxIndex], 4);
                            itemBags[i].quantity += item.quantity;
                            ghiloghack(itemBags[i].getTemplateId());
                            //Database.updateItem(itemBags[i]);
                            itemBoxToBag(boxIndex, i);
                            return;
                        }
                        if (bagIndex == -1 && itemBags[i] == null) {
                            bagIndex = i;
                        }
                    }
                    if (bagIndex == -1) {
                        NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                        return;
                    }
                } else {
                    bagIndex = findIndexEmpty(3);
                    if (bagIndex == -1) {
                        NJUtil.sendDialog(getSession(), AlertFunction.BOX_FULL(getSession()));
                        return;
                    }
                }
                removeItem(itemBoxs[boxIndex], 4);
                item.typeUI = 3;
                item.indexUI = bagIndex;
                addItem(item, item.typeUI, item.indexUI);
                itemBoxToBag(boxIndex, bagIndex);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemBuy(Message message) {
        try {
            if (isLock) {
                sendNotUnlock();
                return;
            }
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            int typeUI;
            int indexUI;
            try {
                typeUI = message.readByte();
                indexUI = message.readByte();
            } catch (Exception e) {
                return;
            }
            int quantity;
            try {
                quantity = message.readShort();
            } catch (Exception e3) {
                quantity = 1;
            }
            int npcPlayerId = -1;
            Item item = null;
            switch (typeUI) {
                case 20:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopNonNams.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopNonNams.get(indexUI);
                    break;
                case 21:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopNonNus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopNonNus.get(indexUI);
                    break;
                case 22:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopAoNams.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopAoNams.get(indexUI);
                    break;
                case 23:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopAoNus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopAoNus.get(indexUI);
                    break;
                case 24:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopGangTayNams.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGangTayNams.get(indexUI);
                    break;
                case 25:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopGangTayNus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGangTayNus.get(indexUI);
                    break;
                case 26:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopQuanNams.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopQuanNams.get(indexUI);
                    break;
                case 27:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopQuanNus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopQuanNus.get(indexUI);
                    break;
                case 28:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopGiayNams.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGiayNams.get(indexUI);
                    break;
                case 29:
                    npcPlayerId = 1;
                    if (indexUI < 0 || indexUI > ServerController.shopGiayNus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGiayNus.get(indexUI);
                    break;
                case 16:
                    npcPlayerId = 2;
                    if (indexUI < 0 || indexUI > ServerController.shopLiens.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopLiens.get(indexUI);
                    break;
                case 17:
                    npcPlayerId = 2;
                    if (indexUI < 0 || indexUI > ServerController.shopNhans.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopNhans.get(indexUI);
                    break;
                case 18:
                    npcPlayerId = 2;
                    if (indexUI < 0 || indexUI > ServerController.shopNgocBois.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopNgocBois.get(indexUI);
                    break;
                case 19:
                    npcPlayerId = 2;
                    if (indexUI < 0 || indexUI > ServerController.shopPhus.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopPhus.get(indexUI);
                    break;
                case 2:
                    npcPlayerId = 0;
                    if (indexUI < 0 || indexUI > ServerController.shopVuKhis.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopVuKhis.get(indexUI);
                    break;
                case 6:
                    npcPlayerId = 3;
                    if (indexUI < 0 || indexUI > ServerController.shopStacks.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopStacks.get(indexUI);
                    break;
                case 7:
                    npcPlayerId = 3;
                    if (indexUI < 0 || indexUI > ServerController.shopStackLocks.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopStackLocks.get(indexUI);
                    break;
                case 8:
                    npcPlayerId = 4;
                    if (indexUI < 0 || indexUI > ServerController.shopGrocerys.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGrocerys.get(indexUI);
                    break;
                case 9:
                    npcPlayerId = 4;
                    if (indexUI < 0 || indexUI > ServerController.shopGroceryLocks.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGroceryLocks.get(indexUI);
                    break;
                case 14:
                    npcPlayerId = 26;
                    if (indexUI < 0 || indexUI > ServerController.shopStores.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopStores.get(indexUI);
                    break;
                case 15:
                    npcPlayerId = 26;
                    if (indexUI < 0 || indexUI > ServerController.shopBooks.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopBooks.get(indexUI);
                    break;
                case 32:
                    npcPlayerId = 26;
                    if (indexUI < 0 || indexUI > ServerController.shopFashions.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopFashions.get(indexUI);
                    break;
                case 34:
                    npcPlayerId = 26;
                    if (indexUI < 0 || indexUI > ServerController.shopGiatocs.size() - 1) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không tồn tại.");
                        return;
                    }
                    item = ServerController.shopGiatocs.get(indexUI);
                    break;
            }
            if (!isOk(npcPlayerId)) {
                return;
            }
            if (getTaskFinish() <= 2 || (getTaskFinish() <= 3 && item != null && item.template.itemTemplateId != 23)) {
                NJUtil.sendDialog(getSession(), AlertFunction.SALE_ERROR2(getSession()));
                return;
            }
            if (item != null && quantity > 0L) {
                if (!GameServer.openThanThuGiaToc && item.isTrungThanThu()) {
                    NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                    return;
                }
                if (typeUI == 34) {
                    if (clan == null || (!clan.isMain(name) && !clan.isAssist(name))) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_TT(getSession()));
                        return;
                    }
                    if (clan.coin < item.buyCoin * quantity) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_COINGT(getSession()));
                        return;
                    }
                    if ((clan.level < 5 && item.template.itemTemplateId == 423) ||
                        (clan.level < 10 && item.template.itemTemplateId == 424) ||
                        (clan.level < 15 && item.template.itemTemplateId == 425) ||
                        (clan.level < 20 && item.template.itemTemplateId == 426) ||
                        (clan.level < 25 && item.template.itemTemplateId == 427)
                    ) {
                        NJUtil.sendDialog(getSession(), AlertFunction.CLAN_NOT_LV(getSession()));
                        return;
                    }
                    if ((clan.itemlevel < 1 && item.template.itemTemplateId == 423) ||
                        (clan.itemlevel < 2 && item.template.itemTemplateId == 424) ||
                        (clan.itemlevel < 3 && item.template.itemTemplateId == 425) ||
                        (clan.itemlevel < 4 && item.template.itemTemplateId == 426) ||
                        (clan.itemlevel < 5 && item.template.itemTemplateId == 427)
                    ) {
                        NJUtil.sendDialog(getSession(), AlertFunction.CLAN_NOT_LV1(getSession()));
                        return;
                    }
                } else {
                    if (item.buyCoin > 0 && getXu() < item.buyCoin * quantity) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_COIN(getSession()));
                        return;
                    }
                    if (item.buyCoinLock > 0 && getYen() < item.buyCoinLock * quantity) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_COIN_LOCK(getSession()));
                        return;
                    }
                    if (item.buyGold > 0 && getLuong() < item.buyGold * quantity) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_GOLD(getSession()));
                        return;
                    }
                    if (item.buyGold > 0) {
                        String date = NJUtil.getDateTime();
                        int templateId = item.getTemplateId();
                        int qua = quantity;
                        ThreadPool.getInstance().workList.add(() -> Database.insertCuahang(date, templateId, qua));
                    }
                }
                Item itemBuy = item.cloneRandom();
                boolean isTuanLocKhangDs = false;
                if (itemBuy.isPetTuanLoc()) {
                    int pc = 1;
                    if (item.buyGold > 0) {
                        pc = 10;
                    }
                    if (NJUtil.random.nextInt(1000) < pc) {
                        itemBuy.doChangeTemplate(744);
                        isTuanLocKhangDs = true;
                    }
                }
                if (itemBuy.isPetReBiHanh()) {
                    int pc = 1;
                    if (item.buyGold > 0) {
                        pc = 10;
                    }
                    if (NJUtil.random.nextInt(1000) < pc) {
                        itemBuy.doChangeTemplate(773);
                        isTuanLocKhangDs = true;
                    }
                }
                itemBuy.vitri = "dobuyitem";
                itemBuy.quantity = quantity;
                if (itemBuy.expires != -1L && typeUI != 34) {
                    itemBuy.expires += System.currentTimeMillis();
                }
                if (itemBuy.template.itemTemplateId == 340) {
                    itemBuy.isLock = true;
                    itemBuy.expires = -1L;
                }
                if (itemBuy.template.itemTemplateId == 705) {
                    itemBuy.isLock = true;
                }
                if (itemBuy.template.itemTemplateId == 393) {
                    itemBuy.isLock = true;
                }
                if (itemBuy.template.itemTemplateId == 594) {
                    (itemBuy.options = new Vector<>()).add(new ItemOption(3000, 6));
                }
                if ((itemBuy.isPetTuanLoc() || itemBuy.ispetTuanLocKhangDs()) && isTuanLocKhangDs) {
                    itemBuy.isLock = false;
                }
                if ((itemBuy.isPetReBiHanh() || itemBuy.ispetReBiHanhKhangDs()) && isTuanLocKhangDs) {
                    itemBuy.isLock = false;
                }
                if (typeUI == 34) {
                    itemBuy.isLock = true;
                    if (itemBuy.isTrungThanThu()) {
                        if (!clan.isMain(name)) {
                            NJUtil.sendDialog(getSession(), "Tộc trưởng mới được mua trứng thần thú");
                        }
                        if (clan.level < 26) {
                            NJUtil.sendDialog(getSession(), "Gia tộc phải đạt cấp độ 26 trở lên mới được mua trứng thần thú");
                            return;
                        }
                        if (clan.level < 30 && clan.allThanThu.size() == 1) {
                            NJUtil.sendDialog(getSession(), "Gia tộc phải đạt cấp độ 30 trở lên mới được mua 2 trứng thần thú");
                            return;
                        }
                    }
                    if (clan.addItem(itemBuy)) {
                        clan.coin -= item.buyCoin * quantity;
                        clan.sendAlert(new String[]{ "Gia tộc " + clan.name + " đã nhận được " + itemBuy.template.name, "Gia tộc " + clan.name + " đã nhận được " + itemBuy.template.name }, null);
                        endWait(null);
                        doRequestClanItem();
                        savezbLog("Mua item clan", item.buyGold + "@" + item.buyCoin + "@" + item.buyCoinLock + "@" + itemBuy.template.itemTemplateId + "@" + itemBuy.quantity);
                    } else {
                        if (itemBuy.isTrungThanThu()) {
                            NJUtil.sendDialog(getSession(), "Gia tộc đã sở hữu thần thú này rồi");
                            return;
                        }
                        NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    }
                } else if (addItemToBag(itemBuy, true)) {
                    savezbLog("Mua item", item.buyGold + "@" + item.buyCoin + "@" + item.buyCoinLock + "@" + itemBuy.template.itemTemplateId + "@" + itemBuy.quantity);
                    if (taskMain != null && taskMain.template.taskId == 3 && taskMain.index == 0) {
                        taskMain.count = (short) countItemBag(23);
                        if (taskMain.count >= taskMain.template.counts[taskMain.index]) {
                            doTaskNext();
                            sendOpenUISay(4, taskMain.template.des[getSession().typeLanguage][taskMain.index]);
                        } else {
                            doTaskUpdate(taskMain.count);
                        }
                    }
                    if (item.buyCoin > 0) {
                        subXu(item.buyCoin * quantity);
                    } else if (item.buyCoinLock > 0) {
                        subYen(item.buyCoinLock * quantity);
                    } else if (item.buyGold > 0) {
                        subLuong(item.buyGold * quantity);
                    }
                    message = new Message(Cmd.ITEM_BUY);
                    message.writeInt(getXu());
                    message.writeInt(getYen());
                    message.writeInt(getLuong());
                    NJUtil.sendMessage(getSession(), message);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemMonToBag(Message message) {
        try {
            Item item = null;
            Player p = null;
            try {
                p = getPlayerMainControl();
                item = p.itemMons[message.readByte()];
            } catch (Exception e) {
            }
            if (item == null) {
                return;
            }
            if (item.template.type == 33) {
                int tt = 0;
                for (int i = 0; i < p.itemMons.length; ++i) {
                    if (p.itemMons[i] != null) {
                        ++tt;
                    }
                }
                if (tt > 1) {
                    if (item.getTemplateId() == 443) {
                        NJUtil.sendDialog(getSession(), "Vui lòng cất tất cả trang bị thú vào hành trang trước.");
                    } else {
                        NJUtil.sendDialog(getSession(), "Vui lòng cất tất cả trang bị xe máy vào hành trang trước.");
                    }
                    return;
                }
            }
            int indexUI = findIndexEmpty(3);
            if (indexUI != -1) {
                itemMonToBag(item, indexUI);
                addItem(item, 3, item.indexUI = indexUI);
            } else {
                NJUtil.sendServer(getSession(), AlertFunction.BAG_FULL(getSession()));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemSale(Message message) {
        try {
            if (isLock) {
                sendNotUnlock();
                return;
            }
            if (getTaskFinish() <= 2) {
                NJUtil.sendDialog(getSession(), AlertFunction.SALE_ERROR1(getSession()));
                return;
            }
            Item item;
            try {
                item = itemBags[message.readByte()];
            } catch (Exception e) {
                return;
            }
            if (item == null) {
                return;
            }
            if (item.template.type == 22 || item.isTypeYoroi() || item.isTypeNguyetNhan()) {
                NJUtil.sendDialog(getSession(), AlertFunction.SALE_ERROR3(getSession()));
                return;
            }
            int quantity;
            try {
                if (getSession().clientType == GameServer.CLIENT_JAVA || getSession().clientType == GameServer.CLIENT_ANDROID) {
                    quantity = message.readInt();
                } else {
                    quantity = message.readUnsignedShort();
                }
            } catch (Exception e3) {
                quantity = 1;
            }
            if (quantity > 0 && quantity <= item.quantity) {
                if (item.upgrade > 0 && item.isTypeBody()) {
                    return;
                }
                item.quantity -= quantity;
                ghiloghack(item.getTemplateId());
                addYen(item.saleCoinLock * quantity);
                if (item.isSaveLog()) {
                    savezbLog("Ban item", item.saleCoinLock * quantity + "@" + item.getTemplateId() + "@" + quantity);
                }
                if (item.quantity <= 0) {
                    removeItem(item, item.typeUI);
                }/* else {
                    Database.updateItem(item);
                }*/
            }
            message = new Message(Cmd.ITEM_SALE);
            message.writeByte(item.indexUI);
            message.writeInt(getYen());
            if (quantity > 1) {
                message.writeShort(quantity);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doItemUse(Message message) {
        try {
            if (hp <= 0 || status == Player.ME_DIE || capcha != null) {
                return;
            }
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            Player playerMain = getPlayerMainControl();
            Item item;
            if (isLockUseItem()) {
                return;
            }
            try {
                int indexBag = message.readByte();
                item = itemBags[indexBag];
            } catch (Exception e) {
                return;
            }
            if (item == null || (item.template.gender != 2 && item.template.gender != playerMain.gender) || item.template.level > playerMain.level) {
                return;
            }
            if (EventManager.getInstance().useItem(this, item)) {
                return;
            }
            if (item.isTypeMon()) {
                if (item.template.type == 33) {
                    if (playerMain.itemMons[4] != null && item.getTemplateId() != playerMain.itemMons[4].getTemplateId()) {
                        if (item.getTemplateId() == 485) {
                            NJUtil.sendDialog(getSession(), "Vui lòng cất thú cưỡi vào hành trang trước khi sử dụng xe máy.");
                            return;
                        }
                        NJUtil.sendDialog(getSession(), "Vui lòng cất xe máy vào hành trang trước khi sử dụng thú cưỡi.");
                        return;
                    }
                } else {
                    if (playerMain.itemMons[4] == null) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm này chỉ có thể sử dụng khi có thú cưỡi.");
                        return;
                    }
                    if (playerMain.itemMons[4].getTemplateId() == 443 && item.getTemplateId() >= 443) {
                        NJUtil.sendDialog(getSession(), "Trang bị thú cưỡi không phù hợp.");
                        return;
                    }
                    if (playerMain.itemMons[4].getTemplateId() == 485 && item.getTemplateId() <= 485) {
                        NJUtil.sendDialog(getSession(), "Trang bị xe máy không phù hợp.");
                        return;
                    }
                }
                if (!item.isLock && item.options.size() == 2 && (item.getTemplateId() == 485 || item.getTemplateId() == 443 || item.getTemplateId() == 523 || item.getTemplateId() == 524 || item.isTrauDen() || item.isTrauVang())) {
                    item.createOptionThuCuoi();
                }
                item.isLock = true;
                Item itemMon = playerMain.itemMons[item.template.type - 29];
                int indexUI = item.indexUI;
                if (itemMon != null) {
                    item.indexUI = itemMon.indexUI;
                    item.typeUI = 41;
                    itemMon.indexUI = indexUI;
                    itemMon.typeUI = 3;
                    playerMain.itemMons[item.indexUI] = item;
                    itemBags[indexUI] = itemMon;
                } else {
                    removeItem(item, 3);
                    addItem(item, 41, item.template.type - 29);
                }
                updateItemUse(indexUI, item);
                loadThuCuoi(1);
            } else {
                if (item.isTypeBody() && (item.template.gender == 2 || item.template.gender == gender) && playerMain.level >= item.template.level) {
                    if (item.getTemplateId() == 397 && playerMain.classId != 1) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 398 && playerMain.classId != 2) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 399 && playerMain.classId != 3) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 400 && playerMain.classId != 4) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 401 && playerMain.classId != 5) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 402 && playerMain.classId != 6) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 420 && playerMain.classId != 1 && playerMain.classId != 2) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 421 && playerMain.classId != 3 && playerMain.classId != 4) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.getTemplateId() == 422 && playerMain.classId != 5 && playerMain.classId != 6) {
                        NJUtil.sendServer(getSession(), AlertFunction.ERROR_CLASS(getSession()));
                        return;
                    }
                    if (item.template.type == 1) {
                        if (playerMain.classId == 0 && !item.isItemClass0()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 1 && !item.isItemClass1()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 2 && !item.isItemClass2()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 3 && !item.isItemClass3()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 4 && !item.isItemClass4()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 5 && !item.isItemClass5()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                        if (playerMain.classId == 6 && !item.isItemClass6()) {
                            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
                            return;
                        }
                    }
                    item.isLock = true;
                    Item itemBody = playerMain.itemBodys[item.template.type];
                    int indexUI = item.indexUI;
                    if (itemBody != null) {
                        item.indexUI = itemBody.indexUI;
                        item.typeUI = 5;
                        itemBody.indexUI = indexUI;
                        itemBody.typeUI = 3;
                        playerMain.itemBodys[item.indexUI] = item;
                        itemBags[indexUI] = itemBody;
                        //Database.updateItem(item);
                        //Database.updateItem(itemBody);
                    } else {
                        removeItem(item, 3);
                        addItem(item, 5, item.template.type);
                    }
                    updateItemUse(indexUI, item);
                    changeNpcMe();
                    if (playerMain.taskMain != null &&
                        playerMain.taskMain.template.taskId == 2 &&
                        playerMain.taskMain.index == 0 &&
                        item.template.type == 1
                    ) {
                        doTaskNext();
                    }
                    if (playerMain.taskMain != null &&
                        playerMain.taskMain.template.taskId == 12 &&
                        playerMain.taskMain.index == 1 &&
                        playerMain.itemBodys[1] != null &&
                        playerMain.itemBodys[1].upgrade >= 1
                    ) {
                        doTaskNext();
                    }
                    if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 12 && playerMain.taskMain.index == 2) {
                        for (int i = 0; i < playerMain.itemBodys.length; ++i) {
                            if (playerMain.itemBodys[i] != null && itemBodys[i].upgrade >= 1 && playerMain.itemBodys[i].isTypeAdorn()) {
                                doTaskNext();
                                break;
                            }
                        }
                    }
                    if (playerMain.taskMain != null && playerMain.taskMain.template.taskId == 12 && playerMain.taskMain.index == 3) {
                        for (int j = 0; j < playerMain.itemBodys.length; ++j) {
                            if (playerMain.itemBodys[j] != null && playerMain.itemBodys[j].upgrade >= 1 && playerMain.itemBodys[j].isTypeClothe()) {
                                doTaskNext();
                                break;
                            }
                        }
                    }
                    return;
                }
                if (item.template.type == 22) {
                    Item it1 = null, it2 = null, it3 = null, it4 = null, it5 = null, it6 = null, it7 = null;
                    for (Item itemBag : itemBags) {
                        if (itemBag != null) {
                            if (itemBag.getTemplateId() == 225) {
                                it1 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 223) {
                                it2 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 226) {
                                it3 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 224) {
                                it4 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 227) {
                                it5 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 228) {
                                it6 = itemBag;
                            }
                            if (itemBag.getTemplateId() == 222) {
                                it7 = itemBag;
                            }
                        }
                    }
                    if (it1 != null && it2 != null && it3 != null && it4 != null && it5 != null && it6 != null && it7 != null) {
                        removeItem(it1, 3);
                        removeItem(it2, 3);
                        removeItem(it3, 3);
                        removeItem(it4, 3);
                        removeItem(it5, 3);
                        removeItem(it6, 3);
                        removeItem(it7, 3);
                        sendClearItemBag(it1.indexUI);
                        sendClearItemBag(it2.indexUI);
                        sendClearItemBag(it3.indexUI);
                        sendClearItemBag(it4.indexUI);
                        sendClearItemBag(it5.indexUI);
                        sendClearItemBag(it6.indexUI);
                        sendClearItemBag(it7.indexUI);
                        Item it = null;
                        if (playerMain.classId == 1 || playerMain.classId == 2) {
                            it = new Item(420, true, " doItemUse char 1");
                        } else if (playerMain.classId == 3 || playerMain.classId == 4) {
                            it = new Item(421, true, "doItemUse char 2");
                        } else if (playerMain.classId == 5 || playerMain.classId == 6) {
                            it = new Item(422, true, "doItemUse char 3");
                        }
                        callEffectBall();
                        NJUtil.sleep(2000L);
                        if (it != null) {
                            if (findItem(it.getTemplateId()) != null) {
                                sendUpdateLuong(10000);
                            } else {
                                it.createOptionYoroi();
                                addItemToBagNoDialog(it);
                            }
                        }
                    } else {
                        NJUtil.sendServer(getSession(), AlertFunction.DRAGONBALL_7(getSession()));
                    }
                    return;
                }
            }
            if (item.isTypeCrystal() || item.template.type == 28 || item.getTemplateId() == 242 || item.getTemplateId() == 243 || item.getTemplateId() == 244 || item.getTemplateId() == 245) {
                return;
            }
            if (playerMain.isLock &&
                item.getTemplateId() != 22 &&
                item.getTemplateId() != 23 &&
                item.getTemplateId() != 24 &&
                item.getTemplateId() != 25 &&
                item.getTemplateId() != 26 &&
                item.getTemplateId() != 27 &&
                item.getTemplateId() != 248 &&
                item.getTemplateId() != 249 &&
                item.getTemplateId() != 250 &&
                item.template.type != 16 &&
                item.template.type != 17 &&
                item.template.type != 25 &&
                item.template.type != 23 &&
                item.template.type != 24
            ) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            IUseHandler usable = ServerController.hUsableItems.get(item.getTemplateId());
            if (usable != null) {
                usable.useItem(this, item);
            }
        } catch (Exception e) {
        } finally {
            doPlayerInfo(this);
            message.cleanup();
        }
    }

    public void doItemUseChangeMap(Message message) {
        try {
            if (isControlCharNhanBan() || getHp() <= 0 || isLockUseItem() || map == null) {
                return;
            }
            Item item = null;
            try {
                item = itemBags[message.readByte()];
            } catch (Exception e) {
            }
            if (item == null || (item.template.gender != 2 && item.template.gender != gender) || item.template.level > level) {
                return;
            }
            if (getSession().type_admin < ROLE_GM && (map.isDun || map.isDunVA || map.isDunClan || map.isDunPB || map.template.isMapChienTruong() || map.template.mapTemplateId == 130 || map.template.mapTemplateId == 131 || map.template.mapTemplateId == 132)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_TELEPORT(getSession()));
                return;
            }
            int[] maps = { 1, 27, 72, 10, 17, 22, 32, 38, 43, 48 };
            if (item.template.itemTemplateId == 35) {
                if (ServerController.huPlayers.get(getSession().userId).doChangeMap(maps[message.readByte()], false, "doitemusechangemap 35")) {
                    removeItem(item, 3);
                    sendClearItemBag(item.indexUI);
                }
            } else if (item.template.itemTemplateId == 37) {
                try {
                    if (getHp() <= 0) {
                        return;
                    }
                    String al = getAlertTime(timeUseItemChangeMap);
                    if (al != null) {
                        NJUtil.sendDialog(getSession(), al);
                        return;
                    }
                    timeUseItemChangeMap = System.currentTimeMillis() + delayItemChangeMap;
                    ServerController.huPlayers.get(getSession().userId).doChangeMap(maps[message.readByte()], false, "doitemusechangemap 37");
                } catch (Exception e) {
                    NJUtil.sendDialog(getSession(), AlertFunction.ERROR(getSession()));
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doMenu(Message message) {
        if (capcha != null) {
            return;
        }
        PlayerTemplate playerTemplate;
        try {
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            try {
                playerTemplate = ServerController.playerTemplates.get(message.readByte());
                playerTemplate.menuId = message.readByte();
                playerTemplate.optionId = message.readByte();
            } catch (Exception e) {
                return;
            }
            if (!isOk(playerTemplate.playerTemplateId)) {
                return;
            }
            if (EventManager.getInstance().handleNpcOption(this, playerTemplate)) {
                return;
            }
            INpcHandler npcHandler = ServerController.hNpcMenus.get(playerTemplate.playerTemplateId);
            if (npcHandler != null) {
                npcHandler.openMenu(this, playerTemplate);
            }
        } catch (Exception e) {
        } finally {
            message.cleanup();
        }
    }

    public void doKhamnNgoc(Message message) {
        try {
            if (!GameServer.openKhamNgoc) {
                NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                return;
            }
            if (isControlCharNhanBan() || !isOk(6)) {
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            byte type = message.dis.readByte();
            Item itemNgoc;
            if (type == 0) {
                Item item;
                try {
                    item = itemBags[message.readByte()];
                    itemNgoc = itemBags[message.readByte()];
                } catch (Exception e) {
                    LOGGER.error(getStringBaseInfo(), e);
                    return;
                }
                int timeNow = (int) (System.currentTimeMillis() / 1000L);
                if (!GameServer.isServerLocal() && timeNow - timeUp <= 1) {
                    NJUtil.sendDialog(getSession(), AlertFunction.FAST(getSession()));
                    return;
                }
                if (item == null || itemNgoc == null) {
                    if (getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendDialog(getSession(), "Không tìm thấy vật phẩm");
                    } else {
                        NJUtil.sendDialog(getSession(), "Item not found");
                    }
                    return;
                }
                if (item.indexUI == itemNgoc.indexUI) {
                    if (getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendDialog(getSession(), "Vật phẩm không hợp lệ");
                    } else {
                        NJUtil.sendDialog(getSession(), "Invalid item");
                    }
                    return;
                }
                timeUp = timeNow;
                int countNeed;
                if (!item.checkKhamNgoc(itemNgoc)) {
                    NJUtil.sendDialog(getSession(), "Không thể khảm cùng 1 loại ngọc lên 1 vật phẩm");
                    return;
                }
                if (item.isTypeBody() && item.template.level >= 10) {
                    Vector<Item> items = new Vector<>();
                    try {
                        int[] a = new int[18];
                        for (int i = 0; i < 18; ++i) {
                            a[i] = -1;
                        }
                        for (int i = 0; i < 18; ++i) {
                            try {
                                a[i] = message.readByte();
                            } catch (Exception e) {
                            }
                        }
                        for (int i = 0; i < 17; ++i) {
                            for (int j = i + 1; j < 18; ++j) {
                                if (a[i] > -1 && a[j] > -1 && a[i] == a[j]) {
                                    ++countHackNgoc;
                                    if (countHackNgoc >= 3) {
                                        doCancelTrade1();
                                        Database.saveLogAll(name, "hack kham ngoc", "hack");
                                        getSession().disconnect("hack kham ngoc");
                                    }
                                    return;
                                }
                            }
                        }
                        for (int i = 0; i < 18; ++i) {
                            int indexUI = a[i];
                            if (indexUI > -1 && itemBags[indexUI] != null && itemBags[indexUI].isTypeCrystal()) {
                                items.add(itemBags[indexUI]);
                            }
                        }
                    } catch (Exception e) {
                    }
                    int countHave = countCrystal(items);
                    if (!item.isTypeClothe() && !item.isTypeAdorn() && !item.isTypeWeapon()) {
                        return;
                    }
                    if (item.gem.size() >= 4) {
                        NJUtil.sendDialog(getSession(), AlertFunction.CANOT_KHAM(getSession()));
                        return;
                    }
                    long sum = (long) getXu() + (long) getYen();
                    if (price_kham_gem[itemNgoc.upgrade] > sum) {
                        return;
                    }
                    if (price_kham_gem[itemNgoc.upgrade] > getYen()) {
                        subXu(price_kham_gem[itemNgoc.upgrade] - getYen());
                        setYen(0);
                    } else {
                        subYen(price_kham_gem[itemNgoc.upgrade]);
                    }
                    countNeed = khamGem[itemNgoc.upgrade];
                    int limit = countNeed * maxPercents[itemNgoc.upgrade] / 100;
                    if (countHave > limit) {
                        countHave = limit;
                    }
                    /*boolean isGold = false;
                    if (isGold) {
                        subLuong(goldUps[itemNgoc.upgrade]);
                        countHave *= (int) 1.5;
                        savezbLog("Nang cap can than cap " + itemNgoc.upgrade, getLuong() + "@" + goldUps[itemNgoc.upgrade]);
                    }*/
                    if (countHave >= countNeed || NJUtil.probability(countHave, countNeed - countHave) == 0) {
                        items.add(itemNgoc);
                        item.isLock = true;
                        ItemOption optiongia = null;
                        if (item.options == null) {
                            item.options = new Vector<>();
                        }
                        for (int k = 0; k < item.options.size(); ++k) {
                            if (item.options.get(k).optionTemplate.itemOptionTemplateId == Item.OPT_YEN_THAO_NGOC) {
                                optiongia = item.options.get(k);
                                break;
                            }
                        }
                        if (optiongia == null) {
                            optiongia = new ItemOption(0, Item.OPT_YEN_THAO_NGOC);
                            item.options.add(optiongia);
                        }
                        ItemOption itemOption = optiongia;
                        itemOption.param += price_kham_gem[itemNgoc.upgrade] / 2;
                        item.gem.add(itemNgoc);
                        savezaLog("kham thanh cong @" + item.getTemplateId() + "@" + itemNgoc.getTemplateId() + "_" + itemNgoc.upgrade);
                        Player pSave = this;
                        Database.savePlayer(pSave, map.getTemplateId());
                        message = new Message(Cmd.UPGRADE);
                        message.writeByte(5);
                    } else {
                        savezaLog("kham that bai @" + item.getTemplateId() + "@" + itemNgoc.getTemplateId() + "_" + itemNgoc.upgrade);
                        message = new Message(Cmd.UPGRADE);
                        message.writeByte(6);
                    }
                    message.writeInt(getLuong());
                    message.writeInt(getXu());
                    message.writeInt(getYen());
                    message.writeByte(item.upgrade);
                    NJUtil.sendMessage(getSession(), message);
                    for (Item value : items) {
                        removeItem(value, 3);
                    }
                }
            } else if (type == 1) {
                doLuyenNgoc(message);
            } else if (type == 2) {
                itemNgoc = itemBags[message.readByte()];
                if (itemNgoc == null || !itemNgoc.isTypeGem()) {
                    return;
                }
                if (itemNgoc.options != null) {
                    int money = itemNgoc.upgrade * itemNgoc.upgrade * itemNgoc.upgrade * 5000;
                    if (getXu() < money) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_COIN(getSession()));
                        return;
                    }
                    for (int l = 0; l < itemNgoc.options.size(); ++l) {
                        ItemOption option = itemNgoc.options.elementAt(l);
                        if (option != null && option.param < -1) {
                            option.param += NJUtil.randomMinMax(1, Math.abs(option.param));
                            if (option.param > -1) {
                                option.param = -1;
                            }
                        }
                    }
                    subXu(money);
                    message = new Message(Cmd.KHAM_NGOC);
                    message.writeByte(2);
                    message.writeInt(getLuong());
                    message.writeInt(getXu());
                    message.writeInt(getYen());
                    message.writeByte(itemNgoc.upgrade);
                    NJUtil.sendMessage(getSession(), message);
                    doSendDetailItem(itemNgoc);
                }
                NJUtil.sendDialog(getSession(), itemNgoc.template.getName(getSession()) + " đã được gọt.");
            } else if (type == 3) {
                int index = message.readByte();
                itemNgoc = itemBags[index];
                if (itemNgoc == null || itemNgoc.gem == null || itemNgoc.gem.size() == 0) {
                    return;
                }
                if (countFreeBag() < itemNgoc.gem.size()) {
                    NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    return;
                }
                int money2 = 0;
                for (int m = 0; m < itemNgoc.gem.size(); ++m) {
                    money2 += price_kham_gem[itemNgoc.gem.get(m).upgrade] / 2;
                }
                if (getYen() < money2) {
                    NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_COIN_LOCK(getSession()));
                    return;
                }
                for (int m = 0; m < itemNgoc.gem.size(); ++m) {
                    addItemToBag(itemNgoc.gem.get(m), false);
                }
                itemNgoc.gem.clear();
                try {
                    Database.savePlayer(this, map.getTemplateId());
                } catch (Exception e) {
                }
                if (itemNgoc.options != null) {
                    for (int m = 0; m < itemNgoc.options.size(); ++m) {
                        if (itemNgoc.options.get(m).optionTemplate.itemOptionTemplateId == Item.OPT_YEN_THAO_NGOC) {
                            itemNgoc.options.remove(m);
                            break;
                        }
                    }
                }
                subYen(money2);
                message = new Message(Cmd.KHAM_NGOC);
                message.writeByte(3);
                message.writeInt(getLuong());
                message.writeInt(getXu());
                message.writeInt(getYen());
                message.writeByte(itemNgoc.upgrade);
                NJUtil.sendMessage(getSession(), message);
                doSendDetailItem(itemNgoc);
                NJUtil.sendDialog(getSession(), itemNgoc.template.getName(getSession()) + " đã tháo ngọc.");
            }
        } catch (Exception e) {
            LOGGER.error("{}. Loi kham ngoc: {}", getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doLatHinh(Message message) {
        try {
            if (!GameServer.openLatHinh) {
                NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                return;
            }
            if (isNhanban() || !isNpcOk(30)) {
                return;
            }
            if (countFreeBag() <= 0) {
                NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                return;
            }
            int index = message.readByte();
            if (index < 0 || index > 8) {
                index = 0;
            }
            Item itemUse = findItemBag(340);
            if (itemUse != null) {
                int[] results = new int[9];
                int idReward = -1;
                Item it = null;
                String str = "";
                ArrayList<DropRate> drops = new ArrayList<>();
                // trang bị
                drops.add(new DropRate(3, -6));
                // svc
                drops.add(new DropRate(2, -5));
                // thức ăn
                drops.add(new DropRate(2, -4));
                // yên
                drops.add(new DropRate(5, -3));
                drops.add(new DropRate(3, -2));
                drops.add(new DropRate(1, -1));
                // đá cường hoá
                drops.add(new DropRate(1, 4));
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(1, 6));
                drops.add(new DropRate(2, 7));
                drops.add(new DropRate(3, 8));
                drops.add(new DropRate(4, 9));
                // mặt nạ
                drops.add(new DropRate(3, 344));
                drops.add(new DropRate(3, 346));
                drops.add(new DropRate(3, 403));
                drops.add(new DropRate(3, 404));
                drops.add(new DropRate(3, 407));
                drops.add(new DropRate(3, 408));
                // thú cưỡi
                drops.add(new DropRate(3, 443));
                drops.add(new DropRate(3, 485));
                drops.add(new DropRate(3, 523));
                // pet
                drops.add(new DropRate(3, 419));
                drops.add(new DropRate(4, 742));
                drops.add(new DropRate(4, 772));
                // exp gia tộc
                drops.add(new DropRate(2, 436));
                drops.add(new DropRate(2, 437));
                drops.add(new DropRate(3, 438));
                // túi vải
                drops.add(new DropRate(4, 283));
                int[] bookIds;
                if (level < 70) {
                    bookIds = new int[]{ 311, 312, 313, 314, 315, 316 }; // 6x
                } else if (level < 80) {
                    bookIds = new int[]{ 375, 376, 377, 378, 379, 380 }; // 7x
                } else {
                    bookIds = new int[]{ 552, 553, 554, 555, 556, 557 }; // 8x
                }
                int foodIds;
                if (level < 40) {
                    foodIds = 249;
                } else if (level < 50) {
                    foodIds = 250;
                } else if (level < 60) {
                    foodIds = 30;
                } else if (level < 70) {
                    foodIds = 409;
                } else {
                    foodIds = 410;
                }
                try {
                    for (int i = 0; i < results.length; ++i) {
                        int dropId = NJUtil.dropItem(drops);
                        switch (dropId) {
                            case -6:
                                results[i] = thuongMaxOp().getTemplateId();
                                break;
                            case -5:
                                results[i] = Item.randomItem(bookIds);
                                break;
                            case -4:
                                results[i] = foodIds;
                                break;
                            case -3:
                            case -2:
                            case -1:
                                results[i] = 12;
                                break;
                            default:
                                results[i] = dropId;
                                break;
                        }
                    }
                    idReward = NJUtil.dropItem(drops);
                    if (idReward == -6) {
                        str = "it max opt";
                        it = thuongMaxOp();
                    } else if (idReward == -5) {
                        it = new Item(Item.randomItem(bookIds), false, "lat hinh");
                    } else if (idReward == -4) {
                        it = new Item(foodIds, false, "lat hinh");
                    } else if (idReward == -3) {
                        str = "30tr yen";
                        results[index] = 12;
                        sendUpdateCoinLockYen(30000000);
                    } else if (idReward == -2) {
                        str = "10tr yen";
                        results[index] = 12;
                        sendUpdateCoinLockYen(10000000);
                    } else if (idReward == -1) {
                        str = "5tr yen";
                        results[index] = 12;
                        sendUpdateCoinLockYen(5000000);
                    } else if (idReward > 0) {
                        it = new Item(idReward, false, "lat hinh");
                        if (it.getTemplateId() == 344 || it.getTemplateId() == 346) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            it.options = new Vector<>();
                            it.options.add(new ItemOption(40, 57));
                        } else if (it.getTemplateId() == 403 || it.getTemplateId() == 404) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            (it.options = new Vector<>()).add(new ItemOption(80, 57));
                        } else if (it.getTemplateId() == 407 || it.getTemplateId() == 408) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            it.options = new Vector<>();
                            it.options.add(new ItemOption(20, 58));
                            it.options.add(new ItemOption(500, 6));
                        } else if (it.isHuyetSacHungLang()) {
                            it.isLock = false;
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 1000;
                            option.optionTemplate = ServerController.iOptionTemplates.get(65);
                            it.options.add(option);
                            option = new ItemOption();
                            option.param = 1000;
                            option.optionTemplate = ServerController.iOptionTemplates.get(66);
                            it.options.add(option);
                            int[] pcday = { 5, 20, 40, 100 };
                            long[] day = { 30L, 15L, 7L, 3L };
                            int idday = NJUtil.getPercent(100, pcday);
                            it.expires = System.currentTimeMillis() + day[idday] * 24L * 60L * 60000L;
                        } else if (it.isXichNhanNganLang() || it.isXeMay()) {
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 0;
                            option.optionTemplate = ServerController.iOptionTemplates.get(65);
                            it.options.add(option);
                            option = new ItemOption();
                            option.param = 1000;
                            option.optionTemplate = ServerController.iOptionTemplates.get(66);
                            it.options.add(option);
                        } else if (it.isPetChimTinhAnh()) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            it.options = new Vector<>();
                            it.options.add(new ItemOption(1000, 0));
                            it.options.add(new ItemOption(1000, 1));
                        } else if (it.isPetTuanLoc()) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            it.createOptionPetTuanLoc();
                        } else if (it.isPetReBiHanh()) {
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                            it.createOptionPetBiReHanh();
                        }
                    }
                    if (it != null) {
                        results[index] = it.template.itemTemplateId;
                        addItemToBagNoDialog(it);
                    }
                    message = NJUtil.messageNotMap(Cmd.LAT_HINH);
                    for (int result : results) {
                        message.writeShort(result);
                    }
                    NJUtil.sendMessage(getSession(), message);
                } catch (Exception e) {
                    sendUpdateCoinLockYen(100000);
                    results[index] = 12;
                    message = NJUtil.messageNotMap(Cmd.LAT_HINH);
                    for (int result : results) {
                        message.writeShort(result);
                    }
                    NJUtil.sendMessage(getSession(), message);
                } finally {
                    useItemUpToUp(itemUse);
                    sendUseItemUpToUp(itemUse.indexUI, 1);
                    ++countLatHinh;
                    if (taskMain != null && taskMain.template.taskId == 40 && taskMain.index == 3) {
                        checkTaskIndex();
                    }
                    if (countLatHinh % 3 == 0 && typeNvbian == 8) {
                        fibian();
                    }
                    if (it != null) {
                        savezbLog("lat hinh nhan duoc: " + it.template.name, it.getTemplateId() + "@L" + getLuong());
                    } else {
                        savezbLog("lat hinh nhan duoc: " + str, idReward + "@L" + getLuong());
                    }
                }
            } else {
                NJUtil.sendDialog(getSession(), AlertFunction.ALERT_QUAYSO1(getSession()));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doLockTeam(Message message) {
        try {
            if (party == null || !party.isTeamLeader(this) || party.players.size() <= 0) {
                return;
            }
            party.isLock = message.readBoolean();
            message = NJUtil.messageSubCommand(Cmd.LOCK_PARTY);
            message.writeBoolean(party.isLock);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doLoiDai(String name1, String name2, boolean isGiaiDau) {
        DunVD dun = DunVD.createDun(111);
        if (dun != null) {
            dun.isGiaDau = isGiaiDau;
            dun.name1 = name1;
            dun.name2 = name2;
            Player player1 = ServerController.hnPlayers.get(name1);
            Player player2 = ServerController.hnPlayers.get(name2);
            player1.clearTestDun();
            player2.clearTestDun();
            if (dun.isGiaDau) {
                ++player1.countLoiDai;
                ++player2.countLoiDai;
            }
            TestSkill testSkillDun = new TestSkill(5000);
            testSkillDun.player1 = player1;
            testSkillDun.player2 = player2;
            testSkillDun.money1 = 500;
            testSkillDun.money2 = 500;
            player2.testSkillDun = testSkillDun;
            player1.testSkillDun = testSkillDun;
            int time = 600;
            dun.timeEnd = System.currentTimeMillis() + time * 1000;
            dun.phe1 = new Vector<>();
            dun.phe2 = new Vector<>();
            dun.money = testSkillDun.money1 + testSkillDun.money2;
            testSkillDun.player1.doOutParty();
            testSkillDun.player2.doOutParty();
            Effect eff;
            eff = new Effect();
            eff.template = ServerController.effTemplates.get(14);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = 10000;
            testSkillDun.player2.addEffect(eff);
            dun.str2 += testSkillDun.player2.name;
            dun.phe2.insertElementAt(testSkillDun.player2, 0);
            testSkillDun.player2.typePk = PK_PHE2;
            testSkillDun.player2.updateTypePk();
            testSkillDun.player2.goTestDun(dun, time, 600, 264);
            eff = new Effect();
            eff.template = ServerController.effTemplates.get(14);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = 10000;
            testSkillDun.player1.addEffect(eff);
            dun.str1 += testSkillDun.player1.name;
            dun.phe1.insertElementAt(testSkillDun.player1, 0);
            testSkillDun.player1.typePk = PK_PHE1;
            testSkillDun.player1.updateTypePk();
            testSkillDun.player1.goTestDun(dun, time, 160, 264);
            if (!isGiaiDau) {
                typePk = PK_NORMAL;
                updateTypePk();
                goTestDun(dun, time, 380, 336);
            }
        }
    }

    public void doLoiDaiClass(String name1, String name2, boolean isGiaiDau) {
        DunVDClass dun = DunVDClass.createDun(111);
        if (dun != null) {
            dun.isGiaDau = isGiaiDau;
            dun.name1 = name1;
            dun.name2 = name2;
            Player player1 = ServerController.hnPlayers.get(name1);
            Player player2 = ServerController.hnPlayers.get(name2);
            player1.clearTestDun();
            player2.clearTestDun();
            if (dun.isGiaDau) {
                ++player1.countLoiDaiClass;
                ++player2.countLoiDaiClass;
            }
            TestSkill testSkillDun = new TestSkill(5000);
            testSkillDun.player1 = player1;
            testSkillDun.player2 = player2;
            testSkillDun.money1 = 500;
            testSkillDun.money2 = 500;
            player2.testSkillDun = testSkillDun;
            player1.testSkillDun = testSkillDun;
            int time = 600;
            dun.timeEnd = System.currentTimeMillis() + time * 1000;
            dun.phe1 = new Vector<>();
            dun.phe2 = new Vector<>();
            dun.money = testSkillDun.money1 + testSkillDun.money2;
            testSkillDun.player1.doOutParty();
            testSkillDun.player2.doOutParty();
            Effect eff;
            eff = new Effect();
            eff.template = ServerController.effTemplates.get(14);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = 10000;
            testSkillDun.player2.addEffect(eff);
            dun.str2 = dun.str2 + testSkillDun.player2.name;
            dun.phe2.insertElementAt(testSkillDun.player2, 0);
            testSkillDun.player2.typePk = PK_PHE2;
            testSkillDun.player2.updateTypePk();
            testSkillDun.player2.goTestDun(dun, time, 600, 264);
            eff = new Effect();
            eff.template = ServerController.effTemplates.get(14);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = 10000;
            testSkillDun.player1.addEffect(eff);
            dun.str1 = dun.str1 + testSkillDun.player1.name;
            dun.phe1.insertElementAt(testSkillDun.player1, 0);
            testSkillDun.player1.typePk = PK_PHE1;
            testSkillDun.player1.updateTypePk();
            testSkillDun.player1.goTestDun(dun, time, 160, 264);
            if (!isGiaiDau) {
                typePk = PK_NORMAL;
                updateTypePk();
                goTestDun(dun, time, 380, 336);
            }
        }
    }

    public void doLuyenNgoc(Message message) {
        try {
            if (!GameServer.openKhamNgoc) {
                NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                return;
            }
            if (isControlCharNhanBan() || !isOk(6)) {
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            Item itemNgoc;
            try {
                itemNgoc = itemBags[message.readByte()];
            } catch (Exception e) {
                return;
            }
            int timeNow = (int) (System.currentTimeMillis() / 1000L);
            if (!GameServer.isServerLocal() && timeNow - timeUp <= 1) {
                NJUtil.sendDialog(getSession(), AlertFunction.FAST(getSession()));
                return;
            }
            if (itemNgoc == null || !itemNgoc.isTypeGem()) {
                return;
            }
            if (itemNgoc.upgrade >= 10) {
                NJUtil.sendDialog(getSession(), "Ngọc đã đạt cấp tối đa");
                return;
            }
            timeUp = timeNow;
            Vector<Item> items = new Vector<>();
            long totalExp = 0L;
            try {
                while (message.dis.available() > 0) {
                    int indexUI = message.readByte();
                    if (itemBags[indexUI] != null && itemBags[indexUI].isTypeGem()) {
                        for (Item item : items) {
                            if (indexUI == item.indexUI) {
                                ++countHackNgoc;
                                if (countHackNgoc >= 3) {
                                    doCancelTrade1();
                                    Database.saveLogAll(name, "hack luyen ngoc", "hack");
                                    getSession().disconnect("hack luyen ngoc");
                                }
                                return;
                            }
                        }
                        items.add(itemBags[indexUI]);
                        totalExp += GemTemplate.EXP_GEM[itemBags[indexUI].upgrade];
                    }
                }
                ItemOption optionexp = null;
                for (int i = 0; i < itemNgoc.options.size(); ++i) {
                    ItemOption option = itemNgoc.options.elementAt(i);
                    if (option.optionTemplate.itemOptionTemplateId == Item.OPT_EXP_UPGRADE) {
                        optionexp = option;
                        break;
                    }
                }
                if (optionexp == null) {
                    return;
                }
                totalExp += optionexp.param;
                int oldLv = itemNgoc.upgrade;
                long[] info = GemTemplate.getLevelGem(totalExp, oldLv);
                boolean isLenCap = false;
                if (oldLv < info[0]) {
                    isLenCap = true;
                    doChangeOptionLuyenNgoc(itemNgoc, itemNgoc.upgrade = (int) info[0], oldLv);
                    long deltaexp = totalExp - info[1];
                    if (info[0] < 10L) {
                        optionexp.param = (int) deltaexp;
                    }
                } else {
                    optionexp.param = (int) totalExp;
                }
                for (Item item : items) {
                    removeItem(item, 3);
                }
                optionexp = itemNgoc.getOptionByID(123); // giá khảm ngọc
                if (optionexp != null) {
                    optionexp.param = price_kham_gem[itemNgoc.upgrade];
                }
                itemNgoc.isLock = true;
                message = new Message(Cmd.KHAM_NGOC);
                message.writeByte(1);
                message.writeInt(getLuong());
                message.writeInt(getXu());
                message.writeInt(getYen());
                message.writeByte(itemNgoc.upgrade);
                NJUtil.sendMessage(getSession(), message);
                doSendDetailItem(itemNgoc);
                if (isLenCap) {
                    NJUtil.sendDialog(getSession(), "Đã luyện ngọc lên cấp " + itemNgoc.upgrade);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    public void doLuyenThach(Message message) {
        if (!GameServer.openTinhLuyen) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            int a1 = message.readByte();
            int a2 = message.readByte();
            int a3 = message.readByte();
            int a4 = message.readByte();
            if (a1 == a2 || a1 == a3 || a1 == a4 || a2 == a3 || a2 == a4 || a3 == a4) {
                ++countHackNgoc;
                if (countHackNgoc >= 3) {
                    doCancelTrade1();
                    Database.saveLogAll(name, "hack luyen thach", "hack");
                    getSession().disconnect("hack luyen thach");
                }
                return;
            }
            Item it1 = itemBags[a1];
            Item it2 = itemBags[a2];
            Item it3 = itemBags[a3];
            Item it4 = itemBags[a4];
            if (it1.getTemplateId() == 10 &&
                it2.getTemplateId() == Item.TU_TINH_THACH_SO &&
                it3.getTemplateId() == Item.TU_TINH_THACH_SO &&
                it4.getTemplateId() == Item.TU_TINH_THACH_SO
            ) {
                doCancelTrade1();
                removeItem(it1, 3);
                removeItem(it2, 3);
                removeItem(it3, 3);
                removeItem(it4, 3);
                sendClearItemBag(it1.indexUI);
                sendClearItemBag(it2.indexUI);
                sendClearItemBag(it3.indexUI);
                sendClearItemBag(it4.indexUI);
                Item itAdd = new Item(Item.TU_TINH_THACH_TRUNG, false, "doLuyenThach");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                itAdd = new Item(Item.TU_TINH_THACH_TRUNG, false, "doLuyenThach");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                itAdd = new Item(Item.TU_TINH_THACH_TRUNG, false, "doLuyenThach");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                savezbLog("luyen thach: trung cap", "luyenthach");
                Database.saveLogAll(name, "luyen thach: trung cap", "luyenthach");
                return;
            }
            if (it1.getTemplateId() == 11 &&
                it2.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                it3.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                it4.getTemplateId() == Item.TU_TINH_THACH_TRUNG
            ) {
                doCancelTrade1();
                removeItem(it1, 3);
                removeItem(it2, 3);
                removeItem(it3, 3);
                removeItem(it4, 3);
                sendClearItemBag(it1.indexUI);
                sendClearItemBag(it2.indexUI);
                sendClearItemBag(it3.indexUI);
                sendClearItemBag(it4.indexUI);
                Item itAdd = new Item(Item.TU_TINH_THACH_CAO, false, "doLuyenThach3");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                itAdd = new Item(Item.TU_TINH_THACH_CAO, false, "doLuyenThach4");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                itAdd = new Item(Item.TU_TINH_THACH_CAO, false, "doLuyenThach5");
                itAdd.setTimeExpire(43200L);
                addItemToBagNoDialog(itAdd);
                savezbLog("luyen thach: cao cap", "luyenthach");
                Database.saveLogAll(name, "luyen thach: cao cap", "luyenthach");
                return;
            }
            try {
                int a5 = message.readByte();
                int a6 = message.readByte();
                int a7 = message.readByte();
                int a8 = message.readByte();
                int a9 = message.readByte();
                if (a5 == a6 || a5 == a7 || a5 == a8 || a5 == a9 || a6 == a7 || a6 == a8 || a6 == a9 || a7 == a8 || a7 == a9 || a9 == a8) {
                    ++countHackNgoc;
                    if (countHackNgoc >= 3) {
                        doCancelTrade1();
                        Database.saveLogAll(name, "hack luyen thach", "hack");
                        getSession().disconnect("hack luyen thach");
                    }
                    return;
                }
                Item it6 = itemBags[a5];
                Item it7 = itemBags[a6];
                Item it8 = itemBags[a7];
                Item it9 = itemBags[a8];
                Item it0 = itemBags[a9];
                if (it1.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it2.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it3.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it4.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it6.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it7.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it8.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it9.getTemplateId() == Item.TU_TINH_THACH_SO &&
                    it0.getTemplateId() == Item.TU_TINH_THACH_SO
                ) {
                    doCancelTrade1();
                    removeItem(it1, 3);
                    removeItem(it2, 3);
                    removeItem(it3, 3);
                    removeItem(it4, 3);
                    removeItem(it6, 3);
                    removeItem(it7, 3);
                    removeItem(it8, 3);
                    removeItem(it9, 3);
                    removeItem(it0, 3);
                    sendClearItemBag(it1.indexUI);
                    sendClearItemBag(it2.indexUI);
                    sendClearItemBag(it3.indexUI);
                    sendClearItemBag(it4.indexUI);
                    sendClearItemBag(it6.indexUI);
                    sendClearItemBag(it7.indexUI);
                    sendClearItemBag(it8.indexUI);
                    sendClearItemBag(it9.indexUI);
                    sendClearItemBag(it0.indexUI);
                    Item itAdd = new Item(Item.TU_TINH_THACH_TRUNG, false, "doLuyenThach6");
                    itAdd.setTimeExpire(43200L);
                    addItemToBagNoDialog(itAdd);
                    savezbLog("luyen thach: trung cap ko co da", "luyenthach");
                    Database.saveLogAll(name, "luyen thach: trung cap ko da", "luyenthach");
                    return;
                }
                if (it1.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it2.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it3.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it4.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it6.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it7.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it8.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it9.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                    it0.getTemplateId() == Item.TU_TINH_THACH_TRUNG
                ) {
                    doCancelTrade1();
                    removeItem(it1, 3);
                    removeItem(it2, 3);
                    removeItem(it3, 3);
                    removeItem(it4, 3);
                    removeItem(it6, 3);
                    removeItem(it7, 3);
                    removeItem(it8, 3);
                    removeItem(it9, 3);
                    removeItem(it0, 3);
                    sendClearItemBag(it1.indexUI);
                    sendClearItemBag(it2.indexUI);
                    sendClearItemBag(it3.indexUI);
                    sendClearItemBag(it4.indexUI);
                    sendClearItemBag(it6.indexUI);
                    sendClearItemBag(it7.indexUI);
                    sendClearItemBag(it8.indexUI);
                    sendClearItemBag(it9.indexUI);
                    sendClearItemBag(it0.indexUI);
                    Item itAdd = new Item(Item.TU_TINH_THACH_CAO, false, "doLuyenThach 7");
                    itAdd.setTimeExpire(43200L);
                    addItemToBagNoDialog(itAdd);
                    savezbLog("luyen thach: cao cap ko co da", "luyenthach");
                    Database.saveLogAll(name, "luyen thach: cao cap ko da", "luyenthach");
                    return;
                }
                NJUtil.sendServer(getSession(), "Nguyên liệu luyện thạch không hợp lệ.");
                doCancelTrade1();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doMoiGTC(Message message) {
        try {
            if (capcha != null) {
                return;
            }
            if (map.giatocchien != null && clan != null && (clan.isMain(name) || clan.isAssist(name))) {
                String nameMem = message.readUTF();
                if (clan.getMember(nameMem) != null) {
                    Player playermap = ServerController.hnPlayers.get(nameMem);
                    if (playermap != null && playermap.capcha != null) {
                        return;
                    }
                    if (map.giatocchien.addInvite(nameMem, clan)) {
                        if (playermap == null || playermap.isControlCharNhanBan()) {
                            return;
                        }
                        if (playermap.level < 40) {
                            NJUtil.sendDialog(getSession(), AlertFunction.NOT_INVITE_LEVEL40(getSession()));
                            return;
                        }
                        message = NJUtil.messageNotMap(Cmd.MOI_GTC);
                        message.writeUTF(name);
                        NJUtil.sendMessage(playermap.getSession(), message);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doMoiTatCaGTC() {
        try {
            if (capcha != null) {
                return;
            }
            if (map.giatocchien != null && clan != null && (clan.isMain(name) || clan.isAssist(name))) {
                for (int i = 0; i < clan.members.size(); ++i) {
                    String nameMem = clan.members.get(i).name;
                    Player playermap = ServerController.hnPlayers.get(nameMem);
                    if (playermap != null && playermap.capcha != null) {
                        return;
                    }
                    if (map.giatocchien.addInvite(nameMem, clan)) {
                        if (playermap != null && !playermap.isControlCharNhanBan()) {
                            if (playermap.level >= 40) {
                                Message message = NJUtil.messageNotMap(Cmd.MOI_GTC);
                                message.writeUTF(name);
                                NJUtil.sendMessage(playermap.getSession(), message);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doMoveMember(Message message) {
        try {
            byte index = message.readByte();
            if (party == null || !party.players.get(0).equals(this) || index <= 0 || index >= party.players.size()) {
                return;
            }
            party.moveOut(party.players.get(index));
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenClanItem() {
        try {
            if (clan == null || (!clan.isMain(name) && !clan.isAssist(name))) {
                return;
            }
            if (clan.itemlevel == 0 && clan.coin >= 5000000 && clan.level >= 5) {
                ++clan.itemlevel;
                clan.coin -= 5000000;
                doRequestClanInfo();
                NJUtil.sendServer(getSession(), String.valueOf(AlertFunction.CLAN_4(getSession())) + clan.itemlevel + AlertFunction.CLAN_5(getSession()));
            } else if (clan.itemlevel == 1 && clan.coin >= 10000000 && clan.level >= 10) {
                ++clan.itemlevel;
                clan.coin -= 10000000;
                doRequestClanInfo();
                NJUtil.sendServer(getSession(), String.valueOf(AlertFunction.CLAN_4(getSession())) + clan.itemlevel + AlertFunction.CLAN_5(getSession()));
            } else if (clan.itemlevel == 2 && clan.coin >= 15000000 && clan.level >= 15) {
                ++clan.itemlevel;
                clan.coin -= 15000000;
                doRequestClanInfo();
                NJUtil.sendServer(getSession(), String.valueOf(AlertFunction.CLAN_4(getSession())) + clan.itemlevel + AlertFunction.CLAN_5(getSession()));
            } else if (clan.itemlevel == 3 && clan.coin >= 30000000 && clan.level >= 20) {
                ++clan.itemlevel;
                clan.coin -= 30000000;
                doRequestClanInfo();
                NJUtil.sendServer(getSession(), String.valueOf(AlertFunction.CLAN_4(getSession())) + clan.itemlevel + AlertFunction.CLAN_5(getSession()));
            } else if (clan.itemlevel == 4 && clan.coin >= 50000000 && clan.level >= 25) {
                ++clan.itemlevel;
                clan.coin -= 50000000;
                doRequestClanInfo();
                NJUtil.sendServer(getSession(), String.valueOf(AlertFunction.CLAN_4(getSession())) + clan.itemlevel + AlertFunction.CLAN_5(getSession()));
            } else {
                NJUtil.sendDialog(getSession(), AlertFunction.CLAN_6(getSession()));
            }
        } catch (Exception e) {
        }
    }

    public void doOpenMenuId(int id) {
        try {
            if (id == 0) {
                if (countFreeBag() <= 0) {
                    NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(getSession()), "1"));
                    return;
                }
                doBagSort();
                int countChange = 250;
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] != null && itemBags[i].getTemplateId() == 251 && itemBags[i].quantity >= countChange) {
                        useItemUpToUp(itemBags[i], countChange);
                        sendUseItemUpToUp(i, countChange);
                        addItemToBagNoDialog(new Item(252, false, "doOpenMenuId char"));
                        return;
                    }
                }
                NJUtil.sendServer(getSession(), countChange + " " + AlertFunction.CHANGE_BOOK_SKILL(getSession()));
            } else if (id == 1) {
                if (countFreeBag() <= 0) {
                    NJUtil.sendServer(getSession(), NJUtil.replace(AlertFunction.BAG_NOT_FREE2(getSession()), "1"));
                    return;
                }
                doBagSort();
                int countChange = 300;
                for (int i = 0; i < itemBags.length; ++i) {
                    if (itemBags[i] != null && itemBags[i].getTemplateId() == 251 && itemBags[i].quantity >= countChange) {
                        useItemUpToUp(itemBags[i], countChange);
                        sendUseItemUpToUp(i, countChange);
                        addItemToBagNoDialog(new Item(253, false, "doOpenMenuId char 1"));
                        return;
                    }
                }
                NJUtil.sendServer(getSession(), countChange + " " + AlertFunction.CHANGE_BOOK_SKILL(getSession()));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenMenuId(String[] arr, short[] ids, String str) {
        try {
            Message message = new Message(Cmd.OPEN_MENU_ID);
            message.writeUTF(str);
            message.writeByte(arr.length);
            for (int i = 0; i < arr.length; ++i) {
                message.writeUTF(arr[i]);
                message.writeShort(ids[i]);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenShop(int typeUI) {
        try {
            Message message = new Message(Cmd.OPEN_UI_SHOP);
            message.writeByte(typeUI);
            Vector<Item> items = null;
            switch (typeUI) {
                case 14: {
                    if (playerIDBuyItem == -1) {
                        items = ServerController.shopStores;
                        break;
                    }
                    Player p;
                    try {
                        p = ServerController.hpPlayers.get(playerIDBuyItem);
                    } catch (Exception e) {
                        return;
                    }
                    if (p == null) {
                        NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                        return;
                    }
                    if (!p.isSellingItem) {
                        NJUtil.sendServer(p.getSession(), p.name + " đã không còn đăng bán vật phẩm.");
                        return;
                    }
                    items = new Vector<>();
                    for (int i = 0; i < p.itemBags.length; ++i) {
                        if (p.itemBags[i] != null) {
                            items.add(p.itemBags[i]);
                        }
                    }
                    if (items.size() == 0) {
                        NJUtil.sendServer(p.getSession(), p.name + " đã hết hàng.");
                        p.isSellingItem = false;
                        playerIDBuyItem = -1;
                        return;
                    }
                    break;
                }
                case 15:
                    items = ServerController.shopBooks;
                    break;
                case 32:
                    items = ServerController.shopFashions;
                    break;
                case 34:
                    if (!GameServer.openGiaToc) {
                        items = new Vector<>();
                        break;
                    }
                    items = ServerController.shopGiatocs;
                    break;
                case 20:
                    items = ServerController.shopNonNams;
                    break;
                case 21:
                    items = ServerController.shopNonNus;
                    break;
                case 22:
                    items = ServerController.shopAoNams;
                    break;
                case 23:
                    items = ServerController.shopAoNus;
                    break;
                case 24:
                    items = ServerController.shopGangTayNams;
                    break;
                case 25:
                    items = ServerController.shopGangTayNus;
                    break;
                case 26:
                    items = ServerController.shopQuanNams;
                    break;
                case 27:
                    items = ServerController.shopQuanNus;
                    break;
                case 28:
                    items = ServerController.shopGiayNams;
                    break;
                case 29:
                    items = ServerController.shopGiayNus;
                    break;
                case 16:
                    items = ServerController.shopLiens;
                    break;
                case 17:
                    items = ServerController.shopNhans;
                    break;
                case 18:
                    items = ServerController.shopNgocBois;
                    break;
                case 19:
                    items = ServerController.shopPhus;
                    break;
                case 2:
                    items = ServerController.shopVuKhis;
                    break;
                case 6:
                    items = ServerController.shopStacks;
                    break;
                case 7:
                    items = ServerController.shopStackLocks;
                    break;
                case 8:
                    items = ServerController.shopGrocerys;
                    break;
                case 9:
                    items = ServerController.shopGroceryLocks;
                    break;
            }
            if (items != null) {
                message.writeByte(items.size());
                for (Item item : items) {
                    message.writeByte(item.indexUI);
                    message.writeShort(item.template.itemTemplateId);
                }
            } else {
                message.writeByte(0);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenUIBox() {
        try {
            Message message = new Message(Cmd.OPEN_UI_BOX);
            message.writeInt(getXuBox());
            message.writeByte(box);
            for (Item item : itemBoxs) {
                if (item != null) {
                    message.writeShort(item.template.itemTemplateId);
                    message.writeBoolean(item.isLock);
                    if (item.isTypeBody() || (item.isTypeGem() && conn.isVersion144())) {
                        message.writeByte(item.upgrade);
                    }
                    message.writeBoolean(item.expires != -1L);
                    message.writeShort(item.quantity);
                } else {
                    message.writeShort(-1);
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenUIPt() {
        try {
            Message message = new Message(Cmd.OPEN_UI_PT);
            message.writeByte(box);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOpenUIZone() {
        try {
            if (!isOkZone()) {
                autoDie("doOpenUIZone player");
                return;
            }
            if (getHp() <= 0) {
                Message message = new Message(Cmd.OPEN_UI_ZONE);
                message.writeByte(0);
                NJUtil.sendMessage(getSession(), message);
                return;
            }
            if (getSession().type_admin < ROLE_GM && (map.isDun || map.isDunClan || map.isDunPB || map.isDunVD || map.isArena() || map.getTemplateId() == 110 || map.getTemplateId() == 130 || map.getTemplateId() == 131 || map.getTemplateId() == 132 || map.getTemplateId() == 149)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_TELEPORT(getSession()));
                return;
            }
            Message message = new Message(Cmd.OPEN_UI_ZONE);
            Vector<Map> list;
            if (map.giatocchien != null) {
                list = map.giatocchien.maps;
                message.writeByte(3);
                for (Map value : list) {
                    if (value.template.equals(map.template)) {
                        message.writeByte(value.players.size());
                        message.writeByte(value.getPts().size());
                    }
                }
            } else if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                list = MixedArena.ALL_MAPS.get(map.template.mapTemplateId);
                message.writeByte(list.size());
                for (Map value : list) {
                    message.writeByte(value.players.size());
                    message.writeByte(value.getPts().size());
                }
            } else {
                MapTemplate temp = ServerController.mapTemplates.get(map.template.mapTemplateId);
                if (temp.checkFullZone()) {
                    temp.createNewZoneMap();
                }
                list = ServerController.ALL_MAP.get(map.template.mapTemplateId);
                message.writeByte(list.size());
                for (Map value : list) {
                    message.writeByte(value.players.size());
                    message.writeByte(value.getPts().size());
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doOutParty() {
        try {
            if (party != null) {
                party.out(this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPick(Message message) {
        try {
            long timeNow = System.currentTimeMillis();
            if (timeNow <= timePick) {
                return;
            }
            int itemMapId = message.readShort();
            Item item = findItemBag(572);
            for (int i = 0; i < map.itemMaps.size(); ++i) {
                ItemMap itemMap = map.itemMaps.get(i);
                if (itemMap.itemMapId == itemMapId) {
                    if (itemMap.owner != -1 && itemMap.owner != playerId) {
                        NJUtil.sendServer(getSession(), AlertFunction.ITEM_NOT_ME(getSession()));
                        return;
                    }
                    if (NJUtil.distance(itemMap.x, x) > ((item != null) ? 320 : 50) || NJUtil.distance(itemMap.y, y) > ((item != null) ? 320 : 50)) {
                        NJUtil.sendServer(getSession(), AlertFunction.ITEM_FAR(getSession()));
                        return;
                    }
                    if (itemMap.item.getTemplateId() == 236 && timeWait == 0) {
                        if (taskMain != null && taskMain.template.taskId == 26 && taskMain.index == 1) {
                            createWaitMap((byte) 5, itemMap);
                            sendShowWait(AlertFunction.WAIT_GET_ICE(getSession()));
                        } else {
                            NJUtil.sendServer(getSession(), AlertFunction.PICK_ITEM_TASK(getSession()));
                        }
                        return;
                    }
                    timePick = timeNow + 1000L;
                    doPickItemMap(itemMap);
                }
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doPickItemAuto(int id) {
        try {
            Item item = findItemBag(572);
            for (int i = 0; i < map.itemMaps.size(); ++i) {
                ItemMap itemMap = map.itemMaps.get(i);
                if (itemMap.itemMapId == id) {
                    if (itemMap.owner != -1 && itemMap.owner != playerId) {
                        NJUtil.sendServer(getSession(), AlertFunction.ITEM_NOT_ME(getSession()));
                        return;
                    }
                    if (NJUtil.distance(itemMap.x, x) > ((item != null) ? 320 : 50) || NJUtil.distance(itemMap.y, y) > ((item != null) ? 320 : 50)) {
                        NJUtil.sendServer(getSession(), AlertFunction.ITEM_FAR(getSession()));
                        return;
                    }
                    if (itemMap.item.getTemplateId() == 236 && timeWait == 0) {
                        if (taskMain != null && taskMain.template.taskId == 26 && taskMain.index == 1) {
                            createWaitMap((byte) 5, itemMap);
                            sendShowWait(AlertFunction.WAIT_GET_ICE(getSession()));
                        } else {
                            NJUtil.sendServer(getSession(), AlertFunction.PICK_ITEM_TASK(getSession()));
                        }
                        return;
                    }
                    doPickItemMap(itemMap);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPickItemMap(ItemMap itemMap) {
        try {
            if (itemMap.item.getTemplateId() == 347 && itemMapPick == null) {
                itemMapPick = itemMap;
                timeNhat = 10;
                sendShowWait("Đang hái nấm");
                return;
            }
            if (itemMapPick != null && timeNhat != 0) {
                return;
            }
            if (itemMap.item.template.type == 19) {
                long sum = (long) coin_lock + (long) itemMap.item.quantity;
                if (sum > maxCoin) {
                    return;
                }
                coin_lock += itemMap.item.quantity;
                pickItemMap(itemMap);
            } else if (itemMap.item.template.type == 25 || itemMap.item.template.type == 23) {
                if (taskMain == null || taskMain.template.taskId != 14 || taskMain.index != 1) {
                    if (taskMain != null && taskMain.template.taskId == 20 && taskMain.index == 1 && itemMap.item.getTemplateId() == 221) {
                        doTaskNext();
                    } else if (taskMain != null && taskMain.template.taskId == 23 && taskMain.index == 1 && itemMap.item.getTemplateId() == 232) {
                        doTaskNext();
                        sendMapTime(3);
                        ((Dun) map).timeEnd = System.currentTimeMillis() + 3000L;
                    } else if (taskMain != null && itemMap.item.getTemplateId() == 212 && (taskMain.template.taskId == 14 || taskMain.index == 1)) {
                        NJUtil.sendServer(getSession(), AlertFunction.PICK_ITEM_TASK(getSession()));
                        return;
                    }
                }
                if (itemMap.item.getTemplateId() == 238) {
                    if (countFreeBag() < 1) {
                        NJUtil.sendServer(getSession(), AlertFunction.BAG_FULL(getSession()));
                        return;
                    }
                    boolean isIce = false;
                    for (int j = 0; j < itemBags.length; ++j) {
                        if (itemBags[j] != null && itemBags[j].getTemplateId() == 237) {
                            isIce = true;
                            useItemUpToUp(itemBags[j]);
                            sendUseItemUpToUp(j, 1);
                            break;
                        }
                    }
                    if (!isIce) {
                        pickItemMap(itemMap);
                        updateFastHp(-1000);
                        checkDie(-1);
                        NJUtil.sendServer(getSession(), AlertFunction.HP_DOWN(getSession()));
                    } else {
                        checkPickTask(new Item(239, true, "doPickItemMap char"), true);
                        pickItemMap(itemMap);
                    }
                } else {
                    if (!checkPickTask(itemMap.item, true)) {
                        return;
                    }
                    pickItemMap(itemMap);
                }
            } else if (itemMap.item.template.type == 21) {
                pickItemMap(itemMap);
                updateFastHp(itemMap.item.quantity);
            } else {
                if (!addItemToBag(itemMap.item, true)) {
                    return;
                }
                pickItemMap(itemMap);
            }
            map.removeItemMap(itemMap);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPlayerInfo(Player p) {
        try {
            Player playerMap;
            Message message = new Message(Cmd.VIEW_INFO);
            message.writeInt(p.playerId);
            playerMap = p.getPlayerMainControl();
            message.writeUTF(playerMap.name);
            message.writeShort(playerMap.headId);
            message.writeByte(playerMap.gender);
            message.writeByte(playerMap.classId);
            message.writeByte(playerMap.pk);
            message.writeInt(playerMap.hp);
            message.writeInt(playerMap.hp_full);
            message.writeInt(playerMap.mp);
            message.writeInt(playerMap.mp_full);
            message.writeByte(playerMap.speed);
            message.writeShort(playerMap.resFire);
            message.writeShort(playerMap.resIce);
            message.writeShort(playerMap.resWind);
            message.writeInt(playerMap.dame_full);
            message.writeInt(playerMap.dameDown);
            message.writeShort(playerMap.exactly);
            message.writeShort(playerMap.miss);
            message.writeShort(playerMap.fatal);
            message.writeShort(playerMap.reactDame);
            message.writeShort(playerMap.sysUp);
            message.writeShort(playerMap.sysDown);
            message.writeByte(playerMap.level);
            message.writeShort(playerMap.pointUyDanh);
            if (playerMap.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMap.clan.name);
                message.writeByte(playerMap.clan.getType(name));
            }
            message.writeShort(playerMap.pointUyDanh);
            message.writeShort(playerMap.pointNon);
            message.writeShort(playerMap.pointAo);
            message.writeShort(playerMap.pointGangtay);
            message.writeShort(playerMap.pointQuan);
            message.writeShort(playerMap.pointGiay);
            message.writeShort(playerMap.pointVukhi);
            message.writeShort(playerMap.pointLien);
            message.writeShort(playerMap.pointNhan);
            message.writeShort(playerMap.pointNgocboi);
            message.writeShort(playerMap.pointPhu);
            message.writeByte(playerMap.countFinishDay);
            message.writeByte(playerMap.countLoopBoos);
            message.writeByte(playerMap.countPB);
            message.writeByte(playerMap.limitTiemnangso);
            message.writeByte(playerMap.limitKynangso);
            for (int i = 0; i < playerMap.itemBodys.length; ++i) {
                if (playerMap.itemBodys[i] != null) {
                    message.writeShort(playerMap.itemBodys[i].template.itemTemplateId);
                    message.writeByte(playerMap.itemBodys[i].upgrade);
                    message.writeByte(playerMap.itemBodys[i].sys);
                }
            }
            NJUtil.sendMessage(getSession(), message);
            message = new Message(Cmd.VIEW_INFO1);
            message.writeInt(pointTT);
            message.writeByte(playerMap.limitBanhPhongloi);
            message.writeByte(playerMap.limitBanhBangHoa);
            NJUtil.sendMessage(getSession(), message);
            if (p.itemBodys[11] != null && p.itemBodys[11].isMatNaThoiTrang()) {
                sendUpdateInfoMe();
                sendUpdateInfoMe2Char();
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPlayerInfo(Message message) {
        try {
            Player playerMap;
            Player p;
            try {
                String nameView = message.readUTF();
                if (map.template.mapTemplateId == 149) {
                    doPlayerInfoInDunArena(nameView);
                    return;
                }
                p = ServerController.hnPlayers.get(nameView);
            } catch (Exception e) {
                return;
            }
            if (p == null) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                return;
            }
            if (!p.equals(this)) {
                NJUtil.sendServer(p.getSession(), name + AlertFunction.VIEW_INFO(p.getSession()));
            }
            try {
                message.readByte(); // cam chat
            } catch (Exception e) {
            }
            /*if (playerIDBuyItem == -1) {
                //playerIDBuyItem = p.playerId;
                return;
            }*/
            message = new Message(Cmd.VIEW_INFO);
            message.writeInt(p.playerId);
            playerMap = p.getPlayerMainControl();
            message.writeUTF(playerMap.name);
            message.writeShort(playerMap.headId);
            message.writeByte(playerMap.gender);
            message.writeByte(playerMap.classId);
            message.writeByte(playerMap.pk);
            message.writeInt(playerMap.hp);
            message.writeInt(playerMap.hp_full);
            message.writeInt(playerMap.mp);
            message.writeInt(playerMap.mp_full);
            message.writeByte(playerMap.speed);
            message.writeShort(playerMap.resFire);
            message.writeShort(playerMap.resIce);
            message.writeShort(playerMap.resWind);
            message.writeInt(playerMap.dame_full);
            message.writeInt(playerMap.dameDown);
            message.writeShort(playerMap.exactly);
            message.writeShort(playerMap.miss);
            message.writeShort(playerMap.fatal);
            message.writeShort(playerMap.reactDame);
            message.writeShort(playerMap.sysUp);
            message.writeShort(playerMap.sysDown);
            message.writeByte(playerMap.level);
            message.writeShort(playerMap.pointUyDanh);
            if (playerMap.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMap.clan.name);
                message.writeByte(playerMap.clan.getType(name));
            }
            message.writeShort(playerMap.pointUyDanh);
            message.writeShort(playerMap.pointNon);
            message.writeShort(playerMap.pointAo);
            message.writeShort(playerMap.pointGangtay);
            message.writeShort(playerMap.pointQuan);
            message.writeShort(playerMap.pointGiay);
            message.writeShort(playerMap.pointVukhi);
            message.writeShort(playerMap.pointLien);
            message.writeShort(playerMap.pointNhan);
            message.writeShort(playerMap.pointNgocboi);
            message.writeShort(playerMap.pointPhu);
            message.writeByte(playerMap.countFinishDay);
            message.writeByte(playerMap.countLoopBoos);
            message.writeByte(playerMap.countPB);
            message.writeByte(playerMap.limitTiemnangso);
            message.writeByte(playerMap.limitKynangso);
            for (int i = 0; i < playerMap.itemBodys.length; ++i) {
                if (playerMap.itemBodys[i] != null) {
                    message.writeShort(playerMap.itemBodys[i].template.itemTemplateId);
                    message.writeByte(playerMap.itemBodys[i].upgrade);
                    message.writeByte(playerMap.itemBodys[i].sys);
                }
            }
            NJUtil.sendMessage(getSession(), message);
            message = new Message(Cmd.VIEW_INFO1);
            message.writeInt(pointTT);
            message.writeByte(playerMap.limitBanhPhongloi);
            message.writeByte(playerMap.limitBanhBangHoa);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPlayerInfoInDunArena(String name) {
        Player p;
        for (int i = 0; i < map.players.size(); ++i) {
            p = map.players.get(i);
            if (p.name.equals(name) && !p.isNhanban()) {
                doPlayerInfo(p);
                return;
            }
        }
    }

    public void doPleaseParty(Message message) {
        try {
            Player playerMap = null;
            try {
                playerMap = ServerController.hnPlayers.get(message.readUTF());
            } catch (Exception e) {
            }
            if (playerMap == null || playerMap.party == null || !playerMap.party.isTeamLeader(playerMap)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_PARTY(getSession()));
                doFindParty();
                return;
            }
            if (party != null && playerMap.party.equals(party)) {
                NJUtil.sendServer(getSession(), AlertFunction.YOU_IN_PARTY_THIS(getSession()));
                return;
            }
            if (party != null) {
                NJUtil.sendServer(getSession(), AlertFunction.YOU_IN_PARTY(getSession()));
                return;
            }
            if (playerMap.party.isLock) {
                NJUtil.sendServer(getSession(), AlertFunction.LEADER_LOCK_PARTY(getSession()));
                return;
            }
            if (playerMap.party.players.size() >= 6) {
                NJUtil.sendServer(getSession(), AlertFunction.PT_FULL(getSession()));
                return;
            }
            if ((playerMap.getTypePk() == PK_PHE1 && getTypePk() == PK_PHE2) || (playerMap.getTypePk() == PK_PHE2 && getTypePk() == PK_PHE1)) {
                NJUtil.sendServer(getSession(), AlertFunction.PHE_DIFFERENT2(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            int type = playerMap.party.setPleaseParty(playerId);
            if (type == 1) {
                NJUtil.sendServer(getSession(), AlertFunction.HAVE_PLEASE(getSession()));
                return;
            }
            if (type == -1) {
                NJUtil.sendServer(getSession(), AlertFunction.PT_FULL(getSession()));
                return;
            }
            message = new Message(Cmd.PLEASE_INPUT_PARTY);
            message.writeUTF(name);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPopUp(Message message) {
        try {
            int id = message.readByte();
            switch (id) {
                case 3:
                case 4:
                case 5:
                    tinhTu(id - 3);
                    break;
                case 8:
                    if (getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(getSession(), "Đã huỷ nhiệm vụ " + nguyetNhanTask.name);
                    } else {
                        NJUtil.sendServer(getSession(), "Mission " + nguyetNhanTask.name + " is canceled");
                    }
                    nguyetNhanTask = null;
                    break;
                case 9:
                    doUpdgradeNguyetNhan(true, false);
                    break;
                case 10:
                    doUpdgradeNguyetNhan(false, false);
                    break;
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doPotentialUp(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            int pIndex = message.readByte();
            int pPoint = message.readShort();
            if (playerMain.potential_point < pPoint || pPoint <= 0 || pIndex < 0 || pIndex > 3) {
                NJUtil.sendDialog(getSession(), AlertFunction.POINT_ERROR(getSession()));
                return;
            }
            switch (pIndex) {
                case 0: {
                    playerMain.p_strength += (short) pPoint;
                    break;
                }
                case 1: {
                    playerMain.p_agile += (short) pPoint;
                    break;
                }
                case 2: {
                    playerMain.p_hp += pPoint;
                    break;
                }
                case 3: {
                    playerMain.p_mp += pPoint;
                    break;
                }
            }
            playerMain.potential_point -= (short) pPoint;
            playerMain.updateData();
            if (status != ME_DIE) {
                playerMain.hp = playerMain.hp_full;
                playerMain.mp = playerMain.mp_full;
            }
            updatePotential();
            if (!playerMain.isNhanban() && taskMain != null && taskMain.template.taskId == 9 && taskMain.index == 2) {
                doTaskNext();
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doProccessInputText(Message message) {
        try {
            if (isNhanban() || isControlCharNhanBan()) {
                return;
            }
            short id;
            String str;
            try {
                id = message.readShort();
                str = message.readUTF().trim();
            } catch (Exception e3) {
                return;
            }
            int typeRoom = -1;
            try {
                typeRoom = message.readByte();
            } catch (Exception e) {
            }
            if (id == 100 && typeRoom == 1) {
                id = 103;
            }
            switch (id) {
                case 0: {
                    str = str.toLowerCase().trim();
                    if (clan != null || level < 10 || getLuong() < 50000) {
                        return;
                    }
                    if (level < 40) {
                        sendOpenUISay(0, AlertFunction.LEVEL_CLAN_ERROR(getSession()));
                        return;
                    }
                    if (!NJUtil.isAlphaNumeric(str) || str.length() < 5 || str.length() > 10) {
                        sendOpenUISay(0, AlertFunction.NAME_CLAN_ERROR1(getSession()));
                    } else if (Database.isHaveClan(str)) {
                        sendOpenUISay(0, AlertFunction.NAME_CLAN_ERROR2(getSession()));
                    } else {
                        if (isLock) {
                            doCancelTrade();
                            sendNotUnlock();
                            return;
                        }
                        clan = new Clan();
                        clan.name = str;
                        clan.main_name = name;
                        clan.reg_date = System.currentTimeMillis();
                        clan.coin = 5000000;
                        clan.openDun = 3;
                        clan.use_card = 4;
                        Member mem = new Member(4);
                        mem.classId = classId;
                        mem.level = level;
                        mem.name = name;
                        mem.isOnline = true;
                        clan.addMember(mem);
                        clan.writeLog(name, Clan.CREATE_CLAN, clan.coin);
                        try {
                            if (Database.createClan(clan)) {
                                savezaLog("Lap gia toc", String.valueOf(getLuong()));
                                subLuong(50000);
                                ServerController.clans.add(clan);
                                Message message2 = NJUtil.messageNotMap(Cmd.CREATE_CLAN);
                                message2.writeUTF(clan.name);
                                message2.writeInt(getLuong());
                                NJUtil.sendMessage(getSession(), message2);
                                sendOpenUISay(0, AlertFunction.SAY_CREATE_CLAN(getSession()));
                            }
                        } catch (Exception e) {
                        }
                    }
                    break;
                }
                case 1: {
                    if (getHp() <= 0) {
                        return;
                    }
                    Item item = findItemBag(279);
                    if (item == null) {
                        return;
                    }
                    if (getSession().type_admin < ROLE_GM && (map.isDun || map.isDunClan || map.isDunPB || map.isDunVA || map.template.isMapChienTruong() || map.template.typeMap == MapTemplate.MAP_PB || map.template.mapTemplateId == 130 || map.template.mapTemplateId == 131 || map.template.mapTemplateId == 132 || map.template.mapTemplateId == 149 || map.template.mapTemplateId >= 139)) {
                        NJUtil.sendServer(getSession(), AlertFunction.NOT_TELEPORT(getSession()));
                        return;
                    }
                    str = str.toLowerCase().trim();
                    Player p = ServerController.hnPlayers.get(str);
                    if (p == null) {
                        NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                        break;
                    }
                    Map mapNext = findMapFriend(p);
                    if (mapNext != null) {
                        if (mapNext.template.mapTemplateId == 130 || mapNext.template.mapTemplateId == 131 || mapNext.template.mapTemplateId == 132 || mapNext.template.mapTemplateId == 133 || mapNext.template.mapTemplateId >= 134) {
                            NJUtil.sendServer(getSession(), "Người chơi đang ở khu vực cấm dùng vạn biến lệnh.");
                            return;
                        }
                        mapClear();
                        map.goOutMap(this);
                        x = p.x;
                        if (p.map.isDunVD) {
                            y = p.map.template.defaultY;
                        } else {
                            y = p.y;
                        }
                        mapNext.waitGoInMap(this);
                    }
                    break;
                }
                case 2: {
                    if (testSkillDun != null) {
                        sendOpenUISay(0, AlertFunction.HAVE_TEST_DUN(getSession()));
                        return;
                    }
                    Player playerMap = map.getPlayer(str);
                    if (playerMap == null) {
                        sendOpenUISay(0, NJUtil.replace(AlertFunction.NOT_IN_HERE(getSession()), str));
                        return;
                    }
                    if (playerMap.equals(this)) {
                        sendOpenUISay(0, AlertFunction.TEST_DUN_ME(getSession()));
                        return;
                    }
                    if (playerMap.testSkillDun != null) {
                        sendOpenUISay(0, str + " " + AlertFunction.HAVE_TEST_DUN1(getSession()));
                        return;
                    }
                    if (party != null) {
                        if (!party.isTeamLeader(this)) {
                            sendOpenUISay(0, AlertFunction.ONLY_TEAM_LEADER1(getSession()));
                            return;
                        }
                        if (playerMap.party != null && playerMap.party.equals(party)) {
                            sendOpenUISay(0, AlertFunction.HEPL_TEST_DUN4(getSession()));
                            return;
                        }
                    }
                    if (playerMap.party != null && !playerMap.party.isTeamLeader(playerMap)) {
                        sendOpenUISay(0, AlertFunction.ONLY_TEAM_LEADER2(getSession()));
                        return;
                    }
                    testSkillDun = new TestSkill(60000);
                    testSkillDun.player1 = this;
                    testSkillDun.player2 = playerMap;
                    sendOpenUISay(0, AlertFunction.SEND_INVITE_TEST(getSession()) + " " + str);
                    message = new Message(Cmd.TEST_DUN_INVITE);
                    message.writeInt(playerId);
                    NJUtil.sendMessage(playerMap.getSession(), message);
                    break;
                }
                case 3:
                    try {
                        if (isLock) {
                            sendNotUnlock();
                            return;
                        }
                        String strNum = str.replaceAll("\\D+", "");
                        if (strNum.isEmpty()) {
                            return;
                        }
                        int money = Integer.parseInt(strNum);
                        if (money <= 0 || testSkillDun == null || map.getTemplateId() != 110 || (party != null && !party.isTeamLeader(this))) {
                            return;
                        }
                        if (money < 1000) {
                            sendOpenUISay(0, AlertFunction.HEPL_TEST_DUN8(getSession()));
                            return;
                        }
                        if (money > getXu()) {
                            sendOpenUISay(0, AlertFunction.HEPL_TEST_DUN9(getSession()));
                            return;
                        }
                        if (testSkillDun.player1.equals(this)) {
                            testSkillDun.money1 = money;
                        } else {
                            testSkillDun.money2 = money;
                        }
                        if (testSkillDun.money1 >= 1000 && testSkillDun.money2 >= 1000 && testSkillDun.money1 == testSkillDun.money2 && testSkillDun.player1.getXu() >= testSkillDun.money1 && testSkillDun.player2.getXu() >= testSkillDun.money2) {
                            map.sendAlert(new String[]{ Alert_VN.HEPL_TEST_DUN11, Alert_EN.HEPL_TEST_DUN11 });
                            DunVD dun = DunVD.createDun(111);
                            if (dun != null) {
                                dun.name1 = testSkillDun.player1.name + " (" + testSkillDun.player1.level + ")";
                                dun.name2 = testSkillDun.player2.name + " (" + testSkillDun.player2.level + ")";
                                if (testSkillDun.money1 >= 100000) {
                                    NJUtil.sendServerAlert(dun.name1 + AlertFunction.HEPL_TEST_DUN13(getSession()) + dun.name2 + " " + testSkillDun.money1 + " " + AlertFunction.COIN(getSession()) + AlertFunction.HEPL_TEST_DUN14(getSession()));
                                }
                                int time = 600;
                                int timeEff = GameServer.isServerLocal() ? 5000 : 30000;
                                dun.timeEnd = System.currentTimeMillis() + time * 1000;
                                dun.phe1 = new Vector<>();
                                dun.phe2 = new Vector<>();
                                dun.money = testSkillDun.money1 + testSkillDun.money2;
                                testSkillDun.player1.sendUpdateCoinBag(-testSkillDun.money1);
                                testSkillDun.player2.sendUpdateCoinBag(-testSkillDun.money2);
                                testSkillDun.player1.savezbLog("Dat cuoc", testSkillDun.player1.getXu() + "@" + testSkillDun.money1);
                                testSkillDun.player2.savezbLog("Dat cuoc", testSkillDun.player2.getXu() + "@" + testSkillDun.money2);
                                if (testSkillDun.player1.party != null) {
                                    for (int i = testSkillDun.player1.party.players.size() - 1; i >= 0; --i) {
                                        Player player = testSkillDun.player1.party.players.get(i);
                                        Effect eff = new Effect();
                                        eff.template = ServerController.effTemplates.get(14);
                                        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                        eff.timeLength = timeEff;
                                        player.addEffect(eff);
                                        dun.str1 = dun.str1.concat(player.name);
                                        if (i > 0) {
                                            dun.str1 = dun.str1.concat(", ");
                                        }
                                        dun.phe1.insertElementAt(player, 0);
                                        player.typePk = PK_PHE1;
                                        player.updateTypePk();
                                        testSkillDun.player1.party.players.get(i).goTestDun(dun, time, 160, 264);
                                    }
                                } else {
                                    Effect eff = new Effect();
                                    eff.template = ServerController.effTemplates.get(14);
                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                    eff.timeLength = timeEff;
                                    testSkillDun.player1.addEffect(eff);
                                    dun.str1 += testSkillDun.player1.name;
                                    dun.phe1.insertElementAt(testSkillDun.player1, 0);
                                    testSkillDun.player1.typePk = PK_PHE1;
                                    testSkillDun.player1.updateTypePk();
                                    testSkillDun.player1.goTestDun(dun, time, 160, 264);
                                }
                                if (testSkillDun.player2.party != null) {
                                    for (int i = testSkillDun.player2.party.players.size() - 1; i >= 0; --i) {
                                        Player player = testSkillDun.player2.party.players.get(i);
                                        Effect eff = new Effect();
                                        eff.template = ServerController.effTemplates.get(14);
                                        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                        eff.timeLength = timeEff;
                                        player.addEffect(eff);
                                        dun.str2 = dun.str2.concat(player.name);
                                        if (i > 0) {
                                            dun.str2 = dun.str2.concat(", ");
                                        }
                                        dun.phe2.insertElementAt(player, 0);
                                        player.typePk = PK_PHE2;
                                        player.updateTypePk();
                                        player.goTestDun(dun, time, 600, 264);
                                    }
                                } else {
                                    Effect eff = new Effect();
                                    eff.template = ServerController.effTemplates.get(14);
                                    eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                                    eff.timeLength = timeEff;
                                    testSkillDun.player2.addEffect(eff);
                                    dun.str2 += testSkillDun.player2.name;
                                    dun.phe2.insertElementAt(testSkillDun.player2, 0);
                                    testSkillDun.player2.typePk = PK_PHE2;
                                    testSkillDun.player2.updateTypePk();
                                    testSkillDun.player2.goTestDun(dun, time, 600, 264);
                                }
                            }
                        } else {
                            String[] strs = {
                                name + " " + NJUtil.replace(Alert_VN.HEPL_TEST_DUN10, String.valueOf(money)),
                                name + " " + NJUtil.replace(Alert_EN.HEPL_TEST_DUN10, String.valueOf(money))
                            };
                            map.sendAlert(strs);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case 4: // gioi thieu
                case 7: // giftcode
                    NJUtil.sendDialog(getSession(), AlertFunction.NOT_USE(getSession()));
                    break;
                case 5: {
                    if (clan == null || !clan.isMain(name) || testGTDun != null) {
                        return;
                    }
                    if (isGTChien()) {
                        sendOpenUISay(32, AlertFunction.GTC_1(getSession()));
                        return;
                    }
                    Player playerMap = map.getPlayer(str);
                    if (playerMap == null) {
                        sendOpenUISay(32, NJUtil.replace(AlertFunction.NOT_IN_HERE(getSession()), str));
                        return;
                    }
                    if (playerMap.equals(this)) {
                        sendOpenUISay(32, AlertFunction.TEST_DUN_ME(getSession()));
                        return;
                    }
                    if (playerMap.testGTDun != null) {
                        sendOpenUISay(32, str + " " + AlertFunction.HAVE_TEST_DUN1(getSession()));
                        return;
                    }
                    if (playerMap.clan == null || !playerMap.clan.isMain(playerMap.name)) {
                        sendOpenUISay(32, str + " " + AlertFunction.ALERT_THIDAUGT_2(getSession()));
                        return;
                    }
                    testGTDun = new TestSkill(60000);
                    testGTDun.player1 = this;
                    testGTDun.player2 = playerMap;
                    sendOpenUISay(32, AlertFunction.SEND_INVITE_TEST(getSession()) + " " + str);
                    message = new Message(Cmd.TEST_GT_INVITE);
                    message.writeInt(playerId);
                    NJUtil.sendMessage(playerMap.getSession(), message);
                    break;
                }
                case 6:
                    clanBet(str);
                    break;
                case 100: {
                    String strNum = str.replaceAll("\\D+", "");
                    if (!GameServer.openVongXoay) {
                        NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                        return;
                    }
                    if (getXu() < 11000000) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Số xu tối thiểu phải trên 11.000.000 mới có tư cách tham gia");
                        } else {
                            NJUtil.sendServer(getSession(), "You must have from 11.000.000 coins");
                        }
                        return;
                    }
                    int moneyLast = 0;
                    PlayerXoso pxs = ALL_PLAYER_XOSO.get(name.toLowerCase());
                    if (pxs != null) {
                        moneyLast = pxs.money;
                    }
                    if (getXu() >= 1000000000) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Số xu của bạn vượt quá mức quy định là 1.000.000.000 xu");
                        } else {
                            NJUtil.sendServer(getSession(), "You coins must have less than 1.000.000.000 coins");
                        }
                        return;
                    }
                    if ((System.currentTimeMillis() - timeXoso) / 1000L >= 110L || MONEY_XOSO >= 500000000L) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Đã khoá đặt cược");
                        } else {
                            NJUtil.sendServer(getSession(), "Can not add more coins now");
                        }
                        return;
                    }
                    if (strNum.isEmpty()) {
                        return;
                    }
                    int money = Integer.parseInt(strNum);
                    if (money < 1000000) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Số xu tối thiểu phải là 1.000.000 xu");
                        } else {
                            NJUtil.sendServer(getSession(), "Minimum coins must be 1.000.000 coins");
                        }
                        return;
                    }
                    if (money + moneyLast > 50000000) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Số xu tối đa có thể đặt là 50.000.000 xu");
                        } else {
                            NJUtil.sendServer(getSession(), "Maximum coins is 50.000.000 xu");
                        }
                        return;
                    }
                    if (getXu() < money) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Không đủ xu để đặt cược");
                        } else {
                            NJUtil.sendServer(getSession(), "Not enough coin");
                        }
                        return;
                    }
                    if (getXu() - money < 10000000) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(getSession(), "Số xu tối thiếu sau khi đặt phải còn lại ít nhất 10.000.000 xu");
                        } else {
                            NJUtil.sendServer(getSession(), "Your coins after join must be 10.000.000 coins");
                        }
                        return;
                    }
                    savezaLog("dat cuoc vip " + money, String.valueOf(getXu()));
                    Database.saveLogAll(name, "dat cuoc vong xoay may man vip " + money, "bidxoso");
                    subXu(money);
                    updateInfo();
                    MONEY_XOSO += money;
                    if (pxs == null) {
                        pxs = new PlayerXoso();
                        pxs.name = name.toLowerCase();
                        pxs.money = money;
                    } else {
                        pxs.money += money;
                    }
                    ALL_PLAYER_XOSO.put(name.toLowerCase(), pxs);
                    Database.doAddUpdatePlayerXoso(name, money);
                    Database.savePlayer(this, map.getTemplateId());
                    if (pxs.money > 0) {
                        NJUtil.sendServer(getSession(), "Đã đặt cược thêm " + NJUtil.getDotNumber(money) + " xu. Tổng số xu đã cược là " + NJUtil.getDotNumber(pxs.money) + "xu");
                    } else {
                        NJUtil.sendServer(getSession(), "Đã đặt cược " + NJUtil.getDotNumber(money) + " xu");
                    }
                    infoXoso();
                    StringBuilder log = new StringBuilder();
                    for (PlayerXoso p : ALL_PLAYER_XOSO.values()) {
                        log.append(p.money).append(", ");
                    }
                    LOGGER.info("Coin xo so: " + log);
                    break;
                }
                case 101:
                    if (!GameServer.openVongXoay) {
                        NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                        return;
                    }
                    if (typeRoom == 0) {
                        infoXoso();
                    }
                    break;
                case 102:
                    if (!GameServer.openVongXoay) {
                        NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                        return;
                    }
                    idActionNewMenu = 2;
                    message = new Message(Cmd.OPEN_UI_NEWMENU);
                    message.writeUTF("Thông tin");
                    message.writeUTF("Luật chơi");
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 112: {
                    Player pp = ServerController.hnPlayers.get(str);
                    if (pp == null) {
                        if (getSession().typeLanguage == GameServer.LANG_VI) {
                            sendOpenUISay(33, "Tên nhân vật không đúng");
                        } else {
                            sendOpenUISay(33, "Your character name is not correct");
                        }
                        return;
                    }
                    if (level < 40) {
                        sendOpenUISay(33, AlertFunction.NOT_LEVEL40(getSession()));
                        return;
                    }
                    if (getLuong() < 5) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_GOLD(getSession()));
                        return;
                    }
                    //doLixiFriend(pp);
                    subLuong(5);
                    updateGold();
                    savezaLog("lixi", "lixi cho " + pp.name);
                    break;
                }
                case 2400: {
                    int goldEx = 0;
                    try {
                        goldEx = Integer.parseInt(str.replaceAll("\\D+", ""));
                    } catch (Exception e) {
                    }
                    if (getLuong() < goldEx) {
                        NJUtil.sendDialog(getSession(), AlertFunction.NOT_GOLD(getSession()));
                        return;
                    }
                    if (goldEx >= 50 && goldEx <= 10000 && goldEx % 5 == 0) {
                        int taskRequire = 32;
                        if (taskFinish <= taskRequire) {
                            TaskTemplate task = ServerController.taskTemplates.get(taskRequire);
                            NJUtil.sendDialog(getSession(), "Phải hoàn thành " + task.name[getSession().typeLanguage] + " mới có thể đổi lượng sang xu.");
                            return;
                        }
                        int coin = goldEx * 10000;
                        long sum = (long) getXu() + (long) coin;
                        if (sum > Player.maxCoin) {
                            NJUtil.sendDialog(getSession(), AlertFunction.MAX_COIN(getSession()));
                            return;
                        }
                        subLuong(goldEx);
                        sendUpdateCoinBag(coin);
                        savezbLog("Doi luong xu", getLuong() + "@" + getXu());
                        updateGold();
                        Database.savePlayer(this, map.getTemplateId());
                    } else {
                        NJUtil.sendDialog(getSession(), "Số lượng đổi phải lớn hơn 50 và chia hết cho 5, tối đa 10000 lượng mỗi lần đổi.");
                    }
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestClanInfo() {
        try {
            if (clan == null) {
                return;
            }
            Message message = NJUtil.messageNotMap(Cmd.REQUEST_CLAN_INFO);
            message.writeUTF(clan.name);
            message.writeUTF(clan.main_name);
            message.writeUTF(clan.assist_name);
            message.writeShort(clan.members.size());
            message.writeByte(clan.openDun);
            message.writeByte(clan.level);
            message.writeInt(clan.exp);
            message.writeInt(clan.getUpgradeExp());
            message.writeInt(clan.coin);
            message.writeInt(clan.getFee());
            message.writeInt(clan.getUpgradeCoin());
            message.writeUTF(NJUtil.getDateTime(clan.reg_date));
            message.writeUTF(clan.alert);
            message.writeInt(clan.use_card);
            message.writeByte(clan.itemlevel);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestClanItem() {
        Message message;
        try {
            if (clan == null) {
                return;
            }
            message = NJUtil.messageNotMap(Cmd.REQUEST_CLAN_ITEM);
            message.writeByte(clan.items.size());
            for (int i = 0; i < clan.items.size(); ++i) {
                Item item = clan.items.get(i);
                message.writeShort(item.quantity);
                message.writeShort(item.template.itemTemplateId);
            }
            message.writeByte(clan.allThanThu.size());
            for (int i = 0; i < clan.allThanThu.size(); ++i) {
                ThanThu tt = clan.allThanThu.get(i);
                tt.writeInfo2Client(message, getSession());
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestEnemies() {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Message message = NJUtil.messageSubCommand(Cmd.REQUEST_ENEMIES);
            for (Friend f : friends) {
                if (f.type == 2) {
                    message.writeUTF(f.friendName);
                    if (ServerController.hnPlayers.get(f.friendName) != null) {
                        message.writeByte(3);
                    } else {
                        message.writeByte(2);
                    }
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestFriend() {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Message message = NJUtil.messageSubCommand(Cmd.REQUEST_FRIEND);
            for (Friend f : friends) {
                if (f.type == 0 || f.type == 1) {
                    message.writeUTF(f.friendName);
                    if (ServerController.hnPlayers.get(f.friendName) != null && f.type == 1) {
                        message.writeByte(3);
                    } else {
                        message.writeByte(f.type);
                    }
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestItem(Message message) {
        try {
            int typeUI = -1;
            try {
                typeUI = message.readByte();
            } catch (Exception e) {
            }
            if (typeUI == -1) {
                return;
            }
            if (typeUI == 4) {
                doOpenUIBox();
            } else {
                if (NJUtil.inArrayInt(new int[]{
                    Item.UI_WEAPON,
                    Item.UI_STACK,
                    Item.UI_STACK_LOCK,
                    Item.UI_GROCERY,
                    Item.UI_GROCERY_LOCK,
                    Item.UI_STORE,
                    Item.UI_BOOK,
                    Item.UI_LIEN,
                    Item.UI_NHAN,
                    Item.UI_NGOCBOI,
                    Item.UI_PHU,
                    Item.UI_NONNAM,
                    Item.UI_NONNU,
                    Item.UI_AONAM,
                    Item.UI_AONU,
                    Item.UI_GANGTAYNAM,
                    Item.UI_GANGTAYNU,
                    Item.UI_QUANNAM,
                    Item.UI_QUANNU,
                    Item.UI_GIAYNAM,
                    Item.UI_GIAYNU,
                    Item.UI_FASHION,
                    Item.UI_CLANSHOP
                }, typeUI)) {
                    doOpenShop(typeUI);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Username: {}, Charname: {}, Client type: {}", getSession().username, name, getSession().clientType, e);
        } finally {
            message.cleanup();
        }
    }

    public void doRequestItemInfoCharSell(int indexUI) {
        try {
            Player p;
            try {
                p = ServerController.hpPlayers.get(playerIDBuyItem);
            } catch (Exception e) {
                return;
            }
            if (p == null) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_ONLINE(getSession()));
                return;
            }
            if (!p.isSellingItem) {
                NJUtil.sendServer(p.getSession(), p.name + " đã không còn đăng bán vật phẩm.");
                return;
            }
            Item item = p.itemBags[indexUI];
            if (item != null) {
                Message message = new Message(Cmd.REQUEST_ITEM_INFO);
                message.writeByte(14);
                message.writeByte(indexUI);
                message.writeLong(item.expires);
                if (item.isTypeUIMe()) {
                    message.writeInt(item.saleCoinLock);
                } else if (item.isTypeUIShop() || item.isTypeUIShopLock() || item.isTypeUIStore() || item.isTypeUIBook() || item.isTypeUIFashion() || item.isTypeUIClanShop()) {
                    message.writeInt(item.buyCoin);
                    message.writeInt(item.buyCoinLock);
                    message.writeInt(item.buyGold);
                }
                if (item.isTypeBody() || item.isTypeMon() || (item.isTypeGem() && conn.isVersion144())) {
                    message.writeByte(item.sys);
                    Vector<ItemOption> options = item.getOptions();
                    if (options != null) {
                        for (ItemOption option : options) {
                            message.writeByte(option.optionTemplate.itemOptionTemplateId);
                            if (conn.isVersion145()) {
                                message.writeInt(option.param);
                            } else {
                                message.writeShort(option.param);
                            }
                        }
                    }
                } else if (item.getTemplateId() == 233 || (item.getTemplateId() == 234 | item.getTemplateId() == 235)) {
                    message.writeBytes(item.template.imgs[conn.typeHD - 1].datas.get(0));
                }
                NJUtil.sendMessage(conn, message);
            }
        } catch (Exception e) {
        }
    }

    public void doRequestItemPlayer(Message message) {
        try {
            Player playerMap;
            Player p = null;
            Item item = null;
            try {
                playerMap = ServerController.hpPlayers.get(message.readInt());
                p = playerMap.getPlayerMainControl();
                item = p.itemBodys[message.readByte()];
            } catch (Exception e) {
            }
            if (p == null || item == null) {
                return;
            }
            message = new Message(Cmd.REQUEST_ITEM_PLAYER);
            message.writeByte(item.indexUI);
            message.writeLong(item.expires);
            message.writeInt(item.saleCoinLock);
            message.writeByte(item.sys);
            if (item.options != null) {
                for (int i = 0; i < item.options.size(); ++i) {
                    message.writeByte(item.options.get(i).optionTemplate.itemOptionTemplateId);
                    if (conn.isVersion145()) {
                        message.writeInt(item.options.get(i).param);
                    } else {
                        message.writeShort(item.options.get(i).param);
                    }
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doRequestPlayer(Message message) {
        try {
            Vector<Player> players = new Vector<>();
            int size;
            try {
                size = message.readByte();
                for (int i = 0; i < size; ++i) {
                    Player p = null;
                    try {
                        p = ServerController.hpPlayers.get(message.readInt());
                    } catch (Exception e) {
                    }
                    if (p != null && p.map != null && p.map.equals(map)) {
                        players.add(p);
                    }
                }
            } catch (Exception e) {
                return;
            }
            message = new Message(Cmd.REQUEST_PLAYERS);
            message.writeByte(players.size());
            for (Player player : players) {
                message.writeInt(player.playerId);
                message.writeShort(player.x);
                message.writeShort(player.y);
                message.writeInt(player.hp);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doReturn(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            if (isLock) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            if (playerMain.map.isDunVD || playerMain.map.isArena() || (playerMain.map.template.mapTemplateId >= 134 && playerMain.map.template.mapTemplateId <= 138)) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_RETURN(getSession()));
                return;
            }
            if (status == ME_DIE) {
                String al = getAlertTime(timeHoiSinh);
                if (al != null) {
                    NJUtil.sendDialog(getSession(), al);
                    return;
                }
                int goldLive = 5;
                if (getLuong() >= goldLive) {
                    subLuong(goldLive);
                    goReturn();
                    savezbLog("Hoi sinh", getLuong() + "@" + goldLive);
                    timeHoiSinh = System.currentTimeMillis() + delayHoiSinh;
                } else {
                    NJUtil.sendDialog(getSession(), AlertFunction.NOT_ENOUGH_GOLD_RETURN(getSession()));
                }
            } else {
                goReturnHack();
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doRevemoFriend(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            String fname;
            try {
                fname = message.readUTF();
            } catch (Exception e) {
                return;
            }
            for (int i = 0; i < friends.size(); ++i) {
                Friend f = friends.get(i);
                if (f.friendName.equals(fname)) {
                    friends.remove(i);
                    if (f.type == 0 || f.type == 1) {
                        message = NJUtil.messageSubCommand(Cmd.FRIEND_REMOVE);
                    } else if (f.type == 2) {
                        message = NJUtil.messageSubCommand(Cmd.ENEMIES_REMOVE);
                    }
                    message.writeUTF(fname);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doSendDetailItem(Item item) {
        try {
            Message message = new Message(Cmd.REQUEST_ITEM_INFO);
            message.writeByte(item.typeUI);
            message.writeByte(item.indexUI);
            message.writeLong(item.expires);
            if (item.isTypeUIMe()) {
                message.writeInt(item.saleCoinLock);
            } else if (item.isTypeUIShop() || item.isTypeUIShopLock() || item.isTypeUIStore() || item.isTypeUIBook() || item.isTypeUIFashion() || item.isTypeUIClanShop()) {
                message.writeInt(item.buyCoin);
                message.writeInt(item.buyCoinLock);
                message.writeInt(item.buyGold);
            }
            if (item.isTypeBody() || item.isTypeMon() || (item.isTypeGem() && conn.isVersion144())) {
                message.writeByte(item.sys);
                Vector<ItemOption> options = item.getOptions();
                if (options != null) {
                    for (ItemOption option : options) {
                        message.writeByte(option.optionTemplate.itemOptionTemplateId);
                        if (conn.isVersion145()) {
                            message.writeInt(option.param);
                        } else {
                            message.writeShort(option.param);
                        }
                    }
                }
            } else if (item.getTemplateId() == 233 || (item.getTemplateId() == 234 | item.getTemplateId() == 235)) {
                message.writeBytes(item.template.imgs[conn.typeHD - 1].datas.get(0));
            }
            conn.sendMessage(message);
        } catch (Exception e) {
        }
    }

    public void doSendItemToSale(Message message) {
        try {
            if (!GameServer.openShinwa) {
                NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            int saleShop;
            Item item;
            try {
                item = itemBags[message.readByte()];
                saleShop = message.readInt();
                if (saleShop <= 0 || saleShop >= 300000000) {
                    return;
                }
            } catch (Exception e) {
                return;
            }
            if (item == null || item.isLock || item.expires != -1L || getXu() < 5000) {
                return;
            }
            savezbLog("Ban item gian hang", item.buyGold + "@" + 5000 + "@" + item.buyCoinLock + "@" + item.template.itemTemplateId + "@" + item.quantity + "@" + saleShop);
            subXu(5000);
            item.saleShop = saleShop;
            item.playerName = name;
            item.playerId = playerId;
            ServerController.addItemShops(item);
            removeItem(item.indexUI);
            message = new Message(Cmd.SEND_ITEM_TO_AUCTION);
            message.writeByte(item.indexUI);
            message.writeInt(getXu());
            NJUtil.sendMessage(getSession(), message);
            if (dailytask != null && dailytask.template.checkTask(this, 1)) {
                checkTaskOrder(dailytask, 1);
            }
            Database.savePlayer(this, map.getTemplateId());
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
            return;
        } finally {
            message.cleanup();
            Database.savePlayer(this, map.getTemplateId());
        }
        message.cleanup();
        Database.savePlayer(this, map.getTemplateId());
    }

    public void doSendTextBoxId(String str, int id) {
        try {
            Message message = new Message(Cmd.OPEN_TEXT_BOX_ID);
            message.writeUTF(str);
            message.writeShort(id);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doSkillSelect(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            short skillId;
            try {
                skillId = message.readShort();
            } catch (Exception e) {
                return;
            }
            playerMain.skillSelect(playerMain.getSkill(skillId), true);
        } catch (Exception e) {
        } finally {
            message.cleanup();
        }
    }

    public void doSkillUp(Message message) {
        try {
            if (isNhanban()) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            Skill skill = playerMain.getSkill(message.readShort());
            int sPoint = message.readByte();
            if (skill == null) {
                NJUtil.sendDialog(getSession(), AlertFunction.SKILL_NOT_LEARN(getSession()));
                return;
            }
            if (skill.isSkillPhanThan()) {
                NJUtil.sendDialog(getSession(), AlertFunction.sKILL_CAN_NOT_UP(getSession()));
                return;
            }
            if (playerMain.skill_point < sPoint || sPoint <= 0) {
                NJUtil.sendDialog(getSession(), AlertFunction.POINT_ERROR(getSession()));
                return;
            }
            if (skill.point + sPoint > skill.template.maxPoint) {
                NJUtil.sendDialog(getSession(), AlertFunction.POINT_MAX_ERROR(getSession()));
                return;
            }
            changeSkill(skill, sPoint);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public boolean checkDongBang(LiveObject attaker) {
        Skill skill = getSkill(57);
        if (skill != null) {
            if (NJUtil.random.nextInt(100) < skill.options.firstElement().param && System.currentTimeMillis() - attaker.getTimeResistDongBang() >= 0L) {
                if (attaker.isNpc()) {
                    try {
                        int timeIce = skill.options.get(1).param / 500;
                        if (skill.options.get(1).param % 500 != 0) {
                            ++timeIce;
                        }
                        Npc npc = (Npc) attaker;
                        npc.timeIce = timeIce;
                        Message message = new Message(Cmd.NPC_IS_ICE);
                        message.writeByte(npc.npcId);
                        message.writeBoolean(npc.timeIce > 0);
                        map.sendToPlayer(message);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                attaker.setTimeResistDongBang(3000);
                int timeIce = skill.options.get(1).param;
                Player p = (Player) attaker;
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(6);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = timeIce;
                p.addEffect(eff);
                return true;
            }
        }
        return false;
    }

    public void doSplit(Message message) {
        try {
            if (isLock) {
                sendNotUnlock();
                return;
            }
            if (idTrade > -1 || !isOk(6)) {
                return;
            }
            Item item;
            try {
                item = itemBags[message.readByte()];
            } catch (Exception e3) {
                return;
            }
            if (item.isTypeNguyetNhan()) {
                return;
            }
            if (item.isTypeBody() && item.upgrade > 0) {
                int count = 0;
                if (item.isTypeClothe()) {
                    for (int i = 0; i < item.upgrade; ++i) {
                        count += upClothe[i];
                    }
                } else if (item.isTypeAdorn()) {
                    for (int i = 0; i < item.upgrade; ++i) {
                        count += upAdorn[i];
                    }
                } else if (item.isTypeWeapon()) {
                    for (int i = 0; i < item.upgrade; ++i) {
                        count += upWeapon[i];
                    }
                }
                count /= 2;
                int index = 0;
                Vector<Item> items = new Vector<>();
                try {
                    for (index = crystals.length - 1; index >= 0; --index) {
                        if (count >= crystals[index]) {
                            Item itemNew = new Item(index, playerId, item.isLock, "char 16");
                            itemNew.typeUI = 3;
                            itemNew.expires = -1L;
                            count -= crystals[index];
                            items.add(itemNew);
                            ++index;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Username: {}, Charname: {}. Tach: {},{}", getSession().username, name, index, crystals.length, e);
                    return;
                }
                int countFree = 0;
                for (int j = 0; j < itemBags.length; ++j) {
                    if (itemBags[j] == null) {
                        if (countFree < items.size()) {
                            items.get(countFree).indexUI = j;
                        }
                        ++countFree;
                    }
                }
                if (items.size() > countFree) {
                    NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                    return;
                }
                changeItemOption(item, -item.upgrade);
                item.upgrade = 0;
                //Database.updateItem(item);
                for (Item element : items) {
                    addItem(element, 3, element.indexUI);
                }
                savezaLog("Tach trang bi @" + item.getTemplateId());
                message = new Message(Cmd.SPLIT);
                message.writeByte(items.size());
                for (Item value : items) {
                    message.writeByte(value.indexUI);
                    message.writeShort(value.template.itemTemplateId);
                }
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doSwitchBetweenMainCopy() {
        try {
            if (party != null) {
                party.out(this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        isInvisible = false;
        sendTestEnd();
        if (isMainchar) {
            if (System.currentTimeMillis() - playercopy.timeLiveCoppy < 0L && playercopy.timeLiveCoppy > 0L && status != ME_DIE) {
                if (playercopy.hp <= 0) {
                    playercopy.hp = 1;
                }
                playercopy.x = x;
                playercopy.y = y;
                map.waitGoInMap(playercopy);
            }
            try {
                if (status == ME_DIE) {
                    if (hp > 0) {
                        status = ME_NORMAL;
                    }
                    goReturn();
                }
                sendUpdateInfoMe();
                sendUpdateInfoMe2Char();
                reAddAllEff();
                changeNpcMe();
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
            sendTask();
        } else {
            map.playerOuts.add(playercopy);
            try {
                sendUpdateInfoMe();
                sendUpdateInfoMe2Char();
                reAddAllEff();
                changeNpcMe();
                sendTask();
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
        }
        if (taskLoopDay != null) {
            getTaskOrder(taskLoopDay);
        }
        if (taskLoopBoss != null) {
            getTaskOrder(taskLoopBoss);
        }
        loadThuCuoi(1);
        loadrms(keyRms[0][0]);
        loadrms(keyRms[0][1]);
        loadrms(keyRms[0][2]);
    }

    public void doTaskFinish(int playerTemplateId) {
        try {
            Message message = new Message(Cmd.TASK_FINISH);
            Item item = null;
            switch (getTaskFinish()) {
                case 0:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(200L, true);
                    sendUpdateCoinLockYen(10000);
                    break;
                case 1:
                    item = new Item(194, playerId, true, "char 17");
                    item.saleCoinLock = 500;
                    item.sys = 0;
                    (item.options = new Vector<>()).add(new ItemOption(NJUtil.randomNumber(20) + 1, 0));
                    item.options.add(new ItemOption(NJUtil.randomNumber(10) + 1, 8));
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(400L, true);
                    sendUpdateCoinLockYen(100);
                    break;
                case 2:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(800L, true);
                    sendUpdateCoinLockYen(200);
                    break;
                case 3:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(1500L, true);
                    sendUpdateCoinLockYen(300);
                    break;
                case 4:
                    item = new Item((gender == 1) ? 197 : 198, playerId, true, "char 18");
                    item.sys = NJUtil.randomNumber(3) + 1;
                    item = Item.getOptionThrow(item);
                    if (item.options == null || item.options.size() == 0) {
                        (item.options = new Vector<>()).add(new ItemOption(5, 6));
                    }
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    sendUpdateCoinLockYen(400);
                    break;
                case 5: {
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    item = new Item(13, true, "doTaskFinish char 1");
                    item.quantity = 5;
                    addItemToBagNoDialog(item);
                    Item item2 = new Item(18, true, "doTaskFinish char 2");
                    item2.quantity = 5;
                    addItemToBagNoDialog(item2);
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(4000L, true);
                    sendUpdateCoinLockYen(500);
                    break;
                }
                case 6:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(6000L, true);
                    sendUpdateCoinLockYen(600);
                    break;
                case 7:
                    item = new Item(205, playerId, true, "char 19");
                    item.sys = NJUtil.randomNumber(3) + 1;
                    item = Item.getOptionThrow(item);
                    if (item.options == null || item.options.size() == 0) {
                        (item.options = new Vector<>()).add(new ItemOption(10, 6));
                    }
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(8000L, true);
                    sendUpdateCoinLockYen(700);
                    break;
                case 8:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    item = new Item(37, true, "doTaskFinish char 3");
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    addItemToBagNoDialog(item);
                    item = new Item(225, true, "doTaskFinish char 4");
                    addItemToBagNoDialog(item);
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(24000L, true);
                    sendUpdateCoinLockYen(800);
                    break;
                case 9:
                    item = new Item(2, playerId, true, "char 20");
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(5000L, true);
                    sendUpdateCoinLockYen(900);
                    break;
                case 10:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(5000L, true);
                    sendUpdateCoinLockYen(1000);
                    break;
                case 11:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(1000L, true);
                    sendUpdateCoinLockYen(1000);
                    break;
                case 12:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    sendUpdateCoinLockYen(2000);
                    break;
                case 13:
                    if (classId == 1) {
                        item = new Item(41, playerId, true, "char 21");
                    } else if (classId == 2) {
                        item = new Item(50, playerId, true, "char 22");
                    } else if (classId == 3) {
                        item = new Item(59, playerId, true, "char 23");
                    } else if (classId == 4) {
                        item = new Item(68, playerId, true, "char 24");
                    } else if (classId == 5) {
                        item = new Item(77, playerId, true, "char 25");
                    } else if (classId == 6) {
                        item = new Item(86, playerId, true, "char 26");
                    }
                    if (item != null) {
                        item.sys = classId;
                        if (!addItemToBagNoDialog(item)) {
                            sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                            return;
                        }
                    }
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 14:
                    item = new Item(189, playerId, true, "char 27");
                    item.sys = 1;
                    item.options = Item.getOptionTemplate(item);
                    if (item.options != null && item.options.get(3) != null) {
                        item.options.remove(3);
                    }
                    item = item.cloneRandom();
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 15:
                    if (classId == 1) {
                        item = new Item(42, playerId, true, "char 28");
                    } else if (classId == 2) {
                        item = new Item(51, playerId, true, "char 29");
                    } else if (classId == 3) {
                        item = new Item(60, playerId, true, "char 30");
                    } else if (classId == 4) {
                        item = new Item(69, playerId, true, "char 31");
                    } else if (classId == 5) {
                        item = new Item(78, playerId, true, "char 32");
                    } else if (classId == 6) {
                        item = new Item(87, playerId, true, "char 33");
                    }
                    if (item != null) {
                        item.sys = classId;
                        if (!addItemToBagNoDialog(item)) {
                            sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                            return;
                        }
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 16:
                    item = new Item(215, playerId, true, "char 34");
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 17:
                    if (classId == 1) {
                        item = new Item(43, playerId, true, "char 35");
                    } else if (classId == 2) {
                        item = new Item(52, playerId, true, "char 36");
                    } else if (classId == 3) {
                        item = new Item(61, playerId, true, "char 37");
                    } else if (classId == 4) {
                        item = new Item(70, playerId, true, "char 38");
                    } else if (classId == 5) {
                        item = new Item(79, playerId, true, "char 39");
                    } else if (classId == 6) {
                        item = new Item(88, playerId, true, "char 40");
                    }
                    if (item != null) {
                        item.sys = classId;
                        if (!addItemToBagNoDialog(item)) {
                            sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                            return;
                        }
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 18:
                case 19:
                    item = new Item(4, playerId, true, "char 41");
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 20:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    if (classId == 1) {
                        item = new Item(44, playerId, true, "char 42");
                    } else if (classId == 2) {
                        item = new Item(53, playerId, true, "char 43");
                    } else if (classId == 3) {
                        item = new Item(62, playerId, true, "char 44");
                    } else if (classId == 4) {
                        item = new Item(71, playerId, true, "char 45");
                    } else if (classId == 5) {
                        item = new Item(80, playerId, true, "char 46");
                    } else if (classId == 6) {
                        item = new Item(89, playerId, true, "char 47");
                    }
                    if (item != null) {
                        item.sys = classId;
                        addItemToBagNoDialog(item);
                    }
                    item = new Item(223, true, "doTaskFinish char 5");
                    addItemToBagNoDialog(item);
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(3000L, true);
                    break;
                case 21:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(3000L, true);
                    sendUpdateCoinLockYen(3000);
                    break;
                case 22:
                    item = new Item(229, true, "doTaskFinish char 6");
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    sendUpdateCoinLockYen(2000);
                    break;
                case 23:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(5000L, true);
                    sendUpdateCoinLockYen(5000);
                    break;
                case 24:
                    if (classId == 1) {
                        item = new Item(45, playerId, true, "char 48");
                    } else if (classId == 2) {
                        item = new Item(54, playerId, true, "char 49");
                    } else if (classId == 3) {
                        item = new Item(63, playerId, true, "char 50");
                    } else if (classId == 4) {
                        item = new Item(72, playerId, true, "char 51");
                    } else if (classId == 5) {
                        item = new Item(81, playerId, true, "char 52");
                    } else if (classId == 6) {
                        item = new Item(90, playerId, true, "char 53");
                    }
                    if (item != null && !addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(2000L, true);
                    break;
                case 25:
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(6000L, true);
                    sendUpdateCoinLockYen(6000);
                    break;
                case 26:
                    item = new Item(4, playerId, true, "char 54");
                    if (!addItemToBagNoDialog(item)) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(4000L, true);
                    break;
                case 27:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    if (classId == 1) {
                        item = new Item(46, playerId, true, "char 55");
                    } else if (classId == 2) {
                        item = new Item(55, playerId, true, "char 56");
                    } else if (classId == 3) {
                        item = new Item(64, playerId, true, "char 57");
                    } else if (classId == 4) {
                        item = new Item(73, playerId, true, "char 58");
                    } else if (classId == 5) {
                        item = new Item(82, playerId, true, "char 59");
                    } else if (classId == 6) {
                        item = new Item(91, playerId, true, "char 60");
                    }
                    if (item != null) {
                        addItemToBagNoDialog(item);
                    }
                    addItemToBagNoDialog(new Item(224, true, "doTaskFinish char 7"));
                    NJUtil.sendMessage(getSession(), message);
                    sendUpdateExp(5000L, true);
                    break;
                case 28:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    addItemToBagNoDialog(new Item(4, true, "doTaskFinish char 8"));
                    addItemToBagNoDialog(new Item(4, true, "doTaskFinish char 9"));
                    sendUpdateCoinLockYen(10000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 29:
                    if (!addItemToBagNoDialog(new Item(257, true, "doTaskFinish char 10"))) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "1"));
                        return;
                    }
                    sendUpdateCoinLockYen(10000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 30:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    addItemToBagNoDialog(new Item(255, true, "doTaskFinish char 11"));
                    if (classId == 1) {
                        item = new Item(47, playerId, true, "char 61");
                    } else if (classId == 2) {
                        item = new Item(56, playerId, true, "char 62");
                    } else if (classId == 3) {
                        item = new Item(65, playerId, true, "char 63");
                    } else if (classId == 4) {
                        item = new Item(74, playerId, true, "char 64");
                    } else if (classId == 5) {
                        item = new Item(83, playerId, true, "char 65");
                    } else if (classId == 6) {
                        item = new Item(92, playerId, true, "char 66");
                    }
                    if (item != null) {
                        addItemToBagNoDialog(item);
                    }
                    sendUpdateCoinLockYen(10000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 31:
                    if (countFreeBag() < 2) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), "2"));
                        return;
                    }
                    if (gender == 1) {
                        item = new Item(258, true, "doTaskFinish char 12");
                    } else if (gender == 0) {
                        item = new Item(259, true, "doTaskFinish char 13");
                    }
                    if (item != null) {
                        item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(3);
                        addItemToBagNoDialog(item);
                    }
                    sendUpdateCoinBag(50000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 32: {
                    Vector<Integer> idBalls = getDragonball(4);
                    if (countFreeBag() < idBalls.size() + 1) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), String.valueOf(idBalls.size() + 1)));
                        return;
                    }
                    item = new Item(246, true, "doTaskFinish char 14");
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    addItemToBagNoDialog(item);
                    for (Integer idBall : idBalls) {
                        addItemToBagNoDialog(new Item(idBall, true, "doTaskFinish char 15"));
                    }
                    sendUpdateCoinLockYen(20000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                }
                case 33:
                    sendUpdateExp(100000000L, true);
                    sendUpdateCoinLockYen(50000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 34:
                    sendUpdateExp(200000000L, true);
                    sendUpdateCoinLockYen(70000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 35:
                    sendUpdateExp(300000000L, true);
                    sendUpdateCoinLockYen(90000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 36:
                    sendUpdateExp(400000000L, true);
                    sendUpdateCoinLockYen(120000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 37:
                    sendUpdateExp(500000000L, true);
                    sendUpdateCoinLockYen(150000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 38:
                    sendUpdateExp(600000000L, true);
                    sendUpdateCoinLockYen(190000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 39:
                    sendUpdateExp(700000000L, true);
                    sendUpdateCoinLockYen(230000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 40:
                    sendUpdateExp(800000000L, true);
                    sendUpdateCoinLockYen(280000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 41:
                    sendUpdateExp(900000000L, true);
                    sendUpdateCoinLockYen(350000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                case 42: {
                    Vector<Integer> idBalls = getDragonball(6);
                    if (countFreeBag() < idBalls.size() + 1) {
                        sendOpenUISay(playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(getSession()), String.valueOf(idBalls.size() + 1)));
                        return;
                    }
                    for (Integer idBall : idBalls) {
                        addItemToBagNoDialog(new Item(idBall, true, "doTaskFinish char 16"));
                    }
                    sendUpdateExp(1000000000L, true);
                    sendUpdateCoinLockYen(450000);
                    NJUtil.sendMessage(getSession(), message);
                    break;
                }
            }
            sendOpenUISay(playerTemplateId, taskMain.template.des[getSession().typeLanguage][taskMain.template.des[getSession().typeLanguage].length - 1]);
            clearTask();
            ++taskFinish;
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doTaskNext() {
        try {
            Player playerMain = getPlayerMainControl();
            ++playerMain.taskMain.index;
            playerMain.taskMain.count = 0;
            Message message = new Message(Cmd.TASK_NEXT);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doTaskUpdate(short count) {
        try {
            if (isNhanban() || isControlCharNhanBan()) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            playerMain.taskMain.count = count;
            Message message = new Message(Cmd.TASK_UPDATE);
            message.writeShort(playerMain.taskMain.count);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doTestDunAccept(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap = map.getPlayer(message.readInt());
            if (playerMap == null || playerMap.testSkillDun == null || playerMap.testSkillDun.player1 == null || playerMap.testSkillDun.player2 == null || !playerMap.testSkillDun.player2.equals(this) || (playerMap.party != null && !playerMap.party.isTeamLeader(playerMap))) {
                NJUtil.sendServer(getSession(), AlertFunction.TEST_DUN_CLEAR(getSession()));
                return;
            }
            if (party != null && playerMap.party != null && playerMap.party.equals(party)) {
                NJUtil.sendServer(getSession(), AlertFunction.HEPL_TEST_DUN4(getSession()));
                return;
            }
            Dun dun = Dun.createDun(110);
            if (dun == null) {
                NJUtil.sendServer(getSession(), AlertFunction.DUN_110_CLOSE(getSession()));
                return;
            }
            try {
                testSkillDun = playerMap.testSkillDun;
                int time = 300;
                dun.playerOpen = testSkillDun.player1;
                dun.timeEnd = System.currentTimeMillis() + time * 1000;
                if (testSkillDun.player1.party == null) {
                    testSkillDun.player1.goTestDun(dun, time, 200, 264);
                } else {
                    for (int i = 0; i < testSkillDun.player1.party.players.size(); ++i) {
                        testSkillDun.player1.party.players.get(i).goTestDun(dun, time, 200, 264);
                    }
                }
                if (testSkillDun.player2.party == null) {
                    testSkillDun.player2.goTestDun(dun, time, 350, 264);
                } else {
                    for (int i = 0; i < testSkillDun.player2.party.players.size(); ++i) {
                        testSkillDun.player2.party.players.get(i).goTestDun(dun, time, 350, 264);
                    }
                }
            } catch (Exception e) {
                dun.isStop = true;
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doTestGTAccept(Message message) {
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Player playerMap = map.getPlayer(message.readInt());
            if (playerMap == null || playerMap.testGTDun == null || playerMap.testGTDun.player1 == null || playerMap.testGTDun.player2 == null || !playerMap.testGTDun.player2.equals(this)) {
                NJUtil.sendServer(getSession(), AlertFunction.TEST_DUN_CLEAR(getSession()));
                return;
            }
            Dun dun = Dun.createDun(117);
            if (dun == null) {
                NJUtil.sendServer(getSession(), AlertFunction.ALERT_THIDAUGT_3(getSession()));
                return;
            }
            if (isGTChien()) {
                sendOpenUISay(32, AlertFunction.GTC_1(getSession()));
                return;
            }
            try {
                testGTDun = playerMap.testGTDun;
                int time = 300;
                dun.playerOpen = testGTDun.player1;
                dun.timeEnd = System.currentTimeMillis() + time * 1000;
                testGTDun.player1.goTestDun(dun, time, 300, 456);
                testGTDun.player2.goTestDun(dun, time, 460, 456);
            } catch (Exception e) {
                dun.isStop = true;
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doTestInvite(Message message) {
        try {
            if (getTypePk() != PK_NORMAL) {
                NJUtil.sendDialog(getSession(), AlertFunction.STATUS_ATT(getSession()));
                return;
            }
            Player playerMap = map.getPlayer(message.readInt());
            if (playerMap.isNhanban() || (isControlCharNhanBan() && !playerMap.isControlCharNhanBan()) || (!isControlCharNhanBan() && playerMap.isControlCharNhanBan())) {
                return;
            }
            if (playerMap.status == ME_DIE) {
                return;
            }
            if ((killPlayer != null && killPlayer.player.equals(playerMap)) || (playerMap.killPlayer != null && playerMap.killPlayer.player.equals(this))) {
                return;
            }
            if (testSkill != null) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_TEST(getSession()));
                return;
            }
            if (playerMap.testSkill != null) {
                NJUtil.sendServer(getSession(), AlertFunction.HAVE_TEST(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            testSkill = new TestSkill(20000);
            testSkill.player2 = playerMap;
            message = new Message(Cmd.TEST_INVITE);
            message.writeInt(playerId);
            NJUtil.sendMessage(playerMap.getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doTestInviteAccept(Message message) {
        try {
            Player playerMap = map.getPlayer(message.readInt());
            if (playerMap == null || playerMap.testSkill == null || !playerMap.testSkill.player2.equals(this)) {
                return;
            }
            if (testSkill != null) {
                NJUtil.sendServer(getSession(), AlertFunction.ERROR_TEST(getSession()));
                return;
            }
            if (isNotEditParty()) {
                return;
            }
            checkClearEffect11();
            checkClearEffect12();
            playerMap.checkClearEffect11();
            playerMap.checkClearEffect12();
            playerMap.testSkill.timeEnd = System.currentTimeMillis() + 100000L;
            playerMap.testSkill.player1 = playerMap;
            testSkill = playerMap.testSkill;
            message = new Message(Cmd.TEST_INVITE_ACCEPT);
            message.writeInt(playerId);
            message.writeInt(playerMap.playerId);
            sendToPlayer(message, true);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doThachDauArena(Message msg) {
        try {
            if (capcha != null) {
                return;
            }
            byte type = msg.readByte();
            String name = msg.readUTF().toLowerCase();
            if (type == 0) {
                Player p = ServerController.hnPlayers.get(name);
                if (p == null) {
                    p = Database.getPlayer(PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(name).name);
                    p.calculatorOptionCharOffline(p);
                } else {
                    if (p.capcha != null) {
                        return;
                    }
                    doPlayerInfo(p);
                }
            } else if (type == 1) {
                if (name.equals(this.name)) {
                    return;
                }
                doStartArena(name, this);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doThrow(Message message) {
        try {
            if (isLock) {
                doCancelTrade();
                sendNotUnlock();
                return;
            }
            if (idTrade > -1) {
                doCancelTrade();
            }
            Item item = itemBags[message.readByte()];
            if (item != null && !item.isLock) {
                throwItem(item);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doTinhLuyen(Message message) {
        if (!GameServer.openTinhLuyen) {
            NJUtil.sendDialog(getSession(), AlertFunction.FEATURE_NOT_OPEN(getSession()));
            return;
        }
        if (isControlCharNhanBan()) {
            return;
        }
        try {
            Item it1 = null;
            Item it2 = null;
            Item it3 = null;
            Item it4 = null;
            Item it5 = null;
            Item it6 = null;
            Item it7 = null;
            Item it8 = null;
            Item it9 = null;
            Item it10 = null;
            Item it11 = null;
            ItemOption itemOption = null;
            try {
                int[] a = new int[11];
                for (int i = 0; i < 11; ++i) {
                    a[i] = -1;
                }
                for (int i = 0; i < 11; ++i) {
                    try {
                        a[i] = message.readByte();
                    } catch (Exception e) {
                    }
                }
                for (int i = 0; i < 10; ++i) {
                    for (int j = i + 1; j < 11; ++j) {
                        if (a[i] > -1 && a[j] > -1 && a[i] == a[j]) {
                            ++countHackNgoc;
                            if (countHackNgoc >= 3) {
                                doCancelTrade1();
                                Database.saveLogAll(name, "hack kham nggoc", "hack");
                                getSession().disconnect("hack kham ngoc");
                            }
                            return;
                        }
                    }
                }
                if (a[0] > -1) {
                    it1 = itemBags[a[0]];
                }
                if (a[1] > -1) {
                    it2 = itemBags[a[1]];
                }
                if (a[2] > -1) {
                    it3 = itemBags[a[2]];
                }
                if (a[3] > -1) {
                    it4 = itemBags[a[3]];
                }
                if (a[4] > -1) {
                    it5 = itemBags[a[4]];
                }
                if (a[5] > -1) {
                    it6 = itemBags[a[5]];
                }
                if (a[6] > -1) {
                    it7 = itemBags[a[6]];
                }
                if (a[7] > -1) {
                    it8 = itemBags[a[7]];
                }
                if (a[8] > -1) {
                    it9 = itemBags[a[8]];
                }
                if (a[9] > -1) {
                    it10 = itemBags[a[9]];
                }
                if (a[10] > -1) {
                    it11 = itemBags[a[10]];
                }
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
            try {
                if (it1 != null) {
                    for (int k = 0; k < it1.options.size(); ++k) {
                        if (it1.options.get(k).optionTemplate.itemOptionTemplateId == 85) {
                            itemOption = it1.options.get(k);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
            if (itemOption == null) {
                doCancelTrade1();
                NJUtil.sendDialog(getSession(), "Không thể tinh luyện vật phẩm này.");
                return;
            }
            if (itemOption.param >= 9) {
                doCancelTrade1();
                NJUtil.sendDialog(getSession(), "Vật phẩm đã được tinh luyện tối đa.");
                return;
            }
            long sum = (long) getYen() + (long) getXu();
            if (sum < Item.yenTinhLuyen[itemOption.param]) {
                doCancelTrade1();
                NJUtil.sendDialog(getSession(), "Không đủ yên để tinh luyện.");
                return;
            }
            try {
                switch (itemOption.param) {
                    case 0:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_SO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 1:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_SO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 2:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it7 != null && it7.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it8 != null && it8.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it9 != null && it9.getTemplateId() == Item.TU_TINH_THACH_SO &&
                            it10 != null && it10.getTemplateId() == Item.TU_TINH_THACH_SO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            removeItem(it7, 3);
                            removeItem(it8, 3);
                            removeItem(it9, 3);
                            removeItem(it10, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            sendClearItemBag(it7.indexUI);
                            sendClearItemBag(it8.indexUI);
                            sendClearItemBag(it9.indexUI);
                            sendClearItemBag(it10.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 3:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_TRUNG
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 4:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it7 != null && it7.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it8 != null && it8.getTemplateId() == Item.TU_TINH_THACH_TRUNG
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            removeItem(it7, 3);
                            removeItem(it8, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            sendClearItemBag(it7.indexUI);
                            sendClearItemBag(it8.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 5:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it7 != null && it7.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it8 != null && it8.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it9 != null && it9.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it10 != null && it10.getTemplateId() == Item.TU_TINH_THACH_TRUNG &&
                            it11 != null && it11.getTemplateId() == Item.TU_TINH_THACH_TRUNG
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            removeItem(it7, 3);
                            removeItem(it8, 3);
                            removeItem(it9, 3);
                            removeItem(it10, 3);
                            removeItem(it11, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            sendClearItemBag(it7.indexUI);
                            sendClearItemBag(it8.indexUI);
                            sendClearItemBag(it9.indexUI);
                            sendClearItemBag(it10.indexUI);
                            sendClearItemBag(it11.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 6:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_CAO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 7:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it7 != null && it7.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it8 != null && it8.getTemplateId() == Item.TU_TINH_THACH_CAO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            removeItem(it7, 3);
                            removeItem(it8, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            sendClearItemBag(it7.indexUI);
                            sendClearItemBag(it8.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                    case 8:
                        if (it2 != null && it2.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it3 != null && it3.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it4 != null && it4.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it5 != null && it5.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it6 != null && it6.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it7 != null && it7.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it8 != null && it8.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it9 != null && it9.getTemplateId() == Item.TU_TINH_THACH_CAO &&
                            it10 != null && it10.getTemplateId() == Item.TU_TINH_THACH_CAO
                        ) {
                            doCancelTrade1();
                            removeItem(it2, 3);
                            removeItem(it3, 3);
                            removeItem(it4, 3);
                            removeItem(it5, 3);
                            removeItem(it6, 3);
                            removeItem(it7, 3);
                            removeItem(it8, 3);
                            removeItem(it9, 3);
                            removeItem(it10, 3);
                            sendClearItemBag(it2.indexUI);
                            sendClearItemBag(it3.indexUI);
                            sendClearItemBag(it4.indexUI);
                            sendClearItemBag(it5.indexUI);
                            sendClearItemBag(it6.indexUI);
                            sendClearItemBag(it7.indexUI);
                            sendClearItemBag(it8.indexUI);
                            sendClearItemBag(it9.indexUI);
                            sendClearItemBag(it10.indexUI);
                            isUp(it1, itemOption);
                            return;
                        }
                        break;
                }
            } catch (Exception e) {
            }
            NJUtil.sendDialog(getSession(), "Không đủ điều kiện để tinh luyện vật phẩm.");
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doUpdgradeNguyetNhan(boolean isvip, boolean isConfirm) {
        try {
            if (countFreeBag() < 1) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Con phải có 1 ô trống trong hành trang");
                } else {
                    NJUtil.sendServer(getSession(), "You must have 1 free slot in your bag");
                }
                return;
            }
            if (itemBodys[14] == null) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Hãy sử dụng Nguyệt Nhãn để sử dụng được chức năng này.");
                } else {
                    NJUtil.sendServer(getSession(), "Please using Nguyet Nhan.");
                }
                return;
            }
            if (itemBodys[14].template.idTemplateUp == -1) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Nguyệt nhãn đã đạt cấp tối đa.");
                } else {
                    NJUtil.sendServer(getSession(), "Can not updgrade.");
                }
                return;
            }
            if (itemBodys[14].upgrade == 0) {
                itemBodys[14].upgrade = 1;
            }
            int money = NguyetNhanTask.MONEY_UPDGRADE_NGUYET_NHAN[itemBodys[14].upgrade];
            int moneyluong = NguyetNhanTask.MONEY_UPDGRADE_NGUYET_NHAN_LUONG[itemBodys[14].upgrade];
            int pc = NguyetNhanTask.PC_UPDGRADE_NGUYET_NHAN[itemBodys[14].upgrade];
            if (isvip) {
                pc *= 2;
            }
            if (isConfirm) {
                Message message = new Message(Cmd.OPEN_UI_CONFIRM_POPUP);
                message.writeByte(isvip ? 9 : 10);
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    String infomoney = money + " yên hoặc xu";
                    if (isvip) {
                        infomoney = infomoney + " và " + moneyluong + " lượng";
                    }
                    message.writeUTF("Bạn có muốn nâng cấp " + itemBodys[14].template.getName(getSession()) + " với " + infomoney + " với tỷ lệ thành công là " + pc + "% không?");
                } else {
                    String infomoney = money + " coins or coins lock";
                    if (isvip) {
                        infomoney = infomoney + " and " + moneyluong + " gold";
                    }
                    message.writeUTF("Do you want to upgrade " + itemBodys[14].template.getName(getSession()) + " with " + infomoney + " and rate success: " + pc + "% ?");
                }
                NJUtil.sendMessage(getSession(), message);
                return;
            }
            int yen = getYen();
            int xu = getXu();
            if (yen < money && xu < money) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Không đủ " + money + " yên hoặc xu.");
                } else {
                    NJUtil.sendServer(getSession(), "Not enought money.");
                }
                return;
            }
            if (isvip && getLuong() < moneyluong) {
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(getSession(), "Không đủ " + moneyluong + " lượng");
                } else {
                    NJUtil.sendServer(getSession(), "Not enought money.");
                }
                return;
            }
            int[] point = {
                pointNon,
                pointVukhi,
                pointAo,
                pointLien,
                pointGangtay,
                pointNhan,
                pointQuan,
                pointNgocboi,
                pointGiay,
                pointPhu
            };
            for (int i = 0; i < point.length; ++i) {
                if (point[i] < NguyetNhanTask.POINT_DANH_VONG_UPDGRADE[itemBodys[14].upgrade]) {
                    NJUtil.sendServer(getSession(), "Không đủ " + NguyetNhanTask.POINT_DANH_VONG_UPDGRADE[itemBodys[14].upgrade] + " điểm " + NAME_DANH_VONG[getSession().typeLanguage][i]);
                    return;
                }
            }
            Item dadvLock = findItemBag(NguyetNhanTask.ID_DA_DANH_VONG[itemBodys[14].upgrade], true, 10);
            Item dadv = findItemBag(NguyetNhanTask.ID_DA_DANH_VONG[itemBodys[14].upgrade], false, 10);
            if (dadv == null && dadvLock == null) {
                NJUtil.sendServer(getSession(), "Không đủ 10 " + ServerController.itemTemplates.get(NguyetNhanTask.ID_DA_DANH_VONG[itemBodys[14].upgrade]).name);
                return;
            }
            String infomoney2 = "";
            if (yen >= money) {
                subYen(money);
                infomoney2 = infomoney2 + money + " yên, ";
            } else {
                subXu(money);
                infomoney2 = infomoney2 + money + " xu, ";
            }
            if (isvip) {
                subLuong(moneyluong);
                infomoney2 = infomoney2 + money + " lượng, ";
            }
            if (dadvLock != null && dadvLock.quantity >= 10) {
                useItemUpToUp(dadvLock, 10);
                sendUseItemUpToUp(dadvLock.indexUI, 10);
            } else if (dadv != null && dadv.quantity >= 10) {
                useItemUpToUp(dadv, 10);
                sendUseItemUpToUp(dadv.indexUI, 10);
            }
            if (NJUtil.random.nextInt(100) < pc) {
                Item item = new Item(itemBodys[14].template.idTemplateUp, true, "doUpdgradeNguyetNhan");
                item.createOptionNguyetNhan();
                Database.saveLogAll(name, itemBodys[14].template.name + " len " + item.template.name + " su dung " + infomoney2, "ncnn");
                savezaLog("nang cap nguyet nhan", "nang cap thanh cong " + itemBodys[14].template.name + " len cap " + item.template.name + " su dung " + infomoney2);
                itemBodys[14] = null;
                addItemToBagNoDialog(item);
                updateData();
                NJUtil.sendServer(getSession(), "Nâng cấp thành công");
            } else {
                Item item = new Item(itemBodys[14].template.idTemplateUp, true, "doUpdgradeNguyetNhan");
                NJUtil.sendServer(getSession(), "Nâng cấp thất bại");
                Database.saveLogAll(name, itemBodys[14].template.name + " len " + item.template.name + " su dung " + infomoney2, "ncnnfail");
                savezaLog("nang cap nguyet nhan", "nang cap that bai " + itemBodys[14].template.name + " su dung " + infomoney2);
            }
            updateInfo();
            doPlayerInfo(this);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doUpgrade(Message message) {
        try {
            if (isControlCharNhanBan() || !isOk(6)) {
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            LocalTime timeNow = LocalTime.now().minusMinutes(5);
            LocalTime timeBaoTri = GameServer.timeMaintenance;
            int hour = timeNow.getHour();
            int minute = timeNow.getMinute();
            if (hour == timeBaoTri.getHour() && (timeBaoTri.getMinute() - minute <= 5)) {
                doCancelTrade();
                return;
            }
            if (!isOk(6)) {
                return;
            }
            Item itemBaoHiem = null;
            boolean isGold;
            Item item;
            int up;
            try {
                isGold = message.readBoolean();
                item = itemBags[message.readByte()];
                up = item.upgrade + 1;
            } catch (Exception e3) {
                return;
            }
            int timeSec = (int) (System.currentTimeMillis() / 1000L);
            if (!GameServer.isServerLocal() && timeSec - timeUp <= 1) {
                NJUtil.sendDialog(getSession(), AlertFunction.FAST(getSession()));
                return;
            }
            timeUp = timeSec;
            if (item.isTypeBody() && item.template.level >= 10) {
                Vector<Item> items = new Vector<>();
                try {
                    int[] input = new int[18];
                    Arrays.fill(input, -1);
                    for (int i = 0; i < 18; ++i) {
                        try {
                            input[i] = message.readByte();
                        } catch (Exception e) {
                        }
                    }
                    for (int i = 0; i < 17; ++i) {
                        for (int j = i + 1; j < 18; ++j) {
                            if (input[i] > -1 && input[j] > -1 && input[i] == input[j]) {
                                ++countHackNgoc;
                                if (countHackNgoc >= 3) {
                                    doCancelTrade1();
                                    Database.saveLogAll(name, "hack cuong hoa", "hack");
                                    getSession().disconnect("hack cuong hoa");
                                }
                                return;
                            }
                        }
                    }
                    for (int i = 0; i < 18; ++i) {
                        int indexUI = input[i];
                        if (indexUI > -1) {
                            if (itemBags[indexUI] != null && itemBags[indexUI].isTypeCrystal()) {
                                items.add(itemBags[indexUI]);
                            }
                            if (itemBags[indexUI] != null && itemBags[indexUI].template.type == 28) {
                                itemBaoHiem = itemBags[indexUI];
                            }
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error(getStringBaseInfo(), e);
                }
                if (isGold && goldUps[item.upgrade] > getLuong()) {
                    return;
                }
                if (itemBaoHiem != null && ((item.upgrade > 14 && itemBaoHiem.template.itemTemplateId != 475) || (item.upgrade > 12 && itemBaoHiem.template.itemTemplateId != 285 && itemBaoHiem.template.itemTemplateId != 475) || (item.upgrade > 8 && itemBaoHiem.template.itemTemplateId != 284 && itemBaoHiem.template.itemTemplateId != 285 && itemBaoHiem.template.itemTemplateId != 475))) {
                    NJUtil.sendDialog(getSession(), AlertFunction.NOT_PROTECT1(getSession()));
                    return;
                }
                int countHave = countCrystal(items);
                int countNeed;
                if (item.isTypeNguyetNhan()) {
                    return;
                }
                if (item.isTypeClothe()) {
                    long sum = (long) getXu() + (long) getYen();
                    if (coinUpClothes[item.upgrade] > sum) {
                        return;
                    }
                    if (coinUpClothes[item.upgrade] > getYen()) {
                        subXu(coinUpClothes[item.upgrade] - getYen());
                        setYen(0);
                    } else {
                        subYen(coinUpClothes[item.upgrade]);
                    }
                    countNeed = upClothe[item.upgrade];
                    int limit = countNeed * maxPercents[item.upgrade] / 100;
                    if (countHave > limit) {
                        countHave = limit;
                    }
                } else if (item.isTypeAdorn()) {
                    long sum = (long) getXu() + (long) getYen();
                    if (coinUpAdorns[item.upgrade] > sum) {
                        return;
                    }
                    if (coinUpAdorns[item.upgrade] > getYen()) {
                        subXu(coinUpAdorns[item.upgrade] - getYen());
                        setYen(0);
                    } else {
                        subYen(coinUpAdorns[item.upgrade]);
                    }
                    countNeed = upAdorn[item.upgrade];
                    int limit = countNeed * maxPercents[item.upgrade] / 100;
                    if (countHave > limit) {
                        countHave = limit;
                    }
                } else {
                    if (!item.isTypeWeapon()) {
                        return;
                    }
                    long sum = (long) getXu() + (long) getYen();
                    if (coinUpWeapons[item.upgrade] > sum) {
                        return;
                    }
                    if (coinUpWeapons[item.upgrade] > getYen()) {
                        subXu(coinUpWeapons[item.upgrade] - getYen());
                        setYen(0);
                    } else {
                        subYen(coinUpWeapons[item.upgrade]);
                    }
                    countNeed = upWeapon[item.upgrade];
                    int limit = countNeed * maxPercents[item.upgrade] / 100;
                    if (countHave > limit) {
                        countHave = limit;
                    }
                }
                if (isGold) {
                    subLuong(goldUps[item.upgrade]);
                    countHave *= 1.5;
                    savezbLog("Nang cap can than cap " + item.upgrade, getLuong() + "@" + goldUps[item.upgrade]);
                }
                for (Item value : items) {
                    removeItem(value, 3);
                }
                if (itemBaoHiem != null) {
                    removeItem(itemBaoHiem, 3);
                }
                if (countHave >= countNeed || NJUtil.probability(countHave, countNeed - countHave) == 0) {
                    item.isLock = true;
                    int levelUp = 1;
                    item.upgrade += levelUp;
                    if (item.upgrade > item.getUpMax()) {
                        item.upgrade = item.getUpMax();
                    } else {
                        changeItemOption(item, levelUp);
                    }
                    if (typeNvbian == 4) {
                        fibian();
                    }
                    savezaLog("Nang cap thanh cong @" + item.getTemplateId() + "@" + item.upgrade);
                    Player pSave = this;
                    Database.savePlayer(pSave, map.getTemplateId());
                    if (dailytask != null && dailytask.template.checkTask(this, 7)) {
                        checkTaskOrder(dailytask, 1);
                    }
                    message = new Message(Cmd.UPGRADE);
                    message.writeByte(1);
                    message.writeInt(getLuong());
                    message.writeInt(getXu());
                    message.writeInt(getYen());
                    message.writeByte(item.upgrade);
                    NJUtil.sendMessage(getSession(), message);
                    checkStepNguyeNhanTaskNangCap(NguyetNhanTask.TYPE_NANG_CAP, item.upgrade, item);
                } else {
                    int levelDown;
                    if (itemBaoHiem == null) {
                        if (item.upgrade >= 14) {
                            levelDown = item.upgrade - 14;
                            item.upgrade = 14;
                            changeItemOption(item, -levelDown);
                        } else if (item.upgrade >= 12) {
                            levelDown = item.upgrade - 12;
                            item.upgrade = 12;
                            changeItemOption(item, -levelDown);
                        } else if (item.upgrade >= 8) {
                            levelDown = item.upgrade - 8;
                            item.upgrade = 8;
                            changeItemOption(item, -levelDown);
                        } else if (item.upgrade >= 4) {
                            levelDown = item.upgrade - 4;
                            item.upgrade = 4;
                            changeItemOption(item, -levelDown);
                        }
                    }
                    savezaLog("Nang cap that bai @" + item.getTemplateId() + "@" + up);
                    message = new Message(Cmd.UPGRADE);
                    message.writeByte(2);
                    message.writeInt(getLuong());
                    message.writeInt(getXu());
                    message.writeInt(getYen());
                    message.writeByte(item.upgrade);
                    NJUtil.sendMessage(getSession(), message);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doUpgradeMon(Item item) {
        callEffectMe(58);
        ++item.upgrade;
        ItemOption itemOption = item.options.get(0);
        itemOption.param -= 1000;
        if (item.upgrade >= 99) {
            item.options.get(0).param = 0;
            item.upgrade = 99;
        }
        int lvup = (item.upgrade + 1) / 10;
        int lvup2 = (item.upgrade + 1) / 20;
        int[] start = { 1, 3, 5, 7, 9 };
        if ((item.upgrade + 1) % 10 == 0) {
            for (int i = 0; i < item.options.size(); ++i) {
                switch (item.options.get(i).optionTemplate.itemOptionTemplateId) {
                    case 6:
                    case 7:
                        item.options.get(i).param = 50 * start[item.sys] + lvup * 100;
                        break;
                    case 10:
                    case 68:
                        item.options.get(i).param = 10 * start[item.sys] + lvup * 10;
                        break;
                    case 69:
                        item.options.get(i).param = 10 * start[item.sys] + lvup2 * 10;
                        break;
                    case 70:
                    case 67:
                    case 72:
                    case 71:
                        item.options.get(i).param = 5 * start[item.sys] + lvup2 * 5;
                        break;
                    case 73:
                        item.options.get(i).param = 100 * start[item.sys] + lvup * 100;
                        break;
                    case 74:
                        item.options.get(i).param = 50 * start[item.sys] + lvup * 20;
                        break;
                }
            }
        }
    }

    public void doUppearl(Message message, boolean isCoin) {
        try {
            if (isControlCharNhanBan() || !isOk(6)) {
                return;
            }
            if (isLock) {
                sendNotUnlock();
                return;
            }
            LocalTime timeNow = LocalTime.now().minusMinutes(5);
            LocalTime timeBaoTri = GameServer.timeMaintenance;
            int hour = timeNow.getHour();
            int minute = timeNow.getMinute();
            if (hour == timeBaoTri.getHour() && (timeBaoTri.getMinute() - minute <= 5)) {
                doCancelTrade();
                return;
            }
            if (!isOk(6)) {
                return;
            }
            int indexUI = findIndexEmpty(3);
            if (indexUI == -1) {
                NJUtil.sendDialog(getSession(), AlertFunction.BAG_FULL(getSession()));
                return;
            }
            long expires = -1L;
            boolean isGetCoin = false;
            boolean isLock = false;
            Vector<Item> items = new Vector<>();
            try {
                int[] a = new int[24];
                for (int i = 0; i < 24; ++i) {
                    a[i] = -1;
                }
                for (int i = 0; i < 24; ++i) {
                    try {
                        a[i] = message.readByte();
                    } catch (Exception e) {
                    }
                }
                for (int i = 0; i < 23; ++i) {
                    for (int j = i + 1; j < 24; ++j) {
                        if (a[i] > -1 && a[j] > -1 && a[i] == a[j]) {
                            ++countHackNgoc;
                            if (countHackNgoc >= 3) {
                                doCancelTrade1();
                                Database.saveLogAll(name, "hack luyen da", "hack");
                                getSession().disconnect("hack luyen da");
                            }
                            return;
                        }
                    }
                }
                for (int i = 0; i < 24; ++i) {
                    if (a[i] > -1) {
                        Item item = itemBags[a[i]];
                        if (expires == -1L && item.expires != -1L) {
                            expires = item.expires;
                        } else if (expires != -1L && item.expires != -1L && item.expires < expires) {
                            expires = item.expires;
                        }
                        if (item.isLock) {
                            isLock = true;
                        }
                        if (!item.isTypeCrystal() || item.template.itemTemplateId == 11) {
                            return;
                        }
                        items.add(item);
                    }
                }
            } catch (Exception e) {
            }
            if (items.size() <= 1) {
                return;
            }
            int total = 0;
            int maxId = -1;
            for (Item item : items) {
                total += crystals[item.template.itemTemplateId];
                if (item.template.itemTemplateId > maxId) {
                    maxId = item.template.itemTemplateId;
                }
            }
            if (total <= crystals[0]) {
                return;
            }
            int level;
            for (level = crystals.length - 1; level >= 0; --level) {
                if (total > crystals[level]) {
                    ++level;
                    break;
                }
                if (total == crystals[level]) {
                    break;
                }
            }
            if (level >= crystals.length) {
                level = crystals.length - 1;
            }
            if (level >= 11) {
                endWait(null);
                NJUtil.sendDialog(getSession(), "Chỉ có thể luyện đá đến cấp 11.");
                return;
            }
            if (crystals[level] * 39 / 100 > total && level == maxId + 1) {
                endWait(null);
                NJUtil.sendDialog(getSession(), AlertFunction.MIN_PER_UP(getSession()));
                return;
            }
            if (isCoin) {
                if (coinUpCrystals[level] > getXu()) {
                    return;
                }
                subXu(coinUpCrystals[level]);
            } else {
                isLock = true;
                long coinRequired = getYen() + getXu();
                if (coinUpCrystals[level] > coinRequired) {
                    return;
                }
                if (coinUpCrystals[level] > getYen()) {
                    subXu(coinUpCrystals[level] - getYen());
                    setYen(0);
                    isGetCoin = true;
                } else {
                    subYen(coinUpCrystals[level]);
                }
            }
            for (Item item : items) {
                removeItem(item, 3);
            }
            int percent = NJUtil.randomNumber(crystals[level]) + 1;
            Item item;
            if (total >= percent) {
                item = new Item(level);
                savezaLog("Luyen da thanh cong @" + item.getTemplateId());
            } else {
                item = new Item(level - 1);
                savezaLog("Luyen da that bai @" + item.getTemplateId());
            }
            item.playerId = playerId;
            item.isLock = isLock;
            item.expires = expires;
            addItem(item, 3, indexUI);
            if (!isLock) {
                message = new Message(Cmd.UPPEARL);
                message.writeByte((total >= percent) ? 1 : 2);
                message.writeByte(item.indexUI);
                message.writeShort(item.template.itemTemplateId);
                message.writeBoolean(item.isLock);
                message.writeBoolean(item.expires != -1L);
                message.writeInt(getXu());
            } else {
                message = new Message(Cmd.UPPEARL_LOCK);
                message.writeByte((total >= percent) ? 1 : 2);
                message.writeByte(item.indexUI);
                message.writeShort(item.template.itemTemplateId);
                message.writeBoolean(item.isLock);
                message.writeBoolean(item.expires != -1L);
                message.writeInt(getYen());
                if (isGetCoin) {
                    message.writeInt(getXu());
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void doUseSkillMyBuff(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            if (map.isDunVD) {
                if (map.isGiaiDauNinjaTaiNang()) {
                    DunVD dun = (DunVD) map;
                    if (!dun.phe1.contains(this) && !dun.phe2.contains(this)) {
                        return;
                    }
                } else if (map.isGiaiDauNinjaDenhat()) {
                    DunVDClass dun2 = (DunVDClass) map;
                    if (!dun2.phe1.contains(this) && !dun2.phe2.contains(this)) {
                        return;
                    }
                }
            }
            if (isLockUseItem()) {
                return;
            }
            checkClearEffect11();
            checkClearEffect12();
            if (!isAttack(true)) {
                return;
            }
            if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 49) {
                int dir = message.readByte();
                short bx = (dir == 1) ? ((short) (x + 15)) : ((short) (x - 15));
                short by = autoFall(bx, y);
                if (y != by) {
                    playerMain.mp += playerMain.myskill.manaUse;
                    playerMain.myskill.timeAttackNext = 0L;
                    NJUtil.sendServer(getSession(), AlertFunction.NOT_SPACE(getSession()));
                    updateMp(0);
                    return;
                }
                updateMp(0);
                BuNhin buNhin = new BuNhin(bx, by, playerMain.myskill.options.firstElement().param * 1000, playerMain, playerMain.myskill.dx, playerMain.myskill.dy);
                map.addBuNhin(buNhin);
                message = new Message(Cmd.CREATE_BUNHIN);
                message.writeUTF(name);
                message.writeShort(buNhin.x);
                message.writeShort(buNhin.y);
                sendToPlayer(message, true);
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 27) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(8);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 5000;
                eff.param = playerMain.myskill.options.firstElement().param + playerMain.myskill.options.firstElement().param * playerMain.perUpBuff / 100;
                addEffect(eff);
                if (playerMain.party != null) {
                    playerMain.party.sendEffBuff(this, eff, playerMain.myskill.options.lastElement().param + playerMain.myskill.options.lastElement().param * playerMain.perUpBuff / 100);
                }
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 51) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(9);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 30000;
                addEffect(eff);
                playerMain.sumonHide = new SumonHide((short) (playerMain.dame_full * playerMain.myskill.options.firstElement().param / 100), (short) playerMain.myskill.dx, (short) playerMain.myskill.dy);
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 30) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(10);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 90000;
                convertMp = (short) myskill.options.firstElement().param;
                addEffect(eff);
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 52) {
                isInvisible = true;
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(16);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 5000;
                eff.param = playerMain.myskill.options.firstElement().param;
                addEffect(eff);
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 53) {
                isInvisible = true;
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(15);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = playerMain.myskill.options.firstElement().param * 1000;
                addEffect(eff);
            } else if (playerMain.myskill.options.firstElement().optionTemplate.skillOptionTemplateId == 56) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(17);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 5000;
                eff.param = playerMain.myskill.options.firstElement().param;
                addEffect(eff);
            } else if (playerMain.myskill.template.skillTemplateId == 51) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(19);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = 90000;
                eff.options = new Vector<>();
                for (int i = 0; i < playerMain.myskill.options.size(); ++i) {
                    SkillOption clone;
                    SkillOption op = clone = playerMain.myskill.options.get(i).clone();
                    clone.param += op.param * perUpBuff / 100;
                    eff.options.add(op);
                }
                addEffect(eff);
                updateData();
                if (party != null) {
                    party.sendEffBuff(this, eff, eff.param);
                }
            } else if (playerMain.myskill.template.skillTemplateId == 52) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(20);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = myskill.options.firstElement().param * 1000;
                eff.timeLength += eff.timeLength * perUpBuff / 100;
                eff.options = new Vector<>();
                for (int i = 0; i < myskill.options.size(); ++i) {
                    if (playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 40 || playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 41 || playerMain.myskill.options.get(i).optionTemplate.skillOptionTemplateId == 42) {
                        eff.options.add(playerMain.myskill.options.get(i));
                    }
                }
                addEffect(eff);
                updateData();
                if (party != null) {
                    party.sendEffBuff(this, eff, eff.param);
                }
            } else if (myskill.template.skillTemplateId == 58) {
                updateMp(0);
                Effect eff = new Effect();
                eff.template = ServerController.effTemplates.get(11);
                eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
                eff.timeLength = myskill.options.firstElement().param;
                eff.param = 15000;
                addEffect(eff);
                playerMain.updateData();
            } else if (GameServer.openPhanThan && playerMain.myskill.isSkillPhanThan() && isMainchar) {
                clonePlayer(myskill.options.get(0).param);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void doViewItemAuction(Message message) {
        try {
            int itemId = message.readInt();
            Item item = null;
            for (int i = 0; i < ServerController.shops.size(); ++i) {
                if (ServerController.shops.get(i).itemId == itemId) {
                    item = ServerController.shops.get(i);
                    break;
                }
            }
            if (item == null) {
                return;
            }
            message = new Message(Cmd.VIEW_ITEM_AUCTION);
            message.writeInt((int) item.itemId);
            message.writeInt(item.saleCoinLock);
            if (item.isTypeBody() || (item.isTypeGem() && conn.isVersion144())) {
                message.writeByte(item.upgrade);
                message.writeByte(item.sys);
                if (item.options != null) {
                    for (int i = 0; i < item.options.size(); ++i) {
                        message.writeByte(item.options.get(i).optionTemplate.itemOptionTemplateId);
                        if (conn.isVersion145()) {
                            message.writeInt(item.options.get(i).param);
                        } else {
                            message.writeShort(item.options.get(i).param);
                        }
                    }
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public PlayerTemplate doLongDen(PlayerTemplate playerTemplate) {
        try {
            Item itDiem = findItemBag(310);
            if (itDiem == null) {
                NJUtil.sendServer(getSession(), AlertFunction.LONG_DEN1(getSession()));
                return playerTemplate;
            }
            for (int i = 0; i < map.npcPlayers.size(); ++i) {
                NpcPlayer npcPlayer = map.npcPlayers.get(i);
                if (npcPlayer.template.playerTemplateId == 31 && NJUtil.distance(npcPlayer.pointx, npcPlayer.pointy, x, y) <= 50 && npcPlayer.status == ME_DIE) {
                    // do add gift here
                    callEffectMe(21);
                    npcPlayer.status = NpcPlayer.A_HIDE;
                    Message message = NJUtil.messageSubCommand(Cmd.NPC_PLAYER_UPDATE);
                    message.writeByte(npcPlayer.npcPlayerId);
                    message.writeByte(npcPlayer.status);
                    sendToPlayer(message, true);
                    useItemUpToUp(itDiem);
                    sendUseItemUpToUp(itDiem.indexUI, 1);
                    return playerTemplate;
                }
            }
        } catch (Exception e) {
        }
        return playerTemplate;
    }

    public void endWait(String str) {
        timeWait = 0;
        itemWait = null;
        itemMapWait = null;
        try {
            Message message = NJUtil.messageSubCommand(Cmd.END_WAIT);
            if (str != null) {
                message.writeUTF(str);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void endWaitCauCa(String str) {
        timeCauca = 0;
        itemCauca = null;
        try {
            Message message = NJUtil.messageSubCommand(Cmd.END_WAIT);
            if (str != null) {
                message.writeUTF(str);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public Friend findFriend(String nameFind) {
        try {
            for (Friend friend : friends) {
                try {
                    if (friend.friendName.equals(nameFind)) {
                        return friend;
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public int findIndexEmpty(int typeUI) {
        if (typeUI == 3) {
            for (int i = 0; i < itemBags.length; ++i) {
                if (itemBags[i] == null) {
                    return i;
                }
            }
        } else if (typeUI == 4) {
            for (int i = 0; i < itemBoxs.length; ++i) {
                if (itemBoxs[i] == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Item findItemBag(int idFind) {
        for (Item it : itemBags) {
            if (it != null && it.getTemplateId() == idFind) {
                return it;
            }
        }
        return null;
    }

    public Item findItemBag(int idFind, boolean lock, int quantity) {
        for (Item it : itemBags) {
            if (it != null && it.getTemplateId() == idFind && it.isLock == lock && it.quantity >= quantity) {
                return it;
            }
        }
        return null;
    }

    public Item findItem(int idFind) {
        for (Item it : itemBags) {
            if (it != null && it.getTemplateId() == idFind) {
                return it;
            }
        }
        for (Item it : itemBoxs) {
            if (it != null && it.getTemplateId() == idFind) {
                return it;
            }
        }
        for (Item it : itemBodys) {
            if (it != null && it.getTemplateId() == idFind) {
                return it;
            }
        }
        return null;
    }

    public Map findMapFriend(Player player) {
        try {
            if ((player.map.isDun || player.map.getTemplateId() == 110 || player.map.isDunClan || player.map.isDunPB || player.map.isDunVA || player.map.template.isMapChienTruong() || player.map.template.typeMap == MapTemplate.MAP_PB) && getSession().type_admin < ROLE_GM) {
                NJUtil.sendServer(getSession(), AlertFunction.NOT_MOVETO(getSession()));
                return null;
            }
            if (player.map.players.size() >= player.map.maxPlayer && getSession().type_admin < ROLE_GM) {
                NJUtil.sendServer(getSession(), AlertFunction.MAP_FULL1(getSession()));
                return null;
            }
            if (player.party != null && party != null && party.equals(player.party)) {
                return player.map;
            }
            if (party != null && player.map.getPts().size() >= Map.maxParty - 1) {
                NJUtil.sendServer(getSession(), AlertFunction.MAX_PARTY1(getSession()));
                return null;
            }
            return player.map;
        } catch (Exception e) {
        }
        return null;
    }

    public String getAlertTime(long t) {
        if (System.currentTimeMillis() - t <= 0L) {
            long time = Math.abs((int) (System.currentTimeMillis() - t));
            long sc = time / 1000L;
            if (sc <= 0L) {
                sc = 1L;
            }
            String[] info = { "Từ từ thôi, " + sc + " giây sau thử lại nhé", "Can use after " + sc + "s" };
            return info[getSession().typeLanguage];
        }
        return null;
    }

    public int getCT() {
        if (pointCT >= 4000) {
            return 4;
        }
        if (pointCT >= 1500) {
            return 3;
        }
        if (pointCT >= 600) {
            return 2;
        }
        if (pointCT >= 200) {
            return 1;
        }
        return 0;
    }

    public int getDam(Npc npc) {
        long dam = dame_full;
        if (npc.isPrivateNpc()) {
            if (privateNpc == null) {
                return 0;
            }
            int maxHp = privateNpc.template.hp;
            dam = maxHp / nHit2PrivateMonster;
        }
        if (MixedArena.isMapArena(map.template.mapTemplateId)) {
            return MixedArena.calcDamAttackMob(this, (int) dam);
        }
        return (int) dam;
    }

    public int getDameSkill() {
        for (int i = 0; i < myskill.options.size(); ++i) {
            if (myskill.options.get(i).optionTemplate.skillOptionTemplateId == 2 || myskill.options.get(i).optionTemplate.skillOptionTemplateId == 3 || myskill.options.get(i).optionTemplate.skillOptionTemplateId == 4) {
                return myskill.options.get(i).param;
            }
        }
        return 0;
    }

    public Vector<Integer> getDragonball(int index) {
        Vector<Integer> idBalls = new Vector<>();
        int[] ids = { 225, 223, 226, 224, 227, 228, 222 };
        int[] isHave = new int[7];
        for (Item itemBag : itemBags) {
            if (itemBag != null) {
                for (int j = 0; j <= index; ++j) {
                    if (ids[j] == itemBag.getTemplateId()) {
                        isHave[j] = 1;
                        break;
                    }
                }
            }
        }
        for (Item itemBox : itemBoxs) {
            if (itemBox != null) {
                for (int j = 0; j <= index; ++j) {
                    if (ids[j] == itemBox.getTemplateId()) {
                        isHave[j] = 1;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i <= index; ++i) {
            if (isHave[i] == 0) {
                idBalls.add(ids[i]);
            }
        }
        return idBalls;
    }

    public String getFriends() {
        String str = "";
        for (int i = 0; i < friends.size(); ++i) {
            if (i > 70) {
                break;
            }
            String tt = str + friends.get(i).friendName + "," + friends.get(i).type;
            if (tt.length() > 1000) {
                break;
            }
            str = tt;
            if (i < friends.size() - 1) {
                str = str + ";";
            }
        }
        return str;
    }

    public void setFriends(String str) {
        friends.clear();
        if (str.isEmpty()) {
            return;
        }
        String[] strs = str.split(";");
        boolean isHave;
        String s;
        for (String value : strs) {
            s = value;
            if (!s.isEmpty()) {
                String[] datas = s.split(",");
                String name = datas[0];
                isHave = false;
                for (Friend friend : friends) {
                    if (friend.friendName.equals(name)) {
                        isHave = true;
                        break;
                    }
                }
                if (!isHave) {
                    friends.add(new Friend(name, Byte.parseByte(datas[1])));
                }
                if (friends.size() > 70) {
                    break;
                }
            }
        }
    }

    public int getIdItemGetAuto(int id) {
        if (findItemBag(572) == null) {
            return -2;
        }
        if (typeGetItemAuto == 1) {
            for (short value : ID_ITEM_GET_AUTO) {
                if (value == id) {
                    return id;
                }
            }
            return -2;
        }
        return -1;
    }

    public short[] getIdPartThoiTrang() {
        Player playerMain = getPlayerMainControl();
        short[] idPartTHoiTrang = { -1, -1, -1, -1 };
        if (playerMain.itemBodys[11] != null) {
            if (playerMain.itemBodys[11].isMatNaThuyTinh()) {
                idPartTHoiTrang = new short[]{ 185, -1, 186, 187 };
            } else if (playerMain.itemBodys[11].isMatNaSonTinh()) {
                idPartTHoiTrang = new short[]{ 188, -1, 189, 190 };
            } else if (playerMain.itemBodys[11].isMatNaThanhGiong()) {
                idPartTHoiTrang = new short[]{ 205, -1, 206, 207 };
            } else if (playerMain.itemBodys[11].isMatNaChuot()) {
                idPartTHoiTrang = new short[]{ 264, -1, 265, 266 };
            } else if (playerMain.itemBodys[11].isMatNaBiNgo()) {
                idPartTHoiTrang = new short[]{ 258, -1, 259, 260 };
            } else if (playerMain.itemBodys[11].isMatNaJrai()) {
                idPartTHoiTrang = new short[]{ 223, -1, 224, 225 };
            } else if (playerMain.itemBodys[11].isMatNaJumito()) {
                idPartTHoiTrang = new short[]{ 226, -1, 227, 228 };
            } else if (playerMain.itemBodys[11].isMatNaSantaClaus()) {
                idPartTHoiTrang = new short[]{ 267, -1, 268, 269 };
            }
        }
        return idPartTHoiTrang;
    }

    public String getInfo(boolean isCreate) {
        if (!isCreate) {
            timeLogOut = System.currentTimeMillis() + NJUtil.getMilisByMinute(10);
        }
        byte b = -1;
        effId_exp_bonus = b;
        effId_food = b;
        int n = 0;
        timeEff_exp_bonus = n;
        timeEff_food = n;
        effId_KhaiThienNhanPhu = -1;
        timeEff_ThiLuyen = 0;
        timeKhai_Thien_Nhan_Phu = 0;
        long time = System.currentTimeMillis();
        for (Effect effect : effects) {
            if (effect.template.type == 0) {
                effId_food = (byte) effect.template.id;
                timeEff_food = (int) (effect.timeStart * 1000 + effect.timeLength - time);
            } else if (effect.template.type == 18) {
                effId_exp_bonus = (byte) effect.template.id;
                timeEff_exp_bonus = (int) (effect.timeStart * 1000 + effect.timeLength - time);
            } else if (effect.template.type == 25) {
                if (effect.template.id != 40 && effect.template.id != 41) {
                    timeEff_ThiLuyen = (int) (effect.timeStart * 1000 + effect.timeLength - time);
                } else {
                    effId_KhaiThienNhanPhu = (byte) effect.template.id;
                    timeKhai_Thien_Nhan_Phu = (int) (effect.timeStart * 1000 + effect.timeLength - time);
                }
            }
        }
        String strTaskDay = " ";
        String strTaskBoss = " ";
        if (taskLoopDay != null) {
            strTaskDay = taskLoopDay.count + "#" + taskLoopDay.maxCount + "#" + taskLoopDay.killId;
        }
        if (taskLoopBoss != null) {
            strTaskBoss = taskLoopBoss.count + "#" + taskLoopBoss.killId;
        }

        return dayLogin + "," + //0
            timeLogOut + "," + //1
            timeOnline + "," + //2
            pointOpenTui + "," + //3
            resetPotential + "," + //4
            resetSkill + "," + //5
            pointUyDanh + "," + //6
            pointNon + "," + //7
            pointVukhi + "," + //8
            pointAo + "," + //9
            pointLien + "," + //10
            pointGangtay + "," + //11
            pointNhan + "," + //12
            pointQuan + "," + //13
            pointNgocboi + "," + //14
            pointGiay + "," + //15
            pointPhu + "," + //16
            effId_food + "," + //17
            timeEff_food + "," + //18
            effId_exp_bonus + "," + //19
            timeEff_exp_bonus + "," + //20
            countFinishDay + "," + //21
            countLoopDay + "," + //22
            strTaskDay + "," + //23
            countLoopBoos + "," + //24
            strTaskBoss + "," + //25
            limitKynangso + "," + //26
            limitTiemnangso + "," + //27
            pointClan + "," + //28
            timeOutClan + "," + //29
            countUseTTL + "," + //30
            pointClanWeek + "," + //31
            countPB + "," + //32
            pointPB + "," + //33
            timePB + "," + //34
            friendPB + "," + //35
            pointCT + "," + //36
            resultCT + "," + //37
            pointPKCT + "," + //38
            countChatAdmin + "," + //39
            countUseHDL + "," + //40
            pointTT + "," + //41
            limitBanhPhongloi + "," + //42
            limitBanhBangHoa + "," + //43
            countLoiDai + "," + //44
            pointLoiDai + "," + //45
            countctkeo + "," + //46
            typeNvbian + "," + //47
            countNvbian + "," + //48
            timeLiveCoppy + "," + //49
            expSkillPhanthan + "," + //50
            switchNewExp + "," + //51
            timeEff_ThiLuyen + "," + //52
            effId_KhaiThienNhanPhu + "," + //53
            timeKhai_Thien_Nhan_Phu + "," + //54
            (capcha != null ? "1" : "0") + "," + //55
            maxKillMonster + "," + // 56
            countMonsKill + "," + //57
            timeKillMonsterLangCo + "," + //58
            point3Arena + "," + //59
            countLoiDaiClass + "," + //60
            pointLoiDaiClass + "," + //61
            getInfoNguyetNhanTask() + "," + //62
            totalNvNguyetNhan + "," + //63
            useDanhVongPhu + ","; //64
    }

    public String getInfoEvent() {
        return "";
    }


    public long[] getInfoLevel() {
        if (exp >= maxExp) {
            exp = maxExp;
        }
        long expRemain = exp;
        byte i = 0;
        while (i < exps.length && expRemain >= exps[i]) {
            expRemain -= exps[i];
            ++i;
        }
        return new long[]{ i, expRemain };
    }

    public String getInfoNguyetNhanTask() {
        if (nguyetNhanTask != null) {
            return nguyetNhanTask.getInfoSave();
        }
        return "";
    }

    public void setInfoNguyetNhanTask(String info) {
        try {
            if (info == null || info.isEmpty()) {
                return;
            }
            String[] data = info.split("#");
            nguyetNhanTask = new NguyetNhanTask(Integer.parseInt(data[4]), Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]));
        } catch (Exception e) {
        }
    }

    public String getItemInfo() {
        StringBuilder str = new StringBuilder(" ");
        if (itemBags != null) {
            for (Item itemBag : itemBags) {
                try {
                    if (itemBag != null) {
                        str.append(itemBag.getAllInfo()).append("@");
                    }
                } catch (Exception e) {
                }
            }
        }
        str.append("& ");
        if (itemBoxs != null) {
            for (Item itemBox : itemBoxs) {
                try {
                    if (itemBox != null) {
                        str.append(itemBox.getAllInfo()).append("@");
                    }
                } catch (Exception e) {
                }
            }
        }
        str.append("& ");
        if (itemBodys != null) {
            for (Item itemBody : itemBodys) {
                try {
                    if (itemBody != null) {
                        str.append(itemBody.getAllInfo()).append("@");
                    }
                } catch (Exception e) {
                }
            }
        }
        str.append("& ");
        if (itemMons != null) {
            for (Item itemMon : itemMons) {
                try {
                    if (itemMon != null) {
                        str.append(itemMon.getAllInfo()).append("@");
                    }
                } catch (Exception e) {
                }
            }
        }
        return str.toString();
    }

    public KillPlayer getKillPlayer() {
        return killPlayer;
    }

    public short getLevel() {
        if (exp >= ((switchNewExp == 0) ? maxExp : maxExp1)) {
            exp = ((switchNewExp == 0) ? maxExp : maxExp1);
        }
        long expRemain = exp;
        short i;
        if (switchNewExp == 0) {
            for (i = 0; i < exps.length; ++i) {
                if (expRemain < exps[i]) {
                    break;
                }
                expRemain -= exps[i];
            }
        } else {
            i = 0;
            while (i < exps1.length && expRemain >= exps1[i]) {
                expRemain -= exps1[i];
                ++i;
            }
        }
        return i;
    }

    public int getPartBody(int indexUI) {
        if (indexUI == 1) {
            return (itemBodys[1] != null) ? itemBodys[1].template.part : -1;
        }
        if (indexUI == 2) {
            return (itemBodys[2] != null) ? itemBodys[2].template.part : -1;
        }
        if (indexUI == 6) {
            return (itemBodys[6] != null) ? itemBodys[6].template.part : -1;
        }
        return -1;
    }

    public int getPercentDamPhanThan() {
        return 100;
    }

    public void setPercentDamPhanThan(int pc) {
    }

    public Player getPlayerMainControl() {
        if (isControlCharNhanBan()) {
            return playercopy;
        }
        return this;
    }

    public int getPointCT() {
        int ct = getCT();
        if (ct == 4) {
            return 15;
        }
        if (ct == 3) {
            return 10;
        }
        if (ct == 2) {
            return 7;
        }
        if (ct == 1) {
            return 5;
        }
        return 3;
    }

    public int getRewardPB() {
        int rwt = 0;
        if (timePB > 0) {
            int tt = 60 - timePB / 60;
            rwt = tt / 5 + 1;
            rwt += friendPB;
            if ((level >= 50 && level < 60) || (level >= 90 && level <= 130)) {
                rwt += friendPB;
            }
        }
        int reward = (pointPB + rwt * 5) / 10;
        if (level >= 40 && level < 50) {
            reward = (pointPB + rwt * 5) / 11;
        }
        if (level >= 50 && level < 60) {
            reward = (pointPB + rwt * 15) / 11 / 4;
        }
        if (level >= 60 && level < 90) {
            reward = (pointPB + rwt * 5) / 25;
        }
        if (level >= 90 && level <= 130) {
            reward = (pointPB + rwt * 15) / 11 / 4;
        }
        return reward * GameServer.chestRate;
    }

    public Skill getSkill(int skillTemplateId) {
        for (Skill skill : skills) {
            if (skill.template.skillTemplateId == skillTemplateId) {
                return skill;
            }
        }
        return null;
    }

    public Skill getSkillAttackHighest() {
        for (int i = skills.size() - 1; i >= 0; --i) {
            Skill sk = skills.get(i);
            if (sk.isSkillAttack() && !isSkillAttackNoneDisplay(sk.template.skillTemplateId)) {
                return sk;
            }
        }
        return null;
    }

    public String getSkillInfo() {
        StringBuilder skillInfo = new StringBuilder();
        for (int i = 0; i < skills.size(); ++i) {
            skillInfo.append(skills.get(i).skillId);
            if (i < skills.size() - 1) {
                skillInfo.append(",");
            }
        }
        return skillInfo.toString();
    }

    public Skill getSkillPhanThan() {
        short[] idSkill = { 67, 67, 68, 69, 70, 71, 72 };
        return getSkill(idSkill[classId]);
    }

    public Skill getSkillValue() {
        return myskill;
    }

    public short getTaskFinish() {
        if (!isMainchar && playercopy != null) {
            return 10000;
        }
        return taskFinish;
    }

    public void getTaskOrder(TaskOrder taskorder) {
        try {
            Player playerMain = getPlayerMainControl();
            if (playerMain.isNhanban()) {
                clearTaskOrder(taskorder);
                return;
            }
            Message message = new Message(Cmd.GET_TASK_ORDER);
            message.writeByte(taskorder.taskId);
            if (getSession().isVersionTet2015()) {
                message.writeInt(taskorder.count);
                message.writeInt(taskorder.maxCount);
            } else {
                message.writeShort(taskorder.count);
                message.writeShort(taskorder.maxCount);
            }
            message.writeUTF(taskorder.name);
            message.writeUTF(taskorder.description);
            message.writeByte(taskorder.killId);
            message.writeByte(taskorder.mapId);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public int getTypePk() {
        return typePk;
    }

    public void ghiloghack(int itemTemplateId) {
        countItemBag(itemTemplateId);
    }

    public boolean goDunClan() {
        try {
            int time = (int) (clan.dunClan.timeEnd / 1000L - System.currentTimeMillis() / 1000L);
            if (clan.dunClan != null && time > 0) {
                mapTemplateIdGo = map.getTemplateId();
                mapClear();
                map.goOutMap(this);
                x = 0;
                y = 0;
                sendMapTime(time);
                clan.dunClan.waitGoInMap(this);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void goGiaiDau() {
        Dun dun = null;
        for (int i = 0; i < ServerController.loidaiduns.size(); ++i) {
            dun = ServerController.loidaiduns.get(i);
            if (dun.players.size() + dun.playerIns.size() < 20) {
                break;
            }
        }
        if (dun != null) {
            mapClear();
            map.goOutMap(this);
            x = 0;
            y = 0;
            dun.waitGoInMap(this);
        } else if (getSession().typeLanguage == GameServer.LANG_VI) {
            sendOpenUISay(0, "Ninja tài năng mở cửa vào lúc 17h50 và đóng cửa vào lúc 19h.");
        } else {
            sendOpenUISay(0, "The tournament starts in 17:50 - 19:00");
        }
    }

    public void goGiaiDauClass() {
        Dun dun = null;
        for (int i = 0; i < ServerController.loidaidunsClass.size(); ++i) {
            dun = ServerController.loidaidunsClass.get(i);
            if (dun.players.size() + dun.playerIns.size() < 20) {
                break;
            }
        }
        if (dun != null) {
            mapClear();
            map.goOutMap(this);
            x = 0;
            y = 0;
            dun.waitGoInMap(this);
        } else if (getSession().typeLanguage == GameServer.LANG_VI) {
            sendOpenUISay(22, "Ninja đệ nhất mở cửa vào lúc 21h50 và đóng cửa vào lúc 23h.");
        } else {
            sendOpenUISay(0, "The tournament starts in 21:50 - 23:00");
        }
    }

    public void goReturn() {
        try {
            Player playerMain = getPlayerMainControl();
            status = ME_NORMAL;
            playerMain.hp = playerMain.hp_full;
            playerMain.mp = playerMain.mp_full;
            lastMoveX = 0;
            lastMoveY = 0;
            Message message = new Message(Cmd.ME_LIVE);
            NJUtil.sendMessage(getSession(), message);
            updateInfo();
            playerLoadLive();
        } catch (Exception e) {
        }
    }

    public void goReturnHack() {
        try {
            status = ME_NORMAL;
            lastMoveX = 0;
            lastMoveY = 0;
            Message message = new Message(Cmd.ME_LIVE);
            NJUtil.sendMessage(getSession(), message);
            updateInfo();
        } catch (Exception e) {
        }
    }

    public void goTestDun(Dun dun, int time, int xMove, int yMove) {
        typePk = PK_NORMAL;
        if (map.getTemplateId() != 110 && map.getTemplateId() != 111) {
            mapTemplateIdGo = map.getTemplateId();
        }
        mapClear();
        map.goOutMap(this);
        x = (short) xMove;
        y = (short) yMove;
        sendMapTime(time);
        dun.waitGoInMap(this);
    }

    public boolean haveLongDenNgoiSao() {
        return getPlayerMainControl().sumon != null && getPlayerMainControl().sumon.isLongDenSao();
    }

    public boolean haveLongDenOngTrang() {
        return getPlayerMainControl().sumon != null && getPlayerMainControl().sumon.isLongDentrang();
    }

    public void initCapCha() {
        Vector<Npc> a = new Vector<>();
        int idtem = 219;
        a.add(Map.createModCapcha(idtem, map, 0, x / 24, y / 24 - 2));
        capcha = (CapchaMonster) a.get(0);
        if (getSession().clientType == GameServer.CLIENT_PC) {
            capcha.initCapcha(2);
        } else {
            capcha.initCapcha(getSession().typeHD);
        }
        ServerController.sendNpcTemplate(this, 219);
        updateInfo();
        isChangeMap = true;
        map.addDynamicMod(this, capcha);
    }

    public void initPrivateMonster(int idtem) {
        if (privateNpc != null) {
            return;
        }
        privateNpc = Map.createModCapcha(idtem, map, 0, x / 24, y / 24 - 1);
        Npc privateNpc = this.privateNpc;
        privateNpc.pointy += 2;
        map.addDynamicMod(this, this.privateNpc);
        this.privateNpc.exactly = 3000;
        nHit2PrivateMonster = (byte) NJUtil.randomMinMax(20, 100);
    }

    public boolean isAcountAo() {
        return getSession().username.startsWith(ServerController.tmpUserPrefix);
    }

    public boolean isAdmin() {
        return getSession() != null && getSession().type_admin > 0 || GameServer.isServerLocal();
    }

    public boolean isAttack(boolean isBuff) {
        Player playerMain = getPlayerMainControl();
        long tt = System.currentTimeMillis();
        int t = 300;
        if (playerMain.classId == 2 || playerMain.classId == 4 || playerMain.classId == 6) {
            t = 450;
        }
        if (isBuff) {
            t = 100;
        }
        if (tt - playerMain.timeAtt < t) {
            return false;
        }
        if (!isCheckTile()) {
            return false;
        }
        if (!map.template.tileTypeAt(x, y, 2)) {
            ++countAttUp;
        }
        if (countAttUp > 10) {
            autoDie("isAttack player");
            return false;
        }
        if (isLockAtt()) {
            return false;
        }
        if (playerMain.skillDao35 != 0 && playerMain.myskill.template.skillTemplateId != 42) {
            playerMain.skillDao35 = 0;
        }
        if (playerMain.skillDao35 == 1) {
            return true;
        }
        if (playerMain.myskill == null || (playerMain.myskill.template.maxPoint > 0 && playerMain.myskill.point == 0)) {
            NJUtil.sendDialog(getSession(), AlertFunction.SKILL_FAIL(getSession()));
            return false;
        }
        if (playerMain.itemBodys[1] == null) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 0 && !playerMain.itemBodys[1].isItemClass0()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 1 && !playerMain.itemBodys[1].isItemClass1()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 2 && !playerMain.itemBodys[1].isItemClass2()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 3 && !playerMain.itemBodys[1].isItemClass3()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 4 && !playerMain.itemBodys[1].isItemClass4()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 5 && !playerMain.itemBodys[1].isItemClass5()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        if (playerMain.classId == 6 && !playerMain.itemBodys[1].isItemClass6()) {
            NJUtil.sendServer(getSession(), AlertFunction.WEAPON_FAIL(getSession()));
            return false;
        }
        long timeNow = System.currentTimeMillis();
        if (playerMain.myskill == null || (playerMain.myskill.template.type == 2 && !isBuff) || (playerMain.myskill.template.type != 1 && playerMain.myskill.template.type != 2 && playerMain.myskill.template.type != 3 && playerMain.myskill.template.type != 4)) {
            NJUtil.sendServer(getSession(), AlertFunction.NOT_SKILL(getSession()));
            return false;
        }
        if (playerMain.myskill.manaUse > playerMain.mp) {
            updateMp(0);
            NJUtil.sendServer(getSession(), AlertFunction.NOT_MANA(getSession()));
            return false;
        }
        if (playerMain.myskill.timeAttackNext <= timeNow) {
            countHackDanh = 0;
            playerMain.mp -= playerMain.myskill.manaUse;
            int timeNext = playerMain.myskill.timeReplay;
            if (playerMain.myskill.timeReplay <= 1000) {
                timeNext = (int) (playerMain.myskill.timeReplay / 1.2);
            } else if (playerMain.myskill.timeReplay <= 10000) {
                timeNext = (int) (playerMain.myskill.timeReplay / 1.15);
            } else if (playerMain.myskill.timeReplay <= 20000) {
                timeNext = (int) (playerMain.myskill.timeReplay / 1.1);
            } else if (playerMain.myskill.timeReplay <= 50000) {
                timeNext = (int) (playerMain.myskill.timeReplay / 1.05);
            }
            playerMain.myskill.timeAttackNext = timeNow + timeNext;
            if (!map.template.tileTypeAt(x, y, 2)) {
                ++countAttUp;
            } else {
                countAttUp = 0;
            }
            timeAtt = tt;
            return true;
        }
        ++countHackDanh;
        int max = 50;
        if (getSession() != null && getSession().isGPRS) {
            max = 100;
        }
        if (countHackDanh > max) {
            countHackDanh = 0;
            return false;
        }
        return false;
    }

    public boolean isAttack(Npc npc, int index) {
        int dx = NJUtil.distance(npc.pointx, x);
        int dy = NJUtil.distance(npc.pointy, y);
        Player playerMain = getPlayerMainControl();
        if (dx <= playerMain.myskill.dx + npc.template.rangeMove * 2 && dy <= playerMain.myskill.dy + npc.template.rangeMove * 2) {
            return true;
        }
        if (index == 0) {
            int ddx = dx - playerMain.myskill.dx + npc.template.rangeMove * 2;
            int ddy = dx - playerMain.myskill.dy + npc.template.rangeMove * 2;
            if (ddx > 70 || ddy > 70) {
                ++countHackXa;
                if (countHackXa == 5) {
                    npc.objDie(getSession());
                    npc.objLive(getSession());
                    countHackXa = 0;
                }
            }
        }
        return false;
    }

    public boolean isAttackLan(int x1, int y1, int x2, int y2, boolean isNpc) {
        int dx = NJUtil.distance(x1, x2);
        int dy = NJUtil.distance(y1, y2);
        if (!isNpc) {
            return dx <= 100 && dy <= 30;
        } else {
            return dx <= 100 && dy <= 50;
        }
    }

    public boolean isAttackPlayer(Player player) {
        int dx = NJUtil.distance(player.x, x);
        int dy = NJUtil.distance(player.y, y);
        Player playerMain = getPlayerMainControl();
        if (dx <= playerMain.myskill.dx && dy <= playerMain.myskill.dy) {
            return true;
        }
        try {
            Message message = new Message(Cmd.PLAYER_MOVE);
            message.writeInt(player.playerId);
            message.writeShort(player.x);
            message.writeShort(player.y);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isBima() {
        return headId == 214 && bodyId == 215 && legId == 216;
    }

    public boolean isCheckTile() {
        if (map.template.tileTypeAt(x, y - 1, 2) && map.template.tileTypeAt(x, y - 1, 4) && map.template.tileTypeAt(x, y - 1, 8)) {
            x = map.template.defaultX;
            y = map.template.defaultY;
            if (!isNhanban()) {
                autoDie("isCheckTile player 1");
            }
            return false;
        }
        if (map.template.tileTypeAt(x, y - 1, 262144) || map.template.tileTypeAt(x, y - 1, 524288)) {
            x = map.template.defaultX;
            y = map.template.defaultY;
            if (!isNhanban()) {
                autoDie("isCheckTile player 2");
            }
            return false;
        }
        return true;
    }

    public boolean isControlCharNhanBan() {
        return !isNhanban() && (!isMainchar && playercopy != null);
    }

    public boolean isFirstJoinGame() {
        return false;
    }

    public void setFirstJoinGame(boolean s) {
    }

    public boolean isGTChien() {
        if (capcha != null) {
            return false;
        }
        for (int i = 0; i < GiaTocChien.gtcs.size(); ++i) {
            GiaTocChien gtc = GiaTocChien.gtcs.get(i);
            if (gtc.nameLefts.contains(name) || gtc.nameRights.contains(name)) {
                gtc.goTestGTC(this);
                return true;
            }
        }
        return false;
    }

    public boolean isHaveSkill(int skillTemplateId) {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.skills.size(); ++i) {
            if (playerMain.skills.get(i).template.skillTemplateId == skillTemplateId) {
                return true;
            }
        }
        return false;
    }

    public boolean isHit(Npc npc, int dameHit, boolean isChiMang, boolean isReactDame, int timeFire, int timeIce, int timeWind) {
        Player playerMain = getPlayerMainControl();
        if (playerMain.isNhanban()) {
            dameHit = dameHit < 1 ? 1 : playerMain.getPercentDamPhanThan() * dameHit / 100;
        }
        dameHit += playerMain.dam_mob;
        int deltalv = playerMain.level - npc.level;
        if (npc.template.isBossId() && !npc.template.isBossEventId() && !npc.isPrivateNpc() && !map.isDunPB) {
            if (map.isVungDatMaQuy()) {
                if (deltalv > 20) {
                    dameHit = 1;
                }
            } else if (deltalv > 15) {
                dameHit = 1;
            }
        }
        if (isAdmin() && isAdminUseCheat) {
            dameHit = 2000000000;
        }
        if ((playerMain.getTypePk() == PK_PHE1 && (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 167)) ||
            (playerMain.getTypePk() == PK_PHE2 && (npc.template.npcTemplateId == 143 || npc.template.npcTemplateId == 166))
        ) {
            return false;
        }
        long t = System.currentTimeMillis();
        try {
            if ((npc.template.npcTemplateId == 144 && map.getTemplateId() == 130) ||
                ((npc.template.npcTemplateId == 97 || npc.template.npcTemplateId == 98 || (npc.template.npcTemplateId == 167 && map.template.isMapChienTruong())) && playerMain.getTypePk() == PK_PHE1) ||
                ((npc.template.npcTemplateId == 96 || npc.template.npcTemplateId == 99 || (npc.template.npcTemplateId == 166 && map.template.isMapChienTruong())) && playerMain.getTypePk() == PK_PHE2)
            ) {
                return false;
            }
            if (npc.timeFire > 0) {
                dameHit *= dameFire;
            }
            dameHit = (Math.min(npc.hp, dameHit));
            if (npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) {
                dameHit = 1;
            }
            if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp <= npc.template.hp - 300) {
                dameHit = 0;
            }
            long t2 = System.currentTimeMillis() - t;
            if (!isReactDame) {
                Hit hit = npc.getHit(playerId);
                if (hit == null) {
                    hit = new Hit(this);
                    npc.hits.add(hit);
                }
                hit.dame += dameHit;
                int range = NJUtil.distance(x, y, npc.pointx, npc.pointy);
                if (hit.rangeAttack < range + 10) {
                    hit.rangeAttack = range + 10;
                }
                npc.hits.remove(hit);
                npc.hits.add(hit);
            }
            int expAdd = npc.getExp(dameHit, playerMain.level);
            if (map.isDunClan) {
                expAdd = npc.getExpClan(dameHit);
            }
            int deltaExp = 1;
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.id == 22) {
                    ++deltaExp;
                    break;
                }
                if (playerMain.effects.get(i).template.id == 32) {
                    deltaExp += 2;
                    break;
                }
                if (playerMain.effects.get(i).template.id == 33) {
                    deltaExp += 3;
                    break;
                }
            }
            int expSumon = 0;
            if (playerMain.sumon != null && playerMain.sumon.isLongDentron()) {
                expSumon = expAdd / 3;
            }
            if (getInfoX2Exp() != null) {
                ++deltaExp;
            }
            if (getInfoClassX2Exp() != null) {
                ++deltaExp;
            }
            expAdd = (expAdd * deltaExp + expSumon) * GameServer.expRate;
            if (npc.template.npcTemplateId == 0) {
                npc.hp -= npc.template.hp / 5;
            } else {
                npc.hp -= dameHit;
            }
            npc.timeHit = 1000;
            if (npc.template.isBossId()) {
                if (playerMain.level > npc.template.level + 6) {
                    npc.reactDame = level - npc.template.level;
                } else {
                    npc.reactDame = 0;
                }
            }
            if (dameHit > 0 && npc.reactDame > 0 && !isReactDame) {
                int dameReact = dameHit * npc.reactDame / 1000;
                npc.attPlayerReact(this, dameReact);
            }
            if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp < 10) {
                npc.hp = npc.template.hp;
            }
            if (npc.hp > 0) {
                if ((npc.template.npcTemplateId == 142 || npc.template.npcTemplateId == 143) && npc.hp % 10 == 0 && dameHit > 0) {
                    Item itEvent = new Item(458, false, "atk gio keo");
                    ItemMap itMap = new ItemMap(itEvent, -1, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                    itMap.y = autoFall(itMap.x, itMap.y);
                    map.addItemMap(itMap);
                    map.sendAddItemMap(itMap);
                }
                Message message = new Message(Cmd.NPC_HP);
                message.writeByte(npc.npcId);
                message.writeInt(npc.hp);
                message.writeInt(dameHit);
                message.writeBoolean(isChiMang);
                if (npc.isTinhAnh() || npc.isThuLinh() || (npc.template.npcTemplateId == 118 && npc.maxhp > 87420) || ((npc.template.npcTemplateId == 117 || npc.template.npcTemplateId == 119) && npc.maxhp == 5000)) {
                    message.writeByte(npc.levelBoss);
                    message.writeInt(npc.maxhp);
                    getSession().sendMessage(message);
                    try {
                        if (map != null) {
                            for (int k = 0; k < map.players.size(); ++k) {
                                try {
                                    if (map.players.get(k).playerId != playerId && npc.isHit(map.players.get(k))) {
                                        map.players.get(k).getSession().sendMessage(message);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                    } catch (Exception e) {
                    } finally {
                        message.cleanup();
                    }
                } else {
                    sendToPlayer(message, true);
                }
                if (npc.timeFire < timeFire) {
                    npc.timeFire = timeFire;
                    message = new Message(Cmd.NPC_IS_FIRE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeFire > 0);
                    map.sendToPlayer(message);
                }
                if (npc.timeIce < timeIce) {
                    npc.timeIce = timeIce;
                    message = new Message(Cmd.NPC_IS_ICE);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeIce > 0);
                    map.sendToPlayer(message);
                }
                if (npc.timeWind < timeWind) {
                    npc.timeWind = timeWind;
                    message = new Message(Cmd.NPC_IS_WIND);
                    message.writeByte(npc.npcId);
                    message.writeBoolean(npc.timeWind > 0);
                    map.sendToPlayer(message);
                }
                if (map.isDunClan) {
                    clan.sendAddExp(expAdd / (5 * deltaExp));
                } else if (map.isDunPB) {
                    ((DunPB) map).sendAddExp(expAdd / (2 * deltaExp), name);
                } else if (playerMain.party != null) {
                    playerMain.party.sendAddExp(this, expAdd / playerMain.party.players.size());
                }
                long exp = (playerMain.buffExp > 0) ? (expAdd + (long) expAdd * playerMain.buffExp / 100) : expAdd;
                sendUpdateExp(exp, true);
                return true;
            }
            if (npc.status == Npc.STATUS_DIE) {
                return false;
            }
            if (npc.isPrivateNpc()) {
                try {
                    npc.status = Npc.STATUS_DIE;
                    npcDie(npc, dameHit, isChiMang);
                } catch (Exception e) {
                }
                /*if (npc.template.npcTemplateId == 230 && playerMain.privateNpc != null) {
                    // drop here
                }*/
                playerMain.privateNpc = null;
                return false;
            }
            countMonsterKill();
            if (npc.template.npcTemplateId == 113) {
                int[] hits = new int[npc.hits.size()];
                for (int i = 0; i < npc.hits.size(); ++i) {
                    hits[i] = npc.hits.get(i).dame;
                }
                Player player = npc.hits.get(NJUtil.probability(hits)).player;
                if (player.map.isDunVA) {
                    player.addItemToBagNoDialog(new Item(288, true, "atk moc nhan"));
                    String[] strs = {
                        player.name + " " + Alert_VN.VUOT_AI_ALERT14,
                        player.name + " " + Alert_EN.VUOT_AI_ALERT14
                    };
                    map.sendAlert(strs);
                }
            }
            //Player pKill = npc.getOwnerPlayer();
            int ownerId = npc.getOwner();
            npc.status = Npc.STATUS_DIE;
            npc.hp = npc.template.hp;
            npc.hits.clear();
            npc.timeDisable = 0;
            npc.timeDontMove = 0;
            npc.timeFire = 0;
            npc.timeIce = 0;
            npc.timeWind = 0;
            if (npc.isTinhAnh()) {
                if (Math.abs(deltalv) <= 10) {
                    checkStepNguyeNhanTask(NguyetNhanTask.TYPE_TIEU_DIET_TINH_ANH, 1);
                }
            } else if (npc.isThuLinh()) {
                if (Math.abs(deltalv) <= 10) {
                    checkStepNguyeNhanTask(NguyetNhanTask.TYPE_TIEU_DIET_THU_LINH, 1);
                }
            } else if (Math.abs(deltalv) <= 10) {
                checkStepNguyeNhanTask(NguyetNhanTask.TYPE_GIET_QUAI, 1);
            }
            if (map.template.isMapChienTruong()) {
                if (!MixedArena.isMapArena(map.template.mapTemplateId)) {
                    int pointAdd = 1;
                    if (npc.isTinhAnh()) {
                        pointAdd = 10;
                    } else if (npc.isThuLinh()) {
                        pointAdd = 50;
                    }
                    if (npc.template.npcTemplateId == 98 || npc.template.npcTemplateId == 99) {
                        pointAdd = 150;
                    }
                    if (map.giatocchien != null) {
                        map.giatocchien.addPoint(name, pointAdd);
                        sendChientruong(map.giatocchien.getPointCT(name));
                    } else {
                        playerMain.pointCT += pointAdd;
                        playerMain.checkStepNguyeNhanTask(NguyetNhanTask.TYPE_KIEM_DIEM_CHIEN_TRUONG, pointAdd);
                        sendChientruong(playerMain.pointCT);
                    }
                }
            }
            if (map.isDunPB) {
                int deltaPointPB = 0;
                if (map.template.typeMap == MapTemplate.MAP_PB) {
                    deltaPointPB = 1;
                    DunPB dun = (DunPB) map;
                    if (npc.template.npcTemplateId == 88) {
                        deltaPointPB = NJUtil.randomNumber(15) + 5;
                    } else if (npc.template.npcTemplateId == 89) {
                        deltaPointPB = NJUtil.randomNumber(30) + 10;
                    } else if (npc.template.npcTemplateId == 105) {
                        deltaPointPB = NJUtil.randomNumber(50) + 50;
                    } else if (npc.template.npcTemplateId == 138) {
                        deltaPointPB = NJUtil.randomNumber(50) + 50;
                    } else if (dun.loop == 4 || dun.loop == 6) {
                        deltaPointPB = 2;
                    } else if (dun.loop == 8 || dun.loop == 10) {
                        deltaPointPB = 3;
                    } else if (dun.loop == 12 || dun.loop == 14) {
                        deltaPointPB = 4;
                    } else if (dun.loop > 14) {
                        deltaPointPB = 5;
                    }
                }
                if (map.isDunPb9x() && npc.nDropItem <= 0) {
                    deltaPointPB = 0;
                }
                if (deltaPointPB > 0) {
                    DunPB dunPB = ((DunPB) map).findDunPB();
                    try {
                        for (int i = 0; i < dunPB.nameIns.size(); ++i) {
                            try {
                                Player p2 = ServerController.hnPlayers.get(dunPB.nameIns.get(i));
                                if (p2 != null && p2.map.isDunPB) {
                                    p2.pointPB += deltaPointPB;
                                    p2.sendPointPB();
                                }
                            } catch (Exception e) {
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
            Player playerAttacck = ServerController.hpPlayers.get(ownerId);
            if (playerAttacck != null && playerAttacck.map.equals(map) && !map.template.isMapChienTruong()) {
                if (playerAttacck.equals(this)) {
                    if (npc.isTinhAnh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                        int yen = npc.randomMoney() * 5;
                        if (map.isDunClan) {
                            yen *= 5;
                            Item itTiengiatoc = new Item(262, true, "isHit char 36");
                            itTiengiatoc.quantity = NJUtil.randomNumber(10) + 11;
                            clan.sendAddItem(itTiengiatoc);
                        }
                        playerAttacck.sendUpdateCoinLockYen(yen);
                        NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    } else if (npc.isThuLinh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                        int yen = npc.randomMoney() * 20;
                        playerAttacck.sendUpdateCoinLockYen(yen);
                        NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    }
                } else if (npc.isTinhAnh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                    int yen = (int) (npc.randomMoney() * 2.5);
                    if (map.isDunClan) {
                        yen *= 5;
                        Item itTiengiatoc = new Item(262, true, "isHit char 5");
                        itTiengiatoc.quantity = NJUtil.randomNumber(10) + 11;
                        clan.sendAddItem(itTiengiatoc);
                    }
                    playerAttacck.sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(getSession(), AlertFunction.YOU_GET(getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(getSession()));
                } else if (npc.isThuLinh() && npc.template.npcTemplateId != 69 && npc.template.npcTemplateId != 71) {
                    int yen = npc.randomMoney() * 10;
                    playerAttacck.sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(playerAttacck.getSession(), AlertFunction.YOU_GET(playerAttacck.getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(playerAttacck.getSession()));
                    sendUpdateCoinLockYen(yen);
                    NJUtil.sendServer(getSession(), AlertFunction.YOU_GET(getSession()) + " " + yen + " " + AlertFunction.COIN_LOCK(getSession()));
                }
            }
            if (MixedArena.isMapArena(map.template.mapTemplateId)) {
                MixedArena.updatePointKillMob(this, npc);
            }
            if (playerAttacck != null && playerAttacck.dailytask != null && playerAttacck.dailytask.template.checkTask(playerAttacck, 4, npc.template.npcTemplateId)) {
                playerAttacck.checkTaskOrder(playerAttacck.dailytask, 1);
            }
            if (map.getTemplateId() == 130) { // kẹo chiến
                if (NJUtil.probability(10, 90) == 0) {
                    Item it = new Item(458, false, "isHit char 32");
                    ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                    itMap.y = autoFall(itMap.x, itMap.y);
                    map.addItemMap(itMap);
                    map.sendAddItemMap(itMap);
                }
            }
            ArrayList<DropRate> drops = new ArrayList<>();
            drops.add(new DropRate(1, 0)); // ko rơi
            drops.add(new DropRate(2, 1)); // yên
            drops.add(new DropRate(2, 2)); // hp mp
            drops.add(new DropRate(3, 3)); // đá
            drops.add(new DropRate(4, 4)); // trang bị
            int idGet = NJUtil.dropItem(drops);
            if (npc.isTinhAnh() || npc.isThuLinh()) {
                idGet = 3;
                if (npc.isTinhAnh()) {
                    ++map.maxBoss1;
                } else {
                    ++map.maxBoss2;
                }
            }
            if (map.isDunClan && npc.isTinhAnh()) {
                idGet = 99;
            }
            try {
                if (EventManager.getInstance().isMobEvent(npc) && Math.abs(playerMain.level - npc.level) <= 10 && playerAttacck != null) {
                    Vector<Item> rewards = EventManager.getInstance().killMobRewards(playerAttacck, npc);
                    if (rewards != null) {
                        for (Item it : rewards) {
                            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                            itMap.y = autoFall(itMap.x, itMap.y);
                            map.addItemMap(itMap);
                            map.sendAddItemMap(itMap);
                        }
                    }
                }
            } catch (Exception e) {
            }
            Item item = null;
            if (Math.abs(playerMain.level - npc.level) <= 7 || idGet == 99 || idGet == 0) {
                switch (idGet) {
                    case 0: // dơi lửa nv
                        if (npc.template.npcTemplateId == 41 && NJUtil.randomBoolean()) {
                            item = new Item(238, true, "xac doi lua nv");
                        }
                        break;
                    case 1: // yên
                        if ((map.isDunClan && npc.isTinhAnh()) || Math.abs(playerMain.level - npc.level) <= 7) {
                            item = new Item(12);
                            item.quantity = npc.randomMoney();
                            item.isLock = true;
                        }
                        break;
                    case 2: // random potion
                        int potion = NJUtil.randomBoolean() ? npc.throwItemHP() : npc.throwItemMP();
                        item = new Item(potion);
                        break;
                    case 3: // stone
                        if (npc.isThuLinh()) {
                            item = new Item(npc.randomThrowHT(true));
                        } else {
                            item = new Item(npc.randomThrowHT(false));
                        }
                        item.isLock = true;
                        break;
                    case 4: // random armor
                        item = new Item(npc.randomItemBody());
                        item.sys = NJUtil.randomNumber(3) + 1;
                        item = Item.getOptionThrow(item);
                        if (item.options == null) {
                            item = null;
                        }
                        break;
                    case 99: // chìa khoá gia tộc
                        item = new Item(260, false, "chia khoa gt");
                        break;
                }
            }
            if (item != null && item.getTemplateId() != 260 && item.getTemplateId() != 530 && map.isDunClan) {
                if (NJUtil.randomBoolean()) {
                    item = new Item(12, true, "isHit char 40");
                    item.quantity = npc.randomMoney() * 5;
                } else {
                    item = new Item(npc.randomThrowHT(false) + 1, true, "isHit char 41");
                }
            }
            if (npc.template.npcTemplateId == 0 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 2 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 1 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 2 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 3 && playerMain.taskMain.index == 3) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 3 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 4 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(209, true, "isHit char 42");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 4 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 4 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(210, true, "isHit char 43");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 54 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 5 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(211, true, "isHit char 44");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 81) {
                item = new Item(261, true, "isHit char 45");
            } else if (npc.template.npcTemplateId == 5 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 0) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 6 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 7 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 10 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 15 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 14 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(213, true, "isHit char 46");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 23 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 16 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 24 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 16 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 26 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 18 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(216, true, "isHit char 47");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 27 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 18 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(217, true, "isHit char 48");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId >= 5 && npc.template.npcTemplateId <= 14 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 12 && playerMain.taskMain.index >= 1 && playerMain.taskMain.index <= 3) {
                if (NJUtil.probability(15, 85) == 0) {
                    if (NJUtil.randomNumber(2) == 0) {
                        item = new Item((playerMain.gender == 1) ? 124 : 125, true, "isHit char 49");
                    } else {
                        item = new Item(174, true, "isHit char 50");
                    }
                    item.sys = NJUtil.randomNumber(3) + 1;
                    item = Item.getOptionThrow(item);
                    if (item.options == null) {
                        item = null;
                    }
                }
            } else if (npc.template.npcTemplateId == 69 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 20 && playerMain.taskMain.index == 1) {
                item = new Item(221, true, "isHit char 51");
                NJUtil.sendServer(getSession(), AlertFunction.LIMIT_TIME_20(getSession()));
                sendMapTime(20);
                ((Dun) map).timeEnd = System.currentTimeMillis() + 20000L;
            } else if (npc.template.npcTemplateId == 30 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 21 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 31 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 21 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 33 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 22 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(230, true, "isHit char 52");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 37 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 25 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 38 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 25 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 42 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 28 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 43 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 28 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 44 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 29 && playerMain.taskMain.index == 1 && npc.isTinhAnh()) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 47 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 31 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(264, true, "isHit char 53");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 48 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 31 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(265, true, "isHit char 54");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 51 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 33 && playerMain.taskMain.index == 1) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 52 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 33 && playerMain.taskMain.index == 2) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 57 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 36 && playerMain.taskMain.index == 1) {
                addItemToBagNoDialog(new Item(348, true, "isHit char 55"));
                doTaskUpdate((short) countItemBag(348));
                if (playerMain.taskMain.count >= playerMain.taskMain.template.counts[playerMain.taskMain.index]) {
                    doTaskNext();
                }
                Message message = NJUtil.messageNotMap(Cmd.OAN_HON);
                message.writeByte(npc.npcId);
                message.writeInt(playerId);
                sendToPlayer(message, true);
            } else if (npc.template.npcTemplateId == 58 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 37 && playerMain.taskMain.index == 1 && npc.isTinhAnh()) {
                if (playerMain.party != null) {
                    playerMain.party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 59 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 37 && playerMain.taskMain.index == 2 && npc.levelBoss == 2) {
                if (party != null) {
                    party.sendTaskAtt(this);
                }
                checkTaskIndex();
            } else if (npc.template.npcTemplateId == 60 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 38 && playerMain.taskMain.index == 1) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(349, true, "isHit char 56");
                } else {
                    item = null;
                }
            } else if (npc.template.npcTemplateId == 61 && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 38 && playerMain.taskMain.index == 2) {
                if (NJUtil.randomNumber(2) == 0) {
                    item = new Item(350, true, "isHit char 57");
                } else {
                    item = null;
                }
            }
            boolean condition = npc.template.npcTemplateId == 54 && (playerMain.taskMain == null || playerMain.taskMain.template.taskId != 5 || playerMain.taskMain.index != 1);
            if (npc.template.npcTemplateId == 0 || condition || map.template.isMapChienTruong()) {
                item = null;
            }
            checkTaskDay(npc);
            if (playerAttacck != null) {
                playerAttacck.checkTaskBoos(npc);
                if (playerMain.party != null) {
                    playerMain.party.sendUpdateTaskDay(this, npc);
                }
                if (playerAttacck.party != null) {
                    playerAttacck.party.sendUpdateTaskBoss(playerAttacck, npc);
                }
            }
            long tt2 = System.currentTimeMillis() - t;
            if (t2 > 200L) {
                LOGGER.warn("{}. Mess danh cham 2: {}", getStringBaseInfo(), tt2);
            }
            if (map.isDunClan) {
                if (!npc.isBoss() && npc.template.npcTemplateId != 81) {
                    Item itTienGiaToc = new Item(262, true, "isHit char 58");
                    clan.sendAddItem(itTienGiaToc);
                } else if (npc.template.npcTemplateId == 82) {
                    Item itTienGiaToc = new Item(262, true, "isHit char 59");
                    itTienGiaToc.quantity = NJUtil.randomNumber(40) + 21;
                    ghiloghack(itTienGiaToc.getTemplateId());
                    clan.sendAddItem(itTienGiaToc);
                    int max = NJUtil.randomNumber(6) + 10;
                    for (int i = 0; i < max; ++i) {
                        int[] ids = { 12, 2, 3, 4, 5, 6 };
                        int[] pers = { 40, 40, 30, 20, 10, 5 };
                        Item itBoss = new Item(ids[NJUtil.probability(pers)], false, "isHit char 60");
                        if (itBoss.getTemplateId() == 12) {
                            itBoss.quantity = NJUtil.randomNumber(npc.randomMoney() * 12);
                        }
                        ItemMap itBoos = new ItemMap(itBoss, -1, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                        itBoos.y = autoFall(itBoos.x, itBoos.y);
                        map.addItemMap(itBoos);
                        map.sendAddItemMap(itBoos);
                    }
                    clan.exp += 500;
                    clan.closeDun();
                    Database.saveClan(clan);
                    String[] strs = {
                        Alert_VN.YOU_GET + " 500 " + Alert_VN.POINT_CLAN,
                        Alert_EN.YOU_GET + " 500 " + Alert_EN.POINT_CLAN
                    };
                    clan.sendAlert(strs, name);
                    sendServerMessage(strs);
                }
            } else if (map.isDunPB) {
                // phó bản 9x, boss 3 anh em
                if (npc.template.npcTemplateId == 198 || npc.template.npcTemplateId == 199 || npc.template.npcTemplateId == 200) {
                    throwItemBossPB(npc, -1);
                }
            } else if (map.isVungDatMaQuy()) {
                // 201_Kền Kền Vương | 203_Đại Lực Sĩ | 204_U Minh Khuyển
                if (npc.template.npcTemplateId == 201 || npc.template.npcTemplateId == 203 || npc.template.npcTemplateId == 204) {
                    throwItemBossVDMQ(npc, -1);
                } else if (NJUtil.random.nextInt(10000) < 750 && isUsingKhaiThienNhanPhu() != null && Math.abs(deltalv) <= 10) {
                    throwItemMobVDMQ(npc, ownerId);
                }
            } else if (map.isLangCo()) {
                // 161_Tử Lôi Diệu Thiên Long | 163_Phù thủy Bí Ngô | 162_Hỏa Kỳ Lân | 164_Băng Đế
                if (npc.template.npcTemplateId == 161 || npc.template.npcTemplateId == 162 || npc.template.npcTemplateId == 163 || npc.template.npcTemplateId == 164) {
                    throwItemBossLC(npc, -1);
                } else {
                    throwItemMobLC(npc, ownerId);
                }
            } else if (npc.template.npcTemplateId == 114) { // Thần thố
                throwItemBoss(npc, 1, -1);
            } else if (npc.template.npcTemplateId == 115) { // Xích phiến thiên long
                throwItemBoss(npc, 2, -1);
            } else if (npc.template.npcTemplateId == 116) { // Samurai chiến tướng
                throwItemBoss(npc, 3, -1);
            } else if (npc.template.npcTemplateId == 139) { // Hỏa ngưu vương
                throwItemBoss(npc, 4, -1);
            }
            if (npc.template.isBossId() && playerAttacck != null) {
                String alert = playerAttacck.name + " đã tiêu diệt " + npc.template.name + ", khiến mọi người đều ngưỡng mộ!!!";
                LOGGER.info(playerAttacck.name + " killed " + npc.template.name_en);
                NJUtil.sendServerAlert(alert);
            }
            if (item != null) {
                if (npc.isBoss() && item.template.type == 26) {
                    item.isLock = false;
                } else if (item.template.type == 25 || item.template.type == 23 || item.template.type == 24) {
                    item.isLock = true;
                }
                item.updateSale();
                item.typeUI = 3;
                int d = NJUtil.randomNumber(15);
                ItemMap itemMap = new ItemMap(item, ownerId, npc.pointx + ((map.tick % 2 == 0) ? d : (-d)), npc.pointy);
                if (item.getTemplateId() == 260) {
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByMinute(5);
                    itemMap.owner = -1;
                } else if (item.getTemplateId() == 261) {
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByMinute(30);
                    itemMap.owner = -1;
                } else {
                    item.expires = -1L;
                }
                itemMap.y = autoFall(itemMap.x, itemMap.y);
                map.addItemMap(itemMap);
                Message message = new Message(Cmd.NPC_DIE);
                message.writeByte(npc.npcId);
                message.writeInt(dameHit);
                message.writeBoolean(isChiMang);
                message.writeShort(itemMap.itemMapId);
                message.writeShort(itemMap.item.template.itemTemplateId);
                message.writeShort(itemMap.x);
                message.writeShort(itemMap.y);
                sendToPlayer(message, true);
                if (npc.template.npcTemplateId == 112) {
                    int countDie = 0;
                    for (int i = 0; i < map.npcs.size(); ++i) {
                        if (map.npcs.get(i).status == ME_NORMAL) {
                            ++countDie;
                        }
                    }
                    if (countDie > 0 && countDie == map.npcs.size()) {
                        addItemToBagNoDialog(new Item(288, true, "isHit char 110"));
                    }
                }
            } else {
                npcDie(npc, dameHit, isChiMang);
            }
            if (map.isDunPB && npc.nDropItem > 0) {
                --npc.nDropItem;
            }
            if (playerMain.party != null) {
                playerMain.party.sendAddExp(this, expAdd / 6);
            }
            sendUpdateExp(expAdd, true);
        } catch (Exception e) {
            if (GameServer.isServerLocal()) {
                LOGGER.error("", e);
            }
        }
        return false;
    }

    public void throwItemBoss(Npc npc, int type, int ownerId) {
        // đá
        for (int i = 0; i < type * 10; i++) {
            Item it = new Item(NJUtil.randomMinMax(5, 9), false, "throwItemBoss 1");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // yên
        for (int i = 0; i < type * 10; ++i) {
            Item it = new Item(12, false, "throwItemBoss 2");
            it.quantity = NJUtil.randomMinMax(40000, 60000);
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // svc 10x
        if (npc.level >= 60 || NJUtil.random.nextInt(1000) < 300) {
            for (int i = 0; i < type * 2; ++i) {
                Item it = new Item(558 + NJUtil.randomNumber(6), false, "throwItemBoss 3");
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
        // weapons
        for (int i = 0; i < 5; ++i) {
            Item it = null;
            ArrayList<DropRate> drops = new ArrayList<>();
            drops.add(new DropRate(1, -3));
            drops.add(new DropRate(1, -2));
            drops.add(new DropRate(1, -1));
            switch (NJUtil.dropItem(drops)) {
                case -3:
                    it = ServerController.shopVuKhis.get(NJUtil.randomNumber(ServerController.shopVuKhis.size())).cloneRandom();
                    break;
                case -2:
                    it = ServerController.shopTrangsucs.get(NJUtil.randomNumber(ServerController.shopTrangsucs.size())).cloneRandom();
                    break;
                case -1:
                    it = ServerController.shopPhongcus.get(NJUtil.randomNumber(ServerController.shopPhongcus.size())).cloneRandom();
                    break;
            }
            if (it != null) {
                int rand = NJUtil.randomNumber(1000);
                if (rand < 10) {
                    it.upgrade = 12 + NJUtil.randomNumber(2);
                } else if (rand < 200) {
                    it.upgrade = 10 + NJUtil.randomNumber(2);
                }
                changeItemOption(it, it.upgrade);
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
    }

    public void throwItemBossPB(Npc npc, int ownerId) {
        if (map.isDunPB && npc.nDropItem > 0) {
            for (int i = 0; i < NJUtil.randomMinMax(15, 30); ++i) {
                Item it = new Item(12, false, "throwItemBossPB 1");
                it.quantity = NJUtil.randomMinMax(40000, 60000);
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
    }

    public void throwItemBossVDMQ(Npc npc, int ownerId) {
        // yên
        for (int i = 0; i < NJUtil.randomMinMax(20, 50); i++) {
            Item it = new Item(12, false, "throwItemBossVDMQ 1");
            it.quantity = NJUtil.randomMinMax(40000, 60000);
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // ttts
        for (int i = 0; i < NJUtil.randomMinMax(5, 10); i++) {
            Item it = new Item(Item.TU_TINH_THACH_SO, false, "throwItemBossVDMQ 2");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // tttt
        for (int i = 0; i < NJUtil.randomMinMax(5, 15); i++) {
            Item it = new Item(Item.TU_TINH_THACH_TRUNG, false, "throwItemBossVDMQ 3");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // ctt
        for (int i = 0; i < NJUtil.randomMinMax(5, 10); i++) {
            Item it = new Item(Item.CHUYEN_TINH_THACH, false, "throwItemBossVDMQ 4");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // exp thú
        for (int i = 0; i < NJUtil.randomMinMax(10, 20); i++) {
            Item it = new Item(NJUtil.randomMinMax(573, 578), false, "throwItemBossVDMQ 5");
            it.quantity = 10;
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // harley
        for (int i = 0; i < NJUtil.randomMinMax(1, 2); i++) {
            Item it = new Item(524, false, "throwItemBossVDMQ 6");
            it.options = new Vector<>();
            ItemOption option = new ItemOption();
            option.param = 0;
            option.optionTemplate = ServerController.iOptionTemplates.get(65);
            it.options.add(option);
            option = new ItemOption();
            option.param = 1000;
            option.optionTemplate = ServerController.iOptionTemplates.get(66);
            it.options.add(option);
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // svc 9-10x
        for (int i = 0; i < NJUtil.randomMinMax(1, 3); ++i) {
            if (npc.level >= 90 && NJUtil.random.nextInt(1000) < 300) {
                Item it = new Item(547, false, "throwItemBossVDMQ 7");
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
            if (npc.level >= 100 && NJUtil.random.nextInt(1000) < 300) {
                Item it = new Item(558 + NJUtil.randomNumber(6), false, "throwItemBossVDMQ 8");
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
        // ptl
        for (int i = 0; i < NJUtil.randomMinMax(5, 10); ++i) {
            Item it = new Item(545, false, "throwItemBossVDMQ 9");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
    }

    public void throwItemMobVDMQ(Npc npc, int ownerId) {
        ArrayList<DropRate> drops = new ArrayList<>();
        // trang bị thú cưỡi
        drops.add(new DropRate(4, 439));
        drops.add(new DropRate(4, 440));
        drops.add(new DropRate(4, 441));
        drops.add(new DropRate(4, 442));
        drops.add(new DropRate(4, 486));
        drops.add(new DropRate(4, 487));
        drops.add(new DropRate(4, 488));
        drops.add(new DropRate(4, 489));
        // exp thú
        drops.add(new DropRate(1, 573));
        drops.add(new DropRate(2, 574));
        drops.add(new DropRate(3, 575));
        drops.add(new DropRate(1, 576));
        drops.add(new DropRate(2, 577));
        drops.add(new DropRate(3, 578));
        // item tinh luyện
        drops.add(new DropRate(4, Item.CHUYEN_TINH_THACH));
        drops.add(new DropRate(2, Item.TU_TINH_THACH_SO));
        drops.add(new DropRate(3, Item.TU_TINH_THACH_TRUNG));
        int itemId = NJUtil.dropItem(drops);
        if (itemId >= 0) {
            Item it = new Item(itemId, false, "throwItemMobVDMQ 1");
            if (it.isTrangBiThu()) {
                it.randomGetOptionTrangBiThu(true);
            }
            if (it.isDaTinhLuyen()) {
                it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(30);
            }
            if (it.isThucAnSoi() || it.isNhienLieuXe()) {
                it.quantity = NJUtil.randomMinMax(1, 3);
            }
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
    }

    public void throwItemBossLC(Npc npc, int ownerId) {
        for (int i = 0; i < NJUtil.randomMinMax(30, 50); i++) {
            Item it = new Item(12, false, "throwItemBossLC 1");
            it.quantity = NJUtil.randomMinMax(40000, 60000);
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // ttts
        for (int i = 0; i < NJUtil.randomMinMax(5, 15); i++) {
            Item it = new Item(Item.TU_TINH_THACH_SO, false, "throwItemBossLC 2");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // tttt
        for (int i = 0; i < NJUtil.randomMinMax(5, 20); i++) {
            Item it = new Item(Item.TU_TINH_THACH_TRUNG, false, "throwItemBossLC 3");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // ctt
        for (int i = 0; i < NJUtil.randomMinMax(5, 15); i++) {
            Item it = new Item(Item.CHUYEN_TINH_THACH, false, "throwItemBossLC 4");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // exp thú
        for (int i = 0; i < NJUtil.randomMinMax(5, 15); i++) {
            Item it = new Item(NJUtil.randomMinMax(573, 578), false, "throwItemBossLC 5");
            it.quantity = 5;
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        for (int i = 0; i < NJUtil.randomMinMax(1, 3); i++) {
            Item it = new Item(524, false, "throwItemBossLC 6");
            it.options = new Vector<>();
            ItemOption option = new ItemOption();
            option.param = 0;
            option.optionTemplate = ServerController.iOptionTemplates.get(65);
            it.options.add(option);
            option = new ItemOption();
            option.param = 1000;
            option.optionTemplate = ServerController.iOptionTemplates.get(66);
            it.options.add(option);
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
        // svc 9-10x
        for (int i = 0; i < NJUtil.randomMinMax(1, 3); ++i) {
            if (npc.level >= 90 && NJUtil.random.nextInt(1000) < 300) {
                Item it = new Item(547, false, "throwItemBossLC 7");
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
            if (npc.level >= 100 && NJUtil.random.nextInt(1000) < 300) {
                Item it = new Item(558 + NJUtil.randomNumber(6), false, "throwItemBossLC 8");
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
        // ptl
        for (int i = 0; i < NJUtil.randomMinMax(5, 10); ++i) {
            Item it = new Item(545, false, "throwItemBossLC 9");
            ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
            itMap.y = autoFall(itMap.x, itMap.y);
            map.addItemMap(itMap);
            map.sendAddItemMap(itMap);
        }
    }

    public void throwItemMobLC(Npc npc, int ownerId) {
        if (NJUtil.random.nextInt(10000) < 1500) {
            ArrayList<DropRate> drops = new ArrayList<>();
            // trang bị thú cưỡi
            drops.add(new DropRate(4, 439));
            drops.add(new DropRate(4, 440));
            drops.add(new DropRate(4, 441));
            drops.add(new DropRate(4, 442));
            drops.add(new DropRate(4, 486));
            drops.add(new DropRate(4, 487));
            drops.add(new DropRate(4, 488));
            drops.add(new DropRate(4, 489));
            // exp thú
            drops.add(new DropRate(1, 573));
            drops.add(new DropRate(2, 574));
            drops.add(new DropRate(3, 575));
            drops.add(new DropRate(1, 576));
            drops.add(new DropRate(2, 577));
            drops.add(new DropRate(3, 578));
            // item tinh luyện
            drops.add(new DropRate(3, Item.CHUYEN_TINH_THACH));
            drops.add(new DropRate(2, Item.TU_TINH_THACH_SO));
            drops.add(new DropRate(4, Item.TU_TINH_THACH_TRUNG));
            int itemId = NJUtil.dropItem(drops);
            if (itemId >= 0) {
                Item it = new Item(itemId, false, "throwItemMobLC 2");
                if (it.isTrangBiThu()) {
                    it.randomGetOptionTrangBiThu(true);
                }
                if (it.isDaTinhLuyen()) {
                    it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(30);
                }
                if (it.isThucAnSoi() || it.isNhienLieuXe()) {
                    it.quantity = NJUtil.randomMinMax(1, 3);
                }
                ItemMap itMap = new ItemMap(it, ownerId, npc.minX + NJUtil.randomNumber(npc.maxX - npc.minX), npc.pointy);
                itMap.y = autoFall(itMap.x, itMap.y);
                map.addItemMap(itMap);
                map.sendAddItemMap(itMap);
            }
        }
    }

    public boolean isItemToBag(Vector<Item> itemAdds, Vector<Item> itemTrades) {
        if (itemAdds == null) {
            return false;
        }
        Item[] bags = new Item[itemBags.length];
        System.arraycopy(itemBags, 0, bags, 0, bags.length);
        for (Item item : itemAdds) {
            if (item != null && item.template.isUpToUp && item.expires == -1L) {
                int indexUI = -1;
                boolean isUpdate = false;
                for (int j = 0; j < bags.length; ++j) {
                    if (bags[j] != null && bags[j].template.itemTemplateId == item.template.itemTemplateId && bags[j].isLock == item.isLock) {
                        boolean isOk = true;
                        for (Item itemTrade : itemTrades) {
                            if (itemTrade != null && j == itemTrade.indexUI) {
                                isOk = false;
                                break;
                            }
                        }
                        if (isOk) {
                            isUpdate = true;
                            break;
                        }
                    }
                    if (indexUI == -1 && bags[j] == null) {
                        boolean isOk = true;
                        for (Item itemTrade : itemTrades) {
                            if (itemTrade != null && j == itemTrade.indexUI) {
                                isOk = false;
                                break;
                            }
                        }
                        if (isOk) {
                            indexUI = j;
                        }
                    }
                }
                if (!isUpdate) {
                    if (indexUI == -1) {
                        return false;
                    }
                    bags[indexUI] = item;
                }
            } else {
                boolean isAdd = false;
                for (int l = 0; l < bags.length; ++l) {
                    if (bags[l] == null) {
                        bags[l] = item;
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isKhangDoSat() {
        return itemBodys[10] != null && (itemBodys[10].ispetTuanLocKhangDs() || itemBodys[10].ispetReBiHanhKhangDs() || itemBodys[10].isPetBoru());
    }

    public boolean isLevelUp(long expAdd) {
        if (dailytask != null && dailytask.template.checkTask(this, 5)) {
            checkTaskOrder(dailytask, (int) expAdd);
        }
        Player playerMain = getPlayerMainControl();
        int currentLevel = playerMain.level;
        playerMain.exp += expAdd;
        playerMain.level = playerMain.getLevel();
        int levelAdd = playerMain.level - currentLevel;
        if (levelAdd > 0) {
            if (playerMain.classId == 0) {
                playerMain.p_strength += (short) (levelAdd * 5);
                playerMain.p_agile += (short) (levelAdd * 2);
                playerMain.p_hp += levelAdd * 2;
                playerMain.p_mp += levelAdd * 2;
                playerMain.updateData();
            } else {
                playerMain.skill_point += (short) levelAdd;
                for (int i = currentLevel + 1; i <= playerMain.level; ++i) {
                    if (i >= 70 && i < 80) {
                        playerMain.potential_point += 20;
                    } else if (i >= 80 && i < 90) {
                        playerMain.potential_point += 30;
                    } else if (i >= 90) {
                        playerMain.potential_point += 50;
                    } else {
                        playerMain.potential_point += 10;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isLockAtt() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.type == 2 || playerMain.effects.get(i).template.type == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isLockUseItem() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.type == 3) {
                return true;
            }
        }
        return false;
    }

    public boolean isNhanban() {
        return false;
    }

    public boolean isNotEditParty() {
        if (map.getTemplateId() == 110 || map.getTemplateId() == 111) {
            NJUtil.sendServer(getSession(), AlertFunction.HEPL_TEST_DUN5(getSession()));
            return true;
        }
        return false;
    }

    public boolean isNotEditParty2() {
        if (map.getTemplateId() == 110 || map.getTemplateId() == 111) {
            NJUtil.sendServer(getSession(), AlertFunction.HEPL_TEST_DUN12(getSession()));
            return true;
        }
        return false;
    }

    public boolean isNpcOk(int playerTemplateId) {
        for (int i = 0; i < map.npcPlayers.size(); ++i) {
            if (map.npcPlayers.get(i).template.playerTemplateId == playerTemplateId && NJUtil.distance(x, y, map.npcPlayers.get(i).x, map.npcPlayers.get(i).y) <= 100) {
                return true;
            }
        }
        return false;
    }

    public boolean isOk(int npcTemplateId) {
        for (NpcPlayer npc : map.npcPlayers) {
            if (npc != null && npc.template.playerTemplateId == npcTemplateId && NJUtil.distance(x, y, npc.x, npc.y) <= 100) {
                return true;
            }
        }
        return false;
    }

    public boolean isOkZone() {
        boolean isOk = false;
        for (Item itemBag : itemBags) {
            if ((itemBag != null && itemBag.getTemplateId() == 35) || (itemBag != null && itemBag.getTemplateId() == 37)) {
                isOk = true;
                break;
            }
        }
        if (!isOk) {
            for (int i = 0; i < map.npcPlayers.size(); ++i) {
                if (map.npcPlayers.get(i).template.playerTemplateId == 13 && NJUtil.distance(x, y, map.npcPlayers.get(i).x, map.npcPlayers.get(i).y) <= 100) {
                    isOk = true;
                    break;
                }
            }
        }
        return isOk;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    public boolean isPlayerArena() {
        return false;
    }

    public boolean isSysOut() {
        return classId == 0 || classId == 1 || classId == 3 || classId == 5;
    }

    public boolean isTeam(Player p) {
        if (party != null) {
            for (int i = 0; i < party.players.size(); ++i) {
                Player pInParty = party.players.get(i);
                if (pInParty.equals(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isKiem() {
        return classId == 1;
    }

    public boolean isTieu() {
        return classId == 2;
    }

    public boolean isKunai() {
        return classId == 3;
    }

    public boolean isCung() {
        return classId == 4;
    }

    public boolean isDao() {
        return classId == 5;
    }

    public boolean isQuat() {
        return classId == 6;
    }

    public boolean isTruongHirosaki() {
        return isKiem() || isTieu();
    }

    public boolean isTruongOokaza() {
        return isKunai() || isCung();
    }

    public boolean isTruongHaruna() {
        return isDao() || isQuat();
    }

    public boolean isUp(Item it1, ItemOption itOp) {
        if (getYen() - Item.yenTinhLuyen[itOp.param] < 0) {
            int co = Item.yenTinhLuyen[itOp.param] - getYen();
            sendUpdateCoinLockYen(-getYen());
            sendUpdateCoinBag(-co);
        } else {
            sendUpdateCoinLockYen(-Item.yenTinhLuyen[itOp.param]);
        }
        int[] pers = { 60, 45, 34, 26, 20, 15, 11, 8, 6 };
        it1.isLock = true;
        updateAll();
        if (NJUtil.probability(pers[itOp.param], 100 - pers[itOp.param]) == 0) {
            ++itOp.param;
            NJUtil.sendServer(getSession(), "Vật phẩm đã được tinh luyện thành công.");
            try {
                Message message = new Message(Cmd.DOI_OPTION);
                message.writeByte(it1.indexUI);
                message.writeByte(it1.upgrade);
                NJUtil.sendMessage(getSession(), message);
            } catch (Exception e) {
            }
            int[] idOption = { 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97 };
            for (int i = 0; i < it1.options.size(); ++i) {
                ItemOption optionTinhluyen;
                for (int jj = 0; jj < idOption.length; ++jj) {
                    if (it1.options.get(i).optionTemplate.itemOptionTemplateId == idOption[jj]) {
                        optionTinhluyen = it1.options.get(i);
                        ItemOption itemOption = optionTinhluyen;
                        itemOption.param += opsTinhLuyen[jj][itOp.param];
                        break;
                    }
                }
            }
            return true;
        }
        NJUtil.sendServer(getSession(), "Tinh luyện thất bại.");
        return false;
    }

    public boolean isUseFood(int id) {
        for (Effect effect : effects) {
            if (effect.template.type == 0 && effect.template.id > id) {
                return true;
            }
        }
        return false;
    }

    public Effect isUsingKhaiThienNhanPhu() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 40 || playerMain.effects.get(i).template.id == 41) {
                return playerMain.effects.get(i);
            }
        }
        return null;
    }

    public Effect isUsingThiLuyen() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 34) {
                return playerMain.effects.get(i);
            }
        }
        return null;
    }

    public Effect isUsingX2() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 22) {
                return playerMain.effects.get(i);
            }
        }
        return null;
    }

    public Effect isUsingX3() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 32) {
                return playerMain.effects.get(i);
            }
        }
        return null;
    }

    public Effect isUsingX4() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            if (playerMain.effects.get(i).template.id == 33) {
                return playerMain.effects.get(i);
            }
        }
        return null;
    }

    public void itemBagToBox(int bagIndex, int boxIndex) {
        try {
            Message message = new Message(Cmd.ITEM_BAG_TO_BOX);
            message.writeByte(bagIndex);
            message.writeByte(boxIndex);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void itemBodyToBag(Item item, int bagUI) {
        try {
            Player playerMain = getPlayerMainControl();
            removeItem(item, item.typeUI);
            playerMain.updateData();
            Message message = new Message(Cmd.ITEM_BODY_TO_BAG);
            writeParam(message);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeByte(item.indexUI);
            message.writeByte(bagUI);
            message.writeShort(playerMain.headId);
            NJUtil.sendMessage(getSession(), message);
            changeNpcMe();
            if (item.template.type == 1) {
                playerLoadVuKhi(-1);
            } else if (item.template.type == 2) {
                playerLoadAo(-1);
            } else if (item.template.type == 6) {
                playerLoadQuan(-1);
            } else if (item.template.type == 10) {
                playerLoadThuNuoi(-1, 0);
            } else if (item.template.type == 12) {
                playerLoadAoChoang(this, -1);
            } else if (item.template.type == 13) {
                playerLoadGiaToc(this, -1);
            } else if (item.template.type == 11) {
                playerLoadMatna(headId);
            } else {
                playerLoadBody();
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void itemBoxToBag(int boxIndex, int bagIndex) {
        try {
            Message message = new Message(Cmd.ITEM_BOX_TO_BAG);
            message.writeByte(boxIndex);
            message.writeByte(bagIndex);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void itemMonToBag(Item item, int bagUI) {
        try {
            Player playerMain = getPlayerMainControl();
            removeItem(item, item.typeUI);
            playerMain.updateData();
            Message message = new Message(Cmd.ITEM_MON_TO_BAG);
            writeParam(message);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeByte(item.indexUI);
            message.writeByte(bagUI);
            NJUtil.sendMessage(getSession(), message);
            playerLoadBody();
            loadThuCuoi(1);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void khoaNhanVat() {
        ban = true;
        getSession().disconnect("Player.khoaNhanVat");
    }

    public void loadAoChoang() {
        try {
            Player pp = getPlayerMainControl();
            if (pp.itemBodys != null && pp.itemBodys[12] != null) {
                Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_AO_CHOANG);
                message.writeInt(playerId);
                message.writeInt(pp.hp);
                message.writeInt(pp.hp_full);
                message.writeShort(pp.itemBodys[12].template.itemTemplateId);
                sendToPlayer(message, false);
            } else if (pp.itemBodys != null) {
                try {
                    Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_AO_CHOANG);
                    message.writeInt(playerId);
                    message.writeInt(pp.hp);
                    message.writeInt(pp.hp_full);
                    message.writeShort(-1);
                    sendToPlayer(message, false);
                } catch (IOException e) {
                    LOGGER.error(getStringBaseInfo(), e);
                }
            }
            if (pp.itemBodys != null && pp.itemBodys[13] != null) {
                Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_GIA_TOC);
                message.writeInt(playerId);
                message.writeInt(pp.hp);
                message.writeInt(pp.hp_full);
                message.writeShort(pp.itemBodys[13].template.itemTemplateId);
                sendToPlayer(message, false);
            } else if (pp.itemBodys != null) {
                Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_GIA_TOC);
                message.writeInt(playerId);
                message.writeInt(pp.hp);
                message.writeInt(pp.hp_full);
                message.writeShort(-1);
                sendToPlayer(message, false);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void loadRms(Message message) {
        try {
            String key = message.readUTF();
            if (key.equals("OSkillnhanban")) {
                key = "OSkill";
            }
            if (key.equals("KSkillnhanban")) {
                key = "KSkill";
            }
            if (key.equals("CSkillnhanban")) {
                key = "CSkill";
            }
            int varMainChar = isMainchar ? 0 : 1;
            int varMainChar2 = isMainchar ? 1 : 0;
            if (dataRms[0][getindexRms.get(key)] == null) {
                String finalKey = key;
                ThreadPool.getInstance().workList.add(() -> {
                    Message msg;
                    try {
                        byte[] value = Database.getRms(playerId, finalKey, isMainchar);
                        if (value == null) {
                            value = new byte[0];
                        }
                        dataRms[varMainChar][getindexRms.get(finalKey)] = value;
                        msg = NJUtil.messageSubCommand(Cmd.LOAD_RMS);
                        msg.writeUTF(finalKey);
                        msg.writeBytes(value);
                        msg.writeByte(varMainChar);
                        NJUtil.sendMessage(getSession(), msg);
                        dataRms[varMainChar2][getindexRms.get(finalKey)] = Database.getRms(playerId, finalKey, !isMainchar);
                        if (dataRms[varMainChar2][getindexRms.get(finalKey)] == null) {
                            dataRms[varMainChar2][getindexRms.get(finalKey)] = new byte[0];
                        }
                    } catch (Exception e) {
                        LOGGER.error(getStringBaseInfo(), e);
                    }
                });
            } else {
                message = NJUtil.messageSubCommand(Cmd.LOAD_RMS);
                message.writeUTF(key);
                message.writeBytes(isMainchar ? dataRms[0][getindexRms.get(key)] : dataRms[1][getindexRms.get(key)]);
                message.writeByte(varMainChar);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
        }
    }

    public void loadThuCuoi(Player pp) {
        try {
            if (isNhanban()) {
                return;
            }
            Player playerMap = pp.getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.LOAD_THU_CUOI);
            message.writeInt(pp.playerId);
            for (int i = 0; i < playerMap.itemMons.length; ++i) {
                if (playerMap.itemMons[i] != null) {
                    message.writeShort(playerMap.itemMons[i].template.itemTemplateId);
                    message.writeByte(playerMap.itemMons[i].upgrade);
                    message.writeLong(playerMap.itemMons[i].expires);
                    message.writeByte(playerMap.itemMons[i].sys);
                    if (playerMap.itemMons[i].options != null) {
                        message.writeByte(playerMap.itemMons[i].options.size());
                        for (int j = 0; j < playerMap.itemMons[i].options.size(); ++j) {
                            message.writeByte(playerMap.itemMons[i].options.get(j).optionTemplate.itemOptionTemplateId);
                            if (conn.isVersion145()) {
                                message.writeInt(playerMap.itemMons[i].options.get(j).param);
                            } else {
                                message.writeShort(playerMap.itemMons[i].options.get(j).param);
                            }
                        }
                    } else {
                        message.writeByte(0);
                    }
                } else {
                    message.writeShort(-1);
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void loadThuCuoi(int typeSend) {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.LOAD_THU_CUOI);
            message.writeInt(playerId);
            for (int i = 0; i < pUseItem.itemMons.length; ++i) {
                if (pUseItem.itemMons[i] != null) {
                    message.writeShort(pUseItem.itemMons[i].template.itemTemplateId);
                    message.writeByte(pUseItem.itemMons[i].upgrade);
                    message.writeLong(pUseItem.itemMons[i].expires);
                    message.writeByte(pUseItem.itemMons[i].sys);
                    if (pUseItem.itemMons[i].options != null) {
                        message.writeByte(pUseItem.itemMons[i].options.size());
                        for (int j = 0; j < pUseItem.itemMons[i].options.size(); ++j) {
                            message.writeByte(pUseItem.itemMons[i].options.get(j).optionTemplate.itemOptionTemplateId);
                            if (conn.isVersion145()) {
                                message.writeInt(pUseItem.itemMons[i].options.get(j).param);
                            } else {
                                message.writeShort(pUseItem.itemMons[i].options.get(j).param);
                            }
                        }
                    } else {
                        message.writeByte(0);
                    }
                } else {
                    message.writeShort(-1);
                }
            }
            if (typeSend == 1) {
                sendToPlayer(message, true);
            } else {
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
        }
    }

    public void loadThuNuoi(Player pp) {
        Player playerMain = getPlayerMainControl();
        if (playerMain.itemBodys[10] != null) {
            if (playerMain.itemBodys[10].isPetNew()) {
                try {
                    Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_THU_NUOI);
                    message.writeInt(playerId);
                    message.writeByte(playerMain.itemBodys[10].template.getIDDataPaint());
                    message.writeByte(1);
                    if (pp.getSession() != null) {
                        pp.getSession().sendMessage(message);
                    }
                } catch (IOException e) {
                    LOGGER.error(getStringBaseInfo(), e);
                }
            }
        }
    }

    public void loadrms(String key) {
        try {
            if (key.equals("CSkill")) {
                if (isMainchar) {
                    dataRms[0][2] = new byte[]{ (byte) myskill.template.skillTemplateId };
                } else if (playercopy != null) {
                    int id = 0;
                    if (playercopy.myskill != null) {
                        id = playercopy.myskill.template.skillTemplateId;
                    }
                    dataRms[1][2] = new byte[]{ (byte) id };
                }
            }
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_THU_NUOI);
            message.writeUTF(isMainchar ? keyRms[0][getindexRms.get(key)] : keyRms[1][getindexRms.get(key)]);
            message.writeBytes(isMainchar ? dataRms[0][getindexRms.get(key)] : dataRms[1][getindexRms.get(key)]);
            message.writeByte(isMainchar ? 0 : 1);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void mapClear() {
        try {
            clearData(false);
            Message message = new Message(Cmd.MAP_CLEAR);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void meLoadActive(int typeActive) {
        try {
            if (typeActive == 0) {
                codeSecure = null;
                isLock = false;
            } else if (typeActive == 1) {
                isLock = true;
            } else if (typeActive == 2) {
                isLock = false;
            }
            Message message = NJUtil.messageNotMap(Cmd.ME_LOAD_ACTIVE);
            message.writeByte(typeActive);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void moveFast() {
        try {
            Message message = new Message(Cmd.MOVE_FAST);
            message.writeInt(playerId);
            message.writeShort(x);
            message.writeShort(y);
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void moveFastNpc(int npcId) {
        try {
            Message message = new Message(Cmd.MOVE_FAST_NPC);
            message.writeByte(npcId);
            message.writeInt(playerId);
            sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public void moveFastPlayer(int playerMapId) {
        try {
            Message message = new Message(Cmd.MOVE_FAST);
            message.writeInt(playerId);
            message.writeShort(x);
            message.writeShort(y);
            message.writeInt(playerMapId);
            sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public void npcDie(Npc npc, int dameHit, boolean isChiMang) {
        try {
            Message message = new Message(Cmd.NPC_DIE);
            message.writeByte(npc.npcId);
            message.writeInt(dameHit);
            message.writeBoolean(isChiMang);
            sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public void pickItemMap(ItemMap itemMap) {
        try {
            itemMap.timeReturn = 50;
            Message message = new Message(Cmd.ITEMMAP_MYPICK);
            message.writeShort(itemMap.itemMapId);
            if (itemMap.item.template.type == 19) {
                message.writeShort(itemMap.item.quantity);
            }
            NJUtil.sendMessage(getSession(), message);
            message = new Message(Cmd.ITEMMAP_PLAYERPICK);
            message.writeShort(itemMap.itemMapId);
            message.writeInt(playerId);
            sendToPlayer(message, false);
            if (itemMap.item.getTemplateId() == 260 && map.isDunClan) {
                String[] strs = {
                    name + Alert_VN.ALERT_DUN_CLAN8 + itemMap.item.template.getName()[0],
                    name + Alert_EN.ALERT_DUN_CLAN8 + itemMap.item.template.getName()[1]
                };
                clan.sendAlertDun(strs, name);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerGoInMap(Map m) {
        if (isMainchar && playercopy != null) {
            if (playercopy.map != null) {
                playercopy.mapClear();
                playercopy.map.goOutMap(playercopy);
            }
            playercopy.x = x;
            playercopy.y = y;
            playercopy.map = m;
            playercopy.map.goInMap(playercopy);
        }
        updateTypePk();
    }

    public void playerGoOutMap() {
        try {
            if (isMainchar && playercopy != null) {
                playercopy.mapClear();
                playercopy.map.goOutMap(playercopy);
            }
        } catch (Exception e) {
        }
    }

    public void playerLoadAll(Message message, Player pp) {
        try {
            message.writeInt(pp.playerId);
            Player playerMap = pp.getPlayerMainControl();
            if (playerMap.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMap.clan.name);
                message.writeByte(playerMap.clan.getType(playerMap.name));
            }
            message.writeBoolean(playerMap.isInvisible);
            message.writeByte(playerMap.getTypePk());
            message.writeByte(playerMap.classId);
            message.writeByte(playerMap.gender);
            if (playerMap.itemBodys != null && playerMap.itemBodys[11] != null) {
                message.writeShort(playerMap.itemBodys[11].template.part);
            } else {
                message.writeShort(playerMap.headId);
            }
            message.writeUTF(playerMap.name);
            message.writeInt(pp.hp);
            message.writeInt(pp.hp_full);
            message.writeByte(playerMap.level);
            if (playerMap.playerId < 0 && !playerMap.isNhanban()) {
                message.writeShort(playerMap.weaponId);
                message.writeShort(playerMap.bodyId);
                message.writeShort(playerMap.legId);
                message.writeByte(-1);
            } else {
                message.writeShort(playerMap.getPartBody(1));
                message.writeShort(playerMap.getPartBody(2));
                message.writeShort(playerMap.getPartBody(6));
                int npcId = -1;
                if (playerMap.itemBodys[10] != null) {
                    if (playerMap.itemBodys[10].template.itemTemplateId == 246) {
                        npcId = 70;
                    } else if (playerMap.itemBodys[10].template.itemTemplateId == 419) {
                        npcId = 122;
                    }
                }
                message.writeByte(npcId);
            }
            message.writeShort(playerMap.x);
            message.writeShort(playerMap.y);
            message.writeShort(playerMap.getEff5BuffHp());
            message.writeShort(playerMap.getEff5BuffMp());
            message.writeByte(playerMap.effects.size());
            for (int i = 0; i < playerMap.effects.size(); ++i) {
                int timeRemain = (int) (System.currentTimeMillis() / 1000L - playerMap.effects.get(i).timeStart);
                message.writeByte(playerMap.effects.get(i).template.id);
                message.writeInt(timeRemain);
                message.writeInt(playerMap.effects.get(i).timeLength);
                message.writeShort(playerMap.effects.get(i).param);
            }
            message.writeBoolean(pp.isMainchar);
            message.writeBoolean(pp.isNhanban());
            short[] idPartTHoiTrang = pp.getIdPartThoiTrang();
            for (short i : idPartTHoiTrang) {
                message.writeShort(i);
            }
            message.writeByte(idBoss);
        } catch (IOException e) {
        }
    }

    public void playerLoadAo(int itemTemplateId) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_AO);
            message.writeInt(playerId);
            Player playerMain = getPlayerMainControl();
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeShort(itemTemplateId);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadAoChoang(Player playerMap, int itemId) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_AO_CHOANG);
            message.writeInt(playerMap.playerId);
            Player p = playerMap.getPlayerMainControl();
            message.writeInt(p.hp);
            message.writeInt(p.hp_full);
            message.writeShort(itemId);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadBody() {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_BODY);
            message.writeInt(playerId);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadGiaToc(Player playerMap, int itemId) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_GIA_TOC);
            message.writeInt(playerMap.playerId);
            Player p = playerMap.getPlayerMainControl();
            message.writeInt(p.hp);
            message.writeInt(p.hp_full);
            message.writeShort(itemId);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadHp() {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_HP);
            message.writeInt(playerId);
            message.writeInt(playerMain.hp);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadInfo() {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_INFO);
            message.writeInt(playerId);
            message.writeInt(pUseItem.hp);
            message.writeInt(pUseItem.hp_full);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadLevel() {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand((byte) (-128));
            message.writeInt(playerId);
            message.writeInt(pUseItem.hp);
            message.writeInt(pUseItem.hp_full);
            message.writeByte(pUseItem.level);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadLive() {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_LIVE);
            message.writeInt(playerId);
            Player playerMain = getPlayerMainControl();
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(x);
            message.writeShort(y);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadMatna(int part) {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_MAT_NA);
            message.writeInt(playerId);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeShort(part);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadQuan(int itemTemplateId) {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_QUAN);
            message.writeInt(playerId);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeShort(itemTemplateId);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadThuNuoi(int npcTemplateId, int type) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_THU_NUOI);
            message.writeInt(playerId);
            message.writeByte(npcTemplateId);
            message.writeByte(type);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void playerLoadVuKhi(int itemTemplateId) {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_VUKHI);
            message.writeInt(playerId);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.hp_full);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeShort(itemTemplateId);
            sendToPlayer(message, false);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void reAddAllEff() {
        Player playerMain = getPlayerMainControl();
        for (int i = 0; i < playerMain.effects.size(); ++i) {
            reAddEff(playerMain.effects.get(i));
        }
    }

    public void reAddEff(Effect eff) {
        try {
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            if (eff.template.type == 1 || eff.template.type == 2 || eff.template.type == 3 || eff.template.type == 14) {
                message.writeShort(x);
                message.writeShort(y);
            }
            NJUtil.sendMessage(getSession(), message);
            message = NJUtil.messageSubCommand(Cmd.PLAYER_ADD_EFFECT);
            message.writeInt(playerId);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            if (eff.template.type == 1 || eff.template.type == 2 || eff.template.type == 3 || eff.template.type == 14) {
                message.writeShort(x);
                message.writeShort(y);
            }
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void removeAllPlayer() {
        try {
            if (map == null) {
                return;
            }
            for (int i = 0; i < map.players.size(); ++i) {
                Player player = map.players.get(i);
                if (player != null && player.playerId != playerId) {
                    player.removePlayer(this);
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void removeEffect(Effect eff, boolean remove) {
        Player player = getPlayerMainControl();
        if (eff.template.type == 11 || eff.template.type == 12) {
            isInvisible = false;
        }
        if (remove) {
            player.effects.remove(eff);
            player.updateData();
        }
        try {
            Message message = NJUtil.messageSubCommand(Cmd.ME_REMOVE_EFFECT);
            message.writeByte(eff.template.id);
            if (eff.template.type == 0 || eff.template.type == 12) {
                message.writeInt(player.hp);
                message.writeInt(player.mp);
            } else if (eff.template.type == 4 || eff.template.type == 17) {
                message.writeInt(player.hp);
            } else if (eff.template.type == 5) {
                player.sumonHide = null;
            } else if (eff.template.type == 6) {
                player.convertMp = 0;
            } else if (eff.template.type == 7) {
                player.miss -= (short) eff.param;
            } else if (eff.template.type == 13) {
                player.dame_full -= eff.param;
                message.writeInt(player.hp);
            } else if (eff.template.type == 23) {
                message.writeInt(player.hp);
                message.writeInt(player.hp_full);
            }
            NJUtil.sendMessage(getSession(), message);
            if (eff.template.type == 18) {
                return;
            }
            message = NJUtil.messageSubCommand(Cmd.PLAYER_REMOVE_EFFECT);
            message.writeInt(playerId);
            message.writeByte(eff.template.id);
            if (eff.template.type == 0 || eff.template.type == 12) {
                message.writeInt(player.hp);
                message.writeInt(player.mp);
            } else if (eff.template.type == 11) {
                message.writeShort(x);
                message.writeShort(y);
            } else if (eff.template.type == 4 || eff.template.type == 13 || eff.template.type == 17) {
                message.writeInt(player.hp);
            } else if (eff.template.type == 23) {
                message.writeInt(player.hp);
                message.writeInt(player.hp_full);
            }
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void removeEffectPlayer(int idEff) {
        try {
            Message message = new Message(Cmd.EFF_DYNAMIC_TOOL);
            message.dos.writeByte(0);
            message.dos.writeByte(0);
            message.dos.writeInt(playerId);
            message.dos.writeShort(idEff);
            message.dos.writeInt(1);
            message.dos.writeByte(1);
            message.dos.writeByte(0);
            conn.sendMessage(message);
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void removeItem(Item item, int typeUI) {
        Player playerMain = getPlayerMainControl();
        if (typeUI == 5) {
            playerMain.itemBodys[item.indexUI] = null;
        } else if (typeUI == 3) {
            itemBags[item.indexUI] = null;
        } else if (typeUI == 4) {
            itemBoxs[item.indexUI] = null;
        } else if (typeUI == 41) {
            playerMain.itemMons[item.indexUI] = null;
        }
        /*if (item.template.type != 25) {
            Database.removeItem(item.itemId);
        }*/
    }

    public void removeItem(int indexUI) {
        //Item item = itemBags[indexUI];
        itemBags[indexUI] = null;
        /*if (item != null) {
            Database.removeItem(item.itemId);
        }*/
    }

    public void removePlayer(Player playerMap) {
        try {
            Message message = new Message(Cmd.PLAYER_REMOVE);
            message.writeInt(playerMap.playerId);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void removePlayerCopyMap() {
        if (isNhanban()) {
            return;
        }
        if (checkTimeOutNhanban()) {
            if (isControlCharNhanBan()) {
                switchMainCharAndCopyNPCTajma(4);
                dataRms[1][2][0] = (byte) playercopy.myskill.template.skillTemplateId;
                if (playercopy.hp <= 0) {
                    playercopy.hp = 1;
                }
                Database.savePlayer(this, map.getTemplateId());
            } else {
                Database.savePlayer(this, map.getTemplateId());
                map.playerOuts.add(playercopy);
            }
            playercopy = null;
        }
    }

    public void resetButtonClient() {
        Message message = new Message(Cmd.RESET_BUTTON);
        NJUtil.sendMessage(getSession(), message);
    }

    public void resetCT() {
        ++rwct;
        resultCT = 0;
        pointCT = 0;
        pointPKCT = 0;
    }

    public void resetPoint() {
        try {
            y = autoFall(x, y);
            timeMoveUp = 0;
            Message message = new Message(Cmd.RESET_POINT);
            message.writeShort(x);
            message.writeShort(y);
            NJUtil.sendMessage(getSession(), message);
            sendMove();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void resetPoint1(int timeS) {
        try {
            y = autoFall(x, y);
            Player playerMain = getPlayerMainControl();
            playerMain.timeSpleep += timeS;
            playerMain.timeMoveUp = 0;
            Message message = new Message(Cmd.RESET_POINT);
            message.writeShort(x);
            message.writeShort(y);
            NJUtil.sendMessage(getSession(), message);
            sendMove();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void tayKynang() {
        for (int i = 0; i < skills.size(); ++i) {
            Skill skill = skills.get(i);
            if (skill.point > 0 && !isSkillPhanThan(skill)) {
                for (int j = 0; j < skill.template.skills.size(); ++j) {
                    Skill s = skill.template.skills.get(j);
                    if (s.point == 1) {
                        Skill sUpdate = s.cloneSkill();
                        if (skill.equals(myskill)) {
                            myskill = sUpdate;
                        }
                        skills.set(i, sUpdate);
                        break;
                    }
                }
            }
        }
        refreshSkill();
    }

    public void tayTiemnang() {
        if (isSysOut()) {
            p_strength = 15;
            p_agile = 5;
            p_mp = 5;
            p_hp = 5;
        } else {
            p_strength = 5;
            p_agile = 5;
            p_mp = 10;
            p_hp = 10;
        }
        refreshPotential();
    }

    public void resetPotential() {
        try {
            tayTiemnang();
            updateData();
            updatePotential();
            callEffectMe(21);
        } catch (Exception e) {
        }
    }

    public void resetSkill() {
        try {
            tayKynang();
            updateData();
            updateSkill();
            callEffectMe(21);
        } catch (Exception e) {
        }
    }

    public void setPointSkill(Skill skill, int sPoint) {
        try {
            Player playerMain = getPlayerMainControl();
            if (skill.point >= sPoint) {
                return;
            }
            int i = 0;
            while (i < skill.template.skills.size()) {
                if (sPoint == skill.template.skills.get(i).point) {
                    Skill newSkill = skill.template.skills.get(i);
                    if (newSkill.level > playerMain.level) {
                        return;
                    }
                    playerMain.skills.set(playerMain.skills.indexOf(skill), newSkill);
                    if (playerMain.myskill == null || playerMain.myskill.template.equals(newSkill.template)) {
                        playerMain.skillSelect(newSkill, false);
                    }
                    playercopy.setPercentDamPhanThan(newSkill.options.get(1).param);
                    playerMain.updateData();
                    updateSkill();
                    NJUtil.sendServer(getSession(), skill.template.getName(getSession()) + " " + AlertFunction.SKILL_LEVEL_UP(getSession()) + sPoint);
                    break;
                } else {
                    ++i;
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void setSkill(String strSkill) {
        skills.clear();
        if (!strSkill.trim().isEmpty()) {
            String[] skill_infos = strSkill.split(",");
            boolean isFail = false;
            for (String skill_info : skill_infos) {
                boolean fail = false;
                Skill skill = ServerController.skills.get(Integer.parseInt(skill_info)).cloneSkill();
                if (skill.level > level) {
                    isFail = true;
                    fail = true;
                }
                if (!fail) {
                    skills.add(skill);
                }
            }
            if (classId == 6 && isFail) {
                tayKynang();
            }
        }
    }

    public void saveRms(Message message) {
        try {
            String key = message.readUTF();
            byte[] value;
            value = new byte[message.readInt()];
            if (key.equals("OSkillnhanban")) {
                key = "OSkill";
            }
            if (key.equals("KSkillnhanban")) {
                key = "KSkill";
            }
            if (key.equals("CSkillnhanban")) {
                key = "CSkill";
            }
            for (int i = 0; i < value.length; ++i) {
                value[i] = message.readByte();
            }
            byte isme = 0;
            try {
                isme = message.readByte();
            } catch (Exception e) {
            }
            dataRms[isme][getindexRms.get(key)] = value;
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void saveRms(String key, byte[] value, boolean isme) {
        byte[] vl = Database.getRms(playerId, key, isme);
        if (vl == null) {
            if (isme) {
                Database.saveRms(playerId, key, value, true);
            } else {
                Database.updateRms(playerId, key, value, false);
            }
        } else {
            Database.updateRms(playerId, key, value, isme);
        }
    }

    public void saveRmsAll() {
        try {
            saveRms(keyRms[0][0], dataRms[0][0], true);
            saveRms(keyRms[0][1], dataRms[0][1], true);
            saveRms(keyRms[0][0], dataRms[1][0], false);
            saveRms(keyRms[0][1], dataRms[1][1], false);
        } catch (Exception e) {
        }
    }

    public void savezaLog(String text) {
        LogData data = new LogData();
        data.username = getSession().username;
        data.playername = name;
        data.text = text;
        StringBuilder itemBag = new StringBuilder();
        if (itemBags != null) {
            for (Item item : itemBags) {
                if (item != null) {
                    itemBag.append(item.itemId).append("$")
                        .append(item.getTemplateId()).append("$")
                        .append(item.typeUI).append("$")
                        .append(item.indexUI).append("$")
                        .append(item.vitri).append("$")
                        .append(item.sys).append("$")
                        .append(item.expires).append("$")
                        .append(item.isLock).append("$")
                        .append(item.upgrade).append("$")
                        .append(item.getOptionInfo()).append("$")
                        .append(item.quantity).append("$")
                        .append(item.saleCoinLock).append("#");
                }
            }
        }
        StringBuilder itemBox = new StringBuilder();
        if (itemBoxs != null) {
            for (Item item : itemBoxs) {
                if (item != null) {
                    itemBox.append(item.itemId).append("$")
                        .append(item.getTemplateId()).append("$")
                        .append(item.typeUI).append("$")
                        .append(item.indexUI).append("$")
                        .append(item.vitri).append("$")
                        .append(item.sys).append("$")
                        .append(item.expires).append("$")
                        .append(item.isLock).append("$")
                        .append(item.upgrade).append("$")
                        .append(item.getOptionInfo()).append("$")
                        .append(item.quantity).append("$")
                        .append(item.saleCoinLock).append("#");
                }
            }
        }
        StringBuilder itemBody = new StringBuilder();
        if (itemBodys != null) {
            for (Item body : itemBodys) {
                if (body != null) {
                    itemBody.append(body.itemId).append("$")
                        .append(body.getTemplateId()).append("$")
                        .append(body.typeUI).append("$")
                        .append(body.indexUI).append("$")
                        .append(body.vitri).append("$")
                        .append(body.sys).append("$")
                        .append(body.expires).append("$")
                        .append(body.isLock).append("$")
                        .append(body.upgrade).append("$")
                        .append(body.getOptionInfo()).append("$")
                        .append(body.quantity).append("$")
                        .append(body.saleCoinLock).append("#");
                }
            }
        }
        StringBuilder itemMon = new StringBuilder();
        if (itemBodys != null) {
            for (Item mon : itemMons) {
                if (mon != null) {
                    itemMon.append(mon.itemId).append("$")
                        .append(mon.getTemplateId()).append("$")
                        .append(mon.typeUI).append("$")
                        .append(mon.indexUI).append("$")
                        .append(mon.vitri).append("$")
                        .append(mon.sys).append("$")
                        .append(mon.expires).append("$")
                        .append(mon.isLock).append("$")
                        .append(mon.upgrade).append("$")
                        .append(mon.getOptionInfo()).append("$")
                        .append(mon.quantity).append("$")
                        .append(mon.saleCoinLock).append("#");
                }
            }
        }
        data.info = getLuong() + "@" + getXu() + "@" + getXuBox() + "@" + getYen() + "@" + headId + "@" + gender + "@" + reg_date + "@" + login_date + "@" + ban + "@" + exp + "@" + exp_down + "@" + pk + "@" + bag + "@" + box + "@" + classId + "@" + potential_point + "@" + getSkillInfo() + "@" + skill_point + "@" + p_strength + "@" + p_agile + "@" + p_hp + "@" + p_mp + "@" + taskFinish + "@" + taskIndex + "@" + taskCount + "@" + pointUyDanh + "@" + itemBag + "@" + itemBox + "@" + itemBody + "@" + itemMon;
        data.log_type = LogData.LOG_TYPE_A;
        GameServer.logs.add(data);
    }

    public void savezaLog(String text, String info) {
        LogData data = new LogData();
        data.username = getSession().username;
        data.playername = name;
        data.text = text;
        data.info = info;
        data.log_type = LogData.LOG_TYPE_A;
        GameServer.logs.add(data);
    }

    public static void savezaLog(String name, String text, String info) {
        LogData data = new LogData();
        data.username = String.valueOf(name);
        data.playername = name;
        data.text = text;
        data.info = info;
        data.log_type = LogData.LOG_TYPE_A;
        GameServer.logs.add(data);
    }

    public void savezbLog(String text, String info) {
        try {
            LogData data = new LogData();
            data.username = getSession().username;
            data.playername = name;
            data.text = text;
            data.info = info;
            data.log_type = LogData.LOG_TYPE_B;
            GameServer.logs.add(data);
        } catch (Exception e) {
        }
    }

    public void sendAddFriend(String name, int type) {
        try {
            Message message = new Message(Cmd.FRIEND_ADD);
            message.writeUTF(name);
            message.writeByte(type);
            NJUtil.sendMessage(getSession(), message);
            if (taskMain != null && taskMain.template.taskId == 11 && taskMain.index == 1) {
                Task taskMain = this.taskMain;
                ++taskMain.count;
                if (this.taskMain.count >= this.taskMain.template.counts[this.taskMain.index]) {
                    doTaskNext();
                } else {
                    doTaskUpdate(this.taskMain.count);
                }
            }
        } catch (Exception e) {
        }
    }

    public void sendAddItemBag(Item item) {
        try {
            Message message = new Message(Cmd.ITEM_BAG_ADD);
            message.writeByte(item.indexUI);
            message.writeShort(item.template.itemTemplateId);
            message.writeBoolean(item.isLock);
            if (item.isTypeBody() || (item.isTypeGem() && conn.isVersion144())) {
                message.writeByte(item.upgrade);
            }
            message.writeBoolean(item.expires != -1L);
            if (item.quantity > 1) {
                message.writeShort(item.quantity);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendAddPlayer() {
        try {
            if (map != null && map.template != null) {
                for (int i = 0; i < map.players.size(); ++i) {
                    if (map.players.get(i).playerId != playerId) {
                        try {
                            addPlayer(map.players.get(i));
                            map.players.get(i).addPlayer(this);
                            loadThuCuoi(map.players.get(i));
                            map.players.get(i).loadThuCuoi(this);
                            loadThuNuoi(map.players.get(i));
                            map.players.get(i).loadThuNuoi(this);
                        } catch (Exception e) {
                            LOGGER.error(getStringBaseInfo(), e);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void fibian() {
        typeNvbian = 0;
        int per = 0;
        if (level >= 60 && level <= 69) {
            per = 10;
        } else if (level >= 70 && level <= 79) {
            per = 5;
        } else if (level >= 80 && level <= 89) {
            per = 3;
        }
        long expx = exps1[level] * per / 100L;
        sendUpdateExp(expx, true);
        sendBian();
        callEffectMe(61);
    }

    public void sendBian() {
        Message message;
        try {
            message = new Message(Cmd.NV_BIAN);
            message.writeByte(typeNvbian);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendChientruong(int pointCT) {
        if (pointCT >= 2000 && taskMain != null && taskMain.template.taskId == 41 && taskMain.index == 1 && getTypePk() == PK_PHE1) {
            doTaskNext();
        }
        if (pointCT >= 2000 && taskMain != null && taskMain.template.taskId == 41 && taskMain.index == 2 && getTypePk() == PK_PHE2) {
            doTaskNext();
        }
        try {
            Message message = NJUtil.messageNotMap(Cmd.CHIENTRUONG_INFO);
            message.writeShort(pointCT);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendClearItemBag(int indexUI) {
        try {
            Message message = new Message(Cmd.ITEM_BAG_CLEAR);
            message.writeByte(indexUI);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendClearItemBody(int indexUI) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.ITEM_BODY_CLEAR);
            message.writeByte(indexUI);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
        }
    }

    public void sendClearItemBox(int indexUI) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.ITEM_BOX_CLEAR);
            message.writeByte(indexUI);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendEffToolNgocKham() {
        if (isTruongHaruna()) {
            if (fullSet10 >= 10) {
                addEffectPlayer(EffectTool.BACH_HO_3, 1, 5000, 0);
                if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.BACH_HO_2);
                } else if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.BACH_HO_1);
                }
            } else if (fullSet8 >= 10) {
                addEffectPlayer(EffectTool.BACH_HO_2, 1, 5000, 0);
                if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.BACH_HO_3);
                } else if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.BACH_HO_1);
                }
            } else if (fullSet6 >= 10) {
                addEffectPlayer(EffectTool.BACH_HO_1, 1, 5000, 0);
                if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.BACH_HO_3);
                } else if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.BACH_HO_2);
                }
            } else if (idSendHuyEff == 2) {
                removeEffectPlayer(EffectTool.BACH_HO_3);
            } else if (idSendHuyEff == 0) {
                removeEffectPlayer(EffectTool.BACH_HO_1);
            } else if (idSendHuyEff == 1) {
                removeEffectPlayer(EffectTool.BACH_HO_2);
            }
        } else if (isTruongHirosaki()) {
            if (fullSet10 >= 10) {
                addEffectPlayer(EffectTool.PHUONG_HOANG_3, 1, 5000, 0);
                if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_1);
                } else if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_2);
                }
            } else if (fullSet8 >= 10) {
                addEffectPlayer(EffectTool.PHUONG_HOANG_2, 1, 5000, 0);
                if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_1);
                } else if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_3);
                }
            } else if (fullSet6 >= 10) {
                addEffectPlayer(EffectTool.PHUONG_HOANG_1, 1, 5000, 0);
                if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_2);
                } else if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.PHUONG_HOANG_3);
                }
            } else if (idSendHuyEff == 0) {
                removeEffectPlayer(EffectTool.PHUONG_HOANG_1);
            } else if (idSendHuyEff == 1) {
                removeEffectPlayer(EffectTool.PHUONG_HOANG_2);
            } else if (idSendHuyEff == 2) {
                removeEffectPlayer(EffectTool.PHUONG_HOANG_3);
            }
        } else if (isTruongOokaza()) {
            if (fullSet10 >= 10) {
                addEffectPlayer(EffectTool.RONG_3, 1, 5000, 0);
                if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.RONG_2);
                } else if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.RONG_1);
                }
            } else if (fullSet8 >= 10) {
                addEffectPlayer(EffectTool.RONG_2, 1, 5000, 0);
                if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.RONG_3);
                } else if (idSendHuyEff == 0) {
                    removeEffectPlayer(EffectTool.RONG_1);
                }
            } else if (fullSet6 >= 10) {
                addEffectPlayer(EffectTool.RONG_1, 1, 5000, 0);
                if (idSendHuyEff == 2) {
                    removeEffectPlayer(EffectTool.RONG_3);
                } else if (idSendHuyEff == 1) {
                    removeEffectPlayer(EffectTool.RONG_2);
                }
            } else if (idSendHuyEff == 2) {
                removeEffectPlayer(EffectTool.RONG_3);
            } else if (idSendHuyEff == 1) {
                removeEffectPlayer(EffectTool.RONG_2);
            } else if (idSendHuyEff == 0) {
                removeEffectPlayer(EffectTool.RONG_1);
            }
        }
    }

    public void sendGoOutClan() {
        LOGGER.info(name + " go out clan " + clan.name);
        clan = null;
        try {
            Message message = NJUtil.messageNotMap(Cmd.CLAN_MOVEOUT_MEM);
            message.writeInt(playerId);
            sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public void sendHaveAtt(int dameHit) {
        try {
            Player pp = getPlayerMainControl();
            Message message = new Message(Cmd.HAVE_ATTACK_PLAYER);
            message.writeInt(playerId);
            message.writeInt(pp.hp);
            message.writeInt(dameHit);
            sendLimitSpace(message, true);
            if (dameHit == 0) {
                Player p = ServerController.hpPlayers.get(playerId);
                if (p != null) {
                    message = new Message(Cmd.PLAYER_MOVE);
                    message.writeInt(p.playerId);
                    message.writeShort(p.x);
                    message.writeShort(p.y);
                    NJUtil.sendMessage(getSession(), message);
                }
            }
        } catch (Exception e) {
        }
    }

    public void sendHaveAttReact(int dameHit, int dameMp) {
        if (dameHit + dameMp <= 0) {
            return;
        }
        try {
            Player pp = getPlayerMainControl();
            Message message = new Message(Cmd.HAVE_ATTACK_PLAYER);
            message.writeInt(playerId);
            message.writeInt(pp.hp);
            message.writeInt(dameHit);
            if (dameMp > 0) {
                message.writeInt(pp.mp);
                message.writeInt(dameMp);
            }
            sendLimitSpace(message, true);
        } catch (Exception e) {
        }
    }

    public void sendLoadItemSale(int optionId) {
        try {
            Vector<Item> items = new Vector<>();
            for (int i = 0; i < ServerController.shops.size(); ++i) {
                Item it = ServerController.shops.get(i);
                if ((optionId == 0 && it.template.type == 26) ||
                    (optionId == 1 && it.template.type == 0) ||
                    (optionId == 2 && it.template.type == 1) ||
                    (optionId == 3 && it.template.type == 2) ||
                    (optionId == 4 && it.template.type == 3) ||
                    (optionId == 5 && it.template.type == 4) ||
                    (optionId == 6 && it.template.type == 5) ||
                    (optionId == 7 && it.template.type == 6) ||
                    (optionId == 8 && it.template.type == 7) ||
                    (optionId == 9 && it.template.type == 8) ||
                    (optionId == 10 && it.template.type == 9) ||
                    (optionId == 11 && it.template.type != 26 && it.template.type != 0 && it.template.type != 1 && it.template.type != 2 && it.template.type != 3 && it.template.type != 4 && it.template.type != 5 && it.template.type != 6 && it.template.type != 7 && it.template.type != 8 && it.template.type != 9)
                ) {
                    items.add(it);
                }
            }
            Vector<Item> itemsSend = new Vector<>();
            for (int count = 0; items.size() > 0 && count <= 500; ++count) {
                int id = NJUtil.random.nextInt(items.size());
                itemsSend.add(items.remove(id));
            }
            long timeNow = System.currentTimeMillis();
            Message message = new Message(Cmd.LOAD_ITEM_AUCTION);
            message.writeByte(optionId);
            message.writeInt(itemsSend.size());
            for (Item item : itemsSend) {
                message.writeInt((int) item.itemId);
                message.writeInt((int) ((NJUtil.getMilisByDay(1) - (timeNow - item.expires)) / 1000L));
                message.writeShort(item.quantity);
                message.writeUTF(item.playerName);
                message.writeInt(item.saleShop);
                message.writeShort(item.template.itemTemplateId);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendMapTime(int time) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.MAP_TIME);
            message.writeInt(time);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendMove() {
        try {
            Message message = new Message(Cmd.PLAYER_MOVE);
            message.writeInt(playerId);
            message.writeShort(x);
            message.writeShort(y);
            sendLimitSpace(message, false);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendNotUnlock() {
        try {
            Message message = NJUtil.messageNotMap(Cmd.NOT_USEACC);
            message.writeUTF(AlertFunction.ALERT_LOCK(getSession()));
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendOpenUI(int typeUI) {
        try {
            Message message = new Message(Cmd.OPEN_UI);
            message.writeByte(typeUI);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendOpenUISay(int npcId, String str) {
        try {
            Message message = new Message(Cmd.OPEN_UI_SAY);
            message.writeShort(npcId);
            message.writeUTF(str);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendOpenUISay(int npcId, String[] strs) {
        try {
            Message message = new Message(Cmd.OPEN_UI_SAY);
            message.writeShort(npcId);
            message.writeUTF(strs[getSession().typeLanguage]);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendPointPB() {
        try {
            Message message = NJUtil.messageNotMap(Cmd.POINT_PB);
            message.writeShort(pointPB);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendResultCT(Session conn, String text) {
        try {
            Message message = NJUtil.messageNotMap(Cmd.REVIEW_CT);
            message.writeUTF(text);
            message.writeBoolean(pointCT > 0);
            conn.sendMessage(message);
            message.cleanup();
        } catch (Exception e) {
        }
    }

    public void sendServerMessage(String[] strs) {
        try {
            Message message = new Message(Cmd.SERVER_MESSAGE);
            message.writeUTF(strs[getSession().typeLanguage]);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendShowAtt(Vector<Attack> atts) {
        try {
            if (atts.size() > 0) {
                Player playerMain = getPlayerMainControl();
                Message message = new Message(Cmd.PLAYER_ATTACK_PLAYER);
                message.writeInt(playerId);
                message.writeByte(playerMain.getSkillValue().template.skillTemplateId);
                for (Attack att : atts) {
                    message.writeInt(att.playerHit.playerId);
                }
                sendLimitSpace(message, true);
            }
        } catch (Exception e) {
        }
    }

    public void sendShowWait(String str) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.SHOW_WAIT);
            message.writeUTF(str);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendTask() {
        try {
            if (isNhanban()) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            Message message = new Message(Cmd.TASK_GET);
            message.writeShort(playerMain.taskMain.getTaskID());
            message.writeByte(playerMain.taskMain.index);
            message.writeUTF(playerMain.taskMain.getName(getSession().typeLanguage));
            message.writeUTF(playerMain.taskMain.getDetail(getSession().typeLanguage));
            message.writeByte(playerMain.taskMain.getSubName(getSession().typeLanguage).length);
            for (int i = 0; i < playerMain.taskMain.getSubName(getSession().typeLanguage).length; ++i) {
                message.writeUTF(playerMain.taskMain.getSubName(getSession().typeLanguage)[i]);
            }
            if (playerMain.taskMain.getCount() != null) {
                message.writeShort(playerMain.taskMain.count);
                for (int i = 0; i < playerMain.taskMain.getCount().length; ++i) {
                    message.writeShort(playerMain.taskMain.getCount()[i]);
                }
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendTaskFollowFail(Player player, int type) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.TASK_FOLLOW_FAIL);
            message.writeByte(type);
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
        }
    }

    public void sendTestEnd() {
        try {
            if (testSkill != null) {
                Player playerMap = testSkill.getPlayer(this);
                playerMap.testSkill = null;
                testSkill = null;
                Message message = new Message(Cmd.TEST_END);
                message.writeInt(playerId);
                message.writeInt(playerMap.playerId);
                message.writeInt(playerMap.getHp());
                sendToPlayer(message, true);
            }
        } catch (Exception e) {
        }
    }

    public void sendTestEndSkill(int playerId1, int playerId2) {
        try {
            Message message = new Message(Cmd.TEST_END);
            message.writeInt(playerId1);
            message.writeInt(playerId2);
            sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public void sendToPlayer(Message message, boolean isSendMe) {
        try {
            if (map == null) {
                return;
            }
            for (int i = 0; i < map.players.size(); ++i) {
                try {
                    Player player = map.players.get(i);
                    if (isSendMe && player.getSession() != null) {
                        player.getSession().sendMessage(message);
                    } else if (player.playerId != playerId && player.getSession() != null) {
                        if (!player.isHideChat) {
                            player.getSession().sendMessage(message);
                        }
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        } finally {
            message.cleanup();
        }
    }

    public void sendUpdateCoinBag(int coinAdd) {
        try {
            if (coinAdd < 0) {
                subXu(Math.abs(coinAdd));
            } else {
                addXu(coinAdd);
            }
            if (coinAdd != 0) {
                Message message = new Message(Cmd.ME_UP_COIN_BAG);
                message.writeInt(coinAdd);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateCoinLockYen(int coinAdd) {
        try {
            if (coinAdd < 0) {
                subYen(Math.abs(coinAdd));
            } else {
                addYen(coinAdd);
            }
            if (coinAdd != 0) {
                Message message = new Message(Cmd.ME_UP_COIN_LOCK);
                message.writeInt(coinAdd);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateLuong(int goldAdd) {
        try {
            if (goldAdd < 0) {
                subLuong(Math.abs(goldAdd));
            } else {
                addLuong(goldAdd);
            }
            if (goldAdd != 0) {
                Message message = NJUtil.messageSubCommand(Cmd.ME_UP_GOLD);
                message.writeInt(goldAdd);
                NJUtil.sendMessage(getSession(), message);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateLuongNotAdd(int goldAdd) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.ME_UP_GOLD);
            message.writeInt(goldAdd);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateExp(long expAdd, boolean addexpPhanThan) {
        try {
            boolean isLevelUp;
            Player pUseItem = getPlayerMainControl();
            if (pUseItem.isNhanban() && !isControlCharNhanBan() && !pUseItem.isFirstJoinGame()) {
                return;
            }
            if (!pUseItem.isNhanban() && pUseItem.playercopy != null && expAdd > 0L && addexpPhanThan) {
                expSkillPhanthan += expAdd;
                int levelskill = getLevelSkill(expSkillPhanthan);
                setPointSkill(getSkillPhanThan(), levelskill);
            }
            int oldLevel = pUseItem.level;
            if (pUseItem.exp_down > 0L) {
                if (pUseItem.exp_down - expAdd <= 0L) {
                    Message message = new Message(Cmd.PLAYER_UP_EXPDOWN);
                    if (getSession().isVersion108()) {
                        message.writeLong(pUseItem.exp_down);
                    } else {
                        message.writeInt((int) pUseItem.exp_down);
                    }
                    NJUtil.sendMessage(getSession(), message);
                    pUseItem.exp_down = 0L;
                    return;
                }
                pUseItem.exp_down -= expAdd;
                if (expAdd > 0L) {
                    Message message = new Message(Cmd.PLAYER_UP_EXPDOWN);
                    if (getSession().isVersion108()) {
                        message.writeLong(expAdd);
                    } else {
                        message.writeInt((int) expAdd);
                    }
                    NJUtil.sendMessage(getSession(), message);
                }
            } else if (pUseItem.exp_down == 0L) {
                if (expAdd > 0L && pUseItem.exp + expAdd < ((switchNewExp == 0) ? maxExp : maxExp1)) {
                    isLevelUp = isLevelUp(expAdd);
                    Message message = new Message(Cmd.PLAYER_UP_EXP);
                    message.writeLong(expAdd);
                    NJUtil.sendMessage(getSession(), message);
                    if (isLevelUp) {
                        Player pSave = this;
                        Database.savePlayer(pSave, map.getTemplateId());
                        if (pUseItem.level >= GameServer.maxLevel && ServerController.topCaothu.size() < 20) {
                            NJUtil.appendFile(GameServer.outPath + "/toplevel.txt", name + "," + NJUtil.getDateTime() + "\n");
                        }
                        updateLevel();
                    }
                }
                if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 4 && pUseItem.taskMain.index == 0 && pUseItem.level >= 5) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 5 && pUseItem.taskMain.index == 0 && pUseItem.level >= 6) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 6 && pUseItem.taskMain.index == 0 && pUseItem.level >= 7) {
                    doTaskNext();
                    if (map.template.mapTemplateId == 2 && pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 6 && pUseItem.taskMain.index == 1) {
                        doTaskNext();
                    }
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 7 && pUseItem.taskMain.index == 0 && pUseItem.level >= 8) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 8 && pUseItem.taskMain.index == 0 && pUseItem.level >= 9) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 11 && pUseItem.taskMain.index == 0 && pUseItem.level >= 11) {
                    doTaskNext();
                } else if (!pUseItem.isNhanban() && pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 12 && pUseItem.taskMain.index == 0 && pUseItem.level >= 12) {
                    doTaskNext();
                    if (pUseItem.itemBodys[1] != null && pUseItem.itemBodys[1].upgrade >= 1) {
                        doTaskNext();
                        for (int i = 0; i < pUseItem.itemBodys.length; ++i) {
                            if (pUseItem.itemBodys[i] != null && pUseItem.itemBodys[i].upgrade >= 1 && pUseItem.itemBodys[i].isTypeAdorn()) {
                                doTaskNext();
                                for (int j = 0; j < pUseItem.itemBodys.length; ++j) {
                                    if (pUseItem.itemBodys[j] != null && pUseItem.itemBodys[j].upgrade >= 1 && pUseItem.itemBodys[j].isTypeClothe()) {
                                        doTaskNext();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 13 && pUseItem.taskMain.index == 0 && pUseItem.level >= 14) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 14 && pUseItem.taskMain.index == 0 && pUseItem.level >= 16) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 15 && pUseItem.taskMain.index == 0 && pUseItem.level >= 19) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 16 && pUseItem.taskMain.index == 0 && pUseItem.level >= 22) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 17 && pUseItem.taskMain.index == 0 && pUseItem.level >= 25) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 18 && pUseItem.taskMain.index == 0 && pUseItem.level >= 26) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 19 && pUseItem.taskMain.index == 0 && pUseItem.level >= 27) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 20 && pUseItem.taskMain.index == 0 && pUseItem.level >= 29) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 21 && pUseItem.taskMain.index == 0 && pUseItem.level >= 30) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 22 && pUseItem.taskMain.index == 0 && pUseItem.level >= 32) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 23 && pUseItem.taskMain.index == 0 && pUseItem.level >= 34) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 24 && pUseItem.taskMain.index == 0 && pUseItem.level >= 35) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 25 && pUseItem.taskMain.index == 0 && pUseItem.level >= 37) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 26 && pUseItem.taskMain.index == 0 && pUseItem.level >= 38) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 27 && pUseItem.taskMain.index == 0 && pUseItem.level >= 39) {
                    doTaskNext();
                    pUseItem.taskMain.count = (short) countItemBag(239);
                    if (pUseItem.taskMain.count >= pUseItem.taskMain.template.counts[pUseItem.taskMain.index]) {
                        doTaskNext();
                    } else {
                        doTaskUpdate(pUseItem.taskMain.count);
                    }
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 28 && pUseItem.taskMain.index == 0 && pUseItem.level >= 41) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 29 && pUseItem.taskMain.index == 0 && pUseItem.level >= 43) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 30 && pUseItem.taskMain.index == 0 && pUseItem.level >= 45) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 31 && pUseItem.taskMain.index == 0 && pUseItem.level >= 47) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 32 && pUseItem.taskMain.index == 0 && pUseItem.level >= 49) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 33 && pUseItem.taskMain.index == 0 && pUseItem.level >= 51) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 34 && pUseItem.taskMain.index == 0 && pUseItem.level >= 53) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 35 && pUseItem.taskMain.index == 0 && pUseItem.level >= 55) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 36 && pUseItem.taskMain.index == 0 && pUseItem.level >= 57) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 37 && pUseItem.taskMain.index == 0 && pUseItem.level >= 59) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 38 && pUseItem.taskMain.index == 0 && pUseItem.level >= 61) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 39 && pUseItem.taskMain.index == 0 && pUseItem.level >= 63) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 40 && pUseItem.taskMain.index == 0 && pUseItem.level >= 65) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 41 && pUseItem.taskMain.index == 0 && pUseItem.level >= 67) {
                    doTaskNext();
                } else if (pUseItem.taskMain != null && pUseItem.taskMain.template.taskId == 42 && pUseItem.taskMain.index == 0 && pUseItem.level >= 69) {
                    doTaskNext();
                }
            }
            if (oldLevel < pUseItem.level && pUseItem.level == 100) {
                NJUtil.appendFile("player/first_100.txt", name + "," + NJUtil.getDateTime());
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUpdateItemBag(int indexUI, int quantity) {
        try {
            Message message = new Message(Cmd.ITEM_BAG_ADD_QUANTITY);
            message.writeByte(indexUI);
            if (quantity > 1) {
                message.writeShort(quantity);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void sendUseItemUpToUp(int indexUI, int quantily) {
        try {
            Message message = new Message(Cmd.ITEM_USE_UPTOUP);
            message.writeByte(indexUI);
            if (quantily > 1) {
                message.writeShort(quantily);
            }
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void setInfo(String info) {
        if (info.isEmpty()) {
            pointOpenTui = maxOpenTui;
            resetPotential = freePotential;
            resetSkill = freeSkill;
            pointUyDanh = 0;
            return;
        }
        String[] infos = info.split(",");
        try {
            dayLogin = infos[0].trim();
        } catch (Exception e) {
        }
        try {
            timeLogOut = Long.parseLong(infos[1]);
        } catch (Exception e) {
        }
        try {
            timeOnline = Integer.parseInt(infos[2]);
        } catch (Exception e) {
        }
        pointOpenTui = Integer.parseInt(infos[3]);
        resetPotential = Integer.parseInt(infos[4]);
        resetSkill = Integer.parseInt(infos[5]);
        pointUyDanh = Integer.parseInt(infos[6]);
        pointNon = Integer.parseInt(infos[7]);
        pointVukhi = Integer.parseInt(infos[8]);
        pointAo = Integer.parseInt(infos[9]);
        pointLien = Integer.parseInt(infos[10]);
        pointGangtay = Integer.parseInt(infos[11]);
        pointNhan = Integer.parseInt(infos[12]);
        pointQuan = Integer.parseInt(infos[13]);
        pointNgocboi = Integer.parseInt(infos[14]);
        pointGiay = Integer.parseInt(infos[15]);
        pointPhu = Integer.parseInt(infos[16]);
        try {
            effId_food = Byte.parseByte(infos[17]);
            timeEff_food = Integer.parseInt(infos[18]);
            effId_exp_bonus = Byte.parseByte(infos[19]);
            timeEff_exp_bonus = Integer.parseInt(infos[20]);
        } catch (Exception e) {
        }
        try {
            countFinishDay = Byte.parseByte(infos[21]);
            countLoopDay = Byte.parseByte(infos[22]);
            String strLoopDay = infos[23].trim();
            if (!strLoopDay.isEmpty()) {
                String[] strs = strLoopDay.split("#");
                int count = Integer.parseInt(strs[0]);
                int countMax = Integer.parseInt(strs[1]);
                int killId = Integer.parseInt(strs[2]);
                int mapId = Map.npcMapIds[killId];
                taskLoopDay = new TaskOrder(0, count, countMax, AlertFunction.TASK_LOOPDAY(getSession()), NJUtil.replace(AlertFunction.TASK_NOTE(getSession()), ServerController.mapTemplates.get(mapId).getName(getSession())), killId, mapId);
            }
            countLoopBoos = Byte.parseByte(infos[24]);
            String strLoopBoss = infos[25].trim();
            if (!strLoopBoss.isEmpty()) {
                String[] strs = strLoopBoss.split("#");
                int count = Integer.parseInt(strs[0]);
                int npcTemplateId = Integer.parseInt(strs[1]);
                for (int i = 0; i < Map.tathus.length; ++i) {
                    BossTaThu tathu = Map.tathus[i];
                    if (tathu.npcTemplateId == npcTemplateId) {
                        taskLoopBoss = new TaskOrder(1, count, 1, AlertFunction.TASK_LOOPBOSS(getSession()), NJUtil.replace(AlertFunction.TASK_NOTE(getSession()), ServerController.mapTemplates.get(tathu.mapTemplateId).getName(getSession())), tathu.npcTemplateId, tathu.mapTemplateId);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            countLoopDay = maxLoopDay;
            countLoopBoos = maxLoopBoss;
        }
        try {
            limitKynangso = Byte.parseByte(infos[26]);
            limitTiemnangso = Byte.parseByte(infos[27]);
        } catch (Exception e) {
        }
        try {
            pointClan = Integer.parseInt(infos[28]);
        } catch (Exception e) {
        }
        try {
            timeOutClan = Integer.parseInt(infos[29]);
        } catch (Exception e) {
        }
        try {
            countUseTTL = Byte.parseByte(infos[30]);
        } catch (Exception e) {
            countUseTTL = defaultCountTTL;
        }
        try {
            pointClanWeek = Integer.parseInt(infos[31]);
            countPB = Byte.parseByte(infos[32]);
            pointPB = Integer.parseInt(infos[33]);
            timePB = Integer.parseInt(infos[34]);
            friendPB = Integer.parseInt(infos[35]);
        } catch (Exception e) {
            pointClanWeek = 0;
            countPB = maxPhoBan;
            pointPB = 0;
            timePB = 0;
            friendPB = 0;
        }
        try {
            pointCT = Integer.parseInt(infos[36]);
            resultCT = Integer.parseInt(infos[37]);
            pointPKCT = Integer.parseInt(infos[38]);
        } catch (Exception e) {
            int n = 0;
            resultCT = n;
            pointCT = n;
        }
        try {
            countChatAdmin = Integer.parseInt(infos[39]);
        } catch (Exception e) {
            countChatAdmin = maxChatAdm;
        }
        try {
            countUseHDL = Byte.parseByte(infos[40]);
        } catch (Exception e) {
            countUseHDL = defaultCountHDL;
        }
        try {
            pointTT = Integer.parseInt(infos[41]);
        } catch (Exception e) {
            pointTT = 0;
        }
        try {
            limitBanhPhongloi = Byte.parseByte(infos[42]);
            limitBanhBangHoa = Byte.parseByte(infos[43]);
        } catch (Exception e) {
        }
        try {
            countLoiDai = Integer.parseInt(infos[44]);
            pointLoiDai = Integer.parseInt(infos[45]);
        } catch (Exception e) {
        }
        try {
            countctkeo = Integer.parseInt(infos[46]);
        } catch (Exception e) {
        }
        try {
            typeNvbian = Byte.parseByte(infos[47]);
            countNvbian = Byte.parseByte(infos[48]);
        } catch (Exception e) {
        }
        try {
            timeLiveCoppy = Long.parseLong(infos[49]);
        } catch (Exception e) {
        }
        try {
            expSkillPhanthan = Long.parseLong(infos[50]);
        } catch (Exception e) {
        }
        try {
            switchNewExp = Byte.parseByte(infos[51]);
        } catch (Exception e) {
        }
        level = getLevel();
        if (switchNewExp == 0) {
            long[] infolv = getInfoLevel();
            long pc = infolv[1] * 10000L / exps[(int) infolv[0]];
            long pcExpNew = exps1[(int) infolv[0]] * pc / 10000L;
            exp = getMaxExp1((int) infolv[0] - 1) + pcExpNew;
            switchNewExp = 1;
            level = getLevel();
        }
        if (switchNewExp == 1) {
            exp_down = 0L;
            switchNewExp = 2;
        }
        try {
            timeEff_ThiLuyen = Integer.parseInt(infos[52]);
        } catch (Exception e) {
        }
        try {
            effId_KhaiThienNhanPhu = Byte.parseByte(infos[53]);
        } catch (Exception e) {
        }
        try {
            timeKhai_Thien_Nhan_Phu = Integer.parseInt(infos[54]);
        } catch (Exception e) {
        }
        try {
            haveCapcha = infos[55].trim().equals("1");
        } catch (Exception e) {
        }
        try {
            maxKillMonster = Short.parseShort(infos[56]);
        } catch (Exception e) {
            if (level < 20) {
                maxKillMonster = 50;
            } else {
                maxKillMonster = (short) (NJUtil.random.nextInt(201) + 600 + level * 5);
            }
        }
        try {
            countMonsKill = Short.parseShort(infos[57]);
        } catch (Exception e) {
            countMonsKill = 0;
        }
        try {
            timeKillMonsterLangCo = Byte.parseByte(infos[58]);
        } catch (Exception e) {
        }
        try {
            point3Arena = Integer.parseInt(infos[59].trim());
        } catch (Exception e) {
            point3Arena = 0;
        }
        try {
            countLoiDaiClass = Integer.parseInt(infos[60]);
            pointLoiDaiClass = Integer.parseInt(infos[61]);
        } catch (Exception e) {
        }
        try {
            setInfoNguyetNhanTask(infos[62].trim());
        } catch (Exception e) {
        }
        try {
            totalNvNguyetNhan = Short.parseShort(infos[63]);
            useDanhVongPhu = Byte.parseByte(infos[64]);
        } catch (Exception e) {
        }
    }

    public void setItemInfo(String info, boolean isList) {
        itemInfo = info.trim();
        if (info.isEmpty()) {
            return;
        }
        String[] infos = info.split("&");
        String[] bagInfos = null;
        String[] boxInfos = null;
        String[] bodyInfos = null;
        String[] monInfos = null;
        try {
            bagInfos = (infos[0].trim() + " ").split("@");
        } catch (Exception e) {
        }
        try {
            boxInfos = (infos[1].trim() + " ").split("@");
        } catch (Exception e) {
        }
        try {
            bodyInfos = (infos[2].trim() + " ").split("@");
        } catch (Exception e) {
        }
        try {
            monInfos = (infos[3].trim() + " ").split("@");
        } catch (Exception e) {
        }
        itemBags = new Item[bag];
        itemBoxs = new Item[box];
        if (!isList && bagInfos != null) {
            itemBags(bagInfos);
        }
        if (!isList && boxInfos != null) {
            itemBoxs(boxInfos);
        }
        if (bodyInfos != null) {
            itemBodys(bodyInfos);
        }
        if (monInfos != null) {
            itemMons(monInfos);
        }
    }

    public void itemBags(String[] itemInfos) {
        for (String bagInfo : itemInfos) {
            if (!bagInfo.trim().isEmpty()) {
                Item it = Item.parseItem(bagInfo, playerId);
                if (it == null) {
                    break;
                }
                if (resetCa && it.template.itemTemplateId == 600) {
                    addXu(it.quantity * 2000);
                } else {
                    if (it.isDaTinhLuyen() && (it.expires == 0L || it.expires == -1L)) {
                        it.expires = System.currentTimeMillis() + 30L * 24L * 60L * 60000L; // 30 day
                    }
                    if (it.indexUI < itemBags.length) {
                        itemBags[it.indexUI] = it;
                    }
                    try {
                        if (it.getTemplateId() == 568 && (it.options == null || it.options.size() == 0)) {
                            it.isLock = false;
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 30;
                            option.active = 1;
                            option.optionTemplate = ServerController.iOptionTemplates.get(100);
                            it.options.add(option);
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                        } else if (it.getTemplateId() == 569 && (it.options == null || it.options.size() == 0)) {
                            it.isLock = false;
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 300;
                            option.optionTemplate = ServerController.iOptionTemplates.get(99);
                            it.options.add(option);
                            option.active = 1;
                            it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                        } else if ((it.getTemplateId() == 524 || it.getTemplateId() == 485) && (it.options == null || it.options.size() == 0)) {
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 0;
                            option.optionTemplate = ServerController.iOptionTemplates.get(65);
                            it.options.add(option);
                            option = new ItemOption();
                            option.param = 1000;
                            option.optionTemplate = ServerController.iOptionTemplates.get(66);
                            it.options.add(option);
                        } else if (it.getTemplateId() == 443 && (it.options == null || it.options.size() == 0)) {
                            it.options = new Vector<>();
                            ItemOption option = new ItemOption();
                            option.param = 0;
                            option.optionTemplate = ServerController.iOptionTemplates.get(65);
                            it.options.add(option);
                            option = new ItemOption();
                            option.param = 1000;
                            option.optionTemplate = ServerController.iOptionTemplates.get(66);
                            it.options.add(option);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public void itemBoxs(String[] itemInfos) {
        for (String boxInfo : itemInfos) {
            if (!boxInfo.trim().isEmpty()) {
                Item it = Item.parseItem(boxInfo, playerId);
                if (it == null) {
                    break;
                }
                if (it.isDaTinhLuyen() && (it.expires == 0L || it.expires == -1L)) {
                    it.expires = System.currentTimeMillis() + 30L * 24L * 60L * 60000L; // 30 day
                }
                if (it.indexUI < itemBoxs.length) {
                    itemBoxs[it.indexUI] = it;
                }
                try {
                    if (it.getTemplateId() == 568 && (it.options == null || it.options.size() == 0)) {
                        it.isLock = false;
                        it.options = new Vector<>();
                        ItemOption option = new ItemOption();
                        option.param = 30;
                        option.active = 1;
                        option.optionTemplate = ServerController.iOptionTemplates.get(100);
                        it.options.add(option);
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    } else if (it.getTemplateId() == 569 && (it.options == null || it.options.size() == 0)) {
                        it.isLock = false;
                        it.options = new Vector<>();
                        ItemOption option = new ItemOption();
                        option.param = 300;
                        option.optionTemplate = ServerController.iOptionTemplates.get(99);
                        it.options.add(option);
                        option.active = 1;
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void itemBodys(String[] itemInfos) {
        for (String bodyInfo : itemInfos) {
            if (!bodyInfo.trim().isEmpty()) {
                Item it = Item.parseItem(bodyInfo, playerId);
                if (it == null) {
                    break;
                }
                if (it.isDaTinhLuyen() && (it.expires == 0L || it.expires == -1L)) {
                    it.expires = System.currentTimeMillis() + 30L * 24L * 60L * 60000L; // 30 day
                }
                if (it.indexUI < itemBodys.length) {
                    itemBodys[it.indexUI] = it;
                }
                try {
                    if (it.getTemplateId() == 568 && (it.options == null || it.options.size() == 0)) {
                        it.isLock = false;
                        it.options = new Vector<>();
                        ItemOption option2 = new ItemOption();
                        option2.param = 30;
                        option2.active = 1;
                        option2.optionTemplate = ServerController.iOptionTemplates.get(100);
                        it.options.add(option2);
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    } else if (it.getTemplateId() == 569 && (it.options == null || it.options.size() == 0)) {
                        it.isLock = false;
                        it.options = new Vector<>();
                        ItemOption option2 = new ItemOption();
                        option2.param = 300;
                        option2.optionTemplate = ServerController.iOptionTemplates.get(99);
                        it.options.add(option2);
                        option2.active = 1;
                        it.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void itemMons(String[] itemInfos) {
        for (String monInfo : itemInfos) {
            if (!monInfo.trim().isEmpty()) {
                Item it = Item.parseItem(monInfo, playerId);
                if (it != null && it.indexUI < itemMons.length) {
                    itemMons[it.indexUI] = it;
                }
            }
        }
    }

    public void showAlert() {
        if (!isSendAlert) {
            /*try {
                Message message = new Message(Cmd.CHAT_MAP);
                message.writeInt(playerId);
                message.writeUTF("Chơi game đều độ, giữ gìn sức khoẻ.");
                getSession().sendMessage(message);
            } catch (Exception e) {
            }*/
            isSendAlert = true;
        }
        if (haveCapcha) {
            try {
                initCapCha();
                haveCapcha = false;
            } catch (Exception e) {
                LOGGER.error(getStringBaseInfo(), e);
            }
        }
    }

    public void skillSelect(Skill skill, boolean isUpdate) {
        try {
            if (skill == null || skill.template.type == 0) {
                return;
            }
            myskill = skill;
            if (isUpdate) {
                updateData();
            }
            if (dataRms[isNhanban() ? 0 : 1][2] == null) {
                dataRms[isNhanban() ? 0 : 1][2] = new byte[1];
            }
            dataRms[isNhanban() ? 0 : 1][2][0] = (byte) myskill.template.skillTemplateId;
        } catch (Exception e) {
        }
    }

    public void switchMainCharAndCopyNPCTajma(int menuId) {
        if (menuId == 3) {
            if (isMainchar && playercopy != null) {
                clearAllEff(false);
                try {
                    Message message = NJUtil.messageNotMap(Cmd.CLEAR_TASK);
                    NJUtil.sendMessage(getSession(), message);
                } catch (Exception e) {
                }
                isMainchar = false;
                doSwitchBetweenMainCopy();
            }
        } else if (menuId == 4 && !isMainchar && playercopy != null) {
            clearAllEff(false);
            try {
                Message message = NJUtil.messageNotMap(Cmd.CLEAR_TASK);
                NJUtil.sendMessage(getSession(), message);
            } catch (Exception e) {
            }
            isMainchar = true;
            doSwitchBetweenMainCopy();
        }
    }

    public void throwItem(Item item) {
        Message message;
        try {
            if (idTrade > -1) {
                doCancelTrade();
                return;
            }
            if (item.isSaveLog()) {
                savezbLog("Bo item", String.valueOf(item.getTemplateId()));
            }
            removeItem(item, 3);
            int d = NJUtil.randomNumber(15);
            ItemMap itemMap = new ItemMap(item, -1, x + ((map.tick % 2 == 0) ? d : (-d)), y);
            itemMap.y = autoFall(itemMap.x, itemMap.y);
            if (itemMap.x < 50) {
                itemMap.x = 50 + ((map.tick % 2 == 0) ? d : (-d));
            }
            if (itemMap.x > map.template.w - 50) {
                itemMap.x = map.template.w - 50 + ((map.tick % 2 == 0) ? d : (-d));
            }
            map.addItemMap(itemMap);
            message = new Message(Cmd.ME_THROW);
            message.writeByte(item.indexUI);
            message.writeShort(itemMap.itemMapId);
            message.writeShort(itemMap.x);
            message.writeShort(itemMap.y);
            NJUtil.sendMessage(getSession(), message);
            message = new Message(Cmd.PLAYER_THROW);
            message.writeInt(playerId);
            message.writeShort(itemMap.itemMapId);
            message.writeShort(itemMap.item.getTemplateId());
            message.writeShort(itemMap.x);
            message.writeShort(itemMap.y);
            sendToPlayer(message, false);
        } catch (Exception e) {
        }
    }

    public void tinhTu(int optionId) {
        try {
            int[] points = { 50, 100, 150 };
            if (countFreeBag() < 1) {
                NJUtil.sendServer(getSession(), AlertFunction.BAG_FULL(getSession()));
                return;
            }
            if (pointTT < points[optionId]) {
                NJUtil.sendDialog(getSession(), AlertFunction.TINH_TU4(getSession()));
                return;
            }
            pointTT -= points[optionId];
            int[] ids = { 0, 397, 398, 399, 400, 401, 402 };
            Item item = new Item(ids[classId], true, "tinhtu char");
            switch (optionId) {
                case 0:
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(3);
                    break;
                case 1:
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
                    break;
                case 2:
                    item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(15);
                    break;
            }
            item.createOptionBiKip();
            addItemToBagNoDialog(item);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void topCaothu() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Math.min(ServerController.topCaothu.size(), 10); ++i) {
            if (!ServerController.topCaothu.get(i).trim().isEmpty()) {
                str.append(i + 1).append(". ").append(ServerController.topCaothu.get(i)).append("\n");
            }
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_LEVEL(getSession()), str.toString());
    }

    public void topChienTruong() {
        String str = "";
        LocalTime timeNow = LocalTime.now();
        int hour = timeNow.getHour();
        int minute = timeNow.getMinute();
        if (hour == 13 || (hour == 14 && minute < 30) || hour == 16 || (hour == 17 && minute < 30) || hour == 19 || (hour == 20 && minute < 30)) {
            str = Map.tongketCT();
            NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.SUMMARY(getSession()), str);
        } else {
            if (pointCT > 0) {
                if (resultCT == 1) {
                    str = str + AlertFunction.ACCUMULATION(getSession()) + ": " + pointCT + " + " + pointCT + " (" + AlertFunction.REWARD1(getSession()) + ")\n";
                } else {
                    str = str + AlertFunction.ACCUMULATION(getSession()) + ": " + pointCT + "\n";
                }
            }
            str = str + Map.strTongket;
            if (str.trim().isEmpty()) {
                str = str + AlertFunction.NOT_INFO(getSession());
            }
            sendResultCT(getSession(), str);
        }
    }

    public void topDaiGia() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Math.min(ServerController.topDaiGia.size(), 10); ++i) {
            if (!ServerController.topDaiGia.get(i).trim().isEmpty()) {
                str.append(i + 1).append(". ").append(ServerController.topDaiGia.get(i)).append("\n");
            }
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_DAI_GIA(getSession()), str.toString());
    }

    public void topGiaToc() {
        ServerController.clans.sort(Clan.sort);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Math.min(ServerController.clans.size(), 10); ++i) {
            str.append(i + 1).append(". ").append(AlertFunction.CLAN(getSession())).append(" ").append(ServerController.clans.get(i).name).append(" ").append(AlertFunction.LEVEL(getSession())).append(" ").append(ServerController.clans.get(i).level).append(" ").append(AlertFunction.BY(getSession())).append(" ").append(ServerController.clans.get(i).main_name).append(" ").append(AlertFunction.CLAN_MAIN(getSession())).append(" ").append(ServerController.clans.get(i).members.size()).append("/").append(ServerController.clans.get(i).getMax()).append("\n");
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_CLAN(getSession()), str.toString());
    }

    public void topHangdong() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < Math.min(ServerController.topHangdong.size(), 10); ++i) {
            str.append(i + 1).append(". ").append(ServerController.topHangdong.get(i).getStr(getSession())).append("\n");
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_HANG(getSession()), str.toString());
    }

    public void topTaiNang() {
        Vector<TopTainang> vClear = new Vector<>(ServerController.topTaiNang);
        vClear.sort(TopTainang.sort);
        StringBuilder str = new StringBuilder();
        TopTainang tt;
        for (int i = 0; i < vClear.size(); ++i) {
            tt = vClear.get(i);
            if (i < 10) {
                str.append(i + 1).append(". ").append(tt.getStr(getSession())).append("\n");
            } else {
                ServerController.topTaiNang.remove(tt);
            }
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_TAI_NANG(getSession()), String.valueOf(AlertFunction.POINT_LOIDAI(getSession())) + pointLoiDai + "\n" + str);
    }

    public void topTaiNangClass(int index) {
        Vector<TopTainang> vClear = new Vector<>(ServerController.topTaiNangClass.get(index));
        vClear.sort(TopTainang.sort);
        StringBuilder str = new StringBuilder();
        TopTainang tt;
        for (int i = 0; i < vClear.size(); ++i) {
            tt = vClear.get(i);
            if (i < 10) {
                str.append(i + 1).append(". ").append(tt.getStr(getSession())).append("\n");
            } else {
                //noinspection SuspiciousMethodCalls
                ServerController.topTaiNangClass.remove(tt);
            }
        }
        NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.TOP_DE_NHAT(getSession()), String.valueOf(AlertFunction.POINT_LOIDAI(getSession())) + pointLoiDaiClass + "\n" + str);
    }

    public void update() {
        try {
            if (!map.isDunPB) {
                while (listItemPickAuto.size() > 0) {
                    doPickItemAuto(listItemPickAuto.remove(0));
                }
            } else if (listItemPickAuto.size() > 0) {
                listItemPickAuto.clear();
            }
            if (idTrade > -1) {
                Trade trade = ALL_CHAR_TRADE.get(idTrade);
                if (trade == null) {
                    idTrade = -1;
                } else if (trade.isTimeOver()) {
                    ALL_CHAR_TRADE.remove(idTrade);
                    idTrade = -1;
                }
            }
            if (!isControlCharNhanBan() && playercopy != null) {
                playercopy.updateThucAn(playercopy);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
        try {
            if ((System.currentTimeMillis() - timeAutoSave) / 1000L % 10L == 0L) {
                if (fullSet10 >= 10 || fullSet6 >= 10 || fullSet8 >= 10) {
                    sendEffToolNgocKham();
                }
                if (itemBodys[11].isMatNaSantaClaus()) {
                    if (itemBodys[11].upgrade > 5 && itemBodys[11].upgrade <= 9) {
                        addEffectPlayer(EffectTool.HOA_TUYET_1, 5000, 5000, 0);
                    } else if (itemBodys[11].upgrade > 9) {
                        addEffectPlayer(EffectTool.HOA_TUYET_2, 5000, 5000, 0);
                    }
                }
            }
            if ((System.currentTimeMillis() - timeAutoSave) / 1000L % 2L == 0L && id_private_eff > -1 && !map.isLang()) {
                if (conn.isVersion160()) {
                    addPriveEffectPlayer(id_private_eff, -1, 0, 0);
                } else {
                    addPriveEffectPlayer(id_private_eff, 50000, 0, 0);
                }
            }
            if (System.currentTimeMillis() - timeAutoSave >= NJUtil.getMilisByMinute(15)) {
                timeAutoSave = System.currentTimeMillis();
                if (getSession() != null) {
                    Player pSave = this;
                    Database.savePlayer(pSave, map.getTemplateId());
                }
            }
        } catch (Exception e) {
        }
        try {
            if (capcha != null) {
                capcha.map = map;
                if (!map.isLang()) {
                    capcha.update(map, -1);
                }
            }
            if (privateNpc != null) {
                boolean iscurrent = privateNpc.map.mapId == map.mapId;
                if (!iscurrent) {
                    if (!map.isLang()) {
                        privateNpc.map = map;
                        map.addDynamicMod(this, privateNpc);
                    } else {
                        privateNpc.status = Npc.STATUS_DIE;
                        npcDie(privateNpc, Npc.STATUS_DIE, false);
                    }
                }
                privateNpc.update(map, -1);
            }
            if (!map.isLangCo()) {
                timeKillMonsterLangCo = 0;
            } else {
                if (pk >= 1 || timeKillMonsterLangCo >= 30) {
                    map.playerOuts.add(this);
                    Map mapNext = Map.find(this, mapTemplateId_focus);
                    if (mapNext != null) {
                        x = mapNext.template.defaultX;
                        y = mapNext.template.defaultY;
                        mapNext.waitGoInMap(this);
                    }
                }
                if (!isAcountTest(this) && System.currentTimeMillis() - timeStartPlay >= 60000L) {
                    ++timeKillMonsterLangCo;
                    timeStartPlay = System.currentTimeMillis();
                }
            }
            if (timeCauca == 0 && itemCauca != null && (itemCauca.getTemplateId() == 548 || itemCauca.getTemplateId() == 597)) {
                if (itemCauca.getTemplateId() == 548) {
                    Item cc = findItemBag(itemCauca.getTemplateId());
                    useItemUpToUp(cc);
                    sendUseItemUpToUp(cc.indexUI, 1);
                    endWaitCauCa(AlertFunction.NOT_FOUND(getSession()));
                    return;
                } else if (itemCauca.getTemplateId() == 597) {
                    Item giun = findItemBag(603);
                    Item de = findItemBag(602);
                    if (giun == null && de == null) {
                        endWaitCauCa(AlertFunction.NOT_FOUND(getSession()));
                        return;
                    }
                    int[] idTemplate = { 598, 599, 600 };
                    if (NJUtil.probability(135, 865) == 0) {
                        int uptuyetsa = 0;
                        int uplinhtam = 0;
                        if (giun != null) {
                            uptuyetsa = 50;
                            uplinhtam = 20;
                            useItemUpToUp(giun);
                            sendUseItemUpToUp(giun.indexUI, 1);
                        } else {
                            useItemUpToUp(de);
                            sendUseItemUpToUp(de.indexUI, 1);
                        }
                        int index = NJUtil.probability(965, 30 + uptuyetsa, 5 + uplinhtam);
                        addItemToBagNoDialog(new Item(idTemplate[index], true, "update char"));
                        endWaitCauCa(null);
                    } else {
                        if (giun != null) {
                            useItemUpToUp(giun);
                            sendUseItemUpToUp(giun.indexUI, 1);
                        } else {
                            useItemUpToUp(de);
                            sendUseItemUpToUp(de.indexUI, 1);
                        }
                        endWaitCauCa(AlertFunction.NOT_FOUND(getSession()));
                    }
                }
            } else if (timeCauca > 0 && itemCauca != null) {
                --timeCauca;
            }
        } catch (Exception e) {
        }
        if (isChangeMap) {
            lastMoveX = 0;
            lastMoveY = 0;
        }
        if (getSession() != null && System.currentTimeMillis() - getSession().timeSend >= 1000L && (lastMoveX != 0 || lastMoveY != 0)) {
            if (Math.abs(lastMoveX - x) < 30 || Math.abs(lastMoveX - y) < 30) {
                doMove(lastMoveX, lastMoveY);
            }
            lastMoveX = 0;
            lastMoveY = 0;
            y = autoFall(x, y);
            doMove(x, y);
        }
        removePlayerCopyMap();
        doAddGiftNewDay();
    }

    public void updateAll() {
        try {
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_ALL);
            message.writeInt(playerId);
            if (playerMain.clan == null) {
                message.writeUTF("");
            } else {
                message.writeUTF(playerMain.clan.name);
                message.writeByte(playerMain.clan.getType(playerMain.name));
            }
            message.writeByte(playerMain.getTaskFinish());
            message.writeByte(playerMain.gender);
            if (playerMain.itemBodys != null && playerMain.itemBodys[11] != null) {
                message.writeShort(playerMain.itemBodys[11].template.part);
            } else {
                message.writeShort(playerMain.headId);
            }
            message.writeByte(playerMain.speed);
            message.writeUTF(playerMain.name);
            message.writeByte(playerMain.pk);
            message.writeByte(playerMain.getTypePk());
            message.writeInt(playerMain.hp_full);
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.mp_full);
            message.writeInt(playerMain.mp);
            message.writeLong(playerMain.exp);
            message.writeLong(playerMain.exp_down);
            message.writeShort(playerMain.getEff5BuffHp());
            message.writeShort(playerMain.getEff5BuffMp());
            message.writeByte(playerMain.classId);
            message.writeShort(playerMain.potential_point);
            message.writeShort(playerMain.p_strength);
            message.writeShort(playerMain.p_agile);
            message.writeInt(playerMain.p_hp);
            message.writeInt(playerMain.p_mp);
            message.writeShort(playerMain.skill_point);
            message.writeByte(playerMain.skills.size());
            for (int i = 0; i < playerMain.skills.size(); ++i) {
                message.writeShort(playerMain.skills.get(i).skillId);
            }
            message.writeInt(getXu());
            message.writeInt(getYen());
            message.writeInt(getLuong());
            message.writeByte(bag);
            int countBall = 0;
            for (Item itemBag : itemBags) {
                if (itemBag != null) {
                    if (itemBag.template.type == 22) {
                        ++countBall;
                    }
                    message.writeShort(itemBag.template.itemTemplateId);
                    message.writeBoolean(itemBag.isLock);
                    if (itemBag.isTypeBody() || itemBag.isTypeMon() || (itemBag.isTypeGem() && conn.isVersion144())) {
                        message.writeByte(itemBag.upgrade);
                    }
                    message.writeBoolean(itemBag.expires != -1L);
                    message.writeShort(itemBag.quantity);
                } else {
                    message.writeShort(-1);
                }
            }
            if (getSession() != null && getSession().version >= 105) {
                for (int j = 0; j < playerMain.itemBodys.length; ++j) {
                    if (playerMain.itemBodys[j] != null) {
                        message.writeShort(playerMain.itemBodys[j].template.itemTemplateId);
                        message.writeByte(playerMain.itemBodys[j].upgrade);
                        message.writeByte(playerMain.itemBodys[j].sys);
                    } else {
                        message.writeShort(-1);
                    }
                }
            } else {
                for (int j = 0; j < playerMain.itemBodys.length; ++j) {
                    if (playerMain.itemBodys[j] != null) {
                        message.writeShort(playerMain.itemBodys[j].template.itemTemplateId);
                        message.writeByte(playerMain.itemBodys[j].upgrade);
                        message.writeByte(playerMain.itemBodys[j].sys);
                    }
                }
            }
            message.writeBoolean(isMainchar);
            message.writeBoolean(isNhanban());
            short[] idPartTHoiTrang = getIdPartThoiTrang();
            for (short i : idPartTHoiTrang) {
                message.writeShort(i);
            }
            NJUtil.sendMessage(getSession(), message);
            if (countBall >= 7) {
                ServerController.doRequestIcon(getSession(), 1670);
                ServerController.doRequestIcon(getSession(), 1671);
                ServerController.doRequestIcon(getSession(), 1672);
                ServerController.doRequestIcon(getSession(), 1673);
                ServerController.doRequestIcon(getSession(), 1674);
                ServerController.doRequestIcon(getSession(), 1675);
                ServerController.doRequestIcon(getSession(), 1676);
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateBoardNap(String playerName, int coinAdd) {
        Player p = ServerController.hnPlayers.get(playerName);
        if (p != null) {
            p.sendUpdateCoinBag(coinAdd);
            NJUtil.sendAlertDialogInfo(p.getSession(), AlertFunction.SYSTEM(p.getSession()), AlertFunction.YOU_GET(p.getSession()) + " " + coinAdd + " " + AlertFunction.COIN(p.getSession()));
        } else {
            Database.insertBoardPlayer(playerName, coinAdd, name);
        }
    }

    public void updateClass() {
        try {
            Player playerMain = getPlayerMainControl();
            playerMain.refreshSkill();
            playerMain.refreshPotential();
            playerMain.myskill = null;
            playerMain.skills.clear();
            playerMain.updateData();
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_CLASS);
            writeParam(message);
            message.writeShort(playerMain.p_strength);
            message.writeShort(playerMain.p_agile);
            message.writeInt(playerMain.p_hp);
            message.writeInt(playerMain.p_mp);
            message.writeByte(playerMain.classId);
            message.writeShort(playerMain.skill_point);
            message.writeShort(playerMain.potential_point);
            NJUtil.sendMessage(getSession(), message);
            message = NJUtil.messageSubCommand(Cmd.PLAYER_LOAD_ALL);
            playerLoadAll(message, this);
            sendToPlayer(message, false);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateFastHp(int hpAdd) {
        try {
            Player playerMain = getPlayerMainControl();
            playerMain.hp += hpAdd;
            if (playerMain.hp > playerMain.hp_full) {
                playerMain.hp = playerMain.hp_full;
            }
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_HP);
            message.writeInt(playerMain.hp);
            NJUtil.sendMessage(getSession(), message);
            playerLoadHp();
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateGold() {
        try {
            if (playerId < 0) {
                return;
            }
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_GOLD);
            message.writeInt(getLuong());
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateHackPass2() {
        ++countPassErr;
        if (countPassErr > 100) {
            ServerController.khoaLogin(getSession().username + getSession().getClientAddress());
            ServerController.hLogins.get(getSession().username + getSession().getClientAddress()).count = 10;
            getSession().disconnect("Player.updateHackPass2");
        }
    }

    public void updateHp(int hpAdd) {
        try {
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(21);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = 3000;
            eff.param = hpAdd;
            addEffect(eff);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateMp(int mpAdd) {
        try {
            Player playerMain = getPlayerMainControl();
            playerMain.mp += mpAdd;
            if (playerMain.mp > playerMain.mp_full) {
                playerMain.mp = playerMain.mp_full;
            }
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_MP);
            message.writeInt(playerMain.mp);
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateIndexOption(Item item, int hp_begin, int mp_begin, int dame_begin) {
        if (item != null && item.isTypeBody()) {
            int lvGem = item.getLevelGem();
            if (lvGem >= 10) {
                ++fullSet10;
                ++fullSet6;
                ++fullSet8;
            } else if (lvGem >= 8) {
                ++fullSet8;
                ++fullSet6;
            } else if (lvGem >= 6) {
                ++fullSet6;
            }
        }
        if (item != null && item.options != null) {
            for (int j = 0; j < item.options.size(); ++j) {
                ItemOption option = item.options.get(j);
                if (option.optionTemplate.type == 0 ||
                    option.optionTemplate.type == 1 ||
                    option.optionTemplate.type == 8 ||
                    (option.optionTemplate.type >= 2 && option.active == 1)
                ) {
                    switch (option.optionTemplate.itemOptionTemplateId) {
                        case 0:
                            if (isSysOut()) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 1:
                            if (!isSysOut()) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 2:
                        case 96:
                        case 70:
                        case 33:
                        case 11:
                            resFire += (short) option.param;
                            break;
                        case 3:
                        case 95:
                        case 71:
                        case 34:
                        case 12:
                            resIce += (short) option.param;
                            break;
                        case 4:
                        case 97:
                        case 72:
                        case 35:
                        case 13:
                            resWind += (short) option.param;
                            break;
                        case 5:
                        case 84:
                        case 78:
                        case 68:
                        case 17:
                            miss += (short) option.param;
                            break;
                        case 6:
                        case 82:
                        case 77:
                        case 32:
                            hp_full += option.param;
                            break;
                        case 7:
                        case 83:
                        case 29:
                        case 19:
                            mp_full += option.param;
                            break;
                        case 8:
                            if (isSysOut()) {
                                dame_full += dame_begin * option.param / 100;
                                break;
                            }
                            break;
                        case 9:
                            if (!isSysOut()) {
                                dame_full += dame_begin * option.param / 100;
                                break;
                            }
                            break;
                        case 10:
                        case 86:
                        case 75:
                        case 18:
                            exactly += (short) option.param;
                            break;
                        case 14:
                        case 92:
                        case 69:
                        case 37:
                            fatal += (short) option.param;
                            break;
                        case 15:
                        case 91:
                            reactDame += (short) option.param;
                            break;
                        case 16:
                            speed += (byte) (speedHard * option.param / 100);
                            break;
                        case 20:
                        case 81:
                        case 36:
                            resFire += (short) option.param;
                            resIce += (short) option.param;
                            resWind += (short) option.param;
                            break;
                        case 21:
                            if (classId == 1) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 22:
                            if (classId == 2) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 23:
                            if (classId == 3) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 24:
                            if (classId == 4) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 25:
                            if (classId == 5) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 26:
                            if (classId == 6) {
                                dame_full += option.param;
                                break;
                            }
                            break;
                        case 27:
                            eff5BuffMp += option.param;
                            break;
                        case 28:
                            mp_full += mp_begin * option.param / 100;
                            break;
                        case 30:
                            eff5BuffHp += option.param;
                            break;
                        case 31:
                            hp_full += hp_begin * option.param / 100;
                            break;
                        case 38:
                        case 90:
                        case 89:
                        case 88:
                        case 87:
                        case 76:
                        case 73:
                            dame_full += option.param;
                            break;
                        case 39:
                        case 67:
                            dameFatalUp += option.param;
                            break;
                        case 40:
                            timeDownFire += (short) option.param;
                            break;
                        case 41:
                            timeDownIce += (short) option.param;
                            break;
                        case 42:
                            timeDownWind += (short) option.param;
                            break;
                        case 43:
                            timeUpFire += (short) (option.param * 1000);
                            break;
                        case 44:
                            timeUpIce += (short) (option.param * 1000);
                            break;
                        case 45:
                            timeUpWind += (short) (option.param * 1000);
                            break;
                        case 46:
                        case 79:
                            dameFatalDown += option.param;
                            break;
                        case 47:
                        case 80:
                        case 74:
                            dameDown += (short) option.param;
                            break;
                        case 48:
                            dameDownFire += (short) option.param;
                            break;
                        case 49:
                            dameDownIce += (short) option.param;
                            break;
                        case 50:
                            dameDownWind += (short) option.param;
                            break;
                        case 51:
                            dameUpFire += (short) option.param;
                            break;
                        case 52:
                            dameUpIce += (short) option.param;
                            break;
                        case 53:
                            dameUpWind += (short) option.param;
                            break;
                        case 54:
                            perDameUpFire += (short) option.param;
                            break;
                        case 55:
                            perDameUpIce += (short) option.param;
                            break;
                        case 56:
                            perDameUpWind += (short) option.param;
                            break;
                        case 93:
                            speed += (byte) option.param;
                            break;
                        case 94:
                            dame_full += dame_begin * option.param / 100;
                            break;
                    }
                }
            }
            Vector<ItemOption> optiongem = item.getOptionGem();
            for (ItemOption option2 : optiongem) {
                switch (option2.optionTemplate.itemOptionTemplateId) {
                    case 120:
                        eff5BuffHpGem += option2.param;
                        break;
                    case 119:
                        eff5BuffMpGem += option2.param;
                        break;
                    case 114:
                        fatal += (short) option2.param;
                        if (fatal < 0) {
                            fatal = 0;
                            break;
                        }
                        break;
                    case 105:
                        damChiMang += option2.param;
                        break;
                    case 116:
                        exactly += (short) option2.param;
                        if (exactly < 0) {
                            exactly = 0;
                            break;
                        }
                        break;
                    case 125:
                        hp_full += option2.param;
                        if (hp_full <= 0) {
                            hp_full = 10;
                            break;
                        }
                        break;
                    case 121:
                        dameFatalDown += option2.param;
                        break;
                    case 118:
                        resFire += (short) option2.param;
                        resIce += (short) option2.param;
                        resWind += (short) option2.param;
                        break;
                    case 117:
                        mp_full += option2.param;
                        break;
                    case 115:
                        miss += (short) option2.param;
                        break;
                    case 126:
                        reactDame += (short) option2.param;
                        break;
                    case 103:
                        dam_player += option2.param;
                        break;
                    case 102:
                        dam_mob += option2.param;
                        break;
                    case 73:
                        dame_full += option2.param;
                        break;
                    case 124:
                        dameDown += (short) option2.param;
                        break;
                }
            }
        }
    }

    public void updateInfo() {
        try {
            if (playerId < 0) {
                return;
            }
            Player playerMain = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_INFO);
            message.writeInt(getXu());
            message.writeInt(getYen());
            message.writeInt(getLuong());
            message.writeInt(playerMain.hp);
            message.writeInt(playerMain.mp);
            message.writeByte(canMove());
            NJUtil.sendMessage(getSession(), message);
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateItemUse(int indexUI, Item item) {
        try {
            Player pUseItem = getPlayerMainControl();
            pUseItem.updateData();
            Message message = new Message(Cmd.ITEM_USE);
            message.writeByte(indexUI);
            writeParam(message);
            message.writeShort(pUseItem.getEff5BuffHp());
            message.writeShort(pUseItem.getEff5BuffMp());
            NJUtil.sendMessage(getSession(), message);
            if (item.template.type == 1) {
                playerLoadVuKhi(item.template.part);
            } else if (item.template.type == 2) {
                playerLoadAo(item.template.part);
            } else if (item.template.type == 6) {
                playerLoadQuan(item.template.part);
            } else if (item.template.type == 12) {
                playerLoadAoChoang(this, item.template.itemTemplateId);
            } else if (item.template.type == 13) {
                playerLoadGiaToc(this, item.template.itemTemplateId);
            } else if (item.template.type == 11) {
                playerLoadMatna(item.template.part);
            } else if (item.template.type == 10) {
                int npcId = -1;
                int type = 0;
                if (pUseItem.itemBodys[10].template.itemTemplateId == 246) {
                    npcId = 70;
                } else if (pUseItem.itemBodys[10].template.itemTemplateId == 419) {
                    npcId = 122;
                } else if (pUseItem.itemBodys[10].isPetNew()) {
                    npcId = pUseItem.itemBodys[10].template.getIDDataPaint();
                    type = 1;
                }
                pUseItem.playerLoadThuNuoi(npcId, type);
            } else if (!item.isTypeMon()) {
                playerLoadBody();
            }
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateLevel() {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_LEVEL);
            writeParam(message);
            message.writeLong(pUseItem.exp);
            message.writeShort(pUseItem.skill_point);
            message.writeShort(pUseItem.potential_point);
            message.writeShort(pUseItem.p_strength);
            message.writeShort(pUseItem.p_agile);
            message.writeInt(pUseItem.p_hp);
            message.writeInt(pUseItem.p_mp);
            NJUtil.sendMessage(getSession(), message);
            playerLoadLevel();
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updatePotential() {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.POTENTIAL_UP);
            writeParam(message);
            message.writeShort(pUseItem.potential_point);
            message.writeShort(pUseItem.p_strength);
            message.writeShort(pUseItem.p_agile);
            message.writeInt(pUseItem.p_hp);
            message.writeInt(pUseItem.p_mp);
            NJUtil.sendMessage(getSession(), message);
            playerLoadInfo();
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateSkill() {
        try {
            Player pUseItem = getPlayerMainControl();
            Message message = NJUtil.messageSubCommand(Cmd.ME_LOAD_SKILL);
            writeParam(message);
            message.writeShort(pUseItem.skill_point);
            message.writeByte(pUseItem.skills.size());
            for (int i = 0; i < pUseItem.skills.size(); ++i) {
                message.writeShort(pUseItem.skills.get(i).skillId);
            }
            NJUtil.sendMessage(getSession(), message);
            playerLoadInfo();
        } catch (IOException e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateSkillOption(SkillOption option, int dame_begin, int hp_begin, int mp_begin) {
        if (option == null || option.optionTemplate == null) {
            return;
        }
        switch (option.optionTemplate.skillOptionTemplateId) {
            case 0:
                if (isSysOut()) {
                    dame_full += dame_begin * option.param / 100;
                    break;
                }
                break;
            case 1:
                if (!isSysOut()) {
                    dame_full += dame_begin * option.param / 100;
                    break;
                }
                break;
            case 2:
                if (classId == 1 || classId == 2) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 3:
                if (classId == 3 || classId == 4) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 4:
                if (classId == 5 || classId == 6) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 5:
                if (classId == 1) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 6:
                if (classId == 3) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 7:
                if (classId == 5) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 8:
                if (classId == 2) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 9:
                if (classId == 4) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 10:
                if (classId == 6) {
                    dame_full += option.param;
                    break;
                }
                break;
            case 11:
                if (isSysOut()) {
                    dame_full += dame_begin * option.param / 100;
                    break;
                }
                dame_full += dame_begin * option.param / 100;
                break;
            case 12:
                exactly += (short) option.param;
                break;
            case 13:
                miss += (short) option.param;
                break;
            case 14:
                fatal += (short) option.param;
                break;
            case 15:
                reactDame += (short) option.param;
                break;
            case 16:
                speed += (byte) (speedHard * option.param / 100);
                break;
            case 17:
                hp_full += hp_begin * option.param / 100;
                break;
            case 18:
                mp_full += mp_begin * option.param / 100;
                break;
            case 19:
                resFire += (short) option.param;
                resIce += (short) option.param;
                resWind += (short) option.param;
                break;
            case 20:
                resFire += (short) option.param;
                break;
            case 21:
                resIce += (short) option.param;
                break;
            case 22:
                resWind += (short) option.param;
                break;
            case 23:
                resReactDame += (short) option.param;
                break;
            case 24:
                effFire += (short) option.param;
                break;
            case 25:
                effIce += (short) option.param;
                break;
            case 26:
                effWind += (short) option.param;
                break;
            case 28:
                liveBack += (short) option.param;
            case 31:
                missAll += (short) option.param;
                break;
            case 32:
                sysDown += (short) option.param;
                break;
            case 33:
                sysUp += (short) option.param;
                break;
            case 34:
                effFireBig += (short) option.param;
                break;
            case 35:
                effIceBig += (short) option.param;
                break;
            case 36:
                effWindBig += (short) option.param;
                break;
            case 37:
                timeDownFire -= (short) option.param;
                break;
            case 38:
                timeDownIce -= (short) option.param;
                break;
            case 39:
                timeDownWind -= (short) option.param;
                break;
            case 61:
                perDameInvi += option.param;
                break;
            case 62:
                timeKeepDao += option.param;
                break;
            case 63:
                autoUpHp += option.param;
                break;
            case 65:
                dameFatalUp += option.param;
                break;
            case 66:
                perUpBuff += option.param;
                break;
        }
    }

    public void updateThucAn(Player p) {
    }

    public void updateTypePk() {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.UPDATE_TYPE_PK);
            message.writeInt(playerId);
            message.writeByte(getTypePk());
            sendToPlayer(message, true);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void updateUseDiaDo(Item item) {
        removeItem(item, 3);
        sendClearItemBag(item.indexUI);
        boolean isHave = false;
        for (Item itemBag : itemBags) {
            if (itemBag != null && itemBag.template.itemTemplateId == 226) {
                isHave = true;
                break;
            }
        }
        for (Item itemBox : itemBoxs) {
            if (itemBox != null && itemBox.template.itemTemplateId == 226) {
                isHave = true;
                break;
            }
        }
        if (!isHave) {
            addItemToBagNoDialog(new Item(226, true, "updateUseDiaDo char 1"));
            int cl = NJUtil.randomNumber(5000) + 5000;
            sendUpdateCoinLockYen(cl);
        } else {
            sendUpdateCoinLockYen(100);
        }
        doTaskNext();
    }

    public void useBookSkill(Item item, int skillTemplateId) {
        try {
            Player playerMain = getPlayerMainControl();
            for (int i = 0; i < ServerController.nClasss.get(playerMain.classId).skillTemplates.size(); ++i) {
                SkillTemplate skillTemplate = ServerController.nClasss.get(playerMain.classId).skillTemplates.get(i);
                if (skillTemplate.skillTemplateId == skillTemplateId) {
                    for (int j = 0; j < skillTemplate.skills.size(); ++j) {
                        Skill skill = skillTemplate.skills.get(j);
                        if (skill.point == 1) {
                            Skill skillClone = skill.cloneSkill();
                            playerMain.skills.add(skillClone);
                            if (!playerMain.isNhanban() && playerMain.taskMain != null && playerMain.taskMain.template.taskId == 9 && playerMain.taskMain.index == 0) {
                                doTaskNext();
                            }
                            removeItem(item, 3);
                            Message message = NJUtil.messageSubCommand(Cmd.USE_BOOK_SKILL);
                            message.writeByte(item.indexUI);
                            message.writeShort(skillClone.skillId);
                            NJUtil.sendMessage(getSession(), message);
                            if (playerMain.myskill == null) {
                                playerMain.skillSelect(skillClone, true);
                            }
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useEff(int effId, int timeLength) {
        try {
            int param = 0;
            switch (effId) {
                case 0:
                    param = 3;
                    break;
                case 1:
                    param = 20;
                    break;
                case 2:
                    param = 30;
                    break;
                case 3:
                    param = 40;
                    break;
                case 4:
                    param = 50;
                    break;
                case 28:
                    param = 60;
                    break;
                case 30:
                    param = 75;
                    break;
                case 31:
                    param = 90;
                    break;
                case 35:
                    param = 120;
                    break;
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(effId);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = timeLength;
            eff.param = param;
            addEffect(eff);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useExpx2(int time) {
        try {
            Player playerMain = getPlayerMainControl();
            Effect ef = playerMain.isUsingX3();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            ef = playerMain.isUsingX4();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.id == 22) {
                    if (playerMain.effects.get(i).timeLength + time > 2000000000) {
                        playerMain.effects.get(i).timeLength = 2000000000;
                    } else {
                        Effect effect = playerMain.effects.get(i);
                        effect.timeLength += time;
                    }
                    int timeRemain = (int) (System.currentTimeMillis() / 1000L - playerMain.effects.get(i).timeStart);
                    Message message = NJUtil.messageSubCommand(Cmd.ME_EDIT_EFFECT);
                    message.writeByte(playerMain.effects.get(i).template.id);
                    message.writeInt(timeRemain);
                    message.writeInt(playerMain.effects.get(i).timeLength);
                    message.writeShort(playerMain.effects.get(i).param);
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(22);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = time;
            playerMain.effects.add(eff);
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useExpx3(int time) {
        try {
            Player playerMain = getPlayerMainControl();
            Effect ef = playerMain.isUsingX2();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            ef = playerMain.isUsingX4();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.id == 32) {
                    playerMain.effects.get(i).timeStart = (int) (System.currentTimeMillis() / 1000L);
                    playerMain.effects.get(i).timeLength = time;
                    int timeRemain = (int) (System.currentTimeMillis() / 1000L - playerMain.effects.get(i).timeStart);
                    Message message = NJUtil.messageSubCommand(Cmd.ME_EDIT_EFFECT);
                    message.writeByte(playerMain.effects.get(i).template.id);
                    message.writeInt(timeRemain);
                    message.writeInt(playerMain.effects.get(i).timeLength);
                    message.writeShort(playerMain.effects.get(i).param);
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(32);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = time;
            playerMain.effects.add(eff);
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useExpx4(int time) {
        try {
            Player playerMain = getPlayerMainControl();
            Effect ef = playerMain.isUsingX3();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            ef = playerMain.isUsingX2();
            if (ef != null) {
                playerMain.removeEffect(ef, true);
            }
            for (int i = 0; i < playerMain.effects.size(); ++i) {
                if (playerMain.effects.get(i).template.id == 33) {
                    playerMain.effects.get(i).timeStart = (int) (System.currentTimeMillis() / 1000L);
                    playerMain.effects.get(i).timeLength = time;
                    int timeRemain = (int) (System.currentTimeMillis() / 1000L - playerMain.effects.get(i).timeStart);
                    Message message = NJUtil.messageSubCommand(Cmd.ME_EDIT_EFFECT);
                    message.writeByte(playerMain.effects.get(i).template.id);
                    message.writeInt(timeRemain);
                    message.writeInt(playerMain.effects.get(i).timeLength);
                    message.writeShort(playerMain.effects.get(i).param);
                    NJUtil.sendMessage(getSession(), message);
                    return;
                }
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(33);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = time;
            playerMain.effects.add(eff);
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useFood(int templateId, int timeLength) {
        try {
            Player pUseItem = getPlayerMainControl();
            int param = 0;
            int effId = templateId;
            switch (templateId) {
                case 0:
                    param = 3;
                    break;
                case 1:
                    param = 20;
                    break;
                case 2:
                    param = 30;
                    break;
                case 3:
                    param = 40;
                    break;
                case 4:
                    param = 50;
                    break;
                case 5:
                    param = 60;
                    effId = 28;
                    break;
                case 6:
                    param = 75;
                    effId = 30;
                    break;
                case 7:
                    param = 90;
                    effId = 31;
                    break;
                case 8:
                    param = 120;
                    effId = 35;
                    break;
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(effId);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = timeLength;
            eff.param = param;
            pUseItem.addEffect(eff);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useItemUpToUp(Item item) {
        try {
            --item.quantity;
            if (item.quantity <= 0) {
                removeItem(item, 3);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void useItemUpToUp(Item item, int quantity) {
        try {
            item.quantity -= quantity;
            ghiloghack(item.getTemplateId());
            if (item.quantity <= 0) {
                removeItem(item, 3);
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public boolean useThiLuyenThiep(int time) {
        try {
            Player pUseItem = getPlayerMainControl();
            Effect ef = pUseItem.isUsingThiLuyen();
            if (ef != null && ef.timeLength + time > NJUtil.getMilisByHour(24)) {
                return false;
            }
            for (int i = 0; i < pUseItem.effects.size(); ++i) {
                if (pUseItem.effects.get(i).template.id == 34) {
                    if (pUseItem.effects.get(i).timeLength + time > NJUtil.getMilisByHour(10)) {
                        pUseItem.effects.get(i).timeLength = (int) NJUtil.getMilisByHour(10);
                    } else {
                        Effect effect = pUseItem.effects.get(i);
                        effect.timeLength += time;
                    }
                    int timeRemain = (int) (System.currentTimeMillis() / 1000L - pUseItem.effects.get(i).timeStart);
                    Message message = NJUtil.messageSubCommand(Cmd.ME_EDIT_EFFECT);
                    message.writeByte(pUseItem.effects.get(i).template.id);
                    message.writeInt(timeRemain);
                    message.writeInt(pUseItem.effects.get(i).timeLength);
                    message.writeShort(pUseItem.effects.get(i).param);
                    NJUtil.sendMessage(getSession(), message);
                    return true;
                }
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get(34);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = time;
            pUseItem.effects.add(eff);
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            NJUtil.sendMessage(getSession(), message);
        } catch (Exception e) {
        }
        return true;
    }

    public void writeParam(Message message) {
        try {
            Player playerMain = getPlayerMainControl();
            message.writeByte(playerMain.speed);
            message.writeInt(playerMain.hp_full);
            message.writeInt(playerMain.mp_full);
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public void addNewbieGift() {
        if (!reward.isNewbie && level < 20) {
            sendUpdateCoinBag(200000000);
            sendUpdateCoinLockYen(200000000);
            sendUpdateLuong(100000);
            setLevel(20);
            reward.isNewbie = true;
        }
    }

    public void checkNap() {
        if (!isLoadPlayerNap) {
            try {
                int coin_bag = 0;
                int coin_lock = 0;
                int gold = 0;
                long exp = 0L;
                Vector<BoardPlayer> boardPlayers = Database.getBoardPlayer(name);
                for (BoardPlayer board : boardPlayers) {
                    Database.clearBoardPlayer(board.id);
                    coin_bag += board.coin_bag_xu;
                    coin_lock += board.coin_lock_yen;
                    gold += board.gold;
                    exp += board.exp;
                    savezbLog("Topup by " + board.sender, board.coin_bag_xu + "@" + board.coin_lock_yen + "@" + board.exp + "@" + board.gold);
                }
                Vector<String> infoDlg = new Vector<>();
                if (coin_bag > 0) {
                    sendUpdateCoinBag(coin_bag);
                    infoDlg.add(coin_bag + " " + AlertFunction.COIN(getSession()));
                }
                if (coin_lock > 0) {
                    sendUpdateCoinLockYen(coin_lock);
                    infoDlg.add(coin_lock + " " + AlertFunction.COIN_LOCK(getSession()));
                }
                if (gold > 0) {
                    sendUpdateLuong(gold);
                    Database.updateLogNapLevel(level, gold);
                    infoDlg.add(gold + " " + AlertFunction.GOLD(getSession()));
                }
                if (exp > 0L && !isControlCharNhanBan()) {
                    sendUpdateExp(exp, true);
                    infoDlg.add(exp + " " + AlertFunction.EXP(getSession()));
                }
                Vector<Item> boardItems = Database.getBoardItem(name);
                if (boardItems.size() > 0) {
                    StringBuilder item_info = new StringBuilder();
                    for (Item board : boardItems) {
                        if (addItemToBagNoDialog(board)) {
                            infoDlg.add(board.template.getName(getSession()));
                            item_info.append(board.getTemplateId()).append("@");
                            Database.clearBoardItem(board.itemId);
                        }
                    }
                    savezbLog("Nhan item", item_info.toString());
                }
                if (infoDlg.size() > 0) {
                    NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.SYSTEM(getSession()), AlertFunction.YOU_GET(getSession()) + " " + String.join(", ", infoDlg) + ".");
                    Database.savePlayer(this, map.getTemplateId());
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            } finally {
                isLoadPlayerNap = true;
            }
        }
    }

    public static void loadInfo() {
        exps = new long[]{ 0L, 200L, 600L, 1200L, 2500L, 5000L, 9000L, 18000L, 20000L, 24000L, 36000L, 54000L, 64800L, 90720L, 127008L, 177811L, 248935L, 261382L, 339796L, 441735L, 574256L, 497688L, 597226L, 716671L, 860005L, 1032006L, 1238407L, 1486089L, 1783307L, 2139968L, 2567962L, 4622331L, 5546797L, 6656157L, 7987387L, 12779820L, 15335784L, 18402942L, 33125295L, 39750354L, 47700426L, 152641360L, 194617734L, 247279005L, 313220073L, 395646410L, 458514475L, 526703913L, 636228539L, 784494868L, 930618588L, 950533224L, 970323501L, 991448556L, 1011810700L, 1034659751L, 1058575992L, 1078672354L, 1098064208L, 1119665920L, 1452641360L, 1814617734L, 2272790205L, 2732203073L, 3256456410L, 3881514475L, 4563703913L, 5366228539L, 6144394868L, 7030612388L, 9000000000L, 11000000000L, 13000000000L, 15000000000L, 17500000000L, 20000000000L, 22500000000L, 25000000000L, 27500000000L, 30000000000L, 33000000000L, 36000000000L, 39000000000L, 42000000000L, 45500000000L, 49000000000L, 52500000000L, 56000000000L, 59500000000L, 63000000000L, 67000000000L, 71000000000L, 75000000000L, 79000000000L, 84000000000L, 89000000000L, 94000000000L, 99000000000L, 1000000000000L, 1000000000000L };
        exps1 = new long[]{ 0L, 200L, 600L, 1200L, 2500L, 5000L, 9000L, 18000L, 20000L, 24000L, 36000L, 54000L, 64800L, 90720L, 127008L, 177811L, 248935L, 261382L, 339796L, 441735L, 574256L, 497688L, 597226L, 716671L, 860005L, 1032006L, 1238407L, 1486089L, 1783307L, 2139968L, 2567962L, 4622331L, 5546797L, 6656157L, 7987387L, 12779820L, 15335784L, 18402942L, 33125295L, 39750354L, 47700426L, 152641360L, 194617734L, 247279005L, 313220073L, 395646410L, 458514475L, 526703913L, 636228539L, 784494868L, 930618588L, 950533224L, 970323501L, 991448556L, 1011810700L, 1034659751L, 1058575992L, 1078672354L, 1098064208L, 1119665920L, 1452641360L, 1814617734L, 2272790205L, 2732203073L, 3256456410L, 3881514475L, 4563703913L, 5366228539L, 6144394868L, 7030612388L, 9000000000L, 11000000000L, 13000000000L, 15000000000L, 17500000000L, 20000000000L, 22500000000L, 25000000000L, 27500000000L, 30000000000L, 33000000000L, 36000000000L, 39000000000L, 42000000000L, 45500000000L, 49000000000L, 52500000000L, 56000000000L, 59500000000L, 63000000000L, 67000000000L, 71000000000L, 75000000000L, 79000000000L, 84000000000L, 89000000000L, 94000000000L, 99000000000L, 105000000000L, 112000000000L, 120000000000L, 129000000000L, 139000000000L, 150000000000L, 160000000000L, 170000000000L, 180000000000L, 190000000000L, 200000000000L, 220000000000L, 230000000000L, 240000000000L, 250000000000L, 260000000000L, 270000000000L, 280000000000L, 290000000000L, 300000000000L, 310000000000L, 330000000000L, 350000000000L, 400000000000L, 400000000000L, 400000000000L, 400000000000L, 450000000000L, 450000000000L, 450000000000L, 450000000000L, 450000000000L, 500000000000L };
        expSkill = new long[]{ 0L, 360000000L, 540000000L, 900000000L, 1260000000L, 1800000000L, 2700000000L, 4320000000L, 38880000000L, 90720000000L, 133920000000L };
        opsTinhLuyen = new int[][]{
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 500, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2180 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 5, 1, 1, 2, 2, 2, 2, 3, 3, 4, 25 },
            { 25, 5, 7, 9, 11, 13, 15, 20, 25, 30 },
            { 5, 5, 5, 5, 5, 10, 10, 10, 15, 20 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 320, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2000 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 10, 5, 5, 5, 5, 10, 10, 10, 15, 15, 90 },
            { 10, 5, 5, 5, 5, 5, 5, 10, 10, 20, 80 },
            { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20 },
            { 100, 10, 10, 10, 20, 20, 30, 40, 50, 60, 350 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 }
        };
        opsTinhLuyen_min = new int[][]{
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 500, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2180 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 5, 1, 1, 2, 2, 2, 2, 3, 3, 4, 25 },
            { 25, 5, 7, 9, 11, 13, 15, 20, 25, 30 },
            { 5, 5, 5, 5, 5, 10, 10, 10, 15, 20 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 320, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2000 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 10, 5, 5, 5, 5, 10, 10, 10, 15, 15, 90 },
            { 10, 5, 5, 5, 5, 5, 5, 10, 10, 20, 80 },
            { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20 },
            { 100, 10, 10, 10, 20, 20, 30, 40, 50, 60, 350 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 }
        };
        opsTinhLuyen_max = new int[][]{
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 500, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2180 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 5, 1, 1, 2, 2, 2, 2, 3, 3, 4, 25 },
            { 25, 5, 7, 9, 11, 13, 15, 20, 25, 30 },
            { 5, 5, 5, 5, 5, 10, 10, 10, 15, 20 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 350, 40, 60, 80, 100, 140, 220, 300, 420, 590 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 100, 25, 30, 35, 40, 50, 60, 80, 115, 165, 700 },
            { 320, 50, 60, 70, 90, 130, 180, 250, 350, 500, 2000 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 480, 50, 70, 100, 140, 190, 250, 320, 400, 500, 2500 },
            { 10, 5, 5, 5, 5, 10, 10, 10, 15, 15, 90 },
            { 10, 5, 5, 5, 5, 5, 5, 10, 10, 20, 80 },
            { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 20 },
            { 100, 10, 10, 10, 20, 20, 30, 40, 50, 60, 350 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 },
            { 5, 5, 5, 5, 5, 5, 5, 10, 10, 15 }
        };
    }

    public static void loadMaxExp() {
        maxExp = 0L;
        for (long l : exps) {
            maxExp += l;
        }
        maxExp1 = 0L;
        for (int i = 0; i <= GameServer.maxLevel; ++i) {
            maxExp1 += exps1[i];
        }
    }

    public static void addEnemyArena(String charname, Map map) {
        Player player = Database.getPlayer(charname);
        if (player.name.equals(charname)) {
            player.login_date = System.currentTimeMillis();
            if (player.skills.size() > 0) {
                player.myskill = player.skills.get(0);
            }
            player.level = player.getLevel();
            player.resetData();
            player.timeActive = System.currentTimeMillis();
            player.mapClear();
            player.updateAll();
            player.changeNpcMe();
            player.loadThuCuoi(0);
            player.sendBian();
            if (((player.effId_food >= 0 && player.effId_food <= 4) || player.effId_food == 28 || player.effId_food == 30 || player.effId_food == 31) && player.timeEff_food > 0) {
                player.useEff(player.effId_food, player.timeEff_food);
            }
            if (player.timeEff_ThiLuyen > 0) {
                player.useThiLuyenThiep(player.timeEff_ThiLuyen);
            }
            if (player.timeKhai_Thien_Nhan_Phu > 0) {
                useThienNhanPhu(player, player.timeKhai_Thien_Nhan_Phu, (player.effId_KhaiThienNhanPhu != 40) ? 1 : 0);
            }
            if (player.effId_exp_bonus == 22 && player.timeEff_exp_bonus > 0) {
                player.useExpx2(player.timeEff_exp_bonus);
            }
            if (player.effId_exp_bonus == 32 && player.timeEff_exp_bonus > 0) {
                player.useExpx3(player.timeEff_exp_bonus);
            }
            if (player.effId_exp_bonus == 33 && player.timeEff_exp_bonus > 0) {
                player.useExpx4(player.timeEff_exp_bonus);
            }
            Item item = player.findItemBag(572);
            player.xStartAuto = player.x;
            player.yStartAuto = player.y;
            if (item != null) {
                player.sendTeleport(-1, player.x, player.y, false, false, "doselect");
            } else {
                player.sendTeleport(-2, player.x, player.y, false, false, "doselect");
            }
            player.doAddGiftNewDay();
        }
        Effect eff = new Effect();
        eff.template = ServerController.effTemplates.get(14);
        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
        eff.timeLength = 10000;
        player.addEffect(eff);
        player.typePk = PK_DOSAT;
        player.x = 661;
        player.y = 264;
        map.waitGoInMap(player);
        map.addPlayerPhe2(player);
    }

    public static boolean canFocusReturnMap(int mapTemplateId) {
        return NJUtil.inArrayInt(new int[]{ 1, 10, 17, 22, 27, 32, 38, 43, 48, 72 }, mapTemplateId);
    }

    public static synchronized void changeTopRuong(Player player, int ruong) {
        if (ServerController.topHangdong.size() < 10 || ServerController.topHangdong.lastElement().ruong < ruong) {
            for (int i = 0; i < ServerController.topHangdong.size(); ++i) {
                if (ServerController.topHangdong.get(i).name.equals(player.name)) {
                    ServerController.topHangdong.remove(i);
                    break;
                }
            }
            ServerController.topHangdong.add(new TopHangDong(player.name, ruong));
            ServerController.topHangdong.sort(TopHangDong.sort);
            for (int i = 10; i < ServerController.topHangdong.size(); ++i) {
                //noinspection SuspiciousListRemoveInLoop
                ServerController.topHangdong.remove(i);
            }
        }
    }

    public static Message createMSgCharServer(String name, String info) {
        Message message = new Message(Cmd.CHAT_SERVER);
        try {
            message.writeUTF(name);
            message.writeUTF(info);
        } catch (Exception e) {
        }
        return message;
    }

    public static void doConfirmDyNamicMenu(Player player, Message msg) {
        try {
            if (player.idTrade > -1) {
                player.doCancelTrade();
                return;
            }
            msg.readByte(); // id npc
            int menuId = msg.readByte();
            msg.readByte(); // optionId
            if (player.idActionNewMenu == -1) {
                return;
            }
            if (GameServer.isServerLocal()) {
                LOGGER.info("Confirm dynamic menu: {}/{}", player.idActionNewMenu, menuId);
            }
            switch (player.idActionNewMenu) {
                case 0: {
                    if (!GameServer.openThienBienLenh) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    player.idActionNewMenu = -1;
                    switch (menuId) {
                        case 0:
                        case 1:
                        case 2:
                            player.rangeTrain = Player.RANGE_THIEN_BIEN_LENH[menuId];
                            msg = new Message(Cmd.TELEPORT_TRAIN);
                            msg.writeByte(-1);
                            msg.writeInt(player.rangeTrain);
                            player.getSession().sendMessage(msg);
                            player.xStartAuto = player.x;
                            player.yStartAuto = player.y;
                            NJUtil.sendServer(player.getSession(), "Phạm vi thay đổi thành " + player.rangeTrain);
                            break;
                        case 3:
                            player.typeGetItemAuto = 0;
                            NJUtil.sendServer(player.getSession(), "Nhặt tất cả vật phẩm");
                            break;
                        case 4:
                            player.typeGetItemAuto = 1;
                            NJUtil.sendServer(player.getSession(), "Chỉ nhặt vật phẩm hữu dụng");
                            break;
                    }
                    return;
                }
                case 2: {
                    if (!GameServer.openVongXoay) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    player.idActionNewMenu = -1;
                    if (menuId == 0) {
                        player.infoXoso();
                    } else if (menuId == 1) {
                        String luat = "- Giá trị nhập xu thấp nhất của mỗi người là 1.000.000\n- Giá trị nhập xu cao nhất của mỗi người là 50.000.000\n- Giá trị xu còn lại sau mỗi lần đặt phải còn ít nhất 10.000.000\n- Mỗi 2 phút là bắt đầu vòng quay một lần.\n- Khi có người bắt đầu nhập xu thì mới bắt đầu đếm ngược thời gian.\n- Còn 10 giây cuối sẽ bắt đầu khóa không cho gửi xu.\n- Người chiến thắng sẽ nhận hết tổng số tiền tất cả người chơi khác đặt cược sau khi đã trừ thuế.\n- Khi người chơi ít hơn 10, thuế sẽ bằng số người chơi -1.\n- Người chơi nhiều hơn 10 người thuế sẽ là 10%.";
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.LUCKY_DRAW(player.getSession()), luat);
                    } else if (menuId == 2) {
                        player.doSendTextBoxId(AlertFunction.LUCKY_DRAW(player.getSession()), 100);
                    }
                    return;
                }
                case 10: {
                    player.idActionNewMenu = -1;
                    if (player.memName.isEmpty() || player.indexUITT == -1) {
                        return;
                    }
                    Member mem = player.clan.getMember(player.memName);
                    if (mem == null) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.CLAN_1(player.getSession()));
                        return;
                    }
                    Player p = ServerController.hnPlayers.get(player.memName);
                    if (p == null) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.CLAN_2(player.getSession()));
                        return;
                    }
                    Item item = player.clan.items.get(player.indexUITT).cloneItem();
                    if (item == null) {
                        return;
                    }
                    Item it = player.clan.allThanThu.get(menuId).getItem();
                    if (it == null) {
                        return;
                    }
                    if (!p.addItemToBagNoDialog(it)) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.CLAN_3(player.getSession()));
                        return;
                    }
                    player.clan.removeItem(player.indexUITT);
                    player.doRequestClanItem();
                    player.memName = "";
                    player.indexUITT = -1;
                    return;
                }
                case 11: {
                    player.idActionNewMenu = -1;
                    if (player.clan == null || player.itFood == null) {
                        return;
                    }
                    if (player.itFood.template.itemTemplateId == 605 && !player.clan.isMain(player.name)) {
                        return;
                    }
                    int a = player.clan.doEat(player.itFood, menuId, player.getSession());
                    if (a == 1 || a == 2) {
                        player.useItemUpToUp(player.itFood);
                        player.sendUseItemUpToUp(player.itFood.indexUI, 1);
                        player.doRequestClanItem();
                        Database.saveClan(player.clan);
                        Database.saveLogAll(player.name, player.name + " cho " + player.clan.allThanThu.get(menuId).getName(player.getSession()) + " ăn " + player.itFood.template.getName(player.getSession()), "choan");
                    }
                    player.itFood = null;
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.error(player.getStringBaseInfo(), e);
        }
        player.idActionNewMenu = -1;
    }

    public static synchronized void doStartArena(String name, Player player) {
        PlayerArenaInfo playerArenaEnemy = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(name);
        PlayerArenaInfo playerArenaMe = PlayerArenaInfo.ALL_PLAYER_TOP_THACH_DAU.get(player.name);
        if (playerArenaEnemy == null || playerArenaMe == null) {
            return;
        }
        if (playerArenaEnemy.isPlaying || playerArenaMe.isPlaying) {
            NJUtil.sendServer(player.getSession(), playerArenaEnemy.isPlaying ? playerArenaEnemy.nameThachdau : playerArenaMe.nameThachdau);
            return;
        }
        if (playerArenaMe.timesPlay <= 0) {
            NJUtil.sendServer(player.getSession(), "Bạn đã hết lượt thi đấu. Xin tiếp tục vào ngày hôm sau.");
            return;
        }
        player.countHpUseArena = 0;
        player.doOutParty();
        if (playerArenaMe.timeWin == -1L) {
            playerArenaMe.timeWin = System.currentTimeMillis();
        }
        DunArena dun = DunArena.createDunArena();
        dun.nameShadow = name;
        player.mapClear();
        player.map.goOutMap(player);
        dun.addPlayerPhe1(player);
        player.x = 107;
        player.y = 264;
        player.lastMoveX = player.x;
        player.lastMoveY = player.y;
        Effect eff = new Effect();
        eff.template = ServerController.effTemplates.get(14);
        eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
        eff.timeLength = 10000;
        player.addEffect(eff);
        dun.waitGoInMap(player);
        playerArenaEnemy.isPlaying = true;
        playerArenaEnemy.timeFight = System.currentTimeMillis() + 300000L;
        playerArenaEnemy.nameThachdau = playerArenaEnemy.name + " vs " + playerArenaMe.name;
        playerArenaMe.isPlaying = true;
        playerArenaMe.timeFight = System.currentTimeMillis() + 300000L;
        playerArenaMe.nameThachdau = playerArenaMe.name + " vs " + playerArenaEnemy.name;
        addEnemyArena(name, dun);
        ++countTotalMatch;
    }

    public static synchronized int getIdTrade() {
        return ID_TRADE++ % 20000000;
    }

    public Item getItemShop(Vector<Item> its, int itemTemplateId) {
        for (Item it : its) {
            if (it.template.itemTemplateId == itemTemplateId && it.sys == getSys()) {
                return it.cloneItem();
            }
        }
        return null;
    }

    public Item thuongMaxOp() {
        int[][] equiptIds;
        if (level < 50) {
            if (gender == 1) {
                equiptIds = new int[][]{ id4xvk, id4xts, id4xnam };
            } else {
                equiptIds = new int[][]{ id4xvk, id4xts, id4xnu };
            }
        } else if (level < 60) {
            if (gender == 1) {
                equiptIds = new int[][]{ id5xvk, id5xts, id5xnam };
            } else {
                equiptIds = new int[][]{ id5xvk, id5xts, id5xnu };
            }
        } else if (level < 70) {
            if (gender == 1) {
                equiptIds = new int[][]{ id6xvk, id6xts, id6xnam };
            } else {
                equiptIds = new int[][]{ id6xvk, id6xts, id6xnu };
            }
        } else if (level < 90) {
            if (gender == 1) {
                equiptIds = new int[][]{ id7xvk, id7xts, id7xnam };
            } else {
                equiptIds = new int[][]{ id7xvk, id7xts, id7xnu };
            }
        } else {
            if (gender == 1) {
                equiptIds = new int[][]{ id9xvk, id9xts, id9xnam };
            } else {
                equiptIds = new int[][]{ id9xvk, id9xts, id9xnu };
            }
        }
        Item it;
        int indexGroup = NJUtil.randomNumber(equiptIds.length);
        int[] itemGroup = equiptIds[indexGroup];
        int itemId = itemGroup[NJUtil.randomNumber(itemGroup.length)];
        if (level < 90) {
            if (indexGroup == 0) {
                it = getItemShop(ServerController.shopVuKhis, itemId);
            } else if (indexGroup == 1) {
                it = getItemShop(ServerController.shopTrangsucs, itemId);
            } else {
                it = getItemShop(ServerController.shopPhongcus, itemId);
            }
        } else {
            if (indexGroup == 0) {
                it = getItemShop(ServerController.shopVuKhis_not_sell, itemId);
            } else if (indexGroup == 1) {
                it = getItemShop(ServerController.shopTrangsucs_not_sell, itemId);
            } else {
                it = getItemShop(ServerController.shopPhongcus_not_sell, itemId);
            }
        }
        if (it != null) {
            it.upgrade = 0;
            it.isLock = false;
        }
        return it;
    }

    public Item getItem4xRandom(int upgrade, int[] types, boolean isLock) {
        Item item = getItemRandom(4, types[NJUtil.randomNumber(types.length)]);
        if (item != null) {
            item.upgrade = upgrade;
            item.expires = -1L;
            item.isLock = isLock;
            changeItemOption(item, upgrade);
            return item;
        }
        return null;
    }

    public Item getItem5xRandom(int upgrade, int[] types, boolean isLock) {
        Item item = getItemRandom(5, types[NJUtil.randomNumber(types.length)]);
        if (item != null) {
            item.upgrade = upgrade;
            item.expires = -1L;
            item.isLock = isLock;
            changeItemOption(item, upgrade);
            return item;
        }
        return null;
    }

    public Item getItem7xRandom(int upgrade, int[] types, boolean isLock) {
        Item item = getItemRandom(7, types[NJUtil.randomNumber(types.length)]);
        if (item != null) {
            item.upgrade = upgrade;
            item.expires = -1L;
            item.isLock = isLock;
            changeItemOption(item, upgrade);
            return item;
        }
        return null;
    }

    public Item getItemRandom(int typeLevel, int type) {
        int classId = this.classId;
        int gender = this.gender;
        if (isControlCharNhanBan()) {
            classId = playercopy.classId;
            gender = playercopy.gender;
        }
        int[] weaponIds;
        int[] trangsucIds;
        int[] phongcuIds;
        Item it;
        if (typeLevel < 5) {
            weaponIds = Player.id4xvk;
            trangsucIds = id4xts;
            if (gender == 1) {
                phongcuIds = id4xnam;
            } else {
                phongcuIds = id4xnu;
            }
        } else if (typeLevel < 7) {
            weaponIds = Player.id5xvk;
            trangsucIds = id5xts;
            if (gender == 1) {
                phongcuIds = id5xnam;
            } else {
                phongcuIds = id5xnu;
            }
        } else {
            weaponIds = Player.id7xvk;
            trangsucIds = id7xts;
            if (gender == 1) {
                phongcuIds = id7xnam;
            } else {
                phongcuIds = id7xnu;
            }
        }
        if (type == 0) {
            it = getItemShop(ServerController.shopVuKhis, weaponIds[classId - 1]);
        } else if (type == 1) {
            it = getItemShop(ServerController.shopTrangsucs, trangsucIds[NJUtil.randomNumber(trangsucIds.length)]);
        } else {
            it = getItemShop(ServerController.shopPhongcus, phongcuIds[NJUtil.randomNumber(phongcuIds.length)]);
        }
        return it;
    }

    public void infoXoso() {
        try {
            if (ALL_PLAYER_XOSO.size() < 2) {
                timeXoso = System.currentTimeMillis();
            }
            PlayerXoso pxs = ALL_PLAYER_XOSO.get(name.toLowerCase());
            long moneytest = 0L;
            if (pxs != null) {
                moneytest = pxs.money;
            }
            long moneyMax = MONEY_XOSO;
            StringBuilder tl = new StringBuilder();
            StringBuilder tl2 = new StringBuilder();
            long pc = 0L;
            if (moneyMax > 0L) {
                tl.append(moneytest * 100L / moneyMax);
                pc = moneytest * 100L / moneyMax;
                long mod = moneytest * 100L % moneyMax;
                if (mod != 0L) {
                    tl.append(".");
                    int i = 0;
                    while (i < 4) {
                        ++i;
                        tl.append(mod * 10L / moneyMax);
                        tl2.append(mod * 10L / moneyMax);
                        if (mod * 10L % moneyMax == 0L) {
                            break;
                        }
                        mod = mod * 10L % moneyMax;
                    }
                } else {
                    tl.append(".0");
                    tl2 = new StringBuilder("0");
                }
            } else {
                tl = new StringBuilder("0");
                tl2 = new StringBuilder("0");
            }
            long timexs = timeXoso;
            long time = 120L - (System.currentTimeMillis() - timexs) / 1000L;
            if (time < 0L) {
                time = 0L;
            }
            String hh = "0" + time / 60L + ":";
            String sc;
            if (time / 60L == 0L) {
                sc = String.valueOf(time);
            } else {
                sc = String.valueOf(time % 60L);
            }
            if (sc.length() == 1) {
                sc = "0" + sc;
            }
            hh = hh + sc;
            if (!getSession().isVersion125()) {
                String str;
                if (getSession().typeLanguage == GameServer.LANG_VI) {
                    str = "Thời gian\n" + hh + "\n\n" + NJUtil.getDotNumber(MONEY_XOSO) + " xu" + "\n" + "Tỷ lệ thắng: " + tl + "%\n" + "Xu tham gia: " + NJUtil.getDotNumber(moneytest) + " xu" + "\n\n" + "Người vừa chiến thắng: " + charNameXosoWin + "\n" + "Số xu thắng: " + NJUtil.getDotNumber(moneyCharXosowin) + " xu\n" + "Số xu tham gia: " + NJUtil.getDotNumber(moneyCharXosoBid) + " xu\n\n" + "Giá tri tham gia: 1m-50m xu";
                } else {
                    str = "Time\n" + hh + "\n\n" + NJUtil.getDotNumber(MONEY_XOSO) + " coins" + "\n" + "Rate: " + tl + "%\n" + "Your coins: " + NJUtil.getDotNumber(moneytest) + " coins" + "\n\n" + "Last winner: " + charNameXosoWin + "\n" + "Coins won: " + NJUtil.getDotNumber(moneyCharXosowin) + " coins\n" + "Coins played: " + NJUtil.getDotNumber(moneyCharXosoBid) + " coins\n\n" + "Must play between: 1m-50m coins";
                }
                NJUtil.sendAlertDialogInfo(getSession(), AlertFunction.LUCKY_DRAW(getSession()), str);
            } else if (getSession().typeLanguage == GameServer.LANG_VI) {
                String str = "Người vừa chiến thắng: " + charNameXosoWin + "\n" + "Số xu thắng: " + NJUtil.getDotNumber(moneyCharXosowin) + " xu\n" + "Số xu tham gia: " + NJUtil.getDotNumber(moneyCharXosoBid) + " xu";
                NJUtil.sendAlertXoso(getSession(), AlertFunction.LUCKY_DRAW(getSession()), (int) time, NJUtil.getDotNumber(MONEY_XOSO) + " xu", (int) pc, tl2.toString(), ALL_PLAYER_XOSO.size(), str, 0, String.valueOf(moneytest));
            } else {
                String str = "Last winner: " + charNameXosoWin + "\n" + "Coins won: " + NJUtil.getDotNumber(moneyCharXosowin) + " coins\n" + "Coins played: " + NJUtil.getDotNumber(moneyCharXosoBid) + " coins";
                NJUtil.sendAlertXoso(getSession(), AlertFunction.LUCKY_DRAW(getSession()), (int) time, NJUtil.getDotNumber(MONEY_XOSO) + " coins", (int) pc, tl2.toString(), ALL_PLAYER_XOSO.size(), str, 0, String.valueOf(moneytest));
            }
        } catch (Exception e) {
            LOGGER.error(getStringBaseInfo(), e);
        }
    }

    public static void startThreadXoso() {
        new Thread(() -> {
            while (!ServerController.isExit && !ServerController.turnOnExit) {
                try {
                    if ((System.currentTimeMillis() - timeXoso) / 1000L >= 120L) {
                        if (ALL_PLAYER_XOSO.size() >= 2) {
                            int[] coinXs = new int[ALL_PLAYER_XOSO.size()];
                            String[] nameJoin = new String[ALL_PLAYER_XOSO.size()];
                            Vector<PlayerXoso> playerJoin = new Vector<>();
                            int i = 0;
                            for (PlayerXoso pxs : ALL_PLAYER_XOSO.values()) {
                                coinXs[i] = pxs.money;
                                nameJoin[i] = pxs.name;
                                pxs.index = i;
                                playerJoin.add(pxs);
                                i++;
                            }
                            int tempIndex = getRandomXoSo(coinXs);
                            int index = tempIndex;
                            Player playerWin = ServerController.hnPlayers.get(nameJoin[index]);
                            while (playerWin == null && !playerJoin.isEmpty()) {
                                playerJoin.remove(tempIndex);
                                if (playerJoin.isEmpty()) {
                                    break;
                                }
                                int[] coinXs2 = new int[playerJoin.size()];
                                for (int j = 0; j < playerJoin.size(); ++j) {
                                    coinXs2[j] = playerJoin.get(j).money;
                                }
                                tempIndex = getRandomXoSo(coinXs2);
                                if (tempIndex == -1) {
                                    break;
                                }
                                index = playerJoin.get(tempIndex).index;
                                playerWin = ServerController.hnPlayers.get(nameJoin[index]);
                            }
                            int tax = 10;
                            int moneyWin = (int) (MONEY_XOSO - MONEY_XOSO * tax / 100L);
                            String info = "Chúc mừng " + nameJoin[index] + " đã chiến thắng " + NJUtil.getDotNumber(moneyWin) + " xu trong trò chơi Vòng xoay may mắn";
                            if (GameServer.isSvEnglish()) {
                                info = "Congratulation " + nameJoin[index] + " won " + NJUtil.getDotNumber(moneyWin) + " coins in Lucky Draw";
                            }
                            NJUtil.sendServerAlert(info);
                            Database.resetXoso(nameJoin[index], coinXs[index], moneyWin, (int) (MONEY_XOSO * tax / 100L));
                            charNameXosoWin = nameJoin[index];
                            moneyCharXosowin = moneyWin;
                            moneyCharXosoBid = coinXs[index];
                            try {
                                savezaLog(nameJoin[index], "win vip " + moneyWin, nameJoin[index]);
                            } catch (Exception e) {
                            }
                            if (playerWin != null) {
                                playerWin.addXu(moneyWin);
                                playerWin.updateInfo();
                            } else {
                                Database.insertBoardPlayer(nameJoin[index], moneyWin, "vxmm");
                            }
                            MONEY_XOSO = 0L;
                            timeXoso = System.currentTimeMillis();
                            ALL_PLAYER_XOSO.clear();
                        } else {
                            timeXoso = System.currentTimeMillis();
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("", e);
                }
                NJUtil.sleep(100L);
            }
        }).start();
    }

    public static int getLevel(long exp) {
        long expRemain = exp;
        int i = 0;
        while (i < exps.length && expRemain >= exps[i]) {
            expRemain -= exps[i];
            ++i;
        }
        return i;
    }

    public static int getLevel1(long exp) {
        long expRemain = exp;
        int i = 0;
        while (i < exps1.length && expRemain >= exps1[i]) {
            expRemain -= exps1[i];
            ++i;
        }
        return i;
    }

    public static int getLevelSkill(long exp) {
        long expRemain = exp;
        int i = 0;
        while (i < expSkill.length && expRemain >= expSkill[i]) {
            expRemain -= expSkill[i];
            ++i;
        }
        return i;
    }

    public static long getMaxExp(int level) {
        long totalExp = 0L;
        for (int i = 0; i <= level; ++i) {
            totalExp += exps[i];
        }
        return totalExp;
    }

    public static long getMaxExp1(int level) {
        long totalExp = 0L;
        for (int i = 0; i <= level; ++i) {
            totalExp += exps1[i];
        }
        return totalExp;
    }

    public static int countCrystal(Vector<Item> items) {
        int count = 0;
        for (Item item : items) {
            count += Player.crystals[item.template.itemTemplateId];
        }
        return count;
    }

    public static String getInfoFromVectorString(Vector<String> v) {
        StringBuilder info = new StringBuilder();
        for (String s : v) {
            info.append(s).append("|");
        }
        return info.toString();
    }

    public static int getRandomXoSo(int[] num) {
        long total = 0L;
        StringBuilder value = new StringBuilder();
        for (int i : num) {
            total += i;
            value.append(i).append(",");
        }
        long random = (long) (NJUtil.random.nextDouble() * total);
        LOGGER.info(value + " TONG SO getRandomSoxo player : " + total + " > " + random);
        total = 0L;
        for (int i = 0; i < num.length; ++i) {
            total += num[i];
            if (random <= total) {
                LOGGER.info("Index value: {}", i);
                return i;
            }
        }
        return 0;
    }

    public static void goLoiDai() {
        Vector<Player> players = new Vector<>();
        for (int i = 0; i < ServerController.loidaiduns.size(); ++i) {
            players.addAll(ServerController.loidaiduns.get(i).players);
        }
        int size = players.size();
        String name1 = null;
        String name2;
        Player player;
        for (int i = 0; i < size; ++i, ++i) {
            for (int k = 0; k < 2; ++k) {
                int index = NJUtil.randomNumber(players.size());
                player = players.remove(index);
                if (player.countLoiDai >= 50) {
                    player.doChangeMap(NJUtil.randomMapBack(), false, "goloidai");
                } else {
                    if (players.size() == 0 && name1 == null) {
                        ++player.countLoiDai;
                        ServerController.addPointLoiDai(player, 3);
                        break;
                    }
                    if (name1 == null) {
                        name1 = player.name;
                    } else {
                        name2 = player.name;
                        player.doLoiDai(name1, name2, true);
                        name1 = null;
                    }
                }
            }
        }
    }

    public static void goLoiDaiClass() {
        Vector<Vector<Player>> players_class = new Vector<>();
        players_class.add(new Vector<>());
        players_class.add(new Vector<>());
        players_class.add(new Vector<>());
        players_class.add(new Vector<>());
        players_class.add(new Vector<>());
        players_class.add(new Vector<>());
        for (int i = 0; i < ServerController.loidaidunsClass.size(); ++i) {
            for (int j = 0; j < ServerController.loidaidunsClass.get(i).players.size(); ++j) {
                Player p = ServerController.loidaidunsClass.get(i).players.get(j);
                if (p != null && p.classId > 0 && !p.isNhanban()) {
                    players_class.get(p.classId - 1).add(p);
                }
            }
        }
        for (Vector<Player> players : players_class) {
            try {
                players.sort(new NJUtil.sortDataTop(0));
                int size = players.size();
                String name1 = null;
                String name2;
                Player player;
                int oldIndex = 0;
                for (int l = 0; l < size; ++l, ++l) {
                    for (int m = 0; m < 2; ++m) {
                        int index = NJUtil.randomNumber(players.size());
                        if (name1 != null) {
                            int start = oldIndex - 5;
                            if (start < 0) {
                                start = 0;
                            }
                            int end = oldIndex + 5;
                            if (end > players.size()) {
                                end = players.size();
                            }
                            index = NJUtil.randomBetweenMinMax(start, end);
                        } else {
                            oldIndex = index;
                        }
                        player = players.remove(index);
                        if (player.countLoiDaiClass >= 50) {
                            player.doChangeMap(NJUtil.randomMapBack(), false, "goloidaiclass");
                            oldIndex = index;
                        } else {
                            if (players.size() == 0 && name1 == null) {
                                ++player.countLoiDaiClass;
                                ServerController.addPointLoiDaiClass(player, 3);
                                break;
                            }
                            if (name1 == null) {
                                name1 = player.name;
                            } else {
                                name2 = player.name;
                                player.doLoiDaiClass(name1, name2, true);
                                name1 = null;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    public static String getInfoX2Exp() {
        try {
            if (NJUtil.inBetweenDate(LocalDate.parse(timeX2Exp[0]), LocalDate.parse(timeX2Exp[1]))) {
                return "X2 kinh nghiệm toàn máy chủ từ " + timeX2Exp[0] + " đến hết " + timeX2Exp[1] + ".";
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static String getInfoClassX2Exp() {
        try {
            if (timeClassX2Exp.length >= 2 && classX2Exp.length >= 2) {
                String[] className = new String[classX2Exp.length];
                for (int i = 0; i < classX2Exp.length; i++) {
                    int idClass = classX2Exp[i];
                    if (idClass == -1) {
                        return null;
                    }
                    if (idClass > 0 && idClass < ServerController.nClasss.size()) {
                        NClass nClass = ServerController.nClasss.get(idClass);
                        if (nClass != null) {
                            className[i] = GameServer.isSvEnglish() ? nClass.name_en : nClass.name;
                        }
                    }
                }
                if (NJUtil.inBetweenDate(LocalDate.parse(timeClassX2Exp[0]), LocalDate.parse(timeClassX2Exp[1]))) {
                    return "X2 kinh nghiệm phái " + String.join(", ", className) + " đến hết " + timeClassX2Exp[1] + ".";
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean isAcountTest(Player p) {
        try {
            Vector<String> names = new Vector<>();
            names.add("aaa");
            return names.contains(p.getSession().username);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSkillAttackNoneDisplay(int id) {
        for (int i = 0; i < ID_SKILL_35_40_15_60[0].length; ++i) {
            if (id == ID_SKILL_35_40_15_60[0][i] || id == ID_SKILL_35_40_15_60[1][i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSkillPhanThan(Skill sk) {
        short[] idSkill = { 67, 68, 69, 70, 71, 72 };
        for (short value : idSkill) {
            if (sk.template.skillTemplateId == value) {
                return true;
            }
        }
        return false;
    }

    public static void openGiaiDau() {
        ServerController.loidaiduns.clear();
        for (int i = 0; i < 100; ++i) {
            Dun dun = Dun.createDun(129);
            int time = 4200;
            if (dun != null) {
                dun.timeEnd = System.currentTimeMillis() + time * 1000;
                ServerController.loidaiduns.add(dun);
            }
        }
    }

    public static void openGiaiDauClass() {
        ServerController.loidaidunsClass.clear();
        for (int i = 0; i < 100; ++i) {
            Dun dun = Dun.createDun(129);
            int time = 4200;
            if (dun != null) {
                dun.timeEnd = System.currentTimeMillis() + time * 1000;
                ServerController.loidaidunsClass.add(dun);
            }
        }
    }

    public static int random25Percent(int value) {
        return NJUtil.random.nextInt() % (value >> 2);
    }

    public static void sendAllCharServer(Message message) {
        try {
            Hashtable<Integer, Player> huPlayers = new Hashtable<>(ServerController.huPlayers);
            for (int userId : huPlayers.keySet()) {
                try {
                    Player p = ServerController.huPlayers.get(userId);
                    if (p == null || p.getSession() == null) {
                        continue;
                    }
                    p.getSession().sendMessage(message);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
    }

    public static void sendUpdateClan(Player playerMap, int typeClan) {
        try {
            Message message = NJUtil.messageSubCommand(Cmd.CLAN_ACCEPT_INVITE);
            message.writeInt(playerMap.playerId);
            message.writeUTF(playerMap.clan.name);
            message.writeByte(typeClan);
            playerMap.sendToPlayer(message, true);
        } catch (Exception e) {
        }
    }

    public static synchronized void setTimeCake() {
        int[] tick = { 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 4, 3, 3, 3, 4, 4, 4, 3, 3, 4 };
        timeDropItem = System.currentTimeMillis() + tick[NJUtil.random.nextInt(tick.length)] * 60000;
    }

    public static boolean useThienNhanPhu(Player player, int time, int type) {
        try {
            Player playerMain = player.getPlayerMainControl();
            Effect ef = playerMain.isUsingKhaiThienNhanPhu();
            if (ef != null) {
                return false;
            }
            Effect eff = new Effect();
            eff.template = ServerController.effTemplates.get((type == 0) ? 40 : 41);
            eff.timeStart = (int) (System.currentTimeMillis() / 1000L);
            eff.timeLength = time;
            playerMain.effects.add(eff);
            int timeRemain = (int) (System.currentTimeMillis() / 1000L - eff.timeStart);
            Message message = NJUtil.messageSubCommand(Cmd.ME_ADD_EFFECT);
            message.writeByte(eff.template.id);
            message.writeInt(timeRemain);
            message.writeInt(eff.timeLength);
            message.writeShort(eff.param);
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
        }
        return true;
    }
}