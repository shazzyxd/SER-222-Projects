# DirectedGraph Project

This project implements a Kanji dependency graph in Java, loading kanji characters and their component relationships from text files and performing a topological sort. This project was developed as part of SER 222 coursework at Arizona State University.

## Requirements

Java 11 or higher (tested and works with Java 25)

Tested with Windows 11

**Important:** Your Command Prompt or PowerShell must support UTF-8 encoding and Japanese characters to display kanji correctly.

## Compilation

If your source files are located in src/edu/ser222/m04_02/ (or the appropriate directory for this project) and you’re compiling to an output directory named out, use the following command:

javac -d out src/edu/ser222/m04_02/*.java

## Running

Navigate to the project root, then run the main class from the out folder using its package name. For example:

Windows:

java -cp out edu.ser222.m04_02.CompletedMain

**Note:** 

Make sure the terminal supports UTF-8 and Japanese characters to correctly see kanji output.
