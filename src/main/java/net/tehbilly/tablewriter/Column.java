package net.tehbilly.tablewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A column has a name, a minimum width, a maximum width, and a formatter for values added to it. The default formatter
 * is {@link String#valueOf(Object)}.
 */
public class Column<T> {

  private final String name;
  private final int maxWidth;
  int width;
  private final Function<T, String> formatter;
  private final Alignment alignment;
  List<String> data = new ArrayList<>();

  Column(ColumnBuilderImpl<T> builder) {
    this.name = builder.name;
    // Width cannot be less than the length of the column name, but cannot be more than builder.max
    this.width = Math.min(
        Math.max(builder.width, name.length()),
        builder.maxWidth
    );
    this.alignment = builder.alignment;
    this.maxWidth = builder.maxWidth;
    this.formatter = builder.formatter;
  }

  /**
   * Add an item to this column. Will have the formatter applied and any trimming if necessary.
   */
  void add(T datum) {
    String str = formatter.apply(datum);
    // Trim if too long
    if (str.length() > maxWidth) {
      str = str.substring(0, maxWidth);
    }

    // Adjust this column's width if it needs to grow
    if (str.length() > width) {
      width = str.length();
    }

    data.add(str);
  }

  public String getName() {
    return name;
  }

  /**
   * Gets the display name, trimmed to fit {@literal maxWidth} or padded to fit {@literal width}.
   */
  String getDisplayName() {
    if (name.length() > width) {
      return name.substring(0, width);
    }

    return String.format("%-" + width + "s", name);
  }

  /**
   * Gets the value for row {@literal i}, padding if necessary.
   */
  String get(int i) {
    switch (alignment) {
      case LEFT:
        return String.format("%-" + width + "s", data.get(i));
      case RIGHT:
        return String.format("%" + width + "s", data.get(i));
      case CENTER:
        String value = data.get(i);
        // if width and value length aren't the same (odd/even) prepend a space to value
        if (width % 2 != value.length() % 2) {
          value = ' ' + value;
        }

        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < (width - value.length()) / 2; j++) {
          sb.append(' ');
        }
        sb.append(value);
        for (int j = 0; j < (width - value.length()) / 2; j++) {
          sb.append(' ');
        }

        return sb.toString();
      default:
        throw new IllegalStateException("Unknown alignment: " + alignment);
    }
  }

  public int getMaxWidth() {
    return maxWidth;
  }

  public static <T> ColumnBuilder<T> builder(String name) {
    return new ColumnBuilderImpl<T>().name(name);
  }

  public static <T> ColumnBuilder<T> builder() {
    return new ColumnBuilderImpl<>();
  }
}
