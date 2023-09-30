package ru.sokolov.book

class BookMapper {
    fun toModel(dto: BookDto): Book {
        return Book(
            id = dto.id ?: 0L,
            name = dto.name,
            author = dto.author
        )
    }

    fun toDto(model: Book): BookDto {
        return BookDto(
            id = model.id,
            name = model.name,
            author = model.author
        )
    }
}