package com.osenov.rickandmorty.util

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import com.osenov.rickandmorty.RickAndMortyApplication
import com.osenov.rickandmorty.di.component.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is RickAndMortyApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

fun NavController.safeNavigate(
    @IdRes currentDestinationId: Int,
    @IdRes id: Int,
    args: Bundle? = null
) {
    if (currentDestinationId == currentDestination?.id) {
        navigate(id, args)
    }
}
