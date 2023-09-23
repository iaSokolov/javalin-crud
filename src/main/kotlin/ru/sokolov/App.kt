package ru.sokolov

import io.javalin.Javalin
import ru.sokolov.route.RouteBuilder

fun main() {
    Javalin
        .create()
        .registerRoute()
        .start(7070)
}

private fun Javalin.registerRoute(): Javalin {
    RouteBuilder().register(this)
    return this
}
