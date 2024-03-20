package com.feczkob.osmtiles

import java.io.File

class Zoom(
    private val level: Int,
    topLeft: Tile,
    bottomRight: Tile,
) {
    private val columns: Set<Column> =
        (topLeft.x..bottomRight.x).map { x ->
            val tiles: Set<Tile> =
                (topLeft.y..bottomRight.y).map { y ->
                    Tile(level, x, y)
                }.toSet()
            Column(x, tiles)
        }.toSet()

    fun generate(basePath: String) {
        val path = path(basePath)
        ensurePathExists(path)
        columns.forEach { column ->
            column.generate(path)
        }
    }

    private fun path(basePath: String): String = "$basePath/$level"

    private fun ensurePathExists(path: String) {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
