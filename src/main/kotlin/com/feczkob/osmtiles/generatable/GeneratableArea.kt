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
        val zoom1 = Zoom(area.startZoom, topLeft, bottomRight, path)
        zoom1.generate()

        for (zoomLevel in area.startZoom + 1..area.endZoom) {
            val topLeftTile = topLeft.topLeft().enclosingTile(zoomLevel)
            val bottomRightTile = bottomRight.bottomRight().enclosingTile(zoomLevel) - (1 to 1)
            val zoom = Zoom(zoomLevel, topLeftTile, bottomRightTile, path)
            zoom.generate()
        }
    }

    private fun generateZoom(
        topLeft: Tile,
        bottomRight: Tile,
        zoomLevel: Int,
    ) {
        val zoom = Zoom(zoomLevel, topLeft, bottomRight, path)
        zoom.generate()
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }
}
