package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.car.Color;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class Main {

    public static void main(String[] args) {
        int numberOfLists = 10;
        long numberOfCars = 100_000;
        int maxThread = 4;
        if(args.length == 3) {
            numberOfLists = Integer.parseInt(args[0]);
            numberOfCars = Long.parseLong(args[1]);
            maxThread = Integer.parseInt(args[2]);
        }

        LinkedList<Car>[] lists = new LinkedList[numberOfLists];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = generate(numberOfCars, i);
        }

        new QuickCarSorting().run(lists, maxThread);

    }

    public static LinkedList<Car> generate(long size, int i) {

        LinkedList<Car> carList = LongStream.range(0, size).mapToObj(recId ->
                        new Car(recId, UUID.randomUUID().toString(),
                                Color.fromOrdinal(ThreadLocalRandom.current().nextInt(4)),
                                Car.DESTINATIONS[ThreadLocalRandom.current().nextInt(5)]))
                .collect(LinkedList::new, (a, b) -> a.add(b), (a, b) -> a.addAll(b));

        return carList;

    }
}
