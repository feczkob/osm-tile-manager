package com.feczkob.osmtiles

import com.feczkob.osmtiles.generatable.GeneratableArea
import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Point
import com.feczkob.osmtiles.model.Tile

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
//    println(tile)
//    println(tile.topLeft())
//    println(tile.bottomRight())

    val area =
        GeneratableArea(
            Area(
                topLeft = topLeft,
                bottomRight = bottomRight,
                startZoom = 13,
                endZoom = 15,
            ),
            path = "/Users/work/IdeaProjects/osm-tile-manager/tiles",
        )
    area.generate()

    val point = Point(47.457808530750306, 19.1162109375)
    println(point.enclosingTile(14))

//    Top left: Point(latitude=47.54687159892238, longitude=18.984375), bottom right: Point(latitude=47.457808530750306, longitude=19.1162109375), top left tile: Tile(zoom=13, x=4528, y=2863), bottom right tile: Tile(zoom=13, x=4530, y=2865)
//    Top left: Point(latitude=47.54687159892238, longitude=18.984375), bottom right: Point(latitude=47.44294999517949, longitude=19.13818359375), top left tile: Tile(zoom=14, x=9056, y=5726), bottom right tile: Tile(zoom=14, x=9062, y=5732)

    val tile1 = Tile(14, 9061, 5731)
    println("${tile1.bottomRight()}")
}
