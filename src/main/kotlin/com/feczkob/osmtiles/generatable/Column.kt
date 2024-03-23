package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Tile
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class Column(
    private val number: Int,
    yRange: IntRange,
    level: Int,
    basePath: String,
) : Fetchable {
    override val path = "$basePath/$number"

    private val tiles: Set<FetchableTile> =
        yRange.map { y ->
            FetchableTile(Tile(level, number, y), path)
        }.toSet()

    override suspend fun generate() =
        coroutineScope {
            tiles.forEach { tile ->
                launch {
                    tile.fetch()
                }
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
