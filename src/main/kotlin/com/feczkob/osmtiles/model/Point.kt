package com.feczkob.osmtiles.model

import kotlin.math.PI
import kotlin.math.asinh
import kotlin.math.tan

class Point(
    private val latitude: Double,
    private val longitude: Double,
) {
    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
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

    fun printToReadme() = "\n```\nlatitude: $latitude,\nlongitude: $longitude\n```\n"

    fun printToConsole() = "\n\tLatitude: $latitude,\n\tLongitude: $longitude"

    override fun toString(): String = "Point(latitude=$latitude, longitude=$longitude)"
}
