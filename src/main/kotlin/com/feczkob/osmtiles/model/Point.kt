package com.feczkob.osmtiles.model

import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.tan

private const val MIN_LON = -180.0
private const val RANGE_LON = 360.0

class Point(
    private val latitude: Double,
    private val longitude: Double,
) {
    /**
     * Calculates the tile that contains this point on the given zoom level.
     * Returns the bottom and/or rightmost tile in case the point is on the edge of a tile.
     * @param zoom Zoom level on which to calculate the enclosing tile
     * @return The tile that contains this point on the given zoom level
     */
    fun enclosingTile(zoom: Int): Tile {
        val latRad = Math.toRadians(latitude)
        val n = 1 shl zoom
        val xTile = ((longitude - MIN_LON) / RANGE_LON * n).toInt()
        val yTile = ((1.0 - asinh(tan(latRad)) / PI) / 2.0 * n).toInt()
        return Tile(zoom, xTile, yTile)
    }

    operator fun compareTo(other: Point): Int {
        val latDiff = latitude.compareTo(other.latitude)
        return if (latDiff != 0) latDiff else longitude.compareTo(other.longitude)
    }

    fun printToReadme() = "\n```\nlatitude: $latitude,\nlongitude: $longitude\n```\n"

    fun printToConsole() = "\n\tLatitude: $latitude,\n\tLongitude: $longitude"
}
