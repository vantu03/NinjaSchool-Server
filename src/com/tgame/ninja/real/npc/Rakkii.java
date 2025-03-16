package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.GameServer;
import com.tgame.ninja.server.NJUtil;

public class Rakkii implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        if (!GameServer.openVongXoay) {
            NJUtil.sendDialog(player.getSession(), AlertFunction.FEATURE_NOT_OPEN(player.getSession()));
            return;
        }
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        if (playerTemplate.menuId == 0 && playerTemplate.optionId == 0) {
            player.sendOpenUI(Item.UI_LUCKY_SPIN);
        } else if (playerTemplate.menuId == 1 && playerTemplate.optionId == 0) {
            player.doSendTextBoxId(AlertFunction.ALERT_QUAYSO2(player.getSession()), 7);
        } else if (playerTemplate.menuId == 2) {
            if (playerTemplate.optionId == 0) {
                player.infoXoso();
            } else if (playerTemplate.optionId == 1) {
                player.doSendTextBoxId(AlertFunction.LUCKY_DRAW(player.getSession()), 100);
            } else if (playerTemplate.optionId == 2) {
                String luat = "- Giá trị nhập xu thấp nhất của mỗi người là 1.000.000\n- Giá trị nhập xu cao nhất của mỗi người là 50.000.000\n- Giá trị xu còn lại sau mỗi lần đặt phải còn ít nhất 10.000.000\n- Mỗi 2 phút là bắt đầu vòng quay một lần.\n- Khi có người bắt đầu nhập xu thì mới bắt đầu đếm ngược thời gian.\n- Còn 10 giây cuối sẽ bắt đầu khóa không cho gửi xu.\n- Người chiến thắng sẽ nhận hết tổng số tiền tất cả người chơi khác đặt cược sau khi đã trừ thuế.\n- Khi người chơi ít hơn 10, thuế sẽ bằng số người chơi -1.\n- Người chơi nhiều hơn 10 người thuế sẽ là 10%.";
                NJUtil.sendAlertDialogInfo(player.getSession(), AlertFunction.LUCKY_DRAW(player.getSession()), luat);
            }
        }
    }
}
