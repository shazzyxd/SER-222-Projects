package edu.ser222.m01_03;

/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author Shazeb Mallick, Acuna
 * @version 1.0
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
         implements OrderedListADT<T> {



    public CompletedOrderedList() {
        super();
    }

    public void add(T element) {
        if (element == null) {
            throw new NullPointerException();
        }
        Node newNode = new Node();
        newNode.data = element;
        newNode.next = null;
        newNode.prev = null;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            Node current = first;
            Node previous = null;

            while (current != null && element.compareTo(current.data) > 0) {
                previous = current;
                current = current.next;
            }

            if (previous == null) {
                newNode.next = first;
                first.prev = newNode;
                first = newNode;
            } else if (current == null) {
                previous.next = newNode;
                newNode.prev = previous;
                last = newNode;
            } else {
                previous.next = newNode;
                newNode.prev = previous;
                newNode.next = current;
                current.prev = newNode;
            }
        }

        count++;
        modChange++;


    }

}
