package com.tgame.ninja.model;

import com.tgame.ninja.server.ServerController;

public class Task {

    public TaskTemplate template;

    public int index;

    public short count;

    int taskID;

    public Task(int taskId) {
        taskID = taskId;
        if (taskId > ServerController.taskTemplates.size()) {
            taskId = ServerController.taskTemplates.size() - 1;
        }
        template = ServerController.taskTemplates.get(taskId);
        index = 0;
    }

    public int getTaskID() {
        if (taskID < ServerController.taskTemplates.size()) {
            return template.taskId;
        }
        return 10000;
    }

    public short[] getCount() {
        if (taskID < ServerController.taskTemplates.size()) {
            return template.counts;
        }
        return null;
    }

    public String getName(int id) {
        if (taskID < ServerController.taskTemplates.size()) {
            return template.name[id];
        }
        return "";
    }

    public String getDetail(int id) {
        if (taskID < ServerController.taskTemplates.size()) {
            return template.detail[id];
        }
        return "";
    }

    public String[] getSubName(int id) {
        if (taskID < ServerController.taskTemplates.size()) {
            return template.subNames[id];
        }
        return new String[]{ "" };
    }
}
