package org.nand2tetris;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AssemblyParser {
    private final List<String> lines;
    private final String pathToAssemblyFile;
    private Scanner scanner;

    public AssemblyParser(String pathToAssemblyFile) {
        this.lines = new ArrayList<>();
        this.pathToAssemblyFile = pathToAssemblyFile;
    }

    private Scanner buildScanner() {
        return new Scanner(String.join("\n", this.lines));
    }

    public void firstPass(SymbolTable symbolTable) {
        File file = new File(this.pathToAssemblyFile);

        try {
            Scanner scanner = new Scanner(file);

            // ignores comments, "//", multi-line comments, and whitespace, empty lines
            while (scanner.hasNextLine()) {
                // ignore content after "//" on a single line
                // ignore multi-line comments, content between "/*" and "*/"
                // ignore whitespace and empty lines
                String line = scanner.nextLine().trim();
                if (line.startsWith("//") || line.isEmpty()) {
                    continue;
                }
                // handle multi-line comments
                if (line.contains("/*")) {
                    // put content before "/*" in the list
                    this.lines.add(line.substring(0, line.indexOf("/*")));
                    while (!line.contains("*/")) {
                        line = scanner.nextLine().trim();
                    }
                    continue;
                }
                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//")).trim();
                }
                this.lines.add(line);
            }

            // build the symbol table (label -> line number)
            int currentLine = 0;
            for (int i = 0; i < this.lines.size(); i++) {
                String line = this.lines.get(i);
                if (InstructionParser.isLabel(line)) {
                    String label = line.substring(1, line.length() - 1);
                    symbolTable.addEntry(label, currentLine, false);
                    this.lines.remove(i);
                    i--;
                } else currentLine++;
            }

            // build the symbol table (variable -> address)
            for (String line : this.lines) {
                if (InstructionParser.isVariable(line)) {
                    String variable = InstructionParser.getVariableName(line);
                    if (!symbolTable.contains(variable)) {
                        symbolTable.addEntry(variable, symbolTable.getVariableAddress());
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while opening the assembly file.");
            e.printStackTrace();
        }

        this.scanner = this.buildScanner();
    }

    public String getNextInstruction() {
        return this.scanner.nextLine().trim();
    }

    public boolean hasMoreInstructions() {
        return this.scanner.hasNextLine();
    }
}
