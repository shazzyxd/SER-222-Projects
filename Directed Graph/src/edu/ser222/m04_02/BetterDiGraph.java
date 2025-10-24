package edu.ser222.m04_02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class BetterDiGraph implements EditableDiGraph {
    private final HashMap<Integer, LinkedList<Integer>> adjList;
    private final HashMap<Integer, Integer> inDegree;
    private int edgeCount;

    public BetterDiGraph() {
        this.adjList = new HashMap<>();
        this.inDegree = new HashMap<>();
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(int v) {
        adjList.putIfAbsent(v, new LinkedList<>());
        inDegree.putIfAbsent(v, 0);
    }

    @Override
    public void addEdge(int v, int w) {
        addVertex(v);
        addVertex(w);
        if (!adjList.get(v).contains(w)) {
            adjList.get(v).add(w);
            inDegree.put(w, inDegree.get(w) + 1);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(int v, int w) {
        if (adjList.containsKey(v) && adjList.get(v).remove(Integer.valueOf(w))) {
            inDegree.put(w, inDegree.get(w) - 1);
            edgeCount--;
        }
    }

    @Override
    public void removeVertex(int v) {
        if (!adjList.containsKey(v)) return;

        for (int neighbor : adjList.get(v)) {
            inDegree.put(neighbor, inDegree.get(neighbor) - 1);
            edgeCount--;
        }
        adjList.remove(v);
        inDegree.remove(v);

        for (LinkedList<Integer> neighbors : adjList.values()) {
            if (neighbors.remove(Integer.valueOf(v))) {
                edgeCount--;
            }
        }
    }

    @Override
    public Iterable<Integer> getAdj(int v) {
        if (!adjList.containsKey(v)) {
            throw new NoSuchElementException("Vertex does not exist");
        }
        return adjList.get(v);
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getIndegree(int v) {
        if (!inDegree.containsKey(v)) {
            throw new NoSuchElementException("Vertex does not exist");
        }
        return inDegree.get(v);
    }

    @Override
    public int getVertexCount() {
        return adjList.size();
    }

    @Override
    public boolean isEmpty() {
        return adjList.isEmpty();
    }

    @Override
    public boolean containsVertex(int v) {
        return adjList.containsKey(v);
    }

    @Override
    public Iterable<Integer> vertices() {
        return adjList.keySet();
    }
}
