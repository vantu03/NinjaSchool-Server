package com.tgame.ninja.io;

import java.util.Vector;

public class SessionManager {

    public static SessionManager instance = new SessionManager();

    public Vector<Session> sessionList = new Vector<>();

    public int size() {
        return sessionList.size();
    }

    public Session get(int index) {
        try {
            return sessionList.get(index);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    public Session find(int userId) {
        try {
            for (Session session : sessionList) {
                if (session.userId == userId) {
                    return session;
                }
            }
        } catch (Exception exception) {
        }
        return null;
    }

    public void add(Session s) {
        sessionList.add(s);
    }

    public void remove(Session s) {
        sessionList.remove(s);
    }
}
