package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Column
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

    private val bottomRight = calculateBottomRight(area)

    // it's not ensured to be a square
    private val columns: Set<FetchableColumn> =
        (topLeft.rangeX(bottomRight)).map { x ->
            FetchableColumn(Column(area, level, x), path)
        }.toSet()

    override suspend fun generate() {
        printHeader()
        fetchColumns()
        printFooter()
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

    private fun printHeader() {
        println("Fetching zoom level $level...")
    }

    private fun printFooter() {
        println("Zoom level $level is finished")
    }

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    private fun calculateBottomRight(area: Area) = area.bottomRightTile(level) - (1 to 1)
}
