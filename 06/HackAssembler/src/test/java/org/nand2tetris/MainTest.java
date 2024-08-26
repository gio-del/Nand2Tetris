package org.nand2tetris;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    private static final String ASM_DIRECTORY = "testbench/";

    @Test
    public void testAssemblerMain() throws IOException {
        try (Stream<Path> asmFiles = Files.list(Paths.get(ASM_DIRECTORY))) {
            asmFiles.filter(file -> file.toString().endsWith(".asm")).forEach(this::runTestOnFile);
        }
    }

    private void runTestOnFile(Path asmFilePath) {
        String asmFileName = asmFilePath.getFileName().toString();
        String expectedBinaryFileName = asmFileName.replace(".asm", "_test.hack");

        try {
            // Prepare the path for the output file in the temporary directory
            Path outputBinaryPath = asmFilePath.resolveSibling(asmFileName.replace(".asm", ".hack"));

            // Run the main method with the .asm input and temp output paths
            Main.main(new String[]{asmFilePath.toString()});

            // Read the generated binary output file
            assertTrue(Files.exists(outputBinaryPath), "Output file was not created: " + outputBinaryPath);
            byte[] generatedBinary = Files.readAllBytes(outputBinaryPath);

            // Read the expected binary file
            Path expectedBinaryPath = Paths.get(ASM_DIRECTORY, expectedBinaryFileName);
            byte[] expectedBinary = Files.readAllBytes(expectedBinaryPath);

            // Compare the generated binary to the expected binary
            assertArrayEquals(expectedBinary, generatedBinary, "Mismatch in binary output for " + asmFileName);

            try {
                Files.deleteIfExists(outputBinaryPath);
            } catch (IOException e) {
                System.err.println("Could not delete output file: " + outputBinaryPath);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to process file: " + asmFilePath, e);
        }
    }
}
