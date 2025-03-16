package com.tgame.ninja.branch.tasks;

import com.tgame.ninja.real.Player;
import java.util.Calendar;

public abstract class DailyTask {

    public static final int TASK_SPEND_MONEY = 0;

    public static final int TASK_MARKET_SHINWA = 1;

    public static final int TASK_WIN_ARENA = 2;

    public static final int TASK_PK = 3;

    public static final int TASK_KILL_MOB = 4;

    public static final int TASK_EXP = 5;

    public static final int TASK_DELIVER_ITEM = 6;

    public static final int TASK_UPGRADE = 7;

    public String name;

    public String name_en;

    public String desc;

    public String desc_en;

    public int taskid;

    public int goal;

    public int type;

    protected DailyTask(String name, String name_en, String desc, String desc_en, int goal, int type) {
        this.name = name;
        this.name_en = name;
        this.desc = desc;
        this.desc_en = desc_en;
        this.goal = goal;
        this.type = type;
    }

    public abstract boolean checkTask(Player p0, int p1);

    public abstract boolean checkTask(Player p0, int p1, int p2);

    public DailyTaskData createTask() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 0);
        DailyTaskData dtd = new DailyTaskData(taskid, 0, goal, name, desc, 0, 0, cal.getTimeInMillis());
        dtd.template = this;
        dtd.typeCheck = 0;
        return dtd;
    }
}
