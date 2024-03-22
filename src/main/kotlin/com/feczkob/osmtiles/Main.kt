package com.feczkob.osmtiles

import com.feczkob.osmtiles.generatable.GeneratableArea
import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Point

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
        GeneratableArea(
            Area(
                topLeft = topLeft,
                bottomRight = bottomRight,
                startZoom = 13,
                endZoom = 13,
            ),
            path = "/Users/work/IdeaProjects/osm-tile-manager/tiles",
        )
    area.generate()
}
