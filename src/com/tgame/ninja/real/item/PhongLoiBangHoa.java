package com.tgame.ninja.real.item;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.Item;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class PhongLoiBangHoa implements IUseHandler {

    @Override
    public void useItem(Player player, Item item) {
        Player playerMain = player.getPlayerMainControl();
        switch (item.getTemplateId()) {
            case 308: {
                if (playerMain.limitBanhPhongloi >= 10) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.LIMIT_TRUNGTHU1(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + AlertFunction.ONE_POINT_SKILL(player.getSession()));
                ++playerMain.skill_point;
                ++playerMain.limitBanhPhongloi;
                player.updateSkill();
                break;
            }
            case 309: {
                if (playerMain.limitBanhBangHoa >= 10) {
                    NJUtil.sendServer(player.getSession(), AlertFunction.LIMIT_TRUNGTHU1(player.getSession()));
                    return;
                }
                player.removeItem(item, 3);
                player.sendClearItemBag(item.indexUI);
                NJUtil.sendServer(player.getSession(), AlertFunction.YOU_GET(player.getSession()) + " " + AlertFunction.TEN_POINT_POTENTIAL(player.getSession()));
                playerMain.potential_point += 10;
                ++playerMain.limitBanhBangHoa;
                player.updatePotential();
                break;
            }
        }
    }
}
