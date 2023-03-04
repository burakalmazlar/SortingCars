package com.sortingcars.sorting;

import java.util.List;

public interface Sorting<T extends Comparable<T>> {

    public void sort(List<T> iterable);

}
