package com.tgame.ninja.model;

public class TaskOrder {

    public static final byte TASK_DAY = 0;

    public static final byte TASK_BOSS = 1;

    public static final byte TASK_GIOITHIEU = 2;

    public static final byte TASK_SUKIEN1 = 3;

    public static final byte TASK_SUKIEN2 = 4;

    public static final byte TASK_SUKIEN3 = 5;

    public static final byte TASK_SUKIEN4 = 6;

    public static final byte TASK_SUKIEN5 = 7;

    public int taskId;

    public int killId;

    public int mapId;

    public int count;

    public int maxCount;

    public String name;

    public String description;

    public byte type;

    public static int[] MAX_COUNT ={ 1, 1, 1, 1, 1, 1, 1, 1, 500000, 200000, 2000000, 5, 10, 50, 20, 100, 500, 1, 5, 10, 1, 1, 1, 5000000, 50000000 };

    public boolean isEventGioto;

    public TaskOrder(int taskId, int count, int maxCount, String name, String description, int killId, int mapId) {
        isEventGioto = false;
        this.count = count;
        this.taskId = taskId;
        this.maxCount = maxCount;
        this.name = name;
        this.description = description;
        this.killId = killId;
        this.mapId = mapId;
    }

    public boolean isTaskTieuYen() {
        return taskId < 11;
    }

    public boolean isTaskTieuLuong() {
        return taskId >= 11 && taskId < 14;
    }

    public boolean isTaskKillMonster() {
        return taskId >= 14 && taskId < 17;
    }

    public boolean isTaskKillPlayer() {
        return taskId >= 17 && taskId < 20;
    }

    public boolean isTaskCuongHoa() {
        return taskId == 20;
    }

    public boolean isTaskBuyVk() {
        return taskId == 21;
    }

    public boolean isTaskBuyGiap() {
        return taskId == 22;
    }

    public boolean isTaskGetExp() {
        return taskId > 22;
    }

    public TaskOrder clone() {
        return new TaskOrder(taskId, count, maxCount, name, description, killId, mapId);
    }
}
