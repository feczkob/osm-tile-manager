package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import java.io.File

class GeneratableArea(
    private val area: Area,
    private val basePath: String,
) : Generatable {
    override fun generate() {
        ensurePathExists()
        for (zoomLevel in area.startZoom..area.endZoom) {
            val topLeftTile = area.topLeft.enclosingTile(zoomLevel)
            val bottomRightTile = area.bottomRight.enclosingTile(zoomLevel)
            val zoom = Zoom(zoomLevel, topLeftTile, bottomRightTile, basePath)
            zoom.generate()
        }
    }

    override fun ensurePathExists() {
        require(File(basePath).exists()) { "Base path must exist." }
    }
}
