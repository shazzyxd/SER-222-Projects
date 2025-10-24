package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class IntuitiveTopological implements TopologicalSort {
    private final EditableDiGraph graph;
    private final LinkedList<Integer> topoOrder;

    public IntuitiveTopological(EditableDiGraph graph) {
        this.graph = graph;
        this.topoOrder = new LinkedList<>();

        if (isDAG()) {
            computeTopologicalSort();
        }
    }

    private void computeTopologicalSort() {
        LinkedList<Integer> zeroInDegree = new LinkedList<>();
        HashMap<Integer, Integer> tempInDegree = new HashMap<>();

        for (int v : graph.vertices()) {
            tempInDegree.put(v, graph.getIndegree(v));
            if (graph.getIndegree(v) == 0) {
                zeroInDegree.add(v);
            }
        }

        while (!zeroInDegree.isEmpty()) {
            int v = zeroInDegree.poll();
            topoOrder.add(v);

            for (int neighbor : graph.getAdj(v)) {
                tempInDegree.put(neighbor, tempInDegree.get(neighbor) - 1);
                if (tempInDegree.get(neighbor) == 0) {
                    zeroInDegree.add(neighbor);
                }
            }
        }

        if (topoOrder.size() != graph.getVertexCount()) {
            topoOrder.clear(); // Cycle detected, clear the order.
        }
    }

    @Override
    public Iterable<Integer> order() {
        return topoOrder.isEmpty() ? null : topoOrder;
    }

    @Override
    public boolean isDAG() {
        // Detect cycles using a simple DFS-based check.
        LinkedList<Integer> visited = new LinkedList<>();
        LinkedList<Integer> recursionStack = new LinkedList<>();

        for (int v : graph.vertices()) {
            if (dfsCycleDetection(v, visited, recursionStack)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfsCycleDetection(int v, LinkedList<Integer> visited, LinkedList<Integer> recursionStack) {
        if (recursionStack.contains(v)) {
            return true; // Cycle detected.
        }
        if (visited.contains(v)) {
            return false;
        }

        visited.add(v);
        recursionStack.add(v);

        for (int neighbor : graph.getAdj(v)) {
            if (dfsCycleDetection(neighbor, visited, recursionStack)) {
                return true;
            }
        }

        recursionStack.remove(Integer.valueOf(v));
        return false;
    }
}
