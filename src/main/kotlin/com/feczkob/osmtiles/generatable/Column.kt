package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import java.io.File

class Column(
    private val number: Int,
    yRange: IntRange,
    level: Int,
    colNum: Int,
    basePath: String,
) : Generatable {
    override val path = "$basePath/$number"

    private val tiles: Set<GeneratableTile> =
        yRange.map { y ->
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
