package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Tile
import java.io.File

class GeneratableArea(
    area: Area,
    override val path: String,
    private val zoom: IntRange,
) : Generatable {
    init {
        require(!zoom.isEmpty()) { "Zoom range must not be empty." }
        require(zoom.first in 0..18) { "Start zoom must be between 0 and 18." }
        require(zoom.last in 0..18) { "End zoom must be between 0 and 18." }
    }

    private val topLeft: Tile = area.topLeftTile(zoom.first)
    private val bottomRight: Tile = area.bottomRightTile(zoom.first)

    override fun generate() {
        ensurePathExists()
        generateFirst()
        generateRest()
        printReadme()
        println("Generated area: ${Area(topLeft.topLeft(), bottomRight.bottomRight())}")
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }

    private fun generateFirst() {
        generateZoom(zoom.first, topLeft, bottomRight)
    }

    private fun generateRest() {
        for (zoomLevel in zoom.first + 1..zoom.last) {
            val topLeftTile = topLeft.topLeft().enclosingTile(zoomLevel)
            // bottom right's bottom right is returned as top left of the bottom right tile + (1, 1) by enclosingTile()
            val bottomRightTile = bottomRight.bottomRight().enclosingTile(zoomLevel) - (1 to 1)
            generateZoom(zoomLevel, topLeftTile, bottomRightTile)
        }
    }

    private fun generateZoom(
        zoomLevel: Int,
        topLeft: Tile,
        bottomRight: Tile,
    ) {
        print("Zoom level $zoomLevel generation: Started...")
        val zoom = Zoom(zoomLevel, topLeft, bottomRight, path)
        zoom.generate()
        println("Finished")
    }

    private fun printReadme() {
        val fileName = "$path/README.md"
        val file = File(fileName)
        val writer = file.printWriter()
        writer.use {
            it.print(
                "The tiles are generated of the following area:\n\n" +
                    "`topLeft:` ${topLeft.topLeft().printToReadme()}" +
                    "`bottomRight:` ${bottomRight.bottomRight().printToReadme()}",
            )
        }
    }
}
