package net.tehbilly.tablewriter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ColumnBuilderImpl<T> implements ColumnBuilder<T> {
  String name = null;
  int width = 10;
  int maxWidth = 50;
  Function<T, String> formatter = String::valueOf;

  @Override
  public ColumnBuilderImpl<T> name(String name) {
    this.name = name;
    return this;
  }

  @Override
  public ColumnBuilderImpl<T> width(int width) {
    this.width = width;
    return this;
  }

  @Override
  public ColumnBuilderImpl<T> maxWidth(int maxWidth) {
    this.maxWidth = maxWidth;
    return this;
  }

  @Override
  public ColumnBuilderImpl<T> formatter(Function<T, String> formatter) {
    this.formatter = formatter;
    return this;
  }

  @Override
  public Column<T> build() {
    List<String> invalidFields = new ArrayList<>();

    if (name == null || name.isEmpty()) {
      invalidFields.add("name");
    }

    if (formatter == null) {
      invalidFields.add("formatter");
    }

    if (!invalidFields.isEmpty()) {
      throw new IllegalStateException("Cannot build Column instance, invalid fields: " + invalidFields);
    }

    return new Column<T>(this);
  }

}
