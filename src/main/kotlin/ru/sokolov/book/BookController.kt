package ru.sokolov.book

import org.jooq.Configuration
import ru.sokolov.author.AuthorDto
import ru.sokolov.author.AuthorMapper
import ru.sokolov.author.AuthorService
import ru.sokolov.connection.DataSource
import ru.sokolov.connection.DataSourceContext

class BookController {
    companion object {
        private val bookMapper = BookMapper()
        private val authorMapper = AuthorMapper()
        private val bookService = BookService()
        private val authorService = AuthorService()
    }

    fun getAll(): List<BookDto> {
        return bookService
            .getAll()
            .map { bookMapper.toDto(it) }
    }

    fun new(bookDraft: BookDto): BookDto {
        val bookNew = bookService.create(
            book = bookMapper.toModel(bookDraft)
        )

        return bookMapper.toDto(bookNew)
    }

    fun create(bookDraft: BookCreateDto): BookCreateDto? {
        var ret: BookCreateDto? = null

        DataSource().getConnection().use { connection ->
            DataSourceContext(connection).getContext().transaction { trx: Configuration ->
                val authorNew = authorService.create(
                    author = authorMapper.toModel(bookDraft.author),
                    context = trx.dsl()
                )

                val bookNew = bookService.create(
                    book = bookMapper.toModel(
                        BookDto(
                            id = bookDraft.id,
                            name = bookDraft.name,
                            author = authorNew.id
                        )
                    ),
                    context = trx.dsl()
                )
                ret = BookCreateDto(
                    id = bookNew.id,
                    name = bookNew.name,
                    author = AuthorDto(
                        id = authorNew.id,
                        firstName = authorNew.firstName,
                        lastName = authorNew.lastName
                    )
                )
            }
        }

        return ret
    }
}