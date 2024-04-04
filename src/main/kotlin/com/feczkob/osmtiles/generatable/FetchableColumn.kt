package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Column
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class FetchableColumn(
    private val column: Column,
    basePath: String,
) : Fetchable {
    override val path = column.printToPath(basePath)

    private val tiles: Set<FetchableTile> =
        column.top().rangeY(column.bottom()).map { y ->
            FetchableTile(column.createTile(y), path)
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
}
