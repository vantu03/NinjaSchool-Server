package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class SkillBook implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 40: {
                int skillTemplateId = 1;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 41: {
                int skillTemplateId = 2;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 42: {
                int skillTemplateId = 3;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 43: {
                int skillTemplateId = 4;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 44: {
                int skillTemplateId = 5;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 45: {
                int skillTemplateId = 6;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 46: {
                int skillTemplateId = 7;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 47: {
                int skillTemplateId = 8;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 48: {
                int skillTemplateId = 9;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 49: {
                int skillTemplateId = 10;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 50: {
                int skillTemplateId = 11;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 51: {
                int skillTemplateId = 12;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 52: {
                int skillTemplateId = 13;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 53: {
                int skillTemplateId = 14;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 54: {
                int skillTemplateId = 15;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 55: {
                int skillTemplateId = 16;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 56: {
                int skillTemplateId = 17;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 57: {
                int skillTemplateId = 18;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 58: {
                int skillTemplateId = 19;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 59: {
                int skillTemplateId = 20;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 60: {
                int skillTemplateId = 21;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 61: {
                int skillTemplateId = 22;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 62: {
                int skillTemplateId = 23;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 63: {
                int skillTemplateId = 24;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 64: {
                int skillTemplateId = 25;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 65: {
                int skillTemplateId = 26;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 66: {
                int skillTemplateId = 27;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 67: {
                int skillTemplateId = 28;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 68: {
                int skillTemplateId = 29;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 69: {
                int skillTemplateId = 30;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 70: {
                int skillTemplateId = 31;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 71: {
                int skillTemplateId = 32;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 72: {
                int skillTemplateId = 33;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 73: {
                int skillTemplateId = 34;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 74: {
                int skillTemplateId = 35;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 75: {
                int skillTemplateId = 36;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 76: {
                int skillTemplateId = 37;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 77: {
                int skillTemplateId = 38;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 78: {
                int skillTemplateId = 39;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 79: {
                int skillTemplateId = 40;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 80: {
                int skillTemplateId = 41;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 81: {
                int skillTemplateId = 42;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 82: {
                int skillTemplateId = 43;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 83: {
                int skillTemplateId = 44;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 84: {
                int skillTemplateId = 45;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 85: {
                int skillTemplateId = 46;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 86: {
                int skillTemplateId = 47;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 87: {
                int skillTemplateId = 48;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 88: {
                int skillTemplateId = 49;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 89: {
                int skillTemplateId = 50;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 90: {
                int skillTemplateId = 51;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 91: {
                int skillTemplateId = 52;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 92: {
                int skillTemplateId = 53;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 93: {
                int skillTemplateId = 54;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 311: {
                int skillTemplateId = 55;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 312: {
                int skillTemplateId = 56;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 313: {
                int skillTemplateId = 57;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 314: {
                int skillTemplateId = 58;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 315: {
                int skillTemplateId = 59;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 316: {
                int skillTemplateId = 60;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 375: {
                int skillTemplateId = 61;
                if (playerMain.classId != 1) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 376: {
                int skillTemplateId = 62;
                if (playerMain.classId != 2) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 377: {
                int skillTemplateId = 63;
                if (playerMain.classId != 3) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 378: {
                int skillTemplateId = 64;
                if (playerMain.classId != 4) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 379: {
                int skillTemplateId = 65;
                if (playerMain.classId != 5) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 380: {
                int skillTemplateId = 66;
                if (playerMain.classId != 6) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 547: {
                if (!GameServer.openPhanThan) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                if (!player.isMainchar) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.CAN_NOT_USE(player.getSession()));
                    return;
                }
                int skillTemplateId = (new int[]{ 67, 67, 68, 69, 70, 71, 72 })[playerMain.classId];
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 552:
            case 553:
            case 554:
            case 555:
            case 556:
            case 557: {
                int[] skilltemid2 = { 73, 78, 75, 76, 74, 77 };
                int skillTemplateId = skilltemid2[item.getTemplateId() - 552];
                if (playerMain.classId != item.getTemplateId() - 551) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
            case 558:
            case 559:
            case 560:
            case 561:
            case 562:
            case 563: {
                int[] skilltemid1 = { 79, 83, 81, 82, 80, 84 };
                int skillTemplateId = skilltemid1[item.getTemplateId() - 558];
                if (playerMain.classId != item.getTemplateId() - 557) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.ERROR_CLASS(player.getSession()));
                    return;
                }
                if (playerMain.isHaveSkill(skillTemplateId)) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.HAVE_SKILL(player.getSession()));
                    return;
                }
                player.useBookSkill(item, skillTemplateId);
                break;
            }
        }
    }
}
