package com.sortingcars.car;

public enum Color implements Comparable<Color> {

    RED, BLUE, BLACK, WHITE;

    public static Color fromOrdinal(int ordinal) {
        switch (ordinal) {
            case 0: return RED;
            case 1: return BLUE;
            case 2: return BLACK;
            default: return WHITE;
        }
    }
}
