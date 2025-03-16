package com.tgame.ninja.model;

import com.tgame.ninja.server.ServerController;

public class ItemOption {

    public ItemOptionTemplate optionTemplate;

    public int param;

    public int active;

    public ItemOption() {
    }

    public ItemOption(int param, int optionTemplateId) {
        this.param = param;
        optionTemplate = ServerController.iOptionTemplates.get(optionTemplateId);
    }
}
