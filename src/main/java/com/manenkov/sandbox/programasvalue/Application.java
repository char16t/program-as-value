package com.manenkov.sandbox.programasvalue;

import static com.manenkov.sandbox.programasvalue.Syntax.*;

public class Application extends Interpreter {
    public static void main(String[] args) {
        new Application().exec();
    }

    void exec() {
        final Console<Void> example1 =
                PrintLine.of("Hello! What's your name?",
                        ReadLine.of(name ->
                                PrintLine.of("Good to meet you, " + name, Return.of(() -> null))));

        final Console<String> example2 =
                printLine("Hello! What's your name?").flatMap(v ->
                        readLine().flatMap(name ->
                                printLine("Hello, " + name).flatMap(v1 ->
                                        succeed(() -> name))));

        interpret(example2);
    }
}
