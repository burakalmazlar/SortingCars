package com.sortingcars.car;

public class Car implements Comparable<Car> {

    private long recId;
    private String serial;
    private Color color;
    private Destination destination;

    public static final Destination[] DESTINATIONS = {
            new Destination("Los Angeles", 0),
            new Destination("Houston", 1),
            new Destination("New Orleans", 2),
            new Destination("Miami", 3),
            new Destination("New York", 4)};

    public Car(long recId, String serial, Color color, Destination destination) {
        this.recId = recId;
        this.serial = serial;
        this.color = color;
        this.destination = destination;
    }

    public long getRecId() {
        return recId;
    }

    public String getSerial() {
        return serial;
    }

    public Color getColor() {
        return color;
    }

    public Destination getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s", destination, color, serial, recId);
    }

    @Override
    public int compareTo(Car car) {
        int c1 = this.destination.compareTo(car.destination);
        if (c1 == 0) {
            int c2 = this.color.compareTo(car.color);
            if (c2 == 0) {
                int c3 = this.serial.compareTo(car.serial);
                return c3;
            } else {
                return c2;
            }
        } else {
            return c1;
        }
    }
}
