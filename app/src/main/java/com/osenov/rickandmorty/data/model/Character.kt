package com.osenov.rickandmorty.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.osenov.rickandmorty.data.model.room.CharacterEntity
import com.osenov.rickandmorty.data.model.room.EpisodeEntity
import com.osenov.rickandmorty.data.model.room.EpisodeUrlEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("episode") val episodes: ArrayList<String>,
    val url: String,
    val created: String
) : Parcelable {

    fun toCharacterEntity() = CharacterEntity(
        id, name, status, species, type, gender, origin, location, imageUrl, url, created
    )

    fun toEpisodeUrlEntity() = episodes.map {
        EpisodeUrlEntity(0, id, it)
    }
}
