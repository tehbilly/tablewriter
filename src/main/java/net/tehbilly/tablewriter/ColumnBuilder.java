package net.tehbilly.tablewriter;

import java.util.function.Function;

/**
 * Allows construction of a {@link Column} instance using a fluent builder pattern.
 */
public interface ColumnBuilder<T> {

  /**
   * The name of the column will be used in the header of the table.
   *
   * @param name The name of the column.
   * @return this {@code ColumnBuilder} instance (for chaining)
   */
  ColumnBuilder<T> name(String name);

  /**
   * The initial/minimum width of the column. The column's size will be no shorter than {@code width}.
   *
   * @param width the initial/minimum width of the column
   * @return this {@code ColumnBuilder} instance (for chaining)
   */
  ColumnBuilder<T> width(int width);

  /**
   * The maximum width <em>(in characters)</em> of the column. Values larger than this will be truncated.
   *
   * @param maxWidth the maximum width of the column
   * @return this {@code ColumnBuilder} instance (for chaining)
   */
  ColumnBuilder<T> maxWidth(int maxWidth);

  /**
   * A formatter is a {@link Function} that is responsible for taking values of type {@code T} and turning them into a
   * {@code String} representation.
   *
   * @param formatter the {@code Function} responsible for converting added values to {@code String}
   * @return this {@code ColumnBuilder} instance (for chaining)
   */
  ColumnBuilder<T> formatter(Function<T, String> formatter);

  /**
   * Align {@code String} from {@link #formatter(Function)} when printing the cell.
   *
   * @return this {@code ColumnBuilder} instance (for chaining)
   */
  ColumnBuilder<T> align(Alignment alignment);

  /**
   * Create a new {@code Column} instance.
   *
   * @return the created {@code Column}
   */
  Column<T> build();
}
