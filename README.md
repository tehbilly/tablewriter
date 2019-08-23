TableWriter
===========

TableWriter is a JVM library for printing/building simple tables.

Simple Example
--------------

Basic usage is straightforward, where you build a `TableWriter` using a builder,
then add rows with `TableWriter#add(T...)`.

```java
// A TableWriter<String> with two columns
TableWriter<String> writer = new TableWriterBuilder<String>()
    .columns("foo", "bar")
    .build();

// Add some data
for (int i = 1; i <= 5; i++) {
  // vararg call with number of arguments matching number of columns
  writer.add("foo:" + i, "bar:" + i);
  // The following would throw IllegalArgumentException!
  writer.add("foo:" + i, "bar:" + i, "baz:" + i);
}

// Print to a PrintStream
writer.printTo(System.out);
// Or build to a string
String table = writer.asString();
```

Typed Example
-------------

You can also create instances that handle a certain type as an argument to
`add`. Given the following class:

```java
class FooClass {
  private final String foo;
  private final String bar;
  private final int number;

  private FooClass(String foo, String bar, int number) {
    this.foo = foo;
    this.bar = bar;
    this.number = number;
  }

  // getters here
}
```

You can create a table that uses a `Function<T, String>` to provide column data.

```java
new TableWriterBuilder<FooClass>()
    // Column named 'Foo' with a default width of 5 characters and a method
    // reference as a formatter 
    .columnBuilder("Foo")
      .width(5)
      // If formatter isn't specified it will be the default of String::valueOf
      .formatter(FooClass::getFoo)
      .endColumn()
    // Bar is the same but with default width
    .columnBuilder("Bar")
      .formatter(FooClass::getBar)
      .endColumn()
    // Number has a max width and using a lambda for the formatter
    .columnBuilder("Number")
      .maxWidth(10)
      .formatter(n -> String.format("number=%d", n.getNumber()))
      .endColumn()
    .build()
    // Now add data. The formatter for each column is applied to get the cell
    // value for that row.
    .add(new FooClass("foo", "bar", 1))
    .add(new FooClass("baz", "qux", 999))
    // And print the result
    .printTo(System.out);
```

The result:

```
┌───────┬────────────┬────────────┐
│ Foo   │ Bar        │ Number     │
╞═══════╪════════════╪════════════╡
│ foo   │ bar        │ number=1   │
├───────┼────────────┼────────────┤
│ baz   │ qux        │ number=990 │
└───────┴────────────┴────────────┘
```

Roadmap
-------

- [ ] Publish to a maven repository
- [ ] Ensure the API is usable/friendly enough
- [ ] Flesh out and publish javadoc
 