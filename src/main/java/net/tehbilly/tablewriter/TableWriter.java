package net.tehbilly.tablewriter;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import net.tehbilly.tablewriter.Style.Row;

/**
 * A class to create a unicode table out of rows of {@code String...} or of objects with formatters.
 *
 * @param <T> The type of item represented by each row. Can be {@code String} and used with varargs to {@link
 *            TableWriter#add(Object[])}
 */
public class TableWriter<T> {

  private boolean showHeader;
  private boolean compact;
  private final Style style;
  private final List<Column<T>> columns;

  TableWriter(TableWriterBuilder<T> builder) {
    this.showHeader = builder.showHeader;
    this.compact = builder.compact;
    this.style = builder.style;
    this.columns = builder.columns;
  }

  List<Column<T>> getColumns() {
    return columns;
  }

  /**
   * Call data to be added to cells. Can be called with a single object and cells with formatters.
   *
   * @see Column
   */
  @SafeVarargs
  public final TableWriter<T> add(T... data) {
    if (data.length != 1 && data.length != columns.size()) {
      String msg = String.format("Expected %d arguments, but got %d", columns.size(), data.length);
      throw new IllegalArgumentException(msg);
    }

    if (data.length == 1) {
      columns.forEach(c -> c.add(data[0]));
      return this;
    }

    for (int i = 0; i < data.length; i++) {
      Column<T> column = columns.get(i);
      column.add(data[i]);
    }

    return this;
  }

  /**
   * Print the table to a PrintStream such as {@code System.out}.
   */
  public void printTo(PrintStream stream) {
    // Print the top row
    stream.println(fillerRow(style.top));

    // Show the header and separator if requested
    if (showHeader) {
      List<String> columnNames = columns.stream()
          .map(Column::getDisplayName)
          .collect(Collectors.toList());
      stream.println(row(style.data, columnNames));

      stream.println(fillerRow(style.headSeparator));
    }

    // Save the separator
    char[] separator = fillerRow(style.separator);

    for (int i = 0; i < columns.get(0).data.size(); i++) {
      // Print separator if not compact and not the first row
      if (!compact && i > 0) {
        stream.println(separator);
      }

      // Collect cells from columns
      int row = i;
      List<String> cells = columns.stream().map(c -> c.get(row)).collect(Collectors.toList());
      stream.println(row(style.data, cells));
    }

    // Print the bottom row
    stream.println(fillerRow(style.bottom));
  }

  /**
   * Return the table as a string.
   */
  public String asString() {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (PrintStream ps = new PrintStream(baos, true)) {
      printTo(ps);
    }

    return new String(baos.toByteArray());
  }

  private String repeatChar(char c, int times) {
    char[] chars = new char[times];

    for (int i = 0; i < times; i++) {
      chars[i] = c;
    }

    return new String(chars);
  }

  private char[] row(Row row, List<String> cols) {
    CharArrayWriter writer = new CharArrayWriter();
    // Top-left
    writer.append(row.left);

    for (int i = 0; i < cols.size(); i++) {
      // Join char if not the first col
      if (i > 0) {
        writer.append(row.join);
      }
      // Fill with style.fill to fit width
      writer.append(row.fill).append(cols.get(i)).append(row.fill);
    }

    writer.append(row.right);

    return writer.toCharArray();
  }

  private char[] fillerRow(Row rowStyle) {
    return row(rowStyle, columns.stream()
        .map(c -> repeatChar(rowStyle.fill, c.width))
        .collect(Collectors.toList()));
  }
}
