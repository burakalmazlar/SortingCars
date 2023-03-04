package com.sortingcars.engine;

public class Statistics {

    int poolSize;
    long millisecond;

    public Statistics(int poolsize, long milisecond) {
        this.poolSize = poolsize;
        this.millisecond = milisecond;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public long getMillisecond() {
        return millisecond;
    }

    @Override
    public String toString() {
        return poolSize + "\t" + millisecond;
    }
}
