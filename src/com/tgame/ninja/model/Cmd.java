package com.tgame.ninja.model;

public class Cmd {

    public static final byte LOGIN = -127;

    public static final byte LOGOUT = -127;

    public static final byte REGISTER = -126;

    public static final byte CLIENT_INFO = -125;

    public static final byte SEND_SMS = -124;

    public static final byte REGISTER_IMEI = -123;

    public static final byte FORGET_PASS = -122;

    public static final byte FORGET_PASS_IMEI = -121;

    public static final byte LOGIN0 = 0;

    public static final byte REGISTER0 = 1;

    public static final byte CLEAR_RMS = 2;

    public static final byte SELECT_PLAYER = -126;

    public static final byte CREATE_PLAYER = -125;

    public static final byte DELETE_PLAYER = -124;

    public static final byte UPDATE_VERSION = -123;

    public static final byte UPDATE_DATA = -122;

    public static final byte UPDATE_MAP = -121;

    public static final byte UPDATE_SKILL = -120;

    public static final byte UPDATE_ITEM = -119;

    public static final byte UPDATE_VERSION_OK = -118;

    public static final byte UPDATE_PK = -117;

    public static final byte UPDATE_OUT_CLAN = -116;

    public static final byte REQUEST_ICON = -115;

    public static final byte REQUEST_CLAN_LOG = -114;

    public static final byte REQUEST_CLAN_INFO = -113;

    public static final byte REQUEST_CLAN_MEMBER = -112;

    public static final byte REQUEST_CLAN_ITEM = -111;

    public static final byte REQUEST_SKILL = -110;

    public static final byte REQUEST_MAPTEMPLATE = -109;

    public static final byte REQUEST_NPCTEMPLATE = -108;

    public static final byte REQUEST_NPCPLAYER = -107;

    public static final byte ME_LOAD_ACTIVE = -106;

    public static final byte ME_ACTIVE = -105;

    public static final byte ME_UPDATE_ACTIVE = -104;

    public static final byte ME_OPEN_LOCK = -103;

    public static final byte ME_CLEAR_LOCK = -102;

    public static final byte CLIENT_OK = -101;

    public static final byte CLIENT_OK_INMAP = -100;

    public static final byte INPUT_CARD = -99;

    public static final byte CLEAR_TASK = -98;

    public static final byte CHANGE_NAME = -97;

    public static final byte CREATE_CLAN = -96;

    public static final byte CLAN_CHANGE_ALERT = -95;

    public static final byte CLAN_CHANGE_TYPE = -94;

    public static final byte CLAN_MOVEOUT_MEM = -93;

    public static final byte CLAN_OUT = -92;

    public static final byte CLAN_UP_LEVEL = -91;

    public static final byte INPUT_COIN_CLAN = -90;

    public static final byte OUTPUT_COIN_CLAN = -89;

    public static final byte CONVERT_UPGRADE = -88;

    public static final byte INVITE_CLANDUN = -87;

    public static final byte NOT_USEACC = -86;

    public static final byte ITEM_SPLIT = -85;

    public static final byte POINT_PB = -84;

    public static final byte REVIEW_PB = -83;

    public static final byte REWARD_PB = -82;

    public static final byte CHIENTRUONG_INFO = -81;

    public static final byte REVIEW_CT = -80;

    public static final byte REWARD_CT = -79;

    public static final byte CHAT_ADMIN = -78;

    public static final byte CHANGE_BG_ID = -77;

    public static final byte CHECK_KEY1 = -76;

    public static final byte CHECK_KEY2 = -75;

    public static final byte CHECK_KEY3 = -74;

    public static final byte CHECK_KEY4 = -73;

    public static final byte LAT_HINH = -72;

    public static final byte CLEAR_TRANSACTION_ID = -71;

    public static final byte MOI_GTC = -70;

    public static final byte MOI_TATCA_GTC = -69;

    public static final byte GO_GTCHIEN = -68;

    public static final byte OAN_HON = -67;

    public static final byte OAN_HON1 = -66;

    public static final byte NAP_NOKIA = -65;

    public static final byte GET_PASS2 = -64;

    public static final byte NAP_GOOGLE = -63;

    public static final byte OPEN_CLAN_ITEM = -62;

    public static final byte CLAN_SEND_ITEM = -61;

    public static final byte CLAN_USE_ITEM = -60;

    public static final byte CLEAR_PRODUCT_ID = -50;

    public static final byte ME_LOAD_ALL = -127;

    public static final byte ME_LOAD_CLASS = -126;

    public static final byte ME_LOAD_SKILL = -125;

    public static final byte ME_LOAD_LEVEL = -124;

    public static final byte ME_LOAD_INFO = -123;

    public static final byte ME_LOAD_HP = -122;

    public static final byte ME_LOAD_MP = -121;

    public static final byte PLAYER_LOAD_ALL = -120;

    public static final byte PLAYER_LOAD_INFO = -119;

    public static final byte PLAYER_LOAD_LEVEL = Byte.MIN_VALUE;

    public static final byte PLAYER_LOAD_VUKHI = -117;

    public static final byte PLAYER_LOAD_AO = -116;

    public static final byte PLAYER_LOAD_QUAN = -113;

    public static final byte PLAYER_LOAD_BODY = -112;

    public static final byte PLAYER_LOAD_HP = -111;

    public static final byte PLAYER_LOAD_LIVE = -110;

    public static final byte POTENTIAL_UP = -109;

    public static final byte SKILL_UP = -108;

    public static final byte BAG_SORT = -107;

    public static final byte BOX_SORT = -106;

    public static final byte BOX_COIN_IN = -105;

    public static final byte BOX_COIN_OUT = -104;

    public static final byte REQUEST_ITEM = -103;

    public static final byte USE_BOOK_SKILL = -102;

    public static final byte ME_ADD_EFFECT = -101;

    public static final byte ME_EDIT_EFFECT = -100;

    public static final byte ME_REMOVE_EFFECT = -99;

    public static final byte PLAYER_ADD_EFFECT = -98;

    public static final byte PLAYER_EDIT_EFFECT = -97;

    public static final byte PLAYER_REMOVE_EFFECT = -96;

    public static final byte MAP_TIME = -95;

    public static final byte NPC_PLAYER_UPDATE = -94;

    public static final byte CHANGE_TYPE_PK = -93;

    public static final byte UPDATE_TYPE_PK = -92;

    public static final byte UPDATE_BAG_COUNT = -91;

    public static final byte TASK_FOLLOW_FAIL = -90;

    public static final byte END_WAIT = -89;

    public static final byte CREATE_PARTY = -88;

    public static final byte CHANGE_TEAMLEADER = -87;

    public static final byte MOVE_MEMBER = -86;

    public static final byte REQUEST_FRIEND = -85;

    public static final byte REQUEST_ENEMIES = -84;

    public static final byte FRIEND_REMOVE = -83;

    public static final byte ENEMIES_REMOVE = -82;

    public static final byte ME_UPDATE_PK = -81;

    public static final byte ITEM_BODY_CLEAR = -80;

    public static final byte BUFF_LIVE = -79;

    public static final byte CALL_EFFECT_ME = -78;

    public static final byte FIND_PARTY = -77;

    public static final byte LOCK_PARTY = -76;

    public static final byte ITEM_BOX_CLEAR = -75;

    public static final byte SHOW_WAIT = -74;

    public static final byte CALL_EFFECT_NPC = -73;

    public static final byte ME_LOAD_GOLD = -72;

    public static final byte ME_UP_GOLD = -71;

    public static final byte ADMIN_MOVE = -70;

    public static final byte ME_LOAD_THU_NUOI = -69;

    public static final byte PLAYER_LOAD_THU_NUOI = -68;

    public static final byte SAVE_RMS = -67;

    public static final byte LOAD_RMS = -65;

    public static final byte PLAYER_LOAD_MAT_NA = -64;

    public static final byte CLAN_INVITE = -63;

    public static final byte CLAN_ACCEPT_INVITE = -62;

    public static final byte CLAN_PLEASE = -61;

    public static final byte CLAN_ACCEPT_PLEASE = -60;

    public static final byte REFRESH_HP = -59;

    public static final byte CALL_EFFECT_BALL = -58;

    public static final byte CALL_EFFECT_BALL_1 = -57;

    public static final byte PLAYER_LOAD_AO_CHOANG = -56;

    public static final byte PLAYER_LOAD_GIA_TOC = -55;

    public static final byte LOAD_THU_CUOI = -54;

    public static final byte FULL_SIZE = -32;

    public static final byte KEY_WINPHONE = -31;

    public static final byte SUB_COMMAND = -30;

    public static final byte NOT_LOGIN = -29;

    public static final byte NOT_MAP = -28;

    public static final byte GET_SESSION_ID = -27;

    public static final byte SERVER_DIALOG = -26;

    public static final byte SERVER_ALERT = -25;

    public static final byte SERVER_MESSAGE = -24;

    public static final byte CHAT_MAP = -23;

    public static final byte CHAT_PRIVATE = -22;

    public static final byte CHAT_SERVER = -21;

    public static final byte CHAT_PARTY = -20;

    public static final byte CHAT_CLAN = -19;

    public static final byte MAP_INFO = -18;

    public static final byte MAP_CHANGE = -17;

    public static final byte MAP_CLEAR = -16;

    public static final byte ITEMMAP_REMOVE = -15;

    public static final byte ITEMMAP_MYPICK = -14;

    public static final byte ITEMMAP_PLAYERPICK = -13;

    public static final byte ME_THROW = -12;

    public static final byte ME_DIE = -11;

    public static final byte ME_LIVE = -10;

    public static final byte ME_BACK = -9;

    public static final byte ME_UP_COIN_LOCK = -8;

    public static final byte ME_CHANGE_COIN = -7;

    public static final byte PLAYER_THROW = -6;

    public static final byte NPC_LIVE = -5;

    public static final byte NPC_DIE = -4;

    public static final byte NPC_ATTACK_ME = -3;

    public static final byte NPC_ATTACK_PLAYER = -2;

    public static final byte NPC_HP = -1;

    public static final byte PLAYER_DIE = 0;

    public static final byte PLAYER_MOVE = 1;

    public static final byte PLAYER_REMOVE = 2;

    public static final byte PLAYER_ADD = 3;

    public static final byte PLAYER_ATTACK_N_P = 4;

    public static final byte PLAYER_UP_EXP = 5;

    public static final byte ITEMMAP_ADD = 6;

    public static final byte ITEM_BAG_REFRESH = 7;

    public static final byte ITEM_BAG_ADD = 8;

    public static final byte ITEM_BAG_ADD_QUANTITY = 9;

    public static final byte ITEM_BAG_CLEAR = 10;

    public static final byte ITEM_USE = 11;

    public static final byte ITEM_USE_CHANGEMAP = 12;

    public static final byte ITEM_BUY = 13;

    public static final byte ITEM_SALE = 14;

    public static final byte ITEM_BODY_TO_BAG = 15;

    public static final byte ITEM_BOX_TO_BAG = 16;

    public static final byte ITEM_BAG_TO_BOX = 17;

    public static final byte ITEM_USE_UPTOUP = 18;

    public static final byte UPPEARL = 19;

    public static final byte UPPEARL_LOCK = 20;

    public static final byte UPGRADE = 21;

    public static final byte SPLIT = 22;

    public static final byte PLEASE_INPUT_PARTY = 23;

    public static final byte ACCEPT_PLEASE_PARTY = 24;

    public static final byte REQUEST_PLAYERS = 25;

    public static final byte UPDATE_ACHIEVEMENT = 26;

    public static final byte MOVE_FAST_NPC = 27;

    public static final byte ZONE_CHANGE = 28;

    public static final byte MENU = 29;

    public static final byte OPEN_UI = 30;

    public static final byte OPEN_UI_BOX = 31;

    public static final byte OPEN_UI_PT = 32;

    public static final byte OPEN_UI_SHOP = 33;

    public static final byte OPEN_MENU_ID = 34;

    public static final byte OPEN_UI_COLLECT = 35;

    public static final byte OPEN_UI_ZONE = 36;

    public static final byte OPEN_UI_TRADE = 37;

    public static final byte OPEN_UI_SAY = 38;

    public static final byte OPEN_UI_CONFIRM = 39;

    public static final byte OPEN_UI_MENU = 40;

    public static final byte OPEN_UI_LUYEN_NGOC = 46;

    public static final byte OPEN_UI_KHAM_NGOC = 47;

    public static final byte SKILL_SELECT = 41;

    public static final byte REQUEST_ITEM_INFO = 42;

    public static final byte TRADE_INVITE = 43;

    public static final byte TRADE_INVITE_ACCEPT = 44;

    public static final byte TRADE_LOCK_ITEM = 45;

    public static final byte TRADE_ACCEPT = 46;

    public static final byte TASK_GET = 47;

    public static final byte TASK_NEXT = 48;

    public static final byte TASK_FINISH = 49;

    public static final byte TASK_UPDATE = 50;

    public static final byte NPC_MISS = 51;

    public static final byte RESET_POINT = 52;

    public static final byte ALERT_MESSAGE = 53;

    public static final byte ALERT_OPEN_WEB = 54;

    public static final byte ALERT_SEND_SMS = 55;

    public static final byte TRADE_INVITE_CANCEL = 56;

    public static final byte TRADE_CANCEL = 57;

    public static final byte TRADE_OK = 58;

    public static final byte FRIEND_INVITE = 59;

    public static final byte PLAYER_ATTACK_NPC = 60;

    public static final byte PLAYER_ATTACK_PLAYER = 61;

    public static final byte HAVE_ATTACK_PLAYER = 62;

    public static final byte OPEN_UI_NEWMENU = 63;

    public static final byte MOVE_FAST = 64;

    public static final byte TEST_INVITE = 65;

    public static final byte TEST_INVITE_ACCEPT = 66;

    public static final byte TEST_END = 67;

    public static final byte ADD_CUU_SAT = 68;

    public static final byte ME_CUU_SAT = 69;

    public static final byte CLEAR_CUU_SAT = 70;

    public static final byte PLAYER_UP_EXPDOWN = 71;

    public static final byte ME_DIE_EXP_DOWN = 72;

    public static final byte PLAYER_ATTACK_P_N = 73;

    public static final byte USE_SKILL_MY_BUFF = 74;

    public static final byte CREATE_BUNHIN = 75;

    public static final byte NPC_ATTACK_BUNHIN = 76;

    public static final byte CLEAR_BUNHIN = 77;

    public static final byte NPC_CHANGE = 78;

    public static final byte PARTY_INVITE = 79;

    public static final byte PARTY_ACCEPT = 80;

    public static final byte PARTY_CANCEL = 81;

    public static final byte PLAYER_IN_PARTY = 82;

    public static final byte PARTY_OUT = 83;

    public static final byte FRIEND_ADD = 84;

    public static final byte NPC_IS_DISABLE = 85;

    public static final byte NPC_IS_MOVE = 86;

    public static final byte SUMON_ATTACK = 87;

    public static final byte RETURN_POINT_MAP = 88;

    public static final byte NPC_IS_FIRE = 89;

    public static final byte NPC_IS_ICE = 90;

    public static final byte NPC_IS_WIND = 91;

    public static final byte OPEN_TEXT_BOX_ID = 92;

    public static final byte VIEW_INFO = 93;

    public static final byte REQUEST_ITEM_PLAYER = 94;

    public static final byte ME_UP_COIN_BAG = 95;

    public static final byte GET_TASK_ORDER = 96;

    public static final byte GET_TASK_UPDATE = 97;

    public static final byte CLEAR_TASK_ORDER = 98;

    public static final byte TEST_DUN_INVITE = 99;

    public static final byte TEST_DUN_LIST = 100;

    public static final byte VIEW_INFO1 = 101;

    public static final byte SEND_ITEM_TO_AUCTION = 102;

    public static final byte LOAD_ITEM_AUCTION = 103;

    public static final byte VIEW_ITEM_AUCTION = 104;

    public static final byte BUY_ITEM_AUCTION = 105;

    public static final byte TEST_GT_INVITE = 106;

    public static final byte OPEN_UI_CONFIRM_POPUP = 107;

    public static final byte ITEM_MON_TO_BAG = 108;

    public static final byte OPEN_UI_NEWMENU1 = 109;

    public static final byte LUYEN_THACH = 110;

    public static final byte TINH_LUYEN = 111;

    public static final byte DOI_OPTION = 112;

    public static final byte CAT_KEO = 113;

    public static final byte NV_BIAN = 114;

    public static final byte UPDATE_INFO_ME = 115;

    public static final byte UPDATE_INFO_CHAR = 116;

    public static final byte TREE_MAP = 117;

    public static final byte CONFIRM_USER_PASS = 118;

    public static final byte TELEPORT_TRAIN = 119;

    public static final byte DOI_MAT_KHAU = 120;

    public static final byte LIST_TOP_ARENA = 121;

    public static final byte SERVER_ADD_MOB = 122;

    public static final byte KHAM_NGOC = 124;

    public static final byte EFF_DYNAMIC_TOOL = 125;

    public static final byte GIAO_ITEM = 126;

    public static final byte ADMIN_COMMAND = 127;

    public static final byte RESET_BUTTON = 57;

    public static final byte NEW_IMAGE = 122;
}
