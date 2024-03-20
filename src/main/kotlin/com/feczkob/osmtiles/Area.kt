package com.feczkob.osmtiles

import java.io.File

class Area(
    private val topLeft: Point,
    private val bottomRight: Point,
    private val startZoom: Int = 0,
    private val endZoom: Int = 18,
    private val basePath: String,
) {
    init {
        require(File(basePath).exists()) { "Base path must exist." }
        require(topLeft > bottomRight) { "Top left corner must be above the bottom right corner." }
        require(startZoom in 0..18) { "Start zoom must be between 0 and 18." }
        require(endZoom in 0..18) { "End zoom must be between 0 and 18." }
        require(startZoom <= endZoom) { "Start zoom must be less than or equal to end zoom." }
    }

    fun generate() {
        for (zoomLevel in startZoom..endZoom) {
            val topLeftTile = topLeft.enclosingTile(zoomLevel)
            val bottomRightTile = bottomRight.enclosingTile(zoomLevel)
            val zoom = Zoom(zoomLevel, topLeftTile, bottomRightTile)
            zoom.generate(basePath)
        }
    }
}
