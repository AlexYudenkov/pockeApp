package ru.alexydenkov.pokeList.ui

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.alexydenkov.pokeList.R
import ru.alexydenkov.pokeList.databinding.FragmentPokeListBinding
import ru.alexydenkov.pokeList.presentation.AbilityState
import ru.alexydenkov.pokeList.presentation.FilterCondition
import ru.alexydenkov.pokeList.presentation.PokeState
import ru.alexydenkov.pokeList.presentation.PokeViewModel
import ru.alexydenkov.pokeList.ui.adapter.PokeAdapter
import ru.alexydenkov.pokeList.ui.render.renderContent
import ru.alexydenkov.pokeList.ui.render.renderFullContent
import ru.alexydenkov.pokeList.ui.render.renderLoading
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

class PokeList : Fragment(), PokeViewModel.EventsListener {

	companion object {

		const val ITEM = "ITEM"
	}

	private var _binding: FragmentPokeListBinding? = null
	private val binding
		get() = _binding ?: throw NullPointerException("Binding can't be null")

	val viewModel: PokeViewModel by viewModel()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPokeListBinding.inflate(inflater, container, false)

		if (viewModel.pokeList.value.isEmpty()) {
			viewModel.fetchInfo()
		}
		viewModel.eventsDispatcher.bind(viewLifecycleOwner, this)
		bindComponents()
		bindState(viewModel.state)
		return binding.root
	}

	private fun bindState(state: Flow<PokeState>) {
		state.onEach {
			when (it) {
				PokeState.Loading     -> renderLoading(binding)
				is PokeState.Content  -> renderContent(binding)
				PokeState.FullContent -> renderFullContent(binding)
			}
		}.launchIn(viewLifecycleOwner.lifecycleScope)
	}

	private fun bindComponents() {
		initRecyclerView()
		bindButtons()
		bindFilters()
		bindArrows()
	}

	private fun bindButtons() {
		binding.floatingActionButton.setOnClickListener{
			viewModel.fetchRandom()
		}

		binding.choseAttack.setOnCheckedChangeListener { _, isChecked ->
			viewModel.changeFilterCondition(AbilityState(isChecked, FilterCondition.Attack))
		}
		binding.choseDefense.setOnCheckedChangeListener { _, isChecked ->
			viewModel.changeFilterCondition(AbilityState(isChecked, FilterCondition.Defense))
		}
		binding.choseHp.setOnCheckedChangeListener { _, isChecked ->
			viewModel.changeFilterCondition(AbilityState(isChecked, FilterCondition.Hp))
		}
	}

	private fun bindFilters() {
		binding.filterButton.setOnClickListener {
			viewModel.changeFilterVisibility()
		}

		viewModel.filterVisibility.onEach {
			TransitionManager.beginDelayedTransition(binding.root)
			binding.filter.isVisible = it
			binding.arrowUp.isVisible = it.not()
		}.launchIn(viewLifecycleOwner.lifecycleScope)
	}

	private fun bindArrows() {
		binding.arrowUp.setOnClickListener {
			binding.pokeRv.scrollToPosition(0)
		}
	}

	private fun initRecyclerView() {
		val adapter = PokeAdapter(viewModel::pokeInfoClick)
		binding.pokeRv.adapter = adapter
		submitList(adapter)
		addNextPageListener()
	}

	private fun submitList(adapter: PokeAdapter) {
		viewModel.state.filterIsInstance<PokeState.FullContent>()
			.onEach {
				adapter.submitList(viewModel.pokeList.value) {
					if (viewModel.filterVisibility.value) {
						binding.pokeRv.scrollToPosition(0)
					}
				}
			}.launchIn(viewLifecycleOwner.lifecycleScope)
	}

	private fun addNextPageListener() {
		binding.pokeRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)
				if (!recyclerView.canScrollVertically(1)) {
					viewModel.getNextPage()
				}
			}
		})
	}

	override fun navigateToPokemonInfo(pokemon: PokemonFeaturesEntity) {
		val bundle = Bundle()
		bundle.putParcelable(ITEM, pokemon)
		findNavController().navigate(R.id.pokemonInfo, bundle)
	}
}

