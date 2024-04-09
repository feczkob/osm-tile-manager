package com.feczkob.osmtiles.model

class Area(
    private val topLeft: Point,
    private val bottomRight: Point,
) {
    init {
        require(topLeft > bottomRight) { "Top left corner must be above and to the right of the bottom right corner." }
    }

    fun topLeftTile(zoom: Int): Tile = topLeft.enclosingTile(zoom)

    fun bottomRightTile(zoom: Int): Tile = bottomRight.enclosingTile(zoom)

    fun zooms(zoomLevels: IntRange): Set<Zoom> = zoomLevels.map { createZoom(it) }.toSet()

    fun printToReadme() = "`topLeft:` ${topLeft.printToReadme()} `bottomRight:` ${bottomRight.printToReadme()}"

    fun printToConsole() = "Top left: ${topLeft.printToConsole()},\nBottom right: ${bottomRight.printToConsole()}"

    private fun createZoom(level: Int): Zoom = Zoom(level, colRange(level), rowRange(level))

    private fun colRange(zoom: Int): IntRange = topLeftTile(zoom).rangeX(calculateBottomRight(zoom))

    private fun rowRange(zoom: Int): IntRange = topLeftTile(zoom).rangeY(calculateBottomRight(zoom))

    private fun calculateBottomRight(level: Int) = bottomRightTile(level) - (1 to 1)
}
