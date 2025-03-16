package com.tgame.ninja.real.npc;

import com.tgame.ninja.branch.tasks.NguyetNhanTask;
import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class TrangSuc implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (player.taskMain == null && player.getTaskFinish() == 14) {
            if (playerMain.isNhanban()) {
                player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
                return;
            }
            if (playerTemplate.menuId == 0) {
                player.confirmTask(playerTemplate.playerTemplateId, new String[]{ AlertFunction.GET(player.getSession()), AlertFunction.NO(player.getSession()) });
                return;
            }
            --playerTemplate.menuId;
        } else if (player.taskMain != null && player.taskMain.index >= player.taskMain.template.subNames[player.getSession().typeLanguage].length - 1 && player.getTaskFinish() == 14) {
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
        if (playerTemplate.menuId == 0) {
            if (playerTemplate.optionId == 0) {
                player.sendOpenUI(Item.UI_LIEN);
            } else if (playerTemplate.optionId == 1) {
                player.sendOpenUI(Item.UI_NHAN);
            } else if (playerTemplate.optionId == 2) {
                player.sendOpenUI(Item.UI_NGOCBOI);
            } else if (playerTemplate.optionId == 3) {
                player.sendOpenUI(Item.UI_PHU);
            }
        } else if (playerTemplate.menuId == 1) {
            if (playerMain.isNhanban()) {
                return;
            }
            if (!GameServer.openNguyetNhan) {
                NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
                return;
            }
            if (player.level < 50) {
                player.sendOpenUISay(2, AlertFunction.NOT_LEVEL50(player.getSession()));
                return;
            }
            if (player.totalNvNguyetNhan <= 0) {
                if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                    NJUtil.sendServer(player.getSession(), "Con đã hoàn thành đủ số nhiệm vụ cho ngày hôm nay rồi");
                } else {
                    NJUtil.sendServer(player.getSession(), "There is no more task for you today");
                }
                return;
            }
            if (playerTemplate.optionId == 0) {
                if (playerMain.nguyetNhanTask != null) {
                    if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(player.getSession(), "Con hãy hoàn thành nhiệm vụ đã được giao trước");
                    } else {
                        NJUtil.sendServer(player.getSession(), "You still have your previous task incomplete");
                    }
                    return;
                }
                int idItemTemplate = NguyetNhanTask.getIDTemplateQuest(playerMain);
                if (idItemTemplate == -1) {
                    return;
                }
                --player.totalNvNguyetNhan;
                String[] info = playerMain.nguyetNhanTask.getInfoSend2Client(playerMain.getSession());
                NJUtil.sendAlertDialogInfo(playerMain.getSession(), info[0], info[1]);
            } else if (playerTemplate.optionId == 1) {
                if (playerMain.nguyetNhanTask == null) {
                    if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(playerMain.getSession(), "Hiện tại con chưa nhận nhiệm vụ nào");
                    } else {
                        NJUtil.sendServer(playerMain.getSession(), "You dont have any task right now");
                    }
                } else {
                    if (!playerMain.nguyetNhanTask.isFinish()) {
                        if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(playerMain.getSession(), "Con hãy hoàn thành nhiệm vụ đã được giao trước");
                        } else {
                            NJUtil.sendServer(playerMain.getSession(), "You still have your previous task incomplete");
                        }
                        return;
                    }
                    playerMain.doFinishTaskNguyetNhan();
                }
            } else if (playerTemplate.optionId == 2) {
                if (playerMain.nguyetNhanTask == null) {
                    if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                        NJUtil.sendServer(playerMain.getSession(), "Hiện tại con chưa nhận nhiệm vụ nào");
                    } else {
                        NJUtil.sendServer(playerMain.getSession(), "You dont have any task right now");
                    }
                } else {
                    if (player.nguyetNhanTask.isFinish()) {
                        if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(player.getSession(), "Hoàn thành nhiệm vụ, hãy gặp # để trả nhiệm vụ".replace("#", "Ameji"));
                        } else {
                            NJUtil.sendServer(player.getSession(), "Task completed, meet # to end the task".replace("#", "Ameji"));
                        }
                        return;
                    }
                    try {
                        Message message = new Message(Cmd.OPEN_UI_CONFIRM_POPUP);
                        message.writeByte(8);
                        if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                            message.writeUTF("Bạn có muốn huỷ nhiệm vụ " + player.nguyetNhanTask.name + " không?");
                        } else {
                            message.writeUTF("Do you want to cancel mission " + player.nguyetNhanTask.name + " ?");
                        }
                        NJUtil.sendMessage(playerMain.getSession(), message);
                    } catch (Exception e) {
                    }
                }
            } else if (playerTemplate.optionId == 3) {
                int[] point = {
                    player.pointNon,
                    player.pointVukhi,
                    player.pointAo,
                    player.pointLien,
                    player.pointGangtay,
                    player.pointNhan,
                    player.pointQuan,
                    player.pointNgocboi,
                    player.pointGiay,
                    player.pointPhu
                };
                for (int m = 0; m < point.length; ++m) {
                    if (point[m] < 100) {
                        if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                            NJUtil.sendServer(playerMain.getSession(), "Không đủ 100 điểm " + Player.NAME_DANH_VONG[player.getSession().typeLanguage][m]);
                        } else {
                            NJUtil.sendServer(playerMain.getSession(), "Do not enough 100 " + Player.NAME_DANH_VONG[player.getSession().typeLanguage][m] + " points");
                        }
                        return;
                    }
                }
                Item it = new Item(Item.NGUYET_NHAN_CAP_1, true, "domenu nguyet nhan");
                it.createOptionNguyetNhan();
                player.addItemToBagNoDialog(it);
            } else if (playerTemplate.optionId == 4) {
                player.doUpdgradeNguyetNhan(false, true);
            } else if (playerTemplate.optionId == 5) {
                player.doUpdgradeNguyetNhan(true, true);
            } else if (playerTemplate.optionId == 6) {
                if (playerMain.getSession().typeLanguage == GameServer.LANG_VI) {
                    if (player.nguyetNhanTask != null) {
                        String[] infoSend2Client;
                        String[] info = infoSend2Client = player.nguyetNhanTask.getInfoSend2Client(playerMain.getSession());
                        int n = 1;
                        infoSend2Client[n] = infoSend2Client[n] + "\n- Có thể nhận thêm " + player.totalNvNguyetNhan + " nhiệm vụ trong ngày.";
                        int n2 = 1;
                        info[n2] = info[n2] + "\n- Sử dụng Danh vọng phù để tăng số lần nhận nhiệm vụ trong ngày.";
                        int n3 = 1;
                        info[n3] = info[n3] + "\n- Có thể sử dụng thêm " + player.useDanhVongPhu + " Danh vọng phù trong ngày.";
                        NJUtil.sendAlertDialogInfo(player.getSession(), info[0], info[1]);
                    } else {
                        String info = "- Có thể nhận thêm " + player.totalNvNguyetNhan + " nhiệm vụ trong ngày.";
                        info = info + "\n- Sử dụng Danh vọng phù để tăng số lần nhận nhiệm vụ trong ngày.";
                        info = info + "\n- Có thể sử dụng thêm " + player.useDanhVongPhu + " Danh vọng phù trong ngày.";
                        NJUtil.sendAlertDialogInfo(player.getSession(), "Thông báo", info);
                    }
                } else {
                    if (player.nguyetNhanTask != null) {
                        String[] infoSend2Client2;
                        String[] info = infoSend2Client2 = player.nguyetNhanTask.getInfoSend2Client(playerMain.getSession());
                        int n4 = 1;
                        infoSend2Client2[n4] = infoSend2Client2[n4] + "\n- Can take " + player.totalNvNguyetNhan + " Daily Mission.";
                        int n5 = 1;
                        info[n5] = info[n5] + "\n- Use  Glory ticket  to take more quest per day.";
                        int n6 = 1;
                        info[n6] = info[n6] + "\n- Can use " + player.useDanhVongPhu + " more Glory ticket  in a day.";
                        NJUtil.sendAlertDialogInfo(player.getSession(), info[0], info[1]);
                    } else {
                        String info = "- Can take " + player.totalNvNguyetNhan + " Daily Mission.";
                        info = info + "\n- Use Glory ticket  to take more quest per day.";
                        info = info + "\n- Can use " + player.useDanhVongPhu + " more Glory ticket  in a day.";
                        NJUtil.sendAlertDialogInfo(player.getSession(), "Alert", info);
                    }
                }
            }
        } else if (playerTemplate.menuId == 2 && playerTemplate.optionId == 0) {
            String[] getSay = playerTemplate.getSay(player.getSession());
            player.sendOpenUISay(playerTemplate.playerTemplateId, getSay[NJUtil.randomNumber(getSay.length)]);
        }
    }
}
