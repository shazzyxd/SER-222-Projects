package edu.ser222.m04_02;

/**
 * This program reads Kanji characters and their component relationships from text files, 
 * builds a directed graph representing those dependencies, and then performs a topological
 * sort to display the Kanji in both their original and sorted order.
 *
 * Completion time: 3.75 hrs
 *
 * @author Shazeb Mallick, Acuna, Buckner
 * @version 1.0
 */

//Note: not all of these packages may be needed.
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

public class CompletedMain implements KanjiMain {

    //Do not add any member variables to this class.

    //TODO: implement interface methods.

    public HashMap<Integer, String> loadKanji(String filename, EditableDiGraph graph) {
        HashMap<Integer, String> kanjiMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue; // Skip comments and empty lines

                String[] parts = line.split("\t");

                try {
                    int id = Integer.parseInt(parts[0].trim()); // Ensure the ID is an integer
                    String character = parts[1];

                    kanjiMap.put(id, character);
                    graph.addVertex(id); // Add the character ID as a vertex in the graph
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid line (non-integer ID): " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kanjiMap;
    }



    public void loadDataComponents(String filename, EditableDiGraph graph) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;

                String[] parts = line.split("\t");
                int source = Integer.parseInt(parts[0]);
                int target = Integer.parseInt(parts[1]);

                graph.addEdge(source, target);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String buildOrderString(EditableDiGraph graph, TopologicalSort topSort, HashMap<Integer, String> kanjiMap) {
        StringBuilder output = new StringBuilder();
        output.append("Original:\n");
        kanjiMap.values().forEach(output::append);
        output.append("\nSorted:\n");

        for (int id : topSort.order()) {
            output.append(kanjiMap.get(id));
        }

        return output.toString();
    }


    public static void main(String[] args) {
        /***************************************************************************
         * START - CORE DRIVER LOGIC, DO NOT MODIFY                                *
         **************************************************************************/
        String FILENAME_KANJI = "data-kanji.txt";
        String FILENAME_COMPONENTS = "data-components.txt";

        KanjiMain driver = new CompletedMain();
        EditableDiGraph graph = new BetterDiGraph();

        HashMap<Integer, String> kanjiMap = driver.loadKanji(FILENAME_KANJI, graph);
        driver.loadDataComponents(FILENAME_COMPONENTS, graph);

        TopologicalSort intuitive = new IntuitiveTopological(graph);

        System.out.println(driver.buildOrderString(graph, intuitive, kanjiMap));

        /***************************************************************************
         * END - CORE DRIVER LOGIC, DO NOT MODIFY                                  *
         **************************************************************************/

        //NOTE: feel free to temporarily comment out parts of the above code while
        //you incrementally develop your program. Just make sure all of it is there
        //when you test the final version of your program.
    }
}