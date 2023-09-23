package ru.sokolov.author

class AuthorMapper {
    fun toModel(dto: AuthorDto): Author {
        return Author(
            id = dto.id ?: 0L,
            firstName = dto.firstName,
            lastName = dto.lastName
        )
    }

    fun toDto(model: Author): AuthorDto {
        return AuthorDto(
            id = model.id,
            firstName = model.firstName,
            lastName = model.lastName
        )
    }
}