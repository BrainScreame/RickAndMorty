package com.osenov.rickandmorty.util

import android.content.Context
import com.osenov.rickandmorty.RickAndMortyApplication
import com.osenov.rickandmorty.di.component.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is RickAndMortyApplication -> appComponent
        else -> this.applicationContext.appComponent
    }