package com.tgame.ninja.real.npc;

import com.tgame.ninja.model.AlertFunction;
import com.tgame.ninja.model.PlayerTemplate;
import com.tgame.ninja.real.NpcPlayer;
import com.tgame.ninja.real.Player;
import com.tgame.ninja.server.NJUtil;

public class ChauBaRei implements INpcHandler {

    @Override
    public void openMenu(Player player, PlayerTemplate playerTemplate) {
        Player playerMain = player.getPlayerMainControl();
        if (playerMain.isNhanban()) {
            player.sendOpenUISay(playerTemplate.playerTemplateId, AlertFunction.PHAN_THAN_NO_PERMIS(player.getSession()));
            return;
        }
        for (int i = 0; i < player.map.players.size(); ++i) {
            if (player.map.players.get(i).owner != null && player.map.players.get(i).owner.equals(player)) {
                return;
            }
        }
        NpcPlayer npcPlayer = null;
        for (int i = 0; i < player.map.npcPlayers.size(); ++i) {
            NpcPlayer np = player.map.npcPlayers.get(i);
            if (np.template.playerTemplateId == 17) {
                npcPlayer = np;
                break;
            }
        }
        if (npcPlayer != null) {
            Player p = new Player(playerTemplate.getName(player.getSession()), 1, playerTemplate.headId);
            p.owner = player;
            p.headId = 96;
            p.bodyId = 94;
            p.legId = 95;
            p.weaponId = -1;
            p.map = player.map;
            p.level = 30;
            p.x = (short) npcPlayer.x;
            p.y = (short) npcPlayer.y;
            p.hp_full = 2000;
            p.hp = 2000;
            p.sysNpc = NJUtil.randomNumber(3) + 1;
            p.resFire = 500;
            p.resIce = 500;
            p.resWind = 500;
            p.playerId = -player.playerId;
            p.timeUpdate = 4;
            player.map.playerIns.add(p);
        }
    }
}
