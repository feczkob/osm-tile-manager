package com.feczkob.osmtiles.model

class Area(
    private val topLeft: Point,
    private val bottomRight: Point,
) {
    init {
        require(topLeft > bottomRight) { "Top left corner must be above the bottom right corner." }
    }

    @Suppress("ktlint:standard:property-naming")
    private lateinit var _bottomRightTile: Tile

    fun topLeftTile(zoom: Int) = topLeft.enclosingTile(zoom)

    // bottom right tile's bottom right point is returned as top left point of the bottom right tile + (1, 1) by enclosingTile()
    // because of this we calculate the bottom right tile only once
    fun bottomRightTile(zoom: Int): Tile =
        when (this::_bottomRightTile.isInitialized) {
            true -> _bottomRightTile
            false ->
                bottomRight.enclosingTile(zoom).also {
                    _bottomRightTile = it
                }
        }.also { println(it) }

    fun printToConsole() = "Top left: ${topLeft.printToConsole()},\nBottom right: ${bottomRight.printToConsole()}"

    fun printToReadme() =
        "`topLeft:` ${topLeft.printToReadme()}" +
            "`bottomRight:` ${bottomRight.printToReadme()}"

    override fun toString(): String = "Area(topLeft=$topLeft, bottomRight=$bottomRight)"
}
