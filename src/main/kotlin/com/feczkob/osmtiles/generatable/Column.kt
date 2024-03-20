package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import java.io.File

class Column(
    private val number: Int,
    topLeft: Tile,
    bottomRight: Tile,
    level: Int,
    colNum: Int,
    basePath: String,
) : Generatable {
    private val path = "$basePath/$number"

    private val tiles: Set<GeneratableTile> =
        (topLeft.y..bottomRight.y).map { y ->
            GeneratableTile(Tile(level, colNum, y), path)
        }.toSet()

    override fun generate() {
        ensurePathExists()
        tiles.forEach { tile ->
            tile.generate()
        }
    }

    override fun ensurePathExists() {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    override fun toString(): String = "Column(number=$number, tiles=$tiles)"
}
