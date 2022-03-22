package com.osenov.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

sealed class EpisodeUI

data class Season(val number: Int) : EpisodeUI()

data class EpisodeItem(
    val id: Long,
    val name: String,
    @SerializedName("air_date") val airDate: String,
    val episode: String
) : EpisodeUI()