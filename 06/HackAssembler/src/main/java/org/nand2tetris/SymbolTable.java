package org.nand2tetris;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SymbolTable {
    private final Map<String, SymbolTableEntry> table;

    public SymbolTable() {
        this.table = new HashMap<>();

        // add strings R0-R15, SP, LCL, ARG, THIS, THAT, SCREEN, KBD, and all the labels in the table
        for (int i = 0; i <= 15; i++) {
            this.addEntry("R" + i, i);
        }
        this.addEntry("SP", 0);
        this.addEntry("LCL", 1);
        this.addEntry("ARG", 2);
        this.addEntry("THIS", 3);
        this.addEntry("THAT", 4);
        this.addEntry("SCREEN", 16384);
        this.addEntry("KBD", 24576);
    }

    public void addEntry(String symbol, int address, boolean isVariable) {
        this.table.put(symbol, new SymbolTableEntry(address, isVariable));
    }

    public void addEntry(String symbol, int address) {
        this.addEntry(symbol, address, true);
    }

    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    public int getAddress(String symbol) {
        return table.get(symbol).address;
    }

    public int getVariableAddress() {
        // Find the next available address for a variable, starting from 16
        int address = 16;
        while (table.containsValue(new SymbolTableEntry(address, true))) {
            address++;
        }
        return address;
    }

    private record SymbolTableEntry(Integer address, Boolean isVariable) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SymbolTableEntry that = (SymbolTableEntry) o;
            return Objects.equals(address, that.address) && Objects.equals(isVariable, that.isVariable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(address, isVariable);
        }
    }
}
