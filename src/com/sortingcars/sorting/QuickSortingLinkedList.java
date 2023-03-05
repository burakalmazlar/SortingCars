package com.sortingcars.sorting;

public class QuickSortingLinkedList<T extends Comparable<T>> implements Iterable<T> {

    private Node<T> first;
    private Node<T> current;

    public void add(T value) {
        if (first == null) {
            // adding initial head node
            first = new Node<>(value);
            current = first;
        } else {
            // adding new node and shifting current to it
            Node<T> newNode = new Node<>(value);
            current.next = newNode;
            current = newNode;
        }
    }

    public long sort() {
        iteration = 0;
        sort(first, current);
        return iteration;
    }

    private long iteration;

    private void sort(Node<T> head, Node<T> tail) {
        ++iteration;
        if (head == tail || head == tail.next) {
            return; // sort işlemini sadece birden fazla eleman olduğunda yapıyoruz
        }

        Node<T> pivot = head;
        Node<T> previous = head;
        Node<T> current = head;
        // ilk elemanı pivot node olarak kabul edip
        // pivot nodun değerinden daha küçük ve büyük olanlar
        // ayrı tarafta kalacak şekilde pivotu nodu kaydırarak listeyi ayırıyoruz
        // >->->->->  küçükler >-> pivot >-> büyükler >->->->->
        // kaydırdığımız pivot değeri listenin ortasında kalıyor
        // bu orta node yeni pivot nodumuz oluyor
        while ((current = current.next) != null) {
            if (pivot.value.compareTo(current.value) > 0) {
                if (pivot.next == current) {
                    // bir sonraki eleman daha küçükse
                    // değerlerini değiştiriyoruz
                    T temp = pivot.value;
                    pivot.value = current.value;
                    current.value = temp;
                    previous = pivot;
                    pivot = current;
                } else {
                    // arada daha büyük elemanlar varsa
                    // önce pivottaki değerle bir sonrakini
                    // daha sonra bulduğumuz küçük değerle
                    // pivottaki değeri değiştiriyoruz
                    // bu sayede pivot değerden büyük olanlar bir tarafta,
                    // pivot değerden küçük olanlar pivotun diğer tarafında kalmış oluyor
                    T tempNext = pivot.next.value;
                    pivot.next.value = current.value;
                    current.value = tempNext;
                    T tempPivot = pivot.value;
                    pivot.value = pivot.next.value;
                    pivot.next.value = tempPivot;
                    previous = pivot;
                    pivot = pivot.next;
                }
            }
        }

        // ayırdığımız ilk parçayı başlangıç nodundan yeni bulduğumuz pivot noduna
        // kadar(pivot node dahil değil) yeni bir liste olarak ele alıp recursive sorting uyguluyoruz
        sort(head, previous);

        if (head == previous) {
            // yeni bulduğumuz pivottan bir önceki node başlangıç noduysa
            // pivot dahil sort ediyoruz
            sort(previous.next, tail);
        } else {
            // pivotumuz listenin ortasındaysa
            // pivotun yeri kesinleştiği için
            // pivottan sonraki kısmı sort ediyoruz
            sort(previous.next.next, tail);
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator(first);
    }

    public class Iterator<T> implements java.util.Iterator<T> {
        Node<T> current;
        boolean currentIsFirst = true;


        Iterator(Node<T> current) {
            this.current = current;
        }

        @Override
        public boolean hasNext() {
            if (currentIsFirst) {
                return current != null;
            }
            return current.next != null;
        }

        @Override
        public T next() {
            if (currentIsFirst) {
                currentIsFirst = false;
            } else {
                current = current.next;
            }
            return current.value;
        }
    }

    private class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }
}
