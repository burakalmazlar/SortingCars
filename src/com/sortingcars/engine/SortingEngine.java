package com.sortingcars.engine;

import com.sortingcars.sorting.QuickSortingLinkedList;
import com.sortingcars.threadpool.ManagedThread;
import com.sortingcars.threadpool.ThreadPool;

import java.util.LinkedList;

public class SortingEngine<T extends Comparable<T>> {

    private final ThreadPool pool;
    private long jobCounter = 0;
    private LinkedList<SortingJob<T>> jobList = new LinkedList<>();

    public SortingEngine(int poolSize) {
        this.pool = new ThreadPool(poolSize);
    }

    // starts concurrent execution until max pool size reached
    // adding started jobs to job queue
    public void sort(QuickSortingLinkedList<T> list) {

        SortingJob<T> job = new SortingJob<>(list, jobCounter++);

        ManagedThread availableThread = pool.getAvailableThread();

        availableThread.executeJob(job);

        jobList.offerLast(job);

    }

    public LinkedList<QuickSortingLinkedList<T>> getAllResults() {
        LinkedList<QuickSortingLinkedList<T>> results = new LinkedList<>();
        while(!jobList.isEmpty()) {
            results.add(jobList.poll().getResult());
        }
        return results;
    }

    public void shutdownEngine() {
        pool.shutdown();
    }

}
