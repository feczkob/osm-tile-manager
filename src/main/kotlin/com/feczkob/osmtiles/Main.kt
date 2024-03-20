package com.feczkob.osmtiles

fun main() {
    val minLat = 47.4683
    val maxLat = 47.5209
    val minLong = 19.0148
    val maxLong = 20.3348

    val topLeft = Point(maxLat, minLong)
    val bottomRight = Point(minLat, maxLong)

    println(topLeft.enclosingTile(13))
}
