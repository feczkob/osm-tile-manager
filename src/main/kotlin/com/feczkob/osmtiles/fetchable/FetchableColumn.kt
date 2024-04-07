package com.feczkob.osmtiles.fetchable

import com.feczkob.osmtiles.model.Column
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class FetchableColumn(
    private val column: Column,
    private val basePath: String,
) : Fetchable() {
    override fun path() = column.printToPath(basePath)

    private val tiles: Set<FetchableTile> =
        column.tiles().map { tile ->
            FetchableTile(tile, path())
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
        val directory = File(path())
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
