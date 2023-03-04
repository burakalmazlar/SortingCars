package com.sortingcars.engine;

import com.sortingcars.car.Car;
import com.sortingcars.sorting.Sorting;
import com.sortingcars.threadpool.ManagedThread;
import com.sortingcars.threadpool.ThreadPool;

import java.util.LinkedList;
import java.util.List;

public class SortingEngine<T extends Comparable<T>> {

    private final ThreadPool pool;
    private final Sorting sorting;
    private long jobCounter = 0;
    LinkedList<SortingJob<T>> jobList = new LinkedList<>();

    public SortingEngine(int poolSize, Sorting sorting) {
        this.pool = new ThreadPool(poolSize);
        this.sorting = sorting;
    }

    // starts concurrent execution until max pool size reached
    // adding started jobs to job queue
    public void sort(LinkedList<T> list) {

        SortingJob<T> job = new SortingJob<>(list, jobCounter++, sorting);

        ManagedThread availableThread = pool.getAvailableThread();

        availableThread.executeJob(job);

        jobList.offerLast(job);

    }

    public SortingJob<T> poll() {
        return jobList.poll();
    }

    public void shutdownEngine() {
        pool.shutdown();
    }

}
