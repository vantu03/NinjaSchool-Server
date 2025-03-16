package com.tgame.ninja.branch.event;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Npc;
import com.tgame.ninja.real.NpcPlayer;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;
import java.time.LocalDate;
import java.util.Vector;

public class EventManager {

    public AbstractEvent event;

    public static EventManager getInstance() {
        return MyHolder.INSTANCE;
    }

    public EventManager() {
        if (isSummerEvent()) {
            event = new SummerEvent();
            event.isRunning = true;
        }
    }

    public void init(Map map) {
        if (event != null) {
            event.initMap(map);
        }
    }

    public void loadData() {
        if (event != null && event.isRunning) {
            event.loadData();
        }
    }

    public boolean isMapEvent(Map map) {
        return event != null && event.isMapEvent(map);
    }

    public boolean isMobEvent(Npc mob) {
        return event != null && event.isMobEvent(mob);
    }

    public void handleMapPK(Player player, Map map) {
        if (event != null) {
            event.handleMapPK(player, map);
        }
    }

    public String[][] handleNpcMenu(PlayerTemplate playerTemplate) {
        if (event != null) {
            return event.handleNpcMenu(playerTemplate);
        }
        return playerTemplate.menu;
    }

    public boolean handleNpcOption(Player player, PlayerTemplate playerTemplate) {
        return event != null && event.handleNpcOption(player, playerTemplate);
    }

    public boolean checkVisibility(Player player, Npc mob) {
        if (event == null) {
            return true;
        }
        updateTime();
        return event.checkVisibility(player, mob);
    }

    public boolean checkVisibiliy(Player player, NpcPlayer npc) {
        if (event == null) {
            return false;
        }
        updateTime();
        return event.checkVisibility(player, npc);
    }

    public boolean useItem(Player player, Item item) {
        if (event == null) {
            return false;
        }
        updateTime();
        return event != null && event.useItem(player, item);
    }

    public Vector<Item> killMobRewards(Player player, Npc mob) {
        if (event == null) {
            return null;
        }
        return event.killMobRewards(player, mob);
    }

    public void updateTime() {
        if (event != null) {
            event.isRunning = isSummerEvent();
        }
    }

    public boolean isSummerEvent() {
        LocalDate startDate = LocalDate.of(2021, 8, 9);
        LocalDate endDate = LocalDate.of(2021, 8, 24);
        return NJUtil.inBetweenDate(startDate, endDate);
    }

    private static class MyHolder {

        public static EventManager INSTANCE = new EventManager();
    }
}
