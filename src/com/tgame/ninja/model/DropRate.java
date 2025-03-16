package com.tgame.ninja.model;

public class DropRate {

    public static int[][] RARITY_LIST = {
        { 1, 6500 },
        { 2, 2500 },
        { 3, 1100 },
        { 4, 500 },
        { 5, 150 },
    };

    public static int maxPercent = 0;

    public int rarity;

    public int itemId;

    static {
        for (int[] rarity : RARITY_LIST) {
            maxPercent += rarity[1];
        }
    }

    public DropRate(int rarity, int itemId) {
        this.rarity = rarity;
        this.itemId = itemId;
    }

    public static int getClosestRarity(int random) {
        int percentage = 0;
        for (int[] rarity : RARITY_LIST) {
            percentage += rarity[1];
            if (random < percentage) {
                return rarity[0];
            }
        }
        return RARITY_LIST[0][0];
    }
}
