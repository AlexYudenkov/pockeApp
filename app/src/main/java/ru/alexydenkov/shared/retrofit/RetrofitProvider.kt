package ru.alexydenkov.shared.retrofit

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.alexydenkov.shared.data.PokemonApi

object InternetUrl  {
	const val BASE_URL = "https://pokeapi.co/api/v2/"
}

fun provideApi(retrofit: Retrofit) = retrofit.create(PokemonApi::class.java)

fun provideRetrofit(moshi: Moshi): Retrofit =
	Retrofit.Builder().apply {
		addConverterFactory(MoshiConverterFactory.create(moshi))
		baseUrl(InternetUrl.BASE_URL)
	}.build()