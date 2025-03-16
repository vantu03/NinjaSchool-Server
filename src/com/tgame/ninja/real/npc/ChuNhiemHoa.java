package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Dun;
import com.tgame.ninja.real.MixedArena;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class ChuNhiemHoa implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.map.getTemplateId() == 73) {
            ((Dun) player.map).timeEnd = 0L;
            return;
        }
        if (MixedArena.isMapArena(player.map.template.mapTemplateId)) {
            MixedArena.handleMenu(player, playerTemplate.menuId);
            return;
        }
        if ((player.classId == 1 || player.classId == 2) && player.taskMain == null && (player.getTaskFinish() == 9 || player.getTaskFinish() == 10 || player.getTaskFinish() == 11 || player.getTaskFinish() == 12)) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if ((player.classId == 1 || player.classId == 2) && player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && (player.getTaskFinish() == 9 || player.getTaskFinish() == 10 || player.getTaskFinish() == 11 || player.getTaskFinish() == 12)) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.doTaskFinish(playerTemplate.playerTemplateId);
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.getTaskFinish() == 13 && player.taskMain.index == 3) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0) {
                int time5 = 300;
                Dun dun = Dun.createDun(73);
                if (dun == null) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ROOM_CLOSE(player.getSession()));
                    return;
                }
                dun.playerOpen = player;
                dun.timeEnd = System.currentTimeMillis() + time5 * 1000;
                player.mapClear();
                player.map.goOutMap(player);
                player.x = 0;
                player.y = 0;
                player.sendMapTime(time5);
                dun.waitGoInMap(player);
                return;
            }
            --playerTemplate.menuId;
        }
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                player.topDaiGia();
            } else if (playerTemplate.optionId == 1) {
                player.topCaothu();
            } else if (playerTemplate.optionId == 2) {
                player.topGiaToc();
            } else if (playerTemplate.optionId == 3) {
                player.topHangdong();
            }
        } else if (playerTemplate.menuId == 1) {
            if (player.getTaskFinish() < 9 || playerMain.level < 10) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_INPUT_CLASS(player.getSession()));
                return;
            }
            if (playerMain.classId != 0) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_CLASS(player.getSession()));
                return;
            }
            if (playerMain.itemBodys[1] != null) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.CLEAR_WEAPON(player.getSession()));
                return;
            }
            int index2 = -1;
            int index3 = -1;
            for (int k = 0; k < player.itemBags.length; ++k) {
                if (player.itemBags[k] == null && index2 == -1) {
                    index2 = k;
                } else if (player.itemBags[k] == null && index3 == -1) {
                    index3 = k;
                    break;
                }
            }
            if (index2 == -1 || index3 == -1) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "2"));
                return;
            }
            if (playerTemplate.optionId == 0) {
                playerMain.classId = 1;
                playerMain.p_strength = 15;
                playerMain.p_agile = 5;
                playerMain.p_mp = 5;
                playerMain.p_hp = 5;
                player.updateClass();
                Item itemBook = new Item(40, player.playerId, true, "char 4");
                player.addItem(itemBook, 3, index2);
                player.sendAddItemBag(itemBook);
                Item itemWeapon = new Item(94, player.playerId, true, "char 5");
                itemWeapon.sys = 1;
                itemWeapon.options = Item.getOptionTemplate(itemWeapon);
                if (itemWeapon.options != null) {
                    itemWeapon.options.remove(5);
                    itemWeapon.options.remove(3);
                }
                itemWeapon = itemWeapon.cloneRandom();
                player.addItem(itemWeapon, 3, index3);
                player.sendAddItemBag(itemWeapon);
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.INPUT_CLASS1(player.getSession()));
            } else if (playerTemplate.optionId == 1) {
                playerMain.classId = 2;
                playerMain.p_strength = 5;
                playerMain.p_agile = 5;
                playerMain.p_mp = 10;
                playerMain.p_hp = 10;
                player.updateClass();
                Item itemBook = new Item(49, player.playerId, true, "char 6");
                player.addItem(itemBook, 3, index2);
                player.sendAddItemBag(itemBook);
                Item itemWeapon = new Item(114, player.playerId, true, "char 7");
                itemWeapon.sys = 4;
                itemWeapon.options = Item.getOptionTemplate(itemWeapon);
                if (itemWeapon.options != null) {
                    itemWeapon.options.remove(5);
                    itemWeapon.options.remove(3);
                }
                itemWeapon = itemWeapon.cloneRandom();
                player.addItem(itemWeapon, 3, index3);
                player.sendAddItemBag(itemWeapon);
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.INPUT_CLASS1(player.getSession()));
            }
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                if (playerMain.classId == 1 || playerMain.classId == 2) {
                    if (playerMain.resetPotential > 0) {
                        --playerMain.resetPotential;
                        playerMain.resetPotential();
                        if (playerMain.resetPotential > 0) {
                            playerMain.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.FREE_POTENTIAL_1(playerMain.getSession()), String.valueOf(playerMain.resetPotential)));
                        } else {
                            playerMain.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.FREE_POTENTIAL_2(playerMain.getSession()), String.valueOf(playerMain.resetPotential)));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_FREE_POTENTIAL(player.getSession()));
                    }
                } else {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_CLASS(player.getSession()));
                }
            } else if (playerTemplate.optionId == 1) {
                if (playerMain.isLock) {
                    player.doCancelTrade();
                    player.sendNotUnlock();
                    return;
                }
                if (playerMain.classId == 1 || playerMain.classId == 2) {
                    if (playerMain.resetSkill > 0) {
                        --playerMain.resetSkill;
                        playerMain.resetSkill();
                        if (playerMain.resetSkill > 0) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.FREE_SKILL_1(player.getSession()), String.valueOf(player.resetSkill)));
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.FREE_SKILL_2(player.getSession()), String.valueOf(player.resetSkill)));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_FREE_SKILL(player.getSession()));
                    }
                } else {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_CLASS(player.getSession()));
                }
            }
        } else if (playerTemplate.menuId == 3) {
            if (playerTemplate.optionId == 0) {
                if (player.taskMain != null && player.taskMain.template.taskId == 8 && player.taskMain.index == 1) {
                    player.doTaskNext();
                    player.sendOpenUISay(playerTemplate.playerTemplateId, player.taskMain.template.des[player.getSession().typeLanguage][player.taskMain.index - 1]);
                } else {
                    String[] getSay = playerTemplate.getSay(player.getSession());
                    player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
                }
            }
        } else if (playerTemplate.menuId == 4) {
            if (!GameServer.openMixedArena) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (playerTemplate.optionId == 0) {
                MixedArena.join(player);
            } else if (playerTemplate.optionId == 1) {
                MixedArena.getSummaryPlayer(player);
            } else {
                MixedArena.getGuide(player);
            }
        }
    }
}
