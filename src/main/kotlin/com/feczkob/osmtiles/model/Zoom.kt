package com.feczkob.osmtiles.model

class Zoom(
    private val area: Area,
    private val level: Int,
    private val colRange: IntRange,
) {
    fun printToPath(basePath: String) = "$basePath/$level"

    fun cols(): Set<Column> = colRange.map { createColumn(it) }.toSet()

    fun printHeader() = println("Fetching zoom level $level...")

    fun printFooter() = println("Zoom level $level is finished")

    private fun topLeft(): Tile = area.topLeftTile(level)

    private fun bottomRight(): Tile = calculateBottomRight(area)

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    private fun calculateBottomRight(area: Area) = area.bottomRightTile(level) - (1 to 1)

    private fun createColumn(x: Int): Column = Column(level, topLeft().rangeY(bottomRight()), x)
}
