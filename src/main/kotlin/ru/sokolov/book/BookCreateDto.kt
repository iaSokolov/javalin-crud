package ru.sokolov.book

import ru.sokolov.author.AuthorDto

data class BookCreateDto(
    val id: Long?,
    val name: String,
    val author: AuthorDto
)
