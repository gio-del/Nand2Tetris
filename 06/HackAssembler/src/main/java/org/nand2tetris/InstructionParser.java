package org.nand2tetris;

public class InstructionParser {
    public static boolean isLabel(String instruction) {
        instruction = instruction.trim();
        return instruction.startsWith("(") && instruction.endsWith(")");
    }

    public static boolean isVariable(String instruction) {
        return instruction.startsWith("@") && !instruction.matches("@\\d+");
    }

    public static String getVariableName(String instruction) {
        return isVariable(instruction) ? instruction.substring(1) : ""; // remove the leading "@"
    }

    public static boolean isAInstruction(String instruction) {
        return instruction.startsWith("@");
    }

    public static boolean isCInstruction(String instruction) {
        return !isAInstruction(instruction) && !isLabel(instruction);
    }

    public static String getDest(String instruction) {
        // The part before "=" is the destination
        if (instruction.contains("=")) {
            return instruction.split("=")[0].trim();
        }
        return "";
    }

    public static String getComp(String instruction) {
        // The part after "=" and before ";" is the computation
        String comp = instruction;
        if (instruction.contains("=")) {
            comp = instruction.split("=")[1];
        }
        if (comp.contains(";")) {
            comp = comp.split(";")[0];
        }
        return comp.trim();
    }

    public static String getJump(String instruction) {
        // The part after ";" is the jump
        if (instruction.contains(";")) {
            return instruction.split(";")[1].trim();
        }
        return "";
    }
}
