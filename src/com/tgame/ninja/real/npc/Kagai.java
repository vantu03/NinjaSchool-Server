package com.tgame.ninja.real.npc;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Map;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class Kagai implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (player.map.getTemplateId() == 98 || player.map.getTemplateId() == 104) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                if (player.map.giatocchien != null) {
                    player.map.giatocchien.namePlayLefts.remove(player.name);
                    player.map.giatocchien.namePlayRights.remove(player.name);
                }
                player.doChangeMap(player.mapTemplateIdGo, false, "doMenu su_gia_nv");
                return;
            } else if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0 && player.map.giatocchien != null) {
                String str = player.map.giatocchien.nameGt1.name + ": " + AlertFunction.ACCUMULATION(player.getSession()) + " " + player.map.giatocchien.getPoint1() + " " + AlertFunction.POINT(player.getSession()) + "\n" + player.map.giatocchien.nameGt2.name + ": " + AlertFunction.ACCUMULATION(player.getSession()) + " " + player.map.giatocchien.getPoint2() + " " + AlertFunction.POINT(player.getSession());
                NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.RESULT(player.getSession()), str);
            }
            return;
        }
        if (player.map.getTemplateId() == 131 || player.map.getTemplateId() == 132) {
            if (playerTemplate.menuId != 0) {
                int cc1 = 0;
                int cc2 = 0;
                Map mm;
                for (int t2 = 0; t2 < Map.mapkeos.size(); ++t2) {
                    mm = Map.mapkeos.get(t2);
                    if (mm.zoneId == player.map.zoneId && mm.getTemplateId() == 130) {
                        for (int k = 0; k < mm.npcs.size(); ++k) {
                            if (mm.npcs.get(k).template.npcTemplateId == 142) {
                                cc1 = (mm.npcs.get(k).hp - 999999700) / 10 + (((mm.npcs.get(k).hp - 999999700) % 10 != 0) ? 1 : 0);
                            }
                            if (mm.npcs.get(k).template.npcTemplateId == 143) {
                                cc2 = (mm.npcs.get(k).hp - 999999700) / 10 + (((mm.npcs.get(k).hp - 999999700) % 10 != 0) ? 1 : 0);
                            }
                        }
                        break;
                    }
                }
                NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.RESULT(player.getSession()), "- Kẹo trắng: " + cc1 + "\n- Kẹo đen: " + cc2);
                return;
            }
            if (playerTemplate.optionId == 0) {
                player.typePk = Player.PK_NORMAL;
                player.updateTypePk();
                player.doChangeMap(27, false, "doMenu su_gia_nv 1");
                return;
            }
        }
        if (player.map.getTemplateId() == 117) {
            if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
                player.clearTestGT();
                player.doChangeMap(player.mapTemplateIdGo, false, "doMenu su_gia_nv 2");
            } else if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0) {
                player.doSendTextBoxId(AlertFunction.HEPL_TEST_DUN0(player.getSession()), 6);
            } else if (playerTemplate.menuId == 2 && playerTemplate.optionId == 0) {
                player.sendOpenUISay(32, AlertFunction.ALERT_THIDAUGT_4(player.getSession()));
            }
            return;
        }
        switch (playerTemplate.menuId) {
            case 0:
                if (!GameServer.openChienTruongKeo) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                if (playerTemplate.optionId == 0) {
                    for (int i = 0; i < Map.mapkeos.size(); ++i) {
                        if (Map.mapkeos.get(i).playerAs != null && Map.mapkeos.get(i).getTemplateId() == 131) {
                            for (int l = 0; l < Map.mapkeos.get(i).playerAs.size(); ++l) {
                                if (Map.mapkeos.get(i).playerAs.get(l).equals(player.name)) {
                                    player.mapClear();
                                    player.map.goOutMap(player);
                                    Map.mapkeos.get(i).waitGoInMap(player);
                                    player.sendMapTime((int) ((player.map.timeEnd - System.currentTimeMillis()) / 1000L));
                                    return;
                                }
                            }
                        }
                        if (Map.mapkeos.get(i).playerBs != null && Map.mapkeos.get(i).getTemplateId() == 132) {
                            for (int l = 0; l < Map.mapkeos.get(i).playerBs.size(); ++l) {
                                if (Map.mapkeos.get(i).playerBs.get(l).equals(player.name)) {
                                    player.mapClear();
                                    player.map.goOutMap(player);
                                    Map.mapkeos.get(i).waitGoInMap(player);
                                    player.sendMapTime((int) ((player.map.timeEnd - System.currentTimeMillis()) / 1000L));
                                    return;
                                }
                            }
                        }
                    }
                    if (player.level < 65) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, "Cấp độ của bạn không đủ để tham gia.");
                    } else if (player.countctkeo < 1) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, "Đã hết số lần tham gia. Vui lòng quay lại vào hôm sau.");
                    } else {
                        player.doChangeMap(133, false, "doMenu su_gia_nv 3");
                    }
                    return;
                } else if (playerTemplate.optionId == 1) {
                    NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.HELP(player.getSession()), "Chiến trường kẹo: \n- 20 ninjas sẽ chia làm 2 đội là Kẹo Trắng và Kẹo Đen.\n- Mỗi đội chơi sẽ có nhiệm vụ tấn công giỏ kẹo đối phương, nhặt kẹo và sau đó chạy về bỏ vào giỏ kẹo của đội bên mình.\n- Trong khoảng thời gian ninja giữ kẹo sẽ bị mất một lượng HP nhất định theo thời gian. Giữ càng nhiều thì nguy hiểm càng lớn.\n- Còn 10 phút cuối thì sẽ xuất hiện Phù Thủy Bí Ngô, khi Phù Thủy Bí Ngô hỏi đội nào thì đội đó phải đưa kẹo cho Phù Thủy Bí Ngô nếu không thành viên đội đó sẽ bị tấn công.\n- Kết thúc giỏ kẹo bên nào nhiều hơn thì bên đó sẽ giành chiến thắng.\n- Đội thằng được 3 Rương kẹo và đội thua được 1 rương kẹo.");
                }
                break;
            case 1:
                if (!GameServer.openGiaToc || !GameServer.openGiaTocChien) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                if (playerTemplate.optionId == 0) {
                    if (player.isGTChien()) {
                        return;
                    }
                    if (player.clan == null || !player.clan.isMain(player.name)) {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.ALERT_THIDAUGT_1(player.getSession()));
                        return;
                    }
                    player.doSendTextBoxId(AlertFunction.NAME_PLAYER(player.getSession()), 5);
                }
                break;
            case 2: {
                if (playerTemplate.optionId == 0) {
                    if (player.level >= 10) {
                        if (!player.reward.isUp10) {
                            player.reward.isUp10 = true;
                            player.sendUpdateLuong(5000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 1) {
                    if (player.level >= 20) {
                        if (!player.reward.isUp20) {
                            player.reward.isUp20 = true;
                            player.sendUpdateLuong(5000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 2) {
                    if (player.level >= 30) {
                        if (!player.reward.isUp30) {
                            player.reward.isUp30 = true;
                            player.sendUpdateLuong(10000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 3) {
                    if (player.level >= 40) {
                        if (!player.reward.isUp40) {
                            player.reward.isUp40 = true;
                            player.sendUpdateLuong(10000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 4) {
                    if (player.level >= 50) {
                        if (!player.reward.isUp50) {
                            player.reward.isUp50 = true;
                            player.sendUpdateLuong(15000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 5) {
                    if (player.level >= 60) {
                        if (!player.reward.isUp60) {
                            player.reward.isUp60 = true;
                            player.sendUpdateLuong(15000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 6) {
                    if (player.level >= 70) {
                        if (!player.reward.isUp70) {
                            player.reward.isUp70 = true;
                            player.sendUpdateLuong(20000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 7) {
                    if (player.level >= 80) {
                        if (!player.reward.isUp80) {
                            player.reward.isUp80 = true;
                            player.sendUpdateLuong(20000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                } else if (playerTemplate.optionId == 8) {
                    if (player.level >= 90) {
                        if (!player.reward.isUp90) {
                            player.reward.isUp90 = true;
                            player.sendUpdateLuong(25000);
                        } else {
                            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.HAVE_REWARD(player.getSession()));
                        }
                    } else {
                        player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.LEVEL_ERROR_2(player.getSession()));
                    }
                }
                break;
            }
            case 3:
                if (!GameServer.openTinhTu) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                String time = "";
                if (playerTemplate.optionId == 0) {
                    time = AlertFunction.DAY_3(player.getSession());
                } else if (playerTemplate.optionId == 1) {
                    time = AlertFunction.DAY_7(player.getSession());
                } else if (playerTemplate.optionId == 2) {
                    time = AlertFunction.DAY_15(player.getSession());
                }
                int confirmId = playerTemplate.optionId + 3;
                int[] points = { 50, 100, 150 };
                try {
                    Message message = new Message(Cmd.OPEN_UI_CONFIRM_POPUP);
                    message.writeByte(confirmId);
                    message.writeUTF(AlertFunction.TINH_TU1(player.getSession()) + " " + points[playerTemplate.optionId] + " " + AlertFunction.TINH_TU2(player.getSession()) + " " + time + " " + AlertFunction.TINH_TU3(player.getSession()));
                    NJUtil.sendMessage(player.getSession(), message);
                } catch (Exception e) {
                }
                break;
            case 4:
                if (!GameServer.openTinhLuyen) {
                    NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                    return;
                }
                if (playerTemplate.optionId == 0) {
                    player.sendOpenUI(Item.UI_LUYEN_THACH);
                } else if (playerTemplate.optionId == 1) {
                    player.sendOpenUI(Item.UI_TINH_LUYEN);
                } else if (playerTemplate.optionId == 2) {
                    player.sendOpenUI(Item.UI_DOI_OPTION);
                } else if (playerTemplate.optionId == 3) {
                    if (player.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.HELP(player.getSession()), "-\tTinh luyện trang bị\n- Để tinh luyện trang bị cần phải có Tử tinh thạch.\n- Độ tinh luyện cấp 1, 2, 3 phải sử dụng Tử tinh thạch sơ cấp.\n- Độ tinh luyện cấp 4, 5, 6 phải sử dụng Tử tinh thạch trung cấp.\n- Độ tinh luyện cấp 7, 8, 9 phải sử dụng Tử tinh thạch cao cấp.Tinh luyện đá:\n- Ghép 9 Tử tinh thạch sơ cấp sẽ nhận được 1 Tử tinh thạch trung cấp.\n- Ghép 9 Tử tinh thạch trung cấp sẽ nhận được 1 Tử tinh thạch cao cấp.\n- Sử dụng 3 viên đá Tử tinh thạch sơ cấp và Đá 11 sẽ nhận được 3 viên Tử tinh thạch trung cấp.\n- Sử dụng 3 viên đá Tử tinh thạch trung cấp và Đá 12 sẽ nhận được 3 viên Tử tinh thạch cao cấp.\nDịch chuyển trang bị:\n-\tTrang bị dịch chuyển phải là trang bị +12 trở lên.\n-\tTrang bị sau dịch chuyển sẽ có thêm 2 dòng thuộc tính dùng để Tinh luyện.\n-Để dịch chuyển cần phải có đủ 20 chuyển tinh thạch.\nThăng cấp sao thú cưỡi:\n- Thú cưỡi đạt level 100 người chơi có thể lựa chọn thăng cấp sao.\n- Thăng sao thành công Thú cưỡi sẽ trở lại level 1 với tiềm năng tốt hơn.\n- Cần sử dụng Chuyển tinh thạch để tăng sao cho thú cưỡi.");
                    } else {
                        NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.HELP(player.getSession()), "- Enhance Equipment\n- You need Essence to enhance your equipment.\n- Common Essence is needed to enhance level 1-2-3.\n- Rare Essence is needed to enhance level 4-5-6.\n- Mythical Essence is needed to enhance level 7-8-9. Upgrade essence:\n- Players can exchange 9 Common Essence for 1 Rare Essence.\n- Players can exchange 9 Rare Essence for 1 Mythical Essence.\n- Players can exchange 3 Common Essence and 1 Level 11 Stone for 3 Rare Essence.\n- Players can exchange 3 Rare Essence and 1 Level 12 Stone for 3 Mythical Essence.\nTransform Equipment:\n- Only Equipment +12 and higher can be transformed.\n- After Transformation, equipment will have 2 more options to enhance.\n-Players need 20 Enhance Stones to Transform.\nUpgrade Mount Grade:\n- To upgrade, mount must be level 100.\n- Successful Mount Upgrade will move mount to Grade +1 level 1.\n- Player must use Enhance Stone to upgrade mount grade.");
                    }
                }
        }
    }
}
