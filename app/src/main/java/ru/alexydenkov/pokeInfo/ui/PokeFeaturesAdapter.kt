package ru.alexydenkov.pokeInfo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.alexydenkov.pokeList.databinding.PokeFeaturesItemBinding

class PokeFeaturesAdapter(
	private val info: List<String>,
) : RecyclerView.Adapter<PokeFeaturesViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeFeaturesViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val binding = PokeFeaturesItemBinding.inflate(layoutInflater, parent, false)
		return PokeFeaturesViewHolder(binding)
	}

	override fun onBindViewHolder(holder: PokeFeaturesViewHolder, position: Int) {
		holder.bind(info[position])
	}

	override fun getItemCount(): Int =
		info.size

}