package com.feczkob.osmtiles.model

import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.tan

class Point(
    private val latitude: Double,
    private val longitude: Double,
) {
    fun enclosingTile(zoom: Int): Tile {
        val latRad = Math.toRadians(latitude)
        val n = 1 shl zoom
        val xTile = ((longitude + 180.0) / 360.0 * n).toInt()
        val yTile = ((1.0 - asinh(tan(latRad)) / PI) / 2.0 * n).toInt()
        return Tile(zoom, xTile, yTile)
    }

    operator fun compareTo(other: Point): Int {
        val latDiff = latitude.compareTo(other.latitude)
        return if (latDiff != 0) latDiff else longitude.compareTo(other.longitude)
    }

    override fun toString(): String = "Point(latitude=$latitude, longitude=$longitude)"
}
