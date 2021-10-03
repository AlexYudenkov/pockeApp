package ru.alexydenkov.shared.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDto(
	@Json(name = "next") val next: String?,
	@Json(name = "previous") val previous: String?,
	@Json(name = "results") var results: List<Result>
)

@JsonClass(generateAdapter = true)
data class Result(
	@Json(name = "name") val name: String,
	@Json(name = "url") val url: String,
)