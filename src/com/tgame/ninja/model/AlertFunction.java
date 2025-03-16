package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import com.tgame.ninja.server.GameServer;

public class AlertFunction {

    public static String URL_DOWNLOAD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.URL_DOWNLOAD;
        }
        return Alert_EN.URL_DOWNLOAD;
    }

    public static String ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR;
        }
        return Alert_EN.ERROR;
    }

    public static String SERVER_PAUSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SERVER_PAUSE;
        }
        return Alert_EN.SERVER_PAUSE;
    }

    public static String VERSION_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VERSION_FAIL;
        }
        return Alert_EN.VERSION_FAIL;
    }

    public static String VERSION_NEW(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VERSION_NEW;
        }
        return Alert_EN.VERSION_NEW;
    }

    public static String LOGIN_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LOGIN_FAIL;
        }
        return Alert_EN.LOGIN_FAIL;
    }

    public static String LOGIN_DOUBLE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LOGIN_DOUBLE;
        }
        return Alert_EN.LOGIN_DOUBLE;
    }

    public static String LOGIN_DIFFERENT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LOGIN_DIFFERENT;
        }
        return Alert_EN.LOGIN_DIFFERENT;
    }

    public static String USER_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_FAIL;
        }
        return Alert_EN.USER_FAIL;
    }

    public static String USER_ACTIVE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_ACTIVE;
        }
        return Alert_EN.USER_ACTIVE;
    }

    public static String USER_XACTHUC(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_XACTHUC;
        }
        return Alert_EN.USER_XACTHUC;
    }

    public static String USER_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_LOCK;
        }
        return Alert_EN.USER_LOCK;
    }

    public static String PLAYER_LIMIT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PLAYER_LIMIT;
        }
        return Alert_EN.PLAYER_LIMIT;
    }

    public static String PLAYER_EXITS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PLAYER_EXITS;
        }
        return Alert_EN.PLAYER_EXITS;
    }

    public static String PLAYER_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PLAYER_ERROR;
        }
        return Alert_EN.PLAYER_ERROR;
    }

    public static String PLAYER_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PLAYER_LOCK;
        }
        return Alert_EN.PLAYER_LOCK;
    }

    public static String PLAYER_NAME_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PLAYER_NAME_FAIL;
        }
        return Alert_EN.PLAYER_NAME_FAIL;
    }

    public static String PASS_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PASS_FAIL;
        }
        return Alert_EN.PASS_FAIL;
    }

    public static String EMAIL_PHONE_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.EMAIL_PHONE_FAIL;
        }
        return Alert_EN.EMAIL_PHONE_FAIL;
    }

    public static String USER_FAIL_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_FAIL_1;
        }
        return Alert_EN.USER_FAIL_1;
    }

    public static String USER_FAIL_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.USER_FAIL_2;
        }
        return Alert_EN.USER_FAIL_2;
    }

    public static String HAVE_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_CLAN;
        }
        return Alert_EN.HAVE_CLAN;
    }

    public static String LEVEL_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_CLAN;
        }
        return Alert_EN.LEVEL_CLAN;
    }

    public static String NAME_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NAME_CLAN;
        }
        return Alert_EN.NAME_CLAN;
    }

    public static String NAME_PLAYER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NAME_PLAYER;
        }
        return Alert_EN.NAME_PLAYER;
    }

    public static String SAY_CREATE_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_CREATE_CLAN;
        }
        return Alert_EN.SAY_CREATE_CLAN;
    }

    public static String NOT_GOLD_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_GOLD_CLAN;
        }
        return Alert_EN.NOT_GOLD_CLAN;
    }

    public static String LEVEL_CLAN_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_CLAN_ERROR;
        }
        return Alert_EN.LEVEL_CLAN_ERROR;
    }

    public static String NAME_CLAN_ERROR1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NAME_CLAN_ERROR1;
        }
        return Alert_EN.NAME_CLAN_ERROR1;
    }

    public static String NAME_CLAN_ERROR2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NAME_CLAN_ERROR2;
        }
        return Alert_EN.NAME_CLAN_ERROR2;
    }

    public static String MAX_PARTY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_PARTY;
        }
        return Alert_EN.MAX_PARTY;
    }

    public static String MAX_PARTY1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_PARTY1;
        }
        return Alert_EN.MAX_PARTY1;
    }

    public static String NOT_MOVETO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_MOVETO;
        }
        return Alert_EN.NOT_MOVETO;
    }

    public static String MAP_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAP_FULL;
        }
        return Alert_EN.MAP_FULL;
    }

    public static String MAP_FULL1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAP_FULL1;
        }
        return Alert_EN.MAP_FULL1;
    }

    public static String ZONE_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ZONE_FULL;
        }
        return Alert_EN.ZONE_FULL;
    }

    public static String SKILL_LEVEL_UP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SKILL_LEVEL_UP;
        }
        return Alert_EN.SKILL_LEVEL_UP;
    }

    public static String SKILL_NOT_LEARN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SKILL_NOT_LEARN;
        }
        return Alert_EN.SKILL_NOT_LEARN;
    }

    public static String sKILL_CAN_NOT_UP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SKILL_CAN_NOT_UP;
        }
        return Alert_EN.SKILL_CAN_NOT_UP;
    }

    public static String BAG_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BAG_FULL;
        }
        return Alert_EN.BAG_FULL;
    }

    public static String HP_DOWN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HP_DOWN;
        }
        return Alert_EN.HP_DOWN;
    }

    public static String BOX_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BOX_FULL;
        }
        return Alert_EN.BOX_FULL;
    }

    public static String NOT_ENOUGH_LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_LEVEL;
        }
        return Alert_EN.NOT_ENOUGH_LEVEL;
    }

    public static String NOT_ENOUGH_COIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_COIN;
        }
        return Alert_EN.NOT_ENOUGH_COIN;
    }

    public static String NOT_TT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_TT;
        }
        return Alert_EN.NOT_TT;
    }

    public static String NOT_ENOUGH_COINGT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_COINGT;
        }
        return Alert_EN.NOT_ENOUGH_COINGT;
    }

    public static String NOT_ENOUGH_COIN_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_COIN_LOCK;
        }
        return Alert_EN.NOT_ENOUGH_COIN_LOCK;
    }

    public static String NOT_ENOUGH_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_GOLD;
        }
        return Alert_EN.NOT_ENOUGH_GOLD;
    }

    public static String NOT_ENOUGH_GOLD_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_GOLD_LOCK;
        }
        return Alert_EN.NOT_ENOUGH_GOLD_LOCK;
    }

    public static String ITEM_NOT_ME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_NOT_ME;
        }
        return Alert_EN.ITEM_NOT_ME;
    }

    public static String ITEM_FAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_FAR;
        }
        return Alert_EN.ITEM_FAR;
    }

    public static String POINT_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_ERROR;
        }
        return Alert_EN.POINT_ERROR;
    }

    public static String POINT_MAX_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_MAX_ERROR;
        }
        return Alert_EN.POINT_MAX_ERROR;
    }

    public static String UPGRADE_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPGRADE_ERROR;
        }
        return Alert_EN.UPGRADE_ERROR;
    }

    public static String COLLECT_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COLLECT_ERROR;
        }
        return Alert_EN.COLLECT_ERROR;
    }

    public static String NOT_LEADER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEADER;
        }
        return Alert_EN.NOT_LEADER;
    }

    public static String PT_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PT_FULL;
        }
        return Alert_EN.PT_FULL;
    }

    public static String ONE_POINT_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ONE_POINT_SKILL;
        }
        return Alert_EN.ONE_POINT_SKILL;
    }

    public static String TEN_POINT_POTENTIAL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEN_POINT_POTENTIAL;
        }
        return Alert_EN.TEN_POINT_POTENTIAL;
    }

    public static String HAVE_PT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_PT;
        }
        return Alert_EN.HAVE_PT;
    }

    public static String MAX_COIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_COIN;
        }
        return Alert_EN.MAX_COIN;
    }

    public static String MAX_YEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_YEN;
        }
        return Alert_EN.MAX_YEN;
    }

    public static String ERROR_INVITE_CLAN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_CLAN1;
        }
        return Alert_EN.ERROR_INVITE_CLAN1;
    }

    public static String ERROR_INVITE_CLAN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_CLAN2;
        }
        return Alert_EN.ERROR_INVITE_CLAN2;
    }

    public static String ERROR_INVITE_CLAN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_CLAN3;
        }
        return Alert_EN.ERROR_INVITE_CLAN3;
    }

    public static String ERROR_INVITE_CLAN4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_CLAN4;
        }
        return Alert_EN.ERROR_INVITE_CLAN4;
    }

    public static String ERROR_INVITE_CLAN5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_CLAN5;
        }
        return Alert_EN.ERROR_INVITE_CLAN5;
    }

    public static String CLAN_ALERT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_ALERT;
        }
        return Alert_EN.CLAN_ALERT;
    }

    public static String CHAT_ADMIN_MAX(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHAT_ADMIN_MAX;
        }
        return Alert_EN.CHAT_ADMIN_MAX;
    }

    public static String CHAT_FILTER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHAT_FILTER;
        }
        return Alert_EN.CHAT_FILTER;
    }

    public static String ALERT_ADMIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_ADMIN;
        }
        return Alert_EN.ALERT_ADMIN;
    }

    public static String ERROR_INVITE_TRADE1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_TRADE1;
        }
        return Alert_EN.ERROR_INVITE_TRADE1;
    }

    public static String ERROR_INVITE_TRADE2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_TRADE2;
        }
        return Alert_EN.ERROR_INVITE_TRADE2;
    }

    public static String ERROR_INVITE_TRADE3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INVITE_TRADE3;
        }
        return Alert_EN.ERROR_INVITE_TRADE3;
    }

    public static String ERROR_ACCEPT_TRADE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_ACCEPT_TRADE;
        }
        return Alert_EN.ERROR_ACCEPT_TRADE;
    }

    public static String ERROR_INPUT_CLASS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_INPUT_CLASS;
        }
        return Alert_EN.ERROR_INPUT_CLASS;
    }

    public static String HAVE_CLASS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_CLASS;
        }
        return Alert_EN.HAVE_CLASS;
    }

    public static String BAG_NOT_FREE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BAG_NOT_FREE;
        }
        return Alert_EN.BAG_NOT_FREE;
    }

    public static String BAG_NOT_FREE2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BAG_NOT_FREE2;
        }
        return Alert_EN.BAG_NOT_FREE2;
    }

    public static String EMPTY_ONE_WATER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.EMPTY_ONE_WATER;
        }
        return Alert_EN.EMPTY_ONE_WATER;
    }

    public static String ERROR_CLASS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_CLASS;
        }
        return Alert_EN.ERROR_CLASS;
    }

    public static String CAN_NOT_USE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CAN_NOT_USE;
        }
        return Alert_EN.CAN_NOT_USE;
    }

    public static String HAVE_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_SKILL;
        }
        return Alert_EN.HAVE_SKILL;
    }

    public static String SAVE_POINT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAVE_POINT;
        }
        return Alert_EN.SAVE_POINT;
    }

    public static String HELP_THI_LUYEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_THI_LUYEN;
        }
        return Alert_EN.HELP_THI_LUYEN;
    }

    public static String SKILL_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SKILL_FAIL;
        }
        return Alert_EN.SKILL_FAIL;
    }

    public static String WEAPON_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WEAPON_FAIL;
        }
        return Alert_EN.WEAPON_FAIL;
    }

    public static String NOT_ENOUGH_GOLD_RETURN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_GOLD_RETURN;
        }
        return Alert_EN.NOT_ENOUGH_GOLD_RETURN;
    }

    public static String HELP_UPGRADE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_UPGRADE;
        }
        return Alert_EN.HELP_UPGRADE;
    }

    public static String NOT_ENOUGH_GOLD_CHAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_GOLD_CHAT;
        }
        return Alert_EN.NOT_ENOUGH_GOLD_CHAT;
    }

    public static String HAVE_CHAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_CHAT;
        }
        return Alert_EN.HAVE_CHAT;
    }

    public static String HAVE_PLEASE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_PLEASE;
        }
        return Alert_EN.HAVE_PLEASE;
    }

    public static String GET(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GET;
        }
        return Alert_EN.GET;
    }

    public static String NO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NO;
        }
        return Alert_EN.NO;
    }

    public static String ASK_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ASK_FAIL;
        }
        return Alert_EN.ASK_FAIL;
    }

    public static String MOVE_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_ERROR;
        }
        return Alert_EN.MOVE_ERROR;
    }

    public static String SALE_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SALE_ERROR;
        }
        return Alert_EN.SALE_ERROR;
    }

    public static String SALE_ERROR3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SALE_ERROR3;
        }
        return Alert_EN.SALE_ERROR3;
    }

    public static String SALE_ERROR1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SALE_ERROR1;
        }
        return Alert_EN.SALE_ERROR1;
    }

    public static String SALE_ERROR2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SALE_ERROR2;
        }
        return Alert_EN.SALE_ERROR2;
    }

    public static String HELP_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_TASK;
        }
        return Alert_EN.HELP_TASK;
    }

    public static String LIMIT_MAP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_MAP;
        }
        return Alert_EN.LIMIT_MAP;
    }

    public static String LIMIT_TEST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TEST;
        }
        return Alert_EN.LIMIT_TEST;
    }

    public static String INPUT_CLASS1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_CLASS1;
        }
        return Alert_EN.INPUT_CLASS1;
    }

    public static String INPUT_CLASS2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_CLASS2;
        }
        return Alert_EN.INPUT_CLASS2;
    }

    public static String INPUT_CLASS3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_CLASS3;
        }
        return Alert_EN.INPUT_CLASS3;
    }

    public static String NOT_NEXTTIME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_NEXTTIME;
        }
        return Alert_EN.NOT_NEXTTIME;
    }

    public static String NOT_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_SKILL;
        }
        return Alert_EN.NOT_SKILL;
    }

    public static String NOT_MANA(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_MANA;
        }
        return Alert_EN.NOT_MANA;
    }

    public static String HP_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HP_FULL;
        }
        return Alert_EN.HP_FULL;
    }

    public static String MP_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MP_FULL;
        }
        return Alert_EN.MP_FULL;
    }

    public static String SYSTEM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SYSTEM;
        }
        return Alert_EN.SYSTEM;
    }

    public static String PASS2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PASS2;
        }
        return Alert_EN.PASS2;
    }

    public static String FRIEND_ADDED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FRIEND_ADDED;
        }
        return Alert_EN.FRIEND_ADDED;
    }

    public static String ENEMY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ENEMY;
        }
        return Alert_EN.ENEMY;
    }

    public static String MATCH_END(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MATCH_END;
        }
        return Alert_EN.MATCH_END;
    }

    public static String TASK_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_FAIL;
        }
        return Alert_EN.TASK_FAIL;
    }

    public static String LIMIT_KYNANG_SO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_KYNANG_SO;
        }
        return Alert_EN.LIMIT_KYNANG_SO;
    }

    public static String LIMIT_TIEMNANG_SO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TIEMNANG_SO;
        }
        return Alert_EN.LIMIT_TIEMNANG_SO;
    }

    public static String TASK_FAIL1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_FAIL1;
        }
        return Alert_EN.TASK_FAIL1;
    }

    public static String MOVE_BACK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_BACK;
        }
        return Alert_EN.MOVE_BACK;
    }

    public static String TOP_DAUTRUONG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_DAUTRUONG;
        }
        return Alert_EN.TOP_DAUTRUONG;
    }

    public static String TASK_OK_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_OK_1;
        }
        return Alert_EN.TASK_OK_1;
    }

    public static String TASK_OK_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_OK_2;
        }
        return Alert_EN.TASK_OK_2;
    }

    public static String TASK_OK_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_OK_3;
        }
        return Alert_EN.TASK_OK_3;
    }

    public static String NOT_EXP_DOWN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_EXP_DOWN;
        }
        return Alert_EN.NOT_EXP_DOWN;
    }

    public static String NOT_PK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_PK;
        }
        return Alert_EN.NOT_PK;
    }

    public static String LEVEL_ERROR_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_ERROR_3;
        }
        return Alert_EN.LEVEL_ERROR_3;
    }

    public static String LEVEL_ERROR_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_ERROR_2;
        }
        return Alert_EN.LEVEL_ERROR_2;
    }

    public static String LEVEL_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_ERROR;
        }
        return Alert_EN.LEVEL_ERROR;
    }

    public static String TODAY_REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TODAY_REWARD;
        }
        return Alert_EN.TODAY_REWARD;
    }

    public static String TODAY_NAME_REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TODAY_NAME_REWARD;
        }
        return Alert_EN.TODAY_NAME_REWARD;
    }

    public static String TEST_ERROR_MAP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEST_ERROR_MAP;
        }
        return Alert_EN.TEST_ERROR_MAP;
    }

    public static String TIME_IN_TEST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TIME_IN_TEST;
        }
        return Alert_EN.TIME_IN_TEST;
    }

    public static String HAVE_CUU_SAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_CUU_SAT;
        }
        return Alert_EN.HAVE_CUU_SAT;
    }

    public static String ERROR_TEST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_TEST;
        }
        return Alert_EN.ERROR_TEST;
    }

    public static String HAVE_TEST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_TEST;
        }
        return Alert_EN.HAVE_TEST;
    }

    public static String BAG_EXTENDED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BAG_EXTENDED;
        }
        return Alert_EN.BAG_EXTENDED;
    }

    public static String PICK_ITEM_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PICK_ITEM_TASK;
        }
        return Alert_EN.PICK_ITEM_TASK;
    }

    public static String POINT_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_FAIL;
        }
        return Alert_EN.POINT_FAIL;
    }

    public static String POINT_FAIL1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_FAIL1;
        }
        return Alert_EN.POINT_FAIL1;
    }

    public static String NOT_SPACE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_SPACE;
        }
        return Alert_EN.NOT_SPACE;
    }

    public static String CLEAR_WEAPON(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLEAR_WEAPON;
        }
        return Alert_EN.CLEAR_WEAPON;
    }

    public static String NOT_TEAMLEADER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_TEAMLEADER;
        }
        return Alert_EN.NOT_TEAMLEADER;
    }

    public static String FRIEND_TEAM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FRIEND_TEAM;
        }
        return Alert_EN.FRIEND_TEAM;
    }

    public static String TEAMED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEAMED;
        }
        return Alert_EN.TEAMED;
    }

    public static String LIMIT_LEVEL_CHIENTRUONG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_LEVEL_CHIENTRUONG;
        }
        return Alert_EN.LIMIT_LEVEL_CHIENTRUONG;
    }

    public static String PHE_DIFFERENT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHE_DIFFERENT1;
        }
        return Alert_EN.PHE_DIFFERENT1;
    }

    public static String PHE_DIFFERENT2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHE_DIFFERENT2;
        }
        return Alert_EN.PHE_DIFFERENT2;
    }

    public static String TEAMLEADER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEAMLEADER;
        }
        return Alert_EN.TEAMLEADER;
    }

    public static String NOT_ONLINE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ONLINE;
        }
        return Alert_EN.NOT_ONLINE;
    }

    public static String NOT_CHAT(Session conn, String name) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return name + Alert_VN.NOT_CHAT;
        }
        return name + Alert_EN.NOT_CHAT;
    }

    public static String NOT_ADD_ME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ADD_ME;
        }
        return Alert_EN.NOT_ADD_ME;
    }

    public static String NOT_ONLINE_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ONLINE_2;
        }
        return Alert_EN.NOT_ONLINE_2;
    }

    public static String LET_GO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LET_GO;
        }
        return Alert_EN.LET_GO;
    }

    public static String FAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FAR;
        }
        return Alert_EN.FAR;
    }

    public static String TOO_STRONG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOO_STRONG;
        }
        return Alert_EN.TOO_STRONG;
    }

    public static String ERROR_EXTENDED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_EXTENDED;
        }
        return Alert_EN.ERROR_EXTENDED;
    }

    public static String GET_KEY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GET_KEY;
        }
        return Alert_EN.GET_KEY;
    }

    public static String GET_ROD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GET_ROD;
        }
        return Alert_EN.GET_ROD;
    }

    public static String HAVE_KEY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_KEY;
        }
        return Alert_EN.HAVE_KEY;
    }

    public static String HAVE_ROD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_ROD;
        }
        return Alert_EN.HAVE_ROD;
    }

    public static String TRUONG_LANG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TRUONG_LANG;
        }
        return Alert_EN.TRUONG_LANG;
    }

    public static String CHANGE_BOOK_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGE_BOOK_SKILL;
        }
        return Alert_EN.CHANGE_BOOK_SKILL;
    }

    public static String CHANGE_BOOK_POTENTIAL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGE_BOOK_POTENTIAL;
        }
        return Alert_EN.CHANGE_BOOK_POTENTIAL;
    }

    public static String WELCOME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WELCOME;
        }
        return Alert_EN.WELCOME;
    }

    public static String DUN_PB_ALERT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT1;
        }
        return Alert_EN.DUN_PB_ALERT1;
    }

    public static String DUN_PB_ALERT2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT2;
        }
        return Alert_EN.DUN_PB_ALERT2;
    }

    public static String DUN_PB_ALERT3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT3;
        }
        return Alert_EN.DUN_PB_ALERT3;
    }

    public static String DUN_PB_ALERT4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT4;
        }
        return Alert_EN.DUN_PB_ALERT4;
    }

    public static String DUN_PB_ALERT5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT5;
        }
        return Alert_EN.DUN_PB_ALERT5;
    }

    public static String DUN_PB_ALERT6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT6;
        }
        return Alert_EN.DUN_PB_ALERT6;
    }

    public static String DUN_PB_ALERT7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT7;
        }
        return Alert_EN.DUN_PB_ALERT7;
    }

    public static String DUN_PB_ALERT8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_PB_ALERT8;
        }
        return Alert_EN.DUN_PB_ALERT8;
    }

    public static String DUN_LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_LEVEL;
        }
        return Alert_EN.DUN_LEVEL;
    }

    public static String OPEN_BETA(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_BETA;
        }
        return Alert_EN.OPEN_BETA;
    }

    public static String FINISH_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FINISH_TASK;
        }
        return Alert_EN.FINISH_TASK;
    }

    public static String HELLO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELLO;
        }
        return Alert_EN.HELLO;
    }

    public static String GO_BACK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GO_BACK;
        }
        return Alert_EN.GO_BACK;
    }

    public static String DUN_74_CLOSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_74_CLOSE;
        }
        return Alert_EN.DUN_74_CLOSE;
    }

    public static String DUN_78_CLOSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_78_CLOSE;
        }
        return Alert_EN.DUN_78_CLOSE;
    }

    public static String DUN_91_CLOSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_91_CLOSE;
        }
        return Alert_EN.DUN_91_CLOSE;
    }

    public static String ROOM_CLOSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ROOM_CLOSE;
        }
        return Alert_EN.ROOM_CLOSE;
    }

    public static String GO_IN_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GO_IN_1;
        }
        return Alert_EN.GO_IN_1;
    }

    public static String GO_IN_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GO_IN_2;
        }
        return Alert_EN.GO_IN_2;
    }

    public static String CHALLENGE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHALLENGE;
        }
        return Alert_EN.CHALLENGE;
    }

    public static String YOU_REMEMBER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_REMEMBER;
        }
        return Alert_EN.YOU_REMEMBER;
    }

    public static String HAVE_EAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_EAT;
        }
        return Alert_EN.HAVE_EAT;
    }

    public static String PK_MAX_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PK_MAX_1;
        }
        return Alert_EN.PK_MAX_1;
    }

    public static String PK_MAX_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PK_MAX_2;
        }
        return Alert_EN.PK_MAX_2;
    }

    public static String PK_MAX_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PK_MAX_3;
        }
        return Alert_EN.PK_MAX_3;
    }

    public static String EXP_DOWN_MAX(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.EXP_DOWN_MAX;
        }
        return Alert_EN.EXP_DOWN_MAX;
    }

    public static String NOT_ATT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ATT;
        }
        return Alert_EN.NOT_ATT;
    }

    public static String NOT_CHANGE_PK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CHANGE_PK;
        }
        return Alert_EN.NOT_CHANGE_PK;
    }

    public static String NOT_CUUSAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CUUSAT;
        }
        return Alert_EN.NOT_CUUSAT;
    }

    public static String MAX_FRIEND(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_FRIEND;
        }
        return Alert_EN.MAX_FRIEND;
    }

    public static String FREE_POTENTIAL_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FREE_POTENTIAL_1;
        }
        return Alert_EN.FREE_POTENTIAL_1;
    }

    public static String FREE_POTENTIAL_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FREE_POTENTIAL_2;
        }
        return Alert_EN.FREE_POTENTIAL_2;
    }

    public static String FREE_SKILL_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FREE_SKILL_1;
        }
        return Alert_EN.FREE_SKILL_1;
    }

    public static String FREE_SKILL_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FREE_SKILL_2;
        }
        return Alert_EN.FREE_SKILL_2;
    }

    public static String NOT_FREE_POTENTIAL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_FREE_POTENTIAL;
        }
        return Alert_EN.NOT_FREE_POTENTIAL;
    }

    public static String NOT_FREE_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_FREE_SKILL;
        }
        return Alert_EN.NOT_FREE_SKILL;
    }

    public static String NOT_CLASS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CLASS;
        }
        return Alert_EN.NOT_CLASS;
    }

    public static String HAVE_REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_REWARD;
        }
        return Alert_EN.HAVE_REWARD;
    }

    public static String CHANGED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGED;
        }
        return Alert_EN.CHANGED;
    }

    public static String NOT_CHANGE_XU(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CHANGE_XU;
        }
        return Alert_EN.NOT_CHANGE_XU;
    }

    public static String LIMIT_TIME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TIME;
        }
        return Alert_EN.LIMIT_TIME;
    }

    public static String WAIT_RESULT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_RESULT;
        }
        return Alert_EN.WAIT_RESULT;
    }

    public static String NOT_CHANGE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CHANGE;
        }
        return Alert_EN.NOT_CHANGE;
    }

    public static String YOU_DIE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_DIE;
        }
        return Alert_EN.YOU_DIE;
    }

    public static String YOU_GET(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_GET;
        }
        return Alert_EN.YOU_GET;
    }

    public static String POINT_UYDANH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_UYDANH;
        }
        return Alert_EN.POINT_UYDANH;
    }

    public static String POINT_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_CLAN;
        }
        return Alert_EN.POINT_CLAN;
    }

    public static String WAIT_GET_ICE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_GET_ICE;
        }
        return Alert_EN.WAIT_GET_ICE;
    }

    public static String WAIT_GET_WATER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_GET_WATER;
        }
        return Alert_EN.WAIT_GET_WATER;
    }

    public static String WAIT_GET_PEARL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_GET_PEARL;
        }
        return Alert_EN.WAIT_GET_PEARL;
    }

    public static String WAIT_GET_FIND(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_GET_FIND;
        }
        return Alert_EN.WAIT_GET_FIND;
    }

    public static String NOT_FOUND(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_FOUND;
        }
        return Alert_EN.NOT_FOUND;
    }

    public static String ITEM_HERE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_HERE;
        }
        return Alert_EN.ITEM_HERE;
    }

    public static String NOT_CLASS1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CLASS1;
        }
        return Alert_EN.NOT_CLASS1;
    }

    public static String COIN_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COIN_LOCK;
        }
        return Alert_EN.COIN_LOCK;
    }

    public static String COIN_BAG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COIN_BAG;
        }
        return Alert_EN.COIN_BAG;
    }

    public static String EXP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.EXP;
        }
        return Alert_EN.EXP;
    }

    public static String YOU_IN_PARTY_THIS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_IN_PARTY_THIS;
        }
        return Alert_EN.YOU_IN_PARTY_THIS;
    }

    public static String NOT_PARTY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_PARTY;
        }
        return Alert_EN.NOT_PARTY;
    }

    public static String YOU_IN_PARTY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_IN_PARTY;
        }
        return Alert_EN.YOU_IN_PARTY;
    }

    public static String LEADER_LOCK_PARTY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEADER_LOCK_PARTY;
        }
        return Alert_EN.LEADER_LOCK_PARTY;
    }

    public static String OPEN_DOOR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR;
        }
        return Alert_EN.OPEN_DOOR;
    }

    public static String CLOSE_CT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLOSE_CT;
        }
        return Alert_EN.CLOSE_CT;
    }

    public static String SUMMARY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SUMMARY;
        }
        return Alert_EN.SUMMARY;
    }

    public static String RESULT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.RESULT;
        }
        return Alert_EN.RESULT;
    }

    public static String CLOSE_DOOR_CT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLOSE_DOOR_CT;
        }
        return Alert_EN.CLOSE_DOOR_CT;
    }

    public static String OPEN_DOOR_CT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT1;
        }
        return Alert_EN.OPEN_DOOR_CT1;
    }

    public static String OPEN_DOOR_CT2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT2;
        }
        return Alert_EN.OPEN_DOOR_CT2;
    }

    public static String OPEN_DOOR_CT3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT3;
        }
        return Alert_EN.OPEN_DOOR_CT3;
    }

    public static String OPEN_DOOR_CT4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT4;
        }
        return Alert_EN.OPEN_DOOR_CT4;
    }

    public static String OPEN_DOOR_CT5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT5;
        }
        return Alert_EN.OPEN_DOOR_CT5;
    }

    public static String OPEN_DOOR_CT6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT6;
        }
        return Alert_EN.OPEN_DOOR_CT6;
    }

    public static String OPEN_DOOR_CT7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_DOOR_CT7;
        }
        return Alert_EN.OPEN_DOOR_CT7;
    }

    public static String CLOSE_DOOR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLOSE_DOOR;
        }
        return Alert_EN.CLOSE_DOOR;
    }

    public static String COIN_INPUT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COIN_INPUT;
        }
        return Alert_EN.COIN_INPUT;
    }

    public static String MOVE_FAIL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_FAIL;
        }
        return Alert_EN.MOVE_FAIL;
    }

    public static String VIEW_INFO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VIEW_INFO;
        }
        return Alert_EN.VIEW_INFO;
    }

    public static String YOU_HAVE_POTENTIAL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_HAVE_POTENTIAL;
        }
        return Alert_EN.YOU_HAVE_POTENTIAL;
    }

    public static String YOU_HAVE_SKILL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_HAVE_SKILL;
        }
        return Alert_EN.YOU_HAVE_SKILL;
    }

    public static String TASK_LOOPDAY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_LOOPDAY;
        }
        return Alert_EN.TASK_LOOPDAY;
    }

    public static String TASK_LOOPBOSS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_LOOPBOSS;
        }
        return Alert_EN.TASK_LOOPBOSS;
    }

    public static String COUNT_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_TASK;
        }
        return Alert_EN.COUNT_TASK;
    }

    public static String HAVE_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_TASK;
        }
        return Alert_EN.HAVE_TASK;
    }

    public static String TASK_FINISH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_FINISH;
        }
        return Alert_EN.TASK_FINISH;
    }

    public static String TASK_CLEAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_CLEAR;
        }
        return Alert_EN.TASK_CLEAR;
    }

    public static String TASK_NOT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOT;
        }
        return Alert_EN.TASK_NOT;
    }

    public static String TASK_MOVE_FAST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_MOVE_FAST;
        }
        return Alert_EN.TASK_MOVE_FAST;
    }

    public static String TASK_NOT_FINISH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOT_FINISH;
        }
        return Alert_EN.TASK_NOT_FINISH;
    }

    public static String TASK_SAY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SAY;
        }
        return Alert_EN.TASK_SAY;
    }

    public static String TASK_NOTE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE;
        }
        return Alert_EN.TASK_NOTE;
    }

    public static String TASK_SUKIEN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN2;
        }
        return Alert_EN.TASK_SUKIEN2;
    }

    public static String TASK_SUKIEN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN3;
        }
        return Alert_EN.TASK_SUKIEN3;
    }

    public static String TASK_SUKIEN4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN4;
        }
        return Alert_EN.TASK_SUKIEN4;
    }

    public static String TASK_SUKIEN8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN8;
        }
        return Alert_EN.TASK_SUKIEN8;
    }

    public static String TASK_SUKIEN5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN5;
        }
        return Alert_EN.TASK_SUKIEN5;
    }

    public static String TASK_SUKIEN6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN6;
        }
        return Alert_EN.TASK_SUKIEN6;
    }

    public static String TASK_SUKIEN7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN7;
        }
        return Alert_EN.TASK_SUKIEN7;
    }

    public static String TASK_SUKIEN9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN9;
        }
        return Alert_EN.TASK_SUKIEN9;
    }

    public static String SAY_DOI_HUY_CHUONG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DOI_HUY_CHUONG;
        }
        return Alert_EN.DOI_HUY_CHUONG;
    }

    public static String TASK_SUKIEN10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_SUKIEN10;
        }
        return Alert_EN.TASK_SUKIEN10;
    }

    public static String TRU_XU(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TRU_XU;
        }
        return Alert_EN.TRU_XU;
    }

    public static String NOT_ITEM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ITEM;
        }
        return Alert_EN.NOT_ITEM;
    }

    public static String NOT_LEVEL20(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL20;
        }
        return Alert_EN.NOT_LEVEL20;
    }

    public static String NOT_LEVEL50(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL50;
        }
        return Alert_EN.NOT_LEVEL50;
    }

    public static String NOT_LEVEL60(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL60;
        }
        return Alert_EN.NOT_LEVEL60;
    }

    public static String NOT_LEVEL40(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL40;
        }
        return Alert_EN.NOT_LEVEL40;
    }

    public static String NOT_INVITE_LEVEL40(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_INVITE_LEVEL40;
        }
        return Alert_EN.NOT_INVITE_LEVEL40;
    }

    public static String LIMIT_LEFT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_LEFT;
        }
        return Alert_EN.LIMIT_LEFT;
    }

    public static String LIMIT_RIGHT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_RIGHT;
        }
        return Alert_EN.LIMIT_RIGHT;
    }

    public static String PHE_FAIL1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHE_FAIL1;
        }
        return Alert_EN.PHE_FAIL1;
    }

    public static String PHE_FAIL2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHE_FAIL2;
        }
        return Alert_EN.PHE_FAIL2;
    }

    public static String REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.REWARD;
        }
        return Alert_EN.REWARD;
    }

    public static String NOT_LEVEL_30(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL_30;
        }
        return Alert_EN.NOT_LEVEL_30;
    }

    public static String NOT_DIFFRENT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_DIFFRENT;
        }
        return Alert_EN.NOT_DIFFRENT;
    }

    public static String NOT_HERE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_HERE;
        }
        return Alert_EN.NOT_HERE;
    }

    public static String HAVE_TASK_PT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_TASK_PT;
        }
        return Alert_EN.HAVE_TASK_PT;
    }

    public static String NOT_LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_LEVEL;
        }
        return Alert_EN.NOT_LEVEL;
    }

    public static String NOT_TEAM_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_TEAM_TASK;
        }
        return Alert_EN.NOT_TEAM_TASK;
    }

    public static String TASK_BOSS_FINISH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_BOSS_FINISH;
        }
        return Alert_EN.TASK_BOSS_FINISH;
    }

    public static String GO_NEXT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GO_NEXT;
        }
        return Alert_EN.GO_NEXT;
    }

    public static String MAX_TTL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_TTL;
        }
        return Alert_EN.MAX_TTL;
    }

    public static String MAX_HDL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_HDL;
        }
        return Alert_EN.MAX_HDL;
    }

    public static String NOT_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_CLAN;
        }
        return Alert_EN.NOT_CLAN;
    }

    public static String MAX_CARD_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_CARD_CLAN;
        }
        return Alert_EN.MAX_CARD_CLAN;
    }

    public static String[] TITLE_CT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TITLE_CT;
        }
        return Alert_EN.TITLE_CT;
    }

    public static String[] CHANGE_BOOK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGE_BOOK;
        }
        return Alert_EN.CHANGE_BOOK;
    }

    public static String COUNT_TT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_TT;
        }
        return Alert_EN.COUNT_TT;
    }

    public static String COUNT_HD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_HD;
        }
        return Alert_EN.COUNT_HD;
    }

    public static String COUNT_OPENGT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_OPENGT;
        }
        return Alert_EN.COUNT_OPENGT;
    }

    public static String ERROR_CONVER_UPGRADE1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_CONVER_UPGRADE1;
        }
        return Alert_EN.ERROR_CONVER_UPGRADE1;
    }

    public static String ERROR_CONVER_UPGRADE2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_CONVER_UPGRADE2;
        }
        return Alert_EN.ERROR_CONVER_UPGRADE2;
    }

    public static String ERROR_CONVER_UPGRADE3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_CONVER_UPGRADE3;
        }
        return Alert_EN.ERROR_CONVER_UPGRADE3;
    }

    public static String ERROR_CONVER_UPGRADE4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_CONVER_UPGRADE4;
        }
        return Alert_EN.ERROR_CONVER_UPGRADE4;
    }

    public static String CLAN_OUT_ONE_DAY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_OUT_ONE_DAY;
        }
        return Alert_EN.CLAN_OUT_ONE_DAY;
    }

    public static String CLAN_CLEAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_CLEAR;
        }
        return Alert_EN.CLAN_CLEAR;
    }

    public static String HAVE_ELDER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_ELDER;
        }
        return Alert_EN.HAVE_ELDER;
    }

    public static String HAVE_ASSIST1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_ASSIST1;
        }
        return Alert_EN.HAVE_ASSIST1;
    }

    public static String HAVE_ASSIST2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_ASSIST2;
        }
        return Alert_EN.HAVE_ASSIST2;
    }

    public static String MAX_ELDER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAX_ELDER;
        }
        return Alert_EN.MAX_ELDER;
    }

    public static String UPDATE_PASS2_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_1;
        }
        return Alert_EN.UPDATE_PASS2_1;
    }

    public static String UPDATE_PASS2_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_2;
        }
        return Alert_EN.UPDATE_PASS2_2;
    }

    public static String UPDATE_PASS2_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_3;
        }
        return Alert_EN.UPDATE_PASS2_3;
    }

    public static String UPDATE_PASS2_4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_4;
        }
        return Alert_EN.UPDATE_PASS2_4;
    }

    public static String UPDATE_PASS2_5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_5;
        }
        return Alert_EN.UPDATE_PASS2_5;
    }

    public static String UPDATE_PASS2_6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE_PASS2_6;
        }
        return Alert_EN.UPDATE_PASS2_6;
    }

    public static String DONT_MOVE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DONT_MOVE;
        }
        return Alert_EN.DONT_MOVE;
    }

    public static String MOVE_OUT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_OUT;
        }
        return Alert_EN.MOVE_OUT;
    }

    public static String MOVE_OUT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_OUT1;
        }
        return Alert_EN.MOVE_OUT1;
    }

    public static String MOVE_OUT2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MOVE_OUT2;
        }
        return Alert_EN.MOVE_OUT2;
    }

    public static String NOT_TELEPORT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_TELEPORT;
        }
        return Alert_EN.NOT_TELEPORT;
    }

    public static String ERROR_DUN_CLAN0(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN0;
        }
        return Alert_EN.ERROR_DUN_CLAN0;
    }

    public static String ERROR_DUN_CLAN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN1;
        }
        return Alert_EN.ERROR_DUN_CLAN1;
    }

    public static String ERROR_DUN_CLAN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN2;
        }
        return Alert_EN.ERROR_DUN_CLAN2;
    }

    public static String ERROR_DUN_CLAN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN3;
        }
        return Alert_EN.ERROR_DUN_CLAN3;
    }

    public static String ERROR_DUN_CLAN4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN4;
        }
        return Alert_EN.ERROR_DUN_CLAN4;
    }

    public static String ERROR_DUN_CLAN5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN5;
        }
        return Alert_EN.ERROR_DUN_CLAN5;
    }

    public static String ERROR_DUN_CLAN6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN6;
        }
        return Alert_EN.ERROR_DUN_CLAN6;
    }

    public static String ERROR_DUN_CLAN7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN7;
        }
        return Alert_EN.ERROR_DUN_CLAN7;
    }

    public static String ERROR_DUN_CLAN8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN8;
        }
        return Alert_EN.ERROR_DUN_CLAN8;
    }

    public static String ERROR_DUN_CLAN9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN9;
        }
        return Alert_EN.ERROR_DUN_CLAN9;
    }

    public static String ERROR_DUN_CLAN10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN10;
        }
        return Alert_EN.ERROR_DUN_CLAN10;
    }

    public static String ERROR_DUN_CLAN11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN11;
        }
        return Alert_EN.ERROR_DUN_CLAN11;
    }

    public static String ERROR_DUN_CLAN12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN12;
        }
        return Alert_EN.ERROR_DUN_CLAN12;
    }

    public static String ERROR_DUN_CLAN13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN13;
        }
        return Alert_EN.ERROR_DUN_CLAN13;
    }

    public static String ERROR_DUN_CLAN14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR_DUN_CLAN14;
        }
        return Alert_EN.ERROR_DUN_CLAN14;
    }

    public static String ALERT_DUN_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN;
        }
        return Alert_EN.ALERT_DUN_CLAN;
    }

    public static String ALERT_DUN_CLAN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN1;
        }
        return Alert_EN.ALERT_DUN_CLAN1;
    }

    public static String ALERT_DUN_CLAN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN2;
        }
        return Alert_EN.ALERT_DUN_CLAN2;
    }

    public static String ALERT_DUN_CLAN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN3;
        }
        return Alert_EN.ALERT_DUN_CLAN3;
    }

    public static String ALERT_DUN_CLAN4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN4;
        }
        return Alert_EN.ALERT_DUN_CLAN4;
    }

    public static String ALERT_DUN_CLAN5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN5;
        }
        return Alert_EN.ALERT_DUN_CLAN5;
    }

    public static String ALERT_DUN_CLAN6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN6;
        }
        return Alert_EN.ALERT_DUN_CLAN6;
    }

    public static String ALERT_DUN_CLAN7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN7;
        }
        return Alert_EN.ALERT_DUN_CLAN7;
    }

    public static String ALERT_DUN_CLAN8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLAN8;
        }
        return Alert_EN.ALERT_DUN_CLAN8;
    }

    public static String[] ALERT_DUN_CLANS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_DUN_CLANS;
        }
        return Alert_EN.ALERT_DUN_CLANS;
    }

    public static String KIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.KIN;
        }
        return Alert_EN.KIN;
    }

    public static String COIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COIN;
        }
        return Alert_EN.COIN;
    }

    public static String CLAN_CLEAR2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_CLEAR2;
        }
        return Alert_EN.CLAN_CLEAR2;
    }

    public static String INPUT_MONEY_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_MONEY_CLAN;
        }
        return Alert_EN.INPUT_MONEY_CLAN;
    }

    public static String HELP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP;
        }
        return Alert_EN.HELP;
    }

    public static String BOARD_CARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BOARD_CARD;
        }
        return Alert_EN.BOARD_CARD;
    }

    public static String NOT_EXP_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_EXP_CLAN;
        }
        return Alert_EN.NOT_EXP_CLAN;
    }

    public static String NOT_COIN_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_COIN_CLAN;
        }
        return Alert_EN.NOT_COIN_CLAN;
    }

    public static String CLAN_UP_LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_UP_LEVEL;
        }
        return Alert_EN.CLAN_UP_LEVEL;
    }

    public static String ALERT_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_LOCK;
        }
        return Alert_EN.ALERT_LOCK;
    }

    public static String PB_OPENED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PB_OPENED;
        }
        return Alert_EN.PB_OPENED;
    }

    public static String HELP_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_CLAN;
        }
        return Alert_EN.HELP_CLAN;
    }

    public static String HELP_CARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_CARD;
        }
        return Alert_EN.HELP_CARD;
    }

    public static String[] CLANS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLANS;
        }
        return Alert_EN.CLANS;
    }

    public static String[] HELP_0(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_0;
        }
        return Alert_EN.HELP_0;
    }

    public static String[] HELP_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_1;
        }
        return Alert_EN.HELP_1;
    }

    public static String[] HELP_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_2;
        }
        return Alert_EN.HELP_2;
    }

    public static String[] HELP_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_3;
        }
        return Alert_EN.HELP_3;
    }

    public static String WAP_VIRUS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAP_VIRUS;
        }
        return Alert_EN.WAP_VIRUS;
    }

    public static String OUT_DUN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OUT_DUN;
        }
        return Alert_EN.OUT_DUN;
    }

    public static String FIND_ITEM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FIND_ITEM;
        }
        return Alert_EN.FIND_ITEM;
    }

    public static String CLOSE_DUN_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLOSE_DUN_CLAN;
        }
        return Alert_EN.CLOSE_DUN_CLAN;
    }

    public static String CLOSE_DUN_PB(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLOSE_DUN_PB;
        }
        return Alert_EN.CLOSE_DUN_PB;
    }

    public static String NOT_INFO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_INFO;
        }
        return Alert_EN.NOT_INFO;
    }

    public static String SERVER_FULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SERVER_FULL;
        }
        return Alert_EN.SERVER_FULL;
    }

    public static String BACH_GIA(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BACH_GIA;
        }
        return Alert_EN.BACH_GIA;
    }

    public static String HAC_GIA(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAC_GIA;
        }
        return Alert_EN.HAC_GIA;
    }

    public static String POINT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT;
        }
        return Alert_EN.POINT;
    }

    public static String BACH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BACH;
        }
        return Alert_EN.BACH;
    }

    public static String HAC(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAC;
        }
        return Alert_EN.HAC;
    }

    public static String TITLE_NAME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TITLE_NAME;
        }
        return Alert_EN.TITLE_NAME;
    }

    public static String BACH_WIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BACH_WIN;
        }
        return Alert_EN.BACH_WIN;
    }

    public static String HAC_WIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAC_WIN;
        }
        return Alert_EN.HAC_WIN;
    }

    public static String NOT_WIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_WIN;
        }
        return Alert_EN.NOT_WIN;
    }

    public static String STATUS_ATT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.STATUS_ATT;
        }
        return Alert_EN.STATUS_ATT;
    }

    public static String MAP_FULL_PT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAP_FULL_PT;
        }
        return Alert_EN.MAP_FULL_PT;
    }

    public static String SEND_INVITE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SEND_INVITE;
        }
        return Alert_EN.SEND_INVITE;
    }

    public static String CAN_NOT_INVITE_PARTY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SEND_INVITE;
        }
        return Alert_EN.SEND_INVITE;
    }

    public static String LIMIT_TIME_20(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TIME_20;
        }
        return Alert_EN.LIMIT_TIME_20;
    }

    public static String GIFT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GIFT;
        }
        return Alert_EN.GIFT;
    }

    public static String DEN_BU(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DEN_BU;
        }
        return Alert_EN.DEN_BU;
    }

    public static String CLEAR_TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLEAR_TASK;
        }
        return Alert_EN.CLEAR_TASK;
    }

    public static String LIMIT_LV_CLEAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_LV_CLEAR;
        }
        return Alert_EN.LIMIT_LV_CLEAR;
    }

    public static String BONUS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BONUS;
        }
        return Alert_EN.BONUS;
    }

    public static String NOT_REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_REWARD;
        }
        return Alert_EN.NOT_REWARD;
    }

    public static String NOT_USE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_USE;
        }
        return Alert_EN.NOT_USE;
    }

    public static String NOT_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_GOLD;
        }
        return Alert_EN.NOT_GOLD;
    }

    public static String NOT_INPUT_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_INPUT_GOLD;
        }
        return Alert_EN.NOT_INPUT_GOLD;
    }

    public static String TITLE_INPUT_CARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TITLE_INPUT_CARD;
        }
        return Alert_EN.TITLE_INPUT_CARD;
    }

    public static String NPC_NOT_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NPC_NOT_GOLD;
        }
        return Alert_EN.NPC_NOT_GOLD;
    }

    public static String DRAGONBALL_7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DRAGONBALL_7;
        }
        return Alert_EN.DRAGONBALL_7;
    }

    public static String ITEM_UPGRADE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_UPGRADE;
        }
        return Alert_EN.ITEM_UPGRADE;
    }

    public static String ITEM_NULL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_NULL;
        }
        return Alert_EN.ITEM_NULL;
    }

    public static String TASK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK;
        }
        return Alert_EN.TASK;
    }

    public static String TASK1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK1;
        }
        return Alert_EN.TASK1;
    }

    public static String NOTE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOTE;
        }
        return Alert_EN.NOTE;
    }

    public static String GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GOLD;
        }
        return Alert_EN.GOLD;
    }

    public static String YEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YEN;
        }
        return Alert_EN.YEN;
    }

    public static String ITEM_MAP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_MAP;
        }
        return Alert_EN.ITEM_MAP;
    }

    public static String HELP_MAP1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP1;
        }
        return Alert_EN.HELP_MAP1;
    }

    public static String HELP_MAP2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP2;
        }
        return Alert_EN.HELP_MAP2;
    }

    public static String HELP_MAP3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP3;
        }
        return Alert_EN.HELP_MAP3;
    }

    public static String HELP_MAP4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP4;
        }
        return Alert_EN.HELP_MAP4;
    }

    public static String HELP_MAP6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP6;
        }
        return Alert_EN.HELP_MAP6;
    }

    public static String HELP_MAP7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP7;
        }
        return Alert_EN.HELP_MAP7;
    }

    public static String HELP_MAP8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP8;
        }
        return Alert_EN.HELP_MAP8;
    }

    public static String HELP_MAP9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP9;
        }
        return Alert_EN.HELP_MAP9;
    }

    public static String HELP_MAP10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP10;
        }
        return Alert_EN.HELP_MAP10;
    }

    public static String HELP_MAP11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MAP11;
        }
        return Alert_EN.HELP_MAP11;
    }

    public static String REWARD_UPLEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.REWARD_UPLEVEL;
        }
        return Alert_EN.REWARD_UPLEVEL;
    }

    public static String IN_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.IN_CLAN;
        }
        return Alert_EN.IN_CLAN;
    }

    public static String MONEY_CHAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MONEY_CHAT;
        }
        return Alert_EN.MONEY_CHAT;
    }

    public static String MIN_PER_UP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MIN_PER_UP;
        }
        return Alert_EN.MIN_PER_UP;
    }

    public static String YOU_AND(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_AND;
        }
        return Alert_EN.YOU_AND;
    }

    public static String FRIENDED(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FRIENDED;
        }
        return Alert_EN.FRIENDED;
    }

    public static String CAREFUL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CAREFUL;
        }
        return Alert_EN.CAREFUL;
    }

    public static String FULL_BAG_TRADE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FULL_BAG_TRADE;
        }
        return Alert_EN.FULL_BAG_TRADE;
    }

    public static String FULL_BAG_TRADE1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FULL_BAG_TRADE1;
        }
        return Alert_EN.FULL_BAG_TRADE1;
    }

    public static String KILL1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.KILL1;
        }
        return Alert_EN.KILL1;
    }

    public static String KILL2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.KILL2;
        }
        return Alert_EN.KILL2;
    }

    public static String ACCUMULATION(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ACCUMULATION;
        }
        return Alert_EN.ACCUMULATION;
    }

    public static String REWARD1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.REWARD1;
        }
        return Alert_EN.REWARD1;
    }

    public static String TOP_DAI_GIA(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_DAI_GIA;
        }
        return Alert_EN.TOP_DAI_GIA;
    }

    public static String LUCKY_DRAW(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VONG_XOAY_MAY_MAN;
        }
        return Alert_EN.VONG_XOAY_MAY_MAN;
    }

    public static String TOP_HANG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_HANG;
        }
        return Alert_EN.TOP_HANG;
    }

    public static String TOP_TAI_NANG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_TAI_NANG;
        }
        return Alert_EN.TOP_TAI_NANG;
    }

    public static String TOP_DE_NHAT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_DE_NHAT;
        }
        return Alert_EN.TOP_TAI_NANG;
    }

    public static String TOP_LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_LEVEL;
        }
        return Alert_EN.TOP_LEVEL;
    }

    public static String TOP_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TOP_CLAN;
        }
        return Alert_EN.TOP_CLAN;
    }

    public static String CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN;
        }
        return Alert_EN.CLAN;
    }

    public static String LEVEL(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL;
        }
        return Alert_EN.LEVEL;
    }

    public static String BY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BY;
        }
        return Alert_EN.BY;
    }

    public static String CLAN_MAIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_MAIN;
        }
        return Alert_EN.CLAN_MAIN;
    }

    public static String UPDATE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.UPDATE;
        }
        return Alert_EN.UPDATE;
    }

    public static String ACCOUNT_LOCK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ACCOUNT_LOCK;
        }
        return Alert_EN.ACCOUNT_LOCK;
    }

    public static String FULL_PLAYER(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FULL_PLAYER;
        }
        return Alert_EN.FULL_PLAYER;
    }

    public static String ACCOUNT_CHECK1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ACCOUNT_CHECK1;
        }
        return Alert_EN.ACCOUNT_CHECK1;
    }

    public static String ACCOUNT_CHECK2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ACCOUNT_CHECK2;
        }
        return Alert_EN.ACCOUNT_CHECK2;
    }

    public static String INPUT_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_GOLD;
        }
        return Alert_EN.INPUT_GOLD;
    }

    public static String REG_FINISH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.REG_FINISH;
        }
        return Alert_EN.REG_FINISH;
    }

    public static String PAUSE_REG1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PAUSE_REG1;
        }
        return Alert_EN.PAUSE_REG1;
    }

    public static String LIMIT_OUT_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_OUT_CLAN;
        }
        return Alert_EN.LIMIT_OUT_CLAN;
    }

    public static String SERVER_STOP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SERVER_STOP;
        }
        return Alert_EN.SERVER_STOP;
    }

    public static String OPEN_CT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_CT;
        }
        return Alert_EN.OPEN_CT;
    }

    public static String WAIT_TRU(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WAIT_TRU;
        }
        return Alert_EN.WAIT_TRU;
    }

    public static String GET_REWARD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GET_REWARD;
        }
        return Alert_EN.GET_REWARD;
    }

    public static String ADD_GOLD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ADD_GOLD;
        }
        return Alert_EN.ADD_GOLD;
    }

    public static String TASK_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_1;
        }
        return Alert_EN.TASK_1;
    }

    public static String TASK_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_2;
        }
        return Alert_EN.TASK_2;
    }

    public static String TASK_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_3;
        }
        return Alert_EN.TASK_3;
    }

    public static String TASK_4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_4;
        }
        return Alert_EN.TASK_4;
    }

    public static String TASK_5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_5;
        }
        return Alert_EN.TASK_5;
    }

    public static String TASK_6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_6;
        }
        return Alert_EN.TASK_6;
    }

    public static String TASK_7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_7;
        }
        return Alert_EN.TASK_7;
    }

    public static String TASK_8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_8;
        }
        return Alert_EN.TASK_8;
    }

    public static String TASK_9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_9;
        }
        return Alert_EN.TASK_9;
    }

    public static String TASK_10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_10;
        }
        return Alert_EN.TASK_10;
    }

    public static String TASK_11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_11;
        }
        return Alert_EN.TASK_11;
    }

    public static String TASK_12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_12;
        }
        return Alert_EN.TASK_12;
    }

    public static String TASK_13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_13;
        }
        return Alert_EN.TASK_13;
    }

    public static String TASK_14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_14;
        }
        return Alert_EN.TASK_14;
    }

    public static String TASK_15(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_15;
        }
        return Alert_EN.TASK_15;
    }

    public static String TASK_16(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_16;
        }
        return Alert_EN.TASK_16;
    }

    public static String TASK_17(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_17;
        }
        return Alert_EN.TASK_17;
    }

    public static String TASK_18(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_18;
        }
        return Alert_EN.TASK_18;
    }

    public static String TASK_19(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_19;
        }
        return Alert_EN.TASK_19;
    }

    public static String TASK_20(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_20;
        }
        return Alert_EN.TASK_20;
    }

    public static String TASK_21(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_21;
        }
        return Alert_EN.TASK_21;
    }

    public static String TASK_22(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_22;
        }
        return Alert_EN.TASK_22;
    }

    public static String TASK_23(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_23;
        }
        return Alert_EN.TASK_23;
    }

    public static String TASK_24(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_24;
        }
        return Alert_EN.TASK_24;
    }

    public static String TASK_25(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_25;
        }
        return Alert_EN.TASK_25;
    }

    public static String TASK_26(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_26;
        }
        return Alert_EN.TASK_26;
    }

    public static String TASK_27(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_27;
        }
        return Alert_EN.TASK_27;
    }

    public static String TASK_28(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_28;
        }
        return Alert_EN.TASK_28;
    }

    public static String TASK_29(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_29;
        }
        return Alert_EN.TASK_29;
    }

    public static String TASK_30(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_30;
        }
        return Alert_EN.TASK_30;
    }

    public static String TASK_31(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_31;
        }
        return Alert_EN.TASK_31;
    }

    public static String TASK_32(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_32;
        }
        return Alert_EN.TASK_32;
    }

    public static String TASK_33(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_33;
        }
        return Alert_EN.TASK_33;
    }

    public static String TASK_34(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_34;
        }
        return Alert_EN.TASK_34;
    }

    public static String TASK_35(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_35;
        }
        return Alert_EN.TASK_35;
    }

    public static String TASK_36(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_36;
        }
        return Alert_EN.TASK_36;
    }

    public static String TASK_37(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_37;
        }
        return Alert_EN.TASK_37;
    }

    public static String TASK_38(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_38;
        }
        return Alert_EN.TASK_38;
    }

    public static String TASK_39(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_39;
        }
        return Alert_EN.TASK_39;
    }

    public static String TASK_40(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_40;
        }
        return Alert_EN.TASK_40;
    }

    public static String TASK_41(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_41;
        }
        return Alert_EN.TASK_41;
    }

    public static String TASK_42(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_42;
        }
        return Alert_EN.TASK_42;
    }

    public static String TASK_43(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_43;
        }
        return Alert_EN.TASK_43;
    }

    public static String TASK_44(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_44;
        }
        return Alert_EN.TASK_44;
    }

    public static String TASK_45(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_45;
        }
        return Alert_EN.TASK_45;
    }

    public static String TASK_46(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_46;
        }
        return Alert_EN.TASK_46;
    }

    public static String TASK_47(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_47;
        }
        return Alert_EN.TASK_47;
    }

    public static String TASK_48(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_48;
        }
        return Alert_EN.TASK_48;
    }

    public static String TASK_49(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_49;
        }
        return Alert_EN.TASK_49;
    }

    public static String TASK_50(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_50;
        }
        return Alert_EN.TASK_50;
    }

    public static String TASK_51(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_51;
        }
        return Alert_EN.TASK_51;
    }

    public static String TASK_52(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_52;
        }
        return Alert_EN.TASK_52;
    }

    public static String TASK_53(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_53;
        }
        return Alert_EN.TASK_53;
    }

    public static String TASK_54(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_54;
        }
        return Alert_EN.TASK_54;
    }

    public static String TASK_55(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_55;
        }
        return Alert_EN.TASK_55;
    }

    public static String TASK_56(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_56;
        }
        return Alert_EN.TASK_56;
    }

    public static String TASK_57(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_57;
        }
        return Alert_EN.TASK_57;
    }

    public static String TASK_58(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_58;
        }
        return Alert_EN.TASK_58;
    }

    public static String TASK_59(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_59;
        }
        return Alert_EN.TASK_59;
    }

    public static String TASK_60(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_60;
        }
        return Alert_EN.TASK_60;
    }

    public static String TASK_61(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_61;
        }
        return Alert_EN.TASK_61;
    }

    public static String TASK_62(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_62;
        }
        return Alert_EN.TASK_62;
    }

    public static String TASK_63(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_63;
        }
        return Alert_EN.TASK_63;
    }

    public static String TASK_64(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_64;
        }
        return Alert_EN.TASK_64;
    }

    public static String TASK_65(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_65;
        }
        return Alert_EN.TASK_65;
    }

    public static String TASK_66(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_66;
        }
        return Alert_EN.TASK_66;
    }

    public static String[] TASK_DES1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES1;
        }
        return Alert_EN.TASK_DES1;
    }

    public static String[] TASK_DES2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES2;
        }
        return Alert_EN.TASK_DES2;
    }

    public static String[] TASK_DES3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES3;
        }
        return Alert_EN.TASK_DES3;
    }

    public static String[] TASK_DES4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES4;
        }
        return Alert_EN.TASK_DES4;
    }

    public static String[] TASK_DES5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES5;
        }
        return Alert_EN.TASK_DES5;
    }

    public static String[] TASK_DES6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES6;
        }
        return Alert_EN.TASK_DES6;
    }

    public static String[] TASK_DES7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES7;
        }
        return Alert_EN.TASK_DES7;
    }

    public static String[] TASK_DES8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES8;
        }
        return Alert_EN.TASK_DES8;
    }

    public static String[] TASK_DES9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES9;
        }
        return Alert_EN.TASK_DES9;
    }

    public static String[] TASK_DES10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES10;
        }
        return Alert_EN.TASK_DES10;
    }

    public static String[] TASK_DES11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES11;
        }
        return Alert_EN.TASK_DES11;
    }

    public static String[] TASK_DES12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES12;
        }
        return Alert_EN.TASK_DES12;
    }

    public static String[] TASK_DES13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES13;
        }
        return Alert_EN.TASK_DES13;
    }

    public static String[] TASK_DES14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES14;
        }
        return Alert_EN.TASK_DES14;
    }

    public static String[] TASK_DES15(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES15;
        }
        return Alert_EN.TASK_DES15;
    }

    public static String[] TASK_DES16(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES16;
        }
        return Alert_EN.TASK_DES16;
    }

    public static String[] TASK_DES17(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES17;
        }
        return Alert_EN.TASK_DES17;
    }

    public static String[] TASK_DES18(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES18;
        }
        return Alert_EN.TASK_DES18;
    }

    public static String[] TASK_DES19(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES19;
        }
        return Alert_EN.TASK_DES19;
    }

    public static String[] TASK_DES20(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES20;
        }
        return Alert_EN.TASK_DES20;
    }

    public static String[] TASK_DES21(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES21;
        }
        return Alert_EN.TASK_DES21;
    }

    public static String[] TASK_DES22(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES22;
        }
        return Alert_EN.TASK_DES22;
    }

    public static String[] TASK_DES23(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES23;
        }
        return Alert_EN.TASK_DES23;
    }

    public static String[] TASK_DES24(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES24;
        }
        return Alert_EN.TASK_DES24;
    }

    public static String[] TASK_DES25(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES25;
        }
        return Alert_EN.TASK_DES25;
    }

    public static String[] TASK_DES26(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES26;
        }
        return Alert_EN.TASK_DES26;
    }

    public static String[] TASK_DES27(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES27;
        }
        return Alert_EN.TASK_DES27;
    }

    public static String[] TASK_DES28(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES28;
        }
        return Alert_EN.TASK_DES28;
    }

    public static String[] TASK_DES29(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES29;
        }
        return Alert_EN.TASK_DES29;
    }

    public static String[] TASK_DES30(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES30;
        }
        return Alert_EN.TASK_DES30;
    }

    public static String[] TASK_DES31(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES31;
        }
        return Alert_EN.TASK_DES31;
    }

    public static String[] TASK_DES32(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES32;
        }
        return Alert_EN.TASK_DES32;
    }

    public static String[] TASK_DES33(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES33;
        }
        return Alert_EN.TASK_DES33;
    }

    public static String[] TASK_DES34(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES34;
        }
        return Alert_EN.TASK_DES34;
    }

    public static String[] TASK_DES35(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES35;
        }
        return Alert_EN.TASK_DES35;
    }

    public static String[] TASK_DES36(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES36;
        }
        return Alert_EN.TASK_DES36;
    }

    public static String[] TASK_DES37(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES37;
        }
        return Alert_EN.TASK_DES37;
    }

    public static String[] TASK_DES38(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES38;
        }
        return Alert_EN.TASK_DES38;
    }

    public static String[] TASK_DES39(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES39;
        }
        return Alert_EN.TASK_DES39;
    }

    public static String[] TASK_DES40(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES40;
        }
        return Alert_EN.TASK_DES40;
    }

    public static String[] TASK_DES41(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES41;
        }
        return Alert_EN.TASK_DES41;
    }

    public static String[] TASK_DES42(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES42;
        }
        return Alert_EN.TASK_DES42;
    }

    public static String[] TASK_DES43(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES43;
        }
        return Alert_EN.TASK_DES43;
    }

    public static String[] TASK_DES44(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES44;
        }
        return Alert_EN.TASK_DES44;
    }

    public static String[] TASK_DES45(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES45;
        }
        return Alert_EN.TASK_DES45;
    }

    public static String[] TASK_DES46(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES46;
        }
        return Alert_EN.TASK_DES46;
    }

    public static String[] TASK_DES47(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES47;
        }
        return Alert_EN.TASK_DES47;
    }

    public static String[] TASK_DES48(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES48;
        }
        return Alert_EN.TASK_DES48;
    }

    public static String[] TASK_DES49(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES49;
        }
        return Alert_EN.TASK_DES49;
    }

    public static String[] TASK_DES50(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES50;
        }
        return Alert_EN.TASK_DES50;
    }

    public static String[] TASK_DES51(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES51;
        }
        return Alert_EN.TASK_DES51;
    }

    public static String[] TASK_DES52(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES52;
        }
        return Alert_EN.TASK_DES52;
    }

    public static String[] TASK_DES53(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES53;
        }
        return Alert_EN.TASK_DES53;
    }

    public static String[] TASK_DES54(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES54;
        }
        return Alert_EN.TASK_DES54;
    }

    public static String[] TASK_DES55(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES55;
        }
        return Alert_EN.TASK_DES55;
    }

    public static String[] TASK_DES56(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES56;
        }
        return Alert_EN.TASK_DES56;
    }

    public static String[] TASK_DES57(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES57;
        }
        return Alert_EN.TASK_DES57;
    }

    public static String[] TASK_DES58(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES58;
        }
        return Alert_EN.TASK_DES58;
    }

    public static String[] TASK_DES59(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES59;
        }
        return Alert_EN.TASK_DES59;
    }

    public static String[] TASK_DES60(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES60;
        }
        return Alert_EN.TASK_DES60;
    }

    public static String[] TASK_DES61(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES61;
        }
        return Alert_EN.TASK_DES61;
    }

    public static String[] TASK_DES62(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES62;
        }
        return Alert_EN.TASK_DES62;
    }

    public static String[] TASK_DES63(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES63;
        }
        return Alert_EN.TASK_DES63;
    }

    public static String[] TASK_DES64(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES64;
        }
        return Alert_EN.TASK_DES64;
    }

    public static String[] TASK_DES65(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES65;
        }
        return Alert_EN.TASK_DES65;
    }

    public static String[] TASK_DES66(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_DES66;
        }
        return Alert_EN.TASK_DES66;
    }

    public static String TASK_NOTE1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE1;
        }
        return Alert_EN.TASK_NOTE1;
    }

    public static String TASK_NOTE2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE2;
        }
        return Alert_EN.TASK_NOTE2;
    }

    public static String TASK_NOTE3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE3;
        }
        return Alert_EN.TASK_NOTE3;
    }

    public static String TASK_NOTE4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE4;
        }
        return Alert_EN.TASK_NOTE4;
    }

    public static String TASK_NOTE5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE5;
        }
        return Alert_EN.TASK_NOTE5;
    }

    public static String TASK_NOTE6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE6;
        }
        return Alert_EN.TASK_NOTE6;
    }

    public static String TASK_NOTE7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE7;
        }
        return Alert_EN.TASK_NOTE7;
    }

    public static String TASK_NOTE8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE8;
        }
        return Alert_EN.TASK_NOTE8;
    }

    public static String TASK_NOTE9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE9;
        }
        return Alert_EN.TASK_NOTE9;
    }

    public static String TASK_NOTE10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE10;
        }
        return Alert_EN.TASK_NOTE10;
    }

    public static String TASK_NOTE11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE11;
        }
        return Alert_EN.TASK_NOTE11;
    }

    public static String TASK_NOTE12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE12;
        }
        return Alert_EN.TASK_NOTE12;
    }

    public static String TASK_NOTE13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE13;
        }
        return Alert_EN.TASK_NOTE13;
    }

    public static String TASK_NOTE14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE14;
        }
        return Alert_EN.TASK_NOTE14;
    }

    public static String TASK_NOTE15(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE15;
        }
        return Alert_EN.TASK_NOTE15;
    }

    public static String TASK_NOTE16(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE16;
        }
        return Alert_EN.TASK_NOTE16;
    }

    public static String TASK_NOTE17(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE17;
        }
        return Alert_EN.TASK_NOTE17;
    }

    public static String TASK_NOTE18(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE18;
        }
        return Alert_EN.TASK_NOTE18;
    }

    public static String TASK_NOTE19(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE19;
        }
        return Alert_EN.TASK_NOTE19;
    }

    public static String TASK_NOTE20(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_NOTE20;
        }
        return Alert_EN.TASK_NOTE20;
    }

    public static String[][] TASK_ASK1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_ASK1;
        }
        return Alert_EN.TASK_ASK1;
    }

    public static String[][] TASK_ASK2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_ASK2;
        }
        return Alert_EN.TASK_ASK2;
    }

    public static String DOWNLOAD_GAME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DOWNLOAD_GAME;
        }
        return Alert_EN.DOWNLOAD_GAME;
    }

    public static String DOWNLOAD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DOWNLOAD;
        }
        return Alert_EN.DOWNLOAD;
    }

    public static String COMFIRM_DOWNLOAD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COMFIRM_DOWNLOAD;
        }
        return Alert_EN.COMFIRM_DOWNLOAD;
    }

    public static String OPEN_RUONGVT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_RUONGVT;
        }
        return Alert_EN.OPEN_RUONGVT;
    }

    public static String OPEN_RUONGHD(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.OPEN_RUONGHD;
        }
        return Alert_EN.OPEN_RUONGHD;
    }

    public static String NOT_PROTECT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_PROTECT;
        }
        return Alert_EN.NOT_PROTECT;
    }

    public static String FAST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FAST;
        }
        return Alert_EN.FAST;
    }

    public static String CANOT_KHAM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CANOT_KHAM;
        }
        return Alert_EN.CANOT_KHAM;
    }

    public static String NOT_PROTECT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_PROTECT1;
        }
        return Alert_EN.NOT_PROTECT1;
    }

    public static String HAVE_TEST_DUN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_TEST_DUN;
        }
        return Alert_EN.HAVE_TEST_DUN;
    }

    public static String NOT_IN_HERE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_IN_HERE;
        }
        return Alert_EN.NOT_IN_HERE;
    }

    public static String HAVE_TEST_DUN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_TEST_DUN1;
        }
        return Alert_EN.HAVE_TEST_DUN1;
    }

    public static String SEND_INVITE_TEST(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SEND_INVITE_TEST;
        }
        return Alert_EN.SEND_INVITE_TEST;
    }

    public static String ONLY_TEAM_LEADER1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ONLY_TEAM_LEADER1;
        }
        return Alert_EN.ONLY_TEAM_LEADER1;
    }

    public static String ONLY_TEAM_LEADER2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ONLY_TEAM_LEADER2;
        }
        return Alert_EN.ONLY_TEAM_LEADER2;
    }

    public static String TEST_DUN_CLEAR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEST_DUN_CLEAR;
        }
        return Alert_EN.TEST_DUN_CLEAR;
    }

    public static String DUN_110_CLOSE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DUN_110_CLOSE;
        }
        return Alert_EN.DUN_110_CLOSE;
    }

    public static String TEST_DUN_ME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TEST_DUN_ME;
        }
        return Alert_EN.TEST_DUN_ME;
    }

    public static String HEPL_TEST_DUN0(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN0;
        }
        return Alert_EN.HEPL_TEST_DUN0;
    }

    public static String HEPL_TEST_DUN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN1;
        }
        return Alert_EN.HEPL_TEST_DUN1;
    }

    public static String HEPL_TEST_DUN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN2;
        }
        return Alert_EN.HEPL_TEST_DUN2;
    }

    public static String HEPL_TEST_DUN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN3;
        }
        return Alert_EN.HEPL_TEST_DUN3;
    }

    public static String HEPL_TEST_DUN4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN4;
        }
        return Alert_EN.HEPL_TEST_DUN4;
    }

    public static String HEPL_TEST_DUN5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN5;
        }
        return Alert_EN.HEPL_TEST_DUN5;
    }

    public static String HEPL_TEST_DUN6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN6;
        }
        return Alert_EN.HEPL_TEST_DUN6;
    }

    public static String PHAN_THAN_NO_PERMIS(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHAN_THAN_NO_PERMIS;
        }
        return Alert_EN.PHAN_THAN_NO_PERMIS;
    }

    public static String HEPL_TEST_DUN7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN7;
        }
        return Alert_EN.HEPL_TEST_DUN7;
    }

    public static String HEPL_TEST_DUN8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN8;
        }
        return Alert_EN.HEPL_TEST_DUN8;
    }

    public static String HEPL_TEST_DUN9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN9;
        }
        return Alert_EN.HEPL_TEST_DUN9;
    }

    public static String HEPL_TEST_DUN10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN10;
        }
        return Alert_EN.HEPL_TEST_DUN10;
    }

    public static String HEPL_TEST_DUN11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN11;
        }
        return Alert_EN.HEPL_TEST_DUN11;
    }

    public static String HEPL_TEST_DUN12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN12;
        }
        return Alert_EN.HEPL_TEST_DUN12;
    }

    public static String HEPL_TEST_DUN13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN13;
        }
        return Alert_EN.HEPL_TEST_DUN13;
    }

    public static String HEPL_TEST_DUN14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HEPL_TEST_DUN14;
        }
        return Alert_EN.HEPL_TEST_DUN14;
    }

    public static String INPUT_MONEY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INPUT_MONEY;
        }
        return Alert_EN.INPUT_MONEY;
    }

    public static String SAY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY;
        }
        return Alert_EN.SAY;
    }

    public static String NOT_RETURN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_RETURN;
        }
        return Alert_EN.NOT_RETURN;
    }

    public static String END_TEST_DUN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.END_TEST_DUN1;
        }
        return Alert_EN.END_TEST_DUN1;
    }

    public static String END_TEST_DUN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.END_TEST_DUN2;
        }
        return Alert_EN.END_TEST_DUN2;
    }

    public static String END_TEST_DUN3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.END_TEST_DUN3;
        }
        return Alert_EN.END_TEST_DUN3;
    }

    public static String CHANGE_OK(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGE_OK;
        }
        return Alert_EN.CHANGE_OK;
    }

    public static String CHANGE_ERR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CHANGE_ERR;
        }
        return Alert_EN.CHANGE_ERR;
    }

    public static String WIN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.WIN;
        }
        return Alert_EN.WIN;
    }

    public static String PHE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.PHE;
        }
        return Alert_EN.PHE;
    }

    public static String VUOT_AI_ALERT1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT1;
        }
        return Alert_EN.VUOT_AI_ALERT1;
    }

    public static String VUOT_AI_ALERT2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT2;
        }
        return Alert_EN.VUOT_AI_ALERT2;
    }

    public static String VUOT_AI_ALERT3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT3;
        }
        return Alert_EN.VUOT_AI_ALERT3;
    }

    public static String VUOT_AI_ALERT4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT4;
        }
        return Alert_EN.VUOT_AI_ALERT4;
    }

    public static String VUOT_AI_ALERT5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT5;
        }
        return Alert_EN.VUOT_AI_ALERT5;
    }

    public static String VUOT_AI_ALERT6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT6;
        }
        return Alert_EN.VUOT_AI_ALERT6;
    }

    public static String VUOT_AI_ALERT7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT7;
        }
        return Alert_EN.VUOT_AI_ALERT7;
    }

    public static String VUOT_AI_ALERT8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT8;
        }
        return Alert_EN.VUOT_AI_ALERT8;
    }

    public static String VUOT_AI_ALERT9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT9;
        }
        return Alert_EN.VUOT_AI_ALERT9;
    }

    public static String VUOT_AI_ALERT10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT10;
        }
        return Alert_EN.VUOT_AI_ALERT10;
    }

    public static String VUOT_AI_ALERT11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT11;
        }
        return Alert_EN.VUOT_AI_ALERT11;
    }

    public static String VUOT_AI_ALERT12(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT12;
        }
        return Alert_EN.VUOT_AI_ALERT12;
    }

    public static String VUOT_AI_ALERT13(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT13;
        }
        return Alert_EN.VUOT_AI_ALERT13;
    }

    public static String VUOT_AI_ALERT14(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT14;
        }
        return Alert_EN.VUOT_AI_ALERT14;
    }

    public static String VUOT_AI_ALERT15(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VUOT_AI_ALERT15;
        }
        return Alert_EN.VUOT_AI_ALERT15;
    }

    public static String NOT_ENOUGH_COIN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NOT_ENOUGH_COIN1;
        }
        return Alert_EN.NOT_ENOUGH_COIN1;
    }

    public static String ITEM_ERROR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_ERROR;
        }
        return Alert_EN.ITEM_ERROR;
    }

    public static String ITEM_NOT_OPEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_NOT_OPEN;
        }
        return Alert_EN.ITEM_NOT_OPEN;
    }

    public static String LAM_BANH0(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH0;
        }
        return Alert_EN.LAM_BANH0;
    }

    public static String LAM_PHAO(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_PHAO;
        }
        return Alert_EN.LAM_PHAO;
    }

    public static String LAM_BANH1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH1;
        }
        return Alert_EN.LAM_BANH1;
    }

    public static String LAM_BANH2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH2;
        }
        return Alert_EN.LAM_BANH2;
    }

    public static String LAM_BANH3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH3;
        }
        return Alert_EN.LAM_BANH3;
    }

    public static String LAM_BANH4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH4;
        }
        return Alert_EN.LAM_BANH4;
    }

    public static String LAM_BANH5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH5;
        }
        return Alert_EN.LAM_BANH5;
    }

    public static String LAM_BANH6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH6;
        }
        return Alert_EN.LAM_BANH6;
    }

    public static String LAM_BANH7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH7;
        }
        return Alert_EN.LAM_BANH7;
    }

    public static String LAM_BANH8(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH8;
        }
        return Alert_EN.LAM_BANH8;
    }

    public static String LAM_BANH9(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH9;
        }
        return Alert_EN.LAM_BANH9;
    }

    public static String LAM_BANH10(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH10;
        }
        return Alert_EN.LAM_BANH10;
    }

    public static String LAM_BANH11(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LAM_BANH11;
        }
        return Alert_EN.LAM_BANH11;
    }

    public static String LONG_DEN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LONG_DEN1;
        }
        return Alert_EN.LONG_DEN1;
    }

    public static String LONG_DEN2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LONG_DEN2;
        }
        return Alert_EN.LONG_DEN2;
    }

    public static String LIMIT_TRUNGTHU1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TRUNGTHU1;
        }
        return Alert_EN.LIMIT_TRUNGTHU1;
    }

    public static String LIMIT_TRUNGTHU2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIMIT_TRUNGTHU2;
        }
        return Alert_EN.LIMIT_TRUNGTHU2;
    }

    public static String YOU_GET1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.YOU_GET1;
        }
        return Alert_EN.YOU_GET1;
    }

    public static String RUONG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.RUONG;
        }
        return Alert_EN.RUONG;
    }

    public static String TASK_GIOITHIEU(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU;
        }
        return Alert_EN.TASK_GIOITHIEU;
    }

    public static String TASK_GIOITHIEU_LIMIT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_LIMIT;
        }
        return Alert_EN.TASK_GIOITHIEU_LIMIT;
    }

    public static String TASK_GIOITHIEU_SAY(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_SAY;
        }
        return Alert_EN.TASK_GIOITHIEU_SAY;
    }

    public static String TASK_GIOITHIEU_HELP(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP;
    }

    public static String TASK_GIOITHIEU_HELP1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP1;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP1;
    }

    public static String TASK_GIOITHIEU_HELP2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP2;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP2;
    }

    public static String TASK_GIOITHIEU_HELP3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP3;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP3;
    }

    public static String TASK_GIOITHIEU_HELP4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP4;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP4;
    }

    public static String TASK_GIOITHIEU_HELP5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP5;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP5;
    }

    public static String TASK_GIOITHIEU_HELP6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP6;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP6;
    }

    public static String TASK_GIOITHIEU_HELP7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_GIOITHIEU_HELP7;
        }
        return Alert_EN.TASK_GIOITHIEU_HELP7;
    }

    public static String ALERT_THIDAUGT_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_THIDAUGT_1;
        }
        return Alert_EN.ALERT_THIDAUGT_1;
    }

    public static String ALERT_THIDAUGT_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_THIDAUGT_2;
        }
        return Alert_EN.ALERT_THIDAUGT_2;
    }

    public static String ALERT_THIDAUGT_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_THIDAUGT_3;
        }
        return Alert_EN.ALERT_THIDAUGT_3;
    }

    public static String ALERT_THIDAUGT_4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_THIDAUGT_4;
        }
        return Alert_EN.ALERT_THIDAUGT_4;
    }

    public static String ALERT_QUAYSO1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_QUAYSO1;
        }
        return Alert_EN.ALERT_QUAYSO1;
    }

    public static String ALERT_QUAYSO2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_QUAYSO2;
        }
        return Alert_EN.ALERT_QUAYSO2;
    }

    public static String ALERT_QUAYSO3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ALERT_QUAYSO3;
        }
        return Alert_EN.ALERT_QUAYSO3;
    }

    public static String BAG_FULL3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BAG_FULL3;
        }
        return Alert_EN.BAG_FULL3;
    }

    public static String ERROR1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ERROR1;
        }
        return Alert_EN.ERROR1;
    }

    public static String GTC_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GTC_1;
        }
        return Alert_EN.GTC_1;
    }

    public static String GTC_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.GTC_2;
        }
        return Alert_EN.GTC_2;
    }

    public static String VIEW_RESULT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.VIEW_RESULT;
        }
        return Alert_EN.VIEW_RESULT;
    }

    public static String INVITE_GTC1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INVITE_GTC1;
        }
        return Alert_EN.INVITE_GTC1;
    }

    public static String INVITE_GTC2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INVITE_GTC2;
        }
        return Alert_EN.INVITE_GTC2;
    }

    public static String INVITE_GTC3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.INVITE_GTC3;
        }
        return Alert_EN.INVITE_GTC3;
    }

    public static String CLAN_NAME(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_NAME;
        }
        return Alert_EN.CLAN_NAME;
    }

    public static String MAXLOIDAI(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.MAXLOIDAI;
        }
        return Alert_EN.MAXLOIDAI;
    }

    public static String LOIDAI(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LOIDAI;
        }
        return Alert_EN.LOIDAI;
    }

    public static String THANH_TICH(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.THANH_TICH;
        }
        return Alert_EN.THANH_TICH;
    }

    public static String THANH_TICH1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.THANH_TICH1;
        }
        return Alert_EN.THANH_TICH2;
    }

    public static String THANH_TICH2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.THANH_TICH2;
        }
        return Alert_EN.THANH_TICH2;
    }

    public static String HAVE_POINT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HAVE_POINT;
        }
        return Alert_EN.HAVE_POINT;
    }

    public static String DAY_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DAY_1;
        }
        return Alert_EN.DAY_1;
    }

    public static String DAY_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DAY_3;
        }
        return Alert_EN.DAY_3;
    }

    public static String DAY_7(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DAY_7;
        }
        return Alert_EN.DAY_7;
    }

    public static String DAY_15(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.DAY_15;
        }
        return Alert_EN.DAY_15;
    }

    public static String LIEN_DAU1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIEN_DAU1;
        }
        return Alert_EN.LIEN_DAU1;
    }

    public static String LIEN_DAU2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIEN_DAU2;
        }
        return Alert_EN.LIEN_DAU2;
    }

    public static String LIEN_DAU3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIEN_DAU3;
        }
        return Alert_EN.LIEN_DAU3;
    }

    public static String LIEN_DAU4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIEN_DAU4;
        }
        return Alert_EN.LIEN_DAU4;
    }

    public static String LIEN_DAU5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LIEN_DAU5;
        }
        return Alert_EN.LIEN_DAU5;
    }

    public static String TINH_TU1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TINH_TU1;
        }
        return Alert_EN.TINH_TU1;
    }

    public static String TINH_TU2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TINH_TU2;
        }
        return Alert_EN.TINH_TU2;
    }

    public static String TINH_TU3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TINH_TU3;
        }
        return Alert_EN.TINH_TU3;
    }

    public static String TINH_TU4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TINH_TU4;
        }
        return Alert_EN.TINH_TU4;
    }

    public static String LEVEL_ERR(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.LEVEL_ERR;
        }
        return Alert_EN.LEVEL_ERR;
    }

    public static String TASK_TIENNU1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_TIENNU1;
        }
        return Alert_EN.TASK_TIENNU1;
    }

    public static String TASK_TIENNU2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_TIENNU2;
        }
        return Alert_EN.TASK_TIENNU2;
    }

    public static String TASK_TIENNU3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.TASK_TIENNU3;
        }
        return Alert_EN.TASK_TIENNU3;
    }

    public static String COUNT_TASK_SUKIEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_TASK_SUKIEN;
        }
        return Alert_EN.COUNT_TASK_SUKIEN;
    }

    public static String HELP_LIENSV(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_LIENSV;
        }
        return Alert_EN.HELP_LIENSV;
    }

    public static String HELP_SUKIENTIENNU1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_SUKIENTIENNU1;
        }
        return Alert_EN.HELP_SUKIENTIENNU1;
    }

    public static String HELP_SUKIENTIENNU2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_SUKIENTIENNU2;
        }
        return Alert_EN.HELP_SUKIENTIENNU2;
    }

    public static String COUNT_NV_TRE_EM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_NV_TRE_EM;
        }
        return Alert_EN.COUNT_NV_TRE_EM;
    }

    public static String COUNT_NV_TRE_EM1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.COUNT_NV_TRE_EM1;
        }
        return Alert_EN.COUNT_NV_TRE_EM1;
    }

    public static String NV_TIM_TRE_LAC(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.NV_TIM_TRE_LAC;
        }
        return Alert_EN.NV_TIM_TRE_LAC;
    }

    public static String HELP_TIM_TRE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_TIM_TRE;
        }
        return Alert_EN.HELP_TIM_TRE;
    }

    public static String SAY_TRE_EM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_TRE_EM;
        }
        return Alert_EN.SAY_TRE_EM;
    }

    public static String SAY_TRE_EM1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_TRE_EM1;
        }
        return Alert_EN.SAY_TRE_EM1;
    }

    public static String SAY_TRE_EM2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_TRE_EM2;
        }
        return Alert_EN.SAY_TRE_EM2;
    }

    public static String SAY_TRE_EM3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_TRE_EM3;
        }
        return Alert_EN.SAY_TRE_EM3;
    }

    public static String SAY_TRE_EM4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_TRE_EM4;
        }
        return Alert_EN.SAY_TRE_EM4;
    }

    public static String SAY_DIE(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.SAY_DIE;
        }
        return Alert_EN.SAY_DIE;
    }

    public static String POINT_MM(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_MM;
        }
        return Alert_EN.POINT_MM;
    }

    public static String POINT_MM1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_MM1;
        }
        return Alert_EN.POINT_MM1;
    }

    public static String HELP_TRELAC(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_TRELAC;
        }
        return Alert_EN.HELP_TRELAC;
    }

    public static String HELP_MyNuong(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_MyNuong;
        }
        return Alert_EN.HELP_MyNuong;
    }

    public static String HELP_TRELAC1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_TRELAC1;
        }
        return Alert_EN.HELP_TRELAC1;
    }

    public static String HELP_QUESTTET2015(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_NHIEM_VU_TET2015;
        }
        return Alert_EN.HELP_NHIEM_VU_TET2015;
    }

    public static String HELP_MAM_NGU_QUA_TET2015(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_DOI_MAM_NGU_QUA;
        }
        return Alert_EN.HELP_DOI_MAM_NGU_QUA;
    }

    public static String HELP_LAM_BANH_TET2016(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.HELP_LAM_BANH;
        }
        return Alert_EN.HELP_LAM_BANH;
    }

    public static String POINT_LOIDAI(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_LOIDAI;
        }
        return Alert_EN.POINT_LOIDAI;
    }

    public static String POINT_LOIDAI1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.POINT_LOIDAI1;
        }
        return Alert_EN.POINT_LOIDAI1;
    }

    public static String CLAN_NOT_LV(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_NOT_LV;
        }
        return Alert_EN.CLAN_NOT_LV;
    }

    public static String CLAN_NOT_LV1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_NOT_LV1;
        }
        return Alert_EN.CLAN_NOT_LV1;
    }

    public static String CLAN_1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_1;
        }
        return Alert_EN.CLAN_1;
    }

    public static String CLAN_2(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_2;
        }
        return Alert_EN.CLAN_2;
    }

    public static String CLAN_3(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_3;
        }
        return Alert_EN.CLAN_3;
    }

    public static String CLAN_4(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_4;
        }
        return Alert_EN.CLAN_4;
    }

    public static String CLAN_5(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_5;
        }
        return Alert_EN.CLAN_5;
    }

    public static String CLAN_6(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.CLAN_6;
        }
        return Alert_EN.CLAN_6;
    }

    public static String ITEM_CLAN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_CLAN;
        }
        return Alert_EN.ITEM_CLAN;
    }

    public static String ITEM_CLAN1(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_CLAN1;
        }
        return Alert_EN.ITEM_CLAN1;
    }

    public static String BANH_CHUNG(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BANH_CHUNG;
        }
        return Alert_EN.BANH_CHUNG;
    }

    public static String BANH_TET(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.BANH_TET;
        }
        return Alert_EN.BANH_TET;
    }

    public static String ITEM_NOT_SPLIT(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.ITEM_NOT_SPLIT;
        }
        return Alert_EN.ITEM_NOT_SPLIT;
    }

    public static String FEATURE_NOT_OPEN(Session conn) {
        if (conn == null || conn.typeLanguage == GameServer.LANG_VI) {
            return Alert_VN.FEATURE_NOT_OPEN;
        }
        return Alert_EN.FEATURE_NOT_OPEN;
    }
}