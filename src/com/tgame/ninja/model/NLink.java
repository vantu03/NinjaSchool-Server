package com.tgame.ninja.model;

public class NLink {

    public int mapTemplateId1;

    public int minX1;

    public int maxX1;

    public int minY1;

    public int maxY1;

    public int X1;

    public int Y1;

    public int mapTemplateId2;

    public int minX2;

    public int maxX2;

    public int minY2;

    public int maxY2;

    public int X2;

    public int Y2;

    public NLink clone() {
        NLink nLink = new NLink();
        nLink.mapTemplateId1 = mapTemplateId1;
        nLink.minX1 = minX1;
        nLink.maxX1 = maxX1;
        nLink.minY1 = minY1;
        nLink.maxY1 = maxY1;
        nLink.X1 = X1;
        nLink.Y1 = Y1;
        nLink.mapTemplateId2 = mapTemplateId2;
        nLink.minX2 = minX2;
        nLink.maxX2 = maxX2;
        nLink.minY2 = minY2;
        nLink.maxY2 = maxY2;
        nLink.X2 = X2;
        nLink.Y2 = Y2;
        return nLink;
    }
}
