package com.osenov.rickandmorty

import android.app.Application
import com.osenov.rickandmorty.di.component.AppComponent
import com.osenov.rickandmorty.di.component.DaggerAppComponent

class RickAndMortyApplication : Application() {

    private var _appComponent: AppComponent? = null

    private val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            resources.getString(R.string.app_component_info)
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder()
            .application(application = this)
            .build()
    }
}