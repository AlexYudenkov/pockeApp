package ru.alexydenkov

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.alexydenkov.pokeList.di.PokeListModules
import ru.alexydenkov.shared.di.PocketListSharedModule

class App():Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin{
			androidLogger()
			androidContext(this@App)
			modules(PokeListModules)
			modules(PocketListSharedModule)
		}
	}
}