package com.feczkob.osmtiles.generatable

interface Fetchable {
    val path: String

    fun fetch() {
        ensurePathExists()
        generate()
    }

    fun generate()

    fun ensurePathExists()
}
