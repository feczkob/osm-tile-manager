package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Zoom
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class FetchableZoom(
    private val zoom: Zoom,
    private val basePath: String,
) : Fetchable() {
    override fun path() = zoom.printToPath(basePath)

    // it's not ensured to be a square
    private val columns: Set<FetchableColumn> =
        zoom.cols().map { column ->
            FetchableColumn(column, path())
        }.toSet()

    override suspend fun generate() {
        printHeader()
        fetchColumns()
        printFooter()
    }

    override fun ensurePathExists() {
        val directory = File(path())
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

    private fun printFooter(): Unit = println("${zoom.printLevel()} is finished")

    private fun printHeader() = println("Fetching ${zoom.printLevel()}...")
}
