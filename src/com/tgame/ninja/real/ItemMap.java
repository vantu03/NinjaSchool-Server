package com.tgame.ninja.real;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.model.Cmd;
import com.tgame.ninja.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemMap {

    private  static final Logger LOGGER = LoggerFactory.getLogger(ItemMap.class);

    public Item item;

    public short itemMapId;

    public int owner;

    public int x;

    public int y;

    public long timeStart;

    public int timeReturn;

    public ItemMap(Item item, int owner, int x, int y) {
        this.item = item;
        this.owner = owner;
        this.x = x;
        this.y = y;
        timeStart = System.currentTimeMillis();
    }

    public ItemMap(Item item, int owner, int x, int y, long timeStart) {
        this.item = item;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.timeStart = timeStart;
    }

    public void update(Map map) {
        try {
            if (timeStart < 0L) {
                if (timeStart < -2L) {
                    ++timeStart;
                } else if (timeStart == -2L) {
                    map.sendAddItemMap(this);
                    ++timeStart;
                }
                return;
            }
            if (item.getTemplateId() == 458) {
                return;
            }
            long timeNow = System.currentTimeMillis();
            if (owner != -1 && timeNow - timeStart > 15000L && item.template.type != 25) {
                owner = -1;
            }
            if (timeNow - timeStart > 45000L) {
                map.removeItemMap(this);
                Message message = new Message(Cmd.ITEMMAP_REMOVE);
                message.writeShort(itemMapId);
                map.sendToPlayer(message);
                message.cleanup();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
