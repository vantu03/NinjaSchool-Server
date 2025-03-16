package com.tgame.ninja.model;

import com.tgame.ninja.io.Message;
import com.tgame.ninja.io.Session;

public class NMessage {

    public Session conn;

    public Message message;

    public NMessage(Session conn, Message message) {
        this.conn = conn;
        this.message = message;
    }
}
