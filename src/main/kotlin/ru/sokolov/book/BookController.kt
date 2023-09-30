package ru.sokolov.book

class BookController {
    companion object {
        private val bookMapper = BookMapper()
        private val bookService = BookService()
    }

    fun getAll(): List<BookDto> {
        return bookService
            .getAll()
            .map { bookMapper.toDto(it) }
    }

    fun create(authorDraft: BookDto): BookDto {
        val authorNew = bookService.create(
            author = bookMapper.toModel(authorDraft)
        )

        return bookMapper.toDto(authorNew)
    }
}