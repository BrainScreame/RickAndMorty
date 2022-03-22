package com.osenov.rickandmorty.data.remote

import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.CharacterResponse
import com.osenov.rickandmorty.data.model.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") numberPage: Int,
        @Query("name") query: String,
        @Query("status") status: String,
        @Query("gender") gender: String
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getSingleCharacter(@Path("id") characterId: Long): Response<Character>

    @GET("episode/{episode_ids}")
    suspend fun getCharacterEpisodes(@Path("episode_ids") episode_ids: List<Int>): Response<List<Episode>>
}