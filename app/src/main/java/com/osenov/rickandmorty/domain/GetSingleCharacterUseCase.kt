package com.osenov.rickandmorty.domain

import com.osenov.rickandmorty.data.model.Character
import com.osenov.rickandmorty.data.repository.CharacterRepository
import com.osenov.rickandmorty.data.repository.DefaultCharacterRepository
import com.osenov.rickandmorty.util.network.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend fun execute(characterId: Long): Flow<Result<Character>> {
        return characterRepository.getSingleCharacter(characterId)
    }
}