package com.feczkob.osmtiles.generatable

import com.feczkob.osmtiles.model.Area
import java.io.File

class GeneratableArea(
    private val area: Area,
    override val path: String,
) : Generatable {
    override fun generate() {
        ensurePathExists()

        for (zoomLevel in area.startZoom..area.endZoom) {
            val topLeftTile = area.topLeft.enclosingTile(zoomLevel)
            val bottomRightTile = area.bottomRight.enclosingTile(zoomLevel)
            val path = "$path/$zoomLevel"
            val columns =
                (topLeftTile.rangeX(bottomRightTile)).map { x ->
                    Column(x, topLeftTile.rangeY(bottomRightTile), zoomLevel, x, path)
                }.toSet()
            val zoom = Zoom(zoomLevel, columns, this.path)
            zoom.generate()
        }
    }

    override fun ensurePathExists() {
        require(File(path).exists()) { "Base path must exist." }
    }
}
