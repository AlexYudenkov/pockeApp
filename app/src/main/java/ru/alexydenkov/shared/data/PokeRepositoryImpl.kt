package ru.alexydenkov.shared.data

import ru.alexydenkov.shared.domain.PokemonEntity
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

class PokeRepositoryImpl(
	private val remoteDataSource: PokeDataSource,
) : PokeRepository {

	override suspend fun getPage(page: Int): PokemonEntity =
		remoteDataSource.getPage(page)

	override suspend fun getPokemonInfo(id: Int): PokemonFeaturesEntity =
		remoteDataSource.getPokemonInfo(id)


}
