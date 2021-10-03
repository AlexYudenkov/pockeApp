package ru.alexydenkov.pokeList.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.alexydenkov.pokeList.databinding.PockeItemBinding
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

class PokeAdapter(
	private val pokemonInfoClick: (item: PokemonFeaturesEntity) -> Unit
) : ListAdapter<PokemonFeaturesEntity, PokeViewHolder>(PokeDiffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = PockeItemBinding.inflate(layoutInflater, parent, false)
		return PokeViewHolder(binding,pokemonInfoClick)
	}

	override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
		return holder.bind(getItem(position))
	}

	override fun onViewRecycled(holder: PokeViewHolder) {
		super.onViewRecycled(holder)
		holder.clear()
	}

	private object PokeDiffUtil : DiffUtil.ItemCallback<PokemonFeaturesEntity>() {

		override fun areItemsTheSame(oldItem: PokemonFeaturesEntity, newItem: PokemonFeaturesEntity): Boolean =
			oldItem.name == newItem.name

		override fun areContentsTheSame(oldItem: PokemonFeaturesEntity, newItem: PokemonFeaturesEntity): Boolean =
			oldItem == newItem
	}


}