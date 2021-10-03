package ru.alexydenkov.pokeInfo.presentation

import androidx.lifecycle.ViewModel
import ru.alexydenkov.pokeList.presentation.EventsDispatcher
import ru.alexydenkov.pokeList.presentation.EventsDispatcherOwner

class PokeInfoViewModel(

) : ViewModel(), EventsDispatcherOwner<PokeInfoViewModel.EventsListener>  {

	interface EventsListener {

	}

	override val eventsDispatcher : EventsDispatcher<EventsListener> = EventsDispatcher()
}