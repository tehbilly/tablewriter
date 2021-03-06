package net.tehbilly.tablewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {
  @Test
  @DisplayName("Value formatting")
  void valuesFormatted() {
    Column<String> col = Column.<String>builder()
        .name("name")
        .width(2)
        .maxWidth(5)
        .formatter(String::toUpperCase)
        .build();

    // Make sure name length trumps width parameter
    assertEquals(4, col.width);
    assertEquals("name", col.getDisplayName());
    // Add a short value and make sure width hasn't changed, but format has
    col.add("foo");
    assertEquals(4, col.width);
    assertEquals("FOO ", col.get(0));
    // Add a long value and make sure width has changed
    col.add("foobarbaz");
    assertEquals("name ", col.getDisplayName());
    assertEquals("FOOBA", col.get(1));
  }

  @Test
  @DisplayName("Alignment: RIGHT")
  void alignRight() {
    Column<String> col = Column.<String>builder()
        .name("name")
        .width(10)
        .maxWidth(10)
        .formatter(String::toUpperCase)
        .align(Alignment.RIGHT)
        .build();

    col.add("foo");
    assertEquals("       FOO", col.get(0));
  }

  @Test
  @DisplayName("Alignment: CENTER (odd)")
  void alignCenterOdd() {
    Column<String> col = Column.<String>builder()
        .name("name")
        .width(9)
        .maxWidth(9)
        .formatter(String::toUpperCase)
        .align(Alignment.CENTER)
        .build();

    col.add("foo");
    col.add("four");
    assertEquals("   FOO   ", col.get(0));
    assertEquals("   FOUR  ", col.get(1));
  }

  @Test
  @DisplayName("Alignment: CENTER (even)")
  void alignCenterEven() {
    Column<String> col = Column.<String>builder()
        .name("name")
        .width(10)
        .maxWidth(10)
        .formatter(String::toUpperCase)
        .align(Alignment.CENTER)
        .build();

    col.add("foo");
    col.add("four");

    assertEquals("    FOO   ", col.get(0));
    assertEquals("   FOUR   ", col.get(1));
  }

  @Test
  @DisplayName("Column with long name")
  void longName() {
    Column<String> col = Column.<String>builder("longlongname")
        .width(5)
        .maxWidth(10)
        .build();

    assertEquals(10, col.getMaxWidth());
    assertEquals(10, col.width);
    assertEquals("longlongna", col.getDisplayName());
    assertEquals("longlongname", col.getName());
  }
}
