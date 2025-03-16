package com.tgame.ninja.real.npc;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.*;
import com.tgame.ninja.server.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tgame.ninja.real.DunVA;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Rikudou implements INpcHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rikudou.class);

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.map.template.isMapChienTruong()) {
            if (playerTemplate.menuId == 0) {
                if (player.doChangeMap(player.mapTemplateIdGo, false, "doMenu dau_truong")) {
                    Map.playerLefts.remove(player);
                    Map.playerRights.remove(player);
                }
            } else if (playerTemplate.menuId == 1) {
                LocalTime timeNow = LocalTime.now();
                int hour = timeNow.getHour();
                int minute = timeNow.getMinute();
                if ((hour == 14 && minute == 30) || (hour == 20 && minute == 30)) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.WAIT_RESULT(player.getSession()));
                } else {
                    player.topChienTruong();
                }
            }
            return;
        }
        if (playerTemplate.menuId == 0) {
            if (!GameServer.openDailyTask) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (player.level < 20) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.NOT_LEVEL(player.getSession()), "20"));
                return;
            }
            if (playerTemplate.optionId == 0) {
                try {
                    if (player.countLoopDay > 0) {
                        if (player.taskLoopDay == null) {
                            int killId = Player.levelByMobId[player.level - NJUtil.randomNumber(4)];
                            if (killId < 20) {
                                killId = 20;
                            } else if (killId > 68 && killId < 100) {
                                killId = 68;
                            } else if (killId >= 100) {
                                killId = 129;
                            }
                            int mapId = Map.npcMapIds[killId];
                            player.taskLoopDay = new TaskOrder(0, 0, 10 + NJUtil.randomNumber(21), AlertFunction.TASK_LOOPDAY(player.getSession()), NJUtil.replace(AlertFunction.TASK_NOTE(player.getSession()), ServerController.mapTemplates.get(mapId).getName(player.getSession())), killId, mapId);
                            --player.countLoopDay;
                            player.getTaskOrder(player.taskLoopDay);
                            player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.COUNT_TASK(player.getSession()), String.valueOf(20 - player.countLoopDay)));
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_TASK(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_FINISH(player.getSession()));
                    }
                } catch (Exception e) {
                    LOGGER.error(player.getStringBaseInfo(), e);
                }
            } else if (playerTemplate.optionId == 1) {
                if (player.taskLoopDay != null) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_CLEAR(player.getSession()));
                    player.clearTaskOrder(player.taskLoopDay);
                    player.taskLoopDay = null;
                    return;
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_NOT(player.getSession()));
            } else if (playerTemplate.optionId == 2) {
                if (player.taskLoopDay == null) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_SAY(player.getSession()));
                    return;
                }
                if (player.taskLoopDay.count != player.taskLoopDay.maxCount) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_NOT_FINISH(player.getSession()));
                    return;
                }
                if (player.countFreeBag() <= 0) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                    return;
                }
                if (player.taskMain != null && player.taskMain.template.taskId == 30 && player.taskMain.index == 1) {
                    player.checkTaskIndex();
                }
                if (player.taskMain != null && player.taskMain.template.taskId == 39 && player.taskMain.index == 3) {
                    player.checkTaskIndex();
                }
                ++player.countFinishDay;
                player.clearTaskOrder(player.taskLoopDay);
                player.taskLoopDay = null;
                ArrayList<DropRate> drops = new ArrayList<>();
                drops.add(new DropRate(1, 5));
                drops.add(new DropRate(2, 6));
                drops.add(new DropRate(4, 7));
                drops.add(new DropRate(5, 8));
                int itemId = NJUtil.dropItem(drops);
                if (itemId >= 0) {
                    player.addItemToBagNoDialog(new Item(itemId, false, "nvhn"));
                }
                int level = player.level / 10;
                if (level < 1) {
                    level = 1;
                } else if (level > 7) {
                     level = 7;
                }
                int goldAdd = level * 100;
                player.sendUpdateLuong(NJUtil.randomMinMax(goldAdd / 100 * 50, goldAdd));
                if (player.countFinishDay > 0 && player.countFinishDay % 10 == 0) {
                    if (player.countFinishDay == 10 && player.typeNvbian == 5) {
                        player.fibian();
                    }
                }
                player.savezbLog("Daily task reward", itemId + "@L" + player.getLuong() + "@Y" + player.getYen());
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.REWARD(player.getSession()));
            } else {
                if (playerTemplate.optionId == 3) {
                    if (player.taskLoopDay != null && player.taskLoopDay.mapId > 0) {
                        player.doChangeMap(player.taskLoopDay.mapId, true, "doMenu dau_truong 1");
                        return;
                    }
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_MOVE_FAST(player.getSession()));
                }
            }
        } else if (playerTemplate.menuId == 1) {
            if (!GameServer.openTaThu) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (playerTemplate.optionId == 0) {
                int levelMin = player.level;
                int levelMax = player.level;
                if (player.party == null) {
                    if (player.level < 30) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.NOT_LEVEL(player.getSession()), "30"));
                        return;
                    }
                } else {
                    if (!player.party.isTeamLeader(player)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_TEAM_TASK(player.getSession()));
                        return;
                    }
                    for (int j = 0; j < player.party.players.size(); ++j) {
                        if (player.party.players.get(j).level < 30) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_LEVEL_30(player.getSession()));
                            return;
                        }
                        if (!player.party.players.get(j).map.equals(player.map)) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_HERE(player.getSession()));
                            return;
                        }
                        if (player.party.players.get(j).taskLoopBoss != null) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, player.party.players.get(j).name + " " + AlertFunction.HAVE_TASK_PT(player.getSession()));
                            return;
                        }
                        if (player.party.players.get(j).level < levelMin) {
                            levelMin = player.party.players.get(j).level;
                        }
                        if (player.party.players.get(j).level > levelMax) {
                            levelMax = player.party.players.get(j).level;
                        }
                    }
                    if (levelMax - levelMin > 5) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.NOT_DIFFRENT(player.getSession()));
                        return;
                    }
                }
                if (player.countLoopBoos <= 0) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_FINISH(player.getSession()));
                    return;
                }
                if (player.taskLoopBoss == null) {
                    levelMin += (NJUtil.randomBoolean() ? -1 : 1) * NJUtil.randomNumber(3);
                    BossTaThu tathu = null;
                    for (int m = 0; m < Map.tathus.length; ++m) {
                        NpcTemplate template = ServerController.npcTemplates.get(Map.tathus[m].npcTemplateId);
                        if (template.level >= levelMin) {
                            tathu = Map.tathus[m];
                            break;
                        }
                    }
                    if (tathu == null) {
                        tathu = Map.tathus[Map.tathus.length - 1];
                    }
                    try {
                        player.taskLoopBoss = new TaskOrder(1, 0, 1, AlertFunction.TASK_LOOPBOSS(player.getSession()), NJUtil.replace(AlertFunction.TASK_NOTE(player.getSession()), ServerController.mapTemplates.get(tathu.mapTemplateId).getName(player.getSession())), tathu.npcTemplateId, tathu.mapTemplateId);
                        --player.countLoopBoos;
                        player.getTaskOrder(player.taskLoopBoss);
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_BOSS_FINISH(player.getSession()));
                        if (player.party != null) {
                            player.party.sendGetTaskBoss(player, player.taskLoopBoss);
                        }
                    } catch (Exception e) {
                    }
                    return;
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_TASK(player.getSession()));
            } else if (playerTemplate.optionId == 1) {
                if (player.taskLoopBoss != null) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_CLEAR(player.getSession()));
                    player.clearTaskOrder(player.taskLoopBoss);
                    player.taskLoopBoss = null;
                    return;
                }
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_NOT(player.getSession()));
            } else {
                if (playerTemplate.optionId == 2) {
                    if (player.taskLoopBoss == null) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_SAY(player.getSession()));
                        return;
                    }
                    if (player.taskLoopBoss.count != player.taskLoopBoss.maxCount) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.TASK_NOT_FINISH(player.getSession()));
                        return;
                    }
                    Item item = new Item(251, player.playerId, false, "char 1");
                    if (ServerController.npcTemplates.get(player.taskLoopBoss.killId).level >= 30 && ServerController.npcTemplates.get(player.taskLoopBoss.killId).level < 40) {
                        item.quantity = 1;
                    } else if (ServerController.npcTemplates.get(player.taskLoopBoss.killId).level >= 40 && ServerController.npcTemplates.get(player.taskLoopBoss.killId).level < 50) {
                        item.quantity = 2;
                    } else if (ServerController.npcTemplates.get(player.taskLoopBoss.killId).level >= 50 && ServerController.npcTemplates.get(player.taskLoopBoss.killId).level < 60) {
                        item.quantity = 3;
                    } else if (ServerController.npcTemplates.get(player.taskLoopBoss.killId).level >= 60 && ServerController.npcTemplates.get(player.taskLoopBoss.killId).level < 70) {
                        item.quantity = 4;
                    } else if (ServerController.npcTemplates.get(player.taskLoopBoss.killId).level >= 70) {
                        item.quantity = 5;
                    }
                    if (!player.addItemToBagNoDialog(item)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                        return;
                    }
                    if (player.taskMain != null && player.taskMain.template.taskId == 30 && player.taskMain.index == 2) {
                        player.checkTaskIndex();
                    }
                    if (player.taskMain != null && player.taskMain.template.taskId == 39 && player.taskMain.index == 1) {
                        player.checkTaskIndex();
                    }
                    player.clearTaskOrder(player.taskLoopBoss);
                    player.taskLoopBoss = null;
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.REWARD(player.getSession()));
                    //player.addPointUydanh(1);
                    player.addExpClan(5);
                    if (player.typeNvbian == 3) {
                        player.fibian();
                    }
                }
            }
        } else if (playerTemplate.menuId == 2) {
            if (!GameServer.openChienTruong) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            player.doChientruong(playerTemplate, playerTemplate.optionId);
        } else if (playerTemplate.menuId == 3) {
            if (!GameServer.openThatThuAi) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            LocalDateTime dateTimeNow = LocalDateTime.now();
            DayOfWeek dayOfWeek = dateTimeNow.getDayOfWeek();
            int hour = dateTimeNow.getHour();
            int minute = dateTimeNow.getMinute();
            if ((dayOfWeek != DayOfWeek.TUESDAY && dayOfWeek != DayOfWeek.THURSDAY && dayOfWeek != DayOfWeek.SATURDAY) || (hour != 9 && (hour != 10 || minute >= 30) && hour != 21 && (hour != 22 || minute >= 30))) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.VUOT_AI_ALERT1(player.getSession()));
                return;
            }
            if (playerTemplate.optionId == 0) {
                DunVA mapNext = DunVA.findMap(player.name, 113);
                if (mapNext != null) {
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    player.mapClear();
                    player.map.goOutMap(player);
                    player.x = mapNext.template.defaultX;
                    player.y = mapNext.template.defaultY;
                    player.sendMapTime((int) (mapNext.timeEnd / 1000L - System.currentTimeMillis() / 1000L));
                    mapNext.waitGoInMap(player);
                    return;
                }
                if (player.party == null || player.party.players.size() < 6) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.VUOT_AI_ALERT2(player.getSession()));
                    return;
                }
                if (!player.party.isTeamLeader(player)) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.VUOT_AI_ALERT3(player.getSession()));
                    return;
                }
                if ((hour != 9 && hour != 21) || minute > 30) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.VUOT_AI_ALERT7(player.getSession()));
                    return;
                }
                for (int i2 = 0; i2 < player.party.players.size(); ++i2) {
                    if (player.party.players.get(i2).level < 50) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.VUOT_AI_ALERT15(player.getSession()));
                        return;
                    }
                }
                int levelDun2 = 0;
                for (int i3 = 0; i3 < player.party.players.size(); ++i3) {
                    levelDun2 += player.party.players.elementAt(i3).level;
                }
                levelDun2 = levelDun2 / player.party.players.size() + 5;
                mapNext = DunVA.createDun(113, player.party, levelDun2);
                if (mapNext != null) {
                    Message message = null;
                    try {
                        for (int i3 = 0; i3 < player.party.players.size(); ++i3) {
                            message = new Message(Cmd.SERVER_MESSAGE);
                            message.writeUTF("Nhóm trưởng đã mở cửa thất thú ải");
                            player.party.players.get(i3).getSession().sendMessage(message);
                        }
                    } catch (Exception e) {
                        LOGGER.error(player.getStringBaseInfo(), e);
                    } finally {
                        if (message != null) {
                            message.cleanup();
                        }
                    }
                    DunVA.createDun(112, player.party, levelDun2);
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    player.mapClear();
                    player.map.goOutMap(player);
                    player.x = mapNext.template.defaultX;
                    player.y = mapNext.template.defaultY;
                    mapNext.waitGoInMap(player);
                    player.sendMapTime((int) (mapNext.timeEnd / 1000L - System.currentTimeMillis() / 1000L));
                }
            }
        }
    }
}
