package com.feczkob.osmtiles

import java.io.File

class Column(
    private val number: Int,
    private val tiles: Set<Tile>,
) {
    fun generate(basePath: String) {
        val path = path(basePath)
        ensurePathExists(path)
        tiles.forEach { tile ->
            tile.download(path)
        }
    }

    private fun path(basePath: String): String = "$basePath/$number"

    private fun ensurePathExists(path: String) {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    override fun toString(): String = "Column(number=$number, tiles=$tiles)"
}
