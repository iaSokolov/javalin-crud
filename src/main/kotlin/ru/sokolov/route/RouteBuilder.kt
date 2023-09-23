package ru.sokolov.route

import io.javalin.Javalin
import ru.sokolov.author.AuthorRoute

class RouteBuilder {
    fun register(app: Javalin) {
        AuthorRoute().register(app)
    }
}