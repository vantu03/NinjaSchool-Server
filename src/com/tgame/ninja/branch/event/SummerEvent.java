package com.tgame.ninja.branch.event;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.DropRate;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Npc;
import com.tgame.ninja.real.NpcPlayer;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.util.ArrayList;
import java.util.Vector;

public class SummerEvent extends AbstractEvent {

    @Override
    public void initMap(Map map) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public boolean isMapEvent(Map map) {
        return false;
    }

    @Override
    public boolean isMobEvent(Npc mob) {
        return !mob.isBoss() && mob.level >= 20;
    }

    @Override
    public void handleMapPK(Player player, Map map) {
        if (isMapEvent(map)) {
            if (player.typePk != Player.PK_NHOM) {
                player.oldTypePK = player.typePk;
                player.changeTypePk(Player.PK_NHOM);
            }
        } else if (player.typePk != player.oldTypePK && player.oldTypePK != -127) {
            player.changeTypePk(player.oldTypePK);
        }
    }

    @Override
    public String[][] handleNpcMenu(PlayerTemplate playerTemplate) {
        if (playerTemplate.playerTemplateId == 33) {
            playerTemplate.menu_event = new String[][]{
                { "Sự kiện hè", "Hướng dẫn", "Làm diều giấy", "Làm diều vải" }
            };
            String[][] menuMerged = new String[playerTemplate.menu.length + playerTemplate.menu_event.length][];
            System.arraycopy(playerTemplate.menu, 0, menuMerged, 0, playerTemplate.menu.length);
            System.arraycopy(playerTemplate.menu_event, 0, menuMerged, playerTemplate.menu.length, playerTemplate.menu_event.length);
            return menuMerged;
        }
        return playerTemplate.menu;
    }

    @Override
    public boolean handleNpcOption(Player player, PlayerTemplate playerTemplate) {
        if (playerTemplate.playerTemplateId == 33 && playerTemplate.getMenu().length > playerTemplate.menu.length && playerTemplate.menuId >= playerTemplate.menu.length) {
            int menuIndex = playerTemplate.getMenu().length - playerTemplate.menuId - 1;
            int optionIndex = playerTemplate.optionId;
            if (menuIndex == 0) {
                if (optionIndex == 0) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), "Hướng dẫn",
                        "Nguyên liệu làm diều giấy:\n- 3 Tre\n- 2 Dây\n- 2 Giấy\n- 1 Màu vẽ thô sơ\n\n" +
                            "Nguyên liệu làm diều vải:\n- 3 Tre\n- 2 Dây\n- 2 Vải\n- 1 Màu vẽ cao cấp\n\n" +
                            "Màu vẽ được bán tại NPC Goosho, thu thập đủ các nguyên liệu trên rồi đến gặp ta để làm diều nhé.");
                } else if (optionIndex == 1) {
                    if (player.countFreeBag() <= 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                        return true;
                    }
                    Item tre = player.findItemBag(428);
                    Item day = player.findItemBag(429);
                    Item giay = player.findItemBag(430);
                    Item mau = player.findItemBag(432);
                    if (tre != null && day != null && giay != null && mau != null && tre.quantity >= 3 && day.quantity >= 2 && giay.quantity >= 2) {
                        player.useItemUpToUp(tre, 3);
                        player.sendUseItemUpToUp(tre.indexUI, 3);
                        player.useItemUpToUp(day, 2);
                        player.sendUseItemUpToUp(day.indexUI, 2);
                        player.useItemUpToUp(giay, 2);
                        player.sendUseItemUpToUp(giay.indexUI, 2);
                        player.useItemUpToUp(mau, 1);
                        player.sendUseItemUpToUp(mau.indexUI, 1);
                        player.addItemToBagNoDialog(new Item(434, false, "Summer event"));
                        return true;
                    }
                    player.sendOpenUISay(playerTemplate.playerTemplateId, "Không đủ nguyên liệu để làm diều, hãy tìm thêm nhé!");
                } else if (optionIndex == 2) {
                    if (player.countFreeBag() <= 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                        return true;
                    }
                    Item tre = player.findItemBag(428);
                    Item day = player.findItemBag(429);
                    Item vai = player.findItemBag(431);
                    Item mau = player.findItemBag(433);
                    if (tre != null && day != null && vai != null && mau != null && tre.quantity >= 3 && day.quantity >= 2 && vai.quantity >= 2) {
                        player.useItemUpToUp(tre, 3);
                        player.sendUseItemUpToUp(tre.indexUI, 3);
                        player.useItemUpToUp(day, 2);
                        player.sendUseItemUpToUp(day.indexUI, 2);
                        player.useItemUpToUp(vai, 2);
                        player.sendUseItemUpToUp(vai.indexUI, 2);
                        player.useItemUpToUp(mau, 1);
                        player.sendUseItemUpToUp(mau.indexUI, 1);
                        player.addItemToBagNoDialog(new Item(435, false, "Summer event"));
                        return true;
                    }
                    player.sendOpenUISay(playerTemplate.playerTemplateId, "Không đủ nguyên liệu để làm diều, hãy tìm thêm nhé!");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Vector<Item> killMobRewards(Player player, Npc mob) {
        Vector<Item> items = new Vector<>();
        ArrayList<DropRate> drops = new ArrayList<>();
        drops.add(new DropRate(1, 428));
        if (NJUtil.randomNumber(1000) > 200) {
            drops.add(new DropRate(1, 429));
            drops.add(new DropRate(1, 430));
            drops.add(new DropRate(1, 431));
        }
        for (int i = 0; i < NJUtil.randomMinMax(1, 2); i++) {
            int itemId = NJUtil.dropItem(drops);
            if (itemId != -1) {
                items.add(new Item(itemId, false, "Summer drop"));
            }
        }
        return items;
    }

    @Override
    public boolean checkVisibility(Player player, Npc mob) {
        return false;
    }

    @Override
    public boolean checkVisibility(Player player, NpcPlayer npc) {
        return false;
    }

    @Override
    public boolean useItem(Player player, Item item) {
        return false;
    }
}
