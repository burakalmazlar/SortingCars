package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.engine.SortingEngine;
import com.sortingcars.sorting.QuickSortingLinkedList;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class QuickCarSorting {

    public void run(QuickSortingLinkedList<Car>[] lists, int maxThread) {
        long start = nanoTime();
        System.out.println("Sorting started.");

        SortingEngine<Car> sortingEngine = new SortingEngine<>(maxThread);

        for (QuickSortingLinkedList<Car> carList : lists) {
            sortingEngine.sort(carList);
        }

        sortingEngine.getAllResults();

        sortingEngine.shutdownEngine();

        System.out.println("Sorting ended.");
        System.err.println("Total elapsed time with "+maxThread+" thread = " +
                MILLISECONDS.convert(nanoTime()-start, NANOSECONDS));

    }





}
