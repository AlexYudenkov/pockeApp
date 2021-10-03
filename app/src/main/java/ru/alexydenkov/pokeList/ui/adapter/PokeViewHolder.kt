package ru.alexydenkov.pokeList.ui.adapter

import android.text.method.TextKeyListener.*
import androidx.recyclerview.widget.RecyclerView
import coil.util.CoilUtils.clear
import com.bumptech.glide.Glide
import ru.alexydenkov.pokeList.R
import ru.alexydenkov.pokeList.databinding.PockeItemBinding
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

class PokeViewHolder(
	private val binding: PockeItemBinding,
	private val pokemonInfoClick: (item: PokemonFeaturesEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

	companion object {

		const val URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/"
		const val IMAGE_TYPE = ".png"
	}

	fun bind(item: PokemonFeaturesEntity) {
		with(binding) {
			title.text = item.name
			Glide.with(root.context)
				.load(URL + item.id + IMAGE_TYPE)
				.placeholder(R.drawable.ic_broken_image)
				.into(pokeImage)
			binding.root.setOnClickListener {
				pokemonInfoClick(item)
			}
		}
	}

	fun clear() {
		Glide.with(binding.root.context).clear(binding.pokeImage);

	}
}