package com.osenov.rickandmorty.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
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
) : Parcelable
