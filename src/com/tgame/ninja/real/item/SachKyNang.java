package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class SachKyNang implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.limitKynangso >= 3) {
            NJUtil.sendServer(player.getSession(), AlertFunction.LIMIT_KYNANG_SO(player.getSession()));
            return;
        }
        player.removeItem(item, 3);
        player.sendClearItemBag(item.indexUI);
        NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + AlertFunction.ONE_POINT_SKILL(player.getSession()));
        ++playerMain.skill_point;
        ++playerMain.limitKynangso;
        player.updateSkill();
    }
}
