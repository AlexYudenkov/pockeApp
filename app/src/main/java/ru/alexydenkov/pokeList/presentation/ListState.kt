package ru.alexydenkov.pokeList.presentation

data class ListState(
	var page:Int,
	var attackFilter:AbilityState = AbilityState(false,FilterCondition.Attack),
	var defenseFilter:AbilityState = AbilityState(false,FilterCondition.Defense),
	var hpFilter:AbilityState = AbilityState(false,FilterCondition.Hp),
)

data class AbilityState(
	var state: Boolean = false,
	val filterCondition :FilterCondition
)

object ConstObjects{
	const val ATTACK = "attack"
	const val DEFENSE = "defense"
	const val HP = "hp"
}

enum class FilterCondition(val key: String){
	Attack(ConstObjects.ATTACK),
	Defense(ConstObjects.DEFENSE),
	Hp(ConstObjects.HP),
}