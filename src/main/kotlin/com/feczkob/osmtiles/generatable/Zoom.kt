package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import java.io.File

class Zoom(
    private val level: Int,
    topLeft: Tile,
    bottomRight: Tile,
    basePath: String,
) : Generatable {
    private val path = "$basePath/$level"

    // TODO make numOfColumns = numOfTiles in a Column to get square
    private val columns: Set<Column> =
        (topLeft.x..bottomRight.x).map { x ->
            Column(x, topLeft, bottomRight, level, x, path)
        }.toSet()

    override fun generate() {
        ensurePathExists()
        columns.forEach { column ->
            column.generate()
        }
    }

    override fun ensurePathExists() {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
