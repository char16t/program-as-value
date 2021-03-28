package com.manenkov.sandbox.programasvalue;

import static com.manenkov.sandbox.programasvalue.Syntax.*;

public class Application<A> {

    private final Console<A> program;
    private final Interpreter interpreter;

    public Application(final Console<A> program, final Interpreter interpreter) {
        this.interpreter = interpreter;
        this.program = program;
    }

    public static void main(final String[] args) {
        new Application<>(program() , Interpreter.of(System.in, System.out, System.err)).exec();
    }

    protected static Console<String> program() {
        return printLine("Hello! What's your name?").flatMap(v ->
                readLine().flatMap(name ->
                        printLine("Hello, " + name).flatMap(v1 ->
                                succeed(() -> name))));
    }

    void exec() {
        interpreter.interpret(program);
    }
}
