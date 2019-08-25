package net.tehbilly.tablewriter

fun <T> table(init: TableWriterDSL<T>.() -> Unit): TableWriter<T> =
    TableWriterDSL<T>().apply(init).build()

class TableWriterDSL<T> {
    private val builder = TableWriterBuilder<T>()

    private var _header: Boolean = true
    var header: Boolean
        get() = _header
        set(value) {
            _header = value
            builder.header(value)
        }

    private var _compact: Boolean = false
    var compact: Boolean
        get() = _compact
        set(value) {
            _compact = value
            builder.compact(value)
        }

    fun column(name: String, init: ColumnDSL<T>.() -> Unit = {}) {
        builder.column(ColumnDSL<T>(name).apply(init).build())
    }

    fun style(init: StyleBuilder.() -> Unit) {
        builder.style(StyleBuilder().apply(init))
    }

    fun build(): TableWriter<T> = builder.build()
}

class ColumnDSL<T>(name: String) {
    private val builder: ColumnBuilder<T> = ColumnBuilderImpl<T>().name(name)
    var width: Int = 10
    var maxWidth: Int = 50

    fun formatter(formatter: (T) -> String) {
        builder.formatter(formatter)
    }

    fun build(): Column<T> = builder
        .width(width)
        .maxWidth(maxWidth)
        .build()
}