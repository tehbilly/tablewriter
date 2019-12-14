package net.tehbilly.tablewriter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TableWriterDSLTest {
    @Test
    fun basic() {
        val expected = """
            ┌────────────┬──────────┬─────┐
            │ Original   │ Reversed │ Upp │
            ╞════════════╪══════════╪═════╡
            │ foo        │ oof      │ FOO │
            ├────────────┼──────────┼─────┤
            │ bar        │ rab      │ BAR │
            ├────────────┼──────────┼─────┤
            │ baz        │ zab      │ BAZ │
            └────────────┴──────────┴─────┘
            
        """.trimIndent()
            .replace("\n", System.lineSeparator())

        val table = table<String> {
            column("Original")

            column("Reversed") {
                width = 5
                formatter { it.reversed() }
            }

            column("Uppercase") {
                maxWidth = 3
                formatter { it.toUpperCase() }
            }
        }

        table.add("foo")
        table.add("bar")
        table.add("baz")

        assertEquals(expected, table.asString())
    }

    @Test
    fun compact() {
        val expected = """
            ┌────────────┬──────────┬─────┐
            │ Original   │ Reversed │ Upp │
            ╞════════════╪══════════╪═════╡
            │ foo        │ oof      │ FOO │
            │ bar        │ rab      │ BAR │
            │ baz        │ zab      │ BAZ │
            └────────────┴──────────┴─────┘
            
        """.trimIndent()
            .replace("\n", System.lineSeparator())

        val table = table<String> {
            compact = true

            column("Original")

            column("Reversed") {
                width = 5
                formatter { it.reversed() }
            }

            column("Uppercase") {
                maxWidth = 3
                formatter { it.toUpperCase() }
            }
        }

        table.add("foo")
        table.add("bar")
        table.add("baz")

        assertEquals(expected, table.asString())
    }

    @Test
    fun styled() {
        val expected = """
            *------------*----------*-----*
            | Original   | Reversed | Upp |
            *============*==========*=====*
            | foo        | oof      | FOO |
            *------------*----------*-----*
            | bar        | rab      | BAR |
            *------------*----------*-----*
            | baz        | zab      | BAZ |
            *------------*----------*-----*
            
        """.trimIndent()
            .replace("\n", System.lineSeparator())

        val table = table<String> {
            style {
                headSeparator('*', '*', '*', '=')
                top('*', '*', '*', '-')
                separator('*', '*', '*', '-')
                bottom('*', '*', '*', '-')
                data('|', '|', '|')
            }

            column("Original")

            column("Reversed") {
                width = 5
                formatter { it.reversed() }
            }

            column("Uppercase") {
                maxWidth = 3
                formatter { it.toUpperCase() }
            }
        }

        table.add("foo")
        table.add("bar")
        table.add("baz")

        assertEquals(expected, table.asString())
    }

    @Test
    fun alignCenter() {
        val expected = """
            *------------*----------*-----*
            | Original   | Reversed | Upp |
            *============*==========*=====*
            |     foo    |    oof   | FOO |
            *------------*----------*-----*
            |     bar    |    rab   | BAR |
            *------------*----------*-----*
            |     baz    |    zab   | BAZ |
            *------------*----------*-----*
            
        """.trimIndent()
            .replace("\n", System.lineSeparator())

        val table = table<String> {
            style {
                headSeparator('*', '*', '*', '=')
                top('*', '*', '*', '-')
                separator('*', '*', '*', '-')
                bottom('*', '*', '*', '-')
                data('|', '|', '|')
            }

            column("Original") {
                align = Alignment.CENTER
            }

            column("Reversed") {
                width = 5
                formatter { it.reversed() }
                align = Alignment.CENTER
            }

            column("Uppercase") {
                maxWidth = 3
                formatter { it.toUpperCase() }
                align = Alignment.CENTER
            }
        }

        table.add("foo")
        table.add("bar")
        table.add("baz")

        assertEquals(expected, table.asString())
    }

    @Test
    fun alignRight() {
        val expected = """
            *------------*----------*-----*
            | Original   | Reversed | Upp |
            *============*==========*=====*
            |        foo |      oof | FOO |
            *------------*----------*-----*
            |        bar |      rab | BAR |
            *------------*----------*-----*
            |        baz |      zab | BAZ |
            *------------*----------*-----*
            
        """.trimIndent()
            .replace("\n", System.lineSeparator())

        val table = table<String> {
            style {
                headSeparator('*', '*', '*', '=')
                top('*', '*', '*', '-')
                separator('*', '*', '*', '-')
                bottom('*', '*', '*', '-')
                data('|', '|', '|')
            }

            column("Original") {
                align = Alignment.RIGHT
            }

            column("Reversed") {
                width = 5
                formatter { it.reversed() }
                align = Alignment.RIGHT
            }

            column("Uppercase") {
                maxWidth = 3
                formatter { it.toUpperCase() }
                align = Alignment.RIGHT
            }
        }

        table.add("foo")
        table.add("bar")
        table.add("baz")

        assertEquals(expected, table.asString())
    }
}