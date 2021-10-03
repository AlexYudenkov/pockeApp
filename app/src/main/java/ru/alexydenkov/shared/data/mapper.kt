package ru.alexydenkov.pokeList.presentation

import ru.alexydenkov.shared.data.dto.PokemonDto
import ru.alexydenkov.shared.data.dto.PokemonFeaturesDto
import ru.alexydenkov.shared.data.dto.Result
import ru.alexydenkov.shared.domain.PokemonEntity
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

fun Result.toPokemonFeatures(): PokemonFeaturesDto {
	val ids = url.split('/')
	val id = ids[ids.size - 2].toInt()
	return PokemonFeaturesDto(
		id = id,
		name = name,
		stats = listOf(),
		types = listOf(),
		height = 0,
		weight = 0
	)
}

fun PokemonFeaturesDto.toEntity(): PokemonFeaturesEntity {
	return PokemonFeaturesEntity(
		id = id,
		name = name,
		stats = stats.map {
			it.stat.name to it.base_stat
		}.toMap(),
		height = height,
		weight = weight,
		types = types.map {
			it.type.name
		}.toList()

	)
}

fun List<Result>.toPokemonFeatures() = map(Result::toPokemonFeatures)

fun Result.toPokemonFeaturesEntity(): PokemonFeaturesEntity {
	val ids = url.split('/')
	val id = ids[ids.size - 2].toInt()
	return PokemonFeaturesEntity(
		id = id,
		name = name,
		stats = mapOf(),
		types = listOf(),
		height = 0,
		weight = 0
	)
}

fun List<Result>.toPokemonFeaturesEntity() = map(Result::toPokemonFeaturesEntity)

fun PokemonDto.toPokemonEntity() =
	PokemonEntity(
		next = next,
		previous = previous,
		results = results
	)