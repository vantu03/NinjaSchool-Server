package com.tgame.ninja.model;

import com.tgame.ninja.io.Session;
import java.util.Comparator;

public class TopHangDong {

    public String name;

    public int ruong;

    public static Comparator<TopHangDong> sort = (o1, o2) -> o2.ruong - o1.ruong;

    public TopHangDong(String name, int ruong) {
        this.name = name;
        this.ruong = ruong;
    }

    public String getStr(Session conn) {
        return name + ": " + ruong + " " + AlertFunction.RUONG(conn);
    }
}
