package com.tgame.ninja.model;

import com.tgame.ninja.real.GemTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.real.item.*;
import com.tgame.ninja.server.NJUtil;
import com.tgame.ninja.server.ServerController;
import java.util.Vector;

public class Item {

    public static final int TYPE_BODY_MIN = 0;

    public static final int TYPE_BODY_MAX = 15;

    public static final int TYPE_NON = 0;

    public static final int TYPE_VUKHI = 1;

    public static final int TYPE_AO = 2;

    public static final int TYPE_LIEN = 3;

    public static final int TYPE_GANGTAY = 4;

    public static final int TYPE_NHAN = 5;

    public static final int TYPE_QUAN = 6;

    public static final int TYPE_NGOCBOI = 7;

    public static final int TYPE_GIAY = 8;

    public static final int TYPE_PHU = 9;

    public static final int TYPE_THUNUOI = 10;

    public static final int TYPE_MATNA = 11;

    public static final int TYPE_AOCHOANG = 12;

    public static final int TYPE_GIATOC = 13;

    public static final int TYPE_NGUYETNHAN = 14;

    public static final int TYPE_BIKIP = 15;

    public static final int TYPE_HP = 16;

    public static final int TYPE_MP = 17;

    public static final int TYPE_EAT = 18;

    public static final int TYPE_MONEY = 19;

    public static final int TYPE_TUI_TIEN = 20;

    public static final int TYPE_MEAT = 21;

    public static final int TYPE_DRAGONBALL = 22;

    public static final int TYPE_TASK_SAVE = 23;

    public static final int TYPE_TASK_WAIT = 24;

    public static final int TYPE_TASK = 25;

    public static final int TYPE_CRYSTAL = 26;

    public static final int TYPE_ORDER = 27;

    public static final int TYPE_PROTECT = 28;

    public static final int TYPE_MON0 = 29;

    public static final int TYPE_MON1 = 30;

    public static final int TYPE_MON2 = 31;

    public static final int TYPE_MON3 = 32;

    public static final int TYPE_MON4 = 33;

    public static final int TYPE_GEM = 34;

    public static final int TYPE_SYS_NORMAL = 0;

    public static final int TYPE_SYS_HOA = 1;

    public static final int TYPE_SYS_THUY = 2;

    public static final int TYPE_SYS_PHONG = 3;

    public static final byte UI_WEAPON = 2;

    public static final byte UI_BAG = 3;

    public static final byte UI_BOX = 4;

    public static final byte UI_BODY = 5;

    public static final byte UI_STACK = 6;

    public static final byte UI_STACK_LOCK = 7;

    public static final byte UI_GROCERY = 8;

    public static final byte UI_GROCERY_LOCK = 9;

    public static final byte UI_UPGRADE = 10;

    public static final byte UI_UPPEARL = 11;

    public static final byte UI_UPPEARL_LOCK = 12;

    public static final byte UI_SPLIT = 13;

    public static final byte UI_STORE = 14;

    public static final byte UI_BOOK = 15;

    public static final byte UI_LIEN = 16;

    public static final byte UI_NHAN = 17;

    public static final byte UI_NGOCBOI = 18;

    public static final byte UI_PHU = 19;

    public static final byte UI_NONNAM = 20;

    public static final byte UI_NONNU = 21;

    public static final byte UI_AONAM = 22;

    public static final byte UI_AONU = 23;

    public static final byte UI_GANGTAYNAM = 24;

    public static final byte UI_GANGTAYNU = 25;

    public static final byte UI_QUANNAM = 26;

    public static final byte UI_QUANNU = 27;

    public static final byte UI_GIAYNAM = 28;

    public static final byte UI_GIAYNU = 29;

    public static final byte UI_TRADE = 30;

    public static final byte UI_UPGRADE_GOLD = 31;

    public static final byte UI_FASHION = 32;

    public static final byte UI_CONVERT = 33;

    public static final byte UI_CLANSHOP = 34;

    public static final byte UI_ELITES = 35;

    public static final byte UI_AUCTION_SALE = 36;

    public static final byte UI_AUCTION_BUY = 37;

    public static final byte UI_LUCKY_SPIN = 38;

    public static final byte UI_CLAN = 39;

    public static final byte UI_MON = 41;

    public static final byte UI_NAP_GOOGLE = 42;

    public static final byte UI_LUYEN_THACH = 43;

    public static final byte UI_TINH_LUYEN = 44;

    public static final byte UI_DOI_OPTION = 45;

    public static final byte UI_LUYEN_NGOC = 46;

    public static final byte UI_KHAM_NGOC = 47;

    public static final byte UI_TASK_ITEM = 48;

    public static final byte UI_GOT_NGOC = 49;

    public static final byte UI_THAO_NGOC = 50;

    public static final int CHUYEN_TINH_THACH = 454;

    public static final int TU_TINH_THACH_SO = 455;

    public static final int TU_TINH_THACH_TRUNG = 456;

    public static final int TU_TINH_THACH_CAO = 457;

    public static final int NGUYET_NHAN_CAP_1 = 685;

    public static final int NGUYET_NHAN_CAP_2 = 686;

    public static final int NGUYET_NHAN_CAP_3 = 687;

    public static final int NGUYET_NHAN_CAP_4 = 688;

    public static final int NGUYET_NHAN_CAP_5 = 689;

    public static final int NGUYET_NHAN_CAP_6 = 690;

    public static final int NGUYET_NHAN_CAP_7 = 691;

    public static final int NGUYET_NHAN_CAP_8 = 692;

    public static final int NGUYET_NHAN_CAP_9 = 693;

    public static final int NGUYET_NHAN_CAP_10 = 694;

    public static final int DANH_VONG_CAP_1 = 695;

    public static final int DANH_VONG_CAP_2 = 696;

    public static final int DANH_VONG_CAP_3 = 697;

    public static final int DANH_VONG_CAP_4 = 698;

    public static final int DANH_VONG_CAP_5 = 699;

    public static final int DANH_VONG_CAP_6 = 700;

    public static final int DANH_VONG_CAP_7 = 701;

    public static final int DANH_VONG_CAP_8 = 702;

    public static final int DANH_VONG_CAP_9 = 703;

    public static final int DANH_VONG_CAP_10 = 704;

    public static int[] OPTION_NAME_NGOC = { 109, 110, 111, 112 };

    public static int OPT_VU_KHI = 106;

    public static int OPT_TRANG_BI = 107;

    public static int OPT_TRANG_SUC = 108;

    public static int OPT_EXP_UPGRADE = 104;

    public static int OPT_YEN_THAO_NGOC = 122;

    public static int OPT_GIA_KHAM_NGOC = 123;

    public static int exps = 1000;

    public static int[] yenTinhLuyen = { 150000, 247500, 408375, 673819, 1111801, 2056832, 4010822, 7420021, 12243035 };

    public ItemTemplate template;

    public Vector<ItemOption> options;

    public long itemId;

    public int playerId;

    public String playerName;

    public int typeUI;

    public int indexUI;

    public int quantity;

    public long expires;

    public boolean isLock;

    public int sys;

    public int upgrade;

    public int buyCoin;

    public int buyCoinLock;

    public int buyGold;

    public int buyGoldLock;

    public int saleCoinLock;

    public int saleShop;

    public Vector<Item> gem;

    public String vitri;

    public String dateCreate;

    public int mapIDBoss;

    public Item() {
        gem = new Vector<>();
        vitri = "";
        dateCreate = "";
        mapIDBoss = -1;
        quantity = 1;
        dateCreate = NJUtil.getDateTime();
    }

    public Item(int itemTemplateId) {
        gem = new Vector<>();
        vitri = "";
        dateCreate = "";
        mapIDBoss = -1;
        try {
            template = ServerController.itemTemplates.get(itemTemplateId);
            quantity = 1;
            expires = -1L;
        } catch (Exception e) {
        }
        dateCreate = NJUtil.getDateTime();
    }

    public Item(int itemTemplateId, boolean isLock, String vitri) {
        gem = new Vector<>();
        this.vitri = "";
        dateCreate = "";
        mapIDBoss = -1;
        try {
            template = ServerController.itemTemplates.get(itemTemplateId);
            quantity = 1;
            expires = -1L;
            this.isLock = isLock;
            this.vitri = vitri;
        } catch (Exception e) {
        }
        dateCreate = NJUtil.getDateTime();
    }

    public Item(int itemTemplateId, int indexUI, String vitri) {
        gem = new Vector<>();
        this.vitri = "";
        dateCreate = "";
        mapIDBoss = -1;
        try {
            template = ServerController.itemTemplates.get(itemTemplateId);
            quantity = 1;
            this.indexUI = indexUI;
            this.vitri = vitri;
        } catch (Exception e) {
        }
        dateCreate = NJUtil.getDateTime();
    }

    public Item(int itemTemplateId, int playerId, boolean isLock, String vitri) {
        gem = new Vector<>();
        this.vitri = "";
        dateCreate = "";
        mapIDBoss = -1;
        try {
            template = ServerController.itemTemplates.get(itemTemplateId);
            this.playerId = playerId;
            this.isLock = isLock;
            expires = -1L;
            quantity = 1;
            this.vitri = vitri;
            if (isTypeBody()) {
                sys = NJUtil.randomNumber(6) + 1;
            }
        } catch (Exception e) {
        }
        dateCreate = NJUtil.getDateTime();
    }

    public boolean canSell() {
        return getTemplateId() < 618 || getTemplateId() > 637;
    }

    public void changeOption(ItemOption option) {
        if (option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 31) {
            if (template.level > 20 && template.level < 30) {
                option.param = 2;
            } else if (template.level > 30 && template.level < 40) {
                option.param = 3;
            } else if (template.level > 40 && template.level < 50) {
                option.param = 4;
            } else if (template.level > 50 && template.level < 60) {
                option.param = 5;
            }
        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
            if (template.level > 20 && template.level < 30) {
                option.param = 200;
            } else if (template.level > 30 && template.level < 40) {
                option.param = 250;
            } else if (template.level > 40 && template.level < 50) {
                option.param = 300;
            } else if (template.level > 50 && template.level < 60) {
                option.param = 350;
            }
        } else if (option.optionTemplate.itemOptionTemplateId == 37) {
            if (template.level == 20) {
                option.param = 20;
            } else if (template.level == 30) {
                option.param = 30;
            } else if (template.level == 40) {
                option.param = 40;
            } else if (template.level == 50) {
                option.param = 50;
            }
        } else if (option.optionTemplate.itemOptionTemplateId == 38) {
            if (template.level == 20) {
                option.param = 200;
            } else if (template.level == 30) {
                option.param = 300;
            } else if (template.level == 40) {
                option.param = 400;
            } else if (template.level == 50) {
                option.param = 500;
            }
        }
    }

    public boolean checkKhamNgoc(Item it) {
        for (Item item : gem) {
            if (item.template.itemTemplateId == it.template.itemTemplateId) {
                return false;
            }
        }
        return true;
    }

    public Item cloneItem() {
        Item newItem = new Item(template.itemTemplateId);
        newItem.playerId = playerId;
        newItem.typeUI = typeUI;
        newItem.indexUI = indexUI;
        newItem.quantity = quantity;
        newItem.expires = expires;
        newItem.isLock = isLock;
        newItem.sys = sys;
        newItem.upgrade = upgrade;
        newItem.buyCoin = buyCoin;
        newItem.buyCoinLock = buyCoinLock;
        newItem.buyGold = buyGold;
        newItem.buyGoldLock = buyGoldLock;
        newItem.saleCoinLock = saleCoinLock;
        newItem.vitri = vitri;
        newItem.mapIDBoss = mapIDBoss;
        newItem.dateCreate = dateCreate;
        if (options != null) {
            newItem.options = new Vector<>();
            for (int i = 0; i < options.size(); ++i) {
                ItemOption option = new ItemOption();
                option.optionTemplate = options.get(i).optionTemplate;
                option.param = options.get(i).param;
                newItem.options.add(option);
            }
        }
        newItem.gem = gem;
        newItem.updateSale();
        return newItem;
    }

    public Item cloneRandom() {
        Item newItem = new Item(template.itemTemplateId);
        newItem.playerId = playerId;
        newItem.typeUI = typeUI;
        newItem.indexUI = indexUI;
        newItem.quantity = quantity;
        newItem.expires = expires;
        newItem.isLock = isLock;
        newItem.sys = sys;
        newItem.upgrade = upgrade;
        newItem.buyCoin = buyCoin;
        newItem.buyCoinLock = buyCoinLock;
        newItem.buyGold = buyGold;
        newItem.buyGoldLock = buyGoldLock;
        newItem.saleCoinLock = saleCoinLock;
        newItem.vitri = vitri;
        newItem.mapIDBoss = mapIDBoss;
        newItem.options = cloneRandomOption();
        newItem.gem = gem;
        newItem.updateSale();
        return newItem;
    }

    public Vector<ItemOption> cloneRandomOption() {
        Vector<ItemOption> newoptions = null;
        if (options != null) {
            newoptions = new Vector<>();
            for (ItemOption itemOption : options) {
                int randomParam = 0;
                ItemOption option = new ItemOption();
                option.optionTemplate = itemOption.optionTemplate;
                if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1 || option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                    randomParam = NJUtil.randomNumber(50);
                } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                    randomParam = NJUtil.randomNumber(10);
                } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                    randomParam = NJUtil.randomNumber(5);
                } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                    randomParam = NJUtil.randomNumber(3);
                }
                option.param = itemOption.param - randomParam;
                newoptions.add(option);
            }
        }
        return newoptions;
    }

    public void createOptionItemGem() {
        if (!isTypeGem()) {
            return;
        }
        isLock = false;
        upgrade = 1;
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.param = 0;
        option.optionTemplate = ServerController.iOptionTemplates.get(OPT_VU_KHI);
        options.add(option);
        int id = GemTemplate.ID_OPTION_GEM[template.itemTemplateId - 652][0];
        option = new ItemOption();
        option.param = NJUtil.randomMinMax(GemTemplate.VALUE[template.itemTemplateId - 652][0], GemTemplate.VALUE[template.itemTemplateId - 652][1]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        id = GemTemplate.ID_OPTION_GEM_SUB[template.itemTemplateId - 652][0];
        option = new ItemOption();
        option.param = -NJUtil.randomMinMax(GemTemplate.VALUE_SUB[template.itemTemplateId - 652][0], GemTemplate.VALUE_SUB[template.itemTemplateId - 652][1]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        option = new ItemOption();
        option.param = 0;
        option.optionTemplate = ServerController.iOptionTemplates.get(OPT_TRANG_BI);
        options.add(option);
        id = GemTemplate.ID_OPTION_GEM[template.itemTemplateId - 652][1];
        option = new ItemOption();
        option.param = NJUtil.randomMinMax(GemTemplate.VALUE[template.itemTemplateId - 652][2], GemTemplate.VALUE[template.itemTemplateId - 652][3]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        id = GemTemplate.ID_OPTION_GEM_SUB[template.itemTemplateId - 652][1];
        option = new ItemOption();
        option.param = -NJUtil.randomMinMax(GemTemplate.VALUE_SUB[template.itemTemplateId - 652][2], GemTemplate.VALUE_SUB[template.itemTemplateId - 652][3]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        option = new ItemOption();
        option.param = 0;
        option.optionTemplate = ServerController.iOptionTemplates.get(OPT_TRANG_SUC);
        options.add(option);
        id = GemTemplate.ID_OPTION_GEM[template.itemTemplateId - 652][2];
        option = new ItemOption();
        option.param = NJUtil.randomMinMax(GemTemplate.VALUE[template.itemTemplateId - 652][4], GemTemplate.VALUE[template.itemTemplateId - 652][5]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        id = GemTemplate.ID_OPTION_GEM_SUB[template.itemTemplateId - 652][2];
        option = new ItemOption();
        option.param = -NJUtil.randomMinMax(GemTemplate.VALUE_SUB[template.itemTemplateId - 652][4], GemTemplate.VALUE_SUB[template.itemTemplateId - 652][5]);
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        id = OPT_EXP_UPGRADE;
        option = new ItemOption();
        option.param = 0;
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
        id = OPT_GIA_KHAM_NGOC;
        option = new ItemOption();
        option.param = Player.price_kham_gem[upgrade];
        option.optionTemplate = ServerController.iOptionTemplates.get(id);
        options.add(option);
    }

    public void createOptionNguyetNhan() {
        int addMore = getTemplateId() - 685;
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(6);
        option.param = 1000 + 1000 * addMore;
        options.add(option);
        option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(87);
        option.param = 500 + 250 * addMore;
        options.add(option);
        upgrade = addMore + 1;
        if (template.itemTemplateId >= 687) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(79);
            option.param = 25;
            options.add(option);
        }
        if (template.itemTemplateId >= 690) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(64);
            option.param = 10;
            options.add(option);
        }
        if (template.itemTemplateId >= 694) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(113);
            option.param = 5000;
            options.add(option);
        }
    }

    public void createOptionPetTuanLoc() {
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(6);
        option.param = 5000;
        options.add(option);
        option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(87);
        option.param = 5000;
        options.add(option);
    }

    public void createOptionPetBiReHanh() {
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(5);
        option.param = 1000;
        options.add(option);
    }

    public void doChangeTemplate(int idtemplate) {
        try {
            template = ServerController.itemTemplates.get(idtemplate);
            quantity = 1;
        } catch (Exception e) {
        }
    }

    public String getAllInfo() {
        StringBuilder info = new StringBuilder(getTemplateId() + "_" + sys + "_" + typeUI + "_" + indexUI + "_" + expires + "_" + isLock + "_" + upgrade + "_" + getOptionInfo() + "_" + quantity + "_" + saleCoinLock + "_" + vitri + "_" + mapIDBoss + "_" + dateCreate);
        if (gem.size() > 0) {
            info.append("|");
            for (Item item : gem) {
                info.append(item.getAllInfo()).append("^");
            }
            info = new StringBuilder(info.substring(0, info.length() - 1));
        }
        return info.toString();
    }

    public int getLevelGem() {
        int lv = 1;
        for (Item item : gem) {
            if (item.upgrade > lv) {
                lv = item.upgrade;
            }
        }
        return lv;
    }

    public ItemOption getOptionByID(int id) {
        if (options != null) {
            for (ItemOption option : options) {
                if (option.optionTemplate.itemOptionTemplateId == id) {
                    return option;
                }
            }
        }
        return null;
    }

    public Vector<ItemOption> getOptionGem() {
        Vector<ItemOption> op = new Vector<>();
        if (gem != null && gem.size() > 0) {
            try {
                for (Item it : gem) {
                    ItemOption option = new ItemOption();
                    option.param = 0;
                    int a = it.template.itemTemplateId - 652;
                    if (a >= 0 && a < OPTION_NAME_NGOC.length) {
                        int index = OPTION_NAME_NGOC[a];
                        option.optionTemplate = ServerController.iOptionTemplates.get(index);
                        op.add(option);
                        Vector<ItemOption> itop = it.getOptions();
                        int idopGet = OPT_VU_KHI;
                        if (!isTypeWeapon()) {
                            if (isTypeClothe()) {
                                idopGet = OPT_TRANG_BI;
                            } else if (isTypeAdorn()) {
                                idopGet = OPT_TRANG_SUC;
                            }
                        }
                        for (int j = 0; j < itop.size(); ++j) {
                            ItemOption gop = itop.get(j);
                            int idop = gop.optionTemplate.itemOptionTemplateId;
                            if (idop == idopGet) {
                                op.add(itop.get(j + 1));
                                op.add(itop.get(j + 2));
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return op;
    }

    public String getOptionInfo() {
        StringBuilder info = new StringBuilder();
        if (options != null) {
            for (int i = 0; i < options.size(); ++i) {
                info.append(options.get(i).optionTemplate.itemOptionTemplateId).append(",").append(options.get(i).param);
                if (i < options.size() - 1) {
                    info.append(";");
                }
            }
        }
        return info.toString();
    }

    public static Vector<ItemOption> getOptionTemplate(Item item) {
        for (int i = 0; i < ServerController.itemDefaults.size(); ++i) {
            if (ServerController.itemDefaults.get(i).template.type == item.template.type && ServerController.itemDefaults.get(i).sys == item.sys) {
                Vector<ItemOption> options = new Vector<>();
                for (int j = 0; j < ServerController.itemDefaults.get(i).options.size(); ++j) {
                    ItemOption option = new ItemOption();
                    option.optionTemplate = ServerController.itemDefaults.get(i).options.get(j).optionTemplate;
                    if (option.optionTemplate.itemOptionTemplateId == 47) {
                        if (item.template.level > 10) {
                            if (item.template.type == 2 || item.template.type == 6) {
                                option.param = item.template.level / 10 * 4;
                            } else {
                                option.param = item.template.level / 10 * 2;
                            }
                        } else if (item.template.type == 2 || item.template.type == 6) {
                            option.param = 2;
                        } else {
                            option.param = 1;
                        }
                    }
                    if (item.template.level >= 10 && item.template.level < 20) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 10;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 5;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 3;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 5;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35 || option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 5;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 30;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 50;
                        }
                    } else if (item.template.level >= 20 && item.template.level < 30) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 120;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 20;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 10;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 200;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 6;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 6;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 60;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35 || option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 120;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 10;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 32;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 55;
                        }
                    } else if (item.template.level >= 30 && item.template.level < 40) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 140;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 30;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 15;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 300;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 9;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 7;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 70;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35 || option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 140;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 15;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 150;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 34;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 60;
                        }
                    } else if (item.template.level >= 40 && item.template.level < 50) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 160;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 40;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 20;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 400;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 12;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 8;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 80;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35 || option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 160;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 20;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 200;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 36;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 65;
                        }
                    } else if (item.template.level >= 50 && item.template.level < 60) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 180;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 25;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 500;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 15;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 9;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 90;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 25;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 250;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 38;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 70;
                        }
                    } else if (item.template.level >= 60 && item.template.level < 70) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 200;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 60;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 30;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 600;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 18;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 10;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35) {
                            option.param = 120;
                        } else if (option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 60;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 30;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 300;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 25;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 80;
                        }
                    } else if (item.template.level >= 70 && item.template.level < 80) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 240;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 80;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 40;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 1000;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 24;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 12;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 120;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35) {
                            option.param = 140;
                        } else if (option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 80;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 40;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 400;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 35;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 100;
                        }
                    } else if (item.template.level >= 80 && item.template.level < 90) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 280;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 1400;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 30;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 14;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 140;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35) {
                            option.param = 160;
                        } else if (option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 100;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 50;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 500;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 45;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 120;
                        }
                    } else if (item.template.level >= 90 && item.template.level < 100) {
                        if (option.optionTemplate.itemOptionTemplateId == 0 || option.optionTemplate.itemOptionTemplateId == 1) {
                            option.param = 500;
                        } else if (option.optionTemplate.itemOptionTemplateId == 6 || option.optionTemplate.itemOptionTemplateId == 7 || option.optionTemplate.itemOptionTemplateId == 8 || option.optionTemplate.itemOptionTemplateId == 9 || option.optionTemplate.itemOptionTemplateId == 19) {
                            option.param = 150;
                        } else if (option.optionTemplate.itemOptionTemplateId == 2 || option.optionTemplate.itemOptionTemplateId == 3 || option.optionTemplate.itemOptionTemplateId == 4 || option.optionTemplate.itemOptionTemplateId == 5 || option.optionTemplate.itemOptionTemplateId == 10 || option.optionTemplate.itemOptionTemplateId == 11 || option.optionTemplate.itemOptionTemplateId == 12 || option.optionTemplate.itemOptionTemplateId == 13 || option.optionTemplate.itemOptionTemplateId == 14 || option.optionTemplate.itemOptionTemplateId == 15 || option.optionTemplate.itemOptionTemplateId == 17 || option.optionTemplate.itemOptionTemplateId == 18 || option.optionTemplate.itemOptionTemplateId == 20) {
                            option.param = 60;
                        } else if (option.optionTemplate.itemOptionTemplateId == 21 || option.optionTemplate.itemOptionTemplateId == 22 || option.optionTemplate.itemOptionTemplateId == 23 || option.optionTemplate.itemOptionTemplateId == 24 || option.optionTemplate.itemOptionTemplateId == 25 || option.optionTemplate.itemOptionTemplateId == 26) {
                            option.param = 1800;
                        } else if (option.optionTemplate.itemOptionTemplateId == 16) {
                            option.param = 36;
                        } else if (option.optionTemplate.itemOptionTemplateId == 27 || option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 30 || option.optionTemplate.itemOptionTemplateId == 31) {
                            option.param = 16;
                        } else if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                            option.param = 160;
                        } else if (option.optionTemplate.itemOptionTemplateId == 33 || option.optionTemplate.itemOptionTemplateId == 34 || option.optionTemplate.itemOptionTemplateId == 35) {
                            option.param = 180;
                        } else if (option.optionTemplate.itemOptionTemplateId == 36) {
                            option.param = 120;
                        } else if (option.optionTemplate.itemOptionTemplateId == 37 || option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                            option.param = 60;
                        } else if (option.optionTemplate.itemOptionTemplateId == 38 || option.optionTemplate.itemOptionTemplateId == 43 || option.optionTemplate.itemOptionTemplateId == 44 || option.optionTemplate.itemOptionTemplateId == 45) {
                            option.param = 600;
                        } else if (option.optionTemplate.itemOptionTemplateId == 46) {
                            option.param = 55;
                        } else if (option.optionTemplate.itemOptionTemplateId == 39) {
                            option.param = 140;
                        }
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 29 || option.optionTemplate.itemOptionTemplateId == 32) {
                        option.param = item.template.level / 10 * 50 + 100;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 28 || option.optionTemplate.itemOptionTemplateId == 31) {
                        option.param = item.template.level / 10;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 37) {
                        option.param = item.template.level / 10 * 10;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 37) {
                        option.param = item.template.level / 10 * 10;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 38) {
                        option.param = item.template.level / 10 * 100;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 40 || option.optionTemplate.itemOptionTemplateId == 41 || option.optionTemplate.itemOptionTemplateId == 42) {
                        option.param = item.template.level / 10 * 100;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 48 || option.optionTemplate.itemOptionTemplateId == 49 || option.optionTemplate.itemOptionTemplateId == 50) {
                        option.param = 500;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 51 || option.optionTemplate.itemOptionTemplateId == 52 || option.optionTemplate.itemOptionTemplateId == 53) {
                        option.param = 800;
                    }
                    if (option.optionTemplate.itemOptionTemplateId == 54 || option.optionTemplate.itemOptionTemplateId == 55 || option.optionTemplate.itemOptionTemplateId == 56) {
                        option.param = 40;
                    }
                    if (option.optionTemplate.type == 3) {
                        if (item.template.level >= 10) {
                            options.add(option);
                        }
                    } else if (option.optionTemplate.type == 4) {
                        if (item.template.level >= 20) {
                            options.add(option);
                        }
                    } else if (option.optionTemplate.type == 5) {
                        if (item.template.level >= 40) {
                            options.add(option);
                        }
                    } else if (option.optionTemplate.type == 6) {
                        if (item.template.level >= 50) {
                            options.add(option);
                        }
                    } else if (option.optionTemplate.type == 7) {
                        if (item.template.level >= 60) {
                            options.add(option);
                        }
                    } else {
                        options.add(option);
                    }
                }
                if (item.sys >= 4) {
                    item.sys -= 3;
                }
                return options;
            }
        }
        if (item.sys >= 4) {
            item.sys -= 3;
        }
        if (item.getTemplateId() == 258 || item.getTemplateId() == 259) {
            (item.options = new Vector<>()).add(new ItemOption(30, 5));
            return item.options;
        }
        if (item.getTemplateId() == 273 || item.getTemplateId() == 274) {
            (item.options = new Vector<>()).add(new ItemOption(50, 5));
            item.options.add(new ItemOption(500, 6));
            return item.options;
        }
        if (item.getTemplateId() == 286 || item.getTemplateId() == 287) {
            (item.options = new Vector<>()).add(new ItemOption(300, 0));
            item.options.add(new ItemOption(300, 1));
            return item.options;
        }
        if (item.getTemplateId() == 306 || item.getTemplateId() == 307) {
            (item.options = new Vector<>()).add(new ItemOption(100, 2));
            item.options.add(new ItemOption(100, 3));
            item.options.add(new ItemOption(100, 4));
            return item.options;
        }
        if (item.getTemplateId() == 351 || item.getTemplateId() == 352) {
            (item.options = new Vector<>()).add(new ItemOption(100, 2));
            item.options.add(new ItemOption(100, 3));
            item.options.add(new ItemOption(100, 4));
            item.options.add(new ItemOption(150, 8));
            item.options.add(new ItemOption(150, 9));
            return item.options;
        }
        if (item.getTemplateId() == 353 || item.getTemplateId() == 354) {
            (item.options = new Vector<>()).add(new ItemOption(500, 6));
            item.options.add(new ItemOption(10, 58));
            return item.options;
        }
        if (item.getTemplateId() == 405 || item.getTemplateId() == 406) {
            (item.options = new Vector<>()).add(new ItemOption(50, 5));
            item.options.add(new ItemOption(50, 8));
            item.options.add(new ItemOption(50, 9));
            item.options.add(new ItemOption(1000, 6));
            return item.options;
        }
        if (item.getTemplateId() == 423) {
            (item.options = new Vector<>()).add(new ItemOption(200, 6));
            item.options.add(new ItemOption(100, 7));
            item.options.add(new ItemOption(30, 10));
            item.options.add(new ItemOption(50, 5));
            item.options.add(new ItemOption(10, 2));
            item.options.add(new ItemOption(10, 3));
            item.options.add(new ItemOption(10, 4));
            return item.options;
        }
        if (item.getTemplateId() == 424) {
            (item.options = new Vector<>()).add(new ItemOption(350, 6));
            item.options.add(new ItemOption(200, 7));
            item.options.add(new ItemOption(70, 10));
            item.options.add(new ItemOption(100, 5));
            item.options.add(new ItemOption(20, 2));
            item.options.add(new ItemOption(20, 3));
            item.options.add(new ItemOption(20, 4));
            return item.options;
        }
        if (item.getTemplateId() == 425) {
            (item.options = new Vector<>()).add(new ItemOption(700, 6));
            item.options.add(new ItemOption(400, 7));
            item.options.add(new ItemOption(120, 10));
            item.options.add(new ItemOption(150, 5));
            item.options.add(new ItemOption(40, 2));
            item.options.add(new ItemOption(40, 3));
            item.options.add(new ItemOption(40, 4));
            return item.options;
        }
        if (item.getTemplateId() == 426) {
            (item.options = new Vector<>()).add(new ItemOption(1200, 6));
            item.options.add(new ItemOption(650, 7));
            item.options.add(new ItemOption(220, 10));
            item.options.add(new ItemOption(300, 5));
            item.options.add(new ItemOption(60, 2));
            item.options.add(new ItemOption(60, 3));
            item.options.add(new ItemOption(60, 4));
            return item.options;
        }
        if (item.getTemplateId() == 427) {
            (item.options = new Vector<>()).add(new ItemOption(2000, 6));
            item.options.add(new ItemOption(1000, 7));
            item.options.add(new ItemOption(400, 10));
            item.options.add(new ItemOption(500, 5));
            item.options.add(new ItemOption(80, 2));
            item.options.add(new ItemOption(80, 3));
            item.options.add(new ItemOption(80, 4));
            return item.options;
        }
        if (item.getTemplateId() == 512 || item.getTemplateId() == 513) {
            (item.options = new Vector<>()).add(new ItemOption(3000, 87));
            item.options.add(new ItemOption(1000, 6));
            return item.options;
        }
        if (item.isMatNaSonTinhThuyTinh()) {
            (item.options = new Vector<>()).add(new ItemOption(3000, 87));
            item.options.add(new ItemOption(1000, 6));
            return item.options;
        }
        if (item.getTemplateId() == 613 || item.getTemplateId() == 614) {
            (item.options = new Vector<>()).add(new ItemOption(3000, 87));
            item.options.add(new ItemOption(15, 100));
            return item.options;
        }
        if (item.getTemplateId() == 615 || item.getTemplateId() == 616) {
            (item.options = new Vector<>()).add(new ItemOption(3500, 87));
            item.options.add(new ItemOption(20, 100));
            return item.options;
        }
        if (item.isMatNaChuot() || item.isMatNaBiNgo()) {
            (item.options = new Vector<>()).add(new ItemOption(2000, 6));
            item.options.add(new ItemOption(20, 58));
            return item.options;
        }
        return null;
    }

    public static Item getOptionThrow(Item item) {
        //noinspection ConstantConditions
        item = getOptionThrowNotClear(item);
        if (item.options != null) {
            for (int i = item.options.size() - 1; i >= 0; --i) {
                if (NJUtil.randomBoolean() && item.options.get(i).optionTemplate.type < 3) {
                    item.options.remove(i);
                }
            }
        }
        return item;
    }

    public static Item getOptionThrowNotClear(Item item) {
        int[] idLevel0 = { 195, 196, 197, 198, 199, 200, 201, 202, 203, 204, 205, 206, 207, 208 };
        if (item.template.itemTemplateId == 194) {
            (item.options = new Vector<>()).add(new ItemOption(NJUtil.randomNumber(50) + 1, 0));
            item.options.add(new ItemOption(NJUtil.randomNumber(50) + 1, 1));
            item.options.add(new ItemOption(NJUtil.randomNumber(10) + 1, 8));
            item.options.add(new ItemOption(NJUtil.randomNumber(10) + 1, 9));
        } else {
            for (int j : idLevel0) {
                if (item.template.itemTemplateId == j) {
                    (item.options = new Vector<>()).add(new ItemOption(1, 47));
                    item.options.add(new ItemOption(NJUtil.randomNumber(5) + 1, 6));
                    item.options.add(new ItemOption(NJUtil.randomNumber(5) + 1, 7));
                    break;
                }
            }
        }
        switch (item.template.type) {
            case 0:
                for (int i = 0; i < ServerController.shopNonNams.size(); ++i) {
                    if (item.sys == ServerController.shopNonNams.get(i).sys && item.template.equals(ServerController.shopNonNams.get(i).template)) {
                        item.options = ServerController.shopNonNams.get(i).cloneRandomOption();
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopNonNus.size(); ++i) {
                    if (item.sys == ServerController.shopNonNus.get(i).sys && item.template.equals(ServerController.shopNonNus.get(i).template)) {
                        item.options = ServerController.shopNonNus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < ServerController.shopVuKhis.size(); ++i) {
                    if (item.sys == ServerController.shopVuKhis.get(i).sys && item.template.equals(ServerController.shopVuKhis.get(i).template)) {
                        item.options = ServerController.shopVuKhis.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < ServerController.shopAoNams.size(); ++i) {
                    if (item.sys == ServerController.shopAoNams.get(i).sys && item.template.equals(ServerController.shopAoNams.get(i).template)) {
                        item.options = ServerController.shopAoNams.get(i).cloneRandomOption();
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopAoNus.size(); ++i) {
                    if (item.sys == ServerController.shopAoNus.get(i).sys && item.template.equals(ServerController.shopAoNus.get(i).template)) {
                        item.options = ServerController.shopAoNus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < ServerController.shopLiens.size(); ++i) {
                    if (item.sys == ServerController.shopLiens.get(i).sys && item.template.equals(ServerController.shopLiens.get(i).template)) {
                        item.options = ServerController.shopLiens.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < ServerController.shopGangTayNams.size(); ++i) {
                    if (item.sys == ServerController.shopGangTayNams.get(i).sys && item.template.equals(ServerController.shopGangTayNams.get(i).template)) {
                        item.options = ServerController.shopGangTayNams.get(i).cloneRandomOption();
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopGangTayNus.size(); ++i) {
                    if (item.sys == ServerController.shopGangTayNus.get(i).sys && item.template.equals(ServerController.shopGangTayNus.get(i).template)) {
                        item.options = ServerController.shopGangTayNus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 5:
                for (int i = 0; i < ServerController.shopNhans.size(); ++i) {
                    if (item.sys == ServerController.shopNhans.get(i).sys && item.template.equals(ServerController.shopNhans.get(i).template)) {
                        item.options = ServerController.shopNhans.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 6:
                for (int i = 0; i < ServerController.shopQuanNams.size(); ++i) {
                    if (item.sys == ServerController.shopQuanNams.get(i).sys && item.template.equals(ServerController.shopQuanNams.get(i).template)) {
                        item.options = ServerController.shopQuanNams.get(i).cloneRandomOption();
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopQuanNus.size(); ++i) {
                    if (item.sys == ServerController.shopQuanNus.get(i).sys && item.template.equals(ServerController.shopQuanNus.get(i).template)) {
                        item.options = ServerController.shopQuanNus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 7:
                for (int i = 0; i < ServerController.shopNgocBois.size(); ++i) {
                    if (item.sys == ServerController.shopNgocBois.get(i).sys && item.template.equals(ServerController.shopNgocBois.get(i).template)) {
                        item.options = ServerController.shopNgocBois.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 8:
                for (int i = 0; i < ServerController.shopGiayNams.size(); ++i) {
                    if (item.sys == ServerController.shopGiayNams.get(i).sys && item.template.equals(ServerController.shopGiayNams.get(i).template)) {
                        item.options = ServerController.shopGiayNams.get(i).cloneRandomOption();
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopGiayNus.size(); ++i) {
                    if (item.sys == ServerController.shopGiayNus.get(i).sys && item.template.equals(ServerController.shopGiayNus.get(i).template)) {
                        item.options = ServerController.shopGiayNus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
            case 9:
                for (int i = 0; i < ServerController.shopPhus.size(); ++i) {
                    if (item.sys == ServerController.shopPhus.get(i).sys && item.template.equals(ServerController.shopPhus.get(i).template)) {
                        item.options = ServerController.shopPhus.get(i).cloneRandomOption();
                        break;
                    }
                }
                break;
        }
        return item;
    }

    public static Item getOptionThrowNotClear9x(Item item, boolean isMax) {
        switch (item.template.type) {
            case 0:
                for (int i = 0; i < ServerController.shopNonNams_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopNonNams_not_sell.get(i).sys && item.template.equals(ServerController.shopNonNams_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopNonNams_not_sell.get(i).cloneItem().options : ServerController.shopNonNams_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopNonNus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopNonNus_not_sell.get(i).sys && item.template.equals(ServerController.shopNonNus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopNonNus_not_sell.get(i).cloneItem().options : ServerController.shopNonNus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 1:
                for (int i = 0; i < ServerController.shopVuKhis_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopVuKhis_not_sell.get(i).sys && item.template.equals(ServerController.shopVuKhis_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopVuKhis_not_sell.get(i).cloneItem().options : ServerController.shopVuKhis_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < ServerController.shopAoNams_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopAoNams_not_sell.get(i).sys && item.template.equals(ServerController.shopAoNams_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopAoNams_not_sell.get(i).cloneItem().options : ServerController.shopAoNams_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopAoNus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopAoNus_not_sell.get(i).sys && item.template.equals(ServerController.shopAoNus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopAoNus_not_sell.get(i).cloneItem().options : ServerController.shopAoNus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 3:
                for (int i = 0; i < ServerController.shopLiens_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopLiens_not_sell.get(i).sys && item.template.equals(ServerController.shopLiens_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopLiens_not_sell.get(i).cloneItem().options : ServerController.shopLiens_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 4:
                for (int i = 0; i < ServerController.shopGangTayNams_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopGangTayNams_not_sell.get(i).sys && item.template.equals(ServerController.shopGangTayNams_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopGangTayNams_not_sell.get(i).cloneItem().options : ServerController.shopGangTayNams_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopGangTayNus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopGangTayNus_not_sell.get(i).sys && item.template.equals(ServerController.shopGangTayNus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopGangTayNus_not_sell.get(i).cloneItem().options : ServerController.shopGangTayNus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 5:
                for (int i = 0; i < ServerController.shopNhans_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopNhans_not_sell.get(i).sys && item.template.equals(ServerController.shopNhans_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopNhans_not_sell.get(i).cloneItem().options : ServerController.shopNhans_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 6:
                for (int i = 0; i < ServerController.shopQuanNams_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopQuanNams_not_sell.get(i).sys && item.template.equals(ServerController.shopQuanNams_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopQuanNams_not_sell.get(i).cloneItem().options : ServerController.shopQuanNams_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopQuanNus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopQuanNus_not_sell.get(i).sys && item.template.equals(ServerController.shopQuanNus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopQuanNus_not_sell.get(i).cloneItem().options : ServerController.shopQuanNus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 7:
                for (int i = 0; i < ServerController.shopNgocBois_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopNgocBois_not_sell.get(i).sys && item.template.equals(ServerController.shopNgocBois_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopNgocBois_not_sell.get(i).cloneItem().options : ServerController.shopNgocBois_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 8:
                for (int i = 0; i < ServerController.shopGiayNams_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopGiayNams_not_sell.get(i).sys && item.template.equals(ServerController.shopGiayNams_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopGiayNams_not_sell.get(i).cloneItem().options : ServerController.shopGiayNams_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                for (int i = 0; i < ServerController.shopGiayNus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopGiayNus_not_sell.get(i).sys && item.template.equals(ServerController.shopGiayNus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopGiayNus_not_sell.get(i).cloneItem().options : ServerController.shopGiayNus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
            case 9:
                for (int i = 0; i < ServerController.shopPhus_not_sell.size(); ++i) {
                    if (item.sys == ServerController.shopPhus_not_sell.get(i).sys && item.template.equals(ServerController.shopPhus_not_sell.get(i).template)) {
                        item.options = (isMax ? ServerController.shopPhus_not_sell.get(i).cloneItem().options : ServerController.shopPhus_not_sell.get(i).cloneRandomOption());
                        break;
                    }
                }
                break;
        }
        return item;
    }

    public Vector<ItemOption> getOptions() {
        Vector<ItemOption> op = new Vector<>();
        if (options != null) {
            for (ItemOption itemOption : options) {
                ItemOption option = new ItemOption();
                option.optionTemplate = itemOption.optionTemplate;
                option.param = itemOption.param;
                op.add(option);
            }
        }
        if (gem != null && gem.size() > 0) {
            try {
                for (Item it : gem) {
                    ItemOption option2 = new ItemOption();
                    option2.param = 0;
                    int a = it.template.itemTemplateId - 652;
                    if (a >= 0 && a < OPTION_NAME_NGOC.length) {
                        int index = OPTION_NAME_NGOC[a];
                        option2.optionTemplate = ServerController.iOptionTemplates.get(index);
                        op.add(option2);
                        Vector<ItemOption> itop = it.getOptions();
                        int idopGet = OPT_VU_KHI;
                        if (!isTypeWeapon()) {
                            if (isTypeClothe()) {
                                idopGet = OPT_TRANG_BI;
                            } else if (isTypeAdorn()) {
                                idopGet = OPT_TRANG_SUC;
                            }
                        }
                        for (int j = 0; j < itop.size(); ++j) {
                            ItemOption gop = itop.get(j);
                            int idop = gop.optionTemplate.itemOptionTemplateId;
                            if (idop == idopGet) {
                                op.add(itop.get(j + 1));
                                op.add(itop.get(j + 2));
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return op;
    }

    public int getTemplateId() {
        return template.itemTemplateId;
    }

    public int getUpMax() {
        if (template.level >= 1 && template.level < 20) {
            return 4;
        }
        if (template.level >= 20 && template.level < 40) {
            return 8;
        }
        if (template.level >= 40 && template.level < 50) {
            return 12;
        }
        if (template.level >= 50 && template.level < 60) {
            return 14;
        }
        return 16;
    }

    public void initInfoNgocKham(String info) {
        try {
            String[] data = info.split("\\^");
            for (String datum : data) {
                String[] itemInfo = datum.split("_");
                Item item = new Item(Integer.parseInt(itemInfo[0].trim()));
                item.playerId = playerId;
                item.sys = Integer.parseInt(itemInfo[1].trim());
                if (item.sys > 3 && !item.isTypeMon()) {
                    item.sys -= 3;
                }
                item.typeUI = Integer.parseInt(itemInfo[2].trim());
                item.indexUI = Integer.parseInt(itemInfo[3].trim());
                item.expires = Long.parseLong(itemInfo[4].trim());
                item.isLock = Boolean.parseBoolean(itemInfo[5].trim());
                item.upgrade = Integer.parseInt(itemInfo[6].trim());
                item.quantity = Integer.parseInt(itemInfo[8].trim());
                item.saleCoinLock = Integer.parseInt(itemInfo[9].trim());
                try {
                    item.mapIDBoss = Integer.parseInt(itemInfo[11].trim());
                } catch (Exception e) {
                }
                String optionInfo = itemInfo[7].trim();
                if (!optionInfo.isEmpty()) {
                    item.options = new Vector<>();
                    String[] optionInfos = optionInfo.split(";");
                    for (String s : optionInfos) {
                        String[] datas = s.split(",");
                        int optionTemplateId = Integer.parseInt(datas[0]);
                        ItemOption option = new ItemOption();
                        option.param = Integer.parseInt(datas[1]);
                        option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                        item.changeOption(option);
                        item.options.add(option);
                    }
                }
                if (item.options == null || item.options.size() == 0) {
                    item.createOptionItemGem();
                }
                gem.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDaTinhLuyen() {
        return getTemplateId() == 454 || getTemplateId() == 455 || getTemplateId() == 456 || getTemplateId() == 457;
    }

    public boolean isGem() {
        return isTypeGem();
    }

    public boolean isItemClass0() {
        return getTemplateId() == 194 || getTemplateId() == 94 || getTemplateId() == 95 || getTemplateId() == 96 || getTemplateId() == 97 || getTemplateId() == 98 || getTemplateId() == 331;
    }

    public boolean isItemClass1() {
        return getTemplateId() == 632 || getTemplateId() == 506 || getTemplateId() == 194 || getTemplateId() == 94 || getTemplateId() == 95 || getTemplateId() == 96 || getTemplateId() == 97 || getTemplateId() == 98 || getTemplateId() == 331 || getTemplateId() == 369;
    }

    public boolean isItemClass2() {
        return getTemplateId() == 633 || getTemplateId() == 507 || getTemplateId() == 114 || getTemplateId() == 115 || getTemplateId() == 116 || getTemplateId() == 117 || getTemplateId() == 118 || getTemplateId() == 332 || getTemplateId() == 370;
    }

    public boolean isItemClass3() {
        return getTemplateId() == 634 || getTemplateId() == 508 || getTemplateId() == 99 || getTemplateId() == 100 || getTemplateId() == 101 || getTemplateId() == 102 || getTemplateId() == 103 || getTemplateId() == 333 || getTemplateId() == 371;
    }

    public boolean isItemClass4() {
        return getTemplateId() == 635 || getTemplateId() == 509 || getTemplateId() == 109 || getTemplateId() == 110 || getTemplateId() == 111 || getTemplateId() == 112 || getTemplateId() == 113 || getTemplateId() == 334 || getTemplateId() == 372;
    }

    public boolean isItemClass5() {
        return getTemplateId() == 636 || getTemplateId() == 510 || getTemplateId() == 104 || getTemplateId() == 105 || getTemplateId() == 106 || getTemplateId() == 107 || getTemplateId() == 108 || getTemplateId() == 335 || getTemplateId() == 373;
    }

    public boolean isItemClass6() {
        return getTemplateId() == 637 || getTemplateId() == 511 || getTemplateId() == 119 || getTemplateId() == 120 || getTemplateId() == 121 || getTemplateId() == 122 || getTemplateId() == 123 || getTemplateId() == 336 || getTemplateId() == 374;
    }

    public boolean isXichNhanNganLang() {
        return getTemplateId() == 443;
    }

    public boolean isHuyetSacHungLang() {
        return getTemplateId() == 523;
    }

    public boolean isXeMay() {
        return getTemplateId() == 485;
    }

    public boolean isXeHarley() {
        return getTemplateId() == 524;
    }

    public boolean isThucAnSoi() {
        return (getTemplateId() >= 449 && getTemplateId() <= 453) || (getTemplateId() >= 573 && getTemplateId() <= 575);
    }

    public boolean isNhienLieuXe() {
        return (getTemplateId() >= 470 && getTemplateId() <= 472) || (getTemplateId() >= 576 && getTemplateId() <= 578);
    }

    public boolean isThucAnTrau() {
        return getTemplateId() == 778;
    }

    public boolean isSoi() {
        return isXichNhanNganLang() || isHuyetSacHungLang();
    }

    public boolean isXe() {
        return isXeMay() || isXeHarley();
    }

    public boolean isTrau() {
        return isTrauDen() || isTrauVang();
    }

    public boolean isTrangBiThu() {
        return getTemplateId() == 439 ||
            getTemplateId() == 440 ||
            getTemplateId() == 441 ||
            getTemplateId() == 442 ||
            getTemplateId() == 486 ||
            getTemplateId() == 487 ||
            getTemplateId() == 488 ||
            getTemplateId() == 489;
    }

    public boolean isMatNaThuyTinh() {
        return getTemplateId() == 541;
    }

    public boolean isMatNaSonTinh() {
        return getTemplateId() == 542;
    }

    public boolean isMatNaThanhGiong() {
        return getTemplateId() == 594;
    }

    public boolean isMatNaJrai() {
        return getTemplateId() == 711;
    }

    public boolean isMatNaJumito() {
        return getTemplateId() == 714;
    }

    public boolean ispetTuanLocKhangDs() {
        return getTemplateId() == 744;
    }

    public boolean isMatNaChuot() {
        return getTemplateId() == 745;
    }

    public boolean isMatNaBiNgo() {
        return getTemplateId() == 771;
    }

    public boolean isPetChimTinhAnh() {
        return getTemplateId() == 419;
    }

    public boolean isPetTuanLoc() {
        return getTemplateId() == 742;
    }

    public boolean isPetReBiHanh() {
        return getTemplateId() == 772;
    }

    public boolean ispetReBiHanhKhangDs() {
        return getTemplateId() == 773;
    }

    public boolean isPetBoru() {
        return getTemplateId() == 781;
    }

    public boolean isMatNaSantaClaus() {
        return getTemplateId() == 774;
    }

    public boolean isTrauDen() {
        return getTemplateId() == 776;
    }

    public boolean isTrauVang() {
        return getTemplateId() == 777;
    }

    public boolean isPetNew() {
        return (getTemplateId() >= 583 && getTemplateId() <= 589) || isPetTuanLoc() || ispetTuanLocKhangDs() || isPetReBiHanh() || ispetReBiHanhKhangDs() || isPetBoru();
    }

    public boolean isMatNaSonTinhThuyTinh() {
        return isMatNaSonTinh() || isMatNaThuyTinh();
    }

    public boolean isMatNaThoiTrang() {
        return isMatNaChuot() || isMatNaSonTinhThuyTinh() || isMatNaThanhGiong() || isMatNaBiNgo() || isMatNaSantaClaus();
    }

    public boolean isNguyetNhanBug() {
        return upgrade > 1 || getTemplateId() > NGUYET_NHAN_CAP_1;
    }

    public boolean isNguyetNhanCap1() {
        return getTemplateId() == NGUYET_NHAN_CAP_1;
    }

    public boolean isSaveLog() {
        return (getTemplateId() >= 5 && getTemplateId() <= 11) || (getTemplateId() >= 40 && getTemplateId() <= 93) || getTemplateId() >= 36 || getTemplateId() <= 37 || getTemplateId() <= 215 || getTemplateId() <= 229;
    }

    public boolean isTrungThanThu() {
        return getTemplateId() == 601 || getTemplateId() == 596;
    }

    public boolean isTypeBody() {
        return template.type >= 0 && template.type <= 15;
    }

    public boolean isTypeWeapon() {
        return template.type == 1;
    }

    public boolean isTypeClothe() {
        return template.type == 0 || template.type == 2 || template.type == 4 || template.type == 6 || template.type == 8;
    }

    public boolean isTypeAdorn() {
        return template.type == 3 || template.type == 5 || template.type == 7 || template.type == 9;
    }

    public boolean isTypeYoroi() {
        return template.type == 12;
    }

    public boolean isTypeNguyetNhan() {
        return template.type == 14;
    }

    public boolean isTypeBiKip() {
        return template.type == 15;
    }

    public boolean isTypeHP() {
        return template.type == 16;
    }

    public boolean isTypeMP() {
        return template.type == 17;
    }

    public boolean isTypeThucAn() {
        return template.type == 18;
    }

    public boolean isTypeCrystal() {
        return template.type == 26;
    }

    public boolean isTypeMon() {
        return 29 <= template.type && template.type <= 33;
    }

    public boolean isTypeGem() {
        return template.type == 34;
    }

    public boolean isTypeUIMe() {
        return typeUI == 3 || typeUI == 4 || typeUI == 5 || typeUI == 39;
    }

    public boolean isTypeUIShopLock() {
        return typeUI == 7 || typeUI == 9;
    }

    public boolean isTypeUIStore() {
        return typeUI == 14;
    }

    public boolean isTypeUIBook() {
        return typeUI == 15;
    }

    public boolean isTypeUIShop() {
        return typeUI == 20 || typeUI == 21 || typeUI == 22 || typeUI == 23 || typeUI == 24 || typeUI == 25 || typeUI == 26 || typeUI == 27 || typeUI == 28 || typeUI == 29 || typeUI == 16 || typeUI == 17 || typeUI == 18 || typeUI == 19 || typeUI == 2 || typeUI == 6 || typeUI == 8;
    }

    public boolean isTypeUIFashion() {
        return typeUI == 32;
    }

    public boolean isTypeUIClanShop() {
        return typeUI == 34;
    }

    public static Item parseItem(String str, int playerId) {
        try {
            if (str.isEmpty()) {
                return null;
            }
            String[] data = str.split("\\|");
            String[] itemInfo = data[0].split("_");
            Item item = new Item(Integer.parseInt(itemInfo[0].trim()));
            item.playerId = playerId;
            item.sys = Integer.parseInt(itemInfo[1].trim());
            if (item.sys > 3 && !item.isTypeMon()) {
                item.sys -= 3;
            }
            item.typeUI = Integer.parseInt(itemInfo[2].trim());
            item.indexUI = Integer.parseInt(itemInfo[3].trim());
            item.expires = Long.parseLong(itemInfo[4].trim());
            item.isLock = Boolean.parseBoolean(itemInfo[5].trim());
            item.upgrade = Integer.parseInt(itemInfo[6].trim());
            item.quantity = Integer.parseInt(itemInfo[8].trim());
            item.saleCoinLock = Integer.parseInt(itemInfo[9].trim());
            try {
                item.mapIDBoss = Integer.parseInt(itemInfo[11].trim());
            } catch (Exception e) {
            }
            try {
                item.dateCreate = itemInfo[12];
            } catch (Exception e) {
            }
            String optionInfo = itemInfo[7].trim();
            boolean isHaveOp128 = false;
            if (!optionInfo.isEmpty()) {
                item.options = new Vector<>();
                String[] optionInfos = optionInfo.split(";");
                for (String info : optionInfos) {
                    String[] datas = info.split(",");
                    int optionTemplateId = Integer.parseInt(datas[0]);
                    ItemOption option = new ItemOption();
                    option.param = Integer.parseInt(datas[1]);
                    option.optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
                    item.changeOption(option);
                    item.options.add(option);
                    if (optionTemplateId == 128) {
                        isHaveOp128 = true;
                    }
                }
            }
            if (item.isTrauDen()) {
                if (item.options == null || item.options.size() == 0) {
                    item.setOptionDefault();
                } else if (!isHaveOp128) {
                    ItemOption option = new ItemOption();
                    option.param = 5;
                    option.optionTemplate = ServerController.iOptionTemplates.get(128);
                    item.options.add(option);
                }
            }
            if (item.expires == -1L && (item.getTemplateId() == 37 || item.getTemplateId() == 337 || item.getTemplateId() == 338)) {
                item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
            }
            if (item.getTemplateId() == 310) {
                item.expires = -1L;
            }
            try {
                item.vitri = itemInfo[10].trim();
            } catch (Exception e) {
                item.vitri = "";
            }
            if (item.template.type == 11 && item.expires == -1L) {
                item.expires = System.currentTimeMillis() + NJUtil.getMilisByDay(7);
            }
            if (item.isTypeYoroi() && item.options == null) {
                item.createOptionYoroi();
            }
            if (item.getTemplateId() == 524 && item.options == null) {
                item.options = new Vector<>();
                ItemOption option = new ItemOption();
                option.param = 0;
                option.optionTemplate = ServerController.iOptionTemplates.get(65);
                item.options.add(option);
                option = new ItemOption();
                option.param = 1000;
                option.optionTemplate = ServerController.iOptionTemplates.get(66);
                item.options.add(option);
            }
            if (item.getTemplateId() == 458) {
                item.expires = 1L;
            }
            if (data.length > 1) {
                item.initInfoNgocKham(data[1]);
            }
            return item;
        } catch (Exception ex3) {
            ex3.printStackTrace();
        }
        return null;
    }

    public static void changeItemOption(Item item, int countUp) {
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

    public static int randomItem(int[] ids) {
        return ids[NJUtil.randomNumber(ids.length)];
    }

    public void setTimeExpire(long minute) {
        expires = System.currentTimeMillis() + minute * 60000L;
    }

    public void createOptionYoroi() {
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.param = 0;
        option.optionTemplate = ServerController.iOptionTemplates.get(85);
        options.add(option);
        option = new ItemOption();
        option.param = 350;
        option.optionTemplate = ServerController.iOptionTemplates.get(82);
        options.add(option);
        option = new ItemOption();
        option.param = 350;
        option.optionTemplate = ServerController.iOptionTemplates.get(83);
        options.add(option);
        option = new ItemOption();
        option.param = 100;
        option.optionTemplate = ServerController.iOptionTemplates.get(84);
        options.add(option);
        option = new ItemOption();
        option.param = 5;
        option.optionTemplate = ServerController.iOptionTemplates.get(81);
        options.add(option);
        option = new ItemOption();
        option.param = 25;
        option.optionTemplate = ServerController.iOptionTemplates.get(80);
        options.add(option);
        option = new ItemOption();
        option.param = 5;
        option.optionTemplate = ServerController.iOptionTemplates.get(79);
        options.add(option);
    }

    public void createOptionBiKip() {
        options = new Vector<>();
        ItemOption option;
        if (getTemplateId() == 397 || getTemplateId() == 398) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(3);
            option.param = 1000;
            options.add(option);
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(88);
            option.param = 1000;
            options.add(option);
        } else if (getTemplateId() == 399 || getTemplateId() == 400) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(4);
            option.param = 1000;
            options.add(option);
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(89);
            option.param = 1000;
            options.add(option);
        } else if (getTemplateId() == 401 || getTemplateId() == 402) {
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(2);
            option.param = 1000;
            options.add(option);
            option = new ItemOption();
            option.optionTemplate = ServerController.iOptionTemplates.get(90);
            option.param = 1000;
            options.add(option);
        }
    }

    public void updateSale() {
        if (options != null) {
            int count = 0;
            for (ItemOption option : options) {
                if (option.optionTemplate.type == 2) {
                    ++count;
                }
            }
            if (count >= 2) {
                if (isTypeClothe()) {
                    saleCoinLock = template.level / 10 * 150 * count;
                } else if (isTypeAdorn()) {
                    saleCoinLock = template.level / 10 * 200 * count;
                } else if (isTypeWeapon()) {
                    saleCoinLock = template.level / 10 * 250 * count;
                }
            } else {
                saleCoinLock = 5;
            }
        }
    }

    public void setOptionDefault() {
        if (isTrauDen() || isTrauVang()) {
            options = new Vector<>();
            ItemOption option = new ItemOption();
            option.param = 0;
            option.param = 1000;
            option.optionTemplate = ServerController.iOptionTemplates.get(65);
            options.add(option);
            option = new ItemOption();
            option.param = 1000;
            option.optionTemplate = ServerController.iOptionTemplates.get(66);
            options.add(option);
            option = new ItemOption();
            option.param = 5;
            option.optionTemplate = ServerController.iOptionTemplates.get(128);
            options.add(option);
        }
    }

    public void createOptionThuCuoi() {
        Vector<ItemOption> options = new Vector<>();
        int valuex = 1;
        if (getTemplateId() == 523) {
            valuex = 10;
        }
        options.add(new ItemOption(50 * valuex, 6));
        options.add(new ItemOption(50 * valuex, 7));
        options.add(new ItemOption(10 * valuex, 10));
        options.add(new ItemOption(10 * valuex, 68));
        options.add(new ItemOption(10 * valuex, 69));
        options.add(new ItemOption(5 * valuex, 70));
        options.add(new ItemOption(5 * valuex, 71));
        options.add(new ItemOption(5 * valuex, 72));
        options.add(new ItemOption(100 * valuex, 73));
        options.add(new ItemOption(5 * valuex, 67));
        options.add(new ItemOption(50 * valuex, 74));
        for (int i = 0; i < 4; ++i) {
            int index = NJUtil.randomNumber(options.size());
            this.options.add(options.remove(index));
        }
    }

    public void createOptionBoru() {
        options = new Vector<>();
        ItemOption option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(6);
        option.param = 5000;
        options.add(option);
        option = new ItemOption();
        option.optionTemplate = ServerController.iOptionTemplates.get(87);
        option.param = 5000;
        options.add(option);
    }

    public void randomGetOptionTrangBiThu(boolean isRandom) {
        if ((!isRandom || NJUtil.random.nextBoolean())) {
            if (getTemplateId() == 439 || getTemplateId() == 486) {
                options = new Vector<>();
                ItemOption option = new ItemOption();
                option.param = 0;
                option.optionTemplate = ServerController.iOptionTemplates.get(85);
                options.add(option);
                option = new ItemOption();
                option.param = 50;
                option.optionTemplate = ServerController.iOptionTemplates.get(75);
                options.add(option);
            }
            if (getTemplateId() == 440 || getTemplateId() == 487) {
                options = new Vector<>();
                ItemOption option = new ItemOption();
                option.param = 0;
                option.optionTemplate = ServerController.iOptionTemplates.get(85);
                options.add(option);
                option = new ItemOption();
                option.param = 500;
                option.optionTemplate = ServerController.iOptionTemplates.get(76);
                options.add(option);
            }
            if (getTemplateId() == 441 || getTemplateId() == 488) {
                options = new Vector<>();
                ItemOption option = new ItemOption();
                option.param = 0;
                option.optionTemplate = ServerController.iOptionTemplates.get(85);
                options.add(option);
                option = new ItemOption();
                option.param = 500;
                option.optionTemplate = ServerController.iOptionTemplates.get(77);
                options.add(option);
            }
            if (getTemplateId() == 442 || getTemplateId() == 489) {
                options = new Vector<>();
                ItemOption option = new ItemOption();
                option.param = 0;
                option.optionTemplate = ServerController.iOptionTemplates.get(85);
                options.add(option);
                option = new ItemOption();
                option.param = 50;
                option.optionTemplate = ServerController.iOptionTemplates.get(78);
                options.add(option);
            }
        }
    }

    public static void loadItemFunction() {
        Potion itemPotion = new Potion();
        ServerController.hUsableItems.put(13, itemPotion);
        ServerController.hUsableItems.put(14, itemPotion);
        ServerController.hUsableItems.put(15, itemPotion);
        ServerController.hUsableItems.put(16, itemPotion);
        ServerController.hUsableItems.put(17, itemPotion);
        ServerController.hUsableItems.put(18, itemPotion);
        ServerController.hUsableItems.put(19, itemPotion);
        ServerController.hUsableItems.put(20, itemPotion);
        ServerController.hUsableItems.put(21, itemPotion);
        ServerController.hUsableItems.put(22, itemPotion);
        ServerController.hUsableItems.put(565, itemPotion);
        ServerController.hUsableItems.put(566, itemPotion);
        Food itemFood = new Food();
        ServerController.hUsableItems.put(23, itemFood);
        ServerController.hUsableItems.put(24, itemFood);
        ServerController.hUsableItems.put(25, itemFood);
        ServerController.hUsableItems.put(26, itemFood);
        ServerController.hUsableItems.put(27, itemFood);
        ServerController.hUsableItems.put(29, itemFood);
        ServerController.hUsableItems.put(30, itemFood);
        ServerController.hUsableItems.put(249, itemFood);
        ServerController.hUsableItems.put(250, itemFood);
        ServerController.hUsableItems.put(409, itemFood);
        ServerController.hUsableItems.put(410, itemFood);
        ServerController.hUsableItems.put(567, itemFood);
        ServerController.hUsableItems.put(28, new NhanSamNganNam());
        BachBienLenh itemBBL = new BachBienLenh();
        ServerController.hUsableItems.put(34, itemBBL);
        ServerController.hUsableItems.put(36, itemBBL);
        ServerController.hUsableItems.put(38, new TuiPhuc());
        SkillBook itemSkillBook = new SkillBook();
        ServerController.hUsableItems.put(40, itemSkillBook);
        ServerController.hUsableItems.put(41, itemSkillBook);
        ServerController.hUsableItems.put(42, itemSkillBook);
        ServerController.hUsableItems.put(43, itemSkillBook);
        ServerController.hUsableItems.put(44, itemSkillBook);
        ServerController.hUsableItems.put(45, itemSkillBook);
        ServerController.hUsableItems.put(46, itemSkillBook);
        ServerController.hUsableItems.put(47, itemSkillBook);
        ServerController.hUsableItems.put(48, itemSkillBook);
        ServerController.hUsableItems.put(49, itemSkillBook);
        ServerController.hUsableItems.put(50, itemSkillBook);
        ServerController.hUsableItems.put(51, itemSkillBook);
        ServerController.hUsableItems.put(52, itemSkillBook);
        ServerController.hUsableItems.put(53, itemSkillBook);
        ServerController.hUsableItems.put(54, itemSkillBook);
        ServerController.hUsableItems.put(55, itemSkillBook);
        ServerController.hUsableItems.put(56, itemSkillBook);
        ServerController.hUsableItems.put(57, itemSkillBook);
        ServerController.hUsableItems.put(58, itemSkillBook);
        ServerController.hUsableItems.put(59, itemSkillBook);
        ServerController.hUsableItems.put(60, itemSkillBook);
        ServerController.hUsableItems.put(61, itemSkillBook);
        ServerController.hUsableItems.put(62, itemSkillBook);
        ServerController.hUsableItems.put(63, itemSkillBook);
        ServerController.hUsableItems.put(64, itemSkillBook);
        ServerController.hUsableItems.put(65, itemSkillBook);
        ServerController.hUsableItems.put(66, itemSkillBook);
        ServerController.hUsableItems.put(67, itemSkillBook);
        ServerController.hUsableItems.put(68, itemSkillBook);
        ServerController.hUsableItems.put(69, itemSkillBook);
        ServerController.hUsableItems.put(70, itemSkillBook);
        ServerController.hUsableItems.put(71, itemSkillBook);
        ServerController.hUsableItems.put(72, itemSkillBook);
        ServerController.hUsableItems.put(73, itemSkillBook);
        ServerController.hUsableItems.put(74, itemSkillBook);
        ServerController.hUsableItems.put(75, itemSkillBook);
        ServerController.hUsableItems.put(76, itemSkillBook);
        ServerController.hUsableItems.put(77, itemSkillBook);
        ServerController.hUsableItems.put(78, itemSkillBook);
        ServerController.hUsableItems.put(79, itemSkillBook);
        ServerController.hUsableItems.put(80, itemSkillBook);
        ServerController.hUsableItems.put(81, itemSkillBook);
        ServerController.hUsableItems.put(82, itemSkillBook);
        ServerController.hUsableItems.put(83, itemSkillBook);
        ServerController.hUsableItems.put(84, itemSkillBook);
        ServerController.hUsableItems.put(85, itemSkillBook);
        ServerController.hUsableItems.put(86, itemSkillBook);
        ServerController.hUsableItems.put(87, itemSkillBook);
        ServerController.hUsableItems.put(88, itemSkillBook);
        ServerController.hUsableItems.put(89, itemSkillBook);
        ServerController.hUsableItems.put(90, itemSkillBook);
        ServerController.hUsableItems.put(91, itemSkillBook);
        ServerController.hUsableItems.put(92, itemSkillBook);
        ServerController.hUsableItems.put(93, itemSkillBook);
        ServerController.hUsableItems.put(311, itemSkillBook);
        ServerController.hUsableItems.put(312, itemSkillBook);
        ServerController.hUsableItems.put(313, itemSkillBook);
        ServerController.hUsableItems.put(314, itemSkillBook);
        ServerController.hUsableItems.put(315, itemSkillBook);
        ServerController.hUsableItems.put(316, itemSkillBook);
        ServerController.hUsableItems.put(375, itemSkillBook);
        ServerController.hUsableItems.put(376, itemSkillBook);
        ServerController.hUsableItems.put(377, itemSkillBook);
        ServerController.hUsableItems.put(378, itemSkillBook);
        ServerController.hUsableItems.put(379, itemSkillBook);
        ServerController.hUsableItems.put(380, itemSkillBook);
        ServerController.hUsableItems.put(547, itemSkillBook);
        ServerController.hUsableItems.put(552, itemSkillBook);
        ServerController.hUsableItems.put(553, itemSkillBook);
        ServerController.hUsableItems.put(554, itemSkillBook);
        ServerController.hUsableItems.put(555, itemSkillBook);
        ServerController.hUsableItems.put(556, itemSkillBook);
        ServerController.hUsableItems.put(557, itemSkillBook);
        ServerController.hUsableItems.put(558, itemSkillBook);
        ServerController.hUsableItems.put(559, itemSkillBook);
        ServerController.hUsableItems.put(560, itemSkillBook);
        ServerController.hUsableItems.put(561, itemSkillBook);
        ServerController.hUsableItems.put(562, itemSkillBook);
        ServerController.hUsableItems.put(563, itemSkillBook);
        TuiVai itemTuiVai = new TuiVai();
        ServerController.hUsableItems.put(215, itemTuiVai);
        ServerController.hUsableItems.put(229, itemTuiVai);
        ServerController.hUsableItems.put(283, itemTuiVai);
        ServerController.hUsableItems.put(219, new BinhRong());
        ServerController.hUsableItems.put(231, new ChiaKhoaCoQuan());
        DiaDo itemDiaDo = new DiaDo();
        ServerController.hUsableItems.put(233, itemDiaDo);
        ServerController.hUsableItems.put(234, itemDiaDo);
        ServerController.hUsableItems.put(235, itemDiaDo);
        ServerController.hUsableItems.put(240, new TayTiemNang());
        ServerController.hUsableItems.put(241, new TayKyNang());
        LinhChi itemExp = new LinhChi();
        ServerController.hUsableItems.put(248, itemExp);
        ServerController.hUsableItems.put(539, itemExp);
        ServerController.hUsableItems.put(540, itemExp);
        ServerController.hUsableItems.put(251, new GiayVun());
        ServerController.hUsableItems.put(252, new SachKyNang());
        ServerController.hUsableItems.put(253, new SachTiemNang());
        HoanCotChiThu itemHCCT = new HoanCotChiThu();
        ServerController.hUsableItems.put(254, itemHCCT);
        ServerController.hUsableItems.put(255, itemHCCT);
        ServerController.hUsableItems.put(256, itemHCCT);
        ServerController.hUsableItems.put(257, new HoanLuongChiThao());
        ServerController.hUsableItems.put(261, new LamThaoDuoc());
        ServerController.hUsableItems.put(263, new TuiQuaGiaToc());
        ServerController.hUsableItems.put(266, new CanCau());
        ServerController.hUsableItems.put(268, new TaThuLenh());
        RuongHangDong ruongHangDong = new RuongHangDong();
        ServerController.hUsableItems.put(272, ruongHangDong);
        ServerController.hUsableItems.put(282, ruongHangDong);
        ServerController.hUsableItems.put(647, ruongHangDong);
        DanDuoc itemDanDuoc = new DanDuoc();
        ServerController.hUsableItems.put(275, itemDanDuoc);
        ServerController.hUsableItems.put(276, itemDanDuoc);
        ServerController.hUsableItems.put(277, itemDanDuoc);
        ServerController.hUsableItems.put(278, itemDanDuoc);
        ServerController.hUsableItems.put(279, new VanBienLenh());
        ServerController.hUsableItems.put(280, new LenhBaiHangDong());
        ServerController.hUsableItems.put(281, new LenhBaiGiaToc());
        ServerController.hUsableItems.put(288, new ThatThuBao());
        TheBaiTT itemTBTT = new TheBaiTT();
        ServerController.hUsableItems.put(289, itemTBTT);
        ServerController.hUsableItems.put(290, itemTBTT);
        ServerController.hUsableItems.put(291, itemTBTT);
        BanhTrungThu itemBTT = new BanhTrungThu();
        ServerController.hUsableItems.put(298, itemBTT);
        ServerController.hUsableItems.put(299, itemBTT);
        ServerController.hUsableItems.put(300, itemBTT);
        ServerController.hUsableItems.put(301, itemBTT);
        PhongLoiBangHoa itemPLBH = new PhongLoiBangHoa();
        ServerController.hUsableItems.put(308, itemPLBH);
        ServerController.hUsableItems.put(309, itemPLBH);
        RuongTrangBi ruongTrangBi = new RuongTrangBi();
        ServerController.hUsableItems.put(383, ruongTrangBi);
        ServerController.hUsableItems.put(384, ruongTrangBi);
        ServerController.hUsableItems.put(385, ruongTrangBi);
        SummerKite summerKite = new SummerKite();
        ServerController.hUsableItems.put(434, summerKite);
        ServerController.hUsableItems.put(435, summerKite);
        ExpGiaToc itemExpGT = new ExpGiaToc();
        ServerController.hUsableItems.put(436, itemExpGT);
        ServerController.hUsableItems.put(437, itemExpGT);
        ServerController.hUsableItems.put(438, itemExpGT);
        SPMount itemSPMount = new SPMount();
        ServerController.hUsableItems.put(444, itemSPMount);
        ServerController.hUsableItems.put(469, itemSPMount);
        ServerController.hUsableItems.put(779, itemSPMount);
        ExpMount itemExpMount = new ExpMount();
        ServerController.hUsableItems.put(449, itemExpMount);
        ServerController.hUsableItems.put(450, itemExpMount);
        ServerController.hUsableItems.put(451, itemExpMount);
        ServerController.hUsableItems.put(452, itemExpMount);
        ServerController.hUsableItems.put(453, itemExpMount);
        ServerController.hUsableItems.put(470, itemExpMount);
        ServerController.hUsableItems.put(471, itemExpMount);
        ServerController.hUsableItems.put(472, itemExpMount);
        ServerController.hUsableItems.put(573, itemExpMount);
        ServerController.hUsableItems.put(574, itemExpMount);
        ServerController.hUsableItems.put(575, itemExpMount);
        ServerController.hUsableItems.put(576, itemExpMount);
        ServerController.hUsableItems.put(577, itemExpMount);
        ServerController.hUsableItems.put(578, itemExpMount);
        ServerController.hUsableItems.put(778, itemExpMount);
        ServerController.hUsableItems.put(454, new ChuyenTinhThach());
        ServerController.hUsableItems.put(490, new CoLenh());
        ServerController.hUsableItems.put(491, new NhiemVuBiAn());
        ServerController.hUsableItems.put(537, new NhanPhu());
        ServerController.hUsableItems.put(538, new NhanPhu());
        ServerController.hUsableItems.put(548, new CanCauVang());
        Giay itemGiay = new Giay();
        ServerController.hUsableItems.put(549, itemGiay);
        ServerController.hUsableItems.put(550, itemGiay);
        ServerController.hUsableItems.put(551, itemGiay);
        ServerController.hUsableItems.put(564, new ThiLuyenThiep());
        ServerController.hUsableItems.put(572, new ThienBienLenh());
        ServerController.hUsableItems.put(597, new VanNguCau());
        ThanThuFood itemTTF = new ThanThuFood();
        ServerController.hUsableItems.put(598, itemTTF);
        ServerController.hUsableItems.put(599, itemTTF);
        ServerController.hUsableItems.put(600, itemTTF);
        ServerController.hUsableItems.put(605, itemTTF);
        DaDanhVong itemDDV = new DaDanhVong();
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_1, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_2, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_3, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_4, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_5, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_6, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_7, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_8, itemDDV);
        ServerController.hUsableItems.put(Item.DANH_VONG_CAP_9, itemDDV);
        ServerController.hUsableItems.put(705, new DanhVongPhu());
        ServerController.hUsableItems.put(743, new TuanThuLenh());
        ServerController.hUsableItems.put(780, new TienHoaTrau());
    }

    public void upOptionTrau() {
        for (ItemOption option : options) {
            if (option.optionTemplate.itemOptionTemplateId == 128) {
                ++option.param;
                break;
            }
        }
    }

    public boolean isActive() {
        if (isTrauDen() || isTrauVang()) {
            if (options == null || options.size() == 0) {
                setOptionDefault();
            }
            return options.get(1).param >= 500;
        }
        return true;
    }
}
