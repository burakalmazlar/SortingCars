package com.sortingcars.car;

public class Destination implements Comparable<Destination> {

    private final String name;
    private final int ordinal;

    public Destination(String name, int ordinal) {
        this.name = name;
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Destination o) {
        return ordinal - o.ordinal;
    }
}
