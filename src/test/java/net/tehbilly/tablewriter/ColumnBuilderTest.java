package net.tehbilly.tablewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnBuilderTest {
  @Test
  @DisplayName("ColumnBuilderImpl formatting")
  void implFormatting() {
    ColumnBuilder<String> builder = Column.builder("name");
    // We want to make sure we have the right type
    assertTrue(builder instanceof ColumnBuilderImpl);

    // Create column from builder with required fields set
    checkFormatting(builder.build());
  }

  @Test
  @DisplayName("ColumnBuilderInline formatting")
  void inlineFormatting() {
    TableWriterBuilder<String> twb = new TableWriterBuilder<>();
    ColumnBuilderInline<String> builder = twb.columnBuilder("name")
        .width(10)
        .maxWidth(50);

    // test that column formatting works propertly
    checkFormatting(builder.build());
  }

  void checkFormatting(Column<String> col) {
    assertEquals("name", col.getName());
    // Ensure display name is formatted properly
    assertEquals(String.format("%-" + col.width + "s", "name"), col.getDisplayName());
    // Assert defaults are set
    assertEquals(10, col.width);
    assertEquals(50, col.getMaxWidth());

    // Ensure that a value we put in is given back formatted
    col.add("foo");
    assertEquals(String.format("%-" + col.width + "s", "foo"), col.get(0));
  }

  @Test
  @DisplayName("ColumnBuilderImpl.build() throws on missing values")
  void builderFailsThrows() {
    assertThrows(IllegalStateException.class, () -> Column.builder().build());
    assertThrows(IllegalStateException.class, () -> Column.builder()
        .name(null)
        .build());
    assertThrows(IllegalStateException.class, () -> Column.builder()
        .formatter(null)
        .build());
  }

  @Test
  @DisplayName("ColumnBuilderInline.build() throws on missing values")
  void inlineBuilderThrows() {
    TableWriterBuilder<String> twb = new TableWriterBuilder<>();
    ColumnBuilderInline<String> builder = twb.columnBuilder();
    assertThrows(IllegalStateException.class, builder::build);
    assertThrows(IllegalStateException.class, () -> builder.name(null).build());
    assertThrows(IllegalStateException.class, () -> builder.formatter(null).build());
  }

  @Test
  @DisplayName("ColumnBuilderInline.endColumn() returns TableWriterBuilder")
  void inlineReturnsTableWriterBuilder() {
    TableWriterBuilder<String> twb = new TableWriterBuilder<>();
    assertEquals(twb, twb.columnBuilder("name").endColumn());
    assertEquals(twb, twb.column("name"));
    assertEquals(twb, twb.columns("foo", "bar"));
  }
}
