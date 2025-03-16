package com.tgame.ninja.real;

public class GemTemplate {

    public static int[] EXP_GEM = { 0, 10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000 };

    public static int[] EXP_UP_LEVEL = { 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000, 100000 };

    public static int[][] ID_OPTION_GEM = {
        { 102, 126, 114 },
        { 73, 124, 115 },
        { 103, 121, 116 },
        { 105, 125, 117 }
    };

    public static int[][] ID_OPTION_GEM_SUB = {
        { 115, 105, 118 },
        { 114, 73, 119 },
        { 125, 120, 126 },
        { 116, 117, 124 }
    };

    public static int[][] VALUE = {
        { 100, 500, 1, 5, 1, 5 },
        { 50, 100, 1, 10, 5, 10 },
        { 100, 200, 1, 5, 5, 10 },
        { 100, 500, 10, 50, 10, 50 }
    };

    public static int[][] VALUE_SUB = {
        { 5, 50, 10, 100, 1, 20 },
        { 1, 5, 10, 100, 1, 3 },
        { 10, 100, 1, 3, 1, 5 },
        { 5, 50, 10, 100, 1, 5 }
    };

    public int getExpGem(int level) {
        return GemTemplate.EXP_GEM[level];
    }

    public static long[] getLevelGem(long totalExp, int lvStart) {
        long[] info = { lvStart, 0L };
        for (int i = lvStart; i < GemTemplate.EXP_UP_LEVEL.length && totalExp > GemTemplate.EXP_UP_LEVEL[i]; ++i) {
            ++info[0];
            info[1] += GemTemplate.EXP_UP_LEVEL[i];
        }
        return info;
    }
}
