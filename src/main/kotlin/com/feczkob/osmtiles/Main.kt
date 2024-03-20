package com.feczkob.osmtiles

fun main() {
//    val minLat = 47.4683
//    val maxLat = 47.5209
//    val minLong = 19.0148
//    val maxLong = 20.3348

    val minLat = 47.4724
    val maxLat = 47.5189
    val minLong = 19.0129
    val maxLong = 19.0829

    val topLeft = Point(maxLat, minLong)
    val bottomRight = Point(minLat, maxLong)

    val tile = topLeft.enclosingTile(13)
    println(tile)
    println(tile.topLeft())
    println(tile.bottomRight())

    val area =
        Area(
            topLeft = topLeft,
            bottomRight = bottomRight,
            startZoom = 13,
            endZoom = 13,
            basePath = "/Users/work/IdeaProjects/osm-tile-manager/tiles",
        )
    area.generate()
}
