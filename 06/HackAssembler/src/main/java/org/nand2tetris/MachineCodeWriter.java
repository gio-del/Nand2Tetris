package org.nand2tetris;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MachineCodeWriter {

    private PrintWriter printWriter;

    public MachineCodeWriter(String pathToOutputFile) {
        // create a new file at the path specified
        // if the file already exists, overwrite it
        // if the file does not exist, create a new file
        // if an error occurs, print an error message

        try {
            FileWriter fileWriter = new FileWriter(pathToOutputFile);
            this.printWriter = new PrintWriter(fileWriter);

        } catch (IOException e) {
            System.out.println("An error occurred while creating the output file.");
            e.printStackTrace();
        }
    }

    public void write(String nextInstruction) {
        try {
            this.printWriter.println(nextInstruction);
        } catch (Exception e) {
            System.out.println("An error occurred while writing to the output file.");
            e.printStackTrace();
        }
    }

    public void close() {
        this.printWriter.close();
    }
}
