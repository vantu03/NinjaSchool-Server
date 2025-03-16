package com.tgame.ninja.model;

import java.util.Comparator;

public class TopDaiGia {

    public String name;

    public int money;

    public static final Comparator<TopDaiGia> sort = (o1, o2) -> o2.money - o1.money;
}
