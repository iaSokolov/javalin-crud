package ru.sokolov.book

import org.jooq.DSLContext
import ru.sokolov.jooq.public_.Tables.BOOK

class BookRepository {
    fun getAll(context: DSLContext): List<Book> {
        try {
            return context
                .select()
                .from(BOOK)
                .fetch()
                .into(Book::class.java)

        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

    fun create(model: Book, context: DSLContext): Book {
        return try {
            val id = context
                .nextval("book_id_gen")
                .toLong()

            val newRecord = context
                .insertInto(BOOK, BOOK.ID, BOOK.NAME, BOOK.AUTHOR)
                .values(id, model.name, model.author)
                .returningResult(BOOK)
                .fetchOne()
                ?.into(Book::class.java)

            newRecord ?: throw Exception("Произошла ошибка при создании записи Book")

        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }
}