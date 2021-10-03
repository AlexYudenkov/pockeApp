package ru.alexydenkov.pokeInfo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import ru.alexydenkov.pokeList.databinding.FragmentPokeInfoBinding
import ru.alexydenkov.shared.domain.PokemonFeaturesEntity

class PokeInfo : Fragment() {

	companion object{
		const val URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"
		const val TYPE_IMAGE = ".svg"
	}

	private var _binding: FragmentPokeInfoBinding? = null
	private val binding
		get() = _binding ?: throw NullPointerException("Binding can't be null")

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentPokeInfoBinding.inflate(inflater, container, false)
		val pokemon = arguments?.getParcelable<PokemonFeaturesEntity>("ITEM")
		bindComponents(pokemon)
		return binding.root
	}

	private fun bindComponents(pokemon: PokemonFeaturesEntity?) {
		if (pokemon != null) {
			downloadImage(pokemon)

			bindFeatures(pokemon)
			bindStats(pokemon)
			bindTypes(pokemon)


		}
	}

	private fun bindTypes(pokemon: PokemonFeaturesEntity){
		val typesList = mutableListOf<String>()
		pokemon.types.forEach {
			typesList.add("тип: $it")
		}
		val typeAdapter = PokeFeaturesAdapter(typesList)
		binding.pokeTypes.adapter = typeAdapter
	}

	private fun bindFeatures(pokemon: PokemonFeaturesEntity){
		binding.pokeHeight.text = pokemon.height.toString()
		binding.pokeWeight.text = pokemon.weight.toString()
		binding.pokeName.text = pokemon.name
	}

	private fun bindStats(pokemon: PokemonFeaturesEntity){
		val argument = pokemon.stats.toList().map { "${it.first} ${it.second}" }.toMutableList()
		val adapter = PokeFeaturesAdapter(argument)
		binding.abilities.adapter = adapter
	}

	private fun downloadImage(pokemon: PokemonFeaturesEntity){
		val imageLoader = ImageLoader.Builder(binding.root.context)
			.componentRegistry {
				add(SvgDecoder(binding.root.context))
			}
			.build()

		val request = ImageRequest.Builder(binding.root.context)
			.data(  URL+ pokemon.id + TYPE_IMAGE)
			.target(binding.pokeMainImage)
			.build()
		imageLoader.enqueue(request)
	}

}