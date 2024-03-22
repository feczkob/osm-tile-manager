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

        val path1 = "$path/${area.startZoom}"
        val columns1 =
            (topLeft.rangeX(bottomRight)).map { x ->
                Column(x, topLeft.rangeY(bottomRight), area.startZoom, path1)
            }.toSet()
        val zoom1 = Zoom(area.startZoom, columns1, path)
        zoom1.generate()

        for (zoomLevel in area.startZoom + 1..area.endZoom) {
            val topLeftTile = topLeft.topLeft().enclosingTile(zoomLevel)
            val bottomRightTile = bottomRight.bottomRight().enclosingTile(zoomLevel)
            val path = "$path/$zoomLevel"
            val columns =
                (topLeftTile.rangeX(bottomRightTile)).map { x ->
                    Column(x, topLeftTile.rangeY(bottomRightTile), zoomLevel, path)
                }.toSet()
            val zoom = Zoom(zoomLevel, columns, this.path)
            zoom.generate()
        }
    }

    override fun ensurePathExists() = require(File(path).exists()) { "Base path must exist." }
}
