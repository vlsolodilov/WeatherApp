package com.solodilov.weatherapp.di

import android.app.Application
import com.solodilov.weatherapp.ui.MainActivity
import com.solodilov.weatherapp.ui.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
	DataModule::class,
	NetworkModule::class,
	ViewModelModule::class,
])
interface ApplicationComponent {

	fun inject(activity: MainActivity)
	fun inject(fragment: MainFragment)

	@Component.Factory
	interface Factory {
		fun create(
			@BindsInstance application: Application
		): ApplicationComponent
	}
}