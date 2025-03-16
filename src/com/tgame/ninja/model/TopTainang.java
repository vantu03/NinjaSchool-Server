package com.tgame.ninja.model;

import java.util.*;
import com.tgame.ninja.io.*;

public class TopTainang
{
    public String name;
    public int point;
    public static final Comparator<TopTainang> sort = (o1, o2) -> o2.point - o1.point;
    
    public TopTainang(String name, int point) {
        this.name = name;
        this.point = point;
    }
    
    public String getStr(Session conn) {
        return name + " " + AlertFunction.HAVE_POINT(conn) + " " + point + " " + AlertFunction.POINT(conn);
    }
}
