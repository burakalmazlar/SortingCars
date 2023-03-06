package com.sortingcars.engine;

import com.sortingcars.Main;
import com.sortingcars.sorting.QuickSortingLinkedList;
import com.sortingcars.threadpool.Job;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class SortingJob<T extends Comparable<T>> implements Job {

    private Object jobLock = new Object();

    private QuickSortingLinkedList<T> list;

    private boolean executed = false;
    private final long id;


    public SortingJob(QuickSortingLinkedList<T> list, long id) {
        this.list = list;
        this.id = id;
    }

    @Override
    public void execute() {
        synchronized (jobLock) {
            long start = nanoTime();
            long iteration = list.sort();
            long elapsedTime = MILLISECONDS.convert(nanoTime() - start, NANOSECONDS);
            String threadName = Thread.currentThread().getName();
            String fileName = "cars-" + id + "-sorted.txt";
            System.out.println("Sorting job " + id + " executed in " + elapsedTime + " ms by " +
                    threadName + " with " + iteration + " iteration and written to file "+ fileName);
            Main.writeToFile(list, fileName);
            executed = true;
            // job execution finished
            // inform getResult method to return the result
            jobLock.notify();
        }
    }

    public QuickSortingLinkedList<T> getResult() {

        synchronized (jobLock) {
            if (executed) {
                // job execution is already finished
                // return the result
                return list;
            }
            try {
                // job execution not yet finished
                // waiting to be notified from job execute method
                jobLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public long getId() {
        return id;
    }

}
