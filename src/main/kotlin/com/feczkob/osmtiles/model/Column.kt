package com.feczkob.osmtiles.model

class Column(
    private val area: Area,
    private val zoom: Int,
    private val x: Int,
) {
    fun printToPath(basePath: String) = "$basePath/$x"

    fun top(): Tile = area.topLeftTile(zoom)

    fun bottom(): Tile = calculateBottomRight(area)

    fun createTile(y: Int): Tile = Tile(zoom, x, y)

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    private fun calculateBottomRight(area: Area) = area.bottomRightTile(zoom) - (1 to 1)
}
