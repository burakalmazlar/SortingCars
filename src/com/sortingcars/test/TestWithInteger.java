package com.sortingcars.test;

import com.sortingcars.engine.SortingEngine;
import com.sortingcars.engine.SortingJob;
import com.sortingcars.sorting.QuickSorting;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class TestWithInteger {

    public static void main(String[] args) {
        new TestWithInteger().test();
    }

    public void test() {

        SortingEngine sortingEngine = new SortingEngine(2, new QuickSorting());

        IntStream.range(1, 9)
                .forEach(i -> sortingEngine.sort(generateArray()));

        IntStream.range(1, 9)
                .mapToObj(i -> sortingEngine.poll())
                .collect(toList())
                .stream().map(SortingJob::getResult).forEach(this::writeToConsole);

        sortingEngine.shutdownEngine();

    }

    private LinkedList<Integer> generateArray() {
        int limit = 50;
        int[] array = IntStream.range(0, limit).toArray();
        IntStream.range(0, limit).forEach(i -> {
            int j = ThreadLocalRandom.current().nextInt(limit);
            int k = array[j];
            array[j] = array[limit - 1 - i];
            array[limit - 1 - i] = k;
        });
        LinkedList<Integer> collect = Arrays.stream(array)
                .mapToObj(Integer::new)
                .collect(LinkedList::new, (a, b) -> a.add(b), (a, b) -> a.addAll(b));

        writeToConsole(collect);

        return collect;
    }

    private void writeToConsole(LinkedList<Integer> collect) {
        System.out.println(collect.stream().map(String::valueOf).collect(Collectors.joining(",","[","]")));
    }
}
