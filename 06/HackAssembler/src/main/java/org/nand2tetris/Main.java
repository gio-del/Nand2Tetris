package org.nand2tetris;

public class Main {
    public static void main(String[] args) {
        // Application take 2 arguments, the path to the assembly file and the path to the output file (optional)
        if (args.length < 1) {
            System.out.println("Usage: java -jar Assembler.jar <path-to-assembly-file> [path-to-output-file]");
            System.exit(1);
        }
        String pathOutputFile = args.length == 2 ? args[1] : args[0].replace(".asm", ".hack");
        String pathAssemblyFile = args[0];
        Assembler assembler = new Assembler(pathAssemblyFile, pathOutputFile);
        assembler.assemble();
    }
}