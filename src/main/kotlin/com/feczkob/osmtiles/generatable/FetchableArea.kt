package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Tile
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.time.measureTime

class FetchableArea(
    area: Area,
    override val path: String,
    private val zoom: IntRange,
) : Fetchable {
    init {
        require(!zoom.isEmpty()) { "Zoom range must not be empty." }
        require(zoom.first in 0..18) { "Start zoom must be between 0 and 18." }
        require(zoom.last in 0..18) { "End zoom must be between 0 and 18." }
    }

    private val area =
        Area(
            area.topLeftTile(zoom.first).topLeft(),
            (area.bottomRightTile(zoom.first)).bottomRight(),
        )

    override suspend fun generate() {
        val timeTaken =
            measureTime {
                generateFirst()
                generateRest()
                printReadme()
            }
        println(
            "The area was fetched in $timeTaken.\n" +
                area.printToConsole(),
        )
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }

    private suspend fun generateFirst() {
        fetchZoom(
            zoom.first,
            area.topLeftTile(zoom.first),
            (area.bottomRightTile(zoom.first) - (1 to 1)),
        )
    }

    private suspend fun generateRest() =
        coroutineScope {
            for (zoomLevel in zoom.first + 1..zoom.last) {
                launch {
                    val topLeftTile = area.topLeftTile(zoomLevel)
                    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
                    val bottomRightTile = (area.bottomRightTile(zoomLevel) - (1 to 1))
                    fetchZoom(zoomLevel, topLeftTile, bottomRightTile)
                }
            }
        }

    private suspend fun fetchZoom(
        zoomLevel: Int,
        topLeft: Tile,
        bottomRight: Tile,
    ) {
        println("Fetching zoom level $zoomLevel...")
        val zoom = Zoom(zoomLevel, topLeft, bottomRight, path)
        zoom.fetch()
        println("Zoom level $zoomLevel is finished")
    }

    private fun printReadme() {
        val fileName = "$path/README.md"
        val file = File(fileName)
        val writer = file.printWriter()
        writer.use {
            it.print("The tiles are generated of the following area:\n\n" + area.printToReadme())
        }
        println("README.md printed to $fileName")
    }
}
