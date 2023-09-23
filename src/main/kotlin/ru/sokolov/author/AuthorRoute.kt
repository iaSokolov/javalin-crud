package ru.sokolov.author

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.javalin.Javalin
import io.javalin.http.ContentType
import io.javalin.http.HttpStatus

class AuthorRoute {
    companion object {
        val authorController = AuthorController()
        val objectMapper = ObjectMapper().registerKotlinModule()
    }

    fun register(app: Javalin) {
        app.get("/author") { ctx ->
            val authors = authorController.getAll()
            val json = objectMapper.writeValueAsString(authors)

            ctx.status(HttpStatus.OK)
            ctx.contentType(ContentType.JSON)
            ctx.result(json)
        }

        app.post("/author") { ctx ->
            val authorDraft = objectMapper.readValue(ctx.body(), AuthorDto::class.java)
            val authorNew = authorController.create(authorDraft)
            val json = objectMapper.writeValueAsString(authorNew)

            ctx.status(HttpStatus.CREATED)
            ctx.contentType(ContentType.JSON)
            ctx.result(json)
        }
    }
}