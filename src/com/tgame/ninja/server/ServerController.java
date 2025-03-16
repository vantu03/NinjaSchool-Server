package com.tgame.ninja.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tgame.ninja.data.Database;
import com.tgame.ninja.io.*;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.*;
import com.tgame.ninja.real.item.IUseHandler;
import com.tgame.ninja.real.npc.INpcHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class ServerController implements ISessionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerController.class);

   public static int versionMin_java = 148;
    public static int versionMax_java = 220;

    public static int versionMin_android = 148;
    public static int versionMax_android = 220;

    public static int versionMin_android_STORE = 148;
    public static int versionMax_android_STORE = 220;

    public static int versionMin_pc = 148;
    public static int versionMax_pc = 220;

    public static int versionMin_iphone = 148;
    public static int versionMax_iphone = 220;

    public static int versionMin_iphoneAP = 148;
    public static int versionMax_iphoneAP = 220;

    public static int versionMin_winphone = 148;
    public static int versionMax_winphone = 220;


    public static boolean isExit = false;

    public static boolean turnOnExit = false;

    public static boolean isPauseLogin = false;

    public static Vector<NClass> nClasss = new Vector<>();

    public static Hashtable<Integer, Skill> skills = new Hashtable<>();

    public static Vector<ItemTemplate> itemTemplates = new Vector<>();

    public static Vector<NpcTemplate> npcTemplates = new Vector<>();

    public static Vector<MapTemplate> mapTemplates = new Vector<>();

    public static Vector<EffectTemplate> effTemplates = new Vector<>();

    public static Vector<TaskTemplate> taskTemplates = new Vector<>();

    public static Vector<Map> maps = new Vector<>();

    public static Hashtable<Integer, Vector<Map>> ALL_MAP = new Hashtable<>();

    public static Vector<Item> shops = new Vector<>();

    public static Vector<Item> shopNonNams = new Vector<>();

    public static Vector<Item> shopNonNus = new Vector<>();

    public static Vector<Item> shopAoNams = new Vector<>();

    public static Vector<Item> shopAoNus = new Vector<>();

    public static Vector<Item> shopGangTayNams = new Vector<>();

    public static Vector<Item> shopGangTayNus = new Vector<>();

    public static Vector<Item> shopQuanNams = new Vector<>();

    public static Vector<Item> shopQuanNus = new Vector<>();

    public static Vector<Item> shopGiayNams = new Vector<>();

    public static Vector<Item> shopGiayNus = new Vector<>();

    public static Vector<Item> shopLiens = new Vector<>();

    public static Vector<Item> shopNhans = new Vector<>();

    public static Vector<Item> shopNgocBois = new Vector<>();

    public static Vector<Item> shopPhus = new Vector<>();

    public static Vector<Item> shopVuKhis = new Vector<>();

    public static Vector<Item> shopStacks = new Vector<>();

    public static Vector<Item> shopStackLocks = new Vector<>();

    public static Vector<Item> shopGrocerys = new Vector<>();

    public static Vector<Item> shopGroceryLocks = new Vector<>();

    public static Vector<Item> shopStores = new Vector<>();

    public static Vector<Item> shopBooks = new Vector<>();

    public static Vector<Item> shopFashions = new Vector<>();

    public static Vector<Item> shopPhongcus = new Vector<>();

    public static Vector<Item> shopTrangsucs = new Vector<>();

    public static Vector<Item> shopGiatocs = new Vector<>();

    public static Vector<Item> shopNonNams_not_sell = new Vector<>();

    public static Vector<Item> shopNonNus_not_sell = new Vector<>();

    public static Vector<Item> shopAoNams_not_sell = new Vector<>();

    public static Vector<Item> shopAoNus_not_sell = new Vector<>();

    public static Vector<Item> shopGangTayNams_not_sell = new Vector<>();

    public static Vector<Item> shopGangTayNus_not_sell = new Vector<>();

    public static Vector<Item> shopQuanNams_not_sell = new Vector<>();

    public static Vector<Item> shopQuanNus_not_sell = new Vector<>();

    public static Vector<Item> shopGiayNams_not_sell = new Vector<>();

    public static Vector<Item> shopGiayNus_not_sell = new Vector<>();

    public static Vector<Item> shopLiens_not_sell = new Vector<>();

    public static Vector<Item> shopNhans_not_sell = new Vector<>();

    public static Vector<Item> shopNgocBois_not_sell = new Vector<>();

    public static Vector<Item> shopPhus_not_sell = new Vector<>();

    public static Vector<Item> shopVuKhis_not_sell = new Vector<>();

    public static Vector<Item> shopPhongcus_not_sell = new Vector<>();

    public static Vector<Item> shopTrangsucs_not_sell = new Vector<>();

    public static Vector<SkillOptionTemplate> sOptionTemplates = new Vector<>();

    public static Vector<ItemOptionTemplate> iOptionTemplates = new Vector<>();

    public static Vector<Item> itemDefaults = new Vector<>();

    public static byte[] dataArrow = null;

    public static byte[] dataEffect = null;

    public static byte[] dataImage = null;

    public static byte[] dataPart = null;

    public static byte[] dataSkill = null;

    public static int newplayeId = -1;

    public static Vector<Dun> loidaiduns = new Vector<>();

    public static Vector<Dun> loidaidunsClass = new Vector<>();

    public static byte[][] imageTreex1 = null;

    public static byte[][] imageTreex2 = null;

    public static byte[][] imageTreex3 = null;

    public static byte[][] imageTreex4 = null;

    public static byte[][] dataDynamicEff = null;

    public static byte[][] imageDynamicEff1 = null;

    public static byte[][] imageDynamicEff2 = null;

    public static byte[][] imageDynamicEff3 = null;

    public static byte[][] imageDynamicEff4 = null;

    public static byte[][] dataDynamicEffOnPlayer = null;

    public static byte[][] imageDynamicEffOnPlayer1 = null;

    public static byte[][] imageDynamicEffOnPlayer2 = null;

    public static byte[][] imageDynamicEffOnPlayer3 = null;

    public static byte[][] imageDynamicEffOnPlayer4 = null;

    public static byte[][] tasks = {
        { 12, 3, 4, 6, 24, 5, 7, 12 },
        { 12, 4, 4, 4, 4, 4, 12 },
        { 12, -2, -2, 12 },
        { 12, 4, -2, -2, -2, 12 },
        { 12, -2, -2, -2, 12 },
        { 3, -2, -2, 3 },
        { 7, -2, -2, -2, -2, 7 },
        { 12, -2, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 12 },
        { 12, -2, 9, 10, 11, 12 },
        { -1, -2, -2, -2, -1 },
        { -1, -2, -2, -2, -1 },
        { -1, -2, -2, -1 },
        { -1, -2, -2, -2, -2, -1 },
        { 0, -2, 10, 11, 9, 0 },
        { 2, -2, -2, -2, 2 },
        { 1, -2, 14, 15, 16, 1 },
        { 16, -2, -2, -2, 16 },
        { 18, -2, 17, 18 },
        { 18, -2, -2, -2, 18 },
        { 18, -2, -2, 18 },
        { 18, -2, 15, 18 },
        { 23, -2, -2, -2, 23 },
        { 23, -2, -2, 23 },
        { 23, -2, -2, 23 },
        { 23, -2, -2, 23 },
        { 19, -2, -2, -2, 19 },
        { 19, -2, -2, 19 },
        { 19, -2, -2, 19 },
        { 20, -2, -2, -2, 20 },
        { 20, -2, -2, 20 },
        { 20, -2, -2, -2, 20 },
        { 20, -2, -2, -2, 20 },
        { 20, -2, -2, 20 },
        { 22, -2, -2, -2, 22 },
        { 22, -2, -2, 22 },
        { 22, -2, -2, 22 },
        { 22, -2, -2, 22 },
        { 22, -2, -2, -2, 22 },
        { 22, -2, -2, -2, 22 },
        { 22, -2, -2, -2, -2, 22 },
        { 22, -2, -2, -2, -2, 22 },
        { 22, -2, -2, -2, 22 },
        { 22, -2, -2, 22 }
    };

    public static byte[][] mapTasks = {
        { 22, 22, 22, 22, 22, 22, 22, 22 },
        { 22, 22, 22, 22, 22, 22, 22 },
        { 22, -2, 22, 22 },
        { 22, 22, -2, 23, 23, 22 },
        { 22, -2, 21, 23, 22 },
        { 22, -2, 6, 22 },
        { 22, -2, 2, 71, 26, 22 },
        { 22, -2, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22, 22 },
        { 22, -2, 1, 72, 27, 22 },
        { -1, -2, -2, -2, -1 },
        { -1, 28, 4, 46, -1 },
        { -1, -2, -2, -1 },
        { -1, -2, -2, -2, -2, -1 },
        { -1, -2, 72, 27, 1, -1 },
        { -1, -2, 29, 40, -1 },
        { -1, -2, 31, 8, 65, -1 },
        { 65, -2, 63, 47, 65 },
        { 32, -2, 33, 32 },
        { 32, -2, 50, 11, 32 },
        { 32, -2, 63, 32 },
        { 32, -2, 8, 32 },
        { 48, -2, 12, 34, 48 },
        { 48, -2, 35, 48 },
        { 48, -2, 35, 48 },
        { 48, -2, -2, 48 },
        { 10, -2, 13, 52, 10 },
        { 10, -2, 64, 10 },
        { 10, -2, 14, 10 },
        { 38, -2, 14, 15, 38 },
        { 38, -2, 15, 38 },
        { 38, -2, -2, -2, 38 },
        { 38, -2, 16, 68, 38 },
        { 38, -2, 38, 38 },
        { 17, -2, 41, 41, 17 },
        { 17, -2, 42, 17 },
        { 17, -2, 24, 17 },
        { 17, -2, 62, 17 },
        { 17, -2, 44, 44, 17 },
        { 17, -2, 18, 18, 17 },
        { 17, -2, -2, -2, -2, 17 },
        { 17, -2, -2, -2, -2, 17 },
        { 17, -2, -2, -2, 17 },
        { 17, -2, -2, 17 }
    };

    public static Vector<Hashtable<Integer, Frame>> iconFramesZoom = new Vector<>();

    public static Vector<PlayerTemplate> playerTemplates = new Vector<>();

    public static Hashtable<Integer, Player> huPlayers = new Hashtable<>();

    public static Hashtable<String, Player> hunPlayers = new Hashtable<>();

    public static Hashtable<Integer, Player> hpPlayers = new Hashtable<>();

    public static Hashtable<String, Player> hnPlayers = new Hashtable<>();

    public static Hashtable<String, Login> hLogins = new Hashtable<>();

    public static Vector<Clan> clans = new Vector<>();

    public static Vector<String> topCaothu = new Vector<>();

    public static Vector<String> topDaiGia = new Vector<>();

    public static Vector<TopHangDong> topHangdong = new Vector<>();

    public static Vector<TopTainang> topTaiNang = new Vector<>();

    public static Vector<Vector<TopTainang>> topTaiNangClass = new Vector<>();

    public static HashMap<String, SpamCheck> usernameSpamMsg = new HashMap<>();

    public static HashMap<Integer, IUseHandler> hUsableItems = new HashMap<>();

    public static HashMap<Integer, INpcHandler> hNpcMenus = new HashMap<>();

    public static int vData = 0;

    public static int vMap = 0;

    public static int vSkill = 0;

    public static int vItem = 0;

    public static String tmpUserPrefix = "tmpusr";

    public static int[] all = new int[]{ 776, 777 };

    public static int[][][] frameMount = new int[][][]{
        {
            { 3049, 3050 },
            { 3051, 3051, 3052, 3052, 3053, 3053 },
            { 3054 }, { 3055 }, { 3056 },
            { 3049, 3049, 3049, 3050, 3050, 3050 }
        },
        {
            { 3057, 3058 },
            { 3059, 3059, 3060, 3060, 3061, 3061, 3062, 3062 },
            { 3063 }, { 3064 }, { 3065 },
            { 3057, 3057, 3057, 3058, 3058, 3058 }
        }
    };

    static {
        iconFramesZoom.add(new Hashtable<>());
        iconFramesZoom.add(new Hashtable<>());
        iconFramesZoom.add(new Hashtable<>());
        iconFramesZoom.add(new Hashtable<>());
        if (imageTreex1 == null) {
            try {
                int nTree = 20;
                imageTreex1 = new byte[nTree][];
                imageTreex2 = new byte[nTree][];
                imageTreex3 = new byte[nTree][];
                imageTreex4 = new byte[nTree][];
                int nEff = 10;
                dataDynamicEff = new byte[nEff][];
                imageDynamicEff1 = new byte[nEff][];
                imageDynamicEff2 = new byte[nEff][];
                imageDynamicEff3 = new byte[nEff][];
                imageDynamicEff4 = new byte[nEff][];
                for (int i = 0; i < nEff; ++i) {
                    ServerController.imageDynamicEff1[i] = NJUtil.readFileBytes("data/deff/x1/" + i + ".png");
                    ServerController.imageDynamicEff2[i] = NJUtil.readFileBytes("data/deff/x2/" + i + ".png");
                    ServerController.imageDynamicEff3[i] = NJUtil.readFileBytes("data/deff/x3/" + i + ".png");
                    ServerController.imageDynamicEff4[i] = NJUtil.readFileBytes("data/deff/x4/" + i + ".png");
                    ServerController.dataDynamicEff[i] = NJUtil.readFileBytes("data/deff/" + i);
                }
                nEff = 24;
                dataDynamicEffOnPlayer = new byte[nEff][];
                imageDynamicEffOnPlayer1 = new byte[nEff][];
                imageDynamicEffOnPlayer2 = new byte[nEff][];
                imageDynamicEffOnPlayer3 = new byte[nEff][];
                imageDynamicEffOnPlayer4 = new byte[nEff][];
                for (int i = 0; i < nEff; ++i) {
                    ServerController.imageDynamicEffOnPlayer1[i] = NJUtil.readFileBytes("data/efftool/x1/" + i + ".png");
                    ServerController.imageDynamicEffOnPlayer2[i] = NJUtil.readFileBytes("data/efftool/x2/" + i + ".png");
                    ServerController.imageDynamicEffOnPlayer3[i] = NJUtil.readFileBytes("data/efftool/x3/" + i + ".png");
                    ServerController.imageDynamicEffOnPlayer4[i] = NJUtil.readFileBytes("data/efftool/x4/" + i + ".png");
                    ServerController.dataDynamicEffOnPlayer[i] = NJUtil.readFileBytes("data/efftool/" + i);
                }
            } catch (Exception e) {
                LOGGER.error("", e);
            }
        }
    }

    @Override
    public void processMessage(Session conn, Message message) {
        if (GameServer.isServerLocal() && message.command != Cmd.SUB_COMMAND && message.command != Cmd.NOT_LOGIN && message.command != Cmd.NOT_MAP) {
            LOGGER.info(">> Recv message: {}", message.command);
        }
        if (turnOnExit || isExit) {
            NJUtil.sendServer(conn, "Máy chủ bảo trì, vui lòng thoát game để tránh gặp lỗi.");
            return;
        }
        if (SessionManager.instance.size() >= GameServer.limitCCU + 200 || huPlayers.size() >= GameServer.limitCCU) {
            NJUtil.sleep(10000L);
            conn.disconnect("njcontrol stop full");
            return;
        }
        if (conn.userId == -1 && message.command != Cmd.GET_SESSION_ID && message.command != Cmd.NOT_LOGIN && message.command != Cmd.UPDATE_VERSION_OK) {
            conn.disconnect("NGAT KET NOI NJCONTROL 1");
            return;
        }
        if (message.command != Cmd.GET_SESSION_ID && message.command != Cmd.UPDATE_VERSION_OK && conn.key == null) {
            NJUtil.sendDialog(conn, AlertFunction.VERSION_FAIL(conn));
            NJUtil.sleep(3000L);
            conn.disconnect("NGAT KET NOI NJCONTROL 2");
            return;
        }
        try {
            switch (message.command) {
                case Cmd.SUB_COMMAND:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).map.doSubCommand(conn, message);
                    }
                    return;
                case Cmd.NOT_LOGIN:
                    doNotLogin(conn, message);
                    return;
                case Cmd.NOT_MAP:
                    doNotMap(conn, message);
                    return;
                case Cmd.GET_SESSION_ID:
                    doEncrypt(conn);
                    return;
                case Cmd.CHAT_MAP:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChatPlayer(message);
                    }
                    return;
                case Cmd.CHAT_PRIVATE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChatPrivate(message);
                    }
                    return;
                case Cmd.CHAT_SERVER:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChatServer(message);
                    }
                    return;
                case Cmd.CHAT_PARTY:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChatParty(message);
                    }
                    return;
                case Cmd.CHAT_CLAN:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChatClan(message);
                    }
                    return;
                case Cmd.MENU:
                case Cmd.MAP_CHANGE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).map.processMessage(conn, message);
                    }
                    return;
                case Cmd.ME_THROW:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doThrow(message);
                    }
                    return;
                case Cmd.ME_LIVE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doReturn(message);
                    }
                    return;
                case Cmd.ME_BACK:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doBack("njcontrol cmd me_back");
                    }
                    return;
                case Cmd.PLAYER_MOVE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doMove(message);
                    }
                    return;
                case Cmd.ITEM_USE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemUse(message);
                    }
                    return;
                case Cmd.ITEM_USE_CHANGEMAP:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemUseChangeMap(message);
                    }
                    return;
                case Cmd.ITEM_BUY:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemBuy(message);
                    }
                    return;
                case Cmd.ITEM_SALE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemSale(message);
                    }
                    return;
                case Cmd.ITEM_BODY_TO_BAG:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemBodyToBag(message);
                    }
                    return;
                case Cmd.ITEM_BOX_TO_BAG:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemBoxToBag(message);
                    }
                    return;
                case Cmd.ITEM_BAG_TO_BOX:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemBagToBox(message);
                    }
                    return;
                case Cmd.UPPEARL:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doUppearl(message, true);
                    }
                    return;
                case Cmd.UPPEARL_LOCK:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doUppearl(message, false);
                    }
                    return;
                case Cmd.UPGRADE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doUpgrade(message);
                    }
                    return;
                case Cmd.SPLIT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doSplit(message);
                    }
                    return;
                case Cmd.REQUEST_PLAYERS:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doRequestPlayer(message);
                    }
                    return;
                case Cmd.ZONE_CHANGE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doChangeZone(message);
                    }
                    return;
                case Cmd.OPEN_UI_BOX:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doOpenUIBox();
                    }
                    return;
                case Cmd.OPEN_UI_PT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doOpenUIPt();
                    }
                    return;
                case Cmd.OPEN_MENU_ID:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doOpenMenuId(message.readShort());
                    }
                    return;
                case Cmd.OPEN_UI_ZONE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doOpenUIZone();
                    }
                    return;
                case Cmd.OPEN_UI_MENU:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doMenuOpen(message);
                    }
                    return;
                case Cmd.SKILL_SELECT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doSkillSelect(message);
                    }
                    return;
                case Cmd.REQUEST_ITEM_INFO:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).map.doRequestItemInfo(conn, message);
                    }
                    return;
                case Cmd.TASK_GET:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doGetTask(message);
                    }
                    return;
                case Cmd.TEST_INVITE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTestInvite(message);
                    }
                    return;
                case Cmd.TEST_INVITE_ACCEPT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTestInviteAccept(message);
                    }
                    return;
                case Cmd.ADD_CUU_SAT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doAddCuuSat(message);
                    }
                    return;
                case Cmd.USE_SKILL_MY_BUFF:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doUseSkillMyBuff(message);
                    }
                    return;
                case Cmd.PARTY_INVITE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doAddParty(message);
                    }
                    return;
                case Cmd.PARTY_ACCEPT:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doAcceptParty(message);
                    }
                    return;
                case Cmd.PARTY_CANCEL:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCancelParty(message);
                    }
                    return;
                case Cmd.PARTY_OUT:
                    if (huPlayers.get(conn.userId) != null) {
                        if (huPlayers.get(conn.userId).isNotEditParty()) {
                            return;
                        }
                        huPlayers.get(conn.userId).doOutParty();
                    }
                    return;
                case Cmd.OPEN_TEXT_BOX_ID:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doProccessInputText(message);
                    }
                    return;
                case Cmd.VIEW_INFO:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doPlayerInfo(message);
                    }
                    return;
                case Cmd.REQUEST_ITEM_PLAYER:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doRequestItemPlayer(message);
                    }
                    return;
                case Cmd.TEST_DUN_INVITE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTestDunAccept(message);
                    }
                    return;
                case Cmd.TEST_DUN_LIST:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doGoViewTestDun(message);
                    }
                    return;
                case Cmd.SEND_ITEM_TO_AUCTION:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doSendItemToSale(message);
                    }
                    return;
                case Cmd.VIEW_ITEM_AUCTION:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doViewItemAuction(message);
                    }
                    return;
                case Cmd.TEST_GT_INVITE:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTestGTAccept(message);
                    }
                    return;
                case Cmd.OPEN_UI_CONFIRM_POPUP:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doPopUp(message);
                    }
                    return;
                case Cmd.ITEM_MON_TO_BAG:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doItemMonToBag(message);
                    }
                    return;
                case Cmd.LUYEN_THACH:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doLuyenThach(message);
                    }
                    return;
                case Cmd.TINH_LUYEN:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTinhLuyen(message);
                    }
                    return;
                case Cmd.DOI_OPTION:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doDoiOption(message);
                    }
                    return;
                case Cmd.CAT_KEO:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCatkeo(message);
                    }
                    return;
                case Cmd.CONFIRM_USER_PASS:
                    if (huPlayers.get(conn.userId) != null) {
                        doProcessConfirmAcount(conn, message);
                    }
                    return;
                case Cmd.TELEPORT_TRAIN:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doTelePort(message);
                    }
                    return;
                case Cmd.KHAM_NGOC:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doKhamnNgoc(message);
                    }
                    return;
                case Cmd.EFF_DYNAMIC_TOOL:
                    doRequestEffDynamicTool(conn, message);
                    return;
                default: {
                    Player player = huPlayers.get(conn.userId);
                    if (player != null && player.map != null && player.map.getMessageMap() != null) {
                        if (player.timeSpleep != 0) {
                            NJUtil.sleep(player.timeSpleep);
                            player.timeSpleep = 0;
                        }
                        player.timeActive = System.currentTimeMillis();
                        player.map.addMessagePlayer(new NMessage(conn, message));
                        player.map.notifyMap();
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    @Override
    public void onDisconnected(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player != null) {
                int mapSave = -1;
                try {
                    if (!player.isSetTimeOnline) {
                        player.timeOnline += (int) (System.currentTimeMillis() - player.login_date);
                    }
                    removePlayer(conn.userId, conn.username, player.playerId, player.name);
                    if (player.map != null && player.map.giatocchien != null) {
                        player.map.giatocchien.namePlayLefts.remove(player.name);
                        player.map.giatocchien.namePlayRights.remove(player.name);
                    }
                    if (player.clan != null) {
                        try {
                            for (int i = 0; i < player.clan.members.size(); ++i) {
                                if (player.clan.members.get(i).name.equals(player.name)) {
                                    player.clan.members.get(i).isOnline = false;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    player.clearData(true);
                    if (player.party != null) {
                        player.party.out(player);
                    }
                    if (player.map != null && player.map.mapId != -1) {
                        player.map.playerOuts.add(player);
                        mapSave = player.map.template.mapTemplateId;
                        if (player.status == Player.ME_DIE) {
                            mapSave = -1;
                        }
                        if (player.map.template.isMapChienTruong()) {
                            Map.playerLefts.remove(player);
                            Map.playerRights.remove(player);
                        }
                        if (MixedArena.isMapArena(player.map.template.mapTemplateId)) {
                            mapSave = MixedArena.leave(player);
                        }
                    }
                    if (player.name != null && !player.name.isEmpty()) {
                        player.savezaLog("Thoat game");
                    }
                    player.saveRmsAll();
                    if (player.myskill != null && player.myskill.template != null) {
                        player.saveRms("CSkill", new byte[]{ (byte) player.myskill.template.skillTemplateId }, true);
                    }
                } catch (Exception e2) {
                    LOGGER.error("Error onDisconnect NJCONTROL");
                }
                int mSave = mapSave;
                if (player.isLockChat) {
                    Database.saveLogAll(player.name, Player.getInfoFromVectorString(player.infoChat), "logchat");
                    player.isLockChat = false;
                }
                Database.savePlayer(player, mSave);
                player.isDisonnect = true;
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    @Override
    public void doLogout(Session conn) {
        try {
            conn.disconnect("NGAT KET NOI NJCONTROL 3");
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    @Override
    public void doEncrypt(Session conn) {
        try {
            byte[] key = { 0 };
            NJUtil.random.nextBytes(key);
            Message message = new Message(Cmd.GET_SESSION_ID);
            message.dos.writeByte(key.length);
            message.dos.write(key);
            //noinspection ConstantConditions
            for (int i = 0; i < key.length - 1; ++i) {
                int n = i + 1;
                key[n] ^= key[i];
            }
            conn.setKey(key);
            conn.sendKey(message);
            message.cleanup();
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    @Override
    public void doLogin(Session conn, Message message) throws IOException {
        try {
            if (isExit || GameServer.isDailyMaintenance || (isPauseLogin && conn.type_admin < Player.ROLE_GM)) {
                NJUtil.sendDialog(conn, AlertFunction.SERVER_PAUSE(conn));
                conn.disconnect("server pause");
                return;
            }
            String username = null;
            String password = null;
            String ver = null;
            String key = "";
            try {
                username = message.readUTF().toLowerCase();
                key = username + conn.getClientAddress();
                Login login = hLogins.get(key);
                if (login != null && login.count >= 10) {
                    long timeWait = (login.timeConnect + login.timeLock - System.currentTimeMillis()) / 1000L;
                    NJUtil.sendDialog(conn, "Bạn đã đăng nhập thất bại quá nhiều lần, hãy thử lại sau " + timeWait + "  giây nữa.");
                    NJUtil.sleep(3000L);
                    conn.disconnect("too many attempt");
                    return;
                }
                password = message.readUTF();
                ver = message.readUTF();
            } catch (Exception e) {
                conn.disconnect("login error");
                khoaLogin(key);
            }
            String imei = null;
            String clientNumber = "";
            try {
                imei = message.readUTF();
            } catch (Exception e) {
            }
            try {
                message.readUTF(); // namepackage
            } catch (Exception e) {
            }
            try {
                clientNumber = message.readUTF().toLowerCase();
            } catch (Exception e) {
            }
            byte idserver = 0;
            try {
                idserver = message.readByte();
            } catch (Exception e) {
            }
            if (username == null || password == null || ver == null) {
                return;
            }
            try {
                conn.version = Integer.parseInt(ver.replace(".", "").replaceAll("[^0-9]", ""));
            } catch (Exception e) {
                conn.disconnect("version error");
                khoaLogin(key);
            }
            int versionMin = versionMin_java;
            int versionMax = versionMax_java;
            if (conn.clientType == GameServer.CLIENT_ANDROID) {
                versionMin = versionMin_android;
                versionMax = versionMax_android;
            } else if (conn.clientType == GameServer.CLIENT_ANDROID_STORE) {
                versionMin = versionMin_android_STORE;
                versionMax = versionMax_android_STORE;
            } else if (conn.clientType == GameServer.CLIENT_IPHONE) {
                versionMin = versionMin_iphone;
                versionMax = versionMax_iphone;
            } else if (conn.clientType == GameServer.CLIENT_PC) {
                versionMin = versionMin_pc;
                versionMax = versionMax_pc;
            } else if (conn.clientType == GameServer.CLIENT_IPHONEAP) {
                versionMin = versionMin_iphoneAP;
                versionMax = versionMin_iphoneAP;
            } else if (conn.clientType == GameServer.CLIENT_WINPHONE) {
                versionMin = versionMin_winphone;
                versionMax = versionMax_winphone;
            }
            if (conn.version < versionMin) {
                NJUtil.sendAlertOpenWeb(conn, AlertFunction.UPDATE(conn), AlertFunction.NO(conn), AlertFunction.URL_DOWNLOAD(conn), NJUtil.replace(AlertFunction.VERSION_FAIL(conn), NJUtil.formatVersion(versionMax)));
                return;
            }
            if ((!NJUtil.isValidUserName(username) && !username.equals("-1") && !username.startsWith(tmpUserPrefix)) || !NJUtil.isAlphaNumeric(password)) {
                NJUtil.sendDialog(conn, AlertFunction.USER_FAIL(conn));
                return;
            }
            conn.idserver = idserver;
            conn.username = username;
            conn.pwd = password;
            conn.imei = imei;
            conn.clientNumber = clientNumber;
            int userId;
            byte typeAdmin = 0;
            if (username.equals("-1")) {
                if (!GameServer.quickPlay) {
                    NJUtil.sendDialog(conn, "Chức năng chơi mới đang tạm đóng.");
                    return;
                }
                try {
                    JsonObject jsonObject = new Gson().fromJson(UserServer.createTemp(), JsonObject.class);
                    if (jsonObject.get("error").getAsBoolean()) {
                        NJUtil.sendDialog(conn, jsonObject.get("message").getAsString());
                        return;
                    }
                    JsonObject data = jsonObject.get("data").getAsJsonObject();
                    userId = data.get("id").getAsInt();
                    username = data.get("username").getAsString();
                    password = data.get("password").getAsString();
                    sendMessageUserInput(conn, username, password);
                } catch (Exception e) {
                    NJUtil.sendDialog(conn, AlertFunction.ERROR(conn));
                    return;
                }
            } else {
                SpamCheck ipspam = usernameSpamMsg.get(username.toLowerCase());
                if (ipspam != null) {
                    long time = ipspam.getTimeLock();
                    if (time > 0L) {
                        NJUtil.sendDialog(conn, "Tài khoản của bạn bị khoá " + time + "s do có dấu hiệu phá hoại.");
                        NJUtil.sleep(10000L);
                        conn.disconnect("temporarily locked spam");
                        return;
                    }
                }
                if (Database.connPool.getIdleCount() <= 0 || Database.connPoolWeb.getIdleCount() <= 0) {
                    NJUtil.sendDialog(conn, "Máy chủ quá tải, vui lòng thử lại sau ít phút.");
                    NJUtil.sleep(5000L);
                    conn.disconnect("qua tai pool");
                    return;
                }
                LoginData loginData = Database.loginWeb(username, password);
                if (loginData.id < 0) {
                    NJUtil.sendDialog(conn, AlertFunction.LOGIN_FAIL(conn));
                    return;
                }
                if (loginData.ban) {
                    NJUtil.sendDialog(conn, AlertFunction.USER_LOCK(conn));
                    return;
                }
                if (!loginData.isActive) {
                    NJUtil.sendDialog(conn, AlertFunction.USER_ACTIVE(conn));
                    return;
                }
                userId = loginData.id;
                username = loginData.username;
                typeAdmin = loginData.typeAdmin;
            }
            if (userId < 0) {
                NJUtil.sendDialog(conn, "Đăng nhập thất bại, vui lòng thử lại sau.");
                return;
            }
            if (conn.userId != -1) {
                if (conn.isConnected()) {
                    conn.disconnect("loi dang nhap");
                }
                return;
            }
            Player player = huPlayers.get(userId);
            if (player != null) {
                Session connOld = player.getSession();
                if (System.currentTimeMillis() - connOld.connectTime > 20000L) {
                    NJUtil.sendDialog(connOld, AlertFunction.LOGIN_DIFFERENT(connOld));
                }
                NJUtil.sleep(3000L);
                if (connOld.isConnected()) {
                    connOld.disconnect("loi dang nhap");
                }
                NJUtil.sendDialog(conn, AlertFunction.LOGIN_DOUBLE(conn));
                NJUtil.sleep(3000L);
                if (conn.isConnected()) {
                    conn.disconnect("loi dang nhap");
                }
                return;
            }
            if (!key.isEmpty() && hLogins.get(key) != null) {
                hLogins.remove(key);
            }
            message = NJUtil.messageNotMap(Cmd.UPDATE_VERSION);
            message.writeByte(vData);
            message.writeByte(vMap);
            message.writeByte(vSkill);
            message.writeByte(vItem);
            message.writeByte(ItemTemplate.HEAD_JUMP.length);
            for (int i = 0; i < ItemTemplate.HEAD_JUMP.length; ++i) {
                int[] arr = ItemTemplate.HEAD_JUMP[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            for (int i = 0; i < ItemTemplate.HEAD_NORMAL.length; ++i) {
                int[] arr = ItemTemplate.HEAD_NORMAL[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            for (int i = 0; i < ItemTemplate.HEAD_BOC_DAU.length; ++i) {
                int[] arr = ItemTemplate.HEAD_NORMAL[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            message.writeByte(ItemTemplate.LEG.length);
            for (int i = 0; i < ItemTemplate.LEG.length; ++i) {
                message.writeShort(ItemTemplate.LEG[i]);
            }
            message.writeByte(ItemTemplate.BODY_JUMP.length);
            for (int i = 0; i < ItemTemplate.BODY_JUMP.length; ++i) {
                int[] arr = ItemTemplate.BODY_JUMP[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            for (int i = 0; i < ItemTemplate.BODY_NORMAL.length; ++i) {
                int[] arr = ItemTemplate.BODY_NORMAL[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            for (int i = 0; i < ItemTemplate.BODY_BOC_DAU.length; ++i) {
                int[] arr = ItemTemplate.BODY_BOC_DAU[i];
                message.writeByte(arr.length);
                for (int j : arr) {
                    message.writeShort(j);
                }
            }
            message.writeByte(all.length);
            for (int j = 0; j < all.length; ++j) {
                message.writeShort(all[j]);
                for (int l = 0; l < 6; ++l) {
                    message.writeByte(frameMount[j][l].length);
                    for (int m = 0; m < frameMount[j][l].length; ++m) {
                        message.writeShort(frameMount[j][l][m]);
                    }
                }
            }
            NJUtil.sendMessage(conn, message);
            conn.userId = userId;
            conn.type_admin = typeAdmin;
            conn.username = username;
            conn.pwd = password;
            conn.idserver = idserver;
            Thread.currentThread().setName("User Session " + username + " " + conn.getClientAddress());
        } catch (EOFException e) {
            LOGGER.error("Username: {} Command: {} Client: {},{} User: {},{}", conn.username, message.command, conn.clientType, conn.typeHD, conn.userId, conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    public void doProcessConfirmAcount(Session conn, Message msg) {
        try {
            if (conn.userId == -1 || !conn.username.startsWith(tmpUserPrefix)) {
                return;
            }
            Player p = hunPlayers.get(conn.username);
            if (p == null || p.level < 3) {
                return;
            }
            String username = msg.readUTF().toLowerCase();
            String password = msg.readUTF().toLowerCase();
            String bindInfo = msg.readUTF().toLowerCase();
            int type = NJUtil.isEmail(bindInfo) ? 0 : (NJUtil.isPhone(bindInfo) ? 1 : -1);
            if (type == -1) {
                NJUtil.sendDialog(conn, AlertFunction.EMAIL_PHONE_FAIL(conn));
                return;
            }
            if (type == 1) {
                NJUtil.sendDialog(conn, "Hiện tại chỉ có thể đăng ký tài khoản bằng email.");
                return;
            }
            if (username.length() < 6 || username.length() > 16) {
                NJUtil.sendDialog(conn, AlertFunction.USER_FAIL_1(conn));
                return;
            }
            if (!NJUtil.isAlphaNumeric(username)) {
                NJUtil.sendDialog(conn, AlertFunction.USER_FAIL_2(conn));
                return;
            }
            if (password.length() < 8 || !NJUtil.isAlphaNumeric(password)) {
                NJUtil.sendDialog(conn, AlertFunction.PASS_FAIL(conn));
                return;
            }
            JsonObject jsonObject = new Gson().fromJson(UserServer.registerTemp(conn.username, username, password, type, bindInfo), JsonObject.class);
            if (jsonObject.get("error").getAsBoolean()) {
                NJUtil.sendDialog(conn, jsonObject.get("message").getAsString());
                return;
            }
            hunPlayers.remove(conn.username);
            hunPlayers.put(username, p);
            conn.username = username;
            conn.pwd = password;
            Database.saveLogAll(huPlayers.get(conn.userId).name, "xac thuc tai khoan: " + conn.username + " voi ten tk moi: " + username + " pass: " + password + " bind_info: " + bindInfo, "xacthuc");
            sendMessageUserInput(conn, username, password);
            NJUtil.sendDialog(conn, "Đã xác thực thành công tài khoản " + username + " với mật khẩu " + password);
        } catch (Exception e) {
            NJUtil.sendDialog(conn, AlertFunction.ERROR(conn));
            LOGGER.error("", e);
        }
    }

    public void sendMessageUserInput(Session conn, String uname, String pass) {
        try {
            Message message = new Message(Cmd.CONFIRM_USER_PASS);
            message.writeUTF(uname);
            message.writeUTF(pass);
            conn.sendMessage(message);
        } catch (Exception e) {
        }
    }

    private void doActive(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            String codeSecure;
            try {
                codeSecure = String.valueOf(message.readInt());
            } catch (Exception e2) {
                return;
            }
            if (codeSecure.length() == 6 && (player.codeSecure == null || player.codeSecure.isEmpty())) {
                if (player.getYen() < 10000) {
                    NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_4(conn));
                    return;
                }
                player.sendUpdateCoinLockYen(-10000);
                player.codeSecure = codeSecure;
                player.meLoadActive(2);
                NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_2(conn));
            } else {
                player.updateHackPass2();
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doClanChangeAlert(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || (!player.clan.isMain(player.name) && !player.clan.isAssist(player.name))) {
                return;
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            String alert = message.readUTF();
            if (alert.length() > 220) {
                alert = alert.substring(220);
            }
            player.clan.alert = AlertFunction.CLAN_ALERT(conn) + player.name + "\n" + alert;
            message = NJUtil.messageNotMap(Cmd.CLAN_CHANGE_ALERT);
            message.writeUTF(player.clan.alert);
            NJUtil.sendMessage(conn, message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doClanChangeType(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            String memName = "";
            int typeClan = 0;
            try {
                memName = message.readUTF();
                typeClan = message.readByte();
            } catch (Exception e) {
            }
            if (player == null || player.clan == null || !player.clan.isMain(player.name) || (typeClan != 2 && typeClan != 3 && typeClan != 0)) {
                return;
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            Member mem = player.clan.getMember(memName);
            if (mem == null || mem.typeClan == 4) {
                return;
            }
            if (typeClan == 0) {
                if (mem.typeClan == 3 || mem.typeClan == 2) {
                    String str = (mem.typeClan == 3) ? Alert_VN.CLANS[0] : Alert_VN.CLANS[1];
                    if (player.clan.assist_name.equals(mem.name)) {
                        player.clan.assist_name = "";
                    } else if (player.clan.elder1_name.equals(mem.name)) {
                        player.clan.elder1_name = "";
                    } else if (player.clan.elder2_name.equals(mem.name)) {
                        player.clan.elder2_name = "";
                    } else if (player.clan.elder3_name.equals(mem.name)) {
                        player.clan.elder3_name = "";
                    } else if (player.clan.elder4_name.equals(mem.name)) {
                        player.clan.elder4_name = "";
                    } else if (player.clan.elder5_name.equals(mem.name)) {
                        player.clan.elder5_name = "";
                    }
                    mem.typeClan = 0;
                    Player p = hnPlayers.get(memName);
                    if (p != null) {
                        Player.sendUpdateClan(p, mem.typeClan);
                        String[] strs = {
                            str + " " + p.name + Alert_VN.CLAN_CLEAR,
                            str + " " + p.name + Alert_EN.CLAN_CLEAR
                        };
                        player.clan.sendAlert(strs, null);
                    }
                    doRequestClanMember(conn);
                }
            } else {
                if (mem.typeClan == 3) {
                    NJUtil.sendServer(conn, AlertFunction.HAVE_ASSIST1(conn));
                    return;
                }
                if (mem.typeClan == 2) {
                    NJUtil.sendServer(conn, AlertFunction.HAVE_ELDER(conn));
                    return;
                }
                if (typeClan == 3 && !player.clan.assist_name.isEmpty()) {
                    NJUtil.sendServer(conn, AlertFunction.HAVE_ASSIST2(conn));
                    return;
                }
                if (typeClan == 2 && !player.clan.elder1_name.isEmpty() && !player.clan.elder2_name.isEmpty() && !player.clan.elder3_name.isEmpty() && !player.clan.elder4_name.isEmpty() && !player.clan.elder5_name.isEmpty()) {
                    NJUtil.sendServer(conn, AlertFunction.MAX_ELDER(conn));
                    return;
                }
                if (typeClan == 3) {
                    player.clan.assist_name = memName;
                    mem.typeClan = 3;
                } else //noinspection ConstantConditions
                    if (typeClan == 2) {
                        if (player.clan.elder1_name.isEmpty()) {
                            player.clan.elder1_name = memName;
                            mem.typeClan = 2;
                        } else if (player.clan.elder2_name.isEmpty()) {
                            player.clan.elder2_name = memName;
                            mem.typeClan = 2;
                        } else if (player.clan.elder3_name.isEmpty()) {
                            player.clan.elder3_name = memName;
                            mem.typeClan = 2;
                        } else if (player.clan.elder4_name.isEmpty()) {
                            player.clan.elder4_name = memName;
                            mem.typeClan = 2;
                        } else //noinspection ConstantConditions
                            if (player.clan.elder5_name.isEmpty()) {
                                player.clan.elder5_name = memName;
                                mem.typeClan = 2;
                            }
                    }
                Player p2 = hnPlayers.get(memName);
                if (p2 != null) {
                    Player.sendUpdateClan(p2, mem.typeClan);
                    String[] strs = {
                        p2.name + Alert_VN.GO_NEXT + ((mem.typeClan == 3) ? Alert_VN.CLANS[0] : Alert_VN.CLANS[1]),
                        p2.name + Alert_EN.GO_NEXT + ((mem.typeClan == 3) ? Alert_EN.CLANS[0] : Alert_EN.CLANS[1])
                    };
                    player.clan.sendAlert(strs, null);
                }
                doRequestClanMember(conn);
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doClanOut(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || player.clan.isMain(player.name)) {
                return;
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            Member mem = player.clan.getMember(player.name);
            if (mem == null) {
                return;
            }
            int xu = 10000;
            if (mem.typeClan == 1) {
                xu = 20000;
            } else if (mem.typeClan == 2) {
                xu = 50000;
            } else if (mem.typeClan == 3) {
                xu = 100000;
            }
            if (player.getXu() < xu) {
                NJUtil.sendServer(conn, NJUtil.replace(AlertFunction.MOVE_OUT2(conn), xu + " " + AlertFunction.COIN(conn)));
                return;
            }
            if (player.clan.assist_name.equals(mem.name)) {
                player.clan.assist_name = "";
            } else if (player.clan.elder1_name.equals(mem.name)) {
                player.clan.elder1_name = "";
            } else if (player.clan.elder2_name.equals(mem.name)) {
                player.clan.elder2_name = "";
            } else if (player.clan.elder3_name.equals(mem.name)) {
                player.clan.elder3_name = "";
            } else if (player.clan.elder4_name.equals(mem.name)) {
                player.clan.elder4_name = "";
            } else if (player.clan.elder5_name.equals(mem.name)) {
                player.clan.elder5_name = "";
            }
            player.subXu(xu);
            String[] strs = {
                mem.name + Alert_VN.MOVE_OUT1 + " " + xu + " " + Alert_VN.COIN,
                mem.name + Alert_EN.MOVE_OUT1 + " " + xu + " " + Alert_EN.COIN,
            };
            player.clan.sendAlert(strs, null);
            player.clan.members.remove(mem);
            player.timeOutClan = (int) (System.currentTimeMillis() / 1000L) + 86400;
            player.sendGoOutClan();
            Message message = NJUtil.messageNotMap(Cmd.UPDATE_OUT_CLAN);
            message.writeInt(player.getXu());
            NJUtil.sendMessage(player.getSession(), message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doClanUpLevel(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || (!player.clan.isMain(player.name) && !player.clan.isAssist(player.name))) {
                return;
            }
            if (player.clan.level >= Clan.exps.length) {
                NJUtil.sendDialog(conn, "Cấp gia tộc đã đạt tối đa");
            }
            if (player.clan.exp < player.clan.getUpgradeExp()) {
                NJUtil.sendServer(conn, AlertFunction.NOT_EXP_CLAN(conn));
            } else if (player.clan.coin < player.clan.getUpgradeCoin()) {
                NJUtil.sendServer(conn, AlertFunction.NOT_COIN_CLAN(conn));
            } else {
                player.clan.exp -= player.clan.getUpgradeExp();
                player.clan.coin -= player.clan.getUpgradeCoin();
                player.clan.writeLog(player.name, Clan.UP_LEVEL, player.clan.getUpgradeCoin());
                ++player.clan.level;
                Database.saveClan(player.clan);
                player.doRequestClanInfo();
                NJUtil.sendServer(conn, String.valueOf(AlertFunction.CLAN_UP_LEVEL(conn)) + player.clan.level);
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doClearLock(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            String codeSecure;
            try {
                codeSecure = String.valueOf(message.readInt());
            } catch (Exception e2) {
                return;
            }
            if (player.codeSecure != null && player.codeSecure.equals(codeSecure)) {
                player.meLoadActive(0);
                NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_6(conn));
                return;
            }
            NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_1(conn));
            player.updateHackPass2();
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    public void doClientInfo(Session conn, Message message) {
        try {
            conn.clientType = message.readByte();
            conn.typeHD = message.readByte();
            conn.isGPRS = message.readBoolean();
            conn.width = message.readInt();
            conn.height = message.readInt();
            conn.isQwerty = message.readBoolean();
            conn.isTouch = message.readBoolean();
            conn.deviceName = message.readUTF();
            message.readByte(); // Main.versionIp
            conn.versionIphone = message.readInt();
            conn.typeLanguage = (byte) (message.readByte() == 1 ? 1 : 0);
            conn.provider = message.readInt();
            conn.agentCode = message.readUTF();
        } catch (Exception e) {
        }
    }

    private void doConvertUpgrade(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            Item itA, itB, itConvert;
            try {
                itA = player.itemBags[message.readByte()];
                itB = player.itemBags[message.readByte()];
                itConvert = player.itemBags[message.readByte()];
            } catch (Exception e2) {
                return;
            }
            if (itA == null || itB == null || itConvert == null) {
                return;
            }
            if (!itA.isTypeBody() || itA.isTypeNguyetNhan() || !itB.isTypeBody() || itB.isTypeNguyetNhan() || itA.template.level > itB.template.level) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CONVER_UPGRADE3(player.getSession()));
                return;
            }
            if (itA.upgrade <= 0 || itB.upgrade > 0) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CONVER_UPGRADE1(player.getSession()));
                return;
            }
            if (itA.template.type != itB.template.type) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CONVER_UPGRADE2(player.getSession()));
                return;
            }
            if ((itA.upgrade >= 14 && itConvert.getTemplateId() != 271) || (itA.upgrade >= 11 && itConvert.getTemplateId() != 271 && itConvert.getTemplateId() != 270) || (itConvert.getTemplateId() != 271 && itConvert.getTemplateId() != 270 && itConvert.getTemplateId() != 269)) {
                NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CONVER_UPGRADE4(player.getSession()));
                return;
            }
            player.changeItemOption(itB, itA.upgrade);
            itB.upgrade = itA.upgrade;
            itB.isLock = true;
            player.changeItemOption(itA, -itA.upgrade);
            itA.upgrade = 0;
            itA.isLock = true;
            Database.savePlayer(player, player.map.getTemplateId());
            player.savezaLog("Chuyen hoa trang bi @" + itA.getTemplateId() + "@" + itB.getTemplateId());
            message = NJUtil.messageNotMap(Cmd.CONVERT_UPGRADE);
            message.writeByte(itA.indexUI);
            message.writeByte(itA.upgrade);
            message.writeByte(itB.indexUI);
            message.writeByte(itB.upgrade);
            NJUtil.sendMessage(conn, message);
            player.removeItem(itConvert.indexUI);
            player.sendClearItemBag(itConvert.indexUI);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doCreatePlayer(Session conn, Message message) {
        try {
            String name = message.readUTF().trim().toLowerCase();
            int isGender = message.readByte();
            int headId = message.readByte();
            Vector<Player> players = Database.getPlayerByServer(conn.userId, conn.idserver);
            if (players.size() >= GameServer.maxPlayer) {
                NJUtil.sendDialog(conn, NJUtil.replace(AlertFunction.PLAYER_LIMIT(conn), String.valueOf(GameServer.maxPlayer)));
                return;
            }
            if (!NJUtil.isAlphaNumeric(name)) {
                NJUtil.sendDialog(conn, AlertFunction.PLAYER_NAME_FAIL(conn));
                return;
            }
            if (name.length() < 5 || name.length() > 10) {
                NJUtil.sendDialog(conn, AlertFunction.PLAYER_NAME_FAIL(conn));
                return;
            }
            if (conn.type_admin == 0) {
                for (int i = 0; i < Database.names.length; ++i) {
                    if (name.equals(Database.names[i].trim())) {
                        NJUtil.sendDialog(conn, AlertFunction.PLAYER_EXITS(conn));
                        return;
                    }
                }
            }
            if (isGender != Player.GENDER_FEMALE && isGender != Player.GENDER_MALE) {
                isGender = NJUtil.randomBoolean() ? Player.GENDER_FEMALE : Player.GENDER_MALE;
            }
            if (isGender == Player.GENDER_FEMALE && headId != Player.HEAD_FEMALE_NGAN && headId != Player.HEAD_FEMALE_CAI_TRAM && headId != Player.HEAD_FEMALE_BUI && headId != Player.HEAD_FEMALE_XU) {
                return;
            }
            if (isGender == Player.GENDER_MALE && headId != Player.HEAD_MALE_DEO_BANG && headId != Player.HEAD_MALE_BO_XU && headId != Player.HEAD_MALE_SAMURAI && headId != Player.HEAD_MALE_NHIM) {
                return;
            }
            if (Database.isCreate(name)) {
                NJUtil.sendDialog(conn, AlertFunction.PLAYER_EXITS(conn));
                return;
            }
            Player player = new Player(conn, name, isGender, headId);
            ++newplayeId;
            player.playerId = newplayeId;
            if(Database.createPlayer(player)) {
                sendListPlayers(conn);
            } else {
                NJUtil.sendDialog(conn, AlertFunction.ERROR(conn));
            }
        } catch (Exception e) {
            LOGGER.error("Create char error, username: " + conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    private void doInputCoinClan(Session conn, Message message) {
        try {
            int xu;
            try {
                xu = message.readInt();
            } catch (Exception e) {
                return;
            }
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || xu <= 0) {
                return;
            }
            if (xu < 100000 || xu > 100000000) {
                NJUtil.sendDialog(conn, "Số xu mỗi lần góp tối thiểu là 100.000 và không quá 100.000.000 xu.");
                return;
            }
            if (player.getXu() < xu) {
                NJUtil.sendDialog(conn, "Không đủ xu để đóng góp.");
                return;
            }
            long sum = (long) xu + (long) player.clan.coin;
            if (sum > Player.maxCoin) {
                NJUtil.sendDialog(conn, "Số xu quỹ gia tộc vượt quá mức cho phép.");
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            player.subXu(xu);
            player.savezaLog("Gop quy gia toc", player.getXu() + "@" + xu);
            player.clan.coin += xu;
            player.clan.writeLog(player.name, Clan.MOVE_IN_MONEY, xu);
            String[] strs = {
                player.name + NJUtil.replace(Alert_VN.INPUT_MONEY_CLAN, String.valueOf(xu)),
                player.name + NJUtil.replace(Alert_EN.INPUT_MONEY_CLAN, String.valueOf(xu))
            };
            player.clan.sendAlert(strs, null);
            message = NJUtil.messageNotMap(Cmd.INPUT_COIN_CLAN);
            message.writeInt(player.getXu());
            message.writeInt(player.clan.coin);
            NJUtil.sendMessage(conn, message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doOutputCoinClan(Session conn, Message message) {
        try {
            int xu;
            try {
                xu = message.readInt();
            } catch (Exception e) {
                return;
            }
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || xu <= 0) {
                return;
            }
            if (!player.clan.isMain(player.name)) {
                NJUtil.sendDialog(conn, "Chỉ tộc trưởng mới có quyền rút quỹ.");
                return;
            }
            if (System.currentTimeMillis() - player.clan.reg_date < NJUtil.getMilisByDay(7)) {
                NJUtil.sendDialog(conn, "Gia tộc lập trên 7 ngày mới có thể rút quỹ.");
                return;
            }
            if (xu < 50000000) {
                NJUtil.sendDialog(conn, "Số xu rút quỹ tối thiểu là 50.000.000.");
                return;
            }
            if (player.clan.coin < xu) {
                NJUtil.sendDialog(conn, "Số xu quỹ gia tộc không đủ để rút.");
                return;
            }
            long sum = (long) xu + (long) player.getXu();
            if (sum > Player.maxCoin) {
                NJUtil.sendDialog(conn, "Số xu trên người vượt quá mức cho phép.");
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            player.clan.coin -= xu;
            player.clan.writeLog(player.name, Clan.MOVE_OUT_MONEY, xu);
            player.addXu(xu / 100 * 90);
            player.savezaLog("Rut quy gia toc", player.getXu() + "@" + xu + "@" + player.clan.coin);
            NJUtil.sendServer(conn, "Rút quỹ thành công, bạn nhận được " + NJUtil.getDotNumber(xu) + " xu.");
            player.doRequestClanInfo();
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doInviteClanDun(Session conn, Message message) {
        try {
            String memName;
            try {
                memName = message.readUTF();
            } catch (Exception e) {
                return;
            }
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null || (!player.clan.isMain(player.name) && !player.clan.isAssist(player.name)) || player.clan.getMember(memName) == null) {
                return;
            }
            if (!player.map.isDunClan) {
                NJUtil.sendServer(conn, AlertFunction.ERROR_DUN_CLAN13(conn));
                return;
            }
            Player p = hnPlayers.get(memName);
            if (p != null) {
                if (!player.clan.inviteNames.contains(memName)) {
                    player.clan.inviteNames.add(memName);
                }
                NJUtil.sendServer(p.getSession(), player.name + AlertFunction.ALERT_DUN_CLAN7(p.getSession()));
            }
        } catch (Exception e) {
        }
    }

    private void doMoveOutClan(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            String memName = "";
            try {
                memName = message.readUTF();
            } catch (Exception e) {
            }
            if (player == null || player.clan == null || (!player.clan.isMain(player.name) && !player.clan.isAssist(player.name))) {
                return;
            }
            if (player.isLock) {
                player.sendNotUnlock();
                return;
            }
            Member mem = player.clan.getMember(memName);
            if (mem == null || mem.typeClan == 4 || (player.clan.isAssist(player.name) && mem.typeClan == 3)) {
                return;
            }
            if (player.clan.countMoveOut == 0) {
                NJUtil.sendServer(conn, AlertFunction.LIMIT_OUT_CLAN(conn));
                return;
            }
            if (player.clan.assist_name.equals(mem.name)) {
                player.clan.assist_name = "";
            } else if (player.clan.elder1_name.equals(mem.name)) {
                player.clan.elder1_name = "";
            } else if (player.clan.elder2_name.equals(mem.name)) {
                player.clan.elder2_name = "";
            } else if (player.clan.elder3_name.equals(mem.name)) {
                player.clan.elder3_name = "";
            } else if (player.clan.elder4_name.equals(mem.name)) {
                player.clan.elder4_name = "";
            } else if (player.clan.elder5_name.equals(mem.name)) {
                player.clan.elder5_name = "";
            }
            int xu = 10000;
            if (mem.typeClan == 1) {
                xu = 20000;
            } else if (mem.typeClan == 2) {
                xu = 50000;
            } else if (mem.typeClan == 3) {
                xu = 100000;
            }
            player.clan.writeLog(mem.name, Clan.MOVE_OUT_MEM, xu);
            --player.clan.countMoveOut;
            player.clan.coin -= xu;
            String[] strs = {
                mem.name + Alert_VN.MOVE_OUT + " " + xu + " " + Alert_VN.COIN,
                mem.name + Alert_EN.MOVE_OUT + " " + xu + " " + Alert_EN.COIN
            };
            player.clan.sendAlert(strs, null);
            player.clan.members.remove(mem);
            doRequestClanMember(conn);
            Player p = hnPlayers.get(mem.name);
            if (p != null) {
                p.timeOutClan = (int) (System.currentTimeMillis() / 1000L) + 86400;
                p.sendGoOutClan();
            } else {
                Database.clearClan(mem.name);
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    public void doNotLogin(Session conn, Message message) {
        byte command;
        try {
            try {
                command = message.readByte();
            } catch (Exception e) {
                return;
            }
            if (GameServer.isServerLocal()) {
                LOGGER.info(">> Receive message: {} > {}", Cmd.NOT_LOGIN, command);
            }
            if (huPlayers.get(conn.userId) != null) {
                huPlayers.get(conn.userId).cmdMe = Cmd.NOT_LOGIN;
                huPlayers.get(conn.userId).subMe = command;
            }
            switch (command) {
                case Cmd.CLIENT_INFO:
                    doClientInfo(conn, message);
                    break;
                case Cmd.LOGIN:
                    doLogin(conn, message);
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Username: {} Command: {} Client: {},{} User: {},{}", conn.username, message.command, conn.clientType, conn.typeHD, conn.userId, conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    public void doNotMap(Session conn, Message message) {
        try {
            byte command = message.readByte();
            if (GameServer.isServerLocal()) {
                LOGGER.info(">> Receive message: {} > {}", Cmd.NOT_MAP, command);
            }
            if (huPlayers.get(conn.userId) != null) {
                huPlayers.get(conn.userId).cmdMe = Cmd.NOT_MAP;
                huPlayers.get(conn.userId).subMe = command;
            }
            switch (command) {
                case Cmd.LOGOUT:
                    doLogout(conn);
                    break;
                case Cmd.SELECT_PLAYER:
                    doSelectPlayer(conn, message);
                    break;
                case Cmd.CREATE_PLAYER:
                    doCreatePlayer(conn, message);
                    break;
                case Cmd.UPDATE_DATA:
                    doUpdateData(conn);
                    break;
                case Cmd.UPDATE_MAP:
                    doUpdateMap(conn);
                    break;
                case Cmd.UPDATE_SKILL:
                    doUpdateSkill(conn);
                    break;
                case Cmd.UPDATE_ITEM:
                    doUpdateItem(conn);
                    break;
                case Cmd.REQUEST_ICON:
                    doRequestIcon(conn, message.readInt());
                    break;
                case Cmd.REQUEST_CLAN_LOG:
                    doRequestClanLog(conn);
                    break;
                case Cmd.REQUEST_CLAN_INFO:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doRequestClanInfo();
                    }
                    break;
                case Cmd.REQUEST_CLAN_MEMBER:
                    doRequestClanMember(conn);
                    break;
                case Cmd.REQUEST_CLAN_ITEM:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doRequestClanItem();
                    }
                    break;
                case Cmd.REQUEST_MAPTEMPLATE:
                    doRequestMapTemplate(conn, message);
                    break;
                case Cmd.REQUEST_NPCTEMPLATE:
                    doRequestNpcTemplate(conn, message);
                    break;
                case Cmd.REQUEST_NPCPLAYER:
                    doRequestNpcPlayer(conn, message);
                    break;
                case Cmd.ME_ACTIVE:
                    doActive(conn, message);
                    break;
                case Cmd.ME_UPDATE_ACTIVE:
                    doUpdateActive(conn, message);
                    break;
                case Cmd.ME_OPEN_LOCK:
                    doOpenLock(conn, message);
                    break;
                case Cmd.ME_CLEAR_LOCK:
                    doClearLock(conn, message);
                    break;
                case Cmd.CLIENT_OK: {
                    if (huPlayers.get(conn.userId) == null) {
                        sendListPlayers(conn);
                        Player player = new Player();
                        player.setSession(conn);
                        putPlayer(conn.userId, conn.username, player.playerId, player.name, player);
                    }
                    int versionMax = versionMax_java;
                    if (conn.clientType == GameServer.CLIENT_ANDROID) {
                        versionMax = versionMax_android;
                    } else if (conn.clientType == GameServer.CLIENT_IPHONE) {
                        versionMax = versionMax_iphone;
                    } else if (conn.clientType == GameServer.CLIENT_PC) {
                        versionMax = versionMax_pc;
                    } else if (conn.clientType == GameServer.CLIENT_IPHONEAP) {
                        versionMax = versionMax_iphoneAP;
                    } else if (conn.clientType == GameServer.CLIENT_WINPHONE) {
                        versionMax = versionMax_winphone;
                    }
                    if (conn.version < versionMax) {
                        NJUtil.sendAlertOpenWeb(conn, AlertFunction.UPDATE(conn), AlertFunction.NO(conn), AlertFunction.URL_DOWNLOAD(conn), NJUtil.replace(AlertFunction.VERSION_NEW(conn), NJUtil.formatVersion(versionMax)));
                    }
                    break;
                }
                case Cmd.INPUT_CARD:
                    NJUtil.sendDialog(conn, "Truy cập vào trang chủ để nạp thẻ.");
                    break;
                case Cmd.CLEAR_TASK:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doClearTask();
                    }
                    break;
                case Cmd.CLAN_CHANGE_ALERT:
                    doClanChangeAlert(conn, message);
                    break;
                case Cmd.CLAN_CHANGE_TYPE:
                    doClanChangeType(conn, message);
                    break;
                case Cmd.CLAN_MOVEOUT_MEM:
                    doMoveOutClan(conn, message);
                    break;
                case Cmd.CLAN_OUT:
                    doClanOut(conn);
                    break;
                case Cmd.CLAN_UP_LEVEL:
                    doClanUpLevel(conn);
                    break;
                case Cmd.INPUT_COIN_CLAN:
                    doInputCoinClan(conn, message);
                    break;
                case Cmd.OUTPUT_COIN_CLAN:
                    doOutputCoinClan(conn, message);
                    break;
                case Cmd.CONVERT_UPGRADE:
                    doConvertUpgrade(conn, message);
                    break;
                case Cmd.INVITE_CLANDUN:
                    doInviteClanDun(conn, message);
                    break;
                case Cmd.ITEM_SPLIT:
                    doSplitItem(conn, message);
                    break;
                case Cmd.REWARD_PB:
                    doRewardPB(conn);
                    break;
                case Cmd.REWARD_CT:
                    doRewardCT(conn);
                    break;
                /*case Cmd.CHAT_ADMIN:
                    huPlayers.get(conn.userId).doChatAmin(message);
                    break;*/
                case Cmd.CHECK_KEY1:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCheckKey1(message);
                    }
                    break;
                case Cmd.CHECK_KEY2:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCheckKey2(message);
                    }
                    break;
                case Cmd.CHECK_KEY3:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCheckKey3(message);
                    }
                    break;
                case Cmd.CHECK_KEY4:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doCheckKey4();
                    }
                    break;
                case Cmd.LAT_HINH:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doLatHinh(message);
                    }
                    break;
                case Cmd.MOI_GTC:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doMoiGTC(message);
                    }
                    break;
                case Cmd.MOI_TATCA_GTC:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doMoiTatCaGTC();
                    }
                    break;
                case Cmd.GO_GTCHIEN:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doGoGTChien();
                    }
                    break;
                case Cmd.NAP_NOKIA: // nạp nokia
                case Cmd.NAP_GOOGLE: // nạp google
                    if (huPlayers.get(conn.userId) != null) {
                        NJUtil.sendDialog(conn, AlertFunction.NOT_USE(conn));
                    }
                    break;
                case Cmd.OPEN_CLAN_ITEM:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doOpenClanItem();
                    }
                    break;
                case Cmd.CLAN_SEND_ITEM:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doClanSendItem(message);
                    }
                    break;
                case Cmd.CLAN_USE_ITEM:
                    if (huPlayers.get(conn.userId) != null) {
                        huPlayers.get(conn.userId).doClanUseItem(message);
                    }
                    break;
                case Cmd.NEW_IMAGE:
                    doRequestNewImage(conn, message);
                    break;
                case Cmd.CHAT_ADMIN:
                    NJUtil.sendDialog(conn, AlertFunction.FEATURE_NOT_OPEN(conn));
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    private void doOpenLock(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            String codeSecure;
            try {
                codeSecure = String.valueOf(message.readInt());
            } catch (Exception e2) {
                return;
            }
            if (player.codeSecure != null && player.codeSecure.equals(codeSecure)) {
                player.meLoadActive(2);
                NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_5(conn));
                return;
            }
            NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_1(conn));
            player.updateHackPass2();
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doRequestClanLog(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null) {
                return;
            }
            Message message = NJUtil.messageNotMap(Cmd.REQUEST_CLAN_LOG);
            message.writeUTF(player.clan.log);
            NJUtil.sendMessage(conn, message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doRequestClanMember(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null || player.clan == null) {
                return;
            }
            player.clan.sort();
            Message message = NJUtil.messageNotMap(Cmd.REQUEST_CLAN_MEMBER);
            message.writeShort(player.clan.members.size());
            for (int i = 0; i < player.clan.members.size(); ++i) {
                message.writeByte(player.clan.members.get(i).classId);
                message.writeByte(player.clan.members.get(i).level);
                int typeClan = player.clan.getType(player.clan.members.get(i).name);
                if (typeClan == Clan.TYPE_NORMAL) {
                    typeClan = player.clan.members.get(i).typeClan;
                }
                message.writeByte(typeClan);
                message.writeUTF(player.clan.members.get(i).name);
                message.writeInt(player.clan.members.get(i).pointClan);
                message.writeBoolean(player.clan.members.get(i).isOnline);
            }
            for (int i = 0; i < player.clan.members.size(); ++i) {
                message.writeInt(player.clan.members.get(i).pointClanWeek);
            }
            NJUtil.sendMessage(conn, message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doRequestEffDynamicTool(Session conn, Message message) {
        try {
            byte type = message.readByte();
            short idEff = message.readShort();
            Message msg = new Message(Cmd.EFF_DYNAMIC_TOOL);
            if (type == 1) {
                msg.writeByte(1);
                msg.writeByte(idEff);
                byte[] data = null;
                if (conn.typeHD == 1) {
                    data = imageDynamicEffOnPlayer1[idEff];
                } else if (conn.typeHD == 2) {
                    data = imageDynamicEffOnPlayer2[idEff];
                } else if (conn.typeHD == 3) {
                    data = imageDynamicEffOnPlayer3[idEff];
                } else if (conn.typeHD == 4) {
                    data = imageDynamicEffOnPlayer4[idEff];
                }
                if (data != null) {
                    msg.writeInt(data.length);
                    msg.write(data);
                } else {
                    msg.writeInt(0);
                }
                conn.sendMessage(msg);
            } else if (type == 2) {
                msg.writeByte(2);
                msg.writeByte(idEff);
                msg.writeShort(dataDynamicEffOnPlayer[idEff].length);
                msg.write(dataDynamicEffOnPlayer[idEff]);
                conn.sendMessage(msg);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }


    private void doRequestMapTemplate(Session conn, Message message) {
        try {
            int mapTemplateId = message.dis.readUnsignedByte();
            MapTemplate mapTemplate = mapTemplates.get(mapTemplateId);
            message = NJUtil.messageNotMap(Cmd.REQUEST_MAPTEMPLATE);
            message.writeByte(mapTemplate.tmw);
            message.writeByte(mapTemplate.tmh);
            for (int i = 0; i < mapTemplate.maps.length; ++i) {
                message.writeByte(mapTemplate.maps[i]);
            }
            NJUtil.sendMessage(conn, message);
        } catch (IOException e) {
            LOGGER.error("Username: " + conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    private void doRequestNewImage(Session conn, Message message) {
        try {
            byte type = message.readByte();
            int idEff = message.readUnsignedByte();
            Message msg = new Message(Cmd.NEW_IMAGE);
            if (type == 0) {
                msg.writeByte(2);
                msg.writeByte(idEff);
                byte[] data = null;
                if (conn.typeHD == 1) {
                    data = imageDynamicEff1[idEff];
                } else if (conn.typeHD == 2) {
                    data = imageDynamicEff2[idEff];
                } else if (conn.typeHD == 3) {
                    data = imageDynamicEff3[idEff];
                } else if (conn.typeHD == 4) {
                    data = imageDynamicEff4[idEff];
                }
                if (data != null) {
                    msg.writeInt(data.length);
                    msg.write(data);
                } else {
                    msg.writeInt(0);
                }
            } else if (type == 1) {
                msg.writeByte(3);
                msg.writeByte(idEff);
                msg.writeShort(dataDynamicEff[idEff].length);
                msg.write(dataDynamicEff[idEff]);
            } else if (type == 4) {
                huPlayers.get(conn.userId).doConfirmCapcha(idEff);
            }
            if (type != 4) {
                conn.sendMessage(msg);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    private void doRequestNpcPlayer(Session conn, Message message) {
        try {
            PlayerTemplate playerTemplate;
            try {
                int playerTemplateId = message.readByte();
                playerTemplate = playerTemplates.get(playerTemplateId);
            } catch (Exception e2) {
                return;
            }
            message = NJUtil.messageNotMap(Cmd.REQUEST_NPCPLAYER);
            message.writeByte(playerTemplate.playerTemplateId);
            message.writeUTF(playerTemplate.getName(conn));
            message.writeShort(playerTemplate.headId);
            message.writeShort(playerTemplate.bodyId);
            message.writeShort(playerTemplate.legId);
            message.writeByte(playerTemplate.getSay(conn).length);
            for (int i = 0; i < playerTemplate.getSay(conn).length; ++i) {
                message.writeUTF(playerTemplate.getSay(conn)[i]);
            }
            if (playerTemplate.getMenu() != null) {
                message.writeByte(playerTemplate.getMenu().length);
                for (int j = 0; j < playerTemplate.getMenu().length; ++j) {
                    message.writeByte(playerTemplate.getMenu()[j].length);
                    for (int j2 = 0; j2 < playerTemplate.getMenu()[j].length; ++j2) {
                        message.writeUTF(playerTemplate.getMenu()[j][j2]);
                    }
                }
            } else {
                message.writeByte(0);
            }
            NJUtil.sendMessage(conn, message);
        } catch (IOException e) {
            LOGGER.error("Username: " + conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    private void doRequestNpcTemplate(Session conn, Message message) {
        try {
            int npcTemplateId = message.dis.readUnsignedByte();
            NpcTemplate npcTemplate = npcTemplates.get(npcTemplateId);
            message = NJUtil.messageNotMap(Cmd.REQUEST_NPCTEMPLATE);
            if (!conn.isVersion123()) {
                message.writeByte(npcTemplate.npcTemplateId);
            } else {
                message.writeShort(npcTemplate.npcTemplateId);
            }
            if (conn.isVersion123()) {
                if (npcTemplate.isPetNew()) {
                    message.writeByte(1);
                } else {
                    message.writeByte(0);
                }
            }
            Vector<byte[]> datas;
            if (npcTemplateId == 219) {
                Player p = huPlayers.get(conn.userId);
                if (conn.clientType == GameServer.CLIENT_PC) {
                    datas = p.capcha.loadImage(4);
                } else {
                    datas = p.capcha.loadImage(conn.typeHD - 1);
                }
            } else if (conn.clientType == GameServer.CLIENT_PC) {
                datas = npcTemplate.loadData(4);
            } else {
                datas = npcTemplate.loadData(conn.typeHD - 1);
            }
            message.writeByte(datas.size());
            for (byte[] data : datas) {
                message.writeBytes(data);
            }
            message.writeBoolean(npcTemplate.isModTool());
            if (npcTemplate.isBossId() || npcTemplate.isPetNew()) {
                message.writeByte(npcTemplate.arr1.length);
                for (int i = 0; i < npcTemplate.arr1.length; ++i) {
                    message.writeByte(npcTemplate.arr1[i]);
                }
                message.writeByte(npcTemplate.arr2.length);
                for (int i = 0; i < npcTemplate.arr2.length; ++i) {
                    message.writeByte(npcTemplate.arr2[i].length);
                    for (int j = 0; j < npcTemplate.arr2[i].length; ++j) {
                        message.writeByte(npcTemplate.arr2[i][j]);
                    }
                }
            }
            message.writeBytes(npcTemplate.arr);
            NJUtil.sendMessage(conn, message);
        } catch (IOException e) {
            LOGGER.error("Username: " + conn.username, e);
        } finally {
            message.cleanup();
        }
    }

    public void doRewardCT(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            if (player.rwct > 2) {
                player.getSession().disconnect("NGAT KET NOI NJCONTROL 12");
                return;
            }
            LocalTime timeNow = LocalTime.now();
            int hour = timeNow.getHour();
            int minute = timeNow.getMinute();
            if (player.pointCT <= 0 || hour == 13 || (hour == 14 && minute < 31) || hour == 16 || (hour == 17 && minute < 31) || hour == 19 || (hour == 20 && minute < 31)) {
                return;
            }
            int ct = player.getCT();
            int[] idDan = { 275, 276, 277, 278 };
            int goldAdd = 0;
            Vector<Item> itAdd = new Vector<>();
            switch (ct) {
                case 4: {
                    for (int i = 0; i < (player.resultCT == 1 ? 30 : 20); i++) {
                        itAdd.add(new Item(Item.randomItem(idDan), false, "ct " + ct + "_" + player.resultCT));
                    }
                    itAdd.add(new Item(player.resultCT == 1 ? 9 : 7, false, "ct " + ct + "_" + player.resultCT));
                    goldAdd = player.resultCT == 1 ? 3000 : 2000;
                    break;
                }
                case 3: {
                    for (int i = 0; i < (player.resultCT == 1 ? 20 : 15); i++) {
                        itAdd.add(new Item(Item.randomItem(idDan), false, "ct " + ct + "_" + player.resultCT));
                    }
                    itAdd.add(new Item(player.resultCT == 1 ? 8 : 6, false, "ct " + ct + "_" + player.resultCT));
                    goldAdd = player.resultCT == 1 ? 2000 : 1500;
                    break;
                }
                case 2: {
                    for (int i = 0; i < (player.resultCT == 1 ? 15 : 10); i++) {
                        itAdd.add(new Item(Item.randomItem(idDan), false, "ct " + ct + "_" + player.resultCT));
                    }
                    itAdd.add(new Item(player.resultCT == 1 ? 7 : 5, false, "ct " + ct + "_" + player.resultCT));
                    goldAdd = player.resultCT == 1 ? 1500 : 1000;
                    break;
                }
                case 1: {
                    for (int i = 0; i < (player.resultCT == 1 ? 5 : 3); i++) {
                        itAdd.add(new Item(Item.randomItem(idDan), false, "ct " + ct + "_" + player.resultCT));
                    }
                    goldAdd = player.resultCT == 1 ? 500 : 300;
                    break;
                }
                default: {
                    for (int i = 0; i < (player.resultCT == 1 ? 2 : 1); i++) {
                        itAdd.add(new Item(Item.randomItem(idDan), false, "ct " + ct + "_" + player.resultCT));
                    }
                    break;
                }
            }
            if (Map.winCT != null) {
                for (int i = 0; i < Map.winCT.length; ++i) {
                    if (Map.winCT[i] != null && Map.winCT[i].equals(player.name)) {
                        player.sendUpdateExp(NJUtil.randomMinMax(3000000, 10000000) + 10000000, true);
                        Map.winCT[i] = null;
                        break;
                    }
                }
            }
            for (Item it : itAdd) {
                player.addItemToBagNoDialog(it);
            }
            if (goldAdd > 0) {
                player.sendUpdateLuong(goldAdd);
            }
            if (player.pointCT >= 1000 && player.typeNvbian == 6) {
                player.fibian();
            }
            player.resetCT();
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    public void doRewardPB(Session conn) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            int rewardPB = player.getRewardPB();
            if (rewardPB <= 0) {
                return;
            }
            int t = (int) (System.currentTimeMillis() / 1000L);
            if (t - player.timeNhanPB < 10) {
                return;
            }
            player.timeNhanPB = t;
            Item it;
            if (player.level >= 90) {
                it = new Item(647, true, "thuong pb 1");
            } else if (player.level >= 50) {
                it = new Item(282, true, "thuong pb 2");
            } else {
                it = new Item(272, true, "thuong pb 3");
            }
            it.quantity = rewardPB;
            player.ghiloghack(it.getTemplateId());
            if (player.level >= 60) {
                Player.changeTopRuong(player, it.quantity);
            }
            if (!player.addItemToBagNoDialog(it)) {
                player.sendOpenUISay(0, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                return;
            }
            player.pointPB = 0;
            player.timePB = 0;
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doSelectPlayer(Session conn, Message message) {
        String name = "";
        try {
            name = message.readUTF().toLowerCase();
            try {
                if (Database.isCharSaveFail(name)) {
                    NJUtil.sendDialog(conn, "Nhân vật này đang được cập nhật dữ liệu, vui lòng chờ!");
                    return;
                }
                Vector<Player> players = conn.listChar;
                if (players.size() == 0) {
                    players = Database.getPlayerByServer(conn.userId, conn.idserver, name);
                }
                for (Player player : players) {
                    if (player.name.equals(name)) {
                        if (player.ban) {
                            NJUtil.sendDialog(conn, AlertFunction.PLAYER_LOCK(conn));
                            NJUtil.sleep(3000L);
                            conn.disconnect("player banned");
                            return;
                        }
                        if (huPlayers.get(conn.userId) != null && huPlayers.get(conn.userId).playerId == -1) {
                            LocalDate dateNow = LocalDate.now();
                            LocalDate dateLogin = NJUtil.getDateByMilis(player.login_date);
                            if (player.clan != null) {
                                try {
                                    for (int j = 0; j < player.clan.members.size(); ++j) {
                                        if (player.clan.members.get(j).name.equals(player.name)) {
                                            player.clan.members.get(j).isOnline = true;
                                            break;
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                            if (dateNow.getMonthValue() != dateLogin.getMonthValue() || dateNow.getDayOfMonth() >= 30) {
                                player.countLoiDai = 0;
                                player.pointLoiDai = 0;
                                player.countLoiDaiClass = 0;
                                player.pointLoiDaiClass = 0;
                            }
                            if (dateNow.getDayOfMonth() != dateLogin.getDayOfMonth() || dateNow.getMonthValue() != dateLogin.getMonthValue() || dateNow.getYear() != dateLogin.getYear()) {
                                if (NJUtil.isNewWeek(dateNow, dateLogin)) {
                                    player.isChangeCoin = false;
                                    player.pointClanWeek = 0;
                                }
                                int numDay = (int) NJUtil.getDaysBetween(dateNow, dateLogin);
                                for (int k = 0; k < numDay; ++k) {
                                    player.pointUyDanh -= 1 + player.pointUyDanh / 50;
                                }
                                if (player.pointUyDanh < 0) {
                                    player.pointUyDanh = 0;
                                }
                                player.countFinishDay = 0;
                                player.timeOnline = 0;
                                player.countLoopDay = Player.maxLoopDay;
                                player.pointOpenTui = Player.maxOpenTui;
                                player.countctkeo = Player.maxCTKeo;
                                player.countNvbian = Player.maxNVBA;
                                player.countChatAdmin = Player.maxChatAdm;
                                player.countPB = Player.maxPhoBan;
                                player.countUseTTL = Player.defaultCountTTL;
                                player.countUseHDL = Player.defaultCountHDL;
                                player.pointPB = 0;
                                player.timePB = 0;
                                player.friendPB = 0;
                                player.countLoopBoos += (byte) (2 * numDay);
                                if (player.countLoopBoos > Player.maxLoopBoss || player.countLoopBoos < 0) {
                                    player.countLoopBoos = Player.maxLoopBoss;
                                }
                                player.pk -= 3;
                                if (player.pk < 0) {
                                    player.pk = 0;
                                }
                                player.checkThuCuoi(numDay);
                            }
                            player.setSession(conn);
                            if (player.skills.size() > 0) {
                                player.myskill = player.skills.get(0);
                            }
                            player.level = player.getLevel();
                            player.login_date = System.currentTimeMillis();
                            if (conn.type_admin < Player.ROLE_GM) {
                                if (player.classId > 0) {
                                    player.checkTiemNang();
                                    int countSPoint = player.skill_point;
                                    for (int l = 0; l < player.skills.size(); ++l) {
                                        Skill skill = player.skills.get(l);
                                        if (skill.template.maxPoint > 0 && skill.point > 1 && !Player.isSkillPhanThan(skill)) {
                                            countSPoint += skill.point - 1;
                                        }
                                    }
                                    if (countSPoint != player.level + player.limitKynangso + player.limitBanhPhongloi - 9) {
                                        LOGGER.error("Error point, charname: {}, countSPoint: {}, lv: {}, limitKynangso: {}, limitBanhPhongloi: {}", name, countSPoint, player.level, player.limitKynangso, player.limitBanhPhongloi);
                                        player.tayKynang();
                                    }
                                } else {
                                    player.skill_point = 0;
                                }
                            }
                            player.resetData();
                            Map mapNext = Map.findMap(player, player.map.template.mapTemplateId);
                            if (player.map.template.mapTemplateId >= 139 && player.timeEff_ThiLuyen <= 0) {
                                mapNext = Map.findMap(player, 22);
                            }
                            putPlayer(conn.userId, conn.username, player.playerId, player.name, player);
                            player.map = mapNext;
                            player.timeActive = System.currentTimeMillis();
                            player.mapClear();
                            player.updateAll();
                            addPointLoiDai(player, 0);
                            if (player.codeSecure == null || player.codeSecure.isEmpty()) {
                                player.meLoadActive(0);
                            } else {
                                player.meLoadActive(1);
                            }
                            if (player.taskLoopDay != null) {
                                player.getTaskOrder(player.taskLoopDay);
                            }
                            if (player.taskLoopBoss != null) {
                                player.getTaskOrder(player.taskLoopBoss);
                            }
                            if (player.dailytask != null) {
                                player.dailytask.updateName(player);
                                player.getTaskOrder(player.dailytask);
                            }
                            player.changeNpcMe();
                            player.loadThuCuoi(0);
                            player.sendBian();
                            try {
                                if (player.taskIndex != -1) {
                                    player.taskMain = new Task(player.getTaskFinish());
                                    player.taskMain.index = player.taskIndex;
                                    if (player.taskIndex >= player.taskMain.template.subNames[conn.typeLanguage].length - 1) {
                                        Task taskMain = player.taskMain;
                                        byte b = (byte) (player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1);
                                        player.taskIndex = b;
                                        taskMain.index = b;
                                    }
                                    if (player.taskCount != 0) {
                                        player.taskMain.count = player.taskCount;
                                    }
                                    if (player.taskMain.template.taskId == 1 && player.taskMain.index > 0 && player.taskMain.index < 5) {
                                        player.taskMain.index = 0;
                                    } else if (player.taskMain.template.taskId == 7 && player.taskMain.index > 1 && player.taskMain.index < 6) {
                                        player.taskMain.index = 1;
                                    } else if (player.taskMain.template.taskId == 7 && player.taskMain.index > 6 && player.taskMain.index < 11) {
                                        player.taskMain.index = 6;
                                    } else if (player.taskMain.template.taskId == 7 && player.taskMain.index > 11 && player.taskMain.index < 16) {
                                        player.taskMain.index = 11;
                                    }
                                    player.sendTask();
                                }
                            } catch (Exception e) {
                                player.taskMain = null;
                            }
                            if (((player.effId_food >= 0 && player.effId_food <= 4) || player.effId_food == 28 || player.effId_food == 30 || player.effId_food == 31) && player.timeEff_food > 0) {
                                player.useEff(player.effId_food, player.timeEff_food);
                            }
                            if (player.timeEff_ThiLuyen > 0) {
                                player.useThiLuyenThiep(player.timeEff_ThiLuyen);
                            }
                            if (player.timeKhai_Thien_Nhan_Phu > 0) {
                                Player.useThienNhanPhu(player, player.timeKhai_Thien_Nhan_Phu, (player.effId_KhaiThienNhanPhu != 40) ? 1 : 0);
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
                            if (mapNext == null) {
                                player.map = null;
                                NJUtil.sleep(1000L);
                                player.endWait(null);
                                NJUtil.sleep(1000L);
                                putPlayer(conn.userId, conn.username, player.playerId, player.name, player);
                                player.getSession().disconnect("NGAT KET NOI NJCONTROL 16");
                                return;
                            }
                            mapNext.waitGoInMap(player);
                            player.savezaLog("Chon nhan vat " + player.name);
                            Item item = player.findItemBag(572);
                            player.xStartAuto = player.x;
                            player.yStartAuto = player.y;
                            if (item != null) {
                                player.sendTeleport(-1, player.x, player.y, false, false, "doselect");
                            } else {
                                player.sendTeleport(-2, player.x, player.y, false, false, "doselect");
                            }
                            player.doAddGiftNewDay();
                        } else {
                            NJUtil.sendDialog(conn, AlertFunction.ERROR(conn));
                        }
                        String infoMsg = NJUtil.readFileString("info_msg.txt");
                        if (infoMsg != null && !infoMsg.trim().equals("")) {
                            NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.SYSTEM(player.getSession()), infoMsg);
                        }
                        String infoX2 = Player.getInfoX2Exp();
                        String infoX2Class = Player.getInfoClassX2Exp();
                        Vector<String> infoDlg = new Vector<>();
                        if (infoX2 != null) {
                            infoDlg.add(infoX2);
                        }
                        if (infoX2Class != null) {
                            infoDlg.add(infoX2Class);
                        }
                        for (String info : infoDlg) {
                            Message msg = new Message(Cmd.SERVER_ALERT);
                            msg.writeUTF(info);
                            try {
                                player.getSession().sendMessage(msg);
                            } catch (Exception e) {
                            }
                        }
                        player.addNewbieGift();
                        int countGem = 0, countGem2 = 0, countGem3 = 0, countGem4 = 0, countGem5 = 0, countGem6 = 0;
                        for (int i = 0; i < player.itemBags.length; ++i) {
                            Item item = player.itemBags[i];
                            if (item != null) {
                                if (item.isTypeGem()) {
                                    switch (item.upgrade) {
                                        case 5:
                                            ++countGem;
                                            break;
                                        case 6:
                                            ++countGem2;
                                            break;
                                        case 7:
                                            ++countGem3;
                                            break;
                                        case 8:
                                            ++countGem4;
                                            break;
                                        case 9:
                                            ++countGem5;
                                            break;
                                        case 10:
                                            ++countGem6;
                                            break;
                                    }
                                } else if (item.gem.size() > 0) {
                                    for (int j = 0; j < item.gem.size(); ++j) {
                                        Item it = item.gem.get(j);
                                        switch (it.upgrade) {
                                            case 5:
                                                ++countGem;
                                                break;
                                            case 6:
                                                ++countGem2;
                                                break;
                                            case 7:
                                                ++countGem3;
                                                break;
                                            case 8:
                                                ++countGem4;
                                                break;
                                            case 9:
                                                ++countGem5;
                                                break;
                                            case 10:
                                                ++countGem6;
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < player.itemBoxs.length; ++i) {
                            Item item = player.itemBoxs[i];
                            if (item != null) {
                                if (item.isTypeGem()) {
                                    switch (item.upgrade) {
                                        case 5:
                                            ++countGem;
                                            break;
                                        case 6:
                                            ++countGem2;
                                            break;
                                        case 7:
                                            ++countGem3;
                                            break;
                                        case 8:
                                            ++countGem4;
                                            break;
                                        case 9:
                                            ++countGem5;
                                            break;
                                        case 10:
                                            ++countGem6;
                                            break;
                                    }
                                } else if (item.gem.size() > 0) {
                                    for (int j = 0; j < item.gem.size(); ++j) {
                                        Item it = item.gem.get(j);
                                        switch (it.upgrade) {
                                            case 5:
                                                ++countGem;
                                                break;
                                            case 6:
                                                ++countGem2;
                                                break;
                                            case 7:
                                                ++countGem3;
                                                break;
                                            case 8:
                                                ++countGem4;
                                                break;
                                            case 9:
                                                ++countGem5;
                                                break;
                                            case 10:
                                                ++countGem6;
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < player.itemBodys.length; ++i) {
                            Item item = player.itemBodys[i];
                            if (item != null) {
                                if (item.isTypeGem()) {
                                    switch (item.upgrade) {
                                        case 5:
                                            ++countGem;
                                            break;
                                        case 6:
                                            ++countGem2;
                                            break;
                                        case 7:
                                            ++countGem3;
                                            break;
                                        case 8:
                                            ++countGem4;
                                            break;
                                        case 9:
                                            ++countGem5;
                                            break;
                                        case 10:
                                            ++countGem6;
                                            break;
                                    }
                                } else if (item.gem.size() > 0) {
                                    for (int j = 0; j < item.gem.size(); ++j) {
                                        Item it = item.gem.get(j);
                                        switch (it.upgrade) {
                                            case 5:
                                                ++countGem;
                                                break;
                                            case 6:
                                                ++countGem2;
                                                break;
                                            case 7:
                                                ++countGem3;
                                                break;
                                            case 8:
                                                ++countGem4;
                                                break;
                                            case 9:
                                                ++countGem5;
                                                break;
                                            case 10:
                                                ++countGem6;
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                        Database.saveLogPlayerWithGems(player, countGem + countGem2 + countGem3 + countGem4 + countGem5 + countGem6, String.format("5,%d;6,%d;7,%d;8,%d;9,%d;10,%d", countGem, countGem2, countGem3, countGem4, countGem5, countGem6));
                        return;
                    }
                }
                NJUtil.sendDialog(conn, AlertFunction.PLAYER_ERROR(conn));
            } catch (Exception e) {
                LOGGER.error("", e);
            } finally {
                conn.listChar.clear();
            }
        } catch (Exception e) {
            LOGGER.error("Username: {} Nhan vat: {}", conn.username, name, e);
        } finally {
            message.cleanup();
        }
    }

    private void doSplitItem(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            Item item;
            int split;
            try {
                item = player.itemBags[message.readByte()];
                split = message.readInt();
            } catch (Exception e2) {
                return;
            }
            if (item == null || split >= item.quantity || split <= 0) {
                return;
            }
            if (player.countFreeBag() < 1) {
                NJUtil.sendDialog(conn, AlertFunction.BAG_FULL(conn));
                return;
            }
            if (item.quantity - split <= 0) {
                return;
            }
            item.quantity -= split;
            player.ghiloghack(item.getTemplateId());
            player.sendUseItemUpToUp(item.indexUI, split);
            Item itNew = item.cloneItem();
            itNew.quantity = split;
            player.ghiloghack(itNew.getTemplateId());
            for (int i = 0; i < player.itemBags.length; ++i) {
                if (player.itemBags[i] == null) {
                    itNew.indexUI = i;
                    player.sendAddItemBag(player.itemBags[i] = itNew);
                    break;
                }
            }
            for (int i = 0; i < player.itemBags.length; ++i) {
                if (player.itemBags[i] != null && player.itemBags[i].quantity < 0) {
                    player.doCancelTrade1();
                    Database.saveLogAll(player.name, "hack tach vp", "hack");
                    player.getSession().disconnect("hack tach vp");
                    return;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doUpdateActive(Session conn, Message message) {
        try {
            Player player = huPlayers.get(conn.userId);
            if (player == null) {
                return;
            }
            String pass2Old, pass2New;
            try {
                pass2Old = String.valueOf(message.readInt());
                pass2New = String.valueOf(message.readInt());
            } catch (Exception e2) {
                return;
            }
            if (pass2New.length() == 6 && (player.codeSecure != null && !player.codeSecure.isEmpty())) {
                if (!player.codeSecure.equals(pass2Old)) {
                    player.updateHackPass2();
                    NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_1(conn));
                    return;
                }
                player.codeSecure = pass2New;
                player.meLoadActive(2);
                NJUtil.sendServer(conn, AlertFunction.UPDATE_PASS2_3(conn));
            } else {
                player.updateHackPass2();
            }
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doUpdateData(Session conn) {
        try {
            NJUtil.sendMessage(conn, messageData(conn));
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doUpdateItem(Session conn) {
        try {
            NJUtil.sendMessage(conn, messageItem(conn));
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doUpdateMap(Session conn) {
        try {
            NJUtil.sendMessage(conn, messageMap(conn));
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    private void doUpdateSkill(Session conn) {
        try {
            NJUtil.sendMessage(conn, messageSkill(conn));
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    public void savePlayer(Player player) {
        try {
            if (player != null) {
                int mapSave = -1;
                try {
                    if (!player.isSetTimeOnline) {
                        player.timeOnline += (int) (System.currentTimeMillis() - player.login_date);
                    }
                    removePlayer(player.userId, null, player.playerId, player.name);
                    if (player.map != null && player.map.giatocchien != null) {
                        player.map.giatocchien.namePlayLefts.remove(player.name);
                        player.map.giatocchien.namePlayRights.remove(player.name);
                    }
                    if (player.clan != null) {
                        try {
                            for (int i = 0; i < player.clan.members.size(); ++i) {
                                if (player.clan.members.get(i).name.equals(player.name)) {
                                    player.clan.members.get(i).isOnline = false;
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                    player.clearData(true);
                    if (player.party != null) {
                        player.party.out(player);
                    }
                    if (player.map != null && player.map.mapId != -1) {
                        player.map.playerOuts.add(player);
                        mapSave = player.map.template.mapTemplateId;
                        if (player.status == Player.ME_DIE) {
                            mapSave = -1;
                        }
                        if (player.map.template.isMapChienTruong()) {
                            Map.playerLefts.remove(player);
                            Map.playerRights.remove(player);
                        }
                    }
                    if (player.name != null && !player.name.isEmpty()) {
                        player.savezaLog("Thoat game");
                    }
                    if (player.myskill != null && player.myskill.template != null) {
                        player.saveRms("CSkill", new byte[]{ (byte) player.myskill.template.skillTemplateId }, true);
                    }
                } catch (Exception e) {
                }
                int mSave = mapSave;
                Database.savePlayer(player, mSave);
                player.isDisonnect = true;
            }
        } catch (Exception e) {
            LOGGER.error("Error save player, charname: {}", player.name, e);
        }
    }

    private void sendListPlayers(Session conn) {
        try {
            Vector<Player> players = conn.listChar;
            if (players.size() == 0) {
                players = Database.getListPlayerByServer(conn.userId, conn.idserver);
            }
            Message message = NJUtil.messageNotMap(Cmd.SELECT_PLAYER);
            message.dos.writeByte(players.size());
            for (Player p : players) {
                message.dos.writeByte(p.gender);
                message.dos.writeUTF(p.name);
                message.dos.writeUTF(nClasss.get(p.classId).getName(conn));
                message.dos.writeByte(p.getLevel());
                if (p.itemBodys != null && p.itemBodys[11] != null) {
                    message.dos.writeShort(p.itemBodys[11].template.part);
                } else {
                    message.dos.writeShort(p.headId);
                }
                short[] idpart = p.getIdPartThoiTrang();
                message.dos.writeShort((idpart[1] > -1) ? idpart[1] : p.getPartBody(1));
                message.dos.writeShort((idpart[2] > -1) ? idpart[2] : p.getPartBody(2));
                message.dos.writeShort((idpart[3] > -1) ? idpart[3] : p.getPartBody(6));
            }
            NJUtil.sendMessage(conn, message);
        } catch (Exception e) {
            LOGGER.error("Username: " + conn.username, e);
        }
    }

    public static void addItemShops(Item item) {
        item.expires = System.currentTimeMillis();
        long itemAutionId = Database.addItemAuction(item);
        if (itemAutionId != -1) {
            item.itemId = itemAutionId;
            shops.add(item);
        }
    }

    public static void removeItemShops(Item item) {
        long itemAuctionId = item.itemId;
        shops.remove(item);
        ThreadPool.getInstance().workList.add(() -> Database.removeItemAuction(itemAuctionId));
    }

    public static void addPointLoiDai(Player player, int point) {
        player.pointLoiDai += point;
        if (topTaiNang.size() < 10) {
            Vector<TopTainang> vClear = new Vector<>(topTaiNang);
            for (TopTainang topTainang : vClear) {
                if (topTainang.name.equals(player.name)) {
                    topTaiNang.remove(topTainang);
                    break;
                }
            }
            topTaiNang.add(new TopTainang(player.name, player.pointLoiDai));
        } else {
            boolean isAdd = false;
            TopTainang ttn;
            Vector<TopTainang> vClear2 = new Vector<>(topTaiNang);
            for (TopTainang topTainang : vClear2) {
                ttn = topTainang;
                if (ttn.name.equals(player.name)) {
                    topTaiNang.remove(ttn);
                    isAdd = true;
                    break;
                }
                if (player.pointLoiDai > ttn.point) {
                    isAdd = true;
                }
            }
            if (isAdd) {
                topTaiNang.add(new TopTainang(player.name, player.pointLoiDai));
            }
        }
        if (point > 0) {
            NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + point + " " + AlertFunction.POINT_LOIDAI1(player.getSession()));
        }
    }

    public static void addPointLoiDaiClass(Player player, int point) {
        player.pointLoiDaiClass += point;
        if (topTaiNangClass.get(player.classId - 1).size() < 10) {
            Vector<TopTainang> vClear = new Vector<>(topTaiNangClass.get(player.classId - 1));
            for (TopTainang topTainang : vClear) {
                if (topTainang.name.equals(player.name)) {
                    topTaiNangClass.get(player.classId - 1).remove(topTainang);
                    break;
                }
            }
            topTaiNangClass.get(player.classId - 1).add(new TopTainang(player.name, player.pointLoiDaiClass));
        } else {
            boolean isAdd = false;
            TopTainang ttn;
            Vector<TopTainang> vClear2 = new Vector<>(topTaiNangClass.get(player.classId - 1));
            for (TopTainang topTainang : vClear2) {
                ttn = topTainang;
                if (ttn.name.equals(player.name)) {
                    topTaiNangClass.get(player.classId - 1).remove(ttn);
                    isAdd = true;
                    break;
                }
                if (player.pointLoiDaiClass > ttn.point) {
                    isAdd = true;
                }
            }
            if (isAdd) {
                topTaiNangClass.get(player.classId - 1).add(new TopTainang(player.name, player.pointLoiDaiClass));
            }
        }
        if (point > 0) {
            NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + point + " " + AlertFunction.POINT_LOIDAI1(player.getSession()));
        }
    }

    public static String decode(String x, int delta) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < x.length(); ++i) {
            s.append((char) (x.charAt(i) + delta));
        }
        return s.toString();
    }

    public static PlayerCopy doCreatePlayerCopy(String namep, int gender, int headid, Player owner) {
        try {
            PlayerCopy player;
            Vector<PlayerCopy> players = Database.getPlayerCopy(owner.playerId);
            if (players.size() >= 1) {
                PlayerCopy playerCopy = players.get(0);
                player = playerCopy;
                playerCopy.playerId += 1000000000;
                player.playerId = -player.playerId;
                return player;
            }
            if (gender != 0 && gender != 1) {
                gender = 1;
            }
            if (gender == 0 && headid != 11 && headid != 26 && headid != 27 && headid != 28) {
                return null;
            }
            if (gender == 1 && headid != 2 && headid != 23 && headid != 24 && headid != 25) {
                return null;
            }
            player = new PlayerCopy(owner.getSession(), namep, gender, headid);
            if (Database.createPlayerCoppy(player, owner.playerId)) {
                player.playerId += 1000000000;
                player.playerId = -player.playerId;
                player.isPlayerCopy = true;
                return player;
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return null;
    }

    public static void doRequestIcon(Session conn, int id) {
        try {
            Message message = NJUtil.messageNotMap(Cmd.REQUEST_ICON);
            message.writeInt(id);
            byte[] data = loadSmallImage(id, conn.typeHD);
            if (data != null) {
                message.writeBytes(data);
                NJUtil.sendMessage(conn, message);
            }
        } catch (Exception e) {
        }
    }

    public static Clan getClan(String clanName) {
        for (Clan clan : clans) {
            if (clan.name.equals(clanName)) {
                return clan;
            }
        }
        return null;
    }

    public static Clan getClanPc(Player player) {
        for (int i = 0; i < clans.size(); i++) {
            Clan clan = clans.get(i);
            if ((clan.main_name != null && clan.main_name.equals(player.name)) ||
                (clan.assist_name != null && clan.assist_name.equals(player.name)) ||
                (clan.elder1_name != null && clan.elder1_name.equals(player.name)) ||
                (clan.elder2_name != null && clan.elder2_name.equals(player.name)) ||
                (clan.elder3_name != null && clan.elder3_name.equals(player.name)) ||
                (clan.elder4_name != null && clan.elder4_name.equals(player.name)) ||
                (clan.elder5_name != null && clan.elder5_name.equals(player.name))
            ) {
                Member mem;
                if (clan.main_name != null && clan.main_name.equals(player.name)) {
                    mem = new Member(4);
                } else if (clan.assist_name != null && clan.assist_name.equals(player.name)) {
                    mem = new Member(3);
                } else {
                    mem = new Member(2);
                }
                mem.classId = player.classId;
                mem.level = player.level;
                mem.name = player.name;
                mem.isOnline = true;
                clan.addMember(mem);
                return clan;
            }
        }
        return null;
    }

    public static void khoaLogin(String key) {
        if (!key.isEmpty() && hLogins.get(key) != null) {
            hLogins.get(key).count = 10;
        } else {
            hLogins.put(key, new Login(10));
        }
    }

    public static synchronized void loadImageTree(int id, int x) {
        switch (x) {
            case 1:
                try (FileInputStream fis = new FileInputStream("data/x1/item/" + id + ".png");
                     DataInputStream dis = new DataInputStream(fis)
                ) {
                    dis.read(ServerController.imageTreex1[id] = new byte[dis.available()], 0, ServerController.imageTreex1[id].length);
                } catch (Exception e) {
                }
                break;
            case 2:
                try (FileInputStream fis = new FileInputStream("data/x2/item/" + id + ".png");
                     DataInputStream dis = new DataInputStream(fis)
                ) {
                    dis.read(ServerController.imageTreex2[id] = new byte[dis.available()], 0, ServerController.imageTreex2[id].length);
                } catch (Exception e) {
                }
                break;
            case 3:
                try (FileInputStream fis = new FileInputStream("data/x3/item/" + id + ".png");
                     DataInputStream dis = new DataInputStream(fis)
                ) {
                    dis.read(ServerController.imageTreex3[id] = new byte[dis.available()], 0, ServerController.imageTreex3[id].length);
                } catch (Exception e) {
                }
                break;
            case 4:
                try (FileInputStream fis = new FileInputStream("data/x4/item/" + id + ".png");
                     DataInputStream dis = new DataInputStream(fis)
                ) {
                    dis.read(ServerController.imageTreex4[id] = new byte[dis.available()], 0, ServerController.imageTreex4[id].length);
                } catch (Exception e) {
                }
                break;
        }
    }

    public static byte[] loadSmallImage(int iconId, int zoom) {
        byte[] data = null;
        Frame f;
        try {
            f = iconFramesZoom.get(zoom - 1).get(iconId);
            if (f != null) {
                data = f.datas.get(0);
                f.time = System.currentTimeMillis();
            }
            if (data == null) {
                //noinspection SynchronizeOnNonFinalField
                synchronized (iconFramesZoom) {
                    data = NJUtil.readFileBytes("data/x" + zoom + "/Small" + iconId + ".png");
                    if (data != null) {
                        f = new Frame();
                        f.datas.add(data);
                        f.id = iconId;
                        f.time = System.currentTimeMillis();
                        iconFramesZoom.get(zoom - 1).put(iconId, f);
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return data;
    }

    public static Message messageData(Session conn) {
        Message message = null;
        try {
            message = NJUtil.messageNotMap(Cmd.UPDATE_DATA);
            message.writeByte(vData);
            message.writeBytes(dataArrow);
            message.writeBytes(dataEffect);
            message.writeBytes(dataImage);
            message.writeBytes(dataPart);
            message.writeBytes(dataSkill);
            message.writeByte(tasks.length);
            for (int i = 0; i < tasks.length; ++i) {
                message.writeByte(tasks[i].length);
                for (int j = 0; j < tasks[i].length; ++j) {
                    message.writeByte(tasks[i][j]);
                    message.writeByte(mapTasks[i][j]);
                }
            }
            int size = Player.exps1.length;
            if (!conn.isVersion108()) {
                size = 110;
            }
            message.writeByte(size);
            for (int k = 0; k < size; ++k) {
                message.writeLong(Player.exps1[k]);
            }
            message.writeByte(Player.crystals.length);
            for (int k = 0; k < Player.crystals.length; ++k) {
                message.writeInt(Player.crystals[k]);
            }
            message.writeByte(Player.upClothe.length);
            for (int k = 0; k < Player.upClothe.length; ++k) {
                message.writeInt(Player.upClothe[k]);
            }
            message.writeByte(Player.upAdorn.length);
            for (int k = 0; k < Player.upAdorn.length; ++k) {
                message.writeInt(Player.upAdorn[k]);
            }
            message.writeByte(Player.upWeapon.length);
            for (int k = 0; k < Player.upWeapon.length; ++k) {
                message.writeInt(Player.upWeapon[k]);
            }
            message.writeByte(Player.coinUpCrystals.length);
            for (int k = 0; k < Player.coinUpCrystals.length; ++k) {
                message.writeInt(Player.coinUpCrystals[k]);
            }
            message.writeByte(Player.coinUpClothes.length);
            for (int k = 0; k < Player.coinUpClothes.length; ++k) {
                message.writeInt(Player.coinUpClothes[k]);
            }
            message.writeByte(Player.coinUpAdorns.length);
            for (int k = 0; k < Player.coinUpAdorns.length; ++k) {
                message.writeInt(Player.coinUpAdorns[k]);
            }
            message.writeByte(Player.coinUpWeapons.length);
            for (int k = 0; k < Player.coinUpWeapons.length; ++k) {
                message.writeInt(Player.coinUpWeapons[k]);
            }
            message.writeByte(Player.goldUps.length);
            for (int k = 0; k < Player.goldUps.length; ++k) {
                message.writeInt(Player.goldUps[k]);
            }
            message.writeByte(Player.maxPercents.length);
            for (int k = 0; k < Player.maxPercents.length; ++k) {
                message.writeInt(Player.maxPercents[k]);
            }
            message.writeByte(effTemplates.size());
            for (EffectTemplate effTemplate : effTemplates) {
                message.writeByte(effTemplate.id);
                message.writeByte(effTemplate.type);
                message.writeUTF(effTemplate.name);
                message.writeShort(effTemplate.iconId);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return message;
    }

    public static Message messageData() {
        Message message = null;
        try {
            message = NJUtil.messageNotMap(Cmd.UPDATE_DATA);
            message.writeByte(vData);
            message.writeBytes(dataArrow);
            message.writeBytes(dataEffect);
            message.writeBytes(dataImage);
            message.writeBytes(dataPart);
            message.writeBytes(dataSkill);
            message.writeByte(tasks.length);
            for (int i = 0; i < tasks.length; ++i) {
                message.writeByte(tasks[i].length);
                for (int j = 0; j < tasks[i].length; ++j) {
                    message.writeByte(tasks[i][j]);
                    message.writeByte(mapTasks[i][j]);
                }
            }
            int size = Player.exps1.length;
            message.writeByte(size);
            for (int k = 0; k < size; ++k) {
                message.writeLong(Player.exps1[k]);
            }
            message.writeByte(Player.crystals.length);
            for (int k = 0; k < Player.crystals.length; ++k) {
                message.writeInt(Player.crystals[k]);
            }
            message.writeByte(Player.upClothe.length);
            for (int k = 0; k < Player.upClothe.length; ++k) {
                message.writeInt(Player.upClothe[k]);
            }
            message.writeByte(Player.upAdorn.length);
            for (int k = 0; k < Player.upAdorn.length; ++k) {
                message.writeInt(Player.upAdorn[k]);
            }
            message.writeByte(Player.upWeapon.length);
            for (int k = 0; k < Player.upWeapon.length; ++k) {
                message.writeInt(Player.upWeapon[k]);
            }
            message.writeByte(Player.coinUpCrystals.length);
            for (int k = 0; k < Player.coinUpCrystals.length; ++k) {
                message.writeInt(Player.coinUpCrystals[k]);
            }
            message.writeByte(Player.coinUpClothes.length);
            for (int k = 0; k < Player.coinUpClothes.length; ++k) {
                message.writeInt(Player.coinUpClothes[k]);
            }
            message.writeByte(Player.coinUpAdorns.length);
            for (int k = 0; k < Player.coinUpAdorns.length; ++k) {
                message.writeInt(Player.coinUpAdorns[k]);
            }
            message.writeByte(Player.coinUpWeapons.length);
            for (int k = 0; k < Player.coinUpWeapons.length; ++k) {
                message.writeInt(Player.coinUpWeapons[k]);
            }
            message.writeByte(Player.goldUps.length);
            for (int k = 0; k < Player.goldUps.length; ++k) {
                message.writeInt(Player.goldUps[k]);
            }
            message.writeByte(Player.maxPercents.length);
            for (int k = 0; k < Player.maxPercents.length; ++k) {
                message.writeInt(Player.maxPercents[k]);
            }
            message.writeByte(effTemplates.size());
            for (EffectTemplate effTemplate : effTemplates) {
                message.writeByte(effTemplate.id);
                message.writeByte(effTemplate.type);
                message.writeUTF(effTemplate.name);
                message.writeShort(effTemplate.iconId);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
        return message;
    }

    public static Message messageItem(Session conn) {
        Message message = null;
        try {
            message = NJUtil.messageNotMap(Cmd.UPDATE_ITEM);
            message.writeByte(vItem);
            int size = iOptionTemplates.size();
            message.writeByte(size);
            for (ItemOptionTemplate optionTemplate : iOptionTemplates) {
                message.writeUTF(optionTemplate.getName(conn));
                message.writeByte(optionTemplate.type);
            }
            message.writeShort(itemTemplates.size());
            for (ItemTemplate itemTemplate : itemTemplates) {
                message.writeByte(itemTemplate.type);
                message.writeByte(itemTemplate.gender);
                message.writeUTF(itemTemplate.getName(conn));
                message.writeUTF(itemTemplate.getDescription(conn));
                message.writeByte(itemTemplate.level);
                message.writeShort(itemTemplate.iconId);
                message.writeShort(itemTemplate.part);
                message.writeBoolean(itemTemplate.isUpToUp);
            }
        } catch (Exception e) {
        }
        return message;
    }

    public static Message messageMap(Session conn) {
        Message message = null;
        try {
            message = NJUtil.messageNotMap(Cmd.UPDATE_MAP);
            message.writeByte(vMap);
            int size = mapTemplates.size();
            message.writeByte(size);
            for (int i = 0; i < size; ++i) {
                message.writeUTF(mapTemplates.get(i).getName(conn));
            }
            message.writeByte(playerTemplates.size());
            for (PlayerTemplate playerTemplate : playerTemplates) {
                message.writeUTF(playerTemplate.getName(conn));
                message.writeShort(playerTemplate.headId);
                message.writeShort(playerTemplate.bodyId);
                message.writeShort(playerTemplate.legId);
                if (playerTemplate.getMenu() != null) {
                    message.writeByte(playerTemplate.getMenu().length);
                    for (int j = 0; j < playerTemplate.getMenu().length; ++j) {
                        message.writeByte(playerTemplate.getMenu()[j].length);
                        for (int j2 = 0; j2 < playerTemplate.getMenu()[j].length; ++j2) {
                            message.writeUTF(playerTemplate.getMenu()[j][j2]);
                        }
                    }
                } else {
                    message.writeByte(0);
                }
            }
            size = npcTemplates.size();
            message.writeByte(size);
            for (int i = 0; i < size; ++i) {
                NpcTemplate npcTemplate = npcTemplates.get(i);
                message.writeByte(npcTemplate.type);
                message.writeUTF(npcTemplate.getName(conn));
                message.writeInt(npcTemplate.hp);
                message.writeByte(npcTemplate.rangeMove);
                message.writeByte(npcTemplate.speed);
            }
        } catch (Exception e) {
        }
        return message;
    }

    public static Message messageSkill(Session conn) {
        Message message = null;
        try {
            message = NJUtil.messageNotMap(Cmd.UPDATE_SKILL);
            message.writeByte(vSkill);
            message.writeByte(sOptionTemplates.size());
            for (SkillOptionTemplate optionTemplate : sOptionTemplates) {
                message.writeUTF(optionTemplate.getName(conn));
            }
            message.writeByte(nClasss.size());
            for (NClass nClass : nClasss) {
                message.writeUTF(nClass.getName(conn));
                message.writeByte(nClass.skillTemplates.size());
                for (int j = 0; j < nClass.skillTemplates.size(); ++j) {
                    SkillTemplate template = nClass.skillTemplates.get(j);
                    message.writeByte(template.skillTemplateId);
                    message.writeUTF(template.getName(conn));
                    message.writeByte(template.maxPoint);
                    message.writeByte(template.type);
                    message.writeShort(template.iconId);
                    message.writeUTF(template.getDescription(conn));
                    message.writeByte(template.skills.size());
                    for (int k = 0; k < template.skills.size(); ++k) {
                        Skill skill = template.skills.get(k);
                        message.writeShort(skill.skillId);
                        message.writeByte(skill.point);
                        message.writeByte(skill.level);
                        message.writeShort(skill.manaUse);
                        message.writeInt(skill.timeReplay);
                        message.writeShort(skill.dx);
                        message.writeShort(skill.dy);
                        message.writeByte(skill.maxFight);
                        message.writeByte(skill.options.size());
                        for (int l = 0; l < skill.options.size(); ++l) {
                            if (skill.options.get(l).optionTemplate.skillOptionTemplateId == 37 ||
                                skill.options.get(l).optionTemplate.skillOptionTemplateId == 38 ||
                                skill.options.get(l).optionTemplate.skillOptionTemplateId == 39
                            ) {
                                message.writeShort(skill.options.get(l).param / 100);
                            } else {
                                message.writeShort(skill.options.get(l).param);
                            }
                            message.writeByte(skill.options.get(l).optionTemplate.skillOptionTemplateId);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return message;
    }

    public static void putPlayer(int userId, String username, int playerId, String name, Player player) {
        try {
            huPlayers.put(userId, player);
            if (username != null && !name.isEmpty()) {
                hunPlayers.put(username, player);
            }
            if (playerId != -1) {
                hpPlayers.put(playerId, player);
            }
            if (name != null && !name.isEmpty()) {
                hnPlayers.put(name, player);
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    public static byte[] readData() {
        Message message = messageData();
        byte[] data = message.toByteArray();
        message.cleanup();
        return data;
    }

    public static byte[] readItem(Session conn) {
        Message message = messageItem(conn);
        byte[] data = message.toByteArray();
        message.cleanup();
        return data;
    }

    public static byte[] readMap(Session conn) {
        Message message = messageMap(conn);
        byte[] data = message.toByteArray();
        message.cleanup();
        return data;
    }

    public static byte[] readSkill(Session conn) {
        Message message = messageSkill(conn);
        byte[] data = message.toByteArray();
        message.cleanup();
        return data;
    }

    public static void removePlayer(int userId, String username, int playerId, String name) {
        huPlayers.remove(userId);
        if (username != null) {
            hunPlayers.remove(username);
        }
        if (playerId != -1) {
            hpPlayers.remove(playerId);
        }
        if (name != null) {
            hnPlayers.remove(name);
        }
    }

    public static void sendNpcTemplate(Player player, int npcTemplateId) {
        try {
            NpcTemplate npcTemplate = npcTemplates.get(npcTemplateId);
            Message message = NJUtil.messageNotMap(Cmd.REQUEST_NPCTEMPLATE);
            if (!player.getSession().isVersion123()) {
                message.writeByte(npcTemplate.npcTemplateId);
            } else {
                message.writeShort(npcTemplate.npcTemplateId);
            }
            if (player.getSession().isVersion123()) {
                if (npcTemplate.isPetNew()) {
                    message.writeByte(1);
                } else {
                    message.writeByte(0);
                }
            }
            Vector<byte[]> datas;
            if (npcTemplateId == 219) {
                if (player.getSession().clientType == GameServer.CLIENT_PC) {
                    datas = player.capcha.loadImage(4);
                } else {
                    datas = player.capcha.loadImage(player.getSession().typeHD - 1);
                }
            } else if (player.getSession().clientType == GameServer.CLIENT_PC) {
                datas = npcTemplate.loadData(4);
            } else {
                datas = npcTemplate.loadData(player.getSession().typeHD - 1);
            }
            message.writeByte(datas.size());
            for (byte[] data : datas) {
                message.writeBytes(data);
            }
            message.writeBoolean(npcTemplate.isModTool());
            if (npcTemplate.isBossId() || npcTemplate.isPetNew()) {
                message.writeByte(npcTemplate.arr1.length);
                for (int i = 0; i < npcTemplate.arr1.length; ++i) {
                    message.writeByte(npcTemplate.arr1[i]);
                }
                message.writeByte(npcTemplate.arr2.length);
                for (int i = 0; i < npcTemplate.arr2.length; ++i) {
                    message.writeByte(npcTemplate.arr2[i].length);
                    for (int j = 0; j < npcTemplate.arr2[i].length; ++j) {
                        message.writeByte(npcTemplate.arr2[i][j]);
                    }
                }
            }
            message.writeBytes(npcTemplate.arr);
            NJUtil.sendMessage(player.getSession(), message);
        } catch (IOException e) {
            LOGGER.error("Username: " + player.getSession().username, e);
        }
    }
}
