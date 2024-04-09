package com.feczkob.osmtiles.model

class Column(
    private val zoomLevel: Int,
    private val x: Int,
    private val rowRange: IntRange,
) {
    fun printToPath(basePath: String) = "$basePath/$x"

    fun tiles(): Set<Tile> = rowRange.map { createTile(it) }.toSet()

    private fun createTile(y: Int): Tile = Tile(zoomLevel, x, y)
}
