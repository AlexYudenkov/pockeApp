package ru.alexydenkov.shared.domain

import ru.alexydenkov.shared.data.dto.Result

data class PokemonEntity(
	val next: String?,
	val previous: String?,
	var results: List<Result>
)
