package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Zoom
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.time.Duration
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
                fetchZooms()
                printReadme()
            }
        printSummary(timeTaken)
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }

    private suspend fun fetchZooms() =
        coroutineScope {
            (zoom.first..zoom.last).forEach { zoomLevel ->
                launch {
                    FetchableZoom(
                        Zoom(
                            level = zoomLevel,
                            colRange = area.topLeftTile(zoomLevel).rangeX(calculateBottomRight(zoomLevel)),
                            rowRange = area.topLeftTile(zoomLevel).rangeY(calculateBottomRight(zoomLevel)),
                        ),
                        basePath = path,
                    ).fetch()
                }
            }
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

    private fun printSummary(timeTaken: Duration) {
        println(
            "The area was fetched in $timeTaken.\n" +
                area.printToConsole(),
        )
    }

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    private fun calculateBottomRight(level: Int) = area.bottomRightTile(level) - (1 to 1)
}
