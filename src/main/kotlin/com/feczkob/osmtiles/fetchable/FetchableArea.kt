package com.feczkob.osmtiles.fetchable

import com.feczkob.osmtiles.model.Area
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.time.Duration
import kotlin.time.measureTime

private const val MAX_ZOOM = 18
private const val MIN_ZOOM = 0

class FetchableArea(
    private val area: Area,
    private val path: String,
    private val zoomLevels: IntRange,
) : Fetchable() {
    override fun path() = path

    init {
        require(!zoomLevels.isEmpty()) { "Zoom range must not be empty." }
        require(zoomLevels.first in MIN_ZOOM..MAX_ZOOM) { "Start zoom must be between $MIN_ZOOM and $MAX_ZOOM." }
        require(zoomLevels.last in MIN_ZOOM..MAX_ZOOM) { "End zoom must be between $MIN_ZOOM and $MAX_ZOOM." }
    }

    private fun area() =
        Area(
            area.topLeftTile(zoomLevels.first).topLeft(),
            (area.bottomRightTile(zoomLevels.first)).bottomRight(),
        )

    override suspend fun generate() {
        val timeTaken =
            measureTime {
                fetchZooms()
                printReadme()
            }
        printSummary(timeTaken)
    }

    override fun ensurePathExists() = require(File(path()).exists()) { "Base path must exist." }

    private suspend fun fetchZooms() {
        coroutineScope {
            area().zooms(zoomLevels).map { zoom ->
                launch {
                    FetchableZoom(
                        zoom = zoom,
                        basePath = path(),
                    ).fetch()
                }
            }
        }
    }

    private fun printReadme() {
        val fileName = "${path()}/README.md"
        val file = File(fileName)
        val writer = file.printWriter()
        writer.use {
            it.print("The tiles are generated of the following area:\n\n" + area().printToReadme())
        }
        println("README.md printed to $fileName")
    }

    private fun printSummary(timeTaken: Duration) {
        println(
            "The area was fetched in $timeTaken.\n" +
                area().printToConsole(),
        )
    }
}
