package com.feczkob.osmtiles

import com.feczkob.osmtiles.generatable.GeneratableArea
import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Point

fun main() {
    val minLat = 47.4724
    val maxLat = 47.5189
    val minLong = 19.0129
    val maxLong = 19.0829

    val topLeft = Point(maxLat, minLong)
    val bottomRight = Point(minLat, maxLong)

    val area =
        GeneratableArea(
            Area(
                topLeft = topLeft,
                bottomRight = bottomRight,
            ),
            path = "/Users/work/IdeaProjects/osm-tile-manager/tiles",
            zoom = 13..15,
        )
    area.generate()

//    val tile1 = Tile(14, 9061, 5731)
//    println("${tile1.bottomRight()}")
}
