package com.osenov.rickandmorty.di.component

import com.osenov.rickandmorty.RickAndMortyApplication
import com.osenov.rickandmorty.di.module.AppModule
import com.osenov.rickandmorty.util.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun injectTo(application: RickAndMortyApplication)

    fun viewModelsFactory(): ViewModelFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: RickAndMortyApplication) : Builder

        fun build() : AppComponent
    }
}