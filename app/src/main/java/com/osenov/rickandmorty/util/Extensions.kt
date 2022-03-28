package com.osenov.rickandmorty.util

import android.content.Context
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import com.osenov.rickandmorty.RickAndMortyApplication
import com.osenov.rickandmorty.data.model.EpisodeItem
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.Season
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

fun ArrayList<EpisodeUI>.addSeasons() {
    val map = HashMap<Int, Boolean>()

    //index
    var i = 0
    while (i < this.size) {
        val item = this[i] as EpisodeItem
        val season = item.episode.substring(1, 3).toInt()

        if (map[season] == null) {
            map[season] = true
            this.add(i, Season(season))
            i++
        }
        i++
    }
}
