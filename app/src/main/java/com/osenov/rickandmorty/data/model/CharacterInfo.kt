package com.osenov.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharacterInfo(
    val count: Int,
    val pages: Int,

    @SerializedName("next")
    val nextUrl: String,

    @SerializedName("prev")
    val prevUrl: String,

    @SerializedName("results")
    val characters : ArrayList<Character>
)