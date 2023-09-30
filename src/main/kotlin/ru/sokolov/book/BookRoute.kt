package ru.sokolov.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.javalin.Javalin
import io.javalin.http.ContentType
import io.javalin.http.HttpStatus

class BookRoute {
    companion object {
        val bookController = BookController()
        val objectMapper = ObjectMapper().registerKotlinModule()
    }

    fun register(app: Javalin) {
        app.get("/book") { ctx ->
            val books = bookController.getAll()
            val json = objectMapper.writeValueAsString(books)

            ctx.status(HttpStatus.OK)
            ctx.contentType(ContentType.JSON)
            ctx.result(json)
        }

        app.post("/book") { ctx ->
            val bookDraft = objectMapper.readValue(ctx.body(), BookDto::class.java)
            val authorNew = bookController.create(bookDraft)
            val json = objectMapper.writeValueAsString(authorNew)

            ctx.status(HttpStatus.CREATED)
            ctx.contentType(ContentType.JSON)
            ctx.result(json)
        }
    }
}