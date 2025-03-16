package com.tgame.ninja.model;

public class SkillOption {

    public int param;

    public SkillOptionTemplate optionTemplate;

    public SkillOption clone() {
        SkillOption option = new SkillOption();
        option.param = param;
        option.optionTemplate = optionTemplate;
        return option;
    }
}
