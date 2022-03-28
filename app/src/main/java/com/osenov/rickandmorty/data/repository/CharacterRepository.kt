package com.osenov.rickandmorty.data.repository

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.EpisodeUI
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun queryCharacters(filter: FilterCharacter): PagingSource<Int, Character>

    suspend fun getSingleCharacter(characterId: Long): Flow<Result<Character>>

    suspend fun getCharacterEpisodes(episode_ids: List<Int>): Flow<Result<List<EpisodeUI>>>
}