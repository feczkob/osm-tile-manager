package com.feczkob.osmtiles

import com.feczkob.osmtiles.fetchable.FetchableArea
import com.feczkob.osmtiles.model.Area
import com.feczkob.osmtiles.model.Point
import io.github.cdimascio.dotenv.Dotenv

private const val TOP_LEFT_LAT = "TOP_LEFT_LAT"
private const val TOP_LEFT_LON = "TOP_LEFT_LON"
private const val BOTTOM_RIGHT_LAT = "BOTTOM_RIGHT_LAT"
private const val BOTTOM_RIGHT_LON = "BOTTOM_RIGHT_LON"
private const val TILES_PATH = "TILES_PATH"
private const val MIN_ZOOM = "MIN_ZOOM"
private const val MAX_ZOOM = "MAX_ZOOM"
private const val ENVIRONMENT_VARIABLE_IS_NOT_SET = "environment variable is not set"

suspend fun main() {
    val dotenv = Dotenv.load()

    val maxLat = dotenv[TOP_LEFT_LAT] ?: error("$TOP_LEFT_LAT $ENVIRONMENT_VARIABLE_IS_NOT_SET")
    val minLong = dotenv[TOP_LEFT_LON] ?: error("$TOP_LEFT_LON $ENVIRONMENT_VARIABLE_IS_NOT_SET")

    val minLat = dotenv[BOTTOM_RIGHT_LAT] ?: error("$BOTTOM_RIGHT_LAT $ENVIRONMENT_VARIABLE_IS_NOT_SET")
    val maxLong = dotenv[BOTTOM_RIGHT_LON] ?: error("$BOTTOM_RIGHT_LON $ENVIRONMENT_VARIABLE_IS_NOT_SET")

    val topLeft = Point(maxLat.toDouble(), minLong.toDouble())
    val bottomRight = Point(minLat.toDouble(), maxLong.toDouble())

    val path = dotenv[TILES_PATH] ?: error("$TILES_PATH $ENVIRONMENT_VARIABLE_IS_NOT_SET")

    val minZoom = dotenv[MIN_ZOOM]?.toInt() ?: error("$MIN_ZOOM $ENVIRONMENT_VARIABLE_IS_NOT_SET")
    val maxZoom = dotenv[MAX_ZOOM]?.toInt() ?: error("$MAX_ZOOM $ENVIRONMENT_VARIABLE_IS_NOT_SET")

    val area =
        FetchableArea(
            Area(
                topLeft = topLeft,
                bottomRight = bottomRight,
            ),
            path = path,
            zoomLevels = minZoom..maxZoom,
        )
    area.fetch()
}
