package net.tehbilly.tablewriter;

import java.util.function.Function;

/**
 * Extends {@link ColumnBuilder} with the ability to be used inline from a {@link TableWriterBuilder}.
 */
public class ColumnBuilderInline<T> extends ColumnBuilderImpl<T> {
  private final TableWriterBuilder<T> tableWriterBuilder;

  ColumnBuilderInline(TableWriterBuilder<T> tableWriterBuilder) {
    this.tableWriterBuilder = tableWriterBuilder;
  }

  @Override
  public ColumnBuilderInline<T> name(String name) {
    super.name(name);
    return this;
  }

  @Override
  public ColumnBuilderInline<T> width(int width) {
    super.width(width);
    return this;
  }

  @Override
  public ColumnBuilderInline<T> maxWidth(int maxWidth) {
    super.maxWidth(maxWidth);
    return this;
  }

  @Override
  public ColumnBuilderInline<T> formatter(Function<T, String> formatter) {
    super.formatter(formatter);
    return this;
  }

  /**
   * Builds this column and adds it to the calling {@code TableWriterBuilder}.
   *
   * @return the {@code TableWriterBuilder} used to create this instance
   */
  public TableWriterBuilder<T> endColumn() {
    return tableWriterBuilder.column(build());
  }
}
