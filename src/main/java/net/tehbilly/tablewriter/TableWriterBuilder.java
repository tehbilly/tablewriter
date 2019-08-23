package net.tehbilly.tablewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableWriterBuilder<T> {

  boolean compact = false;
  boolean showHeader = true;
  Style style = new StyleBuilder().build();
  final List<Column<T>> columns = new ArrayList<>();

  /**
   * A compact table does not include a separator between data rows.
   */
  public TableWriterBuilder<T> compact() {
    this.compact = true;
    return this;
  }

  public TableWriterBuilder<T> compact(boolean compact) {
    this.compact = compact;
    return this;
  }

  public TableWriterBuilder<T> header(boolean showHeader) {
    this.showHeader = showHeader;
    return this;
  }

  public TableWriterBuilder<T> noHeader() {
    this.showHeader = false;
    return this;
  }

  public TableWriterBuilder<T> style(Style style) {
    this.style = style;
    return this;
  }

  public TableWriterBuilder<T> style(StyleBuilder builder) {
    this.style = builder.build();
    return this;
  }

  public TableWriterBuilder<T> column(Column<T> column) {
    this.columns.add(Objects.requireNonNull(column));
    return this;
  }

  public TableWriterBuilder<T> column(ColumnBuilder<T> builder) {
    this.columns.add(Objects.requireNonNull(builder).build());
    return this;
  }

  public TableWriterBuilder<T> column(String name) {
    return columnBuilder(name).endColumn();
  }

  public TableWriterBuilder<T> columns(String... names) {
    for (String name : names) {
      column(name);
    }

    return this;
  }

  public ColumnBuilderInline<T> columnBuilder() {
    return new ColumnBuilderInline<T>(this);
  }

  public ColumnBuilderInline<T> columnBuilder(String name) {
    ColumnBuilderInline<T> builder = new ColumnBuilderInline<T>(this);
    builder.name(name);
    return builder;
  }


  public TableWriter<T> build() {
    if (columns.isEmpty()) {
      throw new IllegalStateException("Cannot create a TableWriter with no columns");
    }

    return new TableWriter<T>(this);
  }
}
