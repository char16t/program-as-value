![bage](https://github.com/char16t/program-as-value/actions/workflows/maven.yml/badge.svg) [![codecov](https://codecov.io/gh/char16t/program-as-value/branch/master/graph/badge.svg?token=QDTUYRIHOM)](https://codecov.io/gh/char16t/program-as-value)

It is a simple program that welcomes the user. Literally:

```
Hello! What's your name?
Valeriy
Good to meet you, Valeriy
```

This program is completely:

* Total - Functions always return an output for every input
* Deterministic — Functions return the same output for the same input
* Pure — The only effect of providing a function an input is computing the output

Test:

```bash
mvn clean test
```

Build:

```bash
mvn clean package
```

## Get to know programs as values

We can build a data structure to describe a console program with just three instructions:

```java
abstract class Console<A> {
}

final class Return<A> extends Console<A> {
    public final Supplier<A> value;
    /* ... */
}

final class PrintLine<A> extends Console<A> {
    public final String line;
    public final Console<A> rest;
    /* ... */
}

final class ReadLine<A> extends Console<A> {
    public final Function<String, Console<A>> rest;
    /* ... */
}
```

In this model, `Console<A>` is an immutable, type-safe value, which represents a console program that returns a value of
type `A`.

The `Console` data structure is an ordered tree, and at the very "end" of the program, you will find a `Return`
instruction that stores a value of type `A`, which is the return value of the `Console<A>` program.

Although very simple, this data structure is enough to build an interactive program:

```java
final Console<Void> example1 =
        PrintLine.of("Hello! What's your name?",
                ReadLine.of(name ->
                        PrintLine.of("Good to meet you, " + name, Return.of(() -> null))));
```

This immutable value doesn't do anything—it just describes a program that prints out a message, asks for input, and
prints out another message that depends on the input.

Although this program is just a model, we can translate the model into procedural effects quite simply using an
interpreter, which recurses on the data structure, translating every instruction into the side-effect that it describes:

```java
<A> A interpret(final Console<A> program) {
    if (program instanceof Return) {
        return ((Return<A>) program).value.get();
    } else if (program instanceof PrintLine) {
        final String line = ((PrintLine<A>) program).line;
        final Console<A> next = ((PrintLine<A>) program).rest;
        System.out.println(line);
        return interpret(next);
    } else if (program instanceof ReadLine) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final Function<String, Console<A>> next = ((ReadLine<A>) program).rest;
        try {
            return interpret(next.apply(reader.readLine()));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
    return null;
}
```

Interpreting (also called running or executing) is not functional, because it may be partial, non-deterministic, and
impure. In an ideal application, however, interpretation only needs to happen once: in the application's main function.
The rest of the application can be purely functional.

In practice, it's not very convenient to build console programs using constructors directly. Instead, we can define
helper functions, which look more like their effectful equivalents:

```java
abstract class Syntax {
    protected Syntax() {
    }

    static <T> Console<T> succeed(final Supplier<T> a) {
        return Return.of(a);
    }

    static Console<Void> printLine(final String line) {
        return PrintLine.of(line, succeed(() -> null));
    }

    static Console<String> readLine() {
        return ReadLine.of(line -> succeed(() -> line));
    }
}
```

Composing these "leaf" instructions into larger programs becomes a lot easier if we define `map` and `flatMap` methods
on `Console`:

* The `map` method lets you transform a console program that returns an `A` into a console program that returns a `B`,
  by supplying a function `A => B`.
* The `flatMap` method lets you sequentially compose a console program that returns an `A` with a callback that returns
  another console program created from the `A`.

These two methods are defined as follows:

```java
<B> Console<B> map(final Function<A, B> f) {
    return flatMap(a -> succeed(() -> f.apply(a)));
}

<B> Console<B> flatMap(final Function<A, Console<B>> f) {
    if (this instanceof Return) {
        final Supplier<A> value = ((Return<A>) this).value;
        return f.apply(value.get());
    } else if (this instanceof PrintLine) {
        final String line = ((PrintLine<A>) this).line;
        final Console<A> next = ((PrintLine<A>) this).rest;
        return PrintLine.of(line, next.flatMap(f));
    } else if (this instanceof ReadLine) {
        final Function<String, Console<A>> next = ((ReadLine<A>) this).rest;
        return ReadLine.of(line -> next.apply(line).flatMap(f));
    }
    return null;
}
```

With these `map` and `flatMap` methods, we can write programs that look like this:

```java
final Console<String> example2 =
        printLine("Hello! What's your name?").flatMap(v ->
                readLine().flatMap(name ->
                        printLine("Hello, " + name).flatMap(v1 ->
                                succeed(() -> name))));
```

When we wish to execute this program, we can call `interpret` on the `Console` value.

All functional programs are constructed like this: instead of interacting with the real world, they build a functional
effect, which is nothing more than an immutable, type-safe, tree-like data structure that models procedural effects.

## Ugly?

Use Scala :)

## License

Source code licensed under Public Domain. See UNLICENSE file for details
