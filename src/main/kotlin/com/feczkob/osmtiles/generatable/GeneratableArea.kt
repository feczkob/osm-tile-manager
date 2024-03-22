package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Tile
import java.io.File

class GeneratableArea(
    private val area: Area,
    override val path: String,
) : Generatable {
    private val topLeft: Tile = area.topLeft.enclosingTile(area.startZoom)
    private val bottomRight: Tile = area.bottomRight.enclosingTile(area.startZoom)

    override fun generate() {
        ensurePathExists()
        generateFirst()
        generateRest()
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

    private fun generateFirst() {
        generateZoom(area.startZoom, topLeft, bottomRight)
    }

    private fun generateRest() {
        for (zoomLevel in area.startZoom + 1..area.endZoom) {
            val topLeftTile = topLeft.topLeft().enclosingTile(zoomLevel)
            val bottomRightTile = bottomRight.bottomRight().enclosingTile(zoomLevel) - (1 to 1)
            generateZoom(zoomLevel, topLeftTile, bottomRightTile)
        }
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }
}
