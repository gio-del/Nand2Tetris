package org.nand2tetris;

public class Assembler extends InstructionParser {
    private final AssemblyParser parser;
    private final SymbolTable symbolTable;
    private final MachineCodeWriter writer;

    public Assembler(String pathToAssemblyFile, String pathToOutputFile) {
        this.symbolTable = new SymbolTable();
        this.parser = new AssemblyParser(pathToAssemblyFile);
        this.writer = new MachineCodeWriter(pathToOutputFile);
    }

    public void assemble() {
        // Perform the first pass to update the symbol table
        this.parser.firstPass(symbolTable);
        while (this.parser.hasMoreInstructions()) {
            String instruction = this.parser.getNextInstruction();
            if (InstructionParser.isAInstruction(instruction)) {
                // A-instruction with a variable
                int address;
                if (InstructionParser.isVariable(instruction)) {
                    String variable = InstructionParser.getVariableName(instruction);
                    // get the address of the variable
                    address = this.symbolTable.getAddress(variable);
                } else {
                    // A-instruction with a number
                    address = Integer.parseInt(instruction.substring(1));
                }
                // write the machine code to the output file
                this.writer.write(String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0'));// + " " + instruction);
            } else if (InstructionParser.isCInstruction(instruction)) {
                // C-instruction
                String dest = Encoder.dest(InstructionParser.getDest(instruction));
                String comp = Encoder.comp(InstructionParser.getComp(instruction));
                String jump = Encoder.jump(InstructionParser.getJump(instruction));
                // write the machine code to the output file
                this.writer.write("111" + comp + dest + jump);// + " " + instruction);
            }
        }
        writer.close();
    }
}
