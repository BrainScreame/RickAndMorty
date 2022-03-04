package com.osenov.rickandmorty.data.repository

import androidx.paging.PagingSource
import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.model.CharacterResponse
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class CharacterRepository
@Inject constructor(private val characterRemoteDataSource: CharacterRemoteDataSource) {

    suspend fun getCharactersResponse(numberPage: Int) =
        characterRemoteDataSource.fetchCharacters(numberPage)

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
}