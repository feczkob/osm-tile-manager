package com.feczkob.osmtiles.model

class Area(
    val topLeft: Point,
    val bottomRight: Point,
    val startZoom: Int = 0,
    val endZoom: Int = 18,
) {
    init {
        require(topLeft > bottomRight) { "Top left corner must be above the bottom right corner." }
        require(startZoom in 0..18) { "Start zoom must be between 0 and 18." }
        require(endZoom in 0..18) { "End zoom must be between 0 and 18." }
        require(startZoom <= endZoom) { "Start zoom must be less than or equal to end zoom." }
    }
}
