package com.osenov.rickandmorty.data.repository

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.db.CharacterDao
import com.osenov.rickandmorty.data.model.*
import com.osenov.rickandmorty.data.page_source.CharacterPageSource
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.util.addSeasons
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterDao: CharacterDao,
    private val characterPageSourceFactory: CharacterPageSource.Factory
) : CharacterRepository {

    override fun queryCharacters(filter: FilterCharacter): PagingSource<Int, Character> {
        return characterPageSourceFactory.create(filter)
    }

    override suspend fun getSingleCharacter(characterId: Long): Flow<Result<Character>> =
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
    override suspend fun getCharacterEpisodes(episode_ids: List<Int>): Flow<Result<List<EpisodeUI>>> =
        flow {
            try {
                emit(Result.loading())
                val response = characterRemoteDataSource.fetchCharacterEpisodes(episode_ids)
                val episodes = checkNotNull(response.body())
                val list: ArrayList<EpisodeUI> = ArrayList(episodes.map { it.toEpisodeUI() })
                characterDao.insertEpisodes(episodes.map { it.toEpisodeEntity() })

                // HashMap to make sure the Season is added
                list.addSeasons()
                emit(Result.success(list))

            } catch (e: UnknownHostException) {
                val list: ArrayList<EpisodeUI> = ArrayList(
                    characterDao.getCharacterEpisodes(episode_ids).map { it.toEpisodeUI() })
                list.addSeasons()
                if (list.isEmpty()) {
                    emit(Result.error<ArrayList<EpisodeUI>>(e.message ?: "Exception", null))
                } else {
                    emit(Result.success(list))
                }
            } catch (e: Exception) {
                emit(Result.error<ArrayList<EpisodeUI>>(e.message.toString(), null))
            }
        }.flowOn(Dispatchers.IO)

}