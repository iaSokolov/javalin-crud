package ru.sokolov.author

import org.jooq.DSLContext
import ru.sokolov.jooq.public_.Tables.AUTHOR

class AuthorRepository {
    fun getAll(context: DSLContext): List<Author> {
        try {
            return context
                .select()
                .from(AUTHOR)
                .fetch()
                .into(Author::class.java)

        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

    fun create(model: Author, context: DSLContext): Author {
        return try {
            val id = context
                .nextval("author_id_gen")
                .toLong()

            val newRecord = context
                .insertInto(AUTHOR, AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
                .values(id, model.firstName, model.lastName)
                .returningResult(AUTHOR)
                .fetchOne()
                ?.into(Author::class.java)

            newRecord ?: throw Exception("Произошла ошибка при создании записи Author")

        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }
}