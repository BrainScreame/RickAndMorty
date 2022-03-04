package com.osenov.rickandmorty

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate.*
import com.osenov.rickandmorty.di.component.AppComponent
import com.osenov.rickandmorty.di.component.DaggerAppComponent

class RickAndMortyApplication : Application() {

    private var _appComponent: AppComponent? = null

    val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            resources.getString(R.string.app_component_info)
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .application(application = this)
            .build()

        appComponent.injectTo(this)


        //setDefaultNightMode(MODE_NIGHT_YES)
    }
}