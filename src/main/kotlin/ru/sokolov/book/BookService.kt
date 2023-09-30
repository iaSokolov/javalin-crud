package ru.sokolov.book

import org.jooq.DSLContext
import ru.sokolov.connection.DataSource
import ru.sokolov.connection.DataSourceContext

class BookService {
    companion object {
        private val bookRepository = BookRepository()
    }

    fun getAll(): List<Book> {
        return DataSource().getConnection().use { connection ->
            this.getAll(
                context = DataSourceContext(connection).getContext()
            )
        }
    }

    fun getAll(context: DSLContext): List<Book> {
        return bookRepository.getAll(context)
    }

    fun create(author: Book): Book {
        return DataSource().getConnection().use { connection ->
            this.create(
                author = author,
                context = DataSourceContext(connection).getContext()
            )
        }
    }

    fun create(author: Book, context: DSLContext): Book {
        return bookRepository.create(
            model = author,
            context = context
        )
    }
}