package com.osenov.rickandmorty.data.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.osenov.rickandmorty.data.model.Episode
import com.osenov.rickandmorty.data.model.EpisodeItem

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey val id: Long,
    val name: String,
    @ColumnInfo(name = "air_date") val airDate: String,
    val episode: String,
) {
    fun toEpisode() = Episode(id, name, airDate, episode, ArrayList())

    fun toEpisodeUI() = EpisodeItem(id, name, airDate, episode)
}