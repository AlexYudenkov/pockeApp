package ru.alexydenkov.shared.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.alexydenkov.shared.data.dto.PokemonDto
import ru.alexydenkov.shared.data.dto.PokemonFeaturesDto

interface PokemonApi {

	@GET("pokemon?&limit=30")
	suspend fun getPage(@Query("offset") page:Int ): PokemonDto

	@GET("pokemon/{id}/")
	suspend fun getPokemonInfo(@Path("id") id:Int ):PokemonFeaturesDto

}