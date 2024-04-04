package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Zoom
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class FetchableZoom(
    private val zoom: Zoom,
    basePath: String,
) : Fetchable {
    override val path = zoom.printToPath(basePath)

    // it's not ensured to be a square
    private val columns: Set<FetchableColumn> =
        zoom.cols().map { column ->
            FetchableColumn(column, path)
        }.toSet()

    override suspend fun generate() {
        zoom.printHeader()
        fetchColumns()
        zoom.printFooter()
    }

    override fun ensurePathExists() {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }

    private suspend fun fetchColumns() {
        coroutineScope {
            columns.forEach { column ->
                launch {
                    column.fetch()
                }
            }
        }
    }
}
