package edu.ser222.m01_03;

import java.util.NoSuchElementException;

/**
 * This program provides an implementation of the Deque interface. Also provides a main that
 * demonstrates it.
 * 
 * @author Shazeb Mallick, Acuna
 * @version 1.0
 */


public class CompletedDeque<Item> implements Deque<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item data;
        Node next;
        Node prev;
    }

    public CompletedDeque() {
        first = null;
        last = null;
        size = 0;
    }

    public void enqueueFront(Item element) {

        Node newNode = new Node();
        newNode.data = element;

        if (size == 0) {
            first = newNode;
            last = newNode;

        } else {

            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        size++;
    }

    public void enqueueBack(Item element) {

        Node newNode = new Node();
        newNode.data = element;

        if (size == 0) {
            first = newNode;
            last = newNode;
        } else {

            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    public Item dequeueFront() throws NoSuchElementException {
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item result = first.data;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }

        size--;



        return result;
    }

    public Item dequeueBack() throws NoSuchElementException {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        Item result = last.data;
        if (first == last) {
            first = null;
            last = null;

        } else {
            last = last.prev;
            last.next = null;
        }
        size--;
        return result;
    }

    public Item first() throws NoSuchElementException {
        if (first == null) {
            throw new NoSuchElementException();
        }

        return first.data;
    }

    public Item last() throws NoSuchElementException {
        if (last == null) {
            throw new NoSuchElementException();
        }

        return last.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "empty";
        }
        StringBuilder sb = new StringBuilder();
        Node current = last;
        while (current != null) {
            sb.append(current.data);
            current = current.prev;
            if (current != null) {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    //TODO: implement all the methods

    /**
     * Program entry point for deque. 
     * @param args command line arguments
     */    
    public static void main(String[] args) {
        CompletedDeque<Integer> deque = new CompletedDeque<>();

        //standard queue behavior
        deque.enqueueBack(3);
        deque.enqueueBack(7);
        deque.enqueueBack(4);
        deque.dequeueFront();        
        deque.enqueueBack(9);
        deque.enqueueBack(8);
        deque.dequeueFront();
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());   

        //deque features
        System.out.println(deque.dequeueFront());        
        deque.enqueueFront(1);
        deque.enqueueFront(11);                         
        deque.enqueueFront(3);                 
        deque.enqueueFront(5);         
        System.out.println(deque.dequeueBack());
        System.out.println(deque.dequeueBack());        
        System.out.println(deque.last());                
        deque.dequeueFront();
        deque.dequeueFront();        
        System.out.println(deque.first());        
        System.out.println("size: " + deque.size());
        System.out.println("contents:\n" + deque.toString());            
    }
} 