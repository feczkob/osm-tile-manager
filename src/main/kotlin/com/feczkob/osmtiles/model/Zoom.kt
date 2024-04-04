package com.feczkob.osmtiles.model

class Zoom(
    private val level: Int,
    private val colRange: IntRange,
    private val rowRange: IntRange,
) {
    fun printToPath(basePath: String) = "$basePath/$level"

    fun cols(): Set<Column> = colRange.map { createColumn(it) }.toSet()

    fun printHeader() = println("Fetching zoom level $level...")

    fun printFooter() = println("Zoom level $level is finished")

    private fun createColumn(x: Int): Column = Column(level, x, rowRange)
}
