package ru.sokolov.route

import io.javalin.Javalin
import ru.sokolov.author.AuthorRoute
import ru.sokolov.book.BookRoute

class RouteBuilder {
    fun register(app: Javalin) {
        AuthorRoute().register(app)
        BookRoute().register(app)
    }
}