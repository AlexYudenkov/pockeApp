package ru.alexydenkov.utils

operator fun Int?.plus(i: Int?): Int {
	val a = this ?: 0
	val b = i ?: 0
	return a + b
}