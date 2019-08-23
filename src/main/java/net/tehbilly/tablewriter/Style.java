package net.tehbilly.tablewriter;

import java.util.Objects;
import java.util.StringJoiner;

public class Style {

  final Row headSeparator;
  final Row top;
  final Row separator;
  final Row bottom;
  final Row data;

  Style(StyleBuilder builder) {
    this.headSeparator = builder.headSeparator;
    this.top = builder.top;
    this.separator = builder.separator;
    this.bottom = builder.bottom;
    this.data = builder.data;
  }

  public Row getHeadSeparator() {
    return headSeparator;
  }

  public Row getTop() {
    return top;
  }

  public Row getSeparator() {
    return separator;
  }

  public Row getBottom() {
    return bottom;
  }

  public Row getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Style other = (Style) o;
    return headSeparator.equals(other.headSeparator) &&
        top.equals(other.top) &&
        separator.equals(other.separator) &&
        bottom.equals(other.bottom) &&
        data.equals(other.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(headSeparator, top, separator, bottom, data);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Style.class.getSimpleName() + "[", "]")
        .add("headSeparator=" + headSeparator)
        .add("top=" + top)
        .add("separator=" + separator)
        .add("bottom=" + bottom)
        .add("data=" + data)
        .toString();
  }

  public static class Row {

    final char left;
    final char join;
    final char right;
    final char fill;

    public Row(char left, char join, char right, char fill) {
      this.left = left;
      this.join = join;
      this.right = right;
      this.fill = fill;
    }

    public char getLeft() {
      return left;
    }

    public char getJoin() {
      return join;
    }

    public char getRight() {
      return right;
    }

    public char getFill() {
      return fill;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Row other = (Row) o;
      return left == other.left &&
          join == other.join &&
          right == other.right &&
          fill == other.fill;
    }

    @Override
    public int hashCode() {
      return Objects.hash(left, join, right, fill);
    }

    @Override
    public String toString() {
      return new StringJoiner(", ", Row.class.getSimpleName() + "[", "]")
          .add("left=" + left)
          .add("join=" + join)
          .add("right=" + right)
          .add("fill=" + fill)
          .toString();
    }
  }
}
