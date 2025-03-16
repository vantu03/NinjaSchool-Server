package com.tgame.ninja.model;

import java.util.Comparator;

public class Member {

    public static Comparator<Member> sort = (o1, o2) -> {
        if (o2.typeClan != o1.typeClan) {
            return o2.typeClan - o1.typeClan;
        }
        if (o2.pointClan == o1.pointClan) {
            return o2.level - o1.level;
        }
        return o2.pointClan - o1.pointClan;
    };

    public int classId;

    public boolean isOnline;

    public int level;

    public String name;

    public int pointClan;

    public int pointClanWeek;

    public int typeClan;

    public Member(int type) {
        typeClan = type;
    }
}
