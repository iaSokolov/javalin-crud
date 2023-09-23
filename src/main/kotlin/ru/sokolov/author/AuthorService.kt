package ru.sokolov.author

import org.jooq.DSLContext
import ru.sokolov.connection.DataSource
import ru.sokolov.connection.DataSourceContext

class AuthorService {
    companion object {
        private val authorRepository = AuthorRepository()
    }

    fun getAll(): List<Author> {
        return DataSource().getConnection().use { connection ->
            this.getAll(
                context = DataSourceContext(connection).getContext()
            )
        }
    }

    fun getAll(context: DSLContext): List<Author> {
        return authorRepository.getAll(context)
    }

    fun create(author: Author): Author {
        return DataSource().getConnection().use { connection ->
            this.create(
                author = author,
                context = DataSourceContext(connection).getContext()
            )
        }
    }

    fun create(author: Author, context: DSLContext): Author {
        return authorRepository.create(
            model = author,
            context = context
        )
    }
}