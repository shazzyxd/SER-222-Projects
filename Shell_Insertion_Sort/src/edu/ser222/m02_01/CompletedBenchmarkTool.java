package edu.ser222.m02_01;

import java.util.Random;

/**
 * Implements a benchmarking tool on the Insertion Sort and
 * Selection Sort algorithms
 * 
 * Completion time: 2.0 hrs
 *
 * @author Shazeb Mallick, Acuna, Sedgewick
 * @version 1.0
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellsort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.

    public Integer[] generateTestDataBinary(int size) {
        Integer[] data = new Integer[size];
        for (int i = 0; i < size; i++) {
            if (i < size / 2) {
                data[i] = 0; // First half: 0s
            } else {
                data[i] = 1; // Second half: 1s
            }
        }
        return data;
    }

    public Integer[] generateTestDataHalves(int size) {
        Integer[] data = new Integer[size];
        int currentValue = 0;
        int range = size / 2;

        // Fill the first half with 0
        for (int i = 0; i < range; i++) {
            data[i] = 0;
        }

        int startIdx = range; // The second half starts here

        // Fill the remaining halves following the given rules
        while (range >= 1) {
            range /= 2;
            currentValue++;// Halve the range
            for (int i = startIdx; i < startIdx + range; i++) {
                data[i] = currentValue; // Fill with the next value
            }
            startIdx += range;

        }

        if (data[size - 1] == null) {
            data[size - 1] = currentValue; // Set to the current value
        }

        return data;
    }

    public Integer[] generateTestDataHalfRandom(int size) {
        Integer[] data = new Integer[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (i < size / 2) {
                data[i] = 0; // First half: 0s
            } else {
                data[i] = random.nextInt(Integer.MAX_VALUE);
            }
        }
        return data;
    }

    public double computeDoublingFormula(double t1, double t2) {
        return Math.log(t2 / t1) / Math.log(2); // Compute and return b
    }

    public double benchmarkInsertionSort(Integer[] small, Integer[] large) {
        // Measure time for small array
        Stopwatch stopwatch = new Stopwatch();
        insertionSort(small);
        double t1 = stopwatch.elapsedTime();

        // Measure time for large array
        stopwatch = new Stopwatch(); // Reset stopwatch
        insertionSort(large);
        double t2 = stopwatch.elapsedTime();

        // Compute and return b value
        return computeDoublingFormula(t1, t2);
    }

    public double benchmarkShellsort(Integer[] small, Integer[] large) {
        // Measure time for small array
        Stopwatch stopwatch = new Stopwatch();
        shellsort(small);
        double t1 = stopwatch.elapsedTime();

        // Measure time for large array
        stopwatch = new Stopwatch(); // Reset stopwatch
        shellsort(large);
        double t2 = stopwatch.elapsedTime();

        // Compute and return b value
        return computeDoublingFormula(t1, t2);
    }

    public void runBenchmarks(int size) {

        // Step 1: Generate small (size) and large (2 * size) versions for each dataset
        Integer[] binarySmall = generateTestDataBinary(size);
        Integer[] binaryLarge = generateTestDataBinary(size * 2);

        Integer[] halvesSmall = generateTestDataHalves(size);
        Integer[] halvesLarge = generateTestDataHalves(size * 2);

        Integer[] randomSmall = generateTestDataHalfRandom(size);
        Integer[] randomLarge = generateTestDataHalfRandom(size * 2);

        // Step 2: Benchmark insertionSort and shellsort on each dataset
        // Binary
        double binaryInsertionB = benchmarkInsertionSort(binarySmall, binaryLarge);
        double binaryShellB = benchmarkShellsort(binarySmall, binaryLarge);

        // Halves
        double halvesInsertionB = benchmarkInsertionSort(halvesSmall, halvesLarge);
        double halvesShellB = benchmarkShellsort(halvesSmall, halvesLarge);

        // Half-Random
        double randomInsertionB = benchmarkInsertionSort(randomSmall, randomLarge);
        double randomShellB = benchmarkShellsort(randomSmall, randomLarge);

        // Step 3: Print the results
        System.out.println("          Insertion          Shellsort");
        System.out.printf("Bin       %.3f                 %.3f\n", binaryInsertionB, binaryShellB);
        System.out.printf("Half      %.3f                 %.3f\n", halvesInsertionB, halvesShellB);
        System.out.printf("RanInt    %.3f                %.3f\n", randomInsertionB, randomShellB);
    }


    public static void main(String[] args) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 4096;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.

        me.runBenchmarks(size);
    }
}