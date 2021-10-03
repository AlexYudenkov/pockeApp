package ru.alexydenkov.shared.data

import ru.alexydenkov.shared.domain.PokemonFeaturesEntity
import ru.alexydenkov.shared.domain.PokemonEntity

interface PokeRepository {

	suspend fun getPage(page:Int): PokemonEntity

	suspend fun getPokemonInfo(id:Int): PokemonFeaturesEntity
}