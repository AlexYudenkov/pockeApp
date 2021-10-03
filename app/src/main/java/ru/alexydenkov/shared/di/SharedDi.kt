package ru.alexydenkov.shared.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import ru.alexydenkov.shared.data.*
import ru.alexydenkov.shared.domain.GetPokemonUseCase
import ru.alexydenkov.shared.retrofit.provideApi
import ru.alexydenkov.shared.retrofit.provideRetrofit

val UseCaseModule = module {
	factory { GetPokemonUseCase(get()) }
}

val CurrencyDataModule = module {
	factory<PokeRepository> { PokeRepositoryImpl(remoteDataSource = get()) }

	factory<PokeDataSource> { PokemonDataSourceImp(api = get()) }
}

val NetworkModule = module {
	single { provideRetrofit(get()) }
}

val ProvideMoshi = module {
	single { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }
}

val ApiModule = module {
	factory { provideApi(get()) }
}


val PocketListSharedModule = listOf(
	UseCaseModule,
	ProvideMoshi,
	NetworkModule,
	CurrencyDataModule,
	ApiModule,
)