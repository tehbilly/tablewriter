package net.tehbilly.tablewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TableWriterTest {

  @Test
  @DisplayName("TableWriterBuilder.build() throws without columns")
  void builderThrowsWithoutColumns() {
    assertThrows(IllegalStateException.class, () -> new TableWriterBuilder<String>().build());
  }

  @Test
  @DisplayName("vararg add(T...) adds to all columns")
  void addsToAllColumns() {
    TableWriter<String> writer = new TableWriterBuilder<String>()
        .column("foo")
        .columns("bar", "baz")
        .build();

    writer.add("foo", "bar", "baz");

    for (Column<String> col : writer.getColumns()) {
      assertTrue(col.get(0).startsWith(col.getName()));
    }
  }

  @Test
  @DisplayName("Adding one value adds that value to all columns")
  void addOneAddsToAll() {
    TableWriter<String> writer = new TableWriterBuilder<String>()
        .column("bar")
        .columns("baz", "qux")
        .build();

    writer.add("foo");

    for (Column<String> col : writer.getColumns()) {
      assertFalse(col.get(0).startsWith(col.getName()));
      assertTrue(col.get(0).startsWith("foo"));
    }
  }

  @Test
  @DisplayName("Calling add with more than one but not the number of columns throws")
  void varargSize() {
    TableWriter<String> writer = new TableWriterBuilder<String>()
        .columns("foo", "bar", "baz")
        .build();

    assertThrows(IllegalArgumentException.class, () -> writer.add("one", "two"));
  }

  @Test
  @DisplayName("Simple table renders")
  void simpleTable() {
    String expected =
        "┌────────────┬────────────┐\n"
      + "│ foo        │ bar        │\n"
      + "╞════════════╪════════════╡\n"
      + "│ foo:1      │ bar:1      │\n"
      + "├────────────┼────────────┤\n"
      + "│ foo:2      │ bar:2      │\n"
      + "└────────────┴────────────┘\n";

    TableWriter<String> writer = new TableWriterBuilder<String>()
        .columns("foo", "bar")
        .build();

    writer.add("foo:1", "bar:1");
    writer.add("foo:2", "bar:2");

    // Output from printTo
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos, true)) {
      writer.printTo(ps);
    }
    String printedOutput = new String(baos.toByteArray());
    assertEquals(expected, printedOutput);

    // asString should match both
    assertEquals(expected, writer.asString());
  }
}