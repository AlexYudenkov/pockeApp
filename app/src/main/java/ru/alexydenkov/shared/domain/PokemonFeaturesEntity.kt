package ru.alexydenkov.shared.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonFeaturesEntity(
	val id: Int,
	var height: Int,
	var weight: Int,
	var types: List<String>,
	var stats: Map<String, Int>,
	val name: String
) : Parcelable
