package com.tgame.ninja.real.npc;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.*;
import com.tgame.ninja.real.*;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class BinhKhi implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        try {
            Player playerMain = player.getPlayerMainControl();
            if (playerMain.isNhanban() && playerTemplate.menuId > 0) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (player.map.getTemplateId() == 110) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0) {
                    player.clearTestDun();
                    player.doChangeMap(NJUtil.randomMapBack(), false, "doMenu BINH_KHI");
                } else if (playerTemplate.menuId == 1) {
                    if (player.party != null && !player.party.isTeamLeader(player)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HEPL_TEST_DUN6(player.getSession()));
                    } else {
                        player.doSendTextBoxId(AlertFunction.HEPL_TEST_DUN0(player.getSession()), 3);
                    }
                } else if (playerTemplate.menuId == 2) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HEPL_TEST_DUN7(player.getSession()));
                }
                return;
            }
            if (player.map.getTemplateId() == 129) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0) {
                    String str = String.valueOf(AlertFunction.THANH_TICH1(player.getSession())) + player.countLoiDai + "\n";
                    str = str + AlertFunction.THANH_TICH2(player.getSession()) + player.pointLoiDai;
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.THANH_TICH(player.getSession()), str);
                } else if (playerTemplate.menuId == 1) {
                    player.clearTestDun();
                    player.doChangeMap(NJUtil.randomMapBack(), false, "doMenu BINH_KHI 1");
                } else if (playerTemplate.menuId == 2) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.HELP(player.getSession()), "Mỗi tháng, giải đấu sẽ bắt đầu vào ngày 1 đến cuối ngày 26, mỗi nhân vật được đánh tối đa 50 trận. Mỗi ngày có 4 trận đánh diễn ra, tương ứng với các mốc thời gian như sau 5h50 sẽ mở cửa cho phép báo danh, các trận đấu sẽ bắt đầu vào lúc 6h, 6h15, 6h30, 6h45.");
                }
                return;
            }
            if (player.taskMain == null && player.getTaskFinish() == 13) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0) {
                    player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                    return;
                }
                --playerTemplate.menuId;
            } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 13) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                    player.doTaskFinish(playerTemplate.playerTemplateId);
                    return;
                }
                --playerTemplate.menuId;
            }
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_WEAPON);
            } else if (playerTemplate.menuId == 1) {
                if (!GameServer.openGiaToc) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.optionId == 0) {
                    int cost = 50000;
                    if (player.clan != null) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_CLAN(player.getSession()));
                    } else if (player.level < 10) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_CLAN(player.getSession()));
                    } else if (player.getLuong() < cost) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.NOT_GOLD_CLAN(player.getSession()), String.valueOf(cost)));
                    } else {
                        if (player.level < 40) {
                            player.sendOpenUISay(0, AlertFunction.LEVEL_CLAN_ERROR(player.getSession()));
                            return;
                        }
                        player.doSendTextBoxId(AlertFunction.NAME_CLAN(player.getSession()), 0);
                    }
                } else if (playerTemplate.optionId == 1) {
                    if (!GameServer.openGiaToc || !GameServer.openLanhDiaGiaToc) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.level < 40) {
                        player.sendOpenUISay(0, AlertFunction.NOT_LEVEL40(player.getSession()));
                        return;
                    }
                    if (player.clan == null) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN1(player.getSession()));
                    } else if (player.clan.dunClan == null) {
                        if (!player.clan.isMain(player.name) && !player.clan.isAssist(player.name)) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN2(player.getSession()));
                        } else if (player.clan.openDun <= 0) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN3(player.getSession()));
                        } else {
                            if (player.countFreeBag() >= 1) {
                                player.clan.dunClan = DunClan.createDun(80, 0, player.clan);
                                if (player.goDunClan()) {
                                    Item itKey = new Item(260, false, "doMenu char 1");
                                    itKey.expires = System.currentTimeMillis() + NJUtil.getMilisByMinute(10);
                                    player.addItemToBagNoDialog(itKey);
                                    --player.clan.openDun;
                                    player.clan.stepDoor = 0;
                                    player.clan.inviteNames.clear();
                                    String[] strs = {
                                        player.name + Alert_VN.ALERT_DUN_CLAN,
                                        player.name + Alert_EN.ALERT_DUN_CLAN
                                    };
                                    player.clan.sendAlert(strs, player.name);
                                } else {
                                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ALERT_DUN_CLAN4(player.getSession()));
                                }
                                return;
                            }
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN6(player.getSession()));
                        }
                    } else if (player.clan.dunClan.names != null) {
                        if (!player.clan.dunClan.names.contains(player.name)) {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN11(player.getSession()));
                        } else {
                            player.goDunClan();
                        }
                    } else if (player.clan.dunClan.players.size() >= 24) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN10(player.getSession()));
                    } else if (player.clan.isGoDun(player.name)) {
                        player.goDunClan();
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ERROR_DUN_CLAN0(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 2) {
                    Item itTienGT = player.findItemBag(262);
                    if (itTienGT == null || itTienGT.quantity < 500) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ALERT_DUN_CLAN5(player.getSession()));
                    } else if (player.addItemToBagNoDialog(new Item(263, true, "doMenu char 2"))) {
                        itTienGT.quantity -= 500;
                        if (itTienGT.quantity <= 0) {
                            player.sendClearItemBag(itTienGT.indexUI);
                        } else {
                            player.sendUseItemUpToUp(itTienGT.indexUI, 500);
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, NJUtil.replace(AlertFunction.BAG_NOT_FREE(player.getSession()), "1"));
                    }
                } else if (playerTemplate.optionId == 3) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.HELP(player.getSession()), AlertFunction.HELP_CLAN(player.getSession()));
                }
            } else if (playerTemplate.menuId == 2) {
                if (playerMain.isNhanban()) {
                    player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                    return;
                }
                if (playerTemplate.optionId == 0) {
                    Message message = NJUtil.messageNotMap(Cmd.REVIEW_PB);
                    message.writeShort(player.pointPB);
                    message.writeShort(player.timePB);
                    message.writeByte(player.friendPB);
                    message.writeShort(player.getRewardPB());
                    NJUtil.sendMessage(player.getSession(), message);
                } else if (playerTemplate.optionId == 1) {
                    if (!GameServer.openHangDong3x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && (player.level < 30 || player.level >= 40)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(35, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 2) {
                    if (!GameServer.openHangDong4x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && (player.level < 40 || player.level >= 50)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(45, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 3) {
                    if (!GameServer.openHangDong5x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && (player.level < 50 || player.level >= 60)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(55, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 4) {
                    if (!GameServer.openHangDong6x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.party != null && player.party.players.size() > 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT8(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && (player.level < 60 || player.level >= 70)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(65, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 5) {
                    if (!GameServer.openHangDong7x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && (player.level <= 69 || player.level >= 90)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(75, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 6) {
                    if (!GameServer.openHangDong9x) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.getSession().type_admin == 0 && player.level < 90) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT3(player.getSession()));
                        return;
                    }
                    player.mapTemplateIdGo = player.map.getTemplateId();
                    int type = DunPB.openDun(95, player);
                    if (type == 0) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_91_CLOSE(player.getSession()));
                    } else if (type == 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT1(player.getSession()));
                    } else if (type == 2) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.DUN_PB_ALERT2(player.getSession()));
                    }
                }
            } else if (playerTemplate.menuId == 3) {
                if (playerTemplate.optionId == 0) {
                    if (!GameServer.openLoiDai) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    player.doSendTextBoxId(AlertFunction.NAME_PLAYER(player.getSession()), 2);
                } else if (playerTemplate.optionId == 1) {
                    if (!GameServer.openLoiDai) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    Message message = new Message(Cmd.TEST_DUN_LIST);
                    message.writeByte(DunVD.maps.size());
                    for (int i = 0; i < DunVD.maps.size(); ++i) {
                        message.writeByte(DunVD.maps.get(i).zoneId);
                        message.writeUTF(DunVD.maps.get(i).name1);
                        message.writeUTF(DunVD.maps.get(i).name2);
                    }
                    NJUtil.sendMessage(player.getSession(), message);
                } else if (playerTemplate.optionId == 2) {
                    if (!GameServer.openLoiDai) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    StringBuilder str = new StringBuilder();
                    if (Map.arenas.size() == 0) {
                        str = new StringBuilder(AlertFunction.NOT_INFO(player.getSession()));
                    } else {
                        int t = 1;
                        for (int j = Map.arenas.size() - 1; j >= 0; --j) {
                            str.append(t).append(". ").append(Map.arenas.get(j)).append(" \n");
                            ++t;
                        }
                    }
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.RESULT(player.getSession()), str.toString());
                } else if (playerTemplate.optionId == 3) {
                    if (!GameServer.openNinjaTaiNang) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    if (player.countLoiDai >= 50) {
                        NJUtil.sendServer(player.getSession(), AlertFunction.MAXLOIDAI(player.getSession()));
                        return;
                    }
                    player.goGiaiDau();
                } else if (playerTemplate.optionId == 4) {
                    if (!GameServer.openNinjaTaiNang) {
                        NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                        return;
                    }
                    player.topTaiNang();
                }
            } else if (playerTemplate.menuId == 4 && playerTemplate.optionId == 0) {
                String[] getSay = playerTemplate.getSay(player.getSession());
                player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
            }
        } catch (Exception e) {
        }
    }
}
