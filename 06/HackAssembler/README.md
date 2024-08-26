# HackAssembler

A simple assembler for the Hack assembly language, written in Java.

## What is the Hack assembly language?

Read more about the Hack assembly language and the ``nand2tetris`` project in
general [here](https://www.nand2tetris.org/).

## How to use

Note: You need to have Maven installed on your system to build the project. Java 17 or later is also required.

1. Clone the repository
2. Run the following command in the terminal (in the root directory of the project) to build the project:

   ```bash
   mvn clean package
   ```

3. The executable JAR file will be generated in the ``target`` directory. Run the following command to assemble a Hack
   assembly file:

   ```bash
   java -jar target/HackAssembler-1.0-SNAPSHOT.jar <input_file>
   ```

The output file will be generated in the same directory as the input file, with the same name but with the extension
``.hack``. Name can be changed by providing a second argument to the assembler:

   ```bash
   java -jar target/HackAssembler-1.0-SNAPSHOT.jar <input_file> <output_file>
   ```

## Testing

The project includes a set of test files in the ``testbench`` directory with ``*.asm`` files and their corresponding (
correct)
``*_test.hack`` files. To run the tests, use the following
command:

   ```bash
   mvn test
   ```