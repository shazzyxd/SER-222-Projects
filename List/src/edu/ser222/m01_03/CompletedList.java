package edu.ser222.m01_03;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * CompletedList represents an implementation of a list.
 *
 * @author Shazeb Mallick, Acuna
 * @version 1.0
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {

    //The following three variables are a suggested start if you are using a list implementation.
    //protected int count;
    //protected int modChange;
    //protected DoubleLinearNode<T> head, tail;

    //TODO: implement this!\

    protected int count;
    protected Node first;
    protected Node last;
    protected int modChange;

    class Node {
        T data;
        Node next;
        Node prev;
    }

    public CompletedList() {
        first = null;
        last = null;
        count = 0;
        modChange = 0;
    }

    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T data = first.data;

        if (first.next != null) {
            first = first.next;
            first.prev = null;
        } else {
            first = null;
            last = null;
        }
        modChange++;
        count--;
        return data;
    }

    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        T data = last.data;

        if (last.prev != null) {
            last = last.prev;
            last.next = null;
        } else {
            first = null;
            last = null;
        }

        modChange++;
        count--;
        return data;
    }

    public T remove(T element) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Iterator<T> iter = iterator();

        while (iter.hasNext()) {
            T current = iter.next();

            if (current.equals(element)) {
                iter.remove();
                return current;
            }
        }

        throw new NoSuchElementException();
    }

    public T first() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.data;
    }

    public T last() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return last.data;
    }

    public int size() {
        return count;
    }

    private class ListIterator implements Iterator<T> {

        private Node current;
        private Node lastReturned;
        private final int expectedModCount;
        private boolean canRemove;

        public ListIterator() {
            current = first;
            expectedModCount = modChange;
            lastReturned = null;
            canRemove = false;
        }

        public boolean hasNext() {
            if (expectedModCount != modChange) {
                throw new ConcurrentModificationException();
            }
            return current != null;
        }

        public T next() {
            if (expectedModCount != modChange) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            lastReturned = current;
            current = current.next;
            canRemove = true;
            return lastReturned.data;
        }

        public void remove() {
            if (!canRemove) {
                throw new UnsupportedOperationException();
            }

            if (lastReturned.prev != null) {
                lastReturned.prev.next = lastReturned.next;
            } else {
                first = lastReturned.next;
            }

            if (lastReturned.next != null) {
                lastReturned.next.prev = lastReturned.prev;
            } else {
                last = lastReturned.prev;
            }

            count--;
            modChange++;
            canRemove = false;
            lastReturned = null;

        }
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public Iterator<T> iterator() {
        return new ListIterator();
    }

    public boolean contains(T target) {
        Iterator<T> iter = iterator();
        while(iter.hasNext()) {
            if(iter.next().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        if (isEmpty()) {
            return "empty";
        }

        StringBuilder sb = new StringBuilder();
        Node current = first;
        while(current != null) {
            sb.append(current.data);

            if (current.next != null) {
                sb.append(" ");
                current = current.next;
            } else {
                break;
            }
        }
        return sb.toString();
    }
}