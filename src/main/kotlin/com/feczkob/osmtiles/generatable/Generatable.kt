package com.feczkob.osmtiles.generatable

interface Generatable {
    val path: String

    fun generate()

    fun ensurePathExists()
}
