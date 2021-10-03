package ru.alexydenkov.shared.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonFeaturesDto(
	@Json(name = "id") val id: Int,
	@Json(name = "height") val height:Int,
	@Json(name = "weight")val weight:Int,
	@Json(name = "types")val types: List<Type>,
	@Json(name = "stats") var stats: List<Stat>,
	@Json(name = "name") val name: String
)

data class Stat(
	val base_stat: Int,
	val effort: Int,
	val stat: StatItem
)

data class StatItem(
	val name: String,
	val url: String
)

data class Type(
	val slot: Int,
	val type: StatItem
)
