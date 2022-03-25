package com.osenov.rickandmorty.data.model

import com.google.gson.annotations.SerializedName
import com.osenov.rickandmorty.data.model.room.EpisodeEntity

data class Episode(
    val id: Long,
    val name: String,
    @SerializedName("air_date") val airDate: String,
    val episode: String,
    val characters: ArrayList<String>
) {
    fun toEpisodeUI() = EpisodeItem(id, name, airDate, episode)

    fun toEpisodeEntity() = EpisodeEntity(id, name, airDate, episode)
}
