package com.osenov.rickandmorty.data.remote

import com.osenov.rickandmorty.data.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharacters(@Query("page") numberPage : Int) : Response<CharacterResponse>
}