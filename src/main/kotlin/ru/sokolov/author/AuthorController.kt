package ru.sokolov.author

class AuthorController {
    companion object {
        private val authorMapper = AuthorMapper()
        private val authorService = AuthorService()
    }

    fun getAll(): List<AuthorDto> {
        return authorService
            .getAll()
            .map { authorMapper.toDto(it) }
    }

    fun create(authorDraft: AuthorDto): AuthorDto {
        val authorNew = authorService.create(
            author = authorMapper.toModel(authorDraft)
        )

        return authorMapper.toDto(authorNew)
    }
}