package ru.alexydenkov.shared.data

import ru.alexydenkov.shared.domain.PokemonFeaturesEntity
import ru.alexydenkov.pokeList.presentation.toEntity
import ru.alexydenkov.pokeList.presentation.toPokemonEntity
import ru.alexydenkov.shared.domain.PokemonEntity

class PokemonDataSourceImp(private val api: PokemonApi) : PokeDataSource {

	override suspend fun getPage(page: Int): PokemonEntity =
		api.getPage(page).toPokemonEntity()

	override suspend fun getPokemonInfo(id: Int): PokemonFeaturesEntity =
		api.getPokemonInfo(id).toEntity()

}