package com.osenov.rickandmorty.data.repository

import com.osenov.rickandmorty.data.model.CharacterResponse
import com.osenov.rickandmorty.data.remote.CharacterRemoteDataSource
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterRepository
@Inject constructor(private val characterRemoteDataSource: CharacterRemoteDataSource) {

    suspend fun getCharactersResponse(numberPage: Int) =
        characterRemoteDataSource.fetchCharacters(numberPage)

    fun getCharacters(numberPage: Int): Flow<Result<CharacterResponse>> =
        flow {
            emit(Result.loading<CharacterResponse>())
            val response = characterRemoteDataSource.fetchCharacters(numberPage)
            if (response.isSuccessful) {
                emit(Result.success(response.body()))
            } else {
                emit(Result.error<CharacterResponse>(response.message(), null))
            }
        }.flowOn(Dispatchers.IO)
}