package com.a00n.stars.data.local.entities

data class Star(
    val id: Int,
    var name: String,
    var img: String,
    var star: Int
) {
    companion object {
        private var counter: Int = 0
    }

    constructor(name: String, img: String, star: Int) : this(++counter, name, img, star)
}
