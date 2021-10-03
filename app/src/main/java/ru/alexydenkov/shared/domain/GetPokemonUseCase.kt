package ru.alexydenkov.shared.domain

import ru.alexydenkov.shared.data.PokeRepository

class GetPokemonUseCase(private val repository: PokeRepository) {

	suspend fun invoke(page: Int): PokemonEntity {
		return repository.getPage(page)
	}

	suspend fun getPokemonInfo(id: Int): PokemonFeaturesEntity {
		return repository.getPokemonInfo(id)
	}
}
