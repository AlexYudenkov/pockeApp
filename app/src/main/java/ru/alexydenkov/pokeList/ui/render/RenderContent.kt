package ru.alexydenkov.pokeList.ui.render

import android.graphics.drawable.Animatable
import android.view.View
import ru.alexydenkov.pokeList.R
import ru.alexydenkov.pokeList.databinding.FragmentPokeListBinding

fun renderFullContent(binding: FragmentPokeListBinding) {
	binding.loadingFilters.visibility = View.GONE
	binding.filterContent.visibility = View.VISIBLE
	binding.pokeRv.visibility = View.VISIBLE
	binding.loader.visibility = View.GONE
	binding.error.visibility = View.GONE
}

fun renderContent(binding: FragmentPokeListBinding) {
	binding.loadingFilters.visibility = View.VISIBLE
	binding.filterContent.visibility = View.GONE
	binding.pokeRv.visibility = View.VISIBLE
	binding.loader.visibility = View.GONE
	binding.error.visibility = View.GONE
}

fun renderLoading(binding: FragmentPokeListBinding) {
	val image = binding.loader
	image.setImageResource(R.drawable.ic_vector_anim_loader)
	(image.drawable as Animatable).start()
	binding.loadingFilters.visibility = View.GONE
	binding.filterContent.visibility = View.GONE
	binding.pokeRv.visibility = View.GONE
	binding.loader.visibility = View.VISIBLE
	binding.error.visibility = View.VISIBLE
}