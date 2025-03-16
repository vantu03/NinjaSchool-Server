package com.tgame.ninja.io;

public interface ISessionHandler {

    void processMessage(Session p0, Message p1);

    void onDisconnected(Session p0);

    void doLogout(Session p0);

    void doEncrypt(Session p0);

    void doLogin(Session p0, Message p1) throws Exception;
}
