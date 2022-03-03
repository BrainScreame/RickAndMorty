package com.osenov.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info") val information: CharacterInfo,
    @SerializedName("results") val characters: ArrayList<Character>
)