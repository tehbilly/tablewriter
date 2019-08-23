package net.tehbilly.tablewriter;

import java.util.Objects;

public class StyleBuilder {

  // @formatter:off
  Style.Row headSeparator = new Style.Row('╞', '╪', '╡', '═');
  Style.Row top           = new Style.Row('┌', '┬', '┐', '─');
  Style.Row separator     = new Style.Row('├', '┼', '┤', '─');
  Style.Row bottom        = new Style.Row('└', '┴', '┘', '─');
  Style.Row data          = new Style.Row('│', '│', '│', ' ');
  // @formatter:on

  public StyleBuilder headSeparator(char left, char join, char right, char fill) {
    this.headSeparator = new Style.Row(left, join, right, fill);
    return this;
  }

  public StyleBuilder headSeparator(Style.Row headSeparator) {
    this.headSeparator = Objects.requireNonNull(headSeparator);
    return this;
  }

  public StyleBuilder top(char left, char join, char right, char fill) {
    this.top = new Style.Row(left, join, right, fill);
    return this;
  }

  public StyleBuilder top(Style.Row top) {
    this.top = Objects.requireNonNull(top);
    return this;
  }

  public StyleBuilder separator(char left, char join, char right, char fill) {
    this.separator = new Style.Row(left, join, right, fill);
    return this;
  }

  public StyleBuilder separator(Style.Row separator) {
    this.separator = Objects.requireNonNull(separator);
    return this;
  }

  public StyleBuilder bottom(char left, char join, char right, char fill) {
    this.bottom = new Style.Row(left, join, right, fill);
    return this;
  }

  public StyleBuilder bottom(Style.Row bottom) {
    this.bottom = Objects.requireNonNull(bottom);
    return this;
  }

  public StyleBuilder data(char left, char join, char right, char fill) {
    this.data = new Style.Row(left, join, right, fill);
    return this;
  }

  public StyleBuilder data(Style.Row data) {
    this.data = Objects.requireNonNull(data);
    return this;
  }

  public Style build() {
    return new Style(this);
  }
}
