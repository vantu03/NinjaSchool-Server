package com.tgame.ninja.branch.event;

import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Npc;
import com.tgame.ninja.real.NpcPlayer;
import com.tgame.ninja.real.Player;
import java.util.Vector;

public abstract class AbstractEvent {

    public boolean isRunning;

    public AbstractEvent() {
        isRunning = false;
    }

    protected void initMap(Map map) {

    }

    public void loadData() {

    }

    public boolean isMapEvent(Map map) {
        return false;
    }

    public boolean isMobEvent(Npc mob) {
        return false;
    }

    public void handleMapPK(Player player, Map map) {

    }

    public String[][] handleNpcMenu(PlayerTemplate playerTemplate) {
        return playerTemplate.menu;
    }

    public boolean handleNpcOption(Player player, PlayerTemplate playerTemplate) {
        return false;
    }

    public Vector<Item> killMobRewards(Player player, Npc mob) {
        return null;
    }

    public boolean checkVisibility(Player player, Npc mob) {
        return false;
    }

    public boolean checkVisibility(Player player, NpcPlayer npc) {
        return false;
    }

    public boolean useItem(Player player, Item item) {
        return false;
    }
}
