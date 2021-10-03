package ru.alexydenkov.pokeList.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.alexydenkov.pokeList.presentation.PokeViewModel

val PokeViewModelModule = module {
	viewModel{
		PokeViewModel(get())
	}
}

internal val PokeListModules = listOf(
	PokeViewModelModule,
)