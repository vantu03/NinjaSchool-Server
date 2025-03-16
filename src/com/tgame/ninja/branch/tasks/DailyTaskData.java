package com.tgame.ninja.branch.tasks;

import com.tgame.ninja.model.TaskOrder;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.ServerController;

public class DailyTaskData extends TaskOrder {

    public byte typeCheck;

    public short[] itemIds;

    public int mobid;

    public DailyTask template;

    public long timeExpire;

    public DailyTaskData(int taskId, int count, int maxCount, String name, String description, int killId, int mapId) {
        super(taskId, count, maxCount, name, description, killId, mapId);
        typeCheck = 0;
        itemIds = null;
        mobid = -1;
    }

    public DailyTaskData(int taskId, int count, int maxCount, String name, String description, int killId, int mapId, long expire) {
        super(taskId, count, maxCount, name, description, killId, mapId);
        typeCheck = 0;
        itemIds = null;
        mobid = -1;
        timeExpire = expire;
    }

    public void complete() {
        count = template.goal - 1;
    }

    public boolean isExpire(long time) {
        return time > timeExpire;
    }

    public void updateName(Player p) {
        if (typeCheck == 0) {
            name = template.name;
            description = template.desc;
        } else if (typeCheck == 1 || typeCheck == 2) {
            if (itemIds == null) {
                name = template.name;
                description = template.desc;
            } else if (itemIds.length == 1) {
                if (p.getSession().typeLanguage == 0) {
                    name = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
                    description = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
                } else {
                    name = "Bring back " + ServerController.itemTemplates.get(itemIds[0]).name_en;
                    description = "Bring back " + ServerController.itemTemplates.get(itemIds[0]).name_en;
                }
            } else if (p.getSession().typeLanguage == 0) {
                name = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
                description = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
            } else {
                name = "Bring back " + ServerController.itemTemplates.get(itemIds[0]).name_en;
                description = "Bring back " + ServerController.itemTemplates.get(itemIds[0]).name_en;
            }
        } else if (typeCheck == 3) {
            if (p.getSession().typeLanguage == 0) {
                name = String.format("Hãy tiêu diệt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name);
                description = String.format("Hãy tiêu diệt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name);
            } else {
                name = String.format("Track down and hunt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name_en);
                description = String.format("Track down and hunt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name_en);
            }
        }
    }

    public void updateName() {
        if (typeCheck == 0) {
            name = template.name;
            description = template.desc;
        } else if (typeCheck == 1 || typeCheck == 2) {
            if (itemIds == null) {
                name = template.name;
                description = template.desc;
            } else if (itemIds.length == 1) {
                name = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
                description = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
            } else {
                name = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
                description = "Hãy giao " + ServerController.itemTemplates.get(itemIds[0]).name;
            }
        } else if (typeCheck == 3) {
            name = String.format("Hãy tiêu diệt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name);
            description = String.format("Hãy tiêu diệt %d %s ở vùng đất ma quỷ", template.goal, ServerController.npcTemplates.get(mobid).name);
        }
    }
}
