package com.osenov.rickandmorty.data.repository

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.model.*
import com.osenov.rickandmorty.data.page_source.CharacterPageSource
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterPageSourceFactory: CharacterPageSource.Factory
) {

    fun queryCharacters(filter: FilterCharacter): PagingSource<Int, Character> {
        return characterPageSourceFactory.create(filter)
    }

    suspend fun getSingleCharacter(characterId: Long): Flow<Result<Character>> =
        flow {
            try {
                emit(Result.loading<Character>())
                val response = characterRemoteDataSource.fetchSingleCharacter(characterId)
                if (response.isSuccessful) {
                    emit(Result.success(response.body()))
                } else {
                    emit(Result.error<Character>(response.message(), null))
                }
            } catch (e: Exception) {
                emit(Result.error<Character>(e.message.toString(), null))
            }
        }.flowOn(Dispatchers.IO)


    // Return List Episodes with Season item for recycler View
    suspend fun getCharacterEpisodes(episode_ids: List<Int>): Flow<Result<ArrayList<EpisodeUI>>> =
        flow {
            try {
                emit(Result.loading())
                val response = characterRemoteDataSource.fetchCharacterEpisodes(episode_ids)
                if (response.isSuccessful) {
                    val list: ArrayList<EpisodeUI> =
                        ArrayList(response.body()?.map { it.toEpisodeUI() } ?: emptyList())
                    // HashMap to make sure the Season is added
                    val map = HashMap<Int, Boolean>()

                    //index
                    var i = 0
                    while (i < list.size) {
                        val item = list[i] as EpisodeItem
                        val season = item.episode.substring(1, 3).toInt()

                        if (map[season] == null) {
                            map[season] = true
                            list.add(i, Season(season))
                            i++
                        }
                        i++
                    }
                    emit(Result.success(list))
                } else {
                    emit(Result.error<ArrayList<EpisodeUI>>(response.message(), null))
                }
            } catch (e: Exception) {
                emit(Result.error<ArrayList<EpisodeUI>>(e.message.toString(), null))
            }
        }.flowOn(Dispatchers.IO)
}