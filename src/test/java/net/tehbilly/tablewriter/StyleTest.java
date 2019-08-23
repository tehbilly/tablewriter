package net.tehbilly.tablewriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StyleTest {
  @Test
  @DisplayName("Default Style from builder is safe to use")
  void defaultBuilder() {
    Style style = new StyleBuilder().build();

    assertNotNull(style.headSeparator);
    assertNotNull(style.top);
    assertNotNull(style.separator);
    assertNotNull(style.bottom);
    assertNotNull(style.data);
  }

  @Test
  @DisplayName("Using char overloads creates proper Style.Row instances")
  void charOverloads() {
    Style style = new StyleBuilder()
        .headSeparator('a', 'b', 'c', 'd')
        .top('e', 'f', 'g', 'h')
        .separator('i', 'j', 'k', 'l')
        .bottom('m', 'n', 'o', 'p')
        .data('q', 'r', 's', 't')
        .build();

    assertEquals(new Style.Row('a', 'b', 'c', 'd'), style.headSeparator);
    assertEquals(new Style.Row('e', 'f', 'g', 'h'), style.top);
    assertEquals(new Style.Row('i', 'j', 'k', 'l'), style.separator);
    assertEquals(new Style.Row('m', 'n', 'o', 'p'), style.bottom);
    assertEquals(new Style.Row('q', 'r', 's', 't'), style.data);
  }

  @Test
  @DisplayName("Using Style.Row overloads creates the same structure")
  void rowOverloads() {
    Style style = new StyleBuilder()
        .headSeparator(new Style.Row('a', 'b', 'c', 'd'))
        .top(new Style.Row('e', 'f', 'g', 'h'))
        .separator(new Style.Row('i', 'j', 'k', 'l'))
        .bottom(new Style.Row('m', 'n', 'o', 'p'))
        .data(new Style.Row('q', 'r', 's', 't'))
        .build();

    assertEquals(new Style.Row('a', 'b', 'c', 'd'), style.headSeparator);
    assertEquals(new Style.Row('e', 'f', 'g', 'h'), style.top);
    assertEquals(new Style.Row('i', 'j', 'k', 'l'), style.separator);
    assertEquals(new Style.Row('m', 'n', 'o', 'p'), style.bottom);
    assertEquals(new Style.Row('q', 'r', 's', 't'), style.data);
  }

  @Test
  @DisplayName("Styles created with both methods are propertly seen as equal")
  void overloadsAreEqual() {
    Style charStyle = new StyleBuilder()
        .headSeparator('a', 'b', 'c', 'd')
        .top('e', 'f', 'g', 'h')
        .separator('i', 'j', 'k', 'l')
        .bottom('m', 'n', 'o', 'p')
        .data('q', 'r', 's', 't')
        .build();

    Style rowStyle = new StyleBuilder()
        .headSeparator(new Style.Row('a', 'b', 'c', 'd'))
        .top(new Style.Row('e', 'f', 'g', 'h'))
        .separator(new Style.Row('i', 'j', 'k', 'l'))
        .bottom(new Style.Row('m', 'n', 'o', 'p'))
        .data(new Style.Row('q', 'r', 's', 't'))
        .build();

    assertEquals(charStyle, rowStyle);
  }

  @Test
  void nullRowThrowsNPE() {
    StyleBuilder builder = new StyleBuilder();

    assertThrows(NullPointerException.class, () -> builder.headSeparator(null));
    assertThrows(NullPointerException.class, () -> builder.top(null));
    assertThrows(NullPointerException.class, () -> builder.separator(null));
    assertThrows(NullPointerException.class, () -> builder.bottom(null));
    assertThrows(NullPointerException.class, () -> builder.data(null));
  }
}