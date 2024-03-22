package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import java.io.File

class Zoom(
    level: Int,
    topLeft: Tile,
    bottomRight: Tile,
    basePath: String,
) : Generatable {
    override val path = "$basePath/$level"

    // it's not ensured to be a square
    private val columns: Set<Column> =
        (topLeft.rangeX(bottomRight)).map { x ->
            Column(x, topLeft.rangeY(bottomRight), level, path)
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
