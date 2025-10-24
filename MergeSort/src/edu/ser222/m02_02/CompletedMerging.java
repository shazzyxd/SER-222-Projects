package edu.ser222.m02_02;

import java.util.Random;
/**
 * Implements various divide and conquer algorithms.
 *
 * Last updated 4/2/2022.
 *
 * Completion time: 3.5 hrs
 *
 * @author Shazeb Mallick, Acuna, Sedgewick and Wayne
 * @verison 1.0
 */

public class CompletedMerging implements MergingAlgorithms {

    //TODO: implement interface methods.
    @Override
    public <T extends Comparable> Queue<T> mergeQueues(Queue<T> q1, Queue<T> q2) {
        //TODO: implement this!
        ListQueue<T> merged = new ListQueue<>();
        while(!q1.isEmpty() && !q2.isEmpty()) {
            if(less(q1.peek(), q2.peek())) {
                merged.enqueue(q1.dequeue());
            } else {
                merged.enqueue(q2.dequeue());
            }
        }

        while (!q1.isEmpty()) {
            merged.enqueue(q1.dequeue());
        }

        while (!q2.isEmpty()) {
            merged.enqueue(q2.dequeue());
        }
        return merged;
    }

    @Override
    public void sort(Comparable[] a) {
        //TODO: implement this!
        Comparable[] sorted = mergesort(a);
        for (int i = 0; i < a.length; i++) {
            a[i] = sorted[i];
        }
    }

    @Override
    public Comparable[] mergesort(Comparable[] a) {
        //TODO: implement this!
        if(a.length <= 1) {
            return a;
        }
        int low = 0;
        int high = a.length - 1;
        int mid = low + (high - low) / 2;

        Comparable[] left = new Comparable[mid - low + 1];
        Comparable[] right = new Comparable[high - mid];

        for (int i = low; i <= mid; i++) {
            left[i] = a[i];
        }

        int j = 0;
        for (int i = mid + 1; i <= high; i++) {
            right[j] = a[i];
            j++;
        }

        Comparable[] unsortedLeft = mergesort(left);
        Comparable[] unsortedRight = mergesort(right);


        return merge(unsortedLeft, unsortedRight);
    }

    @Override
    public Comparable[] merge(Comparable[] a, Comparable[] b) {
        //TODO: implement this!
        Comparable[] result = new Comparable[a.length + b.length];
        int lengthA = a.length;
        int lengthB = b.length;
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < lengthA && j < lengthB) {
            if (less(a[i], b[j])) {
                result[k] = a[i];
                i++;
            } else {
                result[k] = b[j];
                j++;
            }
            k++;
        }
        while (i < lengthA) {
            result[k] = a[i];
            i++;
            k++;
        }
        while (j < lengthB) {
            result[k] = b[j];
            j++;
            k++;
        }

        return result;
    }

    @Override
    public void shuffle(Object[] a) {
        //TODO: implement this!
        Object[] shuffled = shuffler(a);
        for (int i = 0; i < a.length; i++) {
            a[i] = shuffled[i];
        }
    }

    public Object[] shuffler(Object[] a) {
        if(a.length <= 1) {
            return a;
        }
        int low = 0;
        int high = a.length - 1;
        int mid = low + (high - low) / 2;

        Object[] left = new Object[mid - low + 1];
        Object[] right = new Object[high - mid];

        for (int i = low; i <= mid; i++) {
            left[i] = a[i];
        }

        int j = 0;
        for (int i = mid + 1; i <= high; i++) {
            right[j] = a[i];
            j++;
        }

        Object[] swapLeft = shuffler(left);
        Object[] swapRight = shuffler(right);

        return swap(swapLeft, swapRight);
    }

    public Object[] swap(Object[] a, Object[] b) {
        Object[] result = new Object[a.length + b.length];
        int lengthA = a.length;
        int lengthB = b.length;
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < lengthA && j < lengthB) {
            Random random = new Random();
            if (random.nextBoolean()) {
                result[k] = a[i];
                i++;
            } else {
                result[k] = b[j];
                j++;
            }
            k++;
        }

        while (i < lengthA) {
            result[k] = a[i];
            i++;
            k++;
        }

        while (j < lengthB) {
            result[k] = b[j];
            j++;
            k++;
        }

        return result;
    }


     
    /**
     * entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<String> q1 = new ListQueue<>(); q1.enqueue("E"); q1.enqueue("L"); q1.enqueue("O"); q1.enqueue("R"); q1.enqueue("T");
        Queue<String> q2 = new ListQueue<>(); q2.enqueue("A"); q2.enqueue("E"); q2.enqueue("M"); q2.enqueue("P"); q2.enqueue("S"); q2.enqueue("X");
        Queue<Integer> q3 = new ListQueue<>(); q3.enqueue(5); q3.enqueue(12); q3.enqueue(15); q3.enqueue(17); q3.enqueue(20);
        Queue<Integer> q4 = new ListQueue<>(); q4.enqueue(1); q4.enqueue(4); q4.enqueue(12); q4.enqueue(13); q4.enqueue(16); q4.enqueue(18);

        MergingAlgorithms ma = new CompletedMerging();

        //Q1 - sample test cases
        Queue merged1 = ma.mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = ma.mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.shuffle(b);
        show(b);
        
        ma.shuffle(b);
        show(b);
    }

    //below are utilities functions, please do not change them.
        
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}