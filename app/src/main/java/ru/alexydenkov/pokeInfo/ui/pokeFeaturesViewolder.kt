package ru.alexydenkov.pokeInfo.ui

import androidx.recyclerview.widget.RecyclerView
import ru.alexydenkov.pokeList.databinding.PokeFeaturesItemBinding

class PokeFeaturesViewHolder(
	private val binding: PokeFeaturesItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

	fun bind(str: String) {
		binding.textView4.text = str
	}
}