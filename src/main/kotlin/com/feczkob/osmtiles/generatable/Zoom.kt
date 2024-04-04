package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File

class Zoom(
    private val level: Int,
    area: Area,
    basePath: String,
) : Fetchable {
    override val path = "$basePath/$level"

    private val topLeft = area.topLeftTile(level)

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    private val bottomRight = area.bottomRightTile(level) - (1 to 1)

    // it's not ensured to be a square
    private val columns: Set<Column> =
        (topLeft.rangeX(bottomRight)).map { x ->
            Column(x, topLeft.rangeY(bottomRight), level, path)
        }.toSet()

    override suspend fun generate() {
        printHeader()
        fetchColumns()
        printFooter()
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

    private fun printHeader() {
        println("Fetching zoom level $level...")
    }

    private fun printFooter() {
        println("Zoom level $level is finished")
    }

    override fun ensurePathExists() {
        val directory = File(path)
        if (!directory.exists()) {
            directory.mkdirs()
        }
    }
}
