package net.tehbilly.tablewriter;

/**
 * A silly sample demonstration class used in README.md
 */
public class TableWriterDemo {

  public static void main(String[] args) {
    example1();
    example2();
  }

  private static class FooClass {
    private final String foo;
    private final String bar;
    private final int number;

    private FooClass(String foo, String bar, int number) {
      this.foo = foo;
      this.bar = bar;
      this.number = number;
    }

    // getters here
    public String getFoo() {
      return foo;
    }

    public String getBar() {
      return bar;
    }

    public int getNumber() {
      return number;
    }
  }

  private static void example1() {
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
  }

  private static void example2() {
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
  }
}
