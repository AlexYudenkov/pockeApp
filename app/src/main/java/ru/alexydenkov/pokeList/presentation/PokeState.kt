package ru.alexydenkov.pokeList.presentation

sealed class PokeState{
	object Loading: PokeState()
	object Init: PokeState()
	object Content: PokeState()
	object FullContent: PokeState()
}
