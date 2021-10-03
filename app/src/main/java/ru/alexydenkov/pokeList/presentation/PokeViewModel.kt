package ru.alexydenkov.pokeList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity
import ru.alexydenkov.shared.data.dto.PokemonFeaturesDto
import ru.alexydenkov.shared.domain.GetPokemonUseCase
import ru.alexydenkov.utils.plus

class PokeViewModel(
	private val getPokemonUseCase: GetPokemonUseCase
) : ViewModel(), EventsDispatcherOwner<PokeViewModel.EventsListener> {

	interface EventsListener {

		fun navigateToPokemonInfo(pokemon: PokemonFeaturesEntity)
	}

	companion object{
		const val ITEMS_BY_PAGE = 30
	}

	override val eventsDispatcher: EventsDispatcher<EventsListener> = EventsDispatcher()

	private val _state = MutableStateFlow<PokeState>(PokeState.Init)
	val state: StateFlow<PokeState>
		get() = _state

	private val _pageState = MutableStateFlow(ListState(page = 0))

	private val _pokeList = MutableStateFlow<List<PokemonFeaturesEntity>>(mutableListOf())
	val pokeList: StateFlow<List<PokemonFeaturesEntity>>
		get() = _pokeList

	private val _filterVisibility = MutableStateFlow(true)
	val filterVisibility: StateFlow<Boolean>
		get() = _filterVisibility

	fun fetchInfo() {
		getInfo(_pageState.value.page * ITEMS_BY_PAGE)
	}

	private fun getInfo(offset:Int){
		viewModelScope.launch {
			_state.value = PokeState.Loading

			val pokemonEntity = getPokemonUseCase.invoke(offset)
			val resultList = sortPokemons(pokemonEntity.results.toPokemonFeaturesEntity())

			_pokeList.value = resultList
			_state.value = PokeState.Content
			fetchFeatures(pokemonEntity.results.toPokemonFeatures())
		}
	}

	private fun sortPokemons(pokemonFeaturesEntityList: List<PokemonFeaturesEntity>): List<PokemonFeaturesEntity> {
		_state.value = PokeState.Loading
		val pokemonFeaturesEntityListSorted =
			if (isFiltersIsEnabled()) {
				pokemonFeaturesEntityList.sortedByDescending {
					sortingCriterion(it)
				}
			} else {
				pokemonFeaturesEntityList.sortedBy {
					it.id
				}
			}
		return pokemonFeaturesEntityListSorted
	}

	private suspend fun launchRequest(it:PokemonFeaturesDto, PokemonFeaturesEntityList: List<PokemonFeaturesEntity>){
			val apiResponse = getPokemonUseCase.getPokemonInfo(it.id)
			val r = PokemonFeaturesEntityList.find {
				it.id == apiResponse.id
			}
			r?.height = apiResponse.height
			r?.weight = apiResponse.weight
			r?.types = apiResponse.types
			r?.stats = apiResponse.stats
	}

	fun getNextPage() {
		viewModelScope.launch {
			_pageState.value.page = _pageState.value.page + 1
			_state.value = PokeState.Content

			val pokemonEntity = getPokemonUseCase.invoke(_pageState.value.page * ITEMS_BY_PAGE)

			val contentList = _pokeList.value.toMutableList()
			contentList.addAll(pokemonEntity.results.toPokemonFeaturesEntity())

			_pokeList.value = sortPokemons(contentList)

			fetchFeatures(pokemonEntity.results.toPokemonFeatures())
		}
	}

	private fun fetchFeatures(pokemonFeaturesList: List<PokemonFeaturesDto>) {
		_state.value = PokeState.Content
		val pokemonFeaturesEntityList = _pokeList.value
		viewModelScope.launch {
			coroutineScope {
				pokemonFeaturesList.forEach {
					launch {
						launchRequest(it,pokemonFeaturesEntityList)
					}
				}
			}
			_state.value = PokeState.FullContent
		}
	}

	fun changeFilterCondition(filter: AbilityState) {
		when (filter.filterCondition) {
			FilterCondition.Defense -> _pageState.value.defenseFilter = filter
			FilterCondition.Hp      -> _pageState.value.hpFilter = filter
			FilterCondition.Attack  -> _pageState.value.attackFilter = filter
		}
		val filteredPokemons = sortPokemons(_pokeList.value)
		_pokeList.value = filteredPokemons.toMutableList()
		_state.value = PokeState.FullContent
	}

	private fun isFiltersIsEnabled() =
		_pageState.value.attackFilter.state || _pageState.value.defenseFilter.state || _pageState.value.hpFilter.state

	private fun sortingCriterion(it: PokemonFeaturesEntity): Int {
		val attack = setValueForSortingFeatures(_pageState.value.attackFilter,it)
		val defense = setValueForSortingFeatures(_pageState.value.defenseFilter,it)
		val hp = setValueForSortingFeatures(_pageState.value.hpFilter,it)
		return attack + defense + hp
	}

	private fun setValueForSortingFeatures(filter:AbilityState, pokemon: PokemonFeaturesEntity) =
		if (filter.state) {
			pokemon.stats[filter.filterCondition.key]
		} else {
			0
		}

	fun changeFilterVisibility() {
		_filterVisibility.value = _filterVisibility.value.not()
	}

	fun pokeInfoClick(item: PokemonFeaturesEntity) {
		eventsDispatcher.dispatchEvent {
			navigateToPokemonInfo(item)
		}
	}

	fun fetchRandom() {
		val randomOffset = (0..1000).random()
		getInfo(randomOffset)
	}

}

